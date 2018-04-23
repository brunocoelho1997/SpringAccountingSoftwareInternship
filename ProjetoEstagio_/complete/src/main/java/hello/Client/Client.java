package hello.Client;

import hello.Adress.Adress;
import hello.Contact.Contact;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "client")
public class Client extends hello.Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_REGISTRATIONCODE_LENGHT = 30;
    public static final int MAX_NUMBERPHONE_LENGHT = 12;


    @NotNull
    @Length(min = 1, max = MAX_REGISTRATIONCODE_LENGHT)
    @Column(nullable = false, length = MAX_REGISTRATIONCODE_LENGHT)
    private String registrationCode;

    @NotNull
    @Length(min = 1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @NotNull
    @Length(min = 1, max = MAX_NUMBERPHONE_LENGHT)
    @Column(nullable = false, length = MAX_NUMBERPHONE_LENGHT)
    private String numberPhone;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Client_Contact"), nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Valid
    private List<Contact> contacts;


    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "FK_Client_Adress"))
    @Valid
    private List<Adress> adresses;

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public List<Adress> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adress> adresses) {
        this.adresses = adresses;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "registrationCode='" + registrationCode + '\'' +
                ", name='" + name + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", contacts=" + contacts +
                ", adresses=" + adresses +
                '}';
    }
}