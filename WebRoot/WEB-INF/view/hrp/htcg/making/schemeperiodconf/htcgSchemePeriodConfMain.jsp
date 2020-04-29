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
<style >
 .radioIcon{
      margin-top: 6px;
 }
</style>
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
			name : 'scheme_code',
			value : $("#scheme_code").val()
		});
		grid.options.parms.push({
			name : 'scheme_name',
			value : $("#scheme_name").val()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '方案编码',
				name : 'scheme_code',
				align : 'left'
			}, {
				display : '方案名称',
				name : 'scheme_name',
				align : 'left'
			}, {
				display : '月度',
				align : 'center',
				name : 'month_flag',
				render : checkboxRender
			}, {
				display : '季度',
				name : 'quarter_flag',
				align : 'center',
				render : checkboxRender
			}, {
				display : '半年',
				name : 'half_year_flag',
				align : 'center',
				render : checkboxRender
			}, {
				display : '年度',
				name : 'year_flag',
				align : 'center',
				render : checkboxRender
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemePeriodConf.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
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
				},{
					text : '生成',
					id : 'init',
					click : init,
					icon : 'add'
				}, {
					line : true
				},{
					text : '保存',
					id : 'add',
					click : save,
					icon : 'add'
				}, {
					line : true
				},{
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function checkboxRender(rowdata, rowindex, value, column) {
		var res = rowdata['__id'].substr(4, 4);
		var iconHtml = '<input  class="radioIcon" type="radio"';
		if (value)
			iconHtml += ' checked =checked';
		iconHtml += ' rowid = "' + rowdata['__id'] + '"';
		iconHtml += ' gridid = "' + this.id + '"';
		iconHtml += 'id = "' + column.name + res + '"';
		iconHtml += ' name =  "' + rowdata['__id'] + '"';
		iconHtml += 'value = "' + value + '"';
		iconHtml += ' onClick = radioClick()';
		iconHtml += '>';
		return iconHtml;
	}
	
	function radioClick() {
		$('input:radio').each(function() {
			if (this.checked)
				$(this).val("1");
			else
				$(this).val("0");
		});
	}

	function init() {
		ajaxJsonObjectByUrl("initHtcgSchemePathConf.do?isCheck=false", {},
				function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
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
					    this.group_id + "@"
					   +this.hos_id + "@"
					   +this.copy_code + "@"
					   +this.scheme_code
						);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgSchemePeriodConf.do", {
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
	
	function save() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function(i, o) {
						var index = o.__index;
						var month_flag = $("#month_flag" + (index + 1)).val();
						var quarter_flag = $("#quarter_flag" + (index + 1)).val();
						var half_year_flag = $("#half_year_flag" + (index + 1)).val();
						var year_flag = $("#year_flag" + (index + 1)).val();
						ParamVo.push(this.group_id + "@"
							     + this.hos_id + "@"
							     + this.copy_code +"@"
								 + this.scheme_code + "@"
								 + month_flag + "@"
								 + quarter_flag + "@"
								 + half_year_flag + "@"
								 + year_flag);
					});
			$.ligerDialog.confirm('确定保存?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl(
							"saveHtcgSchemePeriodConf.do?isCheck=false", {
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


	function loadDict() {
		//字典下拉框
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input
				style="width: 160px;" name="scheme_code" type="text" id="scheme_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
