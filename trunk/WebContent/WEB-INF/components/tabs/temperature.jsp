<br></br>
<br></br>
<script>
$(function() {
		$( "#date_temp1" ).datepicker({ dateFormat: 'yy-mm-dd' });
	});
$(function() {
	$( "#date_temp2" ).datepicker({ dateFormat: 'yy-mm-dd' });
});
</script>
<table>
	<tr>
		<td>From:</td>
		<td><input name="datepicker" type="text" id="date_temp1"
			readonly="readonly"
			value=<%
		java.util.Calendar calendar = java.util.Calendar.getInstance(); 
		out.print(calendar.get(java.util.Calendar.YEAR) + "-");
		out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
		out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH));
		%>>
		</td>
	</tr>
	<tr>
		<td>
		Time:
		</td>
		<td>
			<select id="temp_hour1">
				<% for(int hour = 0; hour <= 23; hour++) {
					if(hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
					}%>
			</select>
			:
			<select id="temp_minutes1">
				<% for(int minute = 0; minute <= 59; minute++) {
					if(minute < 10) {
						out.print("<option> 0" + minute + "</option>");
					} else {
						out.print("<option>" + minute + "</option>");
					}
					}%>
			</select>
		</td>
	</tr>
	<tr>
		<td>To:</td>
		<td><input name="datepicker" type="text" id="date_temp2"
			readonly="readonly"
			value=<%
		calendar = java.util.Calendar.getInstance(); 
		out.print(calendar.get(java.util.Calendar.YEAR) + "-");
		out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
		out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + 1);
		%>>
		</td>
	</tr>
	<tr>
		<td>
		Time:
		</td>
		<td>
			<select id="temp_hour2">
				<% for(int hour = 0; hour <= 23; hour++) {
					if(hour < 10) {
						out.print("<option> 0" + hour + "</option>");
					} else {
						out.print("<option>" + hour + "</option>");
					}
					}%>
			</select>
			:
			<select id="temp_minutes2">
				<% for(int minute = 0; minute <= 59; minute++) {
					if(minute < 10) {
						out.print("<option> 0" + minute + "</option>");
					} else {
						out.print("<option>" + minute + "</option>");
					}
					}%>
			</select>
		</td>
	</tr>

</table>



<input type="button" onclick="getTempByDate()" value="submit"></input>

<br></br>
<div
	style="left: screen.width/ 3; top: screen.height/ 2; color: #ffffff; width: 300px; height: 300px; border: 5px; float: left; background-color: #000000; overflow: auto;">
<table border="1">
	<thead>
		<tr>
			<th>Date:</th>
			<th>Time:</th>
			<th>Temp:</th>
		</tr>
	</thead>
	<tbody id="temp_table_body"></tbody>
</table>
</div>
<div id="tempdiv"
	style="height: 400px; width: 800px; margin-left: 50px; float: left;">76</div>


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

