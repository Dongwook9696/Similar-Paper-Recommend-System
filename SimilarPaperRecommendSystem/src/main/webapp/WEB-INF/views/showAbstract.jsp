<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���</title>
</head>
<body>
<%= request.getParameter("id") %>
Desktop.getDesktop().edit(new File("C:\fileOpen.txt"));

��ó: https://myeonguni.tistory.com/1199 [���ϴ���]
</body>
</html>