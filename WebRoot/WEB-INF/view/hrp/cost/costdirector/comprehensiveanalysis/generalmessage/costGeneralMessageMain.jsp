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
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
<script type="text/javascript">

		var grid;//收益状况分析-全成本
		var gridManager = null;
		var grid1;//收益状况分析-不含药品
		var gridManager1 = null;
		var grid2;//本量利分析
		var gridManager2 = null;
		var grid_t1;//图表一
		var gridManager_t1 = null;
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      //查询
      function query(){

    	  queryGrid();
    	  queryGrid1();
    	  queryGrid2();
    	  myChargShow_t1();
    	  myChargShow_t2();
    	  myChargShow_t3();
      };

    
       function queryGrid(){
       	    grid.options.parms=[];
      	    grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	    grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
       	    grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
       	    grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
       	    grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[2]}); 
 	       	//加载查询条件
 	        grid.loadData(grid.where);
        }

        function queryGrid1(){
       	 grid1.options.parms=[];
      	 grid1.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
     	 grid1.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
     	 grid1.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
     	 grid1.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
     	 grid1.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
     	 grid1.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[2]}); 
 	       	//加载查询条件
 	     grid1.loadData(grid1.where);
        }

        function queryGrid2(){
          	 grid2.options.parms=[];
         	 grid2.options.newPage=1;
         	   //根据表字段进行添加查询条件，获取年份和月份
          	 grid2.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
          	 grid2.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
          	 grid2.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
          	 grid2.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
          	 grid2.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[2]}); 
    	       	//加载查询条件
    	     grid2.loadData(grid1.where);
           }
           
      //打印 
        function print(){
 	    	
 	    	if(grid.getData().length==0){
 	    		
 				$.ligerDialog.error("请先查询数据！");
 				
 				return;
 			}
 	    	
 	    	var heads={
 	 	    		//"isAuto":true,//系统默认，页眉显示页码
 	 	    		"rows": [
 	 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	 	    	]};
 	 	       var printPara={
 	 	      		title: "综合信息分析",//标题
 	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	 	      		class_name: "com.chd.hrp.cost.service.director.CostComprehensiveAnalysisService",
 	 	   			method_name: "queryCostDepartmentOperationPrint",
 	 	   			bean_name: "costComprehensiveAnalysisService",
 	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	 	   			
 	 	       	};
 	 	       
 	 	      //执行方法的查询条件
 	 		  $.each(grid.options.parms,function(i,obj){
 	 			printPara[obj.name]=obj.value;
 	  	      });
 	 		
 	  	     officeGridPrint(printPara);	
 	    }
  
        
        
      function loadDict(){
          
    	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
    	  
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
			initGrid2();
		}

		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
					columns: [{
						display: '项目',
						name: 'item_name',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							return "<a href=javascript:openGeneralMessageDetail('" +
								$("#year_month_begin").val() + "|" +
								$("#year_month_end").val() + "|" +
								liger.get("dept_code").getValue().split(".")[0] + "|" +
								liger.get("dept_code").getValue().split(".")[1] + "|" +
								liger.get("dept_code").getValue().split(".")[2] + "|" +
								rowdata.item_code+ 
							  "')>" + rowdata.item_name + "</a>";
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
			           dataAction: 'server',dataType: 'server',usePager:false,url:'queryCostGeneralMessage.do',
			           width: '100%',height: '200',checkbox:false,rownumbers:true,delayLoad :true,
			           selectRowButtonOnly:true
			           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}

		  function openGeneralMessageDetail(obj){
		    	var vo = obj.split("|");
				var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] + "&dept_id="+vo[2]+ "&dept_no="+vo[3]+ "&dept_code="+vo[4]+ "&natur_code="+vo[5];
				parent.$.ligerDialog.open({
					title : '收益状况分析明细',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageDetailMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

		    }
		    
		function initGrid1(){
	           grid1 = $("#maingrid1").ligerGrid({
	        	   columns: [{
	        			display: '项目',
	        			name: 'item_name',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
							return "<a href=javascript:openGeneralMessageMedDetail('" +
								$("#year_month_begin").val() + "|" +
								$("#year_month_end").val() + "|" +
								liger.get("dept_code").getValue().split(".")[0] + "|" +
								liger.get("dept_code").getValue().split(".")[1] + "|" +
								liger.get("dept_code").getValue().split(".")[2] + "|" +
								rowdata.item_code+ 
							  "')>" + rowdata.item_name + "</a>";
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
	        	],dataAction: 'server',dataType: 'server',usePager:false,url:'queryCostGeneralMessageMed.do',
		           width: '100%',height: '200', checkbox:false,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
	           });
	           
	           gridManager1 = $("#maingrid1").ligerGetGridManager();
		}

		  function openGeneralMessageMedDetail(obj){
		    	var vo = obj.split("|");
				var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] + "&dept_id="+vo[2]+ "&dept_no="+vo[3]+ "&dept_code="+vo[4]+ "&natur_code="+vo[5];
				parent.$.ligerDialog.open({
					title : '收益状况分析明细',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageMedDetailMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

		    }

		    
		function initGrid2(){
	           grid2 = $("#maingrid2").ligerGrid({
	        	   columns: [{
	        			display: '项目',
	        			name: 'item_name',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
							return "<a href=javascript:openGeneralMessageProfitDetail('" +
								$("#year_month_begin").val() + "|" +
								$("#year_month_end").val() + "|" +
								liger.get("dept_code").getValue().split(".")[0] + "|" +
								liger.get("dept_code").getValue().split(".")[1] + "|" +
								liger.get("dept_code").getValue().split(".")[2] + "|" +
								rowdata.item_code+ 
							  "')>" + rowdata.item_name + "</a>";
						}
		        	},{
	        			display: '固定成本',
	        			name: 't_1',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_1, 2, 1);
    					}
		        	},{
	        			display: '变动成本',
	        			name: 't_2',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_2, 2, 1);
    					}
		        	},{
	        			display: '工作量',
	        			name: 't_3',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_3, 2, 1);
    					}
		        	},{
	        			display: '单位收入',
	        			name: 't_4',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_4, 2, 1);
    					}
		        	},{
	        			display: '单位变动成本',
	        			name: 't_5',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_5, 2, 1);
    					}
		        	},{
	        			display: '单位收益',
	        			name: 't_6',
	        			align: 'left',
    					render: function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.t_6, 2, 1);
    					}
		        	},{
	        			display: '保本工作量',
	        			name: 't_7',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
							if(rowdata.t_7>0){
								return "<a href=javascript:open('" +
								$("#year_month_begin").val() + "|" +
								$("#year_month_end").val() + "|" +
								rowdata.item_code +
							  "')>" + formatNumber(rowdata.t_7, 2, 1); + "</a>";
							}else {
								return formatNumber(rowdata.t_7, 2, 1);
							}
						}
		        	},{
	        			display: '保本收入',
	        			name: 't_8',
	        			align: 'left',
	        			render: function(rowdata, rowindex, value) {
	        				return formatNumber(rowdata.t_8, 2, 1);
						}
		        	}
	        	],dataAction: 'server',dataType: 'server',usePager:false,url:'queryCostGeneralMessageProfit.do',
		           width: '100%',height: '200', checkbox:false,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
	           });
	           
	           gridManager2 = $("#maingrid2").ligerGetGridManager();
		}

		  function openGeneralMessageProfitDetail(obj){
		    	var vo = obj.split("|");
				var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] + "&dept_id="+vo[2]+ "&dept_no="+vo[3]+ "&dept_code="+vo[4]+ "&natur_code="+vo[5];
				parent.$.ligerDialog.open({
					title : '本量利分析',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageProfitDetailMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

		    }

		  function open(obj){
			 var vo = obj.split("|");
			 var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&item_code="+vo[2];
			 parent.$.ligerDialog.open({
					title : '本量利分析',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageChartMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}
			
    //直接,管理,医辅,医技成本
	 function myChargShow_t1(){

		 $("#show_t1").show();
		 
		  var seriesData = [];

		  var para={
        			  year_month_begin:$("#year_month_begin").val(),
        			  year_month_end:$("#year_month_end").val(),
        			  dept_code:liger.get("dept_code").getValue().split(".")[2],
    			};
		   ajaxJsonObjectByUrl("queryCostGeneralMessage_t1.do?isCheck=false",para,function(responseData){
   				//console.log(JSON.stringify(responseData))
   				$.each(responseData.Rows,function(i,obj){
   					//console.log(obj)
   					if(obj.item_name != '合计'){
   					  seriesData.push({value:obj.amount,name:obj.item_name});
   					}
   				});	

   			},false)
		 
	        var myChart = echarts.init(document.getElementById('container_t1'));
   			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
   	 	 myChart.setOption(option);
	 }

    //变动固定成本
	 function myChargShow_t2(){

		 $("#show_t2").show();
		 
		  var seriesData = [];

		  var para={
        			  year_month_begin:$("#year_month_begin").val(),
        			  year_month_end:$("#year_month_end").val(),
        			  dept_code:liger.get("dept_code").getValue().split(".")[2],
        			  
    			};
		   ajaxJsonObjectByUrl("queryCostGeneralMessage_t2.do?isCheck=false",para,function(responseData){
   				//console.log(JSON.stringify(responseData))
   				$.each(responseData.Rows,function(i,obj){
   					//console.log(obj)
   					if(obj.item_name != '合计'){
   					seriesData.push({value:obj.amount,name:obj.item_name});
   					}
   				});	

   			},false)

		 
   		  var myChart = echarts.init(document.getElementById('container_t2'));
  			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
  	 	 myChart.setOption(option);

}
    //成本分类成本
	 function myChargShow_t3(){

		 $("#show_t3").show();

		 
		 var seriesData = [];

		  var para={
       			  year_month_begin:$("#year_month_begin").val(),
       			  year_month_end:$("#year_month_end").val(),
       			  dept_code:liger.get("dept_code").getValue().split(".")[2],
   			};
		   ajaxJsonObjectByUrl("queryCostGeneralMessage_t3.do?isCheck=false",para,function(responseData){
  				$.each(responseData.Rows,function(i,obj){
  					if(obj.item_name != '合计'){
  						seriesData.push({value:obj.amount,name:obj.item_name});
  					}
  				});	

  			},false)
  			
  		  var myChart = echarts.init(document.getElementById('container_t3'));
  			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:seriesData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
  	 	 myChart.setOption(option);

}
		 function showDetail(x){
				var parm ='&'+ "year_month_begin="+$("#year_month_begin").val() 
				+ "&year_month_end="+$("#year_month_end").val() 
				+ "&dept_id="+liger.get("dept_code").getValue().split(".")[0]
				+ "&dept_no="+liger.get("dept_code").getValue().split(".")[1]
				+ "&dept_code="+liger.get("dept_code").getValue().split(".")[2];
			    if(x==1)
			     $.ligerDialog.open({
				     url: 'costGeneralMessage_t1DetailMainPage.do?isCheck=false'+parm, 
				     height: 500,
				     width: 800, 
				     title:'成本状况分析',
				     modal:true,
				     showToggle:false,
				     showMax:false,
				     showMin: true,
				     isResize:true,
			     });
			    if(x==2)
				     $.ligerDialog.open({
					     url: 'costGeneralMessage_t2DetailMainPage.do?isCheck=false'+parm, 
					     height: 500,
					     width: 800, 
					     title:'成本状况分析',
					     modal:true,
					     showToggle:false,
					     showMax:false,
					     showMin: true,
					     isResize:true,
				     });
			    if(x==3)
				     $.ligerDialog.open({
					     url: 'costGeneralMessage_t3DetailMainPage.do?isCheck=false'+parm, 
					     height: 500,
					     width: 800, 
					     title:'成本状况分析',
					     modal:true,
					     showToggle:false,
					     showMax:false,
					     showMin: true,
					     isResize:true,
				     });
		       }
</script>
</head>
<body style="padding: 0px; overflow-x: scroll;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value=" 查询" onclick="query();" /></td>
	 	   <!--  <td align="left" class="l-table-edit-td"><input type="button" value=" 打印" onclick="print();" /></td>-->
	 	</tr>
	 </table>
	      <div class="l-layout-header">收益状况分析</div>
	       <div id="maingrid" style="margin:0; padding:0"></div>
	       <div id="maingrid1" style="margin:0; padding:0"></div>
	     <div class="l-layout-header">成本状况分析</div>
	     <div>
	       <div style="display:inline-block;width: 33%;height:200px;" id="container_t1"></div>
	       <div style="display:inline-block;width: 33%;height:200px;" id="container_t2"></div>
	       <div style="display:inline-block;width: 33%;height:200px;" id="container_t3"></div>
	       <div style="display:inline-block;width: 33%;height:20px;text-align:center"><h5 id="show_t1" style="cursor: pointer;display:none" onclick="javascript:showDetail(1)">查看...</h5></div>
	       <div style="display:inline-block;width: 33%;height:20px;text-align:center"><h5 id="show_t2" style="cursor: pointer;display:none" onclick="javascript:showDetail(2)">查看...</h5></div>
	       <div style="display:inline-block;width: 33%;height:20px;text-align:center"><h5 id="show_t3" style="cursor: pointer;display:none" onclick="javascript:showDetail(3)">查看...</h5></div>
	     </div>
	     <div class="l-layout-header">本量利分析</div>
	     <div id="maingrid2" style="margin:0; padding:0"></div>
</body>
</html>