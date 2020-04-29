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
	
	
	//页面初始化
	$(function() {
		$('body').height("100%");  //   以免拉动左边树时结构混乱
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		
		toolbar();//工具栏
		loadHotkeys();//加载快捷键
		
	});
	
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;

		//根据表字段进行添加查询条件  
		grid.options.parms.push({name : 'acc_year',value : $("#acc_year").val()});
		grid.options.parms.push({name : 'dept_kind_code',value : liger.get("dept_kind_code").getValue()});
		
		var dept_no = liger.get("dept_no").getValue();
		
		if(!dept_no){dept_no= ".";}
		
		grid.options.parms.push({name:'dept_id',value:dept_no.split(".")[0]}); 
		grid.options.parms.push({name:'dept_no',value:dept_no.split(".")[1]});
		
		grid.options.parms.push({name : 'target_code',value : liger.get("target_code").getValue()});
		grid.options.parms.push({name : 'is_audit',value : $("#is_audit").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ 
				{display : '科室名称',name : 'dept_name',align : 'left',width:110}, 
				
				{display : '指标编码',name : 'target_code',align : 'left',width:110}, 
				
				{display : '指标名称',name : 'target_name',align : 'left',width:240}, 
				
				{display : '指标值',name : 'target_value',align : 'right',width:110,editor : {
						type : 'float',
						precision : 4
					},render : 
						function(rowdata, rowindex, value) {
							var col = arguments[arguments.length - 1];
							if (rowdata.is_audit == 1) {
								rowdata.notEidtColNames.push(col.columnname);
							}
						return rowdata.target_value == null || rowdata.target_value == "" ? "" : formatNumber(rowdata.target_value,2,1) ;
					}
				}, 
				
				{display : '审核标志',name : 'is_audit',align : 'center',width:110,render : 
					function(rowdata, rowindex, value) {
						if (rowdata.is_audit == 0 || rowdata.is_audit == null) {
							return "未审核";
						} else {
							return "审核"
						}
					}
				}, 
				
				{display : '审核人',name : 'check_name',align : 'left',width:110}, 
				
				{display : '审核时间',name : 'audit_date',align : 'left',width:140} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,url : 'queryPrmDeptTargetPrmTargetData.do',
			width : '100%',height : '100%',checkbox : true,rownumbers : true,enabledEdit : true,delayLoad : true,
			selectRowButtonOnly : true//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true });
       	
       	obj.push({text : '生成（<u>C</u>）',id : 'create',click : createDeptTarget,icon : 'bookpen'});
       	obj.push({ line:true });
       	
       	obj.push({text : '删除（<u>D</u>）',id : 'delete',click : deleteDeptTarget,icon : 'delete'});
       	obj.push({ line:true });
       	
       	
       	obj.push({text : '下载导入模板（<u>T</u>）',id : 'downTemplate',click : downTemplateDeptTarget,icon : 'down'});
       	obj.push({ line:true });
       	
       	obj.push({text : '导入（<u>O</u>）',id : 'import',click : importDeptTarget,icon : 'up'});
       	obj.push({ line:true });
       	
       	
       	obj.push({text : '审核（<u>A</u>）',id : 'audit',click : auditDeptTarget,icon : 'audit'});
       	obj.push({ line:true });
       	obj.push({text : '反审核（<u>B</u>）',id : 'reAudit',click : reAuditDeptTarget,icon : 'back'});
       	obj.push({ line:true });
       	obj.push({text : '保存（<u>S</u>）',id : 'save',click : saveDeptTarget,icon : 'save'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	//快捷键
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
		hotkeys('C', createDeptTarget);
		hotkeys('D', deleteDeptTarget);
		hotkeys('O', importDeptTarget);
		hotkeys('T', downTemplateDeptTarget);
		hotkeys('A', auditDeptTarget);
		hotkeys('B', reAuditDeptTarget);
		hotkeys('S', saveDeptTarget);

	}
	
	
	//保存
	function saveDeptTarget() {
		gridManager.endEdit();
		var data = gridManager.getUpdated();

		if (data.length == 0) {
			$.ligerDialog.warn('沒有数据更新');
			return ; 
		} 
		
		var ParamVo = [];
		$(data).each(function() {
			var target_value = this.target_value == "" ? "0" : this.target_value;
				ParamVo.push(
					this.group_id + "@" + 
					this.hos_id + "@" + 
					this.copy_code + "@" + 
					this.acc_year + "@" + 
					this.acc_month + "@" + 
					this.target_code + "@" + 
					this.dept_no + "@" + 
					this.dept_id + "@" + 
					target_value
				)
		});

		ajaxJsonObjectByUrl("saveBatchPrmDeptTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
			if (responseData.state == "true") {
				query();
			}
		});
	}
	
	
	//生成
	function createDeptTarget() {
		
		if ($("#acc_year").val() == "") {
			$.ligerDialog.warn("核算年月不能为空");
			return false;
		}
		
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){
				var ParamVo = [];
				ParamVo.push(
					$("#acc_year").val()
				)

				ajaxJsonObjectByUrl("createPrmDeptTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//删除
	function deleteDeptTarget() {
		gridManager.endEdit();
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var ParamVo = [];
		$(data).each(function() {
			if(isnull(this.group_id)){
				gridManager.deleteSelectedRow();
			}else{
				ParamVo.push(this.group_id + "@" + this.hos_id + "@"
					+ this.copy_code + "@" + this.acc_year + "@"
					+ this.acc_month + "@" + this.target_code + "@"
					+ this.dept_no + "@" + this.dept_id);
			}
		});
		
		
		if(ParamVo == ""){
			return;
		}
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deletePrmDeptTargetData.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//导入
	function importDeptTarget() {
		
		parent.$.ligerDialog.open({ 
			url : 'hrp/prm/prmdepttargetdata/prmDeptTargetDataImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,
			title:'科室绩效指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
		
	}
	
	//下载导入模板
	function downTemplateDeptTarget() {
		location.href = "downTemplate.do?isCheck=false";
	}
	
	//审核
	function auditDeptTarget() {
		
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到审核的数据 ');
			return ; 
		}
		
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditPrmDeptTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
		
	}
	
	//反审核
	function reAuditDeptTarget() {
		
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到反审核的数据 ');
			return ; 
		}
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定取消审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("reAuditPrmDeptTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "科室绩效指标数据采集",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmDeptTargetDataService",
				method_name: "queryPrmDeptTargetPrint",
				bean_name: "prmDeptTargetDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
  //字典下拉框
	function loadDict() {
		
		autocomplete("#target_code", "../quertPrmTargetDict.do?isCheck=false","id", "text", true, true, "", false, "", "300");

		loadComboBox({id:"#dept_kind_code",url:"../queryPrmDeptKind.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,width:'172',selectBoxWidth:'180',defaultSelect:false,async:false,
			
			selectEvent:function(value){
				var fromData={
                	dept_kind_code:value
            	}	
				loadComboBox({id:"#dept_no",url:"../queryPrmDeptDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,parms:fromData,hightLight:true,selectBoxWidth:'auto',defaultSelect:false,async:false});
			}
		});
		
		
		$("#acc_year").ligerTextBox({width : 160});
		
		$("#dept_kind_code").ligerTextBox({width : 160});
		
		$("#dept_code").ligerTextBox({width : 160});
		
		$("#target_code").ligerTextBox({width : 160});
		
		$("#is_audit").ligerComboBox({width : 160});

		$("#dept_no").ligerComboBox({width : 160});
		autodate("#acc_year", "yyyymm");
	}

	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_no"
				type="text" id="dept_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="target_code" type="text" id="target_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核标志：</td>
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
