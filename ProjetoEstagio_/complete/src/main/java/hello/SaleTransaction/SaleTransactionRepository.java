package hello.SaleTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long>, JpaSpecificationExecutor {

    SaleTransaction findById(long id);
    Page<SaleTransaction> findAll(Pageable pageable);

    Page<SaleTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean actived);
    Page<SaleTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean executed, boolean actived);

    Collection<SaleTransaction> findAllByGenreAndActivedAndDateAfter(Genre genre, boolean actived, LocalDate dateAfter);
    Collection<SaleTransaction> findAllByGenreAndActivedAndDateAfterAndDateBefore(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore);

    List<SaleTransaction> findAllByGenreAndActived(Genre genre, boolean actived);
}