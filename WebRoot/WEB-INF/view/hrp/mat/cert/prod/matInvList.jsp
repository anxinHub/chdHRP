<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		$("#inv_text").ligerTextBox({width: 160});
		autoCompleteByData("#use_state", [{id: 1, text: "在用"}, {id: 0, text: "停用"}], "id", "text", true, true);
		loadGrid();
		query();
	})
	
	//加载证件材料表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [ {
				display: '材料编码', name: 'inv_code', align: 'left', width: 140
			},{
				display: '材料名称', name: 'inv_name', align: 'left', width: 180, 
			},{
				display: '规格型号', name: 'inv_model', align: 'left', width: 160
			},{
				display: '单位', name: 'unit_name', align: 'left', width: 90
			},{
				display: '单价', name: 'plan_price', align: 'left', width: 100
			},{
				display: '在用状态', name: 'use_state', align: 'left', width: 70
			}],
			dataAction: 'server', dataType: 'server', usePager: true, width: '100%', height: '100%', 
			url:'queryMatInvList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, //heightDiff: 30, 
			toolbar: { items: [{
				text: '查询', id: 'query', icon: 'search', click: query
			},{ line:true },{
				text: '选择', id: 'add', icon: 'save', click: add
			},{ line:true },{
				text: '关闭', id: 'close', icon: 'close', click: thisClose
			}] }
		});
	}
	
	//查询
	function query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_id', value: parentData.cert_id});
		grid.options.parms.push({name: 'inv_text', value: $("#inv_text").val()});
		grid.options.parms.push({name: 'use_state', value: liger.get("use_state").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//保存证件材料
	function add(){
		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请勾选材料！');
			return false;
		}
		
		parentFrameUse().grid.addRows(data);
		thisClose();
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout"  width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				材料信息：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="inv_text" />
			</td>
			<td align="right" class="l-table-edit-td">
				在用状态：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="use_state" />
			</td>
		</tr>
	</table>
	<div id="grid"></div>
</body>
</html>