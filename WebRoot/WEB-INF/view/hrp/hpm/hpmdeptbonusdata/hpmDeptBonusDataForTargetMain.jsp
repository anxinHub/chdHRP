<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">


	var nature_code = '${nature_code}';

	var app_mod_code = '${app_mod_code}';

	if ("00" == app_mod_code && "56" == nature_code) {

		nature_code = "'05','06'";

		app_mod_code = "";

	} else {

		nature_code = "'" + nature_code + "'";

		app_mod_code = "'" + app_mod_code + "','99'"

	}

	var grid;

	var gridManager = null;

	$(function() {
		
		$("#acct_yearm").ligerTextBox({width : 160});autodate("#acct_yearm", "yyyymm");

		loadDict();

		loadHead(null);

	});
	//查询
	function query() {//根据表字段进行添加查询条件

		grid_setColumns();

		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({
			name : 'nature_code',
			value : nature_code
		});
		
		grid.options.parms.push({
			name : 'app_mod_code ',
			value : app_mod_code 
		});

		grid.options.parms.push({
			name : 'acct_yearm',
			value : $("#acct_yearm").val()
		});

		grid.options.parms.push({
			name : 'item_code',
			value : liger.get("item_code").getValue()
		});

		var dept_id = liger.get("dept_id").getValue();

		if (dept_id) {

			grid.options.parms.push({
				name : 'dept_id',
				value : dept_id.split(",")[0]
			});

			grid.options.parms.push({
				name : 'dept_no',
				value : dept_id.split(",")[1]
			});
		}

		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});

		grid.loadData(grid.where);

	}

	function loadHead() {//获取查询条件的数值

		grid = $("#maingrid").ligerGrid({
			columns : [],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHpmDeptBonusDataForTarget'+'${app_mod_code}'+'${nature_code}'+'.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			enabledEdit : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '导出Excel',
					id : 'exportExcel',
					click : exportExcel,
					icon : 'outbox'
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function grid_setColumns() {

		var acct_yearm = $("#acct_yearm").val();

		var dept_id = liger.get("dept_id").getValue();

		var item_code = liger.get("item_code").getValue();

		var formPara = {

			acct_yearm : acct_yearm,

			dept_id : dept_id,

			item_code : item_code,

			nature_code : nature_code,
			
			app_mod_code : app_mod_code,

		};

		ajaxJsonObjectByUrl("queryHpmDeptBonusDataForTargetGrid"+'${app_mod_code}'+'${nature_code}'+".do?isCheck=false", formPara,

		function(responseData) {

			if (responseData != null) {

				grid.set('columns', responseData);

				grid.reRender();
			}

		});

	}

	function loadDict() {//字典下拉框

		autocomplete("#item_code", "../queryItemAllDict.do?app_mod_code=" + app_mod_code + "&isCheck=false", "id", "text", true, true, '', true);

		autocomplete("#dept_kind_code", "../queryDeptKindDict.do?isCheck=false", "id", "text", true, true);
		
		var para = {
				
    			acct_yearm:$("#acct_yearm").val(),
    			
    			nature_code:nature_code
		}
		
		autocomplete("#dept_id","../queryDeptDictTime.do?nature_code=" + nature_code + "&isCheck=false","id","text",true,true,para);
	}

	//导出数据
	function exportExcel() {

		var acct_yearm = $("#acct_yearm").val();

		var dept_id = liger.get("dept_id").getValue() ? liger.get("dept_id").getValue() : 'null';

		if (acct_yearm == '') {

			$.ligerDialog.error('请选择核算年月');

			return false;
		}

		var paras = acct_yearm + "@" + dept_id + "@" + nature_code;

		location.href = "exportTargetDataIncomeExcel.do?paras=" + paras;

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目：</td>
			<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
			
		</tr>

	</table>

	<div id="maingrid"></div>

</body>
</html>
