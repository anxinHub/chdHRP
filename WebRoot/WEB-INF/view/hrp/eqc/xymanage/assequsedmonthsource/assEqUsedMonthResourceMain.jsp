<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
<jsp:param value="grid,select,dialog,checkbox,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/map.js"></script>
<script>
	var grid, year_input, month_input ,analysis_codeSelect , unit_codeSelect,oresource_codeSelect,dept_codeSelect;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
			{ name: 'startyear', value: start_year_input.getValue() },
			{ name: 'endyear', value: end_year_input.getValue() }, 
			{ name: 'analysis_code', value: analysis_codeSelect.getValue() },
			{ name: 'oresource_code', value: oresource_codeSelect.getValue() },
            { name: 'dept_code', value: dept_codeSelect.getValue().split(",")[0] }
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
				 {display: '年', name: 'year',width: '5%',editable:setEdit ,
						 editor: {
							type: 'select',
							keyField:'year',
							url:"../queryAssYear.do?isCheck=false" ,
							keySupport: true,
							autocomplete: true,
						},
				 },
				 {display: '月', name: 'month_name',width: '7%',editable:setEdit ,
						 valueField: 'id', textField: 'text',
						 editor: {
							type: 'select',
							keyField: 'month',
							source: [{ "id": "01", "text": "01月",label:"01月"}, 
							         { "id": "02", "text": "02月",label:"02月"}, 
							         { "id": "03", "text": "03月",label:"03月"}, 
							         { "id": "04", "text": "04月",label:"04月"}, 
							         { "id": "05", "text": "05月",label:"05月"}, 
							         { "id": "06", "text": "06月",label:"06月"}, 
							         { "id": "07", "text": "07月",label:"07月"}, 
							         { "id": "08", "text": "08月",label:"08月"}, 
							         { "id": "09", "text": "09月",label:"09月"}, 
							         { "id": "10", "text": "10月",label:"10月"}, 
							         { "id": "11", "text": "11月",label:"11月"}, 
							         { "id": "12", "text": "12月",label:"12月"}, 
							      ],
							keySupport: true,
							autocomplete: true,
						},
				 },        
            	 {display: '分析项', name: 'analysis_name',width: '12%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'analysis_code',
	           		     url:"../queryAssAnalysisObject.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '其他资源', name: 'oresource_code_name',width: '8%', editable:setEdit ,
            		 editor: {
	           		     type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'oresource_code',
	           		     url:"../queryAssEqcResourceType.do?isCheck=false"
	           		 }	 
            	 },
            	 {display: '单价', name: 'price', width: '8%',align: 'right',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.price){
            				 return formatNumber(ui.rowData.price,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 },
            	 {display: '单位', name: 'unit_name',align: 'center', width: '9%',
            		 editor: {
            			 type: 'select',  //编辑框为下拉框时
	           		  	 keyField: 'unit_code',
	           		     url:"../queryHosUnit.do?isCheck=false"
            			 
            		 }
            	 },
            	 {display: '数量', name: 'quantity',align: 'center', width: '9%', editor: { type: 'float' }},
            	 {display: '金额', name: 'amount',align: 'right', width: '10%',editor: {type: 'float'},
            		 render : function(ui){
            			 if(ui.rowData.amount){
            				 return formatNumber(ui.rowData.amount,2,1);
            			 }else{
            				 return 0.00 ;
            			 }
            		 }
            	 }
            	 
	         	 
            ];
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:false,
                dataModel: {
                    url: 'queryAssEqUsedMonthResource.do'
                },
                /* rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    update(rowData);
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
     	// 根据 group_id 是否存在 返回 true 或 false  控制  收费价格 单元格可否编辑 用  
        function setEdit(ui){
       		 if(ui.rowData && ui.rowData.group_id){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
        }
        
        
		     
        function loadDict(){
        	start_year_input = $("#startyear").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "yyyy-mm",
                defaultDate: true, // 默认显示日期
                clearButton: false
                  
            });
        	end_year_input = $("#endyear").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "yyyy-mm",
               // defaultDate: true, // 默认显示日期
                clearButton: false
                  
            });
        	analysis_codeSelect = $("#analysis_code").etSelect({
 				url: "../queryAssAnalysisObject.do?isCheck=false",
 				defaultValue: "none",
 				onChange: query
 			});
            oresource_codeSelect = $("#oresource_code").etSelect({
            	defaultValue: "none",
            	url:"../queryAssEqcResourceType.do?isCheck=false",
     			onChange: query
            });
         
            dept_codeSelect = $("#dept_code").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
            
          
        }
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
       	   <td class="label">起始年月：</td>
            <td class="ipt">
                <input type="text" id="startyear" />
            </td>
             <td class="label">至：</td>
            <td class="ipt">
                <input type="text" id="endyear" />
            </td>
            <td class="label" style="width: 100px;">使用科室:</td>
             <td class="ipt">
                 <input id="dept_code" style="width: 180px" type="text"/>
            </td>
        	
        </tr>
        <tr>
        <td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>
        	<td class="label" style="width: 100px;">资源类型:</td>
            <td class="ipt">
                <input id="oresource_code" type="text" style="width: 180px" />
            </td> 
            
            
        </tr>
         
    </table>
    <div id="mainGrid"></div>
</body>

</html>

