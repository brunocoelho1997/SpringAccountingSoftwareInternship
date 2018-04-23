package hello.Project;

import hello.Client.Client;
import hello.Contact.Contact;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity(name = "project_client")
public class ProjectClient extends hello.Entity{


    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ProjectClient_Client"), nullable = false)
    /*
    TODO: perguntar ao hugo se é refresh
     */
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Client client;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ProjectClient_Contact"), nullable = false)
    /*
    TODO: perguntar ao hugo se é refresh
     */
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Contact contact;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ProjectClient{" +
                "client=" + client +
                ", contact=" + contact +
                '}';
    }
}
