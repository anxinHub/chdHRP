<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- jsp:include page="${path}/inc.jsp"/-->
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		query();
		loadHotkeys();
		
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'query_date',value : $("#query_date").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'state',value : '${state}'}); 
		grid.options.parms.push({name : 'order_code',value : $("#order_code").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
					    {display : '单据号',name : 'order_code',align : 'left',width : '120'},
					    {display : '供应商', name: 'sup_name', align: 'left',width:'150'}, 
					    {display : '科室', name: 'dept_name', align: 'left',width:'120'},
					    {display : '审核日期', name: 'check_date', align: 'left',width:'80'},
						{display : '材料编码',name : 'inv_code',align : 'left',width : '150'},
						{display : '材料名称',name : 'inv_name',align : 'left',width : '150'},
						{display : '规格型号',name : 'inv_model',width : 120,align : 'left'},
						{display : '计量单位',name : 'unit_name',width : 60,align : 'left'},
				 		{display: '数量', name: 'amount', align: 'right',width:'80'},
						{display: '未处理数量', name: 'undeal_amount', align: 'right',width:'80'}
					],
					rowAttrRender:function(rowdata,rowid){
			 			if(rowdata.undeal_amount!=0){
			 				return "style='background:rgba(220,20,60, 0.25)'"
			 			}
		 			},
		 			alternatingRow:false,
		 			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatOrderInvInfo.do?isCheck=false',
					width: '100%', height: '100%', checkbox: true, rownumbers:true,
					delayLoad : true,//初始化不加载，默认false
					selectRowButtonOnly:true,//heightDiff: -10,
                   	pageSize:200,
					toolbar: {
						items: [{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
								{ line:true },
								{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
						]}
				});
			gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('L', this_close);
	}

	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {
		//字典下拉框
		$("#begin_date").ligerTextBox({width : 100});
		$("#end_date").ligerTextBox({width : 100});
		$("#sup_code").ligerComboBox({width:180,disabled:true,cancelable: false});
		liger.get("sup_code").setValue("${sup_id},${sup_no}");
		liger.get("sup_code").setText("${sup_code} ${sup_name}");
		$("#order_code").ligerTextBox({width : 160});
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border='0'>
		
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">订单日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left"><input class="Wdate" name="begin_date"
							id="begin_date" type="text" 
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" value="${begin_date}"/>
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="end_date" id="end_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" value="${end_date}"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">供货单位：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">订单号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="order_code" type="text" id="order_code" ltype="text"  validate="{required:false}" />
			</td>
			
			<td style="display: none;">
	            <input name="query_date" type="text" id="query_date" value="${query_date}" ltype="text" />
	           
	         </td>
		</tr>
		
	</table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
