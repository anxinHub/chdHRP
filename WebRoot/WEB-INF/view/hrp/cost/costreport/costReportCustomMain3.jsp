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
			$("#year_month_begin").ligerTextBox({
				width: 160
			});
			$("#year_month_end").ligerTextBox({
				width: 160
			});
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
				columns: [ {
	                   display: '科室编码',
	                   name: 'dept_code',
	                   align: 'left',
	                   minWidth: '180',
	                  frozen: true
                }, {
					display: '科室名称',
					name: 'dept_name',
					align: 'left',
					minWidth: 180,
				    frozen: true
				}, {
					display : '科室性质', 
					name : 'natur_name',
					align : 'left',
					minWidth: '180',
					frozen : true
				}, {
					display: '人员经费',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_emp_amount',formatter:'###,##0.00',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_emp_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'emp_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.emp_bl, 2, 1)
						},
						minWidth: '100'
					}]
				}, {
					display: '卫生材料费',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_mate_amount',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_mate_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'mat_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.mat_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}, {
					display: '药品费',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_drug_amount',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_drug_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'drug_bl',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.drug_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}, {
					display: '固定资产折旧',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_fasset_amount',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_fasset_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'fasset_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.fasset_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}, {
					display: '无形资产摊销',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_iasset_amount',
						align: 'right', formatter:'###,##0.00',render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_iasset_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'iasset_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.iasset_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}, {
					display: '提取医疗风险基金',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_risk_amount',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_risk_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'risk_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.risk_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}, {
					display: '其他费用',
					name: '',
					align: 'left',
					columns: [{
						display: '金额',
						name: 'cost_other_amount',
						align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.cost_other_amount, 2, 1)
						},
						minWidth: '100'
					}, {
						display: '%',
						name: 'other_bl',
						align: 'right', render: function (rowdata, rowindex,
							value) {
							return formatNumber(
								rowdata.other_bl, 2, 1)
						},
						minWidth: '100'
					},]
				}

					, {
					display: '科室全成本合计',
					name: 'dept_sum',
					align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
						value) {
						return formatNumber(
							rowdata.dept_sum, 2, 1)
					},
					minWidth: '100'
				}, {
					display: '科室收入',
					name: 'sum_money',
					align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
						value) {
						return formatNumber(
							rowdata.sum_money, 2, 1)
					},
					minWidth: '100'
				}, {
					display: '收入-成本',
					name: 'sum_ce',
					align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
						value) {
						return formatNumber(
							rowdata.sum_ce, 2, 1)
					},
					minWidth: '100'
				}, {
					display: '床日成本',
					name: 'clinic_cost',
					align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
						value) {
						return formatNumber(
							rowdata.clinic_cost, 2, 1)
					},
					minWidth: '100'
				}, {
					display: '诊次成本',
					name: 'bed_cost',
					align: 'right',formatter:'###,##0.00', render: function (rowdata, rowindex,
						value) {
						return formatNumber(
							rowdata.bed_cost, 2, 1)
					},
					minWidth: '100'
				}],
				dataAction: 'server',
				dataType: 'server',
				usePager: true,
				url: 'queryCostDeptReport_3.do',
				width: '100%',
				height: '100%',
				checkbox: false,
				rownumbers: true,
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: {
					items: [{
						text: '查询',
						id: 'search',
						click: query,
						icon: 'search'
					}, {
						line: true
					}, 
// 					{
// 						text: '导出Excel',
// 						id: 'export',
// 						click: exportExcel,
// 						icon: 'pager'
// 					}, {
// 						line: true
// 					},
					{
						text: '打印',
						id: 'print',
						click: print1,
						icon: 'print'
					}, {
						line: true
					}]
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function loadDict() {
			//字典下拉框
			autodate("#year_month_begin", "yyyyMM");
			autodate("#year_month_end", "yyyyMM");
		}

		//打印数据
		function printDate() {
			//有数据直接打印
			if ($("#resultPrint > table > tbody").html() != "") {
				lodopPrinterTable("resultPrint", "开始打印", "列表", true);
				return;
			}

			//重新查询数据，避免分页导致打印数据不全
			var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
			var printPara = {

				usePager: false,
				year_month_begin: $("#year_month_begin").val(),
				year_month_end: $("#year_month_end").val()
			};
			ajaxJsonObjectByUrl("queryCostDeptReport_3.do", printPara, function (
				responseData) {
				$.each(responseData.Rows, function (idx, item) {
					var trHtml = "<tr>";
					trHtml += "<td>" + item.dept_code + "</td>";
					trHtml += "<td>" + item.dept_name + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_emp_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.emp_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_mate_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.mat_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_drug_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.drug_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_fasset_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.fasset_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_iasset_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.iasset_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_risk_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.risk_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_other_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.other_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.dept_sum, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.sum_income, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.sum_ce, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.clinic_cost, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.bed_cost, 2, 1) + "</td>";
					trHtml += "</tr>";
					$("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				//alert($("#resultPrint").html())
				lodopPrinterTable("resultPrint", "开始打印", "列表", true);
			}, true, manager);
			return;
		}

		//导出数据
		function exportExcel() {
			//有数据直接导出
			if ($("#resultPrint > table > tbody").html() != "") {
				lodopExportExcel("resultPrint", "导出Excel", "列表.xls", true);
				return;
			}

			//重新查询数据，避免分页导致打印数据不全
			var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

			var exportPara = {
				usePager: false,
				year_month_begin: $("#year_month_begin").val(),
				year_month_end: $("#year_month_end").val()
			};
			ajaxJsonObjectByUrl("queryCostDeptReport_3.do", exportPara, function (
				responseData) {
				$.each(responseData.Rows, function (idx, item) {
					var trHtml = "<tr>";
					trHtml += "<td>" + item.dept_code + "</td>";
					trHtml += "<td>" + item.dept_name + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_emp_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.emp_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_mate_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.mat_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_drug_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.drug_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_fasset_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.fasset_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_iasset_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.iasset_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_risk_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.risk_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.cost_other_amount, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.other_bl, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.dept_sum, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.sum_income, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.sum_ce, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.clinic_cost, 2, 1) + "</td>";
					trHtml += "<td>" + formatNumber(
						item.bed_cost, 2, 1) + "</td>";
					trHtml += "</tr>";
					$("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				lodopExportExcel("resultPrint", "导出Excel", "列表.xls", true);
			}, true, manager);
			return;
		}
		function print() {
			var printPara = {
				usePager: true,
				year_month_begin: $("#year_month_begin").val(),
				year_month_end: $("#year_month_end").val()
			};

			$.ajax({
				url: "queryCostDeptReport_3.do",
				type: "post",
				dataType: "JSON",
				data: printPara,
				success: function (res) {
					var data = {
						headers: [
							{ x: 0, y: 0, rowSpan: 2, colSpan: 1, displayName: "统计年月", name: "acc_year" },
							{ x: 1, y: 0, rowSpan: 2, colSpan: 1, displayName: "科室名称", name: "dept_name" },

							{ x: 2, y: 0, rowSpan: 1, colSpan: 2, displayName: "人员经费" },
							{ x: 2, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_emp_amount" },
							{ x: 3, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "emp_bl" },

							{ x: 4, y: 0, rowSpan: 1, colSpan: 2, displayName: "卫生材料费" },
							{ x: 4, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_mate_amount" },
							{ x: 5, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "mat_b1" },

							{ x: 6, y: 0, rowSpan: 1, colSpan: 2, displayName: "药品费" },
							{ x: 6, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_drug_amount" },
							{ x: 7, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "drug_bl" },

							{ x: 8, y: 0, rowSpan: 1, colSpan: 2, displayName: "固定资产折旧" },
							{ x: 8, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_fasset_amount" },
							{ x: 9, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "fasset_bl" },

							{ x: 10, y: 0, rowSpan: 1, colSpan: 2, displayName: "无形资产摊销" },
							{ x: 10, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_iasset_amount" },
							{ x: 11, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "iasset_bl" },

							{ x: 12, y: 0, rowSpan: 1, colSpan: 2, displayName: "提取医疗风险基金" },
							{ x: 12, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_risk_amount" },
							{ x: 13, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "risk_bl" },

							{ x: 14, y: 0, rowSpan: 1, colSpan: 2, displayName: "其他费用" },
							{ x: 14, y: 1, rowSpan: 1, colSpan: 1, displayName: "金额", name: "cost_other_amount" },
							{ x: 15, y: 1, rowSpan: 1, colSpan: 1, displayName: "%", name: "other_bl" },

							{ x: 16, y: 0, rowSpan: 2, colSpan: 1, displayName: "科室全成本合计", name: "dept_sum" },
							{ x: 17, y: 0, rowSpan: 2, colSpan: 1, displayName: "科室收入", name: "sum_money" },
							{ x: 18, y: 0, rowSpan: 2, colSpan: 1, displayName: "收入成本", name: "sum_ce" },
							{ x: 19, y: 0, rowSpan: 2, colSpan: 1, displayName: "床日成本", name: "clinic_cost" },
							{ x: 20, y: 0, rowSpan: 2, colSpan: 1, displayName: "诊次成本", name: "bed_cost" }
						],
						rows: res.Rows
					}

					viewPrint(data, "临床服务类科室全成本结构分析表");
				},
				error: function (res) {
					console.error(res);
				}
			})


		}
// 		function print1(){
// 			//console.log(grid)
// 	   		var printPara={
// 	   			headCount:2,
// 	   			title:'科目总账(按月)',
// 	   			type:3,
// 	   			columns:grid.getColumns(1)
// 	   		};
			
// 	   		var printPara1 = {
// 					usePager: true,
// 					year_month_begin: $("#year_month_begin").val(),
// 					year_month_end: $("#year_month_end").val()
// 				};
	   		
// 	   	   	ajaxJsonObjectByUrl("queryCostDeptReport_3.do?isCheck=false", printPara1, function (responseData) {
// 	   	   		printGridView(responseData,printPara);
// 	   		});

// 		}
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
 	   			method_name: "queryCostCustomReportPrint3",
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
<div id="resultPrint" style="display: none">
<table width="100%">
	<thead>
		<tr>
			<th rowspan="2" width="200" align="center">统计年月</th>
			<th rowspan="2" width="200" align="center">科室名称</th>
			<th colspan="2" width="200" align="center">人员经费</th>
			<th colspan="2" width="200" align="center">卫生材料费</th>
			<th colspan="2" width="200" align="center">药品费</th>
			<th colspan="2" width="200" align="center">固定资产折旧</th>
			<th colspan="2" width="200" align="center">无形资产摊销</th>
			<th colspan="2" width="200" align="center">提取医疗风险基金</th>
			<th colspan="2" width="200" align="center">其他费用</th>
			<th rowspan="2" width="200" align="center">科室全成本合计</th>
			<th rowspan="2" width="200" align="center">科室收入</th>
			<th rowspan="2" width="200" align="center">收入-成本</th>
			<th rowspan="2" width="200" align="center">床日成本</th>
			<th rowspan="2" width="200" align="center">诊次成本</th>
		</tr>
		<tr>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
			<th width="200" align="right">金额</th>
			<th width="200" align="right">%</th>
		</tr>
	</thead>
	<tbody></tbody>
</table>
</div>
</body>

</html>