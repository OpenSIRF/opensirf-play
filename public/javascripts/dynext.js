function renumberExtensions() {
	var k = 0;
	$('#extensionsDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('extensionDiv')) { // ignore divs which aren't extension divs
				return true;
			}
	        $('input', this).each(function() {
	        	newname = $(this).attr('name').replace(/objectExtension\[.+?\]/g, 'objectExtension[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removeExtensionFormElements(arg1) {
	if(arg1.data != null) { // called from an event - dynamically added extension
		event = arg1;
		extensionIndex = event.data.param1;
	} else { // called from button onClick() - extension pair loaded from object
		extensionIndex = arg1;
	}
	extensionToRemove = '#extensionDiv' + extensionIndex;
	$(extensionToRemove).remove();
	renumberExtensions();
}

function addExtensionFormElements() {
	/*var extensionDiv = $(document.createElement('div')).attr('id', 'extensionDiv' + extensionCounter);
	var extensionPairsDiv = $(document.createElement('div')).attr('id', 'extensionPairsDiv' + extensionCounter);
	var removeExtensionButton = $(document.createElement('button')).attr('id', 'removeExtensionButton' + extensionCounter).attr("type", "button").text('Remove extension');
	var addExtensionPairButton = $(document.createElement('button')).attr('id', 'addPair' + extensionCounter).attr("type", "button").text('Add extension pair');
	removeExtensionButton.click({param1: extensionCounter}, removeExtensionFormElements);
	addExtensionPairButton.click({param1: extensionCounter}, addExtensionPairFormElements);
	extensionDiv.after().html(
			'extension[' + extensionCounter + '].organization <input type="text" name="objectExtension[' + extensionCounter + '].objectExtensionOrganization" />' +
			' extension[' + extensionCounter + '].description <input type="text" name="objectExtension[' + extensionCounter + '].objectExtensionDescription" />' +
			'<br />');
	extensionDiv.append(extensionPairsDiv);
	extensionDiv.append(addExtensionPairButton);
	extensionDiv.append(removeExtensionButton);
	extensionDiv.insertBefore('#addExtension');*/
	addDynamicFormElements('Extension', extensionCounter, -1);
	epIndex[extensionCounter] = 0;
	extensionCounter++;	
}
