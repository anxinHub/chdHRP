<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var grid;
	
	var gridManager = null;
	
	var meta_code = '${meta_code}';
	
	var mod_code = '${mod_code}';
	
	var table_id =  '${table_id}';
	
	var acc_year =  '${acc_year}';
	
	var is_store =  '${is_store}';
	
	var is_resource =  '${is_resource}';
	
	var level_field = '${level_field}';
	
	var storeData = null;
	
	var sourceData = null;
	
	var level_field_data;
	
	
	var parms ={meta_code:meta_code,mod_code:mod_code,table_id:table_id,acc_year:acc_year,is_store:is_store,is_resource:is_resource},
	
	onoff = false,  // 光标定位 开关
	
	selectRow ;  // 选择行索引

	$(function() {
		
		
		loadDict();

		loadHead(null); 
	
	});

	//查询
	function query() {//根据表字段进行添加查询条件
		
		grid.options.parms = [];grid.options.newPage = 1;
	
		grid.options.parms.push({name : 'code_field',value : $("#code_field").val()});
		
		if(level_field !=''){
			
			grid.options.parms.push({name : 'level_field',value : liger.get("level_field").getValue()});
			
		}
		
		if (liger.get("is_last").getValue() != "") {

			grid.options.parms.push({ name: 'is_last', value: liger.get("is_last").getValue() });

		}
		
		if (liger.get("is_set").getValue() != "") {

			grid.options.parms.push({ name: 'is_set', value: liger.get("is_set").getValue() });

		}
		
		
		grid.options.parms.push({name : 'table_id',value : table_id});
		
		grid.options.parms.push({name : 'meta_code',value : meta_code});
		
		grid.options.parms.push({name : 'mod_code',value : mod_code});
		grid.options.parms.push({name : 'acc_year',value : acc_year});
		
		grid.options.parms.push({name : 'is_store',value : is_store});
		
		grid.options.parms.push({name : 'is_resource',value : is_resource});
		
		grid.loadData(grid.where);//加载查询条件
		
	}

	function loadHead() {
	if(is_resource==0)
		{
		if(is_store==0)
			{
		grid = $("#maingrid").ligerGrid({
					columns : [
							{display : '类别编码',name : 'code_field',width: 100,align : 'left'}, 
							{display : '类别名称',name : 'name_field',width: 300,align : 'left'},
							{display : '科目编码',name : 'subj_code',width: 150,align : 'left'},
				 			{display : '科目名称',name : 'subj_name',width: 300,align : 'left'},
							{display : '设置',name : 'set_subj',align : 'left',width: 100,align : 'left',
								render: function (item,rowindex) {
			                        return "<a onClick=openAccBusiRelaForAccSubjSetPage('"+item.id_field+"','"+item.code_field+"','"+rowindex+"','','') >设置</a>　　<a onClick=delAutoSet('"+item.id_field+"','"+rowindex+"','','') >取消</a>";
			                        
			                    }

							},
							/* {display : '清除设置',name : 'del_subj',align : 'left',width: 80,align : 'left',
								render: function (item) {
									
			                        return "<a onClick=delAutoSet('"+item.id_field+"') >删除设置</a>";
			                        
			                    }

							}, */
							],
					onAfterShowData: function (){ 
						if(onoff) {
							onoff = false;
							grid.select(selectRow);
							grid.gridbody.scrollTop(grid.options.rowHeight * selectRow);
						}
					}, 
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiRelaForAutoSet.do?isCheck=false',parms:parms,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					
					toolbar : {
						items : [
						         {text : '查询',id : 'search',click : query,icon : 'search'},
						         {line : true},
						         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
						         {line : true}
						         ]
					}
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
			}
		else
			{
			
		grid = $("#maingrid").ligerGrid({
					columns : [
							{display : '类别编码',name : 'code_field',width: 100,align : 'left'}, 
							{display : '类别名称',name : 'name_field',width: 300,align : 'left'},
							{display : '仓库',name : 'store_id',width: 200,align : 'left',
								editor : {
									type : 'select',
									valueField : 'store_id',
									textField : 'store_name',
									data : storeData,
									keySupport : true,
									autocomplete : true,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									}
								},
			                    render: function (item)
			                    {
			                    	
			                    	if(!storeData)return;
			                        for (var i = 0; i < storeData.length; i++){
			                        	
			                            if (storeData[i]['store_id'] == item.store_id){
			                            	
			                            	

			                            	return storeData[i]['store_name'];
			                            	
			                            }
			                            
			                        }
			                        
			                    	return item.store_name;       
			                    }
									
							},
							{display : '科目编码',name : 'subj_code',width: 150,align : 'left'},
				 			{display : '科目名称',name : 'subj_name',width: 300,align : 'left'},
							{display : '设置',name : 'set_subj',align : 'left',width: 100,align : 'left',
								render: function (item,rowindex) {
			                        return "<a onClick=openAccBusiRelaForAccSubjSetPage('"+item.id_field+"','"+item.code_field+"','"+rowindex+"','"+item.store_id+"','') >设置</a>　　<a onClick=delAutoSet('"+item.id_field+"','"+rowindex+"','"+item.store_id+"','') >取消</a>";
			                        
			                    }

							},
							/* {display : '清除设置',name : 'del_subj',align : 'left',width: 80,align : 'left',
								render: function (item) {
									
			                        return "<a onClick=delAutoSet('"+item.id_field+"') >删除设置</a>";
			                        
			                    }

							}, */
							],
					onAfterShowData: function (){ 
						if(onoff) {
							onoff = false;
							grid.select(selectRow);
							grid.gridbody.scrollTop(grid.options.rowHeight * selectRow);
						}
					}, 
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiRelaForAutoSet.do?isCheck=false',parms:parms,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					enabledEdit:true,
					toolbar : {
						items : [
						         {text : '查询',id : 'search',click : query,icon : 'search'},
						         {line : true},
						         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
						         {line : true}
						         ]
					}
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
			
			
			}
		}
	else
		{
		if(is_store==0)
		{
	grid = $("#maingrid").ligerGrid({
				columns : [
						{display : '类别编码',name : 'code_field',width: 100,align : 'left'}, 
						{display : '类别名称',name : 'name_field',width: 300,align : 'left'},
						{display : '资金来源',name : 'source_id',width: 100,align : 'left',
							editor : {
								type : 'select',
								valueField : 'source_id',
								textField : 'source_name',
								data : sourceData,
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							},
		                    render: function (item)
		                    {
		                    	
		                    	if(!sourceData)return;
		                        for (var i = 0; i < sourceData.length; i++){
		                        	
		                            if (sourceData[i]['source_id'] == item.source_id){
		                            	
		                            	return sourceData[i]['source_name'];
		                            	
		                            }
		                            
		                        }
		                        
		                    	return item.source_name;       
		                    }
						},
						{display : '科目编码',name : 'subj_code',width: 150,align : 'left'},
			 			{display : '科目名称',name : 'subj_name',width: 300,align : 'left'},
						{display : '设置',name : 'set_subj',align : 'left',width: 100,align : 'left',
							render: function (item,rowindex) {
		                        return "<a onClick=openAccBusiRelaForAccSubjSetPage('"+item.id_field+"','"+item.code_field+"','"+rowindex+"','','"+item.source_id+"') >设置</a>　　<a onClick=delAutoSet('"+item.id_field+"','"+rowindex+"','','"+item.source_id+"') >取消</a>";
		                        
		                    }

						},
						/* {display : '清除设置',name : 'del_subj',align : 'left',width: 80,align : 'left',
							render: function (item) {
								
		                        return "<a onClick=delAutoSet('"+item.id_field+"') >删除设置</a>";
		                        
		                    }

						}, */
						],
				onAfterShowData: function (){ 
					if(onoff) {
						onoff = false;
						grid.select(selectRow);
						grid.gridbody.scrollTop(grid.options.rowHeight * selectRow);
					}
				}, 
				dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiRelaForAutoSet.do?isCheck=false',parms:parms,
				width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
				enabledEdit:true,
				toolbar : {
					items : [
					         {text : '查询',id : 'search',click : query,icon : 'search'},
					         {line : true},
					         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
					         {line : true}
					         ]
				}
			});

	gridManager = $("#maingrid").ligerGetGridManager();
		}
	else
		{

		
	grid = $("#maingrid").ligerGrid({
				columns : [
						{display : '类别编码',name : 'code_field',width: 100,align : 'left'}, 
						{display : '类别名称',name : 'name_field',width: 300,align : 'left'},
						{display : '资金来源',name : 'source_id',width: 100,align : 'left',
							editor : {
								type : 'select',
								valueField : 'source_id',
								textField : 'source_name',
								data : sourceData,
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							},
		                    render: function (item)
		                    {
		                    	
		                    	if(!sourceData)return;
		                        for (var i = 0; i < sourceData.length; i++){
		                        	
		                            if (sourceData[i]['source_id'] == item.source_id){
		                            	
		                            	

		                            	return sourceData[i]['source_name'];
		                            	
		                            }
		                            
		                        }
		                        
		                    	return item.source_name;       
		                    }
							
						},
						
						{display : '仓库',name : 'store_id',width: 200,align : 'left',
							editor : {
								type : 'select',
								valueField : 'store_id',
								textField : 'store_name',
								data : storeData,
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							},
		                    render: function (item)
		                    {
		                    	
		                    	if(!storeData)return;
		                        for (var i = 0; i < storeData.length; i++){
		                        	
		                            if (storeData[i]['store_id'] == item.store_id){
		                            	
		                            	

		                            	return storeData[i]['store_name'];
		                            	
		                            }
		                            
		                        }
		                        
		                    	return item.store_name;       
		                    }
								
						},
						{display : '科目编码',name : 'subj_code',width: 150,align : 'left'},
			 			{display : '科目名称',name : 'subj_name',width: 300,align : 'left'},
						{display : '设置',name : 'set_subj',align : 'left',width: 100,align : 'left',
							render: function (item,rowindex) {
		                        return "<a onClick=openAccBusiRelaForAccSubjSetPage('"+item.id_field+"','"+item.code_field+"','"+rowindex+"','"+item.store_id+"','"+item.source_id+"') >设置</a>　　<a onClick=delAutoSet('"+item.id_field+"','"+rowindex+"','"+item.store_id+"','"+item.source_id+"') >取消</a>";
		                        
		                    }

						},
						/* {display : '清除设置',name : 'del_subj',align : 'left',width: 80,align : 'left',
							render: function (item) {
								
		                        return "<a onClick=delAutoSet('"+item.id_field+"') >删除设置</a>";
		                        
		                    }

						}, */
						],
				onAfterShowData: function (){ 
					if(onoff) {
						onoff = false;
						grid.select(selectRow);
						grid.gridbody.scrollTop(grid.options.rowHeight * selectRow);
					}
				}, 
				dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiRelaForAutoSet.do?isCheck=false',parms:parms,
				width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
				enabledEdit:true,
				toolbar : {
					items : [
					         {text : '查询',id : 'search',click : query,icon : 'search'},
					         {line : true},
					         {text : '关闭',id : 'close',click : myClose,icon : 'close'},
					         {line : true}
					         ]
				}
			});

	gridManager = $("#maingrid").ligerGetGridManager();
		
		
		}
		
		}
	}
	
	

	function loadDict() {
		var formPara1 = {
		}; 
		

		
		ajaxJsonObjectByUrl("queryAccBusiHosStore.do?isCheck=false", formPara1, function(responseData) {

			storeData = responseData.Rows;
			
			loadHead(null); 
		}); 
		
//sourceData
		
		
		ajaxJsonObjectByUrl("queryAccBusiHosResource.do?isCheck=false", formPara1, function(responseData) {

			sourceData = responseData.Rows;
			
			loadHead(null); 
			
		});
		
		
		$("#is_last").ligerComboBox({ data: [{ id: "1", text: "是" }], width: 80, cancelable: true });
		
		$("#is_set").ligerComboBox({ data: [{ id: "1", text: "是" }], width: 80, cancelable: true });
		

    	$("#code_field").ligerTextBox({width:180});

    	if(level_field !=''){
    		
    		level_field_data =  "[";
    		
			for(var i=1;i<parseInt(level_field)+1;i++){
				
				level_field_data =level_field_data +"{text: '"+i+"', id: "+i+"},";
				
			}
			
			level_field_data = level_field_data.substring(0,level_field_data.length-1);
    		
			if(level_field_data.length>0){
				
				level_field_data = level_field_data+"]";
			}

			$("#level_field").ligerComboBox({data: eval(level_field_data),width:180,valueField:'id',textField:'text'}); 

    	}else{
    		
    		$("#level_field").ligerComboBox({disabled: true,width:180}); 
    		
    	}
	}
	function delAutoSet(obj,rowindex,store_id,source_id){
		onoff = true;
		selectRow = rowindex;
		if (source_id=='null')
		{
			source_id='';
		}
		if (store_id=='null')
		{
			store_id='';
		}
		var formPara = {
				
				meta_code : meta_code,
				
				mod_code : mod_code,
				
				table_name : table_id,
				
				type_id : obj,
				
				sub_type_id : obj,
				
				acc_year : acc_year,
				
				store_id : store_id,
				
				source_id : source_id

		};
		
		ajaxJsonObjectByUrl("deleteAutoSet.do?isCheck=false", formPara, function(responseData) {

			query();

		});

	}
	function openAccBusiRelaForAccSubjSetPage(obj,obj1,rowindex,store_id,source_id){
		selectRow = rowindex;
		onoff = true;
		if (source_id=='null')
		{
			source_id='';
		}
		if (store_id=='null')
		{
			store_id='';
		}
		var parm = "meta_code="+meta_code+"&mod_code="+mod_code+"&table_name="+table_id+"&type_id="+obj+"&code_field="+obj1+"&acc_year="+acc_year+"&store_id="+store_id+"&source_id="+source_id;

    	$.ligerDialog.open({ url : 'queryAccBusiRelaForAccSubjSetPage.do?isCheck=false&' + parm,data:{}, height: $(window).height()-100,width:600, title:'选择科目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBusiMap(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
    function myClose(){
    	dialog.close();
    }

</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">类别编码：</td>
            <td align="left" class="l-table-edit-td"><input name="code_field" type="text" id="code_field" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">类别级次：</td>
            <td align="left" class="l-table-edit-td"><input name="level_field" type="text" id="level_field" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left:20px;">末级：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="is_last" />
					</td>
			<td align="right" class="l-table-edit-td" style="padding-left:20px;">未配置：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="is_set" />
					</td>
        </tr>
    </table>

	<div id="maingrid"></div>

</body>
</html>
