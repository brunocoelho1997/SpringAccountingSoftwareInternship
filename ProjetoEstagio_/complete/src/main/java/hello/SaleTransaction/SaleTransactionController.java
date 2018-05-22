package hello.SaleTransaction;


import hello.Enums.Genre;
import hello.Pager;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/sale_transaction")
public class SaleTransactionController implements WebMvcConfigurer {

    @Autowired
    SaleTransactionService saleTransactionService;

    @Autowired
    TypeService typeService;

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_id", required=false) Long typeId,
                                        @RequestParam(name="subtype_id", required=false) Long subTypeId,
                                        @RequestParam(name="date_since", required=false) String dateSince,
                                        @RequestParam(name="date_until", required=false) String dateUntil,
                                        @RequestParam(name="value_since", required=false) String valueSince,
                                        @RequestParam(name="value_until", required=false) String valueUntil)

    {
        ModelAndView modelAndView = new ModelAndView("SaleTransaction/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<SaleTransaction> saleTransactions = saleTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeId, subTypeId, dateSince, dateUntil, valueSince, valueUntil, Genre.REVENUE);


        Pager pager = new Pager(saleTransactions.getTotalPages(), saleTransactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntitys", saleTransactions);
        modelAndView.addObject("types", typeService.getTypes());
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        //TODO: correto?
        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("frequency", frequency);
        modelAndView.addObject("type_id", typeId);
        modelAndView.addObject("subtype_id", subTypeId);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("value_since", valueSince);
        modelAndView.addObject("value_until", valueUntil);

        return modelAndView;
    }
}
