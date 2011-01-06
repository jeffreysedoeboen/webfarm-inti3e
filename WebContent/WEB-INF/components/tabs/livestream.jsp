<script type="text/javascript">
	$(function() {
		$( "button, input:submit, a", ".demo" ).button();
		$( "a", ".demo" ).click(function() { return false; });
	});
</script>
<div align="center">
<a
	href="http://localhost:8088"
	style="display:block;width:425px;height:300px;"
	id="player">
</a>

<script language="JavaScript" type="text/javascript">
flowplayer("player", "assets/flashplayer/flowplayer-3.2.5.swf");
</script>
</div>
<div align="center" class="demo">
<button>Record</button>
</div>
