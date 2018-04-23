package hello.ProjectTransaction;

import hello.Contact.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping(path="/project_transaction")
public class ProjectTransactionController implements WebMvcConfigurer {

    @Autowired
    ProjectTransactionService projectTransactionService;

    @GetMapping("/revenue")
    public String index(Model model) {

        model.addAttribute("listRevenues",projectTransactionService.getProjectsTransactionsByGenre(Genre.REVENUE));
        return "ProjectTransaction/revenues_index";
    }

    @GetMapping("/add_revenue")
    public String addRevenue(Model model) {

        model.addAttribute("listRevenues",projectTransactionService.getProjectsTransactionsByGenre(Genre.REVENUE));
        return "ProjectTransaction/add_revenue";
    }



}
