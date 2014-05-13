<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>


<style>
.group{
	margin-top: 10px;
	border-bottom: 2px solid rgb(190, 190, 190);
}
.group h3{
	text-align: center;
}
.group h4{
	margin-left: 20%;
}
.group h3 , .group h4{
	font-weight: bold;
	color: rgb(99, 99, 99);
}
.info{
	color: rgb(122, 122, 122);
}
select{
	height: 30px;
	margin-right: 5px;
}
textarea{
	resize: vertical;
}
.glyphicon-ok{
	color: green;
}
.glyphicon-remove{
	color: red;
}
.switch i{
	color:white;
}
.opt{
	margin-top: 10px;
	text-align: center;
}
.optSun{
	width: 40%;
	height: 50px;
	line-height: 40px;
	font-weight: bold;
}
</style>
<script>
$(function(){
	var $form = $("#info");
	var checkType = function(){
		var result = true;
		$.each($form.find("[check-type]"), function(){
			if(!this.value){
				//值为空时，没有check-len就不检测
				var flag = !this.hasAttribute("check-len");
				result &= flag;
				flag && $(this).checkResult(true);
			}else{
				result &= $(this).check();
			}
		});
		return result;
	};
	$form.submit(function(){
		var flag = checkType();
		flag &= $form.find("input:not([check-type]),textarea").check();
		flag && $.post($form.attr("action"), $form.getPostData(), function(data){
			if(data && data.count){
				$.box({
					title:"温馨提示",
					html:"制造联企业操作成功",
					okName:"刷新看下~",
					cancelName:"忽略",
					ok:function(){
						$.refresh();
					},
					cancel:function(){}
				});
			}else{
				$.box({
					title:"温馨提示",
					html:"操作失败",
					close:function(){}
				});
			}
		});
		return false;
	});
	
	$(".switch").on("switch-change", function (e, data) {
	    $(data.el).val(!!data.value ? 1 : 0);
	});
	
	$("#alliance span[data-area-id]").area();
	$("#alliance span[data-industry-id]").industry();

});
</script>

<form class="form-horizontal well" role="form" id="info" action="${frontPath }/alliance/handleAlliance">
	<input type="hidden" name="allianceId" value="${alliance.allianceId }">
	<div class="group">
		<h3>佛山制造企业信息收录</h3>
	</div>
	
	<table class="table">
		<tr>
			<td style="border-right: 1px solid #C36F79; width: 50%;">
				<div class="group">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label"><span style="color:red;">*</span>企业名称</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name" name="name" value="${alliance.name }" placeholder="企业叫什么名字呦" check-len="1">
						</div>
					</div>
					<div class="form-group">
						<label for="website" class="col-sm-2 control-label">官方网站</label>
						<div class="col-sm-5">
							<input type="url" class="form-control" id="website" name="website" value="${alliance.website }" placeholder="http://~~">
						</div>
					</div>
					<div class="form-group">
						<label for="scale" class="col-sm-2 control-label">企业规模</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="scale" name="scale" value="${alliance.scale }" placeholder="这是个范围吧，亲">
						</div>
					</div>
				</div>
				
				<div class="group">
					<div class="form-group">
						<label class="col-sm-2 control-label">所属行业</label>
						<div class="col-sm-5">
							<div id="industry" data-industry-id="${alliance.industryId }" ><input name="industryId" type="hidden" value="${alliance.industryId }"/></div>
						</div>
					</div>
					<div class="form-group">
						<label for="mainProduct" class="col-sm-2 control-label">主营产品</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="mainProduct" name="mainProduct" value="${alliance.mainProduct }" placeholder="主营产品">
						</div>
					</div>
				</div>
				
				<div class="group">
					<h4>企业制造优势</h4>
					<div class="form-group">
						<label for="cost" class="col-sm-2 control-label">成本优势</label>
						<div class="col-sm-5">
							<div class="switch" 
							data-on-label="<i class='glyphicon glyphicon-ok'></i>" data-off-label="<i class='glyphicon glyphicon-remove'></i>"
							data-on="success" data-off="danger">
			    				<input type="checkbox" name="cost" id="cost" value="${alliance.cost eq 1 ? 1 : 0 }"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="crafts" class="col-sm-2 control-label">工艺优势</label>
						<div class="col-sm-5">
							<div class="switch" 
							data-on-label="<i class='glyphicon glyphicon-ok'></i>" data-off-label="<i class='glyphicon glyphicon-remove'></i>"
							data-on="success" data-off="danger">
			    				<input type="checkbox" name="crafts" id="crafts" value="${alliance.crafts eq 1 ? 1 : 0 }"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="quality" class="col-sm-2 control-label">质量控制</label>
						<div class="col-sm-5">
							<div class="switch" 
							data-on-label="<i class='glyphicon glyphicon-ok'></i>" data-off-label="<i class='glyphicon glyphicon-remove'></i>"
							data-on="success" data-off="danger">
			    				<input type="checkbox" name="quality" id="quality" value="${alliance.quality eq 1 ? 1 : 0 }"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="delivery" class="col-sm-2 control-label">交期</label>
						<div class="col-sm-5">
							<div class="switch" 
							data-on-label="<i class='glyphicon glyphicon-ok'></i>" data-off-label="<i class='glyphicon glyphicon-remove'></i>"
							data-on="success" data-off="danger">
			    				<input type="checkbox" name="delivery" id="delivery" value="${alliance.delivery eq 1 ? 1 : 0 }"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="yield" class="col-sm-2 control-label">产量</label>
						<div class="col-sm-5">
							<div class="switch" 
							data-on-label="<i class='glyphicon glyphicon-ok'></i>" data-off-label="<i class='glyphicon glyphicon-remove'></i>"
							data-on="success" data-off="danger">
			    				<input type="checkbox" name="yield" id="yield" value="${alliance.yield eq 1 ? 1 : 0 }"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">对企业竞争力/优势的描述</label>
						<div class="col-sm-8">
							<textarea class="form-control" rows="3" id="description" name="description" value="${alliance.description }" placeholder="对企业竞争力/优势的描述"></textarea>
						</div>
					</div>
				</div>
			</td>
			
			<td>
				<div class="group">
					<div class="form-group">
						<label class="col-sm-2 control-label">通信地址</label>
						<div class="col-sm-5">
							<div id="area" data-area-id="${alliance.areaId gt 0 ? alliance.areaId : 1999 }" ><input name="areaId" value="${alliance.areaId gt 0 ? alliance.areaId : 1999 }" type="hidden" /></div>
						</div>
					</div>
					<div class="form-group">
						<label for="stree" class="col-sm-2 control-label">&nbsp;</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="stree" name="stree" value="${alliance.stree }" placeholder="街道 门牌号 大厦名称 房间号码" />
						</div>
					</div>
				</div>
				<div class="group">
					<h4>企业联系人（市场部/营销部）</h4>
					<div class="form-group">
						<label for="name1" class="col-sm-2 control-label">联系人1</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name1" name="name1" value="${alliance.name1 }" placeholder="第一个联系人的名字">
						</div>
					</div>
					<div class="form-group">
						<label for="mobilephone1" class="col-sm-2 control-label">联系手机1</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="mobilephone1" name="mobilephone1" value="${alliance.mobilephone1 }" placeholder="第一个联系人的手机" check-type="mobilePhone">
						</div>
					</div>
					<div class="form-group">
						<label for="name2" class="col-sm-2 control-label">联系人2</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name2" name="name2" value="${alliance.name2 }" placeholder="第二个联系人的名字">
						</div>
					</div>
					<div class="form-group">
						<label for="mobilephone2" class="col-sm-2 control-label">联系手机2</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="mobilephone2" name="mobilephone2" value="${alliance.mobilephone2 }" placeholder="第二个联系人的手机" check-type="mobilePhone">
						</div>
					</div>
				</div>
				
				<div class="group">
					<h4>固定电话</h4>
					<div class="form-group">
						<label for="officephone1" class="col-sm-2 control-label">固话1</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="officephone1" name="officephone1" value="${alliance.officephone1 }" placeholder="第一个固定电话" check-type="officePhone">
						</div>
					</div>
					<div class="form-group">
						<label for="officephone2" class="col-sm-2 control-label">固话2</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="officephone2" name="officephone2" value="${alliance.officephone2 }" placeholder="第二个固定电话" check-type="officePhone">
						</div>
					</div>
				</div>
				
				<div class="group">
					<h4>联系邮箱</h4>
					<div class="form-group">
						<label for="email1" class="col-sm-2 control-label">邮箱1</label>
						<div class="col-sm-5">
							<input type="email" class="form-control" id="email1" name="email1" value="${alliance.email1 }" placeholder="第一个邮箱" check-type="email">
						</div>
					</div>
					<div class="form-group">
						<label for="email2" class="col-sm-2 control-label">邮箱2</label>
						<div class="col-sm-5">
							<input type="email" class="form-control" id="email2" name="email2" value="${alliance.email2 }" placeholder="第二个邮箱" check-type="email">
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	
	<div class="form-group opt">
		<button class="btn btn-success optSun" type="submit">确定</button>
		<c:if test="${param.type eq 'backEnd' }">
			<a href="javascript:;" class="btn btn-default optSun" id="hide">收起</a>
		</c:if>
	</div>
</form>
