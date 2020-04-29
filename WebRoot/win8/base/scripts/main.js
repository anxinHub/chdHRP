function delayShowDialog(dlg){
	window.setTimeout(dlg,200);
}
function initModule(){
	var strLocation = new String(window.location);
	window.module = strLocation.replace(/^[^\?]*\?/g,'');
	
	if(window.module.substr(0,4)=="drug")
	{
	    var drug_l=window.module.split(";");
	    if(drug_l.length==3){
		    window.module=drug_l[0];
		    window._drugDept=drug_l[1];
		    window._drugDeptName=drug_l[2];
		}else
			delayShowDialog("chooseDrugHouse()");
	}else if(window.module.substr(0,4)=="budg")
	{
	    /*var drug_l=window.module.split(";");
	    if(drug_l.length==3){
		    window.module=drug_l[0];
		    window._budgCode=drug_l[1];
		    window._budgYear=drug_l[2];
	    }else
	    	delayShowDialog("chooseBudgYear()");*/
	}else
	{
	  window._drugDept=null;
	  window._drugDeptName=null;
	}
	/*if(window.module.substr(0,4)=="acct")
	{
	    var drug_l=window.module.split(";");
	    if(drug_l.length==4){
		    window.module=drug_l[0];
		    window._Acctyear=drug_l[1];
		    window._AcctyearBeginDate=drug_l[2];
		    window._AcctyearEndDate=drug_l[3];
	    }else
	    	delayShowDialog("chooseAcctYear()");
	}*/
	if(window.module=="")
		delayShowDialog("chooseModule()");

}
var _module = "";
var _menu_xml_setp=0;
//启动加载
function start() {
	var modList = modeCodeToNameList;
	for(var o in modList){
	   if(window.module==modList[o]){
	       setEnvionmentValue("moduleId",o);
		}
	}
  var srcTree = new ActiveXObject("Microsoft.XMLDOM");
  srcTree.async=false;
 
  if(window.module.substr(window.module.length-1)=="#"){
	 window.module=window.module.substr(0,window.module.length-1);
  }

  window.xmlhttp.post("login", "<user><module>"+window.module+"</module></user>");
  var str = window.xmlhttp._object.responseText;

  var f = parseInt(str);
	if(f=="0" || (str.indexOf("<error>null</error>")!=-1 && str.indexOf("menu_code")==-1)){
		alert("您没有正常登录或工作已超时,\n请您重新登录!");
		systemExit();
		return false;
	}
  if (f=='4') {
	  alert('软件狗没有设置此权限，请检查软件狗')
	  window.open('login.jsp');
	  window.close();
	  return false;
	}
  srcTree.loadXML(str);

  // 预算模块，用于根据预算功能设置控制预算菜单的显示
  if(window.module=='budg'){
  }
  //单机核算模块
  if(window.module=='dev'){
  	absModule = "dev";
	initDevParaData();
  }
  //人力资源模块
  if(window.module=='hr')
  {
  	  
	  window.xmlhttp.post("hr_empDocumentQueryInfo_userPerm","<user_id>"+getUserID()+"</user_id>","?isCheck=false");
	  var resXml = window.xmlhttp._object.responseXml;

	  if(resXml.getElementsByTagName("td")[0]&&resXml.getElementsByTagName("td")[0].firstChild!=null)
	  {
	  	  window.xmlhttp.post("hrWorkRemindMain_select","<a>"+getUserID()+"</a><b>auto</b>");
		  var resXml = window.xmlhttp._object.responseXml;
		  var error=resXml.selectSingleNode("root/error");
		 
		  if(null!=error && error.text){
				alertMsg.warn("工作提醒没有权限")
				return false;
		  }
		  var data = resXml.getElementsByTagName('tbody')[0];
		  var trs = data.getElementsByTagName("tr");
		  var sumNum=0;
		  for (var i=0; i<trs.length; i++){
		  	sumNum=sumNum+parseInt(trs[i].getElementsByTagName("td")[1].firstChild.nodeValue);
		  }
		  //查询人员调动是否为0
		  window.xmlhttp.post("hrWorkRemind_personMoveNum","<user_id>"+getUserID()+"</user_id>","?isCheck=false");
	  	var resXml2 = window.xmlhttp._object.responseXml;
		  var data2 = resXml2.getElementsByTagName('tbody')[0];
		  var trs2 = data2.getElementsByTagName("tr");
		  var sumNum2 = 0;
		  sumNum2 = parseInt(trs2[0].getElementsByTagName("td")[0].firstChild.nodeValue);
		  if(sumNum!=0 || sumNum2!=0)
		  {
		  	 //	document.getElementById("mainFrame").src="hrp/hr/workremind/remind/main_remind.html?load=<opmode>auto</opmode>";
		  		openIFDialog(window,'../../hrp/hr/workremind/remind/main_remind.html?load=<opmode>auto</opmode>','dialogWidth:900px;dialogHeight:600px');
		  }
	  }
	  

  }
   if(getEnvionmentValue("_module")=='payctl' || getEnvionmentValue("_module")=='pact'){
  	init_mate_radix_point();
  	
  	if (getEnvionmentValue("mateSysPara")==null || getEnvionmentValue("mateSysPara")=='undefined' ){
			var mateSysParaArray;
			var para='<comp_code>'+getCompCode()+'</comp_code><copy_code>'+getCopyCode()+'</copy_code>'
	  	mateSysParaArray = new Array();
	    window.xmlhttp.post("mate_sys_para_value_select", para, "?isCheck=false");
	    var srcTree1 = new ActiveXObject("Microsoft.XMLDOM");
	    srcTree1.async=false;
	    srcTree1.load(xmlhttp._object.responseXML);
	    var list1 = srcTree1.getElementsByTagName("tr");
	    for (var i=0; i<list1.length; i++) {
	      var list2 = list1.item(i).getElementsByTagName("td");
	      if (list2.item(0).firstChild != null)
	      mateSysParaArray[list2.item(0).firstChild.nodeValue]=list2.item(1).firstChild.nodeValue
	   	}
			setEnvionmentValue("mateSysPara",mateSysParaArray);
		}
  }
  // Page load Excute Method<initPerfParaData>
  if(getEnvionmentValue("_module")=='perf'){
  	initPerfParaData();
  	initOprStratagem();
	if(typeof(showCompName)!="undefined")
		showCompName();
  }
  if(getEnvionmentValue("_module")=='bonus'){
  	initbonusParaData();
  }
  //初始化小数位数
  if(getEnvionmentValue("_module")=='mate'){
  	init_mate_radix_point();
 		init_is_link_budg();
  }
  
  //初始化小数位数
  if(getEnvionmentValue("_module")=='acct'){
 		if (getEnvionmentValue("mateSysPara")==null || getEnvionmentValue("mateSysPara")=='undefined' ){
			var mateSysParaArray;
			var para='<comp_code>'+getCompCode()+'</comp_code><copy_code>'+getCopyCode()+'</copy_code>'
	  	mateSysParaArray = new Array();
	    window.xmlhttp.post("mate_sys_para_value_select", para, "?isCheck=false");
	    var srcTree1 = new ActiveXObject("Microsoft.XMLDOM");
	    srcTree1.async=false;
	    srcTree1.load(xmlhttp._object.responseXML);
	    var list1 = srcTree1.getElementsByTagName("tr");
		    for (var i=0; i<list1.length; i++) {
		      var list2 = list1.item(i).getElementsByTagName("td");
		      	if (list2.item(0).firstChild != null){
		      		mateSysParaArray[list2.item(0).firstChild.nodeValue]=list2.item(1).firstChild.nodeValue
		  		}
		   	}
			setEnvionmentValue("mateSysPara",mateSysParaArray);
		}
		//alert(getCopyCode());
		init_mate_radix_point();
 		init_is_link_budg();
  }
  //结束 fanxiaojing
  if(getEnvionmentValue("_module")=='equi'){
  	var edit_model;
    var d = new Date();
    var equi_month = d.getMonth()+1;
    if(equi_month<10){
    	equi_month = "0"+equi_month;
    }
    var equi_year =d.getFullYear();
    var array=getDict('equip_init','<year>'+equi_year+'</year><month>'+equi_month+'</month>')
    var vXml= new ActiveXObject("Microsoft.XMLDOM");
    vXml.async = false;
    vXml.loadXML(array.xml);
    if(vXml.getElementsByTagName("para").length>0){
      edit_model=vXml.getElementsByTagName("para")[0].getAttributeNode("code").value
	  }
	  {
	  var pages=srcTree.getElementsByTagName("menu_node");
    if (edit_model==1){
      pages=srcTree.getElementsByTagName("menu_node");
    	for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
     	if(node=="期初建帐"){
      	srcTree.getElementsByTagName("menu_node")[i].parentNode.removeChild(pages[i])
      	break;
      }
    }
	}
	}
  }

  if(getEnvionmentValue("_module")=='acct'){
  	
  	var compCode = getCompCode() ;
  	var copyCode = getCopyCode();
  	var para_value;
  	var flag ;
		var hasEqui ;
		var p =  getValuePairBySql("acctGetInitPara","<comp>"+compCode+"</comp><copy>"+copyCode+"</copy>");
		var pValue = p[0].split(';');
		para_value = pValue[0];
		flag=pValue[1];
		hasEqui = pValue[2];
		
		if(hasEqui=="1"){//移除固定资产和无形资产菜单
			var pages = srcTree.selectNodes("/root/menu_node/menu_node[@name='固定资产']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="固定资产"){
	      	srcTree.selectNodes("/root/menu_node/menu_node[@name='固定资产']")[i].parentNode.removeChild(pages[i])
	      	break;
	      }
	    }			
			var pages1 = srcTree.selectNodes("/root/menu_node/menu_node[@name='无形资产']");
	  	for(var i=0;i<pages1.length;i++){
	    	var node =pages1[i].getAttributeNode("name").value;
	    	var ncode =pages1[i].getAttribute("code");
	     	if(node=="无形资产"){
	      	srcTree.selectNodes("/root/menu_node/menu_node[@name='无形资产']")[i].parentNode.removeChild(pages1[i])
	      	break;
	      }
	    }
			var pages2 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='固定资产月报表']");
	  	for(var i=0;i<pages2.length;i++){
	    	var node =pages2[i].getAttributeNode("name").value;
	    	var ncode =pages2[i].getAttribute("code");
	     	if(node=="固定资产月报表"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='固定资产月报表']")[i].parentNode.removeChild(pages2[i])
	      	break;
	      }
	    }

	    
	    			
			var pages3 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='固定资产账簿']");
	  	for(var i=0;i<pages3.length;i++){
	    	var node =pages3[i].getAttributeNode("name").value;
	    	var ncode =pages3[i].getAttribute("code");
	     	if(node=="固定资产账簿"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='固定资产账簿']")[i].parentNode.removeChild(pages3[i])
	      	break;
	      }
	    }

			var pages4 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='无形资产月报表']");
	  	for(var i=0;i<pages4.length;i++){
	    	var node =pages4[i].getAttributeNode("name").value;
	    	var ncode =pages4[i].getAttribute("code");
	     	if(node=="无形资产月报表"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='无形资产月报表']")[i].parentNode.removeChild(pages4[i])
	      	break;
	      }
	    }
	    			
			var pages5 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='无形资产账簿']");
	  	for(var i=0;i<pages5.length;i++){
	    	var node =pages5[i].getAttributeNode("name").value;
	    	var ncode =pages5[i].getAttribute("code");
	     	if(node=="无形资产账簿"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='无形资产账簿']")[i].parentNode.removeChild(pages5[i])
	      	break;
	      }
	    }
			
		}else{
			var pages = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='业务对账']");
			for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="业务对账"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='业务对账']")[i].parentNode.removeChild(pages[i])
	      	break;
	      }
	    }		
		}
		
		if (para_value == '往来部分核销'){
	  	var pages=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来集中核销']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="往来集中核销"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来集中核销']")[i].parentNode.removeChild(pages[i])
	      	var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='往来核销']");
	      	var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来部分核销']");

	      	if (flag==0)
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='往来核销']")[0].parentNode.removeChild(pages2[0])
	      	break;
	      }
	    }
	  }
	  if (para_value == '往来集中核销'){
	  	var pages_log=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='核销日志']");
	  	for(var i=0;i<pages_log.length;i++){
	    	var node =pages_log[i].getAttributeNode("name").value;
	    	var ncode =pages_log[i].getAttribute("code");
	      if(node=="核销日志" ){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='核销日志']")[i].parentNode.removeChild(pages_log[i])
	      	break;
	      }
	    }
	  	var pages=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来部分核销']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	      if(node=="往来部分核销" ){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来部分核销']")[i].parentNode.removeChild(pages[i])
	      	var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='往来核销']");
	      	var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='往来集中核销']");

	      	if (flag ==0){	      		
	      	 srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='往来核销']")[0].parentNode.removeChild(pages2[0])
	      	}
	      	break;
	      }
	    }
	  }
	  
  }
  if(getEnvionmentValue("_module")=='mate'){
  	var compCode = getCompCode() ;
  	var copyCode = getCopyCode();
  
  	window.xmlhttp.post("mateGetInitPara","<comp>"+compCode+"</comp><copy>"+copyCode+"</copy>","?isCheck=false");
		var doc = window.xmlhttp._object.responseXML;
		var arr = doc.getElementsByTagName("td");
		var para_value;
		if(arr.length==1){
			para_value = arr[0].firstChild.xml
		}else{
			para_value = 1;
		}
	  var pages=srcTree.selectNodes("/root/menu_node/menu_node[@name='应付款管理']");
  	for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
    	var ncode =pages[i].getAttribute("code");
     	if(node=="应付款管理" && ncode=="yfkgl" && para_value==2){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='应付款管理']")[i].parentNode.removeChild(pages[i])
      	break;
      }
      if(node=="应付款管理" && ncode=="yfkglbd" && para_value==1){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='应付款管理']")[i].parentNode.removeChild(pages[i])
      	break;
      }
    }
    window.xmlhttp.post("mateGetInitPara2","<comp>"+compCode+"</comp><copy>"+copyCode+"</copy>","?isCheck=false");
		var doc2 = window.xmlhttp._object.responseXML;
		var arr2 = doc2.getElementsByTagName("td");
		var para_value2;
		if(arr2.length==1){
			para_value2 = arr2[0].firstChild.xml
		}else{
			para_value2 = 0;
		}
	  var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='结存报表(按供应商)']");
	  for(var i=0;i<pages2.length;i++){
     	if(para_value2==0){
      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='结存报表(按供应商)']")[i].parentNode.removeChild(pages2[i])
      	break;
      }
    }
    window.xmlhttp.post("mateGetInitPara3","<comp>"+compCode+"</comp><copy>"+copyCode+"</copy>","?isCheck=false");
		var doc3 = window.xmlhttp._object.responseXML;
		var arr3 = doc3.getElementsByTagName("td");
		var para_value3;
		if(arr3.length==1){
			para_value3 = arr3[0].firstChild.xml
		}else{
			para_value3 = 0;
		}
	  var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node/menu_node[@name='按科室级次(零售)']");
	  for(var i=0;i<pages3.length;i++){
     	if(para_value3==0){
      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node/menu_node[@name='按科室级次(零售)']")[i].parentNode.removeChild(pages3[i])
      	break;
      }
    }
  }
  //资产与合同管理模块连用的情况下禁用自身的合同管理
  if(getEnvionmentValue("_module")=='equi' || getEnvionmentValue("_module")=='imma'){
	var compCode = getCompCode() ;
  	var copyCode = getCopyCode();
  	var moduleCode=getEnvionmentValue("_moduleNo");
  	
  	window.xmlhttp.post("pactCombined_moduleselect","<comp>"+compCode+"</comp><copy>"+copyCode+"</copy><mod_code>"+moduleCode+"</mod_code>","?isCheck=false");
		var doc = window.xmlhttp._object.responseXML;
		var arr = doc.getElementsByTagName("td");
		var is_combined;
		if(arr.length==1){
			is_combined = arr[0].firstChild.xml
		}else{
			is_combined = '0';
		}
	  var pages=srcTree.selectNodes("/root/menu_node/menu_node[@name='合同管理']");
  	for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
    	var ncode =pages[i].getAttribute("code");
     	if(node=="合同管理" && ncode=="htgl" && is_combined=='1'){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='合同管理']")[i].parentNode.removeChild(pages[i])
      	break;
      }
    }
	}
	
	initDwzMenu(srcTree);
	
 /* var xsltTree= new ActiveXObject("Microsoft.XMLDOM");
  xsltTree.async = false;
  xsltTree.load("base/xsl/menu.xsl");
  srcTree.loadXML(srcTree.transformNode(xsltTree));

  var selectNodes = srcTree.selectNodes("/root/*");
  if(selectNodes.length==0 ){
    alert("你没有权限访问此模块");
    window.close();
    //window.open('./login.html');
    window.open("base/themes/default/login.jsp");
    is_close_alert='0';
    return false;
  }*/
  //Modified BY ChenHaifeng 01/14/2013 BEGIN
  /*if(getCopyCode!=null){
  	window.xmlhttp.post("sys_copy_name_for_id","<comp>"+getCompCode()+"</comp><copy>"+getCopyCode()+"</copy>","?isCheck=false");
		var doc = window.xmlhttp._object.responseXML;
		var arr = doc.getElementsByTagName("td");
		var copy_name;
		if(arr.length==1){
			copy_name = arr[0].firstChild.xml
		}else{
			copy_name = '';
		}
		document.title=CONST_MODULE_NAMES[window.module]+" - "+copy_name
  }else{
  	document.title=CONST_MODULE_NAMES[window.module];
  }
  //Modified 01/14/2013 END
	//document.title=CONST_MODULE_NAMES[window.module]+" "+window.herpVersion;
	window.menu_xml = srcTree.xml;
	_menu_xml_setp++;
	mainMenuInitTimer=window.setInterval(function(){
		if(_menu_xml_setp<2)
			return;
		try{
			mainMainMenuTable.setMenuData(parent.menu_xml,"first");
			showCompName();
			window.clearInterval(mainMenuInitTimer);
		}catch(E){};
	},20);*/
  ///////////////////
  var moduleName =getModuleName(window.module);

  var year="";
  var sty=" style='cursor:hand;color:#FFFFFF;font-size:12px;' >";
  moduleName="<a  onClick='chooseModule()'"+sty+moduleName+"</a>";
  if(window._drugDept!=null)
    moduleName+="　<a onClick='chooseDrugHouse()'"+sty+window._drugDeptName+'</a>';

  if(window.module=="acct")
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseAcctYear()'"+sty+"<span id='llm'>"+getAcctYear()+"年度</span></a>";
  else if(window.module=="budg")
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseBudgYear()'"+sty+"<span id='llm'>"+getBudgYear()+"预算</span></a>";
  else
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseAcctYear()'"+sty+"<span id='llm'></span></a>";


return true;
	//document.getElementById("oper").innerText = getEmpName();  //HJJ comment out 现在3.0版本没有"oper"这个对象
}
var CONST_MODULE_NAMES={
//"sys":"SmartHERP 系统平台",
//"acct":"SmartHERP-Accounting 医院会计核算与财务管理系统",
//"rep":"SmartHERP-Reporting 报表管理系统",
//"mate":"SmartHERP-Inventory 医院物流管理系统",
//"equi":"SmartHERP-Fixed Asset 医院固定资产管理系统",
//"hr":"SmartHERP-Human Resource 医院人力资源管理系统",
//"drug":"SmartHERP-Pharmacy 医院药品管理系统",
//"ven":"SmartHERP-Supplier 医院供应商管理系统",
//"wage":"SmartHERP-Salary 医院工资管理系统",
//"budg":"SmartHERP-Budgeting 医院预算管理系统",
//"payctl":"SmartHERP-Expense Control 医院支出控制系统",
//"cbcs":"SmartHERP-CBCS 医院成本核算经济管理系统",
//"perf":"SmartHERP-Performance 医院绩效管理系统",
//"stat":"SmartHERP-Stat 医院综合统计系统",
//"uinfo":"SmartHERP-Unit Info 云康单位信息",
//"pote":"SmartHERP-Potecary 云康药房管理",
//"bonus":"SmartHERP-Bonus 云康奖金分配",
//"dev":"SmartHERP-Dev 医院医疗设备单机效能分析系统",
//"hisc":"SmartHERP-HIS 云康HIS收费"
"sys":"医院HRP业务基础平台",
"acct":"医院财务管理系统",
"rep":"报表管理系统",
"mate":"医院物流管理系统",
"equi":"医院固定资产管理系统",
"hr":"医院人力资源管理系统",
"drug":"医院药品管理系统",
"ven":"医院供应商管理系统",
"wage":"医院工资管理系统",
"budg":"医院预算管理系统",
"payctl":"医院资金支出控制系统",
"cbcs":"医院成本管理系统",
"perf":"医院绩效管理系统",
"stat":"医院综合统计系统",
"uinfo":"医院单位信息管理",
"pote":"医院药房管理",
"bonus":"医院奖金奖金分配管理系统",
"dev":"医院医疗设备单机效能分析系统",
"hisc":"医院HIS收费",
"imma":"医院无形资产管理系统",
"pact":"医院合同管理系统"
};

function getModuleName(moduleEn) {
	if(moduleEn=="sys")
		moduleName = "医院系统平台";
	else if(moduleEn=="acct")
		moduleName = "医院会计核算";
	else if(moduleEn=="rep")
		moduleName = "医院报表";
	else if(moduleEn=="mate")
		moduleName = "医院物流";
	else if(moduleEn=="equi")
		moduleName = "医院固定资产";
	else if(moduleEn=="hr")
		moduleName = "人力资源";
	else if(moduleEn=="drug")
		moduleName = "医院药品";
	else if(moduleEn=="ven")
		moduleName = "医院供应商";
	else if(moduleEn=="wage")
		moduleName = "医院工资";
	else if(moduleEn=="budg")
		moduleName = "医院预算";
	else if(moduleEn=="payctl")
		moduleName = "医院支出控制";
	else if(moduleEn=="cbcs")
		moduleName = "医院成本核算";
	else if(moduleEn=="perf")
		moduleName = "医院绩效";
	else if(moduleEn=="stat")
		moduleName = "医院综合统计";
	else if(moduleEn=="uinfo")
		moduleName = "医院单位信息";
	else if(moduleEn=="pote")
		moduleName = "医院药房管理";
	else if(moduleEn=="bonus")
		moduleName = "医院奖金分配";
	else if(moduleEn=="dev")
		moduleName = "医院单机核算";
	else if(moduleEn=="hisc")
		moduleName = "医院HIS收费";	
	else if(moduleEn=="imma")
		moduleName = "医院无形资产";
	else if(moduleEn=="pact")
		moduleName = "医院合同管理";
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
  window.showModalDialog("password.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:250px;dialogWidth:400px;")
}
function hrp_version(){
  window.showModalDialog("version.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:200px;dialogWidth:700px;")
}
function setEnv(){
  var rv = window.showModalDialog("setEnv.html", window, "status:0;resizable:yes;scroll:0;dialogHeight:300px;dialogWidth:350px;");

  if(rv){
  //	top.window.opener=null;
 // 	window.open('login.html');
 // 	top.close();
 		mainQuit.click();
  		return;
  }else{
  	//setEnv();
  }

 /* if("true" == rv){ 	
  	//重新加载菜单
  	try{
  	//	resetLoginDateInfo();
  	}catch(E){}*/
	//	start();
  	//当前情况为管理员登陆系统平台时，需要刷新当前页面
  	/*if("sys" == window.module && isAdmin() == 1){
  		if(_gMMMFLocation)
  			mainMainMainMainMainIFrame.document.location.href = _gMMMFLocation;
  	}
  }*/
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

//start();

//Modifier:Jack Date:2007-04-17
//Begin
/**
 *JavaScript : Map class
 *Map<_key:_value>
 */
function Map(){
	this.elements=new Array();

	this.size=function(){
		return this.elements.length;
	}
	
	this.put=function(_key,_value){
		this.elements.push({key:_key,value:_value});
	}
	
	this.remove=function(_key){
		var bln=false;
		try{   
			for (i=0;i<this.elements.length;i++){  
				if (this.elements[i].key==_key){
					this.elements.splice(i,1);
					return true;
				}
			}
		}catch(e){
			bln=false;    
		}
		return bln;
	}
	
	this.containsKey=function(_key){
		var bln=false;
		try{   
			for (i=0;i<this.elements.length;i++){  
				if (this.elements[i].key==_key){
					bln=true;
				}
			}
		}catch(e){
			bln=false;    
		}
		return bln;
	}
	
	this.get=function(_key){    
		try{   
			for (i=0;i<this.elements.length;i++){  
				if (this.elements[i].key==_key){
					return this.elements[i];
				}
			}
		}catch(e){
			return null;   
		}
	}
}

/**
 *初始化单机核算模块的系统参数
 */

function initDevParaData(){
	var devPara = new Object
	var fs=getDict("devSysPara","");
	var ps=fs.getElementsByTagName("para");
	var c,v;
	for(var i=0;i<ps.length;i++){
		c=ps[i].getAttribute("code");
		v=ps[i].getAttribute("value");
		devPara[c] = v;
	}
	setEnvionmentValue("devParaData",devPara);
}

/**
 *Initialize Performance Module Environment Data
 */
function initPerfParaData() {
  // Global Environment Data Contains
	setEnvionmentValue("_paraMap",new Map());
	
	if(getEnvionmentValue("_module") == 'perf'){
		// Params list
		var modCode = '13';
		var compCode = getCompCode() ;
		// Post request to Server-Side
		window.xmlhttp.post("initPerfEnv","<user><subfunction>initPerfParaData</subfunction><modCode>" + modCode + "</modCode><compCode>" + compCode + "</compCode></user>");
		// Receive Server-Side Data
		var paras = window.xmlhttp._object.responseText;
		if (paras.trim() == '') {
			//var _ErrorMsg = 'Data Load Error!';
			//alert(_ErrorMsg);
			//window.close();
			return false;
		}
		
		//Process Data
		var paraDatas = paras.split("\|");
		var paraCodes = paraDatas[0].split(",");
		var paraValues = paraDatas[1].split(",");

		for(var i = 0 ; i < paraCodes.length ; i++){
			getEnvionmentValue("_paraMap").put(paraCodes[i],paraValues[i]);
		}
	}
}

/**
 *Initialize Operater Current Stratagem  Data
 */
function initOprStratagem() {
	
	if(getEnvionmentValue("_module")== 'perf'){
		// Params list
		var compCode = getCompCode() ;
		var userId = getUserID();
		
		// Post request to Server-Side
		window.xmlhttp.post("confirmOprStratagem","<user><subfunction>ConfirmOperaterStratagem</subfunction><compCode>" + compCode + "</compCode><userId>" + userId + "</userId></user>");
		// Receive Server-Side Data
		var paras = window.xmlhttp._object.responseText;
		if (paras.trim() == '') {
			//var _ErrorMsg = 'Data Load Error!';
			//alert(_ErrorMsg);
			//window.close();
			setEnvionmentValue("_stratagemSeqNo","0");
			setEnvionmentValue("_stateFlag","0");
			setEnvionmentValue("_stratagemCurrentMonth","00")
			return false;
		}
		
		//Process Data
		var paraDatas = paras.split(",");
		
		window.top.window._stratagemSeqNo = paraDatas[0];
		window.top.window._stratagemYear = paraDatas[1];
		window.top.window._stratagemCurrentMonth = paraDatas[2];
		window.top.window._stateFlag = paraDatas[3];
		window.top.window._stratagemName = paraDatas[4];

		setEnvionmentValue("_stratagemSeqNo",paraDatas[0]);
		setEnvionmentValue("_stratagemYear",paraDatas[1]);
		setEnvionmentValue("_stratagemCurrentMonth",paraDatas[2]);
		setEnvionmentValue("_stateFlag",paraDatas[3]);
		setEnvionmentValue("_stratagemName",paraDatas[4]);
	}
}
function initbonusParaData(){
	var p=getValuePairBySql("bonus_get_perf_stratagem_sequence_no","")
	if(p!=null){
		setEnvionmentValue("_stratagemSeqNo",p[0]);
		setEnvionmentValue("_stateFlag",p[1]);
	}else{
		setEnvionmentValue("_stratagemSeqNo",'0');
		setEnvionmentValue("_stateFlag",' ');
	}
}

//add by b2zhangwei
function init_mate_radix_point(){
	var p=get_radix_point();//该方法在sub.js内
	if(p!=null){
		setEnvionmentValue("mate_radix_point",p);
	}else{
		setEnvionmentValue("mate_radix_point",new Array("00","00"));
	}
}
function init_is_link_budg(){
	var isLink=get_is_link_budg();//该方法在sub.js内
	setEnvionmentValue("isLink",isLink[0]);
	setEnvionmentValue("isControl",isLink[1]);
}

//End
//LZK ADD BEGIN
function initModulePrivateFun(w){
	for(var k in baseSystemFunMap)
			w[k]=baseSystemFunMap[k];
	for(var mk in CONST_MODULE_NAMES){
		if(eval("typeof("+mk+"ModulePrivateFunMap)")!="undefined"){
			var m=eval(mk+"ModulePrivateFunMap");
			for(var k in m){
				w[k]=m[k];
			}
		}
	}
}
//LZK ADD END
