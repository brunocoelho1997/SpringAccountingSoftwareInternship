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

                    var elementsBalancePerMonth = [];
                    for (i = 0; i < result.yearMonthList.length; i++) {
                        var element = {};
                        element.period = result.yearMonthList[i];
                        element.balance = result.balanceList[i];
                        element.profit = result.profitList[i];
                        // element.total = result.profitList[i] + result.balanceList[i];


                        // alert(element.period + " " + element.balance + " " + element.profit);
                        elementsBalancePerMonth.push(element);
                    }

                    $("#balancePerMonthProjection").html("");

                    Morris.Bar({
                        element: 'balancePerMonthProjection',
                        data: elementsBalancePerMonth,
                        xkey: 'period',
                        ykeys: ['balance', 'profit'],
                        labels: ['ValorLiquido', 'Lucro'],
                        stacked: true
                    });


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
                        stacked: true
                    });

                    $("#expensesVsRevenuesTotalYear").html("");

                    Morris.Donut({
                        element: 'expensesVsRevenuesTotalYearProjection',
                        data: [
                            {value: result.totalRevenuesYear, label: 'Receitas'},
                            {value: result.totalExpensesYear, label: 'Despesas'}
                        ],
                        formatter: function (x) { return x + "€"}
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