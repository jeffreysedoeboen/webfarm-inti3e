<script type="text/javascript">
$(function() {
		$( "#date_vidplayback" ).datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>
<div style="position: relative;">
<div style="float:left; width:30%;">
<p>Date:</p>
<input name="datepicker" type="text" id="date_vidplayback"
			readonly="readonly"
			value=<%
		java.util.Calendar calendar = java.util.Calendar.getInstance(); 
		out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
		out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
		out.print(calendar.get(java.util.Calendar.YEAR));
		%>>
<input type="button" onclick="getVidListByDate()" value="submit"></input>
<br>
<br>
<table border="1">
	<thead>
		<tr>
			<th>Video:</th>
		</tr>
	</thead>
	<tbody id="playback_table_body"><tr><td><a onclick="playback">2-1-2011/12:00</a></td></tr><tr><td>3-1-2011/14:00</td></tr><tr><td>6-1-2011/17:00</td></tr></tbody>
</table>
</div>
<div style="width:70%; margin-left: 30%;">
<div id="videoplayer">
<a
	href="http://localhost:8088"
	style="display:block;width:425px;height:300px;"
	id="player">
</a>
</div>
<script language="JavaScript" type="text/javascript">
flowplayer("player", "assets/flashplayer/flowplayer-3.2.5.swf");
</script>
</div>
</div>