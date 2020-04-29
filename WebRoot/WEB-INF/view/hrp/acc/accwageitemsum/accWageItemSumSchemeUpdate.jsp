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
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var listbox2, listbox4;
    
    $(function ()
    	    {
    	    	 $("#layout1").ligerLayout({ leftWidth: 210,minLeftWidth:230,allowLeftResize: false}); 
    			
    	    	 loadTree();
    			
    			 $("#listbox1").ligerListBox({
    					//parms : para,
    					url : '../../sys/queryEmpKindDict.do?isCheck=false',
    					valueField : 'id',
    					textField : 'text',
    					isShowCheckBox : true,
    					isMultiSelect : true,
    					height : 350
    				}); 
    			 
    			 $("#listbox3").ligerListBox({
    					//parms : para,
    					url : '../accwagepay/queryAccWageBySchemeList.do?isCheck=false',
    					valueField : 'id',
    					textField : 'text',
    					isShowCheckBox : true,
    					isMultiSelect : true,
    					height : 350
    				});
    			
    			 listbox2=$("#listbox2").ligerListBox({
    	            isShowCheckBox: true,
    	            isMultiSelect: true,
    	            height: 350
    	        });
    			
    			 listbox4=$("#listbox4").ligerListBox({
    	            isShowCheckBox: true,
    	            isMultiSelect: true,
    	            height: 350
    	        });

    	    });
    	   


    $(function ()
    	       {
    	           $("#toptoolbar").ligerToolBar({ items: [
    	               {text: '添加', id:'add', icon:'add', click: itemclick1},
    	               { line:true }
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
    	   			beforeRename: zTreeBeforeRename,
    	   			onRemove: zTreeOnRemove,
    	   			onClick: zTreeOnCheck
    	   		},
    	   		edit: {
    	   			enable: true/* ,
    	   			showRemoveBtn: false,
    	   			showRenameBtn:false */
    	   			
    	   		}
    	      }; 
       
    	       function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
    	    	   
    	    	   $.post("../accwagepay/updateAccWageScheme.do?isCheck=false",{scheme_id: treeNode.id,scheme_name:newName,group_id:treeNode.group_id,hos_id:treeNode.hos_id,copy_code:treeNode.copy_code},function (responseData){
    	               if(responseData.error!=undefined && responseData.error!=""){
    	                 $.ligerDialog.error(responseData.error);
    	                 return;
    	             	}
    	                 if(responseData.state=="true"){
    	                 	tipDlg('修改成功');
    	             		loadTree();
    	             		}
    	             	},"json");
    	    	   
    	    	}
    	       function zTreeOnRemove(event, treeId, treeNode) {
    	    	   $.ligerDialog.confirm('确定删除?', function (yes){
    	               if(yes){   
    	                  $.post("deleteAccWageScheme.do?isCheck=false",{scheme_id: treeNode.id,group_id:treeNode.group_id,hos_id:treeNode.hos_id,copy_code:treeNode.copy_code},function (responseData){
    	                      if(responseData.error!=undefined && responseData.error!=""){
    	                        $.ligerDialog.error(responseData.error);
    	                        return;
    	                    	}
    	                        if(responseData.state=="true"){
    	                        	tipDlg('删除成功');
    	                    		loadTree();
    	                    		}
    	                    	},"json");
    	                 	}
    	        	});
    	    	}
       
    	       function zTreeOnCheck(event, treeId, treeNode){
    	    	   
    	    	   var data=tree.getSelectedNodes();

    	    	   var scheme_id = data[0].id;
    	    	   
    	    	   var para={
    	    			   
    	    		 scheme_id:scheme_id
    	    		 
    	    	   };
    	    	  
    	    	   
    			   listbox4.clear();
    	    	   
    	    	   listbox4.clearContent();
    	    	   
    	    	   $("#listbox4").ligerListBox({
    	    		
    	    		parms:para,
    	   			
    	   			url : '../accwagepay/queryAccSchemeWageItemList.do?isCheck=false',
    	   					
    	   			valueField : 'id',
    	   			
    	   			textField : 'text',
    	   			
    	            isShowCheckBox: true,
    	            
    	            isMultiSelect: true,
    	            
    	            height: 350
    	               
    	           });
    	    	   
    	       }
		
		
    	       function itemclick1(item){ 
    	           if(item.id)
    	           {
    	               switch (item.id)
    	               {
    	               	case "add":
    	             		$.ligerDialog.open({url: '../accwagepay/accWageSchemeAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccWageScheme(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	             		return;
    	               	case "delete":
    	               		var node = tree.getSelectedNodes();
    	            		if(node == ""){
    	            			$.ligerDialog.error('请先选择工资方案');
    	            			return;
    	            		}
    	            				
    	            		$.ligerDialog.confirm('确定删除?', function (yes){
    	                           if(yes){   
    	                              $.post("deleteAccWageScheme.do?isCheck=false",{scheme_id: node[0].id,group_id:node[0].group_id,hos_id:node[0].hos_id,copy_code:node[0].copy_code,check_type_name:node[0].name},function (responseData){
    	                                  if(responseData.error!=undefined && responseData.error!=""){
    		                                $.ligerDialog.error(responseData.error);
    		                                return;
    	                                	}
    	                                    if(responseData.state=="true"){
    	                                    	tipDlg('删除成功');
    	                                		loadTree();
    	                                		}
    	                                	},"json");
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
    	            			if(value.is_sys == 1){
    	            				$.ligerDialog.error('系统核算不能修改');
    	                			return;
    	            			}
    	            			$.ligerDialog.open({url: '../accchecktype/accCheckTypeUpdatePage.do?isCheck=false&check_type_id='+value.id+'&group_id='+value.group_id+'&hos_id='+value.hos_id+'&copy_code='+value.copy_code, 
    	            					height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCheckType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	            		});
    	               		return;
    	               }
    	           }
    	       }    
	
    	       function loadTree(){
    	       	$.post("../accwagepay/queryAccWageSchemeByMenu.do?isCheck=false",null,function (responseData){
    	       	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	       	},"json");
    	       }
       
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
    	       	moveAllToLeft();
    	           var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
    	           var selecteds = box1.getSelectedItems();
    	           if (!selecteds || !selecteds.length) return;
    	           /* box1.removeItems(selecteds); */
    	           box2.addItems(selecteds);
    	       }
    	       function moveAllToLeft()
    	       { 
    	           var box2 = liger.get("listbox2");
    	           var selecteds = box2.data;
    	           box2.removeItems(selecteds); 
    	       }
    	       function moveAllToRight()
    	       { 
    	       	moveAllToLeft();
    	           var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
    	           var selecteds = box1.data;
    	           if (!selecteds || !selecteds.length) return;
    	           box2.addItems(selecteds);
    	           //box1.removeItems(selecteds);
    	          
    	       }
    	       
    	       function moveToLeft1()
    	       {
    	           var  box2 = liger.get("listbox4");
    	           var selecteds = box2.getSelectedItems();
    	           if (!selecteds || !selecteds.length) return;
    	           box2.removeItems(selecteds);
    	           //box1.addItems(selecteds);
    	       }
    	       function moveToRight1()
    	       {
    	       	moveAllToLeft1();
    	           var box1 = liger.get("listbox3"), box2 = liger.get("listbox4");
    	           var selecteds = box1.getSelectedItems();
    	           if (!selecteds || !selecteds.length) return;
    	           /* box1.removeItems(selecteds); */
    	           box2.addItems(selecteds);
    	       }
    	       function moveAllToLeft1()
    	       { 
    	           var box2 = liger.get("listbox4");
    	           var selecteds = box2.data;
    	           if (!selecteds || !selecteds.length) return;
    	           //box1.addItems(selecteds);
    	           box2.removeItems(selecteds); 
    	       }
    	       function moveAllToRight1()
    	       { 
    	           var box1 = liger.get("listbox3"), box2 = liger.get("listbox4");
    	           var selecteds = box1.data;
    	           if (!selecteds || !selecteds.length) return;
    	           box2.addItems(selecteds);
    	           box1.removeItems(selecteds);
    	          
    	       }
    	       
    function save(){

		var box4 = liger.get("listbox4");
    	
    	box4.selectAll();
    	
		var data=tree.getSelectedNodes();
    	
		if(data.length>1){
    		
    		$.ligerDialog.error('请先选择单个方案进行操作');
    		
    		return;
    		
    	}else if(data.length<=0){
    		
    		$.ligerDialog.error('请先选择单个方案进行操作');
    		
    		return;
    		
    	}
    	
    	var scheme_id = data[0].id;
    	
    	var wageData = liger.get("listbox4").getValue();

    	if(wageData.indexOf("=")<1){
    		
    		$.ligerDialog.error('请先选择核算类型');
    		
			return;
    		
    	}
    	
    	$.post("../accwagepay/setAccWageItemSumSchemeRela.do?isCheck=false",{para:"",paraValue:wageData,scheme_id:scheme_id},function (responseData){
            
    		if(responseData.error!=undefined && responseData.error!=""){
            	
              $.ligerDialog.error(responseData.error);
              
              return;
              
          	}
              if(responseData.state=="true"){
            	  
              	tipDlg('设置成功');
              	
          		}
              
          	},"json");
    	
    }

       
   </script>
   <style type="text/css">
        .middle input {
            display: block;width:40px; margin-top:30px;
        }
    </style>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
   <div class="l-layout" id="layout1" style="height: 100%;" >
           
         
	       <div position="left" title="工资方案">
	        <div id="toptoolbar" ></div>
		       		<div  style="overflow:auto;" >
		       			<ul class="ztree"  id="tree"></ul>
		       		</div>
	       </div>
	       
          <div position="center"  id="rightCheckTypeName">
	          <div title="" class="l-layout-content" style="width:100%; height: 100%;" position="center">
		           
				     <div style="margin:4px;float:left;">
		           <div class="l-layout-header">工资项目</div>
				         <div id="listbox3"></div>  
				   </div>
				     <div style="margin:20px;float:left;" class="middle">
				         <input type="button" onclick="moveToLeft1()" value="上移" />
				          <input type="button" onclick="moveToRight1()" value="下移" />
				          <input type="button" onclick="moveToLeft1()" value="左移" />
				          <input type="button" onclick="moveToRight1()" value="右移" />
				          <input type="button" onclick="moveAllToRight1()" value="全选" />
				          <input type="button" onclick="moveAllToLeft1()" value="全删" />
				     </div>
				    <div style="margin:4px;float:left;">
				    <div class="l-layout-header">已选</div>
				        <div id="listbox4"></div> 
				    </div>
	           
	           </div>
         </div>  
    </div>  

</body>
</html>
