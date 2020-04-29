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
		loadHead();
	});
	
	function loadHead() {
		
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
                                      { display: '统计年月', name: 'year_month', align: 'left',width : '150',
                                    	  render : function(rowdata, rowindex,value) {
	                                           return rowdata.acc_year+rowdata.acc_month;
                                            }
                                       //,totalSummary:{
                                      //  type:'sum',
//		                                  render:function(suminf,column,cell){
                                 //		  return '<div>总计</div>';
//		                                   }
//	                                    }
                                      },

										{
											display : '科室编码',
											name : 'dept_code',
											align : 'left',
											width : '150'
											
										},
										{
											display : '科室名称',
											name : 'dept_name',
											align : 'left',
											width : '150'
											
										}, 
										{
											display : '成本项目编码',
											name : 'cost_item_code',
											align : 'left',
											width : '120'
										},
										{
											display : '成本项目名称',
											name : 'cost_item_name',
											align : 'left',
											width : '120'
										},
										{ 
											display: '资金来源名称', 
											name: 'source_name', 
											align: 'left',
											width : '150'
										 }, 
										{
											display : '费用',
											name : 'dir_amount',
											align : 'right',
											width : '80'
										} ],
								dataAction : 'server',
								dataType : 'server',
								usePager : true,
								data:${dirAmountDetail},
								width : '100%', 
								height : '100%',
								checkbox : false,
								rownumbers : true,
								delayLoad : false,//初始化不加载，默认false
								checkBoxDisplay : false,
								selectRowButtonOnly : true,//heightDiff: -10,
								onsuccess : function() {

								}
							});
		
		

		gridManager = $("#maingrid").ligerGetGridManager();
		

	}

	

	


	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
