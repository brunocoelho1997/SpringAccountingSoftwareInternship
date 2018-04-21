package hello.Client;

import hello.Adress.Adress;
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

        Client client = new Client();
        client.setName("ola");

        List<Adress> adresses = new ArrayList<>();
        Adress adress = new Adress();
        adress.setAdressName("asdasd");
        adress.setNumber(123);
        adress.setZipCode("asdasd");
        adress.setCity("asdasd");

        adresses.add(adress);

        Adress adress1 = new Adress();
        adresses.add(adress1);
        client.setAdresses(adresses);
        model.addAttribute("client", client);
        model.addAttribute("adress", new Adress());



        return "Client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(Model model, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Client/add_client";
        }

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n" + client.getAdresses().get(0));

        //we receive the client to get his new id to add contacts
        Client c = clientService.addClient(client);

//        attributes.addAttribute("client", c);
        return "Client/index";
    }

    @PostMapping(value = "/add_adress_client/{client_id}")
    public String addAdressClient(@PathVariable Long client_id, Model model, @Valid @ModelAttribute("adress") Adress adress, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {

            return "Client/add_client";
        }

        System.out.println("\n\n\n\n\n\n\n\n\n\n CLIEND_id" + client_id);
        System.out.println("\n\n\n\n\n\n\n\n\n\n CLIEND_id" + client_id);


//        model.addAttribute("clientResource", clientResource);

        return "Client/add_client";
    }








//    @GetMapping("/add_adress_client")
//    public String addAdressClient(@RequestParam("client_id") long client_id, Model model) {
//
//        AdressResource adressResource = new AdressResource();
//        Client client = clientService.getClient(client_id);
//        adressResource.setClient(client);
//        adressResource.setAdress(new Adress());
//        model.addAttribute("adressResource", adressResource);
//
//        return "Client/add_client";
//    }














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




    @GetMapping("/get_client_adresses")
    public String getClientAdresses(@RequestParam("id_client") Long id, Model model) {
        List<Adress> adresses = clientService.getAdressesClient(id);
        Collections.reverse(adresses);
        model.addAttribute("adresses", adresses);
        return "Adress/list_adresses :: list";
    }
}
