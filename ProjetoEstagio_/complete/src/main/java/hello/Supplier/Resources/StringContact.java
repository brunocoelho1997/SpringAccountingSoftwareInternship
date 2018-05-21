package hello.Supplier.Resources;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class StringContact {

    public static final int MAX_CONTACT_LENGHT = 12;

    @NotNull
    @Length(min=1, max = MAX_CONTACT_LENGHT)
    @Column(nullable = false, length = MAX_CONTACT_LENGHT)
    String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
