<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
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
		
		$("#tree").css("height", $(window).height()-100);
		
    	 $("#layout1").ligerLayout({ leftWidth: 210,minLeftWidth:230,allowLeftResize: false}); 
		
    	 loadTree();
    	
    });
   

  
       $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '添加', id:'add', icon:'add', click: itemclick1},
               { line:true },
               { text: '删除',id:'delete',icon:'delete', click: itemclick1 },
               { line:true },
               { text: '修改',id:'update',icon:'edit', click: itemclick1 }
           ]
           });
          
       });
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
   			beforeExpand: beforeExpand ,
   			onClick:groupDetail
   		}
      }; 

     function beforeExpand(treeId, treeNode) {
       	singlePath(treeNode);
     }

     function singlePath(currNode) {
       	var cLevel = currNode.level;
       	//这里假设id是唯一的
       	var cId = currNode.id;
       	
       	//此对象可以保存起来，没有必要每次查找
       	var treeObj = $.fn.zTree.getZTreeObj("tree");
       	//展开的所有节点，这是从父节点开始查找（也可以全文查找）
       	var expandedNodes = treeObj.getNodesByParam("open", true, currNode.getParentNode());

       	for(var i = expandedNodes.length - 1; i >= 0; i--){
       		var node = expandedNodes[i];
       		var level = node.level;
       		var id = node.id;
       		if (cId != id && level == cLevel) {
       			treeObj.expandNode(node, false);
       		}
       	}
    }
       function itemclick1(item){ 
           if(item.id)
           {
               switch (item.id)
               {
               	case "add":
             		$.ligerDialog.open({url: '../baseData/accCheckTypeAddPage.do', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
             		return;
               	case "delete":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择核算类型');
            			return;
            		}
            		//var ParamVo =[];
                    	if(node[0].is_sys == 1){
            				$.ligerDialog.error('系统核算不能删除');
                			return;
            			}else{
            				/*ParamVo.push(
            						this.id   +"@"+ 
            						this.group_id   +"@"+ 
            						this.hos_id   +"@"+ 
            						this.copy_code 
            						);*/
            				$.ligerDialog.confirm('确定删除?', function (yes){
                             	if(yes){   
                                    $.post("../baseData/deleteAccCheckType.do",{check_type_id: node[0].id,group_id:node[0].group_id,hos_id:node[0].hos_id,copy_code:node[0].copy_code,check_type_name:node[0].name},function (responseData){
                                    	if(responseData.error!=undefined && responseData.error!=""){
                                			$.ligerDialog.error(responseData.error);
                                			return;
                                		}
                                    	if(responseData.state=="true"){
                                    		tipDlg('删除成功');
                                    		//$.ligerDialog.success('删除成功');
                                			loadTree();
                                		}
                                	},"json");
                             	}
                    		 });
            			}
						
                  
               		return;
               	case "update":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择核算类型');
            			return;
            		}
            		$.each(node,function(index,value){
            			if(value.is_sys == 1){
            				$.ligerDialog.error('系统核算不能修改');
                			return;
            			}
            			if(value.id == '00'){
            				$.ligerDialog.error('系统核算不能修改');
                			return;
            			}
            			if(value.id == '01'){
            				$.ligerDialog.error('该项无法修改,请选择其子项修改');
                			return;
            			}
            			$.ligerDialog.open({url: '../baseData/accCheckTypeUpdatePage.do?check_type_id='+value.id+'&group_id='+value.group_id+'&hos_id='+value.hos_id+'&copy_code='+value.copy_code, 
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
    	   		$("#iframe").attr("src","accCheckItemMainPage.do?isCheck=false&check_type_id="+value.id+"&check_type_name="+value.name);
    	   	}else{
    	   		switch (value.name){
    	   			case "部门":
        	   			$("#iframe").attr("src","accCheckDeptPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
    	   			case "职工":
        	   			$("#iframe").attr("src","accCheckEmpPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
					case "项目":
        	   			$("#iframe").attr("src","accCheckProjPage.do?isCheck=false&check_type_id="+value.id);
    	   				return;
					case "库房":
						$("#iframe").attr("src","accCheckStorePage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "客户":
						$("#iframe").attr("src","accCheckCusPage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "供应商":
						$("#iframe").attr("src","accCheckSupPage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "资金来源":
						$("#iframe").attr("src","accCheckSourcePage.do?isCheck=false&check_type_id="+value.id);
						return;
					case "单位":
						$("#iframe").attr("src","accCheckHosInfoPage.do?isCheck=false&check_type_id="+value.id);
						return;
    	   		}
    	   		
    	   	}
    	   	
    	   });	
       }
	
    function loadTree(){
    	$.post("../baseData/queryAccCheckTypeByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	},"json");
    }
       
   </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
   <div class="l-layout" id="layout1" style="height: 100%;" >
           
         
	       <div position="left" title="核算类型">
	        <div id="toptoolbar" ></div>
		       		<div  style="overflow:auto;" >
		       			<ul class="ztree"  id="tree"></ul>
		       		</div>
	       </div>
          <div position="center" title="核算项" id="rightCheckTypeName">
	           <div title="" class="l-layout-content" style="width:100%; height: 100%;" position="center">
	              <iframe frameborder="0" id="iframe" name="qwert" src="" width="100%;" height="100%;"></iframe>
	           </div>
         </div>  
    </div>  

</body>
</html>
