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
    
    var userUpdateStr;
    
    var sumL = 0;
    var sumR = 0;
    var dataLId =[];//存放rowid
	var dataRId =[];//存放rowid
	var dataLAll="";//取消核销功能 取消勾选的全部已核销的数据(借方)
	var dataRAll="";//取消核销功能 取消勾选的全部已核销的数据(贷方)
	
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
    	
		loadDict();//加载查询条件
		loadForm();
    	loadHead(null);	//加载数据
    	
    	if($("#state").val()=='0'){
    		$("#veri").ligerButton({ width:70, disabled:false});
  			$("#veri").attr("disabled","");
  			$("#cancleVeri").ligerButton({width:70, disabled:true});
  			$("#cancleVeri").attr("disabled","disabled");
  			sumL = 0;
  			sumR = 0;
  			$("#ready_B_wbalAmt").text('0.00');
  			$("#ready_D_wbalAmt").text('0.00');
        	$("#balance1").text('0.00');
  		}
    	
    	$("#layout1").ligerLayout({ leftWidth: '49%',rightWidth:'50%',allowLeftCollapse:false,allowRightCollapse:false});
    	
    });
    
    
	//出纳查询
    function  queryA(){
    	sumL = 0;
    	dataLId =[];
    	
    	
		grid1.options.parms=[];
    	grid1.options.newPage=1;
    	
    	if($("#acc_time1").val() == "" || $("#acc_time2").val() == ""){
    		$.ligerDialog.warn('请选择输入出纳日期！');
        	return;
    	}
    	
    	if(liger.get("subj_code").getValue() == "" || liger.get("subj_code").getValue() == null){
        	$.ligerDialog.warn('请选择出纳科目');
        	return;
        }
    	
    	grid1.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	grid1.options.parms.push({name:'acc_time1',value:$("#acc_time1").val()}); 
    	grid1.options.parms.push({name:'acc_time2',value:$("#acc_time2").val()}); 
    	grid1.options.parms.push({name:'subj_dire',value:liger.get("subj_dire").getValue()}); 
    	grid1.options.parms.push({name:'price1',value:$("#price1").val()}); 
    	grid1.options.parms.push({name:'price2',value:$("#price2").val()}); 
    	grid1.options.parms.push({name:'summary',value:$("#summary").val()}); 
    	grid1.options.parms.push({name:'state',value:$("#state").val()}); 
    	
    	//加载查询条件
    	grid1.loadData(grid1.where); 
    	$("#maingrid1").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
    	
    	if($("#state").val()!='1'){
    		$("#ready_A_wbalAmt").text('0.00');
    	}
    	
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
     }
	
	
  	//凭证明细查询
	function queryB(){
		sumR = 0;
		dataRId =[];
    	
    	
		grid2.options.parms=[];
    	grid2.options.newPage=1;
    	
    	grid2.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});
    	grid2.options.parms.push({name:'acc_time1',value:$("#acc_time3").val()}); 
    	grid2.options.parms.push({name:'acc_time2',value:$("#acc_time4").val()}); 
    	grid2.options.parms.push({name:'subj_dire',value:liger.get("vouch_subj_dire").getValue()}); 
    	grid2.options.parms.push({name:'price1',value:$("#vouch_price1").val()}); 
    	grid2.options.parms.push({name:'price2',value:$("#vouch_price2").val()}); 
    	grid2.options.parms.push({name:'summary',value:$("#vouch_summary").val()}); 
    	grid2.options.parms.push({name:'state',value:$("#state").val()}); 
    	
    	grid2.loadData(grid2.where);
    	$("#maingrid2").find('.l-grid1 .l-grid-hd-row').removeClass('l-checked');
    	
    	if($("#state").val()!='1'){
    		$("#ready_B_wbalAmt").text('0.00');
    	}
    	
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
    	
	}
  	
  	
	
    function loadHead(){
    	//加载出纳账表格
		grid1 = $("#maingrid1").ligerGrid({
			columns: [ 
				{ display: '出纳日期', name: 'occur_date', align: 'left',width:80 },
				{ display: '摘要', name: 'summary', align: 'left',width:100 },
				{ display: '方向', name: 'subj_dire', align: 'left',width:40,render : 
					function(rowdata, rowindex,value) {
						if(rowdata.subj_dire == 0){
							return "借";
						}else{
							return "贷";
						}
					}
				},
				{ display: '金额', name: 'price', align: 'right',width:80, render:
					function(rowdata){
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price,2,1);	
					}
				},
				{ display: '对账状态', name: 'is_checks', align: 'left',width:60 } ,
				{ display: '凭证号', name: 'vouch_no', align: 'left',width:80, 
					render : 
					function(rowdata, rowindex, value) {
						if(rowdata.vouch_no !=null && rowdata.vouch_no !="-"){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
						}
					}
				} ,
				{ display: '对账序列', name: 'veri_check_id', align: 'left',width:120}
			],
            dataAction: 'server',dataType: 'server',usePager:true,delayLoad:true,url:'queryAccTellDatas.do?isCheck=false',
            width: '100%', height: '100%', checkbox : true,rownumbers:true, isSingleCheck : false,isScroll:true,
            selectRowButtonOnly : true,pageSizeOptions:[100, 200, 500],pageSize: 100,
            onCheckRow  : clickL, onCheckAllRow: checkAllL,isSelected : L_isChecked
		});

        gridManager1 = $("#maingrid1").ligerGetGridManager();
        
        //凭证明细表格
        grid2 = $("#maingrid2").ligerGrid({
			columns: [ 
 				{ display: '对账序列', name: 'veri_check_id', align: 'left',width:120},
 				{ display: '凭证日期', name: 'create_date', align: 'left',width:80},
				{ display: '凭证号', name: 'vouch_no', align: 'left',width:80,render : 
					function(rowdata, rowindex, value) {
						if(rowdata.vouch_no !=null && rowdata.vouch_no !="-"){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no.split('-')[0]+"-"+rowdata.vouch_no.split('-')[1]+"</div></a>"; 
						}
					}
				},
				{ display: '摘要', name: 'summary', align: 'left',width:100},
				{ display: '方向', name: 'subj_dire', align: 'left',width:40,render : 
					function(rowdata, rowindex,value) {
						if(rowdata.subj_dire == 0){
							return "借";
						}else{
							return "贷";
						}
					}
				},
				{ display: '金额', name: 'price', align: 'right',width:80, render:
					function(rowdata){
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price,2,1);	
					}
				} ,
				{ display: '对账状态', name: 'is_checks', align: 'left',width:60 } 
			],
			dataAction: 'server',dataType: 'server',usePager:true,delayLoad:true,url:'queryAccVouchCheck.do?isCheck=false',
            width: '100%', height: '100%', checkbox : true,rownumbers:true, isSingleCheck : false,isScroll:true,
            selectRowButtonOnly : true,pageSizeOptions:[100, 200, 500],pageSize: 100,
            onCheckRow  : clickR, onCheckAllRow: checkAllR,isSelected : R_isChecked
		});

         gridManager2 = $("#maingrid2").ligerGetGridManager();
         
         grid1.toggleCol("is_check", false);
         grid2.toggleCol("is_check", false);
    }
    
  	//左侧自动核销选中
   	function L_isChecked(rowdata){
   		if(rowdata.is_check == '1'){
			sumL = sumL + rowdata.price;
			
			$("#ready_A_wbalAmt").text(formatNumber(sumL ==null ? 0 : sumL,2,1));
			$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
			
   			return true;
   		}else{
   			return false;
   		}
   		
   		
   	}
  	
   	function R_isChecked(rowdata){
   		if(rowdata.is_check == '1'){
			sumR = sumR + rowdata.price;
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
    		if($("#state").val()=='1'){
    			if(dataLId.length > 0){
    				dataLId.splice($.inArray(rowid,dataLId),1);
    			}
    		}
    		sumL = sumL + data.price;
    		
    	}else{
    		if($("#state").val()=='1'){
    			dataLId.push(String(rowid));
    		}
    		sumL = sumL - data.price;
    		 
    	}	
    	$("#ready_A_wbalAmt").text(formatNumber(sumL ==null ? 0 : sumL,2,1));
		$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
	}
    
    //右侧选中
    function clickR(checked,data,rowid,rowdata){	
    	if(checked){
    		if($("#state").val()=='1'){
    			if(dataRId.length > 0){
    				dataRId.splice($.inArray(rowid,dataRId),1);
    			}
    		}
    		sumR = sumR + data.price;
    		
    	}else{
    		if($("#state").val()=='1'){
    			dataRId.push(String(rowid));
    		}
    		sumR = sumR - data.price;
    		 
    	}	
    	
    	$("#ready_B_wbalAmt").text(formatNumber(sumR ==null ? 0 : sumR,2,1));
		$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
	}
    
  	//左侧全选、全部取消按钮
    function checkAllL(checked,element){   	
    	if(checked){
    		sumL = 0;
    		var data = gridManager1.getCheckedRows();
    		dataLAll = gridManager1.getCheckedRows();
        	if (data.length > 0){
        		$(data).each(function (){
        			sumL = sumL + this.price;
        		});
    		}
    	}else{
    		sumL = 0;
    		if($("#veriState").val()=='2'){
    			//获取取消行的rowid
    			$(dataLAll).each(function (){
    				dataLId.push(this['__id']);
    			});
    		}
    	}
    	
    	$("#ready_A_wbalAmt").text(formatNumber(sumL ==null ? 0 : sumL,2,1));
    	$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1)); 
    }
  	
  	//右侧全选、全部取消按钮
    function checkAllR(checked,element){   	
    	if(checked){
    		sumR = 0;
    		var data = gridManager2.getCheckedRows();
    		dataRAll = gridManager2.getCheckedRows();
        	if (data.length > 0){
        		$(data).each(function (){
        			sumR = sumR + this.price;
        		});
    		}
    	}else{
    		sumR = 0;
    		if($("#veriState").val()=='2'){
    			//获取取消行的rowid
    			$(dataRAll).each(function (){
    				dataRId.push(this['__id']);
    			});
    		}
    	}
    	
    	$("#ready_B_wbalAmt").text(formatNumber(sumR ==null ? 0 : sumR,2,1));
		$("#balance1").text(formatNumber((sumL-sumR) ==null ? 0 : (sumL-sumR),2,1));
    }
    
    //打开凭证
    function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    
    //手工对账
    function verification(){
    	var dataL = gridManager1.getCheckedRows();
    	var dataR = gridManager2.getCheckedRows();
    	
        if (dataL.length <= 0){
        	$.ligerDialog.error('请选择出纳数据！');
        	return;
        }
        
		if(dataR.length <= 0){
        	$.ligerDialog.error('请选择凭证数据！');
        	return;
        }
		
		if($("#subj_dire").val() != $("#vouch_subj_dire").val()){
        	$.ligerDialog.error('请选择同方向的数据！');
        	return;
        }
		
		if(dataL.length > 1 && dataR.length > 1){
	 		$.ligerDialog.warn('出纳帐中数据和凭证数据必须有一方只能勾选一条！');
	        return;
	 	} 
		
		//alert(sumL+"   "+sumR);
		if(dataL.length > 0 && dataR.length > 0){
			if(sumL != sumR ){
            	$.ligerDialog.error('出纳和凭证的对账金额不相等，请重新选择！');
            	return;
            }else{
            	
            	var formPara={
              		check_dataL : JSON.stringify(dataL),
              		check_dataR : JSON.stringify(dataR)
    			};
            	
            	$.ligerDialog.confirm('确定要对账吗?', function (yes){
        			if(yes){
        				ajaxJsonObjectByUrl("saveAccTellVeri.do",formPara,function(data){
                         	if(data.state){
                         		//$.ligerDialog.success(data.msg);
                         		queryA();
                         		queryB();
                         		dataLId=[];
		    		    		dataRId=[];
		    		    		dataLAll = "";
		    		    		dataRAll = "";
		    		    		$("#ready_A_wbalAmt").text('0.00');
		    		    		$("#ready_B_wbalAmt").text('0.00');
		    		        	$("#balance1").text($("#ready_A_wbalAmt").text());
		    		    		
                         	}else{
                         		$.ligerDialog.warn(data.error);
                         	}
                         },"json");
        				
        			}
        		});
            }
		}
    }
    
    //批量对账
    function batchVerification(){
    	$.ligerDialog.open({url: 'accSaveAccTellVeriPage.do', height: 300,width: 500, title:'批量对账 ',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccUnitBankCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
	
    //取消对账
    function cancel(){
    	var detailLRows = new StringBuffer(); 
    	detailLRows.append("[");
    	$.each(dataLId,function(index,value) {
    		//alert("Rindex:"+index+"  "+"  value:"+value);
			var dataLs = gridManager1.getRow(value); 
			//alert(JSON.stringify(dataLs));
			detailLRows.append('{"veri_check_id":').append(dataLs.veri_check_id).append(',');
			detailLRows.append('"group_id":').append(dataLs.group_id).append(',');
			detailLRows.append('"hos_id":').append(dataLs.hos_id).append(',');
			detailLRows.append('"copy_code":"').append(dataLs.copy_code).append('",');
			detailLRows.append('"acc_year":"').append(dataLs.acc_year).append('",');
			detailLRows.append('"tell_id":').append(dataLs.tell_id).append(',');
			detailLRows.append('"price":').append(dataLs.price).append('}');
        });
    	detailLRows.append("]");
    	//alert(detailLRows);
    	
    	var detailRRows = new StringBuffer(); 
    	detailRRows.append("[");
    	$.each(dataRId,function(index,value) {
    		//alert("Dindex:"+index+"  "+"  value:"+value);
			var dataRs = gridManager2.getRow(value);
			//alert(JSON.stringify(dataRs));
			detailRRows.append('{"veri_check_id":').append(dataRs.veri_check_id).append(',');
			detailRRows.append('"group_id":').append(dataRs.group_id).append(',');
			detailRRows.append('"hos_id":').append(dataRs.hos_id).append(',');
			detailRRows.append('"copy_code":"').append(dataRs.copy_code).append('",');
			detailRRows.append('"acc_year":"').append(dataRs.acc_year).append('",');
			detailRRows.append('"vouch_id":').append(dataRs.vouch_id).append(',');
			detailRRows.append('"vouch_check_id":').append(dataRs.vouch_check_id).append(',');
			detailRRows.append('"price":').append(dataRs.price).append('}');
        });
    	detailRRows.append("]");
    	
    	//alert(detailRRows);
    	if(detailLRows=='[]'){
    		$.ligerDialog.error('请取消出纳数据！');
        	return;
    	}
    	if(detailRRows=='[]'){
    		$.ligerDialog.error('请取消凭证明细数据！');
        	return;
    	}
    	
    	if(detailLRows!='[]' && detailRRows!='[]'){
        	if(sumL != sumR){
        		$.ligerDialog.error('出纳和凭证数据的核销金额不相等，请重新选择！');
        		return;
        	}else{
        		if(sumL == sumR ){
        			$.ligerDialog.confirm('确定要取消吗?', 
            		    function (yes){
            		    	if(yes){
            		    		var formPara = {
            		    			check_dataL : detailLRows,
                          			check_dataR : detailRRows
            		            };
            		            $.post("cancelAccTellVeri.do",formPara,function(data){
            		            	if(data.state){
            		            		$.ligerDialog.success(data.msg);
            		            		queryA();
        		    	        		queryB();
        		    		    		dataLId=[];
        		    		    		dataRId=[];
        		    		    		dataLAll = "";
        		    		    		dataRAll = "";
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
    function batchCancel(){
    	var subj_code = liger.get("subj_code").getValue();
    	var subj_name = liger.get("subj_code").getText();
    	$.ligerDialog.open({url: 'accUnitCancelTellCheckPage.do?subj_code='+subj_code+'&subj_name='+subj_name, height: 300,width: 500, title:'批量取消对账 ',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccUnitBankCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    
    function viewAccBankVeriInfo(){
    	$.ligerDialog.open({url: 'accAccTellVeriInfoPage.do?isCheck=false', height: 450,width: 600, title:'对账信息 ',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function loadDict(){
    	var param = {
   			SUBJ_NATURE_CODE1:'02',
   			SUBJ_NATURE_CODE2:'03'
        };
    	$("#price1").ligerTextBox({});
        $("#price2").ligerTextBox({});
        $("#acc_time1").ligerTextBox({});
        $("#acc_time2").ligerTextBox({});
        $("#acc_date1").ligerTextBox({});
        $("#acc_date2").ligerTextBox({});
        $("#summary").ligerTextBox({});
        
        var defaultSelect = true;
        var count = 0;
        
        autocomplete("#subj_code", "../querySubj.do?isCheck=false", "id", "text", true, true, param, true, '', 205);
        defaultSelectValue = "select";
        
        var dateStr = getCurrentDate();
        var date = dateStr.split(";");
        var currentFirstDay = date[3];
        var currentLastDay = date[4];
        
        $("#acc_time1").val(currentFirstDay);
        $("#acc_time2").val(currentLastDay);
        
        $("#acc_time3").val(currentFirstDay);
        $("#acc_time4").val(currentLastDay);
        
        $("#state").ligerComboBox({
            width : 150,
            data: [
                   { text: '全部', id: '-1' },
                   { text: '未对账', id: '0' },
                   { text: '已对账', id: '1' }
            ],
            value: '0',cancelable: false,
            onSelected: function (selectValue){ 
	      		var value = this.getSelected();
	      		if(value){
	      			veriStateChange(value.id);
	      		}
	      	}
        });
        
        $("#acc_time1").ligerTextBox({width:100});
    	$("#acc_time2").ligerTextBox({width:100});
		$("#price1").ligerTextBox({width:100});
    	$("#price2").ligerTextBox({width:100});
    	$("#subj_dire").ligerTextBox({width:70});
    	$("#summary").ligerTextBox({width:160});
		$("#acc_time3").ligerTextBox({width:100});
    	$("#acc_time4").ligerTextBox({width:100});
		$("#vouch_price1").ligerTextBox({width:100});
    	$("#vouch_price2").ligerTextBox({width:100});
    	$("#vouch_subj_dire").ligerTextBox({width:70});
    	$("#vouch_summary").ligerTextBox({width:160});
    }  
    
  	//状态改变  按钮的状态改变
    function veriStateChange(value){
    	if(value=='1'){
  			$("#veri").ligerButton({width:70, disabled:true});
  			$("#veri").attr("disabled","disabled");
  			$("#cancleVeri").ligerButton({width:70, disabled:false});
  			$("#cancleVeri").attr("disabled","");
  		}else if(value=='0'){
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
		
		sumL = 0.00;
	    sumR = 0.00;
	    $("#ready_B_wbalAmt").text('0.00');
		$("#ready_A_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
    }
  	
  	
    function loadForm(){
   	 $("form").ligerForm();
       
    }  
    
    //确定
    function checkAll(){
    	queryA();
	    queryB();
    	sumL = 0;
		sumR = 0;
		$("#ready_A_wbalAmt").text('0.00');
		$("#ready_B_wbalAmt").text('0.00');
    	$("#balance1").text('0.00');
  		dataRId=[];
		dataLId=[];
    }
    </script>

</head>

<body style="padding: 0px; overflow: auto;">
<div id="mainHead" style="height:0px;">
<div class="info">
  <form name="form1" method="post"  id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
         <tr>
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>出纳科目：</td>
            <td align="left" class="l-table-edit-td" ><div id="subj_code" style="display:none"></div></td>
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">对账状态：</td>
        	<td align="left" class="l-table-edit-td;">
        		<div id="state" style="display:none;"></div>
            </td>
            
            <td>
            	<input class="l-button" style="width:70px;margin-left: 10px" type="button" id="checkA" value="确定" onclick="checkAll();"/>
			</td>
        </tr> 
    </table>
  </form>
  <br />
  <br />
</div>
</div>
		 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
         <tr>
            <td><input class="l-button" style="width: 80px;margin-left: 10px" type="button" id="veri" value="对账" onclick="verification();"/></td>
            <td><input class="l-button" style="width: 80px;margin-left: 10px" type="button" id="cancleVeri" value="取消对账" onclick="cancel();"/></td>
            <td><input class="l-button" style="width: 80px;margin-left: 10px" type="button" id="batchVeri" value="批量对账" onclick="batchVerification();"/></td>
            <td><input class="l-button" style="width: 80px;margin-left: 10px" type="button" id="cancleBatch" value="批量取消" onclick="batchCancel();"/></td>
        
        	<td align="right" width="150"><font size="2">出纳帐金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_A_wbalAmt"> 0.00 </td>
	        <td align="right" width="150"><font size="2">会计账金额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;" id="ready_B_wbalAmt"> 0.00 </td>
			<td align="right" width="120"><font size="2">差额：</font></td>
			<td align="left" style="padding-left:10px; width:100px;color:red;size:2" id="balance1"> 0.00 </td>
        </tr> 
    </table>
		<div id="layout1">
		
            <div position="left" title="出纳数据">
            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		         <tr>
		          	<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>出纳日期：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="acc_time1" type="text" id="acc_time1"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		            <td align="center" class="l-table-edit-td">至:</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="acc_time2" type="text" id="acc_time2"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="summary" type="text" id="summary"  class="l-text-field"/></td>
		        </tr> 
		        <tr>
					<td align="right" class="l-table-edit-td" >发&nbsp; 生&nbsp;  额：</td>
		            <td align="left" class="l-table-edit-td"><input name="price1" type="text" id="price1"   class="l-text-field" style="width: 125px;"  /></td>
		            <td align="center" class="l-table-edit-td">至:</td>
		            <td align="left" class="l-table-edit-td"><input name="price2" type="text" id="price2"  class="l-text-field" style="width: 125px;"  /></td>
		            <td align="right" class="l-table-edit-td" style="padding-left:20px;">收支方向：</td>
		            <td align="left" class="l-table-edit-td" >
		            	<select id="subj_dire" name="subj_dire">
		            		<option value="0">借</option>
		            		<option value="1">贷</option>
		            	</select>
		            </td>
		            <td><input class="l-button" style="width: 80px" type="button" value="查询(Q)" onclick="queryA();"/></td>
		       </tr>
		    </table>
            <div id="maingrid1"></div>
            
            </div>
            
            <div position="right" title="凭证明细数据">
            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		         <tr>
		          	<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>凭证日期：</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="acc_time3" type="text" id="acc_time3"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		            <td align="center" class="l-table-edit-td">至:</td>
		            <td align="left" class="l-table-edit-td"><input class="Wdate"  name="acc_time4" type="text" id="acc_time4"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
		        	<td align="right" class="l-table-edit-td" style="padding-left:10px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
		            <td align="left" class="l-table-edit-td" colspan="2"><input name="vouch_summary" type="text" id="vouch_summary"  class="l-text-field"/></td>
		        </tr>
		        <tr>
					<td align="right" class="l-table-edit-td" >发&nbsp; 生&nbsp;  额：</td>
		            <td align="left" class="l-table-edit-td"><input name="vouch_price1" type="text" id="vouch_price1"   class="l-text-field" style="width: 125px;"  /></td>
		            <td align="center" class="l-table-edit-td">至:</td>
		            <td align="left" class="l-table-edit-td"><input name="vouch_price2" type="text" id="vouch_price2"  class="l-text-field" style="width: 125px;"  /></td>
		            <td align="right" class="l-table-edit-td" style="padding-left:10px;">收支方向：</td>
		            <td align="left" class="l-table-edit-td" >
		            	<select id="vouch_subj_dire" name="vouch_subj_dire">
		            		<option value="0">借</option>
		            		<option value="1">贷</option>
		            	</select>
		            </td>
		            <td><input class="l-button" style="width: 80px" type="button" value="查询(Q)" onclick="queryB();"/></td>
		       </tr>
		    </table>
            <div id="maingrid2"></div>
            
            </div>  
            
        </div> 
</body>
</html>
