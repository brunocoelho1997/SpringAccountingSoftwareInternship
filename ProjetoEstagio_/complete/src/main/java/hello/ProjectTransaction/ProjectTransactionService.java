package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProjectTransactionService {

    @Autowired
    ProjectTransactionRepository repository;

    @Autowired
    ProjectService projectService;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;


//    public List<ProjectTransaction> getProjectsTransactionsByGenre(Genre genre){
//        List<ProjectTransaction> projectTransactions = repository.findByGenre(genre);
//        return projectTransactions;
//    }

    public void addTransaction(@Valid ProjectTransaction projectTransaction) {

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);

//        if(projectTransaction.getSubType().getId()==0)
//            projectTransaction.setSubType(null);
        repository.save(projectTransaction);
    }

    public ProjectTransaction getProjectTransaction(long id)
    {
        return repository.findById(id);
    }

    public void removeProjectTransaction(Long id) {
        ProjectTransaction projectTransaction = getProjectTransaction((long)id);
        projectTransaction.setActived(false);
        repository.save(projectTransaction);
    }

    public void editProjectTransaction(@Valid ProjectTransaction editedProjectTransaction) {
        ProjectTransaction projectTransaction = getProjectTransaction((long)editedProjectTransaction.getId());

        projectTransaction.setGenre(editedProjectTransaction.getGenre());

        Type type = typeService.getType(editedProjectTransaction.getType().getId());
        projectTransaction.setType(type);
//        if(editedProjectTransaction.getType().getSubType() !=null)
//        {
//            SubType subType= subTypeService.getSubType(editedProjectTransaction.getType().getSubType().getId());
//            projectTransaction.getType().setSubType(subType);
//        }
        projectTransaction.setDate(editedProjectTransaction.getDate());
        projectTransaction.setValue(editedProjectTransaction.getValue());
        projectTransaction.setFrequency(editedProjectTransaction.getFrequency());
        projectTransaction.setDescription(editedProjectTransaction.getDescription());
        projectTransaction.setName(editedProjectTransaction.getName());

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);


        repository.save(projectTransaction);
    }

    public Page<ProjectTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || projectId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, projectId, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return repository.findAllByGenreAndActived(pageable, genre,true);

    }

    public List<ProjectTransaction> findAllByGenreAndActived(Genre genre, boolean actived)
    {
        return repository.findAllByGenreAndActived(genre, actived);
    }

    private Page<ProjectTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<ProjectTransaction> projectTransactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && projectId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenreAndActived(pageable, genre, true);

        Project project = projectService.getProject(projectId);
        Specification<ProjectTransaction> specFilter = ProjectTransactionSpecifications.filter(value, frequency, typeValue, subTypeValue, project, dateSince, dateUntil,valueSince, valueUntil, genre);

        projectTransactionsPage = repository.findAll(specFilter, pageable);

        return projectTransactionsPage;
    }

}
