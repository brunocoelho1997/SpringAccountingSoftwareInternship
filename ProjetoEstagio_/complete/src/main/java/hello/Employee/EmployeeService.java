package hello.Employee;


import hello.Client.Client;
import hello.Person.Person;
import hello.Person.PersonSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class EmployeeService{

    @Autowired
    EmployeeRepository repository;

    public Page<Employee> findAllPageable(Pageable pageable, String value) {


        //could receive params to filter de list
        if(value!= null)
            return filterEmployees(pageable, value);

        else
            return repository.findAllByActived(pageable, true);

    }
    public Page<Employee> filterEmployees(Pageable pageable, String value) {

        Page<Employee> page = null;


        if(value.isEmpty())
            return repository.findAllByActived(pageable, true);

        Specification<Client> specFilter;
        specFilter= PersonSpecifications.filter(value);

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
}
