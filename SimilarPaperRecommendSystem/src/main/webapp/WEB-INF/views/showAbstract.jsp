<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>요약</title>
</head>
<body>
<%= request.getParameter("id") %>
Desktop.getDesktop().edit(new File("C:\fileOpen.txt"));

출처: https://myeonguni.tistory.com/1199 [명우니닷컴]
</body>
</html>