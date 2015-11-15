function renumberObjectNames(identifierIndex) {
	$('#objectNamesDiv' + identifierIndex).each(function(j) {
    	$('div', this).each(function(i) {
            $('input', this).each(function() {
            	newname = $(this).attr('name').replace(/objectNames\[.+?\]/g, 'objectNames[' + i + ']');
	            $(this).attr('name', newname);
            })
        })
    })
}

function removeObjectNameFormElements(arg1, arg2) {
	if(arg2 == null) { // called from an event - dynamically added extension pair
		event = arg1;
		identifierIndex = event.data.param1;
		objectNameIndex = event.data.param2;
	} else { // called from button onClick() - extension pair loaded from object
		identifierIndex = arg1;
		objectNameIndex = arg2;
	}
	pairToRemove = '#onDiv' + identifierIndex + '-' + objectNameIndex;
	$(pairToRemove).remove();
	renumberObjectNames(identifierIndex);
}

function addObjectNameFormElements(arg) {
	if(arg.data != null) { // called from an event - dynamically added identifier
		idIndex = arg.data.param1;
	} else { // called from button onClick() - identifier loaded from object
		idIndex = arg;
	}
	
	/*var objectNameDiv = $(document.createElement('div')).attr('id', 'onDiv' + idIndex + "-" + onIndex[idIndex]);
	var removeObjectNameButton = $(document.createElement('button')).attr('id', 'removeObjectNameButton' + idIndex + '.'+ onIndex[idIndex]).text('Remove');
	removeObjectNameButton.click({param1: idIndex, param2: onIndex[idIndex]}, removeObjectNameFormElements);
	objectNameDiv.after().html(
			'identifiers[' + idIndex + '].names['+ onIndex[idIndex] + '].type <input type="text" name="objectIdentifiers[' + idIndex + '].objectNames['+ onIndex[idIndex] + '].objectIdentifierType" /> ' +
			'identifiers[' + idIndex + '].names['+ onIndex[idIndex] + '].locale <input type="text" name="objectIdentifiers[' + idIndex + '].objectNames['+ onIndex[idIndex] + '].objectIdentifierLocale" /> ' +
			'identifiers[' + idIndex + '].names['+ onIndex[idIndex] + '].value <input type="text" name="objectIdentifiers[' + idIndex + '].objectNames['+ onIndex[idIndex] + '].objectIdentifierValue" /> ');
	objectNameDiv.append(removeObjectNameButton);
	if(onIndex[idIndex] == 0) {
		$('#objectNamesDiv' + idIndex).append('<h6>identifiers[' + idIndex + '].names:</h6>');
	}
	$('#objectNamesDiv' + idIndex).append(objectNameDiv);*/
	
	addDynamicFormElements('ObjectName', idIndex, onIndex[idIndex]);
	onIndex[idIndex]++;
}
