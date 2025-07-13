<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>サンプル計算用</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1 style="text-align: center;">サンプル計算用</h1>
		<a href="../home/Menu.html">戻る</a>
	</div>
	
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
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>