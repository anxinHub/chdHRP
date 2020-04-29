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
            
			//checkNode();
			if($("#flag").val() == "true"){
            	$("#between").attr("checked",true);
            }else{
            	$("#between").attr("checked",false);
            }
            
           check_nodes='${check_nodes}';
            
            if(check_nodes!="" && check_nodes != null){
            	
            	for (var i=0, l=check_nodes.length; i < l; i++) {
            		
            		tree.checkNode(check_nodes[i], true, true);
            		
            	}
            	
            }
        });
       function onBetween(){
        	
    	   var data = tree.getCheckedNodes(true);
    	   var nodes=[] ;
        	if($("#between").attr("checked") == true){
        		
        		$.each(data,function(index,value){
        			 
        			if(!value.isParent){
        				nodes.push(value); 
        			}
        		
        		});
        		
        		if(nodes.length > 2){
        		 
        			$("#between").attr("checked",false);
        			$.ligerDialog.error('只能添加两个科目进行范围查询');
        			return;
        		}
        	}
     
        }
     function getListBox(){
    	/*  var box2 = liger.get("listbox2"); */
    	// var data = box2.data;
    	var param = "";
    	var data = tree.getCheckedNodes(true);
    	var nodes=[] ;
      		/* if(data.length > 50){
      			$.ligerDialog.error('选中的科目不能超过50');
      			return;
      			
      		}  */
      		
         	if($("#between").attr("checked") == true){
         		
         		$.each(data,function(index,value){
         			 
         			if(!value.isParent){
         				nodes.push(value); 
         			}
         		
         		});
         		
         		if(nodes.length > 2){
         		 
         			$("#between").attr("checked",false);
         			$.ligerDialog.error('只能添加两个科目进行范围查询');
         			return;
         		}
         		$.each(data,function(index,value){
            		
            		if(value.pId != 0){
            			
            			if((index+1) == data.length){
            				
                			param = param +"{'id':'"+value.id+"','text':'"+value.name+"'}";
                		
            			}else{
                			
            				param = param +"{'id':'"+value.id+"','text':'"+value.name+"'},";
                		
            			}
            		}
            	});
         		
         		parent.$("#subj_flag").val("true");
         		
         	}else {
   
     		$.each(data,function(index,value){
        		
        		if(value.pId != 0){
        			
        			if((index+1) == data.length){
        				
            			param = param +"{'id':'"+value.id+"','text':'"+value.name+"'}";
            		
        			}else{
            			
        				param = param +"{'id':'"+value.id+"','text':'"+value.name+"'},";
            		
        			}
        		}
        	});
     		 
     		parent.$("#subj_flag").val("false");
         	}
     		parent.check_tree=data;
     	  
    	 return eval("["+param+"]");
    	 
     }  
    </script>
    <script type="text/javascript">
    var tree;
    var setting = {      
		data: {
			simpleData: {
				enable: false
				
			},
		    key: {
				children: "nodes"
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
    	ajaxJsonObjectByUrl("queryInvBySelector.do?isCheck=false",{'select_code':select_code,'queryTree':queryTree,'is_stop':'0'},function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
    /* function checkNode(){
    	
    	var listBoxData =$("#boxList").val();
        
        if(listBoxData.length>0){
        	
        	var listData =eval("["+listBoxData+"]");
        	
        	$.each(listData,function(i,v){
        		
        		var node = tree.getNodeByParam("id", v.nId);
        		
        		tree.checkNode(node);
        		
        	});
        	
        }
        
    } */
    
    function selectTreeNode(){
    	
    	var param = "";
    	
    	var nodes =tree.getSelectedNodes();
    	 
    	if(nodes.length>2){
    		
    		$.ligerDialog.error('科目为单选！');
    	}
    	
    	$.each(nodes,function(index,value){
    		
    		if(value.pId != 0){
    			
    			if((index+1) == nodes.length){
    				
        			param = param +"{'id':'"+value.inv_id+"','text':'"+value.name+"'}";
        		
    			}else{
        			
    				param = param +"{'id':'"+value.inv_id+"','text':'"+value.name+"'},";
        		
    			}
    		}
    	});
    	
    	
    	
    	return eval("["+param+"]");
    	
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
   <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
   		<tr>
   			<td align="center">
   				<select id="select_code" name="select_code">
   					<option value="spell_code">拼音码</option>
   					<option value="wbx_code">五笔码</option>
   					<option value="subj_code">科目编码</option>
   					<option value="subj_name">科目名称</option>
   				</select>
   			</td>
            <td align="center" class="l-table-edit-td"   ><input id="queryTree" name="queryTree" type="text" /></td>
            <td align="center">
            	<input class="l-button l-button-test"  type="button" value="查询" onclick="loadTree();"/>
            </td>
   		</tr>
   </table>
   <div class="l-layout" id="layout1" style="height: 87%;" ligeruiid="layout1">
   <div class="l-layout-left" style="left: 5px; top: 0px; width: 450px; height: 350px;">
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
    <div style="margin-top: 5px;margin-left: 5px;"><input type="checkbox" id="between" name="between" onclick="onBetween();"/>按范围查询</div>
    </body>
</html>
