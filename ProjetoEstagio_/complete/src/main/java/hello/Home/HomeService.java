package hello.Home;

import hello.Application;
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
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

        listRevenuesMonth.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.REVENUE, true, dateMonth));
        listRevenuesMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.REVENUE, true, dateMonth));

        listCostsMonth.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.COST, true, dateMonth));

        /*
        TODO: falta fazer para o resto dos tipos de despesas...
         */


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
        listRevenuesYear.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.REVENUE, true, dateYear));
        listRevenuesYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.REVENUE, true, dateYear));

        totalValueMonth = getTotal(listRevenuesYear);
        chartResourceStatics.setTotalRevenuesYear((float)totalValueMonth);
        listCostsYear.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.COST, true, dateYear));
//        listCostsYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.COST, true, dateYear));
//        listCostsYear.addAll(generalTransactionRepository.findAllByGenreAndActivedAndDateAfter(Genre.COST, true, dateYear));

        /*
        TODO: falta fazer para o resto dos tipos de despesas...
         */
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

        listRevenues.addAll(saleTransactionRepository.findAllByGenreAndActived(Genre.REVENUE, true));
        listRevenues.addAll(projectTransactionRepository.findAllByGenreAndActived(Genre.REVENUE, true));


        listCosts.addAll(projectTransactionRepository.findAllByGenreAndActived(Genre.COST, true));

        /*
        TODO: falta fazer para o resto dos tipos de despesas...
         */


        for(Transaction transaction : listRevenues){
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


        listRevenuesYear.addAll(saleTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBefore(Genre.REVENUE, true, beginOfYear,finalOfYear));
        listRevenuesYear.addAll(projectTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBefore(Genre.REVENUE, true, beginOfYear,finalOfYear));


        listCostsYear.addAll(employeeTransactionRepository.findAllByGenreAndActivedAndDateAfterAndDateBefore(Genre.COST, true, beginOfYear,finalOfYear));
        /*
        TODO: falta fazer para o resto dos tipos de despesas...
         */

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


        for(int i= 0 ; i<12; i++)
        {
            financialChartResource.getTotalExpensesByMonth().add(new Random().nextFloat() *10 );
            financialChartResource.getTotalRevenueByMonth().add(new Random().nextFloat() *10 );
        }

        financialChartResource.setSelectedCurrency(currencyRepository.findBySelected(true).getSymbol());


        return  financialChartResource;
    }
}
