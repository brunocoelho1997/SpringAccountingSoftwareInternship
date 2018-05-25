package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import hello.Type.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, Long>, JpaSpecificationExecutor {

    ProjectTransaction findById(long id);
    List<ProjectTransaction> findByProject(Project project);
    Page<ProjectTransaction> findAll(Pageable pageable);
    Page<ProjectTransaction> findAllByGenreAndActived(Pageable pageable, Genre genre, boolean actived);

    List<ProjectTransaction> findDistinctByProjectAndGenreAndActived(Project project, Genre genre, boolean actived);

}
