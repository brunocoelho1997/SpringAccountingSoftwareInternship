var listHoursPerProject = $('#listHoursPerProject');
var numberHoursPerProject = $('#listHoursPerProject tr').size();



function validSubmit() {

    if(numberHoursPerProject==0)
    {
        $('#myForm').submit();
    }
    else
    {
        var value = $( ".employee_value option:selected" ).text();

        if(value!=0)
            $("#myForm").submit();
        else
            alert("tem de selecionar funcionário");
    }
}


$('#addHoursPerProject').click(function() {

    var opt = {
        type: 'GET',
        url: '/project/get_options_projects'
    }

    $.ajax(opt).done(function (result) {

        var aux = '<tr>' +
            '<td>' +
            '<select class="form-control" id="hoursPerProjectList'+numberHoursPerProject+'.project.id" name="hoursPerProjectList[' + numberHoursPerProject + '].project.id" required>';

        aux += result;

        aux += '</select>' +
            '</td>' +
            '<td>' +
            '<input type="time" class="form-control" id="hoursPerProjectList'+numberHoursPerProject+'.duration" type="text" name="hoursPerProjectList[' + numberHoursPerProject + '].duration" required />' +
            '</td>' +
            '</tr>';
        listHoursPerProject.append(aux);


        numberHoursPerProject++;

    }).fail(function (jqXHR, textStatus) {
        alert(textStatus);
    });



    return false;
});
//Remove adress of client
$(document).on('click', '#removeHoursPerProject', function() {
    $('#listHoursPerProject>tr:last-child').remove();

    numberHoursPerProject--;

    return false;
});