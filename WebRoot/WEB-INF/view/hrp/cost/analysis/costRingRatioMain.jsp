<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全院科室成本环比分析表查询</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="/CHD-HRP/lib/echarts/echarts.js" type="text/javascript"></script>
<script type="text/javascript">

		var grid;
		var gridManager = null;
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  query();
      });

      //查询
      function query(){

    	  var node=$('#maingrid');
    	  if(node.is(':hidden')){

    		  return false;
          }
    	  
    	  initcolumns();
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
    	   //根据表字段进行添加查询条件，获取年份和月份
     	  grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
     	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
	       	//加载查询条件
	      grid.loadData(grid.where);
	    
      };

           
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
				  dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostRingRatio.do?isCheck=false',
			           /* width: '100%', */ height: '100%', checkbox:true,rownumbers:true,delayLoad :true,
			           selectRowButtonOnly:true,
				       onDblClickRow : function (rowdata, rowindex, value)
	    				{
				    	   openUpdate(
									rowdata.dept_id   + "|" + 
									rowdata.dept_no 
								);
								
	    				 },
			           });
	              gridManager = $("#maingrid").ligerGetGridManager();
		}

		function initcolumns(){
			 var columns = [
					{ display: '科室ID', name: 'dept_id', align: 'left',hide:true,minWidth:'120' },
					{ display: '科室NO', name: 'dept_no', align: 'left',hide:true,minWidth:'120' },
			   		{ display: '科室编码', name: 'dept_code', align: 'left',frozen:true,minWidth:'120',
	   			        render : function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('"+rowdata.dept_id+"|"+rowdata.dept_no+"')>"+(rowdata.dept_code==null?'':rowdata.dept_code)+"</a>";	
				      }
		            },{ display: '科室名称', name: 'dept_name', align: 'left',frozen:true,minWidth:'120' }
 			    ]
	    	var year_month_begin = $("#year_month_begin").val()
		    var year_month_end = $("#year_month_end").val()
			var result = getYearAndMonth(year_month_begin,year_month_end);
    		$.each(result, function(v_index, v_data){ 
        		columns.push({
        			   display:v_data.acc_year +'年'+ v_data.acc_month+'月',
        			   columns:[
	        	        			{display:'直接成本',name:'t_1_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 	return formatNumber(value,2,1);
				  							 }
		  							 },
	        	        			{display:'间接成本',name:'t_2_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 	return formatNumber(value,2,1);
				  							 }
		  							 },
	        	        			{display:'全成本',name:'t_3_'+v_data.acc_year+v_data.acc_month, align: 'right',minWidth:'120',
			  	                         render : function(rowdata, rowindex,value) {
				  							 if(rowdata.dept_name == '合计'){
				  								 return;
				  							 }	
			  	                        	 return formatNumber(value,2,1);
				  							 }
		  							 },
        	        			]
            		})
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

	function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "&"+ "dept_id="+vo[0] 
			     + "&"+ "dept_no="+vo[1]     
		         + "&"+ "year_month_begin="+$("#year_month_begin").val()
		         + "&"+ "year_month_end="+$("#year_month_end").val()
		  parent.$.ligerDialog.open({
				title : '环比分析表明细',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/cost/analysis/costRingRatioDetailMainPage.do?isCheck=false' + parm,
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : false,
		  })
		}


	
	   function myChargShow(){

		   var data = gridManager.getSelectedRow();
		   
		   if(data == null){
			   $.ligerDialog.warn('请选择行');
			   return;
		   }else{

			$("#maingrid").toggle();
	       
			var xAxis_data = [];
	       var t_1 = [] ;//直接成本;
	       var t_2 = [] ;//间接成本;
	       var t_3 = [] ;//全成本;
	       	  var para={
	       			  year_month_begin:$("#year_month_begin").val(),
	       			  year_month_end:$("#year_month_end").val(),
	       			  dept_id:data.dept_id,
	       			  dept_no:data.dept_no
	   			};	      
	       	   ajaxJsonObjectByUrl("queryCostRingRatioChart.do?isCheck=false",para,function(responseData){
	   				  //console.log(JSON.stringify(responseData))
	   				$.each(responseData.Rows,function(i,obj){
	   					xAxis_data.push(obj.year_month)
	   					t_1[i] = obj.t_1;
	   					t_2[i] = obj.t_2;
	   					t_3[i] = obj.t_3;
	   				});	

	   			},false);
	       	   obj = {
	    	       	    legend_data:['直接成本','间接成本','全成本'],
	                 	xAxis_data:xAxis_data,
	                 	series_data:[
	        	                 	 {name:'直接成本',type:'line', stack: '',data:t_1},
	        	                 	 {name:'间接成本',type:'line', stack: '',data:t_2},
	        	                 	 {name:'全成本',type:'line', stack: '',data:t_3},
	        	                 	],
	    	       }
	       	       initEchart(obj);
		   }
		        
	   }

       function initEchart(obj){
           
    	   var myChart = echarts.init(document.getElementById('main'));
    	      //图形展示
           option = {
					    title: {
					        text: '全院科室成本环比折线图'
					    },
					    tooltip: {
					        trigger: 'axis'
					    },
					    legend: {
					        data:obj.legend_data
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    toolbox: {
					        feature: {
					            saveAsImage: {}
					        }
					    },
					    xAxis: {
					        type: 'category',
					        boundaryGap: false,
					        data:obj.xAxis_data
					    },
					    yAxis: {
					        type: 'value'
					    },
					    series: obj.series_data
					};
         	 myChart.setOption(option);
           }
		
</script>
</head >
<body  style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="left" class="l-table-edit-td" ><input type="button" value="查询"  onclick="query()" /></td>
           <td align="left" class="l-table-edit-td" ><input type="button" value="图形" onclick="myChargShow()" /></td>
           <td align="left"></td>
	 	</tr>

	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
	 <div id="echart"><div id="main" style="width: 100%;height:500px;"></div></div>
</body>
</html>