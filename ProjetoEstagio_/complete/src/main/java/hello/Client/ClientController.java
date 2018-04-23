package hello.Client;

import hello.Adress.Adress;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path="/client")
public class ClientController implements WebMvcConfigurer {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listClients", clientService.getClients());
        return "Client/index";
    }

    @GetMapping("/add_client")
    public String addClient(Model model) {

        Adress adress = new Adress();
        List<Adress> adresses = new ArrayList<>();
        adresses.add(adress);

        Client client = new Client();
        client.setAdresses(adresses);

        Contact contact = new Contact();
        contact.setAdresses(adresses);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        client.setContacts(contacts);

        model.addAttribute("client", client);

        return "Client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(Model model, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Client/add_client";
        }

        //we receive the client to get his new id to add contacts
        Client c = clientService.addClient(client);

        model.addAttribute("listClients", clientService.getClients());
        return "Client/index";
    }



    @RequestMapping("/edit_client")
    public String editClient(@RequestParam("id") Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("client", client);

        return "Client/edit_client";
    }
    @PostMapping("/edit_client")
    public String editClient(@Valid Client client, BindingResult bindingResult,Model model) {


        if (bindingResult.hasErrors()) {
            return "Client/edit_client";
        }


        clientService.editClient(client);
        return "redirect:/client/";
    }

    @RequestMapping("/remove_client")
    public String removeClient(@RequestParam("id") Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("client", client);
        return "Client/remove_client :: modal";
    }
    @DeleteMapping("/remove_client")
    public @ResponseBody String removeClient(@RequestParam("id") Long id) {
        clientService.removeClient(id);
        return "redirect:/client/";
    }



















    /*
      ---------------------------------ADRESS
     */




    @GetMapping("/get_client_adresses")
    public String getClientAdresses(@RequestParam("id_client") Long id, Model model) {
        List<Adress> adresses = clientService.getAdressesClient(id);
        Collections.reverse(adresses);
        model.addAttribute("adresses", adresses);
        return "Adress/list_adresses :: list";
    }
}
