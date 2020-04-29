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
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '方案编码',
				name : 'scheme_code',
				width : '5%',
				align : 'left'
			}, {
				display : '方案名称',
				name : 'scheme_name',
				width : '10%',
				align : 'left'
			}, {
				display : '病种编码',
				name : 'drgs_code',
				width : '5%',
				align : 'left'
			}, {
				display : '病种名称',
				name : 'drgs_name',
				width : '10%',
				align : 'left'
			}, {
				display : '病人病历入组规则',
				name : 'icd_rule_name',
				align : 'left'
			}, {
				display : '病人病历样本抽取',
				name : 'mr_rule_name',
				width : '10%',
				align : 'left'
			},, {
				display : '标准住院日',
				name : 'drgs_day',
				width : '10%',
				align : 'left'
			}, {
				display : '同类医嘱项目合并',
				name : 'recipe_merge_name',
				width : '10%',
				align : 'left'
			}, {
				display : '医嘱项目入组准入',
				name : 'recipe_group_name',
				width : '10%',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'querySchemeDrgsCheckReport.do',
			width : '100%',
			height : '100%',
			delayLoad : true,
			checkbox : false,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
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
				name="scheme_code" type="text" id="scheme_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
