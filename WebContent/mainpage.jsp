<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css" />
<script language="javascript" type="text/javascript" src="assets/js/lib/jquery.jqplot.js"></script>
<script type="text/javascript" src="assets/js/lib/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="assets/js/lib/jqplot.cursor.min.js"></script>
<link rel="stylesheet" type="text/css" href="assets/css/jquery.jqplot.css" />
<script type="text/javascript" src="assets/js/data.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
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
		<li><a href="#tabs-4" onclick="getTempByDate()">Temperature</a></li>
		<li><a href="#tabs-5" onclick="getHumidityByDate()">Air humidity</a></li>
		<li><a href="#tabs-6">Illumination</a></li>
		<li><a href="#tabs-7">Door</a></li>
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
	<div id="tabs-7"><jsp:include page="/WEB-INF/components/tabs/door.jsp" />
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