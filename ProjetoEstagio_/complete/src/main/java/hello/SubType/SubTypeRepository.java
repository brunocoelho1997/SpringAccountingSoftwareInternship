package hello.SubType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SubTypeRepository extends JpaRepository<SubType, Long>, JpaSpecificationExecutor {


    SubType findById(long id);
    List<SubType> findAllByActived(boolean actived);
    List<SubType>findByName(String name);

    List<SubType>findByTypeName(String typeName);

    Page<SubType> findAllByActived(Pageable pageable, boolean actived);



}
