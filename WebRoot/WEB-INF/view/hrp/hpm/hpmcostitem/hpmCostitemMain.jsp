<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var app_mod_code = '${app_mod_code}';
	
	
	$(function() {
		$("#cost_item_code").ligerTextBox({width : 160});
		$("#cost_iitem_name").ligerTextBox({width : 160});
		$("#is_stop").ligerComboBox({width : 160});
		
		loadDict();//加载字典
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
		
		loadHead(null);//加载数据
	});
	
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'cost_item_code',
			value : $("#cost_item_code").val()
		});
		grid.options.parms.push({
			name : 'cost_iitem_name',
			value : $("#cost_iitem_name").val()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});
		grid.options.parms.push({
			name : 'app_mod_code',
			value :app_mod_code
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{ display : '支出项目编码',name : 'cost_item_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
							+ rowdata.cost_item_code
							+ "');\" >"
							+ rowdata.cost_item_code + "</a>";
					}
				}, 
				
				{ display : '支出项目名称',name : 'cost_iitem_name',align : 'left'}, 
				
				{ display : '支出项目分类',name : 'cost_type_name',align : 'left'}, 
				
				{ display : '数据来源',name : 'data_source',align : 'left'}, 
				
				{ display : '是否停用',name : 'is_stop',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_stop == 1) {
							return "是";
						} else {
							return "否";
						}
						return "";
					}
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmCostitem'+app_mod_code+'.do',
			width : '100%',height : '100%',checkbox : true,
			rownumbers : true,delayLoad:true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : 
				function(rowdata, rowindex, value) {
					openUpdate(rowdata.cost_item_code);//实际代码中temp替换主键
				}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
	}
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addCostItem, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteCostItem,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importCostItem,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('A',addCostItem);
		hotkeys('D',deleteCostItem);
		hotkeys('T',downTemplate);
		hotkeys('I',importCostItem);
	}
	
	//添加
	function addCostItem(){
		
		$.ligerDialog.open({
			url : 'hpmCostitemAddPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			title : '添加',height : 350,width : 500,modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostitem();
				},cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	//删除
	function deleteCostItem(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.cost_item_code);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmCostitem"+app_mod_code+".do", {checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHpmCostitem.do?isCheck=false";
	}
	
	//导入
	function importCostItem(){
		parent.$.ligerDialog.open({ 
			url:'hrp/hpm/hpmcostitem/hpmCostitemImportPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			data:{columns : grid.columns, grid : grid}, 
			height: 300,width: 450,title:'支出项目导入',
			modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//修改
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmCostitemUpdatePage'+app_mod_code+'.do?isCheck=false&cost_item_code=' + obj+'&app_mod_code='+app_mod_code,
			data : {},height : 350,width : 500,title : '修改',modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostitem();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	
	function loadDict() {
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目编码：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text"
				validate="{required:true,maxlength:20}" style="width: 160px;" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目名称：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_iitem_name" type="text" id="cost_iitem_name" ltype="text"
				validate="{required:true,maxlength:20}" style="width: 160px;" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop" style="width: 160px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
