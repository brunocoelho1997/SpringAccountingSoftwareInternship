package hello.Persons;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/client")
public class ClientController implements WebMvcConfigurer {

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listClients", clientRepository.findAll());
        return "clients_index";
    }


    @GetMapping("/add_client")
    public String addClient(Model model) {
        model.addAttribute("client", new Client());
        return "add_client";
    }



    @PostMapping("/add_submit")
    public String greetingSubmit(@Valid Client client, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add_client";
        }

        clientRepository.save(client);

        return "redirect:/client/";
    }

    @RequestMapping("/infoclient")
    public String infoClient(@RequestParam("id") Long id, Model model) {

        Client c = clientRepository.getOne(id);

        model.addAttribute("client", c);
        return "info_client";
    }

}
