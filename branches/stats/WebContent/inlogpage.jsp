<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="assets/js/lib/jquery-1.4.4.js"></script>
<script type="text/javascript" src="assets/js/lib/jquery-ui-1.8.6.js"></script>
<link rel="stylesheet" href="assets/css/ui-darkness/jquery-ui-1.8.6.css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inlog Page</title>
</head>
<body>
<script type="text/javascript">
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>

<div id="tabs">
	<ul>
		<li><a onclick="hit('Start page')" href="#tabs-1">Start page</a></li>
		<li><a onclick="hit('No Login')" href="#tabs-2">No Login</a></li>
		<li><a onclick="hit('Login')" href="#tabs-3">Login</a></li>
	</ul>
	<div id="tabs-1">
		<p></p>
		<h1 align="center">Welcome remote user!</h1>
		<p></p>
		<p align="center">Login for full access or proceed with restricted access.</p>
	</div>
	<div id="tabs-2">
		<h1 align="center">No login!</h1>
		<p></p>
		<p align="center">If you want to continue you can click on the "Proceed" button.</p>
		<p align="center">If you do this you get restricted acces, login for full access.</p>
		<p align="center"><a href="mainpage.jsp"><input type="button" value="Proceed"/></a></p>
	</div>
	<div id="tabs-3">
	<h1 align="center">Please login</h1>
	<p></p>
	<form method="get" action="LoginServlet.do">
		<p align="center"><input name="usernameLogin" type="text" value="Username" onclick="value=''"/></p>
		<p align="center"><input name="passwordLogin" type="password" value="Password" onclick="value=''"/></p>
		<p align="center"><input type="submit" value="Login"/></p>
	</form>
	</div>
</div>
<img src="analytics.jpg" />
</body>
</html>