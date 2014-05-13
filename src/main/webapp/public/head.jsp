<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set value="${pageContext.request.contextPath}" var="frontPath" />
<!-- JS_CSS -->
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />

<link  type="image/x-icon" rel="shortcut icon" href="${frontPath }/img/S.png">

<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${frontPath}/style/sv.css">
<link rel="stylesheet" href="//cxq.zhihuidao.com.cn/style/style.css">

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="//cxq.zhihuidao.com.cn/js/jquery.plugin.js"></script>
<script>$.frontPath = "${frontPath}";</script>
<script src="${frontPath}/js/sv.js" charset="utf-8" ></script>