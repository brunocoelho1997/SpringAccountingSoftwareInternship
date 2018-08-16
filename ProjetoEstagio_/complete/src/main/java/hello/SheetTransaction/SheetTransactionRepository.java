package hello.SheetTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface SheetTransactionRepository extends JpaRepository<SheetTransaction, Long>, JpaSpecificationExecutor {

    SheetTransaction findById(long id);
    Page<SheetTransaction> findAll(Pageable pageable);
    Page<SheetTransaction> findAllByGenre(Pageable pageable, Genre genre);

    Page<SheetTransaction> findAllByGenreAndExecutedAndActivedOrderByDateDesc(Pageable pageable, Genre genre, boolean executed, boolean actived);

    List<SheetTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed);

    Collection<SheetTransaction> findAllByGenreAndActivedAndDateAfterAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, Boolean executed);
    Collection<SheetTransaction> findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore, Boolean executed);
}
