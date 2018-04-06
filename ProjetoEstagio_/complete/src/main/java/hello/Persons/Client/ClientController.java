package hello.Persons.Client;


import hello.Persons.Client.Resources.Input.SaveContactDTO;
import hello.Persons.Client.Resources.Input.SaveClientDTO;
import hello.Persons.Client.Resources.Output.InfoClientDTO;
import hello.Persons.Client.Resources.Output.InfoContactDTO;
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
public class ClientController implements WebMvcConfigurer{

    @Autowired
    private ClientService clientService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listClients", clientService.getAllClients());
        return "Client/clients_index";
    }


    @GetMapping("/add_client")
    public String addClient(Model model) {
        model.addAttribute("clientDTO", new SaveClientDTO());
        return "Client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(Model model, @Valid @ModelAttribute("clientDTO") SaveClientDTO clientDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Client/add_client";
        }
        //we receive the client to get is new id to add contacts
        Client c = clientService.addClient(clientDTO);
        attributes.addAttribute("client_id", c.getId());
        return "redirect:/client/add_contact";
    }

    @RequestMapping("/info_client")
    public String infoClient(@RequestParam("id") Long id, Model model) {
        /*
        TODO: validacoes aqui feitas.... Mas falta no resto dos controladores
         */
        InfoClientDTO infoClientDTO = clientService.getInfoClientDTO(id);
        model.addAttribute("clientDTO", infoClientDTO);
        return "Client/info_client";
    }

    @RequestMapping("/edit_client")
    public String editClient(@RequestParam("id") Long id, Model model) {
        SaveClientDTO clientDTO = clientService.getSaveClientDTO(id);
        model.addAttribute("clientDTO", clientDTO);
        return "Client/edit_client";
    }

    @PostMapping("/edit_client")
    public String editClient(@Valid SaveClientDTO clientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_client";
        }
        clientService.editClient(clientDTO);
        return "redirect:/client/";
    }

    @RequestMapping("/remove_client")
    public String removeClient(@RequestParam("id") Long id, Model model) {
        SaveClientDTO clientDTO = clientService.getSaveClientDTO(id);
        model.addAttribute("clientDTO", clientDTO);
        return "Client/remove_client :: modal";
    }
    @DeleteMapping("/remove_client")
    public @ResponseBody String removeClient(@RequestParam("id") Long id) {
        clientService.removeClient(id);
        return "redirect:/client/";
    }
    /*
        ----------------------------------------
        CONTACTS
     */
    @GetMapping("/add_contact")
    public String addContact(@RequestParam("client_id") Long clientId, Model model) {

        SaveContactDTO contactDTO = new SaveContactDTO();
        contactDTO.setClientId(clientId);

        model.addAttribute("contactDTO", contactDTO);
        return "Client/Contact/add_contact";
    }
    @PostMapping("/add_contact")
    public String addContact(Model model, @Valid @ModelAttribute("contactDTO") SaveContactDTO contactDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "Client/Contact/add_contact";
        }
        clientService.addContact(contactDTO);
        attributes.addAttribute("client_id", contactDTO.getClientId());
        return "redirect:/client/add_contact";
    }

    @GetMapping("/get_contacts")
    public String getContacts(@RequestParam("id") Long id, Model model) {
        List<Contact>contactList = clientService.getContacts(id);
        Collections.reverse(contactList);
        model.addAttribute("contactList", contactList);
        return "Client/Contact/list_contacts :: list";
    }

    @RequestMapping("/info_contact_client")
    public String infoContactClient(@RequestParam("id_client") Long clientId,@RequestParam("id_contact") Long contactId, Model model) {

        InfoContactDTO infoContactDTO = clientService.getInfoContactDTO(clientId, contactId);
        model.addAttribute("contactDTO", infoContactDTO);
        return "Client/Contact/info_contact :: modal";
    }

    @RequestMapping("/edit_contact")
    public String editContact(@RequestParam("id_client") Long clientId,@RequestParam("id_contact") Long contactId, Model model) {

        SaveContactDTO contactDTO = clientService.getSaveContactDTO(clientId, contactId);

        model.addAttribute("contactDTO", contactDTO);
        return "Client/Contact/edit_contact";
    }
    @PostMapping("/edit_contact")
    public String editContact(@Valid InfoContactDTO contactDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            return "edit_contact";
        }
        clientService.editContact(contactDTO);

        redirectAttrs.addAttribute("id", contactDTO.getClientId());

        return "redirect:/client/edit_client";
    }

    @RequestMapping("/remove_contact")
    public String removeContact(@RequestParam("id_client") Long clientId,@RequestParam("id_contact") Long contactId, Model model) {
        SaveContactDTO contactDTO = clientService.getSaveContactDTO(clientId, contactId);
        model.addAttribute("contactDTO", contactDTO);
        return "Client/Contact/remove_contact :: modal";
    }

    @DeleteMapping("/remove_contact")
    public String removeContact(@RequestParam("id_client") Long clientId,@RequestParam("id_contact") Long contactId, RedirectAttributes redirectAttrs) {
        clientService.removeContact(clientId, contactId);
        redirectAttrs.addAttribute("id", clientId);
        return "redirect:/client/edit_client";
    }

//
//    @GetMapping("/search_submit")
//    public String searchSubmit(@RequestParam(name="value_filter", required=false) String value, Model model) {
//
//        List<Client> clientList;
//
//        if(value.isEmpty()){
//            model.addAttribute("listClients", clientRepository.findAll());
//            return "Client/clients_index";
//        }
//
//        clientList = clientRepository.findByNameContaining(value);
//        if(!clientList.isEmpty()){
//            model.addAttribute("listClients", clientList);
//            return "Client/clients_index";
//        }
//
//        clientList = clientRepository.findByEmail(value);
//        model.addAttribute("listClients", clientList);
//        return "Client/clients_index";
//    }




}
