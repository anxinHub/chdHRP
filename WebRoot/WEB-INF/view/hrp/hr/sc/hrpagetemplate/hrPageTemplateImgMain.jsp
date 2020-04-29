<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<script src="<%=path%>/lib/jquery/jquery.gridnav.min.js"></script>
<style type="text/css">
.template_div{
	width:100%;
    height:100%;
}

.template_div img{
	 width: 800px;
     height: 500px;
	 display:block;
	 border:none;
	 opacity:0.7;
	 -moz-box-shadow:2px 2px 4px #8e8e8e;
	 -webkit-box-shadow:2px 2px 4px #8e8e8e;
	 box-shadow:2px 2px 4px #8e8e8e;
}
</style>
<script type="text/javascript">
</script>
</head>
<body>
  <div class="template_div">
    <img  src="<%=path%>/${template_path}"/>
  </div>
</body>
</html>
