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
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- メニューバー -->
	<script src="../js/header.js"></script>
	<h1>例外のprintStackTrace</h1>

    <pre>
    	<%
			if (exception != null) {
	            exception.printStackTrace(new PrintWriter(out));
	        } else {
	            out.println("例外情報がありません。");
	        }
    	 %>
    </pre>
    
    <!-- フッター -->
    <div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>