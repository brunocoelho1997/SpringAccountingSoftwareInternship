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
}
