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
//        ContactPerson temp = new ContactPerson();
//        temp.setName("Almerinda");
//        temp.setAdress("asd");
//        temp.setNumberPhone("123123");
//        temp.setEmail("asdsad@gmail.com");
        if(client.getContactPerson() == null)
            client.setContactPerson(new ArrayList<>());
//        client.getContactPerson().add(temp);
        
        
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

    public ContactPerson getContactPerson(Long clientId, Long contactId)
    {
        Client c = getClient(clientId);
        for(ContactPerson cc : c.getContactPerson())
        {
            if(cc.getId().equals(contactId))
                return cc;
        }
        return null;
    }

    public void addContact(@Valid CreateContactDTO contactDTO) {

        Client c = getClient(contactDTO.getClientId());
        ContactPerson cp = new ContactPerson();

        cp.setEmail(contactDTO.getEmail());
        cp.setNumberPhone(contactDTO.getNumberPhone());
        cp.setAdress(contactDTO.getAdress());
        cp.setName(contactDTO.getName());
        c.getContactPerson().add(cp);
        clientRepository.saveAndFlush(c);
    }
}
