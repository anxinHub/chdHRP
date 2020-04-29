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
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
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
    	grid.options.parms.push({name:'acc_time',value:$("#acc_time").val().replace(".","")});
    	grid.options.parms.push({name:'remark',value:$("#note").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '职工编码', name: 'emp_code', align: 'left'
					 },
                     { display: '职工名称', name: 'emp_name', align: 'left'
					 },
					 { display: '备注', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../sys/emp/queryEmpChangeRemark.do?isCheck=false',
                     width: '100%', height: '100%', rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}
                     
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
            //字典下拉框
    	 $("#note").ligerComboBox({
    		 data:[{id:1,text:'名称变更'},{id:0,text:'职工停用'},{id:2,text:'职工部门变更'},{id:3,text:'职工分类变更'},{id:4,text:'新增职工'}],
    		 valueField: 'id',
             textField: 'text',
 			 cancelable:true
    	 ,width:140});
    	 
    	 $("#acc_time").etDatepicker({
             view: "months",
             minView: "months",
             dateFormat: "yyyy.mm",
             defaultDate: '${wage_year_month}'
    		});
    }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:7px;">会&nbsp;&nbsp;计&nbsp;&nbsp;期&nbsp;间：</td>
			<td align="left" class="l-table-edit-td" ><input style="width: 160"  name="acc_time" type="text" id="acc_time"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更分类：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
      
    </table>

	<div id="maingrid"></div>

</body>
</html>
