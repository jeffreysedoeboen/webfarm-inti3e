<script type="text/javascript" src="assets/js/recordswitch.js"></script>
<script type="text/javascript">
	$(function() {
		$( "#recordbutton" ).button();
	});
</script>

<div align="center">
<applet code="com.fluendo.player.Cortado.class" archive="http://theora.org/cortado.jar" width="352" height="288">
  <param name="url" value="http://192.168.0.123:8088"/>
  <param name="seekable" value="false"/>
  <param name="live" value="true"/>
  <param name="video" value="true"/>
  <param name="audio" value="false"/>
  <param name="bufferLow" value="0"/>
  <param name="bufferHigh" value="1"/>
  <param name="bufferSize" value="1"/>
</applet>
</div>
<script type="text/javascript">
if(!navigator.javaEnabled()) {
	document.write("<div align='center'>You're browser does not support java, download it <a href='http://www.java.com/nl/download/'>here</a>.</div>"); 
}
</script>
<div align="center" class="demo">
<button type="submit" onclick="singleRecordClick('on')" id="recordbutton">Record</button>
</div>
