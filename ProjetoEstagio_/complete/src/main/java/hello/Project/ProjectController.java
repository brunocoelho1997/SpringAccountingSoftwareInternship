package hello.Project;

import hello.Client.ClientService;
import hello.Contact.Contact;
import hello.CostCenter.CostCenterService;
import hello.Pager;
import hello.Project.Resources.ChartResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static hello.Application.*;


@Controller
@RequestMapping(path="/project")
public class ProjectController implements WebMvcConfigurer {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CostCenterService costCenterService;

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("listProjects", projectService.getProjects());
//        return "Project/index";
//    }

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="date_since", required=false) String dateSince,
                                        @RequestParam(name="date_until", required=false) String dateUntil,
                                        @RequestParam(name="client_id", required=false) Long clientId)
    {
        ModelAndView modelAndView = new ModelAndView("Project/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Project> projects= projectService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value, dateSince, dateUntil,clientId);

        Pager pager = new Pager(projects.getTotalPages(), projects.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listClients", clientService.getClients());
        modelAndView.addObject("listEntities", projects);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("client_id", clientId);

        return modelAndView;
    }

    @GetMapping("/add_project")
    public String addProject(Model model) {

        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("listClients", clientService.getClients());
        model.addAttribute("listCostsCenter", costCenterService.getCostsCenter());

        /*
        TODO: para validacao das datas na backend uma pessoa teria de criar a propria anotacao...
        https://stackoverflow.com/questions/1700295/java-bean-validation-jsr303-constraints-involving-relationship-between-several
         */

        return "Project/add_project";
    }

    @PostMapping("/add_project")
    public String addProject(Model model, @Valid @ModelAttribute("project") Project project, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listClients", clientService.getClients());
            model.addAttribute("listCostsCenter", costCenterService.getCostsCenter());
            return "Project/add_project";
        }
        projectService.addProject(project);

        model.addAttribute("listProjects", projectService.getProjectsActived());

        return "redirect:/project/";
    }

    @GetMapping("/edit_project")
    public String editProject(Model model,@RequestParam("id") Long id) {
        model.addAttribute("project", projectService.getProject(id));
        model.addAttribute("listClients", clientService.getClients());
        model.addAttribute("listCostsCenter", costCenterService.getCostsCenter());

        return "Project/edit_project";
    }
    @PostMapping("/edit_project")
    public String editProject(Model model, @Valid @ModelAttribute("project") Project project, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listClients", clientService.getClients());
            model.addAttribute("listCostsCenter", costCenterService.getCostsCenter());
            return "Project/edit_project";
        }
        projectService.editProject(project);

        model.addAttribute("listProjects", projectService.getProjectsActived());

        return "redirect:/project/";
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

    @RequestMapping("/info_project")
    public String infoProject(@RequestParam("id") Long id, Model model) {

        Project project= projectService.getProject(id);
        model.addAttribute("project", project);
        return "Project/info_project";
    }


    @GetMapping("/get_chart_resource")
    @ResponseBody
    public ChartResource getChartResource(@RequestParam("id") Long id, Model model) {

        ChartResource chartResource = projectService.getStatistic(id);
        return chartResource;
    }

    @RequestMapping("/get_options_projects")
    public String getOptionsProjects(Model model) {

        model.addAttribute("projects", projectService.getProjects());

        return "Project/options_project :: options";
    }

}
