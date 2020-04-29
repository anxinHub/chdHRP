var strLocation = new String(window.location);
window.module = strLocation.replace(/^[^\?]*\?/g,'');
if(window.module.substr(0,4)=="drug")
{
    var drug_l=window.module.split(":");
    window.module=drug_l[0];
    window._drugDept=drug_l[1];
    window._drugDeptName=drug_l[2];
}else if(window.module.substr(0,4)=="budg")
{
    var drug_l=window.module.split(":");
    window.module=drug_l[0];
    window._budgCode=drug_l[1];
    window._budgYear=drug_l[2];
}else
{
  window._drugDept=null;
  window._drugDeptName=null;
}
if(window.module.substr(0,4)=="acct")
{
    var drug_l=window.module.split(";");
    window.module=drug_l[0];
    window._Acctyear=drug_l[1];
    window._AcctyearBeginDate=drug_l[2];
    window._AcctyearEndDate=drug_l[3];
}
window.absModule="cbcs";
var _module = "";
var _menu_xml_setp=0;
function start() {
  
  var srcTree = new ActiveXObject("Microsoft.XMLDOM");
  srcTree.async=false;
  window.xmlhttp.post("login", "<user><module>"+window.module+"</module></user>","");
  var str = window.xmlhttp._object.responseText;

  var f = parseInt(str);
  if(f=="0"){
		alert("您没有正常登录或工作已超时,\n请您重新登录!");
		systemExit();
		return false;
	}

  srcTree.loadXML(str);
  var selectNodes = srcTree.getElementsByTagName("max_user");
  if(selectNodes.length>0 ){
  	alert(selectNodes[0].text)
  	window.open('../login.jsp');
  	window.close();
  	return false;
  }

 /* var xsltTree= new ActiveXObject("Microsoft.XMLDOM");
  xsltTree.async = false;
  xsltTree.load("base/xsl/menu.xsl");
  srcTree.loadXML(srcTree.transformNode(xsltTree));
  var selectNodes = srcTree.selectNodes("/root/*");
  if(selectNodes.length==0 ){
    alert("你没有权限访问此模块");
    //window.open('./login.html');
    window.open(top.prefix);
    is_close_alert='0';
    window.close();
    return false;
  }*/
 /* var p=getValuePairBySql("jiangsuCbcsSysinfoPara_select","");

	var ec,en;
	if(p!=null){
		ec=p[0];
		en=p[1];	
	}else{
		ec="";
		en="";
	}
if(ec!='1'){	
var pages=srcTree.getElementsByTagName("menu_node");
    for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
     	if(node=="江苏省报表"){
      	srcTree.getElementsByTagName("menu_node")[i].parentNode.removeChild(pages[i])
      	break;
      }
    }
 } */
/*	window.menu_xml = srcTree.xml.replace(/&/g,"&amp;");
	_menu_xml_setp++;
	mainMenuInitTimer=window.setInterval(function(){
		if(_menu_xml_setp<2)
			return;
		try{
		mainMainMenuTable.setMenuData(parent.menu_xml,"first");
		window.clearInterval(mainMenuInitTimer);
		}catch(E){}
	},20);*/
  //drawmenu(1,1,1);
  ///////////////////

  var moduleName =getModuleName(window.module);
  //document.title="HBOS-CBCS 医院成本核算经济管理信息系统 - " + moduleName+" "+window.herpVersion;
  //document.title="SmartHERP 医院成本核算经济管理信息系统 - " + moduleName;
  document.title="医院成本管理系统 - " + moduleName;
  //showMessage();
  initDwzMenu(srcTree);
  
  return true;
}
function getModuleName(moduleEn) {
  var moduleName="";

  if(moduleEn=="dept")
    moduleName="科室成本核算";
	else if(moduleEn=="proj")
    moduleName="项目成本核算";
	else if(moduleEn=="hos")
    moduleName="医院运营综合分析";
	else if(moduleEn=="direct")
    moduleName="科主任经营分析";
	else if(moduleEn=="disease")
    moduleName="病种成本核算";
	else if(moduleEn=="dist")
    moduleName="内部分配";
	else if(moduleEn=="eco")
    moduleName="经营分析评价";
	else if(moduleEn=="payout")
    moduleName="支出控制系统";
	else if(moduleEn=="decide")
    moduleName="经营预测投资决策支持";
	else if(moduleEn=="rate")
    moduleName="作业成本核算";
     
  return moduleName;
}

function chooseModule(){
  window.showModalDialog("module.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:400px;dialogWidth:500px;");
  if(window.secondOpenDialog!=null){
    eval(window.secondOpenDialog);
  }
}
function chooseDrugHouse(){
  window.showModalDialog("drughouse.html", window, "status:0;resizable:yes;scroll:1;dialogHeight:300px;dialogWidth:500px;")
}
function chooseAcctYear(){
  window.showModalDialog("acctyear.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:300px;dialogWidth:500px;")
}
function chooseBudgYear(){
  window.showModalDialog("budgyear.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:300px;dialogWidth:500px;")
}
function modifyPswd(){
  window.showModalDialog("/password.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:300px;dialogWidth:350px;")
}

function sendMessage(){
  window.showModalDialog("/hrp/sys/message/creat.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:290px;dialogWidth:480px;")
}
function showMessage()
{
  alertMessage();
  setTimeout("showMessage()", 30000);
}
function alertMessage() {  
  //return;
  //首先查数据库中没有自己的并且没有被阅读的消息列表
  var e = new Array();
  subBody.load = "sysMessage_select";
  subBody.para = "";  //清除修改状态时的参数
 	subBody.post();
  
  e = subBody.getHiddenVs();
  if (e == null) return;
  for (i=0; i<e.length; i++) {      
    alert("发送人："+e[i][1]+"\n内容："+e[i][2]+"\n发送时间："+e[i][3]);
    
	  subBody.load = "sysMessage_changestate";
	  subBody.para = "<msgid>"+e[i][0]+"</msgid>";
	  subBody.post();
  }
}
function initModulePrivateFun(w){
	for(var k in baseSystemFunMap){
			w[k]=baseSystemFunMap[k];
	}
}
