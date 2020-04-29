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
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left',minWidth:90},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',minWidth:130},
                     { display: '管理方式', name: 'paper_way_type', align: 'left',
   					     render : function(rowdata, rowindex,value) {
     						  if(rowdata.paper_way_type == 1){
     							  return "单张管理"
     						  }else if(rowdata.paper_way_type == 2){
     							  return "多张管理"
     						  }
     	  				  }	
   	  				 },
                     { display: '领用方式', name: 'paper_use_type', align: 'left',
   					     render : function(rowdata, rowindex,value) {
    						  if(rowdata.paper_use_type == 1){
    							  return "一次领用"
    						  }else if(rowdata.paper_use_type == 2){
    							  return "二次领用"
    						  }
    	  				  }
   	  				 },
                     { display: '起始号码', name: 'begin_num', align: 'left'},
                     { display: '终止号码', name: 'end_num', align: 'left'},
                     { display: '票据号', name: 'paper_num', align: 'left',minWidth:130},
                     { display: '状态', name: 'state1', align: 'left',
   					     render : function(rowdata, rowindex,value) {
      						  if(rowdata.state1 == 1){
      							  return "新建"
      						  }else if(rowdata.state1 == 2){
      							  return "领用"
      						  }else if(rowdata.state1 == 3){
      							  return "作废"
      						  }
      	  				  }	},
                     { display: '领用人', name: 'sys_user_id1_name', align: 'left' },
                     { display: '领用日期', name: 'out_date1', align: 'left',width:100},
                     { display: '作废人', name: 'inva_user_id_name', align: 'left' },
                     { display: '作废日期', name: 'inva_date', align: 'left',width:100},
                     { display: '每本张数', name: 'paper_zlen', align: 'left'},
                     { display: '购置人', name: 'user_name', align: 'left'},
                     { display: '购置日期', name: 'opt_date', align: 'left',width:100},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'.do',delayLoad:true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
            
            
    	 $(':button').ligerButton({width:80});
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
	</div>
	<div id="maingrid"></div>

</body>
</html>
