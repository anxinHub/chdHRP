<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hrTabStrucMain</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select, grid, tab, tree, hr, dialog,checkbox" name="plugins" />
</jsp:include>
<style>
html, body {
	height: 100%;
	min-width: 900px
}

.grid_header .float_left {
	float: left;
	padding: 5px 10px;
}

.grid_header .float_right {
	float: right;
	padding: 5px 10px;
}

.main_title {
	background-color: #ededed;
	/* background:linear-gradient(to bottom,#EDEDED 0%, #98c4f7 50%,#ededed 100%); */
	/* height: 40px; */
	/* background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff); */
	padding: 2px 10px;
	position: fixed;
	z-index: 99;
	width: 100%;
}

.container {
	padding-top: 30px;
}
</style>
<script>
	var archives_select, tree, grid, store_type_code, tab_code;
	$(function() {
		loadTree();
		loadGrid();
		
		archives_select = $("#archives_select").etSelect({
            url: '../../queryHrStoreType.do?isCheck=false',
            showClear: false,
            defaultValue:'01' ,
            //url: 'http://192.168.1.127:9091/select',
           /*  onInit: function (value) {
            	loadTree(value);
            	//search();
            }, */
            onChange: function (value) {
            	loadTree(value);
            }
        });

		$("#search_input").keyup(function(e) {
			var _this = this;
			searchTree({
				tree : tree,
				value : this.value,
				callback : function(node) {
					$(_this).focus(); //回去焦点
				}
			});
		});
	
	})

	function loadGrid() {
		var gridObj = {
			editable : true,
			checkbox : true,
			height : '100%',
			inWindowHeight : true,
			freezeCols : 2,
			usePager: false,
			addRowByKey : true
		//  快捷键控制添加行
		};
		gridObj.columns = [ {
			display : "数据列编码",
			align : "left",
			width : 120,
			name : "col_code",
			editable : false
		}, {
			display : '数据列名称',
			align : 'left',
			name : 'col_name',
			width : 120,
			editable : false
		}, {
			display : '列显示名称',
			align : 'left',
			name : 'col_name_show',
			width : 120
		}, {
			display : "组件类型",
			align : "left",
			width : 120,
			name : "com_type_name",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'com_type_code',
				url : '../../queryHrComType.do?isCheck=false'
			},
			 editable : function(col){
			    //若内置数据 不予许修改
             	if(col.rowData){
             		if(col.rowData.is_innr==1){
             			return false;
             		}else{
             			return true;
             		}
             	}else{
             		return true;
             	}
             }
		}, {
			display : "显示顺序",
			align : "right",
			width : 60,
			dataType : "integer",
			name : "seq_no"
		}, {
			display : "数据列宽",
			align : "right",
			width : 120,
			dataType : "integer",
			name : "col_width"
		}, {
			display : "是否显示(编辑)",
			align : "left",
			width : 120,
			name : "is_view_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_view',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ]
			}
		}, {
			display : "是否显示(列表)",
			align : "left",
			width : 120,
			name : "is_view_tab_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_view_tab',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ]
			},
		}, {
			display : "对齐方式",
			align : "left",
			width : 120,
			name : "text_align_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'text_align',
				source : [ {
					id : 'left',
					label : "左对齐"
				}, {
					id : 'right',
					label : "右对齐"
				}, {
					id : 'center',
					label : "居中对齐"
				}  ],
			}
		}, {
			display : "合并列数",
			align : "right",
			width : 120,
			name : "field_width_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'field_width',
				source : [ {
					id : '1',
					label : "1列"
				}, {
					id : '2',
					label : "2列"
				}, {
					id : '3',
					label : "3列"
				} , {
					id : '4',
					label : "4列"
				}  ],
			}
		}, {
			display : "是否必填",
			align : "left",
			width : 120,
			name : "is_verify_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_verify',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			},
			 editable : function(col){
				    //若内置数据 不予许修改
	             	if(col.rowData){
	             		if(col.rowData.is_innr==1){
	             			return false;
	             		}else{
	             			return true;
	             		}
	             	}else{
	             		return true;
	             	}
	             }
		}, /* {
			display : "是否主键列",
			align : "left",
			width : 120,
			name : "is_pk_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_pk',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			}
		}, */ {
			display : "是否自动生成",
			align : "left",
			width : 120,
			name : "is_auto_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_auto',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			},
			 editable : function(col){
				    //若内置数据 不予许修改
	             	if(col.rowData){
	             		if(col.rowData.is_innr==1){
	             			return false;
	             		}else{
	             			return true;
	             		}
	             	}else{
	             		return true;
	             	}
	             }
		}, {
			display : "是否只读",
			align : "left",
			width : 120,
			name : "is_read_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_read',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			}
		}, {
			display : "是否默认值",
			align : "left",
			width : 120,
			name : "is_default_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_default',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			}
		}, {
			display : "默认值",
			align : "left",
			width : 120,
			name : "default_value",
		}, {
			display : "默认值名称",
			align : "left",
			width : 120,
			name : "default_text",
		}, {
			display : "是否存在变更",
			align : "left",
			width : 120,
			name : "is_change_text",
			editor : {
				type : 'select', // 下拉框编辑框
				keyField : 'is_change',
				source : [ {
					id : '1',
					label : "是"
				}, {
					id : '0',
					label : "否"
				} ],
			}
		}, {
			display : "变更列",
			align : "left",
			width : 150,
			name : "change_col_code"
		} ];
	/* 	gridObj.dataModel = { // 数据加载的有关属性
			url : 'queryHrStoreColSet.do',
			recIndx : 'tab_code'
		}; */
		gridObj.toolbar = {
			items : [ {
				type : "button",
				label : '查询',
				icon : 'search',
				id : 'search',
				listeners : [ {
					click : search
				} ]
			}, {
				type : "button",
				label : '保存',
				disabled : true,
				icon : 'save',
				id : 'save',
				listeners : [ {
					click : save
				} ]
			}, {
				type : "button",
				label : '同步数据列',
				disabled : true,
				icon : 'add',
				id : 'save',
				listeners : [ {
					click : batch_add
				} ]
			}]
		};
		grid = $("#maingrid").etGrid(gridObj);
	}

	function loadTree(value) {
		value= value != undefined ? value : '01';
		var tree_url = '../../queryHrStoreTabStruc.do?isCheck=false&store_type_code=' + value;
		tree = $("#mainTree").etTree({
			async : {
				enable : true,
				url : tree_url
			},
			callback : {
				onClick : function(e, id, node) {
					search();
				},
				  onAsyncSuccess:function(e, id, node){
	                	 var firstChildrenNode = tree.getNodes()[0];
	                	 tree.selectNode(firstChildrenNode); 
					 search();
	                } 
			},
			addSuffix : function() {
				var treeNodes = tree.transformToArray(tree.getNodes());
				return {
					nodes : treeNodes,
					rules : [ {
						rule : {
							is_innr : 1
						},
						text : '内置',
						color : 'red'
					} ]
				}
			}

		})
	}

	function search() {
		var selectedNode = tree.getSelectedNodes()[0];
		tab_code = selectedNode ? selectedNode.id : '';
		store_type_code = archives_select.getValue();
		
		grid.setEnabledTB('save');
		var param = [ {
			name : 'tab_code',
			value : tab_code
		},{
			name : 'store_type_code',
			value : store_type_code
		},{
			name : 'col_name',
			value : $("#col_name").val()
		}];
		grid.loadData(param,'queryHrStoreColSet.do');
	}

	//保存子集
	function save() {
		if (tab_code && store_type_code) {

			var data = grid.getAllData(); //添加数据
			ajaxPostData({
				url : 'saveHrStoreColSet.do',
				data : {
					'store_type_code' : store_type_code,
					'tab_code' : tab_code,
					'data' : JSON.stringify(data),
				},
				delayCallback : true,
				success : function(data) {
					search();
				}
			})
		} else {
			$.etDialog.error('请选择树节点');
		}
	}

	function print() {

	}

	function exportData() {

	}

	function importData() {

	}
	
	function batch_add(){
		parent.$.etDialog.open({
            url: 'hrp/hr/sysstruc/hrstorecolset/hrStoreColSetBatchAddPage.do?isCheck=false',
            width: 600,
            height: 600,
            title: '批量同步',
            frameName: window.name,
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = parent.window[el.find('iframe')[0].name]
                iframeWindow.save()
            }
        });
	}
	
</script>
</head>

<body>
	<div class="main_title">
		<label for="">档案库：</label> <select name="" id="archives_select"
			style="width: 164px;"></select>
	</div>
	<div class="container">
		<div class="left border-right">
			<div class="search-form">
				<label>快速定位</label> <input type="text" id="search_input"
					class="text-input">
			</div>
			<div id="mainTree"></div>
			<div class="container-bar"></div>
		</div>
		<div class="center">
			<table class="table-layout">
				<tr>
					<td class="label">数据列名称：</td>
					<td class="ipt"><input id="col_name" type="text" /></td>
				</tr>
			</table>

			<div id="maingrid"></div>
		</div>
	</div>
</body>

</html>