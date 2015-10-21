function renumber() {
    $('#digestInformationDiv').each(function(j) {
    	$('div', this).each(function(i) {
    		$('input', this).each(function() {
	            $(this).attr('name', $(this).attr('name').replace(/digestInformation\[.+?\]/g, 'digestInformation[' + i + ']'));
            })

            $('button', this).each(function() {
	            $(this).attr('onclick', $(this).attr('onclick').replace(/removeDigestInformationFormElements\(.+?\)/g, 'removeDigestInformationFormElements(' + i + ')'));
            })
        })
    })
}

function removeDigestInformationFormElements(index) {
	$("#diDiv" + index).remove();
	renumber();
}