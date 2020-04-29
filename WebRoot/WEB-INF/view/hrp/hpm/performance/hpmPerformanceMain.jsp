<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp"/>
<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}  
</style>
<script type="text/javascript">

    var grid;
    var gridManager = null;
	
    
    //页面初始化
    $(function (){
    	
    	
        loadDict();//加载下拉框
        
    	loadHead(null);//加载数据
    	
    	toolbar();//加载工具栏
    	
    	loadHotkeys();//加载快捷键
    	
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
    	//grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[0] : '' }); 
    	//grid.options.parms.push({name:'emp_no',value:liger.get("emp_id").getValue() ? liger.get("emp_id").getValue().split(",")[1] : '' });
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	
    	
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           	columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmPerformance.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,
            selectRowButtonOnly:true,
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
	
	}
    
	
	
	
	
	
	//字典下拉框
    function loadDict(){
    	
   		//autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,'',true);
   		
   		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);

    	//autocomplete("#emp_id","../queryEmpDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true,'',true);

    	$("#acct_yearm").ligerTextBox({width:160 });
    	
    	autodate("#acct_yearm","yyyymm");
    	

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

<body style="padding: 0px; overflow: hidden;"  onload="grid_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" id="acct_yearm" class="Wdate" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="queryGrantMoney();"/></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工姓名：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td> -->
		</tr>
			
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>
