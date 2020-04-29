function init(nameObj,pwdObj,savePwd){
	var mcook=document.cookie;
	var dTheme = mcook.substring(mcook.indexOf('#theme#')+7,mcook.indexOf('#/theme#'));

	if (mcook.indexOf('#vhuser#') != -1){
		vuser=mcook.substring(mcook.indexOf('#vhuser#')+8,mcook.indexOf('#/vhuser#'));
		vpwd=mcook.substring(mcook.indexOf('#vhpwd#')+7,mcook.indexOf('#/vhpwd#'));
		vsav=mcook.substring(mcook.indexOf('#vhsav#')+7,mcook.indexOf('#/vhsav#'));
		nameObj.value=vuser;
		pwdObj.value=vpwd;
		savePwd.checked=(vsav=="true"?true:false);
		nameObj.focus();
	}else{
		nameObj.focus();
	}	
}

function login(nameObj,pwdObj,savePwd,md5){
	var expires = new Date();
	expires.setTime(expires.getTime() + 3 * 30 * 24 * 60 * 60 * 1000);

	if(savePwd.checked==true){
		document.cookie="user=#vhuser#"+nameObj.value+"#/vhuser#,pwd=#vhpwd#"+pwdObj.value+"#/vhpwd#,save=#vhsav#"+savePwd.checked+"#/vhsav#,theme=#theme#"+g_theme+"#/theme#;path=/;expires="+expires.toGMTString();
	}else{
		document.cookie="user=#vhuser#"+nameObj.value+"#/vhuser#,pwd=#vhpwd##/vhpwd#,save=#vhsav#"+savePwd.checked+"#/vhsav#,theme=#theme#"+g_theme+"#/theme#;path=/;expires="+expires.toGMTString();
	}

	var mod=doLogin(nameObj,pwdObj,md5);
	if(mod==null){
		IbtnEnter.src="base/themes/default/css/images/user_botton.gif";
		return;
	}	

	var mods=mod.split(";");
	var main="../../../index_menu.jsp";
	mod=mods[5];
	if(nameObj.value=="admin"){
		main="../../../index_admin.jsp";
	}else if(mod.indexOf("cbcs_")==0){
		main="../../../chd/index_menu.jsp";
		mod=mod.split("_")[1];
	}
	
	var win = window.open(main+'?'+mod, "", "width="+(screen.availWidth)+", height="+(screen.availHeight-35)+", top=0, left=0,scrollbars=1, resizable=1, status=1");
	top.window.opener=null;
	top.window.open('','_self','');
	top.window.close();
	win.focus();
}

function XmlHttp() {
	this._object = new ActiveXObject("Microsoft.XMLHTTP");
}
  
XmlHttp.prototype.post = function() {
	if (arguments.length < 2) {
		alert("arg<2!")
		return;
	}
	var postfix = ".viewhigh";
	if (arguments.length == 4) {
		postfix = arguments[3]
	}
	if (arguments.length == 3) {
		postfix = postfix + arguments[2]
	}
	this._object.open("POST", arguments[0]+postfix, false);
	try {
		this._object.send("<root>"+arguments[1]+"</root>");
	} catch (exception){
		alert(exception)
	}
}

xmlhttp = new XmlHttp;
  
function doLogin(nameObj,pwdObj,md5) {

	window.xmlhttp.post("login", assemble(nameObj.value,pwdObj.value,md5.value) );
	var str = window.xmlhttp._object.responseText;
	if(doMsg(str)==false)
		return null;
	var options = str.split(";");
	var f = parseInt(options[0]);

	if(str.indexOf("Cannot create PoolableConnectionFactory")>0)
		f="-1";
		
	if(str.indexOf("服务器软件安装不正确")>0){
		alert("服务器软件安装不正确，缺少动态链接库!");
		return null;
	}else
	if(f=="-4"){
		alert("该用户已经登录！请联系管理员！");
		return null;
	}else
	if(f=="-3"){
		alert("读取浏览器端加密狗发生错误,请检查！");
		return null;
	}else
	if(f=="-2"){
		alert("超出授权连接数！请联系管理员！");
		return null;
	}else
	if(f=="-1"){
		alert("数据库错误！请联系管理员！");
		return null;
	}else	
	if(f=="1" || f == "2"){
		alert("验证失败，请检查用户账号和密码！");
		nameObj.focus();
		return null;
	}else	
	if(f=="3"){
		alert("管理员只能登陆系统平台！");
		nameObj.setValue(getModuleSimpName("01"));
		return null;
	}else	
	if(f=="4"){
		alert("您没有任一模块的权限！");
		nameObj.focus();
		return null;
	}else	
	if(f=="5"){
		alert("系统中没有" + document.all['LoginDate'].value.substring(0, 4) + "年度的数据，请先建立帐套！");
		nameObj.focus();
		return null;
	}else	
	if(f=="6"){
		alert("此用户已被设置为停用状态！");
		nameObj.focus();
		return null;
	}else	
	if(f=="7"||f=="8"||f=="9"||f=="10"){
		var msg="此工作站没有无权登录系统.";
		if(f=="7")
			msg="请安装工作站允许文件.";
		if(f=="8")
			msg="请插入工作站软件狗.";
		if(f=="9")
			msg="工作站数量已经达到最大允许数."
		alert(msg);
		nameObj.focus();
		return null;
	}
	
	return str;
}
function doMsg(source) {
			if (source.search(/<error>/)!=-1) {
				var error = source.substring(source.search(/<error>/)+"<error>".length, source.search(/<\/error>/))
				alert(error)
				return false;
			}
			return true;
		}
function assemble(user,pwd,md5){
	var result = "<user>";
	result += "<name>"+user+"</name><password>"+pwd+"</password><md5>"+md5+"</md5>";
	result += "<module></module><date>"+getCurrentDate()+"</date>";
	result += "<subfunction>user</subfunction></user>";
	return result;
}
function getCurrentDate(){
	var d=new Date();
	var year=d.getFullYear();
	var month=d.getMonth()+1;
	var day=d.getDate();
	year='0000'+year;
	year=year.substring(year.length-4);
	month='00'+month;
	month=month.substring(month.length-2);
	day='00'+day;
	day=day.substring(day.length-2);
	  return year+'-'+month+'-'+day;
}