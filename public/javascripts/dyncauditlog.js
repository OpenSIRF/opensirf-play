function renumberContainerAuditLogs() {
	var k = 0;
	$('#containerAuditLogsDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('containerAuditLogDiv')) { // ignore divs which aren't audit log divs
				return true;
			}
	        $('input', this).each(function() {
	        	oldname = $(this).attr('name');
	        	newname = $(this).attr('name').replace(/containerAuditLog\[.+?\]/g, 'containerAuditLog[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removeContainerAuditLogFormElements(arg1) {
	if(arg1.data != null) { // called from an event - dynamically added related object
		event = arg1;
		containerAuditLogIndex = event.data.param1;
	} else { // called from button onClick() - related object loaded from object
		containerAuditLogIndex = arg1;
	}
	containerAuditLogToRemove = '#containerAuditLogDiv' + containerAuditLogIndex;
	$(containerAuditLogToRemove).remove();
	renumberContainerAuditLogs();
}

function addContainerAuditLogFormElements() {
	addDynamicFormElements('ContainerAuditLog', containerAuditLogCounter, -1);
	containerAuditLogCounter++;
}