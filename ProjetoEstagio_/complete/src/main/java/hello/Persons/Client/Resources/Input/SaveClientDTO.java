package hello.Persons.Client.Resources.Input;

import hello.Persons.Client.Contact;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SaveClientDTO {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_REGISTRATIONCODE_LENGHT = 30;
    public static final int MAX_NUMBERPHONE_LENGHT = 12;
    public static final int MAX_NUMBER_SIZE = 4;
    public static final int MAX_CITY_LENGHT = 12;
    public static final int MAX_ADRESS_LENGHT = 255;
    public static final int MAX_ZIPCODE_LENGHT = 8;

    //Just to show the Identification
    private Long id;

    @NotNull
    @Length(min=1, max = MAX_REGISTRATIONCODE_LENGHT)
    private String registrationCode;

    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    private String name;

    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    private String numberPhone;

    @NotNull
    private SaveAdressDTO adressDTO;

    private List<Contact> contacts;

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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public SaveAdressDTO getAdressDTO() {
        return adressDTO;
    }

    public void setAdressDTO(SaveAdressDTO adressDTO) {
        this.adressDTO = adressDTO;
    }
}
