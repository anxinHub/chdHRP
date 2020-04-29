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
	
	
	//页面初始化
	$(function() {
		$("#work_item_code").ligerTextBox({width : 160});
		$("#work_item_name").ligerTextBox({width : 160});
		$("#is_stop").ligerComboBox({width : 160});
		loadHead(null);//加载数据
		toolbar();//工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;

		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'work_item_code',value : $("#work_item_code").val()});
		grid.options.parms.push({name : 'work_item_name',value : $("#work_item_name").val()});
		grid.options.parms.push({name : 'is_stop',value : $("#is_stop").val()});

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '工作量指标编码',name : 'work_item_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
							+ rowdata.work_item_code
							+ "');\" >"
							+ rowdata.work_item_code + "</a>";
					}
				}, 
				
				{display : '工作量指标名称',name : 'work_item_name',align : 'left'},

				{display : '数据来源',name : 'data_source',align : 'left'},

				{display : '是否停用',name : 'is_stop',align : 'left',type : 'formatYesNo',render : 
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
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHpmWorkitem.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : 
				function(rowdata, rowindex, value) {
					openUpdate(rowdata.work_item_code);//实际代码中temp替换主键
				}
		});

		gridManager = $("#maingrid").ligerGetGridManager();

		formatYesNo();
	}
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addWormItem, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteWorkItem,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importWorkItem,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('A',addWormItem);
		
		hotkeys('D',deleteWorkItem);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importWorkItem);
	}
	
	//添加
	function addWormItem(){
		$.ligerDialog.open({
			url : 'hpmWorkitemAddPage.do?isCheck=false',
			title : '添加',height : 250,width : 400,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveWorkitem();
					},cls : 'l-dialog-btn-highlight'
				}, 
				{text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	
	//删除
	function deleteWorkItem(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		}
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.work_item_code);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmWorkitem.do", {checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHpmWorkitem.do?isCheck=false";
	}
	
	//导入
	function importWorkItem(){
		parent.$.ligerDialog.open({ 
			url:'hrp/hpm/hpmworkitem/hpmWorkitemImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			title:'工作量指标维护导入',height: 300,width: 450,modal:true,
   			showToggle:false,showMax:true,showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//修改页
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmWorkitemUpdatePage.do?isCheck=false&work_item_code=' + obj,data : {},
			title : '修改',height : 250,width : 400,modal : true,showToggle : false,
			showMax : false,showMin : false,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveWorkitem();
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
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标名称：</td>
			<td align="left" class="l-table-edit-td"><input name="work_item_name" type="text" id="work_item_name" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
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
