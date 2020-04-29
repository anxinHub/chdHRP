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
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
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

 .tree{
       height: 400px;
       width:100%;
       overflow: auto;
   }
</style>
<script type="text/javascript">
	var tree
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function() {
		loadTree();
		 $("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = '../hrtabledesign/queryHrTableDesignTree.do?isCheck=false&key='+key;
			tree.reload();
		}); 
		
	});
	
	function loadTree() {
		 tree = $("#mainTree").ligerTree({
		     url:'../hrtabledesign/queryHrTableDesignTree.do?isCheck=false',
		     ajaxType: 'post' ,
        	 idFieldName:'id',
        	 textFieldName: 'text',
             parentIDFieldName:'pid', 
             nodeWidth:'100%',
        	 checkbox: false,
        });
		 manager = $("#mainTree").ligerGetTreeManager();
	}

	 function save(){
		  var treeData = manager.getSelected();
		  if(treeData!= null)
		  if(treeData.data.children != null)return $.ligerDialog.error('请选择请求名称')
		  parentFrameUse().setElemTemplateTree(dialog.get('treeElemInputTextId'),treeData);
		  dialog.close();//关闭dialog 
	   }
</script>
</head>
<body style="padding:10px">
	<div id="pageloading" class="l-loading" style="display: none"></div>
		<div class="search-form">
		  <label>快速定位</label> 
		  <input type="text" id="search_input" class="text-input"> 
		</div>
		 <div class="tree"> 
            <ul id="mainTree"></ul> 
        </div> 
</body>
</html>
