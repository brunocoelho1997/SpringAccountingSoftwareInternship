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
        }],
        xkey: 'y',
        ykeys: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'],
        labels: ['Investimentos', 'Infra', 'Recursos Projetos', 'Folha', 'Impostos', 'Comissão', 'Outros', 'Op Venda', 'Retiradas'],
        hideHover: 'auto',
        resize: true
    });
});