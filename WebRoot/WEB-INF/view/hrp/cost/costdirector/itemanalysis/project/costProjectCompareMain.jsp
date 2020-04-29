<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>比较分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
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
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
					display: '成本项目',
					name: 'cost_item_name',
					align: 'left',
				},{
					display: '本期',
					columns:[{
						display: '成本额',
						name: 't_1',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							return  formatNumber(rowdata.t_1, 2, 1);
						}
					}]
				  },{
						display: '上期',
						columns:[{
							display: '成本额',
							name: 't_2',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_2, 2, 1);
							}
						},{
							display: '差异',
							name: 't_3',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_3, 2, 1);
							}
						},{
							display: '差异率',
							name: 't_4',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 if($("#color").val()!="" ){
                                    if(formatNumber(rowdata.t_4, 2, 1) > $("#color").val())
                                    return "<span style='color:red'>" + formatNumber(rowdata.t_4, 2, 1) +'%' + "</span>"
								}
								return formatNumber(rowdata.t_4, 2, 1) +'%'
							}
						}]
				  },{
						display: '同期',
						columns:[{
							display: '成本额',
							name: 't_5',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_5, 2, 1);
							}
						},{
							display: '差异',
							name: 't_6',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_6, 2, 1);
							}
						},{
							display: '差异率',
							name: 't_7',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 if($("#color").val()!="" ){
	                                    if(formatNumber(rowdata.t_7, 2, 1) > $("#color").val())
	                                    return "<span style='color:red'>" + formatNumber(rowdata.t_7, 2, 1) +'%' + "</span>"
									}
									return formatNumber(rowdata.t_7, 2, 1) +'%'
							}
						}]
				  },{
						display: '平均',
						columns:[{
							display: '成本额',
							name: 't_8',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_8, 2, 1);
							}
						},{
							display: '差异',
							name: 't_9',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_9, 2, 1);
							}
						},{
							display: '差异率',
							name: 't_10',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 if($("#color").val()!="" ){
	                                    if(formatNumber(rowdata.t_10, 2, 1) > $("#color").val())
	                                    return "<span style='color:red'>" + formatNumber(rowdata.t_10, 2, 1) +'%' + "</span>"
									}
									return formatNumber(rowdata.t_10, 2, 1) +'%'
							}
						}]
				  }],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostProjectCompare.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:true,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true,checkBoxDisplay : f_checkBoxDisplay
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

	    function f_checkBoxDisplay(rowdata){
	     	 if (rowdata.dept_name == "合计")
	  			    return false;
	  		      return true;
	  	       }
	       
	    function loadDict(){

	    	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
	    	  
	    	  //年月的初始化
	    	  autodate("#year_month_begin","yyyyMM");
	    	  autodate("#year_month_end","yyyyMM");
	    	  $("#year_month_begin").ligerTextBox({width:120});
	          $("#year_month_end").ligerTextBox({width:120});
	          $("#color").ligerTextBox({width:120});
	          $(':button').ligerButton({width:80});
	       };

	       function myChargShow(){
	    	   var data = gridManager.getCheckedRows();
	    	   if (data.length == 0){
	             	 $.ligerDialog.error('请选择成本项目');
	                  return false;
	              }
	    		var xAxisData = [];
	    		var benqiData =[]; //本期收入
	    		var shangqiData =[]; //上期收入
	    		var tongqiData =[]; //同期收入
	    		var pingjuData =[]; //同期收入
	    		$.each(data,function(i,obj){
	    			xAxisData.push(obj.cost_item_name)
	    			benqiData.push(obj.t_1)
	    			shangqiData.push(obj.t_2)
	    			tongqiData.push(obj.t_5)
	    			pingjuData.push(obj.t_8)
				});	

	      	 var myChart = echarts.init(document.getElementById('main'));

		      	option = {
	      		    title: {
	      		        text: '成本项目比较分析',
	      		    },
		      	    tooltip : {
		      	        trigger: 'axis',
		      	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		      	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		      	        }
		      	    },
		      	    legend: {
		      	        data:['本期','上期','同期','平均']
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
		      	            data : xAxisData
		      	        }
		      	    ],
		      	    yAxis : [
		      	        {
		      	            type : 'value'
		      	        }
		      	    ],
		      	    series : [
		      	        {
		      	            name:'本期收入',
		      	            type:'bar',
		      	            data:benqiData
		      	        },
		      	        {
		      	            name:'上期收入',
		      	            type:'bar',
		      	            data:shangqiData
		      	        },
		      	        {
		      	            name:'同期收入',
		      	            type:'bar',
		      	            data:tongqiData
		      	        },
		      	        {
		      	            name:'平均收入',
		      	            type:'bar',
		      	            data:pingjuData
		      	        }
		      	    ]
		      	};

	    	        myChart.setOption(option);
			       	$.ligerDialog.open({
						target:$("#main"),
						height : 500,
						width : 800,
						title : '比较分析-图形展示页面',
						modal : true,
						showToggle : false,
						showMax : false,
						showMin : false,
						isResize : true,
			     });

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
 	 	      		title: "成本项目分析-成本比较",//标题
 	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	 	      		class_name: "com.chd.hrp.cost.service.director.CostItemAnalysisService",
 	 	   			method_name: "queryCostProjectComparePrint",
 	 	   			bean_name: "costItemAnalysisService",
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
           <td align="left" class="l-table-edit-td"><input type="button" value="图表" onclick="myChargShow();" /></td>
	 	</tr>
	 	<tr>
	 	    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">颜色警示,差异率大于：</td>
            <td align="left" class="l-table-edit-td"><input name="color" type="text" id="color" class="text"/></td>
	 	    <td align="right" class="l-table-edit-td"></td>
	 	</tr>
	 </table>
	     <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
	     <div id="main" style="width: 700px;height:400px;"></div>

</body>
</html>