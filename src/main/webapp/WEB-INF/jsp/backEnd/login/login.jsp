<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!doctype html>
<html lang="zh-CN">
<head>
<title>登录</title>
<c:import url="/public/head.jsp"></c:import>
<style>
</style>
<script>
	$(function() {

	});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div ckass="col-md-12">
				<form class="form-horizontal" method="post" action="login" >
					<div class="form-group">
						<label for="userName" class="col-sm-2 control-label">userName</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userName" id="userName" placeholder="userName">
						</div>
					</div>
					<div class="form-group">
						<label for="pass" class="col-sm-2 control-label">password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="pass" name="pass" placeholder="pass">
						</div>
					</div>
					 <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">login</button>
                            </div>
                        </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
