<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	Object exception = request.getAttribute("exception");
%>
<%@page import="java.io.PrintWriter"%><html>

<head>
<meta charset="UTF-8">
<title>例外ページ</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1>例外のprintStackTrace</h1>
	</div>

    <pre>
    	<%
    	if (exception instanceof Exception) {
            ((Exception) exception).printStackTrace(new PrintWriter(out));
        } else if (exception instanceof String) {
            out.println((String) exception);
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