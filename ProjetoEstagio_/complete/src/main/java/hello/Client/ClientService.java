package hello.Client;

import hello.Adress.Adress;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    public List<Client> getClients(){
        return repository.findAll();
    }

    public Client addClient(Client client)
    {
        return repository.save(client);
    }

    public Client getClient(Long id) {
        return repository.findById((long)id);
    }

    public void editClient(Client editedClient){
        Client client = getClient(editedClient.getId());
        client.setAdresses(editedClient.getAdresses());
        client.setName(editedClient.getName());
        client.setNumberPhone(editedClient.getNumberPhone());
        client.setRegistrationCode(editedClient.getRegistrationCode());


        client.setAdresses(editedClient.getAdresses());

        client.getContacts().clear();
        for(Contact contact : editedClient.getContacts())
            client.getContacts().add(contact);
        repository.save(client);
    }

    public void addAdress(@Valid long client_id, @Valid Adress adress) {
        Client client = getClient(client_id);
        client.getAdresses().add(adress);
        repository.save(client);
    }

    public List<Adress> getAdressesClient(Long id) {
        Client client = getClient(id);
        return client.getAdresses();
    }

    public void removeClient(Long id) {
        Client c = repository.getOne(id);
        repository.delete(c);
    }

    public Contact getContact(Long id, Long id1) {
        Client c = repository.getOne(id);
        for(Contact contact : c.getContacts())
        {
            if(contact.getId().equals(id1))
                return contact;
        }
        return null;
    }

    public List<Client> filterClients(String value) {

        List<Client> clientList = new ArrayList<>();

        if(value.isEmpty()){
            return repository.findAll();
        }

        clientList = repository.findByNameContaining(value);
        if(!clientList.isEmpty()){
            return clientList;
        }

        return clientList;
    }
}
