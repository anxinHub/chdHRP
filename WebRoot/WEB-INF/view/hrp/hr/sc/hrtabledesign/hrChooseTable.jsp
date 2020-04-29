<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<style>
    html,body{
     height:100%;
    }
	.l-dialog-table tbody:first-child{
		display: none;
	} 
	/* 搜索框 */
	.search-form {
	    padding: 5px;
	    /* text-align: center; */
	    box-sizing: border-box;
	    background: #e0ecff;
	    border-bottom: 1px solid #a3c0e8;
	}
	.search-form label{
	   margin-left: 36px;
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
	    width: 240px;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	}
	#mainTree >li >div.l-unselectable .l-checkbox{
	   display: none; 
	}
</style>
<script type="text/javascript">
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var design_code = dialog.get('data').design_code;
	var tree;
	$(function() {
		loadTree();
		$("#search_input").keyup(function(e) {
            var key = $("#search_input").val();
            tree.options.url = 'queryDBTableTree.do?isCheck=false&key='+key+'&design_code='+design_code;
            tree.reload();
        });
	});
	
	function loadTree() {
        tree = $("#mainTree").ligerTree(
                {url: 'queryDBTableTree.do?isCheck=false&design_code='+design_code, 
                 ajaxType: 'post' ,
                 idFieldName :'tab_code',
                 textFieldName: 'tab_name',
                 parentIDFieldName :'pid', 
                 nodeWidth:'1',
                 checkbox: true,
                 autoCheckboxEven: false,
                 isExpand: false, 
                 selectable: function(e){
                	 if(!e.pid){
                		return false;
                	 }
                 },
                 onCheck: function(node, checked){
                    if(node.data.pid){
                        if(checked){
                        	parentFrameUse().appendRow(node.data);
                        }else{
                        	parentFrameUse().deleteRow(node.data);
                        }
                    }
                }
            });
    }
</script>
</head>
<body style="padding: 0px;">
	<div class="search-form">
        <label>快速定位</label> 
        <input type="text" id="search_input" class="text-input"> 
    </div>
    <div style="width:100%;height:calc(100% - 37px);overflow:auto;">
        <ul id="mainTree"></ul>
    </div>
</body>
</html>
