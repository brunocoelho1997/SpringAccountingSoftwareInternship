package hello.GeneralTransaction;

import hello.Currency.CurrencyService;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.Enums.Genre;
import hello.Pager;
import hello.SubType.SubTypeService;
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
import java.util.Optional;

import static hello.Application.*;


@Controller
@RequestMapping(path="/general_transaction")
public class GeneralTransactionController {

    @Autowired
    GeneralTransactionService generalTransactionService;
    @Autowired
    TypeService typeService;

    @Autowired
    SubTypeService subTypeService;
    @Autowired
    CurrencyService currencyService;

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_value", required=false) String typeValue,
                                        @RequestParam(name="subtype_value", required=false) String subTypeValue,
                                        @RequestParam(name="date_since", required=false) String dateSince,
                                        @RequestParam(name="date_until", required=false) String dateUntil,
                                        @RequestParam(name="value_since", required=false) String valueSince,
                                        @RequestParam(name="value_until", required=false) String valueUntil,
                                        @RequestParam(name="switch_deleted_entities", required=false) Boolean deletedEntities)

    {
        ModelAndView modelAndView = new ModelAndView("GeneralTransaction/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<GeneralTransaction> saleTransactions = generalTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, Genre.COST, true);


        Pager pager = new Pager(saleTransactions.getTotalPages(), saleTransactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", saleTransactions);

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
        modelAndView.addObject("switch_deleted_entities", deletedEntities);
        modelAndView.addObject("currency", currencyService.getCurrentCurrencySelected());

        return modelAndView;
    }

    @GetMapping("/add_transaction")
    public String addRevenue(Model model) {

        GeneralTransaction transaction = new GeneralTransaction();
        transaction.setCurrency(currencyService.getCurrentCurrencySelected());
        transaction.setGenre(Genre.COST);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getDistinctTypesActivedAndManuallyCreated());

        return "GeneralTransaction/add_transaction";
    }

    @PostMapping("/add_transaction")
    public String addRevenue(Model model, @Valid @ModelAttribute("transaction") GeneralTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getDistinctTypesActivedAndManuallyCreated());
            return "GeneralTransaction/add_transaction";
        }
        generalTransactionService.addTransaction(transaction);
        return "redirect:/general_transaction/";
    }

    @GetMapping("/edit_transaction")
    public String editTransaction(Model model,@RequestParam("id") Long id) {

        GeneralTransaction transaction = generalTransactionService.getGeneralTransaction(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getDistinctTypesActivedAndManuallyCreated());

        return "GeneralTransaction/edit_transaction";
    }
    @PostMapping("/edit_transaction")
    public String editTransaction(Model model, @Valid @ModelAttribute("transaction") GeneralTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            return "GeneralTransaction/edit_transaction";
        }
        generalTransactionService.editTransaction(transaction);

        return "redirect:/general_transaction/";

    }

    @RequestMapping("/remove_transaction")
    public String removeTransaction(@RequestParam("id") Long id, Model model) {

        GeneralTransaction transaction = generalTransactionService.getGeneralTransaction(id);
        model.addAttribute("transaction", transaction);

        return "GeneralTransaction/remove_transaction :: modal";
    }
    @DeleteMapping("/remove_transaction")
    public @ResponseBody String removeTransaction(@RequestParam("id") Long id) {
        generalTransactionService.removeTransaction(id);
        return "redirect:/general_transaction/";
    }

    @RequestMapping("/info_transaction")
    public String infoTransaction(@Valid @RequestParam("id") Long id, Model model) {

        GeneralTransaction transaction = generalTransactionService.getGeneralTransaction(id);

        model.addAttribute("transaction", transaction);
        return "GeneralTransaction/info_transaction";
    }
    @RequestMapping("/recovery_transaction")
    public String recoveryTransaction(@RequestParam("id") Long id, Model model) {

        GeneralTransaction transaction = generalTransactionService.getGeneralTransaction(id);
        model.addAttribute("transaction", transaction);

        return "GeneralTransaction/recovery_transaction :: modal";
    }
    @PostMapping("/recovery_transaction")
    public @ResponseBody String recoveryTransaction(@RequestParam("id") Long id) {
        generalTransactionService.recoveryTransaction(id);
        return "redirect:/general_transaction/";
    }
}
