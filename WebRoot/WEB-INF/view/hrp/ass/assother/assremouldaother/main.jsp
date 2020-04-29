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
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#back_date_beg").ligerTextBox({
			width : 90
		});
		$("#back_date_end").ligerTextBox({
			width : 90
		});
		$("#create_date_beg").ligerTextBox({
			width : 100
		});
		$("#create_date_end").ligerTextBox({
			width : 100
		});
		$("#state").ligerComboBox({
			width : 160
		});
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'ven_no',
			value : liger.get("ven_id").getValue().split("@")[1]
		});
		grid.options.parms.push({
			name : 'create_date_beg',
			value : $("#create_date_beg").val()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : $("#create_date_end").val()
		});
		
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '申请单号',
								name : 'apply_no',
								align : 'left',
								width : 100,
								frozen : true,
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.apply_no + "')>"
											+ rowdata.apply_no + "</a>";
								}
							}, {
								display : '摘要',
								name : 'note',
								align : 'left',
								width : 150,
								frozen : true,
							}, {
								display : '业务类型',
								name : 'bus_type_code',
								textField:'bus_type_name',
								align : 'left',
								width : 130,
								frozen : true,
							}, {
								display : '供应商',
								name : 'ven_id',
								textField:'sup_name',
								align : 'left',
								width : 200
							},{
								display : '制单人',
								name : 'create_emp',
								textField:'create_emp_name',
								align : 'left',
								width : 100
							}, {
								display : '制单日期',
								name : 'create_date',
								align : 'left',
								width : 130
							},{
								display : '审核人',
								name : 'audit_emp',
								textField:'audit_emp_name',
								align : 'left',
								width : 100
							}, {
								display : '改造日期',
								name : 'change_date',
								align : 'left',
								width : 130
							}, {
								display : '状态',
								name : 'STATE_NAME',
								align : 'left',
								width : 80

							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssRemouldAOther.do?isCheck=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					checkBoxDisplay : isCheckDisplay,
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
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						},{
							text : '申报确认（<u>B</u>）',
							id : 'Confirm',
							click : Confirm,
							icon : 'right'
					     }, 
						/**{
							text : '审核（<u>S</u>）',
							id : 'toExamine',
							click : toExamine,
							icon : 'ok'
						}, {
							line : true
						}, {
							text : '销审（<u>X</u>）',
							id : 'notToExamine',
							click : notToExamine,
							icon : 'right'
						}, {
							line : true
						},*/
						{
							text : '打印（<u>P</u>）',
							id : 'print',
							click : printDate,
							icon : 'print'
						} ]
					} 
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function Confirm(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.apply_no);
					});
			$.ligerDialog.confirm('确认申报?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateAssRemouldAOtherConfirmState.do?isCheck=false", {
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
	
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}

	function add_open() {

		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assremould/assremouldaOther/assRemouldAOtherAddPage.do?isCheck=false',
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '资产改造申报维护'
				});

	}
	function toExamine() {
	}


	function notToExamine() {
		
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
								+ this.copy_code + "@" + this.apply_no);
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssRemouldAOther.do?isCheck=false",
							{
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
		if("null"==vo[3]){
			return false;
			
		}
		var param = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "apply_no=" + vo[3];
		parent.$.ligerDialog
				.open({
					url : 'hrp/ass/assremould/assremouldaOther/assRemouldAOtherUpdatePage.do?isCheck=false&'
							+ param,
					height : $(window).height(),
					width : $(window).width(),
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					title : '资产改造申报维护'
				});

	}
	function loadDict() {

		var param = {
			query_key : ''
		};
		
		autocomplete("#store_id", "../../queryHosStoreDict.do?naturs_code=04&isCheck=false","id", "text",true,true,param,true,null,"300");
    	
		autocomplete("#purc_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
    	
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,param,true,null,"400");
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
		autodate("#create_date_beg","YYYY-mm-dd","month_first");

		autodate("#create_date_end","YYYY-mm-dd","month_last");
		
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		//hotkeys('S', toExamine);

		//hotkeys('X', notToExamine);

		hotkeys('P', printDate);
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
			title : '资产退货',
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
		ajaxJsonObjectByUrl("queryAssBackMainOther.do?isCheck=false", selPara,
				function(responseData) {
					printGridView(responseData, printPara);
				});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input
				name="create_date_beg" type="text" id="create_date_beg"
				class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" width="2%">至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
			<!-- <select
				id="state" name="state">
					<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="1">审核</option>
            		<option value="2">确认</option>
			</select> -->
			<input  name="state" type="text" id="state"/>
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input
				name="ven_id" type="text" id="ven_id" /></td>
		</tr>
	</table>
	
	
	<div id="maingrid"></div>
</body>
</html>
