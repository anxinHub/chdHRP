<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<%-- <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/> --%>
<script type="text/javascript">



	var grid; 
	var gridManager = null;
	var userUpdateStr;
	
	
	//页面初始化
	$(function() {
		
		loadDict();//加载下拉框
		loadHead(null);//加载数据
		$("#dept_id").ligerTextBox({width : 160});
		$("#dept_name").ligerTextBox({width : 160});
		$("#is_stop").ligerTextBox({width : 160});
		
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
			value : liger.get("dept_id").getValue()
		});
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

			if ($("#dept_id").val() != "") {
				return rowdata.dept_id.indexOf($("#dept_id").val()) > -1;
			}
			 
			if ($("#is_stop").val() != "") {
				return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;
			}
		};
		return clause;
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科室编码',name : 'dept_code',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
								+ rowdata.dept_id + "|"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "')>"
								+ rowdata.dept_code + "</a>";
					}
				},
				
				{display : '科室名称',name : 'dept_name',align : 'left'},
				
				{display : '是否停用',name : 'is_stop',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_stop == 0) {
							return "否";
						} else {
							return "是";
						}
					}
				}, 
				
				{display : '是否参与人均奖',name : 'is_avg',align : 'left',render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_avg == 0) {
							return "否";
						} else {
							return "是";
						}
					}
				}, 
				
				{display : '科室描述',name : 'dept_note',align : 'left'}
			],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryPrmDeptHip.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,selectRowButtonOnly : true,//heightDiff: -10,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id +"|" + rowdata.group_id
						+ "|" + rowdata.hos_id + "|"
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDept, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDept,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: templateDept,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importDept,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addDept);

		hotkeys('D', deleteDept);

		hotkeys('T', templateDept);

		hotkeys('I', importDept);

		//hotkeys('P', printDept);

	}
	
	
	//添加
	function addDept() {
		$.ligerDialog.open({
			url : 'prmdepthipAddPage.do?isCheck=false',
			height : 450,width :500,title : '添加',modal : true,showToggle : false,
			showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmDept();
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
	function deleteDept() {

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
				this.dept_id
			)
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deletePrmDeptHip.do", {ParamVo : ParamVo}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}

	
	//下载导入模板
	function templateDept() {
		location.href = "downTemplateprmdepthip.do?isCheck=false";
	}
	
	
	//导入
	function importDept() {
		parent.$.ligerDialog.open({ 
			url:'hrp/prm/prmdepthip/prmDeptHipImportPage.do?isCheck=false',data:{columns : grid.columns, grid : grid}, 
			height: 300,width: 450,title:'其它平台科室导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	
	
	//修改页
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&dept_id=" + vo[0]
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3]
		
		$.ligerDialog.open({
			url : 'prmDeptHipUpdatePage.do?isCheck=false&' + parm,data : {},
			height : 500,width : 500,title : '修改',modal : true,showToggle : false,
			showMax : false,showMin : false,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmDept();
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
	
	
	//加载字典
	function loadDict() {
		autocomplete("#dept_id", "../../prm/queryPrmDeptHip1.do?isCheck=false", "id","text", true, true);
		
		$("#is_stop").ligerComboBox({width : 160});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		  <td style="display:none">
			<input name="dept_id" type="hidden" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_id" type="hidden" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_id" type="hidden" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_id" type="hidden" id="is_avg" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_id" type="hidden" id="dept_note" ltype="text" validate="{required:true,maxlength:20}" />
		  </td>
		  	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px; ">科室名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left" ></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td" >
				<select id="is_stop" name="is_stop">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select>

			</td>
			<td align="left" ></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
