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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadHead(null);//加载表格
    	toobar();

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    
  //模板工具栏
	function toobar(){
    	var obj = [];
    	obj.push({ text: '添加行', id:'add', click: addRow,icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除行', id:'delete', click: deleteRow,icon:'delete' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '保存', id:'save', click: addAphiTemplateWageConf,icon:'save' });
    	obj.push({ line:true });
    	
    	$("#toptool").ligerToolBar({ items: obj});
    }

    
	//加载表格
	function loadHead(){
		grid = $("#mainGrid").ligerGrid({
			columns : [
				{display : '科室绩效工资项目编码',name : 'dept_item_code',width: '25%',align : 'center',textField : 'dept_item_code',
					editor :{
						type : 'select',valueField : 'dept_item_code',textField : 'dept_item_code',
						selectBoxWidth : 410,selectBoxHeight : 200,
						grid : {
							columns : [ {
								display : '科室绩效工资项目编码', name : 'dept_item_code', align : 'left'
							}, {
								display : '科室绩效工资项目名称', name : 'dept_item_name', align : 'left',width:'auto'
							} ],
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_item,
							url : 'queryHpmItem.do?isCheck=false&is_stop=0',
							usePager: true,
							pageSize : 10,
						},
						width: '98%', height: '98%', 
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						}
					}	
				}, 
				
				{display : '科室绩效工资项目名称',name : 'dept_item_name',width: '25%',align : 'center',editor: { type: 'text'}}, 
				
				{display : '职工绩效工资项目编码',name : 'emp_item_code',width: '25%',align : 'center',
					textField : 'emp_item_code',
					editor :{
						type : 'select',valueField : 'emp_item_code',textField : 'emp_item_code',
						selectBoxWidth : 410,selectBoxHeight : 200,
						grid : {
							columns : [ {
								display : '职工绩效工资项目编码', name : 'emp_item_code', align : 'left'
							}, {
								display : '职工绩效工资项目名称', name : 'emp_item_name', align : 'left',width:'auto'
							} ],
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_empItem,
							url : 'queryHpmEmpItem.do?isCheck=false&is_stop=0',
							usePager: true,
							pageSize : 10,
						},
						width: '98%', height: '98%', 
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						}
					}	
				},
				
				{display : '职工绩效工资项目名称',name : 'emp_item_name',width: '25%',align : 'center',editor: { type: 'text'}}
				
			],
			usePager : false,width : '100%',height :'100%',checkbox : true,enabledEdit : true,
			url:'queryHpmDeptEmpMap.do',
			delayLoad: false,selectRowButtonOnly : true,//heightDiff: -10,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit
		});

    	gridManager = $("#mainGrid").ligerGetGridManager();
    }
    
	
  
	function f_onBeforeEdit(e) {
		
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
	}
	
	//删除行
	function deleteRow(){
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
		
		gridManager.deleteSelectedRow2();//删除选择的行
		
	}
	
	//科室项目名称:选中回充数据
	function f_onSelectRow_item(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		
		if (column_name == "dept_item_code") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					dept_item_code : data.dept_item_code,
					dept_item_name : data.dept_item_name
	 			});
			}
	 	}
	 	return true;
	 }
		
	//职工项目名称:选中回充数据
	function f_onSelectRow_empItem(data, rowindex, rowobj) {
		
		selectData = "";
		selectData = data;
		if (column_name == "emp_item_code") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					emp_item_code : data.emp_item_code,
					emp_item_name: data.emp_item_name
	 			});
			}
	 	}
		
	 	return true;
	 }
	 		
	function f_onSelectRow(data, rowindex, rowobj) {
	 	return true;
	}
	 		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
	 	return true;
	 }
	 
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	 			
		return true;
	} 
  
    //添加行
	function addRow(){
		grid.addRow();
	}
    
	
	//加载字典
    function loadDict(){
	}
    
    //保存
    function addAphiTemplateWageConf(){
    	
    	$.ligerDialog.confirm('将清空原有数据,确定保存吗?',
    		function(yes){
    			if(yes){
    		    	
    		    	var data = gridManager.getData();
    		    	
    		    	 var map = new HashMap();//判断数据是否重复
    		    	 var msg = "";//重复提示
    		    	 
    		    	$.each(data,function(index,content){
    		        	if(	typeof(content.dept_item_code) != "undefined" && content.dept_item_code != "" && typeof(content.emp_item_code) != "undefined" && content.emp_item_code != ""	
    		        	){
    		        		var key = content.dept_item_code + "|" + content.emp_item_code;
    		        		var value = '第'+(index+1)+'行'; 
    		        		
    		        		//判断子目标编码是否重复
    					 	if (!map.get(key)) {
    					 		map.put(key, value);
    						}else{
    							msg += value + '与' + map.get(key) + '重复\n ';
    						}
    		        	}
    		        });
    		    	 
    		    	if(msg != ''){
    		    		$.ligerDialog.warn(msg);
    		    		return ; 
    		    	}
    		    	
    		    	
    		    	var param = {
    		    		detail_data : JSON.stringify(data)
    		    	};
    		    	
    		    	ajaxJsonObjectByUrl("saveHpmDeptEmpMap.do", param, function(responseData) {
    					if(responseData.state == "true"){
    						query();
    					}
    				});  
    			}
    		}
    	);
    }
	 
	
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1">
            
            <div position="center" >
           	<div id="toptool"></div>
			    <div id="mainGrid"></div>
            </div>  
        </div>
</body>
</html>
