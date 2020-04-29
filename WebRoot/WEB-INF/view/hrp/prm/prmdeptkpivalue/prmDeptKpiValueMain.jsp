<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var clicked = 0;
	$(function() {
		$('body').height("100%");  //   以免拉动左边树时结构混乱
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		
		toolbar();
		loadHotkeys();
		
		$("#acc_year_month").ligerTextBox({
			width : 160
		});
		$("#goal_code").ligerTextBox({
			width : 400
		});
		$("#dept_kind_code").ligerTextBox({
			width : 160
		});
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#kpi_code").ligerTextBox({
			width : 160
		});
		$("#is_audit").ligerComboBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year_month").val().substring(0,4)
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : $("#acc_year_month").val().substring(4,6)
		});
		grid.options.parms.push({
			name : 'kpi_code',
			value : liger.get("kpi_code").getValue()
		});
		grid.options.parms.push({
			name : 'goal_code',
			value : liger.get("goal_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'is_audit',
			value : liger.get("is_audit").getValue()
		});
		
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
  	    grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(".")[1] == null?'':liger.get("dept_id").getValue().split(".")[1]});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '科室编码',
				name : 'dept_code',
				align : 'left',
				width : 90
			}, {
				display : '科室名称',
				name : 'dept_name',
				align : 'left',
				width : 90
			}, {
				display : '指标编码',
				name : 'kpi_code',
				align : 'left',
				width : 90
			}, {
				display : '指标名称',
				name : 'kpi_name',
				align : 'left',
				width : 300
			}, {
				display : '指标值',
				name : 'kpi_value',
				align : 'right',
				editor : {
					type : 'text'
				},
				width : 90,
				render : function(rowdata, rowindex,value) {
					  return rowdata.kpi_value == null || rowdata.kpi_value == "" ? "" : formatNumber(rowdata.kpi_value,2,1) ;
				}
			}, {
				display : '审核状态',
				name : 'is_audit',
				align : 'center',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_audit == 0 || rowdata.is_audit == null) {
						return "未审核";
					} else {
						return "审核"

					}
				},
				width : 90
			}, {
				display : '审核人',
				name : 'user_name',
				align : 'center',
				width : 90
			}, {
				display : '审核日期',
				name : 'audit_date',
				align : 'left',
				width : 120
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryPrmDeptKpiValueScheme.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			enabledEdit : true,
			delayLoad : true,
			selectRowButtonOnly : true//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true });
       	
       	obj.push({text : '生成（<u>C</u>）',id : 'create',click : createDeptKpiValue,icon : 'bookpen'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : deleteDeptKpiValue,icon : 'delete'});
       	obj.push({ line:true });
       	
       	obj.push({text : '下载模板（<u>T</u>）',id : 'downTemplate',click : downTemplateDeptKpiValue,icon : 'down'});
       	obj.push({ line:true });
       	
       	obj.push({text : '导入（<u>O</u>）',id : 'import',click : importDeptKpiValue,icon : 'up'});
       	obj.push({ line:true });
       	
       	obj.push({text : '审核（<u>A</u>）',id : 'audit',click : auditDeptKpiValue,icon : 'audit'});
       	obj.push({ line:true });
       	
       	obj.push({text : '反审核（<u>B</u>）',id : 'reAudit',click : reAuditDeptKpiValue,icon : 'back'});
       	obj.push({ line:true });
       	
       	obj.push({text : '保存（<u>S</u>）',id : 'save',click : saveDeptKpiValue,icon : 'save'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
		hotkeys('C', createDeptKpiValue);
		hotkeys('D', deleteDeptKpiValue);
		hotkeys('O', importDeptKpiValue);
		hotkeys('T', downTemplateDeptKpiValue);
		hotkeys('A', auditDeptKpiValue);
		hotkeys('B', reAuditDeptKpiValue);
		hotkeys('S', saveDeptKpiValue);
	}

	function saveDeptKpiValue() {

		var data = gridManager.rows;

		var formPara = {
			kpi_value_data : JSON.stringify(data)
		};
		ajaxJsonObjectByUrl("saveBatchDeptKpiValue.do", formPara, function(
				responseData) {
			if (responseData.state == "true") {
				query();
			}

		});

	}

	function createDeptKpiValue() {
		if ($("#acc_year_month").val() == "") {
			$.ligerDialog.warn("期间不能为空");
			return false;
		}

		
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){
				var ParamVo = {
						acc_year :$("#acc_year_month").val().substring(0,4),
						acc_month:$("#acc_year_month").val().substring(4,6)
				}

				ajaxJsonObjectByUrl("createDeptKpiValue.do", ParamVo, function(responseData) {
					if (responseData.state == "true") {
						query();
					}else{
						$.ligerDialog.warn('未找到可生成的数据 ');
					}
				});
			}
		});
	}
	function deleteDeptKpiValue() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.goal_code + "@"
								+ this.kpi_code + "@" + this.dept_no + "@"
								+ this.dept_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deletePrmDeptKpiValue.do", {
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
	
	//导入页面跳转
	function importDeptKpiValue() {
		
			
		parent.$.ligerDialog.open({ url : 'hrp/prm/prmdeptkpivalue/prmDeptKpiValueImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'科室KPI指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//下载导入模板
	function downTemplateDeptKpiValue() {

		location.href = "downTemplate.do?isCheck=false";
	}
	
	//审核
	function auditDeptKpiValue() {
		var data = gridManager.getData();
		if (data.length == 0) {
			$.ligerDialog.warn('未找到要审核的数据 ');
			return ; 
		}
		
		var checkedRows = gridManager.getCheckedRows();
		var param = {};
		
		if(checkedRows.length == 0){
			
			var goal_code = liger.get("goal_code").getValue();
			if(goal_code == null || goal_code == ''){
				$.ligerDialog.warn('请选择目标名称 ');
				return ; 
			}
			
			var acc_year_month = $('#acc_year_month').val();
			param = {
				acc_year : acc_year_month.substring(0,4),
				acc_month : acc_year_month.substring(4,6),
				goal_code : goal_code
			};
		}else{
			var checkIds = [];
			
			$.each(checkedRows,function(index,obj){
				checkIds.push(
					obj.group_id + "@" +
					obj.hos_id + "@" +
					obj.copy_code + "@" +
					obj.acc_year + "@" +
					obj.acc_month + "@" +
					obj.dept_id + "@" +
					obj.dept_no + "@" +
					obj.goal_code + "@" +
					obj.kpi_code
				);
			});
			
			param = {
				checkIds : checkIds.toString()
			}
		}
		
		
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditPrmDeptKpiValue.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//未审核
	function reAuditDeptKpiValue() {
		
		var data = gridManager.getData();
		if (data.length == 0) {
			$.ligerDialog.warn('未找到要反审核的数据 ');
			return ; 
		}
		
		var checkedRows = gridManager.getCheckedRows();
		var param = {};
		
		if(checkedRows.length == 0){
			
			var goal_code = liger.get("goal_code").getValue();
			if(goal_code == null || goal_code == ''){
				$.ligerDialog.warn('请选择目标名称 ');
				return ; 
			}
			
			var acc_year_month = $('#acc_year_month').val();
			param = {
				acc_year : acc_year_month.substring(0,4),
				acc_month : acc_year_month.substring(4,6),
				goal_code : goal_code
			};
		}else{
			var checkIds = [];
			
			$.each(checkedRows,function(index,obj){
				checkIds.push(
					obj.group_id + "@" +
					obj.hos_id + "@" +
					obj.copy_code + "@" +
					obj.acc_year + "@" +
					obj.acc_month + "@" +
					obj.dept_id + "@" +
					obj.dept_no + "@" +
					obj.goal_code + "@" +
					obj.kpi_code
				);
			});
			
			param = {
				checkIds : checkIds.toString()
			}
		}
		
		$.ligerDialog.confirm('确定取消审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("reAuditDeptKpiValue.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	function loadDict() {
		//字典下拉框

		autocompleteAsync("#goal_code", "../quertPrmGoalDict.do?isCheck=false",
				"id", "text", true, true, "", true, "", "400");

		autocompleteAsync("#dept_kind_code", "../queryPrmDeptKind.do?isCheck=false",
				"id", "text", true, true, "", false);

		autocompleteAsync("#dept_id","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false,null);

		autocompleteAsync("#kpi_code", "../queryPrmDeptKpi.do?isCheck=false", "id",
				"text", true, true, "", false, "", "400");

		autodate("#acc_year_month", "yyyyMM");

	}

	//打印
	function print(){
  		
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'科室KPI指标数据采集',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
    	
   		ajaxJsonObjectByUrl("queryPrmDeptKpiValueScheme.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td align="left" class="l-table-edit-td"><input name="acc_year_month"
				class="Wdate" type="text" id="acc_year_month" ltype="text"
				validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
			<td align="left" class="l-table-edit-td" colspan="4"><input
				name="goal_code" type="text" id="goal_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">KPI指标：</td>
			<td align="left" class="l-table-edit-td"><input name="kpi_code"
				type="text" id="kpi_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td">审核标志：</td>
			<td align="left" class="l-table-edit-td"><select id="is_audit"
				name="is_audit" style="width: 135px;">
					<option value="">全部</option>
					<option value="0">未审核</option>
					<option value="1">已审核</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
