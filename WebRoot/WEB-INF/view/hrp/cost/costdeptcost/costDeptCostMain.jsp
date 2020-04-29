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
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#acc_month").ligerTextBox({
			width : 160
		});
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#dept_no").ligerTextBox({
			width : 160
		});
		$("#cost_item_id").ligerTextBox({
			width : 160
		});
		$("#cost_item_no").ligerTextBox({
			width : 160
		});
		$("#source_id").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : $("#acc_month").val()
		});
		grid.options.parms.push({
			name : 'source_id',
			value : liger.get("source_id").getValue()
		});

		var dept_dict = liger.get("dept_id").getValue();

		var cost_item = liger.get("cost_item_id").getValue();

		if (dept_dict != null && dept_dict != '') {

			grid.options.parms.push({
				name : 'dept_id',
				value : dept_dict.split(".")[0]
			});

			grid.options.parms.push({
				name : 'dept_no',
				value : dept_dict.split(".")[1]
			});

		}

		if (cost_item != null && cost_item != '') {

			grid.options.parms.push({
				name : 'cost_item_id',
				value : cost_item.split(".")[0]
			});

			grid.options.parms.push({
				name : 'cost_item_no',
				value : cost_item.split(".")[1]
			});

		}

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '年度',
										name : 'acc_year',
										align : 'left',
										width : 40,frozen: true
									},
									{
										display : '月份',
										name : 'acc_month',
										align : 'left',
										width : 40,frozen: true
									},
									{
										display : '科室编码',
										name : 'dept_code',
										align : 'left',
										width : 80,frozen: true
									},
									{
										display : '科室名称',
										name : 'dept_name',
										align : 'left',
										width : 80,frozen: true
									},
									{
										display : '项目编码',
										name : 'cost_item_code',
										align : 'left',
										width : 80,frozen: true
									},
									{
										display : '项目名称',
										name : 'cost_item_name',
										align : 'left',
										width : 80,frozen: true
									},
									{
										display : '资金来源',
										name : 'source_name',
										align : 'left',
									    width : 80,frozen: true
									},
									{
										display : '直接成本',
										name : 'dir_amount',
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.dir_amount, 2, 1)
										},
										width : 80
									},
									{
										display : '间接管理成本',
										name : "",
										aling : "center",
										columns : [
												{
													display : '分摊管理直接成本',
													name : 'dir_man_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.dir_man_amount,
																2, 1)
													},
													width : 90
												},
												{
													display : '间接分摊医辅管理成本',
													name : 'indir_ass_man_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.indir_ass_man_amount,
																2, 1)
													},
													width : 90
												},
												{
													display : '间接分摊医技管理成本',
													name : 'indir_med_man_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.indir_med_man_amount,
																2, 1)
													},
													width : 90
												},
												{
													display : '间接分摊医技医辅管理成本',
													name : 'indir_ass_med_man_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.indir_ass_med_man_amount,
																2, 1)
													},
													width : 90
												} ]

									},
									{
										display : "间接医辅成本",
										name : "",
										aling : 'center',
										columns : [

												{
													display : '分摊医辅直接成本',
													name : 'dir_ass_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.dir_ass_amount,
																2, 1)
													},
													width : 90
												},
												{
													display : '间接分摊医技医辅成本',
													name : 'indir_med_ass_amount',
													align : 'right',
													render : function(rowdata,
															rowindex, value) {
														return formatNumber(
																rowdata.indir_med_ass_amount,
																2, 1)
													},
													width : 90
												} ]

									},
									{
										display : '分摊医技成本',
										name : 'dir_med_amount',
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.dir_med_amount, 2,
													1)
										},width : 90
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryCostDeptCost.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad :true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '查询（<u>E</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}
								, {
									text : '校验（<u>K</u>）',
									id : 'add',
									click : check,
									icon : 'add'
								}, {
									line : true
								}
								
								, {
									text : '分摊（<u>A</u>）',
									id : 'add',
									click : generate,
									icon : 'add'
								}, {
									line : true
								}
								, 
// 								{
// 									text : '导出Excel（<u>E</u>）',
// 									id : 'export',
// 									click : exportExcel,
// 									icon : 'pager'
// 								}, {
// 									line : true
// 								}, 
								{
									text : '打印（<u>P</u>）',
									id : 'print',
									click : print,
									icon : 'print'
								}, {
									line : true
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function generate() {
		if ($("#acc_year").val() == "") {

			$.ligerDialog.error('统计年份不能为空');

			return;
		}
		if ($("#acc_month").val() == "") {

			$.ligerDialog.error('统计月份不能为空');

			return;
		}
		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val()
		};

		$.ligerDialog.confirm('是否确定分摊?', function(yes) {
			if (yes) {
				//ajaxJsonObjectByUrl("generate.do", formPara, function(
				ajaxJsonObjectByUrl("saveProc.do?isCheck=false", formPara, function(
						responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	function check() {
		if ($("#acc_year").val() == "") {

			$.ligerDialog.error('统计年份不能为空');

			return;
		}
		if ($("#acc_month").val() == "") {
 
			$.ligerDialog.error('统计月份不能为空');

			return;
		}
		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val()
		};

		$.ligerDialog.confirm('是否确定校验?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("check.do?isCheck=false", formPara, function(
						responseData) {
					if (responseData.state == "true") {
						if(responseData.message != "校验成功"){
							opencheck();
						}
					}
					
				});
			}
		});
	}
	//校验
    function opencheck(){
    	parent.$.ligerDialog.open({
			title: '校验',
			height: 550,
			width: 1000,
			url: 'hrp/cost/costdeptcost/costDeptCostCheckPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: false, showMin:false, isResize: true, top: 10,
			parentframename: window.name,
		});
      }
	function loadDict() {
		//字典下拉框
		autocomplete("#dept_id", "../queryDeptDictNo.do?isCheck=false", "id",
				"text", true, true);

		autocomplete("#cost_item_id", "../queryItemDictNo.do?isCheck=false",
				"id", "text", true, true);

		autocomplete("#source_id", "../querySourceArrt.do?isCheck=false", "id",
				"text", true, true);

		$("#acc_year").ligerTextBox({
			width : 160
		});
		autodate("#acc_year", "yyyy");
		$("#acc_month").ligerTextBox({
			width : 160
		});
		autodate("#acc_month", "MM");

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', generate);

		hotkeys('E', exportExcel);

		hotkeys('P', print);

	}
	//打印数据
// 	function print() {
// 		var exportPara = {
// 	   			usePager : false,
// 	   			acc_year:$("#acc_year").val(),
// 	   			acc_month:$("#acc_month").val(),
// 	   			dept_id:$("#dept_id").val(),
// 	   			cost_item_id:$("#cost_item_id").val(),
// 	   			source_id:$("#source_id").val()		
// 	   		};
// 		$.ajax({
// 			   url:"queryCostDeptCost.do",
// 			   type:"post",
// 			   data:exportPara,
// 			   dataType:"JSON",
// 			   success:function(res){
				  
// 				   var data={
// 						   headers:[
// 									{ x: 0, y: 0, rowSpan: 2, colSpan: 1, displayName: "年度", name: "acc_year",size:80},
// 									{ x:1, y: 0, rowSpan: 2, colSpan: 1, displayName: "月份", name: "acc_month",size:80},
// 									{ x:2, y: 0, rowSpan: 2, colSpan: 1, displayName: "科室编码", name: "dept_code",size:80},
// 									{ x:3, y: 0, rowSpan: 2, colSpan: 1, displayName: "科室名称", name: "dept_name",size:80},
// 									{ x: 4, y: 0, rowSpan: 2, colSpan: 1, displayName: "项目编码", name: "cost_item_code",size:80},
// 									{ x: 5, y: 0, rowSpan: 2, colSpan: 1, displayName: "项目名称", name: "cost_item_name",size:80},
// 									{ x: 6, y: 0, rowSpan: 2, colSpan: 1, displayName: "资金来源", name: "source_name",size:80},
// 									{ x: 7, y: 0, rowSpan: 2, colSpan: 1, displayName: "直接成本", name: "dir_amount",size:80},
// 								    { x: 8, y: 0, rowSpan: 1, colSpan: 4, displayName: "间接管理成本", size:240},
// 									{ x: 8, y: 1, rowSpan: 1, colSpan: 1, displayName: "分摊直接成本", name: "dir_man_amount",size:60},
// 									{ x: 9, y: 1, rowSpan: 1, colSpan: 1, displayName: "间接分摊医辅管理", name: "indir_ass_man_amount",size:60},
// 									{ x: 10, y: 1, rowSpan: 1, colSpan: 1, displayName: "间接分摊医技管理", name: "indir_med_man_amount",size:60},
// 									{ x: 11, y: 1, rowSpan: 1, colSpan: 1, displayName: "间接分摊医技医辅", name: "indir_ass_med_man_amount",size:60},
// 									{ x: 12, y: 0, rowSpan: 1, colSpan: 2, displayName: "间接医辅成本", size:120},
// 									{ x: 12, y: 1, rowSpan: 1, colSpan: 1, displayName: "分摊医辅直接成本", name: "dir_ass_amount",size:60},
// 									{ x: 13, y: 1, rowSpan: 1, colSpan: 1, displayName: "间接分摊医技医辅成本", name: "indir_med_ass_amount",size:60},
// 									{ x: 14, y: 0, rowSpan: 2, colSpan: 1, displayName: "分摊医技成本", name: "dir_med_amount",size:60}, 
// 									],
// 						    rows: res.Rows
						   
// 				   }
// 				   viewPrint(data, "科室核算成本");
// 			   },
// 			   error: function (res) {
// 						console.error(res);
// 					}
// 		});	 	  	 	   		 		 	
// 	}
	    function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计年："+$("#acc_year").val()+"月"+$("#acc_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "科室成本核算",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostDeptCostService",
	 	   			method_name: "queryCostDeptCostPrint",
	 	   			bean_name: "costDeptCostService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);	

	   		
	    }
	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "成本_科室成本核算总表.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			acc_year : $("#acc_year").val(),
			acc_month : $("#acc_month").val(),
			dept_id : $("#dept_id").val(), 
			dept_no : $("#dept_no").val(),
			cost_item_id : $("#cost_item_id").val(),
			cost_item_no : $("#cost_item_no").val(),
			source_id : $("#source_id").val(),
			dir_amount : $("#dir_amount").val(),
			dir_man_amount : $("#dir_man_amount").val(),
			dir_ass_amount : $("#dir_ass_amount").val(),
			dir_med_amount : $("#dir_med_amount").val(),
			indir_ass_man_amount : $("#indir_ass_man_amount").val(),
			indir_med_man_amount : $("#indir_med_man_amount").val(),
			indir_ass_med_man_amount : $("#indir_ass_med_man_amount").val(),
			indir_med_ass_amount : $("#indir_med_ass_amount").val()
		};
		ajaxJsonObjectByUrl("queryCostDeptCost.do", exportPara,
				function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>";
						trHtml += "<td>" + item.acc_year + "</td>";
						trHtml += "<td>" + item.acc_month + "</td>";
						trHtml += "<td>" + item.dept_id + "</td>";
						trHtml += "<td>" + item.dept_no + "</td>";
						trHtml += "<td>" + item.cost_item_id + "</td>";
						trHtml += "<td>" + item.cost_item_no + "</td>";
						trHtml += "<td>" + item.source_id + "</td>";
						trHtml += "<td>" + item.dir_amount + "</td>";
						trHtml += "<td>" + item.dir_man_amount + "</td>";
						trHtml += "<td>" + item.dir_ass_amount + "</td>";
						trHtml += "<td>" + item.dir_med_amount + "</td>";
						trHtml += "<td>" + item.indir_ass_man_amount + "</td>";
						trHtml += "<td>" + item.indir_med_man_amount + "</td>";
						trHtml += "<td>" + item.indir_ass_med_man_amount
								+ "</td>";
						trHtml += "<td>" + item.indir_med_ass_amount + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").empty();
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					lodopExportExcel("resultPrint", "导出Excel",
							"成本_科室成本核算总表.xls", true);
				}, true, manager);
		return;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计年：</td>
			<td align="left" class="l-table-edit-td"><input name="acc_year_month"
				type="text" id="acc_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
		 	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计月份：</td>
			<td align="left" class="l-table-edit-td"><input name="acc_month"
				type="text" id="acc_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="cost_item_id" type="text" id="cost_item_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_id"
				type="text" id="source_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">统计年份</th>
					<th width="200">统计月份</th>
					<th width="200">科室ID</th>
					<th width="200">科室变更ID</th>
					<th width="200">成本项目ID</th>
					<th width="200">成本项目变更ID</th>
					<th width="200">资金来源</th>
					<th width="200">直接成本</th>
					<th width="200">分摊管理直接成本</th>
					<th width="200">分摊医辅直接成本</th>
					<th width="200">分摊医技直接成本</th>
					<th width="200">间接分摊医辅管理成本</th>
					<th width="200">间接分摊医技管理成本</th>
					<th width="200">间接分摊医技医辅管理成本</th>
					<th width="200">间接分摊医技医辅成本</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
