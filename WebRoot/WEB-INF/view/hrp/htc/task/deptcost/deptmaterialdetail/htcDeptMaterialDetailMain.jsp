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
		loadHead();

	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;

		//根据表字段进行添加查询条件
	    grid.options.parms.push({
			name : 'start_year_month',
			value : $("#start_year_month").val()
		});

		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});
		
	 	grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue().split(".")[0]
		});

		grid.options.parms.push({
			name : 'mate_type_code',
			value : liger.get("mate_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'mate_code',
			value : $("#mate_code").val()
		});

		grid.options.parms.push({
			name : 'is_charge',
			value : liger.get("is_charge").getValue()
		});

		grid.options.parms.push({
			name : 'source_id',
			value : liger.get("source_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [{
					    display: '核算年度',
					    name: '',
					    align: 'left',
					    render: function(rowdata, rowindex, value) {
					         return "<a href='#' onclick=\"openUpdate('"
					                      + rowdata.group_id + "|" 
					                      + rowdata.hos_id + "|" 
					                      + rowdata.copy_code + "|" 
					                      + rowdata.acc_year + "|" 
					                      + rowdata.acc_month + "|"
					                      + rowdata.dept_no + "|"
					                      + rowdata.dept_id + "|"
					                      + rowdata.mate_code+
					                      "');\" >" + rowdata.acc_year + rowdata.acc_month; + "</a>";
					    }
					},
					{
					    display: '科室编码',
					    name: 'dept_code',
					    align: 'left',
					},
					{
					    display: '科室名称',
					    name: 'dept_name',
					    align: 'left',
					},
					{
					    display: '材料分类编码',
					    name: 'mate_type_code',
					    align: 'left',
					},
					{
					    display: '材料分类名称',
					    name: 'mate_type_name',
					    align: 'left',
					},
					{
					    display: '材料编码',
					    name: 'mate_code',
					    align: 'left',
					},
					{
					    display: '材料名称',
					    name: 'mate_name',
					    align: 'left',
					},
					{
					    display: '收费标志',
					    name: 'is_charge',
					    align: 'left',
					    render: function(rowdata, rowindex, value) {
					        return rowdata.is_charge == 1 ? "是" : "否";
					    }
					},
					{
					    display: '数量',
					    name: 'num',
					    align: 'left',
					    render: function(rowdata, rowindex, value) {
					        return formatNumber(rowdata.num, 2, 1);
					    }
					},
					{
					    display: '金额',
					    name: 'amount',
					    align: 'right',
					    render: function(rowdata, rowindex, value) {
					        return formatNumber(rowdata.amount, 2, 1);
					    }
					},
					{
					    display: '资金来源',
					    name: 'source_name',
					    align: 'left'
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcDeptMaterialDetail.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					delayLoad:true,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						},

						{
							text : '添加',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						},

						{
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

	function add_open(){
		$.ligerDialog.open({
			url : 'htcDeptMaterialDetailAddPage.do?isCheck=false',
			height : 400,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptMaterialDetail();
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
			$(data).each(
					function() {
						ParamVo.push(
								  this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.acc_year + "@"
								+ this.acc_month + "@"
								+ this.dept_no + "@"
								+ this.dept_id + "@"
								+ this.mate_code);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl(
							"deleteHtcDeptMaterialDetail.do", {
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
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0] + 
		      "&" + "hos_id=" + vo[1] + 
			  "&" + "copy_code=" + vo[2]+
			  "&" + "acc_year=" + vo[3]+ 
			  "&" + "acc_month=" + vo[4]+ 
			  "&" + "dept_no=" + vo[5]+ 
			  "&" + "dept_id=" + vo[6]+ 
			  "&" + "mate_code=" + vo[7];
		$.ligerDialog.open({
			url : 'htcDeptMaterialDetailUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 400,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptMaterialDetail();
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

	function loadDict(){
		
			autocomplete("#dept_code","../../../info/base/queryHtcDeptDict.do?isCheck=false", "id","text", true, true,);
			
			autocomplete("#mate_type_code","../../../info/base/queryHtcMaterialTypeDict.do?isCheck=false","id", "text", true, true);
			
			autocomplete("#is_charge", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true);

			autocomplete("#source_code","../../../info/base/queryHtcSourceDict.do?isCheck=false","id","text",true,true);
			
			autodate("#start_year_month", "YYYYmm");
			autodate("#end_year_month", "YYYYmm");

			$("#start_year_month").ligerTextBox({
				width : 70
			});
			$("#end_year_month").ligerTextBox({
				width : 70
			});
			$("#mate_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="start_year_month" type="text" id="start_year_month"
				style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="right"><span>&nbsp;至&nbsp;</span></td>
			<td><input class="Wdate" name="end_year_month" type="text"
				id="end_year_month" style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">材料分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="mate_type_code" type="text" id="mate_type_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费标志：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input
				name="is_charge" type="text" id="is_charge" ltype="text" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_code"
				type="text" id="source_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资材料：</td>
			<td align="left" class="l-table-edit-td"><input name="mate_code"
				type="text" id="mate_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>