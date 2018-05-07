package hello.Project;

import hello.Client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor {

    Project findById(long id);

//    List<Project> findByNameContaining(String name);


//    Page<Project> findByName(String name, Pageable pageable);

    List<Project> findAll();

    Page<Project> findAll(Pageable pageable);











//    Page<Project> findByNameContaining(String name, Pageable pageable);

//    List<Project>findByFinalDateLessThanEqual(LocalDate finalDate);
//    List<Project>findByInitialDateGreaterThanEqual(LocalDate initialDate);
//
//    List<Project>findByClient(Client client);

//    Page<Project>findByClient(Client client, Pageable pageable);
//    Page<Project>findByClientAndFinalDateLessThanEqual(Client client, LocalDate finalDate, Pageable pageable);
//    Page<Project>findByClientAndFinalDateLessThanEqualAndInitialDateGreaterThanEqual(Client client, LocalDate finalDate, LocalDate initialDate, Pageable pageable);
//    Page<Project>findByClientAndFinalDateLessThanEqualAndInitialDateGreaterThanEqualAndNameContaining(Client client, LocalDate finalDate, LocalDate initialDate, String name, Pageable pageable);

}
