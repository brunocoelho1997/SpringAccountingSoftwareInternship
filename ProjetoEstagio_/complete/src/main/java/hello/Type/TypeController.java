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
        ModelAndView modelAndView = new ModelAndView("Client/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Type> types= typeService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value, category);

        Pager pager = new Pager(types.getTotalPages(), types.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntitys", types);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        modelAndView.addObject("value_filter", value);
        modelAndView.addObject("category", category);


        return modelAndView;
    }

    @GetMapping("/add_type")
    public String addType(Model model) {

        model.addAttribute("type", new Type());
        return "Type/add_type";
    }

    @PostMapping("/add_type")
    public String addType(Model model, @Valid @ModelAttribute("type") Type type, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Type/add_type";
        }

        typeService.addType(type);

        model.addAttribute("listClients", typeService.getTypesActived());

        return "redirect:/client/";
    }

    @RequestMapping("/edit_type")
    public String editType(@RequestParam("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);

        return "Client/edit_client";
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

//    @PostMapping("/edit_type")
//    public String editType(@Valid Client client, BindingResult bindingResult,Model model) {
//
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("listPostContact", postContactService.getAll());
//            return "Client/edit_client";
//        }
//
//        clientService.editClient(client);
//        return "redirect:/client/";
//    }
}
