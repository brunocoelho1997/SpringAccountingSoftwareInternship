package hello.Persons.Client.Resources.Output;

import hello.Persons.Adress;

public class InfoContactDTO {


    private Long id;
    private Long clientId; //used by the "go back" button
    private String name;
    private String email;
    private InfoAdressDTO adressDTO;
    private String numberPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public InfoAdressDTO getAdressDTO() {
        return adressDTO;
    }

    public void setAdressDTO(InfoAdressDTO adressDTO) {
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

    @Override
    public String toString() {
        return "InfoContactDTO{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                '}';
    }
}
