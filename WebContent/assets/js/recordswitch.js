var recordButtonIsPressed = false;
var isRecording = "off";

$(document).ready(function(){
	getRecordState();
	setInterval("getRecordState()", 3000);
});

function getRecordState() {
	$.getJSON("RecordServlet.do?record=" + isRecording, function(json) {
		if (json != undefined) {
			changeRecordSwitchPage(json.recordOn);
		}
	});
}

function changeRecordSwitchPage(recordOn) {
	var button = document.getElementById("recordbutton");
	
	if (recordOn) {
		button.innerHTML = "Stop";
		button.setAttribute("onclick", "singleRecordClick('off')");
		isRecording = "on";
	} else {
		button.innerHTML = "Record";
		button.setAttribute("onclick", "singleRecordClick('on')");
		isRecording = "off";
	}
}


function singleRecordClick(value) {
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