package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

        repository.save(projectTransaction);
    }

    //method private! If you need a project use method getProject(long id);
    private ProjectTransaction getOne(Long id) {

        try
        {
            if(id == null)
                throw new EntityNotFoundException();

            ProjectTransaction projectTransaction = repository.getOne(id);

            return projectTransaction;
        }catch (EntityNotFoundException ex)
        {
            return null;
        }
    }

    public ProjectTransaction getProjectTransaction(Long id) {
        return getOne(id);
    }

    public void removeProjectTransaction(Long id) {
        ProjectTransaction projectTransaction = getOne(id);
        repository.delete(projectTransaction);
    }

    public void editProjectTransaction(@Valid ProjectTransaction editedProjectTransaction) {
        ProjectTransaction projectTransaction = getOne(editedProjectTransaction.getId());

        projectTransaction.setGenre(editedProjectTransaction.getGenre());
        projectTransaction.setTransactionType(editedProjectTransaction.getTransactionType());
        projectTransaction.setDate(editedProjectTransaction.getDate());
        projectTransaction.setValue(editedProjectTransaction.getValue());
        projectTransaction.setFrequency(editedProjectTransaction.getFrequency());
        projectTransaction.setDescription(editedProjectTransaction.getDescription());
        projectTransaction.setName(editedProjectTransaction.getName());

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);


        repository.save(projectTransaction);
    }
}
