<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script src="<%=path%>/lib/hrp/acc/internetbank/icbc/common.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		loadDict();//加载下拉框
		loadForm();
	});

	function save() {

		var totalAmt = '${totalAmt}';

		var wageAmt = '${wageAmt}';

		/* if(wageAmt >= 5000000){
			
			$.ligerDialog.warn('已超过单条支付限额');
			
			return false;	
		}
		 */
		if (totalAmt + wageAmt > 50000000) {

			$.ligerDialog.warn('已超过日累计支付限额,请明日再支付');

			return false;

		}

		var formPara = {

			group_id : $("#group_id").val(),

			hos_id : $("#hos_id").val(),

			copy_code : $("#copy_code").val(),

			payFlag : $("#payFlag").val(),

			paramVo : $("#paramVo").val(),

			wage_code : $("#wage_code").val(),

			item_code : $("#item_code").val(),

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val(),

			payaccno : $("#payaccno").val(),

			payaccnamecn : $("#payaccnamecn").val(),
			
			bank_address : $("#bank_address").val(),

			summary : $("#summary").val(),

			postscript : $("#postscript").val(),
		};

		$("#pay").ligerButton({
			disabled : true
		});

		ajaxJsonObjectByUrl("accNetPayWageToCBC.do?isCheck=false", formPara, function(
				responseData) {

		});

	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		// $("form").ligerForm();
	}

	function saveAccBankNetWage() {

		save();

	}

	function loadDict() {

		$("#pay").ligerButton({
			click : saveAccBankNetWage,
			width : 90
		/* ,disabled: true */});

		$("#close").ligerButton({
			click : this_close,
			width : 90
		});
		
		$("#payaccno").ligerTextBox({
			width : 260
		});
		$("#bank_address").ligerTextBox({
			width : 260
		});
		$("#payaccnamecn").ligerTextBox({
			width : 260
		});
		$("#summary").ligerTextBox({
			width : 660
		});
		$("#postscript").ligerTextBox({
			width : 660
		});
		
		ajaxJsonObjectByUrl("queryAccBankForInternet.do?isCheck=false", {}, function(responseData) {
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
		
		
		var wageAmt = formatNumber('${wageAmt}', 2, 1);

		$("#wageAmt").html(wageAmt);

		var totalAmt = formatNumber('${totalAmt}', 2, 1);

		$("#totalAmt").html(totalAmt);

		var recaccnoNullWarring = '${recaccnoNullWarring}';

		$("#recaccnoNullWarring").html(recaccnoNullWarring);

	}

	function this_close() {

		frameElement.dialog.close();

	}

	function loadHotkeys() {

		hotkeys('B', saveAccBankNetWage);

		hotkeys('C', this_close);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="wage_code" id="wage_code"value="${wage_code}" />
	<input type="hidden" name="item_code" id="item_code"value="${item_code}" />
	<input type="hidden" name="acc_year" id="acc_year" value="${acc_year}" />
	<input type="hidden" name="acc_month" id="acc_month"value="${acc_month}" />
	<input type="hidden" name="group_id" id="group_id" value="${group_id}" />
	<input type="hidden" name="hos_id" id="hos_id" value="${hos_id}" />
	<input type="hidden" name="copy_code" id="copy_code"value="${copy_code}" />
	<input type="hidden" name="payFlag" id="payFlag" value="${payFlag}" />
	<form name="form" method="post" id="form" style="padding-top: 20px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			style="padding-top: 20px;">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">付款单位：</td>
				<td align="left" class="l-table-edit-td"><input
					name="bank_name" type="text" id="bank_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">付款账号：</td>
				<td align="left" class="l-table-edit-td"><input name="payaccno"
					type="text" id="payaccno" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">付款单位开户行：</td>
				<td align="left" class="l-table-edit-td"><input
					name="bank_address" type="text" id="bank_address" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">付款账户名：</td>
				<td align="left" class="l-table-edit-td"><input
					name="payaccnamecn" type="text" id="payaccnamecn" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td colspan="6"><hr /></td>
			</tr>
			<tr>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="4"><input
					name="summary" type="text" id="summary" ltype="text"
					validate="{maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">备注：</td>
				<td align="left" class="l-table-edit-td" colspan="4"><input
					name="postscript" type="text" id="postscript" ltype="text"
					validate="{maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"></td>
				<td align="right" class="l-table-edit-td" colspan="4">
					<!-- <input type="submit" value="确定支付" id="Button1" class="l-button l-button-reset" /> 
					<input type="reset" value="关闭" class="l-button l-button-reset"/> -->

					<button id="pay" accessKey="B" onclick="saveAccBankNetWage();">
						<b>确定支付（<u>B</u>）
						</b>
					</button> &nbsp;&nbsp;&nbsp;&nbsp;
					<button id="close" accessKey="C" onclick="this_close();">
						<b>关闭（<u>C</u>）
						</b>
					</button>


				</td>
				<td align="left"></td>
			</tr>

		</table>
	</form>
</body>
</html>
