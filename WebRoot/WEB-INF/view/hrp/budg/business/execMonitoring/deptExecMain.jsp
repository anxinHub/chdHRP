<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
  //设置单元格打印格式
    var printData={
   		sum_year : function(value){ //一月份的预算值
			return formatNumber(value, 2, 1);
		}, 
   		month1 : function(value){ //一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month2 : function(value){ //二月份的预算值
			return formatNumber(value, 2, 1);
		},
		month3 : function(value){ //三月份的预算值
			return formatNumber(value, 2, 1);
		},
		month4 : function(value){ //四月份的预算值
			return formatNumber(value, 2, 1);
		},
		month5 : function(value){ //五月份的预算值
			return formatNumber(value, 2, 1);
		},
		month6 : function(value){ //六月份的预算值
			return formatNumber(value, 2, 1);
		},
		month7 : function(value){ //七月份的预算值
			return formatNumber(value, 2, 1);
		},
		month8 : function(value){ //八月份的预算值
			return formatNumber(value, 2, 1);
		},
		month9 : function(value){ //九月份的预算值
			return formatNumber(value, 2, 1);
		},
		month10 : function(value){ //十月份的预算值
			return formatNumber(value, 2, 1);
		},
		month11 : function(value){ //十一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month12 : function(value){ //十二月份的预算值
			return formatNumber(value, 2, 1);
		}
	};
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input,index_code_select,dept_id_select;
    
    function init() {
		getData("../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged: function (value) {
					query();
				}
			});
		});
		
		index_code_select = $("#index_code_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgIndexDict.do?isCheck=false",
			onChange:query
		});
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgDeptDict.do?isCheck=false",
			onChange:query
		});
	}
    
	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
    
    //查询
    function  query(){
    	if( !year_input.getValue()){
       		$.etDialog.error('预算年度不能为空');
       		return false ;
       	}
    	var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
		];
		//加载查询条件
		grid.loadData(parms, 'queryDeptExecData.do?isCheck=false');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
    		 columns: [ 
                 { display: '年度', name: 'year', align: 'left',width:90,frozen :true,
				 		},
                 { display: '科室编码', name: 'dept_code', align: 'left',width:100,frozen :true,
				 		},
				 { display: '科室名称', name: 'dept_name', align: 'left',width:120,frozen :true,
					 	 },
				 { display: '指标编码', name: 'index_code', align: 'left',width:100,frozen :true,
					     },
				 { display: '指标名称', name: 'index_code', align: 'left',width:120,frozen :true
						 },	 
                 { display: '项目', name: 'item', align: 'right',width:100,frozen :true,
				 		},
				 { display: '本年合计', name: 'sum_year', align: 'right',width:100,frozen :true,
				 			render:function(ui){
				 				if(ui.rowData.b_no == 2){
				 					 return "<a href=javascript:openUpdateSumYear('"+
	                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+ui.rowData.dept_id+"!"+ui.rowData.sum_year
	                    		        + "')>"+ui.rowData.sum_year+"</a>";	
				 				}else if( ui.rowData.b_no == 3){
					 				
					 				return formatNumber(ui.rowData.sum_year,2,1);	
					 				
					 			}else if( ui.rowData.b_no == 4){
					 				
					 				return formatNumber(ui.rowData.sum_year,2,1)+"%";		
					 			}
							} 
						},
                 { display: '1月', name: 'month1', align: 'right',width:100,
							 render:function(ui){
                	 
								 if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
									 ui.rowData.dept_id+"!"+"01"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
								 }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	 }else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	 }
	                    							 
							 }
				 		},
				 { display: '2月', name: 'month2', align: 'right',width:100,
				 			 render:function(ui){
	                    	    if(ui.rowData.b_no == 2){
	                    	    	return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    	    			ui.rowData.dept_id+"!"+"02"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
	                    	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
							 }
				 		},
				 { display: '3月', name: 'month3', align: 'right',width:100,
				 			render:function(ui){
	                    							 
						 		if(ui.rowData.b_no == 2){
						 			 return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
						 					ui.rowData.dept_id+"!"+"03"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
				 			}
					    },
				{ display: '4月', name: 'month4', align: 'right',width:100,
					    	 render:function(ui){
	                    		if(ui.rowData.b_no == 2){
	                    			return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    				ui.rowData.dept_id+"!"+"04"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";	
	                    	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
							 }
					 	},
				{ display: '5月', name: 'month5', align: 'right',width:100,
					 		render:function(ui){
					 			if(ui.rowData.b_no == 2){
					 				 return "<a href=javascript:openUpdate('"+
	                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    					ui.rowData.dept_id+"!"+"05"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    							 
							 }
						 },
				{ display: '6月', name: 'month6', align: 'right',width:100,
							 render:function(ui){
								if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+
	                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    				ui.rowData.dept_id+"!"+"06"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";	
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    		 				 
							 }
						 },
				{ display: '7月', name: 'month7', align: 'right',width:100,
							 render:function(ui){
								if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
									 	ui.rowData.dept_id+"!"+"07"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    							 
							 }
						},
				{ display: '8月', name: 'month8', align: 'right',width:100,
							render:function(ui){
								if(ui.rowData.b_no == 2){
									return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
											ui.rowData.dept_id+"!"+"08"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    		 					 
							 }
						},	
				 { display: '9月', name: 'month9', align: 'right',width:100,
							render:function(ui){
								if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+
	                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    				ui.rowData.dept_id+"!"+"09"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";	
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    						 
							 }
						},
				{ display: '10月', name: 'month10', align: 'right',width:100,
							 render:function(ui){
								 if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+
	                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+
	                    				ui.rowData.dept_id+"!"+"10"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";	
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    		 				 
							 }
						},
				{ display: '11月', name: 'month11', align: 'right',width:100,
							 render:function(ui){
								if(ui.rowData.b_no == 2){
									return "<a href=javascript:openUpdate('"+
                    				ui.rowData.year+"!"+ui.rowData.index_code+"!"+
                    				ui.rowData.dept_id+"!"+"11"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    		 					 
							 }
						},
				{ display: '12月', name: 'month12', align: 'right',width:100,
							 render:function(ui){
								if(ui.rowData.b_no == 2){
									 return "<a href=javascript:openUpdate('"+ui.rowData.year+"!"+ui.rowData.index_code+"!"+
									 ui.rowData.dept_id+"!"+"12"+ "')>"+formatNumber(ui.cellData, 2, 1)+"</a>";
		                	    }else if(ui.rowData.b_no == 4){
						    		 return formatNumber(ui.cellData,2,1) +"%";
						    	}else{
						    		 return formatNumber(ui.cellData,2,1) ;
						    	}
	                    		 					 
							 }
			    }
           ],
           dataMedol: {
		    	method: 'POST',
		    	location: 'remote',
		    	url: '',
		    	recIndx: 'year'
   		   },
   		   usePager: false,
   		   width: '100%',
   		   height: '100%',
   		   checkbox: true,
   		   freezeCols: 7,
   		   toolbar: { 
   	     	  items: [
   	     		{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
   			  ]
   		   },
   	   });
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=printData;
 		grid.options.lodop.title="科室业务预算监控";
    }
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
   
    function openUpdateSumYear(obj){
    	var vo = obj.split("!");
    	var parm = 
			"year="+vo[0]   +"&"+ 
			"index_code="+vo[1]   +"&"+
			"dept_id="+vo[2]   +"&"+
			"sum_year="+vo[3] 
    	$.etDialog.open({
            url: 'budgWorkDeptYearExecuteUpdPage.do?isCheck=false&'+parm,
            title: '预算值链接',
            isMax: true
        });
    }
    
    function openUpdate(obj){
    	var vo = obj.split("!");
    	var parm = 
			"year="+vo[0]   +"&"+ 
			"index_code="+vo[1]   +"&"+ 
			"dept_id="+vo[2]   +"&"+ 
			"month="+vo[3] 
    	$.etDialog.open({
            url: 'budgWorkDeptMonthExecuteUpdPage.do?isCheck=false&'+parm,
            title: '预算值链接',
            isMax: true
        });
	}; 
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
