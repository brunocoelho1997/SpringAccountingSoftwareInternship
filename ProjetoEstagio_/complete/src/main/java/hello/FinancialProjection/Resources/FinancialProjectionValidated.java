package hello.FinancialProjection.Resources;

import hello.Employee.Employee;
import hello.SheetTransaction.Resources.HoursPerProject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

public class FinancialProjectionValidated {

    @NotNull
    private Long id;
    private ArrayList<HoursPerProject> hoursPerProjectList;
    @NotNull
    private int installments;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<HoursPerProject> getHoursPerProjectList() {
        return hoursPerProjectList;
    }

    public void setHoursPerProjectList(ArrayList<HoursPerProject> hoursPerProjectList) {
        this.hoursPerProjectList = hoursPerProjectList;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FinancialProjectionValidated{" +
                "id=" + id +
                ", hoursPerProjectList=" + hoursPerProjectList +
                ", installments=" + installments +
                '}';
    }
}
