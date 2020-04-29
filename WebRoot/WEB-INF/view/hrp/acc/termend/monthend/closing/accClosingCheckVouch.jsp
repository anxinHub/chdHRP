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
				align : 'left',
				width : '10%'
			}, {
				display : '凭证类别',
				name : 'vouch_type_name',
				align : 'left',
				width : '12%'
			}, {
				display : '凭证号',
				name : 'vouch_no',
				align : 'left',
				width : '12%',
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:openSuperVouch("'+ rowdata.vouch_id + '")>'+rowdata.vouch_type_short+'-'+rowdata.vouch_no+'</a>';
				}
			}, {
				display : '借方合计金额',
				name : 'debit',
				align : 'right',
				width : '21%',
				render : function(rowdata) {
					return formatNumber(rowdata.debit, 2, 1);
				}
			}, {
				display : '贷方合计金额',
				name : 'credit',
				align : 'right',
				width : '21%',
				render : function(rowdata) {
					return formatNumber(rowdata.credit, 2, 1);
				}
			}, {
				display : '制单人',
				name : 'create_name',
				align : 'left',
				width : '12%'
			}, {
				display : '说明',
				name : 'state_name',
				align : 'left',
				width : '12%'
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			pageSize : 14,
			url : 'queryAccClosingCheckVouch.do?isCheck=false',
			width : '100%',
			height : '90%',
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
		$("#but_next").ligerButton({click: next, width:120});
		hotkeys('N',next);
		$("#but_before").ligerButton({click: before, width:120});
		hotkeys('S',before);
		$("#but_print").ligerButton({click: print, width:120});
		hotkeys('P',print);
	}
	
	function next(){
		if(gridManager.rows.length > 0){
			alert("含未记账凭证，不能结账！"); 
			return;
		}
		parent.changeIFrame("accClosingCheckLederPage.do?isCheck=false", "checkLeder", "checkVouch");
	}
	
	function before(){
		parent.changeIFrame("accClosingStartPage.do?isCheck=false", "start", "checkVouch");
	}
	
	function print(){
		alert("print");
	}
	
	function openSuperVouch(vouch_id){
		parent.parent.openFullDialog('hrp/acc/accvouch/superVouch/mainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 90%; height: 100%;">
		<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
			<h2 align="center">未记账凭证列表</h2>
			<div id="maingrid"></div>
		</div>
		<div align="center" style="padding-top:10px;">
			<hr/>
			<input type="button" id="but_before" value="上一步（S）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_next" value="下一步（N）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_print" value="打印（P）"/>
		</div>
	</div>
</body>
</html>