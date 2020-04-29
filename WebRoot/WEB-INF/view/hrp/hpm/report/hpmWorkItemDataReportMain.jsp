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
    
    //初始化加载
	$(function (){
		$("#acct_yearm").ligerTextBox({ width:160 });
		autodate("#acct_yearm","yyyymm");
		
        loadDict();//加载字典
    	loadHead(null);	//加载grid
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
	
	
    //查询
    function  query(){
        //根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'work_item_code',value:liger.get("work_item_code").getValue()}); 
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    //加载grid
	function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室编码', name: 'dept_code', align: 'left'},
				{ display: '科室名称', name: 'dept_name', align: 'left'},
                { display: '工作量指标编码', name: 'work_item_code', align: 'left'},
				{ display: '工作量指标名称', name: 'work_item_name', align: 'left'},
                { display: '工作量', name: 'work_amount', align: 'left'},
				{ display: '计件标准', name: 'work_standard', align: 'left'}
            ],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmWorkItemDataReport.do',
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
				title: "工作量数据准备结果",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiWorkitemDataReportService",
				method_name: "queryWorkitemDataReportPrint",
				bean_name: "aphiWorkitemDataReportService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}

   
    //字典下拉框
    function loadDict(){
    	
    	autocomplete("#dept_kind_code","../../hpm/queryDeptKindDict.do?isCheck=false","id","text",true,true);  
    	changeAcctYear();
	}
    
  	//核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#work_item_code","../../hpm/queryHpmWorkitemSeqTime.do?isCheck=false","id","text",true,true,para);
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,para);
    }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标：</td>
			<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

	
</body>
</html>
