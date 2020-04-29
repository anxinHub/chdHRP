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
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var bar_code;
    
    $(function ()
    {
    	selectPage();   
    	
    	//loadDict();
    	
    	loadHead(null);
    	
    	$("#layout1").ligerLayout({ leftWidth: 600});
    	
    });
    
    function  query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		grid.options.parms.push({name:'bar_code',value:bar_code}); 
   		
   		//加载查询条件
   		grid.loadData(grid.where);
   		
	 }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '业务类型', name: 'bus_type_name', align: 'left',width:80
					 },
                     { display: '单号', name: 'in_no', align: 'left',width:120
					 },
                     { display: '单据时间', name: 'in_date', align: 'left',width:100
					 },
                     { display: '仓库/科室', name: 'store_name', align: 'left',width:120
					 },
					 { display: '操作人', name: 'user_name', align: 'left',width:100
					 },
					 { display: '备注', name: 'note', align: 'left',width:100
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvTraceabilityBar_code.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                   
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
	function selectPage(code){
		
		$("#iframe").attr("src","matInvTraceabilityAddPage.do?isCheck=false");    
		
	}
	
   
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			
		</tr>
	</table>
     <div id="layout1">
     
     	<div position="left">
     		<iframe src="matInvTraceabilityAddPage.do?isCheck=false" id="iframe" frameborder="0" width="100%" height="95%"></iframe>
     	</div>
     	
     	<div position="center" ><div id="maingrid"></div></div>  
	</div>
	 
	
</body>
</html>
