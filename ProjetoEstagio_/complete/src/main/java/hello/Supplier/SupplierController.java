package hello.Supplier;

import hello.Adress.Adress;
import hello.Pager;
import hello.Supplier.Resources.TextContact;
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
@RequestMapping(path="/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value)
    {
        ModelAndView modelAndView = new ModelAndView("Supplier/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Supplier> persons= supplierService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value);

        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", persons);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);

        return modelAndView;
    }

    @RequestMapping("/info_supplier")
    public String infoSupplier(@RequestParam("id") Long id, Model model) {
        Supplier supplier = supplierService.getSupplier(id);
        model.addAttribute("supplier", supplier);

        return "Supplier/info_supplier";
    }
//
    @GetMapping("/add_supplier")
    public String addEmployee(Model model) {

        Supplier supplier = new Supplier();
        List<Adress> adressList = new ArrayList<>();
        adressList.add(new Adress());
        supplier.setAdresses(adressList);

        List<TextContact> contacts = new ArrayList<>();
        contacts.add(new TextContact());
        supplier.setContacts(contacts);

        model.addAttribute("supplier", supplier);


        return "Supplier/add_supplier";
    }

    @PostMapping("/add_supplier")
    public String addEmployee(Model model, @Valid @ModelAttribute("supplier") Supplier supplier, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Supplier/add_supplier";
        }

        supplierService.addSupplier(supplier);

        return "redirect:/supplier/";
    }

    @RequestMapping("/edit_supplier")
    public String editSupplier(@RequestParam("id") Long id, Model model) {
        Supplier entity= supplierService.getSupplier(id);
        model.addAttribute("supplier", entity);

        return "Supplier/edit_supplier";
    }
    @PostMapping("/edit_supplier")
    public String editClient(@Valid Supplier supplier, BindingResult bindingResult,Model model) {


        if (bindingResult.hasErrors()) {
            return "Supplier/edit_supplier";
        }

        supplierService.editSupplier(supplier);
        return "redirect:/supplier/";
    }

    @RequestMapping("/remove_supplier")
    public String removeClient(@RequestParam("id") Long id, Model model) {
        Supplier supplier = supplierService.getSupplier(id);
        model.addAttribute("supplier", supplier);
        return "Supplier/remove_supplier :: modal";
    }
    @DeleteMapping("/remove_supplier")
    public @ResponseBody String removeSupplier(@RequestParam("id") Long id) {
        supplierService.removeSupplier(id);
        return "redirect:/supplier/";
    }
}
