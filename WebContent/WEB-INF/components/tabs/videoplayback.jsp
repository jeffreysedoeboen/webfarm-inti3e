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
		out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
		out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
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
	<tbody id="playback_table_body"></tbody>
</table>
</div>
<div style="width:70%; margin-left: 30%;">
<div id="videoplayer">
<applet code="com.fluendo.player.Cortado.class" archive="cortado.jar" width="352" height="288">
  <param name="url" value=""/>
  <param name="seekable" value="false"/>
  <param name="live" value="true"/>
  <param name="video" value="true"/>
  <param name="audio" value="false"/>
  <param name="bufferLow" value="0"/>
  <param name="bufferHigh" value="1"/>
  <param name="bufferSize" value="200"/>
</applet>
</div>
</div>
</div>
<br><br><br><br><br><br><br>