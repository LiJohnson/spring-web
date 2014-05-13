<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!doctype html>
<html lang="zh-CN" >
<head>
	<title>survey</title>
	<c:import url="/public/head.jsp"></c:import>
	<style>
	</style>
	<script>
	var debug = false;
	$(function(){
		var sidebar = function(list){
			var $bar = $(".survey-sidebar");
			
			var print = function( $con , list ,index){
				var $ul = $("<ul class=nav ></ul>");
				index = index || "";
				$.each(list||[],function(i,q){
					var $li = $("<li></li>").attr("data-id", q.questionId);
					var subIndex = (index+(i+1))+".";
					$li.append("<a href='#id-"+q.questionId+"'>"+ subIndex +q.title+"</a>");
					print($li,q.subQuestions,subIndex);
					$ul.append($li);
				});
				$con.append($ul);
			};
			print($bar,list);
		};
		
		var getAnswerHtml = function(q){
			if(!q)return "";
			var $answer = $("<form class='answer row' ></form>");
			q.answer = $.json.toObject(q.answer) || [];
			q.other = $.json.toObject(q.other) || [];
			$.each(q.answer ,function(i,a){
				var $item = $("<div><div><label><input type=text name=text ><span html-text ></span><input type=hidden name=subScore /></label><div></div>");

				$item.addClass("col-md-4").find(">div").addClass(q.type+" survey-answer-item");				
                $item.setData(a).setHtml(a);
                $item.find("input[name=text]").attr("type",q.type).attr("value",a.text).val(a.text).data("answer",a);
                
                if(a.child){
                	$answer.addClass("survey-child");
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
			return $answer;
		};
		
		var survey = function(list){
		    var $survey = $(".survey-contain");
		    
            var print = function( $con , list ,index){
                index = index || "";
                $.each(list||[],function(i,q){
                	var $question = $("<div class=survey-question data-id='"+q.questionId+"' ></div>");                	
                    var subIndex = (index+(i+1))+".";
                    var h = subIndex.match(/\./g).length;                    
                    var $title = $("<h"+h+"></h"+h+" >").html(subIndex+q.title).attr("id","id-"+ q.questionId);
                    
                    $title.addClass("survey-title").append("<span class='glyphicon glyphicon-warning-sign hide error'></span>");
                    $question.data("question",q);
                    
                    $question.append($title).append(getAnswerHtml(q));
                    print($question,q.subQuestions,subIndex);
                    $.each(q.answer,function(i,a){
                    	if(a.child){
                    		$question.addClass("survey-hide-child");
                    		return false;
                    	}
                    });
                    $con.append($question);
                    if( index == "" ){
                    	$question.append("<div class='survey-process'><a class='btn btn-default btn-warning btn-xs survey-last' >上一步</a><a class='btn btn-default btn-warning btn-xs survey-next' >好了，下一步</a></div>").addClass("hide");
                    }
                });
            };
            
            print($survey,list);
		};
			    
		var SV = window.SV = {};
		
		//count
		(function(){
			var saveed = [];
			var scoreMap = function(scoreMap,num){
				var s = 0;
				num = num *1 || 0;
				$.each(scoreMap||[],function(i,m){
                    var range = m.range.split("-");
                    if( num >= range[0] && num <= range[1] ){
                        s += (m.scoreMap*1||0);
                    }
                });
				return s;
			}
			var count = function( $con ){
				var score = {score:0,sub:{}};
				$con.find(">.survey-question").each(function(){
				    var $this = $(this);
				    var q = $this.data("question");
				    var subScore = 0 , sub;				   
				    $this.find(">.answer input:checked").each(function(){
				    	subScore += ($(this).siblings("input[name=subScore]").val()*1||0)
				    });
				    
				    $this.find(">.answer input[type=number]").each(function(){
				    	if(!$(this).val())return;
				    	var num = $(this).val()*1;
				    	subScore += scoreMap(q.other.scoreMap,num);
                    });
				    
				    (function(){
	                    var length = $this.find(">.answer input:checkbox:checked").length;
	                    subScore += length && scoreMap(q.other.scoreMap,length);
				    })();
				    
				    if( q.other.save ){
				    	saveed.push({
                            result:$this.find(">.answer").getData(),
                            title:q.title,
                            questionId:q.questionId
                        });
				    }
				    
				    if( q.other.special ){
				    	subScore = 0;
				    	var length = $this.find(">.answer input:checkbox:checked:not([value='首页'])").length;
                        subScore += length && scoreMap(q.other.scoreMap,length);
                        if( $this.find(">.answer input[value='首页']").prop("checked") ){
                        	subScore += 5;
                        }
                        if( $this.find(">.answer input[value='不知道']").prop("checked") ){
                            subScore = 0;
                        }
                    }
				    
				    sub = $this.hasClass("survey-hide-child") ? {score:0} : count($this);
				    
				    score.sub[q.questionId] = {score:subScore + sub.score ,questionId:q.questionId ,total :q.score,sub:sub};
				    score.score += subScore;
				    score.score += sub.score;
				    
				    if(debug){
					    //for debug
					    $this.find(">.survey-title").attr("score",subScore);
					    $this.attr("score",sub.score + subScore );
					    //end dubug
				    }
				    
				    $this.data("count",sub);
				    
				});
				return score;
			};
			
			SV.count = count;
                
			SV.getResult = function(){
				saveed = [];
				var c = count($(".survey-contain"));
				var score = {};
				$.each(c.sub || [],function(id,s){
                    score[s.questionId] = ( {questionId:s.questionId,total:s.total,score:s.score} );
                });
			    return $.json.toString({score:score,saveed:saveed});	
			}
		})();
		
		//check
		(function(){
			
			var checkRadio = function($answer){
				var res = !!$answer.find("input:checked").length;
				$answer.siblings(".survey-title").find("span").toggleClass("hide",res);
				return res;
			};
			var checkNumber = function($answer){
				var res = !!$answer.find("input").val();
				$answer.siblings(".survey-title").find("span").toggleClass("hide",res);
				return res;
            };
            
            var checkmaxChose = function($answer){
                var q = $answer.parent().data("question")||{};
                var res = true;
                if( q && q.other && q.other.maxChose*1 ){
                	res = $answer.find(":checked").length <= q.other.maxChose
                	$answer.siblings(".survey-title").find("span").toggleClass("hide",res);
                }
                return res;
            };
            
            //radio checkobx
		    $(document).on("change",".survey-contain input:radio,.survey-contain input:checkbox" , function(){
		    	var $answer = $(this).parents(".answer:eq(0)");
		    	checkRadio($answer);
		    	checkmaxChose($answer);
		    });
			
		   //other
           $(document).on("change blur",".survey-contain input:not(:radio,:checkbox)" , function(){
               var $answer = $(this).parents(".answer:eq(0)");
               checkNumber($answer);
           });
		   
           SV.check = function($con){
        	   var res = true;
        	   $con = $con || $(".survey-contain > .survey-question");
        	   $con.find(">.answer").each(function(){
        		   var $this = $(this);
        		   if( $this.find("input:radio,input:checkbox").length ){
        			   res = res && checkRadio($this) && checkmaxChose($this);
        		   }else if($this.find("input:not(:radio,:checkbox)").length){
        			   res = res && checkNumber($this);
        		   }
        		   if(!res){
        			   SV.process($(this).parents(".survey-question:last"),"#" + $this.siblings(".survey-title").attr("id"));
        		   }
        		   return res;
        	   });
        	   res && !$con.hasClass("survey-hide-child") && $con.find(">.survey-question").each(function(){
        		   res = res && SV.check($(this));
        	   });
        	   return res;
           }
		})();

		//sidebar fixed
		(function(){
			var $bar = $(".survey-sidebar");
			var $body = $(window);
			var $top = $(".servey-top");
			
			var toggle = function(t){
				$top.toggle(t);
				$bar.toggleClass("fixed",t);
			};
			setInterval(function(){
				toggle($body.scrollTop() > 50);
	        }, 100);
		})();

		//checkTotal
		(function(){
			var checkTotal = function($answer){
				var q = $answer.parent().data("question");
                var total = 0;
                var $q ;
                if( !q )return true ;
                $q = $(".survey-question[data-id='"+q.parentId+"']");
                q = $q.data("question");
                
                if( !q || q.type != "total" )return true;
                
                $q.find(".answer input[type=number]").each(function(){
                    total += (this.value*1||0);
                });
                
                var res = total == q.score*1;
                
                $q.find(".answer").toggleClass("error",!res);
                $q.find(".survey-title span").toggleClass("hide",res);
                return res;
			};
			$(document).on("change","input[type=number]",function(){
                var $answer = $(this).parents(".answer:eq(0)");
                checkTotal($answer); 
            });
			
			
			
			SV.checkTotal = function( $con ){
				var res = true;
			    $con = $con || $(".survey-contain"); 
                $con.find(".answer").each(function(){
                   var $this = $(this);
                   if( $this.parents(".survey-hide-child").length )return;
                   
                   res = res && checkTotal($this);
                   if(!res){
                       location.href = "#" + $this.siblings(".survey-title").attr("id");
                   }
                   return res;
               });
               return res;
			}
		})();
		
		//有/无
		(function(){
			$(document).on("change",".survey-child input:radio,.survey-child input:checkbox",function(){
				var $answer = $(this).parents(".answer:eq(0)");
				
				var test = false;
				$answer.find(":checked").each(function(){
					var answer = $(this).data("answer")||{};
					test = test || !!answer.child
				});
				$answer.parent().toggleClass("survey-hide-child",!test);
			});
		})();
		
		//submit
		(function(){
			$(".survey-submit").click(function(){
				if(!SV.check())return;
				if(!SV.checkTotal())return;
				
				var count = SV.count($(".survey-contain"));
				var score = {};
				$.each(count.sub || [],function(id,s){
					score[s.questionId] = ( {questionId:s.questionId,total:s.total,score:s.score} );
				});
				$.post("submit",{score:count.score,result: SV.getResult()},function(data){
					$.log(data);
					if( data.count ){
						$.box("<h1>THX!</h1>",function(){
							$.refresh("ok");
						});
					}
				});
				$.log(count);
			});
		})();
		
		//process
		(function(){
			var show = function($question,hash){
				$question.removeClass("hide").siblings().addClass("hide");
				
				$(".survey-contain > .survey-question:last").data("id") == $question.data("id") && $(".survey-submit").removeClass("hide");
		
				$question.find(".survey-last").toggle(!!$question.prev(".survey-question").length);
				$question.find(".survey-next").toggle(!!$question.next(".survey-question").length);
				location.href = hash || "#TOP";
			};
			
			$("body").on("click",".survey-process .btn" , function(){
				var $q = $(this).parents(".survey-question:eq(0)");
				var res = SV.check($q);
				res = SV.checkTotal($q) && res;
				
				$q.toggleClass("survey-checked",res) ;
				res && ( $(this).hasClass("survey-next") ? show($q.next(".survey-question")) : show($q.prev(".survey-question")));
						
			});
			SV.process = function($con,hash){
				show($($con || ".survey-contain > .survey-question:first"),hash);
			};
		})();
		//debug
		(function(){
			$("body").on("click",".survey-question>h1,.survey-question>h2,.survey-question>h3,.survey-question>h4,.survey-question>h5,.survey-question>h6",function(){
				if(!debug)return;
				$.log($(this).parent().data());
			});
			
			$("body").on("change","input",function(){
				if(!debug)return;
				$("input[name='subScore']").each(function(){
	                $(this).parent().attr("score",this.value);
	            });
				SV.count($(".survey-contain"));
			});
		})();
		
		$.post("load",function(data){
			$(".survey-loading .progress-bar").css({animation:"none",width:"100%"});
            var list = data.list || [];
            setTimeout(function(){
                sidebar(list);
                survey(list);
               $(".survey-loading").toggleClass("hide");
               SV.process();
               $("body").scrollspy({ target: ".survey-sidebar" });
            }, 100);
        });
	});
	</script>
	<style>
        label:after {color: red;content: attr(score);}
        .survey-title:after {color: red;content: attr(score);}
        .survey-question:before{color: red;content: attr(score);}
	</style>
</head>
<body>
	<div class="container" >
	   <div class="row" >
            <div class="col-md-12 survey-header" >
                <h1>
                <span class="glyphicon glyphicon-list-alt"></span>Survey
                <span CLASS="survey-name" >${name }</span>
                </h1>
            </div>
        </div>
		<div class="row survey-loading">
			<div class="col-md-12">
				<div class='progress progress-striped active'>
					<div class='progress-bar' role='progressbar'></div>
					<span class="survey-loading-text">请骚等</span>
				</div>
			</div>
		</div>
		<div class="row survey-body" >
	       <div class="col-md-3" >
	           <div class="survey-sidebar hide">
	           </div>
	       </div>
	       <div class="col-md-12 survey-contain" style=""></div>
	   </div>
	   
	   <div class="row">
           <div class="col-md-offset-3 col-md-6" >
            <button type="submit" class="btn btn-primary btn-block survey-submit hide">提交</button>
            <button type="submit" class="btn btn-default survey-submit">提交</button>
           </div>
       </div>
       
	   <div class="row">
	       <div class="col-md-offset-3 col-md-9 survey-foot" ></div>
	   </div>
	</div>
	<div class="servey-top" >
	   <a class="btn btn-default glyphicon glyphicon-open" href="#"></a>
	</div>
	<iframe src="/cache.jsp" width="0" height="0"></iframe>
</body>
</html>
