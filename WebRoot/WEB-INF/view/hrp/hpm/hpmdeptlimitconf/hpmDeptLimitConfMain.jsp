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
	var nature_code = '${nature_code}';
	var app_mod_code = '${app_mod_code}';
	var userUpdateStr;
	
	//页面初始化
	$(function() {
		$("#lower_money").ligerTextBox({width : 160});
		$("#upper_money").ligerTextBox({width : 160});
		
		autocomplete("#dept_kind_code","../../hpm/queryDeptKindDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?nature_code="+nature_code+"&isCheck=false", "id","text", true, true);

		loadHead(null);//加载数据
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;

		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'dept_kind_code',value : liger.get("dept_kind_code").getValue()});
		
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue().split(",")[0]});

		grid.options.parms.push({name : 'lower_money',value : $("#lower_money").val()});

		grid.options.parms.push({name : 'upper_money',value : $("#upper_money").val()});
		
		grid.options.parms.push({name : 'app_mod_code',value :app_mod_code});
		
		grid.options.parms.push({name : 'dept_no',value : liger.get("dept_id").getValue().split(",")[1]});

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科室编码',name : 'dept_id',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
							+ rowdata.dept_id +"','"+rowdata.dept_no+"');\" >"
							+ rowdata.dept_id + "</a>";
					}
				},

				{display : '科室名称',name : 'dept_name',align : 'left'},

				{display : '是否控制',name : 'is_limit',align : 'left',type : 'formatYesNo'},

				{display : '保底线',name : 'lower_money',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.lower_money == "") {
							return "";
						} 
						return rowdata.lower_money;
					}
				},

				{display : '封顶线',name : 'upper_money',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.upper_money == "") {
							return "";
						} 
						return rowdata.upper_money;
					}
				}
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptLimitConf'+app_mod_code+'.do',
			width : '100%',height: '100%',checkbox : true,rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id,rowdata.dept_no);//实际代码中temp替换主键
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
       	
       	obj.push({text : '生成（<u>G</u>）',id : 'create',click : createDeptLimitConf,icon : 'bookpen'});
       	obj.push({ line:true });
       	
       	obj.push({text : '快速填充（<u>F</u>）',id : 'fast',click : fastDeptLimitConf,icon : 'back'});
       	obj.push({ line:true });
       	
       	obj.push({text : '添加（<u>A</u>）',id : 'add',click : addDeptLimitConf,icon : 'add'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : deleteDeptLimitConf,icon : 'delete'});
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importDeptLimitConf,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('G',createDeptLimitConf);
		
		hotkeys('F',fastDeptLimitConf);
		
		hotkeys('A',addDeptLimitConf);
		
		hotkeys('D',deleteDeptLimitConf);
		
		hotkeys('T',downTemplate);
		
		hotkeys('I',importDeptLimitConf);
	}
	
	//添加
	function addDeptLimitConf(){
		$.ligerDialog.open({
			url : 'hpmDeptLimitConfAddPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			title : '添加',height : 300,width : 500,modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveDeptLimitConf();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	//快速填充
	function fastDeptLimitConf(){
		var data = gridManager.getCheckedRows();
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id+"@"+this.dept_no);//实际代码中temp替换主键
		});
		
		$.ligerDialog.open({
			url : 'hpmDeptBalancePercConfFastPage'+app_mod_code+'.do?isCheck=false&checkIds='+ checkIds+'&app_mod_code='+app_mod_code,
			data:{is_check:data},
			title : '快速填充',height : 250,width : 450,modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveDeptBalancePercConf();
					},
					cls : 'l-dialog-btn-highlight'
				}, 
				{text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	//生成
	function createDeptLimitConf(){
		$.ligerDialog.confirm('确定生成?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("createHpmDeptBalancePercConf"+app_mod_code+".do?isCheck=false", {},function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//删除
	function deleteDeptLimitConf(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id+"@"+this.dept_no);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmDeptLimitConf"+app_mod_code+".do", {checkIds : checkIds.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//下载模板
	function downTemplate(){
		location.href = "downTemplateHpmDeptLimitConf.do?isCheck=false";
	}
	
	//导入
	function importDeptLimitConf(){
		parent.$.ligerDialog.open({ url:'hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImportPage'+app_mod_code+'.do?isCheck=false&app_mod_code='+app_mod_code,
			data:{columns : grid.columns, grid : grid}, 
			title:'奖金上下限维护导入',height: 300,width: 450,modal:true,showToggle:false,
			showMax:true,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	//修改
	function openUpdate(obj1,obj2) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'hpmDeptLimitConfUpdatePage'+app_mod_code+'.do?isCheck=false&dept_id='
					+ obj1+'&app_mod_code='+app_mod_code+'&dept_no='+obj2,data : {},
			title : '修改',height : 300,width : 500,modal : true,showToggle : false,
			showMax : false,showMin : false,isResize : true,
			buttons : [ 
				{
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.saveDeptLimitConf();
					},
					cls : 'l-dialog-btn-highlight'
				}, 
				{
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
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
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">保底线：</td>
			<td align="left" class="l-table-edit-td"><input name="lower_money" type="text" id="lower_money" ltype="text" style="width: 160px;"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">封项线：</td>
			<td align="left" class="l-table-edit-td"><input name="upper_money" type="text" id="upper_money" ltype="text" style="width: 160px;"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
