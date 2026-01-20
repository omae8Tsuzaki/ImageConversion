<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>OCR</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="OCR" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<!-- OCR Servlet 動かない -->
	
	<!-- アップロード -->
	<form action="${pageContext.request.contextPath}/function/opticalCharacterRecognition" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" id="file-input" name="imageFile" accept="image/*">
		<button type="submit">OCR実行</button>
	</form>
	
	<!-- OCR 結果 -->
	<c:if test="${not empty ocrResult}">
        <h3>OCR結果</h3>
        <pre>${ocrResult}</pre>
    </c:if>
	
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>