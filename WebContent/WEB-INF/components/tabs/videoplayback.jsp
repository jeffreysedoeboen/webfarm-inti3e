<script type="text/javascript">
$(function() {
		$( "#date_vidplayback" ).datepicker({ dateFormat: 'dd-mm-yy' });
		$( "#vidlistbutton" ).button();
	});
</script>
<div style="position: relative;">
<div style="float:left; width:30%; height:300px;">
<p>Date:</p>
<input name="datepicker" type="text" id="date_vidplayback"
			readonly="readonly"
			value=<%
		java.util.Calendar calendar = java.util.Calendar.getInstance(); 
		
		if(calendar.get(java.util.Calendar.DAY_OF_MONTH) < 10) {
			out.print("0" +(calendar.get(java.util.Calendar.DAY_OF_MONTH)) + "-");
		} else {
			out.print((calendar.get(java.util.Calendar.DAY_OF_MONTH)) + "-");
		}
		
		if(calendar.get(java.util.Calendar.MONTH)+1 < 10) {
			out.print("0" +(calendar.get(java.util.Calendar.MONTH)+1) + "-");
		} else {
			out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
		}
		out.print(calendar.get(java.util.Calendar.YEAR));
		%>>
		
<button type="submit" onclick="getVidListByDate()" id="vidlistbutton">Submit</button>
<br><br>
<table style="border:3px #363636 solid"
align="left" width="50%" bgcolor="#222222">
	<thead>
		<tr>
			<th>Video:</th>
		</tr>
	</thead>
	<tbody id="playback_table_body"><tr></tr></tbody>
</table>
</div>
<div id="videoplayer" style="width:70%; margin-left: 30%;">
</div>
</div>
<br><br><br><br><br><br><br>