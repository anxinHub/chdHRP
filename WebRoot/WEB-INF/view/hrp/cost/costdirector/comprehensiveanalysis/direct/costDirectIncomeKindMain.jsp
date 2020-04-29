<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合信息分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;//收益状况分析-全成本
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  query();
      });
    
       function query(){
       	 grid.options.parms=[];
      	 grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	 grid.options.parms.push({name:'year_month_begin',value:"${year_month_begin}"}); 
       	 grid.options.parms.push({name:'year_month_end',value:"${year_month_end}"});
      	 grid.options.parms.push({name:'dept_code',value:"${dept_code}"});
      	 grid.options.parms.push({name:'state',value:"${state}"}); 
 	       	//加载查询条件
 	     grid.loadData(grid.where);
        }

      function loadDict(){};
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
        			display: '收费分类',
        			name: 'charge_kind_name',
        			align: 'left',
	        	},{
        			display: '执行收入',
        			name: 'income_money',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.income_money, 2, 1);
					}
	        	}
        	],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDirectIncomeKind.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

			
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	   <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>