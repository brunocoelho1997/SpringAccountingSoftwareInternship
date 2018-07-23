package hello.FinancialProjection;

import hello.EmployeeTransaction.EmployeeTransaction;
import hello.Enums.Genre;
import hello.FinancialProjection.Resources.FinancialProjectionValidated;
import hello.GeneralTransaction.GeneralTransaction;
import hello.SheetTransaction.SheetTransaction;
import hello.SheetTransaction.SheetTransactionRepository;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionRepository;
import hello.Transaction.TransactionSpecifications;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

                /*
                TODO: need to do all this contidions for all entities
                 */
                if (transaction instanceof SheetTransaction) {
                    transactionAux = new SheetTransaction();
                    ((SheetTransaction) transactionAux).setHoursPerProjectList(new ArrayList<>());
                    ((SheetTransaction) transactionAux).getHoursPerProjectList().addAll(financialProjectionValidated.getHoursPerProjectList());

                    ((SheetTransaction) transactionAux).setEmployee(((SheetTransaction) transaction).getEmployee());


                } else if (transaction instanceof EmployeeTransaction) {
                    transactionAux = new EmployeeTransaction();
                    ((EmployeeTransaction) transactionAux).setEmployee(((EmployeeTransaction) transaction).getEmployee());
                } else
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
}
