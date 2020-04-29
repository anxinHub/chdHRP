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
			name : 'in_date',
			value : $("#in_date").val()
		});
		grid.options.parms.push({
			name : 'out_date',
			value : $("#out_date").val()
		});
		grid.options.parms.push({
			name : 'mr_no',
			value : $("#mr_no").val()
		});
		grid.options.parms.push({
			name : 'in_hos_no',
			value : $("#in_hos_no").val()
		});
		grid.options.parms.push({
			name : 'patient_name',
			value : $("#patient_name").val()
		});
		grid.options.parms.push({
			name : 'first_age',
			value : $("#first_age").val()
		});;
		grid.options.parms.push({
			name : 'second_age',
			value : $("#second_age").val()
		});;
		grid.options.parms.push({
			name : 'in_dept_no',
			value : liger.get("in_dept_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'in_dept_id',
			value : liger.get("in_dept_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'out_dept_no',
			value : liger.get("out_dept_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'out_dept_id',
			value : liger.get("out_dept_code").getValue().split(".")[0]
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '病案号',
				name : 'mr_no',
				align : 'left',
				width : '100'
			}, {
				display : '住院号',
				name : 'in_hos_no',
				align : 'left',
				width : '100'
			}, {
				display : '姓名',
				name : 'patient_name',
				align : 'left',
				width : '80'
			}, {
				display : '性别',
				name : 'patient_sex',
				align : 'left',
				width : '70',
				render : function(rowdata, value) {
					return rowdata.patient_sex==1?'男':'女';
				}
			}, {
				display : '出生日期',
				name : 'birth_date',
				align : 'left',
				width : '100'
			},{
				display : '入院科室',
				name : 'in_dept_name',
				align : 'left',
				width : '100'
			}, {
				display : '出院科室',
				name : 'out_dept_name',
				align : 'left',
				width : '100'
			}, {
				display : '入院日',
				name : 'in_date',
				align : 'left',
				width : '100'
			}, {
				display : '出院日',
				name : 'out_date',
				align : 'left',
				width : '100'
			}, {
				display : '住院天数',
				name : 'in_days',
				align : 'left',
				width : '90'
			}, {
				display : '主院医师',
				name : 'in_hos_name',
				align : 'left',
				width : '90'
			}, {
				display : '转归名称',
				name : 'outcome_name',
				align : 'left',
				width : '90'
			}, {
				display : '入院诊断',
				name : 'icd10_name',
				align : 'left',
				width : '150'
			}, {
				display : '出院诊断',
				name : 'icd10_name',
				align : 'left',
				width : '150'
			}, {
				display : '手术名称',
				name : 'icd9_name',
				align : 'left',
				width : '150'
			}, {
				display : '手术日期',
				name : 'icd9_date',
				align : 'left',
				width : '100'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgIntegratedQuery.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,
			heightDiff : -10,
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
		
		autocomplete("#in_dept_code","../../base/queryHtcgDeptDict.do?isCheck=false","id","text",true,true);
		
		autocomplete("#out_dept_code","../../base/queryHtcgDeptDict.do?isCheck=false","id","text",true,true)
		$("#in_date").ligerTextBox({
			width : 160
		});
		$("#out_date").ligerTextBox({
			width : 160
		});
		$("#mr_no").ligerTextBox({
			width : 160
		});
		$("#in_hos_no").ligerTextBox({
			width : 160
		});
		$("#patient_name").ligerTextBox({
			width : 160
		});
		$("#first_age").ligerTextBox({
			width : 60
		});
		$("#second_age").ligerTextBox({
			width : 60
		});
		
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">入院日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				style="width: 160px;" name="in_date" type="text" id="in_date"
				ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">出院日期：</td>
			<td align="left" class="l-table-edit-td" colspan=3><input
				name="out_date" style="width: 160px;" class="Wdate" type="text"
				id="out_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">病案号码：</td>
			<td align="left" class="l-table-edit-td"><input name="mr_no" style="width: 160px;" type="text" id="mr_no" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">住院号码：</td>
			<td align="left" class="l-table-edit-td"><input name="in_hos_no" style="width: 160px;" type="text" id="in_hos_no" ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;"> 患者姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="patient_name" style="width: 160px;" type="text" id="patient_name" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">患者年龄：</td>
			<td align="left" class="l-table-edit-td"><input name="first_age" type="text" id="first_age" ltype="text" /></td>
			<td class="l-table-edit-td"><span>至:</span></td>
			<td class="l-table-edit-td"><input name="second_age" type="text" id="second_age" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">入院科室：</td>
			<td align="left" class="l-table-edit-td"><input name="in_dept_code" type="text" id="in_dept_code" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 5px;">出院科室：</td>
			<td align="left" class="l-table-edit-td" colspan=3><input name="out_dept_code" type="text" id="out_dept_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
