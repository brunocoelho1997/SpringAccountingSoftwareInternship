package hello.Employee;


import hello.Person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class EmployeeService{

    @Autowired
    EmployeeRepository repository;

    public List<Employee> getEmployees(){
        return repository.findAll();
    }

    public void addEmployee(Employee employee) {
        employee.setActived(true);
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
        repository.delete(employee);
    }
}
