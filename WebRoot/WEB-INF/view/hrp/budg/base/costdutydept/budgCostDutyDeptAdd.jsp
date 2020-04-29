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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var budg_year;
	var dialog = frameElement.dialog;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);//加载数据
		loadHotkeys();
		loadForm();
		$("#budg_year,#duty_dept_id,#subj_code").change(function(){
			query();
		})
	});
	//查询
	function query() {
		if (!$("form").valid()) {
			return;
		}
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
		grid.options.parms.push({
			name : 'type_code',
			value : liger.get("type_code").getValue()
		});
		grid.options.parms.push({
			name : 'kind_code',
			value : liger.get("kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'natur_code',
			value : liger.get("natur_code").getValue()
		});
		grid.options.parms.push({
			name : 'out_code',
			value : liger.get("out_code").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
			{
				display : '部门分类',
				name : 'kind_name',
				align : 'left'
			}, {
				display : '部门编码',
				name : 'dept_code',
				align : 'left'
			}, {
				display : '部门名称',
				name : 'dept_name',
				align : 'left'
			}, {
				display : '部门类型',
				name : 'type_name',
				align : 'left'
			}, {
				display : '部门性质',
				name : 'natur_name',
				align : 'left'
			}, {
				display : '支出性质',
				name : 'out_name',
				align : 'left'
			}, {
				display : '部门主管',
				name : 'emp_name',
				align : 'left'
			} , {
				display : '是否职能科室',
				name : 'manager_text',
				align : 'left'
			}  ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryAccDeptAttrData.do?isCheck=false&budg_year='+budg_year,
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			isChecked : initCheck,
			delayLoad : true,
			checkBoxDisplay:function(item){
				if (item.isDisabled) {
					return false;
				} else {
					return true;
				}
			},
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询（<u>E</u>）',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保存（<u>A</u>）',
					id : 'add',
					click : save,
					icon : 'add'
				}, {
					line : true
				}

				]
			},

		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function initCheck(e) {
		if (e.isChecked) {
			return true;
		} else {
			return false;
		}
	}

	function save() {
		if (!$("form").valid()) {
			return;
		}

		var data = grid.getSelectedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行数据后再保存！');
			return false;
		}

		var ParamVo = [];
		var budg_year = liger.get("budg_year").getValue();
		var duty_dept_id = liger.get("duty_dept_id").getValue();
	    var subj_code = liger.get("subj_code").getValue();
		$(data).each(function() {
			ParamVo.push(this.dept_id)
		});
		$.ligerDialog.confirm('确定保存所选的数据?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("addBudgCostDutyDept.do?isCheck=false", {
					paramVo : ParamVo.toString(),
					budg_year : budg_year,
					duty_dept_id : duty_dept_id,
					subj_code : subj_code
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	function loadForm() {
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}
	
	//字典下拉框
	function loadDict() {
		budg_year = dialog.get('data').budg_year;
		
		autocomplete("#budg_year",
				"../../queryBudgYear.do?isCheck=false", "id", "text", true,
				true, '', false, budg_year);
		
		autocomplete("#duty_dept_id", "queryAccDeptAttr.do?isCheck=false",
				"id", "text", true, true)

		autocomplete("#subj_code",
				"../../queryBudgSubj.do?isCheck=false&subj_type=05&budg_year="
						+ budg_year, "id", "text", true, true)
		//部门类型
		autocomplete("#type_code", "../../queryBudgDeptType.do?isCheck=false",
				"id", "text", true, true);
		//部门分类
		autocomplete("#kind_code", "../../queryBudgDeptKind.do?isCheck=false",
				"id", "text", true, true);
		//部门性质
		autocomplete("#natur_code",
				"../../queryBudgDeptNature.do?isCheck=false", "id", "text",
				true, true);
		//支出性质
		autocomplete("#out_code", "../../queryBudgDeptOut.do?isCheck=false",
				"id", "text", true, true);
	}
	//键盘事件
	function loadHotkeys() {
	}
</script>



</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<form name="form1" method="post" id="form1">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算年度<span style="color:red;"><b>*</b></span>：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="budg_year" type="text" id="budg_year" ltype="text"
				validate="{required:true}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">归口科室<span style="color:red;"><b>*</b></span>：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="duty_dept_id" type="text" id="duty_dept_id" ltype="text" validate="{required:true}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出预算科目<span style="color:red;"><b>*</b></span>：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}" />
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">部门类型：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="type_code" type="text" id="type_code" ltype="text" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">部门分类：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="kind_code" type="text" id="kind_code" ltype="text" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">部门性质：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="natur_code" type="text" id="natur_code" ltype="text"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出性质：</td>
			<td align="left" class="l-table-edit-td">
			  <input name="out_code" type="text" id="out_code" ltype="text" />
			</td>
			<td align="left"></td>
		</tr>
	</table>
	</form>
	<div id="maingrid"></div>

</body>
</html>
