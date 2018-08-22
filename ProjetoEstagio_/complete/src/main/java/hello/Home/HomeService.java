package hello.Home;

import hello.Application;
import hello.ComissionTransaction.ComissionTransactionRepository;
import hello.Currency.Currency;
import hello.Currency.CurrencyRepository;
import hello.Employee.EmployeeRepository;
import hello.EmployeeTransaction.EmployeeTransactionRepository;
import hello.Enums.Genre;
import hello.GeneralTransaction.GeneralTransactionRepository;
import hello.Home.Resources.ChartResourceStatics;
import hello.Home.Resources.FinancialChartResource;
import hello.Project.Resources.ChartResource;
import hello.Project.Resources.TypeSubtypeResource;
import hello.ProjectTransaction.ProjectTransactionRepository;
import hello.SaleTransaction.SaleTransactionRepository;
import hello.SheetTransaction.SheetTransactionRepository;
import hello.SupplierTransaction.SupplierTransaction;
import hello.SupplierTransaction.SupplierTransactionRepository;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionRepository;
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class HomeService {

    @Autowired
    TypeService typeService;

    @Autowired
    SaleTransactionRepository saleTransactionRepository;
    @Autowired
    ProjectTransactionRepository projectTransactionRepository;
    @Autowired
    EmployeeTransactionRepository employeeTransactionRepository;
    @Autowired
    GeneralTransactionRepository generalTransactionRepository;
    @Autowired
    SheetTransactionRepository sheetTransactionRepository;
    @Autowired
    SupplierTransactionRepository supplierTransactionRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ComissionTransactionRepository comissionTransactionRepository;
    @Autowired
    TransactionRepository transactionRepository;

    private double getTotal(List<Transaction> listTransactions){

        float total = 0;

        for(Object transaction : listTransactions)
            total+=(double) ((Transaction)transaction).getValue();

        return (double) Math.round(total * 100.0) / 100.0;
    }

    private List<String>getMonthsList(Integer year){
        List<String> months = new ArrayList<>();

        for(int i=0; i<12; i++)
            months.add("" + year + "-" + String.format("%02d", (i+1))  );

        return months;
    }

    private void getExpensesByType(List<String> typeListString, List<Transaction> listCostsYear, List<Float> valuePerType){
        for(Transaction transaction : listCostsYear){
            //if dont has this type add
            if(!typeListString.contains(transaction.getType().getName()))
            {
                typeListString.add(transaction.getType().getName());
                valuePerType.add(new Float(0));
            }
            int i = typeListString.indexOf(transaction.getType().getName());
            Float oldValue = valuePerType.get(i);
            valuePerType.set(i, oldValue+transaction.getValue());
        }
    }

    public ChartResourceStatics getStatistic() {

        ChartResourceStatics chartResourceStatics = new ChartResourceStatics();



        LocalDate dateNow = LocalDate.now();

        int year = dateNow.getYear();
        int month = dateNow.getMonthValue();



        LocalDate dateYear = LocalDate.of(year,1,1);
        LocalDate dateMonth = LocalDate.of(year,month,1);



        /*

        ---Get all transactions for month and for year
         */

        List<Transaction> listRevenuesMonth = new ArrayList<>();
        List<Transaction> listCostsMonth = new ArrayList<>();

        List<Transaction> listRevenuesYear = new ArrayList<>();
        List<Transaction> listCostsYear = new ArrayList<>();

        listRevenuesMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.REVENUE, true, dateMonth,true));
        listRevenuesMonth.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.REVENUE, true, dateMonth,true));

        listCostsMonth.addAll(generalTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));
        listCostsMonth.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));
        listCostsMonth.addAll(sheetTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));
        listCostsMonth.addAll(supplierTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));
        listCostsMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));
        listCostsMonth.addAll(comissionTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateMonth,true));



       /*

        ----Total month revenues and expenses

        */

        double totalValueMonth = getTotal(listRevenuesMonth);
        chartResourceStatics.setTotalRevenuesMonth((float)totalValueMonth);


        totalValueMonth = getTotal(listCostsMonth);
        chartResourceStatics.setTotalExpensesMonth((float)totalValueMonth);

        /*

        ----Total year revenues and expenses

        */
        listRevenuesYear.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.REVENUE, true, dateYear,true));
        listRevenuesYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.REVENUE, true, dateYear,true));

        totalValueMonth = getTotal(listRevenuesYear);
        chartResourceStatics.setTotalRevenuesYear((float)totalValueMonth);

        listCostsYear.addAll(generalTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));
        listCostsYear.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));
        listCostsYear.addAll(sheetTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));
        listCostsYear.addAll(supplierTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));
        listCostsYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));
        listCostsYear.addAll(comissionTransactionRepository.findAllByGenreAndActivedAndDateAfterAndExecuted(Genre.COST, true, dateYear,true));


        double totalValueYear = getTotal(listCostsYear);
        chartResourceStatics.setTotalExpensesYear((float)totalValueYear);


        /*

        -------GENERAL - dates

         */
        chartResourceStatics.setYearMonthList(getMonthsList(year));





        /*

        -----------------expensesByType

         */

        List<String> typeListString = new ArrayList<>();
        List<Float> valuePerType = new ArrayList<>();

        getExpensesByType(typeListString, listCostsYear, valuePerType);

        chartResourceStatics.setExpensesList(typeListString);
        chartResourceStatics.setValueExpensesList(valuePerType);

        chartResourceStatics.setSelectedCurrency(currencyRepository.findBySelected(true).getSymbol());

        return chartResourceStatics;
    }


    public List<String>getAllYears(){
        List<String> years = new ArrayList<>();

        List<Transaction> listRevenues = new ArrayList<>();
        List<Transaction> listCosts = new ArrayList<>();

        listRevenues.addAll(saleTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.REVENUE, true,true));
        listRevenues.addAll(projectTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.REVENUE, true,true));


        listCosts.addAll(generalTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));
        listCosts.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));
        listCosts.addAll(sheetTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));
        listCosts.addAll(supplierTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));
        listCosts.addAll(projectTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));
        listCosts.addAll(comissionTransactionRepository.findAllByGenreAndActivedAndExecuted(Genre.COST, true,true));


        for(Transaction transaction : listRevenues){
            String stringYear = String.valueOf(transaction.getDate().getYear());
            if(!years.contains(stringYear))
                years.add(stringYear);
        }
        for(Transaction transaction : listCosts){
            String stringYear = String.valueOf(transaction.getDate().getYear());
            if(!years.contains(stringYear))
                years.add(stringYear);
        }

        return years;
    }



    public FinancialChartResource getStatistic(Long year) {

        FinancialChartResource financialChartResource = new FinancialChartResource();
        List<Transaction> listRevenuesYear = new ArrayList<>();
        List<Transaction> listCostsYear = new ArrayList<>();


        LocalDate beginOfYear = LocalDate.of((int)(long)year,1,1);
        LocalDate finalOfYear = LocalDate.of((int)(long)year,12,31);


        listRevenuesYear.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.REVENUE, true, beginOfYear,finalOfYear,true));
        listRevenuesYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.REVENUE, true, beginOfYear,finalOfYear,true));


        listCostsYear.addAll(generalTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));
        listCostsYear.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));
        listCostsYear.addAll(sheetTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));
        listCostsYear.addAll(supplierTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));
        listCostsYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));
        listCostsYear.addAll(comissionTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfYear,finalOfYear,true));

         /*
        -------GENERAL - dates
         */
        financialChartResource.setYearMonthList(getMonthsList((int)(long)year));


        /*
        -----------------expensesByType
         */


        List<String> typeListString = new ArrayList<>();
        List<Float> valuePerType = new ArrayList<>();

        getExpensesByType(typeListString, listCostsYear, valuePerType);

        financialChartResource.setExpensesList(typeListString);
        financialChartResource.setValueExpensesList(valuePerType);


        /*
        ---Total year revenues and expenses in
         */
        double totalValueYear = getTotal(listCostsYear);
        financialChartResource.setTotalExpensesYear((float)totalValueYear);

        totalValueYear = getTotal(listRevenuesYear);
        financialChartResource.setTotalRevenuesYear((float)totalValueYear);


        /*
        ---Total revenues and expenses by month
         */
        financialChartResource.setTotalExpensesByMonth(new ArrayList<>());
        financialChartResource.setTotalRevenueByMonth(new ArrayList<>());

        LocalDate beginOfMonth = null;
        LocalDate finalOfMonth = null;
        List<Transaction> listAuxCostsByMonth = new ArrayList<>();
        List<Transaction> listAuxRevenuesByMonth = new ArrayList<>();
        float totalCostsByMonth = 0;
        float totalRevenuesByMonth = 0;

        for(int i= 1 ; i<=12; i++)
        {
            beginOfMonth = LocalDate.of((int)(long)year,i,1);


            finalOfMonth = i!=12? LocalDate.of((int)(long)year,i+1,1) : LocalDate.of((int)(long)year, 12,31);

            listAuxRevenuesByMonth.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.REVENUE, true, beginOfMonth,finalOfMonth,true));
            listAuxRevenuesByMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.REVENUE, true, beginOfMonth,finalOfMonth,true));

            listAuxCostsByMonth.addAll(generalTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));
            listAuxCostsByMonth.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));
            listAuxCostsByMonth.addAll(sheetTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));
            listAuxCostsByMonth.addAll(supplierTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));
            listAuxCostsByMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));
            listAuxCostsByMonth.addAll(comissionTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBeforeAndExecuted(Genre.COST, true, beginOfMonth,finalOfMonth,true));


            totalCostsByMonth = (float) getTotal(listAuxCostsByMonth);
            totalRevenuesByMonth = (float) getTotal(listAuxRevenuesByMonth);


            financialChartResource.getTotalExpensesByMonth().add(totalCostsByMonth);
            financialChartResource.getTotalRevenueByMonth().add(totalRevenuesByMonth);

            listAuxCostsByMonth.clear();
            listAuxRevenuesByMonth.clear();
            totalCostsByMonth = 0;
            totalRevenuesByMonth = 0;

        }

        financialChartResource.setSelectedCurrency(currencyRepository.findBySelected(true).getSymbol());

        return  financialChartResource;
    }

    public List<Transaction> financialProjection(Genre genre) {

        List<Transaction> last5Transactions = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();


        List<Transaction> allTransactions;
        if(genre.equals(Genre.REVENUE))
            allTransactions = transactionRepository.findAllByGenreAndExecutedAndActivedAndDateAfterOrderByDateAsc(Genre.REVENUE,false,true,dateNow);
        else
            allTransactions = transactionRepository.findAllByGenreAndExecutedAndActivedAndDateAfterOrderByDateAsc(Genre.COST,false,true,dateNow);

        int n = allTransactions.size() > 5 ? 5: allTransactions.size();
        last5Transactions.addAll(allTransactions.subList(0,n));

        return last5Transactions;
    }
}
