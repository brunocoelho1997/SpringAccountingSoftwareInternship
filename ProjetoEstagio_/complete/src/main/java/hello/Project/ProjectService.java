package hello.Project;

import hello.Client.Client;
import hello.Client.ClientService;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientService clientService;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }


    //method private! If you need a project use method getProject(long id);
    private Project getOne(Long id) {

        try
        {
            if(id == null)
                throw new EntityNotFoundException();

            Project project = projectRepository.getOne(id);

            return project;
        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }

    public Project getProject(Long id) {
        return getOne(id);
    }

    public void addProject(Project project){

        ProjectClient projectClient = project.getProjectClient();

        Client c = clientService.getClient(projectClient.getClient().getId());
        Contact contact = clientService.getContact(c.getId(), projectClient.getContact().getId());
        projectClient.setClient(c);
        projectClient.setContact(contact);

        project.setProjectClient(projectClient);

        projectRepository.save(project);
    }

    public void removeProject(Long id) {
        Project project = getOne(id);
        projectRepository.delete(project);
    }

    public void editProject(@Valid Project editedProject) {

        Project project = getOne(editedProject.getId());
        project.setScope(editedProject.getScope());
        project.setFinalDate(editedProject.getFinalDate());
        project.setInitialDate(editedProject.getInitialDate());
        project.setName(editedProject.getName());
        project.setDescription(editedProject.getDescription());
        project.setBalance(editedProject.getBalance());
        project.setProjectClient(editedProject.getProjectClient());

        projectRepository.save(project);

    }
}
