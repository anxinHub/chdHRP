<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;

	$(function() {
		loadDict();

		loadHead(null); //加载数据
	});
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [  {
				display : '现金项目编码',
				name : 'cash_item_code',
				align : 'left'
			}, {
				display : '现金项目名称',
				name : 'cash_item_name',
				align : 'left'
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : '../../acccashitem/queryAccCashItem.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function saveTemplate(){
		var data = gridManager.getCheckedRows();
		var ParamVo = [];
		$(data).each(function() {
			ParamVo.push(
			${subj_code_other}+"@"+this.cash_item_code);
		});
		ajaxJsonObjectByUrl("updateAccVouchCashFlow.do", {
			ParamVo : ParamVo
		}, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	}
	
	function loadDict() {
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div id="maingrid"></div>

</body>
</html>
