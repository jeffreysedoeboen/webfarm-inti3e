function getTempByDate() {
	var date1 = document.getElementById("date_temp1");
	var date2 = document.getElementById("date_temp2");
	var time1 = document.getElementById("temp_hour1").value + ":" + document.getElementById("temp_minutes1").value;
	var time2 = document.getElementById("temp_hour2").value + ":" + document.getElementById("temp_minutes2").value;
	
	$.getJSON("DateServlet.do?id=temp&date1="+date1.value + "&date2=" + date2.value + "&time1=" + time1 + "&time2=" + time2, function(json) {
		fillTempTable(json);
		drawTempChart(json, time1, time2, date1.value, date2.value);
	});
}

function fillTempTable(json) {
	var table = document.getElementById("temp_table_body");
	table.innerHTML = "";
	
	for (var i = 0; i < json.temp.length; i++) {
		var trElem = document.createElement("tr");
		
		var tdDate = document.createElement("td");
		tdDate.innerHTML = json.temp[i].date;
		
		var tdTime = document.createElement("td");
		tdTime.innerHTML = json.temp[i].time;
		
		var tdTemp = document.createElement("td");
		tdTemp.innerHTML = json.temp[i].temp + " &deg;C";
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdTemp);
		
		table.appendChild(trElem);
	}
	
	var objDiv = document.getElementById("temp_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
}

function drawTempChart(json, time1, time2, date1, date2) {
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
				tickInterval:'1 hour',
				tickOptions:{formatString:'%Y-%#m-%#d-%H:%M'}
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
		drawHumidityChart(json, time1, time2, date1.value, date2.value);
	});
}

function fillHumidityTable(json) {
	var table = document.getElementById("humidity_table_body");
	table.innerHTML = "";
	
	for (var i = 0; i < json.Humidity.length; i++) {
		var trElem = document.createElement("tr");
		
		var tdDate = document.createElement("td");
		tdDate.innerHTML = json.Humidity[i].date;
		
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

function drawHumidityChart(json, time1, time2, date1, date2) {
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
$(document).ready(function(){
	setInterval("autoupdate()", 10000);
});

function autoupdate() {
	getTempByDate();
	getHumidityByDate();
	getDoorByDate();
	getLightByDate();
}

function getDoorByDate() {
	var date1 = document.getElementById("date_door");
	
	$.getJSON("DateServlet.do?id=door&date1="+date1.value, function(json) {
		fillDoorTable(json);
	});
}

function fillDoorTable(json) {
	var table = document.getElementById("door_table_body");
	table.innerHTML = "";
	
	for (var i = 0; i < json.door.length; i++) {
		var trElem = document.createElement("tr");
		
		var tdDate = document.createElement("td");
		tdDate.innerHTML = json.door[i].date;
		
		var tdTime = document.createElement("td");
		tdTime.innerHTML = json.door[i].time;
		
		var tdDoor = document.createElement("td");
		if(json.door[i].door == 1) {
			tdDoor.innerHTML = "OPEN";
		} else {
			tdDoor.innerHTML = "CLOSED";
		}
		
		trElem.appendChild(tdDate);
		trElem.appendChild(tdTime);
		trElem.appendChild(tdDoor);
		
		table.appendChild(trElem);
	}
	
	var objDiv = document.getElementById("door_table_div");
	objDiv.scrollTop = objDiv.scrollHeight;
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
		tdDate.innerHTML = json.light[i].date;
		
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