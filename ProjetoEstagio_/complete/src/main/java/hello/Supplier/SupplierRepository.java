package hello.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor {

    Supplier findById(long id);

    Page<Supplier> findAllByActived(Pageable pageable, boolean actived);
    List<Supplier> findAllByActived(boolean actived);
}
