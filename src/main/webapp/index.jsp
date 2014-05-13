<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <jsp:include page="/public/head.jsp"></jsp:include>
  </head>
  
  <body>
    <div class="container">
        <div class="row" >
            <div class="col-md-offset-5 col-md-1" ><h1>SHIT</h1></div>
        </div>
        <form action="/index" ></form>
    </div>
    <script>
    setTimeout(function(){$("form").submit()}, 1000);
    </script>
  </body>
</html>
