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
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
<style>
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
      });
    
       function query(){
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
 	       echartsShow();
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
	 	      		title: "科室经营状况",//标题
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

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
        			display: '项目',
        			name: 'item_name',
        			align: 'left',
	        	  },
	        	  {
        			display: '收入',
        			name: 't_1',
        			align: 'left',
        			render:function(rowdata,rowindex,value){
                 		 return formatNumber(rowdata.t_1,2,1);
         	        }
		          },
	        	  {
        			display: '成本',
        			name: 't_2',
        			align: 'left',
        			render:function(rowdata,rowindex,value){
                		 return formatNumber(rowdata.t_2,2,1);
        	        }
		          },
	        	  {
	        			display: '收益',
	        			name: 't_3',
	        			align: 'left',
	        			render:function(rowdata,rowindex,value){
	                 		 return formatNumber(rowdata.t_3,2,1);
	         	        }
			       },{
	        			display: '门诊工作量',
	        			name: 't_4',
	        			align: 'left',
	        			render:function(rowdata,rowindex,value){
	                 		 return formatNumber(rowdata.t_4,2,1);
	         	        }
		          },{
	        			display: '住院工作量',
	        			name: 't_5',
	        			align: 'left',
	        			render:function(rowdata,rowindex,value){
	                 		 return formatNumber(rowdata.t_5,2,1);
	         	        }
			          }
        	   ],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDepartmentOperation.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
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

	       function echartsShow(){
	    	      var xAxis_data = ['收入', '成本', '收益'];
	    	      var series_data = [];
	          	  var para={
	          			  year_month_begin:$("#year_month_begin").val(),
	          			  year_month_end:$("#year_month_end").val(),
	          			  dept_code:liger.get("dept_code").getValue().split(".")[2],

	      			};
	          	   ajaxJsonObjectByUrl("queryCostDepartmentOperation.do?isCheck=false",para,function(responseData){
	      				$.each(responseData.Rows,function(i,obj){
	      					series_data.push(obj.t_1,obj.t_2,obj.t_3)
	      				});	

	      			},false);
	                   
	               var myChart = echarts.init(document.getElementById('main'));
	               option = {
	            		  title: {
	            		        text: '科室经营状况'
	            		   },
	                   color: ['#3398DB'],
	                   tooltip : {
	                       trigger: 'axis',
	                       axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                           type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                       }
	                   },
	                   grid: {
	                       left: '3%',
	                       right: '4%',
	                       bottom: '3%',
	                       containLabel: true
	                   },
	                   xAxis : [
	                       {
	                           type : 'category',
	                           data : xAxis_data,
	                           axisTick: {
	                               alignWithLabel: true
	                           }
	                       }
	                   ],
	                   yAxis : [
	                       {
	                           type : 'value'
	                       }
	                   ],
	                   series : [
	                       {
	                           name:'直接访问',
	                           type:'bar',
	                           barWidth: '60%',
	                           data:series_data
	                       }
	                   ]
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
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="查询" onclick="query();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="打印" onclick="print();" /></td>
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