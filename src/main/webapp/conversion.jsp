<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像変換アプリ</title>
<link rel="stylesheet" type="text/css" href="design.css">
</head>
<body>
<h1>画像変換アプリ</h1>
変換したい画像を選んでください．
	<div class="input-area">
		<p>ドラッグ&amp;ドロップしてください</p>
		<form action="inputImage" method="post" enctype="multipart/form-data">
			<div id="input-image">
				<label for="file-input" style="cursor: pointer;">
				ここに画像をドラッグ＆ドロップしてください
				</label>
				<input type="file" name="file" id="file-input" accept="image/*" style="display: none;" onchange="this.form.submit()">
			</div>
			<button type="submit">変換</button>
		</form>
	</div>
	
	<div class="input-area">
		<p>プレビュー</p>
	</div>
	
	<div class="output-preview-area" id="preview">
		<p>画像表示</p>
		<div>
			<!-- サーブレットからリダイレクトされた画像URLを取得 -->
			<%-- <img alt="変換画像" src='<c:out value="${outimgUrl}" />'> --%>
			<p><c:out value="${outimgUrl}"></c:out>
			</p>
		</div>
	</div>
</body>
</html>