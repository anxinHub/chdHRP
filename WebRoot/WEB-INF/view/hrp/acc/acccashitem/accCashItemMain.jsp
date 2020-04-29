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
    
    var isStop=0;
    
    $(function ()
    {
		loadDict();
		loadTree();
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(cash_type_id,isStop){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
	        //根据表字段进行添加查询条件
			grid.options.parms.push({name:'cash_type_id',value:cash_type_id}); 
	        
			grid.options.parms.push({name:'is_stop',value:isStop}); 
	    	//加载查询条件
	    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'cash_item_code', align: 'left',width:100,
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.cash_item_id   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code  +"')>"+rowdata.cash_item_code+"</a>";
							}
					 },
                     { display: '项目名称', name: 'cash_item_name', align: 'left'
					 },
                     { display: '方向', name: 'cash_dire', align: 'left',width:100,render:function(rowdata){
                    	 if(rowdata.cash_dire==0){
                    		 return "流入";
                    	 }
                    	 return "流出";
                     }
					 },
					 { display: '是否停用', name: 'is_stop', align: 'left',width:100,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccCashItem.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.cash_item_id   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择现金流量类别');
            			return;
            		}
            		$.each(node,function(index,value){
              			$.ligerDialog.open({url: 'accCashItemAddPage.do?cash_type_id='+value.id, height: 350,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCashItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
            		});
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.cash_item_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccCashItem.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			var node = tree.getSelectedNodes();
                            			 if($("#is_have").is(':checked')){
                                  		   
                                  		   isStop='';
                                  		   
	                                  	   }else{
	                                  		   
	                                  		   isStop=0;
	                                  		   
	                                  	   }
                            	    	   $.each(node,function(index,value){
                            	    	   		query(value.id,isStop);
                            	    	   });
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "cash_item_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3]; 
		
    	$.ligerDialog.open({ url : 'accCashItemUpdatePage.do?isCheck=false&' + parm,data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCashItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }   
    </script>
	<script type="text/javascript">
	var isAllCheck;
	
       $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '添加', id:'add', icon:'add', click: itemclick1},
               { line:true },
               { text: '删除',id:'delete',icon:'delete', click: itemclick1 },
               { line:true },
               { text: '修改',id:'update',icon:'edit', click: itemclick1 },
               { line:true },
               { text: '包含停用<input type="checkbox" id="is_have" '+isAllCheck+'/>', id:'is_have',icon:'' }
           ]
           });
           
           $("#is_have").change(function() {
        	   
        	   if($("#is_have").is(':checked')){
        		   
        		   isStop='';
        		   
        	   }else{
        		   
        		   isStop=0;
        		   
        	   }
        	   
        	   $.post("../acccashtype/queryAccCashTypeByMenu.do?isCheck=false&is_stop="+isStop,null,function (responseData){
           	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
           	       
           		},"json");
        	   
        	   var node = tree.getSelectedNodes();
        	   
        	   grid.options.parms=[];
       		
       		   grid.options.newPage=1;
        	   
        	   if(node!= null&&node!= ''){
        		   
        		   $.each(node,function(index,value){
        			   grid.options.parms.push({name:'cash_type_id',value:value.id}); 
   	    	   	  });
        		  
        	   }else{
        		   
        		   grid.options.parms.push({name:'cash_type_id',value:'0'}); 
        	   }

        	  if(isStop==0){

        		  grid.options.parms.push({name:'is_stop',value:isStop}); 
        		  
        	  }else{
        		  
        		  grid.options.parms.push({name:'is_stop',value:''}); 
        		  
        	  }
	           	//加载查询条件
	           grid.loadData(grid.where);
   		    
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
   			onClick:groupDetail
   		}
      }; 
       function itemclick1(item){ 
           if(item.id)
           {
               switch (item.id)
               {
               	case "add":
             		$.ligerDialog.open({url: '../acccashtype/accCashTypeAddPage.do', height: 250,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCashType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
             		return;
               	case "delete":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择现金流量类别');
            			return;
            		}
            		 $.ligerDialog.confirm('确定删除?', function (yes){
                     	if(yes){
                     		var ParamVo =[];
                            $(node).each(function (){					
    							ParamVo.push(
    							this.id   +"@"+ 
    							this.group_id   +"@"+ 
    							this.hos_id   +"@"+ 
    							this.copy_code 
    							);
                            });
                            ajaxJsonObjectByUrl("../acccashtype/deleteAccCashType.do",{ParamVo : ParamVo},function (responseData){
                        		if(responseData.state=="true"){
                        			$.ligerDialog.success('删除成功');
                        			loadTree();
                        		}
                        	},"json");
                     	}
            		 });	
               		return;
               	case "update":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择现金流量类别');
            			return;
            		}
            		$.each(node,function(index,value){
            			$.ligerDialog.open({url: '../acccashtype/accCashTypeUpdatePage.do?cash_type_id='+value.id+'&group_id='+value.group_id+'&hos_id='+value.hos_id+'&copy_code='+value.copy_code+'', 
            					height: 250,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCashType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
            		});
               		return;
                case "extend":
                	$.ligerDialog.open({url: 'accCashItemExtendPage.do', height: 240,width: 500, title:'继承',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	return;
               }
           }
       }    
       function groupDetail(){
    	   if($("#is_have").is(':checked')){
      		   
      		   isStop='';
      		   
          }else{
          		   
          		isStop=0;
          		   
          }
    	   var node = tree.getSelectedNodes();
    	   $.each(node,function(index,value){
    	   		query(value.id,isStop);
    	   });	
       }
       function loadTree(){
       	$.post("../acccashtype/queryAccCashTypeByMenu.do?is_stop="+isStop,null,function (responseData){
       	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
       	       
       	},"json");
       }
   </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 220px; height: 100%;">
          <div class="l-layout-header">现金流量类别</div>
	      <div class="l-layout-content" position="left">
		       		<div class="ztree"  style="float: left " >
		       		<ul id="tree"></ul>
		       		</div>
	      </div>
          </div>
          <div class="l-layout-center" style="left: 220px; top: 0px; width: 975px; height: 100%;"><div class="l-layout-header">现金流量项目</div>
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	              <div id="maingrid"></div>
	           </div>
         </div>  
        </div>  

</body>
</html>
