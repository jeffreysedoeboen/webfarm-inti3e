<div>
<br></br>
<br></br>
<script type="text/javascript">
$(function() {
	var date = new Date();
	$( "#date_door1" ).datepicker({ dateFormat: 'dd-mm-yy' });
	$( "#date_door2" ).datepicker({ dateFormat: 'dd-mm-yy' });
	createDoorTable();
});
</script>
<div style="float: left;">
<table>
	<tr>
		<td>From:</td>
		<td><input name="datepicker" type="text" id="date_door1"
			readonly="readonly"
			value=<%java.util.Calendar calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
	</tr>
	<tr>
		<td>Time:</td>
		<td><select id="door_hour1">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="door_minutes1">
			<%
				for (int minute = 0; minute <= 59; minute++) {
					if (minute < 10) {
						out.print("<option> 0" + minute + "</option>");
					} else {
						out.print("<option>" + minute + "</option>");
					}
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td>To:</td>
		<td><input name="datepicker" type="text" id="date_door2"
			readonly="readonly"
			value=<%calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + 1 + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
	</tr>
	<tr>
		<td>Time:</td>
		<td><select id="door_hour2">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="door_minutes2">
			<%
				for (int minute = 0; minute <= 59; minute++) {
					if (minute < 10) {
						out.print("<option> 0" + minute + "</option>");
					} else {
						out.print("<option>" + minute + "</option>");
					}
				}
			%>
		</select></td>
	</tr>
</table>
<input type="button" onclick="createDoorTable()" value="submit"></input>
</div>
<br></br>
<div id="door_table_div"
style="margin-left: 200px;
position: relative;
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
	<th>Door:</th>
</tr>
</thead>
<tbody id="door_table_body"></tbody>
</table>
</div>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
</div>