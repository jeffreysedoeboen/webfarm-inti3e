
function updateCurrentStats() {
	$.getJSON("CurrentServlet.do", function(json) {
		var currentstats = document.getElementById("currentstats");
		currentstats.innerHTML = "";
		var light = "<p>Light is " + json.Light + "</p>";
		var temperature = "<p>Temperature: " + json.Temperature + "</p>";
		var humidity = "<p>Humidity: " + json.Humidity + "</p>";
		var door = "<p>Door is " + json.Door + "</p>";
		
		currentstats.innerHTML = "<div align='center'>" + light + temperature + humidity + door + "</div>";
			});
}

function hit(page) {
	if(page == "User Statistics") {
		getStatistics();
	} else {
		$.getJSON("analytics.jpg?page=" + page, function(json) {});
	}
}

function getVidListByDate() {
	var date1 = document.getElementById("date_vidplayback").value;
	$.getJSON("VideoServlet.do?id=playback&date1="+date1, function(json) {
		fillPlaybackTable(json);
	});
}

function fillPlaybackTable(json) {
	var table = document.getElementById("playback_table_body");
	table.innerHTML = "";
	for (var i = 0; i < json.Files.length; i++) {
		var trElem = document.createElement("tr");
		var tdVideo = document.createElement("td");

		tdVideo.setAttribute('align','center');

		tdVideo.innerHTML += json.Files[i];
		trElem.appendChild(tdVideo);
		table.appendChild(trElem);
	}
}

function showPlayer(name) {
	var player = document.getElementById("videoplayer");
	var html = '<video src="videos/' + name + '" width="480" height="360" controls preload>Please update to a new generation browser, like Firefox >3.5, Chrome >5.0 or Opera >10.5.</video>';
	
	player.innerHTML = html;
}

function getStatistics() {
	var selectedIp = document.getElementById("selectedIp").value;
	$.getJSON("statistics.do?selectedIp=" + selectedIp, function(json) {
		var pages = json.pages;
		var statsTableBody = document.getElementById("stats_table_body");
		statsTableBody.innerHTML = "";

		for(var i = 0; i < pages.length; i++) {
			var pageHTML = "<tr><td>";
			pageHTML += pages[i].page;
			pageHTML += "</td><td style='text-align:center'>";
			pageHTML += pages[i].hits;
			pageHTML += 'X</td></tr>';
			statsTableBody.innerHTML += pageHTML;
		}
	});
}