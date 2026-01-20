<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>画像表示サンプル</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="画像表示サンプル" scope="request" />
	<jsp:include page="/common/header.jsp" />

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
	<jsp:include page="/common/footer.jsp" />
</body>
<script type="text/javascript" src="../js/common.js"></script>
</html>