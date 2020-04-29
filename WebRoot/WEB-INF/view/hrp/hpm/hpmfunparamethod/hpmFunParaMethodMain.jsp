<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		$("#para_code").ligerTextBox({
			width : 160
		});
		$("#para_name").ligerTextBox({
			width : 160
		});
	/* 	$("#para_sql").ligerTextBox({
			width : 160
		}); */
		$("#is_stop").ligerTextBox({
			width : 160
		});
		
		toolbar();
		loadHotkeys();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'para_code',
			value : liger.get("para_name").getValue()
		});
		/* grid.options.parms.push({
			name : 'para_sql',
			value : $("#para_sql").val()
		}); */
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			if ($("#para_code").val() != "") {
				return rowdata.para_code.indexOf($("#para_code").val()) > -1;
			}
			if ($("#para_name").val() != "") {
				return rowdata.para_name.indexOf($("#para_name").val()) > -1;
			}
			/* if ($("#para_sql").val() != "") {
				return rowdata.para_sql.indexOf($("#para_sql").val()) > -1;
			} */
			if ($("#is_stop").val() != "") {
				return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;
			}
		};
		return clause;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '参数代码',
								name : 'para_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.para_code + "');\" >"
											+ rowdata.para_code + "</a>";
								}
							}, {
								display : '参数名称',
								name : 'para_name',
								align : 'left'
							/* }, {
								display : '取值方法',
								name : 'para_sql',
								align : 'left' */
							}, {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是";
									}
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHpmFunParaMethod.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.para_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addFunParaMethod, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteFunParaMethod,icon:'delete' });
       	obj.push({ line:true });
       	
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addFunParaMethod);

		hotkeys('D', deleteFunParaMethod);


	}

	function addFunParaMethod() {

		$.ligerDialog.open({
			url : 'hpmFunParaMethodAddPage.do?isCheck=false',
			height : 400,
			width : 670,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmFunParaMethod();
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

	function deleteFunParaMethod() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.para_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHpmFunParaMethod.do", {
						ParamVo : ParamVo
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

	}

	

	function templateFunParaMethod() {

		location.href = "downTemplate.do?isCheck=false";

	}

	function importFunParaMethod() {

		$.ligerDialog.open({
			url : 'hpmFunParaMethodImportPage.do?isCheck=false',
			height : 500,
			width : 800,
			title : '导入',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true
		});

	}

	//打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'绩效函数参数取值',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryHpmFunParaMethod.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }

	function openUpdate(obj) { 
		
		$.ligerDialog.open({
			url : 'hpmFunParaMethodUpdatePage.do?isCheck=false&para_code='
					+ obj,
			data : {},
			height : 400,
			width : 670,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmFunParaMethod();
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
		//字典下拉框
		autocomplete("#para_name", "../queryHpmFunParaMethod.do?isCheck=false","id", "text", true, true);
		
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
			<td style="display: none"><input name="para_code" type="hidden" id="para_code" value="${para_code }" ltype="text" validate="{required:true,maxlength:20}" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">参数名称：</td>
			<td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		<!-- 	<td align="right" class="l-table-edit-td" style="padding-left: 20px;" >取值方法：</td>
			<td align="left" class="l-table-edit-td"><input name="para_sql" type="text" id="para_sql" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		 -->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select id="is_stop" name="is_stop">
					<option value=""></option>
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
