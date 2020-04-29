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
	var checkList;
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
			name : 'scheme_code',
			value : $("#scheme_code").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '方案编码',
				name : 'scheme_code',
				align : 'left',
				minWidth : 120,
				frozen : true
			}, {
				display : '方案名称',
				name : 'scheme_name',
				align : 'left',
				minWidth : 120,
				frozen : true
			}, {
				display : '病人病例入组',
				name : 'mr_group',
				render : checkboxRender,
				minWidth : 120,
			}, {
				display : '病人病例样本抽取',
				name : 'mr_sample',
				render : checkboxRender,
				minWidth : 120,
			}, {
				display : '临床路径时程划分',
				name : 'clp_step',
				render : checkboxRender,
				minWidth : 120,
			}, {
				display : '同类医嘱项目合并(病人CLP)',
				name : 'recipe_p_merge',
				render : checkboxRender,
				minWidth : 160,
			}, {
				display : '同类医嘱项目合并(医师CLP)',
				name : 'recipe_d_merge',
				render : checkboxRender,
				minWidth : 160,
			}, {
				display : '医嘱项目准入(病人CLP)',
				name : 'recipe_p_group',
				render : checkboxRender,
				minWidth : 160,
			}, {
				display : '医嘱项目准入(医师CLP)',
				name : 'recipe_d_group',
				render : checkboxRender,
				minWidth : 160,
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemePathConf.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			enabledEdit : true,
			delayLoad : true,
			selectRowButtonOnly : true,
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
					id : 'add',
					click : save,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				},

				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function checkboxRender(rowdata, rowindex, value, column) {
		var iconHtml = '<div class="chk-icon';
		if (value)
			iconHtml += " chk-icon-selected";
		iconHtml += '"';
		iconHtml += ' rowid = "' + rowdata['__id'] + '"';
		iconHtml += ' gridid = "' + this.id + '"';
		iconHtml += ' columnname = "' + column.name + '"';
		iconHtml += '></div>';

		return iconHtml;

	}

	$(document).on('click', '.chk-icon', function() {

		var gridChk = $.ligerui.get($(this).attr("gridid"));

		var rowdata = gridChk.getRow($(this).attr("rowid"));

		var columnname = $(this).attr("columnname");

		var checked = rowdata[columnname];

		grid.updateCell(columnname, !checked, rowdata);

	});

	function init() {
		ajaxJsonObjectByUrl("initHtcgSchemePathConf.do?isCheck=false", {},function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
	}

	function save() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function(i, o) {
						var index = o.__index;
						var par_mr_group = o.mr_group ? 1 : 0;
						var par_mr_sample = o.mr_sample ? 1 : 0;
						var par_clp_step = o.clp_step ? 1 : 0;
						var par_recipe_p_merge = o.recipe_p_merge ? 1 : 0;
						var par_recipe_d_merge = o.recipe_d_merge ? 1 : 0;
						var par_recipe_p_group = o.recipe_p_group ? 1 : 0;
						var par_recipe_d_group = o.recipe_d_group ? 1 : 0;
						ParamVo.push(
								       this.group_id + "@"
								     + this.hos_id + "@"
								     + this.copy_code +"@"
								     + this.scheme_code + "@"
								     + par_mr_group + "@" 
								     + par_mr_sample + "@"
								     + par_clp_step + "@"
								     + par_recipe_p_merge +"@"
								     + par_recipe_d_merge + "@"
								     + par_recipe_p_group+ "@" 
								     + par_recipe_d_group);
					});
			$.ligerDialog.confirm('确定保存?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("saveHtcgSchemePathConf.do?isCheck=false", {
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

	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
						    this.group_id + "@"
						   +this.hos_id + "@"
						   +this.copy_code + "@"
						   +this.scheme_code);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgSchemePathConf.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
		return;
	}

	function loadDict() {
		$("#scheme_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案编码：</td>
			<td align="left" class="l-table-edit-td"><input
				name="scheme_code" type="text" id="scheme_code" ltype="text"  /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
