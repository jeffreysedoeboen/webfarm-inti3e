<%@page import="com.analytics.data.HitsDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.analytics.data.beans.PageHit"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
HitsDAO hits = new HitsDAO();
//pageContext.setAttribute("pagesByUser", hits.getPagesByUserId());
pageContext.setAttribute("pagesByIp", hits.getPagesByIp(request.getRemoteAddr()));
%>

<div id="wrapper">
	<div id="content">
		<h1>Statistics</h1>
		<div class="infobox" style="float:none;">
			<div class="row">
				<div class="left">Totaal aantal hits</div>
				<div class="right">${hits}</div>
			</div>
		</div>
		<div class="infobox">
			<c:forEach items="${browsers}" var="browser">
				<div class="row">
					<div class="left">${browser.value}</div>
					<div class="right">${browser.count}</div>
				</div>
			</c:forEach>
		</div>
		<div class="infobox">
			<c:forEach items="${languages}" var="language">
				<div class="row">
					<div class="left">${language.value}</div>
					<div class="right">${language.count}</div>
				</div>
			</c:forEach>
		</div>
		<div class="infobox">
			<c:forEach items="${ips}" var="ip">
				<div class="row">
					<div class="left">${ip.value}</div>
					<div class="right">${ip.count}</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>