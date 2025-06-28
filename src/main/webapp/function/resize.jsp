<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像サイズ変更</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- メニューバー -->
	<script src="../js/header.js"></script>
	<h1 style="text-align: center;">画像サイズ変更</h1>
	<a href="../home/Menu.html">戻る</a>

	<form action="${pageContext.request.contextPath}/function/resize" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" name="imageFile" accept="image/*"><br>
		<p>幅: <input type="number" name="width" required min="1"></p>
        <p>高さ: <input type="number" name="height" required min="1"></p>
        <c:if test="${empty base64Image}">
			<div class="preview">
				<p>アップロードするファイルが選択されていません．</p>
			</div>
		</c:if>
		<button type="submit">変換</button>
	</form>
	
	<!-- 画像の表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	
	<!-- サイズ変更後の画像 -->
	<c:if test="${not empty base64Image}">
        <h3>リサイズされた画像</h3>
        <a><img src="data:image/jpeg;base64,${base64Image}" alt="リサイズされた画像"/></a>
    </c:if>
	
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>