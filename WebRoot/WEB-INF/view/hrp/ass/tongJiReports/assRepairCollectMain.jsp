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
		
	loadDict();//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;

		//根据表字段进行添加查询条件  

		grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()});
		grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split('@')[0]});
		//加载查询条件
		grid.loadData(grid.where);

	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{ display : '科室编码',name : 'dept_code',align : 'left'},
				{ display : '科室名称',name : 'dept_name',align : 'left'},
				{ display : '维修工时',name : 'repair_hours',align : 'left'},
				{ display : '小维修次数',name : 'small_maintain_times',align : 'left'}, 
				{ display : '中维修次数',name : 'middle_maintain_times',align : 'left'}, 
				{ display : '大维修次数',name : 'large_maintain_times',align : 'left'}, 
				{ display : '总计维修次数',name : 'sum_maintain_times',align : 'left',type:'int',
					/* render : function(rowdata, rowindex,value) {
						var sum_maintain_times =rowdata.small_maintain_times+rowdata.middle_maintain_times+rowdata.large_maintain_times
			 				return sum_maintain_times;
			 			
					} */
				}
						],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssRepairCollect.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : false,
			isScroll : true,
			selectRowButtonOnly : true,
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
					click : printDate,
					icon : 'print'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//打印回调方法
	function lodopPrint() {
		var head = "<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
		head = head + "<tr><td>制单日期：" + $("#create_date_start").val() + " 至  "
				+ $("#create_date_end").val() + "</td></tr>";
		head = head + "</table>";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "资产维修记录";
	}

	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=$("#year_month_begin").val()+"至"+$("#year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"申请科室："},
    	          {"cell":1,"value":liger.get("dept_id").getText().split(" ")[1]},
    	          {"cell":5,"value":"制表日期:"},
  				  {"cell":6,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":5,"value":"制表人:"},
    				{"cell":6,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "维修科室统计",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssRepairCollectService",
 				method_name: "queryAssRepairCollectPrint",
 				bean_name: "assRepairCollectService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}

	function loadDict() {

		autocomplete("#dept_id", "../../queryDeptDict.do?isCheck=false", "id", "text", true, true, '', false);
		$("#year_month_begin,#year_month_end").ligerTextBox({width : 80});

	}
</script>

</head>

<body style="overflow: auto;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td">申请日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="year_month_begin" type="text" id="year_month_begin"
				class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" />
			</td>
			<td align="center">至</td>
			<td align="left"><input name="year_month_end" type="text"
				id="year_month_end" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td">申请科室：</td>
			<td align="left"><input name="dept_id" type="text" id="dept_id" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
