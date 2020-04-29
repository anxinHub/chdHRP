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
</style>
<script type="text/javascript">
	var tree, tab1Grid, tab2Grid, tab3Grid, toptoolbar, actionNodeID;
	
	$(function() {
		//布局
		$("#layout").ligerLayout({leftWidth : 250, minLeftWidth : 230, allowLeftResize : false});
		
		//tab页签
		$("#navtab").ligerTab({onAfterSelectTabItem: function(){/* loadGridData() */}});
		
		toptoolbar =  $("#toptoolbar").ligerToolBar({items : [ 
			{text : '预览', id : 'preview', click : preview, icon : 'search', disable: true}, 
			{line: true}, 
			{text : '保存', id : 'save', click : save, icon : 'save', disable: true}
		]});

		loadTree();
		
		//解决在切换页签时表头加载不全问题
		$('.l-grid').width($('#navtab').width());

		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = '../hrtablestruc/queryHrTableStrucTree.do?isCheck=false&key='+key;
			tree.reload();
		});
		
	});

	function loadTree() {
		tree = $("#mainTree").ligerTree(
				{url: '../hrtablestruc/queryHrTableStrucTree.do?isCheck=false', 
           		 ajaxType: 'post' ,
           		 idFieldName :'id',
           		 textFieldName: 'name',
                 parentIDFieldName :'pId', 
                 nodeWidth:'100%',
           		 checkbox: false,
           		 isExpand: false, 
           		 onClick: function(node, target){
           			actionNodeID = node.data.id;
           			loadGridData();
           			
           			if(this.getSelected()){
           				toptoolbar.setEnabled("save");
           				toptoolbar.setEnabled("preview");
           				
           			}else{
           				toptoolbar.setDisabled("save");
           				toptoolbar.setEnabled("preview");
           			}
           		 }
            });
	}
	
	function loadGridData(){
		ajaxPostData({
			url : 'queryHrColumnSet.do?isCheck=false',
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
	
	//已废弃
	function loadGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display: '基本项', columns:[
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
					}]					
				}, 
				{display: '列表页设置', columns:[
					{display: '列表显示', name: 'list_view', align: 'center', width: 80,
                       	render : function(rowdata, rowindex, value) {
							if(value == 0){
								return "<input name='list_view' type='checkbox' id='list_view_"+rowdata.col_code+"'"
									+" style='margin-top:5px;' />";
							}
							return "<input name='list_view' type='checkbox' checked='checked' id='list_view_"+rowdata.col_code+"'" 
								+" style='margin-top:5px;' />";
						}
					},
					{display: "列表宽度", name: "list_width", align: "center", width: 80, editor: { type: 'text'}, 
                     	render: function(rowdata, rowindex, value) {
							return value ? value : rowdata.list_width = 120;
						}
                    }, 
					{display: '搜索显示', name: 'search_view', align: 'center', width: 80,
                     	render : function(rowdata, rowindex, value) {
						if(value == 0){
							return "<input name='search_view' type='checkbox' id='search_view_"+rowdata.col_code+"'"
								+" style='margin-top:5px;' />";
						}
						return "<input name='search_view' type='checkbox' checked='checked' id='search_view_"+rowdata.col_code+"'"
							+" style='margin-top:5px;' />";
						}
					}]
				},
				{display: '表单页设置', columns:[
					{display: '表单显示', name: 'form_view', align: 'center', width: 80,
						render : function(rowdata, rowindex, value) {
						if(value == 0){
							return "<input name='form_view' type='checkbox' id='form_view_"+rowdata.col_code+"'"
								+" style='margin-top:5px;' />";
						}
						return "<input name='form_view' type='checkbox' checked='checked' id='form_view_"+rowdata.col_code+"'"
							+" style='margin-top:5px;' />";
						}
					},
					{display: "宽度", name: "form_width", align: "center", width: 80, editor: { type: 'text'}, 
                       	render: function(rowdata, rowindex, value) {
							return value ? value : rowdata.form_width = 160;
						}
					},
					{display: "高度", name: "form_height", align: "center", width: 80, editor: { type: 'text'}, 
						render: function(rowdata, rowindex, value) {
							return value ? value : '';
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
					{display: '是否默认值', name: 'is_default_value', align: 'center', width: 80,
						render : function(rowdata, rowindex, value) {
							if(value == 1){
								return "<input name='is_default_value' checked='checked' type='checkbox' id='is_default_value_"+rowdata.col_code+"'"
									+" style='margin-top:5px;' />";
							}
							return "<input name='is_default_value' type='checkbox' id='is_default_value_"+rowdata.col_code+"'"
								+" style='margin-top:5px;' />";
						}
					},
					{display: "默认值", name: "default_value", align: "left", width: 120, editor: { type: 'text'}
                    }, 
					{display: '是否新行', name: 'newline', align: 'center', width: 80,
                       	render : function(rowdata, rowindex, value) {
							if(value == 1){
								return "<input name='newline' checked='checked' type='checkbox' id='newline_"+rowdata.col_code+"'"
									+" style='margin-top:5px;' />";
							}
							return "<input name='newline' type='checkbox' id='newline_"+rowdata.col_code+"'"
								+" style='margin-top:5px;' />";
						}
					}]						
				}],
				dataAction : 'server', dataType : 'server', usePager : true, url : 'queryHrColumnSet.do?isCheck=false',
				width : '100%', height : '100%', checkbox : true, rownumbers : true, delayLoad : true, enabledEdit: true,
				selectRowButtonOnly : true, isAddRow: false, onAfterShowData: function(){},
				onBeforeEdit: function (e) {
					if(e.column.name == 'form_height' && e.record.com_type_code != '06'){
						$.ligerDialog.warn('高度只对控件类型为“多行文本框”时有效');
					}
				},
				onAfterEdit: function (e) {
					if(e.column.name == 'com_type_code' && e.value == '01'){
						$("#is_default_value_" + e.record.col_code).attr("checked",true);
					}
				}
			});

		gridManager = $("#maingrid").ligerGetGridManager();
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

	//保存数据表字段信息
	function save() {
		if (actionNodeID != null && actionNodeID != '') {
			ajaxPostData({
				url : 'saveHrColumnSet.do?isCheck=false',
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
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 62px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="">
			<div id="toptoolbar"></div>
			<div id="navtab" style="height: 100%; overflow: hidden; border: 1px solid #A3C0E8;">
				<div tabid="item1" title="数据列设置" lselected="true">
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
			            data: null, usePager : false, width : '100%', height : '100%', checkbox : true, rownumbers : true, 
			            enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
			            rowAttrRender: function(rowdata,rowid){
			                if(rowdata.status == 1){
			                    return "style='color: #FF0000'"
			                }
			            }
			        });
					</script>
				</div>
				<div tabid="item2" title="查询条件设置">
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
			            data: null, usePager : false, width : '100%', height : '100%', checkbox : true, rownumbers : true, 
			            enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
			            rowAttrRender: function(rowdata,rowid){
			                if(rowdata.status == 1){
			                    return "style='color: #FF0000'"
			                }
			            }
			        });
                    </script>
				</div>
				<div tabid="item3" title="数据表单设置">
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
			            data: null, usePager : false, width : '100%', height : '100%', checkbox : true, rownumbers : true, 
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
		</div>
	</div>
</body>
</html>
