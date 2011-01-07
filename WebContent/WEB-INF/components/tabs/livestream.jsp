<script type="text/javascript">
	$(function() {
		$( "button, input:submit, a", ".demo" ).button();
		$( "a", ".demo" ).click(function() { return false; });
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
  <param name="bufferSize" value="200"/>
</applet>
</div>
<div align="center" class="demo">
<button>Record</button>
</div>
