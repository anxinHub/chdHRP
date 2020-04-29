// Save created XMLHttpRequest object for future use.
var cspXMLHttp = null;

// Set this variable to control is GET or POST method used to send request.
var cspUseGetMethod = false;

// Track which XMLHttpRequest object we are using
var cspMozilla = false;

// Execute the JavaScript from all simultaneously executed #call requests.
var cspMultipleCall = true;

// Save in process XMLHttpRequest objects
var cspActiveXMLHttp = null;

function cspFindXMLHttp(async)
{
	if (window.XMLHttpRequest) {
 	 	cspMozilla = true;
		cspXMLHttp = new XMLHttpRequest();

	} else if (window.ActiveXObject) {
 	 	cspMozilla = false;
		try {
			cspXMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
 		} catch (e) {
			try {
 		 		cspXMLHttp=new ActiveXObject("Msxml2.XMLHTTP");
			} catch (E) {
				cspXMLHttp=null;
			}
		}
	}

	return cspXMLHttp;
}

function cspIntHttpServerMethodMobile(method, args, async)
{
	var arg;
	var i;
	var url = zenPage.getProperty('remoteBrokerAddress');
	var n;
	var req;

	var data = "WARGC=" + (args.length - 1) + "&WEVENT=" + method.replace(/&amp;/,'&') + "&WMOBILE=1";
	for (i = 1; i < args.length; i++) {
		arg = args[i];
		if (typeof arg != "object") {
			// Convert boolean to Cache value before sending
			if (typeof arg == "boolean") arg = (arg ? 1 : 0);
			data = data + "&WARG_" + i + "=" + encodeURIComponent(arg);
		} else if (arg != null) {
			n = 0;
			for (var el in arg) {
				if (typeof arg[el] != "function") {
					data = data + "&W" + i + "=" + encodeURIComponent(arg[el]);
					n = n + 1;
				}
			}
			data = data + "&WLIST" + i + "=" + n;
		}
	}

	try {
		req=cspXMLHttp
		req.withCredentials = true;
		if (async) {
			if (cspMultipleCall) {
				if (cspActiveXMLHttp == null) cspActiveXMLHttp = new Array();
				cspActiveXMLHttp[cspActiveXMLHttp.length] = req;
				req.onreadystatechange = cspProcessMultipleReq;
			} else {
				req.onreadystatechange = cspProcessReq;
			}
		}
		cspXMLHttp = null;
		if (cspUseGetMethod) {
			req.open("GET", url+"?"+data, async);
			if (cspMozilla) {
				req.send(null);
			} else {
				req.send();
			}
		} else {
			req.open("POST", url, async);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send(data);
		}
	} catch (e) {
		var err=new cspHyperEventError(400,'Http object request failed, unable to process HyperEvent.',null,'',e);
		return cspHyperEventErrorHandler(err);
	}

	if (async) {
		return null;
	}

	return cspProcessResponse(req);
}

function cspIntHttpServerMethod(method, args, async)
{
	var arg;
	var i;
	var url = "%25CSP.Broker.cls";
	var n;
	var req;

	var data = "WARGC=" + (args.length - 1) + "&WEVENT=" + method.replace(/&amp;/,'&');
	for (i = 1; i < args.length; i++) {
		arg = args[i];
		if (typeof arg != "object") {
			// Convert boolean to Cache value before sending
			if (typeof arg == "boolean") arg = (arg ? 1 : 0);
			data = data + "&WARG_" + i + "=" + encodeURIComponent(arg);
		} else if (arg != null) {
			n = 0;
			for (var el in arg) {
				if (typeof arg[el] != "function") {
					data = data + "&W" + i + "=" + encodeURIComponent(arg[el]);
					n = n + 1;
				}
			}
			data = data + "&WLIST" + i + "=" + n;
		}
	}

	try {
		req=cspXMLHttp
		if (async) {
			if (cspMultipleCall) {
				if (cspActiveXMLHttp == null) cspActiveXMLHttp = new Array();
				cspActiveXMLHttp[cspActiveXMLHttp.length] = req;
				req.onreadystatechange = cspProcessMultipleReq;
			} else {
				req.onreadystatechange = cspProcessReq;
			}
		}
		cspXMLHttp = null;
		if (cspUseGetMethod) {
			req.open("GET", url+"?"+data, async);
			if (cspMozilla) {
				req.send(null);
			} else {
				req.send();
			}
		} else {
			req.open("POST", url, async);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send(data);
		}
	} catch (e) {
		var err=new cspHyperEventError(400,'Http object request failed, unable to process HyperEvent.',null,'',e);
		return cspHyperEventErrorHandler(err);
	}

	if (async) {
		return null;
	}

	return cspProcessResponse(req);
}

function cspProcessMultipleReq() {
	var i = 0;
	var current;
	// Use try/catch to avoid error if the window has already been closed when response arrives.
	try {
		while (i < cspActiveXMLHttp.length) {
			if (cspActiveXMLHttp[i].readyState == 4) {
				current=cspActiveXMLHttp[i];
				cspActiveXMLHttp.splice(i,1);
				cspProcessResponse(current);
			} else {
				i++;
			}
		}
	} catch (e) {
	}
}

function cspProcessReq() {
	// Use try/catch to avoid error if the window has already been closed when response arrives.
	try {
		if (cspXMLHttp.readyState == 4) {
			cspProcessResponse(cspXMLHttp);
		}
	} catch (e) {
	}
}

function cspProcessResponse(req) {
	if(req.status != 200) {
		var errText='Unexpected status code, unable to process HyperEvent: ' + req.statusText + ' (' + req.status + ')';
		var err = new cspHyperEventError(req.status,errText);
		return cspHyperEventErrorHandler(err);
	}

	var i;
	var result=req.responseText;
	var lines = result.split("\r\n");
	var ok = 0;
	var len=lines.length;
	if (lines[len-1]=="") len=len-1;
	for (i=2; i<len; i++) {
		if (lines[i]=="#OK") {
			ok = i;
			break;
		}
	}

	if ((ok==0) || ((lines[1]!="#R") && (lines[1]!="#V"))) {
		var err = new cspHyperEventError(500,'Http object response incomplete or invalid.'+ok+","+lines[1]);
		return cspHyperEventErrorHandler(err);
	}

	var result = null;
	if (lines[1] == "#R") {
		var result = "";
		if (ok+1 < len) {
			result = lines[ok+1];
			for (i = ok + 2; i < len; i++) {
				result = result + "\r\n" + lines[i];
			}
		}
	}

	var js = '';
	if (ok > 2) {
		js = lines[2];
		for (i = 3; i < ok; i++) js = js + "\r\n" + lines[i];
		try {
			var bidding = new Function('CSPPage', js);
			bidding(self);
		} catch (ex) {
			var err = 'A JavaScript exception was caught during execution of HyperEvent:\n';
			err += ex.name + ': ' + ex.message + '\n';
			err += '--------------------------------------\nResult: ' + result + '\n';
			err += '--------------------------------------\nJavaScript code:\n';
			err += js;
			var mainErr = new cspHyperEventError(400,err,null,'',ex,arguments);
			return cspHyperEventErrorHandler(mainErr);
		}
	}

	// Allow caller to display a debug window.
	if ((typeof cspRunServerDebugWindow != "undefined") && cspRunServerDebugWindow) {
		var debugwin=window.open("","DebugWindow","scrollbars,resizable,height=400,width=700");
		if (debugwin.document.f == null) {
			debugwin.document.write("<html><head></head><body>");
			debugwin.document.title="HyperEvent Debug Window";
			debugwin.document.write('<form name="f">');
			debugwin.document.write('<br>Javascript<br><textarea name="js" cols="80" rows="12">');
			debugwin.document.write(js);
			debugwin.document.write('</textarea>');
			debugwin.document.write('<br>Return Value<br><textarea name="r" cols="80" rows="2">');
			debugwin.document.write(result);
			debugwin.document.write('</textarea>');
			debugwin.document.write('</form>');
			debugwin.document.write("</body></html>");
		} else {
			debugwin.document.f.js.value = js;
			debugwin.document.f.r.value = result;
		}
	}

	return result;
}

function cspHttpServerMethod(method)
{
	if (cspFindXMLHttp(false) == null) {
		var err = new cspHyperEventError(400,'Unable to locate XMLHttpObject.');
		return cspHyperEventErrorHandler(err);
	}

	return cspIntHttpServerMethod(method,cspHttpServerMethod.arguments,false);
}

function cspCallHttpServerMethod(method)
{
	if (cspFindXMLHttp(true) == null) {
		var err = new cspHyperEventError(400,'Unable to locate XMLHttpObject.');
		return cspHyperEventErrorHandler(err);
	}

	return cspIntHttpServerMethod(method,cspCallHttpServerMethod.arguments,true);
}

function cspHttpServerMethodMobile(method)
{
	if (cspFindXMLHttp(false) == null) {
		var err = new cspHyperEventError(400,'Unable to locate XMLHttpObject.');
		return cspHyperEventErrorHandler(err);
	}

	return cspIntHttpServerMethodMobile(method,cspHttpServerMethodMobile.arguments,false);
}

function cspCallHttpServerMethodMobile(method)
{
	if (cspFindXMLHttp(true) == null) {
		var err = new cspHyperEventError(400,'Unable to locate XMLHttpObject.');
		return cspHyperEventErrorHandler(err);
	}

	return cspIntHttpServerMethodMobile(method,cspCallHttpServerMethodMobile.arguments,true);
}

function cspHyperEventErrorHandler(error)
{
	if (typeof cspRunServerMethodError == 'function')
			return cspRunServerMethodError(error.text,error);
	alert(error.text);
	return null;
}

function cspHyperEventError(code,text,serverCode,serverText,ex,args)
{
	this.code = code;
	this.text = text;
	this.serverCode = (!serverCode ? null : serverCode);
	this.serverText = (!serverText ? '' : serverText);
	this.exception = (!ex ? null : ex);
	this.arguments = (!args ? null : args);
}