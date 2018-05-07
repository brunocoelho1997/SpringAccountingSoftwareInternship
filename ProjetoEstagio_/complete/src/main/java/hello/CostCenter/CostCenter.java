package hello.CostCenter;

import hello.EntityPackage.Entity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@javax.persistence.Entity(name="cost_center")
public class CostCenter extends Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_DESCRIPTION_LENGHT = 255;


    @NotNull
    @Size(min=1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @Size(max = MAX_DESCRIPTION_LENGHT)
    @Column(length = MAX_DESCRIPTION_LENGHT)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CostCenter{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
