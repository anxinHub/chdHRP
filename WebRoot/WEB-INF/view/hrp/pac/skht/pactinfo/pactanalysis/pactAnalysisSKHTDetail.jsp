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
        var initChangeMoneyGrid = function () {
        	 var columns = [
                 { display: '变更编号', name: 'change_code', width: '14%'},
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
            	 { display: '合同编号', name: 'pact_code', width: '13%'},
                 { display: '合同名称', name: 'pact_name', width: '15%'},
                 { display: '客户', name: 'cus_name',  width: '15%'},
                 { display: '变更原因', name: 'change_reason', width: '18%'},
                 { display: '变更金额', name: 'money_c', width: '10%'},
                 { display: '操作员', name: 'user_name',  width: '10%'},
                 { display: '变更前合同查看', name: '', align: 'center', width: '10%'},
            ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                    url: '../../change/queryPactMainChangeMoneySKHT.do?isCheck=false',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            changeMoneyGrid = $("#changeMoneyGrid").etGrid(paramObj);
        };
        
        var initGuaranteeGrid = function () {
        	var columns = [
           	 	{ display: '付款编号', name: 'pay_code',width: '14%'},
           	 	{ display: '付款日期', name: 'date', align: 'center', width: '8%'},
                { display: '客户', name: 'cus_name',width: '14%'},
           	 	{ display: '合同编号', name: 'pact_code',width: '14%'},
                { display: '合同名称', name: 'pact_name',width: '15%'},
                { display: '付款金额', name: 'money', align: 'right', width: '10%'},
                { display: '摘要', name: 'note', width: '10%'},
                { display: '付款方式', name: 'pay_way', width: '10%'},
                { display: '支票号码', name: 'cheq_no', width: '15%'},
                { display: '制单人', name: 'maker_name', align: 'center', width: '10%'},
                { display: '审核人', name: 'checker_name', align: 'center', width: '10%'},
                { display: '确认人', name: 'confirmer_name', align: 'center', width: '10%'},
                { display: '状态', name: 'state', align: 'center', width: '5%'}
           ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                    url: '../../guarantee/performance/init/queryPactDepRecSKHT.do?isCheck=false',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            guaranteeGrid = $("#guaranteeGrid").etGrid(paramObj);
        };
        
        var initRefundGrid = function () {
        	var columns = [
            	{ display: '退款编号', name: 'ret_code',width: '15%'},
           		{ display: '退款日期', name: 'date', align: 'center', width: '8%'},
                { display: '客户', name: 'cus_name',width: '14%'},
           		{ display: '合同编号', name: 'pact_code',width: '14%'},
                { display: '合同名称', name: 'pact_name',width: '14%'},
                { display: '退款金额', name: 'money', align: 'right', width: '8%'},
                { display: '摘要', name: 'note', width: '14%'},
                { display: '退款方式', name: 'pay_way', width: '8%'},
                { display: '支票号码', name: 'cheq_no', width: '10%'},
                { display: '制单人', name: 'maker_name', align: 'center', width: '10%'},
                { display: '审核人', name: 'checker_name', align: 'center', width: '10%'},
                { display: '确认人', name: 'confirmer_name', align: 'center', width: '10%'},
                { display: '状态', name: 'state', align: 'center', width: '4%'}
            ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                    url: '../../guarantee/performance/refund/queryPactDepRetSKHT.do?isCheck=false',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            refundGrid = $("#refundGrid").etGrid(paramObj);
        };
        
        var initLetterGrid = function () {
        	var columns = [
           	 	{ display: '保函编号', name: 'letter_code', align: 'center',width: '15%'},
           	 	{ display: '开具日期', name: 'sign_date', align: 'center',width: '10%'},
           		{ display: '银行', name: 'bank_name', align: 'center',width: '12%'},
                { display: '客户', name: 'cus_name', align: 'center', width: '12%'},
           		{ display: '合同编号', name: 'pact_code', align: 'center',width: '15%'},
                { display: '合同名称', name: 'pact_name', align: 'center' ,width: '19%'},
                { display: '担保金额', name: 'money', align: 'right', width: '10%'},
                { display: '开始日期', name: 'start_date', align: 'center', width: '10%'},
                { display: '结束日期', name: 'end_date', align: 'center', width: '10%'},
                { display: '索赔条件', name: 'sp_cond', align: 'center', width: '10%'},
                { display: '备注', name: 'note', align: 'center', width: '15%'},
                { display: '制单人', name: 'maker_name', align: 'center', width: '12%'},
                { display: '审核人', name: 'checker_name', align: 'center', width: '12%'},
                { display: '确认人', name: 'confirmer_name', align: 'center', width: '12%'},
                { display: '失效人', name: 'disabler_name', align: 'center', width: '12%'},
                { display: '状态', name: 'letter_state', align: 'center', width: '5%'},
           ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                    url: '../../guarantee/letter/queryPactLetterSKHT.do?isCheck=false',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            letterGrid = $("#letterGrid").etGrid(paramObj);
        };
        
        var initPayGrid = function () {
        	var columns = [
           	 	{ display: '收款单号', name: 'rec_code',width: '140px'},
           	 	{ display: '收款日期', name: 'rec_date', align: 'center', width: '100px'},
                { display: '客户', name: 'cus_name', width: '150px'},
           	 	{ display: '发票号码', name: 'bill_code',width: '150px'},
           	 	{ display: '收款金额', name: 'rec_money', align:'right', width: '150px'},
           	 	{ display: '合同编号', name: 'pact_code',width: '150px'},
                { display: '合同名称', name: 'pact_name',width: '150px'},
                { display: '业务员', name: 'emp_name', align: 'center',width: '80px'},
                { display: '制单人', name: 'maker_name', align: 'center',width: '80px'},
                { display: '审核人', name: 'checker_name',align: 'center', width: '80px'},
                { display: '确认人', name: 'confirmer_name',align: 'center', width: '80px'},
                { display: '状态', name: 'state_name', align: 'center', width: '80px'}
           ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                	   url: '../../payment/payment/queryPactRecMainSKHT.do?isCheck=false',
                	   postData: {
                       	pact_code : '${pact_code}'
                       }
                },
                columns: columns,
            };
            payGrid = $("#payGrid").etGrid(paramObj);
        };
        
        var initBreakGrid = function () {
        	var columns = [
           	 	{ display: '登记编号', name: 'break_code',width: '150px'},
           	 	{ display: '登记日期', name: 'sign_date', align: 'center',width: '100px'},
                { display: '客户', name: 'cus_name', width: '150px'},
           	 	{ display: '合同编号', name: 'pact_code',width: '150px'},
                { display: '合同名称', name: 'pact_name',width: '150px'},
                { display: '违约金额', name: 'break_money', align: 'right', width: '100px'},
                { display: '违约方', name: 'party_name', align: 'center', width: '60px'},
                { display: '违约开始日期', name: 'start_date', align: 'center', width: '100px'},
                { display: '违约结束日期', name: 'end_date', align: 'center', width: '100px'},
                { display: '制单人', name: 'maker_name', align: 'center',width: '80px'},
                { display: '审核人', name: 'checker_name',align: 'center', width: '80px'},
                { display: '确认人', name: 'confirmer_name',align: 'center', width: '80px'},
                { display: '状态', name: 'state_name', align: 'center', width: '80px'},
           ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
            	pageModel :false,
                dataModel: {
                    url: '../../break/break/queryPactBreakSKHT.do?isCheck',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            breakGrid = $("#breakGrid").etGrid(paramObj);
        };
        
        var initSPGrid = function () {
        	var columns = [
           	 	{ display: '登记编号', name: 'sp_code',width: '140px'},
           		{ display: '登记日期', name: 'sign_date', align: 'center',width: '100px'},
                { display: '客户', name: 'cus_name', width: '150px'},
           	 	{ display: '合同编号', name: 'pact_code',width: '150px'},
                { display: '合同名称', name: 'pact_name',width: '150px'},
                { display: '摘要', name: 'note',width: '150px'},
                { display: '索赔金额', name: 'sp_money', align: 'right', width: '90px'},
                { display: '索赔方', name: 'party_name', align: 'center', width: '60px'},
                { display: '制单人', name: 'maker_name', align: 'center',width: '80px'},
                { display: '审核人', name: 'checker_name',align: 'center', width: '80px'},
                { display: '确认人', name: 'confirmer_name',align: 'center', width: '80px'},
                { display: '状态', name: 'state_name', align: 'center', width: '80px'},
           ];
            var paramObj = {
            	editable: false,
            	height: '240',
            	width:'98.9%',
				pageModel :false,
            	dataModel: {
                    url: '../../break/sp/queryPactSPSKHT.do?isCheck=false',
                    postData: {
                    	pact_code : '${pact_code}'
                    }
                },
                columns: columns,
            };
            spGrid = $("#spGrid").etGrid(paramObj);
        };
        
        $(function () {
            initChangeMoneyGrid();
            initGuaranteeGrid();
            initRefundGrid();
            initLetterGrid();
            initPayGrid();
            initBreakGrid();
            initSPGrid();
        })
        
    </script>
</head>

<body style="overflow: auto;">
    <hr><h1 style="padding-left: 20px">收款合同变更表</h1> 
    <div id="changeMoneyGrid"></div>
    <hr><h1 style="padding-left: 20px">保证金收款表</h1>
    <div id="guaranteeGrid"></div>
    <hr><h1 style="padding-left: 20px">保证金退款表</h1>
    <div id="refundGrid"></div>
    <hr><h1 style="padding-left: 20px">银行保函表</h1>
    <div id="letterGrid"></div>
    <hr><h1 style="padding-left: 20px">付款单</h1>
    <div id="payGrid"></div>
    <hr><h1 style="padding-left: 20px">合同违约</h1>
    <div id="breakGrid"></div>
    <hr><h1 style="padding-left: 20px">合同索赔</h1>
    <div id="spGrid"></div>
</body>

</html>

