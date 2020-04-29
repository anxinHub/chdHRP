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
			
			$("#listbox1").ligerListBox({
                isShowCheckBox: true,
                isMultiSelect: true,
                height: 300,
                width:200
            });
            
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
		    var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		    var selecteds = box2.getSelectedItems();
		    if (!selecteds || !selecteds.length) return;
		    box2.removeItems(selecteds);
		    box1.addItems(selecteds);
		}
		function moveToRight()
		{
		    var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		    var selecteds = box1.getSelectedItems();
		    if (!selecteds || !selecteds.length) return;
		    box1.removeItems(selecteds);
		    box2.addItems(selecteds);
		}
		function moveAllToLeft()
		{ 
		    var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		    var selecteds = box2.data;
		    if (!selecteds || !selecteds.length) return;
		    box1.addItems(selecteds);
		    box2.removeItems(selecteds); 
		}
		function moveAllToRight()
		{ 
		    var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		    var selecteds = box1.data;
		    if (!selecteds || !selecteds.length) return;
		    box2.addItems(selecteds);
		    box1.removeItems(selecteds);
		    
		}

        
        function onBetween(){
        	
        	var box2 = liger.get("listbox2");
    		var data = box2.data;
        	if($("#between").attr("checked") == true){
        		if(data == null || data == ""){
        			box2.removeItems(data);
        			$("#between").attr("checked",false);
        			$.ligerDialog.error('请先又右添加两个职工');
        			return;
        		}
        		if(data.length > 2){
        			box2.removeItems(data);
        			$("#between").attr("checked",false);
        			$.ligerDialog.error('只能添加两个职工进行范围查询');
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
     			$.ligerDialog.error('只能添加两个职工进行范围查询');
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
		treeNode:{
			open:true
		},
		callback:{
			onClick:selectDept
		}             

   }; 
    
    function selectDept(){
    	var node = tree.getSelectedNodes();
    	$.each(node,function(index,value){
    			$.post("../../sys/queryEmp.do?isCheck=false",{'dept_id':value.dept_id},function(data){
        			var box1 = liger.get("listbox1");
                    var selecteds = box1.data;
                    box1.removeItems(selecteds);
                    box1.addItems(data);
    			
    		},"json");
    	});
    }
    
    function loadTree(){
    	var select_code = $("#select_code").val();
    	var queryTree = $("#queryTree").val();
    	ajaxJsonObjectByUrl("queryGroupDeptBySelector.do?isCheck=false",{'select_code':select_code,'queryTree':queryTree,'is_stop':'0','rela_code':'${rela_code}'},function (responseData){
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
   					<option value="emp_code">职工</option>
   					<option value="dept_code">部门</option>
   				</select>
   			</td>
            <td align="center" class="l-table-edit-td"   ><input id="queryTree" name="queryTree" type="text" /></td>
            <td align="center">
            	<input class="l-button l-button-test"  type="button" value="查询" onclick="loadTree();"/>
            </td>
   		</tr>
   </table>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
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
	<div class="l-layout-center" style="left: 205px; top: 0px; width: 480px; height: 350px;">
	<div class="l-layout-header">职工
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	已选</div>
	           <div title="" class="l-layout-content" style="height: 350px;" position="center">
    <div style="margin: 4px; float: left;">
       <div id="listbox1"></div> 
   </div>
    <div class="middle" style="margin-top:50px; float: left;">
		 <input onclick="moveToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value=">>">
         <input onclick="moveToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="<<">
         <input onclick="moveAllToRight()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全选">
         <input onclick="moveAllToLeft()" class="l-button l-button-test" style="margin-top:20px;" type="button" value="全删">
    </div>
   <div style="margin: 4px; float: left;">
       <div id="listbox2"></div> 
   </div>
   <div style="margin-left:360px;float: left; " ><input type="checkbox" id="between" name="between" onclick="onBetween();"/>按范围查询</div>
   </div>
   </div>
   </div>
    </body>
</html>
