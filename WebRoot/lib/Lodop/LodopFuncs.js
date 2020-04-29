/*************************lodop插件core*****************************************/

var CreatedOKLodop7766 = null;

//====判断是否需要安装CLodop云打印服务器:====
function needCLodop() {
	try {
		var ua = navigator.userAgent;
		if (ua.match(/Windows\sPhone/i) != null) return true;
		if (ua.match(/iPhone|iPod/i) != null) return true;
		if (ua.match(/Android/i) != null) return true;
		if (ua.match(/Edge\D?\d+/i) != null) return true;

		var verTrident = ua.match(/Trident\D?\d+/i);
		var verIE = ua.match(/MSIE\D?\d+/i);
		var verOPR = ua.match(/OPR\D?\d+/i);
		var verFF = ua.match(/Firefox\D?\d+/i);
		var x64 = ua.match(/x64/i);
		if ((verTrident == null) && (verIE == null) && (x64 !== null))
			return true; else
			if (verFF !== null) {
				verFF = verFF[0].match(/\d+/);
				if ((verFF[0] >= 42) || (x64 !== null)) return true;
			} else
				if (verOPR !== null) {
					verOPR = verOPR[0].match(/\d+/);
					if (verOPR[0] >= 32) return true;
				} else
					if ((verTrident == null) && (verIE == null)) {
						var verChrome = ua.match(/Chrome\D?\d+/i);
						if (verChrome !== null) {
							verChrome = verChrome[0].match(/\d+/);
							if (verChrome[0] >= 42) return true;
						};
					};
		return false;
	} catch (err) { return true; };
};

//====页面引用CLodop云打印必须的JS文件：====
if (needCLodop()) {

	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;

	var oscript = document.createElement("script");
	oscript.src = "http://localhost:8000/CLodopfuncs.js?priority=1";
	head.insertBefore(oscript, head.firstChild);

	//引用双端口(8000和18000）避免其中某个被占用：
	/*	oscript = document.createElement("script");
		oscript.src = "http://localhost:18000/CLodopfuncs.js?priority=0";
		head.insertBefore(oscript, head.firstChild);
		}*/

};

//====获取LODOP对象的主过程：====
function getLodop(oOBJECT, oEMBED) {
	var lodopUrl = window.location.host + "/CHD-HRP/lib/Lodop/download";
	var strHtmInstall = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='http://" + lodopUrl + "/install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	var strHtmUpdate = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='http://" + lodopUrl + "/install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	var strHtm64_Install = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='http://" + lodopUrl + "/install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	var strHtm64_Update = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='http://" + lodopUrl + "/install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	var strHtmFireFox = "<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
	var strHtmChrome = "<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
	var strCLodopInstall = "<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='http://" + lodopUrl + "/CLodop_Setup_for_Win32NT.exe' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
	var strCLodopUpdate = "<br><font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='http://" + lodopUrl + "/CLodop_Setup_for_Win32NT.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";

	var LODOP;
	try {
		var isIE = (navigator.userAgent.indexOf('MSIE') >= 0) || (navigator.userAgent.indexOf('Trident') >= 0);
		if (needCLodop()) {
			try { LODOP = getCLodop(); } catch (err) { };
			if (!LODOP && document.readyState !== "complete") { alert("C-Lodop没准备好，请稍后再试！"); return; };
			if (!LODOP) {
				if (isIE) document.write(strCLodopInstall); else
					document.documentElement.innerHTML = strCLodopInstall + document.documentElement.innerHTML;
				return;
			} else {

				if (CLODOP.CVERSION < "2.1.0.2") {
					if (isIE) document.write(strCLodopUpdate); else
						document.documentElement.innerHTML = strCLodopUpdate + document.documentElement.innerHTML;
				};
				if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
				if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);
			};
		} else {
			var is64IE = isIE && (navigator.userAgent.indexOf('x64') >= 0);
			//=====如果页面有Lodop就直接使用，没有则新建:==========
			if (oOBJECT != undefined || oEMBED != undefined) {
				if (isIE) LODOP = oOBJECT; else LODOP = oEMBED;
			} else if (CreatedOKLodop7766 == null) {
				LODOP = document.createElement("object");
				LODOP.setAttribute("width", 0);
				LODOP.setAttribute("height", 0);
				LODOP.setAttribute("style", "position:absolute;left:0px;top:-100px;width:0px;height:0px;");
				if (isIE) LODOP.setAttribute("classid", "clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
				else LODOP.setAttribute("type", "application/x-print-lodop");
				document.documentElement.appendChild(LODOP);
				CreatedOKLodop7766 = LODOP;
			} else LODOP = CreatedOKLodop7766;
			//=====Lodop插件未安装时提示下载地址:==========
			if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
				if (navigator.userAgent.indexOf('Chrome') >= 0)
					document.documentElement.innerHTML = strHtmChrome + document.documentElement.innerHTML;
				if (navigator.userAgent.indexOf('Firefox') >= 0)
					document.documentElement.innerHTML = strHtmFireFox + document.documentElement.innerHTML;
				if (is64IE) document.write(strHtm64_Install); else
					if (isIE) document.write(strHtmInstall); else
						document.documentElement.innerHTML = strHtmInstall + document.documentElement.innerHTML;
				return LODOP;
			};
		};
		if (LODOP.VERSION < "6.2.1.7") {
			if (needCLodop())
				document.documentElement.innerHTML = strCLodopUpdate + document.documentElement.innerHTML; else
				if (is64IE) document.write(strHtm64_Update); else
					if (isIE) document.write(strHtmUpdate); else
						document.documentElement.innerHTML = strHtmUpdate + document.documentElement.innerHTML;
			return LODOP;
		};
		//===如下空白位置适合调用统一功能(如注册语句、语言选择等):===

		//===========================================================
		return LODOP;
	} catch (err) { alert("getLodop出错:" + err); };
};



/*************************lodop插件core*****************************************/



function _getFilePath(fileName, callBack) {
	LODOP = getLodop();
	var strResult = LODOP.GET_PRINT_INIFFNAME(fileName);
	if (LODOP.CVERSION) {
		CLODOP.On_Return = function (TaskID, Value) {
			if (typeof callBack === "function") {
				Value = Value.replace("PrintSetups_", "").replace("ini", "json");
				callBack(Value);
			}
		};
	} else {
		if (typeof callBack === "function") {
			callBack(Value);
		}
	}
}

/**
 * 读取文件
 * @param {string} fileName
 * @param {function} callBack 
 */
function lodopReadFile(fileName, callBack) {
	LODOP = getLodop();
	if (LODOP) {
		_getFilePath(fileName, function (filePath) {
			var strResult = LODOP.GET_FILE_TEXT(filePath);
			if (LODOP.CVERSION) {
				CLODOP.On_Return = function (TaskID, Value) {
					if (typeof callBack === "function") {
						callBack(JSON.parse(Value));
					}
				};
			} else {
				if (typeof callBack === "function") {
					callBack(JSON.parse(strResult));
				}
			}
		});
	}
}

/**
 * 写入文件
 * @param {string} fileName 
 * @param {string} content 
 * @param {function} callBack 
 */
function lodopWriteFile(fileName, content, callBack) {
	LODOP = getLodop();
	if (LODOP) {
		var stringContent = JSON.stringify(content);
		_getFilePath(fileName, function (filePath) {
			var strResult = LODOP.WRITE_FILE_TEXT(0, filePath, stringContent);
			if (LODOP.CVERSION) {
				CLODOP.On_Return = function (TaskID, Value) {
					if (typeof callBack === "function") {
						callBack(Value);
					}
				}
			} else {
				if (typeof callBack === "function") {
					callBack(strResult);
				}
			}
		});
	}
}



/**
 * 表格打印   
 * param.data  		数据源　　  	object型   数据库直接查询
 * param.column 	表头源　　  	object型   可通过grid.getColumns(1)获取
 * param.title		标题　　　	string型 　
 * param.head		查询条件　	string型   html字符串
 * param.foot	    尾部信息　	object型   包含制表人和打印日期
 * param.fn			渲染函数　	object型   根据列名 格式化内容函数
 * param.type 		打印方式	 number型   1：打印设计，2：打印维护，3：打印预览，4：直接打印
 * param.taskName	任务名称	 string型   任务名称	
 * param.group		分组　　　   object型   分组信息 包含name和display
 * param.percent	百分比　　   String型		Full-Width|Full-Height|Full-Page|Auto-Width|Auto-Height|Width:200%|Height:200%|Width:200%;Height:200%|55%	
 * 实例
 * lodopTable({
		column:grid.getColumns(1),
		data:grid.getData(),
		title:"三栏明细账",
		type:1,
		head:"<tr><th>查询条件</th></tr>",
		module:"",
		foot:{
			user:parent.sessionJson.user_name,
			date:true
		},
		fn:{
			debit:function(value){//贷方
				if(value==0){
					return "零";
				}
				return value;
			}
		}
	});
 * @param {object} param
 */
function lodopTable(param) {

	var data = param.data;
	var title = param.title;
	var column = param.column;
	var fn = param.fn || {};
	var foot = param.foot;
	var head = param.head;
	var type = param.type;
	var taskName = param.taskName || "";
	var group = param.group;
	var user;
	var date;
	var percent = param.percent;

	if (foot && foot.user) {
		user = foot.user;
	}
	if (foot && foot.date) {
		date = foot.date;
	}


	var LODOP = getLodop();//创建lodop对象

	if (!LODOP) {
		return;
	}

	if (taskName) {
		LODOP.PRINT_INIT(taskName);
	} else {
		alert("任务名称(taskName)必填");
		return;
	}

	var tableHTML = _createTable2(param);


	var printArray = [];

	//装载style
	printArray.push("<style>table{border:0;border-collapse:collapse;font-size:12px;} table tr{height:26px;} table td,table th{border:1px solid black;padding-top:3px;padding-bottom:3px} table.head td,table.head th{border:none}</style>");
	//装载table
	printArray.push(tableHTML);

	//加载打印列表
	LODOP.ADD_PRINT_TABLE(26, 20, "100%", "91%", printArray.join(""));
	//页眉页号
	LODOP.ADD_PRINT_HTM(1, "85%", 150, 30, "<span tdata='pageNO'>第##页</span>/<span tdata='pageCount'>共##页</span>");
	LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);//每页都输出
	if (user) {
		LODOP.ADD_PRINT_TEXT("97%", 60, 200, 22, "制表人：" + user);
		LODOP.SET_PRINT_STYLEA(0, "Horient", 1);//设定打印项在纸张范围内的水平方向的位置锁定方式
		LODOP.SET_PRINT_STYLEA(0, "Vorient", 1);//设定打印项在纸张范围内的垂直方向的位置锁定方式
		LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);//左靠齐
		LODOP.SET_PRINT_STYLEA(0, "PageIndex", "last");	//仅在尾页才输出的内容
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
	}

	if (date) {
		LODOP.ADD_PRINT_TEXT("97%", 550, 220, 22, "制表日期：" + LODOP.FORMAT("TIME:YYYY年MM月DD日", "DATE"));
		LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
		LODOP.SET_PRINT_STYLEA(0, "Vorient", 1);
		LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);//右靠齐
		LODOP.SET_PRINT_STYLEA(0, "PageIndex", "last");	//仅在尾页才输出的内容 
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
	}

	if (!percent) {
		percent = "Auto-Width";//整宽不变形
		//LODOP.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);//内容超出纸宽时对应缩小
		//LODOP.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW",true);//内容超出纸高时对应缩小,只适应于单页打印
	}
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", percent);

	//1：打印设计，2：打印维护，3：打印预览，4：直接打印
	switch (type) {
		case 1:
			LODOP.PRINT_DESIGN();
			break;
		case 2:
			LODOP.PRINT_SETUP();
			break;
		case 3:
			previewStyle();
			LODOP.PREVIEW();
			break;
		case 4:
			LODOP.PRINT();
			break;
		default:
			previewStyle();
			LODOP.PREVIEW();
			break;
	}

	//设置打印预览窗口样式
	function previewStyle() {
		//LODOP.SET_PRINT_PAGESIZE(2,0,0,"");//设定纸张大小
		LODOP.SET_PREVIEW_WINDOW(2, 0, 0, 0, 0, "");//设置预览窗口全屏
		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED", 1);//横向时的正向显示
	}
}

/**
 * 导出数据到Excel
 * @param {object} param 属性包含:
 * param.data  		数据源　　  	object型   数据库直接查询
 * param.column 	表头源　　  	object型   可通过grid.getColumns(1)获取
 * param.fn			渲染函数　	object型   根据列名 格式化内容函数
 * param.group		分组　　　   object型   分组信息 包含name和display
 * param.isFastExport:	boolean型， true、false，快速生成（无表格样式,数据量较大时或许用到；
 */
function lodopExportExcel(param) {
	var isFastExport = param.isFastExport === undefined ? true : param.isFastExport;
	var printArray = [];
	var LODOP = getLodop();

	if (!LODOP) {
		return;
	}

	LODOP.PRINT_INIT("导出Excel");

	//装载style
	printArray.push("<style>table{border:0;border-collapse:collapse} table td,table th{border:1px solid black;} table.head td,table.head th{border:none}</style>");
	//装载table
	var tableHTML = _createTable2(param);

	printArray.push(tableHTML);

	LODOP.ADD_PRINT_TABLE(100, 20, 500, 80, printArray.join(""));
	LODOP.SET_SAVE_MODE("Orientation", 2); 				//Excel文件的页面设置：横向打印   1-纵向,2-横向;
	LODOP.SET_SAVE_MODE("PaperSize", 9);  				//Excel文件的页面设置：纸张大小   9-对应A4
	LODOP.SET_SAVE_MODE("Zoom", 90);       				//Excel文件的页面设置：缩放比例
	LODOP.SET_SAVE_MODE("CenterHorizontally", true);	//Excel文件的页面设置：页面水平居中
	LODOP.SET_SAVE_MODE("CenterVertically", true); 		//Excel文件的页面设置：页面垂直居中
	LODOP.SET_SAVE_MODE("QUICK_SAVE", isFastExport);	//快速生成（无表格样式,数据量较大时或许用到） 
	LODOP.SAVE_TO_FILE("新建文件名.xls");
}

function lodopBarCode(arr) {
	//条码样式
	var style = "<style>.main{text-align:center;font-size:11px;font-weight:bold} .main:after{clear:both;display:block;content:'.';font-size:0;visibility: hidden;} .main>div{clear:both;} .barcode { float:left; clear:both;padding-left:6px;padding-top:4px; height:0.5in;  } .right { float:right;} .barcode + * { clear:both; } .barcode div { float:left; height: 0.35in;  } .barcode .bar1 { border-left:1px solid black; } .barcode .bar2 { border-left:2px solid black; } .barcode .bar3 { border-left:3px solid black; } .barcode .bar4 { border-left:4px solid black; } .barcode .space0 { margin-right:0 } .barcode .space1 { margin-right:1px } .barcode .space2 { margin-right:2px } .barcode .space3 { margin-right:3px } .barcode .space4 { margin-right:4px } .barcode label { clear:both; display:block; text-align:center; font: 0.125in/100% helvetica;font-weight: bolder;  }  .barcode2 { float:left; clear:both; padding: 0 10px; height:1in;  } .barcode2 + * { clear:both; } .barcode2 div { float:left; height: 0.7in;  } .barcode2 .bar1 { border-left:2px solid black; } .barcode2 .bar2 { border-left:4px solid black; } .barcode2 .bar3 { border-left:6px solid black; } .barcode2 .bar4 { border-left:8px solid black; } .barcode2 .space0 { margin-right:0 } .barcode2 .space1 { margin-right:2px } .barcode2 .space2 { margin-right:4px } .barcode2 .space3 { margin-right:6px } .barcode2 .space4 { margin-right:8px } .barcode2 label { clear:both; display:block; text-align:center; font: 0.250in/100% helvetica; } </style>"
	var LODOP = getLodop();
	LODOP.PRINT_INIT("条码打印");
	LODOP.SET_PRINT_PAGESIZE(1, "5cm", "3cm", "CreateCustomPage");
	for (var i = 0; i < arr.length; i++) {
		var data = arr[i];
		var barcode = data.bar_code;
		var other1 = data.other1;
		var other2 = data.other2;
		var other3 = data.other3;
		var other4 = data.other4;
		var $mainDiv = $("<div class='main'></div>");
		var $barCodeDiv = $("<div class='barcode'></div>").append(createBarcode(barcode, 'B'));
		var $other1Div = $("<div style='clear:both'><div>").html(other1.name + ":" + other1.value);
		var $other2Div = $("<div style='clear:both'><div>").html(other2.name + ":" + other2.value);
		var $other3Div = $("<div style='clear:both'><div>").html(other3.name + ":" + other3.value);
		var $other4Div = other4 && other4.name ? $("<div style='clear:both'><div>").html(other4.name + ":" + other4.value) : "";
		$mainDiv.append($barCodeDiv)
			.append($other1Div)
			.append($other2Div)
			.append($other3Div)
			.append($other4Div);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", style + $mainDiv[0].outerHTML);
	}
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", "Auto-Width");
	LODOP.PREVIEW();
	// LODOP.PRINT_DESIGN();
}

function newLodopBarCode(arr) {
	//条码样式
	var style = "<style>.main-barcode{padding:10px;} .main-barcode:after{clear:both;display:block;content:'.';font-size:0;visibility: hidden;} .barcode { float:left; clear:both;padding-left:6px;padding-top:4px; height:0.5in;  } .right { float:right;} .barcode + * { clear:both; } .barcode div { float:left; height: 0.35in;  } .barcode .bar1 { border-left:1px solid black; } .barcode .bar2 { border-left:2px solid black; } .barcode .bar3 { border-left:3px solid black; } .barcode .bar4 { border-left:4px solid black; } .barcode .space0 { margin-right:0 } .barcode .space1 { margin-right:1px } .barcode .space2 { margin-right:2px } .barcode .space3 { margin-right:3px } .barcode .space4 { margin-right:4px } .barcode label { clear:both; display:block; text-align:center; font: 0.125in/100% helvetica;font-weight: bolder;  }  .barcode2 { float:left; clear:both; padding: 0 10px; height:1in;  } .barcode2 + * { clear:both; } .barcode2 div { float:left; height: 0.7in;  } .barcode2 .bar1 { border-left:2px solid black; } .barcode2 .bar2 { border-left:4px solid black; } .barcode2 .bar3 { border-left:6px solid black; } .barcode2 .bar4 { border-left:8px solid black; } .barcode2 .space0 { margin-right:0 } .barcode2 .space1 { margin-right:2px } .barcode2 .space2 { margin-right:4px } .barcode2 .space3 { margin-right:6px } .barcode2 .space4 { margin-right:8px } .barcode2 label { clear:both; display:block; text-align:center; font: 0.250in/100% helvetica; }.title{text-align: center;}.content table{width:100%;}.content table tr td.barcode-name{width:55px;font-size:13px}.info table{border:0;border-collapse:collapse;width:90%} .info table tr{max-height:17px;padding:1px}.info table tr td{font-size:12px;border:1px solid #000;}.info table tr td.info-name{width:70px}.info table tr td.info-content{} </style>"
	var LODOP = getLodop();
	LODOP.PRINT_INIT("条码打印");
	LODOP.SET_PRINT_PAGESIZE(1, "7.7cm", "5cm", "CreateCustomPage");

	for (var i = 0; i < arr.length; i++) {
		var data = arr[i];
		var barcode = data.bar_code;
		var $mainDiv = $("<div class='main-barcode'></div>");
		var $titleDiv = $("<div class='title'>").html(data.hospital);
		var $contentDiv = $("<div class='content'>").html("<table>"
			+ "<tr>"
			+ "<td class='barcode-name'>院内条码 </td>"
			+ "<td class='barcode-content'>"
			+ "<div class='barcode'>" + createBarcode(barcode, 'B') + "</div>"
			+ "</td>"
			+ "</tr>"
			+ "</table>");
		var $infoDiv = $("<div class='info'>").html("<table>" 
			+ "<tr>"
			+ "<td class='info-name' align='right'>材料名称： </td>"
			+ "<td class='info-content'>" + data.info.mat_name + " </td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class='info-name' align='right'>规格型号： </td>"
			+ "<td class='info-content'>" + data.info.spec_model + " </td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class='info-name' align='right'>批号： </td>"
			+ "<td class='info-content'>" + data.info.batch + " </td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class='info-name' align='right'>效期： </td>"
			+ "<td class='info-content'>" + data.info.date + " </td>"
			+ "</tr>"
			+ "</table>");
		$mainDiv.append($titleDiv)
			.append($contentDiv)
			.append($infoDiv);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", style + $mainDiv[0].outerHTML);
	}
	//LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", "Auto-Width");
	LODOP.PREVIEW();
	// LODOP.PRINT_DESIGN();
}

function cardLodopBarCode(arr) {
	
	var style = "<style>.main-barcode{padding:10px;} .main-barcode:after{clear:both;display:block;content:'.';font-size:0;visibility: hidden;} .barcode { float:left; clear:both;padding-left:6px;padding-top:4px; height:0.5in;  } .right { float:right;} .barcode + * { clear:both; } .barcode div { float:left; height: 0.35in;  } .barcode .bar1 { border-left:1px solid black; } .barcode .bar2 { border-left:2px solid black; } .barcode .bar3 { border-left:3px solid black; } .barcode .bar4 { border-left:4px solid black; } .barcode .space0 { margin-right:0 } .barcode .space1 { margin-right:1px } .barcode .space2 { margin-right:2px } .barcode .space3 { margin-right:3px } .barcode .space4 { margin-right:4px } .barcode label { clear:both; display:none; text-align:center; font: 0.125in/100% helvetica;font-weight: bolder;  }  .barcode2 { float:left; clear:both; padding: 0 10px; height:1in;  } .barcode2 + * { clear:both; } .barcode2 div { float:left; height: 0.7in;  } .barcode2 .bar1 { border-left:2px solid black; } .barcode2 .bar2 { border-left:4px solid black; } .barcode2 .bar3 { border-left:6px solid black; } .barcode2 .bar4 { border-left:8px solid black; } .barcode2 .space0 { margin-right:0 } .barcode2 .space1 { margin-right:2px } .barcode2 .space2 { margin-right:4px } .barcode2 .space3 { margin-right:6px } .barcode2 .space4 { margin-right:8px } .barcode2 label { clear:both; display:block; text-align:center; font: 0.250in/100% helvetica; }.title{text-align: center;}.content table{width:100%;}.content table tr td.barcode-name{width:55px;font-size:13px}.info table{margin-left:15px;border:0;border-collapse:collapse;width:92%} .info table tr{max-height:17px;padding:1px}.info table tr td{font-size:12px;border:1px solid #000;}.info table tr td.info-name{width:60px}.info table tr td.info-content{} </style>";
	var LODOP = getLodop();
	LODOP.PRINT_INIT("卡片条码打印");
	LODOP.SET_PRINT_PAGESIZE(1, "6.5cm", "4cm", "CreateCustomPage");
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", "Auto-Width");
	for(var i = 0; i < arr.length; i++){
		var data = arr[i];
		//console.log(data);
		var $table = $("<div class='info' style='margin:0 auto'></div>").html("<table>"
				+ "<tr>"
				+ "  <td colspan='2' align='center'>"+data.title+"</td>"
				+ "</tr>"
				+ "<tr >"
				+ "  <td class='info-name' rowspan='2'>&nbsp;<img style='height:52px' src='"+data.img_path+"'/>&nbsp;</td>"
				+ "  <td class='info-content' >"+data.ass_card_no+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td class='info-content'>使用部门："+data.share_dept+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "  <td class='info-name'>资产名称</td>"
				+ "  <td class='info-content'>"+data.ass_name+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "  <td class='info-name'>规格型号</td>"
				+ "  <td class='info-content'>"+data.ass_spec+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td class='info-name'>入库日期</td>"
				+ "	<td class='info-content'>"+data.in_date+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "	<td class='info-name'>序列号</td>"
				+ "	<td class='info-content'>"+data.ass_seq_no+"</td>"
				+ "</tr>"
				+ "</table>");
		
		//console.log(style + $table[0].outerHTML);
		LODOP.NewPage();
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%", style + $table[0].outerHTML);
	}
	
	LODOP.PREVIEW();
	//LODOP.PRINT_DESIGN();
}

/**
 * 
 * @param {object} param 
 * param.spread			spread
 * param.taskName		任务名称
 * param.foot			user:制表人 ；date日期
 * param.percent		百分比　　 值: Full-Width|Full-Height|Full-Page|Auto-Width|Auto-Height|Width:200%|Height:200%|Width:200%;Height:200%|55%	
 */
function lodopSpread(param) {
	var spread = param.spread;
	var sheet;
	for (var name in spread.sheets) {
		if (spread.sheets.hasOwnProperty(name)) {
			sheet = spread.sheets[name];
			break;
		}
	}
	var namedStyles = spread.namedStyles;
	var taskName = param.taskName;
	var user, date;
	var foot = param.foot;
	var percent = param.percent;
	var type = param.type;
	var printNum = param.pageCount === undefined ? 1 : param.pageCount;
	var printHead = param.head === undefined ? true : param.head;

	var tableWidth = 0;
	if (foot && foot.user) {
		user = foot.user;
	}
	if (foot && foot.date) {
		date = foot.date;
	}

	var LODOP = getLodop();

	if (!LODOP) {
		return;
	}

	if (taskName) {
		LODOP.PRINT_INIT(taskName);
	} else {
		alert("任务名称(taskName)必填");
		return;
	}

	//=================优化前 转换html数据=================
	// var $table = createTable(sheet, namedStyles);
	// var style = "<style>table {border-collapse: collapse;}table td,table th {border: 0px solid #000000;word-break:break-all;}</style>";
	// //计算合并列宽
	// $table.find("td[colspan]").each(function (i, v) {
	// 	var number = $(this).attr("colspan");
	// 	var width = $(this).width();
	// 	for (var i = 0; i < number - 1; i++) {
	// 		var $element = $(this).next().eq(0);
	// 		width += $element.width();
	// 		$element.remove();
	// 	}
	// 	$(this).width(width);
	// });
	// var tablePages = newPage($table, sheet.rows);
	//=================优化前 转换html数据=================

	//=================优化后 转换html数据=================
	var tablePages = createTable2(sheet, namedStyles);
	var style = "<style>table {border-collapse: collapse;}table td,table th {border: 0px solid #000000;word-break:break-all; word-wrap:break-word;white-space:nowrap;overflow:hidden} .pageBreak{page-break-after:always;}</style>";
	//=================优化后 转换html数据=================
	var printHtml = "";
	for (var page = 0; page < tablePages.length; page++) {
		printHtml += tablePages[page] + "<div style='height:45px'></div>";
		//printHtml += tablePages[page][0].outerHTML +"<div style='height:45px'></div>";
		console.log(style + printHtml);
		if (tablePages[page + 1] === undefined || (page + 1) % printNum === 0) {
			// console.log(style + printHtml);
			LODOP.ADD_PRINT_HTML(26, 20, "100%", "91%", style + printHtml);
			LODOP.NewPage();
			printHtml = "";
		}
	}
	//LODOP.ADD_PRINT_TABLE(26, 20, "100%", "91%", style + $table[0].outerHTML);
	//页眉页号
	if (printHead) {
		LODOP.ADD_PRINT_HTM(1, "85%", 150, 30, "<span tdata='pageNO'>第##页</span>/<span tdata='pageCount'>共##页</span>");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);//每页都输出
	}

	if (user) {
		LODOP.ADD_PRINT_TEXT("97%", 60, 200, 22, "制表人：" + user || "制表人");
		LODOP.SET_PRINT_STYLEA(0, "Horient", 1);//设定打印项在纸张范围内的水平方向的位置锁定方式
		LODOP.SET_PRINT_STYLEA(0, "Vorient", 1);//设定打印项在纸张范围内的垂直方向的位置锁定方式
		LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);//左靠齐
		LODOP.SET_PRINT_STYLEA(0, "PageIndex", "last");	//仅在尾页才输出的内容
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12)
	}

	if (date) {
		LODOP.ADD_PRINT_TEXT("97%", 550, 220, 22, "制表日期：" + LODOP.FORMAT("TIME:YYYY年MM月DD日", "DATE"));
		LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
		LODOP.SET_PRINT_STYLEA(0, "Vorient", 1);
		LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);//右靠齐
		LODOP.SET_PRINT_STYLEA(0, "PageIndex", "last");	//仅在尾页才输出的内容 
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
	}

	if (!percent) {
		percent = "Auto-Width";//整宽不变形
		//LODOP.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);//内容超出纸宽时对应缩小
		//LODOP.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW",true);//内容超出纸高时对应缩小,只适应于单页打印
	}
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", percent);

	//1：打印设计，2：打印维护，3：打印预览，4：直接打印
	switch (type) {
		case 1:
			LODOP.PRINT_DESIGN();
			break;
		case 2:
			LODOP.PRINT_SETUP();
			break;
		case 3:
			previewStyle();
			LODOP.PREVIEW();
			break;
		case 4:
			LODOP.PRINT();
			break;
		default:
			previewStyle();
			LODOP.PREVIEW();
			break;
	}
	CLODOP.On_Return = function (TaskID, Value) {
		if (typeof param.complete === "function") {
			param.complete(TaskID, Value);
		}
	}
	//设置打印预览窗口样式
	function previewStyle() {
		//LODOP.SET_PRINT_PAGESIZE(2,0,0,"");//设定纸张大小
		// LODOP.SET_PREVIEW_WINDOW(2, 0, 0, 0, 0, "");//设置预览窗口全屏
		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED", 1);//横向时的正向显示
	}

	//生成表格
	function createTable(sheet, namedStyles) {
		var vAlign = ["top", "middle", "bottom"];
		var hAlign = ["left", "center", "right"];
		if (!sheet) {//为空则返回
			return;
		}
		var columnCount = sheet.columnCount;
		var rowCount = sheet.rowCount;
		var data = sheet.data.dataTable;
		var spans = sheet.spans;
		var columns = sheet.columns;
		var rows = sheet.rows;
		var namedStyles = namedStyles || sheet.namedStyles;
		var namedStylesObject = {};
		var tableWidth = 0;
		//重新构造样式表
		$(namedStyles).each(function (i, v) {
			namedStylesObject[v.name] = v;
		});
		//画表格
		var $table = $("<table></table>");
		for (var i = 0; i < rowCount; i++) {
			var $tr = $("<tr></tr>").css({
				height: rows && rows[i] ? rows[i].size : "",
			});
			if (rows && rows[i] && rows[i].visible === false) {
				$tr.hide();
			}
			for (var j = 0; j < columnCount; j++) {
				if (!data[i]) {
					break;
				}
				var column = data[i][j];
				var $td = $("<td></td>").css({ width: columns && columns[j] ? columns[j].size : "80" });
				var style;
				if (column) {
					if (typeof column.style === "string") {
						style = namedStylesObject[column.style];
					} else if (typeof column.style === "object") {
						style = column.style;
					}
					var font_style = style ? style.font : "";
					var align = style ? hAlign[style.hAlign] : "left"; //默认值
					var valign = style ? vAlign[style.vAlign] : "top"; //默认值
					var backColor = style ? style.backColor : "#ffffff";
					var border = {
						top: style && style.borderTop ? style.borderTop.style : "",
						left: style && style.borderLeft ? style.borderLeft.style : "",
						bottom: style && style.borderBottom ? style.borderBottom.style : "",
						right: style && style.borderRight ? style.borderRight.style : ""
					}
					$td.css({
						width: columns && columns[j] ? columns[j].size : "",
						font: font_style,
						borderTopWidth: border.top,
						borderLeftWidth: border.right,//最左侧单元格没有左边框
						borderBottomWidth: border.bottom,
						borderRightWidth: border.right,
						backgroundColor: backColor
					}).attr({
						align: align || "left",
						valign: valign || "top"
					});

					$td.html(formattValue(column.value, style).toString())
				}
				$tr.append($td);

				if (i === 0) {
					tableWidth += $td.width()
				}
			}
			$table.append($tr);
			$table.width(tableWidth);
		}

		//根据数据合并单元格
		$(spans).each(function (i, v) {
			var $cell = $table.find("tr").eq(v.row).find("td").eq(v.col)
			$cell.attr({
				rowSpan: v.rowCount,
				colSpan: v.colCount,
			});
			if (v.colCount > 1) {
				// $cell.css("width","");
			}

			//根据合并列隐藏单元格
			for (var i = 0; i < v.colCount; i++) {
				for (var j = 0; j < v.rowCount; j++) {
					if (i != 0 || j != 0) {
						$table.find("tr").eq(v.row + j).find("td").eq(v.col + i).hide();
					}
				}
			}
		});

		//返回封装好的表格 jquery对象形式

		// $(columns).each(function (i, v) {
		// 	if (v) {
		// 		tableWidth += v.size || 0;
		// 	} else {
		// 		tableWidth += 10;
		// 	}
		// })
		// $table.width(tableWidth);
		return $table;

		function formattValue(value, style) {
			var formatter = "";
			if (!SSF) {
				console.error("Spread Sheet Format插件未引入 打印数据无法格式化");
				return value;
			}
			if (style && style.formatter) {
				formatter = style.formatter;
			}
			if (typeof value === "string") {
				try {
					var func = value.replace(/\//g, "").replace(/\(.*?\)/g, '');
					if (typeof eval(func) === "function") {
						value = eval(value.replace(/\//g, ""));//执行spread内置函数 在下面创建函数逻辑
					}
				} catch (error) {

				}
				if (formatter) {
					return SSF.format(formatter, value);
				}
				return value;
			} else if (typeof value === "number") {
				var result = SSF.format(formatter, value) || value;
				return result;
			} else {
				return "";
			}
		}

		function OADate(value) {
			var times = value * 24 * 60 * 60 * 1000 + (-2209190400000);
			var date = new Date(times);
			//console.log(date.toLocaleDateString());
			var result = {
				year: date.getFullYear(),
				month: date.getMonth() + 1,
				day: date.getDay()
			}
			//日期格式待定
			return result.year + "-" + result.month + "-" + result.day;
		}
	}


	function newPage($table, rows) {
		var trList = [];
		var tableList = [];
		var tableWidth = $table.width();
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var tr = $table.find("tr").eq(i).get(0);
			if (row && row.pageBreak) {
				tableList.push($("<table style='width:" + tableWidth + "px' >").append(trList.join("")));
				trList.length = 0;
			}
			trList.push(tr.outerHTML);
			if (i === rows.length - 1) {
				tableList.push($("<table style='width:" + tableWidth + "px' >").append(trList.join("")));
			}
		}
		return tableList;
	}


	function createTable2(sheet, namedStyles) {
		var vAlign = ["top", "middle", "bottom"];
		var hAlign = ["left", "center", "right"];
		if (!sheet) {//为空则返回
			return;
		}
		var columnCount = sheet.columnCount;
		var rowCount = sheet.rowCount;
		var data = sheet.data.dataTable;
		var spans = sheet.spans;
		var columns = sheet.columns;
		var rows = sheet.rows;
		var namedStyles = namedStyles || sheet.namedStyles || [];
		var namedStylesObject = {};
		//重新构造样式表
		for (var i = 0; i < namedStyles.length; i++) {
			var value = namedStyles[i];
			namedStylesObject[value.name] = value;
		}

		var table = document.createElement("table");
		var tableHTML = "";
		for (var i = 0; i < rowCount; i++) {
			var tr = document.createElement("tr");
			var trSize = (rows && rows[i] ? rows[i].size : "20") + "px";
			var trHTML = "";
			tr.style.height = trSize;
			if (rows && rows[i] && rows[i].visible === false) {
				tr.style.display = "none";
			}
			for (var j = 0; j < columnCount; j++) {
				if (!data[i]) {
					break;
				}
				var column = data[i][j];
				var td = document.createElement("td");
				var tdSize = (columns && columns[j] ? columns[j].size : "120") + "px";
				//td.style.width = tdSize;
				td.style.maxWidth = tdSize;
				td.style.minWidth = tdSize;
				var style;
				if (column) {
					if (typeof column.style === "string") {
						style = namedStylesObject[column.style];
					} else if (typeof column.style === "object") {
						style = column.style;
					}
					var wordWrap = style.wordWrap;
					var font_style = style ? style.font : "";
					var align = style ? hAlign[style.hAlign] : "left"; //默认值
					var valign = style ? vAlign[style.vAlign] : "top"; //默认值
					var backColor = style ? style.backColor : "#ffffff";
					var border = {
						top: style && style.borderTop ? style.borderTop.style : "",
						left: style && style.borderLeft ? style.borderLeft.style : "",
						bottom: style && style.borderBottom ? style.borderBottom.style : "",
						right: style && style.borderRight ? style.borderRight.style : ""
					}
					//td.style.width = tdSize;
					td.style.font = font_style;
					td.style.borderTopWidth = border.top + "px";
					td.style.borderLeftWidth = border.right + "px";//最左侧单元格没有左边框
					td.style.borderBottomWidth = border.bottom + "px";
					td.style.borderRightWidth = border.right + "px";
					//边框背景色
					td.style.borderTopColor = style && style.borderTop ? style.borderTop.color : "";
					td.style.borderLeftColor = style && style.borderLeft ? style.borderLeft.color : "";
					td.style.borderBottomColor = style && style.borderBottom ? style.borderBottom.color : "";
					td.style.borderRightColor = style && style.borderRight ? style.borderRight.color : "";

					td.style.backgroundColor = backColor;
					if (wordWrap) {
						td.style.whiteSpace = "normal";
					}
					td.align = align || "left";
					td.vAlign = valign || "top";
					td.innerHTML = formattValue(column.value, style).toString();
				}
				trHTML += td.outerHTML;

				if (i === 0) {
					tableWidth += parseInt(td.style.width);
				}
			}
			tr.innerHTML = trHTML;
			tableHTML += tr.outerHTML;
			table.style.width = tableWidth + "px";
		}
		table.innerHTML = tableHTML;
		//根据数据合并单元格
		for (var index = 0; index < spans.length; index++) {
			var v = spans[index];
			var cell = table.getElementsByTagName("tr")[v.row].getElementsByTagName("td")[v.col];
			cell.rowSpan = v.rowCount;
			cell.colSpan = v.colCount;
			//根据合并列隐藏单元格
			for (var i = 0; i < v.colCount; i++) {
				for (var j = 0; j < v.rowCount; j++) {
					var mergeCell = table.getElementsByTagName("tr")[v.row + j].getElementsByTagName("td")[v.col + i];
					if (i != 0 || j != 0) {
						mergeCell.style.display = "none";
					}
				}
			}
		}
		//计算合并列宽度 删除隐藏单元格 因为删除会改变索引 所以再次循环
		var colSpanCell = table.getElementsByTagName("td");
		for (var index = 0; index < colSpanCell.length; index++) {
			var cell = colSpanCell[index];
			var number = 1;
			if (cell.colSpan) {
				number = parseInt(cell.colSpan);
			} else {
				break;
			}
			var cellWidth = parseInt(cell.style.width);
			for (var i = 0; i < number - 1; i++) {
				var element = cell.nextSibling;
				cellWidth += parseInt(element.style.width);
				element.parentNode.removeChild(element);
			}
			cell.style.width = cellWidth + "px";
		}

		return newPage(table, rows);



		// return $(table);

		//格式化
		function formattValue(value, style) {
			var formatter = "";
			if (!SSF) {
				console.error("Spread Sheet Format插件未引入 打印数据无法格式化");
				return value;
			}
			if (style && style.formatter) {
				formatter = style.formatter;
			}
			// if (typeof value === "string") {
			// 	try {
			// 		var func = value.replace(/\//g, "").replace(/\(.*?\)/g, '');
			// 		if (typeof eval(func) === "function") {
			// 			value = eval(value.replace(/\//g, ""));//执行spread内置函数 在下面创建函数逻辑
			// 		}
			// 	} catch (error) {

			// 	}
			// 	if (formatter) {
			// 		return SSF.format(formatter, value);
			// 	}
			// 	return value.replace(" ", "&nbsp;");
			// } else if (typeof value === "number") {
			// 	var result = SSF.format(formatter, value) || value;
			// 	return result;
			// } else {
			// 	return "";
			// }
			value = spreadType(value);

			if (typeof value === "string") {
				try {
					var func = value.replace(/\//g, "").replace(/\(.*?\)/g, '');
					if (typeof eval(func) === "function") {
						value = eval(value.replace(/\//g, ""));//执行spread内置函数 在下面创建函数逻辑
					}
				} catch (error) {

				}
				if (formatter) {
					return SSF.format(formatter, value);
				}
				return value.replace(" ", "&nbsp;");
			} else if (typeof value === "number") {
				if (formatter) {
					return SSF.format(formatter, value);
				}
				return value;
			} else {
				return value;
			}
		}

		function spreadType(value) {
			var result;
			if (typeof value === "number") { //纯数字
				result = value;
			} else {
				if (isNaN(value)) { //不是数字字符串
					result = value || "";
				} else {
					if (value >= 0) { // "0001"这种形式
						result = value;
					} else { // "0.01"这种形式
						result = value * 1;
					}
				}
			}
			return result;
		}

		//spread内部日期函数
		function OADate(value) {
			var times = value * 24 * 60 * 60 * 1000 + (-2209190400000);
			var date = new Date(times);
			//console.log(date.toLocaleDateString());
			var result = {
				year: date.getFullYear(),
				month: date.getMonth() + 1,
				day: date.getDay()
			}
			//日期格式待定
			return result.year + "-" + result.month + "-" + result.day;
		}

		//分页联打
		function newPage(table, rows) {
			var trList = [];
			var tableWidth = table.style.width;
			var tableList = [];
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var tr = table.getElementsByTagName("tr")[i];
				if (row && row.pageBreak) {
					var tableDom = document.createElement("table");
					tableDom.innerHTML = trList.join("");
					tableDom.style.width = tableWidth;
					tableList.push(tableDom.outerHTML);
					// tableList.push($("<table style='width:" + tableWidth + "'>").append(trList.join("")));
					trList.length = 0;
				}
				trList.push(tr.outerHTML);
				if (i === rows.length - 1) {
					//tableList.push($("<table style='width:" + tableWidth + "'>").append(trList.join("")));
					var tableDom = document.createElement("table");
					tableDom.innerHTML = trList.join("");
					tableDom.style.width = tableWidth;
					tableList.push(tableDom.outerHTML);
				}
			}
			return tableList;
		}


	}
}


/**
 * 根据liger grid数据格式 生成table html
 * @param {*} param 
 */
function _createTable(param) {
	// var start = new Date().getTime();
	var data = param.data;
	var title = param.title;
	var column = param.column;
	var fn = param.fn || {};
	var group = param.group;
	var head = param.head;
	var $table = $("<table  cellspacing='0' cellpadding='0' bordercolor='#000000'></table>");

	var tableWidth = 0;

	$table.append("<thead></thead>");
	var colObject = {};//列对象 用于数据渲染 格式以及处理函数
	createTable(column);

	return $table[0].outerHTML;


	//根据列头创建表格
	function createTable(columns, dataHeader) {
		var col, $td;
		var columnsArray = [];
		var headers = dataHeader || [];
		var $tr = $("<tr></tr>");
		var resultTr = "";
		for (var i = 0; i < columns.length; i++) {
			col = columns[i];
			//判断如果不是行号列 且 不是隐藏列
			if (!col.isrownumber && !col._hide && !col.ischeckbox) {
				var colSpan = 1;
				//创建表头单元格
				if (col.columns) {
					colSpan = 0;
					$(col.columns).each(function (i, v) {
						if (!v._hide) {
							colSpan++;
						}
					})
				}
				$td = $("<th></th>").text(col.display)
					.attr({
						colSpan: colSpan,//col.__colSpan,
						rowSpan: col.__rowSpan,
						align: "center",
						width: (col._width * 1)
					}).appendTo($tr);

				tableWidth += col._width * 1 || 0;
				//根据列名name 添加列处理
				colObject[col.name] = { align: col.align };

				//创建渲染数据表头  
				//用于处理多表头时 数据根据最底层表头进行渲染
				if (col.__pid === -1) {
					var colName = col.columns ? col.__id : col.name;
					headers.push(colName);
				}
				if (col.columns) {
					for (var j = 0, len = col.columns.length; j < len; j++) {
						if (!col.columns[j]._hide) {
							var childCol = col.columns[j];
							var childColName = childCol.columns ? childCol.__id : childCol.name;
							//判断是否最后一个 如果最后一个则直接替换 不是最后一个 则添加在索引前面
							if (j === len - 1) {
								headers.splice(headers.indexOf(childCol.__pid), 1, childColName);
							} else {
								headers.splice(headers.indexOf(childCol.__pid), 0, childColName);
							}
						}
					}
					columnsArray = columnsArray.concat(col.columns);
				}
			}
		}
		$table.css({
			width: tableWidth
		});
		$table.find("thead").append($tr);
		if (columnsArray.length > 0) {
			createTable(columnsArray, headers);
		} else { //表头渲染结束 开始渲染数据
			//添加title在thead
			var $head = $("<thead></thead>");
			var $title = $("<tr><th style='border:none;padding-bottom:5px;' colSpan=" + headers.length + "><h1>" + (param.title || "") + "</h1></th></tr>");
			var $search = $("<tr><th></th></tr>")

			if (title) {
				$head.append($title);
			}
			if (head) {
				$search.find("th")
					.append($(head))
					.attr("colSpan", headers.length)
					.css("border", "none");
				$head.append($search);
			}
			$table.find("thead").prepend($head.html());

			//渲染数据 根据分组信息执行不同渲染方式
			if (group) {
				resultTr = renderRowsByGroup(headers, colObject).html();
				$table.append(resultTr);
			} else {
				resultTr = renderRows(headers, colObject).html();
				$table.append(resultTr);
			}
		}
	}

	//渲染行
	function renderRows(column, colObject) {
		var $table = $("<table></table");
		for (var i = 0; i < data.length; i++) {
			var d = data[i];
			var $tr = $("<tr></tr>");
			for (var j = 0; j < column.length; j++) {
				var colHeader = column[j];
				var formatt = fn[colHeader];
				var txt = d[colHeader] || "";
				if (typeof formatt === "function") {
					txt = formatt(txt);
				}
				$("<td style='padding-left:5px;'></td>")
					.attr("align", colObject[colHeader] ? colObject[colHeader].align : "")
					.text(txt).appendTo($tr);
			}
			$table.append($tr);
		}
		return $table;
	}

	//渲染行 包含分组信息
	function renderRowsByGroup(column, colObject) {
		var rowArr = {};
		var groupNameArr = [];
		var $table = $("<table></table>");
		//构造分组数据
		for (var i = 0; i < data.length; i++) {
			var group_name = data[i][group.name];
			//如果rowArr[group_name]未赋值 则创建一个数组
			if (!rowArr[group_name]) {
				rowArr[group_name] = [];
			}
			rowArr[group_name].push(data[i]);
			if (groupNameArr.indexOf(group_name) === -1) {
				groupNameArr.push(group_name);
			}
		}

		//渲染分组数据
		for (var j = 0; j < groupNameArr.length; j++) {
			var groupName = groupNameArr[j];
			var groupData = rowArr[groupName];
			var $tr = $("<tr></tr>").appendTo($table);
			var $td = $("<td></td>")
				.attr("ColSpan", column.length)
				.text(group.display + ":" + groupName).appendTo($tr);

			$(groupData).each(function (i, v) {
				$tr = $("<tr></tr>");
				for (var i = 0; i < column.length; i++) {
					var colHeader = column[i];
					var formatt = fn[colHeader];
					var txt = v[colHeader] || "";
					if (typeof formatt === "function") {
						txt = formatt(txt);
					}
					$("<td style='padding-left:5px;padding-right:5px;'></td>")
						.attr("align", colObject[colHeader] ? colObject[colHeader].align : "")
						.text(txt).appendTo($tr);
				}
				$table.append($tr);
			});
		}
		return $table;
	}
}

/**
 * 根据liger grid数据格式 生成table html
 * @param {*} param 
 */
function _createTable2(param) {

	var data = param.data;
	var title = param.title;
	var column = param.column;
	var fn = param.fn || {};
	var group = param.group;
	var head = param.head;
	var tableWidth = 0;

	var table = document.createElement("table");
	table.cellPadding = 0;
	table.cellSpacing = 0;
	table.borderColor = "#000";

	var thead = document.createElement("thead");
	table.appendChild(thead);


	var colObject = {};//列对象 用于数据渲染 格式以及处理函数
	createTable(column);

	return table.outerHTML;


	//根据列头创建表格
	function createTable(columns, dataHeader) {
		var col, th;
		var columnsArray = [];
		var headers = dataHeader || [];

		var tr = document.createElement("tr");
		var trHTML = "";
		var resultTr = "";
		for (var i = 0; i < columns.length; i++) {
			col = columns[i];
			//判断如果不是行号列 且 不是隐藏列
			if (!col.isrownumber && !col._hide && !col.ischeckbox && !col.isdetail) {
				var colSpan = 1;
				//创建表头单元格
				if (col.columns) {
					colSpan = 0;
					$(col.columns).each(function (i, v) {
						if (!v._hide) {
							colSpan++;
						}
					})
				}

				th = document.createElement("th");
				th.colSpan = colSpan;
				th.rowSpan = col.__rowSpan;
				th.align = "center";
				th.width = col._width * 1 + "px";
				th.innerHTML = col.display;
				trHTML += th.outerHTML;

				tableWidth += col._width * 1 || 0;
				//根据列名name 添加列处理
				colObject[col.name] = { align: col.align };

				//创建渲染数据表头  
				//用于处理多表头时 数据根据最底层表头进行渲染
				if (col.__pid === -1) {
					var colName = col.columns ? col.__id : col.name;
					headers.push(colName);
				}
				if (col.columns) {
					for (var j = 0, len = col.columns.length; j < len; j++) {
						if (!col.columns[j]._hide) {
							var childCol = col.columns[j];
							var childColName = childCol.columns ? childCol.__id : childCol.name;
							//判断是否最后一个 如果最后一个则直接替换 不是最后一个 则添加在索引前面
							if (j === len - 1) {
								headers.splice(headers.indexOf(childCol.__pid), 1, childColName);
							} else {
								headers.splice(headers.indexOf(childCol.__pid), 0, childColName);
							}
						}
					}
					columnsArray = columnsArray.concat(col.columns);
				}
			}
		}
		tr.innerHTML = trHTML;

		table.style.width = tableWidth + "px";

		thead.appendChild(tr);

		if (columnsArray.length > 0) {
			createTable(columnsArray, headers);
		} else { //表头渲染结束 开始渲染数据
			//添加title在thead
			var headDom = document.createElement("thead");
			var titleDom = document.createElement("tr");
			titleDom.innerHTML = "<th style='border:none;padding-bottom:5px;' colSpan=" + headers.length + "><h1>" + (param.title || "") + "</h1></th>";
			var searchDom = document.createElement("tr");
			searchDom.innerHTML = "<th></th>"

			if (title) {
				headDom.appendChild(titleDom);
			}
			if (head) {
				var thDom = searchDom.getElementsByTagName("th")[0]
				thDom.innerHTML = head;
				thDom.colSpan = headers.length;
				thDom.style.border = "none";
				headDom.appendChild(searchDom);
			}
			$(thead).prepend(headDom.innerHTML);

			//渲染数据 根据分组信息执行不同渲染方式
			if (group) {
				resultTr = renderRowsByGroup(headers, colObject).innerHTML;
				$(table).append(resultTr);
			} else {
				resultTr = renderRows(headers, colObject).innerHTML;
				$(table).append(resultTr);
			}
		}
	}

	//渲染行
	function renderRows(column, colObject) {
		var table = document.createElement("table");
		var tableHTML = "";
		for (var i = 0; i < data.length; i++) {
			var d = data[i];
			var tr = document.createElement("tr");
			var trHTML = "";
			for (var j = 0; j < column.length; j++) {
				var colHeader = column[j];
				var formatt = fn[colHeader];
				var txt = d[colHeader] || "";

				var td = document.createElement("td");
				td.style.paddingLeft = "5px";
				td.align = colObject[colHeader] ? colObject[colHeader].align : "";

				if (typeof formatt === "function") {
					txt = formatt(txt);
				}
				td.innerHTML = txt;
				trHTML += td.outerHTML;
			}
			tr.innerHTML = trHTML;
			tableHTML += tr.outerHTML;
		}
		table.innerHTML = tableHTML;
		return table;
	}

	//渲染行 包含分组信息
	function renderRowsByGroup(column, colObject) {
		var rowArr = {};
		var groupNameArr = [];
		var table = document.createElement("table");
		var tableHTML = "";
		//构造分组数据
		for (var i = 0; i < data.length; i++) {
			var group_name = data[i][group.name];
			//如果rowArr[group_name]未赋值 则创建一个数组
			if (!rowArr[group_name]) {
				rowArr[group_name] = [];
			}
			rowArr[group_name].push(data[i]);
			if (groupNameArr.indexOf(group_name) === -1) {
				groupNameArr.push(group_name);
			}
		}

		//渲染分组数据
		for (var j = 0; j < groupNameArr.length; j++) {
			var groupName = groupNameArr[j];
			var groupData = rowArr[groupName];

			var trDom = document.createElement("tr");
			var tdDom = document.createElement("td");
			tdDom.colSpan = column.length;
			tdDom.innerHTML = group.display + ":" + groupName;
			trDom.appendChild(tdDom);

			tableHTML += trDom.outerHTML;

			$(groupData).each(function (i, v) {
				trDom = document.createElement("tr");
				var trHTML = "";
				for (var i = 0; i < column.length; i++) {
					var colHeader = column[i];
					var formatt = fn[colHeader];
					var txt = v[colHeader] || "";

					var tdDom = document.createElement("td");
					tdDom.style.paddingLeft = "5px";
					tdDom.align = colObject[colHeader] ? colObject[colHeader].align : "";

					if (typeof formatt === "function") {
						txt = formatt(txt);
					}
					tdDom.innerHTML = txt;
					trHTML += tdDom.outerHTML;
				}
				trDom.innerHTML = trHTML;
				tableHTML += trDom.outerHTML;
			});
		}
		table.innerHTML = tableHTML;
		return table;
	}
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
function lodopBarcodePrint(BarCodeValue, Width, Height, BarCodeType, Top, Left, isPre) {
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
		isPre = false;
	}
	LODOP = getLodop();
	LODOP.PRINT_INIT("");
	LODOP.ADD_PRINT_BARCODE(Top, Left, Width, Height, BarCodeType, BarCodeValue);
	if (isPre) {
		LODOP.PRINT();
	} else {
		LODOP.PREVIEW();
	}

}


//兼容性问题解决 
function ifIE8() {
	var browser = navigator.appName
	var b_version = navigator.appVersion
	var version = b_version.split(";");
	var trim_Version = version[1].replace(/[ ]/g, "");
	if (browser == "Microsoft Internet Explorer" && trim_Version != "MSIE8.0") {
		return true;
		// $("table").find("[colspan]").css("width", "");
		// $("table").find("[rowspan]").css("height", "");
	} else {
		return false;
	}

}

