package hello.Type;

import hello.EntityPackage.Entity;
import hello.Enums.Category;
import hello.SubType.SubType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity(name="type")
public class Type extends Entity {


    public static final int MAX_NAME_LENGHT = 20;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @NotNull
    @Length(min = 1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Type_SubType"))
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private SubType subType;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "Type{" +
                ", category=" + category +
                ", name='" + name + '\'' +
                '}';
    }
}
