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
			grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()}); 

			grid.loadData(grid.where);

		}

		function loadHead() {
			var columns = [
	                   { display: '科室编码',
		                   name: 'dept_code',
		                   align: 'left',
		                   minWidth: '20%',
		                  frozen: true
	                }, {
						display: '科室名称',
						name: 'dept_name',
						align: 'left',
						minWidth: '20%',
						frozen: true
					}]
			var cost_type_columns="";
			ajaxJsonObjectByUrl("queryCostTypeDictThead.do?isCheck=false", "", function (responseData){
	    		if(responseData.Rows.length > 0){
	    			$.each(responseData.Rows, function(v_index, v_data){ 
	    				cost_type_columns="{"
	        				      +"display: '"+v_data.cost_type_name+"',"
	        				      +"name:'t_"+v_data.cost_type_code+"',"
	        				      +"align: 'right' ,"
	        				      +"formatter: '###,##0.00' ,"
	        				      +"minWidth: '10%' ,"
	        				      +"render:function(rowdata, rowindex, value){return formatNumber(value,2,1)}"
	    				          +"}"; 
	    				columns.push(eval("("+cost_type_columns+")"));
	    			}); 
				}
	    	}, false);
			columns.push(eval("({ display: '合计', name: 'sum_amount', align: 'right',formatter:'###,##0.00',minWidth: '10%',render:function(rowdata, rowindex, value){return formatNumber(value,2,1)}})"));
			grid = $("#maingrid").ligerGrid({
				columns:columns,
				dataAction: 'server',
				dataType: 'server',
				usePager: true, 
				url: 'queryCostDeptReport_1.do',
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
					{
						text: '打印',
						id: 'print',
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
			autocomplete("#dept_level","../queryHosDeptLevel.do?isCheck=false","id","text",true,true);  

		}
		function print(){
			
			var source = "(1";
			
			if($("#source_attr_2").prop("checked") == 1){
				source += ",2";
			}
			if($("#source_attr_3").prop("checked") == 1){
				source += ",3";
			}
			if($("#source_attr_4").prop("checked") == 1){
				source += ",4";
			}
			source+=")";
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
	 	   			method_name: "queryCostReportPrint1",
	 	   			bean_name: "costReportService",
	 	   			source_attr: source,
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
<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室级次：</td>
<td align="left" class="l-table-edit-td" colspan="4"><input name="dept_level" type="text" id="dept_level" /></td>
<td align="center" class="l-table-edit-td"></td>
<td align="center" class="l-table-edit-td"><input name="source_attr_2" type="checkbox" id="source_attr_2" ltype="text" /> 包含财政</td>
<td align="center" class="l-table-edit-td"><input name="source_attr_3" type="checkbox" id="source_attr_3" ltype="text" /> 包含科研</td>
<td align="center" class="l-table-edit-td"><input name="source_attr_4" type="checkbox" id="source_attr_4" ltype="text" /> 包含教学</td>
</tr>
</table>
<div id="maingrid"></div>
<div id="resultPrint" style="display: none">
</div>
</body>
</html>