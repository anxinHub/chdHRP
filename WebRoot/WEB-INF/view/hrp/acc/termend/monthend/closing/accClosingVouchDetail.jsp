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
		query();
	});
	
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '期间',
				name : 'year_month',
				align : 'left',
				width : '10%'
			}, {
				display : '凭证类别',
				name : 'vouch_type_name',
				align : 'left',
				width : '15%'
			}, {
				display : '凭证号',
				name : 'vouch_no',
				align : 'left',
				width : '15%',
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:openSuperVouch("'+ rowdata.vouch_id + '")>'+rowdata.vouch_type_short+'-'+rowdata.vouch_no+'</a>';
				}
			}, {
				display : '制单人',
				name : 'create_name',
				align : 'left',
				width : '10%',
				render : function(rowdata) {
					return formatNumber(rowdata.debit, 2, 1);
				}
			}, {
				display : '凭证账',
				columns : [{
					display : '借方',
					name : 'debit',
					align : 'right',
					width : '25%',
					render : function(rowdata) {
						return formatNumber(rowdata.debit, 2, 1);
					}
				}, {
					display : '贷方',
					name : 'credit',
					align : 'right',
					width : '25%',
					render : function(rowdata) {
						return formatNumber(rowdata.credit, 2, 1);
					}
				}],
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			pageSize : 14,
			url : 'queryAccClosingCheckVouch.do', 
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
			name : 'group_id',
			value : '${group_id}'
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : '${hos_id}'
		});
		grid.options.parms.push({
			name : 'copy_code',
			value : '${copy_code}'
		});
		grid.options.parms.push({
			name : 'subj_code',
			value : '${subj_code}'
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : '${acc_year}'
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : '${acc_month}'
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	function openSuperVouch(vouch_id){
		parent.parent.openFullDialog('../../../hrp/acc/accvouch/superVouch/mainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
		<h2 align="center">凭证明细账列表</h2>
		<div id="maingrid"></div>
	</div>
</body>
</html>