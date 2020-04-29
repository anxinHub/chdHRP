<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var tree;
    var treeManager =null;
    var template_code;
    var page_code = '${page_code}';
    
    var title = '';
    
    
    $(function (){
    	
        loadDict();//加载下拉框
    	loadHead(null);//加载表格
    	loadTree();//加载树
    	
    	toolbar();
    	$("#tree").css("height", $(window).height()-60);
    	$("#layout1").ligerLayout({leftWidth:200});
    });
    //查询
    function  query(){
		if($('#acct_yearm').val() == ''){
			$.ligerDialog.warn('核算年月不能为空');
			return ; 
		}
		
		var columns = grid.getColumns();//获取列头
    	if(columns.length == 1){
    		$.ligerDialog.warn('请查询表头');
    		return ; 
    	}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		grid.options.parms.push({name:'acct_year',value:$('#acct_yearm').val().substring(0,4)});
		grid.options.parms.push({name:'acct_month',value:$('#acct_yearm').val().substring(4,6)});
		grid.options.parms.push({name:'acct_month',value:$('#acct_yearm').val().substring(4,6)});
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() == "" ? "" :liger.get("dept_id").getValue().split(",")[0]});
		grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() == "" ? "" :liger.get("dept_id").getValue().split(",")[1]});
		grid.options.parms.push({name:'template_code',value:template_code});
		grid.options.parms.push({name:'template_table',value:'${template_table}'});
		grid.loadData(grid.where);//加载查询条件
		
     }
    
	//加载表格
    function loadHead(){
		grid = $("#mainGrid").ligerGrid({
			columns : [
			
			],
			usePager : false,width : '100%',height :'100%',checkbox : true,enabledEdit : true,
			url:'queryHpmTemplateDataForParseData' + page_code + '.do?isCheck=false',
			delayLoad: true,selectRowButtonOnly : true,isAddRow:false,//heightDiff: -10,
			onAfterEdit : f_onAfterEdit,
			rownumbers : true,
		});

    	gridManager = $("#mainGrid").ligerGetGridManager();
    }
	
	
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'add', click: initData, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteData,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('G',initData);
		
		hotkeys('D',deleteData);
		
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
				title: title,//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiTemplateDataService",
				method_name: "queryHpmTemplateDataForParseDataPrint",
				bean_name: "aphiTemplateDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	//编辑列修改
    function f_onAfterEdit(e){
    	
		
		if(e.value == e.oldvalue){
			return false;
		}
		
		if(e.value.toString() == ""){
			gridManager.updateRow(e.record,{target_value:e.oldvalue});
			return ;
		}
		gridManager.updateRow(e.record,{target_value:e.value});
		
		var splitStr = e.column.id.split('__');
		var para = {
			template_table:'${template_table}',
			group_id : e.record.group_id,
			hos_id : e.record.hos_id,
			copy_code : e.record.copy_code,
			dept_id :e.record.dept_id,
			dept_no : e.record.dept_no,
			acct_year : e.record.acct_year,
			acct_month : e.record.acct_month,
			value:e.value
		};
		
		para['columns_table'] = splitStr[1];//列表名
		para['code'] = splitStr[2];//列编码
		if(splitStr.length == 4){
			para['columns_colname'] = splitStr[3];//列字段名称
		}
		
		
		ajaxJsonObjectByUrl("updateHpmTemplateDataForParseData" + page_code + ".do?isCheck=false", para, function(responseData){
			if (responseData.state == "true") {
				query();
			}
		});
    }
    
    //删除
	function deleteData(){
    	
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var rows = 0 ;//记录行
        var map = new HashMap();//存储列名称,判断是否已经存在
        var arr = [];
    	
        
    	var columns = grid.getColumns();//获取列头
    	
    	$.each(columns,function(rowindex,rowdata){//外循环控制列
    		if(rowdata.columnname == undefined || rowdata.columnname == 'undefined'){
	    		return ;
    		}
    		
    		if(rowdata.id == undefined){
    			return ;
    		}
    		var columnname = rowdata.id.split("__");
    		
    		
    		var key = rowdata.columnname;
    		if(map.get(key) == null){
	    		$.each(data,function(i,v){//内循环控制行
	            	if(typeof(v.dept_id) != "undefined" && v.dept_id != ""){
	            		rows += 1;
	    	        	var obj = {
	    	        		group_id : v.group_id,
	    	        		hos_id : v.hos_id,
	    	        		copy_code : v.copy_code,
	    	        		dept_id : v.dept_id,
	    	        		dept_no : v.dept_no,
	    	        		acct_year : v.acct_year,
	    	        		acct_month : v.acct_month
	    	        	}
	    	        	obj['columns_table'] = columnname[1];
	    	        	obj['code'] = columnname[2];
	    	        	arr.push(obj);
	            	}
	            });
	    		map.put(key,key);
    		}
    	});
    	
    	if(rows == 0){
    		$.ligerDialog.warn('请选择数据行 ');
    		return ; 
    	}
    	
    	var para = {
    		template_table:'${template_table}',
    		detail:JSON.stringify(arr)
    	}
    	
    	$.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmTemplateDataForParseData" +page_code+ ".do",para,function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	
	 //字典下拉框
    function loadDict(){
    	
		$("#acct_yearm").ligerTextBox({ width:160 });
   	 	autodate("#acct_yearm","yyyymm");

    	changeAcctYear();//核算年月绑定事件
	}
    
  //核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#dept_id","../queryDeptDictTime.do?isCheck=false","id","text",true,true,para);
    }

    
    //生成
    function initData(){
    	
    	var acct_yearm = $('#acct_yearm').val();
    	if(acct_yearm == ''){
    		$.ligerDialog.warn('核算年月不能为空');
    		return ; 
    	}
    	
    	var columns = grid.getColumns();//获取列头
    	if(columns.length == 1){
    		$.ligerDialog.warn('请查询表头');
    		return ; 
    	}
    	
    	var acct_year = acct_yearm.substring(0,4);    	
    	var acct_month = acct_yearm.substring(4,6); 
    	var template_table = '${template_table}';
    	var conf_code = "";//根据配置编码生成
    	
    	
    	
    	$.each(columns,function(rowindex,rowdata){
    		if(rowdata.columnname == undefined || rowdata.columnname == 'undefined'){
	    		return ;
    		} 
        	
    		if(rowdata.id == undefined ){
    			return ; 
    		}
    		var column_id = rowdata.id.split("__");
    		
    		if(column_id[1] != template_table){
    			return ; 
    		}
    		
    		conf_code = conf_code + "'" + column_id[2] + "',";
    	});
    	
    	if(conf_code.length > 0){
    		conf_code = conf_code.substring(0,conf_code.length-1);
    	}
    	
    	var param = {
        	template_table:template_table,//模板操作表
        	acct_year:acct_year,
        	acct_month:acct_month,
        	conf_code:conf_code
        };
    	
    	$.ligerDialog.confirm('生成将覆盖原有数据,确定生成?',function(yes){
    		if(yes){
		    	ajaxJsonObjectByUrl("initHpmTemplateDataForParseData" + page_code + ".do", param, function(responseData) {
					if(responseData.state == "true"){
						query();
					}
				}); 
    		}
    	});
    }
	 
	//节点选中事件 查询
	function onSelect(note){
		
		template_code = note.data.id;//记录值,作为删除后查询使用参数
		title = note.data.text;//打印页面标题
		
		var formPara = {
			template_table:'${template_table}',
			template_code : note.data.id
		}; 
			
     	//查询表头
		ajaxJsonObjectByUrl("queryHpmTemplateDataColumnHeads.do?isCheck=false", formPara, function(responseData) {
			grid.set('columns', responseData.Rows);
			query();
			grid.reRender();
		}); 
	}
     
     /* 设置树形菜单 */
	function loadTree(){
		ajaxJsonObjectByUrl("queryAphiTemplateDataTree.do?isCheck=false&template_table="+'${template_table}',{},function(responseData) {
    		if (responseData != null) {
    			tree = $("#tree").ligerTree({
    				data : responseData.Rows,
    				checkbox : false,
    				idFieldName : 'id',
    				parentIDFieldName : 'pid',
    				onSelect: onSelect,
    				isExpand: 3,
    				nodeWidth:400
    			});
    						
    			treeManager = $("#tree").ligerGetTreeManager();
    			treeManager.collapseAll();
    		}
    	});
	}
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1">
		<div position="left" title="模板类型"><ul id="tree"></ul></div>
        <div position="center" >
		
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
					<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
						onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
					<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"  /></td>
					<td align="left"></td>
			   	</tr>
			</table>
			
			<div id="toptoolbar"></div>
			<div id="mainGrid"></div>
		</div>  
	</div>
</body>
</html>
