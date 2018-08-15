package hello.FinancialProjection;

import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.ComissionTransaction.ComissionTransaction;
import hello.ComissionTransaction.ComissionTransactionRepository;
import hello.Currency.CurrencyService;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.EmployeeTransaction.EmployeeTransactionRepository;
import hello.Enums.Category;
import hello.Enums.Genre;
import hello.FinancialProjection.Resources.FinancialProjection;
import hello.FinancialProjection.Resources.FinancialProjectionValidated;
import hello.GeneralTransaction.GeneralTransaction;
import hello.GeneralTransaction.GeneralTransactionRepository;
import hello.GeneralTransaction.GeneralTransactionService;
import hello.Project.Project;
import hello.Project.ProjectRepository;
import hello.Project.ProjectService;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionRepository;
import hello.SaleTransaction.SaleTransaction;
import hello.SaleTransaction.SaleTransactionRepository;
import hello.SaleTransaction.SaleTransactionService;
import hello.SheetTransaction.SheetTransaction;
import hello.SheetTransaction.SheetTransactionRepository;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.SupplierTransaction.SupplierTransaction;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionRepository;
import hello.Transaction.TransactionSpecifications;
import hello.Type.Type;
import hello.Type.TypeRepository;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialProjectionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    SheetTransactionRepository sheetTransactionRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    ProjectTransactionRepository projectTransactionRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeTransactionRepository employeeTransactionRepository;
    @Autowired
    SaleTransactionRepository saleTransactionRepository;
    @Autowired
    GeneralTransactionRepository generalTransactionRepository;
    @Autowired
    ComissionTransactionRepository comissionTransactionRepository;

    public Page<Transaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return transactionRepository.findAllByGenreAndExecutedAndActived(pageable, genre, false, true);
    }

    private Page<Transaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<Transaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return transactionRepository.findAllByGenreAndExecutedAndActived(pageable, genre, false, true);

        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
        }

        Specification<Transaction> specFilter= TransactionSpecifications.filterToFinancialProjection(value, frequency, typeValue, subTypeList, dateSince, dateUntil,valueSince, valueUntil, genre);

        transactionsPage = transactionRepository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public Transaction getTransaction(long id)
    {
        return transactionRepository.findById(id);
    }

    public void aproveTransaction(FinancialProjectionValidated financialProjectionValidated)
    {

        Transaction transaction = transactionRepository.findById((long) financialProjectionValidated.getId());

        //used if transaction has more than one installment
        Transaction transactionAux = null;

        if(transaction.getInstallments() == 1){
            transaction.setExecuted(true);

            if(transaction instanceof SheetTransaction)
                ((SheetTransaction)transaction).setHoursPerProjectList(((SheetTransaction) transaction).getHoursPerProjectList());

        }
        else
        {

            for(int i = 0; i< financialProjectionValidated.getInstallments(); i++) {


                //if the last element... otherwise the transaction will has installments = 0
                if(i==transaction.getInstallments()-1)
                {
                    transaction.setExecuted(true);
                    break;
                }


                if (transaction instanceof SheetTransaction) {
                    transactionAux = new SheetTransaction();
                    ((SheetTransaction) transactionAux).setHoursPerProjectList(new ArrayList<>());
                    ((SheetTransaction) transactionAux).getHoursPerProjectList().addAll(financialProjectionValidated.getHoursPerProjectList());

                    ((SheetTransaction) transactionAux).setEmployee(((SheetTransaction) transaction).getEmployee());


                } else if (transaction instanceof EmployeeTransaction) {
                    transactionAux = new EmployeeTransaction();
                    ((EmployeeTransaction) transactionAux).setEmployee(((EmployeeTransaction) transaction).getEmployee());
                }
                else if (transaction instanceof SupplierTransaction) {
                    transactionAux = new SupplierTransaction();
                    ((SupplierTransaction) transactionAux).setSupplier(((SupplierTransaction) transaction).getSupplier());
                }
                else if (transaction instanceof ComissionTransaction) {
                    transactionAux = new ComissionTransaction();
                    ((ComissionTransaction) transactionAux).setClient(((ComissionTransaction) transaction).getClient());
                    ((ComissionTransaction) transactionAux).setProject(((ComissionTransaction) transaction).getProject());
                }
                else if (transaction instanceof ProjectTransaction) {
                    transactionAux = new ProjectTransaction();
                    ((ProjectTransaction) transactionAux).setProject(((ProjectTransaction) transaction).getProject());
                }
                else if (transaction instanceof SaleTransaction)
                    transactionAux = new SaleTransaction();
                else
                    transactionAux = new GeneralTransaction();

                transactionAux.setName(transaction.getName());
                if (transaction.getDescription() != null)
                    transactionAux.setDescription(transaction.getDescription());
                transactionAux.setFrequency(transaction.getFrequency());
                transactionAux.setDate(financialProjectionValidated.getDate());
                transactionAux.setCurrency(transaction.getCurrency());
                transactionAux.setValue(transaction.getValue());
                transactionAux.setGenre(transaction.getGenre());
                transactionAux.setType(transaction.getType());
                transactionAux.setExecuted(true);

                if (transactionAux != null)
                    transactionRepository.save(transactionAux);
            }


            //we need this if otherwise the last transaction will have 0 installments
            if(transaction.getInstallments() == financialProjectionValidated.getInstallments() )
                transaction.setInstallments(1);
            else
                transaction.setInstallments(transaction.getInstallments() - financialProjectionValidated.getInstallments());
        }
        transactionRepository.save(transaction);
    }

    public void removeTransaction(Long id) {
        Transaction transaction = getTransaction((long)id);
        transaction.setActived(false);
        transactionRepository.save(transaction);
    }

    public void addTransaction(@Valid FinancialProjection projection) {

        Transaction transaction;


        if(projection.getProject()!=null)
        {

            if(projection.getClient()!= null)
            {
                transaction = new ComissionTransaction();
                Client client = clientRepository.findById((long)projection.getClient().getId());
                ((ComissionTransaction)transaction).setClient(client);
                Project project = projectRepository.findById((long)projection.getProject().getId());
                ((ComissionTransaction)transaction).setProject(project);

            }
            else
            {
                transaction = new ProjectTransaction();
                Project project = projectRepository.findById((long)projection.getProject().getId());
                ((ProjectTransaction)transaction).setProject(project);
            }
        }
        else if(projection.getEmployee()!=null)
        {
            if(projection.getHoursPerProjectList()!=null)
            {
                transaction = new SheetTransaction();
                Employee employee = employeeRepository.findById((long)projection.getEmployee().getId());
                ((SheetTransaction)transaction).setEmployee(employee);
                ((SheetTransaction) transaction).setHoursPerProjectList(new ArrayList<>());
                ((SheetTransaction) transaction).getHoursPerProjectList().addAll(projection.getHoursPerProjectList());

            }
            else
            {
                transaction = new EmployeeTransaction();
                Employee employee = employeeRepository.findById((long)projection.getEmployee().getId());
                ((EmployeeTransaction)transaction).setEmployee(employee);
            }
        }

        else
            transaction = new GeneralTransaction();


        transaction.setInstallments(projection.getInstallments());
        transaction.setExecuted(false);
        transaction.setGenre(projection.getGenre());
        transaction.setValue(projection.getValue());
        transaction.setFrequency(projection.getFrequency());
        transaction.setDate(projection.getDate());
        transaction.setCurrency(projection.getCurrency());
        transaction.setName(projection.getName());
        transaction.setDescription(projection.getDescription());


        Type typeAux = new Type(projection.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : projection.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        transaction.setType(typeAux);

        if(transaction instanceof SaleTransaction)
            saleTransactionRepository.save((SaleTransaction)transaction);
        else if(transaction instanceof GeneralTransaction)
            generalTransactionRepository.save((GeneralTransaction) transaction);
        else if(transaction instanceof ProjectTransaction)
            projectTransactionRepository.save((ProjectTransaction) transaction);
        else if(transaction instanceof EmployeeTransaction)
            employeeTransactionRepository.save((EmployeeTransaction) transaction);
        else if(transaction instanceof SheetTransaction)
            sheetTransactionRepository.save((SheetTransaction) transaction);
        else if(transaction instanceof ComissionTransaction)
            comissionTransactionRepository.save((ComissionTransaction) transaction);


//        transactionRepository.save(transaction);


    }
}
