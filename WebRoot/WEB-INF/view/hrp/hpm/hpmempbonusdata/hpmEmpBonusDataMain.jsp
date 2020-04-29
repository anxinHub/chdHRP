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
    
    var audit_state = {
			Rows : [ 
				{"id" : "-1","text" : "请选择"},
				{"id" : "0","text" : "否"},
				{"id" : "1","text" : "是"}
			],
			Total : 3
	};
	
    
    //页面初始化
    $(function (){
    	autodate("#acct_yearm","yyyymm");
    	
        loadDict();//加载下拉框
    	loadHead(null);//加载数据
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    	
    	$('#item_code').bind('change',function(){query();});
    });
    
	
    //查询
    function  query(){
    	
    	grid_setColumns();
    	  
    	//根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
        
        grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : '' }); 
    	grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'emp_no',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[1] : '' }); 
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	
		if(liger.get("is_audit").getValue() != "" && liger.get("is_audit").getValue() != "-1"){
			grid.options.parms.push({name:'is_audit',value:liger.get("is_audit").getValue()}); 
		}    	

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [],
           dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpBonusDataForReport.do',
           width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
           selectRowButtonOnly:true
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '科室状态查看（<u>V</u>）', id:'search', click: deptstate_query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('V',deptstate_query);
		
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
				title: "二次分配查询",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiEmpBonusDataService",
				method_name: "queryHpmEmpBonusDataForReportPrint",
				bean_name: "aphiEmpBonusDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
  	
  	//科室状态查看
    function deptstate_query(){
    	$.ligerDialog.open({
			url : 'hpmEmpBonusDataDeptStatePage.do?isCheck=false',data : {},
			title : '科室状态查看',height : $(window).height(),width :800,top:1,modal : true,
			showToggle : false,showMax : false,showMin : false,isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
    }
    
  	
  	//字典下拉框
    function loadDict(){
    	autoCompleteByData("#is_audit",audit_state.Rows,"id","text",true,true);
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#emp_id","../queryEmpDict.do?isCheck=false","id","text",true,true);
    	$("#acct_yearm").ligerTextBox({width:160 });
    	autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true,'',true);
    }  
  	
  	//查询动态列
    function grid_setColumns() {
		

		ajaxJsonObjectByUrl("queryHpmEmpBonusDataForReportGrid.do?isCheck=false", {item_code:liger.get("item_code").getValue()},function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData.Rows);
				grid.reRender();
			}
		});
	}

    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" id="acct_yearm" class="Wdate" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>	
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否审核：</td>
			<td align="left" class="l-table-edit-td"><input name="is_audit" type="text" id="is_audit" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
