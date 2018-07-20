var imported = document.createElement('script');
imported.src = '/morris_horizontal.js';
document.head.appendChild(imported);

// FINANCIAL PROJECTION
$( "#yearSelected" )
    .change(function () {

        var selectedYear;

        $( "#yearSelected option:selected" ).each(function() {
            selectedYear = $( this ).val();
        });


        if(selectedYear!=null)
        {


            var opt = {
                type: 'GET',
                url: '/home/get_financialProjection?year=' + selectedYear
            }

            $.ajax(opt).done(function (result) {


                if(result.yearMonthList != null) {

                    var i;

                    var elementsExpensesByType = [];
                    for (i = 0; i < result.expensesList.length; i++) {
                        var element = {};
                        element.expenseType = result.expensesList[i];
                        element.value = result.valueExpensesList[i];

                        // alert(element.period + " " + element.balance + " " + element.profit);
                        elementsExpensesByType.push(element);
                    }

                    $("#expensesByTypeProjection").html("");

                    Morris.Bar({
                        element: 'expensesByTypeProjection',
                        data: elementsExpensesByType,
                        xkey: 'expenseType',
                        ykeys: ['value'],
                        labels: ['Valor'],
                        horizontal: true,
                        stacked: true
                    });

                    $("#expensesVsRevenuesTotalYearProjection").html("");

                    Morris.Donut({
                        element: 'expensesVsRevenuesTotalYearProjection',
                        data: [
                            {value: result.totalRevenuesYear, label: 'Receitas'},
                            {value: result.totalExpensesYear, label: 'Despesas'}
                        ],
                        formatter: function (x) { return x + "" + result.selectedCurrency}
                    }).on('click', function(i, row){
                        console.log(i, row);
                    });

                    var elementsExpenseVsRevenuePerMonth = [];
                    for (i = 0; i < result.yearMonthList.length; i++) {
                        var element = {};
                        element.period = result.yearMonthList[i];
                        element.revenue = result.totalRevenueByMonth[i];
                        element.expense = result.totalExpensesByMonth[i];
                        // element.total = result.profitList[i] + result.balanceList[i];


                        // alert(element.period + " " + element.balance + " " + element.profit);
                        elementsExpenseVsRevenuePerMonth.push(element);
                    }

                    $("#expensesVsRevenuesByMonth").html("");

                    Morris.Bar({
                        element: 'expensesVsRevenuesByMonth',
                        data: elementsExpenseVsRevenuePerMonth,
                        xkey: 'period',
                        ykeys: ['revenue', 'expense'],
                        labels: ['Receita', 'Despesa']
                    }).on('click', function(i, row){
                        console.log(i, row);
                    });

                }

            }).fail(function (jqXHR, textStatus) {
                alert(textStatus);
            });
        }
    })
    .change();