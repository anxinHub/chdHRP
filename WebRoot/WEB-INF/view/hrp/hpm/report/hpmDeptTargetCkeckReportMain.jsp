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
  	
    
    //页面初始化
    $(function (){
    	$("#acct_yearm").ligerTextBox({width:160 });autodate("#acct_yearm","yyyymm");

        loadDict();//加载数据
    	loadHead(null);	//加载grid
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
        
    });
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		
    	grid_setColumns();
		grid.options.parms=[];
		grid.options.newPage=1;
		
		if($("#acct_yearm").val() == ''){
			$.ligerDialog.warn('请选择核算年月 ');
			return ; 
		}

		grid.options.parms.push({name:'year_month',value: $("#acct_yearm").val()});
		grid.options.parms.push({name:'select_para',value:'ZN'});
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue() == null ? '' :liger.get("dept_kind_code").getValue()}); 
    	
    	grid.loadData(grid.where);//加载查询条件
     }

    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns : [],
			dataAction : 'server',dataType : 'server',usePager : true,
			url : 'queryHpmDeptTargetCkeckReport.do',width : '100%',height : '100%',checkbox : false,
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
				title: "职能科室考核明细表",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiCustomReportService",
				method_name: "queryHpmDeptTargetCkeckReportPrint",
				bean_name: "aphiCustomReportService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
	/* function deptSelector(){
    	
    	$.ligerDialog.open({ url : '../hpmdepttargetdata/hpmBookDeptSelectorPage.do?isCheck=false&listBoxData='+obj_box_data+'&flag='+$("#flag").val()+'&sign=1' ,data:{}, height: 500,
    			width: 1000, title:'科室名称选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ 
    		    		    { text: '确定', onclick: function (item, dialog) { 
    		    		    	dialog.frame.saveSelectData();
    		    		    	dialog.close();
    		    		    	
    		    		    },cls:'l-dialog-btn-highlight' }, 
    		    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
     								
    } */
    
    
    /* function depttarget(){
    	
    	$.ligerDialog.open({ url : '../hpmdepttargetdata/targetSelectorPage.do?isCheck=false',
    			 height: 500,width: 1000, title:'指标名称选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			 buttons: [ 
   		    		    { text: '确定', onclick: function (item, dialog) { 
   		    		    	dialog.frame.saveSelectTarge();
   		    		    	dialog.close();
   		    		    
   		    		    },cls:'l-dialog-btn-highlight' }, 
   		    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    	
   } */
	
   	//加载字典
   	function loadDict(){
            //字典下拉框
    	var param={
    			target_nature:"03",
    			method_code:"01"
    	}
        //autocompleteAsyncMulti("#target_code", "../queryTargetMethod.do?isCheck=false", "id", "text", true, true,param);
    	//autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
            
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true); 
    	
    	//autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true);
    	//autocompleteAsyncMulti("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true);
    	
    }   
    
   
   	//查询动态列
    function grid_setColumns() {
    	
    	var flag  = false; 

		var year_month = $("#acct_yearm").val();
		if(year_month == ''){
			$.ligerDialog.warn('请选择核算年月 ');
			return ; 
		}
		
		var formPara = {
			acct_year : year_month.substring(0, 4),
			acct_month : year_month.substring(4, 6),
			select_para : 'ZN',
			dept_kind_code : liger.get("dept_kind_code").getValue() == null ? '' : liger.get("dept_kind_code").getValue()
			//target : liger.get("target_code").getValue().replace(/,/g,'@') == ""? "" : liger.get("target_code").getValue().replace(/,/g,'@'),
			
			//dept :liger.get("dept_id").getValue().replace(/,/g,'@') == ""? "" : liger.get("dept_id").getValue().replace(/,/g,'@'),
		};

		ajaxJsonObjectByUrl("queryDeptTargetCkeckReportHead.do?isCheck=false", formPara,function(responseData) {
			if (responseData != null) {
				grid.set('columns', responseData);
				grid.reRender();
			}
		});

	}


    </script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="grid_setColumns();">
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="flag" name="flag"/>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    
	 <tr>
	 		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acct_yearm"  class="Wdate"  type="text" id="acct_yearm" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
	 		<td align="left"></td>
	 		
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  /></td>
             <td>
            	<input class="l-button l-button-test"  type="button" value="选择" onclick="deptSelector();"/>
            </td>
            <td align="left"></td> -->
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" /></td>
            <td align="left"></td>
            
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" /></td>
            <td>
            	<input class="l-button l-button-test"  type="button" value="选择" onclick="depttarget();"/>
            </td>
            <td align="left"></td> -->
        </tr> 
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
	
</body>
</html>
