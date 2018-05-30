package hello.SheetTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SheetTransactionRepository extends JpaRepository<SheetTransaction, Long>, JpaSpecificationExecutor {

    SheetTransaction findById(long id);
    Page<SheetTransaction> findAll(Pageable pageable);
    Page<SheetTransaction> findAllByGenre(Pageable pageable, Genre genre);

}
