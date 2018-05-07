var listAdresses = $('#listAdresses');
var numberAdresses = $('#listAdresses tr').size();

$('#addAdress').click(function() {

    listAdresses.append('<tr>' +
        '<td>' +
        '<input class="form-control" type="text" id="adresses'+numberAdresses+'.city" name="adresses[' + numberAdresses + '].city" required maxlength="[[${T(hello.Adress.Adress).MAX_CITY_LENGHT}]]"/>' +
        '</td>' +
        '<td><input class="form-control" type="text" id="adresses'+numberAdresses+'.adressName" name="adresses[' + numberAdresses + '].adressName" required maxlength="[[${T(hello.Adress.Adress).MAX_ADRESSNAME_LENGHT}]]"/>' +
        '</td>' +
        '<td><input class="form-control" type="text" id="adresses'+numberAdresses+'.zipCode" name="adresses[' + numberAdresses + '].zipCode" required maxlength="[[${T(hello.Adress.Adress).MAX_ZIPCODE_LENGHT}]]"/>' +
        '</td>' +
        '<td><input class="form-control" type="number" id="adresses'+numberAdresses+'.number" name="adresses[' + numberAdresses + '].number" required maxlength="[[${T(hello.Adress.Adress).MAX_NUMBER_SIZE}]]"/>' +
        '</td>' +
        '</tr>');
    numberAdresses++;
    return false;
});
//Remove adress of client
$(document).on('click', '#removeAdress', function() {
    if (numberAdresses > 1)
    {
        $('#listAdresses>tr:last-child').remove();

        // $(this).closest('tr').remove();
        numberAdresses--;
    }
    return false;
});