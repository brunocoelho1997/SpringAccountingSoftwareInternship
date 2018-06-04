package hello.SupplierTransaction;

import hello.Enums.Genre;
import hello.Pager;
import hello.Supplier.SupplierService;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/supplier_transaction")
public class SupplierTransactionController implements WebMvcConfigurer {

    @Autowired
    SupplierTransactionService supplierTransactionService;

    @Autowired
    TypeService typeService;

    @Autowired
    SupplierService supplierService;

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_value", required=false) String typeValue,
                                        @RequestParam(name="subtype_value", required=false) String subTypeValue,
                                        @RequestParam(name="supplier_id", required=false) Long supplierId,
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

        Page<SupplierTransaction> transactions = supplierTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeValue, subTypeValue, supplierId, dateSince, dateUntil, valueSince, valueUntil, Genre.REVENUE);


        Pager pager = new Pager(transactions.getTotalPages(), transactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", transactions);
        modelAndView.addObject("types", typeService.getDistinctTypes());
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("frequency", frequency);
        modelAndView.addObject("type_id", typeValue);
        modelAndView.addObject("subtype_id", subTypeValue);
        modelAndView.addObject("supplier_id", supplierId);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("value_since", valueSince);
        modelAndView.addObject("value_until", valueUntil);

        return modelAndView;
    }

    @GetMapping("/add_transaction")
    public String addRevenue(Model model) {

        SupplierTransaction revenue = new SupplierTransaction();
        revenue.setGenre(Genre.COST);
        model.addAttribute("transaction", revenue);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("suppliers", supplierService.getSuppliers());

        return "SupplierTransaction/add_transaction";
    }

    @PostMapping("/add_transaction")
    public String addRevenue(Model model, @Valid @ModelAttribute("transaction") SupplierTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("suppliers", supplierService.getSuppliers());
            return "SupplierTransaction/add_transaction";
        }
        supplierTransactionService.addTransaction(transaction);
        return "redirect:/supplier_transaction/";
    }

    @GetMapping("/edit_transaction")
    public String editTransaction(Model model,@RequestParam("id") Long id) {

        SupplierTransaction transaction = supplierTransactionService.getEmployeeTransaction(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        if(transaction.getType().getSubType()!=null)
            model.addAttribute("subtype_id", transaction.getType().getSubType().getId());


        return "SaleTransaction/edit_transaction";
    }
    @PostMapping("/edit_transaction")
    public String editTransaction(Model model, @Valid @ModelAttribute("transaction") SupplierTransaction transaction, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getTypes());
            model.addAttribute("suppliers", supplierService.getSuppliers());

            return "SaleTransaction/edit_transaction";
        }
        supplierTransactionService.editTransaction(transaction);

        return "redirect:/sale_transaction/";

    }
}
