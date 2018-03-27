package hello.Persons.Client;

import hello.Persons.Person;

import javax.persistence.Entity;

@Entity(name = "client")
public class Client extends Person {

    public Client() {
    }

}
