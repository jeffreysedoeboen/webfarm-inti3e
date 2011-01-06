<script type="text/javascript">
$(function() {
	createTempTable();
});
</script>

<table align="center" width="1000"><tr><td>
<div id="tempdiv" style="height: 400px; width: 800px; margin-left: 50px; float: left;"></div>
</td></tr></table>

<div id="temp_table_div"
style="color:#ffffff;
width:100%;
height:300px;
border:5px;
background-color:#000000;
overflow:auto;">
<table style="border:3px #363636 solid"
align="center" width="50%" bgcolor="#222222">
	<thead>
		<tr>
			<th>Date:</th>
			<th>Time:</th>
			<th>Temp:</th>
		</tr>
	</thead>
	<tbody id="temp_table_body"></tbody>
</table>
</div>
<br><br>