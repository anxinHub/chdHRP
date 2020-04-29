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

		var grid;
		var gridManager = null;
		var grid1;
		var gridManager1 = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      //查询
      function query(){

    	  queryGrid();
    	  queryGrid1();
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

         function queryGrid1(){
        	 grid1.options.parms=[];
       	     grid1.options.newPage=1;
       	   //根据表字段进行添加查询条件，获取年份和月份
        	 grid1.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
        	 grid1.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
  	       	//加载查询条件
  	         grid1.loadData(grid1.where);
         }
           
      function loadDict(){
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM");
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
          $(':button').ligerButton({ width: 80 });
          
       };
      
		function loadHead(){
			initGrid();
			initGrid1();
		}

		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
					columns: [{
						display: '项目',
						name: 'item_name',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('" +
								$("#year_month_begin").val() + "|" +
								$("#year_month_end").val() + "|" +
								rowdata.item_code + "|" +
								'0' + "')>" + rowdata.item_name + "</a>";
						}
					},
					{
						display: '全院',
						columns: [{
								display: '收入',
								name: 't_1',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_1, 2, 1);
								}
							},
							{
								display: '成本',
								name: 't_2',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_2, 2, 1);
								}
							},
							{
								display: '收益',
								name: 't_3',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_3, 2, 1);
								}
							},
							{
								display: '成本收益率',
								name: 't_4',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_4, 2) + '%';
								}
							},
							{
								display: '收入结余率',
								name: 't_5',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_5, 2) + '%';
								}
							},
						]
					},
					{
						display: '药占比',
						name: 't_6',
						align: 'right',
						render: function(rowdata, rowindex, value) {
							return formatNumber(rowdata.t_6, 2) + '%';
						}
					},
					{
						display: '单位职工',
						columns: [{
								display: '收入',
								name: 't_7',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_7, 2, 1);
								}
							},
							{
								display: '成本',
								name: 't_8',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_8, 2, 1);
								}
							},
							{
								display: '收益',
								name: 't_9',
								align: 'right',
								render: function(rowdata, rowindex, value) {
									return formatNumber(rowdata.t_9, 2, 1);
								}
							},
						]
					},
				],
			           dataAction: 'server',dataType: 'server',usePager:false,url:'queryCostGeneralHos.do',
			           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :true,
			           selectRowButtonOnly:true,width: '100%', height: '50%'
			           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}

		function initGrid1(){
	           grid1 = $("#maingrid1").ligerGrid({
	        	   columns: [{
	        			display: '项目',
	        			name: 'item_name',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
	        				return "<a href=javascript:openUpdate('" +
	        					$("#year_month_begin").val() + "|" +
	        					$("#year_month_end").val() + "|" +
	        					rowdata.item_code + "|" +
	        					'1' + "')>" + rowdata.item_name + "</a>";
	        			}
	        		},
	        		{
	        			display: '医疗(不含药品)',
	        			columns: [{
	        					display: '收入',
	        					name: 't_1',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_1, 2, 1);
	        					}
	        				},
	        				{
	        					display: '成本',
	        					name: 't_2',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_2, 2, 1);
	        					}
	        				},
	        				{
	        					display: '收益',
	        					name: 't_3',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_3, 2, 1);
	        					}
	        				},
	        				{
	        					display: '成本收益率',
	        					name: 't_4',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_4, 2) + '%';
	        					}
	        				},
	        				{
	        					display: '收入结余率',
	        					name: 't_5',
	        					align: 'right',
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
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_6, 2, 1);
	        					}
	        				},
	        				{
	        					display: '成本',
	        					name: 't_7',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_7, 2, 1);
	        					}
	        				},
	        				{
	        					display: '收益',
	        					name: 't_8',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_8, 2, 1);
	        					}
	        				},
	        				{
	        					display: '成本收益率',
	        					name: 't_9',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_9, 2) + '%';
	        					}
	        				}, {
	        					display: '收入结余率',
	        					name: 't_10',
	        					align: 'right',
	        					render: function(rowdata, rowindex, value) {
	        						return formatNumber(rowdata.t_10, 2) + '%';
	        					}
	        				},
	        			]
	        		},
	        	],dataAction: 'server',dataType: 'server',usePager:false,url:'queryCostGeneralHosMed.do',
		           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,width: '100%', height: '50%'
	           });
	           gridManager1 = $("#maingrid1").ligerGetGridManager();
		}
	    function openUpdate(obj){
	    	var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +  "&item_code="+vo[2]+  "&gridhead="+vo[3];
			parent.$.ligerDialog.open({
				title : '自定义数据采集添加',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/cost/analysis/costGeneralDetailMainPage.do?isCheck=false' + parm,
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : true,
				isResize : true,
				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
			});
	    }
</script>
</head>
<body>
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="left" class="l-table-edit-td"><input type="button" value=" 查询" onclick="query();" /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
	 <div id="maingrid1" style="margin:0; padding:0"></div>
</body>
</html>