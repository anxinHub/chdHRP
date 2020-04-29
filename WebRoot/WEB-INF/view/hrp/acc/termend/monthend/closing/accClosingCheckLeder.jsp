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
	var grid,grid1;
	
	var gridManager = gridManager1 = null;
	
	var userUpdateStr;
	
	$(function() {
		loadHead(null); //加载数据
		loadButton();
		query();
	});
	
	//模板Grid
	function loadHead() {
		grid = $("#checkgrid").ligerGrid({
			columns : [ {
				display : '会计期间',
				name : 'year_month',
				align : 'left',
				width : '8%'
			}, {
				display : '科目编码',
				name : 'subj_code',
				align : 'left',
				width : '12%'
			}, {
				display : '科目名称',
				name : 'subj_name',
				align : 'left',
				width : '20%'
			}, {
				display : '总账',
				columns : [{
					display : '借方',
					name : 'debit',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showCheck(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.debit, 2, 1)+"<a>";
					}
				}, {
					display : '贷方',
					name : 'credit',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showCheck(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.credit, 2, 1)+"<a>";
					}
				}]
			}, {
				display : '辅助账',
				columns : [{
					display : '借方',
					name : 'debit_w',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showCheck(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.debit_w, 2, 1)+"<a>";
					}
				}, {
					display : '贷方',
					name : 'credit_w',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showCheck(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.credit_w, 2, 1)+"<a>";
					}
				}]
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			pageSize : 5,
			url : 'queryAccClosingCheckLederToCheck.do?isCheck=false',
			width : '100%',
			height : '43%',
			checkbox : false,
			rownumbers : false,
			delayLoad: true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});
		gridManager = $("#checkgrid").ligerGetGridManager();
		
		grid1 = $("#vouchgrid").ligerGrid({
			columns : [ {
				display : '会计期间',
				name : 'year_month',
				align : 'left',
				width : '8%'
			}, {
				display : '科目编码',
				name : 'subj_code',
				align : 'left',
				width : '12%'
			}, {
				display : '科目名称',
				name : 'subj_name',
				align : 'left',
				width : '20%'
			}, {
				display : '总账',
				columns : [{
					display : '借方',
					name : 'debit',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showVouch(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.debit, 2, 1)+"<a>";
					}
				}, {
					display : '贷方',
					name : 'credit',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showVouch(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.credit, 2, 1)+"<a>";
					}
				}]
			}, {
				display : '凭证',
				columns : [{
					display : '借方',
					name : 'debit_w',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showVouch(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.debit_w, 2, 1)+"<a>";
					}
				}, {
					display : '贷方',
					name : 'credit_w',
					align : 'right',
					width : '15%',
					render : function(rowdata) {
						return '<a href=javascript:showVouch(\''+ JSON.stringify(rowdata) +'\')>'+formatNumber(rowdata.credit_w, 2, 1)+"<a>";
					}
				}]
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			pageSize : 5,
			url : 'queryAccClosingCheckLederToVouch.do?isCheck=false',
			width : '100%',
			height : '43%',
			checkbox : false,
			rownumbers : false,
			delayLoad: true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});
		gridManager1 = $("#vouchgrid").ligerGetGridManager();
	}
	
	function query(){
		//总账与辅助账查询
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
		
		//总账与凭证查询
		grid1.options.parms = [];
		grid1.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid1.options.parms.push({
			name : 'acc_year',
			value : $("#year_month", parent.document).text().split(".")[0]
		});
		grid1.options.parms.push({
			name : 'acc_month',
			value : $("#year_month", parent.document).text().split(".")[1]
		});
		//加载查询条件
		grid1.loadData(grid.where);
	}
	
	//格式化按钮
	function loadButton(){
		$("#but_finish").ligerButton({click: finish, width:120});
		hotkeys('N',finish);
		$("#but_before").ligerButton({click: before, width:120});
		hotkeys('S',before);
		$("#but_print").ligerButton({click: print, width:120});
		hotkeys('P',print);
	}
	
	function finish(){
		if(gridManager.rows.length > 0){
			alert("总账与辅助账或总账与凭证金额不符，不能结账！"); 
			return;
		}
		var paras={
			acc_year : $("#year_month", parent.document).text().split(".")[0],
			acc_month : $("#year_month", parent.document).text().split(".")[1]
		};
		ajaxJsonObjectByUrl("confirmAccClosing.do?isCheck=false",paras,function (responseData){
			if(responseData.state == "true"){ 
				parent.changeIFrame("accClosingFinishPage.do?isCheck=false", "finish", "checkLeder");
			}
		}); 
		parent.changeIFrame("accClosingFinishPage.do?isCheck=false", "finish", "checkLeder");
	} 
	
	function before(){
		parent.changeIFrame("accClosingCheckVouchPage.do?isCheck=false", "checkVouch", "checkLeder");
	}
	
	function print(){
		alert("print");
	}
	
	function showCheck(rowdata){
		rowdata = JSON.parse(rowdata);
		var paras = "&group_id="+rowdata.group_id+"&hos_id="+rowdata.hos_id+"&copy_code="+rowdata.copy_code+
			"&subj_code="+rowdata.subj_code+"&acc_year="+rowdata.year_month.split(".")[0]+"&acc_month="+rowdata.year_month.split(".")[1];

		$.ligerDialog.open({ 
			title: '核算账明细', 
			width: 700, 
			height: 400, 
			url: 'accClosingCheckDetailPage.do?isCheck=false&'+paras, 
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
		}); 
	}

	function showVouch(rowdata){
		rowdata = JSON.parse(rowdata);
		var paras = "&group_id="+rowdata.group_id+"&hos_id="+rowdata.hos_id+"&copy_code="+rowdata.copy_code+
		"&subj_code="+rowdata.subj_code+"&acc_year="+rowdata.year_month.split(".")[0]+"&acc_month="+rowdata.year_month.split(".")[1];

		$.ligerDialog.open({ 
			title: '凭证明细', 
			width: 700, 
			height: 400, 
			url: 'accClosingVouchDetailPage.do?isCheck=false'+paras, 
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
		}); 
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 90%; height: 100%;">
		<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
			<h3 align="left">1、核对总账与辅助账：</h3>
			<div id="checkgrid"></div>
			<h3 align="left">2、核对总账与凭证：</h3> 
			<div id="vouchgrid"></div>
		</div>
		<div align="center" style="padding-top:10px;">
			<hr/>
			<input type="button" id="but_before" value="上一步（S）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_finish" value="完成结账（N）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_print" value="打印（P）"/>
		</div>
		<div id="resultPrint" style="display:none">
		   	<table width="100%">
				   	<thead>
						<tr>
					<th width="200">单位信息</th>	
	                <th width="200">会计年度</th>	
	                <th width="200">目标编码</th>	
	                <th width="200">目标名称</th>	
	                <th width="200">目标描述</th>	
					   	</tr>
				   	</thead>
		   	</table>
	   	</div>
	</div>
</body>
</html>