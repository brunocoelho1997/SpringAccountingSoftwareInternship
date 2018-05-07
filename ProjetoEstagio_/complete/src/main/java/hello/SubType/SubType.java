package hello.SubType;

import hello.EntityPackage.Entity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity(name="subtype")
public class SubType extends Entity {

    public static final int MAX_NAME_LENGHT = 20;

    @NotNull
    @Length(min=1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubType{" +
                "name='" + name + '\'' +
                '}';
    }
}
