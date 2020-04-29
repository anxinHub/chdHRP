<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
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
		grid.options.parms.push({name : 'method_code',value : liger.get("method_code").getValue()});
		grid.options.parms.push({name : 'target_code',value : liger.get("target_name").getValue()});
		grid.options.parms.push({name : 'nature_code',value : liger.get("nature_code").getValue()});
		grid.options.parms.push({name : 'is_stop',value : $("#is_stop").val()});

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '指标编码',name : 'target_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
								+ rowdata.target_code + "|"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "')>"
								+ rowdata.target_code + "</a>";
					}
				}, 
				
				{display : '指标名称',name : 'target_name',align : 'left'}, 
				
				{display : '指标性质',name : 'nature_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return rowdata.nature_name;
					}
				}, 
				
				{display : '取值方法名称',name : 'method_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return rowdata.method_name;
					}
				}, 
				
				{display : '是否停用',name : 'is_stop',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_stop == 0) {
							return "否";
						} else {
							return "是";
						}
					}
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryPrmTargetMethodNature.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.target_code + "|" + rowdata.group_id
						+ "|" + rowdata.hos_id + "|"
						+ rowdata.copy_code + "|"

				);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '添加（<u>A</u>）',id : 'add',click : addTargetMethod,icon : 'add'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : deleteTargetMethod,icon : 'delete'});
       	obj.push({ line:true });
       	
       	obj.push({text : '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addTargetMethod);

		hotkeys('D', deleteTargetMethod);

		hotkeys('L', templateTargetMethod);

		hotkeys('I', importTargetMethod);
		
		hotkeys('P', print);

	}
	
	
	//添加
	function addTargetMethod() {
		
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmtargetmethod/prmTargetMethodAddPage.do?isCheck=false',
			title : '计算方式设定',height: '500',width: '800',modal: true,showToggle: false, 
			showMax: false, showMin: false, isResize: true,parentframename: window.name,
		    buttons : [ 
			    {
					text : '确定',
					onclick : function(item, dialog) {
						dialog.frame.savePrmTargetMethod();
						query();
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
	
	
	//删除
	function deleteTargetMethod() {

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
				this.target_code
			)
		});

		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deletePrmTargetMethod.do", {ParamVo : ParamVo}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//导入
	function importTargetMethod() {
		$.ligerDialog.open({
			url : 'prmTargetMethodImportPage.do?isCheck=false',
			title : '导入',height : 500,width : 800,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true
		});
	}
	
	
	//下载导入模板
	function templateTargetMethod() {
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
				title: "取值方法维护",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmTargetMethodService",
				method_name: "queryPrmTargetMethodNaturePrint",
				bean_name: "prmTargetMethodService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&target_code=" + vo[0] + 
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3]
		
		
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmtargetmethod/prmTargetMethodUpdatePage.do?isCheck=false&'+ parm,
			height: '500',
			width: '800',
			title : '计算方式设定',
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,
		    buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.savePrmTargetMethod();
					query();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		})
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#target_name", "../quertPrmTargetDict.do?isCheck=false","id", "text", true, true);
		autocomplete("#method_code","../queryPrmTargetMethodPara.do?isCheck=false", "id", "text",true, true);
		autocomplete("#nature_code","../queryPrmTargetNature.do?isCheck=false", "id", "text", true,true);
		
		$("#target_code").ligerTextBox({
			width : 160
		});
		$("#target_name").ligerTextBox({
			width : 160
		});
		$("#method_code").ligerTextBox({
			width : 160
		});
		$("#method_name").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		
		$("#is_stop").ligerComboBox({
			width : 160
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="left" class="l-table-edit-td" style="display:none">
				<input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="target_name" type="text" id="target_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标性质</td>
			<td align="left" class="l-table-edit-td">
				<input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">取值方法名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="method_code" type="text" id="method_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用:</td>
			<td align="left" class="l-table-edit-td"><select id="is_stop" name="is_stop">
					<option value=""></option>
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
