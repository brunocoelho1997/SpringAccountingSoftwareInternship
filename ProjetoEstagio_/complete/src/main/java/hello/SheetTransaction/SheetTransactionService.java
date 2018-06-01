package hello.SheetTransaction;

import hello.Employee.Employee;
import hello.Employee.EmployeeService;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SheetTransaction.Resources.HoursPerProject;
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
import java.util.ArrayList;

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

    public void addTransaction(@Valid SheetTransaction transaction) {

        Employee employee;

        if(transaction.getEmployee() != null )
        {
            employee = employeeService.getEmployee(transaction.getEmployee().getId());
            transaction.setEmployee(employee);
        }

        if(transaction.getHoursPerProjectList()!=null)
        {
            for(HoursPerProject hoursPerProject : transaction.getHoursPerProjectList()){
                Project project = projectService.getProject(hoursPerProject.getProject().getId());
                hoursPerProject.setProject(project);
            }
        }
        repository.save(transaction);
    }

    public Page<SheetTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeId != null || subTypeId != null|| employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeId, subTypeId, employeeId, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return repository.findAllByGenre(pageable, genre);

    }

    private Page<SheetTransaction> filterTransactions(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<SheetTransaction> projectTransactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeId == 0 && employeeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenre(pageable, genre);

        Specification<SheetTransaction> specFilter;

        Type type = typeService.getType(typeId);

        SubType subType = null;
        if(subTypeId!=null)
            subType= subTypeService.getSubType(subTypeId);

        Employee employee = employeeService.getEmployee(employeeId);



        specFilter= SheetTransactionsSpecifications.filter(value, frequency, type, subType, employee, dateSince, dateUntil,valueSince, valueUntil, genre);

        projectTransactionsPage = repository.findAll(specFilter, pageable);

        return projectTransactionsPage;
    }

    public SheetTransaction getTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editTransaction(@Valid SheetTransaction editedTransaction) {
        SheetTransaction transaction = getTransaction((long)editedTransaction.getId());

        transaction.setGenre(editedTransaction.getGenre());

        Type type = typeService.getType(editedTransaction.getType().getId());
        transaction.setType(type);
        if(editedTransaction.getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedTransaction.getSubType().getId());
            transaction.setSubType(subType);
        }
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
        repository.delete(transaction);
    }
}
