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
		tdTemp.innerHTML = json.temp[i].temp + "%";
		
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
				max:'23:59'
			}
		},
		series:[{color:'#5FAB78'}]
	});
}


function getHumidityByDate() {
	var date = document.getElementById("date_humidity");
	
	$.getJSON("DateServlet.do?id=humidity&date="+date.value, function(json) {
		fillTempTable(json);
		drawTempChart(json);
	});
}

function fillTempTable(json) {
	var table = document.getElementById("humidity_table_body");
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

function drawHumidityChart(json) {
	var tempArray = new Array();
	var tempArray2 = new Array();
	
	for (var i = 0; i < json.temp.length; i++) {
		var valueSet = new Array();
		valueSet[0] = json.temp[i].time;
		valueSet[1] = json.temp[i].temp;
		
		tempArray2[i] = valueSet;
	}
	tempArray[0] = tempArray2;
	
	var plot = document.getElementById("humiditydiv");
	plot.innerHTML = "";
	
	$.jqplot('humiditydiv', tempArray, {
		title:'Temperature',
		axes:{
			yaxis:{
				label: "Humidity",
				min:-10,
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