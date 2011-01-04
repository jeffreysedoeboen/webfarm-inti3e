<div>
<br></br>
<br></br>
<script type="text/javascript">	
$(function() {
		$( "#date_light" ).datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>
<div style="float: left;">
	<p>Date: <input name="datepicker" type="text" value="<%
		java.util.Calendar calendar = java.util.Calendar.getInstance(); 
		out.print(calendar.get(java.util.Calendar.DAY_OF_MONTH) + "-");
		out.print((calendar.get(java.util.Calendar.MONTH)+1) + "-");
		out.print(calendar.get(java.util.Calendar.YEAR));
		%>" id="date_light" readonly="readonly"></p>
	<input type="button" onclick="getLightByDate()" value="submit"></input>
</div>
<br></br>
<div id="light_table_div"
style="margin-left: 200px;
position: relative;
color:#ffffff;
width:300px;
height:300px;
border: 5px;
float: left;
background-color:#000000;
overflow:auto;">
<table border="1">
<thead>
<tr>
	<th>Date:</th>
	<th>Time:</th>
	<th>Light:</th>
</tr>
</thead>
<tbody id="light_table_body"></tbody>
</table>
</div>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
</div>