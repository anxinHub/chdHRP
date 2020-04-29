<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	var grid;
	var gridManager = null;
	var is_show = '${sessionScope.hpm_para_map["09001"]}';
	
	var stop_state = {
			Rows : [ 
				{"id" : "-1","text" : "请选择"},
				{"id" : "0","text" : "否"},
				{"id" : "1","text" : "是"}
			],
			Total : 3
		};

	$(function() {//加载下拉框//加载数据

		loadDict();
		
		loadHead(null);
		
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'emp_id',
			value : liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[0] : ''
		});
		grid.options.parms.push({
			name : 'emp_id',
			value : liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[1] : ''
		});
		grid.options.parms.push({
			name : 'duty_code',
			value : liger.get("duty_code").getValue()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : ''
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : ''
		});
		/* grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'nature_code',
			value : liger.get("nature_code").getValue()
		});
		if ($("#is_dept").prop("checked")) {
			grid.options.parms.push({
				name : 'is_dept',
				value : "1"
			});
		}
		if ($("#is_acc").prop("checked")) {
			grid.options.parms.push({
				name : 'is_acc',
				value : "1"
			});
		} */
		/* if(liger.get("is_stop").getValue() != "" && liger.get("is_stop").getValue() != "-1"){
			grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 
		} */
		

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '职工编码',
								name : 'emp_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('" + rowdata.emp_id + "|" + rowdata.group_id + "|" + rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>" + rowdata.emp_code + "</a>";
								}
							}, {
								display : '职工名称',
								name : 'emp_name',
								align : 'left'

							}, {
								display : '科室名称',
								name : 'dept_name',
								align : 'left'

							}, {
								display : '人员类别',
								name : 'duty_name',
								align : 'left'

							}, {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 1) {
										return "是";
									} else {
										return "否";
									}
									return "";
								}
							}/* , {
								display : '是否奖金发放',
								name : 'is_acc',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_acc == 1) {
										return "是";
									} else {
										return "否";
									}
									return "";
								}
							}  */],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : '../hpmemp/queryAphiEmpDict.do?isCheck=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad:true,
					selectRowButtonOnly : true,heightDiff: -35,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
	}

	
	function saveHpmEmpBonusData(){
		
		var data = gridManager.getCheckedRows();
		 
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return false;
        }
        var ParamVo =[];
        
        $(data).each(function (){ParamVo.push(this.emp_id+";"+this.emp_no);});
        
		 var formPara={
				 
				acct_yearm:'${acct_yearm}',
				
				dept_id:'${dept_id}',
				
				item_code : '${item_code}',
				
				ParamVo : ParamVo.toString()
				
			};
		ajaxJsonObjectByUrl("addHpmEmpBonusDataForReport.do",formPara,function(responseData){
			            
			if(responseData.state=="true"){parentFrameUse().query();}});
		
	}
	function loadDict() {
		//字典下拉框
		
		//autoCompleteByData("#is_stop",stop_state.Rows,"id","text",true,true);
		
		autocomplete("#dept_kind_code", "../queryDeptKindDict.do?isCheck=false", "id", "text", true, true);

		autocomplete("#nature_code", "../queryDeptNatureDict.do?isCheck=false", "id", "text", true, true);

		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true);

		autocomplete("#duty_code", "../queryEmpDutyDict.do?isCheck=false", "id", "text", true, true);

		autocomplete("#emp_id", "../queryEmpDict.do?isCheck=false", "id", "text", true, true);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
			<td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td> -->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员类别：</td>
			<td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td> -->
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
			<td align="left" class="l-table-edit-td" colspan="6">
				<input type="checkbox" id="is_dept" />是否维护科室&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="checkbox" id="is_acc" />是否参与核算&nbsp;&nbsp;&nbsp;&nbsp;
				
			</td>
			<td align="left"></td> -->
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
