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
        var startpicker,endpicker,pact_type_code,sup_no,sign_dept,state_code;
        var planPayStart,planPayEnd;
        var curGridData = {};
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
        
		var initGrid = function () {
 			curGridData = {};
            var columns = [
				{ display: '合同编号', name: 'pact_code', align: 'center',width: '10%',
         			render: function (ui, rowindex, value) {
         				var curRowData = ui.rowData;
        				var ret = ' <a href=javascript:udPact("'+ curRowData.pact_code +'")>'+curRowData.pact_code+'</a>'
                  		return ret
        			}
            	 },
                 { display: '合同名称', name: 'pact_name', align: 'left' ,width: '15%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '6%'},
                 { display: '供应商', name: 'sup_name', align: 'left', width: '15%'},
                 { display: '合同金额', name: 'pact_money', align: 'right', width: '10%'},
				 { display: '期号', name: 'pay_id', align: 'right', width: '10%'},
				 { display: '付款类型', name: 'pay_type_name', align: 'right', width: '10%'},
				 { display: '摘要', name: 'summary', align: 'right', width: '10%'},
				 { display: '付款条件', name: 'cond_name', align: 'right', width: '10%'},
				 { display: '计划付款期限', name: 'pay_date', align: 'right', width: '10%'},
				 { display: '计划金额', name: 'plan_money', align: 'right', width: '10%'},
				 { display: '开票金额', name: 'kpje', align: 'right', width: '10%',
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
				{ display: '未开票金额', name: 'pact_money', align: 'right', width: '10%'},
				{ display: '付款金额', name: 'pact_money', align: 'right', width: '10%',
					render:function(ui) {
              		 var curData = ui.rowData;
					 curGridData[curData.pact_code] = curData;
              		 if(curData.fkje==0 || curData.fkje==null){
						return '<a href=javascript:openPayInfo("'+curData.pact_code+'")>'+'查看'+'</a>';
              		 }else{
						return '<a href=javascript:openPayInfo("'+curData.pact_code+'")>'+curData.fkje+'</a>';
              		 }
            	}
				},
				{ display: '已开票未付款金额', name: 'pact_money', align: 'right', width: '10%'},
            ];
            
            var patToolbar ={
            	items: [
                	{ type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
           		]	
            }
            
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: { url: 'queryPactAssetPurchasePayMD.do?isCheck=false' },
                columns: columns,
                toolbar: patToolbar
            };
            
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        //资产发票查询 页面 跳转
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
        //资产付款查询 页面 跳转
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

        var query = function () {
        	curGridData = {};
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
		              { name: 'pact_type_code', value: pact_type_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
	                  
	                  { name: 'planPay_start_date', value: planPayStart.getValue() },
	                  { name: 'planPay_end_date', value: planPayEnd.getValue() },
		              { name: 'pact_code', value: $("#pact_code").val() },
		              { name: 'pact_name', value: $("#pact_name").val() },
	                  
		              { name: 'sign_dept', value: sign_dept.getValue() },
		              { name: 'state_code', value: state_code.getValue() }
               
            ];
            grid.loadData(params);
        };
        var udPact = function (code){
        	debugger
        	parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+code,
                title: '详情',
                modal: true,
                frameName: window.name,
                isMax: true, 
                isResize: true
            });
        }
        
        var initSelect=  function(){
        	//合同类别
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
        	//供应商
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	//签订科室
          	sign_dept = $("#sign_dept").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
          	//合同状态
          	state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "none"});
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
    		
    		
    		planPayStart = $("#planPay_start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
    		  	onChange: function (date) {
    		  		var end = planPayEnd.getValue();
    		  		if(end < date){
    		  			planPayEnd.setValue(end);
    		  		}
    		  	}
    		});
    		planPayEnd = $("#planPay_end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = planPayStart.getValue();
    		  		if(start > date){
    		  			planPayEnd.setValue(start);
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
        	<td class="label" style="width: 100px;">计划付款日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="planPay_start_date" type="text" style="width: 100px"/>至 <input id="planPay_end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;"></td>
            <td class="ipt"></td>
            
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><input id="sign_dept" type="text" style="width: 180px"/> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"><input id="state_code" type="text" style="width: 180px"/></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>