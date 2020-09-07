<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<meta charset="UTF-8"/>
	<title>Similar Paper Recommend System </title>
</head>
<body>
<h1>
	Similar Paper Recommend System  
</h1>


	<form action ="./home" method = "post">
	
	PAPER : <input type = "text" name = "paper">
	<button type = "submit" >SEARCH</button>
	
	</form>
	
	<table>
		<thead>
			<tr>
				<th>ranking</th>
				<th>paper id</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>dkfjwlf</td>
			</tr>
		</tbody>	
	</table>

</body>
</html>
