<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OCR</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1 style="text-align: center;">OCR</h1>
		<a href="../home/Menu.html" class="radius-button">戻る</a>
	</div>
	
	<!-- OCR Servlet 動かない -->
	
	<!-- アップロード -->
	<form action="${pageContext.request.contextPath}/function/opticalCharacterRecognition" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" id="file-input" name="imageFile" accept="image/*">
		<button type="submit">OCR実行</button>
	</form>
	
	<!-- OCR 結果 -->
	<c:if test="${not empty ocrResult}">
        <h3>OCR結果</h3>
        <pre>${ocrResult}</pre>
    </c:if>
	
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>