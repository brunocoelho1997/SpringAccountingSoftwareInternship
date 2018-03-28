package hello.Transaction;

import hello.Persons.Supplier.Supplier;
import hello.Type.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "suplierTransaction")
public class SupplierTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_SupplierTransaction_Supplier"), nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;

    public SupplierTransaction() {
    }

}