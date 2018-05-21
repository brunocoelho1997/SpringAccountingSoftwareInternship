package hello.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor {

    List<Client> findByNameContaining(String name);

    Client findById(long id);

//    Page<Client> findAll(Pageable pageable);

    Page<Client> findAllByActived(Pageable pageable, boolean actived);
    List<Client> findAllByActived(boolean actived);


}

