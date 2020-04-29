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
    
    var treeData;
    
    $(function ()
    {
    	loadTree();
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '系统编码', name: 'mod_code', align: 'left'
					 },
                     { display: '系统名称', name: 'mod_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager :false,url:'../mod/querySysModList.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:false,isChecked: f_isChecked,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
    					{ text: '保存', id:'add', click: itemclick, icon:'add' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function f_isChecked(rowdata)
    {
    	var flag = false;
    	if(treeData != null && treeData != ""){
    		 $.each(treeData.Rows,function(i,v){
    				if(rowdata.mod_code == v.mod_code){
    					flag = true;
    				}
    	    	});
    	}else{
    		return flag;
    	}
    	return flag;
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	var node = tree.getSelectedNodes();
                	var rows = gridManager.getCheckedRows();
                	if(rows == ""){
                		$.ligerDialog.error('请选择系统');
                		return;
                	}
                	if(node == ""){
                		$.ligerDialog.error('请选择医院');
                		return;
                	}
                	var ParamVo = [];
                	var hos_id=node[0].id;
        			var group_id=node[0].group_id;
        		
					$.each(rows,function(index,value){
            			var mod_code = this.mod_code;
            			ParamVo.push(hos_id+"@"+mod_code);
            		});
					
					ajaxJsonObjectByUrl("addPerm.do",{ParamVo:ParamVo,'group_id':group_id,'hos_id':hos_id},function(responseData){
        				
        			});
              		return;
                case "delete":
                	var node = tree.getSelectedNodes();
                	var rows = gridManager.getCheckedRows();
                	if(rows == ""){
                		$.ligerDialog.error('请选择系统');
                		return;
                	}
                	if(node == ""){
                		$.ligerDialog.error('请选择医院');
                		return;
                	}
                	var hos_id;
        			var group_id;
        			$.each(node,function(i,v){
        				hos_id = v.id;
        				group_id = v.group_id;
            		});
					ajaxJsonObjectByUrl("deletePerm.do",{'group_id':group_id,'hos_id':hos_id},function(responseData){
						query();
        			});
                	return;
            }   
        }
        
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
			onClick:groupDetail
		}
   }; 
    function loadTree(){
    	ajaxJsonObjectByUrl("queryInfoDictByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    function groupDetail(){
		var node = tree.getSelectedNodes();
		ajaxJsonObjectByUrl("queryAllPerm.do",{hos_id:node[0].id,group_id:node[0].group_id},function (responseData){
			treeData = responseData;
        	$.each(grid.records,function(i,obj){
        		grid.unselect(obj);
        		if(f_isChecked(obj)){
        			grid.select(obj);
        		}
        	});
    	});
	}

	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 100%;">
          <div class="l-layout-header"></div>
	       <div class="l-layout-content" position="left">
		       		<div class="ztree"  style="float: left " >
		       		<ul id="tree"></ul>
		       		</div>
	       </div>
          </div>
          <div class="l-layout-center" style="left: 205px; top: 0px; width: 975px; height: 100%;"><div class="l-layout-header">业务系统</div>
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	              <div id="maingrid"></div>
	           </div>
         </div>  
        </div>  
</body>
</html>
