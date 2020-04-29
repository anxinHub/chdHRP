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
		loadHead(null); //加载数据
		loadButton();
		query();
	});
	
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '会计期间',
				name : 'year_month',
				align : 'center',
				width : '10%'
			}, {
				display : '财务账管理',
				columns : [{
					display : '结账人',
					name : 'acc_name',
					align : 'center',
					width : '10%'
				}, {
					display : '结账时间',
					name : 'acc_date',
					align : 'center',
					width : '20%'
				}]
			}, {
				display : '出纳账管理',
				columns : [{
					display : '结账人',
					name : 'cash_name',
					align : 'center',
					width : '10%' 
				}, {
					display : '结账时间',
					name : 'cash_date',
					align : 'center',
					width : '20%'
				}]
			}, {
				display : '工资管理',
				columns : [{
					display : '结账人',
					name : 'wage_name',
					align : 'center',
					width : '10%'
				}, {
					display : '结账时间',
					name : 'wage_date',
					align : 'center',
					width : '20%'
				}]
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryAccClosing.do',
			width : '100%',
			height : '80%',
			checkbox : false,
			rownumbers : false,
			delayLoad: true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#year_month", parent.document).text().split(".")[0]
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : $("#year_month", parent.document).text().split(".")[1]
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//格式化按钮
	function loadButton(){
		$("#but_start").ligerButton({click: start, width:120});
		//键盘事件
		hotkeys('J',start);		 
		$("#but_cancel").ligerButton({click: cancel, width:120});
		//键盘事件 
		hotkeys('N',cancel);		
	}
	
	function start(){
		//检查是否需要判断出纳和工资已结账  
		if("${sessionScope.acc_para_map['01024']}" == 1 || "${sessionScope.acc_para_map['01025']}" == 1 ){
			//判断出纳和工资已结账 
			var paras={
				acc_year : $("#year_month", parent.document).text().split(".")[0],
				acc_month : $("#year_month", parent.document).text().split(".")[1],
				check_wage : "${sessionScope.acc_para_map['01024']}",
				check_cash : "${sessionScope.acc_para_map['01025']}"
			};
			ajaxJsonObjectByUrl("queryAccClosingCheckStart.do?isCheck=false",paras,function(responseData){
				if(responseData.state=="true"){ 
					parent.changeIFrame("accClosingCheckVouchPage.do?isCheck=false", "checkVouch", "start");
			 	}
			}); 
		}else{
			//不需要判断出纳和工资已结账 
			parent.changeIFrame("accClosingCheckVouchPage.do?isCheck=false", "checkVouch", "start");
		}
	}
	
	function cancel(){
		 var paras={
			acc_year : $("#year_month", parent.document).text().split(".")[0],
			acc_month : $("#year_month", parent.document).text().split(".")[1]
		};
		ajaxJsonObjectByUrl("reconfirmAccClosing.do?isCheck=false",paras,function(responseData){
			if(responseData.state=="true"){ 
				query();
			}
		});
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 90%; height: 100%;">
		<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
			<div id="maingrid"></div>
			<table align="left" width="100%" >
				<tr>
					<td colspan="2">1、说明：</td>
				</tr>
				<tr>
					<td>（1）、有未记账凭证的月份不允许结账</td>
					<td>（2）、已经结账的月份不允许填制凭证</td>
				</tr>
				<tr>
					<td>（3）、每月对账正确后才能结账</td>
					<td>（4）、年底结账时先建立下一年的年度</td>
				</tr>
			</table>
		</div>
		<div align="center" style="padding-top:10px;">
			<hr/>
			<input type="button" id="but_start" value="开始结账（J）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_cancel" value="反结账（N）"/>
		</div>
	</div>
</body>
</html>
