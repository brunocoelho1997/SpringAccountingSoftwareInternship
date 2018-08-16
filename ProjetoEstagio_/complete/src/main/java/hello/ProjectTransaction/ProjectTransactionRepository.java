package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import hello.Type.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, Long>, JpaSpecificationExecutor {

    ProjectTransaction findById(long id);
    List<ProjectTransaction> findByProject(Project project);
    Page<ProjectTransaction> findAll(Pageable pageable);

    Page<ProjectTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean executed, boolean actived);
    List<ProjectTransaction> findDistinctByProjectAndGenreAndActivedAndExecuted(Project project, Genre genre, boolean actived, boolean executed);

    Collection<ProjectTransaction> findAllByGenreAndActivedAndDateAfterAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, Boolean executed);
    Collection<ProjectTransaction> findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre genre, boolean actived, LocalDate dateAfter, LocalDate dateBefore, Boolean executed);

    List<ProjectTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed);
}
