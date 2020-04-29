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
                  { name: 'charge_item_id', value: charge_kindSelect.getValue() },
                  { name: 'price', value: $("#price").val() },
                  { name: 'work_load_num', value: $("#work_load_num").val() },
                  //{ name: 'unit_code', value: unit_codeSelect.getValue()},
                  { name: 'use_date', value: $("#use_date").val() },
                  { name: 'year', value: $("#year").val() },
                  { name: 'month', value: $("#month").val() },
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
				 {display: '服务细项', name: 'charge_item_name',width: '10%'  },		
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
                    url: 'queryEqUseDetail.do?isCheck=false'
                },
                columns: columns,
               	toolbar: gtoolbar
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        
		// 数据校验
		/* function validateGrid(data) {
	        var msg = "";
	        var rowm = "";
	        //判断grid 中的数据是否重复或者为空
	        var targetMap = new HashMap();
	        $.each(data, function(i, v) {
	            rowm = "";
	            if (!v.analysis_code) {
	                rowm += "[分析项]、";
	            }	           
	            if (!v.use_date) {
	                rowm += "[开始日期]、";
	            }
	            if (!v.start_time) {
	                rowm += "[开始时间]、";
	            }
	            if (!v.work_load_num) {
	                rowm += "[工作量]、";
	            }
	            if (!v.unit_code) {
	                rowm += "[工作量单位]、";
	            }
	            if (!v.dept_code) {
	                rowm += "[使用科室]、";
	            }
	            if (!v.price) {
	                rowm += "[单价]、";
	            }
	            if (!v.total_fee) {
	                rowm += "[收取费用]、";
	            }
	            if (!v.year) {
	                rowm += "[年]、";
	            }
	            if (!v.month) {
	                rowm += "[月]、";
	            }
	            if (!v.charge_kind_id) {
	                rowm += "[服务项目]、";
	            }
	            if (!v.ex_id) {
	                rowm += "[来源ID]、";
	            }
	            if (!v.busi_data_source_code) {
	                rowm += "[系统来源]、";
	            }
	            
	            
	            if (rowm != "") {
	                rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空;" + "\n\r";
	            }
	            msg += rowm ;
	            /* var key = v.analysis_code + v.ur_eq_group + v.user_id
	            var value = "第" + (Number(v._rowIndx) + 1) + "行";
	            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
	                targetMap.put(key, value);
	            } else {
	                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
	            } */
	      /*  });
	        if (msg != "") {
	            $.etDialog.warn(msg);
	            return false;
	        } else {
	            return true;
	        }
	    } */
		
		     
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
            charge_kindSelect = $("#charge_item_id").etSelect({
				url: "../queryCostChargeItem.do?isCheck=false",
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
    		<td class="label" style="width: 100px;">服务细项:</td>
            <td class="ipt">
                <select id="charge_item_id" type="text" style="width: 180px" ></select>
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

