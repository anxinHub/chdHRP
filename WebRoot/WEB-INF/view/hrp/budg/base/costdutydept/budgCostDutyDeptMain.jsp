<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var budg_year;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null); //加载数据
		loadHotkeys();
		
		$("#budg_year,#duty_dept_id,#subj_code").change(function(){
			query();
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'budg_year',
			value : liger.get("budg_year").getValue()
		});
		grid.options.parms.push({
			name : 'duty_dept_id',
			value : liger.get("duty_dept_id").getValue()
		});
		grid.options.parms.push({
			name : 'subj_code',
			value : liger.get("subj_code").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{columns : [{
								display : '归口科室编码',
								name : 'duty_dept_id',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.budg_year + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.subj_code + "')>"
											+ rowdata.duty_dept_code + "</a>";
								}
							}, {
								display : '归口科室名称',
								name : 'duty_dept_name',
								align : 'left'
							}, {
								display : '支出科目编码',
								name : 'subj_code',
								align : 'left'
							}, {
								display : '支出科目名称',
								name : 'subj_name',
								align : 'left'
							}, {
								display : '预算科室编码',
								name : 'dept_code',
								align : 'left'
							}, {
								display : '预算科室名称',
								name : 'dept_name',
								align : 'left'
							}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryBudgCostDutyDept.do?isCheck=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ 
						{
							text : '查询（<u>E</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : openAdd,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						}, { 
							text: '下载导入模板（<u>B</u>）', 
							id:'downTemplate', 
							click:downTemplate,
							icon:'down' 
						},{ 
		                	line:true
		                },{ 
		                	text: '导入（<u>I</u>）', 
		                	id:'import', 
		                	click: imp,
		                	icon:'up' 
		                }, {
							line : true
						}, {
							text : '继承（<u>J</u>）',
							id : 'extend',
							click : extend,
							icon : 'extend'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.budg_year + "|"
								+ rowdata.dept_id + "|"
								+ rowdata.subj_code);
					}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//
	function openAdd() {
		parent.$.ligerDialog.open({
			url : 'hrp/budg/base/costdutydept/budgCostDutyDeptAddPage.do?isCheck=false&',
			data : {budg_year:liger.get("budg_year").getValue()},
			title : '归口科室设置',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			parentframename: window.name
		});

	}
	
	 function imp(){
  		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgCostDutyDeptImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
	}	
	   
	//
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "budg_year=" + vo[3] + "&"
				+ "dept_id=" + vo[4] + "&" + "subj_code=" + vo[5]

		parent.$.ligerDialog.open({
			url : 'hrp/budg/base/costdutydept/budgCostDutyDeptUpdatePage.do?isCheck=false&' + parm,
			data : {},
			title : '归口科室设置',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			parentframename: window.name
		});

	}
	
	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.budg_year + "@"
								+ this.dept_id+ "@" + this.subj_code + "@"
								+ this.duty_dept_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBudgCostDutyDept.do", {
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
	
	function extend(){
		$.ligerDialog.confirm('确定继承上一年度的数据?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("extendBudgCostDutyDept.do?isCheck=false", {
					budg_year : budg_year
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	function loadDict() {
		//预算年度
		autocompleteAsync("#budg_year","../../queryBudgYear.do?isCheck=false", 
				"id", "text", true,true, '', true);
		budg_year = liger.get("budg_year").getValue();

		autocomplete("#duty_dept_id", "queryAccDeptAttr.do?isCheck=false",
				"id", "text", true, true)

		autocomplete("#subj_code",
				"../../queryBudgSubj.do?isCheck=false&subj_type=05&budg_year="
						+ budg_year, "id", "text", true, true)
	}
	//键盘事件
	function loadHotkeys() {

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算年度：</td>
			<td align="left" class="l-table-edit-td"><input name="budg_year"
				type="text" id="budg_year" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">归口科室名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="duty_dept_id" type="text" id="duty_dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出预算科目：</td>
			<td align="left" class="l-table-edit-td"><input name="subj_code"
				type="text" id="subj_code" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
