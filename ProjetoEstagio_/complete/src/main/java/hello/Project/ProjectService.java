package hello.Project;

import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Client.ClientService;
import hello.Contact.Contact;
import hello.CostCenter.CostCenter;
import hello.CostCenter.CostCenterService;
import hello.Enums.Genre;
import hello.Project.Resources.ChartResource;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionRepository;
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
import java.util.List;

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

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }


    public Page<Project> findAllPageable(Pageable pageable, String value, String dateSince, String dateUntil, Long clientId) {


        //could receive params to filter de list
        if(value!= null || dateSince !=null || dateUntil != null)
            return filterProjects(pageable, value, dateSince, dateUntil, clientId);

        else
            return projectRepository.findAll(pageable);

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
        projectRepository.delete(project);
    }

    public void editProject(@Valid Project editedProject) {

        Project project = getProject(editedProject.getId());
        project.setScope(editedProject.getScope());
        project.setFinalDate(editedProject.getFinalDate());
        project.setInitialDate(editedProject.getInitialDate());
        project.setName(editedProject.getName());
        project.setDescription(editedProject.getDescription());
        project.setBalance(editedProject.getBalance());

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
        statistic.setTotal((int)total);
        statistic.setTotalCosts((int)totalCosts);
        statistic.setTotalRevenues((int)totalRevenues);


        return statistic;
    }
    public Page<Project> filterProjects(Pageable pageable, String value, String dateSince, String dateUntil, Long clientId) {

        Page<Project> projectPage = new PageImpl<>(new ArrayList<>());


        if(value.isEmpty() && dateSince.isEmpty() && dateUntil.isEmpty() && clientId==0){
            return projectRepository.findAll(pageable);
        }

        if(value.isEmpty() && dateSince.isEmpty() && dateUntil.isEmpty() && clientId==0){
            return projectRepository.findAll(pageable);
        }

        Specification<Project> specFilter;

        if(clientId == 0)
            specFilter= ProjectSpecifications.filter(value, dateSince, dateUntil, null);
        else
            specFilter= ProjectSpecifications.filter(value, dateSince, dateUntil, clientService.getClient(clientId));


        projectPage = projectRepository.findAll(specFilter, pageable);

        return projectPage;
    }
//
//    private Page<Project> joinTables(Pageable pageable, List<Project> listByName, List<Project> listByMinDate, List<Project> listByMaxDate, List<Project> listByClient) {
//
//        List<Project> projects = new ArrayList<>();
//
//        if(!listByName.isEmpty())
//            projects.addAll(listByName);
//
//        System.out.println("\n\n\n\n\n\n\n\n\n" + listByName);
//
//
//        if(!listByMinDate.isEmpty() && listByMinDate!=null)
//            projects.retainAll(listByMinDate);
//        if(!listByMaxDate.isEmpty()&& listByMaxDate!=null)
//            projects.retainAll(listByMaxDate);
//        if(!listByClient.isEmpty()&& listByClient!=null)
//            projects.retainAll(listByClient);
//
//        System.out.println("\n\n\n\n\n\n\n\n\n" + projects);
//        System.out.println("aqui");
//
//
//        return new PageImpl<>(projects, pageable,pageable.getPageSize());
//    }
}
