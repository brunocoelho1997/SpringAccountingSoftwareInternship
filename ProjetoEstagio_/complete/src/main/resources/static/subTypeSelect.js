// // server just send all options... but subtype is not required so we need to add empty option
// $( document ).ready(function() {
//     var option = document.createElement("option");
//     option.text = "";
//     option.value = 0;
//     $('#subTypesOfType').prepend(option);
// });


// $('.myButton').click(function() {
//     // $(this).attr('disabled', 'disabled');
//     //
//     $(".selectedType").prop('disabled', false);
//     $(".subTypesOfType").prop('disabled', false);
//
//     $(this).parents('form').submit();
// })

// $('.buttonResetTypes').click(function() {
//
//
//     // $(".selectedType").prop('disabled', false);
//     // $(".selectedType").val(0);
//
//     var selectedTypeValue = "";
//     var opt = {
//         type: 'GET',
//         url: '/type/get_subTypes?value=' + selectedTypeValue
//     }
//
//     $.ajax(opt).done(function (result) {
//         $('.subTypesOfType').html(''); //to clear all options
//         var option = document.createElement("option");
//         option.value = "";
//         $('.subTypesOfType').append(option);
//         $('.subTypesOfType').append(result);
//
//         // alert("aqui");
//     }).fail(function (jqXHR, textStatus) {
//         alert(textStatus);
//     });



    // $(".subTypesOfType").prop('disabled', false);
    // $(".subTypesOfType").val(0);
    //
    // var selectedSubTypeValue;
    // $( ".subTypesOfType option:selected" ).each(function() {
    //     selectedSubTypeValue = $( this ).val();
    // });
    // var selectedSubTypeValue = "";
    // var opt = {
    //     type: 'GET',
    //     url: '/type/get_types?value=' + selectedSubTypeValue
    // }
    //
    // $.ajax(opt).done(function (result2) {
    //     $('.selectedType').html(''); //to clear all options
    //     var option = document.createElement("option");
    //     option.value = "";
    //     $('.selectedType').append(option);
    //     $('.selectedType').append(result2);
    //
    // }).fail(function (jqXHR, textStatus) {
    //     alert(textStatus);
    // });

// })





/*

    ISTO E' APENAS PARA QUANDO NAO TEM A APRESENTAR SUBTYPES DE TYPES


 */


//
$( ".selectedType" )
    .change(function () {

        //get type value
        var selectedTypeValue;
        $( ".selectedType option:selected" ).each(function() {
            selectedTypeValue = $( this ).val();
        });

        //get Subtype value
        var selectedSubTypeValue;
        $( ".subTypesOfType option:selected" ).each(function() {
            selectedSubTypeValue = $( this ).val();
        });



        //if a subtype isn't selected we will get the subtypes of this type
        // if(selectedTypeValue == "")
        // {
            var opt = {
                type: 'GET',
                url: '/type/get_subTypes?value=' + selectedTypeValue
            }

            $.ajax(opt).done(function (result) {

                // alert(selectedSubTypeValue);

                // if(!result.includes(selectedSubTypeValue) || selectedSubTypeValue=="")
                // {
                    // alert("aqui");

                    $('.subTypesOfType').html(''); //to clear all options
                    // var option = document.createElement("option");
                    // option.value = "";
                    // $('.subTypesOfType').append(option);
                    $('.subTypesOfType').append(result);


                    var subtype_value_selected = $('.subTypesOfType').data('subtype-value');
                    $(".subTypesOfType").val(subtype_value_selected);
                // }
                // else
                // {
                //
                // }

            }).fail(function (jqXHR, textStatus) {
                alert(textStatus);
            });
        // }

    })
    .change();
//
//
// //for subtype
// $( ".subTypesOfType" )
//     .change(function () {
//         var selectedSubTypeValue;
//         $( ".subTypesOfType option:selected" ).each(function() {
//             selectedSubTypeValue = $( this ).val();
//         });
//
//         // if(selectedSubTypeValue!="")
//         //     $(this).prop('disabled', true);
//         // else
//         //     $(this).prop('disabled', false);
//         // alert(selectedSubTypeValue);
//
//         var selectedTypeValue;
//         $( ".selectedType option:selected" ).each(function() {
//             selectedTypeValue = $( this ).val();
//         });
//
//
//
//         // alert("mexeu subtype");
//
//         if(selectedTypeValue=="") {
//             var opt = {
//                 type: 'GET',
//                 url: '/type/get_types?value=' + selectedSubTypeValue
//             }
//
//             $.ajax(opt).done(function (result2) {
//                 $('.selectedType').html(''); //to clear all options
//                 var option = document.createElement("option");
//                 option.value = "";
//                 $('.selectedType').append(option);
//                 $('.selectedType').append(result2);
//
//                 // define type selected
//                 var type_value_selected = $('.selectedType').data('type-value');
//                 $(".selectedType").val(type_value_selected);
//                 // alert(type_value_selected);
//                 // if(type_value_selected != null)
//                 //     $(".selectedType").prop('disabled', true);
//
//                 // alert("mexeu subtype");
//
//
//             }).fail(function (jqXHR, textStatus) {
//                 alert(textStatus);
//             });
//         }
//
//
//     })
//     .change();