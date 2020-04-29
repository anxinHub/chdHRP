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
	var grid;
	
	var gridManager = null;
	
	var userUpdateStr;
	
	$(function() {
		loadButton();
		$("#yearMonth").html($("#year_month", parent.document).text());
		var paras={
			acc_year : $("#year_month", parent.document).text().split(".")[0],
			acc_month : $("#year_month", parent.document).text().split(".")[1]
		};
		ajaxJsonObjectByUrl("queryAccClosingCountVouch.do?isCheck=false",paras,function (responseData){
			$("#countVouch").html(responseData.countVouch);
			$("#countVouchDetail").html(responseData.countVouchDetail);
		});
	});
	
	//格式化按钮
	function loadButton(){
		$("#but_finish").ligerButton({click: finish, width:120});
		hotkeys('F',finish);
	}

	function finish(){
		parent.changeIFrame("accClosingStartPage.do?isCheck=false", "start", "finish");
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 90%; height: 100%;">
		<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
			<table align="left" width="100%" >
				<tr>
					<td align="right">完成</td>
					<td>结账，当前期间：<span id="yearMonth"></span></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="right">1、</td>
					<td>本月共<span id="countVouch"></span>张凭证，共<span id="countVouchDetail"></span>条分录</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="right">2、</td>
					<td>本月账核对情况：</td>
				</tr>
				<tr>
					<td></td>
					<td>总账与辅助账平衡</td>
				</tr>
				<tr>
					<td></td>
					<td>总账与明细账平衡</td>
				</tr>
				<tr>
					<td></td>
					<td>辅助账与明细账平衡</td>
				</tr>
			</table>
		</div>
		<div align="center" style="padding-top:10px;">
			<hr/>
			<input type="button" id="but_finish" value="结束（F）"/>
		</div>
	</div>
</body>
</html>