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
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#dept_name").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		loadHotkeys();
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(',')[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue().split(',')[1]
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});

		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_codes").getValue()
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
				{display : '科室编码',name : 'dept_code',align : 'left'}, 
				
				{display : '科室名称',name : 'dept_name',align : 'left'}, 
				
				{display : '科室分类',name : 'a',align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.dept_kind_name;
					}
				},
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : '../../../hrp/hpm/hpmdept/queryHpmDept_DeptKind_DeptNature.do?isCheck=false',width : '100%',
			height : '100%',checkbox : true,rownumbers : true,pageSize:500,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
					{text : '查询（<u>Q</u>）',id : 'search',click : query,icon : 'search'}, 
					{line : true} 
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//父页面回显
    function saveSelectData(){
    	
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return ;
        }
        
        var paramVo_id =[];
        var paramVo_text =[]; 
		
        $(data).each(function (){	
			paramVo_id.push(this.dept_id+";"+this.dept_no);
			paramVo_text.push(this.dept_code+" "+this.dept_name); 
		});
            
		parent.liger.get("dept_id").setValue(paramVo_id.toString().replace(/,/g,'@'));
		parent.liger.get("dept_id").setText(paramVo_text.toString());
       
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}

	
/* 
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&dept_id=" + vo[0]
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3]
		$.ligerDialog.open({
			url : 'hpmDeptUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 500,
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

	} */
	
	
	//字典下拉框
	function loadDict() {
		$("#dept_kind_codes").ligerComboBox({
			url : '../../hpm/queryDeptKindDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(selectValue) {
				var para = {dept_kind_code : selectValue}
					autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false", "id","text", true, true, para);
				}
			});
		
		autocomplete("#dept_id", "../../hpm/queryDeptDictByPerm.do?isCheck=false", "id","text", true, true);
		
		$("#is_stop").ligerComboBox({width : 160});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		 
		  	<td align="right" class="l-table-edit-td" style="padding-left: 20px; ">科室分类：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_kind_codes" type="text" id="dept_kind_codes" ltype="text" validate="{required:true,maxlength:20}" />
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
	<div id="maingrid"></div>
</body>
</html>
