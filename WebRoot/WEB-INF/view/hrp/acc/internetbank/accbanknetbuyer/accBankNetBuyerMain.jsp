<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

    var grid;
    
    var gridManager = null;

    $(function (){//加载数据
    	
        loadDict()//加载下拉框
    	
    	loadHead(null);
    
        query();
        
		loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
		grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
		//grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
		grid.options.parms.push({name:'pay_bill_no',value:$("#pay_bill_no").val()});
		grid.options.parms.push({name:'result_state',value:liger.get("result_state").getValue()});
    	grid.loadData(grid.where);//加载查询条件
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '付款单号', name: 'pay_bill_no', align: 'left',width:120,
					 			render:function(rowdata,index,value){
					 				if(rowdata.pay_bill_type == 0){
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pay_id+"|"+
				 						rowdata.state+"|"+
				 						rowdata.vouch_id+"')>"+rowdata.pay_bill_no+"</a>";
					 				}else if(rowdata.pay_bill_type != 0 && rowdata.pay_bill_no !="合计"){
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pay_id+"|"+
				 						rowdata.state+"|"+
				 						rowdata.vouch_id+"')>"+rowdata.pay_bill_no+"<font color='red'>(退)</font></a>";
					 				}else if(rowdata.pay_bill_no =="合计"){
					 					return "合计";
					 				}
					 			}
					 		},
					 /* { display: '单据类型', name: 'pay_bill_type', align: 'left',width:100,
					 			render : function(rowdata,rowindex,value){
					 				if(rowdata.pay_bill_type == 0){
					 					return "付款单";
					 				}else{
					 					return "退款单";
					 				}
					 			}
					 		}, */
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
                     { display: '单据日期', name: 'pay_date', align: 'left',width:100
					 		},
                     { display: '供应商', name: 'sup_name', align: 'left',width:280
					 		},
					 { display: '应付金额', name: 'payable_money', align: 'right',width:100,
					 			render:function(rowdata){ return rowdata.payable_money>0?formatNumber(rowdata.payable_money,2,1):formatNumber('0',2,1)}
					 		},
					 { display: '已付金额', name: 'payed_money', align: 'right',width:100,
					 			render:function(rowdata){ return rowdata.payed_money>0?formatNumber(rowdata.payed_money,2,1):formatNumber('0',2,1)}
					 		},		
				 	 { display: '付款金额', name: 'pay_money', align: 'right',width:100,
					 			render:function(rowdata){ return rowdata.pay_money>0?formatNumber(rowdata.pay_money,2,1):formatNumber('0',2,1)}
					 		},
					 { display: '制单人', name: 'maker_name', align: 'left',width:96
					 		},
					 /* { display: '制单日期', name: 'make_date', align: 'left',width:100
					 		}, */
                     { display: '审核人', name: 'checker_name', align: 'left',width:96
					 		},
                    /*  { display: '审核日期', name: 'chk_date', align: 'left',width:100
					 		}, */
                     { display: '状态', name: 'state', align: 'left',width:80,
					 			render:function(rowdata,index,value){
					 				if(rowdata.state == 1){
					 					return "未审核";
					 				}else if (rowdata.state == 2){
					 					return "已审核";
					 				}else{
					 					return "已确认";
					 				}
					 			}
					 		},
		                     { display: '支付状态', name: 'result_state', align: 'left',width:130}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatPayMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,checkBoxDisplay:isCheckDisplay,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,pageSize:500,
                     delayLoad: true,//初始化不加载，默认false
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
		                //{ text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook' },
						//{ line:true },
						//{ text: '消审（<u>U</u>）', id:'unaudit', click: unaudit,icon:'bookpen' },
						//{ line:true },
						{text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
						{line : true},
				        {text : '转换',id : 'export',click : myPrint,icon : 'down'},
				        {line : true}
    				]}
    				 ,onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.pay_id  + "|" + 
								rowdata.state 
							);
    				} ,
    				lodop:{
    	         		title:"材料付款查询",
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
    function isCheckDisplay(rowdata){
        //admin用户没有复选框
       	if(rowdata.result_state == '未支付' || rowdata.result_state == '支付失败'){
       		return true;
       	}else{
       	 return false;
       	}
        
    }
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	function myPrint(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
		var printPara={
       		title: "材料付款查询",//标题
       		columns: JSON.stringify(grid.getPrintColumns()),//表头
       		class_name: "com.chd.hrp.acc.service.InternetBank.AccBankNetBuyerService",
    		method_name: "queryMatPayMainPrint",
    		bean_name: "accBankNetBuyerService"
      			
         };
       	
       	//执行方法的查询条件
      	$.each(grid.options.parms,function(i,obj){
      			printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
           	
    }
    
	 //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#beginDate").val()+"至"+$("#endDate").val()+"</td>";
 		grid.options.lodop.head=head;
    }

    //网上支付
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
				
				if(this.state != 2){str_state += this.pay_bill_no +",";}
				
				ParamVo.push(this.pay_id +"@"+ this.sup_id +"@"+ this.pay_money+"@"+this.pay_bill_no+"@"+this.vouch_id+"@"+this.vouch_state+"@"+ this.sup_no);
				
        	 })
        	 
        	if(str_sup != ''){$.ligerDialog.error('必须选择相同供应商');return false;}
        	 
        	 if(str_state != ''){
             	$.ligerDialog.error('付款单号：<span style="color:red">'+str_state+'不是审核状态</span>（只有审核状态的付款单允许付款） ');
             	return false;
             }
        	 
        	 var paramVo = ParamVo.toString();	
        	 $.ligerDialog.open({url: 'collectAccBankNetBuyerPage.do?isCheck=false&payFlag=0&paramVo='+ParamVo,height: 500,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
 				/* buttons: [ 
 				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetBuyer(); },cls:'l-dialog-btn-highlight' }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }] */
        	 }); 
        }	
    }

    //查看
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"pay_id="+vo[3] 
			$.ligerDialog.open({ url : '../../../mat/matpay/matpaymain/matPayMainUpdatePage.do?isCheck=false&'+parm,data:{}, top: 0,
				height: 580,width: 1000, title:'查看',modal:true,showToggle:false,showMax:true,
				showMin: false,isResize:true,
				});
    }
    
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 1){
        			 str += this.pay_bill_no +",";
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code +"@"+ this.pay_id +"@"+ this.pay_bill_no +"@"+this.state +"@"+2
					)};
        	 })
        	 if(str != ''){
             	$.ligerDialog.error('付款单号：<span style="color:red">'+str+'不是未审核状态</span>（只有未审核状态的付款单允许审核） ');
             }else{
             	 $.ligerDialog.confirm('确定审核所选付款单吗?', function (yes){
     	             	if(yes){
     	                 	ajaxJsonObjectByUrl("../../../mat/matpay/matpaymain/updatePayState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
     	                 		if(responseData.state=="true"){
     	                 			loadHead(null);
     	                 		}
     	                 	});
     	             	}
     	          }); 
             }
        }	
    }
    //消审
    function unaudit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '' ;
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			 str += this.pay_bill_no +",";
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pay_id +"@"+ this.pay_bill_no +"@"+this.state +"@"+ 1 
					)};
        	 })
        	 if(str != ''){
              	$.ligerDialog.error('付款单号：<span style="color:red">'+str+'不是已审核状态</span>（只有已审核状态的付款单允许消核） ');
             }else{
             	 $.ligerDialog.confirm('确定消审所选付款单吗?', function (yes){
     	             	if(yes){
     	                 	ajaxJsonObjectByUrl("../../../mat/matpay/matpaymain/updatePayState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
     	                 		if(responseData.state=="true"){
     	                 			loadHead(null);
     	                 		}
     	                 	});
     	             	}
     	          }); 
             }
        }	
       
    }
   
    function loadDict(){//字典下拉框
    	
    	var result_state_data = [{ id: 1, text: '全部'},{ id: 2, text: '已支付' },{ id: 3, text: '未支付'},{ id: 4, text: '正在处理'}];
          
		//供应商下拉框
		autocomplete("#sup_id", "../../../mat/queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',275);
			//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
			//autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
			//采购员下拉框
			//autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true);
		autodate("#beginDate", "yyyy-mm-dd", "month_first");autodate("#endDate", "yyyy-mm-dd", "month_last");
		
		$("#beginDate").ligerTextBox({width:120});
		
		$("#endDate").ligerTextBox({width:120});
		
		$("#pay_bill_no").ligerTextBox({width:160});
		
		//状态
    	$("#result_state").ligerComboBox({
	     	data:result_state_data,
	      	valueField: 'id',
	      	textField: 'text', 
	       	selectBoxWidth: 120,
	      	autocomplete: true,
	      	width: 120
		});
		
		//var state_data = [{id:'1',text:'未审核'},{id:'2',text:'审核'},{id:'3',text:'已确认'}];
		//$("#state").ligerComboBox({width:160,data:state_data});
		
		//var pay_bill_type_data = [{id:'0',text:'付款单'},{id:'1',text:'退款单'}];$("#pay_bill_type").ligerComboBox({width:160,data:pay_bill_type_data});
	}
    
	function loadHotkeys() {//键盘事件

		hotkeys('Q', query);
		
		hotkeys('Z', audit);

		hotkeys('U', unaudit);

	 }
	   
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款日期：</td>
			<td align="left" class="l-table-edit-td" style="width: 160"><input class="Wdate" name="beginDate" id="beginDate" type="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" style="width: 15">至：</td>
			<td align="left" style="width: 160"><input class="Wdate" name="endDate" id="endDate" type="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			
			<!-- <td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><input name="state" id="state" style="width: 135px;"/></td>
			<td align="left"></td> -->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款单号：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_bill_no" type="text" id="pay_bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付状态：</td>
            <td align="left" class="l-table-edit-td"><input name="result_state" type="text" id="result_state"  /></td>
            <td align="left"></td>
        </tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
