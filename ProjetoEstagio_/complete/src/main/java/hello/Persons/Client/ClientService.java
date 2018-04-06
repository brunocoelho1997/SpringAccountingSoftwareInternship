package hello.Persons.Client;

import hello.Persons.Client.Resources.Input.SaveContactDTO;
import hello.Persons.Client.Resources.Input.SaveClientDTO;
import hello.Persons.Client.Resources.Output.InfoClientDTO;
import hello.Persons.Client.Resources.Output.InfoContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    List<InfoClientDTO> getAllClients(){
        List<Client> aux = clientRepository.findAll();
        List<InfoClientDTO> allClientes = new ArrayList<>();
        for(Client c : aux)
        {
            InfoClientDTO infoClientDTO = new InfoClientDTO();
            infoClientDTO.setName(c.getName());
            infoClientDTO.setNumberPhone(c.getNumberPhone());
            infoClientDTO.setRegistrationCode(c.getRegistrationCode());
            infoClientDTO.setId(c.getId());
            infoClientDTO.setContacts(c.getContacts());
            allClientes.add(infoClientDTO);
        }
        return allClientes;
    }

    public Client addClient(SaveClientDTO clientDTO) {

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

    public void editClient(SaveClientDTO clientDTO) {
        Client c = clientRepository.getOne(clientDTO.getId());
        c.setName(clientDTO.getName());
        c.setRegistrationCode(clientDTO.getRegistrationCode());
        c.setNumberPhone(clientDTO.getNumberPhone());
        clientRepository.save(c);
    }
    public void removeClient(Long id) {
        Client c = getClient(id);
        clientRepository.delete(c);
    }

    public InfoClientDTO getInfoClientDTO(Long id) {

        try
        {
            Client c = getClient(id);
            //convert entity to DTO
            InfoClientDTO infoClientDTO = new InfoClientDTO();
            infoClientDTO.setName(c.getName());
            infoClientDTO.setNumberPhone(c.getNumberPhone());
            infoClientDTO.setRegistrationCode(c.getRegistrationCode());
            infoClientDTO.setId(c.getId());
            infoClientDTO.setContacts(c.getContacts());

            return infoClientDTO;

        }catch (EntityNotFoundException ex)
        {
            return new InfoClientDTO();
        }
    }

    public SaveClientDTO getSaveClientDTO(Long id) {
        Client c = getClient(id);
        //client to DTO
        SaveClientDTO clientDTO = new SaveClientDTO();
        clientDTO.setId(c.getId());
        clientDTO.setName(c.getName());
        clientDTO.setNumberPhone(c.getNumberPhone());
        clientDTO.setRegistrationCode(c.getRegistrationCode());
        clientDTO.setContacts(c.getContacts());
        return clientDTO;
    }

    /*
    ------------------------------------------
    CONTACTS
     */

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

    public void addContact(SaveContactDTO contactDTO) {

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


    public InfoContactDTO getInfoContactDTO(Long clientId, Long contactId) {
        Contact cp = getContactPerson(clientId, contactId);
        //convert entity to DTO
        InfoContactDTO infoContactDTO = new InfoContactDTO();
        infoContactDTO.setAdress(cp.getAdress());
        infoContactDTO.setEmail(cp.getEmail());
        infoContactDTO.setId(cp.getId());
        infoContactDTO.setClientId(clientId);
        infoContactDTO.setName(cp.getName());
        infoContactDTO.setNumberPhone(cp.getNumberPhone());
        return infoContactDTO;
    }

    public SaveContactDTO getSaveContactDTO(Long clientId, Long contactId) {
        Contact cp = getContactPerson(clientId, contactId);
        //convert entity to DTO
        SaveContactDTO contactDTO = new SaveContactDTO();
        contactDTO.setAdress(cp.getAdress());
        contactDTO.setEmail(cp.getEmail());
        contactDTO.setId(cp.getId());
        contactDTO.setClientId(clientId);
        contactDTO.setName(cp.getName());
        contactDTO.setNumberPhone(cp.getNumberPhone());
        return contactDTO;
    }
}
