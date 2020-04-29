<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

	var grid;
	var gridManager = null;
	
	
	//页面初始化
    $(function (){
    	
		$("#year_month_start").ligerTextBox({ width:160 });autodate("#year_month_start","yyyymm");
		$("#year_month_end").ligerTextBox({ width:160 });autodate("#year_month_end","yyyymm");
        
		loadDict();//加载下拉框
    	loadHead(null);	//加载数据
    	
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
	
	
	
    //查询
    function  query(){
    	
    	//根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
        
		grid.options.parms.push({name : 'year_month_start',value:$("#year_month_start").val()}); 
		grid.options.parms.push({name : 'year_month_end',value:$("#year_month_end").val()});
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(",")[0] : '' }); 
		grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue() ? liger.get("dept_code").getValue().split(",")[1] : '' }); 
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_code").getValue() ? liger.get("emp_code").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'emp_no',value:liger.get("emp_code").getValue() ? liger.get("emp_code").getValue().split(",")[1] : '' }); 

    	
    	grid.options.parms.push({name : 'acct_year_start',value:$("#year_month_start").val().substring(0, 4)}); 
		grid.options.parms.push({name : 'acct_month_start',value:$("#year_month_start").val().substring(4, 6)}); 
		
		grid.options.parms.push({name : 'acct_year_end',value:$("#year_month_end").val().substring(0, 4)}); 
		grid.options.parms.push({name : 'acct_month_end',value:$("#year_month_end").val().substring(4, 6)}); 
    	
    	
    	
    	grid.loadData(grid.where);//加载查询条件
	}
	
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpBonusCalculation.do',
			width: '100%',height: '100%',   checkbox: false,rownumbers:true,delayLoad:true,
			selectRowButtonOnly:true//heightDiff: -10,
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
      	var obj = [];
      	
      	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
      	obj.push({ line:true });
      	
      	$("#toptoolbar").ligerToolBar({ items: obj});
	}
 	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('P',print);
	}

    
  	//字典下拉框 
    function loadDict(){
            
    	autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false","id","text",true,true);
    	
    	$("#acct_year_month").ligerTextBox({width:160 });
   	} 
    
    //加载动态表头
    function grid_setColumns() {
		ajaxJsonObjectByUrl("queryHpmEmpBonusCalculationGrid.do?isCheck=false", {},function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData.Rows);
				grid.reRender();
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
				title: "职工绩效工资核算计算表",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiEmpBonusCalculationService",
				method_name: "queryEmpBonusCalculationByReportPrint",
				bean_name: "aphiEmpBonusCalculationService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开始年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" class="Wdate" name="year_month_start" type="text" id="year_month_start" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">结束年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" class="Wdate" name="year_month_end" type="text" id="year_month_end" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
