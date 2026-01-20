<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>画像の保存に成功しました。</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="画像の保存に成功しました。" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<div>
		<c:if test="${not empty saveImagePath}">
			<p>保存されたファイル名: ${saveImagePath}</p>
		</c:if>
		<a href="../home/Menu.html">ホーム</a>に戻る。
	</div>
	
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>