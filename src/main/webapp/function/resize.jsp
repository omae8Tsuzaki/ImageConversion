<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像サイズ変更</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
	<!-- メニューバー -->
	<script src="../js/header.js"></script>
	
	<h1 style="text-align: center;">画像サイズ変更</h1>
	<a href="../home/Menu.html">戻る</a>

	<form action="resize" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" name="imageFile" accept="image/*" class="input"><br>
		幅: <input type="number" name="width" required><br>
        高さ: <input type="number" name="height" required><br>
		<div class="preview">
			<p>アップロードするファイルが選択されていません．</p>
		</div>

		<button type="submit">変換</button>
	</form>
	
	<!-- 画像の表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	
	<!-- サイズ変更後の画像 -->
	<c:if test="${not empty resizedImagePath}">
        <h3>リサイズされた画像</h3>
        <img src="${pageContext.request.contextPath}/${resizedImagePath}" alt="リサイズされた画像">
    </c:if>
	
	<!-- フッター -->
	<script src="../js/footer.js"></script>
</body>
</html>