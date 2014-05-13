<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!doctype html>
<html lang="zh-CN">
<head>
<title>survey-ok</title>
<c:import url="/public/head.jsp"></c:import>
</head>
<body>
	<div class="container survey-ok">
		<div class="row">
			<div class="col-md-offset-3 col-md-6">
				<div class="row alert alert-warning ">
					<p>
						您的得分是<span class="badge" style="background:#5bc0de;zoom: 2">${survey.score}</span>，此处还隐藏了神秘内容，<a href="login" class="btn btn-warning">登录</a>后可查看
					</p>
				</div>
			</div>
		</div>
	</div>
	<iframe src="/cache.jsp" width="0" height="0"></iframe>
</body>
</html>
