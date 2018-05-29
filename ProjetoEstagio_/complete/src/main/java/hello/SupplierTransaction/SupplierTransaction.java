package hello.SupplierTransaction;

import hello.Supplier.Supplier;
import hello.Transaction.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "supplierTransaction")
public class SupplierTransaction extends Transaction {
    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_SupplierTransaction_Supplier"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Supplier supplier;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
