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
		alert("��û��������¼�����ѳ�ʱ,\n�������µ�¼!");
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
    alert("��û��Ȩ�޷��ʴ�ģ��");
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
     	if(node=="����ʡ����"){
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
  //document.title="HBOS-CBCS ҽԺ�ɱ����㾭�ù�����Ϣϵͳ - " + moduleName+" "+window.herpVersion;
  //document.title="SmartHERP ҽԺ�ɱ����㾭�ù�����Ϣϵͳ - " + moduleName;
  document.title="ҽԺ�ɱ�����ϵͳ - " + moduleName;
  //showMessage();
  initDwzMenu(srcTree);
  
  return true;
}
function getModuleName(moduleEn) {
  var moduleName="";

  if(moduleEn=="dept")
    moduleName="���ҳɱ�����";
	else if(moduleEn=="proj")
    moduleName="��Ŀ�ɱ�����";
	else if(moduleEn=="hos")
    moduleName="ҽԺ��Ӫ�ۺϷ���";
	else if(moduleEn=="direct")
    moduleName="�����ξ�Ӫ����";
	else if(moduleEn=="disease")
    moduleName="���ֳɱ�����";
	else if(moduleEn=="dist")
    moduleName="�ڲ�����";
	else if(moduleEn=="eco")
    moduleName="��Ӫ��������";
	else if(moduleEn=="payout")
    moduleName="֧������ϵͳ";
	else if(moduleEn=="decide")
    moduleName="��ӪԤ��Ͷ�ʾ���֧��";
	else if(moduleEn=="rate")
    moduleName="��ҵ�ɱ�����";
     
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
  //���Ȳ����ݿ���û���Լ��Ĳ���û�б��Ķ�����Ϣ�б�
  var e = new Array();
  subBody.load = "sysMessage_select";
  subBody.para = "";  //����޸�״̬ʱ�Ĳ���
 	subBody.post();
  
  e = subBody.getHiddenVs();
  if (e == null) return;
  for (i=0; i<e.length; i++) {      
    alert("�����ˣ�"+e[i][1]+"\n���ݣ�"+e[i][2]+"\n����ʱ�䣺"+e[i][3]);
    
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
