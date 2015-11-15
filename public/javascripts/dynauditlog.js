function renumberPreservationObjectAuditLogs() {
	var k = 0;
	$('#preservationObjectAuditLogsDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('preservationObjectAuditLogDiv')) { // ignore divs which aren't audit log divs
				return true;
			}
	        $('input', this).each(function() {
	        	oldname = $(this).attr('name');
	        	newname = $(this).attr('name').replace(/objectAuditLog\[.+?\]/g, 'objectAuditLog[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removePreservationObjectAuditLogFormElements(arg1) {
	if(arg1.data != null) { // called from an event - dynamically added related object
		event = arg1;
		preservationObjectAuditLogIndex = event.data.param1;
	} else { // called from button onClick() - related object loaded from object
		preservationObjectAuditLogIndex = arg1;
	}
	preservationObjectAuditLogToRemove = '#preservationObjectAuditLogDiv' + preservationObjectAuditLogIndex;
	$(preservationObjectAuditLogToRemove).remove();
	renumberPreservationObjectAuditLogs();
}

function addPreservationObjectAuditLogFormElements() {
	/*var preservationObjectAuditLogDiv = $(document.createElement('div')).attr('id', 'preservationObjectAuditLogDiv' + preservationObjectAuditLogCounter);
	var removePreservationObjectAuditLogButton = $(document.createElement('button')).attr('id', 'removePreservationObjectAuditLogButton' + preservationObjectAuditLogCounter).attr('type', 'button').text('Remove PO audit log');
	removePreservationObjectAuditLogButton.click({param1: preservationObjectAuditLogCounter}, removePreservationObjectAuditLogFormElements);
	preservationObjectAuditLogDiv.after().html(
			'auditLog[' + preservationObjectAuditLogCounter + '].reference.type <input type="text" name="objectAuditLog[' + preservationObjectAuditLogCounter + '].objectAuditLogReference.referenceType" />' +
			'auditLog[' + preservationObjectAuditLogCounter + '].reference.role <input type="text" name="objectAuditLog[' + preservationObjectAuditLogCounter + '].objectAuditLogReference.referenceRole" />' +
			'auditLog[' + preservationObjectAuditLogCounter + '].reference.value <input type="text" name="objectAuditLog[' + preservationObjectAuditLogCounter + '].objectAuditLogReference.referenceValue" />');
	preservationObjectAuditLogDiv.append(removePreservationObjectAuditLogButton);
	preservationObjectAuditLogDiv.insertBefore('#addPreservationObjectAuditLog');*/
	addDynamicFormElements('PreservationObjectAuditLog', preservationObjectAuditLogCounter, -1);
	preservationObjectAuditLogCounter++;
}