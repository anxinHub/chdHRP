<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>趋势分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
<style>
</style>
<script type="text/javascript">
          var now = new Date();
          var year = now.getFullYear(); //得到年份
          var month = now.getMonth() + 1;//得到月份
          if (month < 10) month = "0" + month;
		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  initcolumns();
    	  loadDict();
      });
    
       function query(){
    	    initcolumns();
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

		   	function loadHead(){
				grid =  $("#maingrid").ligerGrid({
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptRevenueTrend.do?isCheck=false',
		           width: '100%',height: '100%',checkbox:true,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,checkBoxDisplay : f_checkBoxDisplay,
		           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}

		    function f_checkBoxDisplay(rowdata){
		     	 if (rowdata.dept_name == "合计")
		  			    return false;
		  		      return true;
		  	       }
			
        function initcolumns(){

        	var columns = [{
				display: '科室',
				name: 'dept_name',
				align: 'left',
				frozen:true,
				minWidth:'120'
			}]

        	if($("#year_month_begin").val()=="" || $("#year_month_end").val()==""){
        		var year_month_begin = year + '' + month
    		    var year_month_end = year + '' + month
            }else{
            	  var year_month_begin = $("#year_month_begin").val()
      		      var year_month_end = $("#year_month_end").val()
               }
			var result = getYearAndMonth(year_month_begin,year_month_end);
        	$.each(result, function(v_index, v_data){ 
        		if(v_index==0){
        			columns.push({
         			   display:v_data.acc_year +'年'+ v_data.acc_month+'月',
         			   columns:[
 	        	        			{display:'收入',name:'t'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
 			  	                         render : function(rowdata, rowindex,value) {
 				  							 	return formatNumber(value,2,1);
 				  							 }
 		  							 }
         	        			]
             		})
                 }else {
                	   columns.push({
           			   display:v_data.acc_year +'年'+ v_data.acc_month+'月',
           			   columns:[
   	        	        			{display:'收入',name:'t'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
   			  	                         render : function(rowdata, rowindex,value) {
   				  							 	return formatNumber(value,2,1);
   				  							 }
   		  							 },
   	        	        			{display:'比值',name:'t'+v_data.acc_year+v_data.acc_month+'v', align: 'right',minWidth:'120',
   			  	                         render : function(rowdata, rowindex,value) {
   				  							 	return formatNumber(value,2,1) +'%';
   				  							 }
   		  							 },
           	        			]
               		  })
                   }
   			}); 

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

		
	    function loadDict(){

	    	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
	    	  
	    	  //年月的初始化
	    	  autodate("#year_month_begin","yyyyMM");
	    	  autodate("#year_month_end","yyyyMM");
	    	  $("#year_month_begin").ligerTextBox({width:120});
	          $("#year_month_end").ligerTextBox({width:120});
	          $(':button').ligerButton({ width: 80 });
	       };


	    function queryChargShow(){
	    	   var data = gridManager.getCheckedRows();
	    	   var dept_code_str=[];
                if (data.length == 0){
               	 $.ligerDialog.error('请选择科室');
                    return false;
                }
                var dept_code_str =[];
                $(data).each(function (){					
                	dept_code_str.push(
					  this.dept_code
					)
                });

                var legendData=[]
                var xAxisData=[]//定义X坐标值
                var seriesData=[]
                var deptdata=[];
                //获取X坐标DAta数据
                var year_month_begin = $("#year_month_begin").val()
    		    var year_month_end = $("#year_month_end").val()
    			var result = getYearAndMonth(year_month_begin,year_month_end);
            	$.each(result, function(v_index, v_data){ 
            		xAxisData.push(v_data.acc_year+''+v_data.acc_month)
       			}); 
                var para={      	   
            			year_month_begin:$("#year_month_begin").val(),
            			year_month_end:$("#year_month_end").val(),
            			dept_code_str:'('+dept_code_str+')',
       			};
           	   ajaxJsonObjectByUrl("queryCostDeptRevenueTrendEcharts.do?isCheck=false",para,function(responseData){
       				$.each(data,function(i,obj){
       				   deptdata=[];
       					$.each(responseData.Rows,function(v_index,v_data){
           					  if(v_data.dept_code == obj.dept_code){
               					  if(v_data.year_month!=null){
               					   deptdata.push([v_data.year_month,v_data.money])
                   			    }
               				 }
           				});	
           				
           				var  formPara = {
           						   name: obj.dept_name,
     		    			       type: 'line',
     		    			       stack: '总量',
     		    			       data: deptdata,
       	       				}

           				legendData.push(obj.dept_name)
       					seriesData.push(formPara)
       				});	

       			},false);	
	    	 var myChart = echarts.init(document.getElementById('main'));
	    	 option = {
	    			   tooltip: {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			        data:legendData
	    			    },
	    			    grid: {
	    			        left: '3%',
	    			        right: '4%',
	    			        bottom: '3%',
	    			        containLabel: true
	    			    },
	    			    xAxis: {
	    			        type: 'category',
	    			        data:xAxisData
	    			    },
	    			    yAxis: {
	    			        type: 'value'
	    			    },
	    			    series: seriesData
	    			};
    		
	    	  myChart.setOption(option);

	     	$.ligerDialog.open({
					target:$("#main"),
					height : 500,
					width : 800,
					title : '趋势分析-图形展示页面',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : true,
		     });
		}


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
	 	      		title: "科室收入分析-趋势分析",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.director.CostRevenueAnalysisService",
	 	   			method_name: "queryCostDeptRevenueTrendPrint",
	 	   			bean_name: "costRevenueAnalysisService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);	


	    }
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="查询" onclick="query();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="打印" onclick="print();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="图形" onclick="queryChargShow();" /></td>
	 	</tr>
	 </table>
	   <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
	   <div id="main" style="width: 700px;height:400px;"></div>
</body>
</html>