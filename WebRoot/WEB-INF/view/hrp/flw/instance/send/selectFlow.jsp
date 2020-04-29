<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/plugins/ligerPanel.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>

<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ leftWidth: 220}); 
    	loadFlowTree();
    	$("#navtab1").ligerTab({
    		   onBeforeSelectTabItem: function (tabid){
    	            // alert('onBeforeSelectTabItem' + tabid);
    	       }, onAfterSelectTabItem: function (tabid){
    	             //alert('onAfterSelectTabItem' + tabid);
    	       } 		
    	});
    });
   

	//流程模板    
   function loadFlowTree(){
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
					onClick:onImage
				}
		}; 
		
	   ajaxJsonObjectByUrl("queryFlowCategoryDeploymentId.do",{},function (responseData){
	    	if(responseData!=null){
	    		tree=$.fn.zTree.init($("#tree1"), setting, responseData.Rows);
		     }
	    });
   }
	
	
	function onImage(){
		var nodeSel = tree.getSelectedNodes();
		$.each(nodeSel,function(index,value){
			if(value.deployment_id==""){
				return;
			}
			$("#pageloading").show();
			$("#showFlowImag").attr("src",'../flow/flowViewImage.do?deploymentId='+value.deployment_id);
			/*$("#showFlow").attr("src",'../flow/flowViewImagePage.do?deploymentId='+value.deployment_id);
			$("#showFlow").width(document.body.scrollWidth);
			$("#showFlow").height(document.body.scrollHeight);*/
			$("#pageloading").hide();
		});
	
	}
	
	
	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

     <div id="layout1" style="height: 100%;">
       
	     <div position="left" title="流程分类">
		    <div class="ztree"  style="float: left;" >
		       		<ul id="tree1"></ul>
		    </div>
	    </div>
	    <div position="center" title="流程模板" style="left:0px; top: 0px; width: 100%; height: 100%;">
	    	<div id="navtab1" style="width:100%;height:100%;overflow:hidden; border:1px solid #A3C0E8; ">
		    	<div title="正文" lselected="true" style="width:100%;height:100%;">
		    		
				</div>
				<div  title="流程图" style="width:100%;height:100%;">
					<!-- iframe id="showFlow" frameborder="1" src="" ></iframe-->
					<img id="showFlowImag" src="" />
				</div>
			</div>
       	</div>
       	
	</div>
</body>
</html>
