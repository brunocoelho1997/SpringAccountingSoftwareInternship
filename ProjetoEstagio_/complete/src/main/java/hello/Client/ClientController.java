package hello.Client;

import hello.Adress.Adress;
import hello.Contact.Contact;
import hello.Pager;
import hello.PostContact.PostContactService;
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
@RequestMapping(path="/client")
public class ClientController implements WebMvcConfigurer {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PostContactService postContactService;

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("listClients", clientService.getClients());
//        return "Client/index";
//    }

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam(name="value_filter", required=false) String value)
    {
        ModelAndView modelAndView = new ModelAndView("Client/index");

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);


        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Client> clients= clientService.findAllPageable(PageRequest.of(evalPage, evalPageSize), value);

        Pager pager = new Pager(clients.getTotalPages(), clients.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("listEntitys", clients);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }

    @RequestMapping("/info_client")
    public String infoClient(@RequestParam("id") Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("client", client);

        return "Client/info_client";
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
        model.addAttribute("listPostContact", postContactService.getAll());

        return "Client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(Model model, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listPostContact", postContactService.getAll());
            return "Client/add_client";
        }

        clientService.addClient(client);

        model.addAttribute("listClients", clientService.getClientsActived());

        return "redirect:/client/";
    }

    @RequestMapping("/edit_client")
    public String editClient(@RequestParam("id") Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("client", client);
        model.addAttribute("listPostContact", postContactService.getAll());


        return "Client/edit_client";
    }
    @PostMapping("/edit_client")
    public String editClient(@Valid Client client, BindingResult bindingResult,Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("listPostContact", postContactService.getAll());
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

    @RequestMapping("/get_contacts")
    public String getContacts(@RequestParam("id") Long id, Model model) {
        Client client = clientService.getClient(id);
        model.addAttribute("listContacts", client.getContacts());
        return "Client/contacts_list :: options";
    }
}
