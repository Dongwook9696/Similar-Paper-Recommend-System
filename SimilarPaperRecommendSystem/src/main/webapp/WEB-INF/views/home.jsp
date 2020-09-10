<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
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


	<form action ="./abstractList">
	
	PAPER : <input type = "text" name = "paper">
	<button type = "submit" >SEARCH</button>
	
	</form>
	
	<table border="1" style="width:600px">
	    <caption>게시판</caption>
	    <colgroup>
	        <col width='8%' />
	        <col width='*%' />
	        <col width='15%' />
	        <col width='15%' />
	    </colgroup>
	    <thead>
	        <tr>
	            <th>id</th>
	            <th>abstract</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="listview" items="${listview}" varStatus="status">   
	            <tr>
	                <td><c:out value="${listview.id}"/></td>
	                <td><c:out value="${listview.abstract_}"/></td>
	            </tr>
	        </c:forEach>
	    </tbody>
		</table> 
	


</body>
</html>
