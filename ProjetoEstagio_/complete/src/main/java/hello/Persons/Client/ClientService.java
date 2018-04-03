package hello.Persons.Client;

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

    public void addClient(Client client) {

        ContactPerson temp = new ContactPerson();
        temp.setName("Almerinda");
        temp.setAdress("asdsad");
        temp.setNumberPhone("123123");

        if(client.getContactPerson() == null)
            client.setContactPerson(new ArrayList<>());
        client.getContactPerson().add(temp);
        
        clientRepository.save(client);
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
}
