// Copyright (c) 2001 TrakHealth Pty Limited. ALL RIGHTS RESERVED.

//WEB SYS
//Use filename as a prefix to identify global variables and public functions

// MS IE - the one and only global flag (may need to change the definition)
var websys_isIE=false;
var msg=new Array();
var ieVersion = 6.0;		//增加 ms ie 版本变量 2013-09-26
//IE11 appName = Netcape
if (window.navigator.appName=='Microsoft Internet Explorer') {
	var userAgent = window.navigator.userAgent;
	var userAgentArr = userAgent.split(";")	
	var msieArr = userAgentArr[1].split("MSIE ");
	if(msieArr.length>1){
		ieVersion = parseFloat(msieArr[1]);	
	}
	websys_isIE=true;
};
if (!!window.ActiveXObject || "ActiveXObject" in window){
	websys_isIE=true;
}
var websys_topz = 201;
var websys_url = ''; //lookuppage
var websys_webedit;
var websys_isInUpdate=false;
var websys_brokerTime=200;
//add by zzq 2010.08.31


//override cache's broker call to use cache's xmlhttp calls to server to avoid csp applet
function cspRunServerMethod(method) {
	if (method=="") return null;
	if (cspFindXMLHttp(false) == null) {
		err='Unable to locate XMLHttpObject.';
		if (typeof cspRunServerMethodError == 'function')
			return cspRunServerMethodError(err);
		alert(err);
		return null;
	}
	return cspIntHttpServerMethod(method,cspRunServerMethod.arguments,false);
	//return cspHttpServerMethod(method,cspRunServerMethod.arguments);
}
/// cspRunServerMethod by id
function cspRunServerMethodById(Id){
	if (Id=="") return nul;
	var obj = document.getElementById(Id);
	if (obj){
		var method = obj.value;
		return cspRunServerMethod(method); 
	}
}
// add by wuqk 2010-11-19
// modify by wanghc 2016-04-28 errobj
function cspRunServerMethodError(err,errobj){
        //2015+ Cache--
	if(arguments.length>1){
		if (errobj.serverText){
			alert(err+" : "+errobj.serverText);
			return ;
		}
	}
	if (confirm(err+" refresh?")) { 
		window.location.href =window.location.href ;
		}
}
function websys_putontop() {
	websys_topz += 1;
	return websys_topz;
}
//new window lookups and others
function websys_lu(url,lookup,posn) {
	if (lookup) { //always use same window for lookups
		if (posn=='') posn='top=100,left=680,width=300,height=380';
		//use different window for lookups with custom component
		if (lookup==1) {
			websys_createWindow(url,0,posn+",titlebar=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes");
		} else {
			websys_createWindow(url,lookup,posn+",titlebar=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes");
		}
	} else {
		//else use new window name based on time now
		if (posn=='') posn='width=300,height=380';
		//if its a word document, show full screen
		if (url.indexOf('.DOC')>0) posn='top=0,left=0,width='+screen.availWidth+',height='+screen.availHeight;
		var n=new Date();
		var winname='a'+n.getHours()+n.getMinutes()+n.getSeconds()+n.getMilliseconds();
		websys_createWindow(url,winname,posn+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
	}
	return false;
}
function websys_locked() {
	var l=0;
	for (var i=0; i<document.forms.length; i++) {
		if ((document.forms[i].elements['TLOCKED'])&&(document.forms[i].elements['TLOCKED'].value=='1')) {l=1;break;}
	}
	return l;
}
function websys_help(url,e) {
	if (typeof e != "undefined") {
		var keycode=websys_getKey(e);
	} else {
		var keycode=websys_getKey();
	}
	if (keycode==113) { //F2
		// websys_createWindow(url,"HELPWINDOW","width=500,height=380,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes");
	}
}
function websys_printme() {
	//IE if not ready to print then.. try again a bit later
	if (document.readyState!='complete') {
		self.setTimeout(websys_printme,1000);
	} else {
		self.focus();
		self.print();
	}
}
function websys_cancelBackspace(e){
	var keycode;
	try {keycode=websys_getKey(e);} catch(e) {keycode=websys_getKey();}
	var key=String.fromCharCode(keycode);
	// wanghc 2017-4-28 Cancel Backspace
	try{
		if (keycode==8){
			var srcEl = websys_getSrcElement(e);
			var srcElementTag = srcEl.tagName.toUpperCase();
			var scrElementType="";
			if (srcElementTag=="INPUT"){
				scrElementType = srcEl.type.toUpperCase();
			}
			if (srcElementTag=="INPUT"&&scrElementType=="CHECKBOX"){
				websys_cancel(); return false;
			}
			if (srcElementTag=="INPUT"&&scrElementType=="RADIO"){
				websys_cancel(); return false;
			}		
			if ((srcElementTag!=="INPUT")&&(srcElementTag!=="TEXTAREA")&&(srcElementTag!=="SELECT")){
				websys_cancel();return false;
			}
		}
	}catch(e){}
	return true;
}
function websys_sckey(e) { //ALT-* PGUP PGDN
	var keycode;
	try {keycode=websys_getKey(e);} catch(e) {keycode=websys_getKey();}
	var key=String.fromCharCode(keycode);
	if (!websys_cancelBackspace(e)) return false;
	if ((websys_getAlt(e)&&keycode!=18)||(keycode==33)||(keycode==34)) {
		try {
			var key=String.fromCharCode(keycode);
			//if firing shortcut key from field with broker, fire broker first
			key=key.toUpperCase();

			var x=websys_getSrcElement(e);
			if (x.onchange) {
				x.onchange();
				//field's broker sets focus to next tab sequence, need to put focus back to shortcut element.
				for (i in tsc) {
					if (tsc[i]==key) {window.setTimeout("websys_setfocus('"+i+"');",websys_brokerTime+1); break;}
				}
			}

			websys_sckeys[key]();
			websys_cancel();
		}
		//catch and ignore
		catch(e) {}
	}	
}
function websys_sckeypress(e) { //ALT-* PGUP PGDN
	//Don't allow the user to enter ^ (MS Only)
	//this has been separated from websys_sckey as Thai uses Shift+6 as modifier (does not result in ^)
	if (window.event) {
		var keycode;
		try {keycode=websys_getKey(e);} catch(e) {keycode=websys_getKey();}
		var key=String.fromCharCode(keycode);
		if (key=='^') {
				window.event.cancelBubble;
				return false;
		}
	}
}

var websys_sckeys=new Array();
if (websys_isIE==true) {//MS
	document.onkeydown=websys_sckey;
	document.onkeypress=websys_sckeypress;
	}
else { //NS
	//document.onkeypress=websys_sckey;
	document.addEventListener("keydown",websys_sckey)
}


//development tools
function websys_dev() {
	try {
		websys_webedit=null;
		websys_webedit=new self.ActiveXObject("trakWebedit3.trakweb");
		var params=session['LOGON.USERID'] + '^' + session['LOGON.CTLOCID'];
		websys_webedit.ShowManager(params);
		websys_webedit=null;
	}
	catch (e) {
		alert(unescape(t['XLAYOUTERR']));
	}
}

function websys_layout(compid,sconn,context) {
	try {
		websys_webedit=null;
		websys_webedit=new ActiveXObject("trakWebedit3.trakweb");
		//place holder for prefid
		var params=session['LOGON.USERID'] + '^' + session['LOGON.CTLOCID'] + '^^' + session['LOGON.GROUPID'];
		if (context=='') context=session['CONTEXT'];
		websys_webedit.ShowLayout(params,compid,context,sconn);
		websys_webedit=null;
	}catch (e) {
		alert(unescape(t['XLAYOUTERR']));
	}
}
//returns the topmost window of main window (the one with the menu and the TRAK_main)
function websys_getMainWindow(w) {
	if (!w) return null;
	if ((w.top.frames.length)&&(w.top.frames['TRAK_main'])) return w.top;
	else return websys_getMainWindow(w.opener);
}
//Show hide nested component
function websys_component(obj,id) {
	var	row=document.getElementById(id);
	if (row) {
		/*if (row.childNodes[1].innerHTML=="") {
			var lnk="websys.component.shownested.csp?WEBSYSWIN="+window.name+"&NCID="+id;
			var win=null;
			win=websys_getMainWindow(window.top);
			if (win) win.frames['TRAK_hidden'].location=lnk;
			else window.open(lnk,"TRAK_hidden");
		}*/

		if (row.style.display=='none') {
			row.style.display='';
			obj.src="../images/websys/minus.gif";
		} else {
			row.style.display='none';
			obj.src="../images/websys/plus.gif";
		}
	}
	return websys_cancel();
}

//Event functions - DOM compatibilty
function websys_getSrcElement(e) {
	if (window.event)
		return window.event.srcElement;
	else {
		return e.target;
	}
}
function websys_getParentElement(obj) {
	if (obj.parentElement)
		return obj.parentElement;
	else {
		return obj.parentNode;
	}
}
function websys_getChildElements(obj) {
	if (document.all) return obj.all;
	else return obj.getElementsByTagName("*");
}
function websys_getType(e) {
	if (window.event)
		return window.event.type;
	else
		return e.type;
}
function websys_getKey(e) {
	/*ie11,chrome,都不支持重写keyCode,gen中判断117弹放大镜*/
	if (e && e.isLookup) return 117;
	//watch out - 'which' gets set for mouse events as well as keyboard events
	if (window.event)
		return window.event.keyCode;
	else
		return e.which;
}
//returns either "L"|"M"|"R"|n rather than button number for cross browser compatibility
function websys_getButton(e) {
	//if (window.event) return window.event.button;
	//else return e.which;
	if ((typeof e != "undefined")&&(typeof e.button != "undefined")) { //W3C compliant
		return (e.button==0 ? "L" : (e.button==1 ? "M" : (e.button==2 ? "R" : e.button) ) );
	}
	if (window.event) {
		return (window.event.button==1 ? "L" : (window.event.button==4 ? "M" : (window.event.button==2 ? "R" : window.event.button) ) );
	} else {
		return (e.which==1 ? "L" : (e.which==2 ? "M" : (e.which==0 ? "R" : e.which) ) );
	}
}
function websys_getAlt(e) {
	if (window.event)
		return window.event.altKey;
	else
		return e.altKey;
		//return ( (e.modifiers & Event.ALT_MASK) ? true : false);
}
function websys_getOffsets(e) {
	if (window.event) {
		var offsets = {
			offsetX: event.offsetX,
			offsetY: event.offsetY
		}
		return offsets;
	} else {
		var target = e.target;
		if (typeof target.offsetLeft == 'undefined') {
			target = target.parentNode;
		}
		var pageCoords = websys_getPageCoords(target);
		var offsets = {
			offsetX: window.pageXOffset + e.clientX - pageCoords.x,
			offsetY: window.pageYOffset + e.clientY - pageCoords.y
		}
		return offsets;
	}
}
function websys_getPageCoords(obj) {
	var coords = {x : 0, y : 0};
	while (obj) {
		coords.x += obj.offsetLeft;
		coords.y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	return coords;
}


function websys_cancel() {
	if (window.event)
		window.event.cancelBubble=true;
	return false;
}
function websys_returnEvent() {
	//if (window.event)
	//	window.event.returnValue=false;
	return false;
}


//Menus - id is menu counter i.e. 1,2,.. this is a unqiue number
//Show sub menus
function websys_show(id) {
	var mnu=document.getElementById('tbMenu'+id);
	var itm=document.getElementById('tbMenuItem'+id);
	mnu.className='tbMenuHighlight';
	//There may not be a sub menu item
	if (itm) {
		itm.className='tbMenuItem';
		//itm.style.top = 23;
		itm.style.left= itm.parentNode.parentNode.offsetLeft;
		//itm.style.top = itm.parentNode.parentNode.offsetTop+20;	//wanghc
	}
	return websys_cancel();
}
//Hide sub menus
function websys_hide(id) {
	var mnu=document.getElementById('tbMenu'+id);
	var itm=document.getElementById('tbMenuItem'+id);
	mnu.className='tbMenu';
	//There may not be a sub menu item
	if (itm) {
		itm.className='tbMenuItemHide';
	}
	return websys_cancel();
}

//set title bar
function websys_setTitleBar(title) {
	if (title == "") {
		title = "TrakHealth MedTrak";
	}
	top.document.title = title;
}

//determine if anything has changed on the form
//good for IE
function websys_isDirty(eForm)
{
  var iNumElems = eForm.elements.length;
  for (var i=0; i<iNumElems; i++)
  {
    var eElem = eForm.elements[i];
    if ("text" == eElem.type || "TEXTAREA" == eElem.tagName)
    {
	  if (eElem.name != 'UserCode') {
		//if (eElem.value != eElem.defaultValue) alert(eElem.name);
        if (eElem.value != eElem.defaultValue) return true;
      }
    }
    else if ("checkbox" == eElem.type || "radio" == eElem.type)
    {
      //if (eElem.checked != eElem.defaultChecked) alert(eElem.name);
      if (eElem.checked != eElem.defaultChecked) return true;
    }
    else if ("SELECT" == eElem.tagName)
    {
      var cOpts = eElem.options;
      var iNumOpts = cOpts.length;
      for (var j=0; j<iNumOpts; j++)
      {
        var eOpt = cOpts[j];
        //if (eOpt.selected != eOpt.defaultSelected) alert(eElem.name);
        if (eOpt.selected != eOpt.defaultSelected) return true;
      }
    }
  }
  return false;
}
//determine if anything has changed on any form
function websys_isDirtyPage(eForm)
{
	var iNumForms=document.forms.length;
	for (var i=0; i<iNumForms; i++)
	{
		if (websys_isDirty(document.forms[i])) return true;
	}
	return false;
}
function websys_isDirtyPagePrompt()
{	//IE will prompt regardless, but we can include our own message
	if ((!websys_isInUpdate)&&(websys_isDirtyPage())) window.event.returnValue=t['XUNSAVED'];
}

function websys_canfocus(obj) {
	if ( ((obj.tagName=="INPUT")||(obj.tagName=="A")||(obj.tagName=="TEXTAREA")||(obj.tagName=="SELECT"))
	&& (obj.type!="hidden") && (!obj.disabled) && (!obj.readOnly) ) {
		return 1;
	}
	return 0;
}
//jump to first visible form control
function websys_firstfocus() {
	for (var i=0,found=0; (i<document.forms.length)&&(!found); i++) {
		//need to be able to focus on "<A>" buttons as well
		var objs=websys_getChildElements(document.forms[i]);
		for (var j=0; j<objs.length; j++) {
			if ( websys_canfocus(objs[j]) ) {
				websys_setfocus(objs[j].id);
				found=1;
				break;
			}
		}
	}
}
//jump to next element given an index
//currently good for MS
function websys_nextfocus(index) {
	for (var j=index+1;j<document.all.length;j++) {
		if ( websys_canfocus(document.all(j)) ) {
			websys_setfocus(document.all(j).id);
			break;
		}
	}
}
//jump to next element according to page source positioning given an element
function websys_nextfocusElement(obj) {
	//if MSIE .sourceIndex exists call original websys_nextfocus as it's faster
	if (obj.sourceIndex) {websys_nextfocus(obj.sourceIndex); return};
	while (1) {
		obj=websys_FindNextSourceElement(obj,1);
		if (!obj) break;
		if (websys_canfocus(obj)) {websys_setfocus(obj.id); break;}
	}
}
//find the next element according to page source positioning given an element
function websys_FindNextSourceElement(obj,checkChild) {
	if (obj==document.body) return null;
	if ((checkChild)&&(obj.childNodes.length)) {
		if (obj.childNodes[0].nodeType==1) return obj.childNodes[0];  //return obj.firstChild;
		else return websys_FindNextSourceElement(obj.childNodes[0],0);
	} else if ((obj.nextSibling)) {
		if (obj.nextSibling.nodeType==1) return obj.nextSibling;
		else return websys_FindNextSourceElement(obj.nextSibling,0);
	} else if ((obj.parentNode.nextSibling)) {
		if (obj.parentNode.nextSibling.nodeType==1) return obj.parentNode.nextSibling;
		else return websys_FindNextSourceElement(obj.parentNode.nextSibling,0);
	} else {
		return websys_FindNextSourceElement(obj.parentNode,0);
	}
}
//jump to next element by tabindex (lowest of those above index)
//frm is now passed in to speed up from screen that have both edit and list components
function websys_nexttab(index,frm) {
	var next='';
	var nextidx=9999;
	index=parseInt(index,10);
	//if (frm) objs=frm.all; else objs=document.all;
	if (frm) var objs=websys_getChildElements(frm);
	else var objs=document.all;
	for (var j=0;j<objs.length;j++) {
		if ( websys_canfocus(objs[j]) ) {
			if (objs[j].tabIndex>index&&(objs[j].tabIndex<nextidx)) {
				nextidx=objs[j].tabIndex;
				next=objs[j].name;
				if (nextidx==(1+index)) break; //quit as soon as we find the very next one
			}
		}
	}
	if (next!='') {
		websys_setfocus(next);
	}
}
//jump to first element by tabindex that is invalid (className=="clsInvalid")
function websys_firsterrorfocus() {
	var next='';
	var nextidx=9999;
	var index=-1;
	var objs=websys_getChildElements(document);
	for (var j=0;j<objs.length;j++) {
		if ( (objs[j].className=="clsInvalid") && (websys_canfocus(objs[j])) ) {
			if (objs[j].tabIndex>index&&(objs[j].tabIndex<nextidx)) {
				nextidx=objs[j].tabIndex;
				next=objs[j].name;
			}
		}
	}
	if (next!='') {
		websys_setfocus(next);
	}

}
//sets focus on field... called from lookup when item is not selected
function websys_setfocus(objName) {
	setTimeout('websys_setfocus2(\''+objName+'\')',50);
}
function websys_setfocus2(objName) {
	var obj=document.getElementById(objName);
	if (obj) {
		try {
			obj.focus();
			obj.select();
		} catch(e) {}
	}
}
//used in place of javascript escape which causes problems with Greek which gets treated as unicode
function websys_escape(str) {
	//must convert the % first.
	if (str.indexOf("%")>-1) {
		str=str.split("%").join("%25");
	}
	while (str.indexOf("?")>-1) {
		str=str.replace("?","%3F");
	}
	while (str.indexOf("=")>-1) {
		str=str.replace("=","%3D");
	}
	while (str.indexOf(" ")>-1) {
		str=str.replace(" ","%20");
	}
	while (str.indexOf("\"")>-1) {
		str=str.replace("\"","%22");
	}
	while (str.indexOf("&")>-1) {
		str=str.replace("&","%26");
	}
	while (str.indexOf("#")>-1) {
		str=str.replace("#","%23");
	}
	while (str.indexOf(String.fromCharCode(13,10))>-1) {
		str=str.replace(String.fromCharCode(13,10),"%0D%0A");
	}
	if (str.indexOf("+")>-1) {
		str=str.split("+").join("%2B");
	}
	while (str.indexOf(String.fromCharCode(1))>-1) {
		str=str.replace(String.fromCharCode(1),"%01");
	}
	while (str.indexOf(String.fromCharCode(2))>-1) {
		str=str.replace(String.fromCharCode(2),"%02");
	}
	return str;
}
//KM 20-Mar-2001: make size dynamic to the size of its largest DIV.
//KM 18-Sep-2001: This function is rarely used now as it has mostly been superceeded by websys_reSizeT().  Need to think about removing it.
function websys_reSize() {
	var h=0;var w=0;
	//var f=this.document.body.all;
	var f=websys_getChildElements(this.document.body);
	for (var i=0;i<f.length;i++) {
		if (f[i].tagName=="DIV") {
			if (f[i].offsetHeight>h) h=f[i].offsetHeight;
			if (f[i].offsetWidth>w) w=f[i].offsetWidth;
		}
	}
	if (h>eval(window.screen.Height-window.screenTop)) h=eval(window.screen.Height-window.screenTop)-40;
	if (w>eval(window.screen.Width-window.screenLeft)) w=eval(window.screen.Width-window.screenLeft)-40;
	this.resizeTo(w+30,h+45); window.focus();
}
function websys_reSizeT(e) {
	var w=0;var h=0;
	//var f=this.document.body.all;
	var f=websys_getChildElements(this.document.body);
	for (var i=0;i<f.length;i++) {
		if (f[i].tagName=="TABLE") {
			if (f[i].offsetWidth>w) w=f[i].offsetWidth;
			//if (f[i].id) h+=f[i].offsetHeight;
		}
	}
	if (w>eval(window.screen.Width-window.screenLeft)) w=eval(window.screen.Width-window.screenLeft)-40;
	if (w<282) w=282;
	if (h>eval(window.screen.Height-window.screenTop)) h=eval(window.screen.Height-window.screenTop)-40;
	h=document.body.offsetHeight;

	//if (h<document.body.clientHeight) h=document.body.clientHeight;
	if (h<380) h=380;
	this.resizeTo(w+30,h);
	this.resizeBy(0,27); // ab 7.05.02 - to combat lookups growing smaller
}
function websys_move(left,top,width,height) {
	//add TRAK_main by wuqk 2010-05-23
	//if ((window.top.frames["eprmenu"])||(window.top.frames["TRAK_menu"])) {return;}
	if ((window.top.frames["eprmenu"])||(window.top.frames["TRAK_menu"])||(window.top.frames["TRAK_main"])) {return;}
	var xwin = null;
	if (window.opener) { xwin = window; } else {xwin = window.top;}
	//with some sanity checking - centered forms will pass in precalculated values
	if (left<0) left=0;
	if (top<0) top=0;
	if (width<200) width=200;
	if (height<200) height=200;
	// move to a position
	xwin.moveTo(left,top);
	xwin.resizeTo(width,height);
}
function websys_getPositionX(e) {
	if (window.event) {return window.event.x;} else {return e.clientX;}
}
function websys_getPositionY(e) {
	if (window.event) {return window.event.y;} else {return e.clientY;}
}
//KM 4-Apr-2001: Window Manager Functions
var websys_windows=new Array();
function websys_createWindow(url, wname, features) {
	//if ((url.indexOf("websys.csp?")==0)&&(websys_locked())) url+="&TLOCKED=1";
	if (typeof features != 'undefined') {
		features=features.toUpperCase();
		if (features.indexOf('STATUS=')==-1) features="status,"+features;
		if (features.indexOf('SCROLLBARS=')==-1) features="scrollbars,"+features;
		if (features.indexOf('RESIZABLE=')==-1) features="resizable,"+features;
	}
	//ie8-showModalDialog-->session problem
	var openobj = window;
	if(ieVersion>6){
		if(typeof(window.dialogArguments) == "object")
		{
			if((window.dialogArguments)&&(window.dialogArguments.hasOwnProperty('window'))){
				openobj =  window.dialogArguments.window;
				openobj.dialogWindow = window;	//把模态窗口window对象放到src窗口的dialogWidow属性中,websys.lookup.csp中用到
			}
		}
	}
	features = features||"";
	features = features.toLowerCase();
	var obj={};
	var arr = features.split(",");
	for (var ind=0;ind<arr.length;ind++){
		var item = arr[ind];
		var itemArr = item.split("=");
		if (itemArr.length==1){ //status,resizable,scrollbars,
			obj[itemArr[0]] = "yes";
		}else{
			if (itemArr[1]=="true" || itemArr[1]=="false"){
				obj[itemArr[0]] = itemArr[1]=="true"?true:false;
			}else{
				obj[itemArr[0]] = itemArr[1]||"";
			}	
		}
	}
	if (features.indexOf('hisui=true')==-1 || 'undefined' == typeof websys_showModal){
		obj = calcPosition(obj,true);
		if (obj.top>30){obj.top -=30;}
		if (obj.left>10){obj.left -=10;}
		features = "";
		for (var pind in obj){
			if (features=="") features =pind+"="+obj[pind];
			else features +=","+pind+"="+obj[pind];
		}
		websys_windows[wname]=openobj.open(url, wname, features); 
	}else{
		if (obj["iconcls"]){
			obj.iconCls = obj["iconcls"];
		}else{
			obj.iconCls = "icon-w-paper";
		}
		if (websys_showModal) websys_showModal($.extend(obj,{url:url}));
	}
	//websys_windows[wname]=window.open(url, wname, features);
	//websys_windows[wname].focus();
	return websys_windows[wname];
}
function websys_closeWindows() {
	for (w in websys_windows) {
		try {
			if (!websys_windows[w].closed) {websys_windows[w].isclosing=1;websys_windows[w].close();}
		} catch(e) {}
	}
}
function websys_onunload() {
	websys_closeWindows();
}
window.onunload=websys_onunload;
//KM: End of Window Manager Functions

//KK 10/Dec/2002 Log 30023: User print options
function websys_print(printoptions,url,params,target,javascript){
	var tablename="";
	try{
		var eSrc=websys_getSrcElement();
		//getTableName is in websys.List.Tools.js
		if(eSrc) var tbl=getTableName(eSrc);
		if(tbl) tablename=tbl.id;
	}catch(e){}
	if (printoptions==1) {
		/* KK: Commented out - not working yet
		//Check for the selected rows before loding the print options component
		var rowselected=0;
		if (tbl){
			for (var i=1;i<tbl.rows.length;i++) {
				if (tbl.rows[i].className=="clsRowSelected") rowselected=1;
			}
			if (rowselected==0){
				alert("No Rows Selected");
				return "";
			}
		}
		*/
		var CONTEXT = "";
		var objCONTEXT=document.getElementById('CONTEXT');
		if (objCONTEXT) {
			CONTEXT = objCONTEXT.value;
		}
		//convert &'s so all parameters can be passed
		url=escape(url);
		var turl="websys.default.csp?WEBSYS.TCOMPONENT=websys.PrintOptions.Edit&printoptions="+printoptions+"&url="+url+"&params="+params+"&CONTEXT="+CONTEXT;
		turl+="&target="+target+"&javascript="+javascript+"&tablename="+tablename;
		//+"&tempurl="+url
		//Create a new window
		websys_createWindow(turl,1,"width=260,height=180,top=20,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes");
	}else {
		var jobdone=0;
		if (javascript!=""){
			//Build the javascript string with the lnk(url) and newwin(target)
			//Look for the javascript function on the same page
			//Otherwise look in the parent window
			if (tablename!=""){
				javascript=javascript + "('" + tablename + "','" + url + "','" + target + "')"
			}else{
				javascript=javascript + "('" + url + "','" + target + "')"
			}
			try{
				jobdone=1;
				eval(javascript);
			}catch(e) {}
			if ((jobdone==0) && (window.opener)) {
				javascript="window.opener." + javascript;
				eval(javascript);
			}
		}else {
			//if no javascript redirect page to url + params
			websys_createWindow(url,target);
		}
	}
}


 function websys_$(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($(arguments[i]));
    return elements;
   }
  if (typeof element=="string")
   return document.getElementById(element);
 }


 function websys_$V(element) {
  return websys_$(element).value;
}
function $V(element){
  return websys_$(element).value;
}
//增加事件的方法
  function addHand(h,k,r )
  {
    if(h.addEventListener) {
	 if (k.indexOf("on")!=-1) {
		k=k.substr(2);
	 }
     h.addEventListener(k,r,false)
	}else if(h.attachEvent) {
     h.attachEvent(k,r)
	}else{
	 var M=h[k];
      h[k]=function(){
	  var x=M.apply(this,arguments),t=r.apply(this,arguments);
      return x==undefined?t:(t==undefined?x:t&&x)
      }
	}
  }
var resizeListContainer = function(){
	 var listDiv = document.getElementsByClassName("i-tbListContainer")
	 if (listDiv && listDiv.length && listDiv.length>0) {
		 listDiv[0].style.height = 500;
	 	 listDiv[0].style.width = 1000;
	 }
}
var quickK={
f7:function(){ window.location.href =window.location.href ;},
f8:'',
f9:'',
addMethod : function()
{
  if(typeof this.f7 =='function')
  addHand(document.body,'onkeydown',function(){if(event.keyCode==118) quickK.f7();});
  if(typeof this.f8 =='function')
  addHand(document.body,'onkeydown',function(){if(event.keyCode==119) quickK.f8();});
  if(typeof this.f9 =='function')
  addHand(document.body,'onkeydown',function(){if(event.keyCode==120) quickK.f9();});
}
};

 String.prototype.toDate = function()
 {
  
  var dd = this.split(/\D/);
  if(dd.length==1) {
   dd[0]=this.substring(0,4);
   dd[1]=this.substring(4,6);
   dd[2]=this.substring(6,8);
   }
  if(dd.length!=3) return "";
  if(dd[1].length==1) dd[1]="0"+dd[1];
  if(dd[2].length==1) dd[2]="0"+dd[2];
  var str = dd.join('-');
  if(!str.match(/^\d{4}\-\d\d?\-\d\d?$/)){return   "";}   
  var ar=str.replace(/\-0/g,"-").split("-");   
  ar=new Array(parseInt(ar[0]),parseInt(ar[1])-1,parseInt(ar[2]));   
  var d=new Date(ar[0],ar[1],ar[2]);   
  if( d.getFullYear()==ar[0]   &&   d.getMonth()==ar[1]   &&   d.getDate()==ar[2])
  return str;
  return "";
 
 }
 /** side menu form */
function dhcsys_getsidemenuform(){
	var frm = null;
	try{
		var win=top.frames['TRAK_menu'];
		if (win) {
			frm = win.document.forms['fEPRMENU'];
			if(frm) return frm;
		}
		//在新窗口打开的界面
		win = opener;
		if (win) {
			frm = win.document.forms['fEPRMENU'];
			if (frm) return frm;
		}
		win = parent.frames[0];
		if (win){
			frm = win.document.forms["fEPRMENU"];
			if (frm) return frm;
		}
		if (top){
			frm =top.document.forms["fEPRMENU"];
			if(frm) return frm
		}
	}catch(e){}
	return frm;
}
function websys_getMenuWin(){
	var win = null;
	try{
		var win=top.frames['eprmenu'];
		if (win) {
			frm = win.document.forms['fEPRMENU'];
			if(frm) return win;
		}
		win = parent.frames[0];
		if (win){
			frm = win.document.forms["fEPRMENU"];
			if (frm) return win;
		}
		if (top){
			frm =top.document.forms["fEPRMENU"];
			if(frm) return top;
		}
		// opener位置放到后面,如果是从portal转到his的界面,opener就跨域了
		//在新窗口打开的界面
		win = opener;
		if (win) {
			frm = win.document.forms['fEPRMENU'];
			if (frm) return win;
		}
		//在新窗口打开的界面,且包含多个iframe
		win = top.opener;
		if (win) {
			frm = win.document.forms['fEPRMENU'];
			if (frm) return win;
		}
		//main界面open开新界面中调用,8.0+
		win = top.opener;
		if (win) {
			frm = win.top.document.forms['fEPRMENU'];
			if (frm) return win.top;
		}
		win = top.opener.opener;
		if (win) {
			frm = win.top.document.forms['fEPRMENU'];
			if (frm) return win.top;
		}
	}catch(e){}
	return win;
}
function dhcsys_getmenuform(){
	var frm = null;
	try{
		var win = websys_getMenuWin();
		if (win){
			return win.document.forms['fEPRMENU'];
		}else{
			//当前界面的fEPRMENU对象
			return window.fEPRMENU;
		}
	}catch(e){return window.fEPRMENU;}
	return frm;
}
//@input {String} ctloc  科室的id或desc
//@return {Object} obj  
//如果不用ca认证返回{IsSucc:true,ContainerName:""}
//如果要CA认证但失败返回{IsSucc:false,ContainerName:""},成功返回{IsSucc:true,ContainerName:strContainerName}
function dhcsys_calogon(locDesc){
 	var ret = {};
 	var flag = tkMakeServerCall("websys.SessionLogon","IsCaLogon",locDesc);
 	if(flag==1){
	 	var strContainerName = "";	 	
	 	var strContainerName = window.showModalDialog("../csp/dhc.logon.ca.csp", {window:window}, "dialogWidth:300px;dialogHeight:250px;center:yes;toolbar=no;menubar:no;scrollbars:no;resizable:no;location:no;status:no;help:no;");		
		if ((strContainerName=="")||(strContainerName=="undefined")||(strContainerName==null)) {
			return {IsSucc:false,ContainerName:""};
		}else{
			ret = {ContainerName:strContainerName,IsSucc:true};
		}
 	}else{
	 	ret = {IsSucc:true,ContainerName:""};
	}
 	return ret;
}

//@return {String} ContainerName^ContainerPin
function dhcsys_getContainerNameAndPin(){
	var cainfo = tkMakeServerCall("websys.SessionLogon","GetContainerNameAndPin");
	return cainfo;
	//return session['ContainerName'];
}
//非登录页面调用的接口方法,判断是不是已CA认证
//@return {Object} obj  
//如果不用ca认证返回{IsSucc:true,varCert:"",ContainerName:""}
//如果要CA认证但失败返回{IsSucc:false,varCert:"",ContainerName:""},
//              成功返回{IsSucc:true,varCert:varCert,ContainerName:ContainerName};
function dhcsys_getcacert(){
	var obj={} ,varCert="",ContainerName = "",CTLoc = "";
	var failRtn = {IsSucc:false,varCert:"",ContainerName:""};
	if (session['LOGON.CTLOCID']){CTLoc = session['LOGON.CTLOCID'];}
	//if (session["ContainerName"]){ContainerName = session["ContainerName"];}
	if (CTLoc=="") return {IsSucc:true,varCert:"",ContainerName:""};;
	var ContainerNameStr = tkMakeServerCall("websys.SessionLogon","GetContainerNameAndPin");
	if (ContainerNameStr.indexOf("^")>-1) ContainerName = ContainerNameStr.split("^")[0];
	//if (session["ContainerName"]){ContainerName = session["ContainerName"];}
 	
 	var flag = tkMakeServerCall("websys.SessionLogon","IsCaLogon",CTLoc);
 	if(flag!=1){
	 	return {IsSucc:true,varCert:"",ContainerName:""};
	}
	if(ContainerName){
		// 判断有没有登录过
		if (XTXAPP && XTXAPP.SOF_IsLogin){
			var caLoginFlag = XTXAPP.SOF_IsLogin(ContainerName);
			if (caLoginFlag){ 
				varCert = GetSignCert(ContainerName);
				if(varCert){
					return {IsSucc:true,varCert:varCert,ContainerName:ContainerName};
				}
			}
		}
		// 没登录过则弹出CA登录框
		obj = dhcsys_calogon(CTLoc);
		if(obj.IsSucc){
			if(obj.ContainerName){
				if (XTXAPP && XTXAPP.SOF_IsLogin){
					var caLoginFlag = XTXAPP.SOF_IsLogin(obj.ContainerName);
					varCert = GetSignCert(obj.ContainerName);
					return {IsSucc:true,varCert:varCert,ContainerName:obj.ContainerName};
				}
			}
		}
	}
	return failRtn;
}

/**
*@param {String} msg  提示的信息
*@param {String} dialogWidth 窗口宽 如:300
*@param {String} dialogHeight 窗口高 如:400
*@param {String} fontSize 字体  如:14
*dhcsys_alert("测试提示",200,100,14);
*/
function dhcsys_alert(msg,dialogWidth,dialogHeight,fontSize){
	if(websys_isIE){ //ieVersion>6){
			var width = dialogWidth||300;
			var height = dialogHeight||120;
			window.showModalDialog("../csp/alert.html", 
				{msg:msg,fontSize:fontSize}, 
				"dialogWidth:"+parseFloat(width)+"px;dialogHeight:"+parseFloat(height)+"px;center:yes;toolbar=no;menubar:no;scrollbars:no;resizable:no;location:no;status:no;help:no;");	
	}else{
		alert(msg);
	}       
}
/**
*@param {String} msg  提示的信息
*@param {String} YesOrNo 默认焦点在Yes上还是No上 可选值为YES或NO.默认为Yes
*@param {String} fontSize 字体大小 14px
*@param {Stirng} btnValue 按钮文本   如: 是|否 , 自费|公费. 默认是|否
*var rtn = dhcsys_confirm("我是一个测试","YES","14","自费|公费");
*/
function dhcsys_confirm(msg,YesOrNo,fontSize,btnValue){
	if(websys_isIE) { //ieVersion>6
		if(arguments.length==1){
			YesOrNo = "YES";
		}
		return window.showModalDialog("../csp/confirm.html", 
					{msg:msg,YesOrNo:YesOrNo,fontSize:fontSize,btnValue:btnValue}, 
					"dialogWidth:300px;dialogHeight:120px;center:yes;toolbar=no;menubar:no;resizable:no;location:no;status:no;help:no;");	
	}else{
		return window.confirm(msg);
	}
}
/*
* @param {String} modelname  模块名称
* @param {String} condition  条件串
* @param {String} content    内容串
* @param {String} secretCode 密级代码 
* @return  
* 			保存失败返回 "-100^ErrorCode"
*				成功返回  日志表rowid 
*/
function websys_EventLog(modelname, condition, content,secretCode){
	var rtn = tkMakeServerCall("web.DHCEventLog","EventLog",modelname, condition, content,secretCode);
	return rtn;
}
/**
* @param {String} url 链接路径 如: https://127.0.0.1/dthealth/web/csp/test.csp?EpisodeId=1&UserId=1
* @param {Object} obj 直接对象 如: {"EpisodeId":2,OrderId:"2||1"}
* @return {String} 组合后的url串 如:https://127.0.0.1/dthealth/web/csp/test.csp?EpisodeId=2&UserId=1&OrderId=2||1
*/
function rewriteUrl(url, obj){
	var reg,flag, indexFlag = false;
	var indexFlag = (url.indexOf("?")==-1);
	if(indexFlag){
		url += "?";	
	}
	for (var i in obj){	
		if(obj.hasOwnProperty(i)){
			flag = false; 	
			if(!indexFlag){
				reg =  new RegExp(i+"=(.*?)(?=$|&)","g");
				url = url.replace(reg, function(m1){
					flag = true;
					return i+"="+obj[i];
				});
			}
			if(!flag){
				url  +=   "&" + i + "=" + obj[i];
			}
		}
	}
	return url;	
}
function websys_rewriteUrl(url,obj){
	rewriteUrl(url,obj);
}

function websys_getTextContent(obj){
	if (websys_isIE)
		return obj.innerText;
	else {
		return obj.textContent;
	}
}
///add button -->gray disabled
function removeClass(obj, itmCls){
	if (obj.className){
		var classArr = obj.className.split(" ");
	    for(var i=0; i<classArr.length; i++){
	        if(classArr[i]==itmCls){
		        classArr.splice(i,1)
		    }
	    }
	    obj.className = classArr.join(" ")
	}
    return obj;
}
function addClass(obj,itmCls){
	if (obj && obj.className){
		var classArr = obj.className.split(" ");
	    for(var i=0; i<classArr.length; i++){
	        if(classArr[i]==itmCls){
		        return obj
	        }
	    }
	    classArr.push(itmCls)
	    obj.className = classArr.join(" ");
	}else{
		obj.className = itmCls;
	}
    return obj;
}
function websys_removeClass(obj, itmCls){
	removeClass(obj, itmCls)
}
function websys_addClass(obj,itmCls){
	addClass(obj,itmCls)
}
function emptyFun(event){return false;}
/**
*@param {HTMLElement|String} obj|domId dom对象
*@desc disable obj
*/
function websys_disable(obj) {
	if (arguments.length>0){
		if (typeof obj == "string"){
			obj = document.getElementById(obj);
		}
	}else{
		return ;
	}
	if(obj.tagName=="A"){
		obj.onclick = emptyFun;
	}
	obj.setAttribute('disabled','disabled');
	addClass(obj,"i-disabled");
}
/**
* @param {HTMLElement} obj dom对象
* @param {function} fun 事件方法
* @desc 显示obj并为他加上点击事件方法
*/
function websys_enable(obj,fun) {
	if (arguments.length>0){
		if (typeof obj == "string"){
			obj = document.getElementById(obj);
		}
	}else{
		return ;
	}
	obj.removeAttribute('disabled');
	if(obj.tagName=="A"){
        if(arguments.length>1) obj.onclick = fun;
	}
	removeClass(obj,"i-disabled");
}
// exec('cmd.exe /c "time 10:40"')
 function exec(command) 
 { 
	window.oldOnError=window.onerror; 
	window._command=command; 
	window.onerror= function(err){ 
		if(err.indexOf('utomation')>-1) { 
			alert('命令'+window._command+'已经被用户禁止！'); 
			return true; 
		}else{  
			return false;  
		} 
	}; 
	var wsh=new ActiveXObject('WScript.Shell');
	if (wsh) {
		wsh.Run(command);
	} 
	window.onerror = window.oldOnError;
 }
 function websys_exec(command){
	 exec(command);
 }
/*获得top值*/ 
 function getOffsetTop(o){
	var top=0;
	var offsetParent = o;
	while (offsetParent != null && offsetParent != document.body)
	{	
		top += parseInt(offsetParent.offsetTop) 
		offsetParent = offsetParent.offsetParent;
	}
	return top;
}
function resizeWidth(){
	var tblListFlexHeader = document.getElementByClass("tblListFlexHeader");
	tblListFlexHeader
}

function GetServerIP(){
	return tkMakeServerCall("ext.util.String","ServerIP");
}

function dhcsys_getComputerName()
{
	var regedit=new RegEdit();
	var computerName = regedit.regRead("HKEY_CURRENT_USER\\Volatile Environment\\ViewClient_Machine_Name");
	if ((computerName!="")&&(computerName!=null))  return computerName;
	var computerName;
	try
	{
	   var WshNetwork=new ActiveXObject("WScript.Network"); 
	   computerName=WshNetwork.ComputerName;
	   //WshNetwork.UserDomain
	   //WshNetwork.UserName
	   WshNetwork = null;
	} catch(e) {
	   computerName="";
	}
	return computerName;
}

function RegEdit(){
	this.shell = new ActiveXObject("WScript.Shell");
	this.regRead = regRead;
	this.regWrite = regWrite;
	this.regDelete = regDelete;
}
/** 返回名为 strName 的注册键或值。
* @param strName 要读取的键或值。如果 strName 以反斜线 (\) 结束，本方法将返回键，而不是值
* @return 名为 strName 的注册键或值
*/
function regRead(strName){
	var val = null;
	try {
   		val = this.shell.regRead(strName);
	} catch (e) {
   		//alert(e.message);
	}
	return val;
}

/** 设置 strName 指定的注册键或值
* @param strName 要写的键或值的名称.如果 strName 以反斜线 (\) 结束，本方法将返回键，而不是值
* @param anyValue 要写入键或注册表值中的值
* @param strType 可选项。要保存到注册表中的值的数据类型REG_SZ、REG_EXPAND_SZ、REG_DWORD、REG_BINARY
*/
function regWrite(strName,anyValue,strType){
	if(strType == null)
   	strType = "REG_SZ";
	this.shell.regWrite(strName,anyValue,strType);
}

/** 从注册表中删除 strName 指定的键或值。
* @param strName 要删除的键或值的名字。如果 strName 以反斜线 (\) 结束，本方法将删除键，而不是值
*/
function regDelete(strName){
	this.shell.regDelete(strName);
}
/*
*@date 2017-02-09
*@params {String}  xmlName  xml模板名称 DHCHXXYPrescNoPYD1
*@params {String}  params request参数 如EpisodeID=12&PatientID=12&loc=内二科门诊
*如果xml配置了类方法或query则可用此方法打印
*xml打印方法
*websys_printout("DHCHXXYPrescNoPYD1","EpisodeID=12&PatientID=12&loc=内二科门诊")
*/
function websys_printout(xmlName,params){
	var href = "dhctt.xmlprint.csp?xmlName="+xmlName+"&"+params;
	websys_createWindow(href,"TRAK_hidden","width=330,height=160,top=1200,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes");
	return ;
}
/*
*@date 2017-02-09
*@params {String}  xmlName  xml模板名称 如: DHCHXXYPrescNoPYD1
*@params {String}  params request参数   如: EpisodeID=12&PatientID=12&loc=内二科门诊&showPrintBtn=1
*@params {String}  opt 窗口位置与大小   如: width=530,height=460,top=100,left=100
*如果xml配置了类方法或query则可用此方法预览
*xml打印预览
*websys_printview("DHCHXXYPrescNoPYD1","EpisodeID=12&PatientID=12&loc=内二科门诊")
*/
function websys_printview(xmlName,params,opt){
	if (typeof opt == "undefined" ){
		opt = "width=630,height=560,top=100,left=100,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes";
	} 
	var href = "dhctt.xmlpreview.csp?xmlName="+xmlName+"&"+params
	//var href = "dhctt.xmlprint.csp?xmlName="+xmlName+"&"+params+"&preview=1";
	websys_createWindow(href,"preview",opt+",toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes");
	return ;
}
/**
* @param {Window} parentFraObj   在这个框架内查找
* @param {String} fraName        要查找的框架名
* @return {Window}    返回要找的框架对象
* 在指定界面内查找指定名称的框架对象
* dhcsys_getFrameObjByName(top,"TRAK_main");
*/
function dhcsys_getFrameObjByName(parentFraObj,fraName) {
	var ObjList=[];
	if (typeof parentFraObj.frames =="undefined") {
        return null
    }
	function getFrameObjByName(parentObj){
		
	    if (parentObj.name==fraName) {
	    	ObjList.push(parentObj);
	    }
	    for (var i = 0; i < parentObj.frames.length; i++) {
	        getFrameObjByName(parentObj.frames[i]);
	    }
	}

    getFrameObjByName(parentFraObj);

    if (ObjList.length > 0) return ObjList[0]

    return null
}
/**
* @param {String} colname 组件列名. 可以为空,为空则把inname输入框值设为空
* @param {String} inname 输入框名
* @other 把列元素值放到输入框内,一般用里选中组件行时。
*/
var websys_SetValByCol = function(colname, inname){
	if (inname=="") return ;
	var inputObj = document.getElementById(inname);
	if (!inputObj) return ;
	if (colname=="") {
		if (inputObj.type.toUpperCase()=="CHECKBOX"){
			inputObj.checked = false;
		}else{
			inputObj.value = "";
		}
		return ;
	}
	var obj = document.getElementById(colname);
	var val;
	if (obj){
		if (obj.tagName=="LABEL"){
			val = obj.innerText;
		}
		if (obj.tagName=="INPUT"){
			val = obj.value;
		}
		if (inputObj.type.toUpperCase()=="CHECKBOX"){
			if (val=="Y" || val=="是"){
				inputObj.checked = true;
			}else{
				inputObj.checked = false;
			}
		}else{
			inputObj.value = val;
		}
	}
}
var websys_getVal = function(inname){
	if (inname=="") return "";
	var inputObj = document.getElementById(inname);
	if (!inputObj) return ;
	if (inputObj.type.toUpperCase()=="CHECKBOX"){
		if (inputObj.checked){
			return "Y";
		}else{
			return "N";
		}
	}
	if (inputObj.tagName=="LABEL"){
		val = inputObj.innerText;
	}
	if (inputObj.tagName=="INPUT"){
		val = inputObj.value;
	}
	if (inputObj.tagName=="SELECT"){
		var index=inputObj.selectedIndex ;
		val = myselect.options[index].value;
		//myselect.options[index].text;
	}
	return val;
	
}
/**
* @param {String} str 字符串
* @return {String} 去掉左右空格后字符串
*/
function websys_trim(str) { 
  return str.replace(/(^\s*)|(\s*$)/g, ''); 
}
/**
* @param {String} str 字符串
* @return {String} 去掉左空格后字符串
*/
function websys_trimLeft(str){   
	return str.replace(/(^\s*)/g,"");  
}  
/**
* @param {String} str 字符串
* @return {String} 去掉右空格后字符串
*/
function websys_trimRight(str){   
	return str.replace(/(\s*$)/g,"");  
}  

function GetLocalIPAddr(){ 
 var oSetting = null; var ip = null; try{ oSetting = new ActiveXObject("rcbdyctl.Setting"); ip = oSetting.GetIPAddress; 
 if (ip.length == 0){ return "没有连接到Internet"; } oSetting = null; }catch(e){ return ip; } return ip;
}
/**
* @param {Object} 参数
* 把top,left,width,heigth中包含百分比的转成固定值
* 
*/
function calcPosition(opt,win){
	var outerHeight = top.document.documentElement["clientHeight"] ;//$(window)._outerHeight();
	var outerWidth = top.document.documentElement["clientWidth"]; //$(window)._outerWidth();	
	if (win){
		// window.open打开时
		outerHeight = window.screen.availHeight;
		outerWidth = window.screen.availWidth;
	}
	if ("undefined"==typeof opt.width){opt.width="80%";}
	if ("undefined"==typeof opt.height){opt.height="80%";}
	if ("undefined"==typeof opt.top){opt.top="";}
	if ("undefined"==typeof opt.left){opt.left="";}
	if ("string" == typeof opt.top && opt.top!=""){
		if (opt.top.indexOf("%")>-1){
			opt.top = parseFloat(opt.top)*0.01*outerHeight; //document.body.clientHeight;
		}
		opt.top = parseInt(opt.top);
	}
	if ("string" == typeof opt.left && opt.left!=""){
		if (opt.left.indexOf("%")>-1){
			opt.left = parseFloat(opt.left)*0.01*outerWidth; //document.body.clientWidth;
		}
		opt.left = parseInt(opt.left);
	}
	if ("string" == typeof opt.width){
		if (opt.width.indexOf("%")>-1){
			opt.width = parseFloat(opt.width)*0.01*outerWidth; //document.body.clientWidth;
		}
		opt.width = parseInt(opt.width);
	}
	if ("string" == typeof opt.height){
		if (opt.height.indexOf("%")>-1){
			opt.height = parseFloat(opt.height)*0.01*outerHeight; //document.body.clientHeight;
		}
		opt.height = parseInt(opt.height);
	}
	if(opt.left==''){ 
		opt.left = (outerWidth - opt.width)/2; //winJObj.window(opt).window("open").window("vcenter");
	}
	if(opt.top==''){ 
		opt.top = (outerHeight - opt.height)/2; //winJObj.window(opt).window("open").window("hcenter");
		//($(window)._outerHeight() - opt.width) / 2 + $(document).scrollTop()			
	}
	if (opt.left<0){opt.left=0;}
	if (opt.top<0){opt.top=0;}
	return opt;
}
/**
* @param {config} 配置项
* @other 在框架界面打开模态窗口
* websys_showModal({url:'http://www.baidu.com',title:'关于iMedical',width:860,height:592});
* websys_showModal({title:'关于iMedical',content:'我是内容',width:860,height:592});
	websys_showModal({
		url:'websys.default.hisui.csp?WEBSYS.TCOMPONENT=DHCSSUserLogonLog',
		title:'关于iMedical',
		onBeforeClose:function(){alert('close')},
		dataRow:{userId:1,userName:"张病历"},
		width:1160,height:592
	});
	var opt = websys_showModal("options");
	console.log(opt.dataRow);
	websys_showModal("close");
	return ;
*/
function websys_showModal(opt){
	if (typeof opt == 'string'){
		var windowJObj="";
		if (top.$){ //&& top.$("#WinModalEasyUI").length>0
			// windowJObj = top.$("#WinModalEasyUI");
			var countWin = top.$('div[data-type="websysmodel"]').length;
			var windowJObj = top.$("#WinModalEasyUI"+(countWin-1));
		}
		if (windowJObj && windowJObj.length>0){
			if (opt=='hide'){
				windowJObj.window("minimize");
				return ;
			}
			if (opt=='minimize'){
				windowJObj.window("minimize");
				return ;	
			}
			if (opt=='close'){
				windowJObj.window("close"); //close");
				//windowJObj.window("destroy"); //close");
				return ;
			}
			if (opt=='options'){
				return windowJObj.window("options");
			}
		}
		return ;
	}
	// 
	if (top!=window && top.websys_showModal ){ //框架内的界面都是在top上打开	
		top.websys_showModal(opt); //("#WinModalEasyUI",opt.url,opt);
	}else{
		var defOpt = {modal:true,isTopZindex:false,resizable:true,closable:true,width:"80%",height:"80%",top:'',left:'',
		collapsible:false,minimizable:false,maximizable:false,title:"对话框",showbefore:null,showafter:null,content:"无内容",onClose:function(){$(this).window("destroy")}
		};
		if(opt.url){
			opt.content = '<iframe src="'+opt.url+'" width="100%" height="100%" scrolling="auto" marginwidth=0 marginheight=0 frameborder="no" framespacing=0></iframe>';
		}
		if (!$.browser){
			$.browser = {} ;
			$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());  
			$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());  
			$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());  
			$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
		}
		opt = $.extend({},defOpt,opt);
		opt = calcPosition(opt);
		var winJObj="",countWin = $('div[data-type="websysmodel"]').length
		/*if (document.getElementById("WinModalEasyUI")){
			winJObj = $("#WinModalEasyUI");
		}else{
			winJObj = $('<div id="WinModalEasyUI'+countWin+'" data-type="websysmodel"></div>').appendTo("body");	
		}*/
		// <div id="WinModalEasyUI" style="padding:0px;overflow:hidden;" data-options="isTopZindex:true"></div>
		if(winJObj=="") winJObj = $('<div id="WinModalEasyUI'+countWin+'"  style="padding:0px;overflow:hidden;"  data-type="websysmodel"></div>').appendTo("body");	
		winJObj.window(opt).window("open").window("move",{top:opt.top,left:opt.left});
	}
}
/**
* @param {String} str 需求要转成UTF8的字符串
* @return {Array} 对应的UTF8数组
*/
function toUTF8Array(str){
    var arr = str.split('');
    var newArr=[];
    for (var i=0;i<arr.length; i++){
        var item  = arr[i];
        var utf8Item = encodeURI(item); 
        if (item==utf8Item){
            newArr.push(item.charCodeAt(0));
        }else{
            var tmpArr = utf8Item.split("%");
            for (var j=1;j<tmpArr.length;j++){
                newArr.push(parseInt(tmpArr[j],16));
            }
        }
    }
    return newArr;
}
/**
* @param {String} src 需要加密码的字符串
* @param {String} key 密钥
*/
function simpleEncrypt(src,key){
	key = key||"IMedical";
    var rtn = [];
    var srcList = toUTF8Array(src);
    var len = srcList.length;
    var ReKeyLen = len/key.length;
    var okey="";
    while(ReKeyLen-->0){okey += key;}
    var c='',c16=0;
    for (var i=0;i<len;i++){
        c =  srcList[i]^okey.charAt(i).charCodeAt(0);
        c16 = c.toString(16).toUpperCase();
        if (c<=15){c16="0"+c16;}
        rtn.push(c16);
    }
    return rtn.join('');
}