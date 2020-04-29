<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!-- 绩效计算公式 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">


	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	//页面初始化
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
		grid.options.parms.push({name : 'formula_code',value : liger.get("formula_name").getValue()}); 
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '公式编码',name : 'formula_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
								+ rowdata.formula_code + "|"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "')>"
								+ rowdata.formula_code + "</a>";
					},width: '12%'
				}, 
				
				{display : '公式名称',name : 'formula_name',align : 'left',width: '20%'}, 
				
				{display : '计算公式(中文）',name : 'formula_method_chs',align : 'left',width: '55%'},
				
				{display : '是否停用',name : 'is_stop',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_stop == 0) {
							return "否";
						} else {
							return "是";
						}
					},width: '12%'
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryPrmFormula.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.formula_code + "|"
						+ rowdata.group_id + "|" + rowdata.hos_id + "|"
						+ rowdata.copy_code);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '添加（<u>A</u>）',id : 'add',click : addFormula,icon : 'add'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : deleteFormula,icon : 'delete'});
       	obj.push({ line:true });
       	
       	obj.push({text : '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true });
       	
       	obj.push({text : '下载导入模板（<u>T</u>）',id : 'downTemplate',click : templateFormula,icon : 'down'});
       	obj.push({ line:true });
       	
       	obj.push({text : '导入（<u>I</u>）',id : 'import',click : importFormula,icon : 'up'});
       	obj.push({ line:true });
       	
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addFormula);

		hotkeys('D', deleteFormula);

		hotkeys('T', templateFormula);

		hotkeys('I', importFormula);

		hotkeys('P', print);

	}
	
	
	//添加
	function addFormula() {
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmformula/prmFormulaAddPage.do?isCheck=false',
			height: $(window).height(),width: $(window).width(),title : '添加',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.savePrmFormula();
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
	function deleteFormula() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		}
		
		var ParamVo = [];
		$(data).each(function() {
			ParamVo.push(
				this.group_id + "@" + 
				this.hos_id + "@" + 
				this.copy_code + "@" + 
				this.formula_code
			)
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deletePrmFormula.do", {ParamVo : ParamVo}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//导入
	function importFormula() {
		$.ligerDialog.open({
			url : 'prmFormulaImportPage.do?isCheck=false',
			height : 500,width : 800,title : '导入',showMin : true,
			modal : true,showToggle : false,showMax : false,isResize : true
		});
	}
	
	
	//下载导入 模板
	function templateFormula() {
		location.href = "downTemplate.do?isCheck=false";
	}

	
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "计算公式维护",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmFormulaService",
				method_name: "queryPrmFormulaPrint",
				bean_name: "prmFormulaService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	//修改页面跳转
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = 
			"&formula_code=" + vo[0] + 
			"&group_id" + vo[1] + 
			"&hos_id" + vo[2] + 
			"&copy_code" + vo[3]

		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmformula/prmFormulaUpdatePage.do?isCheck=false&' + parm,data : {},
			height: $(window).height(),width: $(window).width(),title : '修改',modal: true,showToggle: false, 
			showMax: true, showMin: false, isResize: true,parentframename: window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.savePrmFormula();
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
		autocomplete("#formula_name", "../queryPrmFormula.do?isCheck=false","id", "text", true, true);
		
		$("#formula_code").ligerTextBox({
			width : 160
		});
		$("#formula_name").ligerTextBox({
			width : 160
		});
		$("#formula_method_chs").ligerTextBox({
			width : 160
		});
		$("#formula_method_eng").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr> 
		<td style="display:none;">
			<input name="formula_code" type="hidden" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" />  
		  </td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_name" type="text" id="formula_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

		</tr>

	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
