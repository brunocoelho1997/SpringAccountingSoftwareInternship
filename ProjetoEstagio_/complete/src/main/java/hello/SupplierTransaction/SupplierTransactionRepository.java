package hello.SupplierTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface SupplierTransactionRepository extends JpaRepository<SupplierTransaction, Long>, JpaSpecificationExecutor {

    SupplierTransaction findById(long id);
    Page<SupplierTransaction> findAll(Pageable pageable);
    Page<SupplierTransaction> findAllByGenre(Pageable pageable, Genre genre);

    Page<SupplierTransaction> findAllByGenreAndExecutedAndActivedOrderByDateDesc(Pageable pageable, Genre genre, boolean executed, boolean actived);

    Collection<SupplierTransaction> findAllByGenreAndActivedAndDateAfterAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, Boolean executed);
    Collection<SupplierTransaction> findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore, Boolean executed);
    List<SupplierTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed);

}
