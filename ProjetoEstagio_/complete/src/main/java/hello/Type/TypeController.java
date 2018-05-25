package hello.Type;

import hello.Client.Client;
import hello.Pager;
import hello.SubType.SubType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hello.Application.*;

@Controller
@RequestMapping(path="/type")
public class TypeController implements WebMvcConfigurer {

    @Autowired
    TypeService typeService;


    @RequestMapping("/get_subTypes")
    public String getSubTypes(@RequestParam("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("listSubTypes", type.getSubTypeList());
        return "SubTypes/subtypes_list :: options";
    }

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value,
                                        @RequestParam(name="category", required=false) String category
    )
    {
        ModelAndView modelAndView = new ModelAndView("Type/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Type> types= typeService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value, category);

        Pager pager = new Pager(types.getTotalPages(), types.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntities", types);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);



        return modelAndView;
    }

    @GetMapping("/add_type")
    public String addType(Model model) {

        model.addAttribute("type",new Type());
        return "Type/add_type";
    }

    @PostMapping("/add_type")
    public String addType(Model model, @Valid @ModelAttribute("type") Type type, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Type/add_type";
        }

        typeService.addType(type);

        model.addAttribute("listEntitys", typeService.getTypesActived());

        return "redirect:/type/";
    }

    @RequestMapping("/edit_type")
    public String editType(@RequestParam("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);

        return "Type/edit_type";
    }

    @RequestMapping("/info_type")
    public String infoType(@RequestParam("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);

        return "Type/info_type";
    }

    @RequestMapping("/remove_type")
    public String removeType(@RequestParam("id") Long id, Model model) {
        Type type= typeService.getType(id);
        model.addAttribute("type", type);
        return "Type/remove_type :: modal";
    }
    @DeleteMapping("/remove_type")
    public @ResponseBody String removeType(@RequestParam("id") Long id) {
        typeService.removeType(id);
        return "redirect:/type/";
    }

    @PostMapping("/edit_type")
    public String editType(@Valid Type type, BindingResult bindingResult,Model model) {


        if (bindingResult.hasErrors()) {
            return "Type/edit_type";
        }

        typeService.editType(type);
        return "redirect:/type/";
    }
}
