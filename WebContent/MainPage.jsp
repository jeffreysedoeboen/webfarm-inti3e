<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css">
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
		<li><a href="#tabs-4">Temperature</a></li>
		<li><a href="#tabs-5">Air humidity</a></li>
		<li><a href="#tabs-6">Illumination</a></li>
		<li><a href="#tabs-7">Door switch</a></li>
		<li><a href="#tabs-8">Sources</a></li>
	</ul>
	<div id="tabs-1">
		<p><h1 align="center">Welcome to our application!</h1></p>
		<p></p>
		<p align="center">On this page you will see the protection of the Saxion webfarm.</p>
		<p align="center">You can look the stats with the tabs.</p>
	</div>
	<div id="tabs-2"><jsp:include page="/WEB-INF/components/tabs/liveStream.jsp" />
	</div>
	<div id="tabs-3"><jsp:include page="/WEB-INF/components/tabs/videoPlayback.jsp" />
	</div>
	<div id="tabs-4"><jsp:include page="/WEB-INF/components/tabs/temperature.jsp" />
	</div>
	<div id="tabs-5"><jsp:include page="/WEB-INF/components/tabs/airHumidity.jsp" />
	</div>
	<div id="tabs-6"><jsp:include page="/WEB-INF/components/tabs/illumination.jsp" />
	</div>
	<div id="tabs-7"><jsp:include page="/WEB-INF/components/tabs/doorSwitch.jsp" />
	</div>
	<div id="tabs-8">
	<p><h1 align="center">The sources we used:</h1></p>
	<p align = "center">Firewall & OS</p>
		<p align="center">* <a href="http://www.ipfire.org">IpFire</a></p>
		<p align="center">* <a href="http://www.debian.org">Debian</a></p>
	<br></br>
	<p align="center">Web page</p>
		<p align="center">* <a href="http://www.jquery.com">jQuery</a></p>
		<p align="center">* <a href="http://www.longtailvideo.com/players/jw-flv-player/">Video Player</a></p>

	</div>
</div>
</body>
</html>