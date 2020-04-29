<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'period_type_code',
			value : liger.get("period_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		grid.options.parms.push({
			name : 'period_code',
			value : liger.get("period_code").getValue()
		});
		grid.options.parms.push({
			name : 'drgs_code',
			value : $("#drgs_code").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {

		var columns = [ {
			display : '期间类型',
			name : 'PERIOD_TYPE_NAME',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '核算年度',
			name : 'ACC_YEAR',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '核算期间',
			name : 'PERIOD_NAME',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '核算方案',
			name : 'SCHEME_NAME',
			align : 'left',
			frozen : true,
			minWidth : 140
		}, {
			display : '核算方案',
			name : 'SCHEME_CODE',
			align : 'left',
			hide : true,
			minWidth : 140
		}, {
			display : '病种名称',
			name : 'DRGS_NAME',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '时程编码',
			name : 'CLP_P_STEP',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '时程名称',
			name : 'CLP_STEP_NAME',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '主要诊疗工作',
			name : 'MAIN_TREAT',
			align : 'left',
			minWidth : 120,
			editor : {
				type : 'text'
			}
		} ];

		var recipe_type_columns = "";
		ajaxJsonObjectByUrl("queryHtcgRecipeType.do?isCheck=false", "", function(responseData) {
			if (responseData.Rows.length > 0) {
				$.each(responseData.Rows, function(v_index, v_data) {
					recipe_type_columns="{ display: '"+v_data.recipe_type_name+"',  align: 'left',"
    				+"	 columns:["
     				+"	          { display: '项目编码', name: 'ITEM_CODE_"+v_data.recipe_type_code+"', align: 'left',minWidth:120},"
    				+"	          { display: '项目名称', name: 'ITEM_NAME"+v_data.recipe_type_code+"', align: 'left',minWidth:120},"
    				+"	          { display: '规格', name: 'MODE_CODE"+v_data.recipe_type_code+"', align: 'left',minWidth:80},"
    				+"	          { display: '数量', name: 'AMOUNT"+v_data.recipe_type_code+"', align: 'left',minWidth:80}"
    				+"]}"; 
					columns.push(eval("(" + recipe_type_columns + ")"));
				});
			}
		}, false);
		
		columns.push(eval("({ display: '主要护理工作', name: 'MAIN_NURSE', align: 'left',minWidth:80,editor:{type:'text'}})"));

		grid = $("#maingrid").ligerGrid({
			columns : columns,
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgDrgsPmodel.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			enabledEdit : true,
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
					text : '生成',
					id : 'init',
					click : init,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '保存',
					id : 'save',
					click : save,
					icon : 'save'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();

	}

	function init() {

		var scheme_code = liger.get("scheme_code").getValue();
		var acc_year = $("#acc_year").val();

		if (scheme_code == '') {
			$.ligerDialog.error('请选择方案');
			return;
		}
		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度');
			return;
		}
		var formPara = {
			scheme_code : scheme_code,
			acc_year : acc_year
		};
		ajaxJsonObjectByUrl("initHtcgDrgsPmodel.do?isCheck=false", formPara,
				function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});

	}
	
	function save() {
		grid.endEdit();
		var formPara = {
			detailData : JSON.stringify(gridManager.getData())
		};
		ajaxJsonObjectByUrl("saveHtcgDrgsPmodel.do?isCheck=false", formPara,
				function(responseData) {
					query();
				});
	}


	function remove() {
		 var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function() {
					ParamVo.push(
							   this.GROUP_ID + "@"
							  +this.HOS_ID + "@"
							  +this.COPY_CODE + "@"
							  +this.PERIOD_TYPE_CODE + "@" 
							  +this.ACC_YEAR + "@"
							  +this.ACC_MONTH + "@"
							  +this.SCHEME_CODE + "@"
							  +this.DRGS_CODE + "@"
							  +this.CLP_P_STEP);//实际代码中temp替换主键
				});
				$.ligerDialog.confirm('确定删除?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteHtcgDrgsPmodel.do", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
	}

	function chargePeriodType() {
		var formPara = {
			period_type_code : liger.get("period_type_code").getValue()
		};
		autocomplete("#period_code","../../base/queryHtcgPeriodDict.do?isCheck=false","id","text", true, true, formPara);
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id","text", true, true);
		autocomplete("#period_type_code","../../base/queryHtcgPeriodTypeDict.do?isCheck=false", "id","text", true, true);

		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#drgs_code").ligerTextBox({
			width : 160
		});
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
         <tr>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left" class="l-table-edit-td"><input name="scheme_code"type="text" id="scheme_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
			<td align="left" class="l-table-edit-td"><input  class="Wdate" style="width:160px;" name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间类型：</td>
			<td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text"  onchange="chargePeriodType()"/></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算期间：</td>
			<td align="left" class="l-table-edit-td"><input
				name="period_code" type="text" id="period_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="drgs_code" type="text" id="drgs_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
