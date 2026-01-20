<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />
<title>拡張子変換</title>
</head>
<body>
	<!-- ヘッダー -->
	<c:set var="pageTitle" value="拡張子変換" scope="request" />
	<jsp:include page="/common/header.jsp" />

	<form action="${pageContext.request.contextPath}/function/changeExtension" method="post" enctype="multipart/form-data">
		<p>画像をアップロードしてください．</p>
		<input type="file" id="file-input" name="imageFile" accept="image/*"><br>
		<input type="hidden" name="backUrl" value="${pageContext.request.requestURL}">

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
        <img src="data:image/jpeg;base64,${base64Image}" alt="拡張子を変更した画像"/>
        
        <form action="${pageContext.request.contextPath}/function/saveImage" method="post" enctype="multipart/form-data">
            <!-- <input type="hidden" name="base64Image" value="${base64Image}"/>  -->
			<input type="hidden" name="base64Image" value="<c:out value='${base64Image}' escapeXml='false'/>" />
            <input type="hidden" name="extension" value="${newExtension}"/>
            <input type="hidden" name="fileName" value="${fileName}"/>
            <input type="hidden" name="backUrl" value="${pageContext.request.requestURL}"/>
            <button type="submit">画像を保存</button>
        </form>
    </c:if>

    <!-- 画像表示 -->
	<script type="text/javascript" src="../js/common.js"></script>
	<!-- フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>