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
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '核算方案编码',
				name : 'scheme_code',
				align : 'left'
			}, {
				display : '核算方案名称',
				name : 'scheme_name',
				align : 'left'
			}, {
				display : '医嘱方案准入方法',
				name : 'recipe_group_code',
				align : 'left',
				textField : 'recipe_group_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../base/queryHtcgRecipeGroupRuleDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				}
			}, {
				display : '百分比',
				name : 'total_percent',
				align : 'left',
				editor : {
					type : 'float'
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemeRecipeGroupRule.do',
			width : '100%',
			height : '100%',
			enabledEdit : true,
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,
			//heightDiff : -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				},{
					line : true
				}, {
					text : '生成',
					id : 'init',
					click : init,
					icon : 'add'
				}, {
					line : true
				},{
					text : '保存',
					id : 'modify',
					click : save,
					icon : 'add'
				},{
					line : true
				},{
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function init(){
		var formPara = {};
		ajaxJsonObjectByUrl("initHtcgSchemeRecipeGroupRule.do?isCheck=false", formPara,
				function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
				});
	}
	function save(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
					if(this.group_id != null || this.hos_id != null || this.copy_code != null)
						ParamVo.push(
								  this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.scheme_code + "@"
								+ this.recipe_group_code + "@"
								+ this.total_percent);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定要保存更新?',
							function(yes) {
								if (yes) {ajaxJsonObjectByUrl("saveHtcgSchemeRecipeGroupRule.do?isCheck=false",{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
								}
							});
		}
	}

	function remove(){
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
					ajaxJsonObjectByUrl(
							"deleteHtcgSchemeRecipeGroupRule.do", {
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
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
	   <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left" class="l-table-edit-td"><input
				name="scheme_code" type="text" id="scheme_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
