package hello.EmployeeTransaction;

import hello.Employee.Employee;
import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface EmployeeTransactionRepository extends JpaRepository<EmployeeTransaction, Long>, JpaSpecificationExecutor {

    EmployeeTransaction findById(long id);
    Page<EmployeeTransaction> findAll(Pageable pageable);
    Page<EmployeeTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean executed, boolean actived);

    Collection<EmployeeTransaction> findAllByGenreAndActivedAndDateAfter(Genre genre, boolean actived, LocalDate dateAfter);
    Collection<EmployeeTransaction> findAllByGenreAndActivedAndDateAfterAndDateBefore(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore);

}