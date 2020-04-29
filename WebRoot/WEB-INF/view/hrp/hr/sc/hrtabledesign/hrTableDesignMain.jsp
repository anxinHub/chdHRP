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

<%-- <link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/codemirror.css" />
<link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/css/dracula.css" />
<link rel="stylesheet" href="<%=path%>/lib/hrp/hr/editor/css/show-hint.css" />

<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/codemirror.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sublime.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql-hint.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/show-hint.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/formatting.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/sql-formatter.min.js"></script> --%>

<style type="text/css">
.l-layout-content{
	height: 100%;
}
/* 搜索框 */
.search-form {
	padding: 5px;
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
.label_btn{    
	float: none !important;
    margin: 4px 10px;
    padding: 0 4px;
    width: auto !important;
}
.left-buttons{
    padding-left: 8px;
}
/* .left-buttons ul{
	display: flex;
    flex-direction: row;
    justify-content: center;
} */

.CodeMirror{
	border: 1px solid #a3c0e8;
	height: 100%;
	overflow: auto;
}

#navtab, 
#navtab .l-tab-content, 
#navtab .l-tab-content .l-tab-content-item{
	height: 100% !important;
}
</style>
<script type="text/javascript">
	var tree, actionNodeID, dataTableGrid, conditionGrid, groupGrid, sortGrid, toptoolbar, editor;
	$(function() {
  	
		//布局
		$("#layout").ligerLayout({leftWidth: 230, minLeftWidth: 230, allowLeftResize: false});
		$("#layout_item2").ligerLayout({allowLeftResize: true});
		
		var height = $("#layout_item2 .l-layout-center").height();
		$("#label_set").ligerAccordion({height: height-55, changeHeightOnResize: true});
		
		toptoolbar = $("#toptoolbar").ligerToolBar(
			{ items: [
				{text: '保存', id:"save", click: function (item){save()}, icon:'save', disable: true}, { line:true },
				{text: '生成SQL', click: function (item){genSql()}}, { line:true },
				{text: '测试SQL', click: function (item){testSql()}}, { line:true }
			]}
		);
		
		loadTree();

		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = 'queryHrTableDesignTree.do?isCheck=false&key='+key;
			tree.reload();
		});
		
		$("#gen_sql_btn").click(function () {
			 dataTableGrid.endEdit();
			 conditionGrid.endEdit();
			 groupGrid.endEdit();
			 sortGrid.endEdit();
			 var dataTable = dataTableGrid.getData();
			 var condition = conditionGrid.getData();
			 var group = groupGrid.getData();
			 var sort = sortGrid.getData();
			 if(dataTable && dataTable.length >0){
			 	ajaxPostData({
					url: 'generateSqlStatement.do?isCheck=false',
					data: {
						'dataTable': JSON.stringify(dataTable),
						'condition': JSON.stringify(condition),
						'group': JSON.stringify(group),
						'sort': JSON.stringify(sort),
					},
					delayCallback: true,
					success: function(data) {
						$("textarea[name='sql_statement']").val(data.sql_statement)
						var str = sqlFormatter.format(data.sql_statement, {language: 'sql'});
				        editor.setValue(str);
					}
				})
		 	}else{
		 		$.ligerDialog.error('数据表未维护');
		 	}	
	    });
		 
		$("#test_sql_btn").click(function () {
	            
		});
		
		//SQL语句块标签选中插入到光标处
		$("#label_set").on("click", ".label_btn", function(e){
			var val = $(this).find("span").text();
			//$('textarea[name="sql_statement"]').insertContent(val);
			editor.replaceSelection(val);
		});
		
	});
	
	
	function genSql() {
	 	ajaxPostData({
			url: 'generateSqlStatement.do?isCheck=false',
			data: {
				'design_code': actionNodeID
			},
			delayCallback: true,
			success: function(data) {
				//$("textarea[name='sql_statement']").val(data.sql_statement)
				var str = sqlFormatter.format(data.sql_statement, {language: 'sql'});
		        editor.setValue(str);
			}
		})
    };
	 
	function testSql() {
           
	};

	//查询
	function search() {
		ajaxPostData({
			url: 'queryDesignQueryCol.do?isCheck=false',
			data: {
				'design_code': actionNodeID
			},
			delayCallback: true,
			success: function(data) {
				dataTableGrid.reload();
				conditionGrid.reload();
				groupGrid.reload();
				sortGrid.reload();
				//$("textarea[name='sql_statement']").val("");
				//editor.setValue("");
				dataTableGrid.appendRange(data.tableData);
				if(data.conditionData){
					conditionGrid.appendRange(data.conditionData);
				}
				if(data.groupData){
					groupGrid.appendRange(data.groupData);
				}
				if(data.sortData){
					sortGrid.appendRange(data.sortData);
				}
				
				//$("textarea[name='sql_statement']").val(data.sql_statement);

				function format() {
			        var str = sqlFormatter.format(data.sql_statement, {language: 'sql'});
			        editor.setValue(str);
			    }
			    format();
			    
			}
		})
	}
	
	function loadTree() {
		
		tree = $("#mainTree").ligerTree(
				{url: 'queryHrTableDesignTree.do?isCheck=false', 
            	 ajaxType: 'post' ,
            	 idFieldName:'id',
            	 textFieldName: 'text',
                 parentIDFieldName:'pid', 
                 nodeWidth:'100%',
            	 checkbox: false,
            	 isExpand: false, 
            	 onClick: function(node, target){
            		if(!node.data.pid || node.data.pid == '0'){
            			return;
            		}
            		actionNodeID = node.data.id;
            		$("#designFrame")[0].contentWindow.loadGridData(actionNodeID);
            		//$("#designPageFrame")[0].contentWindow.loadGridData(actionNodeID);
            	 	//search();
            	 	/* if(this.getSelected()){
            	 		toptoolbar.setEnabled("save");
           			}else{
           				toptoolbar.setDisabled("save");
           			} */
            	}
            });
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
	
	//保存数据表字段信息
	function save() {
		if (actionNodeID != null && actionNodeID != '') {
			ajaxPostData({
				url: 'saveDesignQuerySql.do?isCheck=false',
				data: {
					'design_code': actionNodeID,
					'design_query_sql': editor.getValue(),
					
				},
				delayCallback: true,
				success: function(data) {
					//search();
				}
			})
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}
	
	//添加查询设计器
	function addTabDesign() {
		parent.$.ligerDialog
				.open({
					url: 'hrp/hr/sc/hrtabledesign/hrTableDesignAddPage.do?isCheck=false',
					height: 400,
					width: 700,
					title: '查询设计器添加页',
					modal: true,
					showToggle: false,
					showMax: false,
					showMin: false,
					isResize: true,
					//top: 0,
					parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
					buttons: [ {
						text: '确定',
						onclick: function(item, dialog) {
							dialog.frame.saveDesign();
							//loadTree();
							//search();
						},
						cls: 'l-dialog-btn-highlight'
					}, {
						text: '取消',
						onclick: function(item, dialog) {
							dialog.close();
						}
					} ]
				});
	}

	//修改查询设计器
	function updateTabDesign() {
		//获取树当前选择项
		if (tree.getSelected()) {
			
			var parm = 'design_code=' + tree.getSelected().data.id;
			parent.$.ligerDialog
					.open({
						url: 'hrp/hr/sc/hrtabledesign/hrTableDesignUpdatePage.do?isCheck=false&'
								+ parm,
						title: '查询设计器修改页',
						height: 400,
						width: 700,
						modal: true,
						showToggle: false,
						showMax: false,
						showMin: false,
						isResize: true,
						//top: 0,
						parentframename: window.name,
						//用于parent弹出层调用本页面的方法或变量
						buttons: [ {
							text: '确定',
							onclick: function(item, dialog) {
								dialog.frame.saveDesign();
								//loadTree();
								//search();
							},
							cls: 'l-dialog-btn-highlight'
						}, {
							text: '取消',
							onclick: function(item, dialog) {
								dialog.close();
							}
						} ]
					});
		} else {
			$.ligerDialog.error('请选择树节点');
		}
	}

	//删除查询设计器
	function deleteTabDesign() {
		//获取树当前选择项
		if (tree.getSelected()) {
			/* if (tree.getSelected().data.pid == null) {
				$.ligerDialog.error('请选择末级节点');
				return;
			} */
			$.ligerDialog.confirm('确定删除?', function(e) {
				if(e){
					ajaxPostData({
						url: "deleteHrTableDesign.do?isCheck=false",
						data: {
							design_code: tree.getSelected().data.id
						},
						success: function(res) {
							if (res.state == "true") {
								tree.reload();
								//search();
							}
						}
					})
				}
			});
		} else {
			$.ligerDialog.error('请选择树节点');
		}
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

	//导入数据
	function importData() {
		$.ligerDialog.open({url: 'importPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	}
	
	//导出数据
	function exportData() {
		/* if(tree.getChecked()){
			var ids = '';
			$.each(tree.getChecked(), function(index, item){
				if(item.data.pid){
					ids += item.data.id + ','
				}
			});
			location.href = "export.do?isCheck=false&ids="+ids;
		} */
		var id = '';
        var name = '';
        if(tree.getSelected()){
        	console.log(tree.getSelected())
            id = tree.getSelected().data.id;
            name = tree.getSelected().data.design_name;
        }
        location.href = "export.do?isCheck=false&design_code="+id+"&design_name="+name;
		
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="设计器">
			<div id="form1" class="liger-form left-buttons">
				<div class="buttons">
					<input data-text="增加" data-click="addTabDesign" data-width="60"/> 
					<input data-text="修改" data-click="updateTabDesign" data-width="60"/> 
					<input data-text="删除" data-click="deleteTabDesign" data-width="60"/>
					<input data-text="导入" data-click="importData" data-width="60"/> 
                    <input data-text="导出" data-click="exportData" data-width="60"/>
				</div>
			</div>
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 145px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center" title="">
		    <iframe frameborder="0" width="100%" height="100%" name="designFrame" id="designFrame" src="hrDataTableAddPage.do?isCheck=false"></iframe>
			<!-- <div id="navtab">
				<div id="layout_item1" tabid="item1" title="查询设计器" lselected="true">
			    	<iframe frameborder="0" name="designFrame" id="designFrame" src="hrDataTableAddPage.do?isCheck=false"></iframe>
		    	</div>
		    	<div id="layout_item2" tabid="item2" title="生成SQL">
		    		<div position="center">
						<div id="toptoolbar"></div>
						<div id="sql_container" style="height: 100%; padding: 20px 10px;">
			  			  <table cellpadding="0" cellspacing="0" class="l-table-edit" style="width: 100%; height: calc(100% - 120px);">
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
		    	<div tabid="item3" title="页面显示设置" lselected="true">
                    <iframe frameborder="0" name="designPageFrame" id="designPageFrame" src="hrDesignQuerySetPage.do?isCheck=false"></iframe>
                </div>
		    </div> -->
		</div>
	</div>
	<%-- <script type="text/javascript" src="<%=path%>/lib/hrp/hr/editor/editor.js"></script> --%>
	<script type="text/javascript">
		//tab页签
		/* $("#navtab").ligerTab({height: '100%', onBeforeSelectTabItem: function(e){
			if(e == 'item1'){
				$("#designFrame")[0].contentWindow.loadGridData(actionNodeID);
			}else if(e == 'item2'){
				
			}else if(e == 'item3'){
				$("#designPageFrame")[0].contentWindow.loadGridData(actionNodeID);
			}
		},onAfterSelectTabItem: function(e){
			editor.refresh();
		}}); */
		/* $.ajax({
		    type : "post",
		    url :  '../hrtablestruc/queryHrTableWords.do?isCheck=false',
		    dataType : "json",
		    async:false,
		    success: function(r){
		      if(r){
		          loadEditor(r);
		      }
		    }
		}); */
	</script>
</body>
</html>
