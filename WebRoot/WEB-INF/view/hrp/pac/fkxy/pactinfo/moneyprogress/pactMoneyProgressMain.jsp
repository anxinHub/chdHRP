<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'dept_no', value: $("#dept_no").val() },
                { name: 'emp_id', value: $("#emp_id").val() },
                { name: 'opp_emp', value: $("#opp_emp").val() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false&FKXY_Attr='+"01",defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
            emp_id = $("#emp_id").etSelect({  url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false', defaultValue: "none"});
            dept_no = $("#dept_no").etSelect({  url: '../../../basicset/select/queryDeptSelect.do?isCheck=false', defaultValue: "none"});
            
          }
        
        var initGrid = function () {
            var columns = [
            	 { display: '协议编号', name: 'pact_code', width: '150px'},
                 { display: '协议名称', name: 'pact_name', width: '190px'},
                 { display: '协议类别', name: 'pact_type_name',  width: '190px'},
                 { display: '供应商', name: 'sup_name',  width: '190px'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                 { display: '签订科室', name: 'dept_name',  width: '120px'},
                 { display: '开始日期', name: 'start_date', align: 'center', width: '80px'},
                 { display: '截止日期', name: 'end_date', align: 'center', width: '80px'},
                 { display: '协议总金额', name: 'amount_money', align: 'center', width: '80px',render:changeValue,type: 'float'},
                 { display: '入库金额', name: 'pact_money', align: 'center', width: '80px'},
                 { display: '执行进度', name: 'exeAnalysis', align: 'center', width: '80px', render:toPercent,type: 'float',editor: { type: 'float' }},
            ];
            var paramObj = {
            	editable: false,
            	height: '99%',
            	width:'99%',
            	checkbox: true,
                dataModel: {
                   url: 'queryPactMoneyProgress.do?isCheck=false&is_init='+0+'&FKXY_Attr='+"01"
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        function changeValue(rowData,index,value){
        	var amountmoney =rowData.rowData.amount_money;
     	    return formatNumber(amountmoney,2,1);
        }
        
        function toPercent(rowData,index,value) { 
        	var analysis =rowData.rowData.exeAnalysis;
            if (analysis==null){
           	 return '<span style="color: red">'+(Math.round(0 * 10000) / 100.00 + "%")+'</span>';// 小数点后两位百分比
            }else{
           	 return '<span style="color: red">'+(Math.round(analysis * 10000) / 100.00 + "%")+'</span>';// 小数点后两位百分比
            } 
       	}
        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fM-fd",
    		  	onChange: function (date) {
    		  		var end = endpicker.getValue();
    		  		if(end < date){
    		  			endpicker.setValue(end);
    		  		}
    		  	}
    		});
    		endpicker = $("#end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = startpicker.getValue();
    		  		if(start > date){
    		  			endpicker.setValue(start);
    		  		}
    		  	}
    		});
	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">签订日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">协议类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">协议编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">协议名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><select id="dept_no" type="text" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">甲方负责人：</td>
            <td class="ipt"> <select id="emp_id" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">乙方负责人：</td>
            <td class="ipt"><input id="opp_emp" type="text" /> </td>
        	
        </tr>
    </table>
    <div id="mainGrid" ></div>
</body>

</html>

