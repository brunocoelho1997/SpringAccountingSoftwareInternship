package hello.Project;

import hello.Client.ClientService;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/project")
public class ProjectController implements WebMvcConfigurer {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listProjects", projectService.getProjects());
        return "Project/index";
    }

    @GetMapping("/add_project")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("projectClient", new ProjectClient());

        model.addAttribute("listClients", clientService.getClients());


        return "Project/add_project";
    }

//    @PostMapping("/add_project")
//    public String addProject(Model model, @Valid @ModelAttribute("projectDTO") Project project, BindingResult bindingResult, RedirectAttributes attributes) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("listDTOClients", clientService.getInfoClietDTOList());
//            model.addAttribute("id", project.getId());
//            return "Project/add_project";
//        }
//        projectService.addProject(project);
//
//        model.addAttribute("project", new Project());
//        model.addAttribute("projectClient", new ProjectClient());
//
//        model.addAttribute("listClients", clientService.getClientsList());
//
//        List<Contact> contactList = new ArrayList<>();
//        Contact contact = new Contact();
//        contact.setName("pose");
//        contact.setId((long)1);
//        contactList.add(contact);
//        model.addAttribute("listContacts", contactList);
//
//        return "Project/index";
//    }

}
