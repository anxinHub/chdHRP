<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var tree;
    var treeManager =null;
    var template_code;
	var saveFlag = true;//执行新增的时候为true 修改的时候为false
	//template_table
	var template_table = {
		Rows : [ 
			{
				"id" : "aphi_workitem_data",
				"text" : "工作量数据准备表"
			},
			{
				"id" : "aphi_incomeitem_data",
				"text" : "收入数据准备表"
			},
			{
				"id" : "aphi_costitem_data",
				"text" : "支出数据准备表"
			},
			{
				"id" : "aphi_incomeitem_point_data",
				"text" : "核算项目点数准备表"
			}/* ,
			{
				"id" : "aphi_costitem_data",
				"text" : "可控成本项目准备表"
			} */
			
		],
		Total : 5
	};
	
	//columns_type
	var columns_type_state = {
		Rows : [
			{"columns_type" : "01","columns_type_name" : "取值项"},
			{"columns_type" : "02","columns_type_name" : "关联项"},
			{"columns_type" : "03","columns_type_name" : "小计"},
			{"columns_type" : "04","columns_type_name" : "合计"}
		]
	};
	
	var columns_type = columns_type_state.Rows;
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadHead(null);//加载表格
    	loadTree();//加载树
    	toobar();//加载模板工具栏
    	detailtoolbar();//加载模板明细工具栏
    	loadHotkeys();//加载快捷键
    	
    	$("#tree").css("height", $(window).height()-60);
    	$("#layout1").ligerLayout({leftWidth:200});

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name:'template_code',value:template_code});
		grid.loadData(grid.where);//加载查询条件
		
     }
    
	//加载表格
    function loadHead(){
		grid = $("#mainGrid").ligerGrid({
			columns : [
				{display : '明细编码',name : 'template_detail_code',width: '8%',align : 'center',editor: { type: 'text'}}, 
				{display : '组名称',name : 'group_view',width: '10%',align : 'left',editor: {type:'text'}},
				{display : '列类型',name : 'columns_type',align : 'left',width:'10%',align : 'left',editor: 
					{ type: 'select', data: columns_type, valueField: 'columns_type', textField: 'columns_type_name' }, 
					render: function (item){
	                	for (var i = 0; i < columns_type.length; i++){
	                    	if (columns_type[i]['columns_type'] == item.columns_type)
	                        	return columns_type[i]['columns_type_name']
	                        }
	                	return item.columns_type_name;
	                }
				},
				{display : '列表名',name : 'columns_table',align : 'left',width:'15%',align : 'left',editor: {type:'text'}},
				{display : '列字段',name : 'columns_view',align : 'left',width:'30%',align : 'left',editor: {type:'text'}}
			],
			usePager : false,width : '100%',height :'100%',checkbox : true,enabledEdit : true,
			url:'queryAphiTemplateDetailDataByTemplateCode.do?isCheck=false',
			delayLoad: true,selectRowButtonOnly : true//heightDiff: -10,
		});

    	gridManager = $("#mainGrid").ligerGetGridManager();
    }
	
  	//模板工具栏
	function toobar(){
    	var obj = [];
    	obj.push({ text: '添加新模板（<u>A</u>）', id:'add', click: clearData,icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除模板（<u>R</u>）', id:'delete', click: deleteHpmTemplateData,icon:'delete' });
    	obj.push({ line:true });
    	
    	$("#toptool").ligerToolBar({ items: obj});
    }
	
  	//模板明细工具栏
	function detailtoolbar(){
		var obj = [];
		
		obj.push({ text: '添加行（<u>E</u>）', id:'add', click: addRow,icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '模板保存（<u>S</u>）', id:'save', click: saveAphiTemplateData, icon:'save' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除明细（<u>D</u>）', id:'delete', click: deleteTemplateDetail,icon:'delete' });
       	obj.push({ line:true });
       	
       	
       	$("#detailtool").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		hotkeys('A',clearData);
		hotkeys('R',deleteHpmTemplateData);
		hotkeys('E',addRow);
		hotkeys('S',saveAphiTemplateData);
		hotkeys('D',deleteTemplateDetail);
		 
	}
    
    //添加行
	function addRow(){
		grid.addRow();
	}
    
	//删除模板
	function deleteHpmTemplateData(){
		
		if(template_code == "" || template_code == undefined){
			$.ligerDialog.warn('请选择模板');
			return ; 
		}
		
		var para = {
			template_code:template_code	
		}
		
		$.ligerDialog.confirm('确定删除模板?',function(yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteHpmTemplateData.do",para,function (responseData){
            		if(responseData.state=="true"){
            			$("#template_code").val('');
            			$("#template_name").val('');
            			$("#template_note").val('');
            			$("#template_table").val('');
            			loadTree();//加载树
            			//grid.reRender();
            			loadHead(null);//加载表格
            		}
            	});
			}
		});
	}
	
    //删除明细
	function deleteTemplateDetail(){
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var arr = [];
        var rows = 0 ;
        $.each(data,function(rowindex,item){
        	if(typeof(item.template_code) != "undefined" && typeof(item.template_detail_code) != "undefined"){
        		rows += 1;
	        	arr.push(
	        		item.group_id+"@"+
	        		item.hos_id+"@"+
	        		item.copy_code+"@"+
	        		item.template_code+"@"+
	        		item.template_detail_code
	        	);
        	}
        });
        
        if(rows == 0 ){
        	$.ligerDialog.warn('请选择数据 ');
        	return ;
        }
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmTemplateDataDetail.do",{ParamVo:arr.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	//加载主表字段
    function loadDict(){
    	
    	autoCompleteByData("#template_table",template_table.Rows,"id","text",true,true);
    	$("#template_code").ligerTextBox({width : 160});
    	$("#template_name").ligerTextBox({width : 160});
    	$("#template_note").ligerTextBox({width : 160});
    	$("#template_table").ligerTextBox({width : 160});
	}
    
    //保存
    function saveAphiTemplateData(){
    	
    	//1.主表判断
    	var template_code = $('#template_code').val();
    	if(template_code == ''){
    		$.ligerDialog.warn('模板编码不能为空');
    		return ; 
    	}
    	
    	var template_name = $('#template_name').val();
    	if(template_name == ''){
    		$.ligerDialog.warn('模板名称不能为空');
    		return ; 
    	}
    	
    	var template_note = $('#template_note').val();
    	if(template_note == ''){
    		$.ligerDialog.warn('模版说明不能为空');
    		return ; 
    	}
    	
    	var template_table = liger.get("template_table").getValue();
    	if(template_table == '' || template_table == null){
    		$.ligerDialog.warn('模板操作表不能为空');
    		return ; 
    	}
    	
    	//2.明细判断
    	var data = gridManager.getData();//获取grid数据
    	var exitMap = new HashMap();//用于判断明细数据是否重复
    	var msg = new StringBuffer();//提示信息
    	
    	$.each(data,function(rowindex,item){
    		if(typeof(item.template_detail_code) != "undefined" && item.template_detail_code != null && item.template_detail_code != ''){
    			var value = "第" + (rowindex+1) + "行" ;
				/* if(typeof(item.group_view) == "undefined" || item.group_view == ""){
					msg.append(value + "组名称不能为空<br>");
				} */
				
				if(typeof(item.columns_type) == "undefined" || item.columns_type == ""){
					msg.append(value + "列类型不能为空<br>");
				}
				
				if(typeof(item.columns_view) == "undefined" || item.columns_view == ""){
					msg.append(value + "列字段不能为空<br>");
				}
				
				var key = item.template_detail_code + "|" + item.group_view;
				if(exitMap.get(key) == "undefined" || exitMap.get(key) == null || exitMap.get(key) == ""){
					exitMap.put(key,value);
				}else{
					msg.append(exitMap.get(key) + "与" + value + "明细编码、组名称不能重复<br>");
				}
    		}
    	});
    	
    	if(msg.toString() != ""){
    		$.ligerDialog.warn(msg.toString());
    		return ; 
    	}
    	
    	var param = {
    		saveFlag:saveFlag,
        	template_code:template_code,	
        	template_name:template_name,	
        	template_note:template_note,	
        	template_table:template_table,
        	detailData:JSON.stringify(data)
        };
    	
    	ajaxJsonObjectByUrl("saveHpmTemplateData.do", param, function(responseData) {
			if(responseData.state == "true"){
				loadTree();
			}
		});  
    	
    }
	 
	//节点选中事件 查询
	function onSelect(note){
		saveFlag = false;
		
		template_code = note.data.id;//记录值,作为删除后查询使用参数
		var formPara = {
			template_code : note.data.id
		}; 
			
     	//查询模板
		ajaxJsonObjectByUrl("queryHpmTemplateData.do", formPara, function(responseData) {
			$('#template_code').val(responseData.template_code);
			$('#template_name').val(responseData.template_name);
			$('#template_note').val(responseData.template_note);
			autoCompleteByData("#template_table",template_table.Rows,"id","text",true,true,'',false,responseData.template_table);
		});
		liger.get('template_code').setDisabled(true);
			
     	//查询明细
		grid.options.parms=[];grid.options.newPage=1;
    	grid.options.parms.push({name:'template_code',value:note.data.id});
		grid.loadData(grid.where);//加载查询条件
	}
     
     /* 设置树形菜单 */
	function loadTree(){
		ajaxJsonObjectByUrl("queryAphiTemplateDataTree.do?isCheck=false",{},function(responseData) {
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
    
	
	function clearData(){
		saveFlag = true ;
		liger.get('template_code').setEnabled(true);
		$('#template_code').attr({readonly:false,disabled:false});
		
		$("#template_code").val('');
		$("#template_name").val('');
		$("#template_note").val('');
		$("#template_table").val('');
		grid.deleteAllRows();
	}
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1">
            <div position="left" title="模板类型"><ul id="tree"></ul></div>
            
            
            <div position="center" >
            <div id="toptool"></div>
            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			   		 <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 模板编码：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_code" type="text" id="template_code" ltype="text" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 模板名称：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板说明：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_note" type="text" id="template_note" ltype="text" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板操作表：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_table" type="text" id="template_table" ltype="text" /></td>
			        </tr> 
			    </table>
			    
			    <div id="detailtool"></div>
			    <div id="mainGrid"></div>
            </div>  
        </div>
</body>
</html>
