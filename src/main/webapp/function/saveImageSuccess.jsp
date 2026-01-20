<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>保存成功</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="画像の保存に成功しました。" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>