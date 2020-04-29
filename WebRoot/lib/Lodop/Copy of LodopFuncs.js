/*************************lodop插件core*****************************************/

var CreatedOKLodop7766=null;

//====判断是否需要安装CLodop云打印服务器:====
function needCLodop(){
    try{
	var ua=navigator.userAgent;
	if (ua.match(/Windows\sPhone/i) !=null) return true;
	if (ua.match(/iPhone|iPod/i) != null) return true;
	if (ua.match(/Android/i) != null) return true;
	if (ua.match(/Edge\D?\d+/i) != null) return true;
	
	var verTrident=ua.match(/Trident\D?\d+/i);
	var verIE=ua.match(/MSIE\D?\d+/i);
	var verOPR=ua.match(/OPR\D?\d+/i);
	var verFF=ua.match(/Firefox\D?\d+/i);
	var x64=ua.match(/x64/i);
	if ((verTrident==null)&&(verIE==null)&&(x64!==null)) 
		return true; else
	if ( verFF !== null) {
		verFF = verFF[0].match(/\d+/);
		if ((verFF[0]>= 42)||(x64!==null)) return true;
	} else 
	if ( verOPR !== null) {
		verOPR = verOPR[0].match(/\d+/);
		if ( verOPR[0] >= 32 ) return true;
	} else 
	if ((verTrident==null)&&(verIE==null)) {
		var verChrome=ua.match(/Chrome\D?\d+/i);		
		if ( verChrome !== null ) {
			verChrome = verChrome[0].match(/\d+/);
			if (verChrome[0]>=42) return true;
		};
	};
        return false;
    } catch(err) {return true;};
};

//====页面引用CLodop云打印必须的JS文件：====
if (needCLodop()) {
	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
	var oscript = document.createElement("script");
	oscript.src ="http://localhost:8000/CLodopfuncs.js?priority=1";
	head.insertBefore( oscript,head.firstChild );

	//引用双端口(8000和18000）避免其中某个被占用：
	oscript = document.createElement("script");
	oscript.src ="http://localhost:18000/CLodopfuncs.js?priority=0";
	head.insertBefore( oscript,head.firstChild );
};

//====获取LODOP对象的主过程：====
function getLodop(oOBJECT,oEMBED){
    var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
    var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
    var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
    var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
    var strCLodopInstall="<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
    var strCLodopUpdate="<br><font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
    var LODOP;
    try{
        var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
        if (needCLodop()) {
            try{ LODOP=getCLodop();} catch(err) {};
	    if (!LODOP && document.readyState!=="complete") {alert("C-Lodop没准备好，请稍后再试！"); return;};
            if (!LODOP) {
		 if (isIE) document.write(strCLodopInstall); else
		 document.documentElement.innerHTML=strCLodopInstall+document.documentElement.innerHTML;
                 return;
            } else {

	         if (CLODOP.CVERSION<"2.1.0.2") { 
			if (isIE) document.write(strCLodopUpdate); else
			document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML;
		 };
		 if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
		 if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);	
	    };
        } else {
            var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
            //=====如果页面有Lodop就直接使用，没有则新建:==========
            if (oOBJECT!=undefined || oEMBED!=undefined) {
                if (isIE) LODOP=oOBJECT; else  LODOP=oEMBED;
            } else if (CreatedOKLodop7766==null){
                LODOP=document.createElement("object");
                LODOP.setAttribute("width",0);
                LODOP.setAttribute("height",0);
                LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");
                if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
                else LODOP.setAttribute("type","application/x-print-lodop");
                document.documentElement.appendChild(LODOP);
                CreatedOKLodop7766=LODOP;
             } else LODOP=CreatedOKLodop7766;
            //=====Lodop插件未安装时提示下载地址:==========
            if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
                 if (navigator.userAgent.indexOf('Chrome')>=0)
                     document.documentElement.innerHTML=strHtmChrome+document.documentElement.innerHTML;
                 if (navigator.userAgent.indexOf('Firefox')>=0)
                     document.documentElement.innerHTML=strHtmFireFox+document.documentElement.innerHTML;
                 if (is64IE) document.write(strHtm64_Install); else
                 if (isIE)   document.write(strHtmInstall);    else
                     document.documentElement.innerHTML=strHtmInstall+document.documentElement.innerHTML;
                 return LODOP;
            };
        };
        if (LODOP.VERSION<"6.2.1.7") {
            if (needCLodop())
            document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML; else
            if (is64IE) document.write(strHtm64_Update); else
            if (isIE) document.write(strHtmUpdate); else
            document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
            return LODOP;
        };
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===

        //===========================================================
        return LODOP;
    } catch(err) {alert("getLodop出错:"+err);};
};



/*************************lodop插件core*****************************************/






var scripts = document.getElementsByTagName( "script" );
var script = scripts[ scripts.length - 1 ];
var sUrl=script.hasAttribute ?script.src :script.getAttribute( "src", 4 );
sUrl = sUrl.replace(/^.*?\:\/\/[^\/]+/, "").replace(/[^\/]+$/, "");
var LODOP;
/* 普通查询结果集打印
 * 参数1 resultDivId(字符型，要打印div的ID)
 * 参数2 printTask(字符型，任务名称/窗口标题，例如：用户打印.开始打印)
 * 参数3 printTitle(字符型，打印标题，例如：用户列表)
 * 参数4 isPre(boolean型，是否打印预览) 
*/
function lodopPrinterTable(resultDivId,printTask,printTitle,isPre){
	 var printCount=0; 
	 LODOP=getLodop();
	 //LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	 LODOP.PRINT_INIT(printTask);
	 LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,printTask);
	 //LODOP.ADD_PRINT_TEXT(30,60,170,20,$("#maingrid").html());
	//LODOP.ADD_PRINT_HTML(10,10,"100%","100%",$("#userprint").html());
	//标题
	if(printTitle!=""){
		LODOP.ADD_PRINT_TEXT(10,300,151,30,printTitle+"\n");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		//标题下面的实线
		/*LODOP.ADD_PRINT_LINE(40,0,40,5000,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);	*/	
	}
		
	//打印数据
	var printStyle="<style>table{font-family: verdana,arial,sans-serif;font-size:12px;border-collapse:collapse;}";
	printStyle+="table,th,td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}</style>";
	LODOP.ADD_PRINT_TABLE(50,1,"100%","100%",printStyle+$("#"+resultDivId).html());
	//页眉页号
	LODOP.ADD_PRINT_HTM(1,680,150,30,"<span tdata='pageNO'>第##页</span>/<span tdata='pageCount'>共##页</span>");
	//LODOP.ADD_PRINT_TEXT(1,10,165,22,"第#页/共&页");
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",3);//右靠齐
	//页脚
	//LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.ADD_PRINT_TEXT("98%",40,200,22,"制表人："+parent.sessionJson.user_name);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);//左靠齐
	LODOP.SET_PRINT_STYLEA(0,"PageIndex","last");	//仅在尾页才输出的内容
	LODOP.ADD_PRINT_TEXT("98%",600,200,22,"制表日期："+LODOP.FORMAT("TIME:YYYY年MM月DD日","DATE"));
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",3);//右靠齐
	LODOP.SET_PRINT_STYLEA(0,"PageIndex","last");	//仅在尾页才输出的内容
	//LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1); 
	LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",false);//预览窗口禁止最小化，并始终最前
	LODOP.SET_SHOW_MODE('NP_NO_RESULT',true);
	//当前处理 全部用预览打印，后期确定购买注册后再使用直接打印
	isPre=true;
	if(isPre){
		printCount=LODOP.PREVIEW();//打印预览
		//printCount=LODOP.PRINT_SETUP();//打印维护
	}else{
		if(LODOP.PRINT()){//直接打印
			printCount=1;
		}else{
			printCount=-1;
		}
	}
	//alert(LODOP.PRINT_DESIGN());//打印设计,返回生成的程序代码，用来调试
	return printCount;
}

function lodopPrinterAccTable(resultDivId,printTask,printTitle,isPre,acc_year,obj){
	 var printCount=0; 
	 LODOP=getLodop();
	 //LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	 LODOP.PRINT_INIT(printTask);
	 LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,printTask);
	 //LODOP.ADD_PRINT_TEXT(30,60,170,20,$("#maingrid").html());
	//LODOP.ADD_PRINT_HTML(10,10,"100%","100%",$("#userprint").html());
	//标题
	if(printTitle!=""){
		LODOP.ADD_PRINT_TEXT(10,300,151,30,printTitle+"\n");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		//标题下面的实线
		/*LODOP.ADD_PRINT_LINE(40,0,40,5000,0,1);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);	*/	
	}
		
	//打印数据
	var printStyle="<style>table{font-family: verdana,arial,sans-serif;font-size:12px;border-collapse:collapse;}";
	printStyle+="table,th,td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}</style>";
	LODOP.ADD_PRINT_TABLE(50,1,"100%","100%",printStyle+$("#"+resultDivId).html());
	//页眉页号
	LODOP.ADD_PRINT_HTM(1,680,150,30,"<span tdata='pageNO'>第##页</span>/<span tdata='pageCount'>共##页</span>");
	//LODOP.ADD_PRINT_TEXT(1,10,165,22,"第#页/共&页");
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",3);//右靠齐
	//页脚
	//LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
	LODOP.ADD_PRINT_TEXT("3%",40,200,22,"会计期间："+acc_year.split(",")[0]+"-"+acc_year.split(",")[1]);
	LODOP.ADD_PRINT_TEXT("3%",220,200,40,"会计科目："+obj);
	LODOP.ADD_PRINT_TEXT("98%",40,200,22,"制表人："+parent.sessionJson.user_name);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);//左靠齐
	LODOP.SET_PRINT_STYLEA(0,"PageIndex","last");	//仅在尾页才输出的内容
	LODOP.ADD_PRINT_TEXT("98%",600,200,22,"制表日期："+LODOP.FORMAT("TIME:YYYY年MM月DD日","DATE"));
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);
	LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",3);//右靠齐
	LODOP.SET_PRINT_STYLEA(0,"PageIndex","last");	//仅在尾页才输出的内容
	//LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1); 
	LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",false);//预览窗口禁止最小化，并始终最前
	LODOP.SET_SHOW_MODE('NP_NO_RESULT',true);
	//当前处理 全部用预览打印，后期确定购买注册后再使用直接打印
	isPre=true;
	if(isPre){
		printCount=LODOP.PREVIEW();//打印预览
		//printCount=LODOP.PRINT_SETUP();//打印维护
	}else{
		if(LODOP.PRINT()){//直接打印
			printCount=1;
		}else{
			printCount=-1;
		}
	}
	//alert(LODOP.PRINT_DESIGN());//打印设计,返回生成的程序代码，用来调试
	return printCount;
}

/* 导出数据到excel
 *  参数1 resultDivId(字符型，要打印div的ID)
 *	参数2 printTask(字符型，任务名称，例如：用户导出Excel)
 *	参数3 fileName(字符型，文件名称，例如：新文件名.xls)
 *	参数4 isFastExport(boolean型，true、false，快速生成（无表格样式,数据量较大时或许用到)
 */
function lodopExportExcel(resultDivId,printTask,fileName,isFastExport){
	LODOP=getLodop();   
	LODOP.PRINT_INIT(printTask); 
	LODOP.ADD_PRINT_TABLE(100,20,500,80,$("#"+resultDivId).html()); 
	LODOP.SET_SAVE_MODE("Orientation",2); //Excel文件的页面设置：横向打印   1-纵向,2-横向;
	LODOP.SET_SAVE_MODE("PaperSize",9);  //Excel文件的页面设置：纸张大小   9-对应A4
	LODOP.SET_SAVE_MODE("Zoom",90);       //Excel文件的页面设置：缩放比例
	LODOP.SET_SAVE_MODE("CenterHorizontally",true);//Excel文件的页面设置：页面水平居中
	LODOP.SET_SAVE_MODE("CenterVertically",true); //Excel文件的页面设置：页面垂直居中
	LODOP.SET_SAVE_MODE("QUICK_SAVE",isFastExport);//快速生成（无表格样式,数据量较大时或许用到） 
	LODOP.SAVE_TO_FILE(fileName); 
}

/* 条码打印
 *  参数1 BarCodeValue(条码值)
 *  参数2 Width(条码的总宽度，计量单位px（1px=1/96英寸）)
 *	参数3 Height(条码的总高度（一维条码时包括文字高度）)
 *	参数4 BarCodeType(条码的类型（规制）名称 默认为128A)
 *	参数5 Top(条码的总高度（一维条码时包括文字高度）)
 *	参数6 Left(条码的总高度（一维条码时包括文字高度）)
 *  参数7 isPre(boolean型，是否打印预览)
 */
function lodopBarcodePrint(BarCodeValue,Width,Height,BarCodeType,Top,Left,isPre){
	if (BarCodeValue == undefined || BarCodeValue == "") {
		return false;
	}
	if (Width == undefined || Width == "") {
		Width = 400;
	}
	if (Height == undefined || Height == "") {
		Height = 200;
	}
	if (BarCodeType == undefined || BarCodeType == "") {
		BarCodeType = "128A";
	}
	if (Top == undefined || Top == "") {
		Top = 0;
	}
	if (Left == undefined || Left == "") {
		Left = 0;
	}
	if (isPre == undefined || isPre == "") {
		isPre= false;
	}
	LODOP=getLodop();   
	LODOP.PRINT_INIT("");
	LODOP.ADD_PRINT_BARCODE(Top,Left,Width,Height,BarCodeType,BarCodeValue);
	if(isPre){
		LODOP.PRINT();
	}else{
		LODOP.PREVIEW();
	}
	
}

//根据打印参数编码获取参数值
function getPrintParaValue(paraJson,paraCode){
	var paraValue="";
	$.each(paraJson, function (i, obj) {
		if(obj.para_code==paraCode){
			paraValue=obj.para_value;
			return;
		}
	});
	return paraValue;
}

/*
 * 主从表模板打印-单张单据打印
 * 说明：主从表后台一次性查询，一次性返回拼装好的HTML
 * urlPara参数说明：
 * urlPara{
 * key1:value,
 * key2:value,
 * }
 * 
 * printPara参数说明：
 * printPara{
 * print_para_code:'系统参数编码，是否按用户设置打印模板'
 * template_code:'打印模板编码'
 * }
 * 
 * title：打印窗口标题
 * isPreview参数说明：是否打印预览（true|false），默认true
 */
function templatePrint(url,urlPara,printPara,title,isPreview){

	var isJsonObj=typeof(urlPara) == "object" && Object.prototype.toString.call(urlPara).toLowerCase() == "[object object]" && !urlPara.length;
	isJsonObj=typeof(printPara) == "object" && Object.prototype.toString.call(printPara).toLowerCase() == "[object object]" && !printPara.length;
	
	if(!isJsonObj){
		$.ligerDialog.error("方法参数不正确（url=字符串，urlPara=json对象，printPara=json对象，title=字符串）");
		return;
	}
	if(printPara["print_para_code"]==undefined || printPara["template_code"]==undefined){
		$.ligerDialog.error("printPara参数不正确：应该包含key（print_para_code，template_code）");
		return;
	}
	
	$("#pageloading").show();
	var paraPrintJson=null;
	//查询打印参数
	printPara["flag"]=7;//查询flag大于7的打印参数
	ajaxJsonObjectByUrl("/CHD-HRP/hrp/acc/accvouch/superPrint/querySuperPrintParaByCode.do?isCheck=false",printPara,function (responseData){
		paraPrintJson=responseData.Rows;
	},false);
	
	if(paraPrintJson==null || paraPrintJson.length==0){
		$("#pageloading").hide();
		$.ligerDialog.error("打印参数为空，请到打印模板页面初始化打印参数！");
		return;
	}
	
	LODOP=getLodop();
	LODOP.PRINT_INIT(title);
	LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,title);
	
	//var pages=1;
	var topMM=0;//上边距
	var leftMM=0;//下边距
	
	var intOrient=0;
	var widthStr="0";
	var heightStr="0";
	var pgeName="";//根据宽度和高度自动建立一个自定义纸张（CreateCustomPage）
	//纸张大小mm(宽*高)
	var wh=getPrintParaValue(paraPrintJson,"006");
	if(wh!="" && wh.split("*").length==2){
		widthStr=parseFloat(wh.split("*")[0])*10;
		heightStr=parseFloat(wh.split("*")[1])*10;
	}
	//纸张属性
	pgeName=getPrintParaValue(paraPrintJson,"007");
	
	//纸张方向
	if(getPrintParaValue(paraPrintJson,"008")!=""){
		intOrient=getPrintParaValue(paraPrintJson,"008");
	}
	//上边距
	if(getPrintParaValue(paraPrintJson,"009")!=""){
		topMM=getPrintParaValue(paraPrintJson,"009");
	}
	//左边距
	if(getPrintParaValue(paraPrintJson,"010")!=""){
		leftMM=getPrintParaValue(paraPrintJson,"010");
	}
	LODOP.SET_PRINT_PAGESIZE(intOrient,widthStr,heightStr,pgeName);
	
	//查询主从表
	urlPara["print_user_id"]=paraPrintJson[0].user_id;
	urlPara["print_template_code"]=printPara["template_code"];
	ajaxTextByUrl(url,urlPara,function(responseDataPage){
		
		var $responseData=$(responseDataPage);
		
		//明细数据按分页符分页
		if($responseData.filter("div[name=page-break]").val()!=undefined){
			$.each($responseData.filter("div[name=page-break]"),function(i,obj){
				LODOP.NewPage();
				LODOP.ADD_PRINT_HTM(topMM,leftMM,"100%","100%",$(obj).html());
			});
		}
		
		//物理分页	
		/*if(responseData.indexOf("<html>")!=-1 && $(responseData).filter("input[name=pages]").val()!=undefined){
			var $responseData=$(responseData);
			//总页数
			if($responseData.filter("input[name=pages]").val()!=undefined){
				pages=$responseData.filter("input[name=pages]").val();
			}
			
		}	
		
		for(var i=1;i<=pages;i++){
			LODOP.NewPage();
			if(i==1){
				LODOP.ADD_PRINT_HTM(topMM,leftMM,"100%","100%",responseData);	
			}else{
				para["page"]=i;
				ajaxTextByUrl(url,para,function(responseDataPage){
					
					LODOP.ADD_PRINT_HTM(topMM,leftMM,"100%","100%",responseDataPage);
				},false);
			}
			
		}*/
		//LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前
		LODOP.SET_SHOW_MODE("NP_NO_RESULT",true);//设置NP插件无返回，这可避免chrome（谷歌）等np插件浏览器对弹窗超时误报崩溃
		if(isPreview==undefined)isPreview=true;
		
		if(isPreview){
			LODOP.PREVIEW();
		}else{
			LODOP.PRINT();
		}
		
		$("#pageloading").hide();
	});
}

/*
 * 主从表模板打印-明细表批量打印
 * 说明：根据主表循环查询，循环返回拼装好的HTML
 * urlPara参数说明：
 * urlPara{
 * key1:value,
 * key2:value
 * }
 * 
 * printPara参数说明：
 * printPara{
 * print_para_code:'系统参数编码，是否按用户设置打印模板',
 * template_code:'打印模板编码'
 * }
 * 
 * title：打印窗口标题
 * isPreview参数说明：是否打印预览（true|false），默认true
 */
function templatePrintBatch(url,urlPara,printPara,title,isPreview){
	var isJsonObj=typeof(urlPara) == "object" && Object.prototype.toString.call(urlPara).toLowerCase() == "[object object]" && !urlPara.length;
	isJsonObj=typeof(printPara) == "object" && Object.prototype.toString.call(printPara).toLowerCase() == "[object object]" && !printPara.length;
	
	if(!isJsonObj){
		$.ligerDialog.error("方法参数不正确（url=字符串，urlPara=json对象，printPara=json对象，title=字符串）");
		return;
	}
	if(printPara["print_para_code"]==undefined || printPara["template_code"]==undefined){
		$.ligerDialog.error("printPara参数不正确：应该包含key（print_para_code，template_code）");
		return;
	}
	
	//var printIndex = layer.load(1);
	$("#pageloading").show();
	var paraPrintJson=null;
	//查询打印参数
	printPara["flag"]=7;//查询flag大于7的打印参数
	ajaxJsonObjectByUrl("/CHD-HRP/hrp/acc/accvouch/superPrint/querySuperPrintParaByCode.do?isCheck=false",printPara,function (responseData){
		paraPrintJson=responseData.Rows;
	},false);
	
	if(paraPrintJson==null || paraPrintJson.length==0){
		//layer.close(printIndex);
		$("#pageloading").hide();
		$.ligerDialog.error("打印参数为空，请到打印模板页面初始化打印参数！");
		return;
	}
	
	LODOP=getLodop();
	LODOP.PRINT_INIT(title);
	LODOP.SET_PREVIEW_WINDOW(1,0,1,-1,-1,title);
	var topMM=0;//上边距
	var leftMM=0;//下边距
	
	var intOrient=0;
	var widthStr="0";
	var heightStr="0";
	var pgeName="";//根据宽度和高度自动建立一个自定义纸张（值可以是CreateCustomPage）
	//纸张大小mm(宽*高)
	var wh=getPrintParaValue(paraPrintJson,"006");
	if(wh!="" && wh.split("*").length==2){
		widthStr=parseFloat(wh.split("*")[0])*10;
		heightStr=parseFloat(wh.split("*")[1])*10;
	}
	//纸张属性
	pgeName=getPrintParaValue(paraPrintJson,"007");
	
	//纸张方向
	if(getPrintParaValue(paraPrintJson,"008")!=""){
		intOrient=getPrintParaValue(paraPrintJson,"008");
	}
	//上边距
	if(getPrintParaValue(paraPrintJson,"009")!=""){
		topMM=getPrintParaValue(paraPrintJson,"009");
	}
	//左边距
	if(getPrintParaValue(paraPrintJson,"010")!=""){
		leftMM=getPrintParaValue(paraPrintJson,"010");
	}
	LODOP.SET_PRINT_PAGESIZE(intOrient,widthStr,heightStr,pgeName);
	
	//拼装查询明细表的url，不需要授功能权限
	var detialUrl=url.substring(0,url.indexOf(".do"))+"_detail"+url.substring(url.indexOf(".do"),url.length);
	if(detialUrl.indexOf("isCheck")==-1){
		if(detialUrl.indexOf("?")==-1){
			detialUrl=detialUrl+"?isCheck=false";
		}else{
			detialUrl=detialUrl+"&isCheck=false";
		}
	}
	
	//查询主表
	ajaxJsonObjectByUrl(url,urlPara,function (responseData){
		
		//开始循环打印，以主表开始循环
		$.each(responseData.Rows,function(i,obj){
			
			obj["print_user_id"]=paraPrintJson[0].user_id;
			obj["print_template_code"]=printPara["template_code"];
			//套打参数，通过按钮控制，不走打印参数设置
			if(printPara["p_003"]!=undefined){
				obj["print_p_003"]=printPara["p_003"];
			}
			
			//查询明细表
			ajaxTextByUrl(detialUrl,obj,function(responseDataPage){
				var $responseData=$(responseDataPage);
				
				//明细数据按分页符分页
				if($responseData.filter("div[name=page-break]").val()!=undefined){
					$.each($responseData.filter("div[name=page-break]"),function(i,obj){
						LODOP.NewPage();
						LODOP.ADD_PRINT_HTM(topMM,leftMM,"100%","100%",$(obj).html());
					});
				}
				
			},false);
			
		});
		//LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前
		LODOP.SET_SHOW_MODE("NP_NO_RESULT",true);//设置NP插件无返回，这可避免chrome（谷歌）等np插件浏览器对弹窗超时误报崩溃
		//LODOP.SET_PRINT_MODE("CUSTOM_TASK_NAME","财务系统_凭证批量打印"+i);//为每个打印单独设置任务名
		if(isPreview==undefined)isPreview=true;
		
		if(isPreview){
			LODOP.PREVIEW();
		}else{
			LODOP.PRINT();
		}
		$("#pageloading").hide();
		//layer.close(printIndex);
	});
	
}