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
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
$(function ()
        { 
			loadTree();
            
            $("#listbox2").ligerListBox({
                isShowCheckBox: true,
                isMultiSelect: true,
                height: 300,
                width:200
            });
            var box2 = liger.get("listbox2");
            box2.addItems(eval("["+$("#boxList").val()+"]"));
            if($("#flag").val() == "true"){
            	$("#between").attr("checked",true);
            }else{
            	$("#between").attr("checked",false);
            }
        });
        function moveToLeft()
        {
        	var box2 = liger.get("listbox2");
            var selecteds = box2.getSelectedItems();
            box2.removeItems(selecteds); 
        }
        function moveToRight()
        {
        	moveAllToLeft();
        	var nodes = tree.getCheckedNodes();
        	var param = "";
        	$.each(nodes,function(index,value){
        		
        			if((index+1) == nodes.length){
            			param = param +"{'id':'"+value.name.toString().split(" ")[0]+"."+value.id+"','text':'"+value.name+"'}";
            		}else{
            			param = param +"{'id':'"+value.name.toString().split(" ")[0]+"."+value.id+"','text':'"+value.name+"'},";
            		}
        	});
        	var box2 = liger.get("listbox2");
            box2.addItems(eval("["+param+"]"));
        }
        function moveAllToLeft()
        { 
        	var  box2 = liger.get("listbox2");
            var selecteds = box2.data;
            box2.removeItems(selecteds);
        }
        function moveAllToRight()
        { 
        	moveAllToLeft();
        	var nodes = tree.getCheckedNodes(false);
        	if(nodes == "" || nodes == null){
        		nodes = tree.getCheckedNodes(true);
        	}
        	var param = "";
        	$.each(nodes,function(index,value){
        		
        			if((index+1) == nodes.length){
            			param = param +"{'id':'"+value.name.toString().split(" ")[0]+"."+value.id+"','text':'"+value.name+"'}";
            		}else{
            			param = param +"{'id':'"+value.name.toString().split(" ")[0]+"."+value.id+"','text':'"+value.name+"'},";
            		}
        			
        	});
        	var box2 = liger.get("listbox2");
            box2.addItems(eval("["+param+"]"));
        }
        
        function onBetween(){
        	
        	var box2 = liger.get("listbox2");
    		var data = box2.data;
        	if($("#between").attr("checked") == true){
        		if(data == null || data == ""){
        			box2.removeItems(data);
        			$("#between").attr("checked",false);
        			$.ligerDialog.error('请先又右添加两个库房');
        			return;
        		}
        		if(data.length > 2){
        			box2.removeItems(data);
        			$("#between").attr("checked",false);
        			$.ligerDialog.error('只能添加两个库房进行范围查询');
        			return;
        		}
        	}
     
        }
     function getListBox(){
    	 var box2 = liger.get("listbox2");
    	 var data = box2.data;
    	 if($("#between").attr("checked") == true){
    		if(data.length > 2){
      			box2.removeItems(data);
      			$("#between").attr("checked",false);
      			$.ligerDialog.error('只能添加两个库房进行范围查询');
      			return;
      		}
    		parent.$("#flag").val("true");
     	 }else{
     		parent.$("#flag").val("false");
     	 }
    	 return data;
     }
    </script>
    <script type="text/javascript">
    var tree;
    var setting = {      
		data: {
			simpleData: {
				enable: true
			}
		},
		check: {
			enable: true
		},
		treeNode:{
			open:true
		},
		callback:{
			//onClick:groupDetail
		}                

   }; 
    function loadTree(){
    	var select_code = $("#select_code").val();
    	var queryTree = $("#queryTree").val();
    	ajaxJsonObjectByUrl("queryStoreBySelector.do?isCheck=false",{'select_code':select_code,'queryTree':queryTree,'is_stop':'0'},function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
    </script>
  <style type="text/css">
        .middle input {
            display: block;width:30px; margin:2px;
        }
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
   					<option value="store_code">编码</option>
   					<option value="store_name">名称</option>
   				</select>
   			</td>
            <td align="center" class="l-table-edit-td"   ><input id="queryTree" name="queryTree" type="text" /></td>
            <td align="center">
            	<input class="l-button l-button-test"  type="button" value="查询" onclick="loadTree();"/>
            </td>
   		</tr>
   </table>
   <div class="l-layout" id="layout1" style="height: 87%;" ligeruiid="layout1">
   <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 350px;">
   <div class="l-layout-content" position="left">
	   <div style="float: left;">
	  		<div class="l-layout-header">列表</div>
	        <div style="width:200px; height:300px;overflow:auto;" >
			      <ul class="ztree" id="tree" ></ul>
			</div>
	    </div>
	   </div> 
	</div>    
	<div class="l-layout-center" style="left: 205px; top: 0px; width: 250px; height: 350px;"><div class="l-layout-header">已选</div>
	           <div title="" class="l-layout-content" style="height: 350px;" position="center">
    <div class="middle" style="margin-top:50px; float: left;">
		 <input onclick="moveToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value=">>">
         <input onclick="moveToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="<<">
         <input onclick="moveAllToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全选">
         <input onclick="moveAllToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全删">
    </div>
   <div style="margin: 4px; float: left;">
       <div id="listbox2"></div> 
   </div>
   <div style="margin-left:160px; " >
   	<input type="checkbox" id="between" name="between" onclick="onBetween();"/>按范围查询
   </div>
   </div>
   </div>
   </div>
    </body>
</html>
