function renumberIdentifiers() {
	var k = 0;
	$('#identifiersDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('identifierDiv')) { // ignore divs which aren't identifier divs
				return true;
			}
	        $('input', this).each(function() {
	        	oldname = $(this).attr('name');
	        	newname = $(this).attr('name').replace(/objectIdentifiers\[.+?\]/g, 'objectIdentifiers[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removeIdentifierFormElements(arg1) {
	if(arg1.data != null) { // called from an event - dynamically added identifier
		event = arg1;
		identifierIndex = event.data.param1;
	} else { // called from button onClick() - identifier loaded from object
		identifierIndex = arg1;
	}
	identifierToRemove = '#identifierDiv' + identifierIndex;
	$(identifierToRemove).remove();
	renumberIdentifiers();
}

function addIdentifierFormElements() {
	var identifierDiv = $(document.createElement('div')).attr("id", 'identifierDiv' + identifierCounter);
	var objectNamesDiv = $(document.createElement('div')).attr("id", 'objectNamesDiv' + identifierCounter);
	var removeIdentifierButton = $(document.createElement('button')).attr("id", 'removeIdentifierButton' + identifierCounter).attr("type", "button").text('Remove identifier');
	var addObjectNameButton = $(document.createElement('button')).attr("id", 'addObjectName' + identifierCounter).attr("type", "button").text('Add preservation object name');
	removeIdentifierButton.click({param1: identifierCounter}, removeIdentifierFormElements);
	addObjectNameButton.click({param1: identifierCounter}, addObjectNameFormElements);
	identifierDiv.after().html(
			'objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierType <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierType" />' +
			'objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierLocale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierLocale" />' +
			'objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierValue <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierValue" />' +
			'<br />' + 			
			'objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierType <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierType" />' +
			'objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierLocale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierLocale" />' +
			'objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierValue <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierValue" />' +
			'<br />' + 			
			'objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierType <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierType" />' +
			'objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierLocale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierLocale" />' +
			'objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierValue <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierValue" />' +
			'<br />');
	identifierDiv.append(objectNamesDiv);
	identifierDiv.append(addObjectNameButton);
	identifierDiv.append(removeIdentifierButton);
	$("#identifiersDiv").append(identifierDiv);
	onIndex[identifierCounter] = 0;
	identifierCounter++;
}
