


var opt = {
    type: 'GET',
    url: '/home/get_statics'
}

$.ajax(opt).done(function (result) {


    if(result.yearMonthList != null) {


        // document.getElementById("loader").style.display = "block";
        // document.getElementById("graphsContent").style.visibility = "hidden";
        // alert("aqui");

        var i;

        var elementsExpensesByType = [];
        for (i = 0; i < result.expensesList.length; i++) {
            var element = {};
            element.expenseType = result.expensesList[i];
            element.value = result.valueExpensesList[i];

            // alert(element.period + " " + element.balance + " " + element.profit);
            elementsExpensesByType.push(element);
        }

        $("#expensesByType").html("");

        Morris.Bar({
            element: 'expensesByType',
            data: elementsExpensesByType,
            xkey: 'expenseType',
            ykeys: ['value'],
            labels: ['Valor'],
            stacked: true
        });


        $("#expensesVsRevenuesTotalMonth").html("");
        Morris.Donut({
            element: 'expensesVsRevenuesTotalMonth',
            data: [
                {value: result.totalRevenuesMonth, label: 'Receitas'},
                {value: result.totalExpensesMonth, label: 'Despesas'}
            ],
            formatter: function (x) { return x + "€"}
        }).on('click', function(i, row){
            console.log(i, row);
        });

        // $("#expensesVsRevenuesTotalYear").html("");

        Morris.Donut({
            element: 'expensesVsRevenuesTotalYear',
            data: [
                {value: result.totalRevenuesYear, label: 'Receitas'},
                {value: result.totalExpensesYear, label: 'Despesas'}
            ],
            formatter: function (x) { return x + "€"}
        }).on('click', function(i, row){
            console.log(i, row);
        });


        // document.getElementById("loader").style.display = "none";
        // document.getElementById("graphsContent").style.visibility = "visible";

    }

}).fail(function (jqXHR, textStatus) {
    alert(textStatus);
});

