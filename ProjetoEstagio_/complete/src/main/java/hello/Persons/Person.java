package hello.Persons;

import hello.Entity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Person extends Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_ADRESS_LENGHT = 255;
    public static final int MAX_EMAIL_LENGHT = 255;
    public static final int MAX_NUMBERPHONE_LENGHT = 12;


    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @NotNull
    @Email
    @Length(min=1, max = MAX_EMAIL_LENGHT)
    @Column(nullable = false, length = MAX_EMAIL_LENGHT)
    private String email;

    @NotNull
    @Length(min=1, max = MAX_ADRESS_LENGHT)
    @Column(nullable = false, length = MAX_ADRESS_LENGHT)
    private String adress;

    @NotNull
    @Length(min=1, max = MAX_NUMBERPHONE_LENGHT)
    @Column(nullable = false, length = MAX_NUMBERPHONE_LENGHT)
    private String numberPhone;

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", adress='" + adress + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                '}';
    }
}

