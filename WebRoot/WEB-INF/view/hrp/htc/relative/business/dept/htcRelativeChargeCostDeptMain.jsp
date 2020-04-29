<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		changeAcctYear();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'acc_year',value : $("#acc_year").val()});
		grid.options.parms.push({name : 'plan_code',value:liger.get("plan_code").getValue()});
		grid.options.parms.push({name : 'proj_dept_id',value:liger.get("proj_dept_id").getValue().split(".")[0]});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '方案编码',
				name : 'plan_code',
				align : 'left'
			}, {
				display : '核算科室编码',
				name : 'proj_dept_code',
				align : 'left'
			}
			, {
				display : '核算科室名称',
				name : 'proj_dept_name',
				align : 'left'
			},{
				display : '收费项目编码',
				name : 'charge_item_code',
				align : 'left'
			},{
				display : '收费项目名称',
				name : 'charge_item_name',
				align : 'left'
			}, {
				display : '直接成本',
				name : 'dir_cost',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.dir_cost, 2, 1);
				}
			}, {
				display : '公共分摊成本',
				name : 'pub_cost',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.pub_cost, 2, 1);
				}
			}, {
				display : '管理分摊成本',
				name : 'man_cost',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.man_cost, 2, 1);
				}
			}, {
				display : '医辅分摊管理',
				name : 'mea_cost',
				align : 'right',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.mea_cost, 2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcRelativeChargeCostDept.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '计算',
					id : 'collect',
					click : collect,
					icon : 'plus'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function collect() {
		var acc_year = $("#acc_year").val();
		var plan_code = liger.get("plan_code").getValue();
		var proj_dept_id = liger.get("proj_dept_id").getValue().split(".")[0];
		
		if (acc_year == '') {
			$.ligerDialog.error('年度不能为空!')
			return false;
		}
		if (plan_code == '') {
			$.ligerDialog.error('方案不能为空!')
			return false;
		}

		var formPara = {
			acc_year : acc_year,
			plan_code : plan_code,
			proj_dept_id : proj_dept_id
		};
		ajaxJsonObjectByUrl("collectHtcRelativeChargeCostDept.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});

	}

	
	function loadDict() {
		//字典下拉框
		
		autocomplete("#proj_dept_id","../../../info/base/queryHtcProjDeptDict.do?isCheck=false","id","text",true,true);
		
		autodate("#acc_year", "yyyy");

		$("#acc_year").ligerTextBox({width:100});


	}

	function changeAcctYear(){
		$("#plan_code").val("");
 		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true,{acc_year:$("#acc_year").val()});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text"
				onchange="changeAcctYear();" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_dept_id" type="text" id="proj_dept_id" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
