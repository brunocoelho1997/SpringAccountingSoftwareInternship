package hello.SheetTransaction;

import hello.Employee.Employee;
import hello.SheetTransaction.Resources.HoursPerProject;
import hello.Transaction.Transaction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "sheetTransaction")
public class SheetTransaction extends Transaction{

    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EmployeeTransaction_Employee"), nullable = true)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
    private Employee employee;

    @ElementCollection
    @CollectionTable(foreignKey = @ForeignKey(name = "FK_SheetTransaction_HoursPerProject"))
    @Valid
    private List<HoursPerProject> hoursPerProjectList;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<HoursPerProject> getHoursPerProjectList() {
        return hoursPerProjectList;
    }

    public void setHoursPerProjectList(List<HoursPerProject> hoursPerProjectList) {
        this.hoursPerProjectList = hoursPerProjectList;
    }
}
