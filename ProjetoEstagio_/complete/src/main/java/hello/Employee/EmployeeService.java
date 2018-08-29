package hello.Employee;

import hello.Supplier.SupplierSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService{

    @Autowired
    EmployeeRepository repository;

    public Page<Employee> findAllPageable(Pageable pageable, String value, Boolean deletedEntities) {


        //could receive params to filter de list
        if(value!= null || deletedEntities != null)
            return filterEmployees(pageable, value, deletedEntities);

        else
            return repository.findAllByActived(pageable, true);

    }
    public Page<Employee> filterEmployees(Pageable pageable, String value, Boolean deletedEntities) {

        Page<Employee> page = null;


        if(value.isEmpty() && deletedEntities==null)
            return repository.findAllByActived(pageable, true);

        Specification<Employee> specFilter = null;

        if(!value.isEmpty())
            specFilter= EmployeeSpecifications.filter(value);

        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = EmployeeSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(EmployeeSpecifications.filterDeleletedEntities(deletedEntities));


        page = repository.findAll(specFilter, pageable);

        return page;
    }


    public void addEmployee(Employee employee) {
        repository.save(employee);
    }

    public Employee getEmployee(Long id) {
        return repository.findById((long)id);
    }

    public void editEmployee(Employee editedEmployee){

        Employee employee = getEmployee(editedEmployee.getId());

        employee.setActived(editedEmployee.isActived());
        employee.setPostEmployee(editedEmployee.getPostEmployee());
        employee.setAdresses(editedEmployee.getAdresses());
        employee.setName(editedEmployee.getName());
        employee.setEmail(editedEmployee.getEmail());
        employee.setNumberPhone(editedEmployee.getNumberPhone());

        repository.save(employee);
    }

    public void removeEmployee(Long id) {
        Employee employee = getEmployee(id);
        employee.setActived(false);
        repository.save(employee);
    }

    public List<Employee> getEmployees() {
        return repository.findAll();
    }
    public List<Employee> getActivedEmployees() {
        return repository.findAllByActived(true);
    }

    public void recoveryEntity(Long id) {
        Employee entity = getEmployee(id);
        entity.setActived(true);
        repository.save(entity);
    }
}
