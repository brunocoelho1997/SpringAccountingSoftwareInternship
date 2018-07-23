package hello.FinancialProjection;

import hello.Enums.Genre;
import hello.FinancialProjection.Resources.FinancialProjectionAproved;
import hello.Pager;
import hello.Project.ProjectService;
import hello.SheetTransaction.Resources.HoursPerProject;
import hello.SubType.SubTypeService;
import hello.Transaction.Transaction;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/financial_projection")

public class FinancialProjectionController {

    @Autowired
    FinancialProjectionService financialProjectionService;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/costs")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_value", required=false) String typeValue,
                                        @RequestParam(name="subtype_value", required=false) String subTypeValue,
                                        @RequestParam(name="date_since", required=false) String dateSince,
                                        @RequestParam(name="date_until", required=false) String dateUntil,
                                        @RequestParam(name="value_since", required=false) String valueSince,
                                        @RequestParam(name="value_until", required=false) String valueUntil)

    {
        ModelAndView modelAndView = new ModelAndView("FinancialProjection/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Transaction> transactions = financialProjectionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, Genre.COST);

        Pager pager = new Pager(transactions.getTotalPages(), transactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", transactions);

        //        ISTO E' APENAS PARA QUANDO NAO TEM A APRESENTAR SUBTYPES DE TYPES
        modelAndView.addObject("types", typeService.getDistinctTypes());
        modelAndView.addObject("subTypes", subTypeService.getDistinctSubTypesActived());

        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("frequency", frequency);
        modelAndView.addObject("type_value", typeValue);
        modelAndView.addObject("subtype_value", subTypeValue);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("value_since", valueSince);
        modelAndView.addObject("value_until", valueUntil);
        return modelAndView;
    }

    @GetMapping("/transaction_executed")
    public String executedTransaction(Model model, @RequestParam("id") Long id) {

        Transaction transaction = financialProjectionService.getTransaction(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("projects", projectService.getProjects());

        return "FinancialProjection/executed_transaction";
    }
    @PostMapping("/transaction_executed")
    public String executedTransaction(Model model, @Valid @ModelAttribute("financialProjectionAproved") FinancialProjectionAproved financialProjectionAproved, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "FinancialProjection/executed_transaction";
        }
        System.out.print(financialProjectionAproved);

        financialProjectionService.aproveTransaction(financialProjectionAproved);


        return "redirect:/financial_projection/costs";

    }
}
