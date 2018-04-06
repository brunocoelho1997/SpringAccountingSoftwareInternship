package hello.Persons.Client;

import hello.Persons.Client.Resources.Input.CreateClientDTO;
import hello.Persons.Client.Resources.Input.CreateContactDTO;
import hello.Persons.Client.Resources.Input.EditClientDTO;
import hello.Persons.Client.Resources.Output.InfoContactDTO;
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

        if(client.getContacts() == null)
            client.setContacts(new ArrayList<>());

        clientRepository.save(client);
        return client;
    }

    public Client getClient(Long id) {
        return clientRepository.getOne(id);
    }

    public void deleteClient(Client c) {
        clientRepository.delete(c);
    }

    public void editClient(EditClientDTO clientDTO) {
        Client c = clientRepository.getOne(clientDTO.getId());
        c.setName(clientDTO.getName());
        c.setRegistrationCode(clientDTO.getRegistrationCode());
        c.setNumberPhone(clientDTO.getNumberPhone());
        clientRepository.save(c);
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


    public void editContact(@Valid InfoContactDTO contactDTO) {

        Client c = getClient(contactDTO.getClientId());

        Contact cp = getContactPerson(c.getId(), contactDTO.getId());
        /*
        TODO: validacoes nao?
         */
        cp.setAdress(contactDTO.getAdress());
        cp.setEmail(contactDTO.getEmail());
        cp.setName(contactDTO.getName());
        cp.setNumberPhone(contactDTO.getNumberPhone());


        clientRepository.save(c);
    }

    public void removeContact(Long clientId, Long contactId) {

        Client c = getClient(clientId);
        Contact cp = getContactPerson(clientId, contactId);
        c.getContacts().remove(cp);
        clientRepository.save(c);
    }
}
