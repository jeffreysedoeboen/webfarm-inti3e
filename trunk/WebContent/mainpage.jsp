<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<script type="text/javascript">
function reloadGraphs() {
	setInterval("kaas()", 1000);
}

function kaas() {
	
}


</script>
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css" />
<script language="javascript" type="text/javascript" src="assets/js/lib/jquery.jqplot.js"></script>
<link rel="stylesheet" type="text/css" href="assets/css/jquery.jqplot.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
<script>
$(document).ready(function(){
	$.jqplot('tempdiv',  [[[8, 2],[9,5.12],[10,13.1],[11, 46.6],[12,85.9],[13,99]]],
			{ title:'Temperature',
			  axes:{yaxis:{min:-10, max:100}},
			  series:[{color:'#5FAB78'}]
			});
	});

$(document).ready(function(){
	$.jqplot('humiditydiv',  [[[8, 20],[9,25],[10,30],[11, 32],[12,27],[13,25]]],
			{ title:'Air Humidity',
			  axes:{yaxis:{min:0, max:100}},
			  series:[{color:'#5FAB78'}]
			});
	});
</script>
</head>
<body>
<script>
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Main</a></li>
		<li><a href="#tabs-2">Live stream</a></li>
		<li><a href="#tabs-3">Video playback</a></li>
		<li><a href="#tabs-4">Temperature</a></li>
		<li><a href="#tabs-5">Air humidity</a></li>
		<li><a href="#tabs-6">Illumination</a></li>
		<li><a href="#tabs-7">Door switch</a></li>
		<li><a href="#tabs-8">Light switch</a></li>
		<li><a href="#tabs-9">Sources</a></li>
	</ul>
	<div id="tabs-1">
		<p><h1 align="center">Welcome to our application!</h1></p>
		<p></p>
		<p align="center">On this page you will see the protection of the Saxion webfarm.</p>
		<p align="center">You can look the stats with the tabs.</p>
	</div>
	<div id="tabs-2"><jsp:include page="/WEB-INF/components/tabs/livestream.jsp" />
	</div>
	<div id="tabs-3"><jsp:include page="/WEB-INF/components/tabs/videoplayback.jsp" />
	</div>
	<div id="tabs-4"><jsp:include page="/WEB-INF/components/tabs/temperature.jsp" />
	</div>
	<div id="tabs-5"><jsp:include page="/WEB-INF/components/tabs/airhumidity.jsp" />
	</div>
	<div id="tabs-6"><jsp:include page="/WEB-INF/components/tabs/illumination.jsp" />
	</div>
	<div id="tabs-7"><jsp:include page="/WEB-INF/components/tabs/doorswitch.jsp" />
	</div>
	<div id="tabs-8"><jsp:include page="/WEB-INF/components/tabs/lightswitch.jsp" />
	</div>
	<div id="tabs-9">
	<p><h1 align="center">The sources we used:</h1></p>
	<p><h2 align="center">Firewall & OS</h2></p>
		<p align="center">* <a href="http://www.ipfire.org">IpFire</a></p>
		<p align="center">* <a href="http://www.debian.org">Debian</a></p>
		<p align="center">* <a href="http://distrowatch.com/search.php?category=Firewall#distrosearch">List of firewalls</a></p>
	<br></br>
	<p><h2 align="center">Web page</h2></p>
		<p align="center">* <a href="http://www.jquery.com">jQuery</a></p>
		<p align="center">* <a href="http://www.longtailvideo.com/players/jw-flv-player/">Video Player</a></p>
	<br></br>
	<p><h2 align="center">Webcam sources</h2></p>
		<p align="center">* <a href="http://ubuntuforums.org/showthread.php?t=191770">Driver install tutorial</a></p>
		<p align="center">* <a href="http://home.mag.cx/messenger/source/">Driver download</a></p>
		<p align="center">* <a href="http://code.google.com/p/gstreamer-java/">GStreamer Java Bindings</a></p>
		<p align="center">* <a href="http://dev.fubbie.org/wiki/browser/fubbie-techtests/src/org/fubbie/techtests/snippets/GStreamerWebcam.java">Examples</a></p>
		<p align="center">* <a href="http://www.twm-kd.com/linux/webcam-and-linux-gstreamer-tutorial/">Webcam and Linux</a></p>
	<br></br>
	<p><h2 align="center">Code conventies</h2></p>
		<p align="center">* <a href="http://drupal.org/coding-standards">Coding Standards (Voorbeeld)</a></p>
		<p align="center">* <a href="http://wiki.eclipse.org/index.php/Development_Conventions_and_Guidelines">Eclipse code conventies</a></p>
	</div>
</div>
</body>
</html>