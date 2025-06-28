<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像変換アプリサンプル</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- メニューバー -->
	<script src="../js/header.js"></script>
	<h1>画像変換アプリ</h1>
	<a href="../home/Menu.html">戻る</a>

	<a>表示したい画像を選んでください．</a>
	<div class="input-area">
		<p>ドラッグ&amp;ドロップしてください</p>
		<form action="${pageContext.request.contextPath}/function/inputImage" method="post" enctype="multipart/form-data">
			<div id="input-image">
				<label for="file-input" style="cursor: pointer;">
				ここに画像をドラッグ＆ドロップしてください
				</label>
				<input type="file" name="imageFile" id="file-input" accept="image/*" style="display: none;">
			</div>
			<button type="submit">変換</button>
		</form>
	</div>
	
	<div class="preview">
		<p>プレビュー</p>
		<!-- JavaScriptで画像を表示 -->
	</div>
	
	<div class="output-preview-area">
		<p>画像表示</p>
		<c:if test="${not empty base64Image}">
	        <h3>リサイズされた画像</h3>
	        <a><img src="data:image/jpeg;base64,${base64Image}" alt="リサイズされた画像"/></a>
    	</c:if>
	</div>
	
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
<script type="text/javascript" src="../js/common.js"></script>
</html>