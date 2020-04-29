<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,grid,select,datepicker,pageOffice,checkbox" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var mat_store;
        var inv_category;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'dept_no', value: $("#dept_no").val() },
                { name: 'inv_name', value: $("#inv_name").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                //新增条件
                { name: 'mat_store', value: $("#mat_store").val() },
                { name: 'inv_category', value: $("#inv_category").val() },
                { name: 'inv_spetype', value: $("#inv_spetype").val() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false&FKXY_Attr=01',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	mat_store = $("#mat_store").etSelect({url: '../../basicset/select/queryMatStoreAll.do?isCheck=false',defaultValue: "none"});
          	inv_category = $("#inv_category").etSelect({url: '../../basicset/select/queryMatType.do?isCheck=false',defaultValue: "none"});
        }
        
        function toMatMain(rowIndex,pactcode,invid,signdate,enddate){
			parent.$.ligerDialog.open({
        	 	  url: 'hrp/mat/storage/in/mainPage.do?isCheck=false&showButton='+0+'&protocol_code='+pactcode +'&inv_id='+invid+'&begin_in_date='+signdate+'&end_in_date='+enddate,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流材料入库',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
               });
        }
		
		function toBill(rowIndex,in_detail_id){
			parent.$.ligerDialog.open({
         	 	 url: 'hrp/mat/payment/bill/matBillListPage.do?isCheck=false&showButton='+0+'&in_detail_id='+in_detail_id,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流发票管理',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
               });
        }
		
		function toPrePay(rowIndex,bill_detail_id){
			parent.$.ligerDialog.open({
         	 	 url: 'hrp/mat/matprepay/matprepaymain/matPrePayMainMainPage.do?isCheck=false&showButton='+0+'&bill_detail_id='+bill_detail_id,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流预付款单',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
               });
        }
		
		function toPay(rowIndex,bill_detail_id){
			parent.$.ligerDialog.open({
         	 	 url: 'hrp/mat/matpay/matpaymain/matPayMainMainPage.do?isCheck=false&showButton='+0+'&bill_detail_id='+bill_detail_id,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流付款单',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
               });
        }
		
        var initGrid = function () {
            var columns = [
            	 { display: '协议编号', name: 'pact_code', align: 'center',width: '12%'},
                 { display: '协议名称', name: 'pact_name', align: 'center' ,width: '19%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '9%'},
                 { display: '结束日期', name: 'end_date', align: 'center', width: '9%'},
                 { display: '供应商id', name: 'sup_id', align: 'center', hidden: true},
                 { display: '供应商', name: 'sup_name', align: 'center', width: '12%'},
                 { display: '入库id', name: 'in_id', align: 'center', hidden: true},
                 { display: '入库明细id', name: 'in_detail_id', align: 'center', hidden: true},
                 { display: '材料编码', name: 'inv_id', align: 'center', width: '8%'},
                 { display: '材料名称', name: 'inv_name', align: 'center', width: '20%'},
                 { display: '规格', name: 'inv_model', align: 'center', width: '8%'},
                 { display: '协议单价', name: 'price', align: 'center', width: '8%'},
                 { display: '到货数量', name: 'amount', align: 'center', width: '8%',
                	 render : function(data){ 
                	 if(data.rowData.pact_code !="<b>合计:</b>"){
                		 return '<a href=javascript:toMatMain("'+data.rowData._rowIndx+'","'+data.rowData.pact_code+'","'+data.rowData.inv_id+'","'+data.rowData.sign_date+'","'+data.rowData.end_date+'")>'+data.rowData.amount+'</a>';
         	 		 }else{
         	 			if(data.rowData.amount_money){
         	 				return data.rowData.amount
         	 			}else{
         	 				return 0.00;
         	 			}
         	 		 }}
        		 },	
                 { display: '到货金额', name: 'amount_money', align: 'center', width: '8%',
                	 render : function(data){
                		 if(data.rowData.pact_code !="<b>合计:</b>"){
                			 return '<a href=javascript:toMatMain("'+data.rowData._rowIndx+'","'+data.rowData.pact_code+'","'+data.rowData.inv_id+'","'+data.rowData.sign_date+'","'+data.rowData.end_date+'")>'+data.rowData.amount_money+'</a>';
             	 		 }else{
             	 			if(data.rowData.amount_money){
             	 				return formatNumber(data.rowData.amount_money,2,1)
             	 			}else{
             	 				return 0.00;
             	 			}
             	 		 }}
            	 },	
                 { display: '发票号', name: 'bill_no', align: 'center', hidden: true},
                 { display: '发票id', name: 'bill_id', align: 'center', hidden: true},
                 { display: '发票明细id', name: 'bill_detail_id', align: 'center', hidden: true},
                 { display: '开票金额', name: 'bill_money', align: 'center', width: '8%',
                  	render : function(data){
                  		if(data.rowData.bill_money !="<b>合计:</b>"){
                  			return '<a href=javascript:toBill("'+data.rowData._rowIndx+'","'+data.rowData.in_detail_id+'")>'+data.rowData.bill_money+'</a>';
                  		}else{
                  			if(data.rowData.bill_money){
                  				return formatNumber(data.rowData.bill_money,2,1)
                  			}else{
                  				return "";
                  			}
                  		}}
                 },
                 { display: '未开票金额', name: 'nopay_money', align: 'center', width: '8%'},
                 { display: '预付款金额', name: 'prepay_money', align: 'center', width: '8%',
                	 render : function(data){
                		 if(data.rowData.prepay_money){
                		 	return '<a href=javascript:toPrePay("'+data.rowData._rowIndx+'","'+data.rowData.bill_detail_id+'")>'+data.rowData.prepay_money+'</a>';
                 	 	 }else{
                 	 		 if(data.rowData.prepay_money){
                 	 			 return formatNumber(data.rowData.prepay_money,2,1)
                 	 		 }else{
                 	 			 return 0.00;
                 	 		 }
                 	 	 }}
                 },
                 { display: '付款单号', name: 'pay_no', align: 'center', hidden: true},
                 { display: '付款金额', name: 'pay_money', align: 'center', width: '8%',
                	render : function(data){
                		if(data.rowData.pay_money){
                		 	return '<a href=javascript:toPay("'+data.rowData._rowIndx+'","'+data.rowData.bill_detail_id+'")>'+data.rowData.pay_money+'</a>';
                    	}else{
                    		if(data.rowData.pay_money){
                    			return formatNumber(data.rowData.pay_money,2,1)
                    		}else{
                    			return 0.00;
                    		}
                    	}}
                 },
                 { display: '未付金额', name: 'billnopay_money', align: 'center', width: '8%'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactProtocolDetailFKXY.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                    ]
                },
                summary:{         //  摘要行集合
                    totalColumns:['amount','amount_money','bill_money','nopay_money'],
                    keyWordCol:'pact_code',   //关键字所在列的列名
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
      		
        };
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "履约银行保函",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.fkxy.logistics.PactLogisticsProtocolService",
   					method_name:"queryPactProtocolDetailFKXYPrint",
   					bean_name:"pactLogisticsProtocolService",
   				 	pact_type_code : $("#pact_type_code").val(),
                 	sup_no : $("#sup_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	dept_no : $("#dept_no").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
                 	inv_name : $("#inv_name").val()
               };
               officeGridPrint(printPara);
           };
        //跳转修改页面
        
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
        
        //日期
        var initfrom = function(){
        	/* is_bid = $('#is_bid').etCheck(); */
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
            <td class="label" style="width: 100px;">协议类别：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">协议编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">协议名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">仓库：</td>
            <td class="ipt"><select id="mat_store" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">物资类别：</td>
            <td class="ipt"><select id="inv_category" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">物资名称：</td>
            <td class="ipt"><input id="inv_name" type="text" /> </td>
            <td class="label" style="width: 100px;">规格型号：</td>
            <td class="ipt"><input id="inv_spetype" type="text" /> </td>
        	<!-- <td><input id="is_bid" type="checkbox" /><span style="margin-left: 10px">含结转</span></td> -->
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>