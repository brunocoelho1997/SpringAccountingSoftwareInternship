package hello.Transaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor {

    Page<Transaction> findAllByGenreAndExecutedAndActivedOrderByDateDesc(Pageable pageable, Genre genre, boolean executed, boolean actived);

    List<Transaction> findAllByGenreAndExecutedAndActivedAndDateAfterOrderByDateAsc(Genre genre, boolean executed, boolean actived, LocalDate dateAfter);
    Transaction findById(long id);
}
