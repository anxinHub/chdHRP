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
<script src="<%=path%>/lib/hrp/hr/editor/codemirror.js"></script>
<script src="<%=path%>/lib/hrp/hr/editor/sql.js"></script>

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
	height: 26px;
	padding: 1px 1px 1px 5px;
	border: 1px solid #aecaf0;
	border-radius: 1px;
	outline: none;
	width: 120px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}

#navtab .l-tab-content{
	height: 100%;
}
.label_btn{    
	float: none !important;
    margin: 4px 10px;
    padding: 0 4px;
    width: auto !important;
}

.left-buttons{
    padding-left: 8px;
}

.left-buttons ul{
	/* display: flex;
    flex-direction: row;
    justify-content: center; */
}
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
    var toptoolbar;
	var tree, treeRMenu, subTree, actionNodeID, subActionNodeID;
	var grid, tab1Grid, tab2Grid, tab3Grid;
	var currTextareaEle, editor;
	var gridManager = null;
	var treeSearchKey = '', treeSearchCheck = false;
	var toolbarShowId = 'maingrid';
	
	var toolbarOptions1 = {items :[ 
        {text: '查询', id: 'search', click: search, icon: 'search'}, 
        {line: true}, 
        {text: '添加行', id: 'add', click: addRow, icon: 'add', disable: true},
        {line: true}, 
        {text: '删除行', id: 'delete', click: deleteRow, icon: 'delete', disable: true}, 
        {line: true}, 
        {text: '保存', id: 'save', click: save, icon: 'save', disable: true},
        {line: true}, 
        {text: '重构表结构', id: 'createTableStruc', click: createTableStruc, icon: 'settings', disable: true},
        {line: true},
        {text: '更新表结构', id: 'alterTableStruc', click: alterTableStruc, icon: 'settings', disable: true},
        {line: true}
        /* {text: '导入', id: 'import', click: importData, icon: 'up'}, 
        {line: true}, 
        {text: '导出', id: 'export', click: exportData, icon: 'down'}, 
        {line: true},  
        {text: '导入旧版本数据', id: 'importOld', click: importOldData, icon: 'up'}*/
    ]};
	
	var toolbarOptions2 = {items :[
        {text: '新建', id: "create_sql", click: newCreateSql, icon: 'add', disable: true},
        {line: true},
        {text: '生成', id: "gen_sql", click: genSql, icon: 'add', disable: true},
        {line: true },
        {text: '保存', id: "save_sql", click: saveSql, icon: 'save', disable: true}, 
        {line: true},
        {text: '清空', id: "clearup_sql", click: clearupSql, icon: 'refresh', disable: true}, 
        {line: true},
        {text: '删除', id: "delete_sql", click: deleteSql, icon: 'delete', disable: true},
        {line: true},
        {text: '格式化SQL', id: "format_sql", click: formatSql, icon: 'format'}
    ]};
	
	var toolbarOptions3 = {items :[ 
        {text : '预览', id : 'preview', click : preview, icon : 'search', disable: true}, 
        {line: true}, 
        {text : '保存', id : 'saveColumnSet', click : saveColumnSet, icon : 'save', disable: true}
    ]};
	
	$(function() {
  	
		//布局
		$("#layout").ligerLayout({leftWidth: 230, minLeftWidth: 230, allowLeftResize: false});
		$("#sql_accordion").ligerLayout({leftWidth: 170, rigthWidth: 170, height: 450, isRightCollapse: true});
		var height = $("#sql_accordion .l-layout-center").height();
        $("#label_set").ligerAccordion({height: height-40, changeHeightOnResize: true});
		
		//tab页签
		//$("#navtab").ligerTab({onBeforeSelectTabItem: function(){gridManager.reload()}});
		
		$("#accordion").ligerAccordion({
            width: '100%',
            height: $('body').height() - 30
        });
		
		toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions1);
		
		loadTree();
		loadGrid();
		loadSubTree();
		
		$('.l-grid').width($('#accordion').width());
		
		//tree快速定位
		$("#search_input").keyup(function(e) {
			treeSearchKey = $("#search_input").val();
			tree.options.url = 'queryHrTableStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck;
			tree.reload();
		});
		
		//tree快速定位停用复选框
		$("#is_stop").change(function(e) {
			treeSearchCheck = $("#is_stop").is(':checked');
			tree.options.url = 'queryHrTableStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck;
			tree.reload();
		});
		
		$(".l-accordion-header").click(function (){
            var togglebtn = $(".l-accordion-toggle:first", this);
            if (togglebtn.hasClass("l-accordion-toggle-open")){
            	toolbarShowId = $(this).next(".l-accordion-content").children().attr('id');
                /* if(!actionNodeID){
                    return;
                } */
                changeBtnState();
            }else{
            	toptoolbar =  $("#toptoolbar").ligerToolBar(null);
            }
        });
	});
	
	//改变按钮状态
    function changeBtnState(){
        switch(toolbarShowId)
        {
            case 'maingrid':
            	toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions1);
            	if(actionNodeID){
                    toptoolbar.setEnabled("add");
                    toptoolbar.setEnabled("delete");
                    toptoolbar.setEnabled("save");
                    toptoolbar.setEnabled("createTableStruc");
                    toptoolbar.setEnabled("alterTableStruc");
                }else{
                    toptoolbar.setDisabled("add");
                    toptoolbar.setDisabled("delete");
                    toptoolbar.setDisabled("save");
                    toptoolbar.setDisabled("createTableStruc");
                    toptoolbar.setDisabled("alterTableStruc");
                }
                break;
            case 'sql_accordion':
            	toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions2);
            	if(actionNodeID){
            		toptoolbar.setEnabled("create_sql");
            		toptoolbar.setEnabled("gen_sql");
            		toptoolbar.setEnabled("save_sql");
            		toptoolbar.setEnabled("clearup_sql");
            		toptoolbar.setEnabled("delete_sql");
                }else{
                	toptoolbar.setDisabled("create_sql");
                	toptoolbar.setDisabled("gen_sql");
                	toptoolbar.setDisabled("save_sql");
                	toptoolbar.setDisabled("clearup_sql");
                	toptoolbar.setDisabled("delete_sql");
                }
                editor.refresh();
                break;
            case 'tab1Grid':
            	toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions3);
            	if(actionNodeID){
            		toptoolbar.setEnabled("saveColumnSet");
                    toptoolbar.setEnabled("preview");
                }else{
                    toptoolbar.setDisabled("saveColumnSet");
                    toptoolbar.setDisabled("preview");
                }
                break;
            case 'tab2Grid':
            	toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions3);
            	if(actionNodeID){
                    toptoolbar.setEnabled("saveColumnSet");
                    toptoolbar.setEnabled("preview");
                }else{
                    toptoolbar.setDisabled("saveColumnSet");
                    toptoolbar.setDisabled("preview");
                }
                break;
            case 'tab3Grid':
            	toptoolbar =  $("#toptoolbar").ligerToolBar(toolbarOptions3);
            	if(actionNodeID){
                    toptoolbar.setEnabled("saveColumnSet");
                    toptoolbar.setEnabled("preview");
                }else{
                    toptoolbar.setDisabled("saveColumnSet");
                    toptoolbar.setDisabled("preview");
                }
                break;
            default:
            	toptoolbar =  $("#toptoolbar").ligerToolBar(null);
        }
        
        
    }
    
	//查询
	function search() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'tab_code',value : actionNodeID});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadGridData(){
        ajaxPostData({
            url : '../hrcolumnset/queryHrColumnSet.do?isCheck=false',
            data : {
                'tab_code' : actionNodeID
            },
            delayCallback : true,
            success : function(data) {
                $('.l-grid').width($('#navtab').width());
                tab1Grid.set("data", []);
                tab2Grid.set("data", []);
                tab3Grid.set("data", []);
                tab1Grid.set("data", JSON.parse(data.gridSetData));
                tab2Grid.set("data", JSON.parse(data.searchSetData));
                tab3Grid.set("data", JSON.parse(data.formSetData));
            }
        })
    }
	
	//加载左侧tree
	function loadTree() {
		treeRMenu = $.ligerMenu({ top: 100, left: 100, width: 120, 
			items: [{ text: '启用', click: m_open },{ text: '停用', click: m_close }]});
		
		tree = $("#main_tree").ligerTree({
			url: 'queryHrTableStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck, 
   		 	ajaxType: 'post' ,
   		 	idFieldName :'id',
   		 	textFieldName: 'name',
         	parentIDFieldName :'pId', 
         	nodeWidth:'100%',
   		 	checkbox: false,
   		    isExpand: false, 
         	onContextmenu: function (node, e){ 
        		actionNodeID = node.data.id;
        		treeRMenu.show({ top: e.pageY, left: e.pageX });
        		return false;
    		},
    		onClick: function(node, target){
    			if(node.data.pId && this.getSelected()){
    				actionNodeID = node.data.id;
    				$('.l-grid').width($('#accordion').width());
        			search();
        			
        			loadGridData();
        			
        			subTree.options.url = 'querySqlStatement.do?isCheck=false&tab_code=' + actionNodeID;
        			subTree.reload();
        			
        			changeBtnState();
        			
    			}
    		}
     	});
	}
	
	
	//加载SQL节点tree
	function loadSubTree(){
		subTree = $("#subTree").ligerTree({
			url: '', 
   		 	ajaxType: 'post' ,
   		 	idFieldName :'sql_code',
   		 	textFieldName: 'sql_name',
   		 	parentIDFieldName :'tab_code', 
         	nodeWidth:'100%',
   		 	checkbox: false,
   		 	treeLine: true,
   		 	onClick: function(node, target){
   		 		//if(node.data.tab_code){
	   		 		subActionNodeID = node.data.sql_code;
		   			loadSqlData(node.data);
   		 		//}
   			}
        });
	}

	//加载表格
	function loadGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '数据列编码', name : 'col_code',align : 'left', width : 100, editor: { type: 'text'},
					render : function(rowdata, rowindex, value) {
						return value ? value.toUpperCase() : value;
					}
				}, 
				{display: '数据列名称', name: 'col_name', align: 'left', width: 120, editor: { type: 'text'},
					render : function(rowdata, rowindex, value) {
						return value;
					}
                },
                {display: "类型", name: "data_type_code", align: "left", width: 120, textField: "data_type_name",
                    editor: {
                        type: 'select', data: optionData.fieldType
                    },
                    render: function(rowdata, rowindex, value) {
						return rowdata.data_type_name;
					}
				},
                {display: "属性", name: "filed_length", align: "right", dataType: "integer", width: 120, editor: { type: 'text'},
                    render : function(rowdata, rowindex, value){
                    	return value;
                    }
                },
                {display: "取值方式", name: "value_mode_code", align: "left", width: 120, textField: "value_mode_name",
                    editor: {
                        type: 'select', data: optionData.valueMode
                    },
                    render: function(rowdata, rowindex, value) {
						return rowdata.value_mode_name;
					}
               	},
                {display: "取值代码", name: "field_tab_code", align: "left", width: 120, textField: "field_tab_name", editor: { type: 'text'},
                  	render: function(rowdata, rowindex, value) {
						return rowdata.field_tab_name;
					}
				},
				{display: "是否内置", name: "is_innr", align: "left", width: 120, textField: "is_innr_text",
					editor : {type: 'select', data: optionData.whether},
					render: function(rowdata, rowindex, value) {
						return rowdata.is_innr_text;
					}
				},
				{display: "是否唯一", name: "is_unique", align: "left", width: 120, textField: "is_unique_text",
                     editor: {type: 'select', data: optionData.whether},
                    render : function(rowdata, rowindex, value) {
                    	return rowdata.is_unique_text;
                    }
                },
                {display: "是否主键", name: "is_pk", align: "left", width: 120, textField: "is_pk_text",
                    editor: {type: 'select', data: optionData.whether},
                    render : function(rowdata, rowindex, value) {
                    	return rowdata.is_pk_text;
                    }
                }, 
                {display: "排序", name: "sort", align: "right", width: 60, dataType: "integer", editor: { type: 'int'}
                }, 
                {display: "备注", name: "note", align: "left", width: 120, editor: { type: 'text'}
                }
            ],
			dataAction : 'server', dataType : 'server', usePager : true, url : 'queryHrColStruc.do?isCheck=false',
			width : '99.7%', height : '480', checkbox : true, rownumbers : true, usePager: false,
			delayLoad : false, enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
			onAfterShowData:function(){loadLabelBtn();},
			onBeforeEdit: function (e) {
				if(e.column.name == 'field_tab_code'){
					var value_mode = e.record.value_mode_code;
					if(value_mode == null || value_mode == ''){
						$.ligerDialog.warn("请输入取值方式");
					}else{
						switch(value_mode)
						{
						    case '01':
						    	$.ligerDialog.warn("取值方式为输入项，此处为空");
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
											columns : [ {display: '代码表编码', name: 'field_tab_code', align: 'left', width: 120}, 
														{display: '代码表名称', name: 'field_tab_name', align: 'left', width: 120}, 
						                                {display: '代码表分类', name: 'type_filed_name', align: 'left', width: 120}, 
						                                {display: '备注', name: 'note', align: 'left', width: 120}
											          ],
											switchPageSizeApplyComboBox : false,
											gid: 'fieldTabGrid', 
											url: 'queryHrFiledTableStruc.do?isCheck=false',
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
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//添加字段行
	function addRow(){
		gridManager.addRow();
	}
	
	//删除字段
	function deleteRow(){
		var selectRows = gridManager.getCheckedRows();
        if (selectRows.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	$.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		gridManager.deleteRange(selectRows);
            		setTimeout(function(){ save(); }, 200);
            	}
            }); 
        }	      
	}
	
	//保存数据表字段信息
	function save() {
		gridManager.endEdit();
		if (actionNodeID != null && actionNodeID != '') {
			var allData = gridManager.data;
			var addDataVo = [];
		    //console.log(allData)
			var flag = true; //用来判断
			var flag02 = true; //用来判断
			var reg = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
			var str = "";
			$(allData.Rows).each(function(i) {
				if(this.__status != 'delete'){
					if (!(flag = reg.test(this.col_code))) {
	                    flag = false;
	                    str += "第" + (i + 1) + "行、";
	                }
	                if (this.sort == "" || this.sort == null) {
	                    flag02 = false;
	                    str += "第" + (i + 1) + "行、";
	                }
					addDataVo.push({
	                    col_code: this.col_code,
	                    col_name: this.col_name,
	                    data_type_code: this.data_type_code,
	                    data_type_name: this.data_type_name,
	                    filed_length: this.filed_length,
	                    value_mode_code: this.value_mode_code,
	                    value_mode_name: this.value_mode_name,
	                    field_tab_code: this.field_tab_code,
	                    field_tab_name: this.field_tab_name,
	                    is_innr: this.is_innr,
	                    is_innr_text: this.is_innr_text,
	                    is_unique: this.is_unique,
	                    is_unique_text: this.is_unique_text,
	                    is_pk: this.is_pk,
	                    is_pk_text: this.is_pk_text,
	                    sort: this.sort,
	                    note: this.note,
	                    status: this.__status
	                });
				}
			});

			if (!flag) {
				$.ligerDialog.error(str.substring(0, str.length - 1)
						+ "数据列编码非法，请重新命名。");
				return;
			}
			if (!flag02) {
				$.ligerDialog.error(str.substring(0, str.length - 1)
						+ "排序列不能为空。");
				return;
			}

			ajaxPostData({
				url : 'saveHrColStruc.do?isCheck=false',
				data : {
					'tab_code' : actionNodeID,
					'tab_col' : JSON.stringify(addDataVo)
				},
				delayCallback : true,
				success : function(data) {
					search();
					subTree.reload();
				}
			})
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}
	
	//创建表结构
	function createTableStruc(){
		if(grid.getUpdated().length > 0 || grid.getAdded().length > 0){
			$.ligerDialog.warn("表格数据已变更，请先执行保存操作。");
			return;
		}
		$.ligerDialog.confirm('创建表结构会清空表数据，是否继续？', function (yes) {
			if(yes){
				ajaxPostData({
	                url : 'createTableStruc.do?isCheck=false',
	                data : {
	                    'tab_code' : actionNodeID
	                },
	                delayCallback : true,
	                success : function(data) {
	                    search();
	                    subTree.reload();
	                }
	            })
			}
		});
		
	}
	
	//修改表结构
	function alterTableStruc(){
		if(grid.getUpdated().length > 0 || grid.getAdded().length > 0){
            $.ligerDialog.warn("表格数据已变更，请先执行保存操作。");
            return;
        }
        $.ligerDialog.confirm('记录不为空时，主键与非空字段不允许修改，是否继续？', function (yes) {
            if(yes){
                ajaxPostData({
                    url : 'alterTableStruc.do?isCheck=false',
                    data : {
                        'tab_code' : actionNodeID
                    },
                    delayCallback : true,
                    success : function(data) {
                        search();
                        subTree.reload();
                    }
                })
            }
        });
        
    }
	
	//导入
	function importData(){
		$.ligerDialog.open({url: 'importPage.do?isCheck=false', height: 500,width: 800, 
			title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
	
	//导出
	function exportData(){
		var id = '';
		var name = '';
		if(tree.getSelected()){
			id = tree.getSelected().data.id;
			name = tree.getSelected().data.tab_name;
            /* $.each(tree.getChecked(), function(index, item){
                if(item.data.pId){
                    ids += item.data.id + ','
                }
            });
            location.href = "export.do?isCheck=false&ids="+ids; */
        }
		location.href = "export.do?isCheck=false&tab_code="+id+"&tab_name="+name;
	}
	
	//导入旧版本数据
	function importOldData(){
		ajaxPostData({
			url : 'importOldData.do?isCheck=false',
			data : {},
			delayCallback : true,
			success : function(data) {
				tree.reload();
			}
		})
	}
	
	//新建SQL
	function newCreateSql(){
		resetForm();
		$("#sql_code").ligerGetTextBoxManager().setEnabled();
		$("#sql_name").ligerGetTextBoxManager().setEnabled();
		$("#sql_container :checkbox[name='is_custom']").prop("checked", true);
		subActionNodeID = null;
	}
	
	//生成SQL
	function genSql(){
		save();
	}
	
	//保存SQL
	function saveSql(){
		var data = {
				sql_code: $('#sql_container input[name="sql_code"]').val(),
				sql_name: $('#sql_container input[name="sql_name"]').val(),
				is_custom: $('#sql_container input[name="is_custom"]').is(':checked')?1:0,
				is_proc_jfunc: $('#sql_container input[name="is_proc_jfunc"]:checked').val(),
				//sql_statement: $('#sql_container textarea[name="sql_statement"]').val()
				sql_statement: editor.getValue()
			};
			ajaxPostData({
				url : 'saveSqlStatement.do?isCheck=false',
				data : {
					'tab_code' : actionNodeID,
					'sql_code' : subActionNodeID,
					'tab_sql' : JSON.stringify(data)
				},
				delayCallback : true,
				success : function(data) {
					subTree.reload();
				}
			}) 
	}
	
	//清空SQL语句
	function clearupSql(){
		$("#sql_container textarea[name='sql_statement']").val("");
		editor.setValue("");
	}
	
	//删除SQL
	function deleteSql(){
		$.ligerDialog.confirm('确定删除?', function(e) {
			if(e){
				ajaxPostData({
					url : "deleteSqlStatement.do?isCheck=false",
					data : {
						'tab_code' : actionNodeID,
						'sql_code' : subActionNodeID,
					},
					success : function(res) {
						if (res.state == "true") {
							subActionNodeID = null;
							subTree.reload();
							resetForm();
						}
					}
				});
			}
		});
	}
	
	//格式化SQL
	function formatSql(){
		// 获取输入的值
	    var range = getSelectedRange();
	    editor.autoFormatRange(range.from, range.to);

	    function format() {
	        var str = sqlFormatter.format(editor.getValue(), {language: 'sql'});
	        editor.setValue(str);
	    }
	    format();
	}
	
	//添加数据表
	function addTabStruc() {
		parent.$.ligerDialog
				.open({
					url : 'hrp/hr/sc/hrtablestruc/hrTableStrucAddPage.do?isCheck=false',
					height : 400,
					width : 700,
					title : '数据表构建添加页',
					modal : true,
					parentframename : window.name,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveData();
							tree.reload();
							search();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
	}

	//修改数据表
	function updateTabStruc() {
		//获取树当前选择项
		if (tree.getSelected()) {
			if (tree.getSelected().data.pId) {
				var parm = 'tab_code=' + tree.getSelected().data.id;
				parent.$.ligerDialog
						.open({
							url : 'hrp/hr/sc/hrtablestruc/hrTableStrucUpdatePage.do?isCheck=false&'
									+ parm,
							title : '数据表构建修改页',
							height : 400,
							width : 700,
							modal : true,
							parentframename : window.name,
							buttons : [ {
								text : '确定',
								onclick : function(item, dialog) {
									dialog.frame.saveData();
									tree.reload();
									search();
								},
								cls : 'l-dialog-btn-highlight'
							}, {
								text : '取消',
								onclick : function(item, dialog) {
									dialog.close();
								}
							} ]
						});
			} else {
				$.ligerDialog.error('请选择数据表');
			}
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}

	//删除数据表
	function deleteTabStruc() {
		//获取树当前选择项
		if (tree.getSelected()) {
			/* if (tree.getSelected().data.pId == null) {
				$.ligerDialog.error('请选择末级节点');
				return;
			} */
			$.ligerDialog.confirm('确定删除?', function(e) {
				if(e){
					ajaxPostData({
						url : "deleteHrTableStruc.do?isCheck=false",
						data : {
							tab_code : tree.getSelected().data.id
						},
						success : function(res) {
							if (res.state == "true") {
								tree.reload();
								search();
							}
						}
					})
				}
			});
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}
	
	//数据表启用
	function m_open(){
       	ajaxPostData({
               url: 'updateHrTableStrucIsStop.do?isCheck=false',
               data: {'tab_code': actionNodeID, 'is_stop': '0'},
               delayCallback: true,
               success: function (data) {
            	   tree.reload();
               }
           })
	}
	
	//数据表停用
	function m_close(){
       	ajaxPostData({
               url: 'updateHrTableStrucIsStop.do?isCheck=false',
               data: {'tab_code': actionNodeID, 'is_stop': '1'},
               delayCallback: true,
               success: function (data) {
            	   tree.reload();
               }
           })
	}
	
	//加载SQL信息
	function loadSqlData(data){
		resetForm();
		$("#sql_container input[name='sql_code']").val(data.sql_code);
		$("#sql_container input[name='sql_name']").val(data.sql_name);
		$("#sql_container textarea[name='sql_statement']").val(data.sql_statement);
		$("#sql_container :checkbox[name='is_custom']").prop("checked", data.is_custom == 1);
		$("#sql_container :radio[name='is_proc_jfunc'][value='"+data.is_proc_jfunc+"']").prop("checked", true);
		var str = sqlFormatter.format(data.sql_statement, {language: 'sql'});
        editor.setValue(str);
		if(!data.is_custom){
			$("#sql_code").ligerGetTextBoxManager().setDisabled();
			$("#sql_name").ligerGetTextBoxManager().setDisabled();
			
		}else{
			$("#sql_code").ligerGetTextBoxManager().setEnabled();
			$("#sql_name").ligerGetTextBoxManager().setEnabled();
		}
	}
	
	
	//重置sql页面表单
	function resetForm(){
		$("#sql_container").find('input[type=text],textarea').each(function() {
			$(this).val('');
		});
		
		$("#sql_container").find('input[type=checkbox],input[type=radio]').each(function() {
			$(this).prop("checked", false);
		});
		
		editor.setValue("");
		
	}
	
	//加载SQL列标签
	function loadLabelBtn(){
		$("#column_label").empty();
		$("#sys_label").empty();
		var arr = gridManager.getData();
		$.each(arr, function(index, value){
			$("#column_label").append(labelEle(value.col_code, value.col_name, value.col_name));
		})

		$.each(sc.sysLabel, function(index, value){
			$("#sys_label").append(labelEle(value.id, value.text, value.val));
		})
	}
	
	//标签节点
	function labelEle(id, text, val){
		return '<li><div class="label_btn l-button" id="'+id+'">['+text+']<div class="l-button-l"></div><div class="l-button-r"></div><span>['+val+']</span></div></li>';
	}
	
	
	function preview(){
		parent.$.ligerDialog
        .open({
            url : 'hrp/hr/sc/hrcolumnset/hrColumnSetPreviewPage.do?isCheck=false',
            data: submitData(),
            title: '预览',
            modal : true,
            width : 800,
            height : 600,
            showMax : false,
            isResize : true,
            parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
            buttons : [{
                text : '关闭',
                onclick : function(item, dialog) {
                    dialog.close();
                }
            } ]
        });
	}
	
	function saveColumnSet(){
		if (actionNodeID != null && actionNodeID != '') {
            ajaxPostData({
                url : '../hrcolumnset/saveHrColumnSet.do?isCheck=false',
                data : {
                    'tab_code' : actionNodeID,
                    'tab_view_col' : submitData()
                },
                delayCallback : true,
                success : function(data) {
                    loadGridData();
                }
            })
        } else {
            $.ligerDialog.error('请选择树节点');
        }
	}
	
	function submitData(){
        var tab1GridManager = $("#tab1Grid").ligerGetGridManager();
        var tab2GridManager = $("#tab2Grid").ligerGetGridManager();
        var tab3GridManager = $("#tab3Grid").ligerGetGridManager();
        tab1GridManager.endEdit();
        tab2GridManager.endEdit();
        tab3GridManager.endEdit();
        var gridData = tab1GridManager.getData(); //修改数据'
        var searchData = tab2GridManager.getData(); //修改数据'
        var formData = tab3GridManager.getData(); //修改数据'
        var reData = {};
        
        $(gridData).each(function(i) {
            this.is_view = $("#is_view" + this.col_code, $("#tab1Grid")).is(":checked") ? '1' : '0';
            this.status = 0;
        });
        
        $(searchData).each(function(i) {
            this.is_view = $("#is_view" + this.col_code, $("#tab2Grid")).is(":checked") ? '1' : '0';
            this.is_section = $("#is_section" + this.col_code, $("#tab2Grid")).is(":checked") ? '1' : '0';
            this.is_required =  $("#is_required" + this.col_code, $("#tab2Grid")).is(":checked") ? '1' : '0';
            this.is_default_value = $("#is_default_value" + this.col_code, $("#tab2Grid")).is(":checked") ? '1' : '0';
            this.status = 0;
        });
        
        $(formData).each(function(i) {
            this.is_view = $("#is_view" + this.col_code, $("#tab3Grid")).is(":checked") ? '1' : '0';
            this.is_gen = $("#is_gen" + this.col_code, $("#tab3Grid")).is(":checked") ? '1' : '0';
            this.is_required =  $("#is_required" + this.col_code, $("#tab3Grid")).is(":checked") ? '1' : '0';
            this.is_default_value = $("#is_default_value" + this.col_code, $("#tab3Grid")).is(":checked") ? '1' : '0';
            this.status = 0;
        });
        
        reData.gridSetData = gridData;
        reData.searchSetData = searchData;
        reData.formSetData = formData;
        return JSON.stringify(reData);
    }
	
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="数据表">
			<div class="liger-form left-buttons">
				<div class="buttons">
					<input data-text="增加" data-click="addTabStruc"/>
					<input data-text="修改" data-click="updateTabStruc"/>
					<input data-text="删除" data-click="deleteTabStruc"/>
					<input data-text="导入" data-click="importData" /> 
                    <input data-text="导出" data-click="exportData" />
                    <!-- <input data-text="导入旧版本" data-click="importOldData" width="70" /> -->
				</div>
			</div>
			<div class="search-form">
				<label>快速定位</label>
				<input type="text" id="search_input" class="text-input">
				<input id="is_stop" type="checkbox" name="is_stop" />
				<label for="is_stop">停用</label>
			</div>
			<div style="width:100%;height:calc(100% - 145px);overflow:auto;">
				<ul id="main_tree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="">
		    <div id="toptoolbar"></div>
		    <div id="accordion">
		      <div title="第一步：数据表构建">
                  <div id="maingrid"></div>
		      </div>
		      <div title="第二步：数据表SQL">
		          <div id="sql_accordion" title="数据表SQL">
                    <div position="left">
                        <ul id="subTree"></ul>
                    </div>
                    <div position="center">
                        <div id="sql_container" style="height: 100%; padding: 20px 10px;">
                          <table cellpadding="0" cellspacing="0" class="l-table-edit">
                            <tr>
                              <td align="right" class="l-table-edit-td" style="padding-left: 20px;">编码：</td>
                              <td align="left" class="l-table-edit-td">
                                  <input style="width: 200px;" name="sql_code" type="text" id="sql_code" ltype="text" value="" validate="{required:true,maxlength:20}" />
                              </td>
                              <td align="left"></td>
                              <td align="right" class="l-table-edit-td" style="padding-left: 20px;">名称：</td>
                              <td align="left" class="l-table-edit-td">
                                  <input style="width: 200px;" name="sql_name" type="text" id="sql_name" ltype="text" value="" validate="{required:true,maxlength:200}" />
                              </td>
                              <td align="left"></td>
                              <td align="left" class="l-table-edit-td">
                               <input id="is_custom" type="checkbox" checked name="is_custom"/>
                                <label>自定义</label>
                              </td>
                              <td align="left"></td>
                              <td align="left" class="l-table-edit-td">
                               <input id="is_proc" type="radio" name="is_proc_jfunc" value="1" />
                                <label>存储过程</label>
                              </td>
                              <td align="left"></td>
                              <td align="left" class="l-table-edit-td">
                               <input id="is_jfunc" type="radio" name="is_proc_jfunc" value="2" />
                                <label>JAVA方法</label>
                              </td>
                            </tr>
                          </table>
                          <table cellpadding="0" cellspacing="0" class="l-table-edit" style="width: 100%; height: calc(100% - 60px);">
                            <tr style="height: 100%;">
                              <td align="left" class="l-table-edit-td">
                                <textarea class="liger-textarea" id="code" name="sql_statement" validate="{required:true}"></textarea>
                              </td>
                            <tr />
                          </table>
                        </div>
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
                
                //SQL语句块获取选中textarea元素
                $("#sql_container").on("click", "textarea[name='sql_statement']", function(e){
                    currTextareaEle = $(this);
                });
                
                //SQL语句块标签选中插入到光标处
                $("#label_set").on("click", ".label_btn", function(e){
                    var val = $(this).find("span").text();
                    if(currTextareaEle){
                        currTextareaEle.insertContent(val);
                    }else{
                        //$('textarea[name="sql_statement"]').insertContent(val);
                        editor.replaceSelection(val);
                    }
                    
                });
                
                //radio屏蔽点击事件
                $("#sql_container").on("click", ":radio[name='is_proc_jfunc']", function(e){
                    event.preventDefault();
                });
                
                //radio支持反选
                $("#sql_container").on("mouseup", ":radio[name='is_proc_jfunc']", function(e){
                    $(this).prop("checked",!$(this).is(":checked"));
                    var is_custom = $(this).parent().parent().find(":checkbox[name='is_custom']");
                    if($(this).is(":checked")){
                        $(is_custom).prop("checked", true);
                    }
                    
                });
                
                $("#sql_container").on("click", ":checkbox[name='is_custom']", function(e){
                    var is_proc_jfunc = $(this).parent().parent().find(":radio[name='is_proc_jfunc']");
                    if(!$(this).is(":checked")){
                        $(is_proc_jfunc).prop("checked", false);
                    }
                    
                });
                
                $("#sql_container input[type='text']").ligerTextBox({width:160});
                </script>
              </div>
		      <div title="第三步：数据列设置">
                    <div id="tab1Grid"></div>
                    <script>
                    tab1Grid= $("#tab1Grid").ligerGrid({
                        columns: [
                            {display: '数据列编码', name: 'col_code', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            }, 
                            {display: '数据列名称', name: 'col_name', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                            {display: '显示名称', name: 'view_name', align: 'left', width: 120, editor: {type: 'text'},
                                render : function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.view_name = rowdata.col_name;
                                }
                            },
                            {display: "控件类型", name: "com_type_code", align: "center", width: 120, textField: "com_type_name",
                                editor : {type: 'select', data: optionData.componentType},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.com_type_code = '02';
                                        rowdata.com_type_name = '文本框';
                                    }
                                    return rowdata.com_type_name;
                                }
                            },
                            {display: "宽度", name: "width", align: "center", width: 80, editor: { type: 'text'}, 
                                render: function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.width = 120;
                                }
                            },
                            {display: '显示顺序', name: 'sort', align: 'left', width: 120, editor: {type: 'text'},
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
                            {display: "对齐方式", name: "text_align", align: "center", width: 120, textField: "text_align_text",
                                editor : {type: 'select', data: optionData.alignMode},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.text_align = 'left';
                                        rowdata.text_align_text = '左对齐';
                                    }
                                    return rowdata.text_align_text;
                                }
                            }
                            
                        ],
                        data: null, usePager : false, width : '99.7%', height : '480', checkbox : true, rownumbers : true, 
                        enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
                        rowAttrRender: function(rowdata,rowid){
                            if(rowdata.status == 1){
                                return "style='color: #FF0000'"
                            }
                        }
                    });
                    </script>
                </div>
              <div title="第四步：查询条件设置">
                    <div id="tab2Grid"></div>
                    <script>
                    tab2Grid = $("#tab2Grid").ligerGrid({
                        columns: [
                            {display: '数据列编码', name: 'col_code', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            }, 
                            {display: '数据列名称', name: 'col_name', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                            {display: '显示名称', name: 'view_name', align: 'left', width: 120, editor: {type: 'text'},
                                render : function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.view_name = rowdata.col_name;
                                }
                            },
                            {display: "控件类型", name: "com_type_code", align: "center", width: 120, textField: "com_type_name",
                                editor : {type: 'select', data: optionData.componentType},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.com_type_code = '02';
                                        rowdata.com_type_name = '文本框';
                                    }
                                    return rowdata.com_type_name;
                                }
                            },
                            {display: "宽度", name: "width", align: "center", width: 80, editor: { type: 'text'}, 
                                render: function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.width = 160;
                                }
                            },
                            {display: '显示顺序', name: 'sort', align: 'left', width: 120, editor: {type: 'text'},
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
                            {display: "对齐方式", name: "text_align", align: "center", width: 120, textField: "text_align_text",
                                editor : {type: 'select', data: optionData.alignMode},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.text_align = 'left';
                                        rowdata.text_align_text = '左对齐';
                                    }
                                    return rowdata.text_align_text;
                                }
                            },
                            {display: "合并列数", name: "field_width", align: "center", width: 120, textField: "field_width_name",
                                editor : {type: 'select', data: optionData.fieldWidth},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.field_width = '1';
                                        rowdata.field_width_name = '1列';
                                    }
                                    return rowdata.field_width_name;
                                }
                            },
                            {display: "查询方式", name: "con_sign_code", align: "center", width: 120, textField: "con_sign_name",
                                editor : {type: 'select', data: optionData.conSign},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.con_sign_code = '11';
                                        rowdata.con_sign_name = '等于';
                                    }
                                    return rowdata.con_sign_name;
                                }
                            },
                            {display: '是否区间', name: 'is_section', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_section' type='checkbox' checked='checked' id='is_section"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_section' type='checkbox' id='is_section"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: '是否必填', name: 'is_required', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_required' type='checkbox' checked='checked' id='is_required"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_required' type='checkbox' id='is_required"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: '是否默认值', name: 'is_default_value', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_default_value' checked='checked' type='checkbox' id='is_default_value"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_default_value' type='checkbox' id='is_default_value"+rowdata.col_code+"'"
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: "默认值", name: "default_value", align: "left", width: 120, editor: { type: 'text'}
                            },
                            {display: "默认值名称", name: "default_value_name", align: "left", width: 120, editor: { type: 'text'}
                            }
                            
                        ],
                        data: null, usePager : false, width : '99.7%', height : '480', checkbox : true, rownumbers : true, 
                        enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
                        rowAttrRender: function(rowdata,rowid){
                            if(rowdata.status == 1){
                                return "style='color: #FF0000'"
                            }
                        }
                    });
                    </script>
                </div>
              <div title="第五步：数据表单设置">
                <div id="tab3Grid"></div>
                    <script>
                    tab3Grid = $("#tab3Grid").ligerGrid({
                        columns: [
                            {display: '数据列编码', name: 'col_code', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            }, 
                            {display: '数据列名称', name: 'col_name', align: 'left', width: 120,
                                render : function(rowdata, rowindex, value) {
                                    return value;
                                }
                            },
                            {display: '显示名称', name: 'view_name', align: 'left', width: 120, editor: {type: 'text'},
                                render : function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.view_name = rowdata.col_name;
                                }
                            },
                            {display: "控件类型", name: "com_type_code", align: "center", width: 120, textField: "com_type_name",
                                editor : {type: 'select', data: optionData.componentType},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.com_type_code = '02';
                                        rowdata.com_type_name = '文本框';
                                    }
                                    return rowdata.com_type_name;
                                }
                            },
                            {display: "宽度", name: "width", align: "center", width: 80, editor: { type: 'text'}, 
                                render: function(rowdata, rowindex, value) {
                                    return value ? value : rowdata.width = 160;
                                }
                            },
                            {display: '显示顺序', name: 'sort', align: 'left', width: 120, editor: {type: 'text'},
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
                            {display: "对齐方式", name: "text_align", align: "center", width: 120, textField: "text_align_text",
                                editor : {type: 'select', data: optionData.alignMode},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.text_align = 'left';
                                        rowdata.text_align_text = '左对齐';
                                    }
                                    return rowdata.text_align_text;
                                }
                            },
                            {display: "合并列数", name: "field_width", align: "center", width: 120, textField: "field_width_name",
                                editor : {type: 'select', data: optionData.fieldWidth},
                                render: function(rowdata, rowindex, value) {
                                    if(!value){
                                        rowdata.field_width = '1';
                                        rowdata.field_width_name = '1列';
                                    }
                                    return rowdata.field_width_name;
                                }
                            },
                            {display: '是否必填', name: 'is_required', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_required' type='checkbox' checked='checked' id='is_required"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_required' type='checkbox' id='is_required"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: '是否自动生成', name: 'is_gen', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_gen' type='checkbox' checked='checked' id='is_gen"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_gen' type='checkbox' id='is_gen"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: '是否默认值', name: 'is_default_value', align: 'center', width: 80,
                                render : function(rowdata, rowindex, value) {
                                    if(value == 1){
                                        return "<input name='is_default_value' type='checkbox' checked='checked' id='is_default_value"+rowdata.col_code+"'"
                                            +" style='margin-top:5px;' />";
                                    }
                                    return "<input name='is_default_value' type='checkbox' id='is_default_value"+rowdata.col_code+"'" 
                                        +" style='margin-top:5px;' />";
                                }
                            },
                            {display: "默认值", name: "default_value", align: "left", width: 120, editor: { type: 'text'}
                            },
                            {display: "默认值名称", name: "default_value_name", align: "left", width: 120, editor: { type: 'text'}
                            }
                            
                        ],
                        data: null, usePager : false, width : '99.7%', height : '480', checkbox : true, rownumbers : true, 
                        enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
                        rowAttrRender: function(rowdata,rowid){
                            if(rowdata.status == 1){
                                return "style='color: #FF0000'"
                            }
                        }
                    });
                    </script>
                </div>
		    </div>
			<!-- <div id="navtab" style="height: 100%; overflow: hidden; border: 1px solid #A3C0E8;">
				<div tabid="item1" title="数据表构建" lselected="true">
					
				</div>
				
			</div> -->
		</div>
	</div>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/editor.js"></script>
<script type="text/javascript">
	$.ajax({
	    type : "post",
	    url :  'queryHrTableWords.do?isCheck=false',
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
