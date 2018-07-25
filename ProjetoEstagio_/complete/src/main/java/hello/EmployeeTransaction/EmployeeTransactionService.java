package hello.EmployeeTransaction;

import hello.Employee.Employee;
import hello.Employee.EmployeeService;
import hello.Enums.Category;
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


        System.out.println("\n\n\n\n\n antes:" + employeeTransaction);

        Type typeAux = new Type(employeeTransaction.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : employeeTransaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        employeeTransaction.setType(typeAux);
        employeeTransaction.setExecuted(true);
        System.out.println("\n\n\n\n\n depois" + employeeTransaction);

        repository.save(employeeTransaction);
    }

    public Page<EmployeeTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {


        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);
    }

    private Page<EmployeeTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<EmployeeTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);

        deletedEntities = (deletedEntities == null ? false : true);

        Employee employee = employeeService.getEmployee(employeeId);

        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
        }

        Specification<EmployeeTransaction> specFilter= EmployeeTransactionSpecifications.filter(value, frequency, typeValue, subTypeList, employee, dateSince, dateUntil,valueSince, valueUntil, deletedEntities, genre);

        transactionsPage = repository.findAll(specFilter, pageable);

//        for(EmployeeTransaction employeeTransaction : filtredList){
//            if(!employeeTransaction.getType().getSubTypeList().contains(subTypeList))
//                filtredList.remove(employeeTransaction);
//        }


        return transactionsPage;
    }

    public EmployeeTransaction getEmployeeTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editEmployeeTransaction(@Valid EmployeeTransaction editedEmployeeTransaction) {
        EmployeeTransaction employeeTransaction = getEmployeeTransaction((long)editedEmployeeTransaction.getId());

        employeeTransaction.setGenre(editedEmployeeTransaction.getGenre());

        Type type = typeRepository.findById((long)editedEmployeeTransaction.getType().getId());
        employeeTransaction.setType(type);
//        if(editedEmployeeTransaction.getType().getSubType() !=null)
//        {
//            SubType subType= subTypeService.getSubType(editedEmployeeTransaction.getType().getSubType().getId());
//            employeeTransaction.getType().setSubType(subType);
//        }
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
        EmployeeTransaction employeeTransaction = getEmployeeTransaction((long)id);
        employeeTransaction.setActived(true);
        repository.save(employeeTransaction);
    }
}
