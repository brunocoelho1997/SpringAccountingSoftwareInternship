package hello.FinancialProjection;

import hello.Enums.Genre;
import hello.Pager;
import hello.Transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/financial_projection")

public class FinancialProjectionController {

    @Autowired
    FinancialProjectionService financialProjectionService;

    @GetMapping("/costs")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page)

    {
        ModelAndView modelAndView = new ModelAndView("FinancialProjection/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Transaction> transactions = financialProjectionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), Genre.COST);

        Pager pager = new Pager(transactions.getTotalPages(), transactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", transactions);

        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }
}
