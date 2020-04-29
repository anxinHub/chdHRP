<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">


	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	//页面初始化
	$(function() {

		loadDict();//加载字典
		loadHead(null);//加载grid
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	
	//查询
	function query() {//根据表字段进行添加查询条件
		
		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({name : 'acct_yearm',value : $("#acct_yearm").val()});
		grid.options.parms.push({name : 'emp_name',value : "%" + $("#emp_name").val() + "%"});
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'dept_no',value : liger.get("dept_id").getValue().split(",")[1]});

		grid.loadData(grid.where);//加载查询条件
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({ 	
			columns : [
				{display : '职工编码',name : 'emp_id',align : 'left',width:'120',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('" 
								+ rowdata.emp_id + "','" 
								+ rowdata.emp_no+"','"
								+ rowdata.acct_year + "','" 
								+ rowdata.acct_month
								+ "');\" >" 
								+ rowdata.emp_id + "</a>";
					}
				},
				
				{display : '职工名称',name : 'emp_name',align : 'left',width:'240'}, 
				{display : '职务名称',name : 'duty_name',align : 'left',width:'240'}, 
				{display : '岗位系数',name : 'emp_duty_amount',align : 'right',width:'120',editor:{type : 'float'},render : 
					function(rowdata, rowindex, value) {
						var emp_duty_amount = rowdata.emp_duty_amount;
						if (emp_duty_amount == null || emp_duty_amount == '') {
							emp_duty_amount = 0
						}
						return formatNumber(emp_duty_amount, 2, 1);
					}
				} ],
				dataAction : 'server',dataType : 'server',usePager : true,url : 'queryHpmEmpDutyData.do?isCheck=false',
				width : '100%',height : '100%',checkbox : true,rownumbers : true,
				delayLoad:true,selectRowButtonOnly : true,//heightDiff: -10,
				enabledEdit:true,onAfterEdit : f_onAfterEdit,isAddRow:false,
				onDblClickRow : function(rowdata, rowindex, value) {
					openUpdate(rowdata.emp_id,rowdata.emp_no, rowdata.acct_year, rowdata.acct_month);//实际代码中temp替换主键
				}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		//gridManager.onLoaded(query()), formatTrueFalse();
	}
	
	
	
	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addEmpDutyData, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteEmpDutyData,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: createEmpDutyData,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	
	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('G',createEmpDutyData);
		hotkeys('A',addEmpDutyData);
		hotkeys('D',deleteEmpDutyData);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
		hotkeys('P',print);
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
				title: '职工岗位系数准备',//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiEmpDutyDataService",
				method_name: "queryEmpDutyDataPrint",
				bean_name: "aphiEmpDutyDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
	//添加
	function addEmpDutyData(){
		
		$.ligerDialog.open({
			url : 'addHpmEmpDutyDataPage.do?isCheck=false',data : {},
			title : '添加',height : 350,width : 500,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveEmpTargetData();
					},cls : 'l-dialog-btn-highlight'
				}, {text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});
	}
	
	
	//删除
	function deleteEmpDutyData(){
		
		var acct_year = $("#acct_yearm").val();
		if (acct_year == "") {
			alert("请选择年月！");
			return false;
		}
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		}
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.emp_id+";"+ this.emp_no + ";" + acct_year);//实际代码中temp替换主键
		});
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmEmpDutyData.do", {
					checkIds : checkIds.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	
	//下载导入模板
	function downTemplate(){
		location.href = "downTemplateHpmEmpDutyData.do?isCheck=false";
	}
	
	
	//导入
	function importData(){
		parent.$.ligerDialog.open({ url:'hrp/hpm/hpmempdutydata/hpmEmpDutyDataImportPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			height: 300,
   			width: 450,
   			title:'职工岗位系数数据准备导入',
   			modal:true,
   			showToggle:false,
   			showMax:true,
			showMin: false,
			isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	
	
	//生成
	function createEmpDutyData(){
		
		var acct_yearm = $("#acct_yearm").val();
		var dept_id = liger.get("dept_id").getValue().split(",")[0]==null?"":liger.get("dept_id").getValue().split(",")[0];
		var dept_no = liger.get("dept_id").getValue().split(",")[1]==null?"":liger.get("dept_id").getValue().split(",")[1];
		
		if (acct_yearm == '') {
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}

		$.ligerDialog.open({
			url : 'initHpmEmpDutyDataPage.do?isCheck=false&acct_yearm=' + acct_yearm + '&dept_id=' + dept_id + '&dept_no=' +dept_no,
			title : '生成',height : 200,width : 400,modal : true,
			showToggle : false,showMax : false,showMin : true,isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDeptEmpDataInit();
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
	
	
	//修改页
	function openUpdate(emp_id,emp_no, acct_year, acct_month) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'updateHpmEmpDutyDataPage.do?isCheck=false&emp_id=' 
					+ emp_id + '&emp_no=' +emp_no+ '&acct_year=' + acct_year + '&acct_month=' + acct_month,
			data : {},
			title : '修改',height : 250,width : 400,modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveEmpTargetData();
					},cls : 'l-dialog-btn-highlight'
				}, {text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});

	}
	
	//单元格编辑事件
	function f_onAfterEdit(e){
		if(e.column.columnname == "emp_duty_amount"){
			if(e.value == e.oldvalue){
				return false;
		 	}
			if(e.record.emp_duty_amount == ""){
				return false;
			}
			
			var para = {
				emp_id : e.record.emp_id,
				emp_no : e.record.emp_no,
				acct_year : e.record.acct_year,
				acct_month : e.record.acct_month,
				emp_duty_amount : e.record.emp_duty_amount
			};
			
			ajaxJsonObjectByUrl("updateHpmEmpDutyData.do?isCheck=false", para, function(responseData){
				if(responseData.state=="true"){
        			query();
        		}
			});
			
		}
		
	}
	
	
	//加载下拉字典
	function loadDict() {
		$("#acct_yearm").ligerTextBox({width : 160});
		autodate("#acct_yearm","yyyymm");
		$("#emp_name").ligerTextBox({width : 160});
		autocomplete("#dept_id", "../queryDeptDictByPerm.do?isCheck=false", "id", "text", true, true);
	}

	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" class="Wdate" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
