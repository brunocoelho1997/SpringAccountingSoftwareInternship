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
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class EmployeeTransactionService {

    @Autowired
    EmployeeTransactionRepository repository;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;


    public void addTransaction(@Valid EmployeeTransaction employeeTransaction) {
        Employee employee = employeeService.getEmployee(employeeTransaction.getEmployee().getId());
        employeeTransaction.setEmployee(employee);
        repository.save(employeeTransaction);
    }

    public Page<EmployeeTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {
        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return repository.findAllByGenre(pageable, genre);

    }

    private Page<EmployeeTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeString, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<EmployeeTransaction> projectTransactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeString.isEmpty() && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenre(pageable, genre);


        Specification<EmployeeTransaction> specFilter;

        Employee employee = employeeService.getEmployee(employeeId);

        specFilter= EmployeeTransactionSpecifications.filter(value, frequency, typeString, subTypeValue, employee, dateSince, dateUntil,valueSince, valueUntil, genre);

        projectTransactionsPage = repository.findAll(specFilter, pageable);

        return projectTransactionsPage;
    }

    public EmployeeTransaction getEmployeeTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editEmployeeTransaction(@Valid EmployeeTransaction editedEmployeeTransaction) {
        EmployeeTransaction employeeTransaction = getEmployeeTransaction((long)editedEmployeeTransaction.getId());

        employeeTransaction.setGenre(editedEmployeeTransaction.getGenre());

        Type type = typeService.getType(editedEmployeeTransaction.getType().getId());
        employeeTransaction.setType(type);
        if(editedEmployeeTransaction.getType().getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedEmployeeTransaction.getType().getSubType().getId());
            employeeTransaction.getType().setSubType(subType);
        }
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
        repository.delete(employeeTransaction);
    }
}
