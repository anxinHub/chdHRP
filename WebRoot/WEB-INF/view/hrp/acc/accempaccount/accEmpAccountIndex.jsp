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
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	$("#emp_code").ligerTextBox({width:160});
    	$("#emp_name").ligerTextBox({width:160});
    	autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	
    });
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'emp_code',value:$("#emp_code").val()}); 
        
    	grid.options.parms.push({name:'emp_name',value:$("#emp_name").val()}); 
    	
		if(liger.get("dept_code").getValue()!= "" ){
    		
    		grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]});
    		
        	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]});
    		
    	}

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '职工编码', name: 'emp_code', align: 'left'
					 },
                     { display: '职工姓名', name: 'emp_name', align: 'left'
					 },
					 { display: '身份证号', name: 'id_number', align: 'left'
					 },
					 { display: '部门名称', name: 'dept_name', align: 'left'
					 },
					 { display: '操作', name: 'note', align: 'left',render:function(rowdata,rowindex,
								value){
							return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 40px;' ligeruiid='Button1004'" 
							+"onclick='javascript:addAccEmp("+rowindex+")';>"
              				+"<span>职工账号</span></div>";
						 //return "<input class='liger-button' type='button' value='职工账号' onClick='javascript:addAccEmp("+rowindex+")'/>";
					 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccEmpAccountIndex.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
       
    }

    function addAccEmp(obj){
    	
    	var data = gridManager.getRow(obj);
    	
    	var parm = "emp_id="+
    	data.emp_id   +"&emp_code="+ 
    	data.emp_code   +"&group_id="+ 
    	data.group_id   +"&hos_id="+ 
    	data.hos_id ; 
    	
    	$.ligerDialog.open({ url : 'accEmpAccountUpdateIndex.do?isCheck=false&'+ parm,data:{}, height: 400,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工编码：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>
