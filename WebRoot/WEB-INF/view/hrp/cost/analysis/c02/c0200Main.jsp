<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
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
	.l-grid td div.l-grid-row-cell-inner {
		font-size: 16px;
		line-height: 50px;
		/* color: #888; */
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
						columns: [{
						    display: '行次',
						    name: '',
						    align: 'center',
						    width: '10%',
						    render: function(rowdata, rowindex, value) {
						        return rowindex + 1;
						    }
						},
						{
						    display: '成本类型',
						    name: 'cost_type_name',
						    align: 'center',
						    width: '30%',
						    cssClass:"style='font-size:600px'"
						},
						{
						    display: '全成本',
						    name: 't',
						    align: 'right',
						    width: '30%',
						    render: function(rowdata, rowindex, value) {
						        return formatNumber(rowdata.t, 2, 1);
						    }
						},
						{
						    display: '比例',
						    name: 't_ratio',
						    align: 'right',
						    //width: 'auto',
						    render: function(rowdata, rowindex, value) {
						        return formatNumber(rowdata.t_ratio, 2, 1) + "%";
						    }
						},
						],
						pageSize: 10,
						dataAction: 'server',
						dataType: 'server',
						usePager: false,
						url: 'queryAnalysisC0200.do',
						width: '100%',
						//height: '100%',
						checkbox: false,
						rownumbers: false,
						delayLoad: true,
						selectRowButtonOnly: true,
						fixedCellHeight:false,
					    rowHeight:'50',
						allowAdjustColWidth:false,
						//heightDiff:31
		           });


		           //$("#pageloading").hide();
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
       
     
       /* 
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
 	      		title: "医疗构成分析总表(临床)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c02.C02Service",
 	   			method_name: "queryC0201Print",
 	   			bean_name: "c02Service",
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
         	var seriesData = [];
         	var legendData = [];
         	  var para={
         			  year_month_begin:$("#year_month_begin").val(),
         			  year_month_end:$("#year_month_end").val(),
     			};
     			
         	   ajaxJsonObjectByUrl("queryAnalysisC0200.do?isCheck=false",para,function(responseData){
     				//console.log(JSON.stringify(responseData))
     				$.each(responseData.Rows,function(i,obj){
     					//console.log(obj.item_code + obj.item_name + obj.t_1)
     					if(obj.cost_type_name != '合计'){
     						seriesData.push({name:obj.cost_type_name,value:formatNumber(obj.t, 2)});
     						legendData.push(obj.cost_type_name)
     			          }
     					
     				});	

     			},false);


         	      //图形展示
                  var opts={
                 		 seriesData:seriesData,
                 		 legendData:legendData,
                        } 
                  
                  var myChart = echarts.init(document.getElementById('main'));

              	option = {
              		    title : {
              		        text: '全院成本构成比例',
              		        subtext: '',
              		        x:'center'
              		    },
              		    tooltip : {
              		        trigger: 'item',
              		      /*   formatter: "{a} <br/>{b} : "+"{c}"+" ({d}%)"*/ 
              		      formatter:function(a){
        
                                    return a.name + ':' + formatNumber(a.value, 2,1)+" " + '('+a.percent+')%'
                    		    }

                		   },
              		    legend: {
              		        type: 'scroll',
              		        orient: 'vertical',
              		        right: 10,
              		        top: 20,
              		        bottom: 20,
              		        data: opts.legendData,
              		    },
              		    series : [
              		        {
              		            name: '收入项目',
              		            type: 'pie',
              		            radius : '55%',
              		            center: ['40%', '50%'],
              		            data: opts.seriesData,
              		            label: {
              		                normal: {
              		                    show: true,
              		                    position:'top', //标签的位置
     	         		                textStyle : {
     	                                  fontWeight : 300 ,
     	                                  fontSize : 16    //文字的字体大小
     	                                },
              		                    formatter: '{d}%'
              		                }
              		            },
              		            itemStyle: {
              		                emphasis: {
              		                    shadowBlur: 10,
              		                    shadowOffsetX: 0,
              		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  		                    
              		                },
              		                
              		            }
              		        },
              		        
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
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	    <td align="left" class="l-table-edit-td" >
	 	      <input type="button" value="查询"  onclick="query()" />
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