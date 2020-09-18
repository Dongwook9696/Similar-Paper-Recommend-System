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
	
	PAPER : <input type = "text" name = "paper_id" id = "paper_id">
	<button type = "submit" >SEARCH</button>
	
	</form>
	
	<table border="1" style="width:600px">
	    <colgroup>
	        <col width='8%' />
	        <col width='*%' />
	        <col width='15%' />
	        <col width='15%' />
	    </colgroup>
	    <thead>
	        <tr>
	            <th></th>
	            <th>유사 논문</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="id" items="${listview}" varStatus="status">   
	            <tr>
	            	<th>${status.count}</th>
	            	<th><a href = "./showAbstract?id=${id}"><c:out value="${id}"/></a></th>
	            </tr>
	        </c:forEach>
	    </tbody>
		</table>
	


</body>
</html>
