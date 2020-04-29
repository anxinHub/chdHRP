<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker,endpicker,pact_type_code,sup_no,trade_type,sign_dept,state_code;
        var curGridData = {};
        
        var query = function () {
        	curGridData = {};
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
		              { name: 'pact_name', value: pact_type_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
		              
		              { name: 'min_money', value: $("#min_money").val() },
		              { name: 'max_money', value: $("#max_money").val() },
		              { name: 'pact_code', value: $("#pact_code").val() },
		              { name: 'pact_name', value: $("#pact_name").val() },
		              
		              { name: 'trade_type', value: trade_type.getValue() },
		              { name: 'sign_dept', value: sign_dept.getValue() },
		              { name: 'state_code', value: state_code.getValue() }
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	//合同类别pact_type_fkht
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
        	//供应商HOS_SUP
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
        	//贸易类别 PACT_DICT_DATA
          	trade_type = $("#trade_type").etSelect({url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: "none"});
        	//签订科室 HOS_DEPT_DICT
          	sign_dept = $("#sign_dept").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
        	//合同状态PACT_STATE
          	state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '合同编号', name: 'pact_code', align: 'center',width: '15%'},
                 { display: '合同名称', name: 'pact_name', align: 'left' ,width: '15%'},
                 { display: '贸易类型', name: 'mylb', align: 'center', width: '6%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '6%'},
                 { display: '供应商', name: 'sup_name', align: 'left', width: '15%'},
                 { display: '签订科室', name: 'ks', align: 'left', width: '12%'},
                 { display: '合同金额', name: 'htje', align: 'right', width: '10%'},
                 
                 { display: '开票金额', name: 'kpje', align: 'center', width: '8%',
                	 render:function(ui,a,b,c,d) {
	               		 var curRowData = ui.rowData;
                		 curGridData[curRowData.pact_code] = curRowData;
	               		 if(curRowData.kpje==0 || curRowData.kpje==null){
	               			 return '<a href=javascript:openBillInfo("'+curRowData.pact_code+'")>'+'查看'+'</a>';
	               		 }else{
	               			 return '<a href=javascript:openBillInfo("'+curRowData.pact_code+'")>'+curRowData.kpje+'</a>';
	               		 }
                 	}
                 },
                 { display: '未开票金额', name: 'wkpje', align: 'center', width: '8%',
                	 render:function(ui) {
	               		 var curData = ui.rowData;
	               		 if(curData.wkpje==null){
	               			 return '';
	               		 }
                 	}
                 },
                 { display: '付款金额', name: 'fkje', align: 'center', width: '8%',
					render:function(ui) {
	               		 var curData = ui.rowData;
	               		 if(curData.fkje==0 || curData.fkje==null){
							return '<a href=javascript:openPayInfo("'+curData.pact_code+'")>'+'查看'+'</a>';
	               		 }else{
							return '<a href=javascript:openPayInfo("'+curData.pact_code+'")>'+curData.fkje+'</a>';
	               		 }
                 	}
                 },
                 { display: '已开票未付款金额', name: 'ykpwfk', align: 'center', width: '8%',
					render:function(ui) {
						var curData = ui.rowData;
	               		 if(curData.ykpwfk==null){
	               			 return '';
	               		 }
                 	}
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssetPurchasePay.do?isCheck=false'
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
        
        
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
        
        
        //发票记录查询 页面 跳转
        function openBillInfo(pactCode){
        	var cd = curGridData[pactCode];
        	parent.$.ligerDialog.open({
				url : 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchasePayBillPage.do?isCheck=false',
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '发票记录',
				data:cd,
			});
        }
        //付款记录查询 页面 跳转
		function openPayInfo(pactCode){
        	var cd = curGridData[pactCode];
			parent.$.ligerDialog.open({
				url : 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchasePayPayPage.do?isCheck=false',
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '付款记录',
				data:cd,
			});
		}

        
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
            <td class="label" style="width: 100px;">合同类别：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        
        <tr>
        	<td class="label" style="width: 100px;">合同金额：</td>
            <td class="ipt" style="width: 220px;">
                <input id="min_money" type="text" style="width: 100px"/>至 <input id="max_money" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">贸易类别：</td>
            <td class="ipt"><input id="trade_type" type="text" style="width: 180px"/></td>
             <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><input id="sign_dept" type="text" style="width: 180px"/> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"><input id="state_code" type="text" style="width: 180px"/></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>