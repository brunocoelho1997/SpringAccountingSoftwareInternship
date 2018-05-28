var listStringContacts = $('#listStringContacts');
var numberStringContacts = $('#listStringContacts tr').size();

$('#addStringContact').click(function() {

    listStringContacts.append('<tr>' +
        '<td>' +
        '<input class="form-control" type="text" id="contacts'+numberStringContacts+'.contact" name="contacts[' + numberStringContacts + '].contact" required maxlength="[[${T(hello.Supplier.Resources.StringContact).MAX_CONTACT_LENGHT}]]"/>' +
        // '<p class="text-danger" th:if="${#fields.hasErrors(contacts'+numberStringContacts+'.contact)}" th:errors="*{contacts[__${'+numberStringContacts+'}__].contact}">Name Error</p>'+
        '</td>' +
        '</tr>');
    numberStringContacts++;
    return false;
});
//Remove adress of client
$(document).on('click', '#removeStringContact', function() {
    if (numberStringContacts > 1)
    {
        $('#listStringContacts>tr:last-child').remove();

        // $(this).closest('tr').remove();
        numberStringContacts--;
    }
    return false;
});