package hello.ComissionTransaction;


import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ComissionTransactionRepository extends JpaRepository<ComissionTransaction, Long>, JpaSpecificationExecutor {

    ComissionTransaction findById(long id);
    Page<ComissionTransaction> findAll(Pageable pageable);
    Page<ComissionTransaction> findAllByGenreAndExecutedAndActivedOrderByDateDesc(Pageable pageable, Genre genre, boolean executed, boolean actived);

    Collection<ComissionTransaction> findAllByGenreAndActivedAndDateAfterAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, boolean executed);
    Collection<ComissionTransaction> findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore,boolean executed);
    List<ComissionTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed);


}