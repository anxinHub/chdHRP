<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">


	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	//页面初始化
	$(function() {
		$("#acct_yearm").ligerTextBox({width : 160});
		autodate("#acct_yearm","yyyymm");
		
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
	});
	
	
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acct_yearm',
			value : $("#acct_yearm").val()
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(",")[1]
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '科室编码',name : 'dept_id',align : 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href='#' onclick=\"openUpdate('"
							+ rowdata.dept_id + "','"
							+ rowdata.dept_no+"','"
							+ rowdata.acct_year + "','"
							+ rowdata.acct_month + "');\" >"
							+ rowdata.dept_id + "</a>";
					}
				},
				
				{display : '科室名称',name : 'dept_name',align : 'left'},
				
				{display : '财务收益',name : 'f_score',align : 'right',editor : {type : 'float'},render : 
					function(rowdata, rowindex, value) {
						return formatNumber(rowdata.f_score, 2, 1);
					}
				},
							
				{display : '客户关系',name : 'c_score',align : 'right',editor : {type : 'float'},render : 
					function(rowdata, rowindex, value) {
						return formatNumber(rowdata.c_score, 2, 1);
					}
				},
				
				{
					display : '内部流程',name : 'p_score',align : 'right',editor : {type : 'float'},render : 
						function(rowdata, rowindex, value) {
							return formatNumber(rowdata.p_score, 2, 1);
						}
				},
				
				{display : '学习成长',name : 'l_score',align : 'right',editor : {type : 'float'},render : 
					function(rowdata, rowindex, value) {
						return formatNumber(rowdata.l_score, 2, 1);
					}
				},
				
				{display : '综合得分',name : 'root_score',align : 'right',editor : {type : 'float'},render : 
					function(rowdata, rowindex, value) {
						return formatNumber(rowdata.root_score, 2,1);
					}
				} 
			],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptScoreData.do?isCheck=false',
			width : '100%',height: '100%',checkbox : true,rownumbers : true,//heightDiff: -10,
			selectRowButtonOnly : true,isAddRow:false,enabledEdit:true,onAfterEdit : f_onAfterEdit,
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.dept_id,rowdata.dept_no,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
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
				title: '科室绩效结果准备',//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptScoreDataService",
				method_name: "queryHpmDeptScoreDataPrint",
				bean_name: "aphiDeptScoreDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptScoreData, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptScoreData,icon:'delete' });
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
		hotkeys('A',addDeptScoreData);
		hotkeys('D',deleteDeptScoreData);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
		hotkeys('P',print);
	}
	
	
	
	//添加
	function addDeptScoreData(){
		$.ligerDialog.open({
			url : 'addHpmDeptScoreDataPage.do?isCheck=false',
			title : '添加',height : 320,width : 500,modal : true,
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
	function deleteDeptScoreData(){
		var acct_year = $("#acct_yearm").val();
		if (acct_year == "") {
			$.ligerDialog.warn('请选择年月');
			return false;
		}
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		
		var checkIds = [];
		$(data).each(function() {
			checkIds.push(this.dept_id + ";" +this.dept_no+";"+ acct_year);//实际代码中temp替换主键
		});
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteHpmDeptScoreData.do", {
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
		location.href = "downTemplateHpmDeptScoreData.do?isCheck=false";
	}
	
	
	//导入
	function importData(){
		//$.ligerDialog.open({url: 'DeptScoreDataPage.do?isCheck=false', height: 430,width: 960, isResize:true, title:'导入'});
      	parent.$.ligerDialog.open({ 
      		url:'hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataImpotrPage.do?isCheck=false',
   			data:{columns : grid.columns, grid : grid},
   			title:'科室绩效结果数据准备导入',height: 300,width: 450,modal:true,
   			showToggle:false,showMax:true,showMin: false,
    		isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	
	
	function openUpdate(dept_id,dept_no, acct_year, acct_month) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'updateHpmDeptScoreDataPage.do?isCheck=false&dept_id='
					+ dept_id +'&dept_no='+dept_no+ '&acct_year=' + acct_year + '&acct_month='
					+ acct_month,
			data : {},
			title : '修改',height : 320,width : 500,modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			buttons : [ 
				{text : '确定',onclick : 
					function(item, dialog) {
						dialog.frame.saveEmpTargetData();
					},cls : 'l-dialog-btn-highlight'
				}, 
				{text : '取消',onclick : 
					function(item, dialog) {
						dialog.close();
					}
				} 
			]
		});

	}
	function loadDict() {
		//字典下拉框

		autocomplete("#dept_id", "../queryDeptDictByPerm.do?isCheck=false", "id","text", true, true);
	}

	
	//编辑单元格
	function f_onAfterEdit(e){
		if(e.column.columnname == "root_score"){
			if(e.value == e.oldvalue){
				return false;
			}
			
			if(e.record.f_score == "" || e.record.c_score == "" || e.record.l_score == "" || e.record.p_score == "" || e.record.root_score == ""){
				return false;
			}
			
			var para = {
					f_score : e.record.f_score,
					c_score : e.record.c_score,
					l_score : e.record.l_score,
					p_score : e.record.p_score,
					root_score : e.record.root_score,
					dept_id : e.record.dept_id,
					dept_no : e.record.dept_no,
					acct_year : e.record.acct_year,
					acct_month : e.record.acct_month
					
				};
			 ajaxJsonObjectByUrl("updateHpmDeptScoreData.do?isCheck=false", para, function(responseData){
				if(responseData.state=="true"){
        			query();
        		}
				
			});
			
		}
		
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" id="acct_yearm" ltype="text" class="Wdate"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

		</tr>

	</table>
	
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
