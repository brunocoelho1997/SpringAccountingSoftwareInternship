package hello.Persons.Client;

import hello.Persons.Person;

import javax.persistence.Entity;

@Entity(name = "contactPerson")
public class ContactPerson extends Person {

    public ContactPerson() {
    }

}
