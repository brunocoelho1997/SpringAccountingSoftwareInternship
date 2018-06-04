package hello.SheetTransaction;

import hello.Employee.Employee;
import hello.Employee.EmployeeService;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.Enums.Genre;
import hello.Pager;
import hello.Project.ProjectService;
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
import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/sheet_transaction")
public class SheetTransactionController {

    @Autowired
    SheetTransactionService sheetTransactionService;
    @Autowired
    TypeService typeService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_value", required=false) String typeValue,
                                        @RequestParam(name="subtype_value", required=false) String subTypeValue,
                                        @RequestParam(name="employee_id", required=false) Long employeeId,
                                        @RequestParam(name="date_since", required=false) String dateSince,
                                        @RequestParam(name="date_until", required=false) String dateUntil,
                                        @RequestParam(name="value_since", required=false) String valueSince,
                                        @RequestParam(name="value_until", required=false) String valueUntil)

    {
        ModelAndView modelAndView = new ModelAndView("SheetTransaction/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<SheetTransaction> transactions = sheetTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, Genre.COST);

        Pager pager = new Pager(transactions.getTotalPages(), transactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", transactions);
        modelAndView.addObject("types", typeService.getDistinctTypes());
        modelAndView.addObject("employees", employeeService.getEmployees());
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("frequency", frequency);
        modelAndView.addObject("type_value", typeValue);
        modelAndView.addObject("subtype_value", subTypeValue);
        modelAndView.addObject("employee_id", employeeId);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("value_since", valueSince);
        modelAndView.addObject("value_until", valueUntil);


        return modelAndView;
    }

    @GetMapping("/add_transaction")
    public String addTransaction(Model model) {

        SheetTransaction transaction = new SheetTransaction();
        transaction.setGenre(Genre.COST);
        transaction.setHoursPerProjectList(new ArrayList<>());

        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("projects", projectService.getProjects());


        return "SheetTransaction/add_transaction";
    }

    @PostMapping("/add_transaction")
    public String addTransaction(Model model, @Valid @ModelAttribute("transaction") SheetTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {

        ModelAndView modelAndView = new ModelAndView("SheetTransaction/add_transaction");

        if (bindingResult.hasErrors()) {

            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("employees", employeeService.getEmployees());
            model.addAttribute("projects", projectService.getProjects());

            return "SheetTransaction/add_transaction";
        }

        sheetTransactionService.addTransaction(transaction);

        model.addAttribute("types", typeService.getTypes());
        if(transaction.getType().getSubType()!=null)
        model.addAttribute("subtype_id", transaction.getType().getSubType().getId());
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("projects", projectService.getProjects());

        transaction.setHoursPerProjectList(new ArrayList<>());
        transaction.setEmployee(null);
        transaction.setValue(0);
        model.addAttribute("transaction", transaction);

        return "SheetTransaction/add_transaction";
    }

    @GetMapping("/edit_transaction")
    public String editTransaction(Model model,@RequestParam("id") Long id) {

        SheetTransaction transaction = sheetTransactionService.getTransaction(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("projects", projectService.getProjects());
        if(transaction.getType().getSubType()!=null)
            model.addAttribute("subtype_id", transaction.getType().getSubType().getId());

        return "SheetTransaction/edit_transaction";
    }
    @PostMapping("/edit_transaction")
    public String editTransaction(Model model, @Valid @ModelAttribute("transaction") SheetTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("employees", employeeService.getEmployees());
            return "SheetTransaction/edit_transaction";
        }
        sheetTransactionService.editTransaction(transaction);


        return "redirect:/sheet_transaction/";

    }

    @RequestMapping("/remove_transaction")
    public String removeTransaction(@RequestParam("id") Long id, Model model) {

        SheetTransaction transaction = sheetTransactionService.getTransaction(id);
        model.addAttribute("transaction", transaction);

        return "SheetTransaction/remove_transaction :: modal";
    }
    @DeleteMapping("/remove_transaction")
    public @ResponseBody String removeTransaction(@RequestParam("id") Long id) {
        sheetTransactionService.removeTransaction(id);
        return "redirect:/sheet_transaction/";
    }

    @RequestMapping("/info_transaction")
    public String infoProject(@Valid @RequestParam("id") Long id, Model model) {

        SheetTransaction transaction = sheetTransactionService.getTransaction(id);

        model.addAttribute("transaction", transaction);
        return "SheetTransaction/info_transaction";
    }
}
