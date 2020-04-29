<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	var store_id = null;
	var store_no;
	var store_name;
	
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
        
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'inv_id',value : "${inv_id}"}); 
    	grid.options.parms.push({name : 'inv_no',value : "${inv_no}"}); 
    	grid.options.parms.push({name : 'store_id',value : "${store_id}"}); 
    	grid.options.parms.push({name : 'store_no',value : "${store_no}"}); 
    	grid.options.parms.push({name : 'acc_year',value : "${acc_year}"}); 
    	grid.options.parms.push({name : 'acc_month',value : "${acc_month}"}); 
    	grid.options.parms.push({name : 'begin_date',value : "${begin_date}"}); 
    	grid.options.parms.push({name : 'end_date',value : "${end_date}"});
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '需求计划单号', name: 'req_code', align: 'left',
		                	 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:openUpdate('"
											+rowdata.req_id   + "|" 
											+ rowdata.req_code + "|" 
											+"')>"+rowdata.req_code+"</a>"
								}			 
		                 
		                 },
						 { display: '申请部门', name: 'dept_name', align: 'left' },
						 { display: '单价', name: 'price', align: 'right' ,
							 render:function(rowdata){
				            		return formatNumber(rowdata.price == null ? 0 : rowdata.price,'${p08006}',1);
				             }	 
						 },
						 { display: '需求数量', name: 'req_amount', align: 'right',
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_amount == null ? 0 : rowdata.req_amount,2,1);
				             }
						 },
						 { display: '执行数量', name: 'exec_amount', align: 'right',
							 render:function(rowdata){
				            		return formatNumber(rowdata.exec_amount == null ? 0 : rowdata.exec_amount,2,1);
				             }	 
						 },
						 { display: '需求金额', name: 'req_money', align: 'right',
							 render:function(rowdata){
				            		return formatNumber(rowdata.req_money == null ? 0 : rowdata.req_money,'${p08005}',1);
				             }	 
						 }
					
		                ],
		                	
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedCollectDetail.do?isCheck=false',
		                 width: '100%', height: '90%', checkbox: false,rownumbers:true,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(+rowdata.req_id   + "|" 
										+ rowdata.req_code );
							}
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "req_id="+vo[0]+"&req_code="+vo[1];	
		$.ligerDialog.open({ 
			url : 'queryMedDeptCollectDetailPlan.do?isCheck=false&'+parm,data:{}, 
			height: 520,width: 880, 
			title:'计划单页面', modal : true, showToggle : false, showMax : true, showMin : false, isResize : true
		});
		
	}
	
	
	function loadDict() {
		
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		$("#inv_code").ligerTextBox({width:300,disabled:true});
	}
	//键盘事件
	function loadHotkeys() {
		
		//hotkeys('P', printDate);//打印
		hotkeys('C', this_close);//关闭
		
	}
	//打印
	function printDate(){
		
	}
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">药品信息：</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" requried="true" id="inv_code" disabled="disabled" value="${inv_code}&nbsp;&nbsp;${inv_name}"/>
			</td>
			<td align="left"></td>
			
		</tr>
		
	</table>

	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
	
</body>
</html>
