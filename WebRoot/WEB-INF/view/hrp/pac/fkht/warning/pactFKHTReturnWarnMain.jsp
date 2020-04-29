<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'dept_no', value: $("#dept_no").val() },
                { name: 'emp_id', value: $("#emp_id").val() },
                { name: 'opp_emp', value: $("#opp_emp").val() },
                { name: 'warn_ret_state', value: $("#warn_ret_state").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',async:false,defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({ url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',async:false,defaultValue: "none"});
            dept_no = $("#dept_no").etSelect({ 
            	url: '../../basicset/select/queryDeptSelect.do?isCheck=false',
            	async:false,
            	defaultValue: "none",
          		onItemAdd:function(value, $item){
          			first_man.reload({url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false&dept_no='+value})
        		}
            });
            emp_id = $("#emp_id").etSelect({url: '../../basicset/select/queryHosEmpDictSelect.do?isCheck=false&dept_no='+dept_no.getValue(),async:false,defaultValue: "none"});
            warn_ret_state = $("#warn_ret_state").etSelect({
	           	options: [
	           	    { id: 0, text: '近期' },
	           	    { id: 1, text: '到期' },
	           	    { id: 2, text: '过期' }
	           	  ],
	       		defaultValue: "none"
	       	});
          }
        
        var initGrid = function () {
            var columns = [
            	 { display: '合同类别', name: 'type_name', width: '100px'},
            	 { display: '合同编号', name: 'pact_code', width: '150px'},
                 { display: '合同名称', name: 'pact_name', width: '190px'},
                 { display: '供应商', name: 'sup_name',  width: '190px'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                 { display: '签订科室', name: 'dept_name',  width: '120px'},
                 { display: '合同金额', name: 'pact_money', align: 'right', width: '90px'},
                 { display: '执行金额', name: 'pay_money', align: 'right', width: '90px'},
                 { display: '执行进度(%)', name: '', align: 'center', width: '90px',
                	render:function(date){
                		var obj = date.rowData;
                		if(obj.pact_money){
                			return parseInt((obj.pay_money/obj.pact_money)*100)
                		}
                	}	 
                 },
                 { display: '结束日期', name: 'end_date', align: 'center', width: '100px'},
                 { display: '保证金金额', name: 'ret_money',  align: 'center', width: '100px'},
                 { display: '保证金收取日期', name: 'ret_date',  align: 'center', width: '100px'},
                 { display: '保证金计划归还日期', name: 'ret_plan_date',  align: 'center', width: '100px'},
                 { display: '到期提醒(天)', name: 'warning_ret_day',  align: 'center', width: '100px'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                 dataModel: {
                    url: 'queryPactMainFKHTForRetWarning.do?isCheck=false'
                }, 
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
        	$("#mainGrid").on('click','.getSouse',function(){
              	 var rowIndex = $(this).attr('rowIndex');
                   var currentRowData = grid.getAllData()[rowIndex];
                   toResource(currentRowData);
        	})
        };
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "合同索赔",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService",
   					method_name:"queryPactMainFKHTForRetWarningPrint",
   					bean_name:"pactMainFKHTService",
	   				pact_type_code : $("#pact_type_code").val(),
	                sup_no : $("#sup_no").val(),
	                pact_code : $("#pact_code").val(),
	                pact_name : $("#pact_name").val(),
	                dept_no : $("#dept_no").val(),
	                hos_source : $("#hos_source").val(),
	                warn_ret_state : $("#warn_ret_state").val(),
	                start_date : startpicker.getValue(),
	                end_date : endpicker.getValue(),
	                emp_id:$("#emp_id").val() ,
	                opp_emp:$("#opp_emp").val() ,
               };
               officeGridPrint(printPara);
           };
        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
        		defaultDate: "yyyy-fm-fd",
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
            <td class="label" style="width: 100px;">合同类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><select id="dept_no" style="width: 180px"></select></td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">甲方负责人：</td>
            <td class="ipt"> <select id="emp_id" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">乙方负责人：</td>
            <td class="ipt"><input id="opp_emp" type="text" /> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"> <select id="warn_ret_state" style="width: 180px"></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

