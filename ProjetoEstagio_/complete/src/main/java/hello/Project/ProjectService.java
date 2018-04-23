package hello.Project;

import hello.Client.Client;
import hello.Client.ClientService;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addProject(Project project){

        ProjectClient projectClient = project.getProjectClient();

        Client c = clientService.getClient(projectClient.getClient().getId());
        Contact contact = clientService.getContact(c.getId(), projectClient.getContact().getId());

//        projectClient.getContact().setAdresses(contact.getAdresses());
//        projectClient.getContact().setName(contact.getName());
//        projectClient.getContact().setNumberPhone(contact.getNumberPhone());
//        projectClient.getContact().setEmail(contact.getEmail());
//
//
//        projectClient.getClient().setContacts(c.getContacts());
//        projectClient.getClient().setAdresses(c.getAdresses());
//        projectClient.getClient().setName(c.getName());
//        projectClient.getClient().setNumberPhone(c.getNumberPhone());
//        projectClient.getClient().setRegistrationCode(c.getRegistrationCode());



        projectClient.setClient(c);
        projectClient.setContact(contact);

        project.setProjectClient(projectClient);

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n" + project);
        projectRepository.save(project);
    }

    public Project getProject(Long id) {
        return projectRepository.getOne(id);
    }

    public void removeProject(Long id) {
        Project project = projectRepository.getOne(id);
        projectRepository.delete(project);
    }
}
