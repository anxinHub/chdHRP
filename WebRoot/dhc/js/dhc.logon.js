// Copyright (c) 2001 TrakHealth Pty Limited. ALL RIGHTS RESERVED. 
var objUser=document.getElementById('USERNAME');
var objPswd=document.getElementById('PASSWORD');
var objDept=document.getElementById('DEPARTMENT');
var objRound=document.getElementById('ROUND');
var btnLogon=document.getElementById('Logon');
var objDefaultDept=document.getElementById('ChangDefaultDept');
var objChangePsw = document.getElementById('CHANGEPASSWORD');
var objCHANGEADPASSWORD=document.getElementById("CHANGEADPASSWORD")
var objChangePinPsw = document.getElementById("ChangePINPassword");
var txtUser="";        
var evtTimer;
var evtName;
var doneInit=0;
var focusat=null;
function BodyLoadHandler(){
	//need to call websys_setfocus to wait for elapse time to override setfocus called from scripts_gen
	if ((objUser)&&(objUser.value=='')) {
		$('#USERNAME').focus();
	} else if ((objPswd)&&(objPswd.value=='')) {
		$('#PASSWORD').focus();
	} else if ((objDept)&&(objDept.value=='')&&(!objDept.readOnly)&&(!objDept.disabled)) {
		$('#DEPARTMENT').focus();
	} else if ((objRound)&&(objRound.value=='')&&(!objRound.readOnly)&&(!objRound.disabled)) {
		$('#ROUND').focus();
	} else if (btnLogon) {
		$('#Logon').focus();
	}
	if (objUser) objUser.onfocus = userFocus;
	if (objUser) objUser.onblur = clearDepartment;
	if (objUser) objUser.onkeydown = UserName_KeyDown;
	
	if (objPswd) objPswd.onkeydown = EnterPassword;
	if (objPswd) objPswd.onblur = PswdBlurHandler;
	if (objDept){
		objDept.onkeydown=DEPARTMENT_lookuphandler;
		objDept.onclick=DEPARTMENT_lookuphandler;
		objDept.onmouseover = function(){
	      this.style.backgroundPosition = "0px ";
		}
	    objDept.onmouseout = function(){
	      this.style.backgroundPosition = "0px 0px";
	    }
	}
	txtUser = objUser.value;
	if (btnLogon) btnLogon.onclick = SetLogonClick;
	if (objChangePsw) objChangePsw.onclick = changePswClick;
	if (objChangePinPsw) objChangePinPsw.onclick = changePinPswClick;
	if (objDefaultDept) objDefaultDept.onclick=ChangeDefultDeptClick;
	$(document).on("keyup",function(e){		
		if(e.keyCode==115){
			objDept.fireEvent("onclick");
		}
	})
	if ("undefined"==typeof cspFindXMLHttp){
		$(".tcq").html("<font color='red' size='3pt'>发现IIS缺少csp虚拟目录,请修复安装库.</font>");
		disableDep();
	};
}
function UserName_KeyDown(){
	var keyCode = window.event.keyCode;
	var objUser=document.getElementById('USERNAME');
	var objPswd=document.getElementById('PASSWORD');
	if (keyCode==13){		
		if ((objUser.value!="")&&(objPswd)) objPswd.focus();		
	}
	if((keyCode==8)|| (keyCode>=48&&keyCode<=57)||(keyCode>=65 && keyCode<=90)||(keyCode>=97 && keyCode<=122)){
		objDept.value = "";
		objPswd.value = "";
		$(".usernameinfo").html("");
	}
}
function EnterPassword(evt) {
  var k = websys_getKey(evt);
  var tabkeypressed = (k==9)||(k==13);
  if ((tabkeypressed)&&(objUser)&&(objUser.value!="")&&(objPswd)&&(objPswd.value!="")) {
		btnLogon.onclick();
  }
}

function PswdBlurHandler() {
	if (objUser&&objPswd){
		if ((objUser.value!="")&&(objPswd.value!="")){
			btnLogon.onclick();
		}
	}
}
function DepartmentLookUp(str) {
 	var lu = str.split("^");
	if (objDept) objDept.value = lu[0];
	var obj=document.getElementById('SSUSERGROUPDESC');
	if (obj) obj.value = lu[1];
	var obj=document.getElementById('Hospital');
	if (obj) obj.value = lu[2];
	var obj = document.getElementById("DEPARTMENTAlias");
	if (obj&&lu.length>3) obj.value = lu[3];
	setTimeout(function (){$('#Logon').focus()},100);	
}
function Validate() {
	if ((objUser.value=='')||(objPswd.value=='') ) {
		if (objUser.value=='') {
			objUser.focus();
		} else {
			objPswd.focus();
		}
		event.cancelBubble;
		event.returnValue=false;
	}
}
function userFocus(){
	txtUser = objUser.value;
}
function SetLogonClick(evt) {
	islogon=1;
	var AuthRet , authErrCode ,authErrInfo , myflag;
	//增加密码加密程序 2014-10-10 add by wanghc 
	var password = document.getElementById("PASSWORD").value;
	password = password.replace(/\t|\n|\s|\f|\r|\v/g,"");
	if (password!="" && password.length!==32){ //加过密不再加
		document.getElementById("PASSWORD").value = hex_md5(dhc_cacheEncrypt(password));
	}
	myflag = Logon_click();
	return myflag;
}
function changePswClick () {
	if(objChangePsw && !objChangePsw.disabled) ChangePassword();
}
function changePinPswClick () {
	if(objChangePinPsw && !objChangePinPsw.disabled) ChangePinPassword();
}
function ChangeDefultDeptClick(){
	if(objDefaultDept && !objDefaultDept.disabled) ChangeDefultDept();
}
function clearDepartment() {
	if (txtUser != objUser.value){
		var obj=document.getElementById('SSUSERGROUPDESC');
		if (obj) obj.value = "";
		var obj=document.getElementById('Hospital');
		if (obj) obj.value = "";
		disableDep();
		txtUser = objUser.value;
	}
}
//------- from scripts_gen-----
function Logon_click() {
	if (evtTimer) {
		setTimeout('Logon_click();',200)
	} else {
		websys_setfocus('Logon');
		var frm=document.fSSUserLogon;
		txtUser = objUser.value;
		websys_isInUpdate=true;
		if ((objDept)&&(objDept.value!='')) {	
			var objDeptAlias = document.getElementById("DEPARTMENTAlias");
			var deptDesc = objDept.value;		
			if ( (objDeptAlias)&&(objDeptAlias.value!="") ){
				deptDesc = objDeptAlias.value+"-"+deptDesc;
			}
			if( EnableCALogon==1 && (!dhcsys_calogon(deptDesc).IsSucc)){
				return false;
			}
		}	
		var obj=document.getElementById('Logon');
		if (obj) {obj.disabled=true;obj.onclick=function() {return false};}			
		frm.TEVENT.value='d1473iLogon';
		var nowDate = new Date();
		var nowTime = nowDate.getHours()+":"+nowDate.getMinutes(); //到秒没意义
		nowDate = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+"-"+nowDate.getDate();
		var o = document.getElementById("ClientDate");
		if (o) o.value = nowDate;
		var o = document.getElementById("ClientTime");
		if (o) o.value = nowTime;
		frm.submit();
		
		return false;
	}
}

try {
	var obj=document.getElementById('Logon');
	if (obj) obj.onclick=Logon_click;
} catch(e) { alert(e.number + ' ' + e.description) };

//----lookup component
var DEPARTMENTzLookupGrid;
function DEPARTMENT_lookuphandler(e) {
	if (evtName=='DEPARTMENT') {
		window.clearTimeout(evtTimer);
		evtTimer='';
		evtName='';
	}
	var obj = document.getElementById('DEPARTMENT');
	if (obj && !$(obj).hasClass("disabledField")){
		var type = websys_getType(e);
		var key = websys_getKey(e);
		if ((type=='click')||((type=='keydown')&&(key==117))) {
			var dept = obj.value;
			var depAliObj = document.getElementById('DEPARTMENTAlias');
			if((depAliObj)&&(depAliObj.value!="")){
				dept = depAliObj.value+"-"+dept;
			}
			var url='websys.lookup.csp';
			url += '?ID=d1473iDEPARTMENT&CONTEXT=Kweb.SSUserOtherLogonLoc:LookUpSelectedUserByUHD';
			url += "&TLUJSF=DepartmentLookUp&resizeColumn=0";
			var obj=document.getElementById('USERNAME');
			if (obj) url += "&P1=" + websys_escape(obj.value);
			//var obj=document.getElementById('Hospital');
			//if (obj) url += "&P2=" + websys_escape(obj.value);
			url += "&P2=&P3=&P5="+document.getElementById("IPAddress").value+"&TBAR=科室:P3,安全组:P4";
			websys_lu(url,1,{height:280,width:450});
			return websys_cancel();
		}
	}
}

document.body.onload=BodyLoadHandler;