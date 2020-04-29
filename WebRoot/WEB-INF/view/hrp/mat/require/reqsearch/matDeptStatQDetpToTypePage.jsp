<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var store_id = null;
	var store_no;
	var store_name;
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	
		$("#yearB").ligerTextBox({width:80});
		$("#monthB").ligerTextBox({width:80});
		$("#yearE").ligerTextBox({width:80});
		$("#monthE").ligerTextBox({width:80});
		
        
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		
    	grid.options.parms.push({name : 'yearB',value : $("#yearB").val()}); 
    	grid.options.parms.push({name : 'monthB',value : $("#monthB").val() }); 
    	grid.options.parms.push({name : 'yearE',value : $("#yearE").val()}); 
    	grid.options.parms.push({name : 'monthE',value : $("#monthE").val() }); 
    	
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 { display: '科室编码', name: 'dept_code', align: 'left'},
						 { display: '科室名称', name: 'dept_name', align: 'left' },
						 { display: '合计', name: ' ', align: 'right'}
						 
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'.do',
		                 width: '100%', height: '100%', checkbox: false,rownumbers:false,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          { text: '查询', id:'query', click: query ,icon:'search' },
		                            { line:true }/* ,
		                            
		                            {text : '打印',id : 'print',click : print ,icon : 'print'}, 
		      						{line : true} */
		                   		]
		                 }
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function print(){
		
	}
	
	function loadDict() {
		var date = new Date();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
		
		autocomplete("#yearB","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("yearB").setValue(year);
        liger.get("yearB").setText(year);
        
        autocomplete("#monthB","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("monthB").setValue(month);
        liger.get("monthB").setText(month);
		
        autocomplete("#yearE","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("yearE").setValue(year);
        liger.get("yearE").setText(year);
        
        autocomplete("#monthE","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("monthE").setValue(month);
        liger.get("monthE").setText(month);
		
		
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制年月：</td>
			<td align="left" class="l-table-edit-td"> <input name="yearB" type="text" id="yearB" /></td>
			<td align="center" class="l-table-edit-td" style="width: 15px;">年</td>
			<td align="right" class="l-table-edit-td"><input name="monthB" type="text" id="monthB" /></td>
			<td align="center" class="l-table-edit-td" style="width: 15px;">至：</td>
			<td align="left" class="l-table-edit-td"> <input name="yearE" type="text" id="yearE" /></td>
			<td align="center" class="l-table-edit-td" style="width: 15px;">年</td>
			<td align="right" class="l-table-edit-td"><input name="monthE" type="text" id="monthE" /></td>
			
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
