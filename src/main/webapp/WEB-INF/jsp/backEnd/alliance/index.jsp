<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>制造联</title>
	
<c:import url="/public/head.jsp" />

<link rel="stylesheet" href="//www.bootcss.com/p/bootstrap-switch/static/stylesheets/bootstrapSwitch.css">
<script src="//www.bootcss.com/p/bootstrap-switch/static/js/bootstrapSwitch.js"></script>

<style>
.table>thead>tr>th{
	vertical-align: middle;
	text-align: center;
}
#listTable tbody tr:hover{
	background-color: rgb(224, 224, 224);
}
#add{
	position: relative;
	top: 5px;
	left: 5px;
	width: 150px;
	height: 50px;
}
</style>
<script>
$(function(){
	var $form = $("#info").hide();
	
	$("#add").click(function(){
		$form.find("input , textarea").val("");
		$(".switch").bootstrapSwitch("setState", false);
		$("#area").areaSelect(1999);
		$("#industry").industrySelect();
		$form.slideDown();
	});
	$("#hide").click(function(){
		$form.slideUp();
	});
	
	//修改
	$("#alliance .glyphicon-edit").click(function(){
		var data = $(this).parents("tr:eq(0)").data();
		$form.setData(data).slideDown();
		$.each(["cost","crafts","quality","delivery","yield"], function(i, name){
			$form.find("[name=" + name + "]").val(data[name]).prop("checked", !!data[name]).change();
		});
		$("#area").areaSelect(data.areaId);
		$("#industry").industrySelect(data.industryId);
	});
	
	//删除
	$("#alliance .glyphicon-trash").click(function(){
		var $tr = $(this).parents("tr:eq(0)");
		$.box({
			title:"删除制造联确认",
			html:"亲，你确定要放弃“<span style='color:red;font-weight: bold;'>" + $tr.data("name") + "</span>”这个制造联吗？",
			okName:"删除吧",
			cancelName:"点错啦",
			ok:function(){
				$.post($.frontPath + "/backEnd/alliance/deleteAlliance", {allianceId:$tr.data("allianceId")}, function(data){
					data && data.count && $tr.remove();
				});
			},
			cancel:function(){}
		});
	});

});
</script>
</head>
<body>
<c:import url="../../frontEnd/alliance/temp.jsp?type=backEnd" />
<div class="well">
	<button class="btn btn-success" id="add">Add</button>
	<form class="form-horizontal" role="form" id="search" >
		
	</form>
</div>

<table class="table table-bordered" id="listTable">
	<thead>
		<tr>
			<th rowspan="2">企业名称</th>
			<th rowspan="2">官方网站</th>
			<th rowspan="2">企业规模</th>
			<th colspan="4">企业联系人（市场部/营销部）</th>
			<th colspan="2">固定电话</th>
			<th colspan="2">联系邮箱</th>
			<th rowspan="2">通信地址</th>
			<th rowspan="2">所属行业</th>
			<th rowspan="2">主营产品</th>
			<th colspan="6">企业制造优势</th>
			<th rowspan="2" style="width: 55px;">操作</th>
		</tr>
		<tr>
			<th>联系人1</th>
			<th>联系手机1</th>
			<th>联系人2</th>
			<th>联系手机2</th>
			<th>固话1</th>
			<th>固话2</th>
			<th>邮箱1</th>
			<th>邮箱2</th>
			<th style="width: 30px;">成本优势</th>
			<th style="width: 30px;">工艺优势</th>
			<th style="width: 30px;">质量控制</th>
			<th style="width: 30px;">交期</th>
			<th style="width: 30px;">产量</th>
			<th>对企业竞争力/优势的描述</th>
		</tr>
	</thead>
	<tbody id="alliance">
		<c:forEach items="${list }" var="alliance">
			<tr
				data-alliance-id="${alliance.allianceId }"
				data-name="${alliance.name }"
				data-website="${alliance.website }"
				data-scale="${alliance.scale }"
				data-name1="${alliance.name1 }"
				data-name2="${alliance.name2 }"
				data-mobilephone1="${alliance.mobilephone1 }"
				data-mobilephone2="${alliance.mobilephone2 }"
				data-officephone1="${alliance.officephone1 }"
				data-officephone2="${alliance.officephone2 }"
				data-email1="${alliance.email1 }"
				data-email2="${alliance.email2 }"
				data-area-id="${alliance.areaId }"
				data-stree="${alliance.stree }"
				data-industry-id="${alliance.industryId }"
				data-main-product="${alliance.mainProduct }"
				data-cost="${alliance.cost }"
				data-crafts="${alliance.crafts }"
				data-quality="${alliance.quality }"
				data-delivery="${alliance.delivery }"
				data-yield="${alliance.yield }"
				data-description="${alliance.description }"
			>
				<td>${alliance.name }</td>
				<td>${alliance.website }</td>
				<td>${alliance.scale }</td>
				<td>${alliance.name1 }</td>
				<td>${alliance.mobilephone1 }</td>
				<td>${alliance.name2 }</td>
				<td>${alliance.mobilephone2 }</td>
				<td>${alliance.officephone1 }</td>
				<td>${alliance.officephone2 }</td>
				<td>${alliance.email1 }</td>
				<td>${alliance.email2 }</td>
				<td><span data-area-id="${alliance.areaId }"></span>&nbsp;${alliance.stree }</td>
				<td><span data-industry-id="${alliance.industryId }"></span></td>
				<td>${alliance.mainProduct }</td>
				<td>${alliance.cost eq 1 ? "<i class='glyphicon glyphicon-ok'></i>" : "<i class='glyphicon glyphicon-remove'></i>" }</td>
				<td>${alliance.crafts eq 1 ? "<i class='glyphicon glyphicon-ok'></i>" : "<i class='glyphicon glyphicon-remove'></i>" }</td>
				<td>${alliance.quality eq 1 ? "<i class='glyphicon glyphicon-ok'></i>" : "<i class='glyphicon glyphicon-remove'></i>" }</td>
				<td>${alliance.delivery eq 1 ? "<i class='glyphicon glyphicon-ok'></i>" : "<i class='glyphicon glyphicon-remove'></i>" }</td>
				<td>${alliance.yield eq 1 ? "<i class='glyphicon glyphicon-ok'></i>" : "<i class='glyphicon glyphicon-remove'></i>" }</td>
				<td>${alliance.description }</td>
				<td>
					<i class="glyphicon glyphicon-edit" style="margin-right: 5px;color:green;" title="修改"></i>
					<i class="glyphicon glyphicon-trash" style="color:red;" title="删除"></i>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>
