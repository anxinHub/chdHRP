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
            	{ name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'in_start_date', value: instartpicker.getValue() },
                { name: 'in_end_date', value: inendpicker.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'ass_name', value: $("#ass_name").val() },
                { name: 'ass_type', value: $("#ass_type").val() },
                { name: 'dept_no', value: $("#dept_no").val() },
                { name: 'warning_day', value: $("#warning_day").val() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',async:false,defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',async:false,defaultValue: "none"});
            dept_no = $("#dept_no").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false' ,async:false,defaultValue: "none",});
            ass_type = $("#ass_type").etSelect({url: '../../basicset/select/queryAssTypeDictSelect.do?isCheck=false',async:false,defaultValue: "none",});
          }
        
        var initGrid = function () {
            var columns = [
                 { display: '卡片编码', name: '',  width: '120px'},
                 { display: '资产名称', name: '',  width: '120px'},
                 { display: '资产类别', name: '',  width: '120px'},
                 { display: '所在科室', name: '',  width: '120px'},
                 { display: '规格', name: '',  width: '120px'},
                 { display: '型号', name: '',  width: '120px'},
                 { display: '原值', name: '',  width: '120px'},
                 { display: '供应商', name: 'sup_name',  width: '190px'},
            	 { display: '合同编号', name: 'pact_code', width: '150px'},
                 { display: '合同名称', name: 'pact_name', width: '190px'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                 { display: '入库日期', name: 'arrive_date', align: 'center', width: '90px'},
                 { display: '保修期(月)', name: 'repair_months', align: 'right',width: '90px'},
                 { display: '保修期限', name: 'end_repair_date', align: 'right', width: '90px'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                 dataModel: {
                    url: 'queryPactMainFKHTForNearRepairWarning.do?isCheck=false'
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
   					method_name:"queryPactMainFKHTForNearRepairWarningPrint",
   					bean_name:"pactMainFKHTService",
   					start_date : startpicker.getValue(),
   	                end_date : endpicker.getValue(),
   	                pact_type_code : $("#pact_type_code").val(),
   	                sup_no : $("#sup_no").val(),
   	                in_start_date : instartpicker.getValue(),
   	                in_end_date : inendpicker.getValue(),
   	                pact_code : $("#pact_code").val(),
   	                pact_name : $("#pact_name").val(),
   	                ass_name : $("#ass_name").val(),
   	                ass_type : $("#ass_type").val(),
   	                dept_no : $("#dept_no").val(),
   	                warning_day : $("#warning_day").val()
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
        	instartpicker = $("#in_start_date").etDatepicker({
        		defaultDate: "yyyy-fm-fd",
    		  	onChange: function (date) {
    		  		var end = inendpicker.getValue();
    		  		if(end < date){
    		  			inendpicker.setValue(end);
    		  		}
    		  	}
    		});
    		inendpicker = $("#in_end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = instartpicker.getValue();
    		  		if(start > date){
    		  			instartpicker.setValue(start);
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
            <td class="label" style="width: 100px;">入库日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="in_start_date" type="text" style="width: 100px"/>至 <input id="in_end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">资产类别：</td>
            <td class="ipt"><select id="ass_type" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"><input id="ass_name" type="text" /> </td>
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><select id="dept_no" style="width: 180px"></select></td>
        </tr>
        <tr>
        	 <td class="label" style="width: 100px;">提醒天数：</td>
            <td class="ipt"><input id="warning_day" type="text" style="width: 60px" value="30"/>天 </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

