package hello.Supplier;

import hello.Adress.Adress;
import hello.Person.Person;
import hello.Supplier.Resources.StringContact;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.validation.Valid;
import java.util.List;

@Entity(name = "supplier")
public class Supplier extends Person{

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
}
