<!--
  Header: login.jsp
  Author: LiuZhikun
  Date: 2006/06/02
  Revision: 1.0
  和login.html 的不同是 表格隐藏，去掉focus调用, 固定登陆 dept, 自动调用login(),自动关闭
  request.getParameter("name");
  request.getParameter("pwd");
-->
<%@ page language="java" contentType="text/html;charset=GBK" errorPage="error.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<title>医院成本管理系统-云康医院成本核算</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;

}
.input01 {
	font-size: 12px;
	color: #003C9B;
	text-decoration: none;
	clear: none;
	float: none;
	border: 1px;
	width: 124px;
	line-height: 16px;
	left: 0px;
	margin-left: 0px;
	margin-top: 0px;
	height: 14px;
	background-color: #FFFFFF;
	border-left-color: #AFCDDC;
	border-bottom-color: #AFCDDC;
	border-right-color: #AFCDDC;
	border-top-color: #AFCDDC;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: outset;
	text-align: left;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--



var objDiv = null;

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>


<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

//////////////////////////////////////
function showmenu(){
	objDiv.style.visibility = "visible";
	Image111.src = "image/x2.png";
}

function whenPhotoOut(){
	Image111.src = "image/x1.png";
}

function hidemenu(){
	objDiv.style.visibility = "hidden";
	Image111.src = "image/x1.png";
}

function putVal(){
	if(event.srcElement.tagName.toLowerCase()=="td"){
		inp.value = event.srcElement.innerText;
		objDiv.style.visibility = "hidden";
		Image111.src = "image/x1.png";
	}
}

function createDiv(){
	objDiv = window.document.createElement("<div style='position:absolute;border:solid 0px yellow;background:#000000;overflow:hidden;z-index:999;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=95);'></div>")
	objDiv = window.document.body.appendChild(objDiv);
    jhtcSetHtml(objDiv, '<TABLE onclick="putVal()" cellspacing="1" cellpadding="2" style="width:100%;background:#AFCDDC;font-size:12px;color=#054F8B;cursor:pointer;"><TR bgcolor="#ffffff"><TD id="id_td" onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'dept\'">科室核算系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'eco\'">经营指标评价系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'hos\'">院长经营分析系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'direct\'">科主任经营分析系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'proj\'">项目核算系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'disease\'">病种核算系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'dist\'">内部分配系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'payout\'">支出控制系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'decide\'">经营预测投资决策支持系统</TD ></TR><TR bgcolor="#ffffff"><TD onmouseover="this.style.color=\'#0663AE\';" onmouseout="this.style.color=\'#054F8B\'" onclick="ID_app.value=\'rate\'">项目作业法模型</TD ></TR></TABLE>');
	objDiv.style.left = 562;
	objDiv.style.top  = 314;
	objDiv.style.width  = 128;
	objDiv.style.visibility = "hidden";
	document.getElementById("id_td").click();
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body oncontextmenu="return false" leftmargin="0" topmargin="0" rightmargin="0"  bottommargin="0" onLoad="MM_preloadImages('image/x2.png','image/login2.png');createDiv()" scroll="no">

<table style="display:none" width="1024" height="768" border="0" cellpadding="0" cellspacing="0" background="image/in1.png" id="maintable" onclick="hidemenu()">
  <tr>
    <td  height="768" align="left" valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="554" height="324">&nbsp;</td>
        <td colspan="3" valign="bottom"><div id="Layer1" style="position:absolute; width:128px; height:21px; z-index:1; top: 296px; left: 562px;">
          <input id="inp" name="textfield" type="text" class="input01" value="系统模块菜单……" size="14" >
        </div></td>
      </tr>
      <tr>
        <td rowspan="5">&nbsp;</td>
        <td width="7" height="19">&nbsp;</td>
        <td width="172"><input id='ID_user_id' class="input01" name="textfield" type="text" size="14" maxlength="18" value="<%=request.getParameter("name")%>">
        	<input id='ID_app' style='display:none' value='test'>
        	<input id='ID_MAC' style='display:none' value='test'>
        	<input id='ID_IP' style='display:none' value='test'>
        </td>
        <td width="288" align="left" valign="bottom" >
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="2"></td>
            </tr>
          </table></td>
      </tr>
      <tr>
        <td height="28" rowspan="2">&nbsp;</td>
        <td rowspan="2" valign="bottom"><input id='ID_password' class="input01" "name="textfield" type="password" size="14" maxlength="18" onkeypress="checkEnter(event)" value="<%=request.getParameter("pwd")%>"></td>
        <td align="left" valign="bottom">&nbsp;</td>
      </tr>
      <tr>
        <td height="1" align="left" valign="bottom"></td>
      </tr>
      <tr>
        <td height="33" colspan="2" align="left" valign="bottom"><div id="Layer3" style="position:absolute; width:51px; height:47px; z-index:3; left: 698px; top: 324px;"><a href="#" onMouseOut="MM_swapImgRestore()"  onMouseup="MM_swapImage('Image2','','image/login3.png',2)"  onMouseover="MM_swapImage('Image2','','image/login3.png',2)" onMousedown="MM_swapImage('Image2','','image/login2.png',1)"><img src="image/login.png" name="Image2" width="47" height="47" border="0" onclick='login()'></a></div></td>
        <td align="left" valign="bottom"><div id="Layer2" style="position:absolute; width:35px; height:16px; z-index:2; left: 671px; top: 298px;"><img src="image/x1.png" name="Image111" width="17" height="15" border="0" id="Image111" onmouseover="showmenu()"></div></td>
      </tr>
      <tr>
        <td height="55" colspan="3" valign="bottom">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<script language="javascript" event="OnCompleted(hResult,pErrorObject, pAsyncContext)" for=foo>

//提交客户端IP、MAC地址、计算机名
var cM = document.getElementsByTagName( "txtMACAddr" );
var cN = document.getElementsByTagName( "txtDNSName" );
if (cM!==""&&cN!==""){
        CheckCustoemr();
        }
function CheckCustoemr(){
        var CheckUserMac = unescape(MACAddr);

        ID_MAC.value = CheckUserMac;
        ID_IP.value  = unescape(IPAddr);
        }

</script>
<OBJECT id=locator classid=CLSID:76A64158-CB41-11D1-8B02-00600806D9B6 VIEWASTEXT></OBJECT>
<OBJECT id=foo classid=CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223></OBJECT>
<SCRIPT language=JScript event=OnObjectReady(objObject,objAsyncContext) for=foo>
   if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
   {
    if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
    MACAddr = objObject.MACAddress;
    if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
    IPAddr = objObject.IPAddress(0);
    if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")
    sDNSName = objObject.DNSHostName;
    }
</script>
<script language="javascript">
var service = locator.ConnectServer();
var MACAddr ;
var IPAddr ;
var DomainAddr;
var sDNSName;
service.Security_.ImpersonationLevel=3;
service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
</script>

</body>
</html>

<script>
<!--

// 清空密码
function p_reset() {
  ID_user_id.value=''
  ID_password.value=''
}

// 检测
function checkEnter(e) {
 	if (e.keyCode == 13) {
    	return login();
   }
}

// 登陆
function login() {
  if (ID_user_id.value.length==0) {
      alert("用户代码不能为空，请您重新输入！");
      return false;
    }
    if (ID_password.value.length==0) {
      alert("用户密码不能为空，请您重新输入！");
      return false;
    }
	hbos_login(ID_user_id.value,ID_password.value,ID_app.value,ID_MAC.value,ID_IP.value);
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function XmlHttp() {
	this._object = new ActiveXObject("Microsoft.XMLHTTP");
}

XmlHttp.prototype.post = function() {
	if (arguments.length < 2) {
		alert("arg<2!")
		return;
	}

	this._object.open("POST", arguments[0], false);
	try {
		this._object.send("<root>"+arguments[1]+"</root>");
	} catch (exception){
		alert(exception)
	}
}

var xmlhttp = new XmlHttp;

function hbos_login(name,pwd,m,mac,ip){
	m="dept"
	if(name=="administrator")
		m="sys";
	// alert( assemble(name,pwd,m,mac,ip) );
	if ( mac == "test" && ( m == "hos" || m == "direct" ) ) {
		alert("您的IE安全级别太高，无法登录系统，请设置IE的activeX未签名级别!");
		return false;
	}
	xmlhttp.post("login.hbviewhigh", assemble(name,pwd,m,mac,ip))

	var str = window.xmlhttp._object.responseText

	var options = str.split(";");
	var f = parseInt(options[0]);
  	if(str.indexOf("Cannot create PoolableConnectionFactory")>0)
  	  f=3;
  	if(str.indexOf("服务器软件安装不正确")>0){
  	  alert("服务器软件安装不正确，缺少动态链接库!");
  		return false;
  	}

  	if(f=="1"){
  		alert("无此用户!");
  		return false;
  	}
  	if(f=="2"){
  		alert("密码错误!");

  		return false;
  	}
  	if(f=="3"){
  		alert("数据库错误!请稍后再试!");
  		return false;
  	}


	document.getElementById("ID_password").value = "";
	var smdhtml=new Array();
	smdhtml = options;

	var childTitle='CBCS';
	var win = window.open('main.html?'+m, childTitle, "width="+(screen.availWidth-10)+", height="+(screen.availHeight-30)+", top=0, left=0,  scrollbars=1, resizable=1");
	win.subfunction="mod";
	win.nowEmpCode=smdhtml[1];//当前登录者的员工编码
	win.nowEmpName=smdhtml[2];//名称
	win.focus();

}
function assemble(name,pwd,module,mac,ip){
	var result="<user>";
	result=result+"<name>"+name+"</name><password>"+pwd+"</password><module>"+module+"</module><mac>"+mac+"</mac><ip>"+ip+"</ip><subfunction>user</subfunction>"
	result=result+"</user>"
	return result;
}
login();
window.close();
//-->
</script>