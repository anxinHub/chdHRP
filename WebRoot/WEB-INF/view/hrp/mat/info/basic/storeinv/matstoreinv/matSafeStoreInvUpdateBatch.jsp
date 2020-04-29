<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var invData = frameElement.dialog.options.data.invData;
	var store_id = frameElement.dialog.options.data.store_id;
	
	$(function (){ 
		loadDict();//加载下拉框
	});

	//保存
	function save(){
		var param = {
			store_id: store_id, 
			pack_code: liger.get("pack_code").getValue(), 
			num_exchange: $("#num_exchange").val(), 
			period: liger.get("period").getValue(), 
			period_num: $("#period_num").val(), 
			ps_period: $("#ps_period").val(), 
			cg_period: $("#cg_period").val(), 
			min_pur: $("#min_pur").val(), 
			rxhl_period: $("#rxhl_period").val(), 
			low_limit: $("#low_limit").val(), 
			secu_limit: $("#secu_limit").val(), 
			high_limit: $("#high_limit").val(), 
			warn_amount: $("#warn_amount").val(), 
			pack_amount: $("#pack_amount").val(), 
			invData: JSON.stringify(invData)
		}
		
		ajaxJsonObjectByUrl("updateSafeMatStoreInvBatch.do?isCheck=false", param, function(responseData){
			if(responseData.state == "true"){
				parent.query();
			}
		});
	}

	function loadDict(){
    	//字典下拉框
		autocomplete("#pack_code", "../../../../queryMatHosPackage.do?isCheck=false", "id", "text", true, true);
		$("#num_exchange").ligerTextBox({width:160, number: true, precision: 2}); 
		autoCompleteByData("#period", [{"text": "年", "id": 1}, {"text": "季", "id": 2}, {"text": "月", "id": 3}, {"text": "天", "id": 4}], "id", "text", true, true);
		$("#period_num").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#ps_period").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#cg_period").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#min_pur").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#rxhl_period").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#low_limit").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#secu_limit").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#high_limit").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#warn_amount").ligerTextBox({width:160, number: true, precision: 2}); 
		$("#pack_amount").ligerTextBox({width:160, number: true, precision: 2}); 
		

        $("#save").ligerButton({ click: save, width: 90 });
        $("#close").ligerButton({ click: thisClose, width: 90 });
	}  
	
	function thisClose(){
		frameElement.dialog.close();
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"> 
		<tr>
			<td align="right" class="l-table-edit-td">包装单位：</td>
			<td align="left" class="l-table-edit-td">
				<input id="pack_code" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">转换量：</td>
			<td align="left" class="l-table-edit-td">
				<input id="num_exchange" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">周期单位：</td>
			<td align="left" class="l-table-edit-td">
				<input id="period" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">安全周期：</td>
			<td align="left" class="l-table-edit-td">
				<input id="period_num" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">采购周期：</td>
			<td align="left" class="l-table-edit-td">
				<input id="cg_period" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">配送周期：</td>
			<td align="left" class="l-table-edit-td">
				<input id="ps_period" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">日消耗量周期：</td>
			<td align="left" class="l-table-edit-td">
				<input id="rxhl_period" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">最小采购批量：</td>
			<td align="left" class="l-table-edit-td">
				<input id="min_pur" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">最低库存：</td>
			<td align="left" class="l-table-edit-td">
				<input id="low_limit" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">安全库存：</td>
			<td align="left" class="l-table-edit-td">
				<input id="secu_limit" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">最高库存：</td>
			<td align="left" class="l-table-edit-td">
				<input id="high_limit" type="text" />
			</td>
			
			<td align="right" class="l-table-edit-td">临近预警量：</td>
			<td align="left" class="l-table-edit-td">
				<input id="warn_amount" type="text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">临近预警包装量：</td>
			<td align="left" class="l-table-edit-td">
				<input id="pack_amount" type="text" />
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button id="save" accesskey="S">修改</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="close" accesskey="C">关闭</button>
			</td>
		</tr>
	</table>
</body>
</html>
