package hello.ProjectTransaction;

import hello.Project.Project;
import hello.Transaction.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "projectTransaction")
public class ProjectTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ProjectTransaction_Project"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Project project;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return super.toString() + "\nProjectTransaction{" +
                "project=" + project +
                '}';
    }
}