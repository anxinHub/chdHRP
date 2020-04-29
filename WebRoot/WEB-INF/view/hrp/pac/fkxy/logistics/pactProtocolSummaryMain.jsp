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
        var dept_id;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'dept_id', value:$("#dept_id").val() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false&FKXY_Attr=01',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	dept_id = $("#dept_id").etSelect({url:'../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue:"none"});
        }
        
        function toMatMainMoney(rowIndex,pact_code){
        	//var data = mainGrid.getRowData(rowIndex);
        	//console.log(pact_code);
			//var cd = mainGrid[th];
			parent.$.ligerDialog.open({
          	 	  url: 'hrp/mat/storage/in/mainPage.do?isCheck=false&protocol_code='+pact_code+'&showButton='+0,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流材料入库',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
   				   //data:cd,
               });
        }
        
        function toBill(rowIndex,th){
        	//var data = mainGrid.getRowData(rowIndex);
        	//console.log(th);
			var cd = mainGrid[th];
			parent.$.ligerDialog.open({
          	 	 url: 'hrp/mat/payment/bill/matBillListPage.do?isCheck=false&protocol_code='+th+'&showButton='+0,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流发票管理',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
   				   data:cd,
               });
        }
		
		function toPrePay(rowIndex,th){
        	//var data = mainGrid.getRowData(rowIndex);
        	//console.log(th);
			var cd = mainGrid[th];
			parent.$.ligerDialog.open({
          	 	 url: 'hrp/mat/matprepay/matprepaymain/matPrePayMainMainPage.do?isCheck=false&protocol_code='+th+'&showButton='+0,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流预付款单',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
   				   data:cd,
               });
        }
		
		function toPay(rowIndex,th){
        	//var data = mainGrid.getRowData(rowIndex);
        	//console.log(th);
			var cd = mainGrid[th];
			parent.$.ligerDialog.open({
          	 	 url: 'hrp/mat/matpay/matpaymain/matPayMainMainPage.do?isCheck=false&protocol_code='+th+'&showButton='+0,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '物流付款单',
                   modal: true,
                   showMax : true,
   				   showMin : false,
   				   isResize : true,
   				   parentframename : window.name,
   				   data:cd,
               });
        }
		
        var initGrid = function () {
            var columns = [
            	 { display: '协议编号', name: 'pact_code', align: 'center',width: '15%'},
                 { display: '协议名称', name: 'pact_name', align: 'center' ,width: '19%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '10%'},
                 { display: '结束日期', name: 'end_date', align: 'center', width: '10%'},
                 { display: '供应商id', name: 'sup_id', align: 'center', hidden: true},
                 { display: '供应商', name: 'sup_name', align: 'center', width: '12%'},
                 { display: '协议总金额', name: 'pact_money', align: 'center', width: '8%'},
                 { display: '到货金额', name: 'amount_money', align: 'center', width: '8%',
                	 	render : function(data){
                	 		//console.log(data.rowData);
                	 		if(data.rowData.pact_code !="<b>合计:</b>"){
                	 			return '<a href=javascript:toMatMainMoney("'+data.rowData._rowIndx+'","'+data.rowData.pact_code+'")>'+data.rowData.amount_money+'</a>';
                	 		}else{
                	 			if(data.rowData.amount_money){
                	 				return formatNumber(data.rowData.amount_money,2,1)
                	 			}else{
                	 				return 0.00;
                	 			}
                	 		}
                   			
                	 	}},
                 { display: '开票金额', name: 'bill_money', align: 'center', width: '8%',
                   		render : function(data){
                       		if(data.rowData.pact_code !="<b>合计:</b>"){
                       			return '<a href=javascript:toBill("'+data.rowData.rowIndx+'","'+data.rowData.pact_code+'")>'+data.rowData.bill_money+'</a>';
                       		}else{
                       			if(data.rowData.bill_money){
                       				return formatNumber(data.rowData.bill_money,2,1)
                       			}else{
                       				return 0.00;
                       			}
                       		}
                       		}},
                 { display: '未开票金额', name: 'nopay_money', align: 'center', width: '8%'},
                 { display: '预付款金额', name: 'prepay_money', align: 'center', width: '8%',
                		render : function(data){
                    		if(data.rowData.pact_code !="<b>合计:</b>"){
                    			return '<a href=javascript:toPrePay("'+data.rowData.rowIndx+'","'+data.rowData.pact_code+'")>'+data.rowData.prepay_money+'</a>';
                    		}else{
                    			if(data.rowData.prepay_money){
                    				return formatNumber(data.rowData.prepay_money,2,1)
                    			}else{
                    				return 0.00;
                    			}
                    		}
                    		
                		}},
                 { display: '付款金额', name: 'pay_money', align: 'center', width: '8%',
                 		render : function(data){
                       		if(data.rowData.pact_code !="<b>合计:</b>"){
                       			return '<a href=javascript:toPay("'+data.rowData.rowIndx+'","'+data.rowData.pact_code+'")>'+data.rowData.pay_money+'</a>';
                       		}else{
                       			if(data.rowData.pay_money){
                       				return formatNumber(data.rowData.pay_money,2,1)
                       			}else{
                       				return 0.00;
                       			}
                       		}
                       		}},
                 { display: '已开票未付款', name: 'billnopay_money', align: 'center', width: '8%'},
                 { display: '执行进度', name: 'exeAnalysis', align: 'center', width: '8%', render:toPercent,type: 'float',editor: { type: 'float' }},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactProtocalSummaryFKXY.do?isCheck=false'
                }, 
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                    ]
                },
                summary:{         //  摘要行集合
                    totalColumns:['amount_money','bill_money','nopay_money'],
                    keyWordCol:'pact_code',   //关键字所在列的列名
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            /*
      		$("#mainGrid").on('click','.toMatMainMoney',function(){
            	 var rowIndex = $(this).attr('rowIndex');
                 var currentRowData = grid.getAllData()[rowIndex];
                 toMatMainMoney(currentRowData);
      		})
      		
      		$("#mainGrid").on('click','.toBill',function(){
            	 var rowIndex = $(this).attr('rowIndex');
                 var currentRowData = grid.getAllData()[rowIndex];
                 toBill(currentRowData);
      		})
      		
      		$("#mainGrid").on('click','.toPrePay',function(){
            	 var rowIndex = $(this).attr('rowIndex');
                 var currentRowData = grid.getAllData()[rowIndex];
                 toPrePay(currentRowData);
      		})
      		
      		$("#mainGrid").on('click','.toPay',function(){
            	 var rowIndex = $(this).attr('rowIndex');
                 var currentRowData = grid.getAllData()[rowIndex];
                 toPay(currentRowData);
      		})
            */
        };
        
        function toPercent(rowData,index,value) { 
        	var analysis = rowData.rowData.exeAnalysis;
            if (analysis==null){
           	 return '<span style="color: red">'+(Math.round(0 * 10000) / 100.00 + "%")+'</span>';// 小数点后两位百分比
            }else{
           	 return '<span style="color: red">'+(Math.round(analysis * 10000) / 100.00 + "%")+'</span>';// 小数点后两位百分比
            } 
       	}
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "履约银行保函",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.fkxy.logistics.PactLogisticsProtocolService",
   					method_name:"queryPactProtocalSummaryFKXY",
   					bean_name:"pactLogisticsProtocolService",
   				 	pact_type_code : $("#pact_type_code").val(),
                 	sup_no : $("#sup_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue()
               };
               officeGridPrint(printPara);
           };
        
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
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><select id="dept_id" style="width: 180px"></select> </td>
        </tr>
        <!-- <tr>
        	<td class="label" style="width: 120px;"><input id="is_bid" type="checkbox" /><span style="margin-left: 10px">含结转</span></td>
        </tr> -->
    </table>
    <div id="mainGrid"></div>
</body>

</html>