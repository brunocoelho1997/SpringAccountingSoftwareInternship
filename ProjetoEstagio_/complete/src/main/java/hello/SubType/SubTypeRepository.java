package hello.SubType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubTypeRepository extends JpaRepository<SubType, Long>, JpaSpecificationExecutor {


    SubType findById(long id);
}
