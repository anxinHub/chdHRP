<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<style> 
	.info {
    	padding: 5px 10px;
    	position:relative;
    	height: 50px;
        display: none;/*避免闪动初始规定不显示*/
    }
    .l-box-select{
    		z-index: 999999;
		}
		.l-grid-hd-cell-btn-checkbox{
		    display: none !important;
		} 
		
</style>
<script>
    var grid1;    
    var gridManager1 = null;    
 	var grid2;    
    var gridManager2 = null;   
    var userUpdateStr; 
    
    
    var check_method = "${a018}";//判断是对账方式
    var sumL = 0.00;//单位账金额
    var sumR = 0.00;//银行账金额
    
  	//隐藏的查询条件
    $(function (){
    	$("#mainHead").ktLayer({
            // 参数配置
            direction:"down",
            BtnbgImg:{open:'<%=path%>/lib/hrp/acc/superReport/open.png',close:'<%=path%>/lib/hrp/acc/superReport/close.png'},
            speed:"100",
           	// bgColor:"#ccc",//背景颜色
            closeHeight:0,//关闭状态高度
            Descript:["查询条件","收起条件"],//展开收起描述
	    	open:function(){}
		});
    	
    	$("#layout1").ligerLayout({ leftWidth: '49%',rightWidth:'50%',allowLeftCollapse:false,allowRightCollapse:false});
		loadDict();
    	loadHead(null);	//加载数据
    	
    	if($("#checkState").val()=='1'){
  			$("#check").ligerButton({ width:70, disabled:false});
  			$("#check").attr("disabled","");
  			$("#cancleCheck").ligerButton({width:70, disabled:true});
  			$("#cancleCheck").attr("disabled","disabled");
  			sumL = 0;
  			sumR = 0;
  			$("#ready_B_wbalAmt").text('0.00');
  			$("#ready_A_wbalAmt").text('0.00');
        	$("#balance1").text('0.00');
  		}
    	
    });
    
  	//验证
    function validate(){
    	if(liger.get("subj_code").getValue() == null || liger.get("subj_code").getValue() == ""){
    		$.ligerDialog.warn('请选择银行科目');
        	return;  
  		}
    	return true;	
    }
  	
    //左侧查询
    function  queryA(){
    	sumL = 0;
    	if(validate()){	
        	var formPara={
            	subj_code : liger.get("subj_code").getValue(),
            	checkState : liger.get("checkState").getValue(),
            	begin_dateA : $("#begin_dateA").val(),
            	end_dateA : $("#end_dateA").val(),
            	pay_type_codeA :liger.get("pay_type_codeA").getValue(),
            	priceAB :$("#priceAB").val(), 
            	priceAE :$("#priceAE").val(), 
            	check_noA : $("#check_noA").val(), 
            	summaryA : $("#summaryA").val(),
            	directA : liger.get("directA").getValue(),
    		};
        	
        	grid1.options.parms=formPara;
        	grid1.options.newPage=1;
        	grid1.loadData(grid1.where);
        	
    	}
    	
    	$("#maingrid1").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
		
    	if($("#checkState").val()!='2'){
    		$("#ready_A_wbalAmt").text('0.00');
    	}
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
    	
     }
    
  	//右侧查询
    function queryB(){
    	sumR = 0;
    	
    	if(validate()){
        	
        	var formPara={
            	subj_code : liger.get("subj_code").getValue(),
            	checkState : liger.get("checkState").getValue(),
            	
            	begin_dateB : $("#begin_dateB").val(),
            	end_dateB : $("#end_dateB").val(),
            	pay_type_codeB :liger.get("pay_type_codeB").getValue(),
            	priceBB :$("#priceBB").val(), 
            	priceBE :$("#priceBE").val(), 
            	check_noB : $("#check_noB").val(), 
            	summaryB : $("#summaryB").val(),
            	directB : liger.get("directB").getValue()
    		};
        	
        	grid2.options.parms=formPara;
        	grid2.options.newPage=1;
        	grid2.loadData(grid2.where);
    	}
    	$("#maingrid2").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
    	
    	if($("#checkState").val()!='2'){
    		$("#ready_B_wbalAmt").text('0.00');
    	}
    	
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
    }

    function loadHead(){
    	grid1 = $("#maingrid1").ligerGrid({
           columns: [ 
                     {display: '状态', name: 'state', align: 'left',width:80,
							render : function(rowdata, rowindex,value) {
								if(value == 1){
									return "已对账";
								}else{
									return "未对账";
								}
								/* if(rowdata.yCheck_money == 0){
									return "未对账";
								}							
								if((Math.abs(rowdata.debit -rowdata.credit) -rowdata.yCheck_money) == 0){
									return "已对账";
								} */
							}
                     },
					 { display: '日期', name: 'occur_date', align: 'left',width:80 },
                     { display: '结算方式', name: 'pay_name', align: 'left',width:80},
                     { display: '票据号', name: 'check_no', align: 'left',width:80},
                     { display: '凭证号', name: 'vouch_no', align: 'left',width:80},
                     { display: '摘要', name: 'summary', align: 'left',width:80},
					 { display: '借方金额', name: 'debit', align: 'right',width:80,
		                render:function(rowdata){
		                	return formatNumber(rowdata.debit,2,1);
		                }
					 },{ 
						display: '贷方金额', name: 'credit', align: 'right',width:80,
		             	render:function(rowdata){
		                	return formatNumber(rowdata.credit,2,1);
		                }
					 },{ display: '未对账金额', name: 'wcheck_money', align: 'right',width:80,
					 	render:function(rowdata){
					    	return formatNumber(rowdata.wcheck_money,2,1);
					    }
					 },{ 
						display: '已对账金额', name: 'ycheck_money', align: 'right',width:80,
						render:function(rowdata){
						    	return formatNumber(rowdata.ycheck_money,2,1);
						}
						 /* ,
						 render:function(rowdata, rowindex,value){
							var objDirect = liger.get("directA").getValue();
							var direct = 'L';
							if(rowdata.yCheck_money != 0){
								return "<a href=javascript:openUpdate('"+rowdata.veri_check_id+"|"+rowdata.bank_id+"|"+direct+"|"+objDirect+"')>"+formatNumber(rowdata.yCheck_money ==null ? 0 : rowdata.yCheck_money,2,1)+"</a>";
							}else{
								return "0.00";
							} 
						}*/
					 },{ display: '对账时间', name: 'veri_date', align: 'left',width:100
					 },{ display: '对账序列', name: 'veri_check_id', align: 'left',width:120
					 },{ display: '是否对账', name: 'is_check'}
                    ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccTellOrVouchData.do?isCheck=false',
                     width: '100%',height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     pageSizeOptions:[100, 200, 500],pageSize: 100,selectRowButtonOnly : true,
                     isScroll : true,
                     onCheckAllRow: checkAllL,
                     onCheckRow  : clickL,
                     onChangeSort:changeSortL,
                     onBeforeCheckAllRow: checkAllRowL,
                     //isSelected : L_isChecked
    		});

        gridManager1 = $("#maingrid1").ligerGetGridManager();

        grid2 = $("#maingrid2").ligerGrid({
            columns: [ 
				{ display: '日期', name: 'occur_date', align: 'left',width:80},
				{ display: '结算方式', name: 'pay_name', align: 'left',width:80},
				{ display: '票据号', name: 'check_no', align: 'left',width:80},
				{ display: '摘要', name: 'summary', align: 'left',width:80},
				{ display: '借方金额', name: 'debit', align: 'right',width:80,
		            render:function(rowdata){
		            	return formatNumber(rowdata.debit,2,1);
		            }
				},{ 
					display: '贷方金额', name: 'credit', align: 'right',width:80,
		            render:function(rowdata){
		            	return formatNumber(rowdata.credit,2,1);
		            }
				},{ 
					display: '状态', name: 'state', align: 'left',width:80,
					render : function(rowdata, rowindex,value) {
						if(rowdata.state == 1){
							return "已对账";
						}else{
							return "未对账";
						}
					}
				},{ 
					display: '未对账金额', name: 'wcheck_money', align: 'right',width:80,
					render:function(rowdata){
						  return formatNumber(rowdata.wcheck_money ==null ? 0 : rowdata.wcheck_money,2,1);
					 }
				},{ 
					display: '已对账金额', name: 'ycheck_money', align: 'right',width:80,
					render:function(rowdata){
		            	return formatNumber(rowdata.ycheck_money,2,1);
		            }
					/* ,
					render:function(rowdata, rowindex,value){
						var objDirect = liger.get("directB").getValue();
						var direct = 'R';
						if(rowdata.yCheck_money != 0){
							return "<a href=javascript:openUpdate('"+rowdata.veri_check_id+"|"+rowdata.check_id+"|"+direct+"|"+"|"+objDirect+"')>"+formatNumber(rowdata.yCheck_money ==null ? 0 : rowdata.yCheck_money,2,1)+"</a>";
						}else{
							return "0.00";
						} 
					}*/
				},{ display: '对账时间', name: 'veri_date', align: 'left',width:100
				},{ display: '对账序列', name: 'veri_check_id', align: 'left',width:120
				},{ display: '是否对账', name: 'is_check'}
               ],
               dataAction: 'server',dataType: 'server',usePager:true,//heightDiff:-10,
               url:'queryAccBankCheckData.do?isCheck=false',
               width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
               selectRowButtonOnly : true, pageSizeOptions:[100, 200, 500],pageSize: 100,
               isScroll: true,
               /* onCheckAllRow: checkAllL, */
               onCheckRow  : clickR,
               onChangeSort:changeSortR,
               onBeforeCheckAllRow:checkAllRowR,
               //isSelected : R_isChecked
             });

         gridManager2 = $("#maingrid2").ligerGetGridManager();
         
         grid1.toggleCol("is_check", false);
         grid2.toggleCol("is_check", false);
      /*    */
    }
  	//单位账全部选中
    function checkAllL(checked,element){   	
    	if(checked){
    		sumL = 0;
    		var data = gridManager1.getCheckedRows();
        	if (data.length > 0){
       			$(data).each(function (){
           			sumL = sumL + this.debit - this.credit; 
           		});
    		}
    	}else{
    		sumL = 0;
    		var data = gridManager1.getRows();
   			$(data).each(function (){	
   				sumL = sumL - this.debit + this.credit;
       		});
    	}
    	$("#ready_A_wbalAmt").text(formatNumber(sumR ==null ? 0 : sumR,2,1));
		$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
    }
    
    //银行账全部选中
    function checkAllR(checked,element){   	
    	if(checked){
    		sumR = 0;
    		var data = gridManager2.getCheckedRows();
    		if (data.length > 0){
   				$(data).each(function (){	
           			sumR = sumR - this.debit + this.credit;
           		});
    		}
    	}else{
    		sumR = 0;
    		var data = gridManager2.getRows();
   			$(data).each(function (){	
       			sumR = sumR + this.debit - this.credit;
       		});
    	}
    	$("#ready_B_wbalAmt").text(formatNumber(sumL ==null ? 0 : sumL,2,1));
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1)); 
    }
    
    
    //左侧自动勾兑选中
   	function L_isChecked(rowdata){
   		if(rowdata.is_check == '1'){
   			if($("#checkState").val()=='2'){
   				sumL = sumL + rowdata.ycheck_money; 
   			}else{
   				sumL = sumL + rowdata.wcheck_money; 
   			}
   			
   			
   			$("#ready_A_wbalAmt").text(formatNumber(sumL ==null ? 0 : sumL,2,1));
   			$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
   			return true;
   		}else{
   			return false;
   		}
   	}
  	//右侧自动勾兑选中
   	function R_isChecked(rowdata){
   		if(rowdata.is_check == '1'){
   			if($("#checkState").val()=='2'){
   				sumR = sumR + rowdata.ycheck_money;
   			}else{
   				sumR = sumR + rowdata.wcheck_money;
   			}
   			
   			$("#ready_B_wbalAmt").text(formatNumber(sumR ==null ? 0 : sumR,2,1));
   			$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
   			return true;
   		}else{
   			return false;
   		}
   	}
  	
    //左侧选中
    function clickL(checked,data,rowid,rowdata){
    	if(checked){
   			sumL = sumL + data.debit - data.credit;  
    	}else{
   			sumL = sumL - data.debit + data.credit;
    	}
    	var s = sumL;
    	if(sumR){
    		s = sumL - sumR;
    	}
    	$("#ready_A_wbalAmt").text(formatNumber(sumL, 2, 1));
		$("#balance1").text(formatNumber(s, 2, 1));
    }
    
    //重新加载数据清空金额
    function  changeSortL(data){
    	$("#ready_A_wbalAmt").text('0.00');
    	sumL=0;
    	var s=(0-sumR) ==null ? 0 : (0-sumR);
		s=s.toFixed(2);
	
    	$("#balance1").text(formatNumber(s,2,1));
    }
    //重新加载数据清空金额
    function  changeSortR(data){
    	$("#ready_B_wbalAmt").text('0.00');
    	sumR=0;
    	var s=(sumL-0) ==null ? 0 : (sumL-0);
    	s=s.toFixed(2);
    	
    	$("#balance1").text(formatNumber(s,2,1));
    }
    
    //全选勾选禁用
    function checkAllRowL(checked,element){
    	if(checked){
    		return false;
    	}
    }
    
    //全选勾选禁用
    function checkAllRowR(checked,element){
    	if(checked){
    		return false;
    	}
    }
    //右侧选中
    function clickR(checked,data,rowid,rowdata){	
    	if(checked){
   			sumR = sumR - data.debit + data.credit;
    	}else{
   			sumR = sumR + data.debit - data.credit;
    	}
    	var s = sumR;
    	if(sumL){
    		s = sumL - sumR;
    	}
    	$("#ready_B_wbalAmt").text(formatNumber(sumR, 2, 1));
		$("#balance1").text(formatNumber(s, 2, 1));
	}
    
    //查看对账信息
    function openUpdate(obj){
    	var vo = obj.split("|");
		var parm = "veri_check_id="+vo[0]
			+"&direct="+vo[2]
			+"&objDirect="+vo[3]; 
    	
    	$.ligerDialog.open({
    		url: 'accAccBankVeriInfoPage.do?isCheck=false&' + parm,data:{},  
    	    height: 550,width: 900, title:'对账信息 ',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    	    ] 
    	});
    }
    
    //确定
    function checkAll(){
    	$("#ready_A_wbalAmt").text('0.00');
		$("#ready_B_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
	    queryA();
	    queryB();
    	sumL = 0;
		sumR = 0;
    }
    
    //对账
    function check(){
    	var flag = true;
    	var dataL = gridManager1.getCheckedRows();
    	var dataR = gridManager2.getCheckedRows();
    	var dire_flagL = true;
    	var end_osL = "";
    	var old_end_osL = "";
    	$(dataL).each(function(index,obj){
    		if(obj.is_check == '1'){
    			flag = false;
    			return ; 
    		}
    		end_osL = obj.debit - obj.credit > 0 ? "正" : "负";
    		if(old_end_osL != "" && old_end_osL != end_osL){
    			dire_flagL = false;
    			return false;
    		}
    		old_end_osL = end_osL;
    	});
		
    	var dire_flagR = true;
    	var end_osR = "";
    	var old_end_osR = "";
    	$(dataR).each(function(index,obj){
    		if(obj.is_check == '1'){
    			flag = false;
    			return ; 
    		}
    		end_osR = obj.credit - obj.debit > 0 ? "正" : "负";
    		if(old_end_osR != "" && old_end_osR != end_osR){
    			dire_flagR = false;
    			return false;
    		}
    		old_end_osR = end_osR;
    	});
    	
    	if(flag == false){
    		$.ligerDialog.warn('已经对账的数据不能重复对账！');
        	return;
    	}
    	
    	if (dataL.length <= 0 && dataR.length <= 0){
        	$.ligerDialog.warn('请选择单位账中数据或银行账中数据！');
        	return;
        }
 		
 		 if(dataL.length > 1 && dataR.length > 1){
 			$.ligerDialog.warn('对账单中数据和银行账中数据必须有一方只能勾选一条');
         	return;
 		} 
 		 
 		if(dataL.length > 0 && dataR.length > 0){
 			if(!dire_flagL){
 	 			$.ligerDialog.warn('左侧对账单不能选择发生额不同方向的数据！');
 	         	return;
 			}
 			if(!dire_flagR){
 	 			$.ligerDialog.warn('右侧银行账不能选择发生额不同方向的数据！');
 	         	return;
 			}
 		}
 		 
        var lr=(sumL-sumR).toFixed(2);
       	if(lr!=0){
        	$.ligerDialog.warn('两侧对账金额不相等，请重新选择！');
        	return;
        }
       	
       	var formPara={
         		subj_code : liger.get("subj_code").getValue(),
         		subj_name : liger.get("subj_code").getText(),
         		check_method : check_method,
         		check_dataJ : JSON.stringify(dataL),
         		check_dataD : JSON.stringify(dataR)

		};
        
       	$.ligerDialog.confirm('确定要对账吗?', function (yes){
       		if(yes){
       			ajaxJsonObjectByUrl("saveAccBankVeri.do?isCheck=true",formPara,
					function (responseData){
						if(responseData.state=="true"){
			    			checkAll();
			    		}
			    	}
			    );
       		}
       	});
    }
    
	//取消对账
	function cancle(){
		var flag = true;
		var dataL = gridManager1.getCheckedRows();
		var dataR = gridManager2.getCheckedRows();
		var lr=sumL.toFixed(2)-sumR.toFixed(2);
		var Lid = {}, Rid = {};
		var dire_flagL = true;
    	var end_osL = "";
    	var old_end_osL = "";
		$(dataL).each(function(index,obj){
			if(obj.is_check == '1'){
				Lid[obj.veri_check_id] = Lid[obj.veri_check_id] ? 2 : 1;
			}
			if(obj.is_check == '0' && flag){
				flag = false;
			}
    		end_osL = obj.debit - obj.credit > 0 ? "正" : "负";
    		if(old_end_osL != "" && old_end_osL != end_osL){
    			dire_flagL = false;
    			return false;
    		}
    		old_end_osL = end_osL;
		});

    	var dire_flagR = true;
    	var end_osR = "";
    	var old_end_osR = "";
		$(dataR).each(function(index,obj){
			if(obj.is_check == '1'){
				Rid[obj.veri_check_id] = Rid[obj.veri_check_id] ? 2 : 1;
			}
			if(obj.is_check == '0' && flag){
				flag = false;
			}
    		end_osR = obj.credit - obj.debit > 0 ? "正" : "负";
    		if(old_end_osR != "" && old_end_osR != end_osR){
    			dire_flagR = false;
    			return false;
    		}
    		old_end_osR = end_osR;
		});
		
		if(!flag){
			$.ligerDialog.warn('存在未对账数据不能取消对账！');
			return;
		}
		
		if (dataL.length <= 0 && dataR.length <= 0){
			$.ligerDialog.warn('请选择单位账或银行账中的数据！');
			return;
		}
 		 
 		if(dataL.length > 0 && dataR.length > 0){
 			if(!dire_flagL){
 	 			$.ligerDialog.warn('左侧对账单不能选择发生额不同方向的数据！');
 	         	return;
 			}
 			if(!dire_flagR){
 	 			$.ligerDialog.warn('右侧银行账不能选择发生额不同方向的数据！');
 	         	return;
 			}
 		}
		
		var msg = "";
		if(dataL.length > 0 && dataR.length > 0){
			for(var i in Lid){
				if(!Rid[i]){
					msg += i + "、"
				}
			}
			for(var i in Rid){
				if(!Lid[i]){
					msg += i + "、"
				}
			}
		}else if(dataL.length > 0 && dataR.length == 0){
			for(var i in Lid){
				if(Lid[i] < 2){
					msg += i + "、"
				}
			}
		}else if(dataL.length == 0 && dataR.length > 0){
			for(var i in Rid){
				if(Rid[i] < 2){
					msg += i + "、"
				}
			}
		}
		if(msg != ""){
			$.ligerDialog.warn('对账序列号：' + msg.substring(0, msg.length-1) + "未勾选对应的对帐记录");
			return;
		}
    	
    	if(dataL != '[]' && dataR != '[]'){
        	if(lr!=0){
        		$.ligerDialog.error('单位账和银行账的金额不相等，请重新选择！');
        		return;
        	}else{
        		if(lr==0){
        			$.ligerDialog.confirm('确定要取消吗?', 
            		    function (yes){
            		    	if(yes){
            		    		var formPara = {
            		    			check_dataL : JSON.stringify(dataL),
                          			check_dataR : JSON.stringify(dataR),
                          			subj_name : liger.get("subj_code").getText()
            		            };
            		            
            		    		$.post("cancelAccBankVeri.do",formPara,function(data){
            		            	if(data.state){
            		            		$.ligerDialog.success(data.msg);
            		            		checkAll();
            		            	}else{
            		            		$.ligerDialog.error(data.error);
            		            	}
            		            },"json");
            		    	}						
            			});
        			}
        		}
        }
    }
    
    //批量取消
    function batchCancle(){
    	var parm = "subj_code="+liger.get("subj_code").getValue()+
		   "&subj_name="+liger.get("subj_code").getText();
    	$.ligerDialog.open({
    		url: 'accUnitCancelBankCheckPage.do?&'+ parm, 
    		height: 300,width: 400, title:'批量取消对账 ',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccUnitBankCheck(); },cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    		] 
    	});
    }
    
  	//字典下拉框 
    function loadDict(){
    	var param = {
        	subj_nature_code:'03',
        	is_last:1
        };
    	
        $("#priceAB").ligerTextBox({width:100});
        $("#priceAE").ligerTextBox({width:100});
        $("#priceBB").ligerTextBox({width:100});
        $("#priceBE").ligerTextBox({width:100});
        
        $("#begin_dateA").ligerTextBox({width:100});
        $("#end_dateA").ligerTextBox({width:100});
        $("#begin_dateB").ligerTextBox({width:100});
        $("#end_dateB").ligerTextBox({width:100});
        autodate("#end_dateA");
        autodate("#end_dateB");
        
        $("#summaryA").ligerTextBox({width:240});
        $("#summaryB").ligerTextBox({width:240});
        $("#check_noA").ligerTextBox({width:170});
        $("#check_noB").ligerTextBox({width:170});
        $("#business_noA").ligerTextBox({width:100});
        $("#business_noB").ligerTextBox({width:100});
        
        autoCompleteByData("#directA", [{id: 0, text: '借'}, {id: 1, text: '贷'}], "id", "text", true, false, "", false, "", 60);
        autoCompleteByData("#directB", [{id: 0, text: '借'}, {id: 1, text: '贷'}], "id", "text", true, false, "", false, "", 60);
        
        var defaultSelect = true;
        $("#subj_code").ligerComboBox({
        	parms : param,
    		url: '../querySubj.do?isCheck=false',
    		valueField: 'id',
    		textField: 'text', 
    		selectBoxWidth: 220,
    		autocomplete: true,
    	 	width: 220,
    	 	onSuccess : function (data){
    	    	var count = 0;
    			if(defaultSelect == true){
    				if(data.length >0 ){
    					if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
    						defaultSelectValue = data[0].id;
    						if( count==0){
    							this.setValue(data[0].id);
    						}
    					}
    				}
    			}
    			count++;
    	   }
    	});
            
        $("#pay_type_codeA").ligerComboBox({
        	parms : param,
    		url: '../queryPayType.do?isCheck=false',
    		valueField: 'id',
    		textField: 'text', 
    		selectBoxWidth: 170,
    		autocomplete: true,
    		width: 170,
    	});
            
        $("#pay_type_codeB").ligerComboBox({
        	parms : param,
    		url: '../queryPayType.do?isCheck=false',
    		valueField: 'id',
    		textField: 'text', 
    		selectBoxWidth: 170,
    		autocomplete: true,
    		width: 170,
    	}); 
        
        $("#checkState").ligerComboBox({
            width : 150,
            data: [
                { text: '全部', id: '0' },
                { text: '未对账', id: '1' },
                { text: '已对账', id: '2' }
            ],
            value: '0',cancelable: false,
            onSelected: function (selectValue){ 
	      		var value = this.getSelected();
	      		if(value){
	      			checkStateChange(value.id);
	      		}
	      	}
        });
        
    }  
  	
  	//对账状态改变
  	function checkStateChange(value){
  		if(value=='2'){
  			$("#check").ligerButton({width:70, disabled:true});
  			$("#check").attr("disabled","disabled");
  			$("#cancleCheck").ligerButton({width:70, disabled:false});
  			$("#cancleCheck").attr("disabled","");
  		}else if(value=='1'){
  			$("#check").ligerButton({ width:70, disabled:false});
  			$("#check").attr("disabled","");
  			$("#cancleCheck").ligerButton({width:70, disabled:true});
  			$("#cancleCheck").attr("disabled","disabled");
  		}else{
  			$("#check").ligerButton({width:70, disabled:false});
  			$("#check").attr("disabled","");
  			$("#cancleCheck").ligerButton({ width:70, disabled:false});
  			$("#cancleCheck").attr("disabled","");
  		}
  		sumL = 0;
		sumR = 0;
		$("#ready_B_wbalAmt").text('0.00');
		$("#ready_A_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
  	}
  	
  
  	//批量对账
  	function batchCheck(){
  		$.ligerDialog.open({url: 'accUnitBankBatchVeriPage.do?isCheck=false', height: 300,width: 500, title:'批量对账 ',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccUnitBankCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
  	}
  	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="mainHead" style="height:0px;">
		<div class="info">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" >
					<tr>
						<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>银行科目：</td>
						<td align="left" class="l-table-edit-td" colspan="2" style="padding-left:10px;"><div name="subj_code" type="text"  id="subj_code"  /></td>
						
						<td align="right" class="l-table-edit-td" style="padding-left:10px;">对账状态：</td>
						<td align="left" class="l-table-edit-td" ><div id="checkState" name="checkState" /></td>
						
			        	<td >
			        		<input class="l-button" style="width:70px;margin-left: 10px" type="button" id="checkA" value="确定" onclick="checkAll();"/>
			        	</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr>
			<td align="right" style="padding-right:10px;">
				
	        	<input class="l-button" style="width:70px;margin-left: 10px" type="button" id="check" value="对账" onclick="check();"/>
	            <input class="l-button" style="width:70px;margin-left: 10px" type="button" id="cancleCheck" value="取消对账" onclick="cancle();"/>
	            <input class="l-button" style="width:70px;margin-left: 10px" type="button" id="batchCheck" value="批量对账" onclick="batchCheck();"/>
	            <input class="l-button" style="width:70px;margin-left: 10px" type="button" id="cancleBatch" value="批量取消" onclick="batchCancle();"/>
	        </td>
	        
	        <td align="right" width="150"><font size="2">单位账金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_A_wbalAmt"> 0.00 </td>
	        <td align="right" width="150"><font size="2">银行账金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_B_wbalAmt"> 0.00 </td>
			<td align="right" width="120"><font size="2">差额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;color:red;size:2" id="balance1"> 0.00 </td>	
			 
		</tr>
	</table>
	
	<div id="layout1" width="100%">
		<div div="left" position="left" title="单位账" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" border='0'>
				<tr>	
					<td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位账日期：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="begin_dateA" type="text" id="begin_dateA"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		            <td align="left" class="l-table-edit-td">至：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="end_dateA" type="text" id="end_dateA"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			        
			        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">结算方式：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="pay_type_codeA" type="text"  id="pay_type_codeA"  /></td>
			       
				</tr>
				<tr>
			  		<td align="right" class="l-table-edit-td" style="padding-left:10px;">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
		            <td align="left" class="l-table-edit-td"><input name="priceAB" type="text" id="priceAB"   class="l-text-field" style="width: 80px;"  /></td>
		            <td align="left" class="l-table-edit-td">至：</td>
		            <td align="left" class="l-table-edit-td"><input name="priceAE" type="text" id="priceAE"  class="l-text-field" style="width: 80px;"  /></td>
			 　　　　　 <td align="right" class="l-table-edit-td"  style="padding-left:10px;">票&nbsp;&nbsp;据&nbsp;号：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="check_noA" type="text" id="check_noA"  class="l-text-field" style="width: 180px;"/></td>
	  　　　　 			
	  			</tr>
	  			<tr>
				  　    <td align="right" class="l-table-edit-td"  style="padding-left:10px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			        <td align="left" class="l-table-edit-td" colspan="3"><input name="summaryA" type="text" id="summaryA"  class="l-text-field" /></td>
				    
			        <td align="right" class="l-table-edit-td">方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
				    <td align="left" class="l-table-edit-td">
				  		<input id="directA" type="text" />
				  	</td>
				  	<td align="right" class="l-table-edit-td"><input class="l-button" style="width: 70px" type="button" value="查询(Q)" onclick="queryA();"/></td>
				  	
	  			</tr>
			</table>
			<div id="maingrid1"></div>
		</div>
		
		<div position="right" title="银行账">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" border='0'>
				<tr>	
					<td align="right" class="l-table-edit-td"  style="padding-left:10px;">银行账日期：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="begin_dateB" type="text" id="begin_dateB"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		            <td align="left" class="l-table-edit-td">至：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="end_dateB" type="text" id="end_dateB"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			        <td align="right" class="l-table-edit-td"  style="padding-left:10px;">结算方式：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="pay_type_codeB" type="text"  id="pay_type_codeB"  /></td>
			        
				</tr>
				<tr>
			  		<td align="right" class="l-table-edit-td" style="padding-left:10px;">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
		            <td align="left" class="l-table-edit-td"><input name="priceBB" type="text" id="priceBB"   class="l-text-field" style="width: 80px;"  /></td>
		            <td align="left" class="l-table-edit-td">至：</td>
		            <td align="left" class="l-table-edit-td"><input name="priceBE" type="text" id="priceBE"  class="l-text-field" style="width: 80px;"  /></td>
			 　　　　　 <td align="right" class="l-table-edit-td"  style="padding-left:10px;">票&nbsp;&nbsp;据&nbsp;号：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="check_noB" type="text" id="check_noB"  class="l-text-field" style="width: 180px;"/></td>
	  　　　　 			
	  			</tr>
	  			<tr>
				  　    <td align="right" class="l-table-edit-td"  style="padding-left:10px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			        <td align="left" class="l-table-edit-td" colspan="3"><input name="summaryB" type="text" id="summaryB"  class="l-text-field" /></td>
				    <td align="right" class="l-table-edit-td">方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
				    <td align="left" class="l-table-edit-td">
				  		<input id="directB" type="text" />
				  	</td>
				  	<td align="right" class="l-table-edit-td"><input class="l-button" style="width: 70px" type="button" value="查询(Q)" onclick="queryB();"/></td>
	  			</tr>
			</table>
			<div id="maingrid2"></div>
		</div>
	</div>
</body>
</html>
