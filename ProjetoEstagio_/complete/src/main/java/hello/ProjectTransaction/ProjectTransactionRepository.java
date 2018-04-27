package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, Long> {

    List<ProjectTransaction> findByGenre(Genre genre);

    ProjectTransaction findById(long id);

    List<ProjectTransaction> findByProject(Project project);


}
