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
		$(function () {
			loadDict();
			loadHead(null); //加载数据
			$("#year_month_begin").ligerTextBox({width: 160});
			$("#year_month_end").ligerTextBox({width: 160});
		});
		//查询
		function query() {
			grid.options.parms = [];
			grid.options.newPage = 1;
			//根据表字段进行添加查询条件
			grid.options.parms.push({
				name: 'year_month_begin',
				value: $("#year_month_begin").val()
			});
			grid.options.parms.push({
				name: 'year_month_end',
				value: $("#year_month_end").val()
			});
			grid.options.parms.push({
				name: 'source_attr_2',
				value: $("#source_attr_2").prop("checked") ? 1 : 0
			});
			grid.options.parms.push({
				name: 'source_attr_3',
				value: $("#source_attr_3").prop("checked") ? 1 : 0
			});
			grid.options.parms.push({
				name: 'source_attr_4',
				value: $("#source_attr_4").prop("checked") ? 1 : 0
			});
			//加载查询条件
			grid.loadData(grid.where);
		}

		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [ 
				{display: '科室编码', name: 'dept_code', align: 'left', minWidth: '180', frozen: true}, 
                {display: '科室名称', name: 'dept_name', align: 'left', minWidth: 180, frozen: true}, 
                {display : '科室性质', name : 'natur_name', align : 'left', minWidth: '180', frozen : true}, 
				{display: '人员经费', name: '', align: 'left',
					columns: [
						{display: '金额', name: 'cost_emp_amount', formatter:'###,##0.00', align: 'right', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_emp_amount, 2, 1)
							}
						}, 
						{display: '%', name: 'emp_bl', align: 'right', minWidth: '100' ,
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.emp_bl, 2, 1)
							},
						}
					]
				}, 
				{display: '卫生材料费', name: '', align: 'left',
					columns: [
						{display: '金额', name: 'cost_mate_amount', align: 'right',formatter:'###,##0.00', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_mate_amount, 2, 1)
							},
						}, 
						{display: '%', name: 'mat_bl', align: 'right', minWidth: '100',
							render: function (rowdata, rowindex, value) {
							return formatNumber(rowdata.mat_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '药品费',name: '',align: 'left',
					columns: [
						{display: '金额',name: 'cost_drug_amount',align: 'right',formatter:'###,##0.00', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_drug_amount, 2, 1)
							},
						}, 
						{display: '%',name: 'drug_bl',align: 'right',formatter:'###,##0.00', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.drug_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '固定资产折旧',name: '',align: 'left',
					columns: [
						{display: '金额',name: 'cost_fasset_amount',align: 'right',formatter:'###,##0.00', minWidth: '100', 
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_fasset_amount, 2, 1)
							},
						}, 
						{display: '%',name: 'fasset_bl',align: 'right', minWidth: '100', 
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.fasset_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '无形资产摊销', name: '', align: 'left',
					columns: [
						{display: '金额',name: 'cost_iasset_amount', align: 'right', formatter:'###,##0.00',minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_iasset_amount, 2, 1)
							},
						}, 
						{display: '%', name: 'iasset_bl', align: 'right', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.iasset_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '提取医疗风险基金', name: '', align: 'left',
					columns: [
						{display: '金额', name: 'cost_risk_amount',align: 'right',formatter:'###,##0.00', minWidth: '100' , 
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_risk_amount, 2, 1)
							},
						}, 
						{display: '%', name: 'risk_bl',align: 'right', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.risk_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '其他费用', name: '', align: 'left',
					columns: [
						{display: '金额', name: 'cost_other_amount', align: 'right',formatter:'###,##0.00', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.cost_other_amount, 2, 1)
							},
						}, 
						{display: '%', name: 'other_bl', align: 'right', minWidth: '100',
							render: function (rowdata, rowindex, value) {
								return formatNumber(rowdata.other_bl, 2, 1)
							},
						},
					]
				}, 
				{display: '科室全成本合计', name: 'dept_sum', align: 'right',formatter:'###,##0.00', minWidth: '100',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.dept_sum, 2, 1)
					},
				}, 
				{display: '科室收入', name: 'sum_money', align: 'right',formatter:'###,##0.00', minWidth: '100', 
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.sum_money, 2, 1)
					},
				}, 
				{display: '收入-成本', name: 'sum_ce', align: 'right',formatter:'###,##0.00', minWidth: '100' ,
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.sum_ce, 2, 1)
					},
				}, 
				{display: '床日成本', name: 'clinic_cost', align: 'right',formatter:'###,##0.00', minWidth: '100',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.clinic_cost, 2, 1)
					},
				}, 
				{display: '诊次成本', name: 'bed_cost', align: 'right',formatter:'###,##0.00', minWidth: '100',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.bed_cost, 2, 1)
					},
				}],
				dataAction: 'server',
				dataType: 'server',
				usePager: true,
				url: 'queryCostReportGroupIncome.do',
				width: '100%',
				height: '100%',
				checkbox: false,
				rownumbers: true,
				selectRowButtonOnly: true,
				toolbar: {
					items: [
						{text: '查询',id: 'search',click: query,icon: 'search'}, 
						{line: true}, 
						{text: '打印',id: 'print',click: print1,icon: 'print'}, 
						{line: true}
					]
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function loadDict() {
			//字典下拉框
			autodate("#year_month_begin", "yyyyMM");
			autodate("#year_month_end", "yyyyMM");
		}

		function print1(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		           {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "临床服务类科室全成本构成表",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.CostReportService",
 	   			method_name: "queryCostReportGroupIncomePrint",
 	   			bean_name: "costReportService",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
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
<td align="left" class="l-table-edit-td">
	<input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyyMM'})" ltype="text" /></td>
<td align="left" class="l-table-edit-td">至：</td>
<td align="left" class="l-table-edit-td">
	<input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text" /></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"><input name="source_attr_2" type="checkbox" id="source_attr_2" ltype="text" /> 包含财政
	<td align="center" class="l-table-edit-td"><input name="source_attr_3" type="checkbox" id="source_attr_3" ltype="text" /> 包含科研
		<td align="center" class="l-table-edit-td"><input name="source_attr_4" type="checkbox" id="source_attr_4" ltype="text" /> 包含教学

			</tr>
			</table>

</div>
<div id="maingrid"></div>
</body>

</html>