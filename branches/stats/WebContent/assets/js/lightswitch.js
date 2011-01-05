var iTimeoutId;

function singleClick(value) {
	iTimeoutId = setTimeout("turnLight('"+value+"')", 250);
}

function doubleClick(value) {
	if (iTimeoutId) {
		window.clearTimeout(iTimeoutId);
	}
	turnLight(value);
}

function turnLight(value) {
	window.location.href = "LightServlet.do?light="+value;
}