package hello.Persons.Client.Resources.Output;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class InfoAdressDTO {

    private long id;
    private String city;
    private String adressName;
    private int number;
    private String zipCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdressName() {
        return adressName;
    }

    public void setAdressName(String adressName) {
        this.adressName = adressName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
