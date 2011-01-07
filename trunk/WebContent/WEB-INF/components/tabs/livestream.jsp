<script type="text/javascript">
	$(function() {
		$( "#recordbutton" ).button();
	});
</script>
<div align="center">
<applet code="com.fluendo.player.Cortado.class" archive="cortado.jar" width="352" height="288">
  <param name="url" value="http://145.76.18.91:8088"/>
  <param name="seekable" value="false"/>
  <param name="live" value="true"/>
  <param name="video" value="true"/>
  <param name="audio" value="false"/>
  <param name="bufferLow" value="0"/>
  <param name="bufferHigh" value="1"/>
  <param name="bufferSize" value="1"/>
</applet>
</div>
<div align="center" class="demo">
<button type="submit" onclick="return false;" id="recordbutton">Record</button>
</div>
