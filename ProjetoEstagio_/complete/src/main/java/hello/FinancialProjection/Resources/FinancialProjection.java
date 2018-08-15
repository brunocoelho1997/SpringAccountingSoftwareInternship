package hello.FinancialProjection.Resources;

import hello.Client.Client;
import hello.Currency.Currency;
import hello.Employee.Employee;
import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.SheetTransaction.Resources.HoursPerProject;
import hello.Supplier.Supplier;
import hello.Transaction.Transaction;
import hello.Type.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class FinancialProjection {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_DESCRIPTION_LENGHT = 255;

    @NotNull
    @Length(min = 1, max = MAX_NAME_LENGHT)
    private String name;

    @Length(max = MAX_DESCRIPTION_LENGHT)
    private String description;

    /*
    TODO: se  n escrevermos nada a validacao da backend nao e' a melhor
     */
    @NotNull
    private float value;


    @NotNull
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Transaction_Type"), nullable = false)
    private Type type;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private boolean executed;

    @NotNull
    private int installments;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Transaction_Currency"), nullable = false)
    private Currency currency;

    private Project project;
    private Client client;
    private Employee employee;
    private Supplier supplier;
    private List<HoursPerProject> hoursPerProjectList;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<HoursPerProject> getHoursPerProjectList() {
        return hoursPerProjectList;
    }

    public void setHoursPerProjectList(List<HoursPerProject> hoursPerProjectList) {
        this.hoursPerProjectList = hoursPerProjectList;
    }

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
