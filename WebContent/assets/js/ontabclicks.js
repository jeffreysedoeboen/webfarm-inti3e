function onMainTabClick() {
	hit('Main');
	document.getElementById("datepick").style.display='none';
	//datepick.style.display='none';
}

function onLiveTabClick() {
	hit('Live stream');
	document.getElementById("datepick").style.display='none';
}

function onPlayTabClick() {
	hit('Video playback');
	document.getElementById("datepick").style.display='none';
}

function onUserstatsTabClick() {
	hit('User Statistics');
	document.getElementById("datepick").style.display='none';
}

function onTempTabClick() {
	hit('Temperature');
	document.getElementById("datepick").style.display='block';
	setTimeout("drawTempChart(lastTempJson)", 500);
}

function onHumidTabClick() {
	hit('Air humidity');
	document.getElementById("datepick").style.display='block';
	setTimeout("drawHumidityChart(lastHumidityJson)", 500);
}

function onIllumTabClick() {
	hit('Illumination');
	document.getElementById("datepick").style.display='block';
	setTimeout("drawLightChart(lastLightJson)", 500);
}

function onDoorTabClick() {
	hit('Door');
	document.getElementById("datepick").style.display='block';
	setTimeout("drawDoorChart(lastDoorJson)", 500);
}

function onSwitchTabClick() {
	hit('Light switch');
	document.getElementById("datepick").style.display='none';
}

function onCreateTabClick() {
	hit('Create account');
	document.getElementById("datepick").style.display='none';
}

function onManageAccountsTabClick() {
	hit('Manage Accounts');
	fillAccountsTable();
	document.getElementById("datepick").style.display='none';
}

function onStatsTabClick() {
	document.getElementById("datepick").style.display='none';
}

function onSourcesTabClick() {
	hit('Sources');
	document.getElementById("datepick").style.display='none';
}