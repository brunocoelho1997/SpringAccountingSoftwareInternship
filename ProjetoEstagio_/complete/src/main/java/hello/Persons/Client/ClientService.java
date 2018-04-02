package hello.Persons.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public void addClient(Client client) {

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
}
