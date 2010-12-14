<br></br>
<br></br>
<script>
$(function() {
		$( "#date_humidity" ).datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>

	<p>Date: <input name="datepicker" type="text" id="date_humidity" readonly="readonly" value=<%
	java.util.Calendar calendar = java.util.Calendar.getInstance(); 
	out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
	out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
	out.print(calendar.get(java.util.Calendar.YEAR));
	%>></p>
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

