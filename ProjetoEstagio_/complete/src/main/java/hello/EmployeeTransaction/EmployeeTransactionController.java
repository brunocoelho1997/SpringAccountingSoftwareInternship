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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    ProjectService projectService;


    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="frequency", required=false) String frequency,
                                        @RequestParam(name="type_id", required=false) Long typeId,
                                        @RequestParam(name="subtype_id", required=false) Long subTypeId,
                                        @RequestParam(name="project_id", required=false) Long projectId,
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

        Page<EmployeeTransaction> employeeTransactions = employeeTransactionService.findAllPageableByGenre(PageRequest.of(evalPage, evalPageSize), value, frequency, typeId, subTypeId, projectId, employeeId, dateSince, dateUntil, valueSince, valueUntil, Genre.COST);


        Pager pager = new Pager(employeeTransactions.getTotalPages(), employeeTransactions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", employeeTransactions);
        modelAndView.addObject("types", typeService.getTypes());
        modelAndView.addObject("projects", projectService.getProjects());
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("frequency", frequency);
        modelAndView.addObject("type_id", typeId);
        modelAndView.addObject("subtype_id", subTypeId);
        modelAndView.addObject("project_id", projectId);
        modelAndView.addObject("employee_id", employeeId);
        modelAndView.addObject("date_since", dateSince);
        modelAndView.addObject("date_until", dateUntil);
        modelAndView.addObject("value_since", valueSince);
        modelAndView.addObject("value_until", valueUntil);

        return modelAndView;
    }
}
