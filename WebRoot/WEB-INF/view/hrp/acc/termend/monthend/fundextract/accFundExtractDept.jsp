<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;

	var template_id;
	var year_month;
	$(function() {
		template_id = '${template_id}';
		year_month = '${year_month}';
		loadHead(null); //加载数据
		loadDict();
		loadButton();
		query();
	});
	//科目Grid
	function loadHead() {
		grid = $("#deptgrid").ligerGrid({
			columns : [ {
				display : '科室编码',
				name : 'dept_code',
				align : 'left',
				width : 150
			}, {
				display : '科室名称',
				name : 'dept_name',
				align : 'left',
				width : 300
			}, {
				display : '分摊比例',
				name : 'rate',
				align : 'right',
				width : 130,
				editor : {type : 'float'},
				render : function(rowdata){
					return formatNumber(rowdata.rate == null?0:rowdata.rate, 2, 0);
				}
			} ],
			enabledEdit: true,
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryAccFundExtractDept.do?isCheck=false',
			width : '600px',
			height : '100%',
			checkbox : false,
			rownumbers : false,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});

		gridManager = $("#deptgrid").ligerGetGridManager();
	}

	function loadButton() {

		$("#but_query").ligerButton({click : query,width : 90});
	}

	function loadDict() {
		var paras ={
			is_stop : "0"
		};
		autocomplete("#dept_code", "../../../queryHosDept.do?isCheck=false", "id", "text", true, true, paras, false, "", "200");	
	}

	function query() {
		var dept_code_value = "";
		grid.options.parms = [];
		grid.options.newPage = 1;
		if(liger.get("dept_code").getValue() != ""){
			dept_code_value = liger.get("dept_code").getText().split(" ")[0];
		}
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'template_id',
			value : template_id
		});
		grid.options.parms.push({
			name : 'dept_code',
			value : dept_code_value
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//提取科室收入
	function getDeptIncom(){
		var formPara ={
				template_id : template_id,
				year_month : year_month
			};
			ajaxJsonObjectByUrl("saveAccFundExtractGetDeptIncom.do",formPara,function (responseData){
	        	if(responseData.state=="true"){
	        		query();
	        	}
	        });
	}
	
	//保存科室比例
	function saveRate(){
		var formPara ={
			template_id : template_id,
			deptData : getDeptData()
		};
		ajaxJsonObjectByUrl("saveAccFundExtractDept.do",formPara,function (responseData){
        	if(responseData.state=="true"){
        		query();
        	}
        });
	}
	
	function winClose(){
		dialog.close();
	}

	//获取科室数据
    function getDeptData(){
        var manager = $("#deptgrid").ligerGetGridManager();
        var data = manager.getData();
        return JSON.stringify(data);
    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px; width: 40px;">部门：</td>
			<td align="left" class="l-table-edit-td" style="width: 80px;">
				<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="but_query" accessKey="Q" value="查询(Q)" />
			</td>
		</tr>
	</table>
	<div id="deptgrid" style="margin-top: 0px; margin-left:10px;"></div>
</body>
</html>
