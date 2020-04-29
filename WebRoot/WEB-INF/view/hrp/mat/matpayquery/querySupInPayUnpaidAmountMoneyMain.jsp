<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
	

    
    $(function (){
        loadDict();//加载下拉框
    	loadHead();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
    	grid.options.parms.push({name : 'sup_code',value : $("#sup_code").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
    	//加载查询条件
    	setTimeout("grid.loadData(grid.where)",1000)
    	//grid.loadData(grid.where);
    	
     }

    function loadHead(){
    		grid = $("#maingrid").ligerGrid({
    			columns: [{display: '供应商编码', name: 'sup_code', align: 'left', width: '120'},
    			          {display: '供应商名称', name: 'sup_name', align: 'left', width: '300'},
    			          { display: '期初金额', name: 'init_amount_money', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.init_amount_money ==null ? 0 : rowdata.init_amount_money, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  },
    			          { display: '本期入库金额', name: 'in_amount_money', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.in_amount_money ==null ? 0 : rowdata.in_amount_money, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  },
    			          { display: '本期付款金额', name: 'pay_amount_money', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.pay_amount_money ==null ? 0 : rowdata.pay_amount_money, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  },
		                  { display: '本期财务付款金额', name: 'acc_pay_money', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.acc_pay_money ==null ? 0 : rowdata.acc_pay_money, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  },
		                  { display: '差额', name: 'acc_mat_dis', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.acc_mat_dis ==null ? 0 : rowdata.acc_mat_dis, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  },
    			          { display: '期末未付金额', name: 'unpaid_amount_money', align: 'right', width: 130,
		                    	render : function(rowdata, rowindex, value) {
		    						return formatNumber(rowdata.unpaid_amount_money ==null ? 0 : rowdata.unpaid_amount_money, '${p04005 }', 1);
		    					},formatter:'###,##0.00'
		                  }
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:false,url:'querySupInPayUnpaidAmountMoney.do?isCheck=true',
    			width: '100%', height: '100%', rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    			    { text: '打印', id:'print', click: print, icon:'print' },
    				{ line:true }
    			]}, 
    		});
    		
        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
	//字典下拉框
    function loadDict(){
    	
        
		//供应商
		autocomplete("#sup_id", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',240);
		//仓库
		autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',240);
		$("#sup_id").ligerTextBox({width:240});
      	$("#sup_code").ligerTextBox({width:240});
  	    $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
      	autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
	}
	
	
  //打印
  	function print(){
	  console.log()
      	
      	if(grid.getData().length==0){
      		
  			$.ligerDialog.error("请先查询数据！");
  			
  			return;
  		}
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"入库/付款日期："},
    	          {"cell":1,"value":""+liger.get("begin_date").getValue()+"至"+liger.get("end_date").getValue()}
        	]};
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		}; 
    	var printPara={
          		title: "供应商金额汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService",
       			method_name: "querySupInPayUnpaidAmountMoneyPrint",
       			bean_name: "matAccountReportBillArrNonPayService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots:JSON.stringify(foots),//表尾打印数据,可以为空
       			
           	}; 
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

     		
      }
   
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库/付款日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
           				</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>  
            
	           
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				供应商编码名称：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
	        
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        
    </table>
	<div id="maingrid"></div>
</body>
</html>
