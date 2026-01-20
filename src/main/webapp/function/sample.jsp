<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>サンプル一覧</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="サンプル一覧" scope="request" />
	<jsp:include page="/common/header.jsp" />
	
	<div class="container">
		<div class="box">
			<a href="../function/sampleCalc.jsp">計算サンプル</a>
		</div>
		<div class="box">
			<a href="../function/sampleConversion.jsp">画像表示サンプル</a>
		</div>
		<div class="box">
			<a href="../function/exceptionMessage.jsp">例外ページ</a>
		</div>
	</div>

	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>