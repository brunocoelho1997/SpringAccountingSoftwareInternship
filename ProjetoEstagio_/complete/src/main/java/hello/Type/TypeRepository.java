package hello.Type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long>{

    Type findById(long id);
}
