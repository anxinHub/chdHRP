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
            height: 80px;
            display: none;/*避免闪动初始规定不显示*/
        }
        .l-box-select{
    		z-index: 999999;
		}
</style>

<script>
	
	var grid1;
	var gridManager1 = null;    
	var grid2;    
	var gridManager2 = null;  
	var is_check = 0;//勾选未记账  不勾：0 
	var is_auto = 0 ;//核销方式   0 手动核销，1 自动核销
	var R_isCheckSum = 0;//记录右侧查询勾选的金额
	var is_flag = 0;//是在再次循环勾选
	var sumJ = 0.00;//记录借方总金额
	var sumD = 0.00;//记录贷方总金额
	var proj1 = null;
	var check_id = 0;
	var acc_monthLB ;
	var acc_monthRB ;
	var subj_id ;
	var wbal_amt_check = 0;
	var bill_no = 0;
	var in_no = 0;
	var in_date = 0;
	var occur_date_check = 0;
	var due_date_check = 0;
	var summary_check = 0;
	
	var flag = false;
	var dataJId =[];//存放rowid
	var dataDId =[];//存放rowid
	var dataJAll="";//取消核销功能 取消勾选的全部已核销的数据(借方)
	var dataDAll="";//取消核销功能 取消勾选的全部已核销的数据(贷方)
	var acc_month = "${acc_year_month}";
	var leftData;
    var rightData;
    
    $(function (){
    	//隐藏的查询条件
    	$("#mainHead").ktLayer({
            // 参数配置
            direction:"down",
            BtnbgImg:{open:'<%=path%>/lib/hrp/acc/superReport/open.png',close:'<%=path%>/lib/hrp/acc/superReport/close.png'},
            speed:"100",
           	// bgColor:"#ccc",//背景颜色
            closeHeight:0,//关闭状态高度
            Descript:["查询条件","收起条件"],//展开收起描述
	    	open:function(){
	    		
	    	}
        });
    	
    	$("#layout1").ligerLayout({ leftWidth: '49%',rightWidth:'50%'});
		loadForm();
		loadDict();
    	loadHead(null);	//加载数据
    	
    	if($("#veriState").val()=='1'){
    		$("#veri").ligerButton({ width:70, disabled:false});
  			$("#veri").attr("disabled","");
  			$("#cancleVeri").ligerButton({width:70, disabled:true});
  			$("#cancleVeri").attr("disabled","disabled");
  		}
    	sumJ = 0;
    	sumD = 0;
    	$("#ready_D_wbalAmt").text('0.00');
    	$("#ready_J_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
    	
    });
    
  	//验证
    function validate(){
    	if(liger.get("subj_code").getValue() == null || liger.get("subj_code").getValue() == ""){
  			$.ligerDialog.warn("供应商不能为空！");  
  			return false;  
  		}
    	
    	return true;	
    }
    
    //借方查询
    function queryC(){

    	sumJ = 0;
    	$("#ready_J_wbalAmt").text('0.00');
    	$("#balance1").text($("#ready_D_wbalAmt").text());
    	if(validate()){
    		if(liger.get("subj_code").getValue() !='' && liger.get("subj_code").getValue() != null){
        		subj_id = liger.get("subj_code").getValue().split(".")[1];
    		}else{
    			subj_id = liger.get("subj_code").getValue();
    		}
        	
        	if($('input[name="auto"]:checked').val()=='1'){
        		if($("#wbal_amt_check").attr("checked") == true){
    		    	wbal_amt_check = 1;
    		    }else{
    		    	wbal_amt_check = 0;
    		    }
    			
    		    if($("#bill_no").attr("checked") == true){
    		    	bill_no = 1;
    		    }else{
    		    	bill_no = 0;
    		    }
    		    
    		    if($("#in_no").attr("checked") == true){
    		    	in_no = 1;
    		    }else{
    		    	in_no = 0;
    		   	}
    		    
    		    if($("#in_date").attr("checked") == true){
    		    	in_date = 1;
    		    }else{
    		    	in_date = 0;
    		    }
    		    
    		    if($("#occur_date_check").attr("checked") == true){
    		    	occur_date_check = 1;
    		    }else{
    		    	occur_date_check = 0;
    		    }
    		    
    		    if($("#due_date_check").attr("checked") == true){
    		    	due_date_check = 1;
    		    }else{
    		    	due_date_check = 0;
    		    }
    		    
    		    if($("#summary_check").attr("checked") == true){
    		    	summary_check = 1;
    		    }else{
    		    	summary_check = 0;
    		    }
        	}
        	
        	if($("#is_check").attr("checked") == true){
        		is_check = 1;
        	}else{
        		is_check = 0;
        	}
        	
        	if(proj1 == '' || proj1 == null){
    			proj1 = liger.get("proj1").getText().split(" ")[0];
    	    	proj2 = liger.get("proj2").getText().split(" ")[0];
    		}else{
    			proj1 = proj1.split(" ")[0];
    	    	proj2 = proj1.split(" ")[0];
    		} 
        	
        	if(liger.get("year_MonthCB").getValue() ==''){
        		acc_monthLB = '0'; 
        	}else{
        		acc_monthLB = liger.get("year_MonthCB").getValue().split(".")[0];
        	}
        	
        	if(liger.get("year_MonthDB").getValue() ==''){
        		acc_monthRB = '0' ; 
        	}else{
        		 acc_monthRB = liger.get("year_MonthDB").getValue().split(".")[0];
        	}
        	
        	var formPara={
        		sup_id : liger.get("sup_code").getValue().split(".")[0],
                sup_no : liger.get("sup_code").getValue().split(".")[1],
            	veriState : liger.get("veriState").getValue(),
            	is_check : is_check,
            	is_auto :  $('input[name="auto"]:checked').val(),
            	wbal_amt_check : wbal_amt_check,
            	bill_no : bill_no,
            	in_no : in_no,
            	in_date : in_date,
            	occur_date_check : occur_date_check,
            	due_date_check : due_date_check,
            	summary_check :summary_check,
            	acc_monthLB : acc_monthLB,
            	acc_monthLE : liger.get("year_MonthCE").getValue().split(".")[0] ,
            	summaryL : $("#summaryC").val(),
            	priceL1 : $("#accrualCB").val(),
            	priceL2 : $("#accrualCE").val(),
            	con_noL : $("#con_noC").val(),
            	business_noL : $("#business_noC").val(),
            	acc_monthRB : acc_monthRB,
            	acc_monthRE : liger.get("year_MonthDE").getValue().split(".")[0],
            	summaryR : $("#summaryD").val(),
            	priceR1 : $("#accrualDB").val(),
            	priceR2 : $("#accrualCE").val(),
            	con_noR : $("#accrualDE").val(),
            	business_noR : $("#business_noD").val()
            	
    		};
        	
        	ajaxJsonObjectByUrl("queryAccVerificationL.do?isCheck=false",formPara,
    	    	function (responseData){
    	    		leftData = responseData.left;
    	    		grid1.loadData(leftData);
    	    	}
    	    );
    	}
    	$("#maingrid1").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
    	
    }
  	
  	//贷方查询
    function queryD(){
    	sumD = 0;
    	$("#ready_D_wbalAmt").text('0.00');
    	$("#balance1").text($("#ready_J_wbalAmt").text());
    	if(validate()){
    		if(liger.get("subj_code").getValue() !='' && liger.get("subj_code").getValue() != null){
        		subj_id = liger.get("subj_code").getValue().split(".")[1];
    		}else{
    			subj_id = liger.get("subj_code").getValue();
    		}
        	
        	if($('input[name="auto"]:checked').val()=='1'){
        		if($("#wbal_amt_check").attr("checked") == true){
    		    	wbal_amt_check = 1;
    		    }else{
    		    	wbal_amt_check = 0;
    		    }
    			
    		    if($("#bill_no").attr("checked") == true){
    		    	bill_no = 1;
    		    }else{
    		    	bill_no = 0;
    		    }
    		    
    		    /* if($("#in_no").attr("checked") == true){
    		    	in_no = 1;
    		    }else{
    		    	in_no = 0;
    		   	} */
    		    
    		    if($("#in_date").attr("checked") == true){
    		    	in_date = 1;
    		    }else{
    		    	in_date = 0;
    		    }
    		    
    		    if($("#occur_date_check").attr("checked") == true){
    		    	occur_date_check = 1;
    		    }else{
    		    	occur_date_check = 0;
    		    }
    		    
    		   /*  if($("#due_date_check").attr("checked") == true){
    		    	due_date_check = 1;
    		    }else{
    		    	due_date_check = 0;
    		    }
    		    
    		    if($("#summary_check").attr("checked") == true){
    		    	summary_check = 1;
    		    }else{
    		    	summary_check = 0;
    		    } */
        	}
        	
        	if($("#is_check").attr("checked") == true){
        		is_check = 1;
        	}else{
        		is_check = 0;
        	}
        	
        	if(proj1 == '' || proj1 == null){
    			proj1 = liger.get("proj1").getText().split(" ")[0];
    	    	proj2 = liger.get("proj2").getText().split(" ")[0];
    		}else{
    			proj1 = proj1.split(" ")[0];
    	    	proj2 = proj1.split(" ")[0];
    		} 
        	
        	if(liger.get("year_MonthCB").getValue() ==''){
        		acc_monthLB = '0'; 
        	}else{
        		acc_monthLB = liger.get("year_MonthCB").getValue().split(".")[0];
        	}
        	
        	if(liger.get("year_MonthDB").getValue() ==''){
        		acc_monthRB = '0' ; 
        	}else{
        		 acc_monthRB = liger.get("year_MonthDB").getValue().split(".")[0];
        	}
        	
        	var formPara={
        		sup_id : liger.get("sup_code").getValue().split(".")[0],
                sup_no : liger.get("sup_code").getValue().split(".")[1],
            	check_type : liger.get("check_type").getValue().split(" ")[0],
            	check_type_id : liger.get("check_type").getValue().split(" ")[1],
            	proj1 : proj1,
            	proj2 : proj2,
            	veriState : liger.get("veriState").getValue(),
            	is_check : is_check,
            	is_auto :  $('input[name="auto"]:checked').val(),
            	wbal_amt_check : wbal_amt_check,
            	bill_no : bill_no,
            	in_no : in_no,
            	in_date : in_date,
            	occur_date_check : occur_date_check,
            	due_date_check : due_date_check,
            	summary_check :summary_check,
            	
            	acc_monthLB : acc_monthLB,
            	acc_monthLE : liger.get("year_MonthCE").getValue().split(".")[0] ,
            	summaryL : $("#summaryC").val(),
            	priceL1 : $("#accrualCB").val(),
            	priceL2 : $("#accrualCE").val(),
            	con_noL : $("#con_noC").val(),
            	business_noL : $("#business_noC").val(),
            	
            	
            	acc_monthRB : acc_monthRB,
            	acc_monthRE : liger.get("year_MonthDE").getValue().split(".")[0],
            	summaryR : $("#summaryD").val(),
            	priceR1 : $("#accrualDB").val(),
            	priceR2 : $("#accrualCE").val(),
            	con_noR : $("#accrualDE").val(),
            	business_noR : $("#business_noD").val()
            	
    		};
        	ajaxJsonObjectByUrl("queryAccVerificationR.do?isCheck=false",formPara,
    	    	function (responseData){
    	    		rightData = responseData.right;
    	    		grid2.loadData(rightData);
    	    	}
    	    );
    	}
    	$("#maingrid2").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
    }
  	
    function openSuperVouch(vouch_id){
    	
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    function loadHead(){
    	grid1 = $("#maingrid1").ligerGrid({
        columns: [ 	
                    { display: '供应商名称', name: 'sup_name', align: 'left',width:80
                    },
                    { display: '单据号', name: 'in_no', align: 'left',width:80},
					{ display: '发票号', name: 'bill_no', align: 'left',width:80},
					 { display: '单据日期', name: 'in_date', align: 'left',width:80
					 },
					 { display: '应付金额', name: 'veri_money', align: 'right',width:100,
						render:function(rowdata){
							  return formatNumber(rowdata.veri_money ==null ? 0 : rowdata.veri_money,2,1);
						 }
					 },
 					 { display: '已付款金额', name: 'amount_money', align: 'left',width:80}
                  ],
                  dataAction: 'server',dataType: 'server',usePager:true,delayLoad:true,url:'queryAccMpayVeriL.do?isCheck=true',
                  width: '100%', height: '100%', checkbox : true,rownumbers:true, isSingleCheck : false,isScroll:true,
                  selectRowButtonOnly : true,//pageSize :10,//heightDiff: -10,
                  onCheckRow  : clickL, onCheckAllRow: checkAllL,isSelected : L_isChecked
                  
        });
        gridManager1 = $("#maingrid1").ligerGetGridManager();
        
        grid2 = $("#maingrid2").ligerGrid({
            columns: [  
                     { display: '供应商名称', name: 'sup_name', align: 'left',width:80
                     },
                     { display: '交易类型', name: 'check_name', align: 'left',width:80},
 					 { display: '单据号', name: 'in_no', align: 'left',width:80},
 					 { display: '发票号', name: 'bill_no', align: 'right',width:80,
					 },
					 { display: '单据日期', name: 'in_date', align: 'left',width:80
					 },
					 { display: '应付金额', name: 'amount_money', align: 'right',width:100,
						render:function(rowdata){
							  return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,2,1);
						 }
					 },
 					 { display: '已付款余额', name: 'veri_money', align: 'left',width:80}
            ],
           dataAction: 'server',dataType: 'server',usePager:true,delayLoad:true,url:'queryAccMpayVeriR.do?isCheck=false',
           width: '100%', height: '100%', //pageSize :10,
           checkbox: true ,rownumbers:true,isSingleCheck :false,isScroll:true,
           selectRowButtonOnly:true,onCheckRow  : clickR , onCheckAllRow: checkAllR,isSelected : R_isChecked
       });

       gridManager2 = $("#maingrid2").ligerGetGridManager();
       
       grid1.toggleCol("check_code", false);
       grid1.toggleCol("check_id", false);
       grid1.toggleCol("is_check", false);
       
       grid2.toggleCol("check_code", false);
       grid2.toggleCol("check_id", false);
       grid2.toggleCol("is_check", false);
       
    }
    
    //左侧选中
    function clickL(checked,data,rowid,rowdata){

    	$("#ready_J_wbalAmt").text(data.amount_money);
    	
    	var ready_D_wbalAmt=$("#tit td").eq(4).text();
    	
		$("#balance1").text(formatNumber((data.amount_money-ready_D_wbalAmt) ==null ? 0 : (data.amount_money-ready_D_wbalAmt),2,1));
    }
  
    //右侧选中
    function clickR(checked,data,rowid,rowdata){	
    	
		$("#ready_D_wbalAmt").text(data.amount_money);
    	
    	var ready_j_wbalAmt=$("#ready_J_wbalAmt").text();
    	
    	var ready_D_wbalAmt=$("#ready_D_wbalAmt").text();
    	
		$("#balance1").text(formatNumber(ready_J_wbalAmt-ready_D_wbalAmt,2,1));
	}
    
    //左侧全部选中
    function checkAllL(checked,element){
    	if(checked){
    		sumJ = 0;
    		var data = gridManager1.getCheckedRows();
    		dataJAll = gridManager1.getCheckedRows();
    		//alert(JSON.stringify(dataJAll));
        	if (data.length > 0){
        		$(data).each(function (){	
        			sumJ = sumJ + this.debit;
        		});
    		}
    	}else{
    		sumJ = 0;
    		if($("#veriState").val()=='2'){
    			//获取取消行的rowid
    			$(dataJAll).each(function (){
    				dataJId.push(this['__id']);
    			});
    		}
    	}
    	$("#ready_J_wbalAmt").text(formatNumber(sumJ ==null ? 0 : sumJ,2,1));
    	$("#balance1").text(formatNumber((sumJ-sumD) ==null ? 0 : (sumJ-sumD),2,1)); 	
    }
    
    //右侧全选、全部取消按钮
    function checkAllR(checked,element){   	
    	if(checked){
    		sumD = 0;
    		var data = gridManager2.getCheckedRows();
    		dataDAll = gridManager2.getCheckedRows();
        	if (data.length > 0){
        		$(data).each(function (){
        			sumD = sumD + this.credit;
        		});
    		}
    	}else{
    		sumD = 0;
    		if($("#veriState").val()=='2'){
    			//获取取消行的rowid
    			$(dataDAll).each(function (){
    				dataDId.push(this['__id']);
    			});
    		}
    	}
    	$("#ready_D_wbalAmt").text(formatNumber(sumD ==null ? 0 : sumD,2,1));
		$("#balance1").text(formatNumber((sumJ-sumD) ==null ? 0 : (sumJ-sumD),2,1));
    }
    
   	//左侧自动核销选中
   	function L_isChecked(rowdata){
   		if(rowdata.is_check == '1'){
			sumJ = sumJ + rowdata.debit;
   			$("#ready_J_wbalAmt").text(formatNumber(sumJ ==null ? 0 : sumJ,2,1));
   			$("#balance1").text(formatNumber((sumJ-sumD) ==null ? 0 : (sumJ-sumD),2,1));
   			return true;
   		}else{
   			return false;
   		}
   	}
  	//右侧自动核销选中
   	function R_isChecked(rowdata){
  		
   		if(rowdata.is_check == '1'){
   			sumD = sumD + rowdata.credit;
   			$("#ready_D_wbalAmt").text(formatNumber(sumD ==null ? 0 : sumD,2,1));
   			$("#balance1").text(formatNumber((sumJ-sumD) ==null ? 0 : (sumJ-sumD),2,1));
   			return true;
   		}else{
   			return false;
   		}
   	}
  	
  	
    //查看核销明细
    function openUpdate(obj){
    	var vo = obj.split("|");
		var parm = "veri_check_id="+vo[0]+
			"&check_id="+vo[1]+
			"&direct="+vo[2]+
			"&check_type="+liger.get("check_type").getValue().split(" ")[0]+
			"&subj_id="+liger.get("subj_code").getValue().split(".")[1]+
			"&check_type_id="+liger.get("check_type").getValue().split(" ")[1]; 
    	$.ligerDialog.open({ 
    		url : 'accVerificationDetail.do?isCheck=false&' + parm,data:{}, 
    		height: 500, width: 900, title:'核销明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
    		] 
    	});
    }
    
  	//核销
    function ver(){
  		
    	var dataJ = gridManager1.getCheckedRows();
    	var dataD = gridManager2.getCheckedRows();
    	
        if (dataJ.length <= 0){
        	$.ligerDialog.error('请选择要核销的借方！');
        	return;
        }
		if(dataD.length <= 0){
        	$.ligerDialog.error('请选择要核销的贷方！');
        	return;
        }
		if(dataJ.length > 0 && dataD.length > 0){
			
        	if($("#balance1").text() != "0.00"){
            	$.ligerDialog.error('借方和贷方的核销金额不相等，请重新选择！');
            	return;
            }else{
            	var formPara={
          			subj_id : liger.get("sup_code").getValue().split(".")[0],
          			subj_no : liger.get("sup_code").getValue().split(".")[1],
          			check_dataJ : JSON.stringify(dataJ),
          			check_dataD : JSON.stringify(dataD)
			    };
            		
            	$.ligerDialog.confirm('确定要核销吗?', function (yes){
        			if(yes){
        				ajaxJsonObjectByUrl("addAccMpayVeri.do?isCheck=true",formPara,
				    		function (responseData){
				    		    if(responseData.state=="true"){
				    		    	veriALL();
				    		    	
				    		    }
				    		}
				    	);
        			}
        		});
            }
        }
    }
    
    //勾选取消核销
    function cancle(){
    	var detailJRows = new StringBuffer(); 
    	detailJRows.append("[");
    	$.each(dataJId,function(index,value) {
    		//alert("Rindex:"+index+"  "+"  value:"+value);
			var dataJs = gridManager1.getRow(value); 
			//alert(JSON.stringify(dataJs));
			detailJRows.append('{"veri_check_id":').append(dataJs.veri_check_id).append(',');
			detailJRows.append('"group_id":').append(dataJs.group_id).append(',');
			detailJRows.append('"hos_id":').append(dataJs.hos_id).append(',');
			detailJRows.append('"copy_code":"').append(dataJs.copy_code).append('",');
			detailJRows.append('"acc_year":"').append(dataJs.acc_year).append('",');
			detailJRows.append('"check_id":').append(dataJs.check_id).append(',');
			detailJRows.append('"ybal_amt":').append(dataJs.ybal_amt).append('}');
        });
    	detailJRows.append("]");
    	//alert(detailJRows);
    	
    	var detailDRows = new StringBuffer(); 
    	detailDRows.append("[");
    	$.each(dataDId,function(index,value) {
    		//alert("Dindex:"+index+"  "+"  value:"+value);
			var dataDs = gridManager2.getRow(value);
			//alert(JSON.stringify(dataDs));
			detailDRows.append('{"veri_check_id":').append(dataDs.veri_check_id).append(',');
			detailDRows.append('"group_id":').append(dataDs.group_id).append(',');
			detailDRows.append('"hos_id":').append(dataDs.hos_id).append(',');
			detailDRows.append('"copy_code":"').append(dataDs.copy_code).append('",');
			detailDRows.append('"acc_year":"').append(dataDs.acc_year).append('",');
			detailDRows.append('"check_id":').append(dataDs.check_id).append(',');
			detailDRows.append('"ybal_amt":').append(dataDs.ybal_amt).append('}');
        });
    	detailDRows.append("]");
    	//alert(detailDRows);
    	if(detailJRows=='[]'){
    		$.ligerDialog.error('请取消借方！');
        	return;
    	}
    	if(detailDRows=='[]'){
    		$.ligerDialog.error('请取消贷方！');
        	return;
    	}
    	
        if(detailJRows!='[]' && detailDRows!='[]'){
        	if(sumJ != sumD){
        		$.ligerDialog.error('借方和贷方的核销金额不相等，请重新选择！');
        		return;
        	}else{
        		if(sumJ==sumD ){
        			$.ligerDialog.confirm('确定要取消吗?', 
            		    function (yes){
            		    	if(yes){
            		    		var formPara={
            		    			subj_id : liger.get("subj_code").getValue().split(".")[1],
            		    			subj_name : liger.get("subj_code").getText(),
                      			  	check_dataJ : detailJRows,
                      			  	check_dataD : detailDRows
            			    	};
            		    		ajaxJsonObjectByUrl("deleteCancleAccVericaIsCheck.do?isCheck=true",formPara,
            		    		    function (responseData){
            		    		    	if(responseData.state=="true"){
            		    		    		veriALL();
            		    		    		dataDId=[];
            		    		    		dataJId=[];
            		    		    		dataJAll = "";
            		    		    		dataDAll = "";
            		    		    	}
            		    		    }
            		    		);
            		    	}						
            			});
        			}
        		}
        	}
    }
    
    //批量取消核销
    function batchCancle(){
    	//传递参数
    	var subj_id = liger.get("subj_code").getValue();
    	if(subj_id !='' && subj_id != null){
    		var parm = "subj_id="+liger.get("subj_code").getValue().split(".")[1]+
			   "&subj_name="+liger.get("subj_code").getText();
    	}else{
    		$.ligerDialog.warn("往来科目不能为空！");  
  			return false;
    	}
    	
    	$.ligerDialog.open({
    		url: 'accVerificationBatchCancle.do?isCheck=false&'+ parm, height: 300,width: 500, 
    		title:'批量取消核销',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{text: '确定', onclick: deleteAccVerBatchCancle,cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
    		] });
  		return;
    }
    
    //批量取消确定按钮
    function deleteAccVerBatchCancle(item,dialog){
    	dialog.frame.deleteAccVerBatchCancle();
    }
    
  	//核销类型改变
    function show(){
   		var value = $('input[name="auto"]:checked').val();
		if(value == 0){
			$("#show_table").hide();
			//没有实现    隐藏后勾选取消     occur_date_check due_date_check summary_check  设置成功，但是显示不成功
			$("input[name='in_no']:checkbox").attr("checked",false);
			$("input[name='in_date']:checkbox").attr("checked",false);
			$("input[name='occur_date_check']:checkbox").attr("checked",false);
			$("input[name='due_date_check']:checkbox").attr("checked",false);
			$("input[name='summary_check']:checkbox").attr("checked",false);
		}else{	
			$("#show_table").show();
		}
	}
  
    //确定查询
    function veriALL(){

    	if(liger.get("sup_code").getValue() =='' && liger.get("sup_code").getValue() == null){
    		
    		$.ligerDialog.error('供应商为必填项！');
    		
        	return;
    		
		}
    	
    	if($('input[name="auto"]:checked').val()=='1'){
    		if($("#wbal_amt_check").attr("checked") == true){
		    	wbal_amt_check = 1;
		    }else{
		    	wbal_amt_check = 0;
		    }
			//票据号
		    if($("#bill_no").attr("checked") == true){
		    	bill_no = 1;
		    }else{
		    	bill_no = 0;
		    }
		    
		    if($("#in_date").attr("checked") == true){
		    	in_date = 1;
		    }else{
		    	in_date = 0;
		    }
		    
		    if($("#occur_date_check").attr("checked") == true){
		    	occur_date_check = 1;
		    }else{
		    	occur_date_check = 0;
		    }
		    
    	}
    	
    	if($("#is_check").attr("checked") == true){
    		is_check = 1;
    	}else{
    		is_check = 0;
    	}
    	
    	if(liger.get("year_MonthCB").getValue() ==''){
    		acc_monthLB = '0'; 
    	}else{
    		acc_monthLB = liger.get("year_MonthCB").getValue().split(".")[0];
    	}
    	
    	if(liger.get("year_MonthDB").getValue() ==''){
    		acc_monthRB = '0' ; 
    	}else{
    		 acc_monthRB = liger.get("year_MonthDB").getValue().split(".")[0];
    	}
    	
    	var formPara={
        	sup_id : liger.get("sup_code").getValue().split(".")[0],
        	sup_no : liger.get("sup_code").getValue().split(".")[1],

        	is_auto :  $('input[name="auto"]:checked').val(),
        	wbal_amt_check : wbal_amt_check,
        	bill_no : bill_no,
        	in_no : in_no,
        	in_date : in_date,
        	
        	acc_monthLB : acc_monthLB,
        	acc_monthLE : liger.get("year_MonthCE").getValue().split(".")[0] ,
        	summaryL : $("#summaryC").val(),
        	priceL1 : $("#accrualCB").val(),
        	priceL2 : $("#accrualCE").val(),
        	con_noL : $("#con_noC").val(),
        	business_noL : $("#business_noC").val(),
        	
        	
        	acc_monthRB : acc_monthRB,
        	acc_monthRE : liger.get("year_MonthDE").getValue().split(".")[0],
        	summaryR : $("#summaryD").val(),
        	priceR1 : $("#accrualDB").val(),
        	priceR2 : $("#accrualCE").val(),
        	con_noR : $("#accrualDE").val(),
        	business_noR : $("#business_noD").val()
        	
		};
    	ajaxJsonObjectByUrl("queryAccMpayVeriAll.do?isCheck=false",formPara,
	    	function (responseData){
    			//grid1.remove(responseData.left);
	    		grid1.addRows(responseData.left.Rows);
	    		//grid2.remove(responseData.right);
	    		grid2.addRows(responseData.right.Rows);
	    	}
	    );
    	
  	}
    
    function loadForm(){
    	$("form").ligerForm();
    } 
    
    //字典加载 
   	function loadDict(){
   		//字典下拉框 
        $("#accrualCB").ligerTextBox({width:80});
        $("#accrualCE").ligerTextBox({width:80});
        $("#accrualDB").ligerTextBox({width:80});
        $("#accrualDE").ligerTextBox({width:80});  
        $("#con_noD").ligerTextBox({width:195});
        $("#con_noC").ligerTextBox({width:195});
        $("#summaryD").ligerTextBox({width:190});
        $("#summaryC").ligerTextBox({width:190});
        $("#business_noD").ligerTextBox({width:190});
        $("#business_noC").ligerTextBox({width:190});
        
        
        var param = {
        	SUBJ_NATURE_CODE1:'04',
        	SUBJ_NATURE_CODE2:'05',
        	is_last:1
        };   
        
        $("#sup_code").ligerComboBox({
        	parms : param,
	      	url: '../../sys/querySupDictDict.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 190,
	      	autocomplete: true,
	      	width: 190
		 });
        $("#year_MonthDB").ligerComboBox({url: '../queryMonth.do?isCheck=false',
 			valueField: 'id',textField: 'text',selectBoxWidth: 80,autocomplete: true,width: 80,
 			initValue : 0,	
    		onSuccess:function(data){
	    		for(var i in data){
	    			if(data[i].id.split(".")[0] == 0){
	    				liger.get("year_MonthDB").setValue(data[i].id.split(".")[0]);
	    				liger.get("year_MonthDB").setText(data[i].text);
	    			}
	    		}
    		} 
	   });
       
        $("#year_MonthDE").ligerComboBox({url: '../queryMonth.do?isCheck=false',
        		valueField: 'id',textField: 'text',selectBoxWidth: 80,autocomplete: true,width: 80
		});  
        liger.get("year_MonthDE").setValue(acc_month.substr(4,6));
		liger.get("year_MonthDE").setText(acc_month.substr(4,6));
		
	 	$("#year_MonthCB").ligerComboBox({url: '../queryMonth.do?isCheck=false',
	 			valueField: 'id',textField: 'text',selectBoxWidth: 80,autocomplete: true,width: 80,
	 			initValue : 0,	
        	onSuccess:function(data){
        		for(var i in data){
        			if(data[i].id.split(".")[0] == 0){
        				liger.get("year_MonthCB").setValue(data[i].id.split(".")[0]);
        				liger.get("year_MonthCB").setText(data[i].text);
        			}
        		}
        	} 
		});
	 	
		
 	 	$("#year_MonthCE").ligerComboBox({url: '../queryMonth.do?isCheck=false',
 	 			valueField: 'id',textField: 'text',selectBoxWidth: 80, autocomplete: true,width: 80
		 }); 
 	 	liger.get("year_MonthCE").setValue(acc_month.substr(4,6));
		liger.get("year_MonthCE").setText(acc_month.substr(4,6));
		
     	$("#veriState").ligerComboBox({
            width : 100,
            data: [
                { text: '全部', id: '0' },
                { text: '未核销', id: '1' },
                { text: '已核销', id: '2' }
            ],
            value: '1',cancelable: false,
            onSelected: function (selectValue){ 
	      		var value = this.getSelected();
	      		if(value){
	      			veriStateChange(value.id);
	      		}
	      	}
        });
     	
	}   
    
    //状态改变  按钮的状态改变
    function veriStateChange(value){
    	if(value=='2'){
  			$("#veri").ligerButton({width:70, disabled:true});
  			$("#veri").attr("disabled","disabled");
  			$("#cancleVeri").ligerButton({width:70, disabled:false});
  			$("#cancleVeri").attr("disabled","");
  		}else if(value=='1'){
  			$("#veri").ligerButton({ width:70, disabled:false});
  			$("#veri").attr("disabled","");
  			$("#cancleVeri").ligerButton({width:70, disabled:true});
  			$("#cancleVeri").attr("disabled","disabled");
  		}else{
  			$("#veri").ligerButton({width:70, disabled:true});
  			$("#veri").attr("disabled","disabled");
  			$("#cancleVeri").ligerButton({ width:70, disabled:true});
  			$("#cancleVeri").attr("disabled","disabled");
  		}
  		
		check_id = 0;
		
		sumJ = 0.00;
	    sumD = 0.00;
		$("#ready_J_wbalAmt").text('0.00');
		$("#ready_D_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div id="mainHead" style="height:0px;">
	<div class="info">
		<form name="form1" method="post"  id="form1" >
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		    	<tr>
		    		<td align="right" class="l-table-edit-td"  style="width:8%;" ><font color="red" size="2">*</font>供应商：</td>
		        	<td align="left" class="l-table-edit-td" colspan="2" style="width:17%;">
		        		<div name="sup_code" type="text"  id="sup_code" />
		        	</td>
		        	
		        	<td align="right" class="l-table-edit-td"  style="width:5%;" >核销状态：</td>
		            <td align="left" class="l-table-edit-td" style="width:6%;">
		            	<div id="veriState" name="veriState" />
		            </td>
		            
		            <td align="right" class="l-table-edit-td" style="width:7%;">核销方式：</td>
			        <td colspan="3" style="padding-left:5px;width:15%;">
			            <input type="radio"  id="sd" value="0" name="auto" style="padding-left:10px;"  checked onchange="show();"/> 手动核销&nbsp;&nbsp;
			            <input type="radio" id="zd"  value="1" name="auto" style="padding-left:10px;" onchange="show();"/> 自动核销
			        </td>
			        
		            <td align="left" class="l-table-edit-td" style="width:6%;" colspan="2"> 
			 			<input name="is_check" type="checkbox" id="is_check"   /> 含未记账
			 		</td>
			 		
		        	<td align="right" class="l-table-edit-td" style="width:5%;">
		        		<input class="l-button"  type="button" id="veriAll" value="确定" onclick="veriALL();"/>
		        	</td>
					<td align="right" class="l-table-edit-td" style="width:2%;"></td>
		    	</tr>		
		        <tr>
		        	
		        	<td colspan="9" style="width:58%;">
			        	<table id="show_table" cellpadding="0" cellspacing="0" class="l-table-edit" style="display: none">
							<tr>
								<td align="left" class="l-table-edit-td"><input name="wbal_amt_check" type="checkbox" id="wbal_amt_check" checked="true" readonly  disabled/></td>
						        <td align="left" >未核销金额</td>
						        <td style="padding-left:10px;" align="right" class="l-table-edit-td"><input name="bill_no" type="checkbox" id="bill_no" /></td>
						        <td align="left">票据号</td>
						        <td style="padding-left:10px;" align="right" class="l-table-edit-td"><input name="in_no" type="checkbox" id="in_no"/></td>
						        <td align="left">单据号</td>
						        <td style="padding-left:10px;" align="right" class="l-table-edit-td"><input name="in_date" type="checkbox" id="in_date"   /></td>
						        <td align="left">发生日期</td>
						    </tr>
						</table>
		        	</td>
		        </tr>
		    </table>
	  	</form>
	</div>
</div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="tit">
		<tr>
			<td align="right" style="padding-right:10px;">
	        	<input class="l-button" style="width:70px;margin-left: 10px" type="button" id="veri" value="核销" onclick="ver();"/>
	            <input class="l-button" style="width:70px;margin-left: 10px" type="button" id="cancleVeri" value="取消核销" onclick="cancle();"/>
	            <input class="l-button" style="width:70px;margin-left: 10px" type="button" id="cancleBatch" value="批量取消" onclick="batchCancle();"/>
	        </td>
	        
	        <td align="right" width="150"><font size="2">借方未核销金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_J_wbalAmt"> 0.00 </td>
			<td align="right" width="150"><font size="2">贷方未核销金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_D_wbalAmt"> 0.00 </td>
			<td align="right" width="120"><font size="2">差额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;color:red;size:2" id="balance1"> 0.00 </td>	
			 
	        
		</tr>
	</table>
	
<div id="layout1" width="100%">
	<div div="left" position="left" title="借方" >
	 	<table cellpadding="0" cellspacing="0" class="l-table-edit" border='0'>
	 		<tr>
		        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月：</td>
		        <td align="left" class="l-table-edit-td" ><input class="text"  name="year_MonthCB" type="text" id="year_MonthCB"   class="l-text-field" /></td>
		        <td align="left" class="l-table-edit-td" style="width:10px;">至:</td>
		        <td align="left" class="l-table-edit-td"><input class="text"  name="year_MonthCE" type="text" id="year_MonthCE"  class="l-text-field"   /></td>
		        
		        <td align="right" class="l-table-edit-td" style="padding-left:10px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
		        <td align="left" class="l-table-edit-td" colspan="2"><input name="summaryC" type="text" id="summaryC"  class="l-text-field"/></td>
		        
		    </tr>
		    <tr>
		    	<td align="right" class="l-table-edit-td" style="padding-left:10px;width:70px;" >发&nbsp;生&nbsp;额：</td>
		    	<td align="left" class="l-table-edit-td" ><input name="accrualCB" type="text" id="accrualCB"   class="l-text-field"/></td>
		    	<td align="left" class="l-table-edit-td" style="width:10px;">至:</td>
		    	<td align="left" class="l-table-edit-td" ><input name="accrualCE" type="text" id="accrualCE"  class="l-text-field"/></td>	
		    	
		        <td align="right" class="l-table-edit-td" style="padding-left:10px;">单&nbsp;据&nbsp;号：</td>
	            <td align="left" class="l-table-edit-td" colspan="2"><input name="business_noC" type="text" id="business_noC" /></td>
		    	 
		    </tr> 
		    <tr>
		    	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">合&nbsp;同&nbsp;号：</td>
	            <td align="left" class="l-table-edit-td" colspan="3"><input name="con_noC" type="text" id="con_noC"  class="l-text-field"  /></td>
		    	
	            <td align="right" class="l-table-edit-td"><input class="l-button" style="width: 70px" type="button" value="查询(Q)" onclick="queryC();"/></td>
		    </tr>
	 	</table>
	 	<div id="maingrid1"></div>
	 </div>
	 
	 <div  position="right" title="贷方">
	 	<table cellpadding="0" cellspacing="0" class="l-table-edit" border='0'>
	 		<tr>
		        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月：</td>
		        <td align="left" class="l-table-edit-td" ><div class="text"  name="year_MonthDB" type="text" id="year_MonthDB"   class="l-text-field" /></td>
		        <td align="left" class="l-table-edit-td" style="width:10px;">至:</td>
		        <td align="left" class="l-table-edit-td"><div class="text"  name="year_MonthDE" type="text" id="year_MonthDE"  class="l-text-field"   /></td>
		        
		        <td align="right" class="l-table-edit-td" style="padding-left:10px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
		        <td align="left" class="l-table-edit-td" colspan="2"><input name="summaryD" type="text" id="summaryD"  class="l-text-field"/></td>
		        
	 		</tr>
	        <tr> 
	        	<td align="right" class="l-table-edit-td" style="padding-left:10px;width:70px;" >发&nbsp;生&nbsp;额：</td>
		    	<td align="left" class="l-table-edit-td" ><input name="accrualDB" type="text" id="accrualDB"   class="l-text-field"/></td>
		    	<td align="left" class="l-table-edit-td" style="width:10px;">至:</td>
		    	<td align="left" class="l-table-edit-td" ><input name="accrualDE" type="text" id="accrualDE"  class="l-text-field"/></td>	
		    	
		        <td align="right" class="l-table-edit-td" style="padding-left:10px;">单&nbsp;据&nbsp;号：</td>
	            <td align="left" class="l-table-edit-td" colspan="2"><input name="business_noD" type="text" id="business_noD" /></td>

	 		</tr>
	 		<tr>
	 			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">合&nbsp;同&nbsp;号：</td>
	            <td align="left" class="l-table-edit-td" colspan="3"><input name="con_noD" type="text" id="con_noD"  class="l-text-field"  /></td>
	 			
	            <td align="right" class="l-table-edit-td"><input class="l-button" style="width:70px" type="button" value="查询(Q)" onclick="queryD();"/></td>
	 		</tr>
	 	</table>
	 	<div id="maingrid2"></div>
	 </div> 
</div>
</body>
</html>
