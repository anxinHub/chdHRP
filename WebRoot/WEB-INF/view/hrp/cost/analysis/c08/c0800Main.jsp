<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医院工作量情况查询</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
<style>
	.floatWrap {
	
	}
	.floatWrap:after {
		display:block;
		clear:both;
		content:"";
		visibility:hidden;
		height:0;
	}
</style>
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  query();
    	  
      });
	       
      function loadDict(){
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM");
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
          $(':button').ligerButton({ width: 80 });
      };
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
		           columns: [
		                     { display: '行次', name: '', align: 'center',width:'20%',
			                  		render:function(rowdata,rowindex,value){
					                  		   return rowindex+1;
			                  		}	 
			                   },
		                      {display: '年月', name: 'year_month',align:'left', width : '20%' ,
			                  		render:function(rowdata,rowindex,value){
				                  		 return rowdata.acc_year + rowdata.acc_month;
		                  	      }
		                      }, 
		                      {display: '门诊急人次', name: 'clinic_num',align:'right', width : '20%' ,
		                          render : function(rowdata, rowindex,value) {
		  						 	return formatNumber(rowdata.clinic_num,2,1);
		  						 }},
		                      {display: '实际占用床日', name: 'bed_use_day_num',align:'right', width : '20%' ,
		  	                         render : function(rowdata, rowindex,value) {
		  							 	return formatNumber(rowdata.bed_use_day_num,2,1);
		  							 }},
  							 {display: '出院人数', name: 'out_hos_num',align:'right',
	  	                         render : function(rowdata, rowindex,value) {
	  							 	return formatNumber(rowdata.out_hos_num,2,1);
	  							 }},
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:false,url:'queryAnalysisC0800.do',
		           width: '100%', checkbox: false,rownumbers:false,delayLoad :true,allowAdjustColWidth:false,
		           selectRowButtonOnly:true,
		           });

           gridManager = $("#maingrid").ligerGetGridManager();
		}
		

       //查询
       function query(){
    	   grid.options.parms=[];
      	   grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	   grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
       	   
	       	//加载查询条件
	       	grid.loadData(grid.where);
	       	myChargShow();
       };
       
  
      /*  function print(){
    	
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
 	      		title: "成本控制指标分析",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c08.C08Service",
 	   			method_name: "queryC0801Print",
 	   			bean_name: "c08Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);	

   		
    } */

       function myChargShow(){

   		
       	//构建JSON数据
       	var xAxis_data = [];
       	var series_data = [];
        var clinic_num_data = [];
        var bed_use_day_num_data = [];
        var out_hos_num_data = [];
       	  var para={
       			  year_month_begin:$("#year_month_begin").val(),
       			  year_month_end:$("#year_month_end").val(),
   			};
      
       	   ajaxJsonObjectByUrl("queryAnalysisC0800.do?isCheck=false",para,function(responseData){
   				//console.log(JSON.stringify(responseData))
   				$.each(responseData.Rows,function(i,obj){
   					//console.log(obj.item_code + obj.item_name + obj.t_1)
   					if(obj.acc_year + obj.acc_month  != '总计'){
   						xAxis_data.push(obj.acc_year + obj.acc_month) 
   						clinic_num_data[i] = obj.clinic_num;
   						bed_use_day_num_data[i] = obj.bed_use_day_num;
   						out_hos_num_data[i] = obj.out_hos_num
   			          }
   					
   				});	

   			},false);
       	series_data.push({
			name: "门诊急人次",
            type:'line',
            data:clinic_num_data,
            yAxisindex :0
        },{
			name: "实际占用床日",
            type:'line',
            data:bed_use_day_num_data,
            yAxisindex : 1
        },{
			name: "出院人数",
            type:'line',
            data:out_hos_num_data,
            yAxisindex : 2
        });


       	      //图形展示
                var opts={
                		xAxis_data:xAxis_data,
                		series_data:series_data,
                      } 
                
                var myChart = echarts.init(document.getElementById('main'));

                option = {
                	    title: {
                	        /* text: '医院工作量分析', */
                	        x:'left'
                    	        
                	    },
                	    tooltip: {
                	        trigger: 'axis',
                 	       
                	    },
                	    legend: {
                	    	type: 'scroll',
                	       /*  orient: 'vertical', */
                	        data:['门诊急人次','实际占用床日','出院人数'],
                	    },
                	    grid: {
                	        left: '3%',
                	        right: '4%',
                	        bottom: '3%',
                	        containLabel: true
                	    },
                	    xAxis: {
                	        type: 'category',
                	        boundaryGap: true,
                	        data:opts.xAxis_data,
                	    },
                	    yAxis: {
                	        type: 'value',
                	    },
                	    series:opts.series_data
                	};
            	
            	 myChart.setOption(option);
         }
       
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
            <td align="left" class="l-table-edit-td" >
              <input type="button" value="查询" onclick="query()" />
            </td>
            <td align="left"></td>
	 	</tr>
	 </table>
	    <div class="floatWrap">
            <div style="float:left;width:50%;">
	            <div id="maingrid" style="margin:0; padding:0; border: none;">
	            </div>
            </div>
            <div style="float:left;width:50%;">
	             <div id="echart"> 
						 <div id="main" style="width: 100%;height:500px;"></div>
					</div>
				</div>
          </div> 
</body>
</html>