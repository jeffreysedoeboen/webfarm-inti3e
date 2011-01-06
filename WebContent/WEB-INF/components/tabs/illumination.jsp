<div>
<br>
<br>
<script type="text/javascript">
$(function() {
	var date = new Date();
	$( "#date_light1" ).datepicker({ dateFormat: 'dd-mm-yy' });
	$( "#date_light2" ).datepicker({ dateFormat: 'dd-mm-yy' });
	createLightTable();
});
</script>
<div style="float: left;">
<table>
	<tr>
		<td>From:</td>
		<td><input name="datepicker" type="text" id="date_light1"
			readonly="readonly"
			value=<%java.util.Calendar calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
	</tr>
	<tr>
		<td>Time:</td>
		<td><select id="light_hour1">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="light_minutes1">
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
		<td><input name="datepicker" type="text" id="date_light2"
			readonly="readonly"
			value=<%calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + 1 + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
	</tr>
	<tr>
		<td>Time:</td>
		<td><select id="light_hour2">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="light_minutes2">
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
<input type="button" onclick="createLightTable()" value="submit"></input>
</div>
<br>
<div id="light_table_div"
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
	<th>Light:</th>
</tr>
</thead>
<tbody id="light_table_body"></tbody>
</table>
</div>
<div id="lightdiv"
	style="height: 400px; width: 800px; margin-left: 50px; float: left;"></div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</div>