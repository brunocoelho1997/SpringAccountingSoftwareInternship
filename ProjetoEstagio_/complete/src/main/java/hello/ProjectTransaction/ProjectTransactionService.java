package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProjectTransactionService {

    @Autowired
    ProjectTransactionRepository repository;

    @Autowired
    ProjectService projectService;


    public List<ProjectTransaction> getProjectsTransactionsByGenre(Genre genre){
        List<ProjectTransaction> projectTransactions = repository.findByGenre(genre);
        return projectTransactions;
    }

    public void addTransaction(@Valid ProjectTransaction projectTransaction) {

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);

        System.out.println("\n\n\n\n\n\n\n\n " + projectTransaction + "\n\n\n");

        repository.save(projectTransaction);
    }
}
