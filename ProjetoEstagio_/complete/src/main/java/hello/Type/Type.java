package hello.Type;

import hello.Enums.Genre;
import hello.Entity;
import hello.Enums.Category;
import hello.SubType.SubType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    //    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Transaction_Type"), nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SubType> subTypeList;

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

    public List<SubType> getSubTypeList() {
        return subTypeList;
    }

    public void setSubTypeList(List<SubType> subTypeList) {
        this.subTypeList = subTypeList;
    }

    @Override
    public String toString() {
        return "Type{" +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", subTypeList=" + subTypeList +
                '}';
    }
}
