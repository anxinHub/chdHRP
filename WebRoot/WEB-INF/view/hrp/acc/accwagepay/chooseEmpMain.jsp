<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
	var grid;
	
	var gridManager = null;
	
	var userUpdateStr;
     
     $(function (){
     	
    	 $("#layout1").ligerLayout({ topHeight:80,minLeftWidth:230,allowLeftResize: false}); 
    	 
    	 loadHead();
    	 
    	 loadDict(null);
    	 
     });  
     
   	//查询
    function  query(){
   		grid.options.parms=[];
   		
   		grid.options.newPage=1;
   		
		grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()}); 
    	
    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[0]}); 
    	
    	grid.options.parms.push({name:'emp_code',value:liger.get("emp_code").getValue().split(".")[0]});
    	
    	grid.options.parms.push({name:'dept_kind',value:liger.get("dept_kind").getValue()});
    	
    	grid.options.parms.push({name:'station_code',value:liger.get("station_code").getValue()});
    	
    	grid.options.parms.push({name:'duty_code',value:liger.get("duty_code").getValue()});
    	
     	//加载查询条件
     	grid.loadData(grid.where);
      
     }
     
	function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
            	{ display: '职工id', name: 'emp_id', hide: 'emp_id' },
                { display: '职工编码', name: 'emp_code', align: 'left' },
                { display: '职工名称', name: 'emp_name', align: 'left' },
                { display: '科室名称', name: 'dept_name', align: 'left' }
			],
            dataAction: 'server',
            dataType: 'server',
            usePager: true,
            url: 'queryAccEmpTetail.do?isCheck=false&wage_code=' + '${wage_code}' + '&scheme_id=' + '${scheme_id}' + '&is_gzt=' + '${is_gzt}'+ "&scheme_type_code=" + '${scheme_type_code}'   ,
            width: '100%', 
            height: '100%', 
            checkbox: true,
            rownumbers: true,
            selectRowButtonOnly: true,
            toolbar: {
				items: [
					{ text: '查询', id: 'search', click: query, icon: 'search' },
					{ line: true },
					{ text: '确认', id: 'add', click: btn_add, icon: 'add' },
					{ line: true }
				]
            } 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	// 确认选择
	function btn_add() { 
		var data = grid.getCheckedRows(); 
		if(data.length == 0){
			$.ligerDialog.warn('请选择行！');
			return; 
		}
         
        var ParamVo=[];
     	$(data).each(function (){
     		ParamVo.push(
	 			//表的主键
	 			this.emp_id+"@"+'${scheme_id}'
 			)
		});
     	
     	ajaxJsonObjectByUrl("addBatchAccWageSchemeKind.do?isCheck=false", {ParamVo : ParamVo.toString()}, function (responseData){
    		if(responseData.state == "true"){
    			parent.loadHead();
    		} 
    	});
	}
     
     function loadDict(){
         //字典下拉框
 		 autocomplete("#dept_kind","../../sys/queryDeptKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
 	     autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
 		 autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
 	     autocomplete("#emp_code","../queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
 	     autocomplete("#station_code","../../sys/queryStationDict.do?isCheck=false","id","text",true,true);
 	     autocomplete("#duty_code","../../sys/queryDutyDict.do?isCheck=false","id","text",true,true);
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
	     
		<div position="center"  title="  ">
			<div id="maingrid" ></div>
		</div>  
	</div>  
</body>
</html>
