<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var wageCode = "${wageCode}";
	
	$(function(){
		$("#layout1").ligerLayout({ topHeight:80,minLeftWidth:230,allowLeftResize: false}); 
		loadDict();
		initGrid();
	});
	
	function loadDict(){
		//字典下拉框
		autocomplete("#dept_kind","../../sys/queryDeptKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
		autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
		autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
		autocomplete("#emp_code","../queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
		autocomplete("#station_code","../../sys/queryStationDict.do?isCheck=false","id","text",true,true);
		autocomplete("#duty_code","../../sys/queryDutyDict.do?isCheck=false","id","text",true,true);
	}
	
	// 初始化grid
	function initGrid(){
		grid = $("#maingrid").ligerGrid({
			columns:[
				{ display: '职工编码', name: 'emp_code', align: 'left', width:'30%' },
				{ display: '职工姓名', name: 'emp_name', align: 'left', width:'30%' },
				{ display: '部门名称', name: 'dept_name', align: 'left', width:'30%' }
			],
			width: '100%',
			height: '100%',
			dataAction: 'server',
			dataType: 'server', 
			checkbox: true,
			usePager: true,
			rownumbers: true,
			url: 'queryAccWageEmpNotBind.do?isCheck=false&wage_code=' + wageCode,
			isSaveColumn: false,
			toolbar: {
				items: [
					{ text: '查询', id: 'search', click: query, icon: 'search' },
					{ line: true }
				]
            } 
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	// 获取选择行
	function f_select(){
		var rowData = grid.getCheckedRows();
		return rowData;
	}
	
	// 查询
	function query(){
		grid.options.parms = [];
		grid.options.newPage=1;
   		
		grid.options.parms.push({name: 'emp_kind', value: liger.get("emp_kind").getValue()}); 
    	grid.options.parms.push({name: 'dept_id', value: liger.get("dept_code").getValue().split(".")[0]}); 
    	grid.options.parms.push({name: 'emp_id', value: liger.get("emp_code").getValue().split(".")[0]});
    	grid.options.parms.push({name: 'dept_kind', value: liger.get("dept_kind").getValue()});
    	grid.options.parms.push({name: 'station_code', value: liger.get("station_code").getValue()});
    	grid.options.parms.push({name: 'duty_code', value: liger.get("duty_code").getValue()});

		grid.loadData(grid.where);
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;" >
		<div position="top" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 10px">
		   	 	<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
		            <td align="left" class="l-table-edit-td" ><input name="dept_kind" type="text" id="dept_kind" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
		            <td align="left" class="l-table-edit-td" ><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
		            <td align="left" class="l-table-edit-td" ><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:18}" /></td>
		        </tr>
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位名称：</td>
		            <td align="left" class="l-table-edit-td" ><input name="station_code" type="text" id="station_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务名称：</td>
		            <td align="left" class="l-table-edit-td" ><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
		            <td align="left" class="l-table-edit-td" ><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
		        </tr>
			</table>
		</div>
	     
		<div position="center">
			<div id="maingrid" ></div>
		</div>  
	</div>  
</body>
</html>