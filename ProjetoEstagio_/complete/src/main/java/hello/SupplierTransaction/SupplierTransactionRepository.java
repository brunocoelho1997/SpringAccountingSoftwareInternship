package hello.SupplierTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierTransactionRepository extends JpaRepository<SupplierTransaction, Long>, JpaSpecificationExecutor {

    SupplierTransaction findById(long id);
    Page<SupplierTransaction> findAll(Pageable pageable);
    Page<SupplierTransaction> findAllByGenre(Pageable pageable, Genre genre);

}
