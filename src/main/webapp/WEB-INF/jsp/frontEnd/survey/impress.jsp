<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!doctype html>
<html lang="zh-CN">
<head>
<title>survey-impress</title>
<c:import url="/public/head.jsp"></c:import>
<style>
#impress .survey-question{width:1000px;}

</style>
<script>
$(function(){
	var api ;
	var getList = function(questionsList,index,h){
		var list = [];
		index = index || "";
		h = h || 1;
		$.each( questionsList || [] , function(i,q){
			q.index = index + (i+1) +".";
			q.h = h;
			list.push(q);
			var subList = getList(q.subQuestions,q.index,h+1);
			while(subList.length){
				list.push(subList.shift());
			}
		});
		return list;
	};
	var getQuestionHtml = function(q){
	    if(!q)return new $();
	    
	    var $question = $("<div class='survey-question'></div>");
	    $question.append( $("<h"+q.h+" class='survey-title'></h"+q.h+">").html(q.index+q.title).append("<span class='glyphicon glyphicon-warning-sign hide error'></span>") );
	   
	    var $answer = $("<form class='survey-answer row' ></form>");
	    q.answer = $.json.toObject(q.answer) || [];
	    q.other = $.json.toObject(q.other) || [];
	    $.each(q.answer ,function(i,a){
	        var $item = $("<div><div><label><input type=text name=text ><span html-text ></span><input type=hidden name=subScore /></label><div></div>");
	
	        $item.addClass("col-md-4").find(">div").addClass(q.type+" survey-answer-item");             
	        $item.setData(a).setHtml(a);
	        $item.find("input[name=text]").attr("type",q.type).attr("value",a.text).val(a.text).data("answer",a);
	        
	        if(a.child){
	            $answer.addClass("survey-child");
	            $question.addClass("survey-hide-child");
	        }
	        
	        if( q.type == "text" ||  q.type=="number" ){
	            $item.find("input[name=text]").val("").addClass("form-control");
	            $item.find("span").remove();
	        }
	        if( q.type == "radio" || q.type == "checkbox" ){
	            $item.find("input[name=text]").data("value",a.text)
	        }
	        $answer.append($item);
	    });
	    $question.append($answer);
	    $question.attr("data-id",q.questionId).attr("data-parent-id",q.parentId);
	    return $question;
	};
	var getAttr = function(s,q){
		var attr = {};
		var index = q.index.split(".");
		var a = "y,z,rotate".split(",");
		$.each(q.index.split("."),function(i,index){
			attr["data-"+a[ (i >= a.length? a.length-1:i) ]] = ((index*1)||1) * (i==2 ?90:3000) ;
			attr["data-x"] = s *2000 ;
		});
		attr.id = q.questionId;
		return attr;
	};
	
	var eachQuestion = function(cb,$list){
		$list = $list || $(".survey-question[data-parent-id='0']");
		$list.each(function(){
			var $q = $(this);
			!$q.hasClass("survey-hide-child") && eachQuestion(cb,$(".survey-question[data-parent-id='"+$q.data("id")+"']") );
			cb.call(this,this);
		});
		return $list;
	};
	
	var show = function(id,offset){
		var id = (id || $(".step.active > .survey-question").data("id"))*1;
        var ids = [];
        eachQuestion(function(){
            ids.push($(this).data("id")*1);
        });
        api.goto(ids[ids.indexOf(id)+offset]);
	};
	
	var getNext = function(id){
		
	};
	var getPrev = function(id){
		show(id,-1);
    };
    
	var getHtml = function(questionsList){
        var $list = new $();
        $.each( questionsList || [] , function(i,q){
        	var $q = getQuestionHtml(q);
        	$list = $list.add( $("<div class=step ></div>").attr(getAttr(i,q)).append($q));
        });
        return $list;
    };
	$.post("load",function(data){
		getList(data.list);
		$("#impress").append(getHtml(getList(data.list)) );
		api = impress();
		api.init();
		eachQuestion(function(){
			$.log(this);
		})
	});
});

</script>
</head>
<body>
<div class="fallback-message hide">
    <p>Hey , Shitter ! Go <a href="index">here</a> please</p>
</div>
<div id="impress" >
   
</div>
</body>
<script src="//bartaz.github.io/impress.js/js/impress.js"></script>

</html>
