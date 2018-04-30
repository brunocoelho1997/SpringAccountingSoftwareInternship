package hello.ProjectTransaction;

import hello.Enums.Genre;
import hello.Project.ProjectService;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String indexRevenue(Model model) {

        model.addAttribute("listTransactions",projectTransactionService.getProjectsTransactionsByGenre(Genre.REVENUE));
        return "ProjectTransaction/index";
    }

    @GetMapping("/add_revenue")
    public String addRevenue(Model model) {

        ProjectTransaction revenue = new ProjectTransaction();
        revenue.setGenre(Genre.REVENUE);
        model.addAttribute("transaction", revenue);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("projects", projectService.getProjects());

        return "ProjectTransaction/add_transaction";
    }

    @PostMapping("/add_transaction")
    public String addRevenue(Model model, @Valid @ModelAttribute("transaction") ProjectTransaction projectTransaction, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("projects", projectService.getProjects());
            return "ProjectTransaction/add_transaction";
        }
        projectTransactionService.addTransaction(projectTransaction);

        return "redirect:/project_transaction/revenue";
    }

    @GetMapping("/edit_transaction")
    public String editTransaction(Model model,@RequestParam("id") Long id) {

        ProjectTransaction revenue = projectTransactionService.getProjectTransaction(id);
        model.addAttribute("transaction", revenue);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("projects", projectService.getProjects());

        return "ProjectTransaction/edit_transaction";
    }
    @PostMapping("/edit_transaction")
    public String editTransaction(Model model, @Valid @ModelAttribute("transaction") ProjectTransaction projectTransaction, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("projects", projectService.getProjects());
            return "ProjectTransaction/edit_transaction";
        }
        projectTransactionService.editProjectTransaction(projectTransaction);
        return "redirect:/project_transaction/revenue";
    }

    @RequestMapping("/remove_transaction")
    public String removeTransaction(@RequestParam("id") Long id, Model model) {

        ProjectTransaction projectTransaction = projectTransactionService.getProjectTransaction(id);
        model.addAttribute("transaction", projectTransaction);

        return "ProjectTransaction/remove_transaction :: modal";
    }
    @DeleteMapping("/remove_transaction")
    public @ResponseBody String removeTransaction(@RequestParam("id") Long id) {
        projectTransactionService.removeProjectTransaction(id);
        return "redirect:/project_transaction/revenue";
    }

    @RequestMapping("/info_transaction")
    public String infoProject(@Valid @RequestParam("id") Long id, Model model) {

        ProjectTransaction projectTransaction = projectTransactionService.getProjectTransaction(id);

        model.addAttribute("transaction", projectTransaction);
        return "ProjectTransaction/info_transaction";
    }

    @GetMapping("/cost")
    public String indexCost(Model model) {

        model.addAttribute("listTransactions",projectTransactionService.getProjectsTransactionsByGenre(Genre.COST));
        return "ProjectTransaction/index";
    }

    @GetMapping("/add_cost")
    public String addCost(Model model) {

        ProjectTransaction cost = new ProjectTransaction();
        cost.setGenre(Genre.COST);
        model.addAttribute("transaction", cost);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("projects", projectService.getProjects());

        return "ProjectTransaction/add_transaction";
    }

}
