<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>科目添加页面</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var caseStr = "${caseStr}";
	var saveUrl = "addTmpAccSubj.do?isCheck=false";
	var dataFormat;
	$(function() {
		// 编码规则
		$.post("../accsubj/getRules.do?isCheck=false", null, function(responseData) {
			$("#rules").val(responseData)
			$("#font2").text(responseData);
		});

		loadDict();//加载下拉框
		loadForm();
		
		initValue();
	});
	
	// 保存临时会计科目
	function saveAccSubj() {
		if ($("form").valid()) {
			save();
		}
	}

	// 保存
	function save() {
		var is_check = "否";
		if (liger.get("check1").getValue() != null
				&& liger.get("check1").getValue() != "") {
			is_check = "是";
		}
		
		var subj_dire = "借";
		if($("#subj_dire").val() == "1"){
			subj_dire = "贷";
		}
		
		var is_cash = "是";
		if($("#is_cash").val() == "0"){
			is_cash = "否";
		}

		var formPara = {
			caseStr: caseStr,
			subj_code : $("#subj_code").val(),
			subj_type_code : liger.get("subj_type_code").getText(),
			subj_nature_code : liger.get("subj_nature_code").getText(),
			subj_name : $("#subj_name").val(),
			is_cash : is_cash,
			is_check: is_check,
			subj_dire : subj_dire,
			check1 : liger.get("check1").getText(),
			check2 : liger.get("check2").getText(),
			check3 : liger.get("check3").getText(),
			check4 : liger.get("check4").getText(),
			rules : $("#rules").val(),
		};
		ajaxJsonObjectByUrl(saveUrl, formPara, function(responseData) {
			if (responseData.state == "true") {
				$("#subj_code").val('');
				$("#subj_name").val('');
				$("#is_cash").val('');
				$("#is_check").val('');
				$("#check1").val('');
				$("#check2").val('');
				$("#check3").val('');
				$("#check4").val('');
			}
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
		$("form").ligerForm();
	}
	
	// 初始化字典
	function loadDict() {
		// 科目类别
		autocomplete("#subj_type_code", "../querySubjTypeKind.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);
		// 科目性质
		autocomplete("#subj_nature_code", "../querySubjNature.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);

		// 辅助核算
		autocomplete("#check1", "../queryCheckType.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);
		autocomplete("#check2", "../queryCheckType.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);
		autocomplete("#check3", "../queryCheckType.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);
		autocomplete("#check4", "../queryCheckType.do?isCheck=false", "id", "text", 
				true, true, '', false, '', '', 120);

		$("#check1").ligerComboBox({
			disabled : false,
			cancelable : true
		});
		$("#check2").ligerComboBox({
			disabled : true,
			cancelable : true
		});
		$("#check3").ligerComboBox({
			disabled : true,
			cancelable : true
		});
		$("#check4").ligerComboBox({
			disabled : true,
			cancelable : true
		});

		$("#subj_name_all").ligerTextBox({
			disabled : true
		});

		liger.get("subj_type_code").setValue("${tmpAccSubj.subj_type_code}");
		liger.get("subj_type_code").setText("${tmpAccSubj.subj_type_name}");
		liger.get("subj_nature_code").setValue("${tmpAccSubj.subj_nature_code}");
		liger.get("subj_nature_code").setText("${tmpAccSubj.subj_nature_name}");

		disabledCheck();
		on_change();
	}
	
	//当会计科目改变时触发 科目类别 以及 余额方向 同步更改
	//1---资产/借方 2---负责/贷方  3---净资产/贷方 4----收入/贷方  5----费用/借方
	function on_change() {
		$("#subj_code").change(function() {

			var v_subj_code = $("#subj_code").val();

			var first_str = v_subj_code.substr(0, 1)
			if (first_str == '1') {
				//科目类别
				liger.get("subj_type_code").setValue('01,01');
				liger.get("subj_type_code").setText('资产');
				//余额方向
				liger.get("subj_dire").setValue('0');
				liger.get("subj_dire").setText('借方');
			} else if (first_str == '2') {
				//科目类别
				liger.get("subj_type_code").setValue('02,01');
				liger.get("subj_type_code").setText('负债');
				//余额方向
				liger.get("subj_dire").setValue('1');
				liger.get("subj_dire").setText('贷方');
			} else if (first_str == '3') {
				//科目类别
				liger.get("subj_type_code").setValue('03,01');
				liger.get("subj_type_code").setText('净资产');
				//余额方向
				liger.get("subj_dire").setValue('1');
				liger.get("subj_dire").setText('贷方');
			} else if (first_str == '4') {
				//科目类别
				liger.get("subj_type_code").setValue('04,01');
				liger.get("subj_type_code").setText('收入');
				//余额方向
				liger.get("subj_dire").setValue('1');
				liger.get("subj_dire").setText('贷方');
			} else if (first_str == '5') {
				//科目类别
				liger.get("subj_type_code").setValue('05,01');
				liger.get("subj_type_code").setText('费用');
				//余额方向
				liger.get("subj_dire").setValue('0');
				liger.get("subj_dire").setText('借方');
			} else {
				//科目类别
				liger.get("subj_type_code").setValue('');
				liger.get("subj_type_code").setText('');
				//余额方向
				liger.get("subj_dire").setValue('');
				liger.get("subj_dire").setText('');
			}
		});

	}
	
	//辅助核算联动
	function disabledCheck() {
		$("#check1").change(function() {
			if (this.value == "") {
				$("#check2").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check2").setValue('');
				liger.get("check2").setText('');
				$("#check3").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check3").setValue('');
				liger.get("check3").setText('');
				$("#check4").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check4").setValue('');
				liger.get("check4").setText('');
			} else {

				$("#check2").ligerComboBox({
					disabled : false,
					cancelable : true
				});
				$("#check3").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				$("#check4").ligerComboBox({
					disabled : true,
					cancelable : true
				});
			}
		});
		$("#check2").change(function() {
			if (this.value == "") {
				$("#check3").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check3").setValue('');
				liger.get("check3").setText('');
				$("#check4").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check4").setValue('');
				liger.get("check4").setText('');
			} else {
				$("#check3").ligerComboBox({
					disabled : false,
					cancelable : true
				});
				$("#check4").ligerComboBox({
					disabled : true,
					cancelable : true
				});
			}
		});
		$("#check3").change(function() {
			if (this.value == "") {
				$("#check4").ligerComboBox({
					disabled : true,
					cancelable : true
				});
				liger.get("check4").setValue('');
				liger.get("check4").setText('');

			} else {

				$("#check4").ligerComboBox({
					disabled : false,
					cancelable : true
				});
			}
		});

	}
	
	// 初始化表单
	function initValue(){
		var subjCode = "${tmpAccSubj.subj_code}";
		if(subjCode != ""){
			saveUrl = "updateTmpAccSubj.do?isCheck=false";
			$("#subj_code").attr("disabled", "disabled");
			$("#subj_name_all").val("${tmpAccSubj.subj_name_all}");
			$("#subj_code").ligerTextBox({ disabled : true });
			
			if("${tmpAccSubj.subj_dire}" == "0" && "${tmpAccSubj.subj_dire}" == "借"){
				$("#subj_dire").val("0");
			}else{
				$("#subj_dire").val("1");
			}
			
			if("${tmpAccSubj.subj_dire}" == "0" && "${tmpAccSubj.subj_dire}" == "否"){
				$("#is_cash").val("0");
			}else{
				$("#is_cash").val("1");
			}
			
			liger.get("check1").setText("${tmpAccSubj.check1}");
			liger.get("check2").setText("${tmpAccSubj.check2}");
			liger.get("check3").setText("${tmpAccSubj.check3}");
			liger.get("check4").setText("${tmpAccSubj.check4}");
		}
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<font id="font1">编码规则：<font id="font2" color="red"></font></font>
		<hr />
		<input type="hidden" id="rules" name="rules" />
		<div id="panel1-1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 5px;">
						<b><font color="red">*</font></b>科目编码：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 5px;">
						<input name="subj_code" type="text" id="subj_code" ltype="text" value="${tmpAccSubj.subj_code}" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 5px;">
						<b><font color="red">*</font></b>科目名称：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 5px;">
						<input name="subj_name" type="text" id="subj_name" ltype="text" value="${tmpAccSubj.subj_name}" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						<b><font color="red">*</font></b>科目全称：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<input name="subj_name_all" type="text" id="subj_name_all" ltype="text" value="系统生成" disabled="disabled" validate="{required:true,maxlength:200}" />
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						<b><font color="red">*</font></b>科目类别：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<input name="subj_type_code" type="text" id="subj_type_code" ltype="text" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						<b><font color="red">*</font></b>科目性质：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<input name="subj_nature_code" type="text" id="subj_nature_code" ltype="text" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						<b><font color="red">*</font></b>余额方向：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<select id="subj_dire" name="subj_dire">
							<option value="0">借方</option>
							<option value="1">贷方</option>
						</select>
					</td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						是否核算现金流 
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<select id="is_cash" name="is_cash">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td align="left"></td>
				</tr>
			</table>
		</div>
		<br/>
		辅助核算
		<hr />
		<div id="panel1-2">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 5px;">
						辅助核算1：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 5px;">
						<input name="check1" type="text" id="check1" ltype="text"/>
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 5px;">
						辅助核算2：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 5px;">
						<input name="check2" type="text" id="check2" ltype="text"/>
					</td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						辅助核算3：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<input name="check3" type="text" id="check3" ltype="text"/>
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px; padding-top: 10px;">
						辅助核算4：
					</td>
					<td align="left" class="l-table-edit-td" style="padding-top: 10px;">
						<input name="check4" type="text" id="check4" ltype="text"/>
					</td>
					<td align="left"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>