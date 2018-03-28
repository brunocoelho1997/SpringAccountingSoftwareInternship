package hello.Transaction;

import hello.Company.Company;
import hello.Project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "projetctTransaction")
public class ProjectTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ProjectTransaction_Project"), nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;


    public ProjectTransaction() {
    }

}