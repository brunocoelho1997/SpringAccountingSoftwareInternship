package hello.Supplier;

import hello.Adress.Adress;
import hello.Person.Person;
import hello.Supplier.Resources.StringContact;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "supplier")
public class Supplier extends hello.EntityPackage.Entity{

    public static final int MAX_NAME_LENGHT = 20;

    @NotNull
    @Length(min = 1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "FK_Supplier_StringContact"))
    @Valid
    private List<StringContact> contacts;

    public List<StringContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<StringContact> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
