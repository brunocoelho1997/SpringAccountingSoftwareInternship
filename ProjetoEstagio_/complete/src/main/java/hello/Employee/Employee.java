package hello.Employee;

import hello.Person.Person;
import hello.PostEmployee.PostEmployee;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "employee")
public class Employee extends Person{

    @NotNull
    @Column(nullable = false)
    private boolean actived;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Employee_PostEmployee"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private PostEmployee postEmployee;

    public PostEmployee getPostEmployee() {
        return postEmployee;
    }

    public void setPostEmployee(PostEmployee postEmployee) {
        this.postEmployee = postEmployee;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }


}
