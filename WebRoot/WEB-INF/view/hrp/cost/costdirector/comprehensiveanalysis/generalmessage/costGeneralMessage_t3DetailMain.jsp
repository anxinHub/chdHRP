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
       	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	 grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
      	 grid.options.parms.push({name:'dept_code',value:"${dept_code}"=="undefined"?"":"${dept_code}"});
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
	        			display: '成本分类',
	        			name: 'item_name',
	        			align: 'left',
		        	},{
	        			display: '金额',
	        			name: 'amount',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount, 2, 1);
    					}
		        	},{
	        			display: '百分比',
	        			name: 'percentage',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.percentage, 2, 1) + "%";
    					}
		        	}
	        	],
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostGeneralMessage_t3.do?isCheck=false',
		           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true
		           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value="${year_month_begin}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value="${year_month_end}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
	 	</tr>
	 </table>
	   <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>