<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var grid;
	
	var gridManager = null;
	
	var stop_state = {
		Rows : [ 
			{"id" : "-1","text" : "请选择"},
			{"id" : "0","text" : "否"},
			{"id" : "1","text" : "是"}
		],
		Total : 3
	};

	
	
	//初始化
	$(function() {

		loadDict();//加载下拉框
		loadHead(null);//加载数据

		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({
			name : 'emp_name',
			value : $('#emp_name').val()
		});
		
		grid.options.parms.push({
			name : 'duty_code',
			value : liger.get("duty_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '职工编码',name : 'emp_code',align : 'left'}, 
				
				{display : '职工名称',name : 'emp_name',align : 'left'}, 
				
				{display : '科室名称',name : 'dept_name',align : 'left'}, 
				
				{display : '职务名称',name : 'duty_name',align : 'left'}, 
				
				{display : '是否停用',name : 'is_stop',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_stop == 1) {
							return "是";
						} else {
							return "否";
						}
					}
				}
			],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHpmSysEmp.do',width : '100%',
			height : '100%',checkbox : true,rownumbers : true,delayLoad:true,selectRowButtonOnly : true,heightDiff: -35
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
	}
	
	
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		
	}
	
	//字典下拉框
	function loadDict() {
		
		//autoCompleteByData("#is_stop",stop_state.Rows,"id","text",true,true);
		autocomplete("#duty_code", "../queryEmpDutyDict.do?isCheck=false", "id", "text", true, true);
		$("#emp_name").ligerTextBox({width : 160});
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>

</body>
</html>
