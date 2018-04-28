package hello.Employee;

import hello.Adress.Adress;
import hello.PostEmployee.PostEmployee;
import hello.PostEmployee.PostEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @Autowired
    PostEmployeeService postEmployeeService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listEmployees", employeeService.getEmployees());
        return "Employee/index";
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

}
