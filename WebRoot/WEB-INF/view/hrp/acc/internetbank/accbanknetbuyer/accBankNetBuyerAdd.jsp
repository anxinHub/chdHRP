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
     
	var bank_data;

	var paramVo = '${paramVo}';
	
	var strArrVo = paramVo.split("@");
	
	var paraSpl = paramVo.split(",");
	
	var buyerAmt = 0.00;
	
	var busi_no="";//生成凭证使用
	
	for(var i = 0 ; i< paraSpl.length; i++){
		
		buyerAmt = buyerAmt +parseFloat(paraSpl[i].split("@")[2]);
		
		busi_no=busi_no+paraSpl[i].split("@")[0]+",";

	}
	
	busi_no=busi_no.substring(0,busi_no.length-1);

	buyerAmt = formatNumber(buyerAmt,2,1);

	$(function (){
		
		$("#issamecity").ligerComboBox({
            width : 260,
            data: isSameCityData, 
            value: '1',
            autocomplete: true,
            keySupport: true
        });
		
		$("#sysioflg").ligerComboBox({
            width : 260,
            data: sysIOFlgData, 
            value: '1',
            autocomplete: true,
            keySupport: true
        });

        loadDict();//加载下拉框
        
        loadForm();

	});  

	function  save(){
		
		var reccityname = liger.get("reccityname").getValue();

		if(!reccityname){
			
			$.ligerDialog.error("请选择收款方所在城市名称！");
			
			return false;
		}

		var formPara={
	       		
				paramVo:$("#paramVo").val(),
				
				payaccno:$("#payaccno").val(),
				
				payaccnamecn:$("#payaccnamecn").val(),
				
				recaccnamecn:$("#recaccnamecn").val().split(" ")[1],
				
				new_sup_id:liger.get("recaccnamecn").getValue(),
				
				recaccno:$("#recaccno").val(),
				
				recbankname:$("#recbankname").val(),
				
				reccityname:$("#reccityname").val(),
				
				issamecity:liger.get("issamecity").getValue(),
				
				sysioflg:liger.get("sysioflg").getValue(),
				
				paytype:liger.get("paytype").getValue(),
				
				currtype:liger.get("currtype").getValue(),
				
				summary:$("#summary").val(),
				
				postscript:$("#postscript").val(),
				
				settlemode:liger.get("settlemode").getValue()
		};
		
		$("#pay").ligerButton({disabled: true});
		
		ajaxJsonObjectByUrl("queryAccBankNetBuyerNum.do?isCheck=false",formPara,function(responseData){
			
			if (responseData.result_msg != "") {
				
				$.ligerDialog.error(responseData.result_msg+'已经支付!');
				
				return false;
				
			}else{
				
				ajaxJsonObjectByUrl("collectAccBankNetBuyer.do",formPara,function(responseData){
    				
    				parent.query();
    				
    			});
    			
			}
			
		});
     
	}

	function loadDict(){
		
		$("#pay").ligerButton({click: saveAccBankNetBuyer, width: 90/* ,disabled: true */});

        $("#close").ligerButton({click: this_close, width: 90 });

		ajaxJsonObjectByUrl("../queryAccBankForInternet.do?isCheck=false", {}, function(responseData) {
        	acc_bank_data = responseData.Rows;
        },false);

		$("#bank_name").ligerComboBox({
	      	data: acc_bank_data,
	       	selectBoxWidth: 260,
	      	autocomplete: true,
	      	width: 260,
	      	valueField:'bank_number',
	      	textField: 'bank_name',
	      	value:acc_bank_data[0].bank_number,
	      	autocomplete:true,
			highLight: true,
			keySupport: true,
			onSelected: function (selectValue){
           		
				$.each(acc_bank_data, function(b_index, b_content){
					
					if(selectValue == acc_bank_data[b_index].bank_number){
						
						$("#payaccno").val(acc_bank_data[b_index].bank_zh);
						
						$("#bank_address").val(acc_bank_data[b_index].bank_address);
						
						$("#payaccnamecn").val(acc_bank_data[b_index].bank_name);
					}
					
				});

            }
		 });
		
		$("#payaccno").val(acc_bank_data[0].bank_zh);
		
		$("#bank_address").val(acc_bank_data[0].bank_address);
		
		$("#payaccnamecn").val(acc_bank_data[0].bank_name);
		
		$("#reccityname").ligerComboBox({
	      	url: '../queryAccBankNetICBCCode.do?isCheck=false',
	       	selectBoxWidth: 260,
	      	autocomplete: true,
	      	width: 260,
	      	valueField:'text',
	      	textField: 'text',
	      	autocomplete:true,
			highLight: true,
			keySupport: true
		 });
		
		$("#payaccno").ligerTextBox({width:260});$("#bank_address").ligerTextBox({width:260});$("#payaccnamecn").ligerTextBox({width:260});
		
		$("#paytype").ligerComboBox({
            width : 260,
            data: payTypeData, 
            value: '1',
            autocomplete: true,
            keySupport: true,
            disabled: true
        }); 
		$("#bank_settle").ligerComboBox({
            width : 260,
            data: bankSettleData,
            value: '1',
            autocomplete: true,
            keySupport: true
        }); 
		$("#currtype").ligerComboBox({
            width : 260,
            data: curCodeData, 
            value: '001',
            autocomplete: true,
            keySupport: true,
            disabled: true
            
        });
		
		
		$("#settlemode").ligerComboBox({
            width : 260,
            data: settleModeData,
            autocomplete: true,
            keySupport: true,
            value: '0',
            disabled: true
        });
		/* 
		if(paramVo.indexOf(",") != -1){

			liger.get("settlemode").setValue('2');liger.get("settlemode").setText('并笔入账');
			
		}else{
			
			liger.get("settlemode").setValue('0');liger.get("settlemode").setText('逐笔记账');
		} */

		$("#recaccnamecn").ligerComboBox({
	      	url: '../../../mat/queryHosSupDict.do?isCheck=false',
	       	selectBoxWidth: 260,
	      	autocomplete: true,
	      	width: 260,
	      	valueField:'id',
	      	textField: 'text',
	      	autocomplete:true,
			highLight: true,
			keySupport: true,
			onSelected: function (selectValue){loadRecBankName(selectValue.split(",")[0]);}
		 });

		liger.get("recaccnamecn").setValue(strArrVo[1]+','+strArrVo[6]);liger.get("recaccnamecn").setText(sup_bank_data[0].sup_code+' '+sup_bank_data[0].sup_name);

		$("#recaccno").ligerTextBox({width:260});$("#recaccnamecn").ligerTextBox({width:260});
		
		$("#recbankname").ligerTextBox({width:260});$("#reccityname").ligerTextBox({width:260});
		
		$("#summary").ligerTextBox({width:660});$("#postscript").ligerTextBox({width:660});
		
		$("#buyeramt").html(buyerAmt);
		
		var totalAmt = formatNumber('${totalAmt}',2,1);
		
		$("#totalAmt").html(totalAmt);
  
	} 
	
	function loadRecBankName(str){

		ajaxJsonObjectByUrl("../querySupBankForInternet.do?isCheck=false", {sup_id:str}, function(responseData) {
        	sup_bank_data = responseData.Rows;
        	
        	if(sup_bank_data==null || sup_bank_data==''){$.ligerDialog.error("该供应商未维护账户信息！！！");}
        	
        },false);
		
		if(sup_bank_data !=null && sup_bank_data!=""){
			
			$("#recbankname").ligerComboBox({
		      	data: sup_bank_data,
		       	selectBoxWidth: 260,
		      	autocomplete: true,
		      	width: 260,
		      	valueField:'bank_no',
		      	textField: 'bank_name',
		      	autocomplete:true,
				highLight: true,
				keySupport: true,
				onSelected: function (selectValue){
					
					$.each(sup_bank_data, function(b_index, b_content){
						
						if(selectValue == sup_bank_data[b_index].bank_no){
							
							$("#recaccno").val(sup_bank_data[b_index].bank_no);
							
							liger.get("reccityname").setValue(sup_bank_data[b_index].bank_area_name);
			
							liger.get("reccityname").setText(sup_bank_data[b_index].bank_area_name);
							
							$("#recbankname").val(sup_bank_data[b_index].bank_name);
						}
						
					});
	            }
			 });
			
			$.each(sup_bank_data, function(b_index, b_content){
				
				if(1 == sup_bank_data[b_index].is_default){
					
					liger.get("recbankname").setValue(sup_bank_data[b_index].bank_no);
					
					liger.get("recbankname").setText(sup_bank_data[b_index].bank_name);
					
					$("#recaccno").val(sup_bank_data[b_index].bank_no);
					
					liger.get("reccityname").setValue(sup_bank_data[b_index].bank_area_name);
					
					liger.get("reccityname").setText(sup_bank_data[b_index].bank_area_name);
					
					$("#recbankname").val(sup_bank_data[b_index].bank_name);
					
					if(sup_bank_data[b_index].bank_name.indexOf("工商") >= 0){
						
						liger.get("sysioflg").setValue('1');liger.get("sysioflg").setText('系统内');
						
					}else{
						
						liger.get("sysioflg").setValue('2');liger.get("sysioflg").setText('系统外');
					}
					
					if(sup_bank_data[b_index].bank_area_name == "台州"){
						
						liger.get("issamecity").setValue('1');liger.get("issamecity").setText('同城');
						
					}else{
						
						liger.get("issamecity").setValue('2');liger.get("issamecity").setText('异地');
					}
				}
				
			});

		}else{
			
			$("#recaccno").val("");
			
			liger.get("reccityname").setValue("");

			liger.get("reccityname").setText("");
			
			$("#recbankname").val("");
			
		}
	}
	 function loadForm() {

	        $.metadata.setType("attr", "validate");
	        var v = $("form").validate({
	            errorPlacement: function(lable, element) {
	                if (element.hasClass("l-textarea")) {
	                    element.ligerTip({ content: lable.html(), target: element[0] });
	                } else if (element.hasClass("l-text-field")) {
	                    element.parent().ligerTip({ content: lable.html(), target: element[0] });
	                } else {
	                    lable.appendTo(element.parents("td:first").next("td"));
	                }
	            },
	            success: function(lable) {
	                lable.ligerHideTip();
	                lable.remove();
	            },
	            submitHandler: function() {
	                $("form .l-text,.l-textarea").ligerHideTip();
	            }
	        });
	        // $("form").ligerForm();
	}
	
	function saveAccBankNetBuyer(){
    	
		save();
	}
	
    function this_close() {
    	
        frameElement.dialog.close();
        
    }

    function loadHotkeys() {

        hotkeys('B', saveAccBankNetBuyer);
        
        hotkeys('C', this_close);
    }

    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" name="paramVo" id="paramVo" value="${paramVo}"/>

   <form name="form" method="post"  id="form" style="padding-top:20px;">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="padding-top:20px;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">付款单位：</font></td>
                <td align="left" class="l-table-edit-td"><input name="bank_name" type="text" id="bank_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">付款账号：</font></td>
                <td align="left" class="l-table-edit-td"><input name="payaccno" type="text" id="payaccno" ltype="text" validate="{required:true,maxlength:60}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
           		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">付款单位开户行：</font></td>
                <td align="left" class="l-table-edit-td"><input name="bank_address" type="text" id="bank_address" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">付款账户名：</font></td>
                <td align="left" class="l-table-edit-td"><input name="payaccnamecn" type="text" id="payaccnamecn" ltype="text" validate="{required:true,maxlength:200}"/>              
                </td>
                <td align="left"></td>
            </tr>
  			<tr>
            	<td colspan="6"><hr/></td>
            </tr>
            <!-- <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="blue">供应商：</font></td>
				<td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"></td>
			</tr> -->
            <tr>          
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">收款账户名：</font></td>
                <td align="left" class="l-table-edit-td"><input name="recaccnamecn" type="text" id="recaccnamecn" ltype="text" validate="{required:true,maxlength:60}"/>              
                </td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">收款账号：</font></td>
                <td align="left" class="l-table-edit-td"><input name="recaccno" type="text" id="recaccno" ltype="text" validate="{required:true,maxlength:60}" /></td>
                <td align="left"></td>
               </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">交易对方银行名称：</font></td>
                <td align="left" class="l-table-edit-td"><input name="recbankname" type="text" id="recbankname" ltype="text" validate="{required:true,maxlength:60}"/>              
                </td>
                <td align="left"></td>

                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">收款方所在城市名称：</font></td>
                <td align="left" class="l-table-edit-td"><input name="reccityname" type="text" id="reccityname" ltype="text" validate="{required:true,maxlength:60}"/>              
                </td>
                <td align="left"></td>
            </tr> 
   
  			<tr>
            	<td colspan="6"><hr/></td>
            </tr>
             <tr>
             	
             	
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">同行异行：</font></td>
                <td align="left" class="l-table-edit-td"><input name="sysioflg" type="text" id="sysioflg" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="blue">同城异地：</font></td>
                <td align="left" class="l-table-edit-td"><input name="issamecity" type="text" id="issamecity" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
             	

            </tr> 
            <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入账方式：</td>
                <td align="left" class="l-table-edit-td"><input name="settlemode" type="text" id="settlemode" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">汇款速度：</td>
                <td align="left" class="l-table-edit-td"><input name="paytype" type="text" id="paytype" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种：</td>
                <td align="left" class="l-table-edit-td"><input name="currtype" type="text" id="currtype" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"></td>
                <td align="left"></td>
            </tr> 
             
			<tr>
            	<td colspan="6"><hr/></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input name="summary" type="text" id="summary" ltype="text" validate="{maxlength:200}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input name="postscript" type="text" id="postscript" ltype="text" validate="{maxlength:200}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<font color="red">*</font>单 笔 限 额：5000000元 当前合计额度：<span id="buyeramt"></span>元<br/>
                	<font color="red">*</font>日累计限额：50000000元 已使用：<span id="totalAmt"></span>元<br/>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="right" class="l-table-edit-td" colspan="4">
                	<!-- <input type="submit" value="确定支付" id="Button1" class="l-button l-button-reset" /> 
					<input type="reset" value="关闭" class="l-button l-button-reset"/> -->

                        <button id="pay" accessKey="B"><b>确定支付（<u>B</u>）</b></button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>

					
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
