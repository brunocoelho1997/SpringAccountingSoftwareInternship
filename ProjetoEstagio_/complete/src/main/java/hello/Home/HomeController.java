package hello.Home;

import hello.Currency.CurrencyService;
import hello.Enums.Genre;
import hello.Home.Resources.ChartResourceStatics;
import hello.Home.Resources.FinancialChartResource;
import hello.Pager;
import hello.Project.ProjectService;
import hello.Project.Resources.ChartResource;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionService;
import hello.SaleTransaction.SaleTransactionService;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static hello.Application.*;

@Controller
@RequestMapping(path="/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ProjectTransactionService projectTransactionService;
    @Autowired
    private SaleTransactionService saleTransactionService;
    @Autowired
    CurrencyService currencyService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam("pageSize") Optional<Integer> pageSize)
    {

        ModelAndView modelAndView = new ModelAndView("Home/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


//        Page<EmployeeTransaction> employeeTransactions = employeeTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, Genre.COST);

        List<Transaction> transactionList = new ArrayList();

        transactionList.addAll(projectTransactionService.findAllByGenreAndActivedAndExecuted(Genre.REVENUE, true,true));
        transactionList.addAll(saleTransactionService.findAllByGenreAndActivedAndExecuted(Genre.REVENUE, true,true));
        Collections.sort(transactionList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction transaction, Transaction t1) {

                if(transaction.getDate().isBefore(t1.getDate()))
                    return 1;

                return -1;
            }
        });

        int n = (evalPageSize > transactionList.size() ? transactionList.size() : evalPageSize);

        List<Transaction> pageTransactions = new ArrayList<>(transactionList.subList(0,n));

        modelAndView.addObject("listEntities", pageTransactions);
        modelAndView.addObject("yearsToSelect", homeService.getAllYears());

        modelAndView.addObject("financialProjectionRevenue", homeService.financialProjection(Genre.REVENUE));
        modelAndView.addObject("financialProjectionCost", homeService.financialProjection(Genre.COST));


        modelAndView.addObject("currency", currencyService.getCurrentCurrencySelected());


        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        return modelAndView;
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
