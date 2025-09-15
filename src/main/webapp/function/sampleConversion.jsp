<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像表示サンプル</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1>画像表示サンプル</h1>
		<a href="../home/Menu.html" class="radius-button">戻る</a>
	</div>

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
			<!-- <button type="submit">変換</button> -->
		</form>
	</div>
	
	<div class="output-preview-area">
		<div class="preview">
			<p>画像表示</p>
	    </div>
	</div>
	
	<!-- 画像表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
<script type="text/javascript" src="../js/common.js"></script>
</html>