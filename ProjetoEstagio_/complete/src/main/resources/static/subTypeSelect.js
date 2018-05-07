$( ".selectedType" )
    .change(function () {

        var selectedTypeValue;

        $( ".selectedType option:selected" ).each(function() {
            selectedTypeValue = $( this ).val();
        });

        // alert(selectedTypeValue);

        if(selectedTypeValue !=null)
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
            }).fail(function (jqXHR, textStatus) {
                alert(textStatus);
            });
        }
    })
    .change();