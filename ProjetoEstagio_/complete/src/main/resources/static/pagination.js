$(document).ready(function() {
    changePageAndSize();

    // alert(document.forms['project_id']);
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function(evt) {
        var pathname = window.location.pathname;
        window.location.replace(pathname + "?pageSize=" + this.value + "&page=1");
    });
}


$(".pageLink").click(function(){



    var pageSize = $(this).attr("pageSize");
    var page = $(this).attr("page");


    if(pageSize!=null && page != null)
    {
        $('#form').append('<input type="hidden" name="pageSize" value="' + pageSize + '" />');
        $('#form').append('<input type="hidden" name="page" value="' + page + '" />');
    }


    $("#form").submit();

    //
    // var pathname= window.location.pathname;
    // args_page = args_page.substring(1);
    // window.location.replace(pathname + args_page);


    // var args_page = $(this).attr("link_to");
    //
    // // alert("Cliquei:" + link);
    // var pathname= window.location.pathname;
    //
    // args_page = args_page.substring(1);

    //
    // var params = getAllUrlParams();
    // var x;
    // for(var i=0; i<x.length-1; i++)
    //     x = x + "&" + params[i];
    //
    // alert(x);

    //
    // var x = "" + getAllUrlParams();
    //
    // alert(x);
    //
    // var n ="";
    //
    // if(x == "")
    //     alert("String vazia");
    // else
    // {
    //     x = x.substring(1);
    //     n = "?" + x;
    //     alert(n);
    // }
    //
    //
    //
    // //alert(x);
    //
    // window.location.replace(pathname + n + args_page);



});



//BASE
// var args_page = $(this).attr("link_to");
// var pathname= window.location.pathname;
// args_page = args_page.substring(1);
// window.location.replace(pathname + args_page);

String.prototype.replaceAt=function(index, replacement) {
    return this.substr(0, index) + replacement+ this.substr(index + replacement.length);
}

function getAllUrlParams() {
    var keyPairs = [],
        params = window.location.search.substring(1).split('&');
    for (var i = params.length - 1; i >= 0; i--) {
        // if( params[i] != "page" || params[i]!="pageSize" )
        //     alert(params[i].split('=')[0]);

        if(params[i].split('=')[0] == "page" || params[i].split('=')[0] == "pageSize")
            ;
        else
            keyPairs = keyPairs + "&" + params[i];


    };
    return keyPairs;
}