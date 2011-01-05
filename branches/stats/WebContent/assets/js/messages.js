function msgPrint(errors, success) {
	var messages = document.getElementById('messages');
	
	if (errors != '' ) {
		var errorsDiv = document.createElement('div');
		errorsDiv.className = 'errors';
		errorsDiv.innerHTML = errors;
		messages.appendChild(errorsDiv);
	}
	
	if (success != '') {
		var successDiv = document.createElement('div');
		successDiv.className = 'success';
		successDiv.innerHTML = success;
		messages.appendChild(successDiv);
	}
}

function msgCleanup() {
	var messages = document.getElementById('messages');
	messages.innerHTML = '';
}