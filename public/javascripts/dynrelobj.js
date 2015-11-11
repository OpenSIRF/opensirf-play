function renumberRelatedObjects() {
	var k = 0;
	$('#relatedObjectsDiv').each(function(j) {
		$('div', this).each(function(i,item) {
			if(!item.id.match('relatedObjectDiv')) { // ignore divs which aren't related object divs
				return true;
			}
	        $('input', this).each(function() {
	        	oldname = $(this).attr('name');
	        	newname = $(this).attr('name').replace(/objectRelatedObjects\[.+?\]/g, 'objectRelatedObjects[' + k + ']');
	        	$(this).attr('name', newname);
            })
        k++;
		})
    })
}

function removeRelatedObjectFormElements(arg1) {
	if(arg1.data != null) { // called from an event - dynamically added related object
		event = arg1;
		relatedObjectIndex = event.data.param1;
	} else { // called from button onClick() - related object loaded from object
		relatedObjectIndex = arg1;
	}
	relatedObjectToRemove = '#relatedObjectDiv' + relatedObjectIndex;
	$(relatedObjectToRemove).remove();
	renumberRelatedObjects();
}

function addRelatedObjectFormElements() {
	var relatedObjectDiv = $(document.createElement('div')).attr('id', 'relatedObjectDiv' + relatedObjectCounter);
	var removeRelatedObjectButton = $(document.createElement('button')).attr('id', 'removeRelatedObjectButton' + relatedObjectCounter).attr('type', 'button').text('Remove related object reference');
	removeRelatedObjectButton.click({param1: relatedObjectCounter}, removeRelatedObjectFormElements);
	relatedObjectDiv.after().html(
			'objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceType <input type="text" name="objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceType" />' +
			'objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceRole <input type="text" name="objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceRole" />' +
			'objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceValue <input type="text" name="objectRelatedObjects[' + relatedObjectCounter + '].objectRelatedObjectReference.referenceValue" />');
	relatedObjectDiv.append(removeRelatedObjectButton);
	$("#relatedObjectsDiv").append(relatedObjectDiv);
	relatedObjectCounter++;
}
