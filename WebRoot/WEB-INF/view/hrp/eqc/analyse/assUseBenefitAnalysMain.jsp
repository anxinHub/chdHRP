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
	var grid, yearSelect,monthSelect,analysis_code,dept_idSelect;
	$(function () {
		loadDict();
	    initGrid();
	})
    function query() {
		if(yearSelect.getValue()){
			
		}
        params = [
			{ name: 'year', value: yearSelect.getValue() },         
			{ name: 'month', value: monthSelect.getValue() },
            { name: 'analysis_code', value: analysis_code.getValue() },         
            { name: 'dept_id', value: dept_idSelect.getValue() }
            
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
            	{display: '设备名称', name: 'analysis_name',width: '10%'},
	         	{display: '设备规格', name: 'ass_spec', width: '10%'},
	         	{display: '数量', name: 'amount', width: '10%'},
	         	{display: '设备采购单价', name: 'price',width: '10%',
	         		render : function(ui){
	        			if(ui.rowData.price){
	        				return formatNumber(ui.rowData.price,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}	
	         	}, 
	         	{display: '设备采购总价', name: 'money', width: '10%',
	         		render : function(ui){
	        			if(ui.rowData.monney){
	        				return formatNumber(ui.rowData.monney,2,1);
	        			}else{
	        				return 0.00 ;
	        			}
	        		}	
	         	},
	         	{display: '设备使用日期', name: 'use_date', width: '10%'},
	         	{display: '使用科室', name: 'dept_name',width: '12%'},
	         	{display: '折旧年限', name: 'acc_depre_amount', width: '10%'},
	         	{display: '就诊人次(人次)', columns:
	                [{ display: '投决累计', name: 'use_num', width:'10%'} ,
	                 { display: '实际累计', name: 'use_num_sum', width:'10%'} ,
	                 { display: '上年全年累计', name: 'pre_use_num', width:'10%'} ,
	                 { display: '本年全年预算', name: 'budg_num', width:'10%'} ,
	                 { display: '本年实际累计', name: 'year_use_num', width:'10%'} 
	                ]
	         	},
	         	{display: '设备产生的业务收入', columns:
	                [{ display: '投决累计', name: 'income', width:'10%',
	                	 render : function(ui){
	            			 if(ui.rowData.income){
	            				 return formatNumber(ui.rowData.income,2,1);
	            			 }else{
	            				 return 0.00 ;
	            			 }
	            		 }	
	                 } ,
	                 { display: '实际累计', name: 'income_sum', width:'10%',
	                	 render : function(ui){
	            			 if(ui.rowData.income_sum){
	            				 return formatNumber(ui.rowData.income_sum,2,1);
	            			 }else{
	            				 return 0.00 ;
	            			 }
	            		 }	 
	                 } ,
	                 { display: '上年全年累计', name: 'pre_income_sum', width:'10%',
	                	 render : function(ui){
	            			 if(ui.rowData.pre_income_sum){
	            				 return formatNumber(ui.rowData.pre_income_sum,2,1);
	            			 }else{
	            				 return 0.00 ;
	            			 }
	            		 }	
	                 } ,
	                 { display: '本年全年预算', name: 'budg_income', width:'10%',
	                	 render : function(ui){
	            			 if(ui.rowData.budg_income){
	            				 return formatNumber(ui.rowData.budg_income,2,1);
	            			 }else{
	            				 return 0.00 ;
	            			 }
	            		 }
	                 } ,
	                 { display: '本年实际累计', name: 'year_income', width:'10%',
	                	 render : function(ui){
	            			 if(ui.rowData.year_income){
	            				 return formatNumber(ui.rowData.year_income,2,1);
	            			 }else{
	            				 return 0.00 ;
	            			 }
	            		 }
	                 } 
	                ]
	         	}
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryAssUseBenefitAnalys.do'
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
        	yearSelect = $("#year").etDatepicker({
	            view: "years",
	            minView: "years",
	            dateFormat: "yyyy",
	            defaultValue: true,
	            clearButton: true,
	            onChange: query
	              
	       });
	       monthSelect = $("#month").etDatepicker({
	           view: "months",
	           minView: "months",
	           dateFormat: "mm",
	           clearButton: true,
	           onChange: query
	             
	       }); 
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
        	<td class="label" style="width: 100px;">年度:</td>
            <td class="ipt">
                <input id="year" style="width: 180px" type="text"/>
            </td>
        	<td class="label" style="width: 100px;">月份:</td>
            <td class="ipt">
                <input id="month" style="width: 180px" type="text"/>
            </td>
            <td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>          
            <td class="label" style="width: 100px;">使用科室:</td>
            <td class="ipt">
                <select id="dept_id" type="text" style="width: 180px" ></select>
            </td>       
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

