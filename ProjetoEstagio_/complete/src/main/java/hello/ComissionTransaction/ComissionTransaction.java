package hello.ComissionTransaction;

import hello.Client.Client;
import hello.Project.Project;
import hello.Transaction.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "comissionTransaction")
public class ComissionTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ComissionTransaction_Project"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Project project;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ClientTransaction_Client"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Client client;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return super.toString() + "\nProjectTransaction{" +
                "project=" + project +
                '}';
    }
}
