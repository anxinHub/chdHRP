<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var table_name = "";
    var table_code = "";
    var is_group = "";
    var is_hos = "";
    var is_copy_code = "";
    $(function ()
    {
         loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	
         query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
       
		grid.options.parms.push({name:'table_name',value:$("#table_name").val()}); 
		
		grid.options.parms.push({name:'mod_code',value:liger.get("mod_code").getValue()}); 
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					    display: '系统模块', name: 'mod_name', align: 'left',isSort:false
				      },{
					    display: '表名', name: 'table_code', align: 'left',isSort:false
				      },{
					    display: '说明', name: 'table_name', align: 'left',isSort:false
				      },{
					    display: '是否集团', name: 'is_group_name', align: 'left',isSort:false
				      },{
					    display: '是否医院', name: 'is_hos_name', align: 'left',isSort:false 
				      },{
					    display: '是否账套', name: 'is_copy_name', align: 'left',isSort:false 
				      }],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHrpTable.do',
			width: '100%', height: '100%', checkbox: false,rownumbers:true,
			delayLoad: true,
			selectRowButtonOnly:true,
			isSingleCheck:true
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
    	$("#table_name").ligerTextBox({
			width : 180
		});
    	$("#query").ligerButton({click: query, width:90});
    	
        
    	autocompleteAsync("#mod_code","../../sys/queryModDict.do?isCheck=false&","id","text",false,true,'',false,'',180);
    } 
    
    function this_close(){
		frameElement.dialog.close();
	}
    
    function selectedTable(){
    	var gridRow = gridManager.getSelectedRow();
    	if(gridRow == null || gridRow == ""){
    		return false;
    	}
    	table_name = gridRow.table_name;
    	table_code = gridRow.table_code;
    	is_group = gridRow.is_group;
    	is_hos = gridRow.is_hos;
    	is_copy_code = gridRow.is_copy_code;
    	return true;
    }

	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">系统模块：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code"  /></td>
            <td align="left"></td>
			<td align="left"  class="l-table-edit-td" style="padding-left: 20px;">表名：</td>
			<td align="left"  class="l-table-edit-td"><input name="table_name"
				type="text" id="table_name" 
				 /></td>
			<td align="left"  class="l-table-edit-td"><button id ="query"><b>查询</b></button></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
