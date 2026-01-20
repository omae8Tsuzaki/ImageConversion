<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>トリミング</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="トリミング" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<form action="${pageContext.request.contextPath}/function/trimming" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" id="file-input" name="imageFile" accept="image/*"><br>
		
		<p>x: <input type="number" name="x" required min="1"></p>
        <p>y: <input type="number" name="y" required min="1"></p>
        <p>width: <input type="number" name="width" required min="1"></p>
        <p>height: <input type="number" name="height" required min="1"></p>
		<button type="submit">変換</button>
	</form>
	
	<!-- アップロードした画像 -->
	<div class="preview">
		<p>アップロードするファイルが選択されていません．</p>
	</div>
	
	<!-- サイズ変更後の画像 -->
	<c:if test="${not empty base64Image}">
        <h3>トリミングされた画像</h3>
        <a><img src="data:image/jpeg;base64,${base64Image}"/></a>
    </c:if>

    <!-- 画像表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>