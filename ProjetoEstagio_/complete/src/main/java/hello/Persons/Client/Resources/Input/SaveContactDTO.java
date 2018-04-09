package hello.Persons.Client.Resources.Input;

import hello.Persons.Adress;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SaveContactDTO {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_EMAIL_LENGHT = 255;
    public static final int MAX_NUMBERPHONE_LENGHT = 12;
    public static final int MAX_NUMBER_SIZE = 4;
    public static final int MAX_CITY_LENGHT = 12;
    public static final int MAX_ADRESS_LENGHT = 255;
    public static final int MAX_ZIPCODE_LENGHT = 8;
    //Just to show the Identification
    private Long id;

    @NotNull
    private Long clientId;
    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    private String name;

    @NotNull
    @Email
    @Length(min=1, max = MAX_EMAIL_LENGHT)
    private String email;

    @NotNull
    @Length(min=1, max = MAX_NUMBERPHONE_LENGHT)
    private String numberPhone;

    @NotNull
    @Valid
    private SaveAdressDTO adressDTO;

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

    public SaveAdressDTO getAdressDTO() {
        return adressDTO;
    }

    public void setAdressDTO(SaveAdressDTO adressDTO) {
        this.adressDTO = adressDTO;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
