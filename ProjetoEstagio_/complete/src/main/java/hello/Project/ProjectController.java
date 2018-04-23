package hello.Project;

import hello.Client.ClientService;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

        Project project = new Project();
        project.setProjectClient(new ProjectClient());
        model.addAttribute("project", project);
        model.addAttribute("listClients", clientService.getClients());


        return "Project/add_project";
    }

    @PostMapping("/add_project")
    public String addProject(Model model, @Valid @ModelAttribute("project") Project project, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("project", new Project());
//            model.addAttribute("projectClient", new ProjectClient());
//            model.addAttribute("listClients", clientService.getClients());

            model.addAttribute("listClients", clientService.getClients());
            return "Project/add_project";
        }
        projectService.addProject(project);

        model.addAttribute("listProjects", projectService.getProjects());

        return "Project/index";
    }

    @RequestMapping("/remove_project")
    public String removeProject(@RequestParam("id") Long id, Model model) {

        Project project = projectService.getProject(id);
        model.addAttribute("project", project);

        return "Project/remove_project :: modal";
    }
    @DeleteMapping("/remove_project")
    public @ResponseBody String removeProject(@RequestParam("id") Long id) {
        projectService.removeProject(id);
        return "redirect:/client/";
    }

}
