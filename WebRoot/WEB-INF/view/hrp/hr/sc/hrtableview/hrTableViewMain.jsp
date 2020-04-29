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

#navtab .l-tab-content{
	height: 100%;
}
#navtab .l-tab-content .l-tab-content-item{
	overflow: auto;
}
</style>
<script type="text/javascript">
	var tree, grid, actionNodeID;
	var gridManager = null;
	
	$(function() {
		//布局
		$("#layout").ligerLayout({leftWidth : 250, minLeftWidth : 230, allowLeftResize : false});

		//tab页签
		//$("#navtab").ligerTab();
		
		loadDict();//加载下拉框
		loadTree();
		loadGrid();
		
		$("#search_input").keyup(function(e) {
			loadTree();
		});
		
	});

	//查询
	function search() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'tab_code',
			value : actionNodeID
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadTree() {
		var key = $("#search_input").val();
		tree = $("#mainTree").ligerTree(
				{url: '../hrtablestruc/queryHrTableStrucTree.do?isCheck=false&key='+key, 
           		 ajaxType: 'post' ,
           		 idFieldName :'id',
           		 textFieldName: 'name',
                 parentIDFieldName :'pId', 
                 nodeWidth:'100%',
           		 checkbox: false,
           		 onClick: function(node, target){
           			actionNodeID = node.data.id;
           			search();
           			
           			var toolBar = gridManager.toolbarManager;
           			if(this.getSelected()){
           				toolBar.setEnabled("save");
           			}else{
           				toolBar.setDisabled("save");
           			}
           		 }
            });
	}

	function loadGrid() {
		grid = $("#maingrid").ligerGrid(
				{columns : [],
					dataAction : 'server', dataType : 'server', usePager : true, url : 'queryHrTableView.do?isCheck=false',
					width : '100%', height : '100%', checkbox : true, rownumbers : true, delayLoad : true, enabledEdit: true,
					selectRowButtonOnly : true, isAddRow: false, onAfterShowData: function(){},
					toolbar : {
						items : [ {
							text : '查询', id : 'search', click : search, icon : 'search'
						}, {
							line : true
						}, {
							text : '保存', id : 'save', click : save, icon : 'save', disable: true
						}]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}


	//保存数据表字段信息
	function save() {
		gridManager.endEdit();
		var allData = gridManager.getData(); //修改数据
	    console.log(allData)
	}

	//字典下拉框
	function loadDict() {
	}

	//打印数据
	function printDate() {
		
	}

	//导入数据
	function importData() {
		
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
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
