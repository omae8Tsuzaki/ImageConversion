<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	Exception exception = (Exception)request.getAttribute("exception");
%>
<%@page import="java.io.PrintWriter"%><html>

<head>
<meta charset="UTF-8">
<title>例外ページ</title>
</head>
<body>
<h1>例外のprintStackTrace</h1>
    <pre>
        <% exception.printStackTrace(new PrintWriter(out)); %>
    </pre>
</body>
</html>