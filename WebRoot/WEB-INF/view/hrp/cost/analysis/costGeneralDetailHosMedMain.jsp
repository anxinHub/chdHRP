<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合信息分析明细</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      //查询
      function query(){

    	  queryGrid();
      };

         function queryGrid(){
             
        	 grid.options.parms=[];
       	     grid.options.newPage=1;
       	   //根据表字段进行添加查询条件，获取年份和月份
        	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
        	 grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
  	       	//加载查询条件
  	         grid.loadData(grid.where);
          }

           
      function loadDict(){
    	  //年月的初始化
    	  $("#year_month_begin").ligerTextBox({width:120,disabled:true});
          $("#year_month_end").ligerTextBox({width:120,disabled:true});
          
       };
      
		function loadHead(){
			initGrid();
		}

		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
					columns: [{
						display: '科室',
						name: 'dept_name',
						align: 'left'
					},
					{
						display: '医疗(不含药品)',
						columns: [{
								display: '收入',
								name: 't_1',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_1, 2, 1);
								}
							},
							{
								display: '成本',
								name: 't_2',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_2, 2, 1);
								}
							},
							{
								display: '收益',
								name: 't_3',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_3, 2, 1);
								}
							},
							{
								display: '成本收益率',
								name: 't_4',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_4, 2) + '%';
								}
							},
							{
								display: '收入结余率',
								name: 't_5',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_5, 2) + '%';
								}
							},
						]
					},
					{
						display: '药品',
						columns: [{
								display: '收入',
								name: 't_6',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_6, 2, 1);
								}
							},
							{
								display: '成本',
								name: 't_7',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_7, 2, 1);
								}
							},
							{
								display: '收益',
								name: 't_8',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_8, 2, 1);
								}
							},
							{
								display: '成本收益率',
								name: 't_9',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_9, 2, 1) + '%';
								}
							},
							{
								display: '收入结余率',
								name: 't_10',
								align: 'left',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_10, 2) + '%';
								}
							},
						]
					},
				], dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostGeneralDetailMedHos.do?isCheck=false&year_month_begin='+'${year_month_begin}'+'&year_month_end='+'${year_month_end}'+'&natur_code='+'${item_code}',
			           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :false,
			           selectRowButtonOnly:true,width: '100%', height: '100%'
			           });
		              gridManager = $("#maingrid").ligerGetGridManager();
			}
</script>
</head>
<body  style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value = '${year_month_begin}' /></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value = '${year_month_end}' /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>