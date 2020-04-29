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
//��������
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
		alert("��û��������¼�����ѳ�ʱ,\n�������µ�¼!");
		systemExit();
		return false;
	}
  if (f=='4') {
	  alert('�����û�����ô�Ȩ�ޣ����������')
	  window.open('login.jsp');
	  window.close();
	  return false;
	}
  srcTree.loadXML(str);

  // Ԥ��ģ�飬���ڸ���Ԥ�㹦�����ÿ���Ԥ��˵�����ʾ
  if(window.module=='budg'){
  }
  //��������ģ��
  if(window.module=='dev'){
  	absModule = "dev";
	initDevParaData();
  }
  //������Դģ��
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
				alertMsg.warn("��������û��Ȩ��")
				return false;
		  }
		  var data = resXml.getElementsByTagName('tbody')[0];
		  var trs = data.getElementsByTagName("tr");
		  var sumNum=0;
		  for (var i=0; i<trs.length; i++){
		  	sumNum=sumNum+parseInt(trs[i].getElementsByTagName("td")[1].firstChild.nodeValue);
		  }
		  //��ѯ��Ա�����Ƿ�Ϊ0
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
  //��ʼ��С��λ��
  if(getEnvionmentValue("_module")=='mate'){
  	init_mate_radix_point();
 		init_is_link_budg();
  }
  
  //��ʼ��С��λ��
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
  //���� fanxiaojing
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
     	if(node=="�ڳ�����"){
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
		
		if(hasEqui=="1"){//�Ƴ��̶��ʲ��������ʲ��˵�
			var pages = srcTree.selectNodes("/root/menu_node/menu_node[@name='�̶��ʲ�']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="�̶��ʲ�"){
	      	srcTree.selectNodes("/root/menu_node/menu_node[@name='�̶��ʲ�']")[i].parentNode.removeChild(pages[i])
	      	break;
	      }
	    }			
			var pages1 = srcTree.selectNodes("/root/menu_node/menu_node[@name='�����ʲ�']");
	  	for(var i=0;i<pages1.length;i++){
	    	var node =pages1[i].getAttributeNode("name").value;
	    	var ncode =pages1[i].getAttribute("code");
	     	if(node=="�����ʲ�"){
	      	srcTree.selectNodes("/root/menu_node/menu_node[@name='�����ʲ�']")[i].parentNode.removeChild(pages1[i])
	      	break;
	      }
	    }
			var pages2 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�̶��ʲ��±���']");
	  	for(var i=0;i<pages2.length;i++){
	    	var node =pages2[i].getAttributeNode("name").value;
	    	var ncode =pages2[i].getAttribute("code");
	     	if(node=="�̶��ʲ��±���"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�̶��ʲ��±���']")[i].parentNode.removeChild(pages2[i])
	      	break;
	      }
	    }

	    
	    			
			var pages3 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�̶��ʲ��˲�']");
	  	for(var i=0;i<pages3.length;i++){
	    	var node =pages3[i].getAttributeNode("name").value;
	    	var ncode =pages3[i].getAttribute("code");
	     	if(node=="�̶��ʲ��˲�"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�̶��ʲ��˲�']")[i].parentNode.removeChild(pages3[i])
	      	break;
	      }
	    }

			var pages4 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�����ʲ��±���']");
	  	for(var i=0;i<pages4.length;i++){
	    	var node =pages4[i].getAttributeNode("name").value;
	    	var ncode =pages4[i].getAttribute("code");
	     	if(node=="�����ʲ��±���"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�����ʲ��±���']")[i].parentNode.removeChild(pages4[i])
	      	break;
	      }
	    }
	    			
			var pages5 = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�����ʲ��˲�']");
	  	for(var i=0;i<pages5.length;i++){
	    	var node =pages5[i].getAttributeNode("name").value;
	    	var ncode =pages5[i].getAttribute("code");
	     	if(node=="�����ʲ��˲�"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='�����ʲ��˲�']")[i].parentNode.removeChild(pages5[i])
	      	break;
	      }
	    }
			
		}else{
			var pages = srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='ҵ�����']");
			for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="ҵ�����"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='ҵ�����']")[i].parentNode.removeChild(pages[i])
	      	break;
	      }
	    }		
		}
		
		if (para_value == '�������ֺ���'){
	  	var pages=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������к���']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	     	if(node=="�������к���"){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������к���']")[i].parentNode.removeChild(pages[i])
	      	var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='��������']");
	      	var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������ֺ���']");

	      	if (flag==0)
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='��������']")[0].parentNode.removeChild(pages2[0])
	      	break;
	      }
	    }
	  }
	  if (para_value == '�������к���'){
	  	var pages_log=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='������־']");
	  	for(var i=0;i<pages_log.length;i++){
	    	var node =pages_log[i].getAttributeNode("name").value;
	    	var ncode =pages_log[i].getAttribute("code");
	      if(node=="������־" ){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='������־']")[i].parentNode.removeChild(pages_log[i])
	      	break;
	      }
	    }
	  	var pages=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������ֺ���']");
	  	for(var i=0;i<pages.length;i++){
	    	var node =pages[i].getAttributeNode("name").value;
	    	var ncode =pages[i].getAttribute("code");
	      if(node=="�������ֺ���" ){
	      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������ֺ���']")[i].parentNode.removeChild(pages[i])
	      	var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='��������']");
	      	var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='�������к���']");

	      	if (flag ==0){	      		
	      	 srcTree.selectNodes("/root/menu_node/menu_node/menu_node[@name='��������']")[0].parentNode.removeChild(pages2[0])
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
	  var pages=srcTree.selectNodes("/root/menu_node/menu_node[@name='Ӧ�������']");
  	for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
    	var ncode =pages[i].getAttribute("code");
     	if(node=="Ӧ�������" && ncode=="yfkgl" && para_value==2){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='Ӧ�������']")[i].parentNode.removeChild(pages[i])
      	break;
      }
      if(node=="Ӧ�������" && ncode=="yfkglbd" && para_value==1){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='Ӧ�������']")[i].parentNode.removeChild(pages[i])
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
	  var pages2=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='��汨��(����Ӧ��)']");
	  for(var i=0;i<pages2.length;i++){
     	if(para_value2==0){
      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node[@name='��汨��(����Ӧ��)']")[i].parentNode.removeChild(pages2[i])
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
	  var pages3=srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node/menu_node[@name='�����Ҽ���(����)']");
	  for(var i=0;i<pages3.length;i++){
     	if(para_value3==0){
      	srcTree.selectNodes("/root/menu_node/menu_node/menu_node/menu_node/menu_node[@name='�����Ҽ���(����)']")[i].parentNode.removeChild(pages3[i])
      	break;
      }
    }
  }
  //�ʲ����ͬ����ģ�����õ�����½�������ĺ�ͬ����
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
	  var pages=srcTree.selectNodes("/root/menu_node/menu_node[@name='��ͬ����']");
  	for(var i=0;i<pages.length;i++){
    	var node =pages[i].getAttributeNode("name").value;
    	var ncode =pages[i].getAttribute("code");
     	if(node=="��ͬ����" && ncode=="htgl" && is_combined=='1'){
      	srcTree.selectNodes("/root/menu_node/menu_node[@name='��ͬ����']")[i].parentNode.removeChild(pages[i])
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
    alert("��û��Ȩ�޷��ʴ�ģ��");
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
    moduleName+="��<a onClick='chooseDrugHouse()'"+sty+window._drugDeptName+'</a>';

  if(window.module=="acct")
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseAcctYear()'"+sty+"<span id='llm'>"+getAcctYear()+"���</span></a>";
  else if(window.module=="budg")
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseBudgYear()'"+sty+"<span id='llm'>"+getBudgYear()+"Ԥ��</span></a>";
  else
    moduleName+="&nbsp;&nbsp;&nbsp;&nbsp;<a onClick='chooseAcctYear()'"+sty+"<span id='llm'></span></a>";


return true;
	//document.getElementById("oper").innerText = getEmpName();  //HJJ comment out ����3.0�汾û��"oper"�������
}
var CONST_MODULE_NAMES={
//"sys":"SmartHERP ϵͳƽ̨",
//"acct":"SmartHERP-Accounting ҽԺ��ƺ�����������ϵͳ",
//"rep":"SmartHERP-Reporting �������ϵͳ",
//"mate":"SmartHERP-Inventory ҽԺ��������ϵͳ",
//"equi":"SmartHERP-Fixed Asset ҽԺ�̶��ʲ�����ϵͳ",
//"hr":"SmartHERP-Human Resource ҽԺ������Դ����ϵͳ",
//"drug":"SmartHERP-Pharmacy ҽԺҩƷ����ϵͳ",
//"ven":"SmartHERP-Supplier ҽԺ��Ӧ�̹���ϵͳ",
//"wage":"SmartHERP-Salary ҽԺ���ʹ���ϵͳ",
//"budg":"SmartHERP-Budgeting ҽԺԤ�����ϵͳ",
//"payctl":"SmartHERP-Expense Control ҽԺ֧������ϵͳ",
//"cbcs":"SmartHERP-CBCS ҽԺ�ɱ����㾭�ù���ϵͳ",
//"perf":"SmartHERP-Performance ҽԺ��Ч����ϵͳ",
//"stat":"SmartHERP-Stat ҽԺ�ۺ�ͳ��ϵͳ",
//"uinfo":"SmartHERP-Unit Info �ƿ���λ��Ϣ",
//"pote":"SmartHERP-Potecary �ƿ�ҩ������",
//"bonus":"SmartHERP-Bonus �ƿ��������",
//"dev":"SmartHERP-Dev ҽԺҽ���豸����Ч�ܷ���ϵͳ",
//"hisc":"SmartHERP-HIS �ƿ�HIS�շ�"
"sys":"ҽԺHRPҵ�����ƽ̨",
"acct":"ҽԺ�������ϵͳ",
"rep":"�������ϵͳ",
"mate":"ҽԺ��������ϵͳ",
"equi":"ҽԺ�̶��ʲ�����ϵͳ",
"hr":"ҽԺ������Դ����ϵͳ",
"drug":"ҽԺҩƷ����ϵͳ",
"ven":"ҽԺ��Ӧ�̹���ϵͳ",
"wage":"ҽԺ���ʹ���ϵͳ",
"budg":"ҽԺԤ�����ϵͳ",
"payctl":"ҽԺ�ʽ�֧������ϵͳ",
"cbcs":"ҽԺ�ɱ�����ϵͳ",
"perf":"ҽԺ��Ч����ϵͳ",
"stat":"ҽԺ�ۺ�ͳ��ϵͳ",
"uinfo":"ҽԺ��λ��Ϣ����",
"pote":"ҽԺҩ������",
"bonus":"ҽԺ���𽱽�������ϵͳ",
"dev":"ҽԺҽ���豸����Ч�ܷ���ϵͳ",
"hisc":"ҽԺHIS�շ�",
"imma":"ҽԺ�����ʲ�����ϵͳ",
"pact":"ҽԺ��ͬ����ϵͳ"
};

function getModuleName(moduleEn) {
	if(moduleEn=="sys")
		moduleName = "ҽԺϵͳƽ̨";
	else if(moduleEn=="acct")
		moduleName = "ҽԺ��ƺ���";
	else if(moduleEn=="rep")
		moduleName = "ҽԺ����";
	else if(moduleEn=="mate")
		moduleName = "ҽԺ����";
	else if(moduleEn=="equi")
		moduleName = "ҽԺ�̶��ʲ�";
	else if(moduleEn=="hr")
		moduleName = "������Դ";
	else if(moduleEn=="drug")
		moduleName = "ҽԺҩƷ";
	else if(moduleEn=="ven")
		moduleName = "ҽԺ��Ӧ��";
	else if(moduleEn=="wage")
		moduleName = "ҽԺ����";
	else if(moduleEn=="budg")
		moduleName = "ҽԺԤ��";
	else if(moduleEn=="payctl")
		moduleName = "ҽԺ֧������";
	else if(moduleEn=="cbcs")
		moduleName = "ҽԺ�ɱ�����";
	else if(moduleEn=="perf")
		moduleName = "ҽԺ��Ч";
	else if(moduleEn=="stat")
		moduleName = "ҽԺ�ۺ�ͳ��";
	else if(moduleEn=="uinfo")
		moduleName = "ҽԺ��λ��Ϣ";
	else if(moduleEn=="pote")
		moduleName = "ҽԺҩ������";
	else if(moduleEn=="bonus")
		moduleName = "ҽԺ�������";
	else if(moduleEn=="dev")
		moduleName = "ҽԺ��������";
	else if(moduleEn=="hisc")
		moduleName = "ҽԺHIS�շ�";	
	else if(moduleEn=="imma")
		moduleName = "ҽԺ�����ʲ�";
	else if(moduleEn=="pact")
		moduleName = "ҽԺ��ͬ����";
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
  	//���¼��ز˵�
  	try{
  	//	resetLoginDateInfo();
  	}catch(E){}*/
	//	start();
  	//��ǰ���Ϊ����Ա��½ϵͳƽ̨ʱ����Ҫˢ�µ�ǰҳ��
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
 *��ʼ����������ģ���ϵͳ����
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
	var p=get_radix_point();//�÷�����sub.js��
	if(p!=null){
		setEnvionmentValue("mate_radix_point",p);
	}else{
		setEnvionmentValue("mate_radix_point",new Array("00","00"));
	}
}
function init_is_link_budg(){
	var isLink=get_is_link_budg();//�÷�����sub.js��
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
