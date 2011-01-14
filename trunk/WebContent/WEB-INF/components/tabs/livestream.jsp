<script type="text/javascript" src="assets/js/recordswitch.js"></script>
<script type="text/javascript">
	$(function() {
		$( "#recordbutton" ).button();
	});
</script>

<div align="center">
<video src="http://192.168.0.123:8088/stream.ogg" width="480" height="360" preload="none" autoplay>Please update to a new generation browser, like Firefox >3.5, Chrome >5.0 or Opera >10.5.</video>
</div>
<div align="center" class="demo">
<button type="submit" onclick="singleRecordClick('on')" id="recordbutton">Record</button>
</div>
