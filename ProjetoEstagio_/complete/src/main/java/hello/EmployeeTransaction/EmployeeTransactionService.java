package hello.EmployeeTransaction;

import hello.Employee.Employee;
import hello.Employee.EmployeeService;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.ProjectTransaction.ProjectTransaction;
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
public class EmployeeTransactionService {

    @Autowired
    EmployeeTransactionRepository repository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;


    public void addTransaction(@Valid EmployeeTransaction employeeTransaction) {
        Employee employee = employeeService.getEmployee(employeeTransaction.getEmployee().getId());
        employeeTransaction.setEmployee(employee);


        Type typeAux = new Type(employeeTransaction.getType().getName());
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : employeeTransaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        employeeTransaction.setType(typeAux);
        employeeTransaction.setExecuted(true);

        repository.save(employeeTransaction);
    }

    public Page<EmployeeTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {


        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);
    }

    private Page<EmployeeTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<EmployeeTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);


        Employee employee = employeeService.getEmployee(employeeId);



        Specification<EmployeeTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = EmployeeTransactionSpecifications.filter(value, frequency, employee, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(EmployeeTransactionSpecifications.filter(value, frequency, employee, dateSince, dateUntil,valueSince, valueUntil, genre));

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
                            specFilter = EmployeeTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(EmployeeTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = EmployeeTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(EmployeeTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = EmployeeTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(EmployeeTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = EmployeeTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(EmployeeTransactionSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public EmployeeTransaction getEmployeeTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editEmployeeTransaction(@Valid EmployeeTransaction editedEmployeeTransaction) {
        EmployeeTransaction employeeTransaction = getEmployeeTransaction((long)editedEmployeeTransaction.getId());

        employeeTransaction.setGenre(editedEmployeeTransaction.getGenre());

        Type type = employeeTransaction.getType();
        type.setName(editedEmployeeTransaction.getType().getName());
        type.setSubTypeList(editedEmployeeTransaction.getType().getSubTypeList());

        employeeTransaction.setDate(editedEmployeeTransaction.getDate());
        employeeTransaction.setValue(editedEmployeeTransaction.getValue());
        employeeTransaction.setFrequency(editedEmployeeTransaction.getFrequency());
        employeeTransaction.setDescription(editedEmployeeTransaction.getDescription());
        employeeTransaction.setName(editedEmployeeTransaction.getName());

        Employee employee = employeeService.getEmployee(editedEmployeeTransaction.getEmployee().getId());
        employeeTransaction.setEmployee(employee);

        repository.save(employeeTransaction);
    }

    public void removeEmployeeTransaction(Long id) {
        EmployeeTransaction employeeTransaction = getEmployeeTransaction((long)id);
        employeeTransaction.setActived(false);
        repository.save(employeeTransaction);
    }
    public void recoveryTransaction(Long id) {
        EmployeeTransaction transaction = getEmployeeTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }
}
