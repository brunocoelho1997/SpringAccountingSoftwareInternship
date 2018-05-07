package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, Long>, JpaSpecificationExecutor {

    List<ProjectTransaction> findByGenre(Genre genre);

    ProjectTransaction findById(long id);

    List<ProjectTransaction> findByProject(Project project);

    Page<ProjectTransaction> findAll(Pageable pageable);
    Page<ProjectTransaction> findByGenre(Pageable pageable, Genre genre);

}
