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
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var state = "${state}";
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
  
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	if(state==1){
    		grid = $("#maingrid").ligerGrid({
				columns: [ 
                	{ display: '票据类型编码', name: 'type_code', align: 'left', width: '15%'},
   	  			 	{ display: '票据类型名称', name: 'type_name', align: 'left', width: '15%'},
                    { display: '购置人', name: 'user_name', align: 'right', width: '15%'},
                    { display: '购置时间', name: 'opt_date', align: 'right', width: '15%'},
                    { display: '票据号', name: 'paper_num', align: 'right', width: '15%'},
                    { display: '状态', name: 'state1', align: 'right', width: '15%',
   					    render : function(rowdata, rowindex,value) {
      						if(rowdata.state1 == 1){
      							return "购置"
      						}else if(rowdata.state1 == 2){
      						    return "领用生效"
      						}else if(rowdata.state1 == 3){
      							return "作废"
      						}
      	  				}	
                    }
				],
                dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperStockCollectDetail.do?isCheck=false&state1=&type_code='+"${type_code}"+'&end_date='+"${end_date}"+'',
                width: '100%', height: '100%',rownumbers:true,frozen:true,
                selectRowButtonOnly:true//heightDiff: -10,
   	         });
    	 }else if(state==2){
    		 grid = $("#maingrid").ligerGrid({
  	           columns: [ 
  	                     { display: '票据类型编码', name: 'type_code', align: 'left', width: '12%'},
  	   	  				 { display: '票据类型名称', name: 'type_name', align: 'left', width: '12%'},
  	                     { display: '购置人', name: 'user_name', align: 'right', width: '12%'},
  	                     { display: '购置时间', name: 'opt_date', align: 'right', width: '12%'},
  	                     { display: '票据号', name: 'paper_num', align: 'right', width: '12%'},
  	                     { display: '领用人', name: 'out_name', align: 'right', width: '12%'},
	                     { display: '领用时间', name: 'out_date1', align: 'right', width: '12%'},
	                     { display: '状态', name: 'state1', align: 'right', width: '12%',
	   					     render : function(rowdata, rowindex,value) {
	      						  if(rowdata.state1 == 1){
	      							  return "购置"
	      						  }else if(rowdata.state1 == 2){
	      							  return "领用生效"
	      						  }else if(rowdata.state1 == 3){
	      							  return "作废"
	      						  }
	      	  				  }},
  	                     ],
  	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperStockCollectDetail.do?isCheck=false&state1=2&type_code='+"${type_code}"+'&end_date='+"${end_date}"+'',
  	                     width: '100%', height: '100%',rownumbers:true,frozen:true,
  	                     selectRowButtonOnly:true//heightDiff: -10,
  	                   });
    	 }else if(state == 3){
    		 grid = $("#maingrid").ligerGrid({
  	           columns: [ 
  	                     { display: '票据类型编码', name: 'type_code', align: 'left', width: '12%'},
  	   	  				 { display: '票据类型名称', name: 'type_name', align: 'left', width: '12%'},
  	                     { display: '购置人', name: 'user_name', align: 'right', width: '12%'},
  	                     { display: '购置时间', name: 'opt_date', align: 'right', width: '12%'},
  	                     { display: '票据号', name: 'paper_num', align: 'right', width: '12%'},
  	                     { display: '作废人', name: 'inva_name', align: 'right', width: '12%'},
	                     { display: '作废时间', name: 'inva_date', align: 'right', width: '12%'},
	                     { display: '状态', name: 'state1', align: 'right', width: '12%',
	   					     render : function(rowdata, rowindex,value) {
	      						  if(rowdata.state1 == 1){
	      							  return "购置"
	      						  }else if(rowdata.state1 == 2){
	      							  return "领用生效"
	      						  }else if(rowdata.state1 == 3){
	      							  return "作废"
	      						  }
	      	  				  }},
  	                     ],
  	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperStockCollectDetail.do?isCheck=false&state1=3&type_code='+"${type_code}"+'&end_date='+"${end_date}"+'',
  	                     width: '100%', height: '100%',rownumbers:true,frozen:true,
  	                     selectRowButtonOnly:true//heightDiff: -10,
  	                   });
    	 }else if(state == 4){
    		 grid = $("#maingrid").ligerGrid({
  	           columns: [ 
  	                     { display: '票据类型编码', name: 'type_code', align: 'left', width: '12%'},
  	   	  				 { display: '票据类型名称', name: 'type_name', align: 'left', width: '12%'},
  	                     { display: '购置人', name: 'user_name', align: 'right', width: '12%'},
  	                     { display: '购置时间', name: 'opt_date', align: 'right', width: '12%'},
  	                     { display: '票据号', name: 'paper_num', align: 'right', width: '12%'},
	                     { display: '核销人', name: 'check_name', align: 'right', width: '12%'},
	                     { display: '核销时间', name: 'check_date', align: 'right', width: '12%'},
	                     { display: '状态', name: 'is_check', align: 'right', width: '12%',
	   					     render : function(rowdata, rowindex,value) {
	      						  if(rowdata.is_check == 1){
	      							  return "核销"
	      						  }else if(rowdata.state1 == 0){
	      							  return "未核销"
	      						  }
	      	  				  }},
  	                     ],
  	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperStockCollectDetail.do?isCheck=false&is_check=1&type_code='+"${type_code}"+'&end_date='+"${end_date}"+'',
  	                     width: '100%', height: '100%',rownumbers:true,frozen:true,
  	                     selectRowButtonOnly:true//heightDiff: -10,
  	                   });
    	 }
    	
    	

        gridManager = $("#maingrid").ligerGetGridManager();
        
        
        
    }
    
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
        $(':button').ligerButton({width:80});
            
    	$("#end_date").ligerTextBox({width:160});
    	
    	autodate("#end_date","yyyy-MM-dd");
    	
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    </table>
    <div style="border:1px">
	</div>
	<div id="maingrid"></div>

</body>
</html>
