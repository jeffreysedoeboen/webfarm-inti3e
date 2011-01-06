<%@page import="com.analytics.data.StatisticsDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.analytics.data.beans.RowBean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
StatisticsDAO statistics = new StatisticsDAO();
pageContext.setAttribute("hits", statistics.getHits());
pageContext.setAttribute("browsers", statistics.getHitsPerBrowser());
pageContext.setAttribute("languages", statistics.getHitsPerLanguages());
pageContext.setAttribute("ips", statistics.getHitsPerIp());
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