package hello.Persons.Client;

import hello.Persons.Adress;
import hello.Persons.Client.Resources.Input.SaveAdressDTO;
import hello.Persons.Client.Resources.Input.SaveContactDTO;
import hello.Persons.Client.Resources.Input.SaveClientDTO;
import hello.Persons.Client.Resources.Output.InfoAdressDTO;
import hello.Persons.Client.Resources.Output.InfoClientDTO;
import hello.Persons.Client.Resources.Output.InfoContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    public List<InfoClientDTO> getAllClients(){
        List<Client> aux = clientRepository.findAll();

        List<InfoClientDTO> allClientes = new ArrayList<>();
        for(Client c : aux)
        {
            InfoClientDTO infoClientDTO = new InfoClientDTO();
            infoClientDTO.setName(c.getName());
            infoClientDTO.setNumberPhone(c.getNumberPhone());
            infoClientDTO.setRegistrationCode(c.getRegistrationCode());
            infoClientDTO.setId(c.getId());

            List<InfoContactDTO> contactDTOS = getInfoContactDTOList(c);
            infoClientDTO.setContacts(contactDTOS);
            allClientes.add(infoClientDTO);
        }
        return allClientes;
    }


    //convert list of contact to list of contactDTO
    private List<InfoContactDTO> getInfoContactDTOList(Client c)
    {
        List<InfoContactDTO> allcontacts = new ArrayList<>();
        List<Contact> aux = c.getContacts();
        for(Contact cc : aux)
        {
            InfoContactDTO contactDTO = new InfoContactDTO();
            contactDTO.setId(cc.getId());
            contactDTO.setEmail(cc.getEmail());
            contactDTO.setName(cc.getName());
            contactDTO.setNumberPhone(cc.getNumberPhone());
            InfoAdressDTO adressDTO = getInfoAdressDTO(cc);
            contactDTO.setAdressDTO(adressDTO);
            allcontacts.add(contactDTO);

        }
        return allcontacts;
    }
    private InfoAdressDTO getInfoAdressDTO(Contact cc){
        InfoAdressDTO adressDTO = new InfoAdressDTO();
        Adress adress = cc.getAdress();
        adressDTO.setZipCode(adress.getZipCode());
        adressDTO.setNumber(adress.getNumber());
        adressDTO.setCity(adress.getCity());
        adressDTO.setAdressName(adress.getAdressName());
        adressDTO.setId(adress.getId());
        return adressDTO;
    }

    public Client addClient(SaveClientDTO clientDTO) {

        //DTO to Entity
        Client client = new Client();
        client.setNumberPhone(clientDTO.getNumberPhone());
        client.setName(clientDTO.getName());
        client.setRegistrationCode(clientDTO.getRegistrationCode());

        Adress ad = new Adress();
        ad.setAdressName(clientDTO.getAdressDTO().getAdressName());
        ad.setCity(clientDTO.getAdressDTO().getCity());
        ad.setNumber(clientDTO.getAdressDTO().getNumber());
        ad.setZipCode(clientDTO.getAdressDTO().getZipCode());
        client.setAdress(ad);

        if(client.getContacts() == null)
            client.setContacts(new ArrayList<>());

        clientRepository.save(client);
        return client;
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

            List<InfoContactDTO> contactDTOS = getInfoContactDTOList(c);
            infoClientDTO.setContacts(contactDTOS);

            Adress ad = c.getAdress();
            InfoAdressDTO adressDTO = new InfoAdressDTO();
            adressDTO.setAdressName(ad.getAdressName());
            adressDTO.setCity(ad.getCity());
            adressDTO.setNumber(ad.getNumber());
            adressDTO.setZipCode(ad.getZipCode());

            infoClientDTO.setAdressDTO(adressDTO);
            return infoClientDTO;

        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }

    public Client getClient(Long id) {
        return clientRepository.getOne(id);
    }

    public void editClient(SaveClientDTO clientDTO) {
        Client c = clientRepository.getOne(clientDTO.getId());
        c.setName(clientDTO.getName());
        c.setRegistrationCode(clientDTO.getRegistrationCode());
        c.setNumberPhone(clientDTO.getNumberPhone());

        Adress ad = c.getAdress();
        ad.setAdressName(clientDTO.getAdressDTO().getAdressName());
        ad.setCity(clientDTO.getAdressDTO().getCity());
        ad.setZipCode(clientDTO.getAdressDTO().getZipCode());
        ad.setNumber(clientDTO.getAdressDTO().getNumber());

        clientRepository.save(c);
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

        Adress ad = c.getAdress();
        clientDTO.setAdressDTO(new SaveAdressDTO());
        clientDTO.getAdressDTO().setId(ad.getId());
        clientDTO.getAdressDTO().setCity(ad.getCity());
        clientDTO.getAdressDTO().setNumber(ad.getNumber());
        clientDTO.getAdressDTO().setZipCode(ad.getZipCode());
        clientDTO.getAdressDTO().setAdressName(ad.getAdressName());

        return clientDTO;
    }

    public void removeClient(Long id) {
        Client c = getClient(id);
        clientRepository.delete(c);
    }
    public void deleteClient(Client c) {
        clientRepository.delete(c);
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
        cp.setName(contactDTO.getName());

        Adress ad = new Adress();
        ad.setZipCode(contactDTO.getAdressDTO().getZipCode());
        ad.setNumber(contactDTO.getAdressDTO().getNumber());
        ad.setCity(contactDTO.getAdressDTO().getCity());
        ad.setAdressName(contactDTO.getAdressDTO().getAdressName());
        cp.setAdress(ad);

        c.getContacts().add(cp);
        clientRepository.saveAndFlush(c);
    }

    public List<Contact> getContacts(Long id){
        Client c = getClient(id);
        return c.getContacts();
    }

    public void editContact(@Valid SaveContactDTO contactDTO) {

        Client c = getClient(contactDTO.getClientId());

        Contact cp = getContactPerson(c.getId(), contactDTO.getId());
        /*
        TODO: validacoes nao?
         */
        Adress ad = cp.getAdress();
        ad.setZipCode(contactDTO.getAdressDTO().getZipCode());
        ad.setNumber(contactDTO.getAdressDTO().getNumber());
        ad.setCity(contactDTO.getAdressDTO().getCity());
        ad.setAdressName(contactDTO.getAdressDTO().getAdressName());
        cp.setAdress(ad);

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
        infoContactDTO.setEmail(cp.getEmail());
        infoContactDTO.setId(cp.getId());
        infoContactDTO.setClientId(clientId);
        infoContactDTO.setName(cp.getName());
        infoContactDTO.setNumberPhone(cp.getNumberPhone());

        Adress ad = cp.getAdress();
        InfoAdressDTO adressDTO = new InfoAdressDTO();
        adressDTO.setAdressName(ad.getAdressName());
        adressDTO.setCity(ad.getCity());
        adressDTO.setNumber(ad.getNumber());
        adressDTO.setZipCode(ad.getZipCode());
        infoContactDTO.setAdressDTO(adressDTO);
        return infoContactDTO;
    }

    public SaveContactDTO getSaveContactDTO(Long clientId, Long contactId) {
        Contact cp = getContactPerson(clientId, contactId);
        //convert entity to DTO
        SaveContactDTO contactDTO = new SaveContactDTO();
        contactDTO.setEmail(cp.getEmail());
        contactDTO.setId(cp.getId());
        contactDTO.setClientId(clientId);
        contactDTO.setName(cp.getName());
        contactDTO.setNumberPhone(cp.getNumberPhone());

        Adress ad = cp.getAdress();
        contactDTO.setAdressDTO(new SaveAdressDTO());
        contactDTO.getAdressDTO().setId(ad.getId());
        contactDTO.getAdressDTO().setAdressName(ad.getAdressName());
        contactDTO.getAdressDTO().setCity(ad.getCity());
        contactDTO.getAdressDTO().setZipCode(ad.getZipCode());
        contactDTO.getAdressDTO().setNumber(ad.getNumber());


        return contactDTO;
    }
}
