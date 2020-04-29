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
    
    var acc_year;
    
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(acc_year){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'acc_year',value:acc_year});
		grid.options.parms.push({name:'copy_code',value:liger.get("copy_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '月份', name: 'acc_month', align: 'left'
					 },
                     { display: '起始日期', name: 'begin_date', align: 'left',render:function(rowdata,index,value){
                    	 return rowdata.begin_date.substr(0,10);
                     }
					 },
                     { display: '结束日期', name: 'end_date', align: 'left',render:function(rowdata,index,value){
                    	 return rowdata.end_date.substr(0,10);
                     }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccYearMonth.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function loadDict(){
        //字典下拉框
		autocomplete("#copy_code","../../sys/queryCopyCodeDict.do?isCheck=false","id","text",true,true);
		var manager=$("#copy_code").ligerComboBox({
            onSelected: function (newvalue)
            {
            	if(newvalue!=""){
            		loadTree(newvalue);	
            	}
            }
        });
     }   
    </script>
	<script type="text/javascript">
       $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '添加年度', id:'add', icon:'add', click: itemclick1},
               { line:true },
               { text: '删除年度',id:'delete',icon:'delete', click: itemclick1 },
               { line:true },
               { text: '修改期间',id:'update',icon:'edit', click: itemclick1 }
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
   			onClick:groupDetail
   		}
      }; 
       function itemclick1(item){ 
           if(item.id)
           {
               switch (item.id)
               {
               	case "add":
               		var copy_code = liger.get("copy_code").getValue();
               		if(copy_code==""){
               			$.ligerDialog.error('请先选择账套');
               			return;
               		}
               		
             		$.ligerDialog.open({url: '../accyearmonth/accYearMonthAddPage.do?isCheck=false&copy_code='+copy_code, height: 250,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
             		return;
               	case "delete":
               		var node = tree.getSelectedNodes();
            		if(node == ""){
            			$.ligerDialog.error('请先选择会计年度');
            			return;
            		}
            		 $.ligerDialog.confirm('确定删除?', function (yes){
                     	if(yes){
                     	
                     		$.post("../accyearmonth/deleteAccYearMonth.do",{acc_year: node[0].id,copy_code:liger.get("copy_code").getValue()},function (responseData){
                        		if(responseData.state=="true"){
                        			$.ligerDialog.success('删除成功');
                        			loadTree();
                        			var treenode = tree.getSelectedNodes();
                        			query(treenode[0].id);
                        		}
                        	},"json");
                     	}
            		 });	
               		return;
               	case "update":
               	//	var node = gridManager.getCheckedRows();
               	if(liger.get("copy_code").getValue()==""){
               		$.ligerDialog.error('请先选择账套');
               		return;
               	}
            	if(tree.getSelectedNodes()==""){
               		$.ligerDialog.error('请先选择会计年度');
               		return;
               	}
               	var node = tree.getSelectedNodes();
               	$.ligerDialog.open({url: '../accyearmonth/accYearMonthUpdatePage.do?isCheck=false&acc_year='+node[0].id, height: 350,width: 400, title:'修改会计期间',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
               		/* var begin_time = $("#begin_time").val();
               		var end_time = begin_time-1;
            		if(begin_time < 1){
            			$.ligerDialog.error('起始日期不能小于1');
            			return;s
            		} else if(end_time>28){
            			$.ligerDialog.error('结束日期不能大于28');
            			return;
            		}else{     			
            				 var checkIds =[];
                             checkIds.push(begin_time+";"+end_time+";"+node[0].id);
                    		ajaxJsonObjectByUrl("../accyearmonth/updateAccYearMonth.do",{checkIds:checkIds},function (responseData){
                        		if(responseData.state=="true"){
                        			groupDetail();
                        		}
                        	});
            		} */
               		return;
               }
           }
       }    
       function groupDetail(){
    	   
    	   var node = tree.getSelectedNodes();
    	   
    	   $.each(node,function(index,value){
    		   
    	   		query(value.id);
    	   		
    	   });	
    	   
       }
       function loadTree(){
    	   
       	$.post("queryAccYearMonthByMenu.do?isCheck=false",{copy_code:liger.get("copy_code").getValue()},function (responseData){
       	       
       		tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
       		
       		query(responseData.Rows[0].name);
       		
       	},"json");
       }
       
      
   </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套编码：</td>
            <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
     
    </table>


	<div id="toptoolbar" ></div>
   <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 100%;">
          <div class="l-layout-header">会计年度</div>
	       <div class="l-layout-content" position="left">
		       		<div class="ztree"  style="float: left " >
		       		<ul id="tree"></ul>
		       		</div>
	       </div>
          </div>
          <div class="l-layout-center" style="left: 205px; top: 0px; width: 975px; height: 100%;">
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	           <table cellpadding="0" cellspacing="0" class="l-table-edit" >
				        <!-- <tr>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">起始日期：</td>
				            <td align="left" class="l-table-edit-td"><input name="begin_time" type="text" id="begin_time" ltype="text" /></td>
				            <td align="left"></td>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束日期：</td>
				            <td align="left" class="l-table-edit-td"><input name="end_time" type="text" id="end_time" ltype="text" /></td>
				            <td align="left"></td>
				        </tr>  -->
				    </table>
	              <div id="maingrid"></div>
	           </div>
         </div>  
        </div>  

</body>
</html>
