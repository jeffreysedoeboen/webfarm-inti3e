<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<script type="text/javascript" src="assets/js/messages.js"></script>
<link href="assets/css/stylestatistics.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css">
<script language="javascript" type="text/javascript" src="assets/js/lib/jquery.jqplot.js"></script>
<script type="text/javascript" src="assets/js/lib/jqplot.canvasAxisTickRenderer.js"></script>
<script type="text/javascript" src="assets/js/lib/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="assets/js/lib/jqplot.cursor.min.js"></script>
<link rel="stylesheet" type="text/css" href="assets/css/jquery.jqplot.css">
<c:if test="${user.admin}">
	<script type="text/javascript" src="assets/js/data.js"></script>
</c:if>
<script type="text/javascript" src="assets/js/data2.js"></script>
<script type="text/javascript" src="assets/js/ontabclicks.js"></script>
<script src="assets/js/flowplayer-3.2.4.min.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>

</head>
<body onload="msgPrint('${errors}','${success}');">
<div ID="messages"></div>
<script type="text/javascript">
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>

<div id="tabs">
	<ul onmouseup="msgCleanup();">
		<li><a onclick="onMainTabClick();" href="#tabs-1">Main</a></li>
		<c:if test="${user != null}">
			<li><a onclick="onLiveTabClick();" href="#tabs-2">Live stream</a></li>
			<li><a onclick="onPlayTabClick();" href="#tabs-3">Video playback</a></li>
			<li><a onclick="onUserstatsTabClick();" href="#tabs-4">User Statistics</a></li>
			<c:if test="${user.admin}">
				<li><a onclick="onTempTabClick();" href="#tabs-5">Temperature</a></li>
				<li><a onclick="onHumidTabClick();" href="#tabs-6">Air humidity</a></li>
				<li><a onclick="onIllumTabClick();" href="#tabs-7">Illumination</a></li>
				<li><a onclick="onDoorTabClick();" href="#tabs-8">Door</a></li>
				<li><a onclick="onSwitchTabClick();" href="#tabs-9">Light switch</a></li>
				<li><a onclick="onCreateTabClick();" href="#tabs-10">Create account</a></li>
				<li><a onclick="onManageAccountsTabClick();" href="#tabs-11">Manage accounts</a></li>
				<li><a onclick="onStatsTabClick();" href="#tabs-12">Statistics</a></li>
			</c:if>
		</c:if>
		<li><a onclick="onSourcesTabClick();" href="#tabs-13">Sources</a></li>
	</ul>
	<div id="datepick" style="display:none" align="center"><jsp:include page="/WEB-INF/components/datepick.jsp" /></div>
	<div id="tabs-1">
		<h1 align="center">Welcome to our application!</h1>
		<p></p>
		<p align="center">On this page you will see the protection of the Saxion webfarm.</p>
		<p align="center">You can look the stats with the tabs.</p>
		<br>
		<p align="center">Here you can see the current stats:</p>
		<div id="currentstats"></div>
	</div>
	<c:if test="${user != null}">
	<div id="tabs-2"><jsp:include page="/WEB-INF/components/tabs/livestream.jsp" />
	</div>
	<div id="tabs-3"><jsp:include page="/WEB-INF/components/tabs/videoplayback.jsp" />
	</div>
	<div id="tabs-4"><jsp:include page="/WEB-INF/components/tabs/statistics-user.jsp" />
	</div>
	<c:if test="${user.admin}">
	<div id="tabs-5"><jsp:include page="/WEB-INF/components/tabs/temperature.jsp" />
	</div>
	<div id="tabs-6"><jsp:include page="/WEB-INF/components/tabs/airhumidity.jsp" />
	</div>
	<div id="tabs-7"><jsp:include page="/WEB-INF/components/tabs/illumination.jsp" />
	</div>
	<div id="tabs-8"><jsp:include page="/WEB-INF/components/tabs/door.jsp" />
	</div>
	<div id="tabs-9"><jsp:include page="/WEB-INF/components/tabs/lightswitch.jsp" />
	</div>
	<div id="tabs-10"><jsp:include page="/WEB-INF/components/tabs/createaccount.jsp" />
	</div>
	<div id="tabs-11"><jsp:include page="/WEB-INF/components/tabs/manageaccounts.jsp" />
	</div>
	<div id="tabs-12"><jsp:include page="/WEB-INF/components/tabs/statistics.jsp" />
	</div>
	</c:if>
	</c:if>
	<div id="tabs-13">
	<h1 align="center">The sources we used:</h1>
	<h2 align="center">Firewall &amp; OS</h2>
		<p align="center">* <a href="http://www.smoothwall.org">SmoothWall</a></p>
		<p align="center">* <a href="http://www.debian.org">Debian</a></p>
		<p align="center">* <a href="http://distrowatch.com/search.php?category=Firewall#distrosearch">List of firewalls</a></p>
	<br>
	<h2 align="center">Web page</h2>
		<p align="center">* <a href="http://www.jquery.com">jQuery</a></p>
		<p align="center">* <a href="http://www.longtailvideo.com/players/jw-flv-player/">Video Player</a></p>
	<br>
	<h2 align="center">Webcam sources</h2>
		<p align="center">* <a href="http://ubuntuforums.org/showthread.php?t=191770">Driver install tutorial</a></p>
		<p align="center">* <a href="http://home.mag.cx/messenger/source/">Driver download</a></p>
		<p align="center">* <a href="http://code.google.com/p/gstreamer-java/">GStreamer Java Bindings</a></p>
		<p align="center">* <a href="http://dev.fubbie.org/wiki/browser/fubbie-techtests/src/org/fubbie/techtests/snippets/GStreamerWebcam.java">Examples</a></p>
		<p align="center">* <a href="http://www.twm-kd.com/linux/webcam-and-linux-gstreamer-tutorial/">Webcam and Linux</a></p>
	<br>
	<h2 align="center">Code conventies</h2>
		<p align="center">* <a href="http://drupal.org/coding-standards">Coding Standards (Voorbeeld)</a></p>
		<p align="center">* <a href="http://wiki.eclipse.org/index.php/Development_Conventions_and_Guidelines">Eclipse code conventies</a></p>
	</div>
</div>
<%
session.removeAttribute("errors");
session.removeAttribute("success");
%>
<img src="analytics.jpg" alt=""/>
</body>
</html>