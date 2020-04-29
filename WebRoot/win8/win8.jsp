<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>医院资源管理与大数据应用系统 DRP-HRP</title>
<style>
.logo_box{
    /* box-shadow: 0 4px 30px #d3c6f4; */
    width: 370px;
    height: auto;
    overflow: hidden;
    margin: 7% auto;
    padding: 40px;
    border-radius: 5px;
    color: #fff;
    font-size: 14px;
}
.input_outer{
	height: 46px;
	padding: 0 5px;
	margin-bottom: 20px;
	border-radius: 50px;
	position: relative;
	border: rgba(86, 144, 212, .6) 2px solid !important;
	background: #005691;
}
.u_user{
	width: 25px;
	height: 25px;
	background: url(base/win8/images/login_ico.png);
	background-position:  -125px 0;
	position: absolute;
	margin: 12px 13px;
}
.us_uer{
	width: 25px;
	height: 25px;
	background: url(base/win8/images/login_ico.png);
	background-position: -125px -34px;
	position: absolute;
	margin: 12px 13px;
}
.text{
	width: 290px;
	height: 46px;
	outline: none;
	display: inline-block;
	font: 16px "microsoft yahei",Helvetica,Tahoma,Arial,"Microsoft jhengHei";
	margin-left: 50px;
	border: none;
	background: transparent;
	line-height: 46px;
	color:#fff;
}

.mb2{
	margin-bottom: 20px
}
.mb2 a{
	text-decoration: none;
	outline: none;
}
.submit {
	padding: 15px;
	margin-top: 20px;
	display: block;
}
.act-but{
	height: 20px;
	line-height: 20px;
	text-align: center;
	font-size: 20px;
	border-radius: 50px;
	background: #0096e6;
}
.clearfix::before{
	content: "";
	display: table;
}
.clearfix::after{
	display: block;
	clear: both;
	content: "";
	visibility: hidden;
	height: 0;
}
.checkbox{
	vertical-align: middle;
	margin: 4px 5px 0 0;
	float: left;
}

input::-webkit-input-placeholder {
    color: #fff;
}

input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus  {
 	-webkit-box-shadow: 0 0 0 1000px #005691 inset !important;
 	-webkit-text-fill-color: #fff;
    height: 44px !important;
}
</style>
</head>

<script type="text/javascript" language="javascript" src="<%=path%>/win8/base/win8/style/jquery1.7.js"></script>
<script type="text/javascript" language="javascript" src="<%=path%>/win8/base/win8/style/jquery.XYTipsWindow.min.2.8.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	$("#login_remove").click(function(){
		parent.$.XYTipsWindow.removeBox();
	});	
	
	$("#user_code").val("");
	$("#user_pwd").val(""); 
	
	if ($.cookie("drp_hrp_usercode") !=null) {
		$("#user_code").val($.cookie("drp_hrp_usercode"));
	}
	if ($.cookie("drp_hrp_password") !=null) {
		$("#user_pwd").val($.cookie("drp_hrp_password"));
	}
	if ($.cookie("drp_hrp_check-box") !=null) {
		if ($.cookie("drp_hrp_check-box") == "true") {
			$("#check-box").ligerCheckBox({}).setValue(true);
		}else {
			$("#check-box").ligerCheckBox({}).setValue(false);
		}
	};
		
	
	document.onkeydown = function(event) {
		var e = event || window.event|| arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc 
		}
		if (e && e.keyCode == 113) { // 按 F2 
		}
		if (e && e.keyCode == 13) { // enter 键
			_loginSys();
		}
	};
});

function _loginSys(){
	
	$('.act-but').css('background','#c6cacc').text('登录中');
	$('#IbtnEnter').attr("disabled","disabled");
	var myDate = new Date();
	ajaxJsonObjectByUrl("../login.do?isCheck=false", {
		user_code : $("#user_code").val(),
		user_pwd : $("#user_pwd").val(),
		acct_year : myDate.getFullYear()
	}, function(responseData) {
		$("#loginMsg").text("");
		if (responseData.isLogin == "ok") {
			openMain(
				$("#user_code").val(),
				$("#user_pwd").val()
			);
		}
		else if (responseData.loginMsg != null && responseData.loginMsg != "") {
			$("#loginMsg").text(responseData.loginMsg);
			$('#IbtnEnter').removeAttr("disabled");
			$('.act-but').css('background','#0096e6').text('登录');
		}
	});
};

// 工作环境
function openSystemTheme() {

  	 $.XYTipsWindow({
		___title:"杭州亦童科技有限公司",
		___showTitle:false,
		___showBoxbg:false,
		___boxBdColor:"#666",
		___boxBdOpacity:"2",
		___boxWrapBdColor:"#ABABAB",
		___closeID:"closelogin",
		___width:"300",
		___height:"200",
		___content:"iframe:../aotuInfoCopy.jsp",
		___showbg:true
	});
}
function openMain(user, pwd) {
	
	if ($("#check-box").is(":checked")) {

		$.cookie("drp_hrp_usercode", user, {
			expires : 7
		});
		$.cookie("drp_hrp_password", pwd, {
			expires : 7
		});
		$.cookie("drp_hrp_check-box", "true", {
			expires : 7
		});
	}else {

		$.cookie("drp_hrp_usercode", "", {
			expires : 7
		});
		$.cookie("drp_hrp_password", "", {
			expires : 7
		});
		$.cookie("drp_hrp_check-box", "false", {
			expires : 7
		});
	}; 
	
	top.window.location.href = "<%=path%>/main.html";
}
</script>

<body style="background-color:#005691;overflow: hidden;">
	<!-- 登录界面 -->
   	<div class="logo_box">
		<form action="#" name="f" method="post">
			<div class="input_outer">
				<span class="u_user"></span>
				<input name="logname" class="text" id="user_code" type="text" placeholder="输入用户名" autocomplete="new-password" onfocus="this.removeAttribute('readonly')"/>
			</div>
			<div class="input_outer">
				<span class="us_uer"></span>
				<input name="logpass" class="text" id="user_pwd" type="password" placeholder="输入密码" autocomplete="new-password" onfocus="this.removeAttribute('readonly')"/>
			</div>
			<div id="loginMsg" style="width: 300px; text-align: left; color: #F00;font-size:16px;"></div>
			<input id="check-box" class="checkbox" type="checkbox" /><span>记住密码</span>
			<div class="mb2" id="IbtnEnter" onclick="_loginSys()"><a class="act-but submit" href="javascript:;" style="color: #FFFFFF">登录</a></div>
		</form>
	</div>
</body>
</html>
