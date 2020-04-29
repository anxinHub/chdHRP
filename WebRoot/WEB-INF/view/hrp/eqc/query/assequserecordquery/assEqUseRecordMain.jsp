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
	var grid, analysis_codeSelect,ur_eq_groupSelect,charge_kindSelect,charge_itemSelect,unit_codeSelect,dept_codeSelect,use_dateSelect,end_dateSelect;
	var year_input ,month_input;
	$(function () {
		loadDict();
	    initGrid();
	    
	})
    function query() {
        params = [
                  { name: 'analysis_code', value: analysis_codeSelect.getValue() },
                  { name: 'charge_kind_id', value: charge_kindSelect.getValue() },
                  { name: 'price', value: $("#price").val() },
                  { name: 'year', value: $("#year").val() },
                  { name: 'month', value: $("#month").val() },
                  { name: 'work_load_num', value: $("#work_load_num").val() },
                  //{ name: 'unit_code', value: unit_codeSelect.getValue()},
                  { name: 'use_date', value: $("#use_date").val() },
                  { name: 'dept_code', value: dept_codeSelect.getValue() },
                  { name: 'remark', value: $("#remark").val() },
                  { name: 'patient_id', value: $("#patient_id").val()},
                  { name: 'use_date', value: use_dateSelect.getValue()},
                  { name: 'end_date', value: end_dateSelect.getValue()}
        ];
        grid.loadData(params);
    };
        
	function initGrid() {
            var columns = [
                 {display: '分析项', name: 'analysis_name',width: '5%'  },           	
	         	 {display: '工作量', name: 'work_load_num', width: '8%',editor: {type: 'float'} },
	         	 {display: '工作量单位', name: 'unit_name',align: 'center', width: '7%'},
				 {display: '单价', name: 'price', align: 'left', width: '10%'},
				 {display: '收取费用', name: 'total_fee', align: 'left', width: '10%'},
	         	 {display: '开始使用日期', name: 'use_date', width: '8%',editor: {type: 'date',} },
	         	 {display: '开始使用时间', name: 'start_time', width: '8%',editor: {type: 'date',} },
	         	 {display: '结束使用日期', name: 'end_date', width: '8%',editor: {type: 'date'}  },
	         	 {display: '结束使用时间', name: 'end_time', width: '8%',editor: {type: 'date'} },
            	 {display: '使用科室', name: 'dept_name',width: '10%',  },
				 {display: '患者信息', name: 'patient_name', align: 'left', width: '8%' },
	         	 {display: '手工录入标识', name: 'is_input_flag', align: 'center', width: '7%'  },
				 {display: '服务项目', name: 'charge_kind_name',width: '10%'  },		
	         	 {display: '备注', name: 'remark', align: 'left', width: '10%'},
	         	 {display: '状态', name: 'status_name',align: 'center', width: '7%'},
            ];
            var gtoolbar = {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    ]
			};
            var paramObj = {
            	height: '97%',
            	width:'100%',
            	checkbox: true,editable:true,
                dataModel: {
                    url: 'queryEqUseMain.do?isCheck=false'
                },
                columns: columns,
               	toolbar: gtoolbar
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
		     
        function loadDict(){
        	
        	year_input = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: query
            });
        	month_input = $("#month").etDatepicker({
                view: "months",
                minView: "months",
                dateFormat: "mm",
                clearButton: false,
                onChange: query
                  
            });
			analysis_codeSelect = $("#analysis_code").etSelect({
            	 url: "../queryAssAnalysisObject.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});         
            use_dateSelect = $("#use_date").etDatepicker({
                dateFormat: "yyyy-mm-dd",
                onChange: query
                  
            });
			dept_codeSelect = $("#dept_code").etSelect({
				url: "../queryDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			}); 
            charge_kindSelect = $("#charge_kind_id").etSelect({
				url: "../queryCostChargeKind.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});       
            end_dateSelect = $("#end_date").etDatepicker({
                dateFormat: "yyyy-mm-dd",
                onChange: query
                  
            });
        
        }
    </script>
</head>

<body>
    <table class="table-layout">
    	<tr>
            <td class="label" style="width: 100px;">年</td>
            <td class="ipt" style="width: 180px;">
                <input id="year" type="text" style="width: 80px"/>月 <input id="month" type="text" style="width: 80px"/>
            </td>
    		<td class="label" style="width: 100px;">分析项:</td>
            <td class="ipt">
                <select id="analysis_code"  style="width: 180px" type="text" ></select>
            </td>
			<td class="label" style="width: 100px;">开始日期:</td>
            <td class="ipt">
                 <input id="use_date" style="width: 180px" type="text"/>
            </td>  
    	</tr>
    	<tr>
    		<td class="label" style="width: 100px;">使用科室:</td>
            <td class="ipt">
                <select id="dept_code" type="text" style="width: 180px" ></select>
            </td> 
    		<td class="label" style="width: 100px;">服务项目:</td>
            <td class="ipt">
                <select id="charge_kind_id" type="text" style="width: 180px" ></select>
            </td> 
			<td class="label" style="width: 100px;">结束日期:</td>
            <td class="ipt">
                 <input id="end_date" style="width: 180px" type="text"/>
            </td>
    	</tr>
    	
    </table>
    <div id="mainGrid"></div>
</body>

</html>

