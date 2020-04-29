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
<%-- <jsp:include page="${path}/resource.jsp"> --%>
<%--         <jsp:param value="hr,dialog,grid,select,tree,pageOffice" name="plugins" /> --%>
<%-- </jsp:include> --%>
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
</style>
<script type="text/javascript">
	var tree, treeRMenu, actionNodeID, subActionNodeID, grid, toptoolbar, currTextareaEle, editor;
	var gridManager = null;
	var treeSearchKey = '', treeSearchCheck = 0;
	$(function() {
  	
		//布局
		$("#layout").ligerLayout({leftWidth: 230, minLeftWidth: 230, allowLeftResize: false});
		
		//tab页签
// 		$("#navtab").ligerTab({onBeforeSelectTabItem: function(){gridManager.reload()}});
		
		toptoolbar =  $("#toptoolbar").ligerToolBar({items : [ 
			{text: '查询', id: 'search', click: search, icon: 'search'}, 
			{line: true}, 
			{text: '添加行', id: 'add', click: addRow, icon: 'add', disable: true},
			{line: true}, 
			{text: '删除行', id: 'delete', click: deleteRow, icon: 'delete', disable: true}, 
			{line: true}, 
			{text: '保存', id: 'save', click: save, icon: 'save', disable: true},
			{line: true},
			{text: '设置外部引用', id: 'setCite', click: setCite, icon: 'plus',},
			{line: true},
			{text: '打印', id: 'print', click: print, icon: 'print', disable: true},
			{line: true},
			/* {text: '导入', id: 'import', click: importData, icon: 'up'}, 
			{line: true}, 
			{text: '导出', id: 'export', click: exportData, icon: 'down'}, 
			{line: true},  
			{text: '导入旧版本数据', id: 'importOld', click: importOldData, icon: 'up'}*/
		]});
		
		
		loadTree();
		loadGrid();
		loadDict();
		
		//tree快速定位
		$("#search_input").keyup(function(e) {
			treeSearchKey = $("#search_input").val();
			if(treeSearchCheck)
			{
				treeSearchCheck = 1;
			}else{
				treeSearchCheck = 0;
			}
			tree.options.url = 'queryHrFiledTabStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck;
			tree.reload();
		});
		
		//tree快速定位停用复选框
		$("#is_stop").change(function(e) {
			treeSearchCheck = $("#is_stop").is(':checked');
			if(treeSearchCheck)
			{
				treeSearchCheck = 1;
			}else{
				treeSearchCheck = 0;
			}
			tree.options.url = 'queryHrFiledTabStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck;
			tree.reload();
		});

	});
	
	
	function print() {
		if (gridManager.data == null) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		//alert(tree.getSelectedNodes()[0].id);
		var heads = {
		/* "isAuto":true,//系统默认，页眉显示页码
		"rows": [
		 {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
		]  */};
		var printPara = {
			title : tree.getSelected().data.name + " 代码表打印",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.hr.service.sc.HrFiledTabStrucService",
			method_name : "queryHrFiledDataByPrint",
			bean_name : "hrFiledTabStrucService",
			heads : JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			foots : '',//表尾需要打印的查询条件,可以为空 
		};
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}
	
	// 设置外部引用
	function setCite() {
// 		var selected = tree.getSelectedNodes()[0];
// 		var sId = selected.id;
// 		var sText = selected.name;

		parent.$.ligerDialog.open({
					title : '设置外部引用',
					url : 'hrp/hr/sc/hrfiledtabstruc/hrFiledViewAddPage.do?isCheck=false&field_tab_code='
							+ actionNodeID,
					height: 600,
					width: 1300,		
					showMax : true,
					modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
					frameName : window.name,
					buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveData(); },cls:'l-dialog-btn-highlight' }, { text: '关闭', onclick: function (item, dialog) { dialog.close(); grid.reload();} } ]
				});
	}
	

	//查询
	function search() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'field_tab_code',value : actionNodeID});
		grid.options.parms.push({name : 'field_col_code',value : $("#field_col_code").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//加载左侧tree
	function loadTree() {
		treeRMenu = $.ligerMenu({ top: 100, left: 100, width: 120, 
// 			items: [{ text: '启用', click: m_open },{ text: '停用', click: m_close }]
		});
		
		tree = $("#main_tree").ligerTree({
			url: 'queryHrFiledTabStrucTree.do?isCheck=false&key='+treeSearchKey+'&is_stop='+treeSearchCheck, 
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
        			search();
        			$('#field_col_code').val('');
        			$('#kind_code').val(node.data.name);
        			if(node.data.is_cite){
        				toptoolbar.setDisabled("add");
        				toptoolbar.setDisabled("delete");
        				toptoolbar.setDisabled("save");
           				toptoolbar.setEnabled("print");
           				toptoolbar.setEnabled("setCite");
           				
        			}else{
        				toptoolbar.setEnabled("add");
           				toptoolbar.setEnabled("delete");
           				toptoolbar.setEnabled("save");
           				toptoolbar.setEnabled("print");
           				toptoolbar.setDisabled("setCite");
        			}
       				
           		
    			}else{
    				toptoolbar.setDisabled("add");
    				toptoolbar.setDisabled("delete");
    				toptoolbar.setDisabled("save");
    				toptoolbar.setDisabled("print");
    				toptoolbar.setDisabled("setCite");
    			}
    		}
     	});
	}
	

	//加载表格
	function loadGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '代码项编码', name : 'field_col_code',align : 'left', width : 100, editor: { type: 'text'},
				}, 
				{display: '代码项名称', name: 'field_col_name', align: 'left', width: 120, editor: { type: 'text'},
					render : function(rowdata, rowindex, value) {
						return value;
					}
                },
                {display: "上级代码项", name: "super_col_name", align: "left", width: 120, editor: { type: 'text'}, 
					render : function(rowdata, rowindex, value) {
						if(value=="" || value=="TOP" || value == null){
							return "TOP";
						}else{
							return value ? value.toUpperCase() : value;
						}
						
					}
				},
                {display: "是否末级", name: "is_last", align: "right", dataType: "integer", width: 120,  textField: "is_last_text", 
               		editor : {type: 'select', data: optionData.whether},
					render: function(rowdata, rowindex, value) {
						if(rowdata.is_last == 0){
							return "否";
						}else{
							return "是"
						}
					}
                },
                {display: "是否内置", name: "is_innr", align: "right", width: 120,  textField: "is_innr_text",
               		editor : {type: 'select', data: optionData.whether},
					render: function(rowdata, rowindex, value) {
						if(rowdata.is_innr == 0){
							return "否";
						}else{
							return "是"
						}
					}
               	},
                {display: "是否停用", name: "is_stop", align: "right", width: 120, textField: "is_stop_text",
               		editor : {type: 'select', data: optionData.whether},
					render: function(rowdata, rowindex, value) {
						if(rowdata.is_stop == 0){
							return "否";
						}else{
							return "是"
						}
					}
				},
				{display: "备注", name: "note", align: "left", width: 120,editor: { type: 'text'}
					
				}
            ],
			dataAction : 'server', dataType : 'server', usePager : true,  url : 'queryHrFiledData.do?isCheck=false',
			width : '100%', height : '100%', checkbox : true, rownumbers : true,
			delayLoad : true, enabledEdit: true, selectRowButtonOnly : true, isAddRow: false,
			
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//添加字段行
	function addRow(){
		gridManager.addRow({super_col_name:'TOP',is_innr:0,is_stop:0});
	}
	
	//删除字段
	function deleteRow(){
		var selectRows = gridManager.getCheckedRows();
        if (selectRows.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
       
          	var ParamVo = [], FdRows = []; // 后台要删数据变量与不走后台要删数据变量
			$(selectRows).each(
					function() {
						if (this.field_col_code) {
							ParamVo
									.push({
										'field_col_code' : this.field_col_code,
										'field_tab_code' : actionNodeID
									});
						} else {
							FdRows.push(this);
						}

			});
			if (FdRows.length > 0) { // 直接在前台所删的数据
				gridManager.deleteRange(FdRows);
			}

			if (ParamVo.length > 0) {
				$.ligerDialog.confirm(
					'确定删除?',
					function() {
						ajaxPostData({
							url : "deleteHrFiledData.do?isCheck=false",
							data : {
								ParamVo : JSON.stringify(ParamVo)
							},
							success : function(res) {
								if (res.state == "true") {
									search();
								}
							}
						})
					});
			}
            
        }	      
	}
	
	//保存数据表字段信息
	function save() {
		gridManager.endEdit();
		if (actionNodeID != null && actionNodeID != '') {
			var allDataVo = gridManager.data;
			var allData = [];
		    //console.log(allData)
			var flag = true; //用来判断
			var flag02 = true; //用来判断
// 			var reg = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
			var str = "";
			$(allDataVo.Rows).each(function(i) {
				if (this.field_col_code == "" || this.field_col_code == null) {
					flag = false;
					str += "第" + (i + 1) + "行、";
				}
				if (this.field_col_name == "" || this.field_col_name == null) {
					flag02 = false;
					str += "第" + (i + 1) + "行、";
				}
				if(this.__status != 'delete'){
					allData.push({
						field_tab_code: actionNodeID,
						field_col_code: this.field_col_code,
						field_col_name: this.field_col_name,
						super_col_code: this.super_col_name,
						is_last: this.is_last,
						is_innr: this.is_innr,
						is_stop: this.is_stop,
						note: this.note,
						status:this.__status
	                });
				}
			});
			if (!flag) {
				$.ligerDialog.error(str.substring(0, str.length - 1)
						+ "代码列编码不能为空。");
				return;
			}
			if (!flag02) {
				$.ligerDialog.error(str.substring(0, str.length - 1)
						+ "代码项名称不能为空。");
			 return;
			}
			ajaxPostData({
				url : 'saveHrFiledData.do?isCheck=false',
				data : {
					'field_tab_code' : actionNodeID,
					'allData' : JSON.stringify(allData)
				},
				delayCallback : true,
				success : function(data) {
					search();
				}
			})
		} else {
			$.ligerDialog.error('请选择树节点');
		}
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
		var url = '';
		if(tree.getSelected()){
			id = tree.getSelected().data.id;
			name = tree.getSelected().data.name;
            /* $.each(tree.getChecked(), function(index, item){
                if(item.data.pId){
                    ids += item.data.id + ','
                }
            });
            location.href = "export.do?isCheck=false&ids="+ids; */
            
          //选择的树节点是非末级节点
            if (tree.getSelected().data.pId == null){
            	url = "export.do?isCheck=false&type_filed_code="+id+"&file_name="+name;
            }else{
            	url = "export.do?isCheck=false&field_tab_code="+id+"&file_name="+name;
            }
        }else{
        	url  =  "export.do?isCheck=false";
        }
		//选择是树节点是末级节点
		location.href = url;
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
	
	//添加数据表
	function addTree() {
		parent.$.ligerDialog
				.open({
					url : 'hrp/hr/sc/hrfiledtabstruc/hrFiledTabStrucAddPage.do?isCheck=false',
					height : 400,
					width : 700,
					title : '代码表添加页',
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
	function updateTree() {
		//获取树当前选择项
		if (tree.getSelected()) {
			if (tree.getSelected().data.pId) {
				var parm = 'field_tab_code=' + tree.getSelected().data.id;
				parent.$.ligerDialog
						.open({
							url : 'hrp/hr/sc/hrfiledtabstruc/hrFiledTabStrucUpdatePage.do?isCheck=false&'
									+ parm,
							title : '代码表构建修改页',
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
									tree.reload();
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
			if (tree.getSelected().data.is_innr == 1) {
				$.ligerDialog.error('内置数据不允许删除！');
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(e) {
				if(e){
					ajaxPostData({
						url : "hrFiledTabStrucDelete.do?isCheck=false",
						data : {
							'field_tab_code'  : tree.getSelected().data.id
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
	
	   function loadDict(){
        
	       	$("#field_col_code").ligerTextBox({width:250});
	     	$("#kind_code").ligerTextBox({width:250,disabled:true});
        }
	
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="数据表">
			<div class="liger-form left-buttons">
				<div class="buttons">
					<input data-text="增加" data-click="addTree"/>
					<input data-text="修改" data-click="updateTree"/>
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
				<div id="navtab" style="height: 100%; overflow: hidden; border: 1px solid #A3C0E8;">
					<div tabid="item1"  lselected="true">
		           		 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					        <tr>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码项编码/名称：</td>
					            <td align="left" class="l-table-edit-td"><input name="field_col_code" type="text" id="field_col_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
					            <td align="left"></td>
					            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码表名称：</td>
					            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
					            <td align="left"></td>
					       </tr>
					      </table>
						<div id="toptoolbar"></div>
						<div id="maingrid"></div>
					</div>
				</div>
		</div>
	</div>
</body>
</html>
