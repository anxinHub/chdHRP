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
    
    var payData;
    
    $.post("../../queryPayType.do?isCheck=false",null,function(data){payData = data;},"json");

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
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
		grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(".")[0]}); 
		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue().split(".")[0]}); 
		//grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'apply_code',value:$("#apply_code").val()});
		grid.options.parms.push({name:'apply_date_b',value:$("#apply_date_b").val()}); 
		grid.options.parms.push({name:'apply_date_e',value:$("#apply_date_e").val()});
		grid.options.parms.push({name:'result_state',value:liger.get("result_state").getValue()});
    	grid.loadData(grid.where);//加载查询条件
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '借款单号', name: 'apply_code', align: 'left',width:130,
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id   + "|" + 
										rowdata.hos_id   + "|" + 
										rowdata.copy_code   + "|" + 
										rowdata.apply_code + "')>"
										+ rowdata.apply_code + "</a>";
							}
			 		 		},
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
			 		 { display: '申请日期', name: 'apply_date', align: 'left',width:130},	
			 		 
                     { display: '科室', name: 'dept_name', align: 'left',width:180},
                     
                     { display: '项目', name: 'proj_name', align: 'left',width:130},
                     
                     { display: '借款人', name: 'emp_name', align: 'left',width:130},
                     
                     { display: '借款金额', name: 'borrow_amount', align: 'left', align: 'right',width:130,
						render: function(item){
					    	return formatNumber(item.borrow_amount,2,1);
					   	} 
					 },
					 { display: '制单人', name: 'maker_name', align: 'left',width:130},
					 
	                 { display: '制单日期', name: 'make_date', align: 'left',width:130},
	                 
                     { display: '审核人', name: 'checker_name', align: 'left',width:130},
                     
                     { display: '审核日期', name: 'check_date', align: 'left',width:130},
                     
					 { display: '支付人', name: 'payer_name', align: 'left',width:130},
					 
                     { display: '支付日期', name: 'pay_date', align: 'left',width:130},
                     
					 /* { display: '支付方式', name: 'pay_way', align: 'center',
					 	render: function(rowdata){
					 		var text;
					 		$.each(payData,function(i,v){
								if(rowdata.pay_way == v.id){											
									text= v.text;
								}
							});
					 		return text;
					    } 
					 },	 */	
                     { display: '状态', name: 'state_name', align: 'left',width:130},
                     { display: '支付状态', name: 'result_state', align: 'left',width:130}
					 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBorrApply.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,checkBoxDisplay:isCheckDisplay,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
						//{ text : '确认（<u>S</u>）',id : 'pay',click : pay,icon : 'ok'}
                     	{text : '网上支付',id : 'carry',click : pay,icon : 'communication'},
						{line : true},
				        {text : '转换',id : 'export',click : myPrint,icon : 'down'},
				        {line : true}
						                
				    ]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{//双击行事件
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.apply_code
							);
						
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
       		title: "借款网银",//标题
       		columns: JSON.stringify(grid.getPrintColumns()),//表头
       		class_name: "com.chd.hrp.acc.service.InternetBank.AccBankNetBorrService",
    		method_name: "queryAccBorrApplyPrint",
    		bean_name: "accBankNetBorrService"
      			
         };
       	
       	//执行方法的查询条件
      	$.each(grid.options.parms,function(i,obj){
      			printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
        	
    }
    
    //网上支付
    function pay(){
    	
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
        if (data.length == 0){
        	
        	$.ligerDialog.error('请选择行');
        	
        }else{
        	
        	 $(data).each(function (){
				
				ParamVo.push(this.apply_code+"@"+this.borrow_amount);
				
        	 })
        	 
        	 var paramVo = ParamVo.toString();	
        	 $.ligerDialog.open({url: 'collectAccBankNetBorrPage.do?isCheck=false&paramVo='+ParamVo,height: 450,width: 870,title:'补录网上银行信息',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
 				/* buttons: [ 
 				           { text: '确定支付', onclick: function (item, dialog) {dialog.frame.saveAccBankNetBuyer(); },cls:'l-dialog-btn-highlight' }, 
 				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]  */
        	 }); 
        }	
    }

	function openUpdate(obj){
    	
		var vo = obj.split("|");
		
    	var parm ="group_id="+vo[0] +"&"+ 
		"hos_id="+vo[1] +"&"+ 
		" copy_code="+vo[2] +"&"+ 
    		"apply_code="+vo[3];
    	parent.$.ligerDialog.open({
			title: '借款支付',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/payable/loanmt/pay/budgBorrApplyUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
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
        
    	autocomplete("#dept_id", "../../../sys/queryDeptDict.do?isCheck=false", "id","text", true, true,null,false,null,"200");   

    	autocomplete("#proj_id", "../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false,null,"200"); 

    	autocomplete("#emp_id","../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,null,"200");
       
    	$("#apply_date_b").ligerTextBox({width : 90});
    	
    	$("#apply_date_e").ligerTextBox({width : 90});
    	
    	autodate("#apply_date_b", "yyyy-mm-dd", "month_first");autodate("#apply_date_e", "yyyy-mm-dd", "month_last");
    	
    	$("#apply_code").ligerTextBox({width : 200});
    	//状态
    	$("#result_state").ligerComboBox({
	     	data:result_state_data,
	      	valueField: 'id',
	      	textField: 'text', 
	       	selectBoxWidth: 200,
	      	autocomplete: true,
	      	width: 200
		});
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">申请日期：</td>
            <td align="left" class="l-table-edit-td" style="width: 90px"><input name="apply_date_b" class="Wdate" type="text" id="apply_date_b" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left">至</td>
            <td align="left" class="l-table-edit-td" style="width: 90px"><input name="apply_date_e" class="Wdate" type="text" id="apply_date_e" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">借款人：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="emp_id" type="text" id="emp_id"  /></td>

            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">借款单号：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_code" type="text" id="apply_code"  /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付状态：</td>
            <td align="left" class="l-table-edit-td"><input name="result_state" type="text" id="result_state"  /></td>
            <td align="left"></td>
        </tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
