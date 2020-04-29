<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/hrp/acc/internetbank/icbc/common.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var para={
    		erpsqn : '${erpsqn}',
    		group_id : '${group_id}',
    		hos_id : '${hos_id}',
    		copy_code : '${copy_code}',
    		acc_year : '${acc_year}'
    };

    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	query();
    });
    
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];grid.options.newPage=1;
		
		grid.options.parms.push({name:'erpsqn',value:'${erpsqn}'});
      	
      	grid.options.parms.push({name:'group_id',value:'${group_id}'});
      	
      	grid.options.parms.push({name:'copy_code',value:'${copy_code}'});
      	
      	grid.options.parms.push({name:'acc_year',value:'${acc_year}'});	
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '付款单号',name : 'pay_bill_no',width: 100,align : 'left'},
					
					{display : '付款日期',name : 'pay_date',width: 140,align : 'left'},
					
					{display : '供应商',name : 'sup_name',width: 190,align : 'left'},
					
					{display : '银行账户',name : 'recaccno',width: 180,align : 'left'},
					
					{display : '付款金额',name : 'payamt',width: 100,align : 'right',
						render:function(rowdata){ return rowdata.payamt>0?formatNumber(rowdata.payamt,2,1):formatNumber('0',2,1)}
					},

					{display : '指令状态',name : 'result',width: 150,align : 'left',
						/* render: function (item)
	                    {
							for (var i = 0; i < resultData.length; i++)
	                        {
	                            if (resultData[i]['id'] == item.result)
	                                return resultData[i]['text']
	                        }
	                    } */	
					},
					
					{display : '交易返回码',name : 'iretcode',width: 100,align : 'left'},
					
					{display : '交易返回描述',name : 'iretmsg',width: 190,align : 'left'},
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccBankNetBuyerRd.do?isCheck=false',parms:para,pageSize:500,
					width : '100%',height : '100%',delayLoad:true,checkbox : false,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '打印',id : 'export',click : myPrint,icon : 'down'},
				         {line : true},
				         {text : '关闭',id : 'search',click : btn_close,icon : 'delete'},
				         {line : true}
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var printPara={
           		title: "支付明细查询",//标题
           		columns: JSON.stringify(grid.getPrintColumns()),//表头
           		class_name: "com.chd.hrp.acc.service.InternetBank.AccBankNetBuyerService",
        		method_name: "queryAccBankNetBuyerRdPrint",
        		bean_name: "accBankNetBuyerService"
          			
         };
       	
       	//执行方法的查询条件
      	$.each(grid.options.parms,function(i,obj){
      			printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
    }
    
    function btn_close(){
    	frameElement.dialog.close();
    }
    
    function loadDict(){
    	
    	$("#pay_bill_no").ligerTextBox({width:180});
    	 
     } 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="maingrid"></div>
</body>
</html>
