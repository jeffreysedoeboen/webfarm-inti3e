var iTimeoutId;
var lightButtonIsPressed = false;

$(document).ready(function(){
	getLightState();
	setInterval("getLightState()", 3000);
});

function getLightState() {
	$.getJSON("LightServlet.do?param=state", function(json) {
		changeLightSwitchPage(json.lightOn);
	});
}

function changeLightSwitchPage(lightOn) {
	var status = document.getElementById("lightstatus");
	var message = document.getElementById("lightmessage");
	var button = document.getElementById("lightbutton");
	
	var onoff = "off";
	if (lightOn) { onoff = "on"; } else { onoff = "off"; }
	
	status.innerHTML = "Currently the light is: " + onoff;
	message.innerHTML = "Turn the light:";
	if (lightOn) {
		button.innerHTML = "<a href=\"#\" onclick=\"singleClick('off')\" ondblclick=\"doubleClick('on')\"><img width=\"100\" height=\"100\" border=\"0\" alt=\"Off\" src=\"image/offbutton.png\" /></a>";
	} else {
		button.innerHTML = "<a href=\"#\" onclick=\"singleClick('on')\" ondblclick=\"doubleClick('off')\"><img width=\"100\" height=\"100\" border=\"0\" alt=\"On\" src=\"image/onbutton.png\" /></a>";
	}
}


function turnLight(value) {
	if (!lightButtonIsPressed) {
		lightButtonIsPressed = true;
		lightButtonTimeout = setTimeout("resetLightButtonPress()", 3000);
		
		xmlhttp = getXMLHTTPRequest();
		xmlhttp.open("POST","LightServlet.do?param=turn&light="+value);
		xmlhttp.send();
		
		var lightOn = (value == "on");
		changeLightSwitchPage(lightOn);
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


function resetLightButtonPress() {
	lightButtonIsPressed = false;
}


function singleClick(value) {
	iTimeoutId = setTimeout("turnLight('"+value+"')", 500);
}

function doubleClick(value) {
	if (iTimeoutId) {
		window.clearTimeout(iTimeoutId);
	}
	turnLight(value);
}
