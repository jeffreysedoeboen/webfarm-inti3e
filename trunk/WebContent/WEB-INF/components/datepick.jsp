<center>
<div>
<script type="text/javascript">
$(function() {
	var date = new Date();
	$( "#date_pick1" ).datepicker({ dateFormat: 'dd-mm-yy' });
	$( "#date_pick2" ).datepicker({ dateFormat: 'dd-mm-yy' });
});
</script>
<div style="background: #363636;" >
<table>
	<tr>
		<td><b>From: </b></td>
		<td><input name="datepicker" type="text" id="date_pick1"
			readonly="readonly"
			value=<%java.util.Calendar calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
		<td width="10"></td>
		<td><select id="pick_hour1">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="pick_minutes1">
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
		<td width="100"><b>-</b></td>
		<td><b>To: </b></td>
		<td><input name="datepicker" type="text" id="date_pick2"
			readonly="readonly"
			value=<%calendar = java.util.Calendar.getInstance();
			out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + 1 + "-");
			out.print((calendar.get(java.util.Calendar.MONTH) + 1) + "-");
			out.print(calendar.get(java.util.Calendar.YEAR));%>>
		</td>
		<td width="10"></td>
		<td><select id="pick_hour2">
			<%
				for (int hour = 0; hour <= 23; hour++) {
					if (hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
				}
			%>
		</select> : <select id="pick_minutes2">
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
		<td width="25"></td>
		<td>
			<input type="button" onclick="createAllTables()" value="Submit"></input>
		</td>
	</tr>
</table>
</div>
</div>
</center>