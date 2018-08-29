package hello.Employee;

import hello.Adress.Adress;
import hello.Client.Client;
import hello.Pager;
import hello.PostEmployee.PostEmployee;
import hello.PostEmployee.PostEmployeeService;
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
@RequestMapping(path="/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    PostEmployeeService postEmployeeService;

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("listEmployees", employeeService.getEmployees());
//        return "Employee/index";
//    }

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="switch_deleted_entities", required=false) Boolean deletedEntities)
    {
        ModelAndView modelAndView = new ModelAndView("Employee/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Employee> persons= employeeService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value, deletedEntities);

        Pager pager = new Pager(persons.getTotalPages(), persons.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", persons);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("switch_deleted_entities", deletedEntities);

        modelAndView.addObject("value_filter", value);

        return modelAndView;
    }

    @RequestMapping("/info_employee")
    public String infoEmployee(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);

        return "Employee/info_employee";
    }

    @GetMapping("/add_employee")
    public String addEmployee(Model model) {

        Employee employee = new Employee();
        List<Adress> adressList = new ArrayList<>();
        adressList.add(new Adress());
        employee.setAdresses(adressList);

        List<PostEmployee> postEmployeeList = postEmployeeService.getAllPostsEmployees();
        model.addAttribute("employee", employee);
        model.addAttribute("postEmployeeList", postEmployeeList);

        return "Employee/add_employee";
    }

    @PostMapping("/add_employee")
    public String addEmployee(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postEmployeeList", postEmployeeService.getAllPostsEmployees());
            return "Employee/add_employee";
        }

        employeeService.addEmployee(employee);

        model.addAttribute("postEmployeeList", postEmployeeService.getAllPostsEmployees());

        return "redirect:/employee/";
    }

    @GetMapping("/edit_employee")
    public String editEmployee(@RequestParam("id") Long id, Model model) {

        Employee employee = employeeService.getEmployee(id);
        List<PostEmployee> postEmployeeList = postEmployeeService.getAllPostsEmployees();
        model.addAttribute("employee", employee);
        model.addAttribute("postEmployeeList", postEmployeeList);

        return "Employee/edit_employee";
    }

    @PostMapping("/edit_employee")
    public String editEmployee(Model model, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postEmployeeList", postEmployeeService.getAllPostsEmployees());
            return "Employee/edit_employee";
        }

        employeeService.editEmployee(employee);

        model.addAttribute("postEmployeeList", postEmployeeService.getAllPostsEmployees());

        return "redirect:/employee/";
    }

    @RequestMapping("/remove_employee")
    public String removeEmployee(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);
        return "Employee/remove_employee :: modal";
    }
    @DeleteMapping("/remove_employee")
    public @ResponseBody String removeEmployee(@RequestParam("id") Long id) {
        employeeService.removeEmployee(id);
        return "redirect:/employee/";
    }

    @RequestMapping("/recovery_entity")
    public String recoveryEntity(@RequestParam("id") Long id, Model model) {

        Employee entity = employeeService.getEmployee(id);
        model.addAttribute("entity", entity);

        return "Supplier/recovery_entity :: modal";
    }
    @PostMapping("/recovery_entity")
    public @ResponseBody String recoveryEntity(@RequestParam("id") Long id) {
        employeeService.recoveryEntity(id);
        return "redirect:/supplier/";
    }
}
