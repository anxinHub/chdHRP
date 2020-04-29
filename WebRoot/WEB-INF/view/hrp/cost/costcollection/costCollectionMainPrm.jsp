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
			name : 'item_grade',
			value : liger.get("item_grade").getValue()
		});
		grid.options.parms.push({
			name : 'dept_code',
			value : liger.get("dept_code").getValue().split(".")[2]
		});
		loadHead();
		grid.loadData(grid.where);
	}

	function loadHead() {
		var columns = [{
			display : '科室编码',
			name : 'DEPT_CODE',
			width : 120,
			align : 'left',
			frozen : true
		},{
			display : '科室名称',
			name : 'DEPT_NAME',
			width : 120,
			align : 'left',
			frozen : true
		}];
		var t_columns="";
		var para = {item_grade:liger.get("item_grade").getValue()}
    	ajaxJsonObjectByUrl("queryCostCollectionPrmHead.do?isCheck=false", para, function (responseData){
    		if(responseData.Rows.length > 0){
    			$.each(responseData.Rows, function(v_index, v_data){ 
    				t_columns="";
    				t_columns="{"
        				      +"display: '"+v_data.COST_ITEM_NAME+"',"
        				      +"name:'"+ "C_"+v_data.COST_ITEM_CODE+"',"
        				      +"align: 'left',"
        				      +"minWidth: 160,"
        				      +"render:function(rowdata){return formatNumber(rowdata.C_" + v_data.COST_ITEM_CODE + ",2,1)}"
    				          +"}"; 
    				 columns.push(eval("("+t_columns+")")); 
    			}); 
    			
			}
    	}, false);
		columns.push(eval("({ display: '合计', name: 'TOTAL', align: 'left',minWidth:150,render:function(rowdata){return formatNumber(rowdata.TOTAL,2,1)}})"));
		grid = $("#maingrid").ligerGrid({
							columns : columns,
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryCostCollectionPrm.do',
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
		autocomplete("#dept_code", "../queryDeptDictCode.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#item_grade",
				"../queryItemDictNoItemGrade.do?isCheck=false", "id", "text",
				true, true);
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
			title : "科室成本统计",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.cost.service.CostCollectionService",
			method_name : "queryCostCollectionPrmPrint",
			bean_name : "costCollectionService",
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目级次：</td>
			<td align="left" class="l-table-edit-td"><input
				name="item_grade" type="text" id="item_grade" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
