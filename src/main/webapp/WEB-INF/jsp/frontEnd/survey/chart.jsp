<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!doctype html>
<html lang="zh-CN">
<head>
<title>survey-ok</title>
<c:import url="/public/head.jsp"></c:import>
<script src="//code.highcharts.com/highcharts.js" ></script>
<script src="//code.highcharts.com/highcharts-3d.js"></script>
<style>
</style>
<script>
	$(function() {
		var SV = {};
	    $.post("loadSurvey",function(data){window.data=data;
	    	var score = [];
	    	var total = []
	    	var pie = {score:0,total:0};
	    	var scoreData = (data.survey.result = $.json.toObject(data.survey.result)).score||{};
	    	$.each(data.list,function(i,q){
	    		var s = scoreData[q.questionId]||{};
	    		if(!s.total)return;
	    		score.push([q.title,s.score]);
	    		total.push([q.title,s.total]);
	    		
	    		pie.score += s.score;
	    		pie.total += s.total;
	    	});
	    	 var chart = $(".survey-chart1").highcharts({
	    	        chart: {
	    	            type: "column",
	    	            renderTo: ".survey-chart1",
	    	            options3d: {
	    	                enabled: true,
	    	                alpha: 0,
	    	                beta: 0,
	    	                depth: 50,
	    	                viewDistance: 25
	    	            }
	    	        },
	    	        title: {
	    	            text: "survey"
	    	        },
	    	        xAxis: {
	                    type: "category",
	                    labels: {
	                        rotation: -45,
	                        align: "right",
	                        style: {
	                            fontSize: "13px",
	                            fontFamily: "Verdana, sans-serif"
	                        }
	                    }
	                },
	                yAxis: {
	                    min: 0,
	                    title: {
	                        text: "得分"
	                    }
	                },
	    	        series: [{
	    	        	name:"总分",
                        data:total,
                        color:"rgba(165,170,217,0.5)",
                        //pointPadding: 0.1,
                        //pointPlacement: 0
	    	            
	    	        },{
	    	        	name: "得分",
	                    data:score,
	    	        	pointPadding: 0.2,
                        pointPlacement: -0.3
	    	        }]
	    	    }).highcharts();
	    	
	    	$("div>svg>text:last").remove();
	    	SV.controller(function(x,y){
	    		chart.options.chart.options3d.alpha = y*45;
	    		chart.options.chart.options3d.beta = x*45;
	    		chart.redraw(false);
	    	});
	    	$("#R0").change(function(){
	    		chart.options.chart.options3d.alpha = this.value
	    		chart.redraw(false);
	    	});
	    	$("#R1").change(function(){
	    		chart.options.chart.options3d.beta = this.value;
	    		chart.redraw(false);
	    	});
	    	/*********************************************
	    	$("#container").highcharts({
	    	    chart: {
	    	        type: "pie",
	    	        options3d: {
	    	            enabled: true,
	    	            alpha: 45,
	    	            beta: 0
	    	        }
	    	    },
	    	    title: {
	    	        text: "总分"
	    	    },
	    	    tooltip: {
	    	        pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
	    	    }
	    	    series: [{
	    	        type: "pie",
	    	        name: "总分",
	    	        data: []
	    	    }]
	    	});
	    	*/
	    });
	    
	    $(".btn.survey-save").click(function(){
	    	$.post("save",function(data){
	    		if(data.count){
	    			$.box("保存好了");
	    			$(".btn.survey-save").remove();
	    		}else{
	    			$.box("已经保存过了");			
	    		}
	    	});
	    });
	    //chart-cintroller
	    (function(){
	    	var $controller = $(".survey-chart-controller");
	    	var $point = $controller.find("div");
	    	var w = $controller.width();
	    	var h = $controller.height();
	    	var offset ;
	    	var cbs = [];
	    	var callback = function(x,y){
	    		x = x*2/w;
	    		y = y*2/h;
	    		y*= -1;
	    		$.each(cbs,function(){
                    this.call(this,x,y);
                });
	    		
	    	};
	        $controller.on("mousemove mousedown",function(e){
	    		if(!e.which)return ;
	    		var x , y;
	    		offset = $controller.offset();
	    		x = e.pageX - offset.left;
	    		y =  e.pageY - offset.top;
	    		
	    		x = x < 0 ? 0 : x > w ? w : x ;
	    		y = y < 0 ? 0 : y > h ? h : y ;
	    		
	    		$point.css({left:x-0.5*$point.width(),top:y-0.5*$point.height()});
	    		
	    		callback(x-0.5*w,y-0.5*h);
	    		
	    	});
	        
	        $point.dblclick(function(){
                callback(0,0);
                $point.removeAttr("style");
            });
	        
	        SV.controller = function(cb){
	        	$.isFunction(cb) && cbs.push(cb);
	        };
	    })();
	});
</script>
</head>
<body>
	<div class="container survey-ok">
	   <div class="row">
            <div class="col-md-offset-3 col-md-3" >
                <a class="btn btn-primary btn-block survey-save"  >保存</a>
            </div>
            <div class="col-md-3" >
                <a class="btn btn-primary btn-block" href="${frontPath }/alliance/index" >完善行业信息</a>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-12 survey-chart1" >
            </div>
            <div class="col-md-3">
                <div class="survey-chart-controller"><div></div></div>
			</div>
            <div class="col-md-12 survey-chart2" >
            </div>
        </div>
	</div>
	<iframe src="/cache.jsp" width="0" height="0"></iframe>
</body>
</html>
