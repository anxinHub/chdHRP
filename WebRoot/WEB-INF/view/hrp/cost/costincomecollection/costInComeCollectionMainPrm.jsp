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
	$(function() {
		loadDict();
		loadHead(null); //加载数据
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'year_month',
			value : $("#year_month").val()
		});
		grid.options.parms.push({
			name : 'e_year_month',
			value : $("#e_year_month").val()
		});
		grid.options.parms.push({
			name : 'appl_dept',
			value : liger.get("appl_dept").getValue().split(".")[2]
		});
		grid.options.parms.push({
			name : 'exec_dept',
			value : liger.get("exec_dept").getValue().split(".")[2]
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {

		var columns = [ {
			display : '统计年月',
			name : '',
			width : 60,
			align : 'left',
			render:function(rowdata){ return rowdata.ACC_YEAR + rowdata.ACC_MONTH},
			frozen : true
		}, {
			display : '开单科室编码',
			name : 'APPL_DEPT_CODE',
			width : 80,
			align : 'left',
			frozen : true
		}, {
			display : '开单科室名称',
			name : 'APPL_DEPT_NAME',
			width : 120,
			align : 'left',
			frozen : true
		}, {
			display : '执行科室编码',
			name : 'EXEC_DEPT_CODE',
			width : 80,
			align : 'left',
			frozen : true
		}, {
			display : '执行科室名称',
			name : 'EXEC_DEPT_NAME',
			width : 120,
			align : 'left',
			frozen : true
		}];
		var t_columns="";
    	ajaxJsonObjectByUrl("queryIncomeCollectionPrmHead.do?isCheck=false", "", function (responseData){
    		if(responseData.Rows.length > 0){
    			$.each(responseData.Rows, function(v_index, v_data){ 
    				t_columns="";
    				t_columns="{"
        				      +"display: '"+v_data.CHARGE_KIND_NAME+"',"
        				      +"name:'"+ "C_"+v_data.CHARGE_KIND_CODE+"',"
        				      +"align: 'left',"
        				      +"minWidth: 120,"
        				      +"render:function(rowdata){return formatNumber(rowdata.C_" + v_data.CHARGE_KIND_CODE + ",2,1)}"
    				          +"}"; 
    				columns.push(eval("("+t_columns+")"));
    			}); 
			}
    	}, false);
		grid = $("#maingrid").ligerGrid({
			columns : columns,
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryIncomeCollectionPrm.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
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
					text : '打印',
					id : 'print',
					click : print,
					icon : 'print'
				}, {
					line : true
				}, ]
			},
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {

		$("#year_month").ligerTextBox({
			width : 160
		});
		$("#e_year_month").ligerTextBox({
			width : 160
		});

		autodate("#year_month", "yyyyMM");
		autodate("#e_year_month", "yyyyMM");
		//字典下拉框
	
		//字典下拉框 
		autocomplete("#appl_dept", "../queryDeptDictCode.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#exec_dept", "../queryDeptDictCode.do?isCheck=false", "id",
				"text", true, true);
	}

	function print() {

		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var heads = {
			//"isAuto":true,//系统默认，页眉显示页码
			"rows" : [ {
				"cell" : 0,
				"value" : "统计日期：" + $("#year_month").val() + "至"
						+ $("#e_year_month").val(),
				"colSpan" : "5"
			} ]
		};
		var printPara = {
			title : "科室收入统计",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.cost.service.CostInComeCollectionService",
			method_name : "queryCostInComeCollectionPrmPrint",
			bean_name : "costInComeCollectionService",
			heads : JSON.stringify(heads)
		//表头需要打印的查询条件,可以为空

		};
		//执行方法的查询条件
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});

		officeGridPrint(printPara);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="year_month" type="text" id="year_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="left" class="l-table-edit-td"><input
				name="e_year_month" type="text" id="e_year_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开单科室：</td>
			<td align="left" class="l-table-edit-td"><input name="appl_dept"
				type="text" id="appl_dept" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">执行科室：</td>
			<td align="left" class="l-table-edit-td"><input name="exec_dept"
				type="text" id="exec_dept" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
