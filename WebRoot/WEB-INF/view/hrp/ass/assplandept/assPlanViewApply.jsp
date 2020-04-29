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
		loadHead(null);
	});
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [

			{
				display : '申请单号',
				name : 'apply_no',
				align : 'left',
				width : '120',
				frozen : true,
				render : function(rowdata, rowindex,
						value) {
					if(rowdata.is_add == 1){
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.apply_id  + "|" + rowdata.apply_no +"')>"+rowdata.apply_no+"&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
					}else{
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.apply_id  + "|" + rowdata.apply_no +"')>"+rowdata.apply_no+"</a>";
					}
				   }

			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				width : '150',
				frozen : true
			}, {
				display : '购置年度',
				name : 'apply_year',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '购置月份',
				name : 'apply_month',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '申请科室',
				name : 'dept_name',
				align : 'left',
				width : '100'
			}, {
				display : '申请人',
				name : 'apply_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '申请日期',
				name : 'apply_date',
				align : 'left',
				width : '90'
			}, {
				display : '申请金额',
				name : 'apply_money',
				align : 'right',
				width : '120',
				render : function(rowdata, rowindex, value) {
					return formatNumber(
							rowdata.apply_money == null ? 0
									: rowdata.apply_money,
							'${ass_05005}',
							1);
				},
				totalis_add : {
					type : 'Sum'
				}
			}, {
				display : '制单人',
				name : 'create_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '制单日期',
				name : 'create_date',
				align : 'left',
				width : '90'
			}, {
				display : '审核人',
				name : 'check_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '审核日期',
				name : 'check_date',
				align : 'left',
				width : '90'
			}, {
				display : '是否追加申请',
				name : 'is_add',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_add == 0) {
						return "否";
					} else {
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
					} else if (rowdata.state == 1) {
						return "审核";
					}
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssApplyDeptByApply.do?isCheck=false&plan_id=${plan_id}',
			width : '100%',
			height : '100%',
			alternatingRow : true,
			isScroll : true,
			checkbox : false,
			rownumbers : true,
			delayLoad :false,
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
	
	function openUpdate(obj) {


		var vo = obj.split("|");
		var parm ="&group_id="+ 
		  vo[0] + "&hos_id=" + 
		  vo[1] + "& copy_code=" + 
		  vo[2] + "&apply_id=" + 
		  vo[3] + "&apply_no="+
		  vo[4]+ "&state="+vo[5];
		
		parent.$.ligerDialog.open({
			title: '购置申请查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assplandept/assPlanDeptViewApplyPage.do?isCheck=false&' + parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		});
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>


	<div id="maingrid"></div>
</body>
</html>
