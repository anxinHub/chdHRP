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
		loadDict()//加载下拉框
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
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'cost_item_code',
			value : liger.get("cost_item_code").getValue()
		});
		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'app_mod_code',
			value :app_mod_code
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split(",")[1]
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科室名称',name : 'dept_name',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
								+ rowdata.dept_id + "','"
								+ rowdata.cost_item_code
								+ "');\" >" + rowdata.dept_name
							+ "</a>";
					}
				}, 
				
				{display : '支出项目编码',name : 'cost_item_code',align : 'left'}, 
				
				{display : '支出项目名称',name : 'cost_iitem_name',align : 'left'}, 
				
				{display : '是否参与核算',name : 'is_acc',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_acc == 1) {
							return "是";
						} else {
							return "否";
						}
						return "";
					}
				}, 
				
				{display : '是否直接成本',name : 'is_prim_cost',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_prim_cost == 1) {
							return "是";
						} else {
							return "否";
						}
						return "";
					}
				}, 
				
				{display : '是否间接成本',name : 'is_calc_cost',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_calc_cost == 1) {
							return "是";
						} else if (rowdata.is_calc_cost == 0) {
							return "否";
						}
						return "";
					}
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,delayLoad:true,url : 'queryHpmCostitemConf'+app_mod_code+'.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id, rowdata.cost_item_code);//实际代码中temp替换主键
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
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'create', click: create,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '复制（<u>C</u>）', id:'copy', click: copy,icon:'copy' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addCostItemConf, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteCostItemConf,icon:'delete' });
       	obj.push({ line:true });
       	
        obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importCostItemConf,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',create);
		hotkeys('C',copy);
		hotkeys('A',addCostItemConf);
		hotkeys('D',deleteCostItemConf);
		hotkeys('T',downTemplate);
		hotkeys('I',importCostItemConf);
	}
	
	//添加
	function addCostItemConf(){
		$.ligerDialog.open({
			url : 'hpmCostitemConfAddPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			height : 400,width : 500,title : '添加',modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostitemConf();
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
	
	//复制
	function copy(){
		$.ligerDialog.open({
			url : 'hpmCostitemConfCopy'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			title : '添加',height : 300,width : 500,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveIncomeitemConf();
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
	
	//生成
	function create(){
		var dept_kind_code = liger.get("dept_kind_code").getValue()? liger.get("dept_kind_code").getValue():'null'
			
		$.ligerDialog.confirm('确定生成?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("hpmCreateHpmCostitemConf"+app_mod_code+".do?isCheck=false", {dept_kind_code:dept_kind_code},function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//删除
	function deleteCostItemConf(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id+"@"+this.dept_no + ";"+ this.cost_item_code);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmCostitemConf"+app_mod_code+".do", {checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHpmCostitemConf.do?isCheck=false";
	}
	
	//导入
	function importCostItemConf(){
		parent.$.ligerDialog.open({ 
			url:'hrp/hpm/hpmcostitemconf/hpmCostitemConfImportPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			data:{columns : grid.columns, grid : grid}, 
			title:'支出项目配置导入',height: 300,width: 450,modal:true,
			showToggle:false,showMax:true,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//修改
	function openUpdate(obj, obj2) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmCostitemConfUpdatePage'+app_mod_code+'.do?isCheck=false&dept_id=' + obj + '&cost_item_code=' + obj2+'&app_mod_code='+app_mod_code,
			data : {},height : 400,width : 500,title : '修改',modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostitemConf();
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
	
	//字典下拉框
	function loadDict() {
		autocomplete("#dept_kind_code","../../hpm/queryDeptKindDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#dept_id", "../../hpm/queryDeptDictByPerm.do?isCheck=false","id", "text", true, true);
		autocomplete("#cost_item_code","../../hpm/queryAphiCostItem.do?isCheck=false", "id", "text", true,true);
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
