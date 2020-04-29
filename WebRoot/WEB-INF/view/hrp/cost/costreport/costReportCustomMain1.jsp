<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
%>
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html style="overflow:hidden;">

<head>


	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var spread;
		$(function () {

			loadDict();

			loadHead(); //加载数据
		});
		//查询
		function query() {

			grid.options.parms = [];

			grid.options.newPage = 1;

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
			
			grid.loadData(grid.where);

		}

		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [
                   {
	                   display: '科室编码',
	                   name: 'dept_code',
	                   align: 'left',
	                   minWidth: 90,
	                  frozen: true
                }, {
					display: '科室名称',
					name: 'dept_name',
					align: 'left',
					minWidth: 90,
					frozen: true
				},{ 
					display: '科室性质',
					name: 'natur_name',
					align: 'left',
					minWidth: 90,
					frozen: true
				}, {
					display: '人员经费',
					name: 'cost_emp_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_emp_amount, 2, 1)
					},
					minWidth: 90
				}, {
					display: '卫生材料费',
					name: 'cost_mate_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_mate_amount, 2, 1)
					},
					minWidth: 90
				}, {
					display: '药品费',
					name: 'cost_drug_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_drug_amount, 2, 1)
					},
					minWidth: 90
				}, {
					display: '固定资产折旧',
					name: 'cost_fasset_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_fasset_amount, 2, 1)
					},
					minWidth: 90
				}, {
					display: '无形资产摊销',
					name: 'cost_iasset_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_iasset_amount, 2, 1)
					},
					minWidth: 90
				}, {
					display: '提取医疗风险基金',
					name: 'cost_risk_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.cost_risk_amount, 2, 1)
					},
					minWidth: 100
				}, {
					display: '其他费用',
					name: 'cost_other_amount',
					align: 'right',formatter:'###,##0.00',
				/* 	render: function (rowdata, rowindex, value) {
						return value ==null ? "" : "<a href=javascript:queryDirDetail('"+
								    "&dept_code="+rowdata.dept_code+"')>"+formatNumber(value, 2, 1)+"</a>";
					}, */
					minWidth: 90
				}, {
					display: '合计',
					name: 'sum_amount',
					align: 'right',formatter:'###,##0.00',
					render: function (rowdata, rowindex, value) {
						return formatNumber(rowdata.sum_amount, 2, 1)
					},
					minWidth: 90
				}],
				dataAction: 'server',
				dataType: 'server',
				usePager: true, 
				url: 'queryCostDeptCustomReport_1.do',
				width: '100%',
				height: '100%',
				delayLoad :true,
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
						//click : printDate,
						click: print,
						icon: 'print'
					}, {
						line: true
					}]
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}
		function queryDirDetail(para){
	    	
	    	var paras=para+"&year_month_begin="+$("#year_month_begin").val()
	    	          +"&year_month_end="+$("#year_month_end").val()
			parent.$.ligerDialog.open({
				title:'其他费用明细',
				height: '800',
				width: '1000',
				url: 'hrp/cost/costreport/dirAmountDetailPage.do?isCheck=false&' + paras.toString(),
				modal: true, showToggle: false,  isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});	 
	    }
		function loadDict() {
			autodate("#year_month_begin", "yyyyMM");
			autodate("#year_month_end", "yyyyMM");
			$("#year_month_begin").ligerTextBox({
				width: 160
			});

			$("#year_month_end").ligerTextBox({
				width: 160
			});
			
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
			ajaxJsonObjectByUrl("queryCostDeptCustomReport_1.do", printPara, function (
				responseData) {
				$.each(responseData.Rows, function (idx, item) {
					var trHtml = "<tr>";
					trHtml += "<td>" + item.dept_code + "</td>";
					trHtml += "<td>" + item.dept_name + "</td>";
					trHtml += "<td>" + formatNumber(item.cost_emp_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_mate_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_drug_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_fasset_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_iasset_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_risk_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_other_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.sum_amount, 2, 1)
						+ "</td>";
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
			ajaxJsonObjectByUrl("queryCostDeptCustomReport_1.do", exportPara, function (
				responseData) {
				$.each(responseData.Rows, function (idx, item) {
					var trHtml = "<tr>";
					trHtml += "<td>" + item.dept_code + "</td>";
					trHtml += "<td>" + item.dept_name + "</td>";
					trHtml += "<td>" + formatNumber(item.cost_emp_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_mate_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_drug_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_fasset_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_iasset_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_risk_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.cost_other_amount, 2, 1)
						+ "</td>";
					trHtml += "<td>" + formatNumber(item.sum_amount, 2, 1)
						+ "</td>";
					trHtml += "</tr>";
					$("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				lodopExportExcel("resultPrint", "导出Excel", "列表.xls", true);
			}, true, manager);
			return;
		}
		//打印
// 	function print(){
    	
//     	if(grid.getData().length==0){
    		
// 			$.ligerDialog.error("请先查询数据！");
			
// 			return;
// 		}
    	
//     	var selPara={};
    	
//     	$.each(grid.options.parms,function(i,obj){
    		
//     		selPara[obj.name]=obj.value;
    		
//     	});
   		
// 		var dates = getCurrentDate();
    	
//     	var cur_date = dates.split(";")[2];
//     	//跨所有列:计算列数
//     	var colspan_num = grid.getColumns(1).length-1;
   		
//     	var printPara={
//        			title:'科室直接成本表',
//        			head:[
//     				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
//     				{"cell":0,"value":
//     					"统计日期: " + $("#year_month_begin").val()+
//     					" 至  "+ $("#year_month_end").val(),
//     				"colspan":colspan_num,"br":true}
//        			],
//        			foot:[
//     				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
// 					{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
// 					{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
// 					{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
//        			],
//        			columns:grid.getColumns(1),
//        			headCount:2,//列头行数
//        			autoFile:true,
//        			type:3
//        	};
//    		ajaxJsonObjectByUrl("queryCostDeptCustomReport_1.do?isCheck=false", selPara, function (responseData) {
//    			printGridView(responseData,printPara);
// 		});

   		
//     }
		function print(){
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
	 	      		title: "科室直接成本表",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostReportService",
	 	   			method_name: "queryCostCustomReportPrint1",
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
	<input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text" /></td>
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

			<div id="maingrid"></div>
			<div id="resultPrint" style="display: none">
<table width="100%">
	<thead>
		<tr>
			<th width="200">科室编码</th>
			<th width="200">科室名称</th>
			<th width="200" align="right">人员经费</th>
			<th width="200" align="right">卫生材料费</th>
			<th width="200" align="right">药品费</th>
			<th width="200" align="right">固定资产折旧</th>
			<th width="200" align="right">无形资产摊销</th>
			<th width="200" align="right">提取医疗风险基金</th>
			<th width="200" align="right">其他费用</th>
			<th width="200" align="right">合计</th>
		</tr>
	</thead>
	<tbody></tbody>
</table>
</div>

</body>

</html>