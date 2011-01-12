var recordButtonIsPressed = false;

$(document).ready(function(){
	getRecordState();
	setInterval("getRecordState()", 3000);
});

function getRecordState() {
	$.getJSON("RecordServlet.do", function(json) {
		changeRecordSwitchPage(json.recordOn);
	});
}

function changeRecordSwitchPage(recordOn) {
	var button = document.getElementById("recordbutton");
	
	var onoff = "off";
	if (recordOn) { onoff = "on"; } else { onoff = "off"; }
	

	if (recordOn) {
		button.innerHTML = "<button type=\"submit\" onclick=\"singleClick('off')\" id=\"recordbutton\">Stop</button>";
	} else {
		button.innerHTML = "<button type=\"submit\" onclick=\"singleClick('on')\" id=\"recordbutton\">Record</button>";;
	}
}


function turnRecord(value) {
	if (!recordButtonIsPressed) {
		recordButtonIsPressed = true;
		recordButtonTimeout = setTimeout("resetRecordButtonPress()", 3000);
		
		xmlhttp = getXMLHTTPRequest();
		xmlhttp.open("POST","RecordServlet.do?record="+value);
		xmlhttp.send();
		
		var recordOn = (value == "on");
		changeRecordSwitchPage(recordOn);
	}
}

function getXMLHTTPRequest() {
	if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else { // code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}


function resetRecordButtonPress() {
	recordButtonIsPressed = false;
}


function singleRecordClick(value) {
	turnRecord(value);
}
