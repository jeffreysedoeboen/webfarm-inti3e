<br></br>
<br></br>
<script>
$(function() {
		$( "#date_humidity" ).datepicker();
	});
</script>

	<p>Date: <input name="datepicker" type="text" id="date_humidity" readonly="readonly"></p>
	<input type="button" onclick="getHumidityByDate()" value="submit"></input>

<br></br>
<div style="
left:screen.width / 3;
top:screen.height / 2; 
color:#ffffff;
width:300px;
height:300px;
border: 5px;
float: left;
background-color:#000000;
overflow:auto;">
<table border="1">
<thead>
<tr>
	<th>Date:</th>
	<th>Time:</th>
	<th>Humidity:</th>
</tr>
</thead>
<tbody id="humidity_table_body"></tbody>
</table>
</div>
<div id="humiditydiv" style="height:400px;width:800px;margin-left: 50px;float:left;"></div>


<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>

