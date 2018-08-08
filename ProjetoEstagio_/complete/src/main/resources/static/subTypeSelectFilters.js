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
            url: '/type/get_subTypes_filters?value=' + selectedTypeValue
        }

        $.ajax(opt).done(function (result) {

            // alert(selectedSubTypeValue);

            // if(!result.includes(selectedSubTypeValue) || selectedSubTypeValue=="")
            // {
            // alert("aqui");

            $('.subTypesOfType').html(''); //to clear all options
            var option = document.createElement("option");
            option.value = "";
            $('.subTypesOfType').append(option);
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
