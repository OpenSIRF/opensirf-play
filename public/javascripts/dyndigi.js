function removeDigestInformationFormElements(arg) {
	if(arg.data == null) {
		index = arg;
	} else {
		index = arg.data.param1;
	}

	$("#diDiv" + index).remove();
	renumberDigestInformation();
}

function addDigestInformationFormElements() {
	var digestInformationDiv = $(document.createElement('div')).attr("id", 'diDiv' + digestCounter);
	var removeDigestInformationButton = $(document.createElement('button')).
	 	attr("id", 'removeDigestInformationButton'+ digestCounter).
	 	text('Remove');
	removeDigestInformationButton.click({param1: digestCounter}, removeDigestInformationFormElements);
	digestInformationDiv.after().html(
			'digestInfo['+ digestCounter + '].digestOriginator <input type="text" name="objectFixity.digestInformation['+ digestCounter + '].digestOriginator" /> ' +
			'digestInfo['+ digestCounter + '].digestAlgorithm <input type="text" name="objectFixity.digestInformation['+ digestCounter + '].digestAlgorithm" /> ' +
			'digestInfo['+ digestCounter + '].digestValue <input type="text" name="objectFixity.digestInformation['+ digestCounter + '].digestValue" /> ');
	digestInformationDiv.append(removeDigestInformationButton);
	$("#digestInformationDiv").append(digestInformationDiv);
	digestCounter++;
}

function renumberDigestInformation() {
    $('#digestInformationDiv').each(function(j) {
    	$('div', this).each(function(i) {
            $('input', this).each(function() {
	            $(this).attr('name', $(this).attr('name').replace(/digestInformation\[.+?\]/g, 'digestInformation[' + i + ']'));
            })
        })
    })
}