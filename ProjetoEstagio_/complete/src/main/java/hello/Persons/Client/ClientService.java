package hello.Persons.Client;

import hello.Persons.Client.Resources.Input.CreateClientDTO;
import hello.Persons.Client.Resources.Input.CreateContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client addClient(CreateClientDTO clientDTO) {

        //DTO to Entity
        Client client = new Client();
        client.setNumberPhone(clientDTO.getNumberPhone());
        client.setName(clientDTO.getName());
        client.setRegistrationCode(clientDTO.getRegistrationCode());
        
        
        //para apagar
//        Contact temp = new Contact();
//        temp.setName("Almerinda");
//        temp.setAdress("asd");
//        temp.setNumberPhone("123123");
//        temp.setEmail("asdsad@gmail.com");
        if(client.getContacts() == null)
            client.setContacts(new ArrayList<>());
//        client.getContacts().add(temp);
        
        
        clientRepository.save(client);

        return client;
    }

    public Client getClient(Long id) {
        return clientRepository.getOne(id);
    }

    public void deleteClient(Client c) {
        clientRepository.delete(c);
    }

    public void editClient(@Valid Client client) {
        Client c = clientRepository.getOne(client.getId());
//        c.setEmail(client.getEmail());
//        c.setName(client.getName());
//        c.setAdress(client.getAdress());
        c.setNumberPhone(client.getNumberPhone());
    }

    public Contact getContactPerson(Long clientId, Long contactId)
    {
        Client c = getClient(clientId);
        for(Contact cc : c.getContacts())
        {
            if(cc.getId().equals(contactId))
                return cc;
        }
        return null;
    }

    public void addContact(CreateContactDTO contactDTO) {

        Client c = getClient(contactDTO.getClientId());
        Contact cp = new Contact();

        cp.setEmail(contactDTO.getEmail());
        cp.setNumberPhone(contactDTO.getNumberPhone());
        cp.setAdress(contactDTO.getAdress());
        cp.setName(contactDTO.getName());
        c.getContacts().add(cp);
        clientRepository.saveAndFlush(c);
    }

    public List<Contact> getContacts(Long id){
        Client c = getClient(id);
        return c.getContacts();
    }
}
