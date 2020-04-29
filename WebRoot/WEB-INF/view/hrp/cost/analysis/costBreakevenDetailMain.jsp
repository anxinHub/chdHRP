<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室盈亏分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      function loadDict(){
    	  //年月的初始化
    	  $("#year_month_begin").ligerTextBox({width:120,disabled:true});
          $("#year_month_end").ligerTextBox({width:120,disabled:true}); 
       };
      
		function loadHead(){
			initGrid();
		}

		function initGrid(){
			 if('${state}'==0){incomeGrid()}
			 if('${state}'==1){costGrid()}
			}


		
		function incomeGrid(){
			var p = '&dept_id='+ '${dept_id}'+'&dept_no='+ '${dept_no}'+'&year_month_begin='+ '${year_month_begin}'+'&year_month_end='+ '${year_month_end}'
			grid =  $("#maingrid").ligerGrid({
				columns: [
							{ display: '行次', align: 'center',width:'10%',
				             		render:function(rowdata,rowindex,value){
				         				return rowindex+1;
				         		}	 
				             },
					         { display: '收费类别编码', name: 'charge_kind_code', align: 'left'},
					         { display: '收费类别名称', name: 'charge_kind_name', align: 'left'},
					         { display: '金额', name: 'money', align: 'right',
	       				         render : function(rowdata, rowindex,value) {
									   return formatNumber(rowdata.money,2,1);
									}},
					       ],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBreakevenDetailIncome.do?isCheck=false'+p,
		           height: '100%', checkbox:false,rownumbers:true,delayLoad :false,
		           selectRowButtonOnly:true
		           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

		function costGrid(){
			var p = '&dept_id='+ '${dept_id}'+'&dept_no='+ '${dept_no}'+'&year_month_begin='+ '${year_month_begin}'+'&year_month_end='+ '${year_month_end}'
			grid =  $("#maingrid").ligerGrid({
				columns: [
							{ display: '行次', align: 'center',width:'10%',
					             		render:function(rowdata,rowindex,value){
					         				return rowindex+1;
					         		}	 
					             },
					         { display: '成本类型编码', name: 'cost_type_code', align: 'left',width:'20%'},
					         { display: '成本类型名称', name: 'cost_type_name', align: 'left',width:'20%',},
					         { display: '直接成本', name: 't1', align: 'right',
	       				         render : function(rowdata, rowindex,value) {
									   return formatNumber(rowdata.t1,2,1);
									}
							  },
					         { display: '全成本', name: 't2', align: 'right',
		       				         render : function(rowdata, rowindex,value) {
										   return formatNumber(rowdata.t2,2,1);
										}
								},
					       ],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBreakevenDetailCost.do?isCheck=false'+p,
		           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :false,
		           selectRowButtonOnly:true,width: '100%', height: '100%'
		           });
	           gridManager = $("#maingrid").ligerGetGridManager();

		}
</script>
</head >
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value = '${year_month_begin}'/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value = '${year_month_end}' /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>