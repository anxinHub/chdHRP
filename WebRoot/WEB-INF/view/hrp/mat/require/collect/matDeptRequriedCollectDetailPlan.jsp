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
	$(function() {
	
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		query();
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;  
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'req_id',value : '${req_id}' });
		grid.options.parms.push({name : 'req_code',value : '${req_code}' });
		
		grid.options.parms.push({name : 'dept_id',value : '${dept_id}' }); 
    	grid.options.parms.push({name : 'dept_no',value : '${dept_no}'});
    	
		grid.options.parms.push({name : 'stock_id',value : '${stock_id}'}); 
    	grid.options.parms.push({name : 'stock_no',value : '${stock_no}'});
    	
    	grid.options.parms.push({name : 'make_date',value : '${make_date}'}); 
    	
    	grid.options.parms.push({name : 'other_inv',value : '${other_inv}' }); 
    	
    
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                
						 { display: '材料编码', name: 'inv_code', align: 'left',width :'160',
							 totalSummary: {
			                        type: 'sum',
			                        render: function (suminf, column, cell) {
			                            return '<div>合计</div>';
			                        }
			                    }
						 },
						 { display: '材料名称(E)', name: 'inv_name', align: 'left',width :'160' },
						 { display: '规格型号', name: 'inv_model', align: 'left' ,width :'160'},
						 { display: '计量单位', name: 'unit_name', align: 'left' ,width :'100'},
						 { display: '包装单位(E)', name: 'pack_code', align: 'left' ,width :'100'},
						 { display: '转换量(E)', name: 'num_exchange', align: 'right',width :'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.num_exchange ==null ? 0 : rowdata.num_exchange,2,1);
				             }	 
						 },
						 { display: '包装数量(E)', name: 'num', align: 'right',width :'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.num ==null ? 0 : rowdata.num,2,1);
				             }	 
						 },
						 { display: '计划数量', name: 'amount', align: 'right',width :'100',
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
						 { display: '单价(E)', name: 'price', align: 'right' ,width :'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p04006}',1);
				             }	 
						 },
						 { display: '金额', name: 'sum_money', align: 'right',width :'100',
							 render:function(rowdata){
				            		return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,'${p04005}',1);
				            },
				            totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p04005}',1)+ '</div>';
		                        }
		                    }
						 },
						 { display: '供应商', name: 'fac_name', align: 'left' ,width :'100'},
						 { display: '生产厂商', name: 'sup_name', align: 'left' ,width :'100'},
						 { display: '需求日期', name: 'rdate', align: 'left' ,width :'100'},
						 { display: '备注', name: 'memo', align: 'left' ,width :'160'}
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,
		                 url:'queryMatDeptCollectDetail.do?isCheck=false',
		                 width: '95%', height: '80%', 
		                 checkbox: false, rownumbers:true, delayLoad : true,
		                 selectRowButtonOnly:true
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	function loadDict() {
		
		autocomplete("#dept_code","../../queryMatDept.do?isCheck=false","id","text",true,true);
        liger.get("dept_code").setValue("${dept_id},${dept_no}");
        liger.get("dept_code").setText("${dept_code} ${dept_name}");
        $("#dept_code").ligerComboBox({disabled:true,cancelable: false});
        
		//主管部门
    	autocomplete("#store_code","../../queryMatStore.do?isCheck=false","id","text",true,true);
        liger.get("store_code").setValue("${stock_id},${stock_no}");
        liger.get("store_code").setText("${store_code} ${store_name}");
        $("#store_code").ligerComboBox({disabled:true,cancelable: false});
		
        $("#dept_code").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        $("#make_date").ligerTextBox({width:160,disabled:true});
        $("#req_code").ligerTextBox({width:160,disabled:true});
        $("#other_inv").ligerTextBox({width:160,disabled:true});
        //$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
   	
	}
	
	/* function printDate(){
		
	} */
	function this_close(){
		frameElement.dialog.close();
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">计划单号：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="req_code" type="text" requried="true"  value="${req_code}" id="req_code" disabled="disabled" />
	        </td>
	        <td align="left"></td>
		</tr>
		<tr>
		 	<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制科室：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="dept_code" type="text" requried="true"  id="dept_code"  />
	        </td>
	        <td align="left"></td>
	        
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>响应库房：</td>
	        <td align="left" class="l-table-edit-td" >
	            <input name="store_code" type="text" requried="true"  id="store_code"  />
	        </td>
	        <td align="left"></td>
	        	
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red">*</font>编制日期：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <input class="Wdate" requried="true"  name="make_date" type="text" 
	            	onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" id="make_date" disabled="disabled"  
	            	value="${make_date}" 
	            />
	        </td>
	        <td align="left"></td>
	        
	         
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">其他需求物资：</td>
	        <td align="left" class="l-table-edit-td"  colspan="8">
	            <input name="other_inv" type="text"  id="other_inv"  value="${other_inv}" disabled="disabled" style="width:680px;"/>
	        </td>
	       <td align="left"></td>     
	        
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;"></td>
			<td align="left" class="l-table-edit-td"  colspan="8">
	            <font color="red">物资单价300元以下或总价1000元以下的个性化临购物资且在系统中找不到物资名称的情况请在此处填写</font>
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
