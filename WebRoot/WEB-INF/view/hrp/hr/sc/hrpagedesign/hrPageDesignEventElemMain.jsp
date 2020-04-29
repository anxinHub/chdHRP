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
<style type="text/css">
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

 .treemain{
       height: 400px;
       width:100%;
       overflow: auto;
   }
</style>
<script type="text/javascript">
	var tree
	var manager = null;
	var submanager = null;
	var treeSearchKey = ''
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function() {
  	
		//布局
		$("#layout").ligerLayout({leftWidth: '100%', allowLeftResize: false,allowLeftCollapse:false});
		loadTree();
		loadSubTree();
		
		//tree快速定位
		$("#search_input").keyup(function(e) {
			treeSearchKey = $("#search_input").val();
			tree.options.url = 'queryHrTableStrucTree.do?isCheck=false&key='+treeSearchKey;
			tree.reload();
		});

	});
	
	//加载左侧tree
	function loadTree() {
		tree = $("#main_tree").ligerTree({
			url: '../hrtablestruc/queryHrTableStrucTree.do?isCheck=false', 
   		 	ajaxType: 'post' ,
   		 	idFieldName :'id',
   		 	textFieldName: 'name',
         	parentIDFieldName :'pId', 
         	nodeWidth:'100%',
   		 	checkbox: false,
    		onClick: function(node, target){
    			subTree.clear();
    			if(node.data.pId && this.getSelected()){
    				actionNodeID = node.data.id;
        			subTree.options.url = '../hrtablestruc/querySqlStatement.do?isCheck=false&tab_code=' + actionNodeID;
        			subTree.reload();
    			}
    		}
     	});
		manager = $("#main_tree").ligerGetTreeManager();
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
        });
		submanager= $("#subTree").ligerGetTreeManager();
	}

	 function save(){
		  var leftTreeData = manager.getSelected();
		  var rightTreeData = submanager.getData();
		  if(leftTreeData!= null)
		  if(leftTreeData.data.children != null)return $.ligerDialog.error('请选择末级节点')
		  if(rightTreeData == '')return $.ligerDialog.error('请维护数据表信息')
		  parentFrameUse().setElemTemplateEvent(dialog.get('eventElemInputTextId'),leftTreeData,rightTreeData);
		  dialog.close();//关闭dialog 
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
			<div class="treemain">
				<ul id="main_tree"></ul>
			</div>
		</div>
		<div position="center" style="display: none">
			<div>
			   <ul id="subTree"></ul>
			</div>
		</div>
	</div>
</body>
</html>
