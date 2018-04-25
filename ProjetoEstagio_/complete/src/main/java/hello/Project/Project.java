package hello.Project;

import hello.Client.Client;
import hello.Contact.Contact;
import hello.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@javax.persistence.Entity(name = "project")
public class Project extends Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_DESCRIPTION_LENGHT = 255;
    public static final int MAX_SCOPE_LENGHT = 255;


    @NotNull
    @Size(min=1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    /*
    TODO: penso que isto nao deveria ser obrigatorio. Ver requisitos... Isto e mts outros devem estar errados...
     */
    @Size(max = MAX_DESCRIPTION_LENGHT)
    @Column(length = MAX_DESCRIPTION_LENGHT)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate initialDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate finalDate;

    @NotNull
    @Column(nullable = false)
    private float balance;

    @Size(max = MAX_SCOPE_LENGHT)
    @Column(length = MAX_SCOPE_LENGHT)
    private String scope;

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



    /*
        TODO: No DC nao esta' representado... mas a meu ver a ligacao de Projeto para ProjectTransaction devia ser bidirecional. O mesmo acontece parra EmployeeTransaction
        o projeto tbm deve ter uma var de cliente. Falta a direcao da seta
     */

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

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

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
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", balance=" + balance +
                ", scope='" + scope + '\'' +
                ", client=" + client +
                ", contact=" + contact +
                '}';
    }
}