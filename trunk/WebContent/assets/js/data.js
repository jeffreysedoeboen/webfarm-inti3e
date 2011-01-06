var lastTempDate = "", lastDoorDate = "", lastHumidityDate = "", lastLightDate = "";
var lastTempTime = "", lastDoorTime = "", lastHumidityTime = "", lastLightTime = "";
var lastTempJson = null, lastDoorJson = null, lastHumidityJson = null, lastLightJson = null;

$(document).ready(function(){
	setInterval("autoupdate()", 10000);
});

function hit(page) {
	$.getJSON("analytics.jpg?page=" + page, function(json) {});
}

function autoupdate() {
	getTempByDate(); //TODO updateTemp();
	getHumidityByDate(); //TODO updateHumidity()
	getDoorByDate(); //TODO updateDoor();
	getLightByDate(); //TODO updateLight()
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
		var date2 = document.getElementById("date_temp2").value;
		var time2 = document.getElementById("temp_hour2").value + ":" + document.getElementById("temp_minutes2").value;
		getTempByDate(lastTempDate, date2, lastTempTime, time2);
	}
}

function createTempTable() {
	var table = document.getElementById("temp_table_body");
	var date1 = document.getElementById("date_temp1").value;
	var date2 = document.getElementById("date_temp2").value;
	var time1 = document.getElementById("temp_hour1").value + ":" + document.getElementById("temp_minutes1").value;
	var time2 = document.getElementById("temp_hour2").value + ":" + document.getElementById("temp_minutes2").value;
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
				//tickInterval:'1 hour', nodig??
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'}
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


function getHumidityByDate() {
	var date1 = document.getElementById("date_humidity1");
	var date2 = document.getElementById("date_humidity2");
	var time1 = document.getElementById("humidity_hour1").value + ":" + document.getElementById("humidity_minutes1").value;
	var time2 = document.getElementById("humidity_hour2").value + ":" + document.getElementById("humidity_minutes2").value;
	$.getJSON("DateServlet.do?id=humidity&date1="+date1.value + "&date2=" + date2.value + "&time1=" + time1 + "&time2=" + time2, function(json) {
		fillHumidityTable(json);
		drawHumidityChart(json);
	});
}

function fillHumidityTable(json) {
	var table = document.getElementById("humidity_table_body");
	table.innerHTML = "";
	
	for (var i = 0; i < json.Humidity.length; i++) {
		var trElem = document.createElement("tr");
		
		var tdDate = document.createElement("td");
		tdDate.innerHTML = changeDateFormat(json.Humidity[i].date);
		
		var tdTime = document.createElement("td");
		tdTime.innerHTML = json.Humidity[i].time;
		
		var tdTemp = document.createElement("td");
		tdTemp.innerHTML = json.Humidity[i].humidity + "%";
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdTemp);
		
		table.appendChild(trElem);
	}
	
	var objDiv = document.getElementById("humidity_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawHumidityChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.Humidity.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.Humidity[i].date + "/" + json.Humidity[i].time;
		valueSet[1] = json.Humidity[i].humidity;
		
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
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'}
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
		var date2 = document.getElementById("date_door2").value;
		var time2 = document.getElementById("door_hour2").value + ":" + document.getElementById("door_minutes2").value + ":00";
		getDoorByDate(lastDoorDate, date2, lastDoorTime, time2);
	}
}

function createDoorTable() {
	var table = document.getElementById("door_table_body");
	var date1 = document.getElementById("date_door1").value;
	var date2 = document.getElementById("date_door2").value;
	var time1 = document.getElementById("door_hour1").value + ":" + document.getElementById("door_minutes1").value + ":00";
	var time2 = document.getElementById("door_hour2").value + ":" + document.getElementById("door_minutes2").value + ":00";
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
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M:%S'},
				numberTicks: 5
			}
		},
		series:[{color:'#5FAB78'}]
	});
}

function getLightByDate() {
	var date1 = document.getElementById("date_light");
	
	$.getJSON("DateServlet.do?id=light&date1="+date1.value, function(json) {
		fillLightTable(json);
	});
}

function fillLightTable(json) {
	var table = document.getElementById("light_table_body");
	table.innerHTML = "";
	
	for (var i = 0; i < json.light.length; i++) {
		var trElem = document.createElement("tr");
		
		var tdDate = document.createElement("td");
		tdDate.innerHTML = changeDateFormat(json.light[i].date);
		
		var tdTime = document.createElement("td");
		tdTime.innerHTML = json.light[i].time;
		
		var tdLight = document.createElement("td");
		if(json.light[i].light == 1) {
			tdLight.innerHTML = "ON";
		} else {
			tdLight.innerHTML = "OFF";
		}
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdLight);
		
		table.appendChild(trElem);
	}
	
	var objDiv = document.getElementById("light_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
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