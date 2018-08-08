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


        var id = $("#id").val();


        //if a subtype isn't selected we will get the subtypes of this type
        // if(selectedTypeValue == "")
        // {

        if(id ==null)
        {

            var opt = {
                type: 'GET',
                url: '/type/get_subTypes?value=' + selectedTypeValue
            }

            // alert(selectedTypeValue);
            // alert("é null");
        }
        else
        {
            // alert(id);


            var opt = {
                type: 'GET',
                url: '/type/get_subTypes?value=' + selectedTypeValue + "&id=" + id
            }
        }


            $.ajax(opt).done(function (result) {



                $('.subTypesOfType').html(''); //to clear all options
                // var option = document.createElement("option");
                // option.value = "";
                // $('.subTypesOfType').append(option);
                $('.subTypesOfType').append(result);


                // define subtype selected
                var subtype_id_selected = $('#subTypesOfType').data('subtype-id');
                $("#subTypesOfType").val(subtype_id_selected);
                // alert(subtype_id_selected);





            }).fail(function (jqXHR, textStatus) {
                alert(textStatus);
            });
        // }

    })
    .change();
