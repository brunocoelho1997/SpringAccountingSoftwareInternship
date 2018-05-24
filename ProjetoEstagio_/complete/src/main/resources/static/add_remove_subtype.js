var listSubtypes = $('#listSubtypes');
var numberSubtypes = $('#listSubtypes tr').size();

// alert(numberSubtypes);
// alert(listSubtypes);

$('#addSubtype').click(function() {
    listSubtypes.append('<tr>' +
        '<td>' +
        '<input class="form-control" type="text" id="subTypeList'+numberSubtypes+'.name" name="subTypeList[' + numberSubtypes + '].name" required maxlength="[[${T(hello.SubType.SubType).MAX_NAME_LENGHT}]]"/>' +
        '</td>' +
        '</tr>');
    numberSubtypes++;
    return false;
});
//Remove adress of client
$(document).on('click', '#removeSubtype', function() {
    $('#listSubtypes>tr:last-child').remove();

    // $(this).closest('tr').remove();
    numberSubtypes--;

    return false;
});