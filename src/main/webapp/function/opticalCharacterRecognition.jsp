<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OCR</title>
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1 style="text-align: center;">OCR</h1>
		<a href="../home/Menu.html">戻る</a>
	</div>
	
	<!-- アップロード -->
	<form action="${pageContext.request.contextPath}/function/opticalCharacterRecognition" method="post" enctype="multipart/form-data">
		<input type="file" name="imageFile" accept="image/*">
		<input type="submit" value="OCR実行">
	</form>
	
	<!-- OCR 結果 -->
	
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>