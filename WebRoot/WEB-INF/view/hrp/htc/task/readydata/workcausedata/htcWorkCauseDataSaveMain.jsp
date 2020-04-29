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
	//动因输入规则
	var grid;
	var gridManager = null;
	var userUpdateStr;
    var work_cause_code = '${work_cause_code}';
    var work_cause_name = '${work_cause_name}';
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms=[];
    	grid.options.newPage=1;
		grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'plan_code',value:liger.get("plan_code").getValue()}); 
    	grid.options.parms.push({name:'work_cause_code',value:work_cause_code}); 
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '核算科室编码',
										name : 'proj_dept_code',
										align : 'left'
									},
									{
										display : '核算科室名称',
										name : 'proj_dept_name',
										align : 'left'
									},
									{
										display : '作业名称',
										name : 'work_name',
										align : 'left'
									},
									{
										display : '收费项目',
										name : 'charge_item_name',
										align : 'left'
									},
									{
										display : '作业动因',
										name : 'work_cause_name',
										render : function(rowdata, rowindex,value) {
											return work_cause_name;
										}
									},
									{
										display : '动因规则',
										name : 'cause_type_code',
										align : 'left',
										render: function (item){ return '手动输入'}
									}, {
										display : '动因值',
										name : 'work_cause_data',
										type : 'float',
										editor : {
											type : 'float'
										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcWorkCauseData.do',
							width : '100%',
							height : '100%',
							enabledEdit : true,
							checkbox : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							delayLoad : true,
							toolbar : {
								items : [ {
									text : '查询',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '采集',
									id : 'collect',
									click : collect,
									icon : 'cut'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function collect() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
						+ this.group_id + "@"
						+ this.hos_id + "@"
						+ this.copy_code + "@"
						+ this.acc_year + "@"
						+ this.plan_code + "@"
						+ this.proj_dept_no + "@"
						+ this.proj_dept_id + "@"
						+ this.charge_item_id + "@"
						+ this.work_code + "@"
						+ work_cause_code + "@"
						+ this.work_cause_data
						);//实际代码中temp替换主键
			});
		ajaxJsonObjectByUrl("collectHtcWorkCauseData.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}


	
	function loadDict() {

		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);
		
		autodate("#acc_year", "YYYY");
		$("#acc_year").ligerTextBox({
			width : 160
		});
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	  <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <td align="right" class="l-table-edit-td" style="padding-left: 20px;">年：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" style="width:160px;"/></td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="maingrid"></div>

</body>
</html>
