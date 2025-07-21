<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<select name="extension" size="1">
	        <c:forEach var="extension" items="${extensions}">
	            <option value="${extension}">${extension}</option>
	        </c:forEach>
    	</select>

		<button type="submit">変換</button>
	</form>
	
	<!-- アップロードした画像 -->
	<div class="preview">
		<p>アップロードするファイルが選択されていません．</p>
	</div>

	<!-- 拡張子変更後の画像 -->
	<c:if test="${not empty base64Image}">
        <h3>拡張子を変更した画像</h3>
        <h5>変更前の拡張子：${oldExtension}→変更後の拡張子：${newExtension}</h5>
        <a><img src="data:image/jpeg;base64,${base64Image}" alt="拡張子を変更した画像"/></a>
    </c:if>

    <!-- サイズ変更後の画像<!-- 画像表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	<!-- フッター -->
	<div>
		<script src="../js/footer.js"></script>
	</div>
</body>
</html>