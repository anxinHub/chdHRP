<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成本</title>
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
      	 grid.options.parms.push({name:'nature_id',value:"${nature_id}"}); 
 	       	//加载查询条件
 	     grid.loadData(grid.where);
        }

      function loadDict(){

      	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true,"",false,"${dept_id}.${dept_no}.${dept_code}");
    	  $("#year_month_begin").ligerTextBox({width:120, disabled:true});
          $("#year_month_end").ligerTextBox({width:120, disabled:true});
          $("#dept_code").ligerComboBox({disabled:true});
          
       };
      
		function loadHead(){
			initGrid();
		}

		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
					columns: [{
	        			display: '成本项目',
	        			name: 'cost_item_name',
	        			align: 'left',
	        		},{
	        			display: '成本额',
	        			name: 't_1',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
							return  formatNumber(rowdata.t_1, 2, 1);
						}
	        		},{
	        			display: '比例',
	        			name: 't_2',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
							return  formatNumber(rowdata.t_2, 2, 1) + '%';
						}
	        		}
	        	 ],
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostGeneralMessageProfitClinicCost.do?isCheck=false',
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