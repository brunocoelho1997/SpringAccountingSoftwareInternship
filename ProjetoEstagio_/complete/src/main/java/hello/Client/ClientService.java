package hello.Client;

import hello.Adress.Adress;
import hello.Contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public List<Client> getClientsActived(){
        return repository.findAllByActived(true);
    }

    public Page<Client> findAllPageable(Pageable pageable, String value) {


        //could receive params to filter de list
        if(value!= null)
            return filterClients(pageable, value);

        else
            return repository.findAllByActived(pageable, true);

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


    public void removeClient(Long id) {
        Client c = repository.getOne(id);
        c.setActived(false);
        repository.save(c);
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

    public Page<Client> filterClients(Pageable pageable, String value) {

        Page<Client> page = null;


        if(value.isEmpty())
            return repository.findAllByActived(pageable, true);

        Specification<Client> specFilter;
        specFilter= ClientSpecifications.filter(value);

        page = repository.findAll(specFilter, pageable);

        return page;
    }
}
