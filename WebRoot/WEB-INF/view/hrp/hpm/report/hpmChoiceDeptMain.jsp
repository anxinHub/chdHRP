<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/htc.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript"></script>
<script type="text/javascript">

	$(function(){
		loadTree();
	});
    var tree;
    var setting = {      
		data: {
			simpleData: {enable: false},
		    key: {children: "nodes"}
		},
		check: {
			enable: true
		},
		treeNode:{
			open:false
		},
		callback:{
			//onClick:groupDetail
		}                
   }; 
    
    function loadTree(){
    	var para = {
	    	select_code : $("#select_code").val(),
	    	queryTree : $("#queryTree").val()
    	}
    	ajaxJsonObjectByUrl("queryCustomDeptTree.do?isCheck=false",para,function (responseData){
//    	ajaxJsonObjectByUrl("../target/queryTargetTree.do?isCheck=false",para,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    	
    function getDeptList(){
    	var param = "";
    	var data = tree.getCheckedNodes(true);
		
    	$.each(data,function(index,value){
        	if((index+1) == data.length){
        		param = param +"{'id':'" +value.id +"','text':'"+value.name+"'}";
            }else{
        		param = param +"{'id':'"+value.id +"','text':'"+value.name+"'},";
        	}
        });
     		
     	parent.check_tree=data;
    	return eval("["+param+"]");
     }
    
</script>

<style type="text/css">
	.middle input {display: block;width:30px; margin:2px;}
</style>
</head>
  
<body>
   	<input type="hidden" value="${listBoxData}" id="boxList" name="boxList"/>
   	<input type="hidden" value="${flag}" id="flag" name="flag"/>
   
   	<table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
		<tr>
	   		<td align="center">
	   			<select id="select_code" name="select_code">
	   				<option value="spell_code">拼音码</option>
	   				<option value="wbx_code">五笔码</option>
	   				<option value="dept_code">科室编码</option>
	   				<option value="dept_name">科室名称</option>
	   			</select>
	   		</td>
	        
	        <td align="center" class="l-table-edit-td"   ><input id="queryTree" name="queryTree" type="text" /></td>
	            <td align="center">
	            	<input class="l-button l-button-test"  type="button" value="查询" onclick="loadTree();"/>
	            </td>
	   	</tr>
   </table>
   
   	<div class="l-layout" id="layout1" style="height: 87%;" ligeruiid="layout1">
   	<div class="l-layout-left" style="left: 0px; top: 0px; width: 450px; height: 350px;">
	   	<div class="l-layout-content" position="left">
			<div style="float: left;">
				<div class="l-layout-header">列表</div>
				   	<div style="width:450px; height:300px;overflow:auto;" >
						<ul class="ztree" id="tree" ></ul>
					</div>
			    </div>
			</div> 
		</div>    	
   	</div>
    </body>
</html>