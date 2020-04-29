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

	$(function() {
		loadHead(null);
		query();
	});
	
	 //查询
    function  query(){
   		  grid.options.parms=[];
   		  grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'index_code',value:'${index_code}'});
    	  grid.options.parms.push({name:'year',value:'${year}'}); 
    	  grid.options.parms.push({name:'formula_id',value:'${formula_id}'}); 
    	  
    	  grid.options.parms.push({name:'element_level',value:'${element_level}'}); 
    	  grid.options.parms.push({name:'element_type_code',value:'${element_type_code}'}); 
    	  
    	  if('${month}'){
    		  grid.options.parms.push({name:'month',value:'${month}'}); 
    	  }
    	  
    	  if('${dept_id}'){
    		  grid.options.parms.push({name:'dept_id',value:'${dept_id}'}); 
    	  }
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ 
		            {display : '序号',name : 'stack_seq_no',align : 'left'},
		            
		            {display : '计算项编码',name : 'code',align : 'left'},

					{display : '计算项',name : 'count_item',align : 'left'},

					{display : '值',name : 'count_value',align : 'right',
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1);
						}
					}

					],
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryCountProcess.do?isCheck=false',
					width : '100%',	height:'100%', checkbox : false,pageSize : 10,	rownumbers : false,
					selectRowButtonOnly : true,
					delayLoad:true ,
				});

		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style=" background-color:#BEBEBE;">
			<tr>	
				<td align="left" class="l-table-edit-td" width="40%"><font size="3">公式编码：${formula_id}</font></td>
				<td align="left" class="l-table-edit-td" width="60%"><font size="3">公式名称：${formula_name}</font></td>
			</tr>
			<tr>	
				<td align="left" class="l-table-edit-td" width="50%"><font size="3" >计算公式：${formula_ca}</font></td>
			</tr>
		</table>
	</div>

<div id="maingrid"></div>

</body>
</html>
