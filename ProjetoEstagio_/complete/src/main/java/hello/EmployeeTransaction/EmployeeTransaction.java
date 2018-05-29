package hello.EmployeeTransaction;

import hello.Employee.Employee;
import hello.Project.Project;
import hello.Transaction.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "employeeTransaction")
public class EmployeeTransaction extends Transaction {
    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EmployeeTransaction_Employee"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
