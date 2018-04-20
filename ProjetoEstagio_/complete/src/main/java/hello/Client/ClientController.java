package hello.Client;

import hello.Adress.Adress;
import hello.Adress.AdressResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        model.addAttribute("client", new Client());
        return "Client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(Model model, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Client/add_client";
        }
        //we receive the client to get his new id to add contacts
        Client c = clientService.addClient(client);
        attributes.addAttribute("id", c.getId());
        return "redirect:/client/edit_client";
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
//            if(saveClientDTO.getContacts() == null)
//                System.out.println("\n\n\n\n\n\n\n\n" + "É NULL\n");

//            funciona
//            Client c = clientService.getClient(saveClientDTO.getId());
//            saveClientDTO.setContacts(contactService.getSaveContactDTOList(c));

            /*
            TODO: sera' esta a melhor maneira de fazer?
             */

//            SaveClientDTO aux= clientService.getSaveClientDTO(saveClientDTO.getId());
//            saveClientDTO.setContacts(aux.getContacts());
//            saveClientDTO.setAdressDTO(aux.getAdressDTO());

            return "Client/edit_client";
        }


        clientService.editClient(client);
        return "redirect:/client/";
    }



    /*
      ---------------------------------ADRESS
     */

    @GetMapping("/add_adress_client")
    public String addAdressClient(@RequestParam("client_id") Long client_id, Model model) {

        AdressResource adressResource = new AdressResource();
        adressResource.setClientId(client_id);
        model.addAttribute("adressResource", adressResource);

        return "Adress/add_adress_client";
    }

    @PostMapping(value = "/add_adress_client")
    public String addAdressClient(Model model, @Valid @ModelAttribute("adress") AdressResource adressResource, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Adress/add_adress_client";
        }
        Adress adress = adressResource.convertToAdress();
        clientService.addAdress(adressResource.getClientId(), adress);

        AdressResource aux = new AdressResource();
        aux.setClientId(adressResource.getClientId());
        attributes.addAttribute("client_id", adressResource.getClientId());

        return "redirect:/client/add_adress_client";
    }

    @GetMapping("/get_client_adresses")
    public String getClientAdresses(@RequestParam("id_client") Long id, Model model) {
        List<Adress> adresses = clientService.getAdressesClient(id);
        Collections.reverse(adresses);
        model.addAttribute("adresses", adresses);
        return "Adress/list_adresses :: list";
    }
}
