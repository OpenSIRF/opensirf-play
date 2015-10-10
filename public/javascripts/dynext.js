function addExtensionPairFormElements() {
	var extensionPairDiv = $(document.createElement('div')).attr("id", 'epDiv' + counter);
	var removeExtensionPairButton = $(document.createElement('button')).
	 	attr("id", 'removeExtensionPairButton'+ counter).
	 	text('Remove');	    	         
	removeExtensionPairButton.click({param1: counter}, removeExtensionPairFormElements);
	extensionPairDiv.after().html(
			'objectExtensionKey: <input type="text" name="objectExtensionPairs['+ counter + '].objectExtensionKey" />' +
			' objectExtensionValue: <input type="text" name="objectExtensionPairs['+ counter + '].objectExtensionValue" />');
	extensionPairDiv.append(removeExtensionPairButton);
	$("#extensionPairsDiv").append(extensionPairDiv);
	counter++;
}

function renumber() {
    $('#extensionPairsDiv').each(function(j) {
    	$('div', this).each(function(i) {
            $('input', this).each(function() {
	            $(this).attr('name', $(this).attr('name').replace(/objectExtensionPairs\[.+?\]/g, 'objectExtensionPairs[' + i + ']'));
            })
        })
    })
}

function removeExtensionPairFormElements(event) {
	alert("Removing " + event.data.param1);
	$("#epDiv" + event.data.param1).remove();
	renumber();
}
