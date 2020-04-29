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

    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    });
    
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];grid.options.newPage=1;
        
		grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
		
		grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }
    
  	//查询
    function  updateAccBankNetWage(){//根据表字段进行添加查询条件

    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
		if (data.length == 0){
         	$.ligerDialog.error('请选择行');
		}else{
        	 $(data).each(function (){
 				
 				ParamVo.push(this.group_id +"@"+ this.hos_id +"@"+ this.copy_code+"@"+ this.acc_year+"@"+ this.acc_month+"@"+ this.fseqno);
 				
         	 })

        	 $.ligerDialog.confirm('确定更新指令状态?', function (yes){if(yes){
     			
     			ajaxJsonObjectByUrl("updateAccBankNetWage.do?isCheck=false&ParamVo="+ParamVo.toString(),{}, function(responseData) {
     	
     				if (responseData.state == "true") {
     					
     					query();
     					
     				}
     			});
     		}});
        }	
    }
    
    function exportData(){
		
	}

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '付款日期',name : 'trandate',width: 140,align : 'left',
						render: function (rowdata)
	                    {
							return "<a href=javascript:openUpdate('"+rowdata.fseqno+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.acc_year+"|"+rowdata.acc_month+"')>"+rowdata.trandate+" "+rowdata.trantime+"</a>";
	                    }
					},
					
					{display : '流水号',name : 'serialno',width: 140,align : 'left'},
					
					{display : '入账方式',name : 'settlemode',width: 140,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < settleModeData.length; i++)
	                        {
	                            if (settleModeData[i]['id'] == item.settlemode)
	                                return settleModeData[i]['text']
	                        }
	                    }	
					},
					
					{display : '总笔数',name : 'totalnum',width: 80,align : 'left'}, 
					
					{display : '总金额',name : 'totalamt',width: 80,align : 'right',
						render:function(rowdata){ return rowdata.totalamt>0?formatNumber(rowdata.totalamt,2,1):formatNumber('0',2,1)}	
					},
					
					{display : '交易返回码',name : 'retcode',width: 160,align : 'left',
						render:function(rowdata){

							if(rowdata.retcode ==  0){
								
								if(typeof(rowdata.serialno) == 'undefined'){
									
									return "请更新指令状态"; 
									
								}else{
									
									return "成功"; 
									
								}

							}else{
								
								return rowdata.retcode; 
								
							}
						}
					},

					{display : '交易返回描述',name : 'retmsg',align : 'left',
						render:function(rowdata){

							if(rowdata.retcode != 0){
								
								return rowdata.retmsg;
								

							}else{
								
								return "指令更新成功";
								
							}
							
							
						}
					}
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccBankNetWage.do',pageSize:50,delayLoad:true,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '更新指令状态',id : 'search',click : updateAccBankNetWage,icon : 'up'},
				         {line : true},
				         /* {text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
				         {line : true}, */
				         {text : '失败查询',id : 'carry',click : openFailUpdate,icon : 'bcancle'},
				         {line : true}
				         /* {text : '导出',id : 'search',click : exportData,icon : 'down'},
				         {line : true} */
				         ]
			},lodop:{
         		title:"工资项目",
      			fn:{
          			debit:function(value){//借方
          				if(value == 0){return "";}
                 			else{return formatNumber(value, 2, 1);}
          			},
          			credit:function(value){//贷方
          				if(value == 0){return "";}
                			else{return formatNumber(value, 2, 1);}
         				},
         				end_os:function(value){//余额
      	   				 if(value==0){return "Q";}
      					 else{return formatNumber(value, 2, 1);}
        				}
          		}
      		}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    //打印回调方法
    function lodopPrint(){
    /* 	var accStr="不包含未记账"
       	if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
       	
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#beginDate").val()+"至"+$("#endDate").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "fseqno="+vo[0]   
			+"&group_id="+ vo[1]   
			+"&hos_id="+ vo[2]   
			+"&copy_code="+ vo[3]
			+"&acc_year="+ vo[4]
			+"&acc_month="+ vo[5];
		$.ligerDialog.open({ url : 'queryAccBankNetWageRdPage.do?isCheck=false&' + parm,data:{}, height: 520,width: 1180, title:'支付单明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});

    }
    
	function openFailUpdate(){

		$.ligerDialog.open({ url : 'queryAccBankNetWageFailRdPage.do?isCheck=false',data:{}, height: 520,width: 1180, title:'失败支付单明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});

    }
    
	function pay(){
    	
		var data = gridManager.getCheckedRows();
    	
		if (data.length == 0){
			
         	$.ligerDialog.error('请选择行 并且只能选择一条数据支付');return false;
         	
		}else if(data.length > 1){
			
			$.ligerDialog.error('只能选择一条数据支付');return false;
			
		}
		
		var para = "&payFlag=1"+"&group_id="+data[0].group_id+"&hos_id="+data[0].hos_id+"&copy_code="+data[0].copy_code+"&fseqno="+data[0].fseqno+"&acc_year="+year_Month.substring(0,4)+"&acc_month="+year_Month.substring(4,6);

		$.ligerDialog.open({url: 'payAccBankNetWagePage.do?isCheck=false'+para,height: 460,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				/* buttons: [ 
				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetWage(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]  */
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