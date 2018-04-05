package hello.Persons.Client;

import hello.Persons.Person;

import javax.persistence.Entity;

@Entity(name = "contactPerson")
public class Contact extends Person {

    public Contact() {
    }

}
