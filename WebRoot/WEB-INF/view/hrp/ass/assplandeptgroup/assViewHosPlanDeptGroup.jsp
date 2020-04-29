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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		//加载数据
		loadHead(null);
	});

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '医院名称',
				name : 'hos_name',
				align : 'left',
				width : '90',
				frozen : true
			},{
				display : '计划号',
				name : 'plan_no',
				align : 'left',
				width : '110',
				frozen : true,
				render : function(rowdata, rowindex, value) {
					if(rowdata.note == "合计"){
						return '';
					}
					
					if(rowdata.is_add == 1){
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.plan_id  +"')>"+rowdata.plan_no+"&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
					}else{
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.plan_id  +"')>"+rowdata.plan_no+"</a>";
					}
				}
			}, {
				display : '摘要',
				name : 'note',
				align : 'left',
				width : '150',
				frozen : true
			},
			{
				display : '购置年度',
				name : 'plan_year',
				align : 'left',
				width : '60',
				frozen : true
			},
			{
				display : '编制科室名称',
				name : 'dept_no',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					return rowdata.dept_name;
				}
			},
			{
				display : '计划金额',
				name : 'plan_money',
				align : 'right',
				width : '120',
				render : function(rowdata, rowindex, value) {
					return formatNumber((
							rowdata.plan_money == null ? 0
									: rowdata.plan_money,
							'${ass_05005}',
							1);
				}
			}, {
				display : '制单人',
				name : 'create_emp',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					return rowdata.create_emp_name;
				}
			}, {
				display : '制单日期',
				name : 'create_date',
				align : 'left',
				width : '90'
			}, {
				display : '审核人',
				name : 'check_emp',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					return rowdata.check_emp_name;
				}
			}, {
				display : '审核日期',
				name : 'check_date',
				align : 'left',
				width : '90'
			}, {
				display : '是否追加计划',
				name : 'is_add',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_add == 0) {
						return "否";
					}else if(rowdata.is_add == 1){
						return "是";
					}
				}
			}, {
				display : '状态',
				name : 'state',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.state == 0) {
						return "新建";
					}else if(rowdata.state == 1){
						return "审核";
					}
				}
			}, {
				display : '采购方式',
				name : 'buy_code',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.buy_code == 0) {
						return "自主采购";
					}else if(rowdata.buy_code == 1){
						return "集中采购";
					}
					
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : "queryAssPlanDeptGroupHosView.do?isCheck=false&group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&plan_id=${plan_id}",
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : false,
			selectRowButtonOnly : true,//heightDiff: -10,
			checkBoxDisplay : isCheckDisplay,
			toolbar : {
				items : [ {
					text : '关闭',
					id : 'close',
					click : this_close,
					icon : 'candle'
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	function this_close(){
		frameElement.dialog.close();
	}

	function openUpdate(obj) {

		var vo = obj.split("|");

		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3];

		parent.$.ligerDialog
				.open({
					title : '购置计划查看',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assPlanDeptGroupViewPage.do?isCheck=false&'
							+ parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
