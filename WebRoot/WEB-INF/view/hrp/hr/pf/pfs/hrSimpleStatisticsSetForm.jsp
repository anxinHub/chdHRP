<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>

<style type="text/css">
.l-layout-content{
	height: 100%;
}
/* 搜索框 */
.search-form {
	padding: 5px;
	/* text-align: center; */
	box-sizing: border-box;
	background: #e0ecff;
	border-bottom: 1px solid #a3c0e8;
}
/* 文本input */
.text-input {
	box-sizing: border-box;
	width: 180px;
	height: 26px;
	padding: 1px 1px 1px 5px;
	border: 1px solid #aecaf0;
	border-radius: 1px;
	outline: none;
	width: 140px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}

.middle input {
    display: block;width:30px; margin:2px;
}

.l-dialog-table .l-panel .l-toolbar{
	width: auto !important;
}

</style>
<script type="text/javascript">
	var tree, dataTableGrid, conditionGrid, groupGrid, sortGrid, toptoolbar, design_code, design_query_col, store_type_code;
	$(function() {
		
		design_code = "${design.design_code}";
		
		design_query_col = ${design.design_query_col};
		
		//布局
		$("#layout1").ligerLayout({
			leftWidth : 220,
			minLeftWidth : 220,
			allowLeftResize : false
		});
		
		toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '保存', id:"save", click: function (item){save()}, icon:'save'}, { line:true },
				/* {text: '导入', click: function (item){importData()}, icon:'up'}, { line:true },
				{text: '导出', click: function (item){exportData()}, icon:'down'}, { line:true } */
			]}
		);
		$("#archives_select").ligerComboBox({
            width: 220,
            url: '../../queryHrStoreType.do?isCheck=false',
            keySupport:false,
            autocomplete: false,
            onSuccess: function(data){
            	this.selectValue(data[0].id);
            },
            onSelected: function(value,text){
            	store_type_code = value;
            	reloadTree();
            }
            
        });
		
		initTree();
		loadGrid();
		
		$("#desingForm").ligerForm();
		liger.get("design_code").setDisabled(true);

		$("#search_input").keyup(function(e) {
			reloadTree();
		});
		
	});
	
	function reloadTree(){
		var key = $("#search_input").val();
		tree.options.url = 'queryHrStatisticTableTree.do?isCheck=false&key='+key+'&design_code='+design_code+'&store_type_code='+store_type_code;
        tree.reload();
	}
	
	function initTree() {
		tree = $("#mainTree").ligerTree(
				{url: '', 
            	 ajaxType: 'post' ,
            	 idFieldName :'tab_code',
            	 textFieldName: 'tab_name',
                 parentIDFieldName :'pid', 
                 nodeWidth:'100%',
            	 checkbox: true,
            	 delay: true,
            	 autoCheckboxEven: false,
            	 onCheck: function(node, checked){
            		if(node.data.pid){
            			var gridManager = $("#data_table_grid").ligerGetGridManager();
                		if(checked){
                			gridManager.appendRow(node.data);
                		}else{
                			//gridManager.remove(node.data);
                			deleteRow(node.data);
                		}
            		}
            	}
            });
	}
	
	function deleteRow(tempSelectData) {
        manager = $("#data_table_grid").ligerGetGridManager();
        var row;
        for (var i in manager.rows) {
            if (manager.rows[i].tab_code == tempSelectData.tab_code) {
                row = manager.rows[i];
                break;
            }
        }
        if (row != "undefined") {
            manager.remove(row)
        }
    }
	
	function loadGrid() {
		dataTableGrid = $("#data_table_grid").ligerGrid({
			columns: [
				{display: "连接方式", name: "join_mode", align: "center", width: 120, textField: "text_align_text",
                   	editor: {type: 'select', data: optionData.joinMode},
                   	render: function(rowdata, rowindex, value) {
                   		if(rowindex == 0){
                   			return "主表";
                   		}
                   		if(!value){
                       		rowdata.join_mode = 'left join';
                       		rowdata.text_align_text = '左连接';
                       	}
						return rowdata.text_align_text;
					}
				},
	            {display: '数据表', name: 'tab_name', align: 'center', width: 200
	            },
				{display: '数据列', name: 'col_code', align: 'center', width: 200, textField: "col_name", editor: { type: 'text'}, 
	            	render: function(rowdata, rowindex, value) {
						return value ? '已设置' : '';
					}
				}
			],
			data: {Rows: design_query_col.tableData}, width: '100%', height: '210', checkbox: false, rownumbers: true, delayLoad: false, rowDraggable: true,  
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false, isSingleCheck: true,
			onBeforeEdit: function (e) {
				if(e.column.name == 'col_code'){
					var tables = [e.record.tab_code]
					//func参数是用来回显函数与参数信息
					var parm = {'tab_codes': tables, func: JSON.stringify(e.record.func)};
			    	e.column.editor = {
			    			type: 'popup', parms: parm, valueField: 'col_code', textField: 'col_name', 
		            		grid: {
	                        	url: "../../sc/hrtabledesign/queryHrTableColByCodes.do?isCheck=false", parm: parm, columns: [
	                        		{ display: '数据列编码', name: 'col_code', width: 150 }, 
	                        		{ display: '数据列名称', name: 'col_name', width: 150 },
	                        		{ display: '函数', name: 'func', width: 180, align: 'left', textField: "func_text", 
	                                	editor : {type: 'select', data: optionData.dbFunc},
	                                	render: function(rowdata, rowindex, value) {
	                						return rowdata.func_text;
	                					}
	                                },
	                                { display: '可选参数', name: 'param', width: 120, align: 'left', editor: {type: 'text'} }
	                        	],
	                        	checkbox: true, usePager: false, enabledEdit: true 
	                    	},
	                    	onSelected: f_onSelected,
						}
				}
			}
		});
		
		function f_onSelected(e){
			if (!e.data || !e.data.length) return;
			var tab_cols = "";
			var lastEditRow = dataTableGrid.lastEditRow;
			var func = [];
	        $.each(e.data, function(index, item){
	       		if(item.func != null && item.func.length > 0) {
	       			tab_cols += item.func.format(lastEditRow.tab_code + '.' + item.col_code, item.param) + ',';
				}else{
					tab_cols += lastEditRow.tab_code + '.' + item.col_code + ',';
				}
	       		if(item.func){
	       			func.push({tab_code: lastEditRow.tab_code, col_code: item.col_code, func: item.func, func_text: item.func_text, param: item.param});
	       		}
	       		
	        });
	        tab_cols = (tab_cols.substring(tab_cols.length-1)==',')?tab_cols.substring(0,tab_cols.length-1):tab_cols;
	        dataTableGrid.updateRow(lastEditRow, {
	         func: func,
	       	 tab_cols: tab_cols
	        });
		}
		
		conditionGrid = $("#condition_grid").ligerGrid({
			columns: [
				{display: "连接符", name: "conn_mode", align: "center", width: 80, textField: "conn_mode_text",
                    editor: {type: 'select', data: optionData.connMode},
                    render: function(rowdata, rowindex, value) {
                        if(rowindex == 0){
                            return "第一行为空";
                        }
                        if(!value){
                            rowdata.conn_mode = 'AND';
                            rowdata.conn_mode_text = '与';
                        }
                        return rowdata.conn_mode_text;
                    }
                },
				{display: '左括号', name: 'left_bracket', align: 'center', width: 60, editor: { type: 'text'},
					render: function(rowdata, rowindex, value) {
						return value;
					}
	            },
                {display: '数据表', name: 'tab_name', align: 'center', width: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
				},
				{display: '数据列', name: 'col_name', align: 'center', width: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
				},
				{display: "条件", name: "condition", align: "center", width: 120, textField: "condition_text",
                    editor: {type: 'select', data: optionData.condition},
                    render: function(rowdata, rowindex, value) {
                    	if(!value){
                            rowdata.condition = '=';
                            rowdata.condition_text = '等于';
                        }
						return rowdata.condition_text;
					}
				},
				{display: "取值方式", name: "value_mode_code", align: "center", width: 120, textField: "value_mode_name",
					editor: {
						type: 'select', data: optionData.valueMode
					},
					render: function(rowdata, rowindex, value) {
						return rowdata.value_mode_name;
					}
				},
				{display: '数据项值', name: 'item_value', align: 'left', textField: "item_value_text", width: 120, editor: { type: 'text'},
					render: function(rowdata, rowindex, value) {
						if(rowdata.item_value_text == null || rowdata.item_value_text.trim() == ''){
							rowdata.item_value_text = value;
						}
						return rowdata.item_value_text;
					}
				},
				{display: '右括号', name: 'right_bracket', align: 'center', width: 60, editor: { type: 'text'},
					render: function(rowdata, rowindex, value) {
						return value;
					}
				}
			],
			data: {Rows: design_query_col.conditionData}, width: '100%', height: '300', checkbox: false, rownumbers: true, delayLoad: false,
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,
			onBeforeEdit: function (e) {
				if(e.column.name == 'item_value'){
					var value_mode = e.record.value_mode_code;
					if(value_mode == null || value_mode == ''){
						$.ligerDialog.warn("请输入取值方式");
					}else{
						switch(value_mode)
						{
						    case '01':
						    	e.column.editor = {type: 'text'};
						        break;
						    case '02':
						    	e.column.editor = {
										type : 'select',
										valueField : 'field_tab_code',
										textField : 'field_tab_name',
										selectBoxWidth : "80%",
										selectBoxHeight : 370,
										keySupport : true,
										autocomplete : true,
										grid : {
											columns : [{display: '代码表编码', align: 'left', width: 120, name: 'field_tab_code'}, 
														{display: '代码表名称', align: 'left', width: 120, name: 'field_tab_name'}, 
						                                {display: '代码表分类', align: 'left', width: 120, name: 'type_filed_name'}, 
						                                {display: '备注', align: 'left', width: 120, name: 'note'}
											          ],
											switchPageSizeApplyComboBox : false,
											gid: 'fieldTabGrid', 
											url : '../../sc/hrtablestruc/queryHrFiledTableStruc.do?isCheck=false',
											pageSize : 20
										}
									}
						        break;
						    case '03':
						    	e.column.editor = {type: 'select', data: optionData.valueSystemItem};
						        break;
						}
					}
				}
			},
			toolbar: {
				items: [ 
					{text: '添加条件', id: 'addCondition', click:  function(){field_import(conditionGrid)}, icon: 'add'}, 
					{line: true}, 
					{text: '删除条件', id: 'delCondition', click: delField, icon: 'delete'}]
			}
		});
		
		groupGrid = $("#group_grid").ligerGrid({
			columns: [
				{display: '数据表', name: 'tab_name', align: 'left', minWidth: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
				},
				{display: '数据项名称', name: 'col_name', align: 'left', minWidth: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
                }
			],
			data: {Rows: design_query_col.groupData}, width: '100%', height: '250', checkbox: false, rownumbers: true, delayLoad: false, 
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,
			toolbar: {
				items: [ 
					{text: '添加分组', id: 'addGroup', click: function(){field_import(groupGrid)}, icon: 'add'}, 
					{line: true}, 
					{text: '删除分组', id: 'delGroup', click: delField, icon: 'delete'}]
			}
		});
		
		sortGrid = $("#sort_grid").ligerGrid({
			columns: [
				{display: '数据表', name: 'tab_name', align: 'left', minWidth: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
                },
				{display: '数据项名称', name: 'col_name', align: 'left', minWidth: 120,
					render: function(rowdata, rowindex, value) {
						return value;
					}
				},
				{display: "排序", name: "sort_mode", align: "left", minWidth: 120, textField: "sort_mode_text",
					editor: {type: 'select', data: optionData.sortMode},
					render: function(rowdata, rowindex, value) {
						return rowdata.sort_mode_text;
					}
				}
			],
			data: {Rows: design_query_col.sortData}, width: '100%', height: '250', checkbox: false, rownumbers: true, delayLoad: false,
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,
			toolbar: {
				items: [ 
					{text: '添加排序', id: 'addSort', click: function(){field_import(sortGrid)}, icon: 'add'}, 
					{line: true}, 
					{text: '删除排序', id: 'delSort', click: delField, icon: 'delete'}]
			}
		});

	}
	
	//加载表格数据
	function loadGridData(designCode){
		design_code = designCode;
		tree.options.url = 'queryDBTableTree.do?isCheck=false&design_code='+design_code;
		tree.reload();
		ajaxPostData({
			url: 'queryDesignQueryCol.do?isCheck=false',
			data: {
				'design_code': designCode
			},
			delayCallback: true,
			success: function(data) {
				dataTableGrid.reload();
				conditionGrid.reload();
				groupGrid.reload();
				sortGrid.reload();
				if(data.json){
					var gridData = JSON.parse(data.json);
					if(gridData && gridData.tableData){
						dataTableGrid.appendRange(gridData.tableData);
						window.parent.loadLabelBtn(gridData.tableData);//加载数据标签
					}
					if(gridData && gridData.conditionData){
						conditionGrid.appendRange(gridData.conditionData);
					}
					if(gridData && gridData.groupData){
						groupGrid.appendRange(gridData.groupData);
					}
					if(gridData && gridData.sortData){
						sortGrid.appendRange(gridData.sortData);
					}
				}
				
				window.parent.setSqlData(data.sql);
			    
			}
		})
	}
	
	//追加字段
	function field_import(curr_grid){
		var tables = [];
		var tableOptions = [];
		var oldGrid = curr_grid.getData();
		
		$.each(dataTableGrid.getData(), function(index, item){
			tables.push(item.tab_code);
			//引出表格查询条件下拉框数据
			tableOptions.push({id: item.tab_code, text: item.tab_name});
		});
		var parm = {'tab_codes': tables};
		
		($.ligerui.getPopupFn({
	            top : 80,
	            onSelect: function (e) {
	            	curr_grid.deleteAllRows();
	            	curr_grid.addRows(f_appendData(oldGrid, e.data));
	            },
	            grid: {
	                columns: [
	                { display: '数据表', name: 'tab_name', width: 150 },
	                { display: '数据列编码', name: 'col_code', width: 150 },
	                { display: '数据列名称', name: 'col_name', width: 150 }
	                ], isScroll: true, checkbox: true,dataAction: 'server', dataType: 'server', usePager: false,
	                url: '../../sc/hrtabledesign/queryHrTableColByCodes.do?isCheck=false',parms: parm, width: '95%', 
	            },
	            selectInit: function(rowdata){
	            	return f_isExist(curr_grid.getData(), rowdata);
                },
                condition: {
                	fields: [{ name: 'tab_code', label: '数据表', width: '450', type: "select", editor: {
                        type: 'select', data: tableOptions
                    }}]
                }
	            
	        })
		)();
    }  
	
	function delField(item){
		switch(item.id)
		{
		    case 'delDataTable':
		    	dataTableGrid.deleteSelectedRow2();
		        break;
		    case 'delCondition':
		    	conditionGrid.deleteSelectedRow2();
		        break;
		    case 'delGroup':
		    	groupGrid.deleteSelectedRow2();
		        break;
		    case 'delSort':
		    	sortGrid.deleteSelectedRow2();
		        break;
		}
	}
	
	//保存数据表字段信息
	function save() {
		var dataTableGridManager = $("#data_table_grid").ligerGetGridManager();
		var conditionGridManager = $("#condition_grid").ligerGetGridManager();
		var groupGridManager = $("#group_grid").ligerGetGridManager();
		var sortGridManager = $("#sort_grid").ligerGetGridManager();
		dataTableGridManager.endEdit();
		conditionGridManager.endEdit();
		groupGridManager.endEdit();
		sortGridManager.endEdit();
		var tableData = dataTableGridManager.getData();
		var conditionData = conditionGridManager.getData();
		var groupData = groupGridManager.getData();
		var sortData = sortGridManager.getData();
		
		var design_query_col = {
				tableData: tableData,
				conditionData: conditionData,
				groupData: groupData,
				sortData: sortData,
		}
		
		if(liger.get("design_name").getValue() == ""){
            $.ligerDialog.error('统计表名称不能为空');
            return;
        }
		
		ajaxPostData({
			url: 'saveHrStatisticDesign.do?isCheck=false',
			data: {
				'design_code': liger.get("design_code").getValue(),
				'design_name': liger.get("design_name").getValue(),
				'design_query_col': JSON.stringify(design_query_col),
				
			},
			delayCallback: true,
			success: function(data) {
				liger.get("design_code").setValue(data.design_code);
			    var parentFrameName = parent.$.etDialog.parentFrameName;
                var parentWindow = parent.parent.window[parentFrameName];
				parentWindow.tree.reload()
			}
		})
	}
	
	//是否存在
	function f_isExist(arr, data){
		var _isInvalid = false;
		$.each(arr, function(index, item){
    		if (data.tab_code == item.tab_code && data.col_code == item.col_code){
    			_isInvalid = true;
    			return false;
    		} 
    	});
		return _isInvalid;
	}
	
	//追加数据，过滤源表格存在的数据
	function f_appendData(oldData, newData){
		var re = [];
		$.each(newData, function(i, data){
			$.each(oldData, function(index, item){
				if (data.tab_code == item.tab_code && data.col_code == item.col_code){
					if(!f_isExist(re, item)){
						re.push(item);
					}
					return false;
				}
			})
			if(!f_isExist(re, data)){
				re.push(data);
			}
		})
		return re;
	}
	
    
    function moveToUp(){
    	var gridManager = $("#grid").ligerGetGridManager();
    	var selecteds = gridManager.getSelected();
    	if (!selecteds) return;
    	gridManager.up(selecteds);
    }
    
    function moveToDown(){
    	var gridManager = $("#grid").ligerGetGridManager();
    	var selecteds = gridManager.getSelected();
    	if (!selecteds) return;
    	gridManager.down(selecteds);
    }
    
    
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;">
		<div position="left" title="数据表">
		    <div><input type="text" id="archives_select" name="archives_select"/></div>
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 90px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="" style="overflow: auto">
			<div id="toptoolbar"></div>
			<form name="desingForm" method="post" id="desingForm">
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		            <tr>
		                <td align="right" class="l-table-edit-td">统计表编号:</td>
		                <td align="left" class="l-table-edit-td"><input name="design_code" type="text" id="design_code" placeholder="自动生成" value="${design.design_code}" ltype="text" /></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"><font color="red">*</font>统计表名称:</td>
                        <td align="left" class="l-table-edit-td"><input name="design_name" type="text" id="design_name" value="${design.design_name}" ltype="text" validate="{required:true,minlength:3,maxlength:50}"/></td>
                        <td align="left"></td>
		            </tr>
		         </table>
			</form>
			<div style="padding:4px;float:left;width:100%;">
		    	<div id="data_table_grid"></div>  
		  	</div>
		  	<div style="padding:4px;float:left;width:100%;">
		    	<div id="condition_grid"></div>  
		  	</div>
		  	<div style="padding:4px;float:left;width:calc(50% - 8px);">
		    	<div id="group_grid"></div>  
		  	</div>
		  	<div style="padding:4px 4px 50px;float:left;width:calc(50% - 8px);">
		    	<div id="sort_grid"></div>  
		  	</div>
		</div>
	</div>
</body>
</html>
