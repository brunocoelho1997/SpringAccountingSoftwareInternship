package hello.Employee;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor {


    Employee findById(long id);

    Page<Employee> findAllByActived(Pageable pageable, boolean actived);
    List<Employee> findAllByActived(boolean actived);
}
