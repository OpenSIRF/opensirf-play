function renumber() {
    $('#extensionPairsDiv').each(function(j) {
    	$('div', this).each(function(i) {
    		$('input', this).each(function() {
	            $(this).attr('name', $(this).attr('name').replace(/objectExtensionPairs\[.+?\]/g, 'objectExtensionPairs[' + i + ']'));
            })

            $('button', this).each(function() {
	            $(this).attr('onclick', $(this).attr('onclick').replace(/removeExtensionPairFormElements\(.+?\)/g, 'removeExtensionPairFormElements(' + i + ')'));
            })
        })
    })
}

function removeExtensionPairFormElements(index) {
	$("#epDiv" + index).remove();
	renumber();
}