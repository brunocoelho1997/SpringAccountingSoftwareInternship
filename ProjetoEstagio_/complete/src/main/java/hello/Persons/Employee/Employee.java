package hello.Persons.Employee;

import hello.Persons.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity(name = "employee")
public class Employee extends Person {

    @NotNull
    @Column(nullable = false)
    private boolean actived;

    public Employee() {
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }
}