package hello.Home.Resources;

import java.util.List;

public class FinancialChartResource {

    private List<String> yearMonthList;

    private List<String> expensesList;
    private List<Float> valueExpensesList;


    private Float totalRevenuesYear;
    private Float totalExpensesYear;

    //to indicate value per month (will has 12 values in a full year)
    private List<Float> totalRevenueByMonth;
    private List<Float> totalExpensesByMonth;

    public List<String> getYearMonthList() {
        return yearMonthList;
    }

    public void setYearMonthList(List<String> yearMonthList) {
        this.yearMonthList = yearMonthList;
    }

    public List<String> getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(List<String> expensesList) {
        this.expensesList = expensesList;
    }

    public List<Float> getValueExpensesList() {
        return valueExpensesList;
    }

    public void setValueExpensesList(List<Float> valueExpensesList) {
        this.valueExpensesList = valueExpensesList;
    }

    public Float getTotalRevenuesYear() {
        return totalRevenuesYear;
    }

    public void setTotalRevenuesYear(Float totalRevenuesYear) {
        this.totalRevenuesYear = totalRevenuesYear;
    }

    public Float getTotalExpensesYear() {
        return totalExpensesYear;
    }

    public void setTotalExpensesYear(Float totalExpensesYear) {
        this.totalExpensesYear = totalExpensesYear;
    }

    public List<Float> getTotalRevenueByMonth() {
        return totalRevenueByMonth;
    }

    public void setTotalRevenueByMonth(List<Float> totalRevenueByMonth) {
        this.totalRevenueByMonth = totalRevenueByMonth;
    }

    public List<Float> getTotalExpensesByMonth() {
        return totalExpensesByMonth;
    }

    public void setTotalExpensesByMonth(List<Float> totalExpensesByMonth) {
        this.totalExpensesByMonth = totalExpensesByMonth;
    }
}
