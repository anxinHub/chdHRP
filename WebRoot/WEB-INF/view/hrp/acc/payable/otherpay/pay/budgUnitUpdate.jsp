<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	
	$(function() {
		$("#unit_name").ligerTextBox({width: 160});
		$("#card_no").ligerTextBox({width: 240});
		
		loadHead();
	});
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns: [
				{ display: '收款单位/个人', name: 'unit_name', align: 'left', width: '200', editor: {type: 'text'} },
				{ display: '开户行', name: 'bank_name', align: 'left', width: '200', editor: {type: 'text'} },
				{ display: '开户行所在地', name: 'bank_location', align: 'left', width: '200', editor: {type: 'text'} },
				{ display: '银行账号', name: 'card_no', align: 'left', width: '280', editor: {type: 'text'} }
			],
			dataAction: 'server',
			dataType: 'server',
			usePager: true,
			url: "../apply/queryBudgUnit.do?isCheck=false",
			width: '100%',
			height: '95%',
			checkbox: true,
			enabledEdit: true,
			isAddRow: false,
			rownumbers: true,
			toolbar: {
				items: [ 
					{text: '查询', id: 'query', click: query, icon: 'search'},
					{line: true}, 
					{text: '提交', id: 'save', click: save, icon: 'save'}, 
					{line: true}
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
		
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name: 'unit_name', value: $("#unit_name").val()});
		grid.options.parms.push({name: 'card_no', value: $("#card_no").val()});
 		grid.loadData(grid.where);
	}
	
	// 保存
	function save(){
		var rowData = gridManager.getSelectedRows();
		
		if(rowData.length == 0){
			$.ligerDialog.warn('请选择行！');
			return;
		}
		var formPara;
		var flag = false;
		var reg = /^[0-9]*$/;
		$.each(rowData, function(i, row){
			if(!reg.test(row.card_no)){
				flag = true;
				$.ligerDialog.warn('银行账号只能是数字！');
				return;
			}
  		});
		
		if(flag){
			return;
		}else{
			formPara = {
				paramVo : JSON.stringify(rowData)
			};
			ajaxJsonObjectByUrl("updateBudgUnit.do?isCheck=false", formPara, function(responseData) {
				if (responseData.state == "true") {
					
				}
			});
		}
	}
		
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收款单位/个人：</td>
			<td align="left" class="l-table-edit-td"><input name="unit_name" type="text" id="unit_name"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">银行账号：</td>
			<td align="left" class="l-table-edit-td"><input name="card_no" type="text" id="card_no"/></td>
			<td align="left"></td>
		</tr>	
	</table>
	<div id="maingrid"></div>
</body>
</html>
