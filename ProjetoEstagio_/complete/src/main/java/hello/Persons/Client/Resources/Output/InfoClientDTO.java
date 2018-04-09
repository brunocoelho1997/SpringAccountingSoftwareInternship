package hello.Persons.Client.Resources.Output;


import hello.Persons.Client.Contact;

import java.util.List;

public class InfoClientDTO {

    private Long id;
    private String registrationCode;
    private String name;
    private String numberPhone;

    /*
    TODO: esta' incorreto. Esta' a passar a entidade mesmo...
     */
    private List<InfoContactDTO> contacts;

    private InfoAdressDTO adressDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<InfoContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<InfoContactDTO> contacts) {
        this.contacts = contacts;
    }

    public InfoAdressDTO getAdressDTO() {
        return adressDTO;
    }

    public void setAdressDTO(InfoAdressDTO adressDTO) {
        this.adressDTO = adressDTO;
    }
}
