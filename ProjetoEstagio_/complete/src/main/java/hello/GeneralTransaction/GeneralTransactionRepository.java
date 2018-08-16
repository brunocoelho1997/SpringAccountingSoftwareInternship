package hello.GeneralTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface GeneralTransactionRepository extends JpaRepository<GeneralTransaction, Long>, JpaSpecificationExecutor {

    GeneralTransaction findById(long id);
    Page<GeneralTransaction> findAll(Pageable pageable);


    Collection<GeneralTransaction> findAllByGenreAndActivedAndDateAfterAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, Boolean executed);
    Collection<GeneralTransaction> findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore, Boolean executed);
    List<GeneralTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed);

    Page<GeneralTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean executed, boolean actived);

}
