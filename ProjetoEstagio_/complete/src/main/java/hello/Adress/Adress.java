package hello.Adress;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Embeddable
public class Adress {

    public static final int MAX_NUMBER_SIZE = 4;
    public static final int MAX_CITY_LENGHT = 12;
    public static final int MAX_ADRESSNAME_LENGHT = 35;
    public static final int MAX_ZIPCODE_LENGHT = 8;



    @Length(min=1, max = MAX_CITY_LENGHT)
    @Column(length = MAX_CITY_LENGHT)
    private String city;

    @NotNull
    @Length(min=1, max = MAX_ADRESSNAME_LENGHT)
    @Column(nullable = false, length = MAX_ADRESSNAME_LENGHT)
    private String adressName;

    @NotNull
    @Digits(integer=MAX_NUMBER_SIZE,fraction=0)
    @Column(nullable = false, length = MAX_NUMBER_SIZE)
    private int number;

    @NotNull
    @Length(min=1, max = MAX_ZIPCODE_LENGHT)
    @Column(nullable = false, length = MAX_ZIPCODE_LENGHT)
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

    @Override
    public String toString() {
        return "Adress{" +
                "city='" + city + '\'' +
                ", adressName='" + adressName + '\'' +
                ", number=" + number +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
