<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	Object exception = request.getAttribute("exception");
%>
<%@page import="java.io.PrintWriter"%><html>

<head>
<jsp:include page="/common/head.jsp" />
<title>例外ページ</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="例外のprintStackTrace" scope="request" />
	<jsp:include page="/common/header.jsp" />

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
    <jsp:include page="/common/footer.jsp" />
</body>
</html>