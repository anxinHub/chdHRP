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
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>

<script type="text/javascript">
    var grid;
    var gridManager = null;
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ leftWidth: 150}); 
    	templateBar();
    	loadHead(null);	//加载数据
    	$("#is_stop").ligerComboBox({
             width : 100,
             cancelable: true,
             data:[{ id: '1',text: '激活' },{ id: '2',text: '挂起' }]
        });
    	loadFlowTree();
    });
   
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
					{ display: '部署ID', name: 'deployment_id_', align: 'left',width:80
					}, 
					{ display: '流程分类', name: 'category_', align: 'left',width:120
					 },
                     { display: '流程名称', name: 'name_', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+rowdata.deployment_id_+"')>"+rowdata.name_+"</a>";
						}
					 },
					 { display: '关联业务', name: 'template_business', align: 'left'
					 },
                     { display: '状态', name: 'suspension_state_', align: 'center',width:50,
						 render : function(rowdata, rowindex,value) {
								if(value == 1){
									return "<font style='font-weight:bold;color:green;'>激活</font>";
								}else{
									return "<font style='font-weight:bold;color:red;'>挂起</font>";
								}
							}
					 },
					 { display: '版本号', name: 'version_', align: 'center',width:50,
						 render : function(rowdata, rowindex,value) {
							 return "<a href=javascript:openVersion('"+rowdata.key_+"','"+rowdata.version_+"')>"+value+"</a>";
							}
					 },
                     { display: '部署人', name: 'user_name', align: 'left',width:80
					 },
					 { display: '部署时间', name: 'deploy_time_', align: 'left'
					 },
					 { display: '操作', align: 'center',isAllowHide: false,
						 render: function (row)
		                 {
							 	var run="<a href=\"#\" onclick=\"onRun('" + row.key_ + "');\">测试流程</a>";
							 	var image="<a href=\"#\" onclick=\"onImage('" + row.deployment_id_ + "','"+row.dgrm_resource_name_+"');\">流程图</a>";
		                        return run+"&nbsp;&nbsp;"+image;
		                  }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryFlowActReProcdef.do',
                     width: '100%', height: $(window).height()-80, checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    //查询
    var nodeSel=null;
    function  query(flag){
    	grid.options.parms=[];
        //根据表字段进行添加查询条件
        if($("#template_name").val()==""){
    		grid.options.parms.push({name:'name_',value:$("#template_name").val()}); 
        }else{
        	grid.options.parms.push({name:'name_',value:'%'+$("#template_name").val()+'%'});
        }
    	grid.options.parms.push({name:'suspension_state_',value:liger.get("is_stop").getValue()});
    	
    	nodeSel = tree.getSelectedNodes();
		if(flag==1 || nodeSel == ""){
			nodeSel=null;
			 grid.options.parms.push({name:'category_',value:''});
		}else{
			$.each(nodeSel,function(index,value){
				 grid.options.parms.push({name:'category_',value:value.name});
			});
		}

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function openUpdate(deployment_id_){
    	
    	$.ligerDialog.open({ url : 'flowDesignerPage.do?isCheck=false&deployment_id_='+deployment_id_, height: $(window).height()+38,width: $(window).width()+10, title:'流程设计',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });

    }
    
  
 	 //流程分类
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
					onClick:query
				}
	}; 
 	//加载流程类别
    function loadFlowTree(){
 	   ajaxJsonObjectByUrl("queryFlowCategory.do?isCheck=false",{category_:"分类"},function (responseData){
 	    	if(responseData!=null){
 	    		tree=$.fn.zTree.init($("#tree1"), setting, responseData.Rows);
 	    		
 	    		if(nodeSel!=null){
 	    			var zTree = $.fn.zTree.getZTreeObj("tree1");//获取ztree对象  
 	    			
 	    			$.each(nodeSel,function(index,value){
 	    				var node = zTree.getNodeByParam('id', value.id);
 	    				zTree.selectNode(node);//选择节点 
 	    				zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用事件
 	  				});
 	    		}
 	    		
 		     }
 	    });
    }
    
	//流程模板    
    function templateBar(){
   	 $("#tempToolBar").ligerToolBar({ items: [
   	         {text: '流程设计', id:'add', icon:'add', click: function(){
   	        	 $.ligerDialog.open({
                     title : '流程设计',
                     url: 'flowDesignerPage.do?isCheck=false',
                     width: $(window).width()+10,
                     height: $(window).height()+38
                 });
   	         }},
   	      	{text: '导入流程', id:'import', icon:'up', click: function(){
	        	 $.ligerDialog.open({
                  title : '导入流程',
                  url: 'flowImportPage.do',
                  width: 500,height: 400,
                  buttons: [ { text: '选择文件',onclick: function (item, dialog) { dialog.frame.selectFiles(); },cls:'l-dialog-btn-highlight' },{ text: '导入',onclick: function (item, dialog) { dialog.frame.importFlow(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
              });
	         }},
   	         { line:true },
   	         { text: '删除',id:'delete',icon:'delete', click: function(){
   	        	 var data = gridManager.getCheckedRows();
                 if (data.length == 0){
                 	$.ligerDialog.error('请选择行');
                 }else{
                     $.ligerDialog.confirm('确定删除?', function (yes){
                     	if(yes){
                     		var paramVo =[];
                            $(data).each(function (){					
                            	paramVo.push(this.deployment_id_);
                            });
                         	ajaxJsonObjectByUrl("flowDeploymentDelete.do",{paramVo : paramVo},function (responseData){
                         		if(responseData.state=="ok"){
                         			loadFlowTree();
                         			if(nodeSel==null){
                         				query(1);
                         			}
                         		}
                         	});
                     	}
                     }); 
                 }
   	         } },
   	      	 { line:true },
   	     	 { text: '设置分类',id:'settings',icon:'settings', click: function(){
	        	 
	         } },
	         { line:true },
   	      	 { text: '激活',id:'logout',icon:'flowstart', click: function(){
	        	 
	         } },
	         { line:true },
	         { text: '挂起',id:'refresh',icon:'flowstop', click: function(){
	        	 
	         } },
	         { line:true },
   	         { text: '查询全部',id:'search',icon:'search', click: function(){
   	        	query(1);
   	         } }
   	         ]
   	 });
   }
	
	function onRun(key_){
		 ajaxJsonObjectByUrl("runFlowDesignerTest.do?isCheck=false",{"key_":key_},function(responseData){
	           
	     });
	}
	
	//查看流程图
	function onImage(deployment_id_,dgrm_resource_name_){
		$.ligerDialog.open({
            title : '流程图',
            url: 'flowViewImagePage.do?deploymentId='+deployment_id_,
            width: $(window).width()+10,
            height: $(window).height(),
            buttons: [{ text: '打印',onclick: function (item, dialog) {
	         		var LODOP=getLodop();
	         		LODOP.PRINT_INIT("流程图打印");
	         		LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,"流程图打印.开始打印");
	         		LODOP.SET_PRINT_PAGESIZE(0,0,0,"A4");//设定纸张大小 
	         		LODOP.ADD_PRINT_IMAGE(1,1,'100%','100%',dialog.frame.imagePrint.innerHTML);
	         		LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前
	         		LODOP.PREVIEW();
           	 
            	},cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
		 });
	
	}
	
	function openVersion(key_,version_){
		$.ligerDialog.open({
            title : '历史流程',
            url: 'queryFlowActReProcdefVersionPage.do?key_='+key_+'&version_='+version_,
            width: $(window).width()+10,
            height: $(window).height()+35
		 });
	}
	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

     <div id="layout1" style="height: 100%;">
           
       
	     <div position="left" title="流程分类">
		    <div class="ztree"  style="float: left " >
		       		<ul id="tree1"></ul>
		    </div>
	    </div>
	    <div position="center" title="流程模板">
	    	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">流程名称：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" /></td>
			     		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
		                <td align="left" class="l-table-edit-td">
		                <input name="is_stop" type="text" id="is_stop" ltype="text"  validate="{required:false}" />
		              		<!--select id="is_stop" name="is_stop" style="width: 135px;">
		              			  <option value=""></option>
					              <option value="1">激活</option>
					              <option value="2">挂起</option>
		                	</select-->
		                </td>
			        </tr> 
			    </table>
	    
	    	<div class="l-layout-header" id="tempToolBar"></div>
	        <div class="l-layout-center" style="left:0px; top: 0px; width: 100%; height: 100%;" id="maingrid">
		          
	        </div>  
       	</div>
	</div>
</body>
</html>
