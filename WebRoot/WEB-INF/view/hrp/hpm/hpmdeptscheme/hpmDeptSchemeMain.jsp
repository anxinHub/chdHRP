<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var nature_code = '${nature_code}';
	var app_mod_code = '${app_mod_code}';
	
	if("00" == app_mod_code && "56" == nature_code){
 		nature_code = "'05','06'";
 		app_mod_code = "";
	}else{
		nature_code = "'"+nature_code+"'";
		app_mod_code = "'"+app_mod_code+"','99'"
	}
	
	

	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	//页面初始化
	$(function() {

		autocomplete("#dept_id", "../queryDeptDictByPerm.do?nature_code=" + nature_code + "&isCheck=false", "id", "text", true, true);
		autocomplete("#item_code", "../queryItemAllDict.do?app_mod_code="+app_mod_code+"&isCheck=false", "id", "text", true, true);
		autocomplete("#formula_code", "../queryFormula.do?isCheck=false", "id", "text", true, true);

		loadHead(null);//加载数据
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键

	});
	
	
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		
		var dept_id = liger.get("dept_id").getValue();
		
		if(dept_id){
			grid.options.parms.push({name : 'dept_id',value : dept_id.split(",")[0]});
			grid.options.parms.push({name : 'dept_no',value : dept_id.split(",")[1]});
		}

		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'item_code',value : liger.get("item_code").getValue()});
		grid.options.parms.push({name : 'formula_code',value : liger.get("formula_code").getValue()});
		grid.options.parms.push({name : 'formula_code',value : liger.get("formula_code").getValue()});
		
		if("00" == '${app_mod_code}' && "56" == '${nature_code}'){
			grid.options.parms.push({
				name : "sql",
				value : "AND ad.nature_code in ('05','06') " 
			});
		}else{
			
			grid.options.parms.push({
				name : "sql",
				value : "AND ad.nature_code in (" + nature_code + ")" + "AND ai.app_mod_code in (" + app_mod_code + ")"
			});
		}
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ 
				{display : '医疗单元代码',name : 'dept_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('" 
								+ rowdata.dept_id + "','" 
								+ rowdata.dept_no + "','" 
								+ rowdata.item_code + "');\" >" 
								+ rowdata.dept_code + "</a>";
					}
				}, 
				
				{display : '医疗单元名称',name : 'dept_name',align : 'left'},

				{display : '奖金项目',name : 'item_name',align : 'left'},

				{display : '公式名称',name : 'formula_name',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openFormulaUpdate('" 
								+ rowdata.formula_code 
								+ "');\" >" + rowdata.formula_name + "</a>";
					}
				},

				{display : '计算公式',name : 'formula_method_chs',align : 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			url : 'queryHpmDeptScheme'+'${app_mod_code}'+'${nature_code}'+'.do',
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id, rowdata.dept_no,rowdata.item_code);//实际代码中temp替换主键
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '快速填充（<u>F</u>）',id : 'fast',click : fastDeptScheme,icon : 'back'});
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptScheme, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptScheme,icon:'delete' });
       	obj.push({ line:true });
       	
       	
       /* 	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importIncomeItem,icon:'up' });
       	obj.push({ line:true }); */
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('A',addDeptScheme);
		hotkeys('D',deleteDeptScheme);
		hotkeys('F',fastDeptScheme);
	}
	
	
	//添加
	function addDeptScheme(){
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage'+'${app_mod_code}'+'${nature_code}'+'.do?isCheck=false&app_mod_code='+'${app_mod_code}'+'&nature_code='+'${nature_code}',
			title : '添加',height : $(window).height(),width : $(window).width(),parentframename : window.name,
			modal : true,showToggle : false,showMax : true,showMin : false,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveDeptScheme();
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
	
	//快速填充
	function fastDeptScheme(){
		
		var data = gridManager.getCheckedRows();
		var checkIds = [];
		var codes = [];
		
		$(data).each(function() {
			checkIds.push(this.dept_id + ";"+ this.dept_no + ";" + this.item_code);//实际代码中temp替换主键
		});
		
		codes.push('${app_mod_code}'+","+'${nature_code}');
		$.ligerDialog.open({
			url : 'hpmDeptSchemeFastPage'+'${app_mod_code}'+'${nature_code}'+'.do?isCheck=false&checkIds=' + checkIds+'&codes='+codes,
			title : '快速填充',height : 400,width : 800,modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveDeptScheme();
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
	
	//删除
	function deleteDeptScheme(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id + ";" + this.dept_no + ";" + this.item_code);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmDeptScheme"+'${app_mod_code}'+'${nature_code}'+".do", {checkIds : checkIds.toString()}, 
					function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					}
				);
			}
		});
	}
	
	//修改
	function openFormulaUpdate(obj) {
		//实际代码中&temp替换主键
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmformula/hpmFormulaUpdatePage.do?isCheck=false&formula_code=' + obj,data : {},
			title : '修改',height : $(window).height(),width : $(window).width(),modal : true,
			showToggle : false,showMax : true,showMin : false,isResize : true,parentframename : window.name,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveHpmFormula();
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
	
	//修改
	function openUpdate(obj1, obj2,obj3) {
		//实际代码中&temp替换主键
		parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage'+'${app_mod_code}'+'${nature_code}'+'.do?isCheck=false&dept_id=' + obj1 + '&dept_no=' + obj2 +'&item_code=' + obj3+'&app_mod_code='+'${app_mod_code}'+'&nature_code='+'${nature_code}',
			data : {},
			title : '修改',height : $(window).height(),width : $(window).width(),
			modal : true,showToggle : false,showMax : true,showMin : false,isResize : true,
			parentframename : window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptScheme();
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医疗单元：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目：</td>
			<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
