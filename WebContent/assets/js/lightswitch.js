var iTimeoutId;

$(document).ready(function(){
	getLightState();
	setInterval("getLightState()", 3000);
});

function getLightState() {
	$.getJSON("LightServlet.do?param=state", function(json) {
		changeLightSwitchPage(json);
	});
}

function changeLightSwitchPage(json) {
	var lightOn = json.lightOn;
	
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
	window.location.href = "LightServlet.do?param=turn&light="+value;
}


function singleClick(value) {
	iTimeoutId = setTimeout("turnLight('"+value+"')", 250);
}

function doubleClick(value) {
	if (iTimeoutId) {
		window.clearTimeout(iTimeoutId);
	}
	turnLight(value);
}
