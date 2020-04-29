<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本量利分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="/CHD-HRP/lib/echarts/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
		
      $(function(){myChargShow()});
      function myChargShow(){
    	  var dept_name;//科室名称
    	  var fixed_cost; //固定成本
    	  var change_cost; //变动成本
    	  var income_money; //总收入
    	  var workload; //工作量
    	  var t_workload //保本工作量
    	  var unit_income_money //单位收入
    	  var unit_change_cost //单位变动成本
    	  var xAxis //X轴 也就是保本工作量
    	  var yAxis //Y轴  
    	  var Gdata = [];  //固定成本data
	  	  var Costdata = []; //固定成本 + 变动成本data
	  	  var incomedata = [];  //总收入data
      	  var para={      	   
        			year_month_begin:'${year_month_begin}',
        			year_month_end:'${year_month_end}',
        			dept_code:'${dept_code}',
   			};
       	   ajaxJsonObjectByUrl("queryCostGeneralMessageProfitInhos.do?isCheck=false",para,function(responseData){
   				$.each(responseData.Rows,function(i,obj){
   					   dept_name = obj.dept_name
			    	   fixed_cost = obj.t_5
			    	   change_cost = obj.t_6
			    	   income_money = obj.money
			    	   workload = obj.t_1
			    	   t_workload = obj.t_7
			    	   unit_income_money = obj.t_2
			    	   unit_change_cost = obj.t_3
			    	   xAxis = obj.t_7
			    	   yAxis = obj.t_8 
   				});	

   			},false);
  			
           Gdata.push([1,fixed_cost],[t_workload + workload,fixed_cost]);
           Costdata.push([1,fixed_cost + unit_change_cost],[t_workload + workload,fixed_cost + unit_change_cost*(t_workload + workload)]);
           incomedata.push([1,unit_income_money],[t_workload + workload,unit_income_money * (t_workload + workload)]);
           var obj = {
            		  xAxis:xAxis, //X轴 
            		  yAxis:yAxis, //Y轴
            		  title:dept_name + '${year_month_begin}' + '--'+'${year_month_end}' + '本量利分析图',  //标题
            		  Gdata:Gdata,   //固定成本DATA
            		  Costdata:Costdata, //变动成本+固定成本DATA
            		  incomedata:incomedata,//收入DATA
                }

            initEchart(obj);
         }

         function initEchart(obj){

        	 var myChart = echarts.init(document.getElementById('main'));
             
             option = {
             	    backgroundColor: "#eee",
             	    title: {
             	        text: obj.title,
             	        textStyle: {
             	            align: 'right'
             	        },
             	        x: 'center'
             	    },
             	    legend: {
             	        data: ['固定成本', '总成本', '总收入'],
             	        x: 'left',
             	        textStyle: {
             	            fontSize: 15
             	        },
             	    },

             	    xAxis: {
             	        type: 'value',
             	        min: 1,
             	    },
             	    grid: {
             	        left: '3%',
             	        right: '4%',
             	        bottom: '3%',
             	        containLabel: true
             	    },
             	    yAxis: {
             	        type: 'value',
             	        name: '元',
             	        min: 1,
             	    },
             	    series: [{
             	        name: '固定成本',
             	        type: 'line',
             	        symbolSize: 0,
             	        data: obj.Gdata,
             	    },
             	    {
             	        name: '总成本',
             	        type: 'line',
             	        symbolSize: 0,
             	        data: obj.Costdata,
             	        clipOverflow: true
             	    },
             	    {
             	        name: '总收入',
             	        type: 'line',
             	        symbolSize: 0,
             	        data: obj.incomedata,
             	        markPoint: {
             	            itemStyle: {
             	                normal: {
             	                    label: {
             	                        show: true,
             	                        position: 'top',
             	                        formatter: function(param) {
             	                            return '(保本点' + parseInt(param.data.value) + ',' + formatNumber(param.data.yAxis, 2) + ')'
             	                        }
             	                    }
             	                }

             	            },
             	            data: [{
             	                value: obj.xAxis,
             	                xAxis: obj.xAxis,
             	                yAxis: obj.yAxis
             	            }]
             	        },
             	        markLine: {
             	            lineStyle: {
             	                color: {
             	                    type: 'solid',
             	                }
             	            },
             	            data: [[{
             	                coord: [obj.xAxis, 1]
             	            },
             	            {
             	                coord: [obj.xAxis, obj.yAxis]
             	            },
             	            ], [{
             	                coord: [1, obj.yAxis]
             	            },
             	            {
             	                coord: [obj.xAxis, obj.yAxis]
             	            }]]
             	        }
             	    }]
             	};
      		
            	 myChart.setOption(option);
          }
</script>
</head>
<body>
<div id="echart"> 
	 <div id="main" style="width: 100%;height:500px;"></div>
</div>
</body>
</html>