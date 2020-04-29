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
	var req_no = null;
	
	$(function() {
		
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'req_id',value :'${req_id}'}); //计划单号
    
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '汇总需求计划号', name: 'req_code', align: 'left',width :'120',
		                	 totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>合计</div>';
			                        }
			                    }	 
		                 },
						 { display: '汇总库房', name: 'store_name', align: 'left' ,width:'120'},
						 { display: '药品代码', name: 'inv_code', align: 'left',width:'100'},
						 { display: '药品名称', name: 'inv_name', align: 'left',width:'150'},

						 { display: '规格型号', name: 'inv_model', align: 'left' ,width:'200' },
						 { display: '计量单位', name: 'unit_name', align: 'left' ,width:'100'},
						 { display: '计划数量', name: 'amount', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
				             },
				             totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
			                        }
			                    }	 
						 },
						 { display: '计量单价', name: 'price', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p08006}',1);
				             }	 
						 },
						 { display: '计划金额', name: 'money', align: 'right',width:'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.money ==null ? 0 : rowdata.money,'${p08005}',1);
				             },
				             totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p08005}',1)+ '</div>';
			                        }
			                    }
						 }
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMedDeptCollectStoreDetail.do?isCheck=false',
		                 width: '95%', height: '85%', checkbox: false, rownumbers : true, delayLoad : true,
		                 selectRowButtonOnly:true
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {
		//$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	}
	function printDate(){
		
	}
	function this_close(){
		frameElement.dialog.close();
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr></tr>
		<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 10px;">
				汇总需求计划单号：&nbsp;&nbsp;${req_code}    &nbsp;&nbsp;&nbsp;&nbsp;   制单人：&nbsp;&nbsp; ${maker}
			</td>
			
			
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
