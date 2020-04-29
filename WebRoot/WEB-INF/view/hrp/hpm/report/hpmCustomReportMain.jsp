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
    var obj_box_data = "";
    var subj_box_data = "";
    var is_show_print = '${is_show_print}' != '1' ? true:false;
    
    
    
    //页面初始化
    $(function (){
    	$("#begin_date").ligerTextBox({width:66});autodate("#begin_date","yyyymm");
    	$("#end_date").ligerTextBox({width:66});autodate("#end_date","yyyymm");

        loadDict();//加载数据
    	loadHead(null);	//加载grid
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];
		grid.options.newPage=1;
		
		if($("#begin_date").val() == ''){
			$.ligerDialog.warn('请选择核算开始年月 ');
			return ; 
		}
		
		if($("#end_date").val() == ''){
			$.ligerDialog.warn('请选择核算结束年月 ');
			return ; 
		}
		
    	grid.options.parms.push({name:'begin_date',value: $("#begin_date").val()}); 
    	grid.options.parms.push({name:'end_date',value: $("#end_date").val()}); 
    	
    	var target = liger.get("target_code").getValue().replace(/,/g,'@') == ""? "" : liger.get("target_code").getValue().replace(/,/g,'@');
    	var dept = liger.get("dept_id").getValue().replace(/;/g,',') == ""? "" : liger.get("dept_id").getValue().replace(/;/g,',');
    	var deptKind = liger.get("dept_kind_code").getValue() == null ? '' :liger.get("dept_kind_code").getValue();
    	
    	if(target == '' && dept == '' && deptKind ==''){
    		$.ligerDialog.warn('科室分类、科室名称、指标名称必须选择其中一个');
    		return ; 
    	}
    	
    	grid_setColumns();
		grid.options.parms.push({
			name:'target',
			value:target
		}); 
		grid.options.parms.push({
			name:'dept',
			value:dept
		}); 
    	
    	grid.options.parms.push({
    		name:'dept_kind_code',
    		value:deptKind
    	}); 
    	

    	grid.loadData(grid.where);//加载查询条件
	}
	
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns : [],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmCustomReport.do?isCheck=false',width : '100%',height : '100%',checkbox : true,
			rownumbers : true,enabledEdit : true,delayLoad : true,selectRowButtonOnly : true//heightDiff: -10,
    	
    	});
    	
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
    
	
	//科室选择器
	function deptSelector(){
    	
    	$.ligerDialog.open({ 
    		url : '../hpmdepttargetdata/hpmBookDeptSelectorPage.do?isCheck=false&listBoxData='+obj_box_data+'&flag='+$("#flag").val()+'&sign=1' ,
    		data:{}, 
    		title:'科室名称选择器',height: 500,width: 1000,modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
		   		{ text: '确定', onclick: 
		   			function (item, dialog) { 
	    		    	dialog.frame.saveSelectData();
	    		    	dialog.close();
		    		},cls:'l-dialog-btn-highlight' 
		    	}, 
    		    { text: '取消', onclick: 
    		    	function (item, dialog) { 
    		    		dialog.close(); 
    		    	} 
		    	} 
		    ] 
    	});
    }
	
	
	//指标选择器
    function depttarget(){
    	$.ligerDialog.open({ 
    		url : '../hpmdepttargetdata/hpmtargetSelectorPage.do?isCheck=false',
    		height: 500,width: 1000, title:'指标名称选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
   				{ text: '确定', onclick: 
   					function (item, dialog) { 
 		    			dialog.frame.saveSelectTarge();
 		    			dialog.close();
   		    		},cls:'l-dialog-btn-highlight' 
   		    	}, 
   		    	
   		    	{ text: '取消', onclick: 
   		    		function (item, dialog) { 
   		    			dialog.close(); 
   		    		} 
   		    	} 
   		    ] 
    	});
	}
	
	
  	//字典下拉框
    function loadDict(){
            
    	var param={
    		target_nature:"03"
    	}
        //autocompleteAsyncMulti("#target_code", "../queryTargetMethod.do?isCheck=false", "id", "text", true, true,param);
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true); 
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	//autocompleteAsyncMulti("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true);
    	
    }   
    
  	
  	//查询动态列
    function grid_setColumns() {
    	
    	var flag  = false; 

		var begin_date = $("#begin_date").val();
		if(begin_date == ''){
			$.ligerDialog.warn('请选择核算开始年月 ');
			return ; 
		}
		
		var end_date = $("#end_date").val();
		if(end_date == ''){
			$.ligerDialog.warn('请选择核算结束年月 ');
			return ; 
		}
		
		var formPara = {
			begin_date : begin_date,
			end_date : end_date,
			target : liger.get("target_code").getValue().replace(/,/g,'@') == ""? "" : liger.get("target_code").getValue().replace(/,/g,'@'),
			dept :liger.get("dept_id").getValue().replace(/;/g,',') == ""? "" : liger.get("dept_id").getValue().replace(/;/g,','),
			dept_kind_code : liger.get("dept_kind_code").getValue() == null ? '' : liger.get("dept_kind_code").getValue()

		};

		ajaxJsonObjectByUrl("queryHpmCustomReportHead.do?isCheck=false", formPara,function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData);
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
				title: "自定义指标表",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiCustomReportService",
				method_name: "queryHpmCustomReportPrint",
				bean_name: "aphiCustomReportService"/* ,
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

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="flag" name="flag"/>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    
	 	<tr>
	 		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="begin_date"  class="Wdate"  type="text" id="begin_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
            </td>
	 		<td align="left">至:</td>
	 		<td align="left" class="l-table-edit-td" >
            	<input name="end_date"  class="Wdate"  type="text" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
	 		</td>
	 		<td align="left"></td>
           
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td" colspan="2"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" /></td>
            <td align="left"></td>
            
        </tr> 
        
        <tr>
            
             <td align="left" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
             <td>
            	<input class="l-button l-button-test"  type="button" value="选择" onclick="deptSelector();"/>
            </td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" /></td>
            <td>
            	<input class="l-button l-button-test"  type="button" value="选择" onclick="depttarget();"/>
            </td>
            <td align="left"></td>
        </tr>
    </table>
    

	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
	
</body>
</html>
