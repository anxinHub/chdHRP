var subjWidth="600px"; 

//转换字符串，undefined,null等转化为""
function praseStrEmpty(str){
	if(!str || str=="undefined" || str=="null"){
		return "";
	}
	return str;
};

/**
 * 根据风格加载css
 */
// function loadStyle() {

// 	if (window.top.skinSelect == undefined) {
// 		// 首页返回
// 		return;
// 	}
// 	// 获取当前js文件路径
// 	var scripts = document.getElementsByTagName("script");
// 	var script = scripts[scripts.length - 1];
// 	var sUrl = script.hasAttribute ? script.src : script.getAttribute("src", 4);

// 	sUrl = sUrl.replace(/^.*?\:\/\/[^\/]+/, "").replace(/[^\/]+$/, "");
// 	var selectSkin = window.top.skinSelect.value;
// 	var head = document.getElementsByTagName('head')[0];
// 	var link = document.createElement('link');
// 	if (selectSkin == "gray") {// 灰色风格
// 		link.href = sUrl + "ligerUI/skins/Gray/css/all.css";
// 	} else if (selectSkin == "gray2014") {// 白色风格
// 		link.href = sUrl + "ligerUI/skins/Gray2014/css/all.css";
// 	} else if (selectSkin == "silvery") {// 银色风格
// 		link.href = sUrl + "ligerUI/skins/Silvery/css/style.css";
// 	} else {// 默认风格
// 		link.href = sUrl + "ligerUI/skins/Aqua/css/ligerui-all.css";
// 	}
// 	link.rel = 'stylesheet';
// 	link.type = 'text/css';
// 	head.appendChild(link);
// }
// loadStyle();

/*
 * ajax查询方法,返回json对象 参数1：字符型，url（例如：querySystemUser.do?isCheck=false）
 * 参数2：字符型，para，请求参数，json格式（例如：{usePager:false,comp_code:compCodeStr,user_code:$("#user_code").val(),user_name:'%'+$("#user_name").val()+'%'}）
 * 参数3：函数类型，callBackFun:回调函数 参数4：boolean类型，isAsync，false同步、true异步
 * 参数5：对象类型，managerShow:自定义弹出进度条对象
 */
function ajaxJsonObjectByUrl(urlStr, para, callBackFun, isAsync, managerShow) {

	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		return false;
	}

	if (isAsync != false) {
		// 默认异步
		isAsync = true;
	}
	if (typeof managerShow != "object") {
		// 默认显示进度条
		$("#pageloading").show();
	}
	var is_flag = $.ajax({
		type: "post",
		url: urlStr,
		data: para,
		dataType: "json",
		async: isAsync,
		// contentType: 'application/json',
		// //application/xml、html、application/json
		success: function (responseData) {
			$("#pageloading").hide();

			if (responseData != null) {

				if (typeof (responseData.error) != 'undefined'
					&& responseData.error != null
					&& responseData.error != '') {
					if($.ligerDialog){
						$.ligerDialog.error(responseData.error);
					}else{
						$.etDialog.error(responseData.error);
					}
					
					return false;
				}

				if (typeof (responseData.tipError) != 'undefined'
					&& responseData.tipError != null
					&& responseData.tipError != '') {
					tipDlg(responseData.tipError);
					return false;
				}

				if (typeof (responseData.msg) != 'undefined'
					&& responseData.msg != null && responseData.msg != '') {
					
					if($.ligerDialog){
						$.ligerDialog.success(responseData.msg);
					}else{
						$.etDialog.success(responseData.error);
					}
					
				}

				if (typeof (responseData.tipMsg) != 'undefined'
					&& responseData.tipMsg != null
					&& responseData.tipMsg != '') {
					tipDlg(responseData.tipMsg);
				}

				if (typeof (responseData.warn) != 'undefined'
					&& responseData.warn != null
					&& responseData.warn != '') {
					$.ligerDialog.warn(responseData.warn)
					return false;
				}
			}

			if (typeof callBackFun === 'function') {
				callBackFun(responseData);
			} else {
				// $.ligerDialog.error('回调方法不存在！');
			}
			
			return true;
		},
		error: function (XMLHttpRequest, textStatus) {
			$("#pageloading").hide();
			if (typeof managerShow == "object") {
				managerShow.close();
			}
			// 通过XMLHttpRequest取得响应头，sessionstatus
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if (sessionstatus == "MSG_TIME_OUT") {
				alert('会话超时，请重新登录.');
				// 这里跳转的登录页面
				// window.top.location.href="login.html";
			} else if (sessionstatus == "NOT_PERMID") {
				$.ligerDialog.error('没有该操作权限.');
			} else if (sessionstatus == "TOKEN_MAPPING") {
				$.ligerDialog.error("重复提交数据.");
			} else if (sessionstatus == "REQUEST_MAPPING") {
				$.ligerDialog.error("没有找到对应的请求.");
			} else if (textStatus == "parsererror") {
				$.ligerDialog.error("返回类型不是json.");
			} else {
				if (XMLHttpRequest.status && XMLHttpRequest.status == "404") {
					$.ligerDialog.error('没有找到对应的请求404.');
				} else {
					$.ligerDialog.error('操作失败.');
				}
			}
			return false;
		}, complete: function () {
			if(Local){
				Local._remove("hrp[repeat[commit");	
			}
		}
	});
	
	return is_flag;
}


/*
 *利用layer弹出进度条层，可以屏蔽页面的其他操作，不仅仅是进度条的功能。
 *调用方法前，先调用var index=layer.load(1)，操作成功后需要手动关闭提示layer.close(jumpIndex);。
 *传入layer对象和index参数。
 *参考hrp\acc\accvouch\making\main.jsp，mySave方法。
 */
function ajaxJsonObjectBylayer(urlStr, para, callBackFun, layer, index) {

	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		layer.close(index);
		return false;
	}

	$.ajax({
		type: "post",
		url: urlStr,
		data: para,
		dataType: "json",
		async: true,
		success: function (responseData) {

			if (responseData != null) {
				if (typeof (responseData.error) != 'undefined'
					&& responseData.error != null
					&& responseData.error != '') {
					$.ligerDialog.error(responseData.error);
					return false;
				}

				if (typeof (responseData.tipError) != 'undefined'
					&& responseData.tipError != null
					&& responseData.tipError != '') {
					tipDlg(responseData.tipError);
					return false;
				}

				if (typeof (responseData.msg) != 'undefined'
					&& responseData.msg != null && responseData.msg != '') {
					$.ligerDialog.success(responseData.msg);
				}

				if (typeof (responseData.tipMsg) != 'undefined'
					&& responseData.tipMsg != null
					&& responseData.tipMsg != '') {
					tipDlg(responseData.tipMsg);
				}

				if (typeof (responseData.warn) != 'undefined'
					&& responseData.warn != null && responseData.warn != '') {
					$.ligerDialog.warn(responseData.warn);
				}
			}

			if (typeof callBackFun === 'function') {
				callBackFun(responseData);
			} else {
				// $.ligerDialog.error('回调方法不存在！');
			}

		},
		error: function (XMLHttpRequest, textStatus) {

			// 通过XMLHttpRequest取得响应头，sessionstatus
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if (sessionstatus == "MSG_TIME_OUT") {
				alert('会话超时，请重新登录.');
				// 这里跳转的登录页面
				// window.top.location.href="login.html";
			} else if (sessionstatus == "NOT_PERMID") {
				$.ligerDialog.error('没有该操作权限.');
			} else if (sessionstatus == "REQUEST_MAPPING") {
				$.ligerDialog.error("没有找到对应的请求.");
			} else if (sessionstatus == "TOKEN_MAPPING") {
				$.ligerDialog.error("重复提交数据.");
			} else if (textStatus == "parsererror") {
				$.ligerDialog.error("返回类型不是json.");
			} else {
				if (XMLHttpRequest.status && XMLHttpRequest.status == "404") {
					$.ligerDialog.error('没有找到对应的请求404.');
				} else {
					$.ligerDialog.error('操作失败.');
				}
			}
		}, complete: function () {
			if(Local){
				Local._remove("hrp[repeat[commit");	
			}
			layer.close(index);
		}
	});
}

//20160929,ajax直接返回字符串格式，例如打印模板设置直接返回html字符串
function ajaxTextByUrl(urlStr, para, callBackFun, isAsync) {

	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		return false;
	}

	if (isAsync != false) {
		// 默认异步
		isAsync = true;
	}

	$.ajax({
		type: "post",
		url: urlStr,
		data: para,
		dataType: "text",
		async: isAsync,
		success: function (responseData) {
			$("#pageloading").hide();

			if (typeof callBackFun === 'function') {
				callBackFun(responseData);
			} else {
				// $.ligerDialog.error('回调方法不存在！');
			}

		},
		error: function (XMLHttpRequest, textStatus) {
			$("#pageloading").hide();
			if (typeof managerShow == "object") {
				managerShow.close();
			}
			// 通过XMLHttpRequest取得响应头，sessionstatus
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if (sessionstatus == "MSG_TIME_OUT") {
				alert('会话超时，请重新登录.');
				// 这里跳转的登录页面
				// window.top.location.href="login.html";
			} else if (sessionstatus == "NOT_PERMID") {
				$.ligerDialog.error('没有该操作权限.');
			} else if (sessionstatus == "REQUEST_MAPPING") {
				$.ligerDialog.error("没有找到对应的请求.");
			} else if (sessionstatus == "TOKEN_MAPPING") {
				$.ligerDialog.error("重复提交数据.");
			} else if (textStatus == "parsererror") {
				$.ligerDialog.error("返回类型不是text.");
			} else {
				$.ligerDialog.error('操作失败.');
			}
		}, complete: function () {
			if(Local){
				Local._remove("hrp[repeat[commit");	
			}
		}
	});
}

function ajaxLigerPostFormData(paramObj) {
    var param = {
        url: '', // 请求路径
        para: {}, // 请求参数
        sync: false, //默认异步
        success: function () {}, // 成功回调
        error: function () {}, // 失败回调
        delayCallback: false // 是否在success等弹框点击确定后再执行回调函数
    }
    $.extend(param, paramObj);

    $.ajax({
        type: 'POST',
        url: param.url,
        data: param.para,
        dataType: 'json',
        sync: param.sync,
        cache: false,
        contentType: false,
        processData: false,
		success: function (responseData) {
			$("#pageloading").hide();

			if (responseData != null) {

				if (typeof (responseData.error) != 'undefined'
					&& responseData.error != null
					&& responseData.error != '') {
					if($.ligerDialog){
						$.ligerDialog.error(responseData.error);
					}else{
						$.etDialog.error(responseData.error);
					}
					
					return false;
				}

				if (typeof (responseData.tipError) != 'undefined'
					&& responseData.tipError != null
					&& responseData.tipError != '') {
					tipDlg(responseData.tipError);
					return false;
				}

				if (typeof (responseData.msg) != 'undefined'
					&& responseData.msg != null && responseData.msg != '') {
					
					if($.ligerDialog){
						$.ligerDialog.success(responseData.msg);
					}else{
						$.etDialog.success(responseData.error);
					}
					
				}

				if (typeof (responseData.tipMsg) != 'undefined'
					&& responseData.tipMsg != null
					&& responseData.tipMsg != '') {
					tipDlg(responseData.tipMsg);
				}

				if (typeof (responseData.warn) != 'undefined'
					&& responseData.warn != null
					&& responseData.warn != '') {
					$.ligerDialog.warn(responseData.warn)
					return false;
				}
			}

			if (typeof param.success === 'function') {
				param.success(responseData);
			} else {
				// $.ligerDialog.error('回调方法不存在！');
			}
			
			return true;
		},
		error: function (XMLHttpRequest, textStatus) {
			$("#pageloading").hide();
			if (typeof managerShow == "object") {
				managerShow.close();
			}
			// 通过XMLHttpRequest取得响应头，sessionstatus
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
			if (sessionstatus == "MSG_TIME_OUT") {
				alert('会话超时，请重新登录.');
				// 这里跳转的登录页面
				// window.top.location.href="login.html";
			} else if (sessionstatus == "NOT_PERMID") {
				$.ligerDialog.error('没有该操作权限.');
			} else if (sessionstatus == "TOKEN_MAPPING") {
				$.ligerDialog.error("重复提交数据.");
			} else if (sessionstatus == "REQUEST_MAPPING") {
				$.ligerDialog.error("没有找到对应的请求.");
			} else if (textStatus == "parsererror") {
				$.ligerDialog.error("返回类型不是json.");
			} else {
				if (XMLHttpRequest.status && XMLHttpRequest.status == "404") {
					$.ligerDialog.error('没有找到对应的请求404.');
				} else {
					$.ligerDialog.error('操作失败.');
				}
			}
			return false;
		}, complete: function () {
			if(Local){
				Local._remove("hrp[repeat[commit");	
			}
		}
    })
};

/*
 * 格式化是否
 */
function formatYesNo() {
	$.ligerDefaults.Grid.formatters['formatYesNo'] = function (num, column) {
		if (num == 1) {
			return "是";
		} else {
			return "否";
		}
	};
}

function formatTrueFalse() {
	$.ligerDefaults.Grid.formatters['formatTrueFalse'] = function (num, column) {
		if (num == 'true') {
			return "是";
		} else {
			return "否";
		}
	};
}
/*
 * 格式化启用、停用
 */
function formatIsStop() {
	$.ligerDefaults.Grid.formatters['formatIsStop'] = function (num, column) {
		if (num == 0) {
			return "启用";
		} else {
			return "停用";
		}
	};
}

// 右下角弹窗，自动关闭
var tipOb = null;
function tipDlg(message, obj) {
	if (tipOb != null) {
		return;
	}

	if (obj == undefined) {
		// 首页
		tipOb = $.ligerDialog.tip({
			title: '提示信息',
			content: message
		});
	} else {
		// 弹出页
		tipOb = obj.tip({
			title: '提示信息',
			content: message
		});
	}

	setTimeout(function () {
		tipOb.hide();
		tipOb = null;
	}, 1000);

}
/**
 * id:input id<br>
 * urlStr:数据源URL(需返回JSON)<br>
 * valueField:值字段名 <br>
 * textField:文本字段名 <br>
 * autocomplete:自动检索 <br>
 * highLight:是否匹配字符高亮显示<br>
 * parmsStr:异步数据请求参数<br>
 * 例子
 * autocomplete("#title_code","../htcSelectDict/queryPeopleTitleAllDict.do?isCheck=false","id","text",true,true);
 */

function autocomplete(id, urlStr, valueField, textField, autocomplete, highLight, parmsStr, defaultSelect, initvalue, initWidth, 
		initHeight, boxwidth, alwayShowInDown, selectEvent,textBoxKeySpace,autocompletelocal,pageSize) {
	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		return false;
	}
	if (parmsStr == undefined || parmsStr == "") {
		parmsStr = null;
	}
	if (valueField == undefined || valueField == "") {
		$.ligerDialog.error("valueField为空");
		return false;
	}
	if (textField == undefined || textField == "") {
		$.ligerDialog.error("textField为空");
		return false;
	}
	if (initWidth == undefined || initWidth == "") {
		initWidth = 160;
	}
	if (boxwidth == undefined || boxwidth == "") {
		boxwidth = initWidth;
	}
	if (initHeight == undefined || initHeight == "") {
		initHeight = 260;
	}
	if (alwayShowInDown == undefined) {
		alwayShowInDown = true;
	}

	if (autocomplete != false) {
		autocomplete = true;
	}
	if (defaultSelect != true) {
		defaultSelect = false;
	}
	if (alwayShowInDown != true) {
		alwayShowInDown = false;
	}
	var count = 0;
	if (highLight != false) {
		highLight = true;
	}
	if(autocompletelocal!=true){
		autocompletelocal=false;
	}
	if(pageSize == undefined || pageSize == ""){
		pageSize=20;
	}

	$(id).ligerComboBox({
		parms: parmsStr,
		url: urlStr,
		valueField: valueField,
		textField: textField,
		selectBoxWidth: boxwidth,
		selectBoxHeight: initHeight,
		setTextBySource: true,
		width: initWidth,
		autocomplete: autocomplete,
		autocompletelocal: autocompletelocal,
		pageSize: pageSize,
		highLight: highLight,
		keySupport: true,
		async: true,
		alwayShowInDown: alwayShowInDown,
		onTextBoxKeySpace:function(data) {
			if (textBoxKeySpace)
				textBoxKeySpace(data);
		},
		onSelected: function (data) {
			if (selectEvent)
				selectEvent(data);
		},
		onSuccess: function (data) {
			if (initvalue != undefined && initvalue != "" && initvalue != null) {
				this.setValue(initvalue);
				initvalue = "";
			}

			if (defaultSelect == true) {
				//console.log(data)
				if (data.length > 0) {
					if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
						defaultSelectValue = data[0].id;
						if (count == 0) {
							this.setValue(data[0].id);
						}
					}
				}
			}
			count++;

		}
	});

	defaultSelectValue = "select";
}
/**
 * 重新封装
 * 传参为对象形式
 */
function autocompleteObj(obj) {
	autocomplete(obj.id, obj.urlStr, obj.valueField, obj.textField, obj.autocomplete, obj.highLight, obj.parmsStr, obj.defaultSelect, 
			obj.initvalue === undefined?obj.initValue:obj.initvalue, obj.initWidth, obj.initHeight, obj.boxwidth, obj.alwayShowInDown, obj.selectEvent,obj.textBoxKeySpace,
			obj.autocompletelocal,obj.pageSize)
}


/**写法：
 * autocompleteObj({
 * 		id:                      必写
 * 		urlStr:					必写				
 * 		valueField:               必写
 * 		textField:                必写
 * 		autocomplete:				
 * 		highLight:
 * 		parmsStr:
 * 		defaultSelect:
 * 		initvalue:
 * 		initWidth:
 * 		initHeight:
 * 		boxwidth:
 * 		alwayShowInDown:
 * 		selectEvent:fucntion(value) {
 *     }
 * 		
 * })
 * id:input id<br>
 * urlStr:数据源URL(需返回JSON)<br>
 * valueField:值字段名 <br>
 * textField:文本字段名 <br>
 * autocomplete:自动检索 <br>
 * highLight:是否匹配字符高亮显示<br>
 * parmsStr:同步数据请求参数<br>
 * 例子
 * autocomplete("#title_code","../htcSelectDict/queryPeopleTitleAllDict.do?isCheck=false","id","text",true,true);
 */
function autocompleteAsync(id, urlStr, valueField, textField, autocomplete, highLight, parmsStr, defaultSelect, initvalue, initWidth, isTriggerToLoad) {
	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		return false;
	}
	if (parmsStr == undefined || parmsStr == "") {
		parmsStr = null;
	}
	if (valueField == undefined || valueField == "") {
		$.ligerDialog.error("valueField为空");
		return false;
	}
	if (textField == undefined || textField == "") {
		$.ligerDialog.error("textField为空");
		return false;
	}
	if (initWidth == undefined || initWidth == "") {
		initWidth = 160;
	}

	if (autocomplete != false) {
		autocomplete = true;
	}
	if (defaultSelect != true) {
		defaultSelect = false;
	}
	var count = 0;
	if (highLight != false) {
		highLight = true;
	}

	$(id).ligerComboBox({
		parms: parmsStr,
		url: urlStr,
		valueField: valueField,
		textField: textField,
		selectBoxWidth: initWidth,
		setTextBySource: true,
		width: initWidth,
		autocomplete: autocomplete,
		highLight: highLight,
		keySupport: true,
		triggerToLoad: isTriggerToLoad,
		async: false,
		onSuccess: function (data) {
			if (initvalue != undefined && initvalue != "" && initvalue != null) {
				this.setValue(initvalue);
				initvalue = "";
			}

			if (defaultSelect == true) {
				if (data.length > 0) {
					if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
						defaultSelectValue = data[0].id;
						if (count == 0) {
							this.setValue(data[0].id);
						}
					}
				}
			}
			count++;
		}
	});

	defaultSelectValue = "select";
}


function loadComboBox(obj) {
	var count = 0;
	if (!obj.url) {
		$.ligerDialog.error("url为空");
		return false;
	}
	if (!obj.value) {
		$.ligerDialog.error("valueField为空");
		return false;
	}
	if (!obj.text) {
		$.ligerDialog.error("textField为空")
		return false;
	}
	if (typeof obj.autocomplete !== 'boolean') {
		obj.autocomplete = true;
	}
	if(obj.autocompletelocal!=true){
		obj.autocompletelocal=false;
	}
	if(obj.pageSize == undefined || obj.pageSize == ""){
		obj.pageSize=20;
	}

	$(obj.id).ligerComboBox({
		parms: obj.parms ? obj.parms : null,
		url: obj.url,
		valueField: obj.value,
		textField: obj.text,
		selectBoxWidth: obj.selectBoxWidth ? obj.selectBoxWidth : 160,
		maxWidth: obj.maxWidth,
		setTextBySource: true,
		width: obj.width ? obj.width : 160,
		autocomplete: obj.autocomplete,
		autocompletelocal: obj.autocompletelocal,
		pageSize: obj.pageSize,
		hightLight: obj.hightLight,
		keySupport: true,
		triggerToLoad: obj.isTriggerToLoad,
		async: obj.async ? true : obj.async,
		alwayShowInDown: obj.alwayShowInDown,
		onSelected: function (data) {
			if (obj.selectEvent) {
				obj.selectEvent(data);
			}
		},
		onSuccess: function (data) {
			if (obj.initValue) {
				this.setValue(obj.initValue);
			}
			if (obj.defaultSelect) {
				if (data.length > 0 && data[0].id) {
					if (count == 0) {
						this.setValue(data[0].id);
					}
				}
			}
			count++;
		}

	});
}

/**
 * id:input id<br>
 * urlStr:数据源URL(需返回JSON)<br>
 * valueField:值字段名 <br>
 * textField:文本字段名 <br>
 * autocomplete:自动检索 <br>
 * highLight:是否匹配字符高亮显示<br>
 * parmsStr:同步数据请求参数<br>
 * 例子
 * autocomplete("#title_code","../htcSelectDict/queryPeopleTitleAllDict.do?isCheck=false","id","text",true,true);
 */
function autocompleteAsyncMulti(id, urlStr, valueField, textField, autocomplete, highLight, parmsStr, defaultSelect, initvalue, initWidth, isTriggerToLoad) {
	if (urlStr == undefined || urlStr == "") {
		$.ligerDialog.error("url为空");
		return false;
	}
	if (parmsStr == undefined || parmsStr == "") {
		parmsStr = null;
	}
	if (valueField == undefined || valueField == "") {
		$.ligerDialog.error("valueField为空");
		return false;
	}
	if (textField == undefined || textField == "") {
		$.ligerDialog.error("textField为空");
		return false;
	}
	if (initWidth == undefined || initWidth == "") {
		initWidth = 160;
	}

	if (autocomplete != false) {
		autocomplete = true;
	}
	if (defaultSelect != true) {
		defaultSelect = false;
	}
	var count = 0;
	if (highLight != false) {
		highLight = true;
	}

	$(id).ligerComboBox({
		parms: parmsStr,
		url: urlStr,
		valueField: valueField,
		textField: textField,
		selectBoxWidth: initWidth,
		setTextBySource: true,
		width: initWidth,
		autocomplete: autocomplete,
		highLight: highLight,
		keySupport: true,
		triggerToLoad: isTriggerToLoad,
		isShowCheckBox: true, isMultiSelect: true,
		async: false,
		onSuccess: function (data) {
			if (initvalue != undefined && initvalue != "" && initvalue != null) {
				this.setValue(initvalue);
				initvalue = "";
			}

			if (defaultSelect == true) {
				if (data.length > 0) {
					if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
						defaultSelectValue = data[0].id;
						if (count == 0) {
							this.setValue(data[0].id);
						}
					}
				}
			}
			count++;
		}
	});

	defaultSelectValue = "select";
}

/*
 * 初始化日期
 * autodate("#acc_year","yyyy-mm");
 * dateFmt yyyy、mm、dd、yyyy-mm、yyyy-mm-dd
 * flag : new-当前日期, month_first-月初，month_last-月末, year_first-年初, year_last-年末, 
 *        before_month-前推一月(当前05月15日，下月即04月15日), next_month-后退一月(当前05月15日，下月即06月15日)
 */
function autodate(id, dateFmt, flag) {
	var d;
	if (dateFmt == undefined || dateFmt == "") {
		dateFmt = "yyyy-mm-dd";
	}
	if (flag == undefined || flag == "") {
		d = new Date();
	} else {
		if (flag == "new") {
			d = new Date();
		} else {
			var mydate = new Date();
			var vYear = mydate.getFullYear();
			var vMon = mydate.getMonth() + 1;
			var vDay = mydate.getDate();
			var this_date = getMonthDate(vYear, vMon);
			//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
			var daysInMonth = new Array(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
			//若是闰年，二月最后一天是29号
			if (vYear % 4 == 0 && vYear % 100 != 0) {
				daysInMonth[2] = 29;
			}

			if (flag == "month_first") {
				d = new Date(this_date.split(";")[0].replace(/-/g, '/'));
			} else if (flag == "month_last") {
				d = new Date(this_date.split(";")[1].replace(/-/g, '/'));
			} else if (flag == "year_first") {
				d = new Date((vYear + '-01' + '-01').replace(/-/g, '/'));
			} else if (flag == "year_last") {
				d = new Date((vYear + '-12' + '-31').replace(/-/g, '/'));
			} else if (flag == "before_month") {
				if (vMon == 1) {
					vYear = mydate.getFullYear() - 1;
					vMon = 12;
				} else {
					vMon = vMon - 1;
				}
				if (daysInMonth[vMon] < vDay) {
					vDay = daysInMonth[vMon];
				}
				if (vDay < 10) {
					vDay = "0" + vDay;
				}
				if (vMon < 10) {
					vMon = "0" + vMon;
				}
				d = new Date((vYear + "-" + vMon + "-" + vDay).replace(/-/g, '/'));
			} else if (flag == "next_month") {
				if (vMon == 12) {
					vYear = mydate.getFullYear() + 1;
					vMon = 1;
				} else {
					vMon = vMon + 1;
				}
				if (daysInMonth[vMon] < vDay) {
					vDay = daysInMonth[vMon];
				}
				if (vDay < 10) {
					vDay = "0" + vDay;
				}
				if (vMon < 10) {
					vMon = "0" + vMon;
				}
				d = new Date((vYear + "-" + vMon + "-" + vDay).replace(/-/g, '/'));
			} else {
				d = new Date();
			}
		}
	}

	if (dateFmt.toLowerCase() == 'yyyy') {
		$(id).val(d.getFullYear().toString());
	}
	if (dateFmt.toLowerCase() == 'mm') {
		$(id).val(addzero(d.getMonth() + 1));
	}
	if (dateFmt.toLowerCase() == 'dd') {
		$(id).val(addzero(d.getDate()));
	}
	if (dateFmt.toLowerCase() == 'yyyy-mm') {
		$(id).val(d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1));
	}
	if (dateFmt.toLowerCase() == 'yyyy mm') {
		$(id).val(d.getFullYear().toString() + " " + addzero(d.getMonth() + 1));
	}
	if (dateFmt.toLowerCase() == 'yyyymm') {
		$(id).val(d.getFullYear().toString() + addzero(d.getMonth() + 1));
	}
	if (dateFmt.toLowerCase() == 'yyyy/mm') {
		$(id).val(d.getFullYear().toString() + "/" + addzero(d.getMonth() + 1));
	}
	if (dateFmt.toLowerCase() == 'yyyy-mm-dd') {
		$(id).val(d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1) + "-" + addzero(d.getDate()));
	}
	if (dateFmt.toLowerCase() == 'yyyy mm dd') {
		$(id).val(d.getFullYear().toString() + " " + addzero(d.getMonth() + 1) + " " + addzero(d.getDate()));
	}
	if (dateFmt.toLowerCase() == 'yyyy/mm/dd') {
		$(id).val(d.getFullYear().toString() + "/" + addzero(d.getMonth() + 1) + "/" + addzero(d.getDate()));
	}
	if (dateFmt.toLowerCase() == 'yyyymmdd') {
		$(id).val(d.getFullYear().toString() + addzero(d.getMonth() + 1) + addzero(d.getDate()));
	}
	if (dateFmt.toLowerCase() == 'yyyy-mm-dd hh:mm:ss') {
		$(id).val(d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1) + "-" + addzero(d.getDate()) + " " + addzero(d.getHours()) + ":" + addzero(d.getMinutes()) + ":" + addzero(d.getSeconds()));
	}

	function addzero(v) {
		if (v < 10) return '0' + v;
		return v.toString();
	}
}
/**
 * 键盘快捷键 ctrl+键盘按键 通过键盘事件 触发对应的函数
 *
 * @param hotkey
 *            快捷键 忽略大小写
 * @param fun
 *            触发的函数
 * @param args
 *            参数 <br>
 *            调用示例：<br>
 *            hotkeys('Q',query); 函数无参数<br>
 *            hotkeys('D',delete,['参数1','参数2','参数3']); 函数有参数<br>
 *            hotkeys('A',itemclick,[{id:'add'}]);函数的参数带数组的<br>
 */
function hotkeys(hotkey, fun, args) {

	jQuery.hotkeys.add('Alt+' + hotkey.toLowerCase() + '', function () {
		fun.apply(this, args);
	});
}
function autoCompleteByData(id, data, valueField, textField, autocomplete, highLight, parmsStr, defaultSelect, initvalue, initWidth,selectEvent) {
	if (parmsStr == undefined || parmsStr == "") {
		parmsStr = null;
	}
	if (data == undefined || data == "") {
		$.ligerDialog.error("数据为空");
		return false;
	}
	if (valueField == undefined || valueField == "") {
		$.ligerDialog.error("valueField为空");
		return false;
	}
	if (textField == undefined || textField == "") {
		$.ligerDialog.error("textField为空");
		return false;
	}
	if (!autocomplete) {
		autocomplete = true;
	}
	if (!highLight) {
		highLight = true;
	}
	if (!defaultSelect) {
		defaultSelect = false;
	}
	if (!initWidth) {
		initWidth = 160;
	}

	var count = 0;

	var comboBox = $(id).ligerComboBox({
		parms: parmsStr,
		data: data,
		valueField: valueField,
		textField: textField,
		selectBoxWidth: initWidth,
		initValue: 1,
		width: initWidth,
		autocomplete: autocomplete,
		highLight: highLight,
		keySupport: true,
		onSelected: function (data) {
			if (selectEvent)
				selectEvent(data);
		},
		onSuccess: function (data) {
			// if(initvalue != undefined && initvalue != "" && initvalue != null){
			// 	this.setValue(initvalue);
			// 	initvalue="";
			// }

			// if(defaultSelect == true){
			// 	if(data.length >0 ){
			// 		if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
			// 			defaultSelectValue = data[0].id;
			// 			if( count==0){
			// 				this.setValue(data[0].id);
			// 			}
			// 		}
			// 	}
			// }
			// count++;

		}
	});

	if (initvalue != undefined && initvalue != "" && initvalue != null) {
		comboBox.setValue(initvalue);
		initvalue = "";
	}

	if (defaultSelect == true) {
		if (data.length > 0) {
			if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
				defaultSelectValue = data[0].id;
				if (count == 0) {
					comboBox.setValue(data[0].id);
				}
			}
		}
	}
	count++;

	defaultSelectValue = "select";
}
/**
 * 格式化字符串 添加占位符
 * @param value 内容
 * @param number 占位符数量
 * @param place 占位符 默认空格
 */
function formatSpace(value, number, place) {
	var replace = place || "　";
	for (var i = 0; i < number; i++) {
		value = replace + value;
	}
	return value
};


/**
 * 将数值四舍五入后格式化. <br>
 *
 * @param num
 *            数值(Number或者String) <br>
 * @param cent
 *            要保留的小数位(Number)<br>
 * @param isThousand
 *
 * 是否需要千分位 0:不需要,1:需要(数值类型)<br>
 * @return 格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatNumber(num, cent,
	isThousand) {
	if (!num) {
		var zero = '';
		while (zero.length < cent) {// 补足小数位到指定的位数.
			zero = "0" + zero;
		};
		if (isNaN(cent) || !cent || cent=="undefined" || cent=="null") {
			return "0";
		}else {
			return "0." + zero;
		}	
	}
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num)|| !num || num=="undefined" || num=="null")// 检查传入数值为数值类型.
		num = "0";
	if (isNaN(cent) || !cent || cent=="undefined" || cent=="null")// 确保传入小数位为数值型数值.
		cent = 0;
	cent = parseInt(cent);
	cent = Math.abs(cent);// 求出小数位数,确保为正整数.
	if (isNaN(isThousand)|| !isThousand || isThousand=="undefined" || isThousand=="null")// 确保传入是否需要千分位为数值类型.
		isThousand = 0;
	isThousand = parseInt(isThousand);
	if (isThousand < 0)
		isThousand = 0;
	if (isThousand >= 1) // 确保传入的数值只为0或1
		isThousand = 1;
	sign = (num == (num = Math.abs(num)));// 获取符号(正/负数)
	// Math.floor:返回小于等于其数值参数的最大整数
	num = Math.floor(num * Math.pow(10, cent) + 0.50000000001);// 把指定的小数位先转换成整数.多余的小数位四舍五入.
	cents = num % Math.pow(10, cent); // 求出小数位数值.
	num = Math.floor(num / Math.pow(10, cent)).toString();// 求出整数位数值.
	cents = cents.toString();// 把小数位转换成字符串,以便求小数位长度.
	while (cents.length < cent) {// 补足小数位到指定的位数.
		cents = "0" + cents;
	}
	if (isThousand == 0) // 不需要千分位符.
		return (((sign) ? '' : '-') + num + '.' + cents);
	// 对整数部分进行千分位格式化.
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
			+ num.substring(num.length - (4 * i + 3));
	if(cents === "0") {
		return (((sign) ? '' : '-') + num);
	}else {
		return (((sign) ? '' : '-') + num + '.' + cents);
	}
	
}



/*******************************************************************************
 * 小写金额转换成大写（支持千分位格式及负数输入，整数部分可达44位-千正~元，小数部分可达5位
 *
 * @param dValue
 *            数值(Number或者String) <br>
 * @param maxDec
 *            小数点后位数 <br>
 * @returns
 */
function amountInWords(dValue, maxDec) {
	// 验证输入金额数值或数值字符串：
	dValue = dValue.toString().replace(/,/g, "");
	dValue = dValue.replace(/^0+/, ""); // 金额数值转字符、移除逗号、移除前导零
	if (dValue == "") {
		return "零元整";
	} // （错误：金额为空！）
	else if (isNaN(dValue)) {
		return "错误：金额不是合法的数值！";
	}
	var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
	var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
	if (dValue.length > 1) {
		if (dValue.indexOf('-') == 0) {
			dValue = dValue.replace("-", "");
			minus = "负";
		} // 处理负数符号“-”
		if (dValue.indexOf('+') == 0) {
			dValue = dValue.replace("+", "");
		} // 处理前导正数符号“+”（无实际意义）
	}

	// 变量定义：
	var vInt = "";
	var vDec = ""; // 字符串：金额的整数部分、小数部分
	var resAIW; // 字符串：要输出的结果
	var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
	var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
	var zeroCount; // 零计数
	var i, p, d; // 循环因子；前一位数字；当前位数字。
	var quotient, modulus; // 整数部分计算用：商数、模数。

	// 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
	var NoneDecLen = (typeof (maxDec) == "undefined" || maxDec == null
		|| Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
	parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
	if (parts.length > 1) {
		vInt = parts[0];
		vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分

		if (NoneDecLen) {
			maxDec = vDec.length > 5 ? 5 : vDec.length;
		} // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
		var rDec = Number("0." + vDec);
		rDec *= Math.pow(10, maxDec);
		rDec = Math.round(Math.abs(rDec));
		rDec /= Math.pow(10, maxDec); // 小数四舍五入
		var aIntDec = rDec.toString().split('.');
		if (Number(aIntDec[0]) == 1) {
			vInt = (Number(vInt) + 1).toString();
		} // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
		if (aIntDec.length > 1) {
			vDec = aIntDec[1];
		} else {
			vDec = "";
		}
	} else {
		vInt = dValue;
		vDec = "";
		if (NoneDecLen) {
			maxDec = 0;
		}
	}
	if (vInt.length > 44) {
		return "错误：金额值太大了！整数位长【" + vInt.length.toString()
			+ "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！";
	}
	// 准备各字符数组 Prepare the characters corresponding to the digits:
	digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
	radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
	bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
	decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
	resAIW = ""; // 开始处理
	// 处理整数部分（如果有）
	if (Number(vInt) > 0) {
		zeroCount = 0;
		for (i = 0; i < vInt.length; i++) {
			p = vInt.length - i - 1;
			d = vInt.substr(i, 1);
			quotient = p / 4;
			modulus = p % 4;
			if (d == "0") {
				zeroCount++;
			} else {
				if (zeroCount > 0) {
					resAIW += digits[0];
				}
				zeroCount = 0;
				resAIW += digits[Number(d)] + radices[modulus];
			}
			if (modulus == 0 && zeroCount < 4) {
				resAIW += bigRadices[quotient];
			}
		}
		resAIW += "元";
	}
	// 处理小数部分（如果有）
	for (i = 0; i < vDec.length; i++) {
		d = vDec.substr(i, 1);
		if (d != "0") {
			resAIW += digits[Number(d)] + decimals[i];
		}else{
			resAIW+="零";
		}
	}
	// 处理结果
	if (resAIW == "") {
		resAIW = "零" + "元";
	} // 零元
	if (vDec == "") {
		resAIW += "整";
	} // ...元整
	resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负......元角分/整
	return resAIW;
	// 备注：
}

var item_type_dict = {
	Rows: [{
		"item_type_code": "1",
		"item_type_name": "基本项"
	}, {
		"item_type_code": "2",
		"item_type_name": "社保"
	}, {
		"item_type_code": "3",
		"item_type_name": "公积金"
	}, {
		"item_type_code": "4",
		"item_type_name": "个税"
	}],
	Total: 4
};

var item_cal_dict = {
	Rows: [{
		"item_cal_code": "1",
		"item_cal_name": "输入项"
	}, {
		"item_cal_code": "2",
		"item_cal_name": "计算项"
	}],
	Total: 2
};

var item_nature_dict = {
	Rows: [{
		"item_nature_code": "1",
		"item_nature_name": "应付项"
	}, {
		"item_nature_code": "2",
		"item_nature_name": "应扣项"
	}],
	Total: 2
};

/*日期大小验证
*t1:第一个时间控件ID
*t2:第二个时间控件ID
*false:结束日期不能小于开始日期
*true:正常
*/

function dateValid(t1, t2) {
	var v1 = new Date($("#" + t1).val().replace(/-/g, "/"));
	var v2 = new Date($("#" + t2).val().replace(/-/g, "/"));
	if (v2 < v1) {
		return false;
	} else {
		return true;
	}
}

/*
*返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
*
*/
function getCurrentDate() {

	var currentYear;
	var currentMonth;
	var currentdate;
	var currentFirstDay;
	var currentLastDay;
	var lastMonth;
	var lastFirstDay;
	var lastEndDay;

	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();


	var thisEnd = getLastDay(date.getFullYear(), month);
	lastMonth = month - 1;
	var lastEnd = getLastDay(date.getFullYear(), lastMonth);


	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}

	if (lastMonth >= 1 && lastMonth <= 9) {
		lastMonth = "0" + lastMonth;
	}

	currentMonth = month;
	lastMonth = lastMonth;
	currentYear = date.getFullYear();


	currentFirstDay = date.getFullYear() + seperator1 + month + seperator1 + '01';
	currentLastDay = date.getFullYear() + seperator1 + month + seperator1 + thisEnd;

	lastFirstDay = date.getFullYear() + seperator1 + lastMonth + seperator1 + '01';
	lastEndDay = date.getFullYear() + seperator1 + lastMonth + seperator1 + lastEnd;

	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}

	currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;

	return currentYear + ';' + currentMonth + ';' + currentdate + ';' + currentFirstDay + ';' + currentLastDay + ';' + lastMonth + ';' + lastFirstDay + ';' + lastEndDay;

}
/*
 * 获得一个月有多少天
 */
function getLastDay(year, month) {
	var new_year = year;    //取当前的年份
	var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
	if (month > 12) {            //如果当前大于12月，则年份转到下一年
		new_month -= 12;        //月份减
		new_year++;            //年份增
	}
	var new_date = new Date(new_year, new_month, 1);                //取当年当月中的第一天
	var date_count = (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();//获取当月的天数
	var last_date = new Date(new_date.getTime() - 1000 * 60 * 60 * 24);//获得当月最后一天的日期
	return date_count;

}

/*某个日期多少天之前或者多少天之后的日期
*date:时间
*days:需要加减的天数(正数加负数减)
*返回格式yyyy-mm-dd
*/
function getDateAddDay(date, days) {
	var d = new Date(date);
	d.setDate(d.getDate() + parseInt(days));
	var month = d.getMonth() + 1;
	var day = d.getDate();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	var val = d.getFullYear() + "-" + month + "-" + day;
	return val;
}
/*
*返回传入月份的起止日期
*
*/
function getMonthDate(year, month) {
	var monthBeginDay;
	var monthEndDay;
	var seperator1 = "-";

	var thisEnd = getLastDay(year, month);

	monthBeginDay = year + seperator1 + month + seperator1 + '01';
	monthEndDay = year + seperator1 + month + seperator1 + thisEnd;

	return monthBeginDay + ';' + monthEndDay;
}
/**
 * 判断变量是否为空
 * @param exp
 * @returns
 */
function isnull(exp) {
	if (exp == "" || exp == null || exp == 'undefined' || exp == 0) {
		return true;
	}
	return false;
}
/**
 * 判断变量是否为空
 * @param exp
 *        传递的字符串
 * @param def
 *        为空时的默认值
 * @returns
 */
function isnull_default(exp, def) {
	if (def == "" || def == null || def == 'undefined') {
		def = "";
	}
	if (exp == "" || exp == null || exp == 'undefined' || exp == 0) {
		return def;
	}
	return exp;
}
//调用父级窗口页面，调用此方法以父级窗口为对象
function parentFrameUse() {
	var frameName = frameElement.dialog.get('parentframename');
	return $(window.parent.frames[frameName])[0];
}

/*模板打印，主从表*/
function printTemplate(url, para) {
	var isJsonObj = typeof (para) == "object" && Object.prototype.toString.call(para).toLowerCase() == "[object object]" && !para.length;

	if (!isJsonObj) {
		$.ligerDialog.error("参数不是json格式！");
		return;
	}
	if (para["template_code"] == undefined) {
		$.ligerDialog.error("参数不正确：没有包含template_code！");
		return;
	}

	//打印预览
	if (para["isPreview"] != undefined && para["isPreview"]) {

		parent.$.ligerDialog.open({
			url: 'print/printTemplate2.jsp',
			data: { url: url, para: para }, height: $(parent).height(), width: $(parent).width(), title: '打印预览', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true
		});

	} else {
		ajaxJsonObjectByUrl(url, para, function (responseData) {
			//console.log(JSON.stringify(responseData))
			var spread = new GC.Spread.Sheets.Workbook($('#spread')[0], { sheetCount: 1 });
			//var spreadNS = GcSpread.Sheets;

			try {
				if (!spread) {
					return;
				}
			} catch (ex) { }

			spread.isPaintSuspended(true);
			spread.fromJSON(responseData);
			spread.isPaintSuspended(false);
			var pageCount = responseData.pageCount;

			if (url == "querySuperVouchPrint.do") {
				//window打印
				spreadPrint({
					spread: spread.toJSON(),
					head: false,
					pageCount: pageCount
				});

				/*lodopSpread({
					spread:spread.toJSON(),
					taskName:"模板打印",
					percent:"Width:100%",
					head:false,
					pageCount:pageCount//模板每页打印次数
				})*/
			} else {
				spread.print();
			}

		});
	}


	/*	//查询打印数据，主从表
		ajaxJsonObjectByUrl(url,para,function(responseData){
			//console.log(JSON.stringify(responseData))
			//打印预览
			if(para["isPreview"]!=undefined && para["isPreview"]){
				parent.$.ligerDialog.open({url : 'lib/hrp/acc/superPrint/printPreView.jsp',
				data:{responseData:responseData,url:url,para:para}, height: $(parent).height(),width: $(parent).width(), title:'打印预览',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
				});

			}else{

				 var spread = new GcSpread.Sheets.Spread($('#spread')[0], { sheetCount: 1 });
				 //var spreadNS = GcSpread.Sheets;

				 try {
						 if (!spread) {
						 return;
					   }
				 } catch (ex) {}

				  spread.isPaintSuspended(true);
					  spread.fromJSON(responseData);
				spread.isPaintSuspended(false);
				spread.print();
			}

		});*/

	//updateSuperPrintTemplateByPrintCount.do
	//开始设置打印格式
	// var sheet = spread.getSheet(0);
	//var sheet = spread.getActiveSheet();
	// var printInfo = sheet.printInfo();
	//  printInfo.repeatRowStart(0);
	// printInfo.repeatRowEnd(3);
}

/*列表打印，弹出打印预览窗口*/
function printGridView(responseData, printPara) {
	parent.openDialog({ url: "print/printGrid.jsp", data: { responseData: responseData, printPara: printPara }, title: "预览", width: 0, height: 0 });

}

/*spread打印，弹出打印预览窗口*/
function printSpreadView(responseData, printPara) {
	parent.openDialog({ url: "print/printSpread.jsp", data: { responseData: responseData, printPara: printPara }, title: "打印预览", width: 0, height: 0 });

}

/*spread导入，弹出导入预览窗口
para:
{
    "column": [
        {
            "name": "a",
            "display": "第一列",
            "width": "200",
            "require":true
        },
        {
            "name": "b",
            "display": "第二列",
            "width": "200",
            "require":false
        }
    ]
}
 * */
// fun: 导入完成点击关闭时执行函数
function importSpreadView(url, para, fun) {
	parent.openDialog({ url: "print/import.jsp", data: { url: url, para: para, fun: fun}, title: "导入", width: 0, height: 0 });
}

/**
 * json中的Rows部分转换成数组对象并排序
 * @param1 需要转换的json串
 * @param2 排序字段
 * @param3 排序方式(默认升序) 1: 升序 2: 降序
 */
function jsonRowsToObject(jsonRows, sort_col, order_by) {
	if(!jsonRows) return [];
	var rows = eval(jsonRows.replace(/:,/g, ':"",').replace(/:}/g, ':""}'));
	if(sort_col){
		var sort_way = order_by && order_by == 2 ? -1 : 1;

		rows.sort(function (a, b) {
			if (!a['sort_col'] || !parseInt(a['sort_col'])) {
				return -1;
			} else if (!b['sort_col'] || !parseInt(b['sort_col'])) {
				return -1;
			} else {
				return (parseInt(a['sort_col']) - parseInt(b['sort_col']))*sort_way
			}
		});
	}
	return rows;
}

/**
 * 普通表格打印单表头<BR>
 * 参数1:请求地址URL<BR>
 * 参数2:请求参数条件<BR>
 * 参数3:单表头<BR>
 * 表头格式<BR>
 *<p>
 * var columnInfos = [ {
 *		name : "dept_code",
 *		displayName : "科室编码",
 *      size: 80  列宽度
 *	}]<BR>
 *<p>
 * 参数4:标题<BR>
 * 参数5:延迟打印时间 <BR>
        数据要加载 需要一点时间来缓冲  默认1000 如果打印没有数据的话，延迟时间稍长一些<BR>
 */
function viewPrintOneHead(url, para, columnInfos, title, timeout) {
	var spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"));
	var sheet = spread.getActiveSheet();
	printInfo = sheet.printInfo();
	//设置渲染信息
	sheet.isPaintSuspended(true);
	sheet.autoGenerateColumns = false;
	printInfo.showRowHeader(GC.Spread.Sheets.Print.PrintVisibilityType.hide);
	printInfo.headerCenter(title);

	ajaxJsonObjectByUrl(url, para, function (responseData) {
		//console.log(JSON.stringify(responseData))
		sheet.setDataSource(responseData.Rows);
		sheet.bindColumns(columnInfos);
	});
	sheet.isPaintSuspended(false);

	if (timeout < 1000 || timeout == null) {
		timeout = 1000
	}

	setTimeout(function () {
		spread.sheets[0];
		spread.print();
		;
	}, timeout);
}

/**
 * data:{
 * 		headers:{},//表头数据
 * 		rows:{},//标题数据
 * }
 *
 * title: 表格标题
 *
 * options: 扩展参数 无用
 */
function viewPrint(data, title, options) {
	var headers = data.headers;
	var rows = data.rows;
	var spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"));

	var sheet = spread.getActiveSheet();
	printInfo = sheet.printInfo();

	sheet.isPaintSuspended(true);
	sheet.autoGenerateColumns = false;
	sheet.colHeaderVisible = false;
	sheet.setColumnCount(24);

	printInfo.showRowHeader(GC.Spread.Sheets.Print.PrintVisibilityType.hide);
	printInfo.headerCenter(title);

	//设置列名
	var columns = {};
	//表体位置
	var body_Y = -1; // Y取最大的

	headers.forEach(function (v) {
		//组装表列
		if (v.name) {
			columns[v.name] = { x: v.x, formatter: v.formatter };
		}
		//设置列宽
		if (v.size) {
			sheet.setColumnWidth(v.x, v.size);
		}
		//设置表体 位置
		body_Y = body_Y > v.y ? body_Y : v.y;
		sheet.addSpan(v.y, v.x, v.rowSpan, v.colSpan);
		sheet.setValue(v.y, v.x, v.displayName);
	});
	body_Y++;
	rows.forEach(function (v, i) {
		for (var key in v) {
			if (columns[key] != undefined) {
				sheet.setValue(body_Y + i, columns[key].x, v[key]);
				if (columns[key].formatter) {
					sheet.setFormatter(body_Y + i, columns[key].x, '#,##0.00');
				}
			}
		}

	});
	spread.print();
}




//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e) {
	var ev = e || window.event;//获取event对象
	var obj = ev.target || ev.srcElement;//获取事件源

	var t = obj.type || obj.getAttribute('type');//获取事件源类型

	//获取作为判断条件的事件类型
	var vReadOnly = obj.getAttribute('readonly');
	var vEnabled = obj.getAttribute('enabled');
	//处理null值情况
	vReadOnly = (vReadOnly == null) ? false : vReadOnly;
	vEnabled = (vEnabled == null) ? true : vEnabled;

	//当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	//并且readonly属性为true或enabled属性为false的，则退格键失效
	var flag1 = (ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea" || t == "number")
		&& (vReadOnly == true || vEnabled != true)) ? true : false;

	//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	var flag2 = (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" && t != "number")
		? true : false;

	//判断
	if (flag2) {
		return false;
	}
	if (flag1) {
		return false;
	}
}

//禁止后退键 作用于Firefox、Opera
document.onkeypress = banBackSpace;
//禁止后退键 作用于IE、Chrome
document.onkeydown = banBackSpace;

// 银行账号（16-19位）验证  参数是input验证框对象
function luhmCheck(checkObject) {
	var bankno = $.trim(checkObject.val());
	if (bankno.length == 0) {
		checkObject.next().html("请输入银行卡号");
		return false;
	}
	if (bankno.length < 16 || bankno.length > 19) {
		checkObject.next().html("银行卡号长度必须在16到19之间");
		return false;
	}
	//开头6位
	var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
	if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
		checkObject.next().html("银行卡号开头6位不符合规范");
		return false;
	}
	var lastNum = bankno.substr(bankno.length - 1, 1); //取出最后一位（与luhm进行比较）
	var first15Num = bankno.substr(0, bankno.length - 1); //前15或18位
	var newArr = new Array();
	for (var i = first15Num.length - 1; i > -1; i--) { //前15或18位倒序存进数组
		newArr.push(first15Num.substr(i, 1));
	}
	var arrJiShu = new Array(); //奇数位*2的积 <9
	var arrJiShu2 = new Array(); //奇数位*2的积 >9
	var arrOuShu = new Array(); //偶数位数组
	for (var j = 0; j < newArr.length; j++) {
		if ((j + 1) % 2 == 1) { //奇数位
			if (parseInt(newArr[j]) * 2 < 9)
				arrJiShu.push(parseInt(newArr[j]) * 2);
			else
				arrJiShu2.push(parseInt(newArr[j]) * 2);
		} else //偶数位
			arrOuShu.push(newArr[j]);
	}
	var jishu_child1 = new Array(); //奇数位*2 >9 的分割之后的数组个位数
	var jishu_child2 = new Array(); //奇数位*2 >9 的分割之后的数组十位数
	for (var h = 0; h < arrJiShu2.length; h++) {
		jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
		jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
	}
	var sumJiShu = 0; //奇数位*2 < 9 的数组之和
	var sumOuShu = 0; //偶数位数组之和
	var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
	var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
	var sumTotal = 0;
	for (var m = 0; m < arrJiShu.length; m++) {
		sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
	}
	for (var n = 0; n < arrOuShu.length; n++) {
		sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
	}
	for (var p = 0; p < jishu_child1.length; p++) {
		sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
		sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
	}
	//计算总和
	sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);
	//计算Luhm值
	var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
	var luhm = 10 - k;
	if (lastNum == luhm) {
		checkObject.next().html("Luhm验证通过");
		return true;
	} else {
		checkObject.next().html("银行卡号必须符合Luhm校验");
		return false;
	}
}


/**
* 
* obj格式：   注意obj一定要是一个数组 即使只有一个元素
* [
*  {ctrl:false,alt:false,keyCode:"S",fn:function(){}}, 单独S键触发
*  {ctrl:true,alt:false,keyCode:83,fn:function(){}}    S键和ctrl键组合触发
* ]
* 
*/
function BindKeyBoard(obj) {
	$(document).keydown(function (e) {
		var c = e.which;
		var ctrl = e.ctrlKey;
		var alt = e.altKey;
		if (obj && obj.length > 0) {
			for (var i = 0, len = obj.length; i < len; i++) {
				var _item = obj[i];
				var _keyCode = _item.keyCode;
				var _code = -1;
				if (typeof _keyCode === "string") {
					_code = _keyCode.charCodeAt();
					if (_keyCode.length > 1) {
						console.log(_keyCode + "字符串过长");
						return false;
					}
				} else if (typeof _keyCode === "number") {
					_code = _keyCode;
				}
				if (c === _code && ctrl === !!_item.ctrl && alt === !!_item.alt) {
					if (_item.fn) {
						_item.fn();
					}
					return false;
				}
			}
		}
	});
}

/**
 * 保存表格列头
 * obj:{
 * 		grid:表格
 * 		path:保存路径
 * 		url:接口地址
 * }
 */
function saveColHeader(obj) {
	var path = obj.path;
	var url = obj.url;
	var gridColumns = obj.grid.getColumns();
	var columns = [];
	$(gridColumns).each(function (i, v) {
		if (v.columnname) {
			columns.push({ col: v.columnname, is_show: v._hide ? 0 : 1 });
		}
	});
	var data = {
		path_l: path,
		colList: columns
	}
	$.ajax({
		type: 'post',
		url: url,
		dataType: "json",
		data: {
			param: JSON.stringify(data)
		},
		success: function (result) {
			obj.callback(result);
		},
		error: function (result) {
			console.error(result);
		}
	});
}

/**
 * 加载表格列头
 * obj:{
 * 		grid:表格
 * 		path:保存路径
 * 		url:接口地址
 * }
 */
function loadColHeader(obj) {
	var path = obj.path;
	var url = obj.url;
	var grid = obj.grid;
	var data = {
		path_l: path
	}
	$.ajax({
		type: 'post',
		url: url,
		dataType: 'json',
		data: data,
		success: function (data) {
			$(data.Rows).each(function (i, v) {
				var hide = v.is_show ? true : false;
				toggleByColName(v.col, hide);
			});
		},
		error: function () {
			console.error(data);
		}
	});

	//根据列名隐藏列
	function toggleByColName(colName, is_show) {
		var columnInfo = grid.getColumnByName(colName);
		if (columnInfo) {
			grid.toggleCol(columnInfo, is_show);
		}
	}
}


//加载列表style信息
function loadServer(modCode, callBack) {
	var url = "";
	$.ajax({
		url: url,
		type: "POST",
		dataType: "json",
		success: function (res) {

		},
		errr: function (res) {

		},
		complete: function (res) {

		}
	});
}


//保存列表style信息
function saveServer(obj, callBack) {
	var url = "";
	$.ajax({
		url: url,
		type: "POST",
		dataType: "json",
		success: function (res) {

		},
		error: function (res) {

		},
		complete: function (res) {

		}
	});
}

//Local全局对象  用于操作 localStorage数据
if (window.localStorage) {


	var Local = {
		get: function (key) {//获取数据
			return localStorage.getItem(key);
		},
		set: function (key, value) {//设置数据
			localStorage.setItem(key, value);
		},
		getAll: function () {//获取所有数据
			var content = {};
			for (var key in localStorage) {
				if (localStorage.hasOwnProperty(key)) {
					content[key] = JSON.parse(localStorage[key]);
				}
			}
			return content;
		},
		setAll: function (json) {//设置所有数据
			for (var key in json) {
				if (json.hasOwnProperty(key)) {
					localStorage.setItem(key, json[key]);
				}
			}
		},
		_clearAll: function () {//清空所有数据
			localStorage.clear();
		},
		_remove: function (key) {//清空数据
			localStorage.removeItem(key);
		},
		getKeyArr: function () {//获取所有key名字
			if (this._keyArr.length !== 0) {
				return this._keyArr;
			}
			for (var key in localStorage) {
				if (localStorage.hasOwnProperty(key)) {
					this._keyArr.push(key);
				}
			}
			return this._keyArr;
		},
		length: localStorage.length,
		_keyArr: []
	}
}
/*
 *单据模板维护 
 *@param {para:para}
 */
function officeFormTemplate(param) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printFormSet.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printFormSet)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
														   
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}

    /* var sessionJson=getSessionJson();
    var group_id=sessionJson.group_id;
    var hos_id=sessionJson.hos_id;
    var copy_code=sessionJson.copy_code;
    var mod_code=sessionJson.mod_code;
    var paraStr="group_id="+group_id+"&hos_id="+hos_id+"&hos_copy="+copy_code+"&mod_code="+mod_code;
    for(var key in param){
    	paraStr=paraStr+"&"+key+"="+param[key];
    }
   
    param["group_id"]=group_id;
    param["hos_id"]=hos_id;
    param["copy_code"]=copy_code;
    param["mod_code"]=mod_code;*/


	//处理数据文档
	ajaxJsonObjectByUrl(urlStr + "PageOffice/printFormSetData.do?isCheck=false", param, function (responseData) {
		POBrowser.openWindowModeless(urlStr + 'PageOffice/fromSetLoad.jsp', 'fullscreen=yes', JSON.stringify(responseData));
	});



	//.pobmodal-overlay
	/*var oTimer = setInterval(function(){
		if($("#pobmodal-dialog").length>0){
			$("#pobmodal-dialog").hide();
			window.clearInterval(oTimer);   
		}
	},100);*/


}


/**
 * PageOffice单据打印
 * @param {para:para,isPreview:isPreview}
 */
function officeFormPrint(param) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}
	
	ajaxJsonObjectByUrl(urlStr + "PageOffice/printFormData.do?isCheck=false", param, function (responseData) {
		var isPreview = param.isPreview === undefined ? false : param.isPreview
		responseData["isPreview"] = isPreview;

		var paraStr = "";
		for (var key in responseData) {
			paraStr = paraStr + "&" + key + "=" + responseData[key];
		}

		var isChromeVer42 = existsChromeVer42(42);
		if (isChromeVer42) {
			//高于谷歌42版本
			if (isPreview) {
				//POBrowser.openWindow(urlStr+"PageOffice/fromLoad.jsp", "fullscreen=yes;",JSON.stringify(responseData));
				POBrowser.openWindowModeless(urlStr + "PageOffice/printFormPre.do?isCheck=false" + paraStr, "fullscreen=yes;");
			} else {
				//POBrowser.openWindow(urlStr+"PageOffice/fromLoad.jsp", "width=400px;height=300px;",JSON.stringify(responseData));
				POBrowser.openWindowModeless(urlStr + "PageOffice/printForm.do?isCheck=false" + paraStr, "width=400px;height=300px;");
			}
		} else {
			//低于谷歌42版本
			if (isPreview) {
				parent.openDialog({ url: "PageOffice/printFormPre.do?isCheck=false" + paraStr, data: {}, title: "打印预览", width: 0, height: 0 });
			} else {
				$.ligerDialog.open({ url: urlStr + "PageOffice/printForm.do?isCheck=false" + paraStr, data: {}, title: "打印", width: 400, height: 300 });
			}
		}

	});
}


/**
 * PageOffice单据批量打印
 * @param 
 * gridData: {Total: 3, Rows: [{},{},{}]}
 */
function officeFormPrintBatch(param, gridData) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}

	var paraStr = "";
	
	for (var key in param) {
		paraStr = paraStr + "&" + key + "=" + param[key];
	}
	
	if(typeof gridData == 'object'){
		gridData = JSON.stringify(gridData);
	}
	
	var isChromeVer42 = existsChromeVer42(42);
	if (isChromeVer42) {
		//高于谷歌42版本
		POBrowser.openWindowModeless(urlStr + "PageOffice/printFormBatch.do?isCheck=false" + paraStr, "fullscreen=yes;", gridData);
	} else {
		//低于谷歌42版本
		parent.openDialog({ url: "PageOffice/printFormBatch.do?isCheck=false" + paraStr, data: {}, title: "打印", width: 0, height: 0 });
	}
}

/**
 * 报表打印
 * {report_code,sheetName,url}
 */
function officePrintReport(para, blob, callBack) {

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}

	//document.forms.namedItem("myForm")
	var formData = new FormData();
	formData.append("file", blob);
	formData.append("fileName", para.report_code);
	formData.append("fileType", "xlsx");

	$.ajax({
		url: urlStr + para.url,
		type: "POST",
		data: formData,
		contentType: false,
		processData: false,
		dataType: "json",
		success: function (res) {
			if (!res.state) {
				$.ligerDialog.error(res.msg);
				return;
			}

			if (res.state) {
				var paraStr = "group_id=" + res.group_id + "&hos_id=" + res.hos_id + "&hos_copy=" + res.hos_copy + "&temp_file_name=" + res.temp_file_name;
				paraStr = paraStr + "&mod_code=" + res.mod_code + "&report_code=" + res.report_code + "&sheet_name=" + para.sheet_name + "&openFlag=" + para.openFlag;
				//POBrowser.openWindow(urlStr+"PageOffice/reportLoad.jsp", "fullscreen=yes;",paraStr);

				var isChromeVer42 = existsChromeVer42(42);
				if (isChromeVer42) {
					//高于谷歌42版本
					POBrowser.openWindowModeless(urlStr + "PageOffice/printReportPre.do?isCheck=false&" + paraStr, "fullscreen=yes;");
				} else {
					//低于谷歌42版本
					parent.openWindowModeless({ url: "PageOffice/printReportPre.do?isCheck=false&" + paraStr, data: {}, title: "打印预览", width: 0, height: 0 });
				}

			}
		},
		error: function (res) {
			$.ligerDialog.error('操作失败！');
		},
		complete: function () {
			callBack();
		}
	});
}


/**
 * PageOffice表格模板打印
 * @param {para:para}
 */
function officeTablePrint(param) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}

	ajaxJsonObjectByUrl(urlStr + "PageOffice/printTableData.do?isCheck=false", param, function (responseData) {
		//POBrowser.openWindow(urlStr+"PageOffice/tableLoad.jsp", "fullscreen=yes;",JSON.stringify(responseData));

		var paraStr = "";
		for (var key in responseData) {
			paraStr = paraStr + "&" + key + "=" + responseData[key];
		}
		var isPreview = param.isPreview === undefined ? false : param.isPreview
		var isChromeVer42 = existsChromeVer42(42);
		if (isChromeVer42) {
			//高于谷歌42版本
			if(isPreview){
				POBrowser.openWindowModeless(urlStr + "PageOffice/printTablePre.do?isCheck=false&" + paraStr, "fullscreen=yes;");
			}else{
				POBrowser.openWindowModeless(urlStr + "PageOffice/printTable.do?isCheck=false" + paraStr, "width=400px;height=300px;");
			}
		} else {
			//低于谷歌42版本
			if(isPreview){
				parent.openDialog({ url: "PageOffice/printTablePre.do?isCheck=false&" + paraStr, data: {}, title: "打印预览", width: 0, height: 0 });
			}else{
				parent.openDialog({ url: "PageOffice/printTable.do?isCheck=false" + paraStr, data: {}, title: "打印", width: 400, height: 300 });
			}
		}
	});
}


/**
 * PageOffice表格批量打印
 * @param 
 * gridData: {Total: 3, Rows: [{},{},{}]}
 */
function officeTablePrintBatch(param, gridData) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}
	
	var paraStr = "";
	
	for (var key in param) {
		paraStr = paraStr + "&" + key + "=" + param[key];
	}
	
	if(typeof gridData == 'object'){
		gridData = JSON.stringify(gridData);
	}
	
	var isChromeVer42 = existsChromeVer42(42);
	if (isChromeVer42) {
		//高于谷歌42版本
		POBrowser.openWindowModeless(urlStr + "PageOffice/printTableBatch.do?isCheck=false" + paraStr, "fullscreen=yes;", gridData);
	} else {
		//低于谷歌42版本
		parent.openDialog({ url: "PageOffice/printTableBatch.do?isCheck=false" + paraStr, data: {}, title: "打印", width: 0, height: 0 });
	}
}


/**
 * PageOffice列表打印
 * 如果个页面需要打多个grid，需要传gridIndex序号
 * 
title: "财政基本补助支出备查簿",//标题
columns: JSON.stringify(columns),
class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
method_name: "collectAccExpendFinancialPrint",
bean_name: "accSubjLedgerService",
heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空

 * columns表头说明：
display：//表头显示名称
name: //sql返回别名
align: left/center/right,//内容水平对齐方式,可为空
colSpan://合并列数,可为空
columnindex: //单元格下标
type: string/int/float,//数据类型,可为空
width: 133//单元格宽度,可为空
formatter: ###,##0.00,//数据格式化,可为空
 */
/*formatter:
金额格式化：###,##0.00
日期格式化：yyyy-MM-dd HH:mm:ss
reg:
正则判断：0=否,1=是
正则判断：0.00=Q
*/
//参考页面：
//hrp\acc\accfinancialsubsidy\accExpendFinancialMain.jsp
//hrp\acc\accwagepay\accWagePayMain.jsp
//格式：columns={ "maxcolumnindex":9, "maxlevel":2, "rows":[{ "display": "期间",//显示名称 "align": "left",//内容居左 "colSpan": 2,//合并2列 "columnindex": 0,//单元格下标 "level":1, "columns": [ { "display": "年", "name": "acc_year",//sql返回别名 "align": "left", "type": "string",//数据类型 "columnindex": 0, "level":2, "width": 133//单元格宽度 }, { "display": "月", "name": "acc_month", "align": "left", "columnindex": 1, "level":2, "width": 133 } ] }, { "display": "凭证号", "name": "vouch_no", "align": "left", "rowSpan": 2, "type": "string", "columnindex": 2, "level":1, "width": 133 }, { "display": "摘要", "name": "summary", "align": "left", "rowSpan": 2, "type": "string", "columnindex": 3, "level":1, "width": 133 }, { "display": "补助内容", "name": "content_name", "align": "left", "colSpan": 6, "columnindex": 4, "level":1, "columns": [ { "display": "在职人员补助", "name": "fun_name", "align": "left", "type": "string", "columnindex": 4, "level":2, "width": 133 }, { "display": "支出经济分类科目", "name": "eco_name", "align": "left", "type": "string", "columnindex": 5, "level":2, "width": 133 }, { "display": "借方", "name": "debit", "align": "right", "formatter": "###,##0.00", "type": "string", "columnindex": 6, "level":2, "width": 133 }, { "display": "贷方", "name": "credit", "align": "right", "formatter": "###,##0.00", "type": "string", "columnindex": 7, "level":2, "width": 133 }, { "display": "方向", "name": "subj_dire", "align": "left", "type": "string", "columnindex": 8, "level":2, "width": 133 }, { "display": "余额", "name": "end_os", "align": "right", "formatter": "###,##0.00",//数据格式化 "type": "string", "columnindex": 9, "level":2, "width": 133 } ] } ] }


function officeGridPrint(param) {

	if (!param) {
		return;
	}

	//计算请求路径
	var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
	var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
	//当请求地址中不包含CHD-HRP的时候 特殊处理
	if(pathName.indexOf("CHD-HRP") != -1 ){
		split = pathName.split("/").length - 4;
	}else{
		split = pathName.split("/").length - 3;
	}
	
	var urlStr = "../";
	for (var i = 0; i < split; i++) {
		urlStr = urlStr + "../";
	}

	pathName = pathName.replace(".do", "").replace(/\//g, '{').replace("{CHD-HRP{hrp{", "");
	if (param.gridIndex) {
		pathName = pathName + "{" + param.gridIndex
	}
	param["page_url"] = pathName;
	//处理数据文档
	ajaxJsonObjectByUrl(urlStr + "PageOffice/printGridData.do?isCheck=false", param, function (responseData) {

		var paraStr = "";
		for (var key in responseData) {
			paraStr = paraStr + "&" + key + "=" + responseData[key];
		}

		var isChromeVer42 = existsChromeVer42(42);
		if (isChromeVer42) {
			//高于谷歌42版本
			POBrowser.openWindowModeless(urlStr + "PageOffice/printGridPre.do?isCheck=false" + paraStr, "fullscreen=yes;");
		} else {
			//低于谷歌42版本
			parent.openDialog({ url: "PageOffice/printGridPre.do?isCheck=false" + paraStr, data: {}, title: "打印预览", width: 0, height: 0 });
		}

	});

}

function getSessionJson() {

	var sessionJson = {};
	if (parent.sessionJson) {
		sessionJson = parent.sessionJson;
	} else if (parent.parent.sessionJson) {
		sessionJson = parent.parent.sessionJson;
	} else if (parent.parent.parent.sessionJson) {
		sessionJson = parent.parent.parent.sessionJson;
	} else if (parent.parent.parent.parent.sessionJson) {
		sessionJson = parent.parent.parent.parent.sessionJson;
	}
	return sessionJson;
}

//获取浏览器版本信息
function getBrowserInfo() {
	var agent = navigator.userAgent.toLowerCase();

	var regStr_ie = /msie [\d.]+;/gi;
	var regStr_ff = /firefox\/[\d.]+/gi
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	//IE
	if (agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie);
	}

	//firefox
	if (agent.indexOf("firefox") > 0) {
		return agent.match(regStr_ff);
	}

	//Chrome
	if (agent.indexOf("chrome") > 0) {
		return agent.match(regStr_chrome);
	}

	//Safari
	if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
		return agent.match(regStr_saf);
	}
}

//判断是否高于42版本
function existsChromeVer42(vs) {
	var isVer42 = true;
	var browser = getBrowserInfo();
	var verinfo = (browser + "").replace(/[^0-9.]/ig, "");
	if (verinfo != "") {
		verinfo = verinfo.substring(0, verinfo.indexOf("."));
		if (parseInt(verinfo) < vs) {
			isVer42 = false;
		}
	}
	return isVer42;
}


// 日期格式化计算
// dateStr   :日期字符串
// formatStr :格式化字符串
// state     :年月日 (year|month|day)
// num		 :增为正数 减为负数
// 如：
// mathDate('20171130', 'YYYYMMDD','day',31)  返回 '20171231'
// mathDate('201711','YYYYMM','month',1)      返回 '201712'
// mathDate('20171231','YYYYMMDD','day',1)    返回 '20180101'
function mathDate(dateStr, formatStr, state, num) {
	var lowerFormatStr = formatStr.toLowerCase();
	var d = matchDate(dateStr, lowerFormatStr);
	var date = MathDate(d, state, num);

	var resultDate = lowerFormatStr.replace('yyyy', date.getFullYear())
		.replace('mm', addZero(date.getMonth() + 1))
		.replace('dd', addZero(date.getDate()));

	return resultDate;

	// 匹配格式化于日期 返回一个对象 包含年月日
	function matchDate(dateStr, formatStr) {
		var dateFormat = ['yyyy', 'mm', 'dd'];
		var date = {};
		dateFormat.forEach(function (v, i) {
			var index = formatStr.indexOf(v.toLowerCase());
			var d = dateStr.substr(index, v.length) || 1;
			date[v] = d * 1;
		});
		return date;
	}


	// 计算日期加减  返回一个Date对象
	function MathDate(dateObj, flag, num) {
		var date = new Date(dateObj.yyyy, dateObj.mm - 1, dateObj.dd);
		if (flag === 'year') {
			date.setFullYear(date.getFullYear() + num);
		}

		if (flag === 'month') {
			date.setMonth(date.getMonth() + num);
		}

		if (flag === 'day') {
			date.setDate(date.getDate() + num);
		}
		return date;
	}

	// 小于10的数字 生成两位字符串 如: 1 => 01
	function addZero(v) {
		if (v < 10) return '0' + v;
		return v.toString();
	}
	
	function getWeekByDate(date){
		var sWeek = new Date(date).getDay();
		switch (sWeek){ 
			case  0: 
				sWeek='周日';  
				break;  
			case  1: 
				sWeek='周一';  
				break;  
			case  2: 
				sWeek='周二';  
				break;  
			case  3: 
				sWeek='周三';  
				break;  
			case  4: 
				sWeek='周四';  
				break;  
			case  5: 
				sWeek='周五';  
				break;  
			case  6: 
				sWeek='周六';  
				break;  
			default:  
				break;  
		};
		return sWeek;
	}
}

//阿拉伯数字转换为简写汉字
function numToC(Num) {
    for (i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")//替换Num中的“,”
        Num = Num.replace(" ", "")//替换Num中的空格
    }    
    if (isNaN(Num)) { //验证输入的字符是否为数字
        //alert("请检查小写金额是否正确");
        return;
    }
    //字符处理完毕后开始转换，采用前后两部分分别转换
    part = String(Num).split(".");
    newchar = "";
    //小数点前进行转化
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) {
            //alert("位数过大，无法计算");
            return "";
        }//若数量超过拾亿单位，提示
        tmpnewchar = ""
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0":  tmpnewchar = "零" + tmpnewchar;break;
            case "1": tmpnewchar = "一" + tmpnewchar; break;
            case "2": tmpnewchar = "二" + tmpnewchar; break;
            case "3": tmpnewchar = "三" + tmpnewchar; break;
            case "4": tmpnewchar = "四" + tmpnewchar; break;
            case "5": tmpnewchar = "五" + tmpnewchar; break;
            case "6": tmpnewchar = "六" + tmpnewchar; break;
            case "7": tmpnewchar = "七" + tmpnewchar; break;
            case "8": tmpnewchar = "八" + tmpnewchar; break;
            case "9": tmpnewchar = "九" + tmpnewchar; break;
        }
        switch (part[0].length - i - 1) {
            case 0: tmpnewchar = tmpnewchar; break;
            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 4: tmpnewchar = tmpnewchar + "万"; break;
            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
            case 8: tmpnewchar = tmpnewchar + "亿"; break;
            case 9: tmpnewchar = tmpnewchar + "十"; break;
        }
        newchar = tmpnewchar + newchar;
    }   
    //替换所有无用汉字，直到没有此类无用的数字为止
    while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
        newchar = newchar.replace("零亿", "亿");
        newchar = newchar.replace("亿万", "亿");
        newchar = newchar.replace("零万", "万");
        newchar = newchar.replace("零零", "零");      
    }
    //替换以“一十”开头的，为“十”
    if (newchar.indexOf("一十") == 0) {
        newchar = newchar.substr(1);
    }
    //替换以“零”结尾的，为“”
    if (newchar.lastIndexOf("零") == newchar.length - 1) {
        newchar = newchar.substr(0, newchar.length - 1);
    }
    return newchar;
}

	function showFileWord(param) {
	
		if (!param) {
			return;
		}
		//计算请求路径
		var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
		var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
		//当请求地址中不包含CHD-HRP的时候 特殊处理
		if(pathName.indexOf("CHD-HRP") != -1 ){
			split = pathName.split("/").length - 4;
		}else{
			split = pathName.split("/").length - 3;
		}
		var urlStr = "../";
		for (var i = 0; i < split; i++) {
			urlStr = urlStr + "../";
		}
	
		POBrowser.openWindowModeless(urlStr+'PageOffice/showWord.do?isCheck=false&paraName='+param.file_name,'width=1200px;height=700px;');
	
	}
	function showFile(file_path) {
		if (!file_path) {
			return;
		}
		//计算请求路径
		var pathName = window.location.pathname;//(/CHD-HRP/hrp/acc/accvouch/superPrint/printLoad.do)
		var split = pathName.split("/").length - 4;//(后台请求路径：PageOffice/printLoad)
		//当请求地址中不包含CHD-HRP的时候 特殊处理
		if(pathName.indexOf("CHD-HRP") != -1 ){
			split = pathName.split("/").length - 4;
		}else{
			split = pathName.split("/").length - 3;
		}
		
		var urlStr = "../";
		for (var i = 0; i < split; i++) {
			urlStr = urlStr + "../";
		}
		POBrowser.openWindowModeless(urlStr+'PageOffice/showFile.do?isCheck=false&file_path='+file_path,'width=1200px;height=700px;');
		
	}

/**
 * 弹出科目树（data对象需包含ligerId，acc_year， windowName，idStr，splitStr）
 * ligerId：元素节点ID
 * acc_year：会计年度
 * windowName：一般为window.name
 * idStr：元素节点为下拉框时value是否含有科目ID
 * splitStr：元素节点为下拉框时value的科目ID和科目CODE之间的特殊字符
 */
function showSubjTree(data){
	parent.$.ligerDialog.open({
		title :  '科目选择',
		width :  600,
		height :  $(window).height()-50,
		url :  'hrp/acc/books/subjTreePage.do?isCheck=false', 
		data: data, modal :  true, showToggle :  false, 
		showMax :  false, showMin :  false, isResize :  true,
		parentframename :  data.windowName
	});
}

/**
 * 图片打印
 * imgsNode：页面中图片节点集合
 */
function hrpPrintImgs() {
	
	var imgsNode = $(".upload-preView").find('img');
	
	if (!imgsNode) {
		$.ligerDialog.warn("页面中没有找到图片！");
		return false;
	}
	
	parent.$.ligerDialog.open({ 
		title: '图片打印',
		url : 'PageOffice/printImg.do?isCheck=false',
		height: $(window).height(),
		width: $(window).width(),
		data: {imgsNode: imgsNode}, 
		modal: true, showToggle: false, showMax: true,
		isResize: true, showMin: true, 
	}); 
}