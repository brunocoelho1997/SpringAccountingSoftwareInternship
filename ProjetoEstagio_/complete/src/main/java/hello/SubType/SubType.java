package hello.SubType;

import hello.EntityPackage.Entity;
import hello.Type.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity(name="subtype")
public class SubType extends Entity {

    public static final int MAX_NAME_LENGHT = 20;

    @NotNull
    @Length(max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @JoinColumn(foreignKey = @ForeignKey(name = "FK_SubType_Type"))
    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubType{" +
                "name='" + name + '\'' +
                ", id=" + getId()+

                '}';
    }
}
