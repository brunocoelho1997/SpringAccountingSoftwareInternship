$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2018-01',
            liquido: 2666,
            lucro: null
        }, {
            period: '2018-02',
            liquido: 2778,
            lucro: 2294
        }, {
            period: '2018-03',
            liquido: 4912,
            lucro: 1969
        }, {
            period: '2018-04',
            liquido: 3767,
            lucro: 3597
        }, {
            period: '2018-05',
            liquido: 6810,
            lucro: 1914
        }, {
            period: '2018-06',
            liquido: 5670,
            lucro: 4293
        }, {
            period: '2018-07',
            liquido: 4820,
            lucro: 3795
        }, {
            period: '2018-08',
            liquido: 15073,
            lucro: 5967
        }, {
            period: '2018-09',
            liquido: null,
            lucro: null
        }, {
            period: '2018-10',
            liquido: null,
            lucro: null
        }, {
            period: '2018-11',
            liquido: null,
            lucro: null
        }, {
            period: '2018-12',
            liquido: null,
            lucro: null
        }],
        xkey: 'period',
        ykeys: ['liquido', 'lucro'],
        labels: ['ValorLiquido', 'Lucro'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });
});

$(function() {

    Morris.Area({
        element: 'morris-area-chart-entradas',

        data: [{
            period: '2018-01',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 3000
        }, {
            period: '2018-02',
            Entrada: null,
            Recorrente: 2454,
            Liquido: 3570
        }, {
            period: '2018-03',
            Entrada: 2666,
            Recorrente: 2754,
            Liquido: 3000
        }, {
            period: '2018-04',
            Entrada: 1366,
            Recorrente: 2454,
            Liquido: 3000
        }, {
            period: '2018-05',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 1000
        }, {
            period: '2018-06',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 3000
        }, {
            period: '2018-07',
            Entrada: 4666,
            Recorrente: 2454,
            Liquido: 6000
        }, {
            period: '2018-08',
            Entrada: null,
            Recorrente: 2454,
            Liquido: 7254
        }, {
            period: '2018-09',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 1000
        }, {
            period: '2018-10',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 4000
        }, {
            period: '2018-11',
            Entrada: 2166,
            Recorrente: 2954,
            Liquido: 3000
        }, {
            period: '2018-12',
            Entrada: 2666,
            Recorrente: 2454,
            Liquido: 1000
        }],
        xkey: 'period',
        ykeys: ['Entrada', 'Recorrente', 'Liquido'],
        labels: ['Entrada', 'Recorrente', 'Liquido'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });
});


$(function() {

    //VER ISTO EM CONDICOES...
    //NO TEMPLATE TEM ISTO TUDO NUMA SO FUNCAO

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2018-01',
            a: 100,
            b: 90,
            c: 90,
            d: 110,
            e: 90,
            f: 90,
            g: 50,
            h: 90,
            i: 90
        }, {
            y: '2018-02',
            a: 100,
            b: 90,
            c: 90,
            d: 90,
            e: 90,
            f: 90,
            g: 90,
            h: 90,
            i: 90
        }, {
            y: '2018-03',
            a: 100,
            b: 90,
            c: 140,
            d: 90,
            e: 90,
            f: 90,
            g: 50,
            h: 90,
            i: 90
        }, {
            y: '2018-04',
            a: 100,
            b: 90,
            c: 30,
            d: 80,
            e: 90,
            f: 90,
            g: 80,
            h: 90,
            i: 90
        }, {
            y: '2018-05',
            a: 100,
            b: 90,
            c: 10,
            d: 90,
            e: 90,
            f: 30,
            g: 90,
            h: 90,
            i: 40
        }, {
            y: '2018-06',
            a: 100,
            b: 90,
            c: 20,
            d: 30,
            e: 40,
            f: 90,
            g: 90,
            h: 50,
            i: 90
        }, {
            y: '2018-07',
            a: 100,
            b: 90,
            c: 90,
            d: 94,
            e: 90,
            f: 90,
            g: 92,
            h: 90,
            i: 93
        }, {
            y: '2018-09',
            a: null,
            b: null,
            c: null,
            d: null,
            e: null,
            f: null,
            g: null,
            h: null,
            i: null
        }, {
            y: '2018-10',
            a: null,
            b: null,
            c: null,
            d: null,
            e: null,
            f: null,
            g: null,
            h: null,
            i: null
        }, {
            y: '2018-11',
            a: null,
            b: null,
            c: null,
            d: null,
            e: null,
            f: null,
            g: null,
            h: null,
            i: null
        }, {
            y: '2018-12',
            a: null,
            b: null,
            c: null,
            d: null,
            e: null,
            f: null,
            g: null,
            h: null,
            i: null
        }
        ],
        xkey: 'y',
        ykeys: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'],
        labels: ['Investimentos', 'Infra', 'Recursos Projetos', 'Folha', 'Impostos', 'Comissão', 'Outros', 'Op Venda', 'Retiradas'],
        hideHover: 'auto',
        resize: true
    });
});