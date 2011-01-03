
<%@ page import="com.inti3e.database.dao.*" %>
<%
LightSensorDao lsd = new LightSensorDao();
boolean lightOn = lsd.getLightOn();
%>

<script type="text/javascript" src="assets/js/lightswitch.js"></script>

<h1 id="lightstatus" align="center">The light is: [<%=lightOn ? "on":"off" %>]</h1>
<h1 id="lightmessage" align="center">Turn <%=lightOn ? "off":"on" %> the light:</h1>
<p id="lightbutton" align="center">
<% if (lightOn) { %>
	<a href="#" onclick="singleClick('off')" ondblclick="doubleClick('on')"><img width="100" height="100" border="0" alt="Off" src="image/offbutton.png" /></a>
<% } else { %>
	<a href="#" onclick="singleClick('on')" ondblclick="doubleClick('off')"><img width="100" height="100" border="0" alt="On" src="image/onbutton.png" /></a>
<% } %>
</p>