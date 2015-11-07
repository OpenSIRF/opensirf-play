function renumberIdentifiers() {
	var k = 0;
	$('#identifiersDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('identifierDiv')) { // ignore divs which aren't identifier divs
				return true;
			}
	        $('input', this).each(function() {
	        	newname = $(this).attr('name').replace(/objectIdentifier\[.+?\]/g, 'objectIdentifier[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removeIdentifierFormElements(arg1) {
	alert('removing');
	if(arg1.data != null) { // called from an event - dynamically added identifier
		event = arg1;
		identifierIndex = event.data.param1;
	} else { // called from button onClick() - identifier pair loaded from object
		identifierIndex = arg1;
	}
	identifierToRemove = '#identifierDiv' + identifierIndex;
	alert('removing ' + identifierToRemove);
	$(identifierToRemove).remove();
	//renumberIdentifiers();
}

/*function addIdentifierFormElements() {
	var identifierDiv = $(document.createElement('div')).attr("id", 'identifierDiv' + identifierCounter);
	var objectNamesDiv = $(document.createElement('div')).attr("id", 'objectNamesDiv' + identifierCounter);
	var removeIdentifierButton = $(document.createElement('button')).attr("id", 'removeIdentifierButton' + identifierCounter).attr("type", "button").text('Remove identifier');
	var addObjectNameButton = $(document.createElement('button')).attr("id", 'addPair' + identifierCounter).attr("type", "button").text('Add identifier pair');
	removeIdentifierButton.click({param1: identifierCounter}, removeIdentifierFormElements);
	addObjectNameButton.click({param1: identifierCounter}, addObjectNameFormElements);
	identifierDiv.after().html(
			'objectIdentifier[' + identifierCounter + '].objectIdentifierOrganization <input type="text" name="objectIdentifier[' + identifierCounter + '].objectIdentifierOrganization" />' +
			' objectIdentifier[' + identifierCounter + '].objectIdentifierDescription <input type="text" name="objectIdentifier[' + identifierCounter + '].objectIdentifierDescription" />' +
			'<br />');
	identifierDiv.append(objectNamesDiv);
	identifierDiv.append(addObjectNameButton);
	identifierDiv.append(removeIdentifierButton);
	$("#identifiersDiv").append(identifierDiv);
	epIndex[identifierCounter] = 0;
	identifierCounter++;
}*/
