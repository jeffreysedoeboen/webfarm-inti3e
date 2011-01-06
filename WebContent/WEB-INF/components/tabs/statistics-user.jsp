<%@page import="com.analytics.data.HitsDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.analytics.data.beans.PageHit"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<input type='hidden' value='<%= request.getRemoteAddr()%>' id="selectedIp"/>
</c:if>

<div id="wrapper">
	<div id="content">
		<h1>User Statistics</h1>
		<div class="infobox" style="float:none;">
			<div class="row">
				<div class="left">IP: </div>
				<div id='ipAdress' class="right"></div>
			</div>
			<div id='pages'></div>
		</div>
	</div>
</div>