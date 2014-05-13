<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!doctype html>
<html lang="zh-CN" >
<head>
	<title>添加</title>
	<c:import url="/public/head.jsp"></c:import>
	<style>
	</style>
	<script>
	$(function(){ 
		var load = function(parentId,cb){
			if( $.type("parentId") == 'function' ){
				cb = parentId;
				parentId = 0;
			}
			$.post("load",{parentId:parentId||0},function(data){
				cb(data.list||[]);
			});
		};
		
		var print = function($li){
			if(!$li || !$li.length){
				var $ul = $(".nav:eq(0)");
			}else{
				var $ul = $li.find(">ul");
				if( !$ul.length )$ul = $("<ul class=nav></ul>").appendTo($li);
			}
			
			load($li ? $li.data("questionId"):0,function(list){
				$ul.empty();
				$.each(list,function(i,q){
					var $li = $("<li><span html-title /></span><span class='btn btn-xs glyphicon glyphicon-plus add'></span><span class='btn btn-xs glyphicon glyphicon-edit edit'></span><span class='btn btn-xs glyphicon glyphicon-remove delete'></span></li>");
					$li.attr("data-id",q.questionId);
					$li.data(q).setHtml(q);
					$ul.append($li);
				});
			});
		};
		
		$("div[left-nav]").on("click",".add",function(){
			var $input = $(".hide > form[add-form]").clone();
			var $li = $(this).parent();
			var q = $li.data();
			$.box({
				title:"添加于 " + (q.title||""),
				html:$input,
				ok:function(){
					$input.postData( "add" ,{parentId:q.questionId},function(data){
						$.log(data);
						print($li);
					});
				} 
			});
			return false;
		});
		
		$("div[left-nav]").on("click",".delete",function(){
			var $li = $(this).parent();
			$.box({
				html:"<p>确定删除 <b>'"+$li.data("title")+"</b>'?</p>",
				ok:function(){
					$.post( "delete" ,{questionId:$li.data("questionId")},function(data){
						$.log(data);
						print($li.parents("li:eq(0)"));
					});
				},
				cancel:function(){}
			});
			return false;
		});
		
		$("div[left-nav]").on("click","li",function(){
			var $li = $(this);
			$li.siblings(".active").removeClass("active");
			print($li.toggleClass("active"));
			$.log($li.data());
			return false;
		});
		print();
		/*****************************************************************/
		//right-side
		(function(){
			var $html = $("div[right-question]");
			var $add = $html.find("form");
			var $edit = $add.clone().attr("action","edit").appendTo("#edit");
			
			var addAnswer = (function(){
				var $tmp = $html.find("[data-answer-item]").remove().eq(0);
				return function($form,num,list){
					num = num*1 || 1;
					list = list || [];
					for( var i = 0 ; i < num ; i++ ){
						$form.find("[data-answer]")	.append($tmp.clone().setData( list[i]||{} ));
					}
				};
			})();
			
			var addOther = (function(){
				var $tmp = $html.find("[data-other='scoreMap'] [data-other-item]").remove().eq(0);
				return function($other,num,list){
					num = num*1 || 1;
					list = list || [];
					for( var i = 0 ; i < num ; i++ ){
						$other.append($tmp.clone().setData( list[i]||{} ));
					}
				};
			})();

			var initEdit = function(q){
				$edit.setData(q).data("question",q);
				$("a[href='#edit']").tab("show");
				$edit.find("[data-answer-item]").remove();
				$edit.find("[data-other='scoreMap']").empty();
				var answer = $.json.toObject(q.answer)||[];
				var other = $.json.toObject(q.other) || {};

				addAnswer($edit,answer.length , answer);
			    
				//other-scoreMap
				addOther($edit.find("[data-other='scoreMap']"),(other.scoreMap||[]).length , other.scoreMap||[]);
				//other-save
				$edit.find("[data-other='save'] input").prop("checked",!!other.save);
				//other-special
				$edit.find("[data-other='special'] input").prop("checked",!!other.save);
				//other-maxChose
				$edit.find("[data-other='maxChose'] input").val(other.maxChose);
			};

			var getAnswer = function($form){
				var answers = [];
				$form.find("[data-answer-item]").each(function(){
					var $this = $(this);
					var data = $this.getData();
					if(!data.text)return;
					answers.push(data);
				});
				return JSON.stringify(answers);
			};

			var getOther = function($form){
				var other = {};
				var list = [];
				$form.find("[data-other='scoreMap'] [data-other-item]").each(function(){
					list.push($(this).getData());
				});
				other.scoreMap = list;
				$.extend(other,$form.find("[data-other='save']").getData());
				$.extend(other,$form.find("[data-other='special']").getData());
				$.extend(other,$form.find("[data-other='maxChose']").getData());
				
				return JSON.stringify(other);
			};

			$html.find(".btn.add-answer").click(function(){
				addAnswer($(this).parents("form:eq(0)"),3);
			});
			$html.find("[data-answer]").on("click",".close",function(){
				$(this).parents("[data-answer-item]").remove();
			});

			$html.find(".btn.add-other").click(function(){
				var $other = $(this).parent().find("[data-other]");
				addOther($other,3);
			});

			$html.on("click","[data-other-item] .close",function(){
				$(this).parents("[data-other-item]").remove();
			});

			$("div[left-nav]").on("click",".edit",function(){
				var q = $(this).parent().data();
				initEdit(q);
				return false;
			});

			$add.submit(function(){
				var postData = $add.getData();
				var $li = $(".nav li.active");
				var q = $li.data();
				if( !q || !q.questionId )$.box("error no question");
				postData.answer = getAnswer($add);
				postData.other = getOther($add);
				postData.parentId = q.questionId;
				$.post("add",postData,function(){
					print($li);
				});
				return false;
			});

			$edit.submit(function(){
				var postData = $edit.getData();
				var q = $edit.data("question");

				if( !q || !q.questionId )$.box("error no question");
				postData.answer = getAnswer($edit);
				postData.other = getOther($edit);
				q=$.extend(q,postData);
				delete q.subQuestions;
				$.post("edit",q,function(){
					var $li = $("li[data-id='"+q.questionId+"']").parents("li:eq(0)");
					print($li.length ? $li : false);
				});
				return false;
			});
			addAnswer($add,3);
		})();
	});
	</script>
</head>
<body>
	<div class="row">
		<div class="col-md-2" left-nav >
			<a class="btn btn-default add"><span class="glyphicon glyphicon-plus"></span></a>
			<ul class="nav">
			</ul>
		</div>
		<div class="col-md-10" right-question >
			<ul class="nav nav-tabs">
			  <li class="active"><a href="#add" data-toggle="tab">添加</a></li>
			  <li><a href="#edit" data-toggle="tab">修改</a></li>
			</ul>
			<div class="tab-content">
			  <div class="tab-pane active" id="add">
			     <form action="add" class="form-horizontal" role="form">
		                <input type="hidden" name="parentId" />
		                <div class="form-group">
		                    <label for="type" class="col-sm-1 control-label">类型</label>
		                    <div class="col-sm-10">
		                        <select class="form-control" id="type" name="type" style="width:auto" >
		                            <option value="radio">单选</option>
		                            <option value="checkbox">多选</option>
		                            <option value="number">数字</option>
		                            <option value="total" >合计</option>
		                            <option value="text">其它</option>
		                            <option value="title" >标题</option>
		                        </select>
		                    </div>
		                </div>
		                
		                <div class="form-group">
		                    <label for="pos" class="col-sm-1 control-label">排序</label>
		                    <div class="col-sm-10">
		                    <input class="form-control" value="-1" style="width: 80px;" type="number" name="pos" id="pos" placeholder="排序" />
		                    </div>
		                </div>
		
		                <div class="form-group">
		                    <label for="score" class="col-sm-1 control-label">总分</label>
		                    <div class="col-sm-10">
		                    <input class="form-control" value="-1" style="width: 80px;" type="number" name="score" id="score" placeholder="总分" />
		                    </div>
		                </div>
		
		                <div class="form-group">
		                    <label for="title" class="col-sm-1 control-label">问题描述</label>
		                    <div class="col-sm-10">
		                    <textarea class="form-control" style="height:150px;" name="title" id="title" placeholder="问题描述" ></textarea>
		                    </div>
		                </div>
		
		                <div class="form-group">
		                    <label for="input1" class="col-sm-1 control-label">答案</label>
		                    <div class="col-sm-10">
		                        <div class="row" data-answer >
		                            <div class="col-md-4 alert" data-answer-item>
		                                <div class="row">
		                                <div class="col-md-7 to">
		                                    <input type="text" name="text" class="form-control" placeholder="答案">
		                                </div>
		                                <div class="col-md-3">
		                                    <input type="text" name="subScore" class="form-control" placeholder="分数">
		                                </div>
		                                <div class="col-md-1 checkbox" ><input type=checkbox name="child" value="child" /></div>
		                                <a class="close">&times;</a>
		                                </div>
		                            </div>
		                        </div>
		                        <a class="btn btn-default glyphicon glyphicon-plus add-answer"></a>
		                    </div>
		                </div>
		              
		                <div class="form-group" >
                            <label for="input1" class="col-sm-1 control-label"  >分数映射</label>
                            <div class="col-sm-10">
                                <div class="row" data-other="scoreMap"  >
                                    <div class="col-md-4" data-other-item />
                                        <div class="row">
                                        <div class="col-md-8 to">
                                            <input type="text" name="range" class="form-control" placeholder="范围:min-max">
                                        </div>
                                        <div class="col-md-3">
                                            <input type="text" name="scoreMap" class="form-control" placeholder="对应分数">
                                        </div>
                                        <a class="close">&times;</a>
                                        </div>
                                    </div>
                                </div>
                                <a class="btn btn-default glyphicon glyphicon-plus add-other"></a>
                            </div>
                        </div>
                        
                         <div class="form-group" >
                            <label for="input1" class="col-sm-1 control-label"  >是否保存</label>
                            <div class="col-sm-10">
                                <div class="row" data-other="save"  >
                                   <div class="col-md-4" >
                                        <div class="checkbox">
                                            <label> <input type="checkbox" name="save" value="save" /> 保存 </label>
                                        </div>
                                   </div>
                                   </div>
                            </div>
                        </div>
                        
                         <div class="form-group" >
                            <label for="input1" class="col-sm-1 control-label" >特别处理</label>
                            <div class="col-sm-10">
                                <div class="row" data-other="special"  >
                                   <div class="col-md-4" >
                                        <div class="checkbox">
                                            <label> <input type="checkbox" name="special" value="special" />特别处理 </label>
                                        </div>
                                   </div>
                                   </div>
                            </div>
                        </div>
                        
                        <div class="form-group" >
                            <label for="input1" class="col-sm-1 control-label" >最多选几个</label>
                            <div class="col-sm-10">
                                <div class="row" data-other="maxChose"  >
                                   <div class="col-md-4" >
                                        <div class="checkbox">
                                            <label> <input type="number" name="maxChose" value="0" />个(0表示没有限制) </label>
                                        </div>
                                   </div>
                                   </div>
                            </div>
                        </div>
		                
		                <div class="form-group">
		                    <div class="col-sm-offset-1 col-sm-10">
		                        <button type="submit" class="btn btn-default">submit</button>
		                    </div>
		                </div>
		            </form>
			  </div>
			  <div class="tab-pane" id="edit"></div>
			</div>
		</div>
	</div>
	
	<div class="hide">
	 <!-- add form -->
		<form class="form-horizontal" add-form>
		  <div class="form-group">
			<label for="title" class="col-sm-2 control-label">大分类</label>
			<div class="col-sm-10">
			  <input type="text" class="form-control" name="title" id="title" placeholder="标题">
			</div>
		  </div>
		  <div class="form-group">
			<label for="pos" class="col-sm-2 control-label">排序</label>
			<div class="col-sm-10">
			  <input type="number" class="form-control" id="pos" name="pos" placeholder="排序">
			</div>
		  </div>
		</form>
		<!-- end add form -->
	</div>
	<iframe src="/cache.jsp" width="0" height="0"></iframe>
</body>
</html>
