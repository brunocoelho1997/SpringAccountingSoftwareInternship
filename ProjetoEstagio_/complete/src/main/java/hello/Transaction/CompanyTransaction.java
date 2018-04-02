package hello.Transaction;

import hello.Company.Company;
import hello.Persons.Supplier.Supplier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "companyTransaction")
public class CompanyTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CompanyTransaction_Company"), nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;

    public CompanyTransaction() {
    }



}
