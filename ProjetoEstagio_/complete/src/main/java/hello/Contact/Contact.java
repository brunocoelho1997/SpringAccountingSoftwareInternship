package hello.Contact;

import hello.Person.Person;
import hello.PostContact.PostContact;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "contact")
public class Contact extends Person {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Contact_PostContact"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private PostContact postContact;

    public Contact() {
    }

    public PostContact getPostContact() {
        return postContact;
    }

    public void setPostContact(PostContact postContact) {
        this.postContact = postContact;
    }
}
