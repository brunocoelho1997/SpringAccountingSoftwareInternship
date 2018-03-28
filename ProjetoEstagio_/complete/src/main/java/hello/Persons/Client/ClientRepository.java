package hello.Persons.Client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByUuid(UUID uuid);
    List<Client> findByNameContaining(String name);
    List<Client> findByEmail(String email);




}
