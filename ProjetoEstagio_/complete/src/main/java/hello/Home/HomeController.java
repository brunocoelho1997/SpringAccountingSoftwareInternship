package hello.Home;

import hello.Home.Resources.ChartResourceStatics;
import hello.Home.Resources.FinancialChartResource;
import hello.Project.Resources.ChartResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/")
    public String index(Model model) {


        return "Home/index";
    }


    @GetMapping("/get_statics")
    @ResponseBody
    public ChartResourceStatics get_statics(Model model) {

        ChartResourceStatics chartResourceStatics = homeService.getStatistic();
        return chartResourceStatics;
    }

    @GetMapping("/get_financialProjection")
    @ResponseBody
    public FinancialChartResource get_statics(@RequestParam("year") Long year, Model model) {

        FinancialChartResource financialChartResource = homeService.getStatistic(year);

        return financialChartResource;
    }

}
