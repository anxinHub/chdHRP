<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

    var gridRight;
    
    var check_column1_manager;
    
    var check_column2_manager;

    $(function (){
    	
    	toobar();
    	
		check_column1_manager = $("#check_column1").ligerCheckBox();check_column1_manager.setValue(true);
		
		check_column2_manager = $("#check_column2").ligerCheckBox();check_column2_manager.setValue(false);
    	 
        loadDict();//加载下拉框

    	loadRightGrid(null);//加载数据
        
		loadHotkeys();
    	
		query();

    	$("#check_column1").change(function () {query();});//注册事件check选中显示栏目 否则隐藏栏目
    	
    	$("#check_column2").change(function () {query();});//注册事件check选中显示栏目 否则隐藏栏目

    });
    
  //计算月份差
    function getMonthNumber(dateStart,dateEnd){
  	  
  	  var yearStart =  dateStart.substr(0,4);var yearEnd =  dateEnd.substr(0,4); 

  	  var monthStart = dateStart.substr(4,2);var monthEnd = dateEnd.substr(4,2);
  	  
  	  var len=(yearEnd-yearStart)*12+(monthEnd-monthStart);
  	  
  	  return len;
	}
  
    // 得到时间区间的月份数
	function getMonthData(dateStart, dateEnd){
    	
		var months = [];months.push(dateStart);if(dateStart == dateEnd) return months;
            
		while(dateStart < dateEnd){
            	
			dateStart++;
            	
			if(dateStart.toString().slice(-2) - 12 >0){dateStart = dateStart.toString().slice(0,4)*1+1+"01";dateStart = dateStart * 1;}
                
			months.push(dateStart);
                
		}
            
		return months;
    };
    
 	// 得到时间区间的月份数 并且排序
	function getMonthDataSort(dateStart, dateEnd){
 		
		var months = [];
		
		if (dateStart == dateEnd){months.push(dateStart);return months;}
        
        var count_time = dateStart.slice(0, 4) + "01";var start = count_time; //  储存初始数据
        
        while (count_time.toString().slice(-2) * 1 <= 12) { // 遍历月份找出区间日期  从一月份开始
        	
            if (count_time * 1 <= dateEnd && count_time >= dateStart) { months.push(count_time * 1);}
        
            count_time = 100 + count_time * 1;
            
            if (count_time > dateEnd) {start++;count_time = start;}

        }
        
        return months;          
    };
    
 // 得到时间区间的月份数
	function getYearData(dateStart, dateEnd){
	 
			var yearStart =  dateStart.substr(0,4);var yearEnd =  dateEnd.substr(0,4); 
	  	  	
	  	  	var years = [];years.push(yearStart);
    	
            if(yearStart == yearEnd){return years;}

            while(yearStart < yearEnd){yearStart++;yearStart = yearStart * 1;years.push(yearStart);}
            
            return years;
    };

    function query(){//根据表字段进行添加查询条件

		var start_year_month = $("#start_year_month").val();var end_year_month = $("#end_year_month").val();
    	
    	if(!start_year_month){$.ligerDialog.warn('请选择开始区间');}
    	
    	if(!end_year_month){$.ligerDialog.warn('请选择结束区间');}
    	
    	var monthNumber = getMonthNumber(start_year_month,end_year_month);

		if(monthNumber >24){$.ligerDialog.warn('月份跨度最大为24个月!');return false;}

		if(monthNumber < 0){$.ligerDialog.warn('开始区间要小于结束区间');return false;}
		
		if(check_column1_manager.getValue() && check_column2_manager.getValue()){//目标值 同比 同时选中
			
			f_setColumns01(start_year_month,end_year_month);
		
		}else if(check_column1_manager.getValue() && !check_column2_manager.getValue()){//目标值选中 同比不选中
			
			f_setColumns02(start_year_month,end_year_month);
		
		}else if(!check_column1_manager.getValue() && check_column2_manager.getValue()){//目标值不选中 同比选中
			
			f_setColumns03(start_year_month,end_year_month);
		
		}else{//都不选中
			
			f_setColumns04(start_year_month,end_year_month);
		
		}
		
		var arr_months = getMonthData(start_year_month, end_year_month);

		var dept_no = liger.get("dept_no").getValue();
		
		if(!dept_no){dept_no= ".";}
		
		var goal_code = liger.get("goal_code").getValue();
		
		var dim_code = liger.get("dim_code").getValue();
		
		var kpi_code = liger.get("kpi_code").getValue();
		
		var dept_kind_code = liger.get("dept_kind_code").getValue();
		
    	//-----------------------------------------------------------------------------------------------
    	
    	gridRight.options.parms=[];gridRight.options.newPage=1;
        
    	gridRight.options.parms.push({name:'arr_months',value:arr_months}); 
    	
    	gridRight.options.parms.push({name:'start_year_month',value:start_year_month}); 
    	
    	gridRight.options.parms.push({name:'end_year_month',value:end_year_month}); 
    	
    	gridRight.options.parms.push({name:'goal_code',value:goal_code}); 
    	
    	gridRight.options.parms.push({name:'dim_code',value:dim_code}); 
    	
    	gridRight.options.parms.push({name:'kpi_code',value:kpi_code}); 
    	
    	gridRight.options.parms.push({name:'dept_kind_code',value:dept_kind_code}); 
    	
    	gridRight.options.parms.push({name:'dept_id',value:dept_no.split(".")[0]}); 
    	
    	gridRight.options.parms.push({name:'dept_no',value:dept_no.split(".")[1]});
		
    	gridRight.loadData(gridRight.where);//加载查询条件
    	
     }

    function f_setColumns01(start_year_month,end_year_month){ 

    	var yearStart =  start_year_month.substr(0,4);var yearEnd =  end_year_month.substr(0,4); 

		var monthStart = start_year_month.substr(4,2);var monthEnd = end_year_month.substr(4,2);
		
		var columns = [{ display: '科室', name: 'dept_name', align: 'left',width:90 }];
		
		var arr_months = getMonthDataSort(start_year_month, end_year_month);

		var column_m=[];
		
		if(yearStart != yearEnd){
			
			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				column_m.push(
						{display: month.slice(0,4)+"年",columns:[
	            		{display: month.slice(-2)*1 + "月份",columns:[
							{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}	
							},
							{display: '灯',name: 'led'+month,align: 'left',width:25,
								render : function(rowdata, rowindex, value,col) {
									if(!rowdata[col.name]){return '';}
										return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
									}
							},
							{display: '值',name: 'goal'+month,id:'goal'+month, align: 'left',width:45,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}
							}
							]}
	            			]}
	
				)
	            
	        }
		}else {
			
			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				column_m.push(
	            		{display: month.slice(-2)*1 + "月份",columns:[
							{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}
							},
							{display: '灯',name: 'led'+month,align: 'left',width:25,
								render : function(rowdata, rowindex, value,col) {
									if(!rowdata[col.name]){return '';}
										return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
									}
							},
							{display: '值',name: 'goal'+month,id:'goal'+month, align: 'left',width:45,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}
							}
							]}
	
				)
	            
	        }
		}
		
		columns = columns.concat(column_m);
		
		columns.push({ display: '红卡', name: 'count_led', align: 'left',width:90,
			render : function(rowdata, rowindex, value,col) {
				if(rowdata[col.name] < 3){return '';}
				return rowdata[col.name];
			}
		});
		
		gridRight.set('columns', columns);

    }
    
    function f_setColumns03(start_year_month,end_year_month){ 

    	var yearStart =  start_year_month.substr(0,4);var yearEnd =  end_year_month.substr(0,4); 

		var monthStart = start_year_month.substr(4,2);var monthEnd = end_year_month.substr(4,2);
		
		var columns = [{ display: '科室', name: 'dept_name', align: 'left',width:90 }];
		
		var arr_months = getMonthDataSort(start_year_month, end_year_month);

		var column_m=[];
		
		if(yearStart != yearEnd){
			
			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				column_m.push(
						{display: month.slice(0,4)+"年",columns:[
	            		{display: month.slice(-2)*1 + "月份",columns:[
							{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}
							},
							{display: '灯',name: 'led'+month,align: 'left',width:25,
								render : function(rowdata, rowindex, value,col) {
									if(!rowdata[col.name]){return '';}
										return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
									}
							}
							]}
	            			]}
	
				)
	            
	        }
		}else {
			
			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				column_m.push(
	            		{display: month.slice(-2)*1 + "月份",columns:[
							{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
								render : function(rowdata, rowindex,value,col) {
									if(!rowdata[col.name]){return '';}
									else{return rowdata[col.name];}
								}	
							},
							{display: '灯',name: 'led'+month,align: 'left',width:25,
								render : function(rowdata, rowindex, value,col) {
									if(!rowdata[col.name]){return '';}
										return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
									}
							}
							]}
	
				)
	            
	        }
		}
		
		columns = columns.concat(column_m);
		
		columns.push({ display: '红卡', name: 'count_led', align: 'left',width:90,
			render : function(rowdata, rowindex, value,col) {
				if(rowdata[col.name] < 3){return '';}
				return rowdata[col.name];
			}
		});
		
		gridRight.set('columns', columns);

    }
    
    function f_setColumns02(start_year_month,end_year_month){ 

    	var yearStart =  start_year_month.substr(0,4);var yearEnd =  end_year_month.substr(0,4); 

		var monthStart = start_year_month.substr(4,2);var monthEnd = end_year_month.substr(4,2);
		
		var columns = [{ display: '科室', name: 'dept_name', align: 'left',width:90 }];
		
		var arr_months = getMonthData(start_year_month, end_year_month);var arr_years = getYearData(start_year_month, end_year_month);

		for(var i = 0; i < arr_years.length; i++){
			
			var year = arr_years[i].toString();var column_m=[];

			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				if(year == month.slice(0,4)){
					
					column_m.push(
		            		
		            		{display: month.slice(-2)*1 + "月份",columns:[
								{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
									render : function(rowdata, rowindex,value,col) {
										if(!rowdata[col.name]){return '';}
										else{return rowdata[col.name];}
									}
								},
								{display: '灯',name: 'led'+month,align: 'left',width:25,
									render : function(rowdata, rowindex, value,col) {
										if(!rowdata[col.name]){return '';}
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
								},
								{display: '值',name: 'goal'+month,id:'goal'+month, align: 'left',width:45,
									render : function(rowdata, rowindex,value,col) {
										if(!rowdata[col.name]){return '';}
										else{return rowdata[col.name];}
									}
								}
								]}
		
		                )
				}
	            
	        }
			if(yearStart != yearEnd){columns.push({display: year+"年",columns:column_m});}
			else{columns = columns.concat(column_m);}
		}
		
		columns.push({ display: '红卡', name: 'count_led', align: 'left',width:90,
			render : function(rowdata, rowindex, value,col) {
				if(rowdata[col.name] < 3){return '';}
				return rowdata[col.name];
			}
		});
		
		gridRight.set('columns', columns);

    } 

    function f_setColumns04(start_year_month,end_year_month){ 

    	var yearStart =  start_year_month.substr(0,4);var yearEnd =  end_year_month.substr(0,4); 

		var monthStart = start_year_month.substr(4,2);var monthEnd = end_year_month.substr(4,2);
		
		var columns = [{ display: '科室', name: 'dept_name', align: 'left',width:90 }];
		
		var arr_months = getMonthData(start_year_month, end_year_month);var arr_years = getYearData(start_year_month, end_year_month);

		for(var i = 0; i < arr_years.length; i++){
			
			var year = arr_years[i].toString(), column_m=[];

			for(var j = 0; j < arr_months.length; j++){
				
				var month = arr_months[j].toString();

				if(year == month.slice(0,4)){
					
					column_m.push(
		            		
		            		{display: month.slice(-2)*1 + "月份",columns:[
								{display: 'KPI',name: 'kpi'+month,align: 'left',width:60,
									render : function(rowdata, rowindex,value,col) {
										if(!rowdata[col.name]){return '';}
										else{return rowdata[col.name];}
									}	
								},
								{display: '灯',name: 'led'+month,align: 'left',width:25,
									render : function(rowdata, rowindex, value,col) {
										if(!rowdata[col.name]){return '';}
											return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata[col.name]+"' border='0' width ='50px' />";
										}
								}
								]
		            		}
		
		                )
				}
	            
	        }
			if(yearStart != yearEnd){columns.push({display: year+"年",columns:column_m});}
			else{columns = columns.concat(column_m);}
		}
		
		columns.push({ display: '红卡', name: 'count_led', align: 'left',width:90,
			render : function(rowdata, rowindex, value,col) {
				if(rowdata[col.name] < 3){return '';}
				return rowdata[col.name];
			}
		});
		
		gridRight.set('columns', columns);

    } 

    function loadRightGrid(){
    	gridRight = $("#rightgrid").ligerGrid({
           columns: [
                    ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmDeptReportGrid.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,enabledEdit : false,isScroll:true,delayLoad:true
                   });

        gridManager = $("#rightgrid").ligerGetGridManager();
        gridRight.gridview2.width("100%");
        
    }

    function toobar(){
    	
    	var obj = [];
    	
    	obj.push({text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
    	
    	obj.push({line:true });
    	
    	$("#toptoolmod").ligerToolBar({items: obj});
    }
   
    function loadDict(){//字典下拉框
    	
		loadComboBox({id:"#goal_code",url:"../quertPrmGoalDict.do?isCheck=false&prem_data=true",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'260',defaultSelect:true,async:false,
    		
    		selectEvent:function(value){
    			
    			loadComboBox({id:"#dim_code",url:"../queryPrmKpiDim.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',defaultSelect:true,async:false,
    			
    				selectEvent:function(value){
    	    			
    					var fromData={
    	                		
    							dim_code:liger.get("dim_code").getValue(),
    							
    							goal_code:liger.get("goal_code").getValue(),
    	                
    	            	}	

    					loadComboBox({id:"#kpi_code",url:"../queryPrmDeptKpi.do?isCheck=false&is_last=1",value:"id",text:"text",autocomplete:true,parms:fromData,hightLight:true,selectBoxWidth:'auto',defaultSelect:true,async:false});
        			}
    			
    			});
    			
			}
    	});
		
		loadComboBox({id:"#dept_kind_code",url:"../queryPrmDeptKind.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'172',width:'172',defaultSelect:false,async:false,
			
			selectEvent:function(value){
    			
				var fromData={
                		
						dept_kind_code:value
                
            	}	

				loadComboBox({id:"#dept_no",url:"../queryPrmDeptDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,parms:fromData,hightLight:true,selectBoxWidth:'auto',defaultSelect:false,async:false});
			}
		
		});

		$("#dept_no").ligerComboBox({width : 160});

		$("#start_year_month").ligerTextBox({width : 80});
		
		liger.get("start_year_month").wrapper.css({"display":"inline-block","verticalAlign": "middle"});
		
    	autodate("#start_year_month","yyyymm","year_first");
		
    	$("#end_year_month").ligerTextBox({width : 80});
    	
		liger.get("end_year_month").wrapper.css({"display":"inline-block","verticalAlign": "middle"});
		
    	autodate("#end_year_month","yyyymm","year_last");

	}  
	 //键盘事件
	function loadHotkeys(){
		 
		hotkeys('Q',query);
		
	}

    </script>


</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar">
	   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
	 	  	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效区间：</td>
            <td align="left" class="l-table-edit-td">
	            <input name="start_year_month" type="text" id="start_year_month"   validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
	            <span style="vertical-align: middle;">-</span>
	            <input name="end_year_month" type="text" id="end_year_month"   validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维度名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dim_code" type="text" id="dim_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">KPI指标：</td>
			<td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
        </tr>
        <tr>
	 	  	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">
				<input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />目标值
				<input name="check_column2" type="checkbox" id="check_column2" ltype="text"  />同比
            </td>
            <td align="left"></td>
            
            
        </tr> 
    </table>
	</div>
   	<div id="toptoolmod"></div>

   	<div position="right">
		<div id="rightgrid"></div>
	</div>
	
</body>
</html>
