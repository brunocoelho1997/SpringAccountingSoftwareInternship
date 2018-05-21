package hello.ProjectTransaction;

import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        repository.delete(projectTransaction);
    }

    public void editProjectTransaction(@Valid ProjectTransaction editedProjectTransaction) {
        ProjectTransaction projectTransaction = getProjectTransaction((long)editedProjectTransaction.getId());

        projectTransaction.setGenre(editedProjectTransaction.getGenre());

        Type type = typeService.getType(editedProjectTransaction.getType().getId());
        projectTransaction.setType(type);
        if(editedProjectTransaction.getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedProjectTransaction.getSubType().getId());
            projectTransaction.setSubType(subType);
        }
        projectTransaction.setDate(editedProjectTransaction.getDate());
        projectTransaction.setValue(editedProjectTransaction.getValue());
        projectTransaction.setFrequency(editedProjectTransaction.getFrequency());
        projectTransaction.setDescription(editedProjectTransaction.getDescription());
        projectTransaction.setName(editedProjectTransaction.getName());

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);


        repository.save(projectTransaction);
    }

    public Page<ProjectTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeId != null || subTypeId != null|| projectId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeId, subTypeId, projectId, dateSince, dateUntil, valueSince, valueUntil, genre);

        else
            return repository.findAllByGenre(pageable, genre);

    }

    private Page<ProjectTransaction> filterTransactions(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<ProjectTransaction> projectTransactionsPage = null;


        if(value.isEmpty() && frequency.isEmpty() && typeId == 0 && projectId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenre(pageable, genre);



        Specification<ProjectTransaction> specFilter;

        Type type = typeService.getType(typeId);

        SubType subType = null;
        if(subTypeId!=null)
            subType= subTypeService.getSubType(subTypeId);

        Project project = projectService.getProject(projectId);

        specFilter= ProjectTransactionSpecifications.filter(value, frequency, type, subType, project, dateSince, dateUntil,valueSince, valueUntil, genre);

        projectTransactionsPage = repository.findAll(specFilter, pageable);

        return projectTransactionsPage;
    }

}
