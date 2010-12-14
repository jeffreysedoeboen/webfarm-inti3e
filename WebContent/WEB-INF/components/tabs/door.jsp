<div>
<br></br>
<br></br>
<script>
$(function() {
		$( "#date_door" ).datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>
<div style="float: left;">
	<p>Date: <input name="datepicker" type="text" id="date_door" readonly="readonly"></p>
	<input type="button" onclick="getDoorByDate()" value="submit"></input>
</div>
<br></br>
<div style="
margin-left: 200px;
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
	<th>Door:</th>
</tr>
</thead>
<tbody id="door_table_body"></tbody>
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