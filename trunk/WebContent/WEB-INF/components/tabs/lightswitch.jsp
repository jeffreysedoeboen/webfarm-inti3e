
<%@ page import="com.inti3e.database.dao.*" %>
<%
LightSensorDao lsd = new LightSensorDao();
boolean lightOn = lsd.getLightOn();
%>

<h1 align="center">The light is: [<%=lightOn ? "on":"off" %>]</h1>
<h1 align="center">Turn <%=lightOn ? "off":"on" %> the light:</h1>
<p align="center">
<% if (lightOn) { %>
	<a href="LightServlet.do?light=off"><img width="100" height="100" border="0" alt="Off" src="image/offbutton.png" /></a>
<% } else { %>
	<a href="LightServlet.do?light=on"><img width="100" height="100" border="0" alt="On" src="image/onbutton.png" /></a>
<% } %>
</p>