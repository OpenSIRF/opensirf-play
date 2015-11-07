function renumberExtensionPairs(extensionIndex) {
	$('#extensionPairsDiv' + extensionIndex).each(function(j) {
    	$('div', this).each(function(i) {
            $('input', this).each(function() {
            	newname = $(this).attr('name').replace(/objectExtensionPairs\[.+?\]/g, 'objectExtensionPairs[' + i + ']');
	            $(this).attr('name', newname);
            })
        })
    })
}

function removeExtensionPairFormElements(arg1, arg2) {
	if(arg2 == null) { // called from an event - dynamically added extension pair
		event = arg1;
		extensionIndex = event.data.param1;
		extensionPairIndex = event.data.param2;
	} else { // called from button onClick() - extension pair loaded from object
		extensionIndex = arg1;
		extensionPairIndex = arg2;
	}
	pairToRemove = "#epDiv" + extensionIndex + "-" + extensionPairIndex;
	code = $(pairToRemove).remove();
	renumberExtensionPairs(extensionIndex);
}

function addExtensionPairFormElements(arg) {
	if(arg.data != null) { // called from an event - dynamically added extension
		extIndex = arg.data.param1;
	} else { // called from button onClick() - extension pair loaded from object
		extIndex = arg;
	}
	
	var extensionPairDiv = $(document.createElement('div')).attr("id", 'epDiv' + extIndex + "-" + epIndex[extIndex]);
	var removeExtensionPairButton = $(document.createElement('button')).attr("id", 'removeExtensionPairButton' + extIndex + '.'+ epIndex[extIndex]).text('Remove');
	removeExtensionPairButton.click({param1: extIndex, param2: epIndex[extIndex]}, removeExtensionPairFormElements);
	extensionPairDiv.after().html(
			'objectExtension[' + extIndex + '].objectExtensionPairs['+ epIndex[extIndex] + '].objectExtensionKey <input type="text" name="objectExtension[' + extIndex + '].objectExtensionPairs['+ epIndex[extIndex] + '].objectExtensionKey" />' +
			' objectExtension[' + extIndex + '].objectExtensionPairs['+ epIndex[extIndex] + '].objectExtensionValue <input type="text" name="objectExtension[' + extIndex + '].objectExtensionPairs['+ epIndex[extIndex] + '].objectExtensionValue" />');
	extensionPairDiv.append(removeExtensionPairButton);
	$("#extensionPairsDiv" + extIndex).append(extensionPairDiv);
	epIndex[extIndex]++;
}