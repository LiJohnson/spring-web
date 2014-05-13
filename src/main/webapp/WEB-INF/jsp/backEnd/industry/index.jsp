<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>制造联行业</title>
	
<c:import url="/public/head.jsp" />
<style>
	li a ,[has-child]{opacity: 0.2;} 
	li a:hover,li:hover > div [has-child],[has-child]:hover{opacity: 0.8;}
	.industry > ul{
		width: 428px;
		margin: 30px;
		padding: 10px;
		background-color: #fff;
		-webkit-border-radius: 6px;
		-moz-border-radius: 6px;
		border-radius: 6px;
		-webkit-box-shadow: 0 1px 4px rgba(0,0,0,.065);
		-moz-box-shadow: 0 1px 4px rgba(0,0,0,.065);
		box-shadow: 0 1px 4px rgba(0,0,0,.065);
		border: 1px solid rgb(233, 233, 233); 
	}
	li{
		display: block;
		width: 190px \9;
		margin: 0 0 -1px;
		border-left: 1px solid #e5e5e5;
		cursor: pointer;
	}
	[html-num]{
	    font-family: "Comic Sans MS",sans-serif;
	    color: rgb(141, 0, 0);
	    margin-right: 5px;
	    font-weight: bold;
	    margin-left: 0px;
	}
	
	li > div {padding: 8px 0px 8px 14px;r}
	li div:hover{background-color: rgba(204, 204, 204, 0.5);}
	
	li:hover,.click {border-left: 1px solid #653BE9;} 
	.click > div [has-child]{-webkit-transform: rotate(90deg);opacity: 0.8;}
	li a{margin-left: 15px;float: right;display: inline-block;}
	li a[has-child]{float: none;margin-left: 3px;} 
	ul li > ul{display: none;opacity:0;margin-left: 30px;}
	li.click > ul{display: block;opacity:1;}

	</style>
	
	<script>
	$(function(){
		var $html = $("body");
		var post = function( url ,  postData , cb){ 
			postData = postData || {};
			$.post(url , postData , cb);
		};
		
		var postForm = function(postData){
            if( !postData ) return false;
            post($.frontPath + "/backEnd/industry/handleIndustry" ,postData , function(data){
            	loadIndustry();
            });
        };
        //加载所有分类
		var loadIndustry = function(){
			var getInduHtml = function(list,level,parent){
				var $ul = $("<ul></ul>");
				level = level || 1;
				$.each(list||{},function(i,indu){
					indu.num = (!!parent?parent+".":"") + (i+1);
					var hasChild = indu.children && indu.children.length;
					var $li = $("<li><div><span html-num></span><span html-name></span><a class=close title='删除'>&times;</a><a edit-industry title='修改'><i class='glyphicon glyphicon-edit'></i></a><a add-industry title='添加'><i class='glyphicon glyphicon-plus'></i></a></div></li>");
					$li.setHtml(indu); 
					if( hasChild ){
						$li.addClass("click").find("div span[html-num]").append("<a has-child ><i class='glyphicon glyphicon-chevron-right' ></i></a>");
						$li.append(getInduHtml(indu.children , level + 1,i+1));
					}
					$li.data("indu",indu);
					
					$ul.append($li);
				});
				//二级没有添加子类功能
				if( level > 1 )$ul.find("a[add-industry]").remove();
			    return $ul;
			};
			
			post($.frontPath + "/backEnd/industry/list", function(data){
				$html.find("[list]").html(getInduHtml(data.list));
			});
		};
		
		$html.delegate("li","click",function(e){
			e.preventDefault();
			$(this).find(">ul").length && $(this).toggleClass("click");
			return false;
		});
		var inputHtml = "<div><input name='name' type='text' check-len='1-50' placeholder='请输入' /> 排序<input name=pos type=number check-type=num value=1 placeholder='排序' /></div>";
		//添加
		$html.delegate("a[add-industry]","click",function(){
			var indu = $(this).parents("li:eq(0)").data("indu")||{};
            var postData = {parentId:indu.industryId};
            var $input = $(inputHtml);
            $.box({
                title: "添加行业" + (indu.name ? "-" + indu.name : "" ),
                html: $input,
                ok: function(){
                    if($input.check()){
                    	postData = $.extend(postData,$input.getData());
                        postForm(postData);
                        return ;
                    }
                    return false;
                }
            });
            return false;
		});
		//修改
		$html.delegate("a[edit-industry]","click",function(){
            var postData = $(this).parents("li:eq(0)").data("indu")||{};
            var $input = $(inputHtml);
            $input.setData(postData);
            $.box({
                title: "修改行业",
                html: $input,
                ok: function(){
                    if($input.check()){
                    	postData = $.extend(postData,$input.getData());
                    	delete postData.children;
                        postForm(postData);
                        return ;
                    }
                    return false;
                }
            });
            return false;
        });
		//删除
		$html.delegate("li a.close","click",function(){
			var indu = $(this).parents("li:eq(0)").data("indu")||{};
			var postData = indu;
			
			$.box({
                title:"删除行业:" + indu.name,
                html:"您删除的是行业:" + indu.name + " (及其下面所有子行业)",
                ok:function(){
                	delete postData.children;
                    post($.frontPath + "/backEnd/industry/deleteIndustry", postData, function(data){
                    	loadIndustry();
                    });
                }
            });
			return false;
		});
		
		loadIndustry();
	});
	</script>
</head>
<body>
<a class="btn btn-default" add-industry style="margin: 15px 0 0 30px;">添加一级行业</a>
<div class="industry animate-children" list></div>
</body>
</html>