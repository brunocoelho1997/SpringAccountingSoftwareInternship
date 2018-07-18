package hello.Home.Resources;

import hello.Project.Resources.TypeSubtypeResource;

import java.util.List;

public class ChartResourceStatics {



    private List<String> yearMonthList;

    private List<Float> balanceList;
    private List<Float> profitList;

    private List<String> expensesList;
    private List<Float> valueExpensesList;


    private Float totalRevenuesMonth;
    private Float totalExpensesMonth;
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

    public List<Float> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Float> balanceList) {
        this.balanceList = balanceList;
    }

    public List<Float> getProfitList() {
        return profitList;
    }

    public void setProfitList(List<Float> profitList) {
        this.profitList = profitList;
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

    public Float getTotalRevenuesMonth() {
        return totalRevenuesMonth;
    }

    public void setTotalRevenuesMonth(Float totalRevenuesMonth) {
        this.totalRevenuesMonth = totalRevenuesMonth;
    }

    public Float getTotalExpensesMonth() {
        return totalExpensesMonth;
    }

    public void setTotalExpensesMonth(Float totalExpensesMonth) {
        this.totalExpensesMonth = totalExpensesMonth;
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

    @Override
    public String toString() {
        return "ChartResourceStatics{" +
                "yearMonthList=" + yearMonthList +
                ", balanceList=" + balanceList +
                ", profitList=" + profitList +
                ", expensesList=" + expensesList +
                ", valueExpensesList=" + valueExpensesList +
                ", totalRevenuesMonth=" + totalRevenuesMonth +
                ", totalExpensesMonth=" + totalExpensesMonth +
                ", totalRevenuesYear=" + totalRevenuesYear +
                ", totalExpensesYear=" + totalExpensesYear +
                ", totalRevenueByMonth=" + totalRevenueByMonth +
                ", totalExpensesByMonth=" + totalExpensesByMonth +
                '}';
    }
}
