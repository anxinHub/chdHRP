<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
 <style>
 	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	    -webkit-appearance: none;
	}
	 
	input[type="number"] {
	    -moz-appearance: textfield;
	
	}
	.l-grid-totalsummary{
		display:none;
	}
	
</style>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">	


    var grid;
    var gridManager = null;
    var userUpdateStr;
    var menu;
    var actionCustomerID;
    var enable=false;
    var onoff;
    var eidtValue = false;
    
    
    //页面初始化
    $(function (){
    	
        menu = { width: 120, items:
            [ { text: '插入', id:'insert',click: itemclickmenu,icon: 'back' }]
		}; 
        
    	$("#begin_date").ligerTextBox({ width:100});
     	$("#end_date").ligerTextBox({ width:100});
     	$("#vouch_no").ligerTextBox({ width:120});
     	
    	//获取当前时间，根据年度、月份设置凭证起止日期
		var mydate = new Date();
		var vYear = mydate.getFullYear();
		var vMon = mydate.getMonth() + 1;
		
		var acc_month;
		if(vMon<10){
			acc_month = getMonthDate(vYear,"0"+vMon);
		}else{
			acc_month = getMonthDate(vYear,vMon);
		}
		
		$("#begin_date").val(acc_month.split(";")[0]);
		$("#end_date").val(acc_month.split(";")[1]);
    	$('html').height($(window).height());
    	
    	loadHead(null);	//加载数据
    	loadDict();
    	
    });
    
    
    //查询
    function  query(){
    	
		var begin_date=$("#begin_date").val();
    	var end_date=$("#end_date").val();
    	var subj_code = liger.get("subj_code").getValue();
    	
    	if(subj_code == ''){
    		$.ligerDialog.warn('会计科目为必填项');
    		return;
    	}
    	
    	if($("#nature_code").val()==''){
   		 	$.ligerDialog.warn('出纳类型为必填项');
   		 	return;
   		}
    	
   		if(begin_date=='' || end_date==''){
  		 	$.ligerDialog.warn('起止日期为必填项');
  		 	return;
   		}else if(begin_date>end_date){
  		 	$.ligerDialog.warn('起始日期必须小于终止日期');
  		 	return;
   		}else if(begin_date.substring(0,4)!= end_date.substring(0,4)){
  		 	$.ligerDialog.warn('该查询不支持跨年查询');
  		 	return;
   		}

   		grid.options.parms=[];
    	grid.options.newPage=1;

        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'subj_code',value: liger.get("subj_code").getValue()}); 
        grid.options.parms.push({name:'nature_code',value: liger.get("nature_code").getValue()=="全部"?"":liger.get("nature_code").getValue().split(" ")[0]});
        grid.options.parms.push({name:'type_code',value: liger.get("nature_code").getValue()=="全部"?"":liger.get("nature_code").getValue().split(" ")[1]});
        grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
        grid.options.parms.push({name:'tell_num',value:$("#tell_num").val()}); 
        grid.options.parms.push({name:'tell_number',value:$("#tell_number").val()}); 
        grid.options.parms.push({name:'tell_debit',value:$("#tell_debit").val()}); 
        grid.options.parms.push({name:'summary',value:$("#summary").val()}); 
        
		if($('#vouch_no').val() != '' && $('#vouch_no').val() != null){
			grid.options.parms.push({name:'vouch_no',value:$("#vouch_no").val()}); 
    	}
        
        grid.options.parms.push({name:'tell_credit',value:$("#tell_credit").val()}); 
    	
        if($('#is_con').val() != '' && $('#is_con').val() != null){
    		grid.options.parms.push({name:'is_con',value:$('#is_con').val()});
    	}
    	if(enable==true){
    		grid.options.enabledEdit=true;
    	}else{
    		grid.options.enabledEdit=false;
    	}
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	gridManager = $("#maingrid").ligerGetGridManager();
    	
	}
    
    
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
            	{ display: 'id', name: 'tell_id', align: 'left',width:100,hide: true},
                { display: '流水号', name: 'tell_number', align: 'left',width:100},
				{ display: '出纳日期', name: 's_occur_date', align: 'left',width:100,type :'date',format : 'yyyy-MM-dd',
                	editor : {
    					type : 'date'
    				},
   				},
                
   				{ display: '摘要', name : 'summary1',  textField : 'summary',width:200, align : 'left',
	   				editor : {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: '../queryAccTellSummaryById.do?isCheck=false',
						keySupport: true,
						autocomplete: true,
 					}
   				},
   				
				{ display: '附件张数', name: 'att_num', align: 'left',width:80,editor : {type : 'text'}},
				
				{ display: '票据号', name: 'check_no', align: 'left'	,width:80,editor : {type : 'text'}},
				
				{ display: '对方科目', name: 'other_subj_code',align: 'left',textField : 'subj_name',width:100,editor : {
					type : 'select',
						valueField : 'id',
						textField : 'text', 
						url : '../querySubj.do.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
						selectBoxWidth:300 
					}
				 },
				
				{ display: '收入金额', name: 'debit', align: 'right',width:130,
   					editor : {
   						type : 'float',
   					},render:
   						function(rowdata){
  	   						return formatNumber(rowdata.debit, 2, 1);
  						},
   						totalSummary:{
   							type: 'sum',
   						  	render:
   						  		function(suminf,column){var sum = 0;
   									if(onoff) {
	   									var data = grid.getAdded();
		   								for(var i=0;i < data.length ; i++){
		   									sum +=(data[i].debit == null ? 0:data[i].debit ) *1;
		   								}
	   								}
   									return "收入合计："+sum;
   								}  
						} 
				},
   					 
				{ display: '支出金额', name: 'credit', align: 'right',width:130,editor : {type : 'float'},render:
					function(rowdata){
   	   					return formatNumber(rowdata.credit, 2, 1);
   					},
   					totalSummary:{type: 'sum',render:
   						function(suminf,column){
	   						var sum = 0;
	  						if(onoff) {
	 							var data = grid.getAdded();
	  							for(var i=0;i < data.length ; i++){
	  								sum +=(data[i].credit == null ? 0:data[i].credit ) *1;
	  							}
	  						}
  							return "支出合计："+sum;
  						}  
					}
				},
				
				{ display: '余额', name: 'bal', align: 'right',width:80,render:
					function(rowdata){
  	   					return formatNumber(rowdata.bal, 2, 1);
  					}
   				},
   				
   					 { display: '制单人', name: 'create_user_name', align: 'left',width:80
   					 },
   					 { display: '确认人', name: 'con_user_name', align: 'left',width:80
   					 },
   					 { display: '确认日期', name: 's_con_date', align: 'left',width:80
   					 },
   					 { display: '凭证号', name: 'vouch_no', align: 'left',width:80
   					 },
   					 { display: '凭证导入', name: 'is_por', align: 'left',width:80
   					 }
                        ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccCashAccount.do',/* enabledEdit : true, */
                     width: '100%', height: '100%',heightDiff:30, rownumbers:true,checkbox:true,checkBoxDisplay:isCheckDisplay,onBeforeEdit: f_onBeforeEdit,
                     selectRowButtonOnly:true,delayLoad:true,onAfterEdit : f_onAfterEdit, onLoaded :f_onLoaded,isAddRow:false,
                      
                      contextmenuEidtor:menu
                       
                   });

    }
    
    
    function f_onLoaded(){
    	
    	var nature_code = 0;
    	
    	if(nature_code =="0"){
    		
    		is_addRow();
    		
    		nature_code="1";
    		
    	}
    	
    	return;
    	
    }
    
    function isCheckDisplay(rowdata){
        //admin用户没有复选框
       	if(rowdata.tell_number == null && rowdata.summary != null){
       		return false;
       	}else if(rowdata.vouch_no != null){
       		return false;
       	}else{
       		
       	 return true;
       	}
        
    }
    //编辑前事件
    function f_onBeforeEdit(e)
    { 
    	if(e.record.summary == "期初余额" || e.record.summary == "本日合计" || e.record.summary == "本月合计" || e.record.summary == "本年累计"||e.record.summary =="流水号合计")return false;
		
		if(e.column.columnname == "s_occur_date") {
			if($('body').find('.l-window-mask').length != 0 && $('body').find('.l-window-mask').css("display") == "block"){
				return false;
			};
		}
		
		if(e.record.is_con == "1"){
			return false;
		}
		
    	return true;
    }
    
    function f_onAfterEdit(e){
    	
    	var onoff = false;  
    	
    	if(eidtValue==false){
    		
    		if(e.column.columnname=="debit"&&e.record.debit!=null&&e.record.debit!=""&&e.record.debit!=0) {
      			
    			 gridManager.updateCell("credit", "0", e.record);
    			 
    		}else if(e.column.columnname=="credit"&&e.record.credit!=null&&e.record.credit!=""&&e.record.credit!=0) {
     			
   			 	 gridManager.updateCell("debit", "0", e.record);
   			 
   			}
    		
	    	if(grid.getColumnByName('credit')._hide) {   // 确定最后可编辑的列是支出金额还是收入金额
	    		if(e.column.columnname == "debit"&&e.record.debit!=null) {
	    			onoff = true;
	    		}
	    	}else{
	    		if(e.column.columnname == "credit"){
	    			
	    			onoff = true;
	    		}
	    	}
	    	
	    	
			if (onoff) {   // 设置开关 当所对应的列是最后可编辑的列（支出金额或收入金额）时，执行
		 		var acc_para = '${a030}';
				var year_month = '${acc_year_month}';
				var validate_detail_buf = new StringBuffer();
				var d = new Date(e.record.s_occur_date)
				var year = d.getFullYear();
				var month = d.getMonth() + 1;
				var day = d.getDate();
			
				if((year +""+month) < year_month){
					validate_detail_buf.append('当前会计期间已经结账！');
				}
			
				if(liger.get("subj_code").getValue().split(".")[0] == ''){
					validate_detail_buf.append('科目编码不能为空');
				}

		    	if(e.record.summary== "" || e.record.summary == null ){
		    		validate_detail_buf.append('摘要不能为空!');
		    	}
		    
				/* if((e.record.debit == 0 && e.record.credit == 0) || (e.record.debit == "" && e.record.credit == "")){
					validate_detail_buf.append('借方金额或贷方金额填写一个');
					}
			
					if(e.record.debit!=null && e.record.credit!=null){
						validate_detail_buf.append('借方金额贷方金额填写一个');
					} */
			
				if(e.record.s_occur_date == "" || e.record.s_occur_date == null){
					validate_detail_buf.append('出纳日期不能为空！');
				}
					
				if(validate_detail_buf.toString() != ""){
				 	$.ligerDialog.warn("第"+(e.rowindex + 1)+"行"+validate_detail_buf.toString(), '提示','',{closeWhenEnter:false});
					return false;
			 	}
				
				var formPara = {
					cash_subj_code : liger.get("subj_code").getValue(),
					debit : (e.record.debit == "" ? "0":e.record.debit),
					credit : (e.record.credit == ""? "0":e.record.credit),
					check_no : (e.record.check_no == null ? "":e.record.check_no),
					att_num :e.record.att_num==""?0:e.record.att_num,
					occur_date : year + '-' + (month < 10 ? "0" + month : month) + '-' + (day < 10 ? "0" + day : day),
					other_subj_code:(e.record.other_subj_code == null ? "": e.record.other_subj_code).split(".")[0],
					summary : e.record.summary,
					pay_type_code : e.record.pay_name,
					tell_number :'',
					tell_id : e.record.tell_id,
					tell_type_code: liger.get("nature_code").getValue()
				};
				//sumMoney();
				
				if (e.record.tell_id == null) {
					if(acc_para == 1){
						$.ligerDialog.confirm('是否往对方科目添加出纳账？',"出纳账",function(yes) {
							if (yes) {
								var formPara = {
									cash_subj_code : liger.get("subj_code").getValue(),
									debit : (e.record.debit == "" ? "0":e.record.debit),
									credit : (e.record.credit == "" ? "0":e.record.credit),
									check_no :(e.record.check_no == null ? "":e.record.check_no),
									att_num :e.record.att_num==""?0:e.record.att_num,
									occur_date : year + '-' + (month < 10 ? "0" + month : month) + '-' + (day < 10 ? "0" + day : day),
									other_subj_code:(e.record.other_subj_code == null ? "": e.record.other_subj_code).split(".")[0],
									summary : e.record.summary,
									pay_type_code : (e.record.pay_code == null ? "":e.record.pay_code),
									tell_number :'',
									tell_id : e.record.tell_id,
									subj_id:'1',
									tell_type_code : liger.get("nature_code").getValue()
								};
						
								ajaxJsonObjectByUrl("addAccCashAccount.do", formPara, function(responseData) {
									if (responseData.state == "true") {
										grid.updateCell('tell_id',responseData.tell_id, e.rowindex);
										grid.addRowEdited({'s_occur_date':responseData.occur_date},'summary1');
									}
								});
							}else {
								var formPara = {
									cash_subj_code : liger.get("subj_code").getValue(),
									debit : (e.record.debit == "" ? "0":e.record.debit),
									credit : (e.record.credit == "" ? "0":e.record.credit),
									check_no :(e.record.check_no == null ? "":e.record.check_no),
									att_num :e.record.att_num==""?0:e.record.att_num,
									occur_date : year + '-' + (month < 10 ? "0" + month : month) + '-' + (day < 10 ? "0" + day : day),
									other_subj_code:(e.record.other_subj_code == null ? "": e.record.other_subj_code).split(".")[0],
									summary : e.record.summary,
									pay_type_code : (e.record.pay_code == null ? "":e.record.pay_code),
									tell_number :'',
									tell_id : e.record.tell_id,
									tell_type_code : liger.get("nature_code").getValue()
								};

								ajaxJsonObjectByUrl("addAccCashAccount.do", formPara, function(responseData) {
									if (responseData.state == "true") {
										grid.updateCell('tell_id',responseData.tell_id, e.rowindex);
										grid.addRowEdited({'s_occur_date':responseData.occur_date},'summary1');
									}
								});
							}
						},{closeWhenEnter:false});
				}else if(acc_para == 0){
					ajaxJsonObjectByUrl("addAccCashAccount.do", formPara, function(responseData) {
						if (responseData.state == "true") {
							grid.updateCell('tell_id',responseData.tell_id, e.rowindex);
							grid.addRowEdited({'s_occur_date':responseData.occur_date},'summary1');
						}
					});
				}
			} else if (e.record.tell_id != 0) {
				ajaxJsonObjectByUrl("updateAccCashAccount.do", formPara, function(responseData) {
					if (responseData.state == "true") {
						grid.addRowEdited({'s_occur_date':responseData.occur_date},'summary1');
					}
				});
			}
		}
	   return true;
    	}
    }

    function add_open(){
    	
    	$.ligerDialog.open({url: 'addAccCashAccountPage.do?isCheck=false',height: 400,width: 470,title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function delete_btn(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
        }
        var flag;
		var ParamVo =[];
        $(data).each(function (){
        	
			 if(this.is_con == '1'){
				 $.ligerDialog.warn('已经确认的数据不能删除');
				 return flag = false;
			 }
			
			if(this.tell_id != undefined){
				ParamVo.push(
					//表的主键
					this.tell_id+"@"+ 
					this.s_occur_date+"@"+
					this.tell_number.split("-")[1]+"@"+
					liger.get("nature_code").getValue().split(" ")[0]+"@"+
					liger.get("subj_code").getValue()
				)
			} 
         });
        
        if(flag == false){
        	
        	return;
        }
        
        if(ParamVo.length == 0 ){
        	$.ligerDialog.warn('请选择出纳账每日发生的数据');
        	return; 
        }

		 $.ligerDialog.confirm('确定要删除吗？', '删除', function(flag){
				if(flag){
					
					 ajaxJsonObjectByUrl("deleteAccCashAccount.do",{ParamVo : ParamVo.toString()},function(responseData){
				            
				            if(responseData.state=="true"){
				                query();
				            }
				        });
             		
				}
		 });
    }
    
    /* function add_btn(){
    	
    	eidtValue=true;
    	var data = grid.getAdded();
    	var updateData = gridManager.getUpdated();
		var ParamVo =[];
        $(data).each(function (){
        	var d = new Date(this.s_occur_date);
			var year = d.getFullYear();
			var month = d.getMonth() + 1;
			var day = d.getDate();
			var subj=liger.get("subj_code").getValue();
			
			if(this.summary!= null){
				ParamVo.push(
						(this.debit==null?0:this.debit)+"@"+
						(this.credit==null?0:this.credit)+"@"+
						(this.check_no==null?0:this.check_no)+"@"+
						(this.att_num==null?0:this.att_num)+"@"+
						year + '-' + (month < 10 ? "0" + month : month) + '-' + (day < 10 ? "0" + day : day)
						+"@"+(this.other_subj_id == null ? "": this.other_subj_id)
						+"@"+ this.summary+"@"+ liger.get("nature_code").getValue().split(" ")[0]+"@"+subj
					)
			}
         });

		$(updateData).each(function (){
        	var d = new Date(this.s_occur_date);
			var year = d.getFullYear();
			var month = d.getMonth() + 1;
			var day = d.getDate();
			var subj=liger.get("subj_code").getValue();
			if(this.summary!= null){
				ParamVo.push(
						(this.debit==null?0:this.debit)+"@"+
						(this.credit==null?0:this.credit)+"@"+
						(this.check_no==null?0:this.check_no)+"@"+
						(this.att_num==null?0:this.att_num)+"@"+
						year + '-' + (month < 10 ? "0" + month : month) + '-' + (day < 10 ? "0" + day : day)
						+"@"+(this.other_subj_id == null ? "": this.other_subj_id)
						+"@"+ this.summary+"@"+ liger.get("nature_code").getValue().split(" ")[0]+"@"+subj+"@"+(this.tell_id==""?"":this.tell_id)
					)
			}

         });

		 ajaxJsonObjectByUrl("addBatchAccCashAccount.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	eidtValue=true;
	                query();
	            }
	        });  
             		
    }  */
    
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    function imp(){
    	$.ligerDialog.open({url: 'accCashAccountImportPage.do?isCheck=false', height: 500,width: 1135, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
    }
    
    function con(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
        }                    
        
        var flag;
        $(data).each(function (){
        	
			 if(this.is_con == '1'){
				 $.ligerDialog.warn('已经确认的数据不能在此确认');
				 return flag = false;
			 }

        });
        
         if(flag == false){
        	
        	return;
        }
   
    	$.ligerDialog.open({url: 'updateAccCashAccountStatePage.do?isCheck=false',
    			data:{data:data},
    			height: 300,
    			width: 400,
    			title:'',
    			modal:true,
    			showToggle:false,
    			showMax:false,
    			showMin: false,
    			isResize:true,
    			buttons: [ 
    			           { text: '确定',
    			        	 onclick: function (item, dialog) {dialog.frame.accCashAccountCon();},
    			        	 cls:'l-dialog-btn-highlight' 
    			        	 },
    			           { text: '取消', 
    			        	 onclick: function (item, dialog) { dialog.close(); } 
    			        	 } 
    			        ] });
    }
    
    function cancelCon(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
        }  
        var flag;
        var ParamVo =[];
        $(data).each(function (){
        	
				 if(this.is_con == '0'){
					 $.ligerDialog.warn('已经取消确认的数据不能再次取消确认');
					 return flag = false;
				 }
			ParamVo.push(
					//表的主键
					this.tell_id
				)
        });
        
        if(flag == false){
        	
        	return;
        }
		 $.ligerDialog.confirm('确定要进行取消确认操作吗？', '取消确认', function(flag){
				if(flag){
					 ajaxJsonObjectByUrl("updateAccCashAccountCancelState.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
				            if(responseData.state=="true"){
				                query();
				            }
				        });
             		
				}
		 });
    }

   
    function itemclickmenu(item, i)
    {
    	if(item.id == "insert"){
        
    		 $.ligerDialog.open({url: 'addAccCashAccountPage.do?isCheck=false&tell_number='+actionCustomerID.split(",")[1]+"&occur_date=" + actionCustomerID.split(",")[2] ,height: 400,width: 470,title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	}

    }
    function openUpdate(obj){

    	$.ligerDialog.open({ url : 'updateAccCashAccountPage.do?isCheck=false&tell_id=' + obj,data:{}, height: 400,width: 470, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function print_btn(){
    	 if(grid.getData().length==0){
 			$.ligerDialog.warn("请先查询数据！");
 			return;
 		}
        	
    	 var heads={
    			 "isAuto": true,
    			  "rows": [
    		      {"cell":0,"value":"科目名称："+liger.get("subj_code").getText(),"colSpan":"5"}
    		      ]
    			}; 
	
		var printPara={
				title: "现金出纳账",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.tell.AccTellService",
				method_name: "queryCashAccountPrint",
				bean_name: "accTellService" ,
				heads: JSON.stringify(heads) 
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);

    }
    
    function loadDict(){
            //字典下拉框
    	$("#subj_code").ligerComboBox({ width:160});
            
    	$("#tell_num").ligerTextBox({ width:100});
    	
    	$("#tell_number").ligerTextBox({width:100});
    	
		$("#tell_debit").ligerTextBox({ width:65});
    	
    	$("#tell_credit").ligerTextBox({width:65});
    	
    	$("#summary").ligerTextBox({width:160});

		var begin_date=$("#begin_date").val();
        
        autocomplete("#subj_code","../querySubj.do?isCheck=false&is_last=1&subj_nature_code=02&acc_year="+begin_date.substring(0,4),"id","text",true,true,'',true);

    	autocompleteObj({
	    		id:"#nature_code",
	    		urlStr:"../queryAccTellType.do?isCheck=false&nature_code=02",
	    		valueField:"id",
	    		textField:"text",
	    		autocomplete:true,
	    		highLight:true,
	    		parmsStr:null,
	    		defaultSelect:true,
	    		initvalue:null,
	    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,
	    		selectEvent:function (value){
		           
						if(value != '全部'){
	    				
	    				if(value.split(" ")[1] == "01"){
	    					
	    					grid.toggleCol('debit', true);
	    					
	    					grid.toggleCol('credit', false);
	    					
	    				}else if(value.split(" ")[1]=="02"){
	    					
							grid.toggleCol('credit', true);
	    					
	    					grid.toggleCol('debit', false);
	    					
	    				}else{
	    					
							grid.toggleCol('debit', true);
	    					
	    					grid.toggleCol('credit', true);
	    					
	    				}
	    				
	    				enable=true;return;
	    				
	    			}else{
	    				
	    				grid.toggleCol('debit', true);
						
						grid.toggleCol('credit', true);
						
		    			enable=false; return;
		    			
	    			}
	    			
				}
	    	});
    	
    	$("#tell_type_attr").ligerComboBox({ width:160});
            
    	$("#is_con").ligerTextBox({ width:120});
    	
    	$(':button').ligerButton({width:80});
     
    }   
    
	function is_addRow() {
		
		onoff = true;
		
		setTimeout(function() { //当数据为空时 默认新增一行
			
			grid.addRow({'s_occur_date':new Date()});
		
			$(".l-grid-totalsummary").show();
			
			$(".l-grid-totalsummary-cell-inner").css('text-Align','center');
			
		}, 100);
		
	}
	
	function add_tellVouch(){
		
		if(liger.get("nature_code").getText()=="全部"){
			$.ligerDialog.warn("生成凭证出纳类型不能为全部！");
 			return;
		}		
		
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return;
        }
		
		var ParamVo =[];
		var tell_id = '';
		var init_tell=false;
		var vouch_date = '';
		var msg = '';
		var num = 0 ;
		
        $(data).each(function (){
        	
/*         	if(num != 0){
	        	if(this.vouch_date != vouch_date ){
	        		msg = '只能选择发生日期在同一天的数据生成凭证';
	        		return ; 
	        	}
        	} */
        	
        	tell_id+=this.tell_id+",";
        	
			ParamVo.push(
				this.tell_id+"@"+
				this.s_occur_date+"@"+
				this.summary+"@"+
				this.att_num+"@"+
				this.other_subj_code+"@"+
				this.debit+"@"+
				this.credit+"@"+
				liger.get("subj_code").getValue()+"@"+
				liger.get("nature_code").getValue().split(" ")[0]
			)
				
			if(this.con_user_name==null||this.con_user_name==""){
				init_tell=true;
			}
			
			vouch_date = this.s_occur_date;
			num ++ ;
        });
        
		if(msg != ''){
        	$.ligerDialog.warn(msg);
 			return;
        }
        
        if(init_tell){
        	$.ligerDialog.warn("所选流水登记含有未确认记录！");
 			return;
        }
        
        var param = {
        	ParamVo : ParamVo.toString(),
        }
        
        ajaxJsonObjectByUrl("queryAccTellVouch.do?isCheck=false",param,function(responseData){
            
        	if(responseData.state=="true"){
        		
            	var loadIndex = layer.load(1);
            	
            	 var para = {
                 		ParamVo:ParamVo.toString(),
                 		vouch_type_code:responseData.vouch_type_code,
                 		vouch_date : vouch_date
             	};
                
                ajaxJsonObjectBylayer("addAccTellVouch.do?isCheck=false",para,function(responseData){	
        			
        			layer.close(loadIndex);
        			
        			var para={
        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch&openBusiType=tell',
        				title:'会计凭证',
        				width:0,
        				height:0,
        				isShowMin:true,
        				isModal:true,
        				parentframename: window.name,
        				data:{auto_id:responseData.vouch_id, busi_log_table:'ACC_BUSI_LOG_ZZ', busi_type_code:'Z011',busi_no:tell_id.substring(0,tell_id.length-1)}
        			};
        			parent.openDialog(para);
          		},layer,loadIndex);
            	
            }else{
            	
            	$.ligerDialog.open({url: 'chooseAccVouchTypePage.do?isCheck=false&tell_id='+tell_id+'&subj_id='+liger.get("subj_code").getValue().split(".")[0],
        			data:{data:data},
        			height: 300,
        			width: 400,
        			title:'',
        			modal:true,
        			showToggle:false,
        			showMax:false,
        			showMin: false,
        			isResize:true,
        			buttons: [ 
        			           { text: '确定',
        			        	 onclick: function (item, dialog) {dialog.frame.accCashAccountCon();
        			        
        			        	 },
        			        	 cls:'l-dialog-btn-highlight' 
        			        	 },
        			           { text: '取消', 
        			        	 onclick: function (item, dialog) { dialog.close(); } 
        			        	 } 
        			        ] });
            	
            }
        });
        

	}
	
	function cYearFunc(){
		cFunc('y');
		
	}
	
	 function cFunc(obj){

		 var p,c = $dp.cal;
		 if(obj=='y'){
		 p='y';
		 }
		 else if(obj=='M'){
		 p='M';
		 }
		 else if(obj=='d'){
		 p='d';
		 }
		 
        autocomplete("#subj_code","../querySubj.do?isCheck=false&is_last=1&subj_nature_code=02&acc_year="+c.newdate[p],"id","text",true,true,'',true);
 
	} 
	 
	 function addSummary (){ 
		 
			parent.$.ligerDialog.open({ url : 'hrp/acc/acctell/accTellSummaryPage.do?isCheck=false' ,data:{}, height: 470,width:500, title:'摘要维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				  });

		 
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="3px" color="red">*</font>日&nbsp;&nbsp;&nbsp;期:</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="begin_date" type="text" id="begin_date" value="${begin_date}"
				ltype="text" validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:'${modStartTime}',ychanged:cYearFunc})" /></td>
			<td align="center" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="end_date" type="text" id="end_date" value="${end_date}"
				ltype="text" validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:'${modStartTime}'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="3px" color="red">*</font>科目编码：</td>
			<td align="left" class="l-table-edit-td" colspan="4"><input name="subj_code"
				type="text" id="subj_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<!-- <td align="left"></td> -->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="3px" color="red">*</font>出纳类型属性：</td>
			<td align="left" class="l-table-edit-td"><input name="nature_code"
				type="text" id="nature_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><select id="is_con">
					<option value="">全部</option>
					<option value="1">确认</option>
					<option value="0">未确认</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">流水号:</td>
			<td align="left" class="l-table-edit-td"><input
				name="tell_num" type="number" id="tell_num" 
				ltype="text" validate="{required:true,maxlength:20}" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"/></td>
			<td align="center" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td">
			<input name="tell_number" type="number" id="tell_number" 
				ltype="text" validate="{required:true,maxlength:20}" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"/></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="tell_debit" type="number" id="tell_debit" 
				ltype="text" validate="{required:true,maxlength:20}"/></td>
			<td align="center" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td">
			<input name="tell_credit" type="number" id="tell_credit" 
				ltype="text" validate="{required:true,maxlength:20}"/></td>
			<td align="left" ></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td"><input name="summary"
				type="text" id="summary" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否生成凭证：</td>
			<td align="left" class="l-table-edit-td"><select id="vouch_no">
					<option value="">全部</option>
					<option value="1">是</option>
					<option value="0">否</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	<div style="border: 1px">
		<input type="button" value=" 查询" onclick="query();" /> 
		<!-- <input type="button" value=" 保存" onclick="add_btn();"/> -->
		<input type="button" value=" 删除" onclick="delete_btn();" />
	    <input type="button" value=" 下载导入模板" onclick="downTemplate();" /> 
	    <input type="button" value="打印" onclick="print_btn();"/>
	    <input type="button" value=" 导入" onclick="imp();" />
	    <input type="button" value=" 确认" onclick="con();" />
	    <input type="button" value=" 取消确认" onclick="cancelCon();" />
	    <input type="button" value=" 生成凭证" onclick="add_tellVouch();" />
	    <input type="button" value="摘要维护" onclick="addSummary();" />
	</div>
	<div id="maingrid"></div>
</body>
</html>
