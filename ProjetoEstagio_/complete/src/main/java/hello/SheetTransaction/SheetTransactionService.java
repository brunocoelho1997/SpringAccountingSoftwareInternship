package hello.SheetTransaction;

import hello.Employee.Employee;
import hello.Employee.EmployeeService;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.Enums.Category;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SaleTransaction.SaleTransactionSpecifications;
import hello.SheetTransaction.Resources.HoursPerProject;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeRepository;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SheetTransactionService {

    @Autowired
    SheetTransactionRepository repository;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TypeRepository typeRepository;

    public void addTransaction(@Valid SheetTransaction transaction) {

        Employee employee;

        if(transaction.getEmployee() != null )
        {
            employee = employeeService.getEmployee(transaction.getEmployee().getId());
            transaction.setEmployee(employee);
        }

        Type typeAux = new Type(transaction.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : transaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        transaction.setType(typeAux);
        transaction.setExecuted(true);

        if(transaction.getHoursPerProjectList()!=null)
        {
            for(HoursPerProject hoursPerProject : transaction.getHoursPerProjectList()){
                Project project = projectService.getProject(hoursPerProject.getProject().getId());
                hoursPerProject.setProject(project);
            }
        }
        repository.save(transaction);
    }

    public Page<SheetTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null|| deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);

    }

    private Page<SheetTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<SheetTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty()&& deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);



        Employee employee = employeeService.getEmployee(employeeId);

        Specification<SheetTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = SheetTransactionsSpecifications.filter(value, frequency, employee, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(SheetTransactionsSpecifications.filter(value, frequency, employee, dateSince, dateUntil,valueSince, valueUntil, genre));

        }

        //types and subtypes
        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
//            System.out.println("\n\n\n\n subTypeList: " + subTypeList);
        }
        if(subTypeValue!= null && !subTypeValue.isEmpty())
        {
            List<Type> types = typeRepository.findByName(typeValue);

            if(!types.isEmpty())
            {
                for(Type type1: types)
                {
                    if(!type1.isManuallyCreated() && !Collections.disjoint(type1.getSubTypeList(), subTypeList))
                    {
//                        System.out.println("\n\n Type: " + type1);

                        if(specFilter==null)
                            specFilter = SheetTransactionsSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(SheetTransactionsSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = SheetTransactionsSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(SheetTransactionsSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = SheetTransactionsSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(SheetTransactionsSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = SheetTransactionsSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(SheetTransactionsSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public SheetTransaction getTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editTransaction(@Valid SheetTransaction editedTransaction) {
        SheetTransaction transaction = getTransaction((long)editedTransaction.getId());

        transaction.setGenre(editedTransaction.getGenre());

        Type type = transaction.getType();
        type.setName(editedTransaction.getType().getName());
        type.setSubTypeList(editedTransaction.getType().getSubTypeList());

        transaction.setDate(editedTransaction.getDate());
        transaction.setValue(editedTransaction.getValue());
        transaction.setFrequency(editedTransaction.getFrequency());
        transaction.setDescription(editedTransaction.getDescription());
        transaction.setName(editedTransaction.getName());

        Employee employee = employeeService.getEmployee(editedTransaction.getEmployee().getId());
        transaction.setEmployee(employee);

        transaction.setHoursPerProjectList(new ArrayList<>());

        if(editedTransaction.getHoursPerProjectList() != null)
        {
            for(HoursPerProject hoursPerProject : editedTransaction.getHoursPerProjectList()){
                Project project = projectService.getProject(hoursPerProject.getProject().getId());
                hoursPerProject.setProject(project);
                transaction.getHoursPerProjectList().add(hoursPerProject);
            }
        }

        repository.save(transaction);
    }

    public void removeTransaction(Long id) {
        SheetTransaction transaction = getTransaction((long)id);
        transaction.setActived(false);
        repository.save(transaction);
    }

    public void recoveryTransaction(Long id) {
        SheetTransaction transaction = getTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }
}
