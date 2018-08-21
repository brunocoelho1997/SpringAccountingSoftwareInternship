/*
add adress for a contact
 */
function addAdressContact(idContact) {

    var listContactAdresses = $("#listContactAdresses\\[" + idContact + "\\]");
    var numberlistContactAdresses = $("#listContactAdresses\\[" + idContact + "\\] tr").length;

    listContactAdresses.append('<tr>' +
        '<td><input class="form-control" type="text" id="contacts'+idContact+'.adresses'+numberlistContactAdresses+ '.city" name="contacts[' + idContact + '].adresses[' + numberlistContactAdresses + '].city" required maxlength="[[${T(hello.Adress.Adress).MAX_CITY_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="text" id="contacts'+idContact+'.adresses'+numberlistContactAdresses+ '.adressName" name="contacts[' + idContact + '].adresses[' + numberlistContactAdresses + '].adressName" required maxlength="[[${T(hello.Adress.Adress).MAX_ADRESSNAME_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="text" id="contacts'+idContact+'.adresses'+numberlistContactAdresses+ '.zipCode" name="contacts[' + idContact + '].adresses[' + numberlistContactAdresses + '].zipCode" required maxlength="[[${T(hello.Adress.Adress).MAX_ZIPCODE_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="number" id="contacts'+idContact+'.adresses'+numberlistContactAdresses+ '.number" name="contacts[' + idContact + '].adresses[' + numberlistContactAdresses + '].number" required maxlength="[[${T(hello.Adress.Adress).MAX_NUMBER_SIZE}]]"/></td>' +
        '</tr>');
    return false;
}
/*
remove adress of a contact
 */
function removeAdressContact(idContact) {
    var ultimoElementoTabela = $("#listContactAdresses\\[" + idContact + "\\] > tr:last-child");
    var numberlistContactAdresses = $("#listContactAdresses\\[" + idContact + "\\] tr").length;

    if (numberlistContactAdresses > 1)
    {
        ultimoElementoTabela.remove();
    }
}




// ----------------------------Contacts
var listContacts = $('#listContacts');
var numberContacts = $('#listContacts>tr').size()-1;

$('#addContact').click(function() {

    var $options = $("#listPostContacts").clone();

    listContacts.append('<tr class="warning">' +
        '<td><input class="form-control" type="text" id="contacts'+numberContacts+'.name" name="contacts[' + numberContacts + '].name" required maxlength="[[${T(hello.Contact.Contact).MAX_NAME_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="email" id="contacts'+numberContacts+'.email" name="contacts[' + numberContacts + '].email" maxlength="[[${T(hello.Contact.Contact).MAX_EMAIL_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="text" id="contacts'+numberContacts+'.numberPhone" name="contacts[' + numberContacts + '].numberPhone" required maxlength="[[${T(hello.Contact.Contact).MAX_NUMBERPHONE_LENGHT}]]"/></td>' +
        '<td><select class="form-control selectedClient" id="contacts'+numberContacts+'.postContact" name="contacts[' + numberContacts + '].postContact"  required>' +
        $options.html() +
        '</select></td>' +
        '</tr>'+
        '<tr>'+
        '<td colspan="5"'+
        '<div class="row">'+
        '<div class="col-sm-1"></div>\n' +
        '<div class="col-sm-10">' +
        '<table width="100%" class="table" style="background-color: transparent;">'+
        '<thead><tr><th>[[#{Adress.City}]]</th><th>[[#{Adress.Adress}]]</th><th>[[#{Adress.ZipCode}]]</th><th>[[#{Adress.Number}]]</th><th></th></tr></thead>'+
        '<tbody id="listContactAdresses[' + numberContacts + ']" >' +
        '<p>[[#{Adress.PanelHeader}]]</p>'+
        '<tr>' +
        '<td><input class="form-control" type="text" id="contacts'+numberContacts+'.adresses0.city" name="contacts[' + numberContacts + '].adresses[0].city" required maxlength="[[${T(hello.Adress.Adress).MAX_CITY_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="text" id="contacts'+numberContacts+'.adresses0.adressName" name="contacts[' + numberContacts + '].adresses[0].adressName" required maxlength="[[${T(hello.Adress.Adress).MAX_ADRESSNAME_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="text" id="contacts'+numberContacts+'.adresses0.zipCode" name="contacts[' + numberContacts + '].adresses[0].zipCode" required maxlength="[[${T(hello.Adress.Adress).MAX_ZIPCODE_LENGHT}]]"/></td>' +
        '<td><input class="form-control" type="number" id="contacts'+numberContacts+'.adresses0.number" name="contacts[' + numberContacts + '].adresses[0].number" required maxlength="[[${T(hello.Adress.Adress).MAX_NUMBER_SIZE}]]"/></td>' +
        '<td>' +
        '</td>' +
        '</tr>'+
        '</tbody>'+
        '</table>'+
        '<div align="right">'+
        '<a class="btn btn-danger pull-right" onclick="removeAdressContact('+numberContacts+')" ><i class="glyphicon glyphicon-remove "/></i></a>'+
        '<a class="btn btn-default pull-right" onclick="addAdressContact('+numberContacts+')" >[[#{Adress.Contact.Add}]]</a>'+
        '</div>'+
        '</div>'+
        '</td>'+
        '</tr>'

    );
    numberContacts++;
    return false;
});

//Remove contact
$(document).on('click', '#removeContact', function() {
    if (numberContacts > 1)
    {
        $('#listContacts>tr:last-child').before().remove();
        $('#listContacts>tr:last-child').remove();
        numberContacts--;
    }
    return false;
});