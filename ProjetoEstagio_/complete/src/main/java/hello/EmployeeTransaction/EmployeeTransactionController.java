package hello.EmployeeTransaction;

import hello.Employee.EmployeeService;
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
import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/employee_transaction")
public class EmployeeTransactionController {
    @Autowired
    EmployeeTransactionService employeeTransactionService;
    @Autowired
    TypeService typeService;
    @Autowired
    EmployeeService employeeService;


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
        ModelAndView modelAndView = new ModelAndView("EmployeeTransaction/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<EmployeeTransaction> employeeTransactions = employeeTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, Genre.COST);


        Pager pager = new Pager(employeeTransactions.getTotalPages(), employeeTransactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", employeeTransactions);
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

        EmployeeTransaction transaction = new EmployeeTransaction();
        transaction.setGenre(Genre.COST);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("employees", employeeService.getEmployees());

        return "EmployeeTransaction/add_transaction";
    }

    @PostMapping("/add_transaction")
    public String addTransaction(Model model, @Valid @ModelAttribute("transaction") EmployeeTransaction employeeTransaction, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("employees", employeeService.getEmployees());

            /*
            TODO: caso nos nos engamos em algo... ao validar dps o subtyppe fica desselecionado!! Verificar
             */
            return "EmployeeTransaction/add_transaction";
        }
        employeeTransactionService.addTransaction(employeeTransaction);

        return "redirect:/employee_transaction/";
    }

    @GetMapping("/edit_transaction")
    public String editTransaction(Model model,@RequestParam("id") Long id) {

        EmployeeTransaction transaction = employeeTransactionService.getEmployeeTransaction(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getTypes());
        if(transaction.getType().getSubType()!=null)
            model.addAttribute("subtype_id", transaction.getType().getSubType().getId());
        model.addAttribute("employees", employeeService.getEmployees());

        return "EmployeeTransaction/edit_transaction";
    }
    @PostMapping("/edit_transaction")
    public String editTransaction(Model model, @Valid @ModelAttribute("transaction") EmployeeTransaction employeeTransaction, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("employees", employeeService.getEmployees());
            return "EmployeeTransaction/edit_transaction";
        }
        employeeTransactionService.editEmployeeTransaction(employeeTransaction);


        return "redirect:/employee_transaction/";

    }

    @RequestMapping("/remove_transaction")
    public String removeTransaction(@RequestParam("id") Long id, Model model) {

        EmployeeTransaction projectTransaction = employeeTransactionService.getEmployeeTransaction(id);
        model.addAttribute("transaction", projectTransaction);

        return "EmployeeTransaction/remove_transaction :: modal";
    }
    @DeleteMapping("/remove_transaction")
    public @ResponseBody String removeTransaction(@RequestParam("id") Long id) {
        employeeTransactionService.removeEmployeeTransaction(id);
        return "redirect:/employee_transaction/";
    }

    @RequestMapping("/info_transaction")
    public String infoProject(@Valid @RequestParam("id") Long id, Model model) {

        EmployeeTransaction transaction = employeeTransactionService.getEmployeeTransaction(id);

        model.addAttribute("transaction", transaction);
        return "EmployeeTransaction/info_transaction";
    }
}
