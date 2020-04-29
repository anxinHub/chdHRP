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
        var dept_select , proj_select , emp_dept_select , ass_select , fac_select ;
        var query = function () {
            params = [
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'dept_id', value: dept_select.getValue()},
                { name: 'proj_id', value: proj_select.getValue()},
                { name: 'emp_dept_id', value: emp_dept_select.getValue()},
                { name: 'ass_id', value: ass_select.getValue()},
                { name: 'fac_id', value: fac_select.getValue()},
            ];
            grid.loadData(params);
        };
        
        
        var initGrid = function () {
            var columns = [
            	 { display: '申请单号', name: 'apply_no',align: 'left', width: '120px'},
                 { display: '申请日期', name: 'create_date',align: 'left', width: '80px'},
                 { display: '申请部门', name: 'dept_name', align: 'left', width: '100px'},
                 { display: '资产编号', name: 'subject_code', align: 'left', width: '90px'},
                 { display: '资产名称', name: 'subject_name',  align: 'left',width: '120px'},
                 { display: '规格', name: 'item_spec', align: 'left', width: '80px'},
                 { display: '型号', name: 'item_model', align: 'left', width: '100px'},
                 { display: '生成厂家', name: 'fac_name',  align: 'left', width: '180px'},
                 { display: '品牌要求', name: 'item_brand', align: 'left', width: '80px'},
                 { display: '功能要求', name: 'features_req', align: 'left', width: '80px'},
                 { display: '计量单位', name: 'unit_name', align: 'left', width: '80px'},
                 { display: '申请数量', name: 'apply_amount', align: 'center', width: '80px'},
                 { display: '预算单价', name: 'price', align: 'center', width: '80px'},
                 { display: '已生成数量', name: 'pact_amount', align: 'center', width: '80px'},
                 { display: '剩余数量', name: 'left_amount', align: 'center', width: '80px'},
                 { display: '合同数量', name: 'amount', align: 'center', width: '80px'},
                 { display: '使用科室', name: 'dept_name', align: 'center', width: '80px'},
                 { display: '项目', name: 'proj_name', align: 'center', width: '80px'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactAssApplyFKHT.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '生成', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '关闭', listeners: [{ click: this_close }], icon: 'del' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
       
        //跳转保存页面
        var save = function (data) {
        	var data = grid.selectGet();
        	if (data.length == 0) {
                $.etDialog.error('请选择行');
                return false ;
            }else{
            	$(data).each(function () {
            		var parentFrameName = parent.$.etDialog.parentFrameName;
    		        var parentWindow = parent.window[parentFrameName];
    			    parentWindow.subGrid.addRow(this.rowData);
    			    parentWindow.addMoney();//更新 合同主表总金额
                })
            	
            }
        	
        	this_close();
        };
        // 关闭
        function this_close() {
        	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
    	}
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
         var initSelect=  function(){
        	dept_select = $("#dept_id").etSelect(
            		{url: '../../../basicset/select/queryDeptSelect.do?isCheck=false', 
            			defaultValue: "none"}
            		);
        	proj_select = $("#proj_id").etSelect(
            		{url: '../../../basicset/select/queryHosProjDictSelect.do?isCheck=false', 
            			defaultValue: "none"}
            		);
        	emp_dept_select = $("#emp_dept_id").etSelect(
            		{url: '../../../basicset/select/queryDeptSelect.do?isCheck=false', 
            			defaultValue: "none"}
            		);
        	ass_select = $("#ass_id").etSelect(
            		{url: '../../../basicset/select/querySubjectSelect.do?isCheck=false&type=01', 
            			defaultValue: "none"}
            		);
        	fac_select = $("#fac_id").etSelect(
            		{url: '../../../basicset/select/queryHosFacDict.do?isCheck=false', 
            			defaultValue: "none"}
            		);
          }
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: 'yyyy-fm-fd',
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
            <td class="label" style="width: 100px;">申请日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">申请部门：</td>
            <td class="ipt"><select id="dept_id" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">申请单号：</td>
            <td class="ipt"> <input id="apply_code" type="text" style="width: 180px"/> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">项目：</td>
            <td class="ipt"> <select id="proj_id" style="width: 220px"></select> </td>
        	<td class="label" style="width: 100px;">使用科室：</td>
            <td class="ipt"> <select id="emp_dept_id" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"> <select id="ass_id" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">生产厂家：</td>
            <td class="ipt"> <select id="fac_id" style="width: 220px"></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

