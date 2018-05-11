package hello.Project;

import hello.Client.Client;
import hello.Type.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor {

    Project findById(long id);

    List<Project> findAll();

    Page<Project> findAll(Pageable pageable);
}
