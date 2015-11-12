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
	var identifierDiv = $(document.createElement('div')).attr('id', 'identifierDiv' + identifierCounter).attr('class', 'subcategory1');
	var objectNamesDiv = $(document.createElement('div')).attr('id', 'objectNamesDiv' + identifierCounter).attr('class', 'subcategory2');
	var removeIdentifierButton = $(document.createElement('button')).attr('id', 'removeIdentifierButton' + identifierCounter).attr('type', 'button').text('Remove identifier');
	var addObjectNameButton = $(document.createElement('button')).attr('id', 'addObjectName' + identifierCounter).attr('type', 'button').text('Add preservation object name');
	removeIdentifierButton.click({param1: identifierCounter}, removeIdentifierFormElements);
	addObjectNameButton.click({param1: identifierCounter}, addObjectNameFormElements);
	identifierDiv.after().html(
			'<h5>identifiers[' + identifierCounter + ']:</h5>' +
			'identifiers[' + identifierCounter + '].versionIdentifier.type <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierType" /> ' +
			'identifiers[' + identifierCounter + '].versionIdentifier.locale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierLocale" /> ' +
			'identifiers[' + identifierCounter + '].versionIdentifier.value <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectVersionIdentifier.objectIdentifierValue" /> ' +
			'<br /> ' + 			
			'identifiers[' + identifierCounter + '].parentIdentifier .type <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierType" /> ' +
			'identifiers[' + identifierCounter + '].parentIdentifier .locale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierLocale" /> ' +
			'identifiers[' + identifierCounter + '].parentIdentifier .value <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectParentIdentifier.objectIdentifierValue" /> ' +
			'<br /> ' + 			
			'identifiers[' + identifierCounter + '].logicalIdentifier.type <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierType" /> ' +
			'identifiers[' + identifierCounter + '].logicalIdentifier.locale <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierLocale" /> ' +
			'identifiers[' + identifierCounter + '].logicalIdentifier.value <input type="text" name="objectIdentifiers[' + identifierCounter + '].objectLogicalIdentifier.objectIdentifierValue" /> ' +
			'<br /> ');
	identifierDiv.append(objectNamesDiv);
	identifierDiv.append(addObjectNameButton);
	identifierDiv.append(removeIdentifierButton);
	identifierDiv.insertBefore('#addIdentifier');
	onIndex[identifierCounter] = 0;
	identifierCounter++;
}
