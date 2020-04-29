<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>
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
		loadTree();
    	
    });
   

    </script>
	<script type="text/javascript">
       
       var tree;
       var setting = {      
   		data: {
   			simpleData: {
   				enable: true
   			}
   		},check: {  
            enable: true //显示复选框  
        }, 
   		treeNode:{
   			open:true
   		},
   		callback:{
   			onClick:groupDetail,
   			onCheck: zTreeOnCheck
   		}
      }; 
       
       function zTreeOnCheck(event, treeId, treeNode) {
    	    /* treeNode.tId  选择的节点id
    	    treeNode.name  选择的节点名称
    	    treeNode.checked 节点的选中状态*/
    	};
		
       function itemclick1(item){ 
           if(item.id)
           {
               switch (item.id)
               {
               	case "add":
             		$.ligerDialog.open({url: '../accchecktype/accCheckTypeAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
             		return;
               	case "delete":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择核算类型');
            			return;
            		}
            		var ParamVo =[];
                    $(node).each(function (){	
                    	if(this.id == "00" || this.id == "01"){
            				$.ligerDialog.error('系统节点不能操作');
                			return;
            			}else{
            				ParamVo.push(
            						this.id   +"@"+ 
            						this.group_id   +"@"+ 
            						this.hos_id   +"@"+ 
            						this.copy_code 
            						);
            				$.ligerDialog.confirm('确定删除?', function (yes){
                             	if(yes){
                                    $.post("../accchecktype/deleteAccCheckType.do?isCheck=false",{ParamVo : ParamVo},function (responseData){
                                		if(responseData.state=="true"){
                                			$.ligerDialog.success('删除成功');
                                			loadTree();
                                		}
                                	},"json");
                             	}
                    		 });
            			}
						
                    });
               		return;
               	case "update":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择核算类型');
            			return;
            		}
            		$.each(node,function(index,value){
            			if(value.id == "00" || value.id == "01"){
            				$.ligerDialog.error('系统节点不能操作');
                			return;
            			}
            			$.ligerDialog.open({url: '../accchecktype/accCheckTypeUpdatePage.do?isCheck=false&check_type_id='+value.id+'&group_id='+value.group_id+'&hos_id='+value.hos_id+'&copy_code='+value.copy_code, 
            					height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
            		});
               		return;
               }
           }
       }    
       function groupDetail(){
    	   var node = tree.getSelectedNodes();
    	   
    	   $.each(node,function(index,value){
    		   if(value.id == "00" || value.id == "01"){
       			return;
   			}
    		   
    	   	if(value.is_sys == 0){
    	   		
    	   			$("#myFrame").attr("src","accCheckItemMainPage.do?isCheck=false&check_type_id="+value.id+"&check_type_name="+value.name);
    	   	}
    	   	if(value.is_sys == 1){
    	   		switch (value.name){
    	   			case "部门":
        	   			$("#myFrame").attr("src","accZcheckDeptPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
    	   			case "职工":
        	   			$("#myFrame").attr("src","accZcheckEmpPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
					case "项目":
        	   			$("#myFrame").attr("src","accZcheckProjPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
					case "库房":
						$("#myFrame").attr("src","accZcheckStorePage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "客户":
						$("#myFrame").attr("src","accZcheckCusPage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "供应商":
						$("#myFrame").attr("src","accZcheckSupPage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "资金来源":
						$("#myFrame").attr("src","accZcheckSourcePage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "单位":
						$("#myFrame").attr("src","accCheckHosInfoPage.do?isCheck=false&check_type_id="+value.id);
						return;
    	   		}
    	   		
    	   	}
    	   });	
       }
       
       var select_child="";
       
       function saveSelectData(){
    	   
    	   var select_Json=[];
    	   
    	   var check_node=tree.getCheckedNodes(true);

    	   //alert($("#myFrame").getSelectedRow();)
    	  
    	   $.each(check_node,function(index,value){

    		   var parent_node={};
    		   
			   if((index+1)==check_node.length){
    			   
    			   parent_node.child_node=select_child;

    		   }
    		   
    		   if(value.id != "00"&&value.id != "01"){
    			   
    			   parent_node.node_code=value.id;
    			   
    			   parent_node.node_name=value.name;
    			   
    			   select_Json.push(parent_node);
    			   
    		   }
    		   
   			});

    	   console.log("JSON",JSON.stringify(select_Json));
    	  
    	   return select_Json;
    	   
    	   
    	   
       }
	
	   var oFrm = document.getElementById('myFrame');
       
       oFrm.onload = oFrm.onreadystatechange = function() {
            
   	   if (this.readyState && this.readyState != 'complete') return;
           
   	   else {
               alert("1231");
   		//loadFrame(this);
           
   	   }}
       
    /* function loadFrame(obj){  
    	
   	     var url = obj.contentWindow.location.href;  

   	    	if(url.indexOf("Dept")==-1){  
   	   	    	
   	   	    	myFrame.window.getCheckDeptData();
   	   	    	
   	   	    	alert(result);
   	   	    	
   	   	    }else if(url.indexOf("Emp")!=-1){
   	   	    	
   	   	    	
   	   	    }   
   	}  */ 
       
    function loadTree(){
    	$.post("../accchecktype/queryAccCheckTypeByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	},"json");
    }
       
   </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 150px; height: 100%;">
          <div class="l-layout-header">核算类型</div>
	       <div class="l-layout-content" position="left">
		       		<div  style="width:200px; height:500px;overflow:auto;" >
		       		<ul class="ztree"  id="tree"></ul>
		       		</div>
	       </div>
          </div>
          <div class="l-layout-center" style="left: 155px; top: 0px;width: 550px;  height: 100%;"><div class="l-layout-header"></div>
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	              <iframe frameborder="0" id="myFrame" src="" width="100%;" height="90%;" ></iframe>
	           </div>
         </div>  
        </div>  

</body>
</html>
