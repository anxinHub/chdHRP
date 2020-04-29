<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, analysis_code,dept_idSelect,start_month,end_month;
	$(function () {
		loadDict();
	    initGrid();
	})
    function query() {
        params = [
            { name: 'analysis_code', value: analysis_code.getValue() },         
            { name: 'dept_id', value: dept_idSelect.getValue() },
            { name: 'start_month', value: start_month.getValue()},
            { name: 'end_month', value: end_month.getValue() }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '科室', name: 'dept_name',width: '10%'},            	
             	 {display: '设备名称', name: 'analysis_name',width: '8%'},
 	         	 {display: '规格型号', name: 'ass_spec', width: '8%'},
 	         	 {display: '编号', name: 'card_id', width: '8%'},
 	         	 {display: '购入日期', name: 'begin_month', width: '8%'},
            	 {display: '设备原值', name: 'purchase_money',width: '8%',
                	 render : function(ui){
            			 if(ui.rowData.purchase_money){
            				 return formatNumber(ui.rowData.purchase_money,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }   	 
            	 },
            	 {display: '月份', name: 'use_month', width: '8%'},
	         	 {display: '使用标准率(%)', name: 'use_point', width: '8%'},
	         	 {display: '检查量', name: 'check_amount', width: '8%'},
	         	 {display: '曝光次数', name: 'exposure_name', width: '8%'},
	         	 {display: '收入', name: 'income', width:'8%',
                	 render : function(ui){
            			 if(ui.rowData.income){
            				 return formatNumber(ui.rowData.income,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
                 } ,
	             { display: '支出', align: 'center' ,columns:
                     [
                         { display: '耗材', columns:[
                                                 { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                               	  render : function(rowdata, rowindex,value) {
                             							 return formatNumber(rowdata.t_3,2,1);
                             						}   
                                                 }, 
                            	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                               	  render : function(rowdata, rowindex,value) {
                             							 return formatNumber(rowdata.t_4,2,1);
                             						}   
                            	                   }  
                                                   ] 
                         }, 
                         { display: '人员',  columns:[
                                                 { display: '金额', name: 't_5', align: 'right', width: '9%',formatter:'###,##0.00',
                               						render : function(rowdata, rowindex,value) {
                                							 return formatNumber(rowdata.t_5,2,1);
                                						}  
                                                 }, 
                               	               { display: '构成比', name: 't_6', width: '9%', align: 'right',formatter:'###,##0.00',
                             						render : function(rowdata, rowindex,value) {
                             							 return formatNumber(rowdata.t_6,2,1);
                             						}

                               	                 }  
                                                      ] 
                         } ,
                         { display: '水电', columns:[
                                                   { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                 	  render : function(rowdata, rowindex,value) {
                               							 return formatNumber(rowdata.t_3,2,1);
                               						}   
                                                   }, 
                              	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                 	  render : function(rowdata, rowindex,value) {
                               							 return formatNumber(rowdata.t_4,2,1);
                               						}   
                              	                   }  
                                                     ] 
                           }, 
                           { display: '维修', columns:[
                                                     { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                   	  render : function(rowdata, rowindex,value) {
                                 							 return formatNumber(rowdata.t_3,2,1);
                                 						}   
                                                     }, 
                                	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                   	  render : function(rowdata, rowindex,value) {
                                 							 return formatNumber(rowdata.t_4,2,1);
                                 						}   
                                	                   }  
                                                       ] 
                             }, 
                             { display: '保养', columns:[
                                                       { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                     	  render : function(rowdata, rowindex,value) {
                                   							 return formatNumber(rowdata.t_3,2,1);
                                   						}   
                                                       }, 
                                  	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                     	  render : function(rowdata, rowindex,value) {
                                   							 return formatNumber(rowdata.t_4,2,1);
                                   						}   
                                  	                   }  
                                                         ] 
                               }, 
                               { display: '计量', columns:[
                                                         { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                       	  render : function(rowdata, rowindex,value) {
                                     							 return formatNumber(rowdata.t_3,2,1);
                                     						}   
                                                         }, 
                                    	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                       	  render : function(rowdata, rowindex,value) {
                                     							 return formatNumber(rowdata.t_4,2,1);
                                     						}   
                                    	                   }  
                                                           ] 
                                 }, 
                                 { display: '设备折旧', columns:[
                                                           { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                         	  render : function(rowdata, rowindex,value) {
                                       							 return formatNumber(rowdata.t_3,2,1);
                                       						}   
                                                           }, 
                                      	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                         	  render : function(rowdata, rowindex,value) {
                                       							 return formatNumber(rowdata.t_4,2,1);
                                       						}   
                                      	                   }  
                                                             ] 
                                   },
                                   { display: '房屋折旧', columns:[
                                                               { display: '金额', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                             	  render : function(rowdata, rowindex,value) {
                                           							 return formatNumber(rowdata.t_3,2,1);
                                           						}   
                                                               }, 
                                          	                   { display: '构成比', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                             	  render : function(rowdata, rowindex,value) {
                                           							 return formatNumber(rowdata.t_4,2,1);
                                           						}   
                                          	                   }  
                                                                 ] 
                                       },
                     ]
                  },                  
 	         	 {display: '支出合计', name: 'all_cost', width:'8%',
                 	 render : function(ui){
             			 if(ui.rowData.income){
             				 return formatNumber(ui.rowData.income,2,1);
             			 }else{
             				 return 0.00 ;
             			 }
             		 }
                  },
                  {display: '利润率', name: 'exposure_name', width: '8%'},
                  {display: '投资回收期', name: 'exposure_name', width: '8%'},
                  {display: '投资回报率', name: 'exposure_name', width: '8%'},
                  {display: '设备使用率(%)', name: 'exposure_name', width: '8%'},
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssIncomAndCost.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        function loadDict(){
        	analysis_code =  $("#analysis_code").etSelect({
				url: "../queryAssAnalysisObject.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})  ;  
        	dept_idSelect =  $("#dept_id").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})  ;    
            start_month = $("#start_month").etDatepicker({
                 view: "months",
                 minView: "months",
                 dateFormat: "mm",
                 clearButton: false,
                 onChange: query
                   
            });
            end_month = $("#end_month").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "mm",
                clearButton: false,
                onChange: query
                  
            });
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>          
            <td class="label" style="width: 100px;">科室:</td>
            <td class="ipt">
                <select id="dept_id" type="text" style="width: 180px" ></select>
            </td>       
         	<td class="label" style="width: 100px;">月份:</td>
            <td class="ipt">
                <input id="start_month" style="width: 70px" type="text"/>到
                <input id="end_month" style="width: 70px" type="text"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

