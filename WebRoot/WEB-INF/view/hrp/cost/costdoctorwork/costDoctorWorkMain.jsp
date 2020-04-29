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
	$(function() {
		loadDict();
		loadHead(null); //加载数据

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_year_month',
			value : $("#begin_year_month").val()
		});
		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});
		grid.options.parms.push({
			name : 'dept_code',
			value : liger.get("dept_code").getValue().split(".")[2]
		});
		grid.options.parms.push({
			name : 'emp_id',
			value : liger.get("emp_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'emp_no',
			value : liger.get("emp_code").getValue().split(".")[1]
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
						{
							columns : [
									{
										display : '统计年度',
										name : 'year_month',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											if(rowdata.acc_year+ rowdata.acc_month==""){
												return "合计";
										    }else{
											    return rowdata.acc_year+ rowdata.acc_month;
										    }
										}
									},
									{
										display : '科室编码',
										name : 'dept_code',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											if (rowdata.acc_year+ rowdata.acc_month != "") {
												return "<a href=javascript:openUpdate('"
														+ rowdata.group_id + "|"
														+ rowdata.hos_id + "|"
														+ rowdata.copy_code + "|"
														+ rowdata.acc_year + "|"
														+ rowdata.acc_month + "|"
														+ rowdata.dept_id + "|"
														+ rowdata.dept_no + "|"
														+ rowdata.emp_id + "|"
														+ rowdata.emp_no + "|" + "')>"
														+ rowdata.dept_code
														+ "</a>";
											} else {
												return null;
											}
										}
									}, {
										display : '科室名称',
										name : 'dept_name',
										align : 'left'
									}, {
										display : '医生编码',
										name : 'emp_code',
										align : 'left'
									}, {
										display : '医生名称',
										name : 'emp_name',
										align : 'left'
									}, {
										display : '医生工作量',
										name : 'doctor_num',
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryCostDoctorWork.do',
							delayLoad : true,
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							checkBoxDisplay : f_checkBoxDisplay,
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
									text : '添加',
									id : 'add',
									click : add_open,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								},{
									text : '导入',
									id : 'import',
									click : imp,
									icon : 'up'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function f_checkBoxDisplay(rowdata) {
		if (rowdata.acc_year + rowdata.acc_month == "")return false;
		return true;
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'costDoctorWorkAddPage.do?isCheck=false',
			height : 300,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostDoctorWork();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

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
						+  this.hos_id + "@"
						+  this.copy_code + "@"
						+  this.acc_year + "@"
						+  this.acc_month + "@"
						+  this.dept_id + "@"
						+  this.dept_no + "@"
						+  this.emp_id + "@"
						+  this.emp_no);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteCostDoctorWork.do", {
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


	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				 + "copy_code=" + vo[2] + "&" + "acc_year=" + vo[3] + "&"
				 + "acc_month=" + vo[4] + "&" + "dept_id=" + vo[5] + "&"
				 + "dept_no=" + vo[6] + "&" + "emp_id=" + vo[7]+ "&"
				 + "emp_no=" + vo[8];
		$.ligerDialog.open({
			url : 'costDoctorWorkUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 300,
			width : 400,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostDoctorWork();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {

		//字典下拉框
		autocomplete("#dept_code", "../queryDeptDictCode.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#emp_code", "../queryCostEmpDict.do?isCheck=false", "id",
				"text", true, true);

		$("#begin_year_month").ligerTextBox({
			width : 160
		});
		$("#end_year_month").ligerTextBox({
			width : 160
		});

		autodate("#begin_year_month", "yyyyMM");
		autodate("#end_year_month", "yyyyMM");
	}

	function imp(){
		var para={
			    "column": [
			        {
			            "name": "year_month",
			            "display": "统计年度",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_code",
			            "display": "科室编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_name",
			            "display": "科室名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "emp_code",
			            "display": "医生编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "emp_name",
			            "display": "医生名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "doctor_num",
			            "display": "医生工作量",
			            "width": "200",
			            "require":true
			        },
			    ]
			};
		
		  importSpreadView("hrp/cost/costdoctorwork/impCostDoctorWork.do?isCheck=false",para);

    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">统计年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="begin_year_month" type="text" id="begin_year_month"
				class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="left" class="l-table-edit-td"><input
				name="end_year_month" type="text" id="end_year_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" /></td>
		</tr>
		<tr>
		  <td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工：</td>
		  <td align="left" class="l-table-edit-td"><input name="emp_code"
				type="text" id="emp_code" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
