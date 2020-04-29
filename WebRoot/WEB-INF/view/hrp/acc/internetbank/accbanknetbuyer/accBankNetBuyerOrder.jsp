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
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var check_column1_manager;

    var year_Month = '${sessionScope.wage_year_month}';
	
	if(year_Month.toString()=="000000"){
		
		var date=new Date;
		
		var year=date.getFullYear();
		 
		var month=date.getMonth()+1;
		 
		month =(month<10 ? "0"+month:month); 
		 
		year_Month = (year.toString()+month.toString());
		
	}
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
		$("#check_column1").change(function () { 
			
			query();
			
		});

    });
    
    
    
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
		grid.options.parms=[];grid.options.newPage=1;
		
		if(check_column1_manager.getValue()){
			
			grid.options.parms.push({name:'result',value:'6'});
			
		}
        
		grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
		
		grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
		
		grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue()});
		
		grid.options.parms.push({name:'pay_bill_no',value:$("#pay_bill_no").val()});
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     
    }
    
  	//查询
    function  downloadOrder(){//根据表字段进行添加查询条件

      	var acc_time= liger.get("acc_time").getValue();
    
    	if(!acc_time){
    		
    		$.ligerDialog.warn('请选择会计区间！');
    		
    		return false;
    		
    	}
    	
    	var item_code = liger.get("item_code").getValue();
    	
		if(!item_code){
    		
    		$.ligerDialog.warn('请选择工资项目！');
    		
    		return false;
    		
    	}

		var formPara = {
				
				acc_year : acc_time.split(".")[0],
				
				acc_month : acc_time.split(".")[1],
				
				item_code : item_code

		};
    	
    	$.ligerDialog.confirm('确定生成支付单?', function (yes){if(yes){
			
			ajaxJsonObjectByUrl("downloadAccBankNetWageOrder.do?isCheck=false", formPara, function(responseData) {
	
				if (responseData.state == "true") {
					
					query();
					
				}
			});
		}});
     
    }

    function exportData(){
		
	}

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '付款日期',name : 'trandate',width: 140,align : 'left',
						render: function (rowdata)
	                    {
							return "<a href=javascript:openUpdate('"+rowdata.erpsqn+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.acc_year+"')>"+rowdata.trandate+" "+rowdata.trantime+"</a>";
	                    }
					},
					{display : '付款单号',name : 'pay_bill_no',width: 100,align : 'left'},
					
					 { display: '凭证号', name: 'vouch_no', align: 'left',width:100,
						render: function (rowdata){
							if(rowdata.vouch_no){
								if(rowdata.vouch_no=='-'){
									return rowdata.vouch_no;
								}else{
									return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
								}
							}else{
								return '';
							}
						}
				 	},
				 	
				 	{display : '凭证状态',name : 'state_name',width: 90,align : 'left'},
					{display : '供应商',name : 'sup_name',width: 190,align : 'left'},
					
					/* {display : '流水号',name : 'serialno',width: 140,align : 'left'}, */
					
					/* {display : '入账方式',name : 'settlemode',width: 140,align : 'left',
						render: function (item)
	                    {
							for (var i = 0; i < settleModeData.length; i++)
	                        {
	                            if (settleModeData[i]['id'] == item.settlemode)
	                                return settleModeData[i]['text']
	                        }
	                    }	
					}, 
					
					{display : '总笔数',name : 'totalnum',width: 80,align : 'left'}, */
					
					{display : '总金额',name : 'totalamt',width: 80,align : 'right',
						render:function(rowdata){ return rowdata.totalamt>0?formatNumber(rowdata.totalamt,2,1):formatNumber('0',2,1)}	
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
					
					{display : '交易返回描述',name : 'iretmsg',width: 240,align : 'left',
						render: function (item)
	                    {
							if(item.retcode == 'error'){return item.retmsg} 
							else{return item.iretmsg}
	                    }	
					}
					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryAccBankNetBuyer.do',pageSize:500,delayLoad:true,
					width : '100%',height : '100%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,heightDiff: -10,rownumbers:true,
					toolbar : {
					items : [
				         {text : '查询',id : 'search',click : query,icon : 'search'},
				         {line : true},
				         {text : '更新指令状态',id : 'search',click : updateAccBankNetBuyer,icon : 'up'},
				         {line : true},
				         {text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
						 {line : true}
				         /* {text : '导出',id : 'search',click : exportData,icon : 'down'},
				         {line : true} */
				         ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  	//查询
    function  updateAccBankNetBuyer(){//根据表字段进行添加查询条件

    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
		if (data.length == 0){
         	$.ligerDialog.error('请选择行');
		}else if(data.length >50){
			$.ligerDialog.error('每次最多更新50条');
		}else{
			var busi_no="";//生成凭证使用
			
        	 $(data).each(function (){
 				
 				ParamVo.push(this.group_id +"@"+ this.hos_id +"@"+ this.copy_code+"@"+ this.acc_year+"@" + this.fseqno+"@" + this.vouch_id+"@" + this.vouch_state);
 				
 				busi_no=busi_no+this.pay_id+","
 				
         	 })
         	 
         	 busi_no=busi_no.substring(0,busi_no.length-1);

        	 $.ligerDialog.confirm('确定更新指令状态?', function (yes){if(yes){
     			
     			ajaxJsonObjectByUrl("updateAccBankNetBuyer.do?isCheck=false&ParamVo="+ParamVo.toString(),{}, function(responseData) {
     	
     				if (responseData.state == "true") {
     					
     					query();
     					
     					<%-- var para={
     							busi_date_b:'',
     							busi_date_e:'',
     							template_code:'001',
     							vouch_date:'${vouch_date}',
     							init_type:1,
     							mod_code:'04',
     							busi_type_code:'040703',
     							busi_no:busi_no,
     							busi_log_table:'ACC_BUSI_LOG_0408',
     							huizong_sql:''
     					};
     					//console.log(para);
     					var loadIndex = layer.load(1);
     		    		ajaxJsonObjectBylayer("<%=path%>/hrp/acc/autovouch/matautovouch/queryVouchJsonByBusi.do",para,function(responseData){	
     		    			//console.log(responseData);
     		    			layer.close(loadIndex);
     		    			var para={
     		    				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
     		    				title:'会计凭证',
     		    				width:0,
     		    				height:0,
     		    				isShowMin:true,
     		    				isModal:true,
     		    				data:{vouch:responseData.vouch,
     		    					busi_log_table:'ACC_BUSI_LOG_0408',
     		    					busi_type_code:'040703',
     		    					busi_no:responseData.busi_no,
     		    					template_code:'001'}
     		    			};
     		    			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
     		    			//console.log(para);
     		    			parent.parent.openDialog(para);
     		      		},layer,loadIndex);
     					 --%>
     				}
     			});
     		}});
         }	
    }
  	
    function pay(){
    	
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str_state = '';
    	var str_sup = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 
				if(data[0].sup_id != this.sup_id){str_sup += this.sup_id +",";}
				
				ParamVo.push(this.erpsqn +"@"+ this.sup_id +"@"+ this.totalamt+"@"+this.pay_bill_no+"@"+this.vouch_id+"@"+this.vouch_state+"@"+ this.sup_no);
				
        	 })
        	 
        	if(str_sup != ''){$.ligerDialog.error('必须选择相同供应商');return false;}
    
        	 var paramVo = ParamVo.toString();
        	 $.ligerDialog.open({url: 'collectAccBankNetBuyerPage.do?isCheck=false&payFlag=0&paramVo='+ParamVo,height: 500,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
 				/* buttons: [ 
 				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetBuyer(); },cls:'l-dialog-btn-highlight' }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]  */
        	 }); 
        }	
    }
	function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "erpsqn="+vo[0]   
			+"&group_id="+ vo[1]   
			+"&hos_id="+ vo[2]   
			+"&copy_code="+ vo[3]
			+"&acc_year="+ vo[4];
    	$.ligerDialog.open({ url : 'queryAccBankNetBuyerRdPage.do?isCheck=false&' + parm,data:{}, height: 520,width: 1180, title:'支付单明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});

    }
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    function loadDict(){
    	
		autodate("#beginDate", "yyyy-mm-dd");autodate("#endDate", "yyyy-mm-dd", "month_last");
		
		$("#beginDate").ligerTextBox({width:100});
		
		$("#endDate").ligerTextBox({width:100});
		
		autocomplete("#sup_id", "../../../mat/queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',275);
		
		check_column1_manager = $("#check_column1").ligerCheckBox();check_column1_manager.setValue(false);

		$("#pay_bill_no").ligerTextBox({width:160});
     } 

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款日期：</td>
						<td align="left" class="l-table-edit-td" style="width: 90px"><input class="Wdate" name="beginDate" id="beginDate" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
						<td align="left" style="width: 5px">至：</td>
						<td align="left" style="width: 90px"><input class="Wdate" name="endDate" id="endDate" type="text"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
						
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
						<td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
						<td align="left" style="padding-left: 20px;"></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款单号：</td>
						<td align="left" class="l-table-edit-td"><input name="pay_bill_no" type="text" id="pay_bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
						<td align="left"></td>
						<td align="left"><input name="check_column1" type="checkbox" id="check_column1" ltype="text"  />显示失败</td>
			        </tr>
	   			  </table>
	   		<div id="maingrid"></div>
	
	
</body>
</html>
