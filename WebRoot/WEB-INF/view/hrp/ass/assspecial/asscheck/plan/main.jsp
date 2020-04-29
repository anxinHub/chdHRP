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
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'check_plan_no',
			value : $("#check_plan_no").val()
		});
		grid.options.parms.push({
			name : 'summary',
			value : $("#summary").val()
		});

		grid.options.parms.push({
			name : 'mak_date_beg',
			value : $("#mak_date_beg").val()
		});
		grid.options.parms.push({
			name : 'mak_date_end',
			value : $("#mak_date_end").val()
		});
		grid.options.parms.push({
			name : 'audit_date_beg',
			value : $("#audit_date_beg").val()
		});
		grid.options.parms.push({
			name : 'audit_date_end',
			value : $("#audit_date_end").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '任务单号',
						name : 'check_plan_no',
						align : 'left',
						render : function(rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('"
									+ rowdata.group_id + "|"
									+ rowdata.hos_id + "|"
									+ rowdata.copy_code + "|"
									+ rowdata.check_plan_no + "')>"
									+ rowdata.check_plan_no + "</a>";
						}
					}, {
						display : '任务说明',
						name : 'summary',
						align : 'left'
					}, {
						display : '盘点开始日期',
						name : 'beg_date',
						align : 'left'
					}, {
						display : '盘点结束日期',
						name : 'end_date',
						align : 'left'
					}, {
						display : '盘点完成日期',
						name : 'fin_date',
						align : 'left'
					}, {
						display : '制单人',
						name : 'mak_emp_name',
						align : 'left'
					}, {
						display : '制单日期',
						name : 'mak_date',
						align : 'left'
					}, {
						display : '确认人',
						name : 'audit_emp_name',
						align : 'left'
					}, {
						display : '确认日期',
						name : 'audit_date',
						align : 'left'
					}, {
						display : '状态',
						name : 'state',
						align : 'left',render : function(rowdata, rowindex, value) {
    		 				if(rowdata.state>0){
    		 					return '确认';
    		 				}else{
    		 					return '新建';
    		 				}
    		 			}
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssCheckPlanSpecial.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
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
							text : '添加（<u>A</u>）',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'add',
							click : remove,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '确认（<u>D</u>）',
							id : 'audit',
							click : audit,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '取消确认（<u>D</u>）',
							id : 'unAudit',
							click : unAudit,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '盘点完成（<u>Y</u>）',
							id : 'finish',
							click : finish,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '取消完成（<u>T</u>）',
							id : 'unfinish',
							click : unFinish,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '生成盘点单（<u>H</u>）',
							id : 'generate',
							click : generate,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '打印（<u>P</u>）',
							id : 'print',
							click : printDate,
							icon : 'print'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.check_plan_no);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		$.ligerDialog.open({
			url : 'assCheckPlanSpecialAddPage.do?isCheck=false&',
			data : {},
			height : 300,
			width : 450,
			title : '盘点任务单(专用设备)',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssCheckPlanSpecial();
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

	function remove() {

		var data = gridManager.getCheckedRows();
		var group_id = "";
		var hos_id = "";
		var copy_code = "";
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.check_plan_no  );
					});
		/* 	var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;

				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

					group_id : group_id,
					hos_id : hos_id,
					copy_code : copy_code,
				check_plan_no_all : check_plan_no_all.toString()
			}; */

			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssCheckPlanSpecial.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function audit() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;

				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

				group_id : group_id,
				hos_id : hos_id,
				copy_code : copy_code,
				check_plan_no_all : check_plan_no_all.toString()
			};
			$.ligerDialog.confirm('确定确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("specialAudit.do", formPara, function(
							responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function unAudit() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;

				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

					group_id : group_id,
					hos_id : hos_id,
					copy_code : copy_code,
				check_plan_no_all : check_plan_no_all.toString()
			};

			$.ligerDialog.confirm('确定取消确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("specialUnAudit.do", formPara, function(
							responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function finish() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;

				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

				group_id : group_id,
				hos_id : hos_id,
				copy_code : copy_code,
				check_plan_no_all : check_plan_no_all.toString()
			};
			$.ligerDialog.confirm('确定盘点完成?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("specialFinish.do", formPara, function(
							responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function unFinish() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;

				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

					group_id : group_id,
					hos_id : hos_id,
					copy_code : copy_code,
				check_plan_no_all : check_plan_no_all.toString()
			};

			$.ligerDialog.confirm('确定取消完成?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("specialUnFinish.do", formPara, function(
							responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function generate() {

		var data = gridManager.getCheckedRows();
		var group_id = "";
		var hos_id = "";
		var copy_code = "";
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.error('请选择盘点任务单 并且只能选择一条');
		} else {
			var check_plan_no="";
			var check_plan_no_all = [];
			$(data).each(function() {

				group_id = this.group_id;
				hos_id = this.hos_id;
				copy_code = this.copy_code;
				check_plan_no=this.check_plan_no;
				check_plan_no_all.push(this.check_plan_no)
			});

			var formPara = {

					group_id : group_id,
					hos_id : hos_id,
					copy_code : copy_code,
				//check_plan_no_all : check_plan_no_all.toString()
					check_plan_no:check_plan_no
			};

			$.ligerDialog.confirm('生成的盘点单将会覆盖原有数据?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("specialGenerate.do", formPara, function(responseData) {
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
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "check_plan_no=" + vo[3]

		parent.$.ligerDialog.open({
			
			title: '盘点单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assspecial/asscheck/plan/assCheckPlanSpecialUpdatePage.do?isCheck=false&'+ parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			
		});

	}
	function loadDict() {
		//字典下拉框
		$("#mak_date_beg").ligerTextBox({
			width : 90
		});

		$("#mak_date_end").ligerTextBox({
			width : 90
		});

		$("#audit_date_beg").ligerTextBox({
			width : 90
		});

		$("#audit_date_end").ligerTextBox({
			width : 90
		});
		$("#check_plan_no").ligerTextBox({
			width : 160
		});
		$("#summary").ligerTextBox({
			width : 580
		});

		/* $("#state").ligerComboBox({
			width : 160
		}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'确认'},{id:2,text:'完成'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		autodate("#mak_date_beg","YYYY-mm-dd","month_first");

		autodate("#mak_date_end","YYYY-mm-dd","month_last");
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('W', audit);
		hotkeys('R', unAudit);
		hotkeys('W', finish);
		hotkeys('R', unFinish);
		hotkeys('G', generate);

	}

	function printDate() {

		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var selPara = {};

		$.each(grid.options.parms, function(i, obj) {

			selPara[obj.name] = obj.value;

		});

		var dates = getCurrentDate();

		var cur_date = dates.split(";")[2];
		//跨所有列:计算列数
		var colspan_num = grid.getColumns(1).length - 1;

		var printPara = {
			title : '盘点任务单',
			head : [ {
				"cell" : 0,
				"value" : "单位: ${hos_name}",
				"colspan" : colspan_num,
				"br" : true
			} ],
			foot : [ {
				"cell" : 0,
				"value" : "主管:",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "复核人:",
				"colspan" : colspan_num - 2,
				"br" : true
			}, {
				"cell" : 0,
				"value" : "制单人： ${user_name}",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "打印日期: " + cur_date,
				"colspan" : colspan_num - 2,
				"br" : true
			} ],
			columns : grid.getColumns(1),
			headCount : 2,//列头行数
			autoFile : true,
			type : 3
		};
		ajaxJsonObjectByUrl("queryAssCheckPlanSpecial.do?isCheck=false",
				selPara, function(responseData) {
					printGridView(responseData, printPara);
				});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"
		width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="mak_date_beg" type="text" id="mak_date_beg" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left">至：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="mak_date_end" type="text" id="mak_date_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'mak_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">任务单号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="check_plan_no" type="text" id="check_plan_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="state"
				name="state">
					<option value="">全部</option>
					<option value="0">新建</option>
					<option value="1">确认</option>
					<option value="1">完成</option>
			</select> -->
			<input  name="state" type="text" id="state"/>
			</td>
			<td align="left"></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="audit_date_beg" type="text" id="audit_date_beg" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left">至：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="audit_date_end" type="text" id="audit_date_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">任务说明：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><input
				name="summary" type="text" id="summary" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
