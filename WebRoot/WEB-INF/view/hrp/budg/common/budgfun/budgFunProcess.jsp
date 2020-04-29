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
        grid.options.parms.push({name:'fun_code',value:'${fun_code}'}); 
    	  grid.options.parms.push({name:'type_code',value:'${type_code}'});
    	  
    	  grid.options.parms.push({name:'index_code',value:'${index_code}'});
    	  
    	  grid.options.parms.push({name:'index_type_code',value:'${index_type_code}'});
    	  
    	  if('${year}'){
    		  grid.options.parms.push({name:'year',value:'${year}'}); 
    	  }
    	  
    	  if('${budg_year}'){
    		  grid.options.parms.push({name:'budg_year',value:'${budg_year}'}); 
    	  }
    	  
    	  if('${budg_level}'){
    		  grid.options.parms.push({name:'budg_level',value:'${budg_level}'}); 
    	  }
    	  
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
		            
		            {display : '函数编码',name : 'fun_code',align : 'left',width:150},

					{display : '函数名称',name : 'fun_name',align : 'left',width:200},
					{display : '函数取值',name : 'count_value',align : 'right',width:200,
						render:function(rowdata,rowindex,value){
							return formatNumber(value,2,1);
						}
					},
					{display : '取值函数中文',name : 'fun_method_chs',align : 'left'}

					],
					dataAction : 'server',dataType : 'server',usePager : false,url : 'queryFunProcess.do?isCheck=false',
					width : '100%',	height:'100%', checkbox : false,pageSize : 10,	rownumbers : false,
					selectRowButtonOnly : true,
					delayLoad:true ,
				});

		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="height: 50px ; background-color:#BEBEBE;text-align:center;padding-top:25px">
		<table class="l-table-edit" width="100%" style="margin:auto;">
			<tr>	
				<td align="left"><font size="3" >函数类型：${type_name}</font></td>
			</tr>
		</table>
	</div>

<div id="maingrid"></div>

</body>
</html>
