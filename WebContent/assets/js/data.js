$(document).ready(function() {
	getTempByDate();
	getHumidityByDate();
});


function getTempByDate() {
	var date = document.getElementById("date_temp");
	
	$.getJSON("DateServlet.do?id=temp&date="+date.value, function(json) {
		fillTempTable(json);
		drawTempChart(json);
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
}

function drawTempChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.temp.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.temp[i].time;
		valueSet[1] = json.temp[i].temp;
		
		tempArray2[i] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("tempdiv");
	plot.innerHTML = "";
	
	$.jqplot('tempdiv', tempArray, {
		title:'Temperature',
		axes:{
			yaxis:{
				label: "Temperature",
				min:-10,
				max:100
			},
			xaxis: {
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%H:%M'},
				min:'00:00',
				max:'24:00'
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function getHumidityByDate() {
	var date = document.getElementById("date_humidity");
	
	$.getJSON("DateServlet.do?id=humidity&date="+date.value, function(json) {
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
}

function drawHumidityChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.Humidity.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.Humidity[i].time;
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
				label: "Time",
				renderer:$.jqplot.DateAxisRenderer,
				tickOptions:{formatString:'%H:%M'},
				min:'00:00',
				max:'23:59'
			}
		},
		series:[{color:'#5FAB78'}]
	});
}

function getDoorByDate() {
	var date = document.getElementById("date_door");
	
	$.getJSON("DateServlet.do?id=door&date="+date.value, function(json) {
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
}

function getLightByDate() {
	var date = document.getElementById("date_light");
	
	$.getJSON("DateServlet.do?id=light&date="+date.value, function(json) {
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
}