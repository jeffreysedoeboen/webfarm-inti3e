    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.inti3e.model.Temperature" %>
    <%@page import="com.inti3e.database.dao.TempDao"%>
<br></br>
<br></br>
<script>
$(function() {
		$( "#datepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>
<form method="get" action="DateServlet.do?ID=temp">
<input name='ID' type="hidden" value="temp"></input>
<p>Date: <input name="datepicker" type="text" id="datepicker"></p>
<input type="submit" value="submit"></input></form>
<br></br>
<div style="
left:screen.width / 3;
top:screen.height / 2; 
color:#ffffff;
width:300px;
height:300px;
border: 5px;
float: left;
background-color:#000000;
overflow:auto;">
<table border="1">
<tr>
	<th>Date:</th>
	<th>Time:</th>
	<th>Temp:</th>
</tr>
</table>
</div>
<div id="tempdiv" style="height:400px;width:800px;margin-left: 50px;float:left;"></div>


<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>

