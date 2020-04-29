<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/json2.js"></script>
<link href="<%=path%>/lib/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var dataFormat;
	$(function() {
		
		document.location.href="win8/base/win8/home.html";
		
		
		//loadForm();
		//init();
		/*
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 27) { // 按 Esc 
			}
			if (e && e.keyCode == 113) { // 按 F2 
			}
			if (e && e.keyCode == 13) { // enter 键
				login();
			}
		};
		*/
	});
	/*
	function init() {
		$(document).ready(
				function() {

					if ($.cookie("drp_htc_userCode") != null) {
						$("#userCode").val($.cookie("drp_htc_userCode"));
					}
					if ($.cookie("drp_htc_password") != null) {
						document.getElementById("password").value = $.cookie("drp_htc_password");
					}
					if ($.cookie("drp_htc_savePwd") != null) {
						if ($.cookie("drp_htc_savePwd") == "true") {
							$("#savePwd").ligerCheckBox({}).setValue(true);
							;
							//$("#savePwd").attr("checked",true);
						} else {
							$("#savePwd").ligerCheckBox({}).setValue(false);
							;
							//$("#savePwd").attr("checked","false");
						}
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
		var curDate = new Date();
		$("#acctYear").val(curDate.getFullYear());
		$("form").ligerForm();
	}

	function login() {
		if ($("form").valid()) {
			$("#loginMsg").text("系统登录中,请稍候...");
			//parent.location.href="login.do?userCode="+$("#userCode").val()+"&password="+$("#password").val();
			//document.getElementById("form1").submit();
			ajaxJsonObjectByUrl("login.do?isCheck=false", {
				user_code : $("#userCode").val(),
				password : $("#password").val(),
				acct_year : $("#acctYear").val(),
				mod_code : "00"
			}, function(responseData) {
				$("#loginMsg").text("");
				if (responseData.isLogin == "ok") {
					openMain($("#userCode").val(), $("#password").val(),
							responseData.mod_code);
				}
				if (responseData.loginMsg != null
						&& responseData.loginMsg != "") {
					$("#loginMsg").text(responseData.loginMsg);
				}
			});
		}
	}

	function openMain(user, pwd, mod_code) {

		if ($("#savePwd").attr("checked") == true) {
			
			$.cookie("drp_htc_userCode", user, {
				expires : 7
			});
			$.cookie("drp_htc_password", pwd, {
				expires : 7
			});
			$.cookie("drp_htc_savePwd", "true", {
				expires : 7
			});
		} else {
			$.cookie("drp_htc_userCode", "", {
				expires : 7
			});
			$.cookie("drp_htc_password", "", {
				expires : 7
			});
			$.cookie("drp_htc_savePwd", "false", {
				expires : 7
			});
		}
		var skin = $.cookie("drp_htc_skin");
		if (skin != null && skin != "") {
			skin = "&skin=" + skin;
		} else {
			skin = "";
		}
		var win = window
				.open("main.html?mod_code=" + mod_code + skin, "", "width="
						+ (screen.availWidth) + ", height="
						+ (screen.availHeight - 35)
						+ ", top=0, left=0,scrollbars=1, resizable=0, status=1");
		top.window.opener = null;
		top.window.open('', '_self', '');
		top.window.close();
		win.focus();

	}
	function MM_jumpMenu(targ, selObj, restore) { //v3.0
		eval(targ + ".location='" + selObj.options[selObj.selectedIndex].value
				+ "'");
		if (restore)
			selObj.selectedIndex = 0;
	}
	*/
</script>

</head>
<body>
	<!-- 登陆页面 -->
<!-- 
	<div id="login_dlbox">
		<div class="login_dl">
			<form name="form1" method="post" id="form1" action="login.do">
		<table width="572" height="200" cellpadding="0" cellspacing="0"
			align="center" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</td>
				<td align="left" class="l-table-edit-td"><input name="userCode"
					type="text" id="userCode" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
				<td align="left" class="l-table-edit-td"><input name="password"
					type="password" id="password" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate"
					type="text" name="acctYear" id="acctYear"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">记住密码：</td>
				<td align="left" class="l-table-edit-td"><input id="savePwd"
					name="savePwd" type="checkbox" /></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><div id="loginMsg"
						style="width: 200px; text-align: center; color: #F00;"></div></td>
			</tr>
			<tr>
				<td colspan="3" align="center">&nbsp;</td>
			</tr>
			<tr>
			<td></td>
				<td colspan="3" align="right" ><div class="liger-button"
						style="position: absolute;"
						data-width="80" onclick="login()">登 录</div></td>

			</tr>

		</table>
	</form>
		</div>
	</div>
	 -->
</body>
</html>
<!-- 
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" action="login.do">
		<table width="572" height="200" cellpadding="0" cellspacing="0"
			align="center" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td">用户名：</td>
				<td align="left" class="l-table-edit-td"><input name="userCode"
					type="text" id="userCode" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td">密码：</td>
				<td align="left" class="l-table-edit-td"><input name="password"
					type="password" id="password" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">年度：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate"
					type="text" name="acctYear" id="acctYear"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">记住密码：</td>
				<td align="left" class="l-table-edit-td"><input id="savePwd"
					name="savePwd" type="checkbox" /></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><div id="loginMsg"
						style="width: 200px; text-align: center; color: #F00;"></div></td>
			</tr>
			<tr>
				<td colspan="3" align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><div class="liger-button"
						style="position: absolute; left: 120px; top: 170px;"
						data-width="80" onclick="login()">登 录</div></td>

			</tr>

		</table>
	</form>
</body>
</html>
 -->
