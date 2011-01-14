<%@page import="com.analytics.data.HitsDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.analytics.data.beans.PageHit"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<center>
<h1>User statistics</h1>
<label>IP: </label>
<c:if test="${user.admin}">
	<select id="selectedIp" onchange="getStatistics()">
			<%
			HitsDAO hits = new HitsDAO();
				for (String ip :hits.getAllIp()) {
					out.print("<option>" + ip + "</option>");
				}
			%>
	</select> 
</c:if>

<c:if test="${!user.admin}">
<input id="selectedIp" value="<%= request.getRemoteAddr()%>" type="hidden"></input>
<label><%= request.getRemoteAddr()%></label>
</c:if>

<div id="statistics_table_div"
style="color:#ffffff;
width:100%;
height:400px;
border:5px;
background-color:#000000;
overflow:auto;">
<table style="border:3px #363636 solid;"
align="center" width="40%" bgcolor="#222222">
	<thead>
		<tr>
			<th>Page</th>
			<th>Hits</th>
		</tr>
	</thead>
	<tbody id="stats_table_body"><tr><td></td></tr></tbody>
</table>
</div>
</center>
<br>
<br>
<br>