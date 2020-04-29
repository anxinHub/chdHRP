<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 150});
		/* ajaxJsonObjectByUrl("getAccClosingYearMonth.do?isCheck=false","",function (responseData){
			$("#year_month").html(responseData.year_month);
		}, false); */
		var year_month = "${wage_year_month}";
		$("#year_month").html(year_month.substring(0, 4)+'.'+year_month.substring(4, 6));
	});
	
	function changeIFrame(url, now, before){
		//注图片的名称必须跟DIV的ID对应
		$("#"+before).css("background","url(<%=path%>/lib/hrp/acc/images/"+before+"1.png) no-repeat;");
		$("#"+now).css("background","url(<%=path%>/lib/hrp/acc/images/"+now+"2.png) no-repeat;");
		$("#leder").attr("src", url);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="layout1" style="width:100%;margin:0; padding:0px;">
		<div position="left" align="center" title="">
			<div style="margin-top: 20px;">
				当前期间：<span id="year_month"></span>
			</div>
			<!-- div style="background:url(<%=path%>/lib/hrp/acc/images/accountFlow.png);height:100%;width:100px;background-repeat:no-repeat;"></div-->
			<!-- 开始结账图片 -->
			<div id="start" style="margin-top: 20px;width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/start2.png) no-repeat;"></div>
			
			<!-- 导向剪头图片 -->
			<div style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/arrow.png) no-repeat;"></div>
			
			<!-- 核对凭证图片 -->
			<div id="checkVouch" style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/checkVouch1.png) no-repeat;"></div>
			
			<!-- 导向剪头图片 -->
			<div style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/arrow.png) no-repeat;"></div>
			
			<!-- 核对账簿图片 -->
			<div id="checkLeder" style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/checkLeder1.png) no-repeat;"></div>

			<!-- 导向剪头图片 -->
			<div style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/arrow.png) no-repeat;"></div>
			
			<!-- 完成结账图片 -->
			<div id="finish" style="width: 100px;height: 62px;background: url(<%=path%>/lib/hrp/acc/images/finish1.png) no-repeat;"></div>
		</div>
		<div position="center" title=" ">
			<iframe id="leder" height="98%" width="100%" frameborder="0" scrolling="auto" src="accClosingStartPage.do?isCheck=false"></iframe>
		</div>
	</div>
</body>
</html>
