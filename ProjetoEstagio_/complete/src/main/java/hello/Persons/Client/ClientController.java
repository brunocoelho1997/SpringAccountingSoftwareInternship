package hello.Persons.Client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path="/client")
public class ClientController implements WebMvcConfigurer {

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listClients", clientRepository.findAll());
        return "Client/clients_index";
    }


    @GetMapping("/add_client")
    public String addClient(Model model) {
        model.addAttribute("client", new Client());
        return "Client/add_client";
    }


    @PostMapping("/add_submit")
    public String addSubmit(@Valid Client client, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Client/add_client";
        }

        clientRepository.save(client);

        return "redirect:/client/";
    }

    @RequestMapping("/infoclient")
    public String infoClient(@RequestParam("id") Long id, Model model) {

        Client c = clientRepository.getOne(id);

        model.addAttribute("client", c);
        return "Client/info_client";
    }

    @RequestMapping("/editclient")
    public String editClient(@RequestParam("id") Long id, Model model) {

        Client c = clientRepository.getOne(id);

        model.addAttribute("client", c);
        return "Client/edit_client";
    }

    @RequestMapping("/removeclient")
    public String removeClient(@RequestParam("id") Long id, Model model) {

        Client c = clientRepository.getOne(id);

        model.addAttribute("client", c);
        return "Client/remove_client";
    }
    @RequestMapping("/removeclientid")
    public String removeClientId(@RequestParam("id") Long id, Model model) {

        Client c = clientRepository.getOne(id);
        clientRepository.delete(c);
        return "Client/clients_index";
    }

    @PostMapping("/edit_submit")
    public String editSubmit(@Valid Client client, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit_client";
        }

        //EntityManagerFactory xptop;

        Client c = clientRepository.getOne(client.getId());
        c.setEmail(client.getEmail());
        c.setName(client.getName());
        c.setAdress(client.getAdress());
        c.setNumberPhone(client.getNumberPhone());

        clientRepository.save(c);

        return "redirect:/client/";
    }

    @GetMapping("/search_submit")
    public String searchSubmit(@RequestParam(name="value_filter", required=false) String value, Model model) {

        List<Client> clientList;

        if(value.isEmpty()){
            model.addAttribute("listClients", clientRepository.findAll());
            return "Client/clients_index";
        }

        clientList = clientRepository.findByNameContaining(value);
        if(!clientList.isEmpty()){
            model.addAttribute("listClients", clientList);
            return "Client/clients_index";
        }

        clientList = clientRepository.findByEmail(value);
        model.addAttribute("listClients", clientList);
        return "Client/clients_index";
    }

}
