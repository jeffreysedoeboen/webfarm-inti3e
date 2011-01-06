var lastTempDate = "", lastDoorDate = "", lastHumidityDate = "", lastLightDate = "";
var lastTempTime = "", lastDoorTime = "", lastHumidityTime = "", lastLightTime = "";
var lastTempJson = null, lastDoorJson = null, lastHumidityJson = null, lastLightJson = null;
var buttonIsPressed = false;

$(document).ready(function(){
	setInterval("autoupdate()", 10000);
});

function autoupdate() {
	updateTemp();
	updateHumidity();
	updateDoor();
	updateLight();
}

function createAllTables() {
	if (!buttonIsPressed) {
		buttonIsPressed = true;
		buttonTimeout = setTimeout("resetButtonPress()", 3000);
		createTempTable();
		createHumidityTable();
		createDoorTable();
		createLightTable();
	}
}

function resetButtonPress() {
	buttonIsPressed = false;
}

function hit(page) {
	if(page == "User Statistics") {
		getStatistics();
	} else {
		$.getJSON("analytics.jpg?page=" + page, function(json) {});
	}
}


function getVidListByDate() {
	var date1 = document.getElementById("date_vidplayback");
	$.getJSON("VideoServlet.do?id=playback&date1="+date1, function(json) {
		fillPlaybackTable(json);
	});
}

function fillPlaybackTable(json) {
	var table = document.getElementById("playback_table_body");
	table.innerHTML = "";
	
		for (var i = 0; i < json.temp.length; i++) {
			var trElem = document.createElement("tr");
			var tdVideo = document.createElement("td");

			tdVideo.setAttribute('align','center');
		
			tdVideo.innerHTML = changeDateFormat(json.video[i].name);
		
			trElem.appendChild(tdVideo);
			table.appendChild(trElem);
		}
}

function showPlayer(name) {
	var player = document.getElementById("videoplayer");
	player.innerHTML = "<a href='" + name + "'	style='display:block;width:425px;height:300px;'	id='player'></a>";
}


function getTempByDate(date1, date2, time1, time2) {
	$.getJSON("DateServlet.do?id=temp&date1="+date1 + "&date2=" + date2 + "&time1=" + time1 + "&time2=" + time2, function(json) {
		if (lastTempJson == null) {
			lastTempJson = json;
		} else {
			lastTempJson = mergeJson(lastTempJson,json);
		}
		fillTempTable(json);
		drawTempChart(lastTempJson);
	});
}

function updateTemp() {
	if (lastTempDate != null && !lastTempDate == "" &&
			lastTempTime != null && !lastTempTime == "") {
		var date2 = document.getElementById("date_pick2").value;
		var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
		getTempByDate(lastTempDate, date2, lastTempTime, time2);
	}
}

function createTempTable() {
	var table = document.getElementById("temp_table_body");
	var date1 = document.getElementById("date_pick1").value;
	var date2 = document.getElementById("date_pick2").value;
	var time1 = document.getElementById("pick_hour1").value + ":" + document.getElementById("pick_minutes1").value;
	var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
	table.innerHTML = "";
	lastTempJson = null;
	getTempByDate(date1, date2, time1, time2);
}

function fillTempTable(json) {
	var table = document.getElementById("temp_table_body");
	
	for (var i = 0; i < json.temp.length; i++) {
		var trElem = document.createElement("tr");
		var tdDate = document.createElement("td");
		var tdTime = document.createElement("td");
		var tdTemp = document.createElement("td");

		tdDate.setAttribute('align','center');
		tdTime.setAttribute('align','center');
		tdTemp.setAttribute('align','center');
		
		tdDate.innerHTML = changeDateFormat(json.temp[i].date);
		tdTime.innerHTML = json.temp[i].time;
		tdTemp.innerHTML = json.temp[i].temp + " &deg;C";
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdTemp);
		table.appendChild(trElem);
		
		lastTempDate = changeDateFormat(json.temp[i].date);
		lastTempTime = json.temp[i].time;
	}
	
	var objDiv = document.getElementById("temp_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawTempChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.temp.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.temp[i].date + "/" + json.temp[i].time;
		valueSet[1] = json.temp[i].temp;
		
		tempArray2[i] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("tempdiv");
	plot.innerHTML = "";
	
	$.jqplot('tempdiv', tempArray, {
		title:'Temperature',
		cursor: {tooltipLocation:'sw', zoom:true, clickReset:true},
		axes:{
			yaxis:{
				label: "Temperature",
				min:-10,
				max:100
			},
			xaxis: {
				autoscale:true,
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'},
				numberTicks: 5
			}
		},
		seriesDefaults: {
			markerOptions: {
				show: false
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function getHumidityByDate(date1, date2, time1, time2) {
	$.getJSON("DateServlet.do?id=humidity&date1="+date1 + "&date2=" + date2 + "&time1=" + time1 + "&time2=" + time2, function(json) {
		if (lastHumidityJson == null) {
			lastHumidityJson = json;
		} else {
			lastHumidityJson = mergeJson(lastHumidityJson,json);
		}
		fillHumidityTable(json);
		drawHumidityChart(lastHumidityJson);
	});
}

function updateHumidity() {
	if (lastHumidityDate != null && !lastHumidityDate == "" &&
			lastHumidityTime != null && !lastHumidityTime == "") {
		var date2 = document.getElementById("date_pick2").value;
		var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
		getHumidityByDate(lastHumidityDate, date2, lastHumidityTime, time2);
	}
}

function createHumidityTable() {
	var table = document.getElementById("humidity_table_body");
	var date1 = document.getElementById("date_pick1").value;
	var date2 = document.getElementById("date_pick2").value;
	var time1 = document.getElementById("pick_hour1").value + ":" + document.getElementById("pick_minutes1").value;
	var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
	table.innerHTML = "";
	lastHumidityJson = null;
	getHumidityByDate(date1, date2, time1, time2);
}

function fillHumidityTable(json) {
	var table = document.getElementById("humidity_table_body");
	
	for (var i = 0; i < json.humidity.length; i++) {
		var trElem = document.createElement("tr");
		var tdDate = document.createElement("td");
		var tdTime = document.createElement("td");
		var tdHumidity = document.createElement("td");

		tdDate.setAttribute('align','center');
		tdTime.setAttribute('align','center');
		tdHumidity.setAttribute('align','center');
		
		tdDate.innerHTML = changeDateFormat(json.humidity[i].date);
		tdTime.innerHTML = json.humidity[i].time;
		tdHumidity.innerHTML = json.humidity[i].humidity + " &deg;C";
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdHumidity);
		table.appendChild(trElem);
		
		lastHumidityDate = changeDateFormat(json.humidity[i].date);
		lastHumidityTime = json.humidity[i].time;
	}
	
	var objDiv = document.getElementById("humidity_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawHumidityChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.humidity.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.humidity[i].date + "/" + json.humidity[i].time;
		valueSet[1] = json.humidity[i].humidity;
		
		tempArray2[i] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("humiditydiv");
	plot.innerHTML = "";
	
	$.jqplot('humiditydiv', tempArray, {
		title:'Humidity',
		axes:{
			yaxis:{
				label: "Humidity(%)",
				min:0,
				max:100
			},
			xaxis: {
				autoscale:true,
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'},
				numberTicks: 5
			}
		},
		seriesDefaults: {
			markerOptions: {
				show: false
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function getDoorByDate(date1, date2, time1, time2) {
	$.getJSON("DateServlet.do?id=door&date1="+date1 + "&date2=" + date2 + "&time1=" + time1 + "&time2=" + time2, function(json) {
		if (lastDoorJson == null) {
			lastDoorJson = json;
		} else {
			lastDoorJson = mergeJson(lastDoorJson,json);
		}
		fillDoorTable(json);
		drawDoorChart(lastDoorJson);
	});
}

function updateDoor() {
	if (lastDoorDate != null && !lastDoorDate == "" &&
			lastDoorTime != null && !lastDoorTime == "") {
		var date2 = document.getElementById("date_pick2").value;
		var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
		getDoorByDate(lastDoorDate, date2, lastDoorTime, time2);
	}
}

function createDoorTable() {
	var table = document.getElementById("door_table_body");
	var date1 = document.getElementById("date_pick1").value;
	var date2 = document.getElementById("date_pick2").value;
	var time1 = document.getElementById("pick_hour1").value + ":" + document.getElementById("pick_minutes1").value + ":00";
	var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value + ":00";
	table.innerHTML = "";
	lastDoorDate = "";
	lastDoorTime = "";
	lastDoorJson = null;
	getDoorByDate(date1, date2, time1, time2);
}

function fillDoorTable(json) {
	var table = document.getElementById("door_table_body");
	
	for (var i = 0; i < json.door.length; i++) {
		var trElem = document.createElement("tr");
		var tdDate = document.createElement("td");
		var tdTime = document.createElement("td");
		var tdDoor = document.createElement("td");

		tdDate.setAttribute('align','center');
		tdTime.setAttribute('align','center');
		tdDoor.setAttribute('align','center');
		
		tdDate.innerHTML = changeDateFormat(json.door[i].date);
		tdTime.innerHTML = json.door[i].time;
		if(json.door[i].door == 1) {
			tdDoor.innerHTML = "OPEN";
		} else {
			tdDoor.innerHTML = "CLOSED";
		}
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdDoor);
		table.appendChild(trElem);
		
		lastDoorDate = changeDateFormat(json.door[i].date);
		lastDoorTime = json.door[i].time;
	}
	
	var objDiv = document.getElementById("door_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawDoorChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.door.length; i++) {
		var invertedValueSet = new Array();
		var valueSet = new Array();

		
		var testtime = json.door[i].time.split(":");
		var test = parseInt(testtime[2])-1;
		if (test < 0) { test = 0; }
		invertedValueSet[0] = json.door[i].date + "/" + testtime[0]+":"+testtime[1]+":"+test;//json.door[i].time;
		if (i-1 >= 0) {
			if (json.door[i-1].door == 1) {
				invertedValueSet[1] = 1;
			} else {
				invertedValueSet[1] = 0;
			}
		}
		valueSet[0] = json.door[i].date + "/" + json.door[i].time;
		valueSet[1] = json.door[i].door;
		
		tempArray2[i*2] = invertedValueSet;
		tempArray2[i*2+1] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("doordiv");
	plot.innerHTML = "";
	
	$.jqplot('doordiv', tempArray, {
		title:'Door',
		axes:{
			yaxis:{
				label: "State(0/1)",
				min:-1,
				max:2
			},
			xaxis: {
				autoscale:true,
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'},
				numberTicks: 5
			}
		},
		seriesDefaults: {
			markerOptions: {
				show: false
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function getLightByDate(date1, date2, time1, time2) {
	$.getJSON("DateServlet.do?id=light&date1="+date1 + "&date2=" + date2 + "&time1=" + time1 + "&time2=" + time2, function(json) {
		if (lastLightJson == null) {
			lastLightJson = json;
		} else {
			lastLightJson = mergeJson(lastLightJson,json);
		}
		fillLightTable(json);
		drawLightChart(lastLightJson);
	});
}

function updateLight() {
	if (lastLightDate != null && !lastLightDate == "" &&
			lastLightTime != null && !lastLightTime == "") {
		var date2 = document.getElementById("date_pick2").value;
		var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("light_minutes2").value;
		getLightByDate(lastLightDate, date2, lastLightTime, time2);
	}
}

function createLightTable() {
	var table = document.getElementById("light_table_body");
	var date1 = document.getElementById("date_pick1").value;
	var date2 = document.getElementById("date_pick2").value;
	var time1 = document.getElementById("pick_hour1").value + ":" + document.getElementById("pick_minutes1").value;
	var time2 = document.getElementById("pick_hour2").value + ":" + document.getElementById("pick_minutes2").value;
	table.innerHTML = "";
	lastLightJson = null;
	getLightByDate(date1, date2, time1, time2);
}

function fillLightTable(json) {
	var table = document.getElementById("light_table_body");
	
	for (var i = 0; i < json.light.length; i++) {
		var trElem = document.createElement("tr");
		var tdDate = document.createElement("td");
		var tdTime = document.createElement("td");
		var tdLight = document.createElement("td");

		tdDate.setAttribute('align','center');
		tdTime.setAttribute('align','center');
		tdLight.setAttribute('align','center');
		
		tdDate.innerHTML = changeDateFormat(json.light[i].date);
		tdTime.innerHTML = json.light[i].time;
		if(json.light[i].light == 1) {
			tdLight.innerHTML = "ON";
		} else {
			tdLight.innerHTML = "OFF";
		}
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdLight);
		table.appendChild(trElem);
		
		lastLightDate = changeDateFormat(json.light[i].date);
		lastLightTime = json.light[i].time;
	}
	
	var objDiv = document.getElementById("light_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawLightChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.light.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.light[i].date + "/" + json.light[i].time;
		valueSet[1] = json.light[i].light;
		
		tempArray2[i] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("lightdiv");
	plot.innerHTML = "";
	
	$.jqplot('lightdiv', tempArray, {
		title:'Light',
		axes:{
			yaxis:{
				label: "Light(%)",
				min:0,
				max:100
			},
			xaxis: {
				autoscale:true,
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'},
				numberTicks: 5
			}
		},
		seriesDefaults: {
			markerOptions: {
				show: false
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function changeDateFormat(date) {
	var dateArray = date.split("-");
	return dateArray[2]+"-"+dateArray[1]+"-"+dateArray[0];
}

function mergeJson(json1, json2) {
	for (var i=0; i<json2.lenght; i++) {
		json1[json1.length+i] = json2[i];
	}
	return json1;
}

function getStatistics() {
	var selectedIp = document.getElementById("selectedIp").value;
	$.getJSON("statistics.do?selectedIp=" + selectedIp, function(json) {
		var pages = json.pages;
		var pagesObject = document.getElementById("pages");
		pagesObject.innerHTML = "";
		document.getElementById("ipAdress").innerHTML = selectedIp;

		for(var i = 0; i < pages.length; i++) {
			pagesObject.innerHTML += "<div class='row'> <div class='left'>Page:</div> <div class='right'>";
			pagesObject.innerHTML += pages[i].page;
			pagesObject.innerHTML += pages[i].hits;
			pagesObject.innerHTML += 'X';
			pagesObject.innerHTML += '</div></div>';
			
		}
	});
	
}