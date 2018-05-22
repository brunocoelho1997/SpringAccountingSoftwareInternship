// // server just send all options... but subtype is not required so we need to add empty option
// $( document ).ready(function() {
//     var option = document.createElement("option");
//     option.text = "";
//     option.value = 0;
//     $('#subTypesOfType').prepend(option);
// });

$( ".selectedType" )
    .change(function () {

        var selectedTypeValue;

        $( ".selectedType option:selected" ).each(function() {
            selectedTypeValue = $( this ).val();
        });

        //alert(selectedTypeValue);

        if(selectedTypeValue !=null && selectedTypeValue!="0")
        {
            var opt = {
                type: 'GET',
                url: '/type/get_subTypes?id=' + selectedTypeValue
            }

            $.ajax(opt).done(function (result) {
                $('#subTypesOfType').html(''); //to clear all options
                var option = document.createElement("option");
                option.text = "";
                option.value = 0;
                $('#subTypesOfType').append(option);
                $('#subTypesOfType').append(result);

                // define subtype selected
                var subtype_id_selected = $('#subTypesOfType').data('subtype-id');
                $("#subTypesOfType").val(subtype_id_selected);
                // alert(subtype_id_selected);

            }).fail(function (jqXHR, textStatus) {
                alert(textStatus);
            });
        }
    })
    .change();