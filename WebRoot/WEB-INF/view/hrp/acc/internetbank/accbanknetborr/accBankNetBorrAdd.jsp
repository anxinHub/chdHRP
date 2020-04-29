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

	var paramVo = '${paramVo}';
	
	var strArrVo = paramVo.split("@");
	
	var paraSpl = paramVo.split(",");
	
	var amt = 0.00;
	
	for(var i = 0 ; i< paraSpl.length; i++){
		
		amt = amt +parseFloat(paraSpl[i].split("@")[1]);
	}
	
	amt = formatNumber(amt,2,1);
     
	$(function (){

        loadDict();//加载下拉框
        
        loadForm();

	});  

	function  save(){
		
		var totalAmt = '${totalAmt}';
		
		if(amt >= 5000000){
			
			$.ligerDialog.warn('已超过单条支付限额');
			
			return false;	
		}
		
		if(totalAmt + amt > 50000000){
			
			$.ligerDialog.warn('已超过日累计支付限额,请明日再支付');
			
			return false;	
		}

		var formPara={

				group_id:$("#group_id").val(),
				
				hos_id:$("#hos_id").val(),
				
				copy_code:$("#copy_code").val(),
				
				fseqno:$("#fseqno").val(),
				
				paramVo:$("#paramVo").val(),
				
				acc_year:year_Month.substring(0,4),
				
				acc_month:year_Month.substring(4,6),
				
				payaccno:$("#payaccno").val(),
				
				payaccnamecn:$("#payaccnamecn").val(),

				paytype:liger.get("paytype").getValue(),
				
				currtype:liger.get("currtype").getValue(),
				
				summary:$("#summary").val(),
				
				postscript:$("#postscript").val(),
				
				settlemode:liger.get("settlemode").getValue()
		};
		
		$("#pay").ligerButton({disabled: true});
		
		ajaxJsonObjectByUrl("collectAccBankNetBorr.do",formPara,function(responseData){
			parent.query();
		});
     
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
		
		$("#payaccno").val(acc_bank_data[0].bank_zh);$("#bank_address").val(acc_bank_data[0].bank_address);$("#payaccnamecn").val(acc_bank_data[0].bank_name);

		$("#payaccno").ligerTextBox({width:260});$("#bank_address").ligerTextBox({width:260});$("#payaccnamecn").ligerTextBox({width:260});
		
		$("#paytype").ligerComboBox({
            width : 260,
            data: payTypeData, 
            value: '1',
            autocomplete: true,
            keySupport: true,
            disabled: true
        }); 

		$("#currtype").ligerComboBox({
            width : 260,
            data: curCodeData, 
            value: '001',
            disabled: true
        });
		
		
		$("#settlemode").ligerComboBox({
            width : 260,
            data: settleModeData,
            value: '0',
            disabled: true
        });
		
		/* if(paramVo.indexOf(",") != -1){

			liger.get("settlemode").setValue('2');liger.get("settlemode").setText('并笔入账');
			
		}else{
			
			liger.get("settlemode").setValue('0');liger.get("settlemode").setText('逐笔记账');
		} */
		
		$("#summary").ligerTextBox({width:660});$("#postscript").ligerTextBox({width:660});
		
		$("#amt").html(amt);
		
		var totalAmt = formatNumber('${totalAmt}',2,1);
		
		$("#totalAmt").html(totalAmt);
	} 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input type="hidden" name="group_id" id="group_id" value="${group_id}"/>
   <input type="hidden" name="hos_id" id="hos_id" value="${hos_id}"/>
   <input type="hidden" name="copy_code" id="copy_code" value="${copy_code}"/>
   <input type="hidden" name="paramVo" id="paramVo" value="${paramVo}"/>

   <form name="form" method="post"  id="form" style="padding-top:20px;">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="padding-top:20px;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单位：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_name" type="text" id="bank_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款账号：</td>
                <td align="left" class="l-table-edit-td"><input name="payaccno" type="text" id="payaccno" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
           		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单位开户行：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_address" type="text" id="bank_address" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款账户名：</td>
                <td align="left" class="l-table-edit-td"><input name="payaccnamecn" type="text" id="payaccnamecn" ltype="text" validate="{required:true,maxlength:20}"/>              
                </td>
                <td align="left"></td>
            </tr>
            
  			<tr>
            	<td colspan="6"><hr/></td>
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
                <td align="left" class="l-table-edit-td" colspan="4"><input name="summary" type="text" id="summary" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input name="postscript" type="text" id="postscript" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<font color="red">*</font>对公转账：<br/>
                	<font color="red">*</font>单 笔 限 额：5000000元 当前合计额度：<span id="amt"></span>元<br/>
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
