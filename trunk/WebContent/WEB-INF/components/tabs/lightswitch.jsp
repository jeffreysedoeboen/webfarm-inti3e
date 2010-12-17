<%@ page import="com.inti3e.database.dao.*" %>
<%
LightSensorDao lsd = new LightSensorDao();
boolean lightOn = lsd.getLightOn();
%>

<h1 align="center">Turn <%=lightOn ? "off":"on" %> the light</h1>
<br></br>
<p align="center">
<a href="LightServlet.do?light=on"><img border="0" height="154" width="144" alt="Yes" src="image/onbutton.png" /></a>
<a href="LightServlet.do?light=off"><img border="0" height="154" width="144" alt="No" src="image/offbutton.png" /></a>
<!--<% if (lightOn) { %>-->
<!--	<img alt="Yes" onclick="LightServlet.do?light=on" src="image/yes-button.png" />-->
<!--<% } else { %>-->
<!--	<img alt="No" onclick="LightServlet.do?light=off" src="image/no-button.png" />-->
<!--<% } %>-->
</p>