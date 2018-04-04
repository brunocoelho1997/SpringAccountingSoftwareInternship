package hello.Persons.Client.Resources.Input;

import hello.Persons.Client.ContactPerson;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateContactDTO {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_ADRESS_LENGHT = 255;
    public static final int MAX_EMAIL_LENGHT = 255;
    public static final int MAX_NUMBERPHONE_LENGHT = 12;

    private Long clientId;
    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    private String name;

    @NotNull
    @Email
    @Length(min=1, max = MAX_EMAIL_LENGHT)
    private String email;

    @NotNull
    @Length(min=1, max = MAX_ADRESS_LENGHT)
    private String adress;

    @NotNull
    @Length(min=1, max = MAX_NUMBERPHONE_LENGHT)
    private String numberPhone;

    private List<ContactPerson> contacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ContactPerson> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactPerson> contacts) {
        this.contacts = contacts;
    }
}
