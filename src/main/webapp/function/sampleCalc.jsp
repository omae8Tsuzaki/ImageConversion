<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>サンプル計算用</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="サンプル計算用" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<form action="${pageContext.request.contextPath}/function/sampleCalc" method="get">
		<h2>足し算フォーム</h2>
		<input type="text" name="num1" placeholder="数値1" required>
		+ 
		<input type="text" name="num2" placeholder="数値2" required>
		<input type="submit" value="計算">
	</form>
	=
	<c:out value="${result}" default="未入力"></c:out>
	
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>