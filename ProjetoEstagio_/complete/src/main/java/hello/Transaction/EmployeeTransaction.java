package hello.Transaction;

import hello.Persons.Employee.Employee;
import hello.Persons.Supplier.Supplier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "employeeTransaction")
public class EmployeeTransaction extends Transaction {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EmployeeTransaction_Employee"), nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employee employee;

    public EmployeeTransaction() {
    }

}
