package hello.Contact;

import hello.Person.Person;

import javax.persistence.Entity;

@Entity(name = "contact")
public class Contact extends Person {

    public Contact() {
    }

}
