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
        grid.options.parms.push({name:'opt_date',value:$("#opt_date").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	if(liger.get("dept_code").getValue() !=""){
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
    	}
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left',
   					     render : function(rowdata, rowindex,value) {
   						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
   							rowdata.hos_id   + "|" + 
   							rowdata.copy_code   + "|" + 
   							rowdata.pid  +"')>"+rowdata.type_code+"</a>";
   	  				  }	},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',width:160},
                     { display: '购置科室', name: 'dept_name', align: 'left'},
                     { display: '购置人', name: 'user_name', align: 'left'},
                     { display: '购置日期', name: 'opt_date', align: 'left'},
                     { display: '起始号码', name: 'begin_num', align: 'left'},
                     { display: '终止号码', name: 'end_num', align: 'left'},
                     { display: '数量', name: 'amount', align: 'left'},
                     { display: '费用', name: 'amoney', align: 'left',
 						 render:function(rowdata){
 							 return formatNumber(rowdata.amoney,2,1);
 						 }
                      },
                     { display: '备注', name: 'note', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
    	 autocomplete("#dept_code","../../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	 
        $(':button').ligerButton({width:80});
            
    	$("#opt_date").ligerTextBox({width:160});
    	

    	
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置日期：</td>
            <td align="left" class="l-table-edit-td"><input name="opt_date" class="Wdate" id="opt_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
	</div>
	<div id="maingrid"></div>

</body>
</html>
