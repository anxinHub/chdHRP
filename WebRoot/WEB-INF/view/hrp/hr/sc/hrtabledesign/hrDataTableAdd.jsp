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

<link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/codemirror.css" />
<link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/css/dracula.css" />
<link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/css/show-hint.css" />

<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/codemirror.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sublime.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql-hint.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/show-hint.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/formatting.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql-formatter.min.js"></script>

<style type="text/css">
.l-layout-content{
	height: 100%;
}
#sql_container .l-layout-right, #sql_container .l-layout-center{
    border: none;
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
.label_btn{    
    float: none !important;
    margin: 4px 10px;
    padding: 0 4px;
    width: auto !important;
}

/*.left-buttons ul{
    display: flex;
    flex-direction: row;
    justify-content: center;
} */

.CodeMirror{
    border: 1px solid #a3c0e8;
    height: 100%;
    overflow: auto;
}

.CodeMirror .CodeMirror-scroll{
    margin-right: 0;
}

</style>
<script type="text/javascript">
	var tree, dataTableGrid, conditionGrid, groupGrid, sortGrid, toptoolbar, design_code, configgrid, editor;
	var gridId = 'data_table_grid';
	$(function() {
  	
		//布局
		$("#layout1").ligerLayout({
			leftWidth : 220,
			minLeftWidth : 220,
			allowLeftResize : false
		});
		
		toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '保存', id:"save", click: function (item){save()}, icon:'save', disable: true}, { line:true },
				{text: '生成', id:"genData", click: function (item){genData()}, icon:'add', disable: true}, { line:true },
				{text: '设置数据表', id:"chooseTable", click: function (item){chooseTable()}, icon:'add', disable: true}, { line:true },
				{text: '设置条件', id: 'addCondition', click:  function(){field_import(conditionGrid)}, icon: 'add', disable: true}, { line:true },
				{text: '设置分组', id: 'addGroup', click: function(){field_import(groupGrid)}, icon: 'add', disable: true}, { line:true },
				{text: '设置排序', id: 'addSort', click: function(){field_import(sortGrid)}, icon: 'add', disable: true}, { line:true }, 
				/* {text: '导入', click: function (item){importData()}, icon:'up'}, { line:true },
				{text: '导出', click: function (item){exportData()}, icon:'down'}, { line:true } */
			]}
		);
		
		$("#accordion").ligerAccordion({
			width: '100%',
			height: $('body').height() - 30
        });
		
		/* toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '上移', id:"up", click: function (item){moveToUp()}, icon:'up', }, {line:true },
				{text: '下移', id:"down", click: function (item){moveToDown()}, icon:'down', }, {line:true }
			]}
		);
 		*/
		//loadTree();
		loadGrid();

		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = 'queryDBTableTree.do?isCheck=false&key='+key+'&design_code='+design_code;
			tree.reload();
		});
		
		$(".l-accordion-header").click(function (){
	        var togglebtn = $(".l-accordion-toggle:first", this);
	        if (togglebtn.hasClass("l-accordion-toggle-open")){
	            gridId = $(this).next(".l-accordion-content").children().attr('id');
	            if(!design_code){
	                return;
	            }
	            changeBtnState();
	        }else{
	            setBtnState(null);
	        }
		});
		
	});
	
	//改变按钮状态
	function changeBtnState(){
		switch(gridId)
        {
            case 'data_table_grid':
                setBtnState('chooseTable');
                break;
            case 'condition_grid':
                setBtnState('addCondition');
                break;
            case 'group_grid':
                setBtnState('addGroup');
                break;
            case 'sort_grid':
                setBtnState('addSort');
                break;
            case 'sql_container':
                setBtnState('genData');
                editor.refresh();
                break;
            case 'configgrid':
                setBtnState('genData');
                break;
            default:
                setBtnState(null);
        }
	}
	
	//设置按钮状态 
	function setBtnState(id){
		var ids = ['chooseTable', 'addCondition', 'addGroup', 'addSort', 'genData'];
		ids.forEach((item) => {
            if(item == id){
                toptoolbar.setEnabled(item);
            }else{
                toptoolbar.setDisabled(item);
            }
        });
	}
	
	//生成SQL语句和数据列设置
	function genData(){
		if(gridId == 'sql_container'){
			genSql();
		}
		if(gridId == 'configgrid'){
			genDesignQueryPage();
		}
	}
	
	//生成SQL
	function genSql() {
		var design_query_col = getSaveData();
        ajaxPostData({
            url: 'generateSqlStatement.do?isCheck=false',
            data: {
                'design_code': design_code,
                'design_query_col': JSON.stringify(design_query_col)
            },
            delayCallback: true,
            success: function(data) {
                //$("textarea[name='sql_statement']").val(data.sql_statement)
                var str = sqlFormatter.format(data.sql_statement, {language: 'sql'});
                editor.setValue(str);
            }
        })
    };
    
    //生成数据列设置
    function genDesignQueryPage() {
    	var design_query_col = getSaveData();
        ajaxPostData({
            url: 'genDesignQueryPage.do?isCheck=false',
            data: {
                'design_code': design_code,
                'design_query_col': JSON.stringify(design_query_col)
            },
            delayCallback: true,
            success: function(data) {
            	configgrid.loadData({Rows: data.design_query_page ? JSON.parse(data.design_query_page) : null});
            }
        })
    };
    
    
	
	function loadTree() {
		tree = $("#mainTree").ligerTree(
				{url: '', 
            	 ajaxType: 'post' ,
            	 idFieldName :'tab_code',
            	 textFieldName: 'tab_name',
                 parentIDFieldName :'pid', 
                 nodeWidth:'100%',
            	 checkbox: true,
            	 autoCheckboxEven: false,
            	 isExpand: false, 
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
	
	function chooseTable(){
		parent.$.ligerDialog.open({ 
            url: 'hrChooseTablePage.do?isCheck=false', 
            title: '选择或编辑字段', 
            height: $('body').height(),width: 400, 
            data: {design_code: design_code},
            parentframename : window.name,
            buttons: [ { text: '确定', onclick: function (item, dialog) { 
                    //var val = dialog.frame.getValue();
                    //e.value = val;
                    dialog.close();
                } }, { text: '取消', onclick: function (item, dialog) { 
                    dialog.close(); 
                } }
            ] 
        });
	}
	
	function appendRow(tempSelectData) {
        manager = $("#data_table_grid").ligerGetGridManager();
        manager.appendRow(tempSelectData);
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
				{display: "连接方式", name: "join_mode", align: "center", width: 120, textField: "join_mode_text",
                   	editor: {type: 'select', data: optionData.joinMode},
                   	render: function(rowdata, rowindex, value) {
                   		if(rowindex == 0){
                   			return "主表";
                   		}
                   		if(!value){
                       		rowdata.join_mode = 'left join';
                       		rowdata.join_mode_text = '左连接';
                       	}
						return rowdata.join_mode_text;
					}
				},
	            {display: '数据表', name: 'tab_name', align: 'center', width: 200
	            },
				{display: '数据列', name: 'col_code', align: 'center', width: 200, textField: "col_name", editor: { type: 'text'}, 
	            	render: function(rowdata, rowindex, value) {
						return value ? '已设置' : '';
					}
				},
				{display: 'ON条件', name: 'join_condition', align: 'center', width: 200, editor: { type: 'text'},
					render: function(rowdata, rowindex, value) {
						if(rowindex == 0){
                   			return "主表条件为空";
                   		}
						return rowdata.join_condition_text ? '已设置' : '';
					}
				}
			],
			data: null, width: '99.7%', height: 455, checkbox: false, rownumbers: true, delayLoad: true, rowDraggable: true,  
			enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false, isSingleCheck: true,
			onBeforeEdit: function (e) {
				if(e.column.name == 'col_code'){
					var tables = [e.record.tab_code]
					//func参数是用来回显函数与参数信息
					var parm = {'tab_codes': tables, func: JSON.stringify(e.record.func)};
			    	e.column.editor = {
			    			type: 'popup', parms: parm, valueField: 'col_code', textField: 'col_name', 
		            		grid: {
	                        	url: "queryHrTableColByCodes.do?isCheck=false", parm: parm, columns: [
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
				if(e.column.name == 'join_condition'){
					var tables = [];
					$.each(this.rows, function(index, item){
						tables.push(item.tab_code);
					})
					var parm = {'tab_codes': tables};
			    	e.column.editor = {
			    			type: 'popup', valueField: 'join_condition', 
		            		grid: {
	                        	data: {Rows:e.record.join_condition_grid}, columns: [
	                        		{ display: '连接符', name: 'conn_mode', width: 100, align: 'center', textField: "conn_mode_text", 
	                        			editor : {type: 'select', data: optionData.connMode2},
	                        			render: function(rowdata, rowindex, value) {
	                                   		if(rowindex == 0){
	                                   			rowdata.conn_mode = 'ON';
	                                       		rowdata.conn_mode_text = 'ON';
	                                   		}else if(!value){
	                                   			rowdata.conn_mode = 'AND';
	                                       		rowdata.conn_mode_text = 'AND';
	                                   		}
	                						return rowdata.conn_mode_text;
	                					}
	                        		}, 
	                        		{ display: '左侧字段', name: 'left_field', width: 200, align: 'left', editor: {type: 'text'}
                        			},
	                        		{ display: '连接条件', name: 'condition', width: 100, align: 'center', textField: "condition_text", 
	                                	editor : {type: 'select', data: optionData.condition},
	                                	render: function(rowdata, rowindex, value) {
	                                   		if(!value){
	                                   			rowdata.condition = '=';
	                                       		rowdata.condition_text = '等于';
	                                   		}
	                						return rowdata.condition_text;
	                					}
	                                },
	                                { display: '右侧字段', name: 'right_field', width: 200, align: 'left', editor: {type: 'text'} }
	                        	],
	                        	cssClass: 'joinCond',
	                        	checkbox: true, usePager: false, enabledEdit: true, isAddRow: false,
	                        	onBeforeEdit: function(e){
	                        		if(e.column.name == 'left_field' || e.column.name == 'right_field'){
	                        			$.ligerDialog.open({ 
	                        				url: 'hrDataItemAddPage.do?isCheck=false', 
	                        				title: '选择或编辑字段', 
	                        				height: 300,width: 300, 
	                        				data: parm,
	                        				buttons: [ { text: '确定', onclick: function (item, dialog) { 
	                        						var val = dialog.frame.getValue();
	                        						e.value = val;
	                        						dialog.close();
	                        					} }, { text: '取消', onclick: function (item, dialog) { 
	                        						dialog.close(); 
	                        					} }
	                        				] 
	                        			});
	                        		}
	                        		return true;
	                        	},
	                			toolbar: {
	                				items: [ 
	                					{text: '添加行', click: joinCondAddRow, icon: 'add'}, 
	                					{line: true}, 
	                					{text: '删除行', click: joinCondDeleteRow, icon: 'delete'}]
	                			}
	                    	},
	                    	onSelected: f_onSelectedCond,
						}
				}
			},
			/* toolbar: {
				items: [ 
					{text: '添加数据表', id: 'addDataTable', click: addField, icon: 'add'}, 
					{line: true}, 
					{text: '删除数据表', id: 'delDataTable', click: delField, icon: 'delete'}]
			} */
		});
		
		function joinCondAddRow(){
			var joinCondGrid = $(".joinCond:last").ligerGetGridManager();
			joinCondGrid.addRow();
		}
		
		function joinCondDeleteRow(){
			var joinCondGrid = $(".joinCond:last").ligerGetGridManager();
			joinCondGrid.deleteSelectedRow2();
		}
		
		function f_onSelected(e){
			if (!e.data || !e.data.length) return;
			var tab_cols = "";
			var lastEditRow = dataTableGrid.lastEditRow;
			var func = [];
	        $.each(e.data, function(index, item){
	       		/* if(item.func != null && item.func.length > 0) {
	       			tab_cols += item.func.format(lastEditRow.tab_code + '.' + item.col_code, item.param) + ',';
				}else{
					tab_cols += lastEditRow.tab_code + '.' + item.col_code + ',';
				} */
	       		if(item.func){
	       			func.push({tab_code: lastEditRow.tab_code, col_code: item.col_code, func: item.func, func_text: item.func_text, param: item.param});
	       		}
	       		
	        });
	        tab_cols = (tab_cols.substring(tab_cols.length-1)==',')?tab_cols.substring(0,tab_cols.length-1):tab_cols;
	        dataTableGrid.updateRow(lastEditRow, {
	         func: func,
	       	 //tab_cols: tab_cols
	        });
		}
		
		function f_onSelectedCond(e){
			var joinCondGrid = $(".joinCond:last").ligerGetGridManager();
			if (!e.data || !e.data.length){
				e.data = joinCondGrid.rows;
			};
			var join_condition = "";
			$.each(e.data, function(index, item){
				join_condition += item.conn_mode +' '+ item.left_field +' ' + item.condition +' ' + item.right_field +' ';
	        });
			//var join_condition_grid = {Rows:e.data, Total:e.data.length} ;
			var join_condition_grid = e.data ;
			var lastEditRow = dataTableGrid.lastEditRow;
	        dataTableGrid.updateRow(lastEditRow, {
	        	join_condition_text: join_condition,
	        	join_condition_grid: join_condition_grid
	        });
		}
	}
	
	//加载表格数据
	function loadGridData(designCode){
		design_code = designCode;
		//tree.options.url = 'queryDBTableTree.do?isCheck=false&design_code='+design_code;
		//tree.reload();
		ajaxPostData({
			url: 'queryDesignQueryCol.do?isCheck=false',
			data: {
				'design_code': designCode
			},
			delayCallback: true,
			success: function(data) {
				var tableData, conditionData, groupData, sortData;
				if(data){
					tableData = data.tableData;
					conditionData = data.conditionData;
					groupData = data.groupData;
					sortData = data.sortData;
					if(data.tableData){
						loadLabelBtn(data.tableData);//加载数据标签
					}
				}
				$('.l-grid').width($('#accordion').width());
				dataTableGrid.loadData({Rows: tableData});
				conditionGrid.loadData({Rows: conditionData});
				groupGrid.loadData({Rows: groupData});
				sortGrid.loadData({Rows: sortData});
				
				//设置SQL语句
				setSqlData(data.sql);
				
				//加载页面设置显示数据
				configgrid.loadData({Rows: data.page ? JSON.parse(data.page) : null});
			    
			    if(designCode){
        	 		//toptoolbar.setEnabled("chooseTable");
        	 		toptoolbar.setEnabled("save");
        	 		changeBtnState();
       			}else{
       				//toptoolbar.setDisabled("chooseTable");
       				toptoolbar.setDisabled("save");
       				changeBtnState();
       			}
			}
		})
	}
	
	//设置SQL值
    function setSqlData(data){
        editor.setValue('');
        function format() {
            var str = sqlFormatter.format(data, {language: 'sql'});
            editor.setValue(str);
        }
        if(data){
            format();
        }
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
		
		(parent.$.ligerui.getPopupFn({
	            top : 50,
	            height: 420,
	            onSelect: function (e) {
	            	curr_grid.deleteAllRows();
	            	curr_grid.addRows(f_appendData(oldGrid, e.data));
	            },
	            grid: {
	                columns: [
	                { display: '数据表', name: 'tab_code', width: 150 },
	                { display: '数据列编码', name: 'col_code', width: 150 },
	                { display: '数据列名称', name: 'col_name', width: 150 }
	                ], isScroll: true, checkbox: true,dataAction: 'server', dataType: 'server', usePager: false,
	                url: 'queryHrTableColByCodes.do?isCheck=false',parms: parm, width: '95%', 
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
	
	function getSaveData(){
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
        
        return {
                tableData: tableData,
                conditionData: conditionData,
                groupData: groupData,
                sortData: sortData
        }
        
        
	}
	
	//保存数据表字段信息
	function save() {
		var configgrid = $("#configgrid").ligerGetGridManager();
		configgrid.endEdit();
		if (design_code != null && design_code != '') {
			
			var configgridData = configgrid.getData();
			
			$(configgridData).each(function(i) {
                this.is_view = $("#is_view" + this.col_code, $("#configgrid")).is(":checked") ? '1' : '0';
            });
			
			var design_query_col = getSaveData();
			
			ajaxPostData({
				url: 'saveDesignQueryCol.do?isCheck=false',
				data: {
					'design_code': design_code,
					'design_query_col': JSON.stringify(design_query_col),
					'design_query_page': JSON.stringify(configgridData),
					'design_query_sql': editor.getValue()
				},
				delayCallback: true,
				success: function(data) {
					loadGridData(design_code);
				}
			})
		} else {
			$.ligerDialog.error('请选择树节点');
		}
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
    
    
  //加载SQL列标签
    function loadLabelBtn(table){
        $("#column_label").empty();
        $("#sys_label").empty();
        var tables = [];
        
        $.each(table, function(index, item){
            tables.push(item.tab_code);
        });
        var parm = {'tab_codes': tables.join()};
        
        ajaxPostData({
            url: "queryHrTableColByCodes.do?isCheck=false",
            data: parm,
            success: function(res) {
                $.each(res.Rows, function(i, v){
                    $("#column_label").append(labelEle(v.col_code, v.tab_name+'·'+v.col_name, v.col_code));
                })
            }
        })
        
        $.each(sc.sysLabel, function(index, value){
            $("#sys_label").append(labelEle(value.id, value.text, value.val));
        })
    }
    
    function labelEle(id, text, val){
        return '<li><div class="label_btn l-button" id="'+id+'">['+text+']<div class="l-button-l"></div><div class="l-button-r"></div><span>['+val+']</span></div></li>';
    }
    
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;">
		<!-- <div position="left" title="数据表">
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 90px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div> -->
		<div position="center" title="" style="overflow: auto">
			<div id="toptoolbar"></div>
			<div id="accordion"> 
		        <div title="第一步：数据表">
		          <div id="data_table_grid"></div>
		        </div>
		        <div title="第二步：查询条件">
		          <div id="condition_grid"></div>
		          <script>
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
		                  {display: '数据表', name: 'tab_name', align: 'center', width: 200,
		                      render: function(rowdata, rowindex, value) {
		                          return value;
		                      }
		                  },
		                  {display: '数据列', name: 'col_name', align: 'center', width: 200,
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
		              data: null, width: '99.7%', height: 455, checkbox: false, rownumbers: true, delayLoad: true,
		              enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,rowDraggable: true,
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
		                                              url : '../../queryHrFiiedTabStruc.do?isCheck=false',
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
		              /* toolbar: {
		                  items: [ 
		                      {text: '添加条件', id: 'addCondition', click:  function(){field_import(conditionGrid)}, icon: 'add'}, 
		                      {line: true}, 
		                      {text: '删除条件', id: 'delCondition', click: delField, icon: 'delete'}]
		              } */
		          });
		          </script>
		        </div> 
		        <div title="第三步：分组条件"> 
		          <div id="group_grid"></div>  
		          <script>
		          groupGrid = $("#group_grid").ligerGrid({
		              columns: [
		                  {display: '数据表', name: 'tab_name', align: 'left', width: 200,
		                      render: function(rowdata, rowindex, value) {
		                          return value;
		                      }
		                  },
		                  {display: '数据项名称', name: 'col_name', align: 'left', width: 200,
		                      render: function(rowdata, rowindex, value) {
		                          return value;
		                      }
		                  }
		              ],
		              data: null, width: '99.7%', height: 455, checkbox: false, rownumbers: true, delayLoad: true, 
		              enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,
		              /* toolbar: {
		                  items: [ 
		                      {text: '添加分组', id: 'addGroup', click: function(){field_import(groupGrid)}, icon: 'add'}, 
		                      {line: true}, 
		                      {text: '删除分组', id: 'delGroup', click: delField, icon: 'delete'}]
		              } */
		          });
		          </script>
		        </div>
		        <div title="第四步：排序条件"> 
		          <div id="sort_grid"></div>
		          <script>
		          sortGrid = $("#sort_grid").ligerGrid({
		              columns: [
		                  {display: '数据表', name: 'tab_name', align: 'left', width: 200,
		                      render: function(rowdata, rowindex, value) {
		                          return value;
		                      }
		                  },
		                  {display: '数据项名称', name: 'col_name', align: 'left', width: 200,
		                      render: function(rowdata, rowindex, value) {
		                          return value;
		                      }
		                  },
		                  {display: "排序", name: "sort_mode", align: "left", width: 120, textField: "sort_mode_text",
		                      editor: {type: 'select', data: optionData.sortMode},
		                      render: function(rowdata, rowindex, value) {
		                          return rowdata.sort_mode_text;
		                      }
		                  },
                          {display: '显示顺序', name: 'sort', align: 'left', width: 80, editor:{type: 'text'},
                              render : function(rowdata, rowindex, value) {
                                  return value;
                              }
                          },
		              ],
		              data: null, width: '99.7%', height: 455, checkbox: false, rownumbers: true, delayLoad: true,
		              enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false
		              /* toolbar: {
		                  items: [ 
		                      {text: '添加排序', id: 'addSort', click: function(){field_import(sortGrid)}, icon: 'add'}, 
		                      {line: true}, 
		                      {text: '删除排序', id: 'delSort', click: delField, icon: 'delete'}]
		              } */
		          });
		          </script>
                </div>
                <div title="第五步：SQL语句设置"> 
                    <div id="sql_container">
	                    <div position="center">
	                        <div id="toptoolbar"></div>
	                        <textarea class="liger-textarea" id="code" name="sql_statement" validate="{required:true}"></textarea>
	                    </div>
	                    <div position="right">
	                        <div id="label_set">
	                            <div title="数据标签">
	                                <ul id="column_label">
	                                </ul>
	                            </div>
	                            <div title="系统标签">
	                                <ul id="sys_label">
	                                </ul>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <script>
	                $("#sql_container").ligerLayout({rightWidth: 180, height: 425, isRightCollapse: true});
	                var height = $("#sql_container .l-layout-center").height();
	                $("#label_set").ligerAccordion({height: height-25, changeHeightOnResize: true});
	                //SQL语句块标签选中插入到光标处
	                $("#label_set").on("click", ".label_btn", function(e){
	                    var val = $(this).find("span").text();
	                    //$('textarea[name="sql_statement"]').insertContent(val);
	                    editor.replaceSelection(val);
	                });
	                </script>
                </div>
                <div title="第六步：页面显示设置"> 
                    <div id="configgrid"></div>
                    <script>
                    configgrid= $("#configgrid").ligerGrid({
                    	columns: [
                            {display: '数据表编码', name: 'tab_code', align: 'left', width: 160,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            }, 
                            {display: '数据表名称', name: 'tab_name', align: 'left', width: 160,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                            {display: '数据列编码', name: 'col_code', align: 'left', width: 200,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            }, 
                            {display: '数据列名称', name: 'col_name', align: 'left', width: 160,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                            {display: '是否显示', name: 'is_view', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 0){
                                        return "<input name='is_view' type='checkbox' id='is_view"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_view' type='checkbox' checked='checked' id='is_view"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: '显示顺序', name: 'sort', align: 'left', width: 80, editor:{type: 'text'},
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                        ],
                        data: null, width: '99.7%', height: 455, checkbox: false, rownumbers: true, delayLoad: true,
                        enabledEdit: true, selectRowButtonOnly: true, isAddRow: false, usePager: false,
                    });
                    </script>
                </div>
		    </div>  
		</div>
	</div>
	<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/editor.js"></script>
    <script type="text/javascript">
        $.ajax({
            type : "post",
            url :  '../hrtablestruc/queryHrTableWords.do?isCheck=false',
            dataType : "json",
            async:false,
            success: function(r){
              if(r){
                  loadEditor(r);
              }
            }
        });
    </script>
</body>
</html>
