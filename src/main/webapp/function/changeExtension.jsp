<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>拡張子変換</title>
<link rel="stylesheet" type="text/css" href="../css/design.css">
</head>
<body>
	<!-- ヘッダー -->
	<div>
		<script src="../js/header.js"></script>
		<h1 style="text-align: center;">拡張子変換</h1>
		<a href="../home/Menu.html">戻る</a>
	</div>

	<form action="${pageContext.request.contextPath}/function/changeExtension" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" id="file-input" name="imageFile" accept="image/*"><br>

		<div class="preview">
			<p>アップロードするファイルが選択されていません．</p>
		</div>

		<select name="newExtension" required>
	        <c:forEach var="extension" items="${extensions}">
	            <option value="${extension}">${extension}</option>
	        </c:forEach>
    	</select>

		<button type="submit">変換</button>
	</form>

    <!-- サイズ変更後の画像<!-- 画像表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>