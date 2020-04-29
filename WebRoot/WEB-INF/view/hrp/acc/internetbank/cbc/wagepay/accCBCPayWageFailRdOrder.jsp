<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/hrp/acc/internetbank/icbc/common.js" type="text/javascript"></script>
<script type="text/javascript">

	var year_Month = '${sessionScope.wage_year_month}';
	
	if(year_Month.toString()=="000000"){
		
		var date=new Date;
		
		var year=date.getFullYear();
		 
		var month=date.getMonth()+1;
		 
		month =(month<10 ? "0"+month:month); 
		 
		year_Month = (year.toString()+month.toString());
		
	}

    var grid;
    
    var gridManager = null;
    
    var para={
    		fseqno : '${fseqno}',
    		group_id : '${group_id}',
    		hos_id : '${hos_id}',
    		copy_code : '${copy_code}',
    		acc_year : '${acc_year}',
    		acc_month : '${acc_month}'
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
      	
      	grid.options.parms.push({name:'result',value:'6'}); 
      	
		grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
		
		grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }
    
	function pay(){
    	
		var data = gridManager.getCheckedRows();
		
    	var ParamVo =[];
    	
		if (data.length == 0){
			
         	$.ligerDialog.error('请选择行');
         	
         	return false;
         	
		}else if(data.length > 200){
			
			$.ligerDialog.error('最多只能选择200行数据');
			
			return false;
			
		}else{
			
        	 $(data).each(function (){
 				
 				ParamVo.push(this.iseqno+";"+this.fseqno+";"+this.erpsqn+";"+this.buscode);
 				
         	 })

        }
		
		var para = "&payFlag=3"+"&group_id="+data[0].group_id+"&hos_id="+data[0].hos_id+"&copy_code="+data[0].copy_code+"&fseqno="+'${fseqno}'+"&acc_year="+year_Month.substring(0,4)+"&acc_month="+year_Month.substring(4,6)+"&paramVo="+ParamVo.toString();

		$.ligerDialog.open({url: 'payAccBankNetWagePage.do?isCheck=false'+para,height: 450,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ 
				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetWage(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
	}

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '职工编码',name : 'emp_code',width: 100,align : 'left'},
					
					{display : '职工名称',name : 'emp_name',width: 140,align : 'left'},
					
					{display : '职工分类',name : 'kind_name',width: 140,align : 'left'},
					
					{display : '银行账户',name : 'recaccno',width: 180,align : 'left'},
					
					{display : '实发合计',name : 'payamt',width: 100,align : 'right',
						render:function(rowdata){ return rowdata.payamt>0?formatNumber(rowdata.payamt,2,1):formatNumber('0',2,1)}
					},

					{display : '指令状态',name : 'result',width: 150,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < resultData.length; i++)
	                        {
	                            if (resultData[i]['id'] == item.result)
	                                return resultData[i]['text']
	                        }
	                    }	
					},
					
					{display : '交易返回码',name : 'iretcode',width: 100,align : 'left'},
					
					{display : '交易返回描述',name : 'iretmsg',width: 160,align : 'left',
						render:function(rowdata){
							
							if(rowdata.result == '7'){
								
								return "交易成功";

							}else{
								
								return rowdata.iretmsg;
							}
						}
					},
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccBankNetWageRd.do?isCheck=false',parms:para,pageSize:500,
					width : '100%',height : '100%',delayLoad:true,checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
				         {line : true},
				         {text : '转换',id : 'export',click : myPrint,icon : 'down'},
				         {line : true},
				         {text : '关闭',id : 'search',click : btn_close,icon : 'delete'},
				         {line : true},
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function btn_close(){
    	frameElement.dialog.close();
    }
    
	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};

    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
   		var printPara={
   			rowCount:1,
   			title:'支付明细查询',
   			type:1,//表单级数据绑定，适用于单表头
   			columns:grid.getColumns(1)
   			};
   		ajaxJsonObjectByUrl("queryAccBankNetWageRd.do?isCheck=false", selPara, function (responseData) {
   			//printGridView(responseData,printPara);
   			parent.parent.openDialog({url:"print/printGrid.jsp",data: { responseData:responseData,printPara:printPara},title:"打印预览",width:0,height:0});
				
		});
    }
    
    function loadDict(){
    	
		autodate("#beginDate", "yyyy-mm-dd", "month_first");autodate("#endDate", "yyyy-mm-dd", "month_last");
		
		$("#beginDate").ligerTextBox({width:120});
		
		$("#endDate").ligerTextBox({width:120});

     } 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<input name="fseqno" type="hidden" id="fseqno" ltype="text" value="${fseqno}"/>
	<input name="group_id" type="hidden" id="group_id" ltype="text" value="${group_id}"/>
	<input name="copy_code" type="hidden" id="copy_code" ltype="text" value="${copy_code}"/>
	<input name="acc_year" type="hidden" id="acc_year" ltype="text" value="${acc_year}"/>
	<input name="acc_month" type="hidden" id="acc_month" ltype="text" value="${acc_month}"/>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款日期：</td>
						<td align="left" class="l-table-edit-td" style="width: 160"><input class="Wdate" name="beginDate" id="beginDate" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
						<td align="left" style="width: 15">至：</td>
						<td align="left" style="width: 160"><input class="Wdate" name="endDate" id="endDate" type="text"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			        </tr>
	   			  </table>
	   		<div id="maingrid"></div>
	
	
</body>
</html>
