package hello.Project;

import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Client.ClientService;
import hello.Contact.Contact;
import hello.CostCenter.CostCenter;
import hello.CostCenter.CostCenterService;
import hello.Enums.Genre;
import hello.Project.Resources.ChartResource;
import hello.Project.Resources.TypeSubtypeResource;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionRepository;
import hello.ProjectTransaction.ProjectTransactionSpecifications;
import hello.SubType.SubType;
import hello.Type.Type;
import hello.Type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CostCenterService costCenterService;

    @Autowired
    private ProjectTransactionRepository projectTransactionRepository;

    @Autowired
    private TypeRepository typeRepository;


    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsActived() {
        return projectRepository.findAllByActived(true);
    }


    public Page<Project> findAllPageable(Pageable pageable, String value, String dateSince, String dateUntil, Long clientId, Boolean deletedEntities) {


        //could receive params to filter de list
        if(value!= null || dateSince !=null || dateUntil != null || clientId != null|| deletedEntities != null)
        {
            return filterProjects(pageable, value, dateSince, dateUntil, clientId, deletedEntities);
        }

        else
            return projectRepository.findAllByActived(pageable, true);

    }

    public Project getProject(Long id) {
        return projectRepository.findById((long)id);
    }

    public void addProject(Project project){


        Client c = clientService.getClient(project.getClient().getId());
        Contact contact = clientService.getContact(c.getId(), project.getContact().getId());
        project.setClient(c);
        project.setContact(contact);

        projectRepository.save(project);
    }

    public void removeProject(Long id) {
        Project project = getProject(id);
        project.setActived(false);
        projectRepository.save(project);
    }

    public void editProject(@Valid Project editedProject) {

        Project project = getProject(editedProject.getId());
        project.setScope(editedProject.getScope());
        project.setFinalDate(editedProject.getFinalDate());
        project.setInitialDate(editedProject.getInitialDate());
        project.setName(editedProject.getName());
        project.setDescription(editedProject.getDescription());

        Client c = clientService.getClient(editedProject.getClient().getId());
        Contact contact = clientService.getContact(c.getId(), editedProject.getContact().getId());
        CostCenter costCenter = costCenterService.getCostCenter(editedProject.getCostCenter().getId());

        if(c !=null)
            project.setClient(c);

        if(contact != null)
            project.setContact(contact);

        if(costCenter !=null)
            project.setCostCenter(costCenter);

        projectRepository.save(project);

    }

    public ChartResource getStatistic(long id) {

        ChartResource statistic = new ChartResource();
        Project project = getProject(id);
        List<ProjectTransaction> projectTransactionList = projectTransactionRepository.findByProject(project);
        float total=0, totalCosts=0, totalRevenues=0;

        for(ProjectTransaction projectTransaction : projectTransactionList)
        {
            if(projectTransaction.getGenre().equals(Genre.REVENUE))
                totalRevenues+=projectTransaction.getValue();
            else
                totalCosts+=projectTransaction.getValue();
            total+=projectTransaction.getValue();
        }

        statistic.setPercentageRevenues((int)((totalRevenues*100)/total));
        statistic.setPercentageCosts((int)((totalCosts*100)/total));
        statistic.setTotal(total);

        statistic.setTotalCosts(totalCosts);
        double x = totalRevenues;
        //round
        double valueRounded = Math.round(x * 100D) / 100D;
        statistic.setTotalRevenues((float)valueRounded);

        List<TypeSubtypeResource> typeSubtypeResources = new ArrayList<>();

        List<ProjectTransaction> list = projectTransactionRepository.findDistinctByProjectAndGenreAndActivedAndExecuted(project, Genre.COST, true,true);
        getValues(list, typeSubtypeResources);
        statistic.setTypeSubtypeResourcesCosts(typeSubtypeResources);


        list = projectTransactionRepository.findDistinctByProjectAndGenreAndActivedAndExecuted(project, Genre.REVENUE, true,true);
        typeSubtypeResources = new ArrayList<>();
        getValues(list, typeSubtypeResources);
        statistic.setTypeSubtypeResourcesRevenues(typeSubtypeResources);

        return statistic;
    }

    private void getValues(List<ProjectTransaction> list, List<TypeSubtypeResource> typeSubtypeResources) {
        Map<String, Float> mapTypeValues = new HashMap<>();
        //just to help... to validate if type exist in typeSubtypeResources or not
        //or to get the TypeSubtypeResource because this index is the index of the type in typeSubtypeResources
        List<String> nameTypesProcessed = new ArrayList<>();



        for(ProjectTransaction projectTransaction : list){
            if(!nameTypesProcessed.contains(projectTransaction.getType().getName()))
            {
                TypeSubtypeResource resource = new TypeSubtypeResource();
                resource.setTypeName(projectTransaction.getType().getName());
                Float newValue = (float)(Math.round(projectTransaction.getValue() * 100D) / 100D);
                resource.setTypeValueTotal(newValue);

                processSubType(resource, projectTransaction);

                typeSubtypeResources.add(resource);
                //indicate this type exist
                nameTypesProcessed.add(projectTransaction.getType().getName());
            }
            else
            {
                int index = nameTypesProcessed.indexOf(projectTransaction.getType().getName());
                TypeSubtypeResource resource = typeSubtypeResources.get(index);
                Float newValue = (float)(Math.round((resource.getTypeValueTotal() + projectTransaction.getValue()) * 100D) / 100D);
                resource.setTypeValueTotal(newValue);
                processSubType(resource, projectTransaction);
            }
        }

    }

    private void processSubType(TypeSubtypeResource resource, ProjectTransaction projectTransaction) {

        if(resource.getSubTypeNames() == null)
        {
            resource.setSubTypeNames(new ArrayList<>());
            resource.setSubTypeValues(new ArrayList<>());
        }

        if(projectTransaction.getType().getSubTypeList().isEmpty())
        {
            //and do not exist in list subtypeNames the subtype "NoSubTypeDefined"
            if(!resource.getSubTypeNames().contains("NoSubTypeDefined") ){
                resource.getSubTypeNames().add("NoSubTypeDefined");
                resource.getSubTypeValues().add(projectTransaction.getValue());
            }
            else
            {
                int index = resource.getSubTypeNames().indexOf("NoSubTypeDefined");
                Float newValue = resource.getSubTypeValues().get(index) + projectTransaction.getValue();
                resource.getSubTypeValues().set(index, newValue);
            }

        }
        else
        {
            String name = "";

            if(projectTransaction.getType().getSubTypeList().size() == 1) //if just has one subtype...
                name = projectTransaction.getType().getSubTypeList().get(0).getName();
            else
            {
                SubType subType;
                for(int i = 0; i<projectTransaction.getType().getSubTypeList().size(); i++)
                {
                    subType = projectTransaction.getType().getSubTypeList().get(i);
                    if(i==projectTransaction.getType().getSubTypeList().size()-1)
                        name += " " + subType.getName() + "";
                    else
                        name += " " + subType.getName() + ";";
                }
            }

            if(!resource.getSubTypeNames().contains(name)){
                resource.getSubTypeNames().add(name);

                Float newValue = (float)(Math.round(projectTransaction.getValue() * 100D) / 100D);
                resource.getSubTypeValues().add(newValue);
            }
            //if already exists subtype..
            else
            {
                int index = resource.getSubTypeNames().indexOf(name);
                Float newValue = resource.getSubTypeValues().get(index) + projectTransaction.getValue();
                newValue = (float)(Math.round(newValue * 100D) / 100D);
                resource.getSubTypeValues().set(index, newValue);
            }
        }
    }

    public Page<Project> filterProjects(Pageable pageable, String value, String dateSince, String dateUntil, Long clientId, Boolean deletedEntities) {

        Page<Project> projectPage = null;


        if(value.isEmpty() && dateSince.isEmpty() && dateUntil.isEmpty() && clientId==0&& deletedEntities==null)
            return projectRepository.findAllByActived(pageable, true);


        Specification<Project> specFilter= null;

        Client client = clientService.getClient(clientId);

        if(!value.isEmpty())
            specFilter= ProjectSpecifications.filter(value, dateSince, dateUntil, client);

        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = ProjectSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(ProjectSpecifications.filterDeleletedEntities(deletedEntities));

        projectPage = projectRepository.findAll(specFilter, pageable);

        return projectPage;
    }

    public void recoveryEntity(Long id) {
        Project entity = getProject(id);
        entity.setActived(true);
        projectRepository.save(entity);
    }
}
