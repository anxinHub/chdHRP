<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全院科室成本环比分析表查询</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  initcolumns();
      });

      //查询
      function query(){
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
    	   //根据表字段进行添加查询条件，获取年份和月份
     	  grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
     	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
	       	//加载查询条件
	      grid.loadData(grid.where);
	    
      };

           
      function loadDict(){
    	  $("#year_month_begin").ligerTextBox({width:120,disabled:true});
          $("#year_month_end").ligerTextBox({width:120,disabled:true});
       };
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				  dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostRingRatioDetail.do?isCheck=false&dept_id='+'${dept_id}'+'&dept_no='+'${dept_no}'+'&year_month_begin='+'${year_month_begin}'+'&year_month_end='+'${year_month_end}',
			           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :false,
			           selectRowButtonOnly:true,width: '100%', height: '100%'
			           });
	              gridManager = $("#maingrid").ligerGetGridManager();
		}

		function initcolumns(){
			 var columns = [
			   		{ display: '成本类型编码', name: 'cost_type_code', align: 'left',frozen:true,minWidth:'120'},
			   		{ display: '成本类型名称', name: 'cost_type_name', align: 'left',frozen:true,minWidth:'120'}
			    ]
	    	var year_month_begin = '${year_month_begin}'
		    var year_month_end = '${year_month_end}'
			var result = getYearAndMonth(year_month_begin,year_month_end);
   		$.each(result, function(v_index, v_data){ 
       		columns.push({
       			   display:v_data.acc_year +'年'+ v_data.acc_month+'月',
       			   columns:[
	        	        			{display:'直接成本',name:'t_1_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 	return formatNumber(value,2,1);
				  							 }
		  							 },
	        	        			{display:'间接成本',name:'t_2_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 	return formatNumber(value,2,1);
				  							 }
		  							 },
	        	        			{display:'全成本',name:'t_3_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 	return formatNumber(value,2,1);
				  							 }
		  							 },
       	        			]
           		})
  			}); 

   		    columns.push({
   		    	display:'合计',
   		    	columns:[
 	        			{display:'直接成本',name:'t_1_sum', align: 'right',minWidth:'120',
	  	                         render : function(rowdata, rowindex,value) {
		  							 	return formatNumber(value,2,1);
		  							 }
							 },
 	        			{display:'间接成本',name:'t_2_sum', align: 'right',minWidth:'120',
	  	                         render : function(rowdata, rowindex,value) {
		  							 	return formatNumber(value,2,1);
		  							 }
							 },
 	        			{display:'全成本',name:'t_3_sum', align: 'right',minWidth:'120',
	  	                         render : function(rowdata, rowindex,value) {
		  							 	return formatNumber(value,2,1);
		  							 }
							 },
        			]

   	   		})
		    
			  grid.set('columns', columns);
			}
		
		function getYearAndMonth(start, end) {
			var start = start.substring(0,4) +'-' + start.substring(4,6)
			var end   = end.substring(0,4) +'-' + end.substring(4,6)
		    var result = [];
		    var starts = start.split('-');
		    var ends = end.split('-');
		    var staYear = parseInt(starts[0]);
		    var staMon = parseInt(starts[1]);
		    var endYear = parseInt(ends[0]);
		    var endMon = parseInt(ends[1]);
		    while (staYear <= endYear) {
		        if (staYear === endYear) {
		            while (staMon <= endMon) {
		                result.push({acc_year: staYear, acc_month: staMon < 10 ?"0" + staMon:staMon});
		                staMon++;
		            }
		            staYear++;
		        } else {
		            if (staMon > 12) {
		                staMon = 1;
		                staYear++;
		            }
		            result.push({acc_year: staYear, acc_month: staMon < 10 ?"0" + staMon:staMon});
		            staMon++;
		        }
		    }
		    return result;
		}
</script>
</head >
<body  style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value = '${year_month_begin}'/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value = '${year_month_end}' /></td>
           <td align="left"></td>
	 	</tr>

	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>