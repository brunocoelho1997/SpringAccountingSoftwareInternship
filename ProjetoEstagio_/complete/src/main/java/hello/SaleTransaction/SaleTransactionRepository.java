package hello.SaleTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long>, JpaSpecificationExecutor {

    SaleTransaction findById(long id);
    Page<SaleTransaction> findAll(Pageable pageable);
    Page<SaleTransaction> findAllByGenreAndActived(Pageable pageable, Genre genre, boolean actived);

    List<SaleTransaction> findDistinctByGenreAndActived(Genre genre, boolean actived);

}