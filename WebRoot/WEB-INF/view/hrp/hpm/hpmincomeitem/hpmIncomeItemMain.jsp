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
	
	//初始化
	$(function() {
		$("#is_stop").ligerComboBox({
			width : 160
		});
		loadHead(null);//加载数据
		loadDict(null);//加载下拉框
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {

		grid.options.parms = [];

		grid.options.newPage = 1;

		grid.options.parms.push({
			name : 'income_item_code',
			value : liger.get("income_item_code").getValue()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});
		grid.options.parms.push({
			name : 'app_mod_code',
			value :app_mod_code
		});
		
		grid.loadData(grid.where);

	}
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '收入项目编码',
				name : 'income_item_code',
				width : '20%',
				align : 'left',
				render : 
					function(rowdata, rowindex,value) {
						return "<a href='#' onclick=\"openUpdate('"
							+ rowdata.income_item_code
							+ "');\" >"
							+ rowdata.income_item_code
							+ "</a>";
					}
				}, 
				
				{
					display : '收入项目名称',
					name : 'income_item_name',
					width : '40%',
					align : 'left'
				}, {
					display : '数据来源',
					name : 'data_source',
					width : '20%',
					align : 'left'
				}, 
				{
					display : '是否停用',
					name : 'is_stop',
					width : '20%',
					align : 'left',
					type : 'formatTrueFalse',
					render : 
						function(rowdata, rowindex,value) {
							if (rowdata.is_stop == 1) {
								return "是";
							} else {
								return "否";
							}
							return "";
						}
									} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHpmIncomeItem'+app_mod_code+'.do',
					width : '100%',
					height: '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad:true,
					selectRowButtonOnly : true,//heightDiff: -10,
					onDblClickRow : function(rowdata, rowindex, value) {

						openUpdate(rowdata.income_item_code);

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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addIncomeItem, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteIncomeItem,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importIncomeItem,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addIncomeItem);
		
		hotkeys('D',deleteIncomeItem);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importIncomeItem);
	}
	
	//添加
	function addIncomeItem(){
		
		$.ligerDialog.open({
			url : 'addHpmIncomeItem'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			height : 250,width : 500,title : '添加',modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveIncomeItem();
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
	
	//删除
	function deleteIncomeItem(){
		var data = gridManager.getCheckedRows();

		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 

		var checkIds = [];
	
		$(data).each(function() {
			checkIds.push(this.income_item_code);
		});

		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmIncomeItem"+app_mod_code+".do", {checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHpmIncomeItem.do?isCheck=false";
	}
	
	//导入
	function importIncomeItem(){
		parent.$.ligerDialog.open({ url:'hrp/hpm/hpmincomeitem/hpmIncomeItemImportPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			data:{columns : grid.columns, grid : grid}, 
			height: 300,width: 450,title:'收入项目维护导入',modal:true,
			showToggle:false,showMax:true,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	
	//修改页面跳转
	function openUpdate(obj) {

		$.ligerDialog.open({
			url : 'hpmIncomeItemUpdatePage'+app_mod_code+'.do?isCheck=false&income_item_code='+ obj+'&app_mod_code='+app_mod_code,
			data : {},
			height : 250,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveIncomeItem();
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
		autocomplete("#income_item_code","../../hpm/queryAphiIncomeItem.do?isCheck=false", "id", "text",true, true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td">收入项目：</td>
			<td align="left" class="l-table-edit-td"><input type="text" id="income_item_code" style="width: 160px;" /></td>
			<!-- <td align="left"></td> -->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop" style="width: 160px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
