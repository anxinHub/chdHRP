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
		//加载数据
		loadHead(null);
		
		loadHotkeys();
		$("#unit_name").ligerTextBox({width : 160}); 
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'unit_name',value : $("#unit_name").val()});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '收款单位名称',name : 'unit_name',align : 'left'}, 
				
				{display : '银行账号',name : 'card_no',align : 'left'}, 
				
				{display : '开户银行',name : 'bank_name',align : 'left'},
				
				{display : '开户银行所在地',name : 'bank_location',align : 'left'},
			],
			dataAction : 'server',dataType : 'server',usePager : true,isSingleCheck:true,
			url : 'queryBudgUnit.do?isCheck=false',width : '100%',
			height : '100%',checkbox : true,rownumbers : true,pageSize:500,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
					{text : '查询（<u>Q</u>）',id : 'search',click : query,icon : 'search'}, 
					{line : true}, 
					{text : '删除（<u>D</u>）',id : 'delete',click : deleteBudgUnit,icon : 'delete'}, 
					{line : true}
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	//删除
	function deleteBudgUnit(){
		
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
		}
		
		var ParamVo = [];
		$(data).each(function() {
			ParamVo.push(this.group_id + "@" + this.hos_id + "@" + this.unit_id);
		});
		
		$.ligerDialog.confirm('确定删除?',function(yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteBudgUnit.do?isCheck=false", {ParamVo:ParamVo.toString()},function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//父页面回显
    function saveSelectData(){
    	
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ;
        }
        
        var unit_name ='';
        var card_no =''; 
        var bank_name =''; 
        var bank_location =''; 
		
        $(data).each(function (){	
			unit_name = this.unit_name;
			card_no = this.card_no;
			bank_name = this.bank_name;
			bank_location = this.bank_location;
		});
            
		parent.$("#unit_name").val(unit_name);
		parent.$("#card_no").val(card_no);
		parent.$("#bank_name").val(bank_name);
		parent.liger.get("bank_location").setText(bank_location);
       
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', deleteBudgUnit);
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
		/* $("#dept_kind_codes").ligerComboBox({
			url : '../../hpm/queryDeptKindDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160,
			onSelected : function(selectValue) {
				var para = {dept_kind_code : selectValue}
					autocomplete("#dept_id","../../hpm/queryDeptDict.do?isCheck=false", "id","text", true, true, para);
				}
			});
		
		autocomplete("#dept_id", "../../hpm/queryDeptDict.do?isCheck=false", "id","text", true, true);
		*/
		
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		 
		  	<td align="right" class="l-table-edit-td" style="padding-left: 20px; ">单位名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="unit_name" type="text" id="unit_name" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left" ></td>
			
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px; ">科室名称：</td>
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
			<td align="left" ></td> -->
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
