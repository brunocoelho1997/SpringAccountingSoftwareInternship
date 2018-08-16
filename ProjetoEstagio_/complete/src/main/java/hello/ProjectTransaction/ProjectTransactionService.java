package hello.ProjectTransaction;

import hello.EmployeeTransaction.EmployeeTransaction;
import hello.EmployeeTransaction.EmployeeTransactionSpecifications;
import hello.Enums.Category;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeRepository;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    TypeRepository typeRepository;

//    public List<ProjectTransaction> getProjectsTransactionsByGenre(Genre genre){
//        List<ProjectTransaction> projectTransactions = repository.findByGenre(genre);
//        return projectTransactions;
//    }

    public void addTransaction(@Valid ProjectTransaction transaction) {

        Project project = projectService.getProject(transaction.getProject().getId());
        transaction.setProject(project);

        Type typeAux = new Type(transaction.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : transaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        transaction.setType(typeAux);
        transaction.setExecuted(true);

        repository.save(transaction);
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

        Type type = projectTransaction.getType();
        type.setName(editedProjectTransaction.getType().getName());
        type.setSubTypeList(editedProjectTransaction.getType().getSubTypeList());

        projectTransaction.setDate(editedProjectTransaction.getDate());
        projectTransaction.setValue(editedProjectTransaction.getValue());
        projectTransaction.setFrequency(editedProjectTransaction.getFrequency());
        projectTransaction.setDescription(editedProjectTransaction.getDescription());
        projectTransaction.setName(editedProjectTransaction.getName());

        Project project = projectService.getProject(projectTransaction.getProject().getId());
        projectTransaction.setProject(project);


        repository.save(projectTransaction);
    }

    public Page<ProjectTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || projectId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, projectId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);

    }

    public List<ProjectTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed)
    {
        return repository.findAllByGenreAndActivedAndExecuted(genre, actived,executed);
    }

    private Page<ProjectTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<ProjectTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && projectId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);

        Project project = projectService.getProject(projectId);
//        Specification<ProjectTransaction> specFilter = ProjectTransactionSpecifications.filter(value, frequency, typeValue, subTypeValue, project, dateSince, dateUntil,valueSince, valueUntil, genre);

        Specification<ProjectTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && projectId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = ProjectTransactionSpecifications.filter(value, frequency, project, dateSince, dateUntil,valueSince, valueUntil);
            else
                specFilter.and(ProjectTransactionSpecifications.filter(value, frequency, project, dateSince, dateUntil,valueSince, valueUntil));

        }

        //types and subtypes
        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
//            System.out.println("\n\n\n\n subTypeList: " + subTypeList);
        }
        if(subTypeValue!= null && !subTypeValue.isEmpty())
        {
            List<Type> types = typeRepository.findByName(typeValue);

            if(!types.isEmpty())
            {
                for(Type type1: types)
                {
                    if(!type1.isManuallyCreated() && !Collections.disjoint(type1.getSubTypeList(), subTypeList))
                    {
//                        System.out.println("\n\n Type: " + type1);

                        if(specFilter==null)
                            specFilter = ProjectTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(ProjectTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = ProjectTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(ProjectTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = ProjectTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(ProjectTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = ProjectTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(ProjectTransactionSpecifications.filterExecuted(true));


        //genre
        if(specFilter==null)
            specFilter = ProjectTransactionSpecifications.filterGenre(genre);
        else
            specFilter = specFilter.and(ProjectTransactionSpecifications.filterGenre(genre));
        transactionsPage = repository.findAll(specFilter, pageable);


        return transactionsPage;
    }

    public void recoveryTransaction(Long id) {
        ProjectTransaction projectTransaction = getProjectTransaction((long)id);
        projectTransaction.setActived(true);
        repository.save(projectTransaction);
    }
}
