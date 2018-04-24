package hello.Type;

import hello.Client.Client;
import hello.SubType.SubType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
}
