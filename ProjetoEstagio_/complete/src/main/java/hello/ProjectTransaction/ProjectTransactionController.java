package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.ProjectService;
import hello.Type.TypeService;
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

@Controller
@RequestMapping(path="/project_transaction")
public class ProjectTransactionController implements WebMvcConfigurer {

    @Autowired
    ProjectTransactionService projectTransactionService;

    @Autowired
    TypeService typeService;

    @Autowired
    ProjectService projectService;


    @GetMapping("/revenue")
    public String index(Model model) {

        model.addAttribute("listRevenues",projectTransactionService.getProjectsTransactionsByGenre(Genre.REVENUE));
        return "ProjectTransaction/revenues_index";
    }

    @GetMapping("/add_revenue")
    public String addRevenue(Model model) {

        ProjectTransaction revenue = new ProjectTransaction();
        revenue.setGenre(Genre.REVENUE);
        model.addAttribute("transaction", revenue);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("projects", projectService.getProjects());

        return "ProjectTransaction/add_revenue";
    }

    @PostMapping("/add_revenue")
    public String addRevenue(Model model, @Valid @ModelAttribute("transaction") ProjectTransaction projectTransaction, BindingResult bindingResult, RedirectAttributes attributes) {

        System.out.println("\n\n\n\n\n\n\n\n " + projectTransaction + "\n\n\n");

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("projects", projectService.getProjects());
            return "ProjectTransaction/add_revenue";
        }
        projectTransactionService.addTransaction(projectTransaction);

        model.addAttribute("listRevenues",projectTransactionService.getProjectsTransactionsByGenre(Genre.REVENUE));

        /*
        TODO: acho que esta' a pedir um redirect aqui!!!!
         */
        return "Project/index";
    }



}
