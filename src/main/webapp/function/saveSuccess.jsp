<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像の保存に成功しました。</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1 style="text-align: center;">画像の保存に成功しました。</h1>
	</div>
	
	<div>
		<c:if test="${not empty saveImagePath}">
			<p>保存されたファイル名: ${saveImagePath}</p>
		</c:if>
		<a href="../home/Menu.html">ホーム</a>に戻る。
	</div>
	
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>