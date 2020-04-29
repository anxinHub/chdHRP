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
<style type="text/css">
.ztree * {
font-size:16px;
line-height:20px
}
.ztree li {
line-height:20px
}
</style>
<script type="text/javascript">
		$(function ()
        {   
			 
			loadDict();
			loadTree();
            
			//checkNode();
			if($("#flag").val() == "true"){
            	$("#between").attr("checked",true);
            }else{
            	$("#between").attr("checked",false);
            }
            
           check_nodes='${check_nodes}';
            console.log(JSON.stringify(check_nodes))
            if(check_nodes!="" && check_nodes != null){
            	
            	for (var i=0, l=check_nodes.length; i < l; i++) {
            		
            		tree.checkNode(check_nodes[i], true, true);
            		
            	}
            	
            }
        });
		
		//按范围查询
		function onBetween(){
        	
			if($("#between").attr("checked") == true){
        		$("#munSearch").show();
        	}else{
        		$("#munSearch").hide();
        	}
     
        }
     function getListBox(){
    	/*  var box2 = liger.get("listbox2"); */
    	// var data = box2.data;
    	
    	var data = tree.getCheckedNodes(true);
    	var nodes=[] ;
    	if($("#between").attr("checked") == true){
	   		 nodes.push({id:liger.get("subj_code1").getValue(),text:liger.get("subj_code1").getText()},
	   		 			{id:liger.get("subj_code2").getValue(),text:liger.get("subj_code2").getText()})
	   	}else {
      		if(data.length > 200){
       			$.ligerDialog.error('选中的科目不能超过200');
       			return;	
       		} 
       		$.each(data,function(index,value){
       			var param = {};
       			if(!value.isParent){
       				if(value.pId != 0){
              			
              			if((index+1) == data.length){
              				param.id = value.subj_id+"."+value.name.toString().split(" ")[0];
              				param.text = value.name;
              			}else{
              				param.id = value.subj_id+"."+value.name.toString().split(" ")[0];
              				param.text = value.name;
              			}
              		}else{
              			return;
              		}
       				nodes.push(param);
       			}else{
       				return;
       			}
          	});
         		
       		parent.$("#subj_flag").val("true");	
       	}
     		
    	 return nodes;
     }  
    </script>
    <script type="text/javascript">
    var tree;
    var setting = {  
   		check : {
               enable : true,
               chkStyle : "checkbox",    //复选框
               chkboxType : {
            	   "Y": "ps",
                   "N" : "ps"
               }
           },
		data: {  
			simpleData: {
				enable: true,
				idKey: "id",
                pIdKey: "pId",
                rootPId: 0
				
			},
			keep: {
				leaf: true
			},
		    key: {
				children: "nodes"
			}
		},
		   
		treeNode:{
			open:true
		},
		callback:{
			onNodeCreated: function (event, treeId, node) {
		        var treeObj = $.fn.zTree.getZTreeObj("tree");
		        var treeNodes = treeObj.getNodes();
		        if(liger.get("subj_level").getValue()){
		        	  treeObj.expandNode(node, true, false, false);  
		        } 
		        
		       if(node.nodes && node.level===0 && node.nodes.length === 0) {
		            treeObj.hideNode(node);
		          }  
		       
		      },
		      onDblClick: function(event,treeId, node) {
		    	  
		    		  var zTree = $.fn.zTree.getZTreeObj("tree");
		    		   
		    		  if(node && !node.pId == 0){
		    			 
		    			  zTree.checkNode(node, !node.checked, true);   
			        	   
		    			  parent.liger.get("subj_code").setValue(node.id);
			        	    
		    			  parent.liger.get("subj_code").setText(node.name);
			        	    
		    			  parent.$(".l-dialog,.l-window-mask").remove(); //关闭弹出层
		    		 } 
		    		  
		    }
		},
		view:{
			dblClickExpand:false
		}
			

   }; 
    function loadTree(){
    	
    	var select_code = $("#select_code").val();
    	var queryTree = $("#queryTree").val();
    	var subj_level = liger.get("subj_level").getValue();
    	ajaxJsonObjectByUrl("queryGroupSubjBySelector.do?isCheck=false",{'select_code':select_code,'queryTree':queryTree,'is_stop':'0','subj_level':subj_level,'rela_code':'${rela_code}'},function (responseData){
    	     //console.log(JSON.stringify(responseData))  
    		 
    		tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    		
    	});
    }
    
    function loadDict (){
    	autocomplete("#subj_level","../querySubjLevel.do?isCheck=false","id","text",true,true,'',false,false,'160');
    	$("#queryTree").ligerTextBox({width:160}  );
    	$("#select_code").ligerTextBox({width:80}  );
    	
    	var count = 0;
    	$("#subj_code1").ligerComboBox({
    		url: "../querySubjBylevel.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '160',
    		selectBoxHeight:'150',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInTop: true,
    		onSuccess: function (data) {
   				if (count == 0) {
   					this.setValue(data[0].id);
   				}
    			count++;
    		},
    	}); 
    	var count1=0;
    	$("#subj_code2").ligerComboBox({
    		url: "../querySubjBylevel.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '160',
    		selectBoxHeight:'150',
    		setTextBySource: true,
    		width: '160',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInTop: true,
    	    onSuccess: function (data) {
   				if (count1 == 0) {
   					this.setValue(data[0].id);
   				}
   				count1++;
    		}
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
    				
        			param = param +"{'id':'"+value.subj_id+"."+value.name.toString().split(" ")[0]+"','text':'"+value.name+"'}";
        		
    			}else{
        			
    				param = param +"{'id':'"+value.subj_id+"."+value.name.toString().split(" ")[0]+"','text':'"+value.name+"'},";
        		
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
   			<td align="center" style="font-size: 16px">
   				<select id="select_code" name="select_code">
   					<option value="spell_code">拼音码</option>
   					<option value="wbx_code">五笔码</option>
   					<option value="subj_code">科目编码</option>
   					<option value="subj_name">科目名称</option>
   				</select>
   			</td>
            <td align="center" class="l-table-edit-td"   ><input id="queryTree" name="queryTree" type="text" /></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;font-size: 16px">级次：</td>
			<td align="left" class="l-table-edit-td"><input
				name="subj_level" type="text" id="subj_level" ltype="text" /></td>
			<td align="left"></td>
            <td align="center" style="font-size: 16px;padding-left: 20px">
            	<input class="l-button l-button-test" type="button" value="查询" onclick="loadTree();"/>
            </td>
   		</tr>
   </table>
  <div class="l-layout" id="layout1" style="height: 87%;" ligeruiid="layout1" >
	   <div class="l-layout-left" style="left: 5px; top: 0px;height: 420px;">
	   		<div class="l-layout-content" position="left"  >
			   <div style="float: left;"  >
			  		<div class="l-layout-header" style="font-size: 16px">列表</div>
			        <div style="width:575px; height:400px;overflow:auto;" >
					      <ul class="ztree" id="tree" ></ul>
					</div>
			    </div>
		   </div>  
		</div>    
   </div>
    <div style="margin-top: 5px;margin-left: 5px;font-size: 16px;float:left;"><input type="checkbox" id="between" name="between" onclick="onBetween();"/>按范围查询</div>
    <div style="float:left;display:none" class="l-table-edit-td" id="munSearch">
  		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 0;font-size: 16px">科目：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="subj_code1" type="hidden" id="subj_code1" ltype="text" />
               	</td>
                <td align="right" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td" >
                	<input name="subj_code2" type="hidden" id="subj_code2" ltype="text" />
               	</td>
       		</tr>
		</table> 
     </div>  
    </body>
</html>
