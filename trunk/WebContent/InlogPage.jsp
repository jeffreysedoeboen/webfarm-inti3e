<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inlog Page</title>
</head>
<body>
<script>
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Start page</a></li>
		<li><a href="#tabs-2">No Login</a></li>
		<li><a href="#tabs-3">Login</a></li>
	</ul>
	<div id="tabs-1">
		<p></p>
		<p><h1 align="center">Welcome remote user!</h1></p>
		<p></p>
		<p align="center">Login for full access or proceed with restricted access.</p>
	</div>
	<div id="tabs-2"><jsp:include page="/WEB-INF/components/tabs/liveStream.jsp" />
		<p><h1 align="center">No login!</h1></p>
		<p></p>
		<p align="center">If you want to continue you can click on the "Proceed" button.</p>
		<p align="center">If you do this you get restricted acces, login for full access.</p>
		<form>
			<p align="center"><a href="MainPage.jsp"><input type="button" value="Proceed"></input></a></p>
		</form>
	</div>
	<div id="tabs-3"><jsp:include page="/WEB-INF/components/tabs/liveStream.jsp" />
	<p><h1 align="center">Please login</h1></p>
	<p></p>
	<form>
		<p align="center"><input ID="username" type="text" value="Username" onclick="value=''"></input></p>
		<p align="center"><input ID="password" type="text" value="Password" onclick="value=''"></input></p>
		<p align="center"><input type="submit" value="Login" action="LoginServlet.do"></input></p>
	</form>
	</div>
</div>
</body>
</html>