package hello.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long>, JpaSpecificationExecutor {

    Type findById(long id);

    List<Type>findByName(String name);
    Page<Type> findAllByActived(Pageable pageable, boolean actived);
    List<Type> findAllByActived(boolean actived);

}
