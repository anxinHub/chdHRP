/*
	CSPBroker.js
	Copyright (c) 2000 InterSystems Corp. ALL RIGHTS RESERVED.
*/

function cspInsertCode(object,event,code)
{
	// create a new handler function
	// if there was an old one, invoke it from the new one.

	if (object['old' + event] != null) {
		// handler already added
		return;
	}

	var old = '';

	if (object[event] != null) {
		object['old' + event] = object[event];
		old = 'return old' + event + '();'
	}

	object[event] = new Function(code + old);
}

function cspUnloadPopup()
{
	if (self.cspPopupWindow != null) {
		self.cspPopupWindow.close();
	}

	return true;
}

function cspGetSearchValues(form)
{
	var query = '';
	for (var i = 1; i < arguments.length; i++) {
		query = query + ((i > 1) ? '&' : '') + 'PARM=';
		if (form[arguments[i]] != null) {
			query = query + cspEncodeUTF8(form[arguments[i]].value);
		}
	}
	return query;
}

function cspFindCookie(name)
{
	var one;
	var all = document.cookie.split('; ');
	for (var i = 0; i < all.length; i++) {
		one = all[i].split('=');
		if (name == one[0]) {
			return unescape(one[1]);
		}
	}
	return null;
}

function cspOpenSession(target,url,name,features,replace)
{
	var win = null;
	var cookie = cspFindCookie('CSPSESSIONID');

	// get rid of session cookie to force new window to have a new session
	self.document.cookie = 'CSPSESSIONID=' + escape('garbage') + '; expires=Fri, 02-Jan-1970 00:00:00 GMT';

	if (cspOpenSession.arguments.length == 2) {
		win = target.open(url);
	}
	else if (cspOpenSession.arguments.length == 3) {
		win = target.open(url,name);
	}
	else if (cspOpenSession.arguments.length == 4) {
		win = target.open(url,name,features);
	}
	else if (cspOpenSession.arguments.length == 5) {
		win = target.open(url,name,features,replace);
	}

	// restore value of cookie
	self.document.cookie = 'CSPSESSIONID=' + escape(cookie);

	return win;
}

// Convert JavaScript boolean value to Cache %Boolean (1 or 0)
// in order for #server and #call %Boolean arguments to have a consistent value.
function cspMakeBoolean(boolarg)
{
	// Comment "return boolarg ? 1 : 0;" and uncomment "return boolarg;"
	//   to return to previous functionality for compatibility.

	// The next line converts true/false to 1/0
	return boolarg ? 1 : 0;

	// The next line does no conversion and is the previous default -- initially commented.
	//return boolarg
}

// utilities used by bound forms

// test if the specified field within a form is empty
function cspIsFieldEmpty(form,field)
{
	var element=self.document[form][field];
	var val;

	if ((element.type == 'select-one') || (element.type == 'select-multiple')) {
		val = cspGetSelectList(element);
		return (val.length == 0)
	}


	val = element.value;

	if ((val == null) || (val == '')) {
		return true;
	}

	// strip white space and test
	for (var i=0; val.length > i; i++) {
		var c = val.charAt(i);
		if ((c != ' ') && (c != '\n') && (c != '\t')) {
			return false;
		}
	}

	return true;
}

// trim the trailing spaces from a string
function cspTrim(string)
{
	for (var i = string.length - 1; i >= 0; i--) {
		if (string.charAt(i) != ' ') {
			return string.substr(0, i + 1);
		}
	}

	return '';
}

// trim the trailing spaces from a string
function cspString(string)
{
	return (string.length > 0) ? string : '';
}

// Normalize the line ending characters within a string so that
// they are always \r\n.
function cspNormalizeString(string)
{
	return (string.length > 0) ? string.replace(/\r\n|\n/g,"\r\n") : '';
}

// return the current value of select control 'select'
function cspGetSelectValue(select)
{
	var opt;
	var values;

	if (select == null) {
		return null;
	}

	if (select.type == 'select-one') {
		values = (select.selectedIndex < 0) ? '' : select.options[select.selectedIndex].value;
	} else if (select.type == 'select-multiple') {
		for (var i=0; i < select.options.length; i++) {
			opt = select.options[i];
			if (opt.selected) {
				values = opt.value;
			}
		}
	}

	return values;
}

// return the current list of selected values of select control 'select'
function cspGetSelectList(select)
{
	var opt;
	var index;
	var values = new Array();

	if (select == null) {
		return null;
	}

	if (select.type == 'select-one') {
		if (select.selectedIndex >= 0) {
			values[0] = select.options[select.selectedIndex].value;
		}
	} else if (select.type == 'select-multiple') {
		index = 0;
		for (var i=0; i < select.options.length; i++) {
			opt = select.options[i];
			if (opt.selected) {
				values[index] = opt.value;
				index = index + 1;
			}
		}
	}

	return values;
}

// set the selected value of select control 'select'
function cspSetSelectValue(select,val)
{
	var selected = false;

	if (select == null) {
		return;
	}

	for (var i = 0; select.options.length > i; i++) {
		if (select.options[i].value == val) {
			select.selectedIndex = i;
			selected = true;
		}
	}

	if (!selected) {
		select.selectedIndex = -1;
	}
}

// set the list of selected values of multiple select control 'select'
function cspSetSelectList(select,valList)
{
	if (select == null) {
		return;
	}

	select.selectedIndex = -1;  // clear all
	for (var i = 0; i < select.options.length; i++) {
		for (var j = 0; j < valList.length; j++) {
			if (select.options[i].value == valList[j]) {
				select.options[i].selected = true;
				break;
			}
		}
	}
}

// return the current value of radio control 'radio'
function cspGetRadioValue(radio)
{
	if (radio == null) {
		return null;
	}

	for (var i = 0; radio.length > i; i++) {
		if (radio[i].checked == 1) {
			return radio[i].value;
		}
	}

	return '';
}

// return the current list of checked values of multiple checkbox
function cspGetCheckList(checks)
{
	var index;
	var values = new Array();

	if (checks == null) {
		return null;
	}

	index = 0;
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked) {
			values[index] = checks[i].value;
			index = index + 1;
		}
	}

	return values;
}

// set the list of selected values of multiple checkboxes
function cspSetCheckList(checks,valList)
{
	if (checks == null) {
		return;
	}

	for (var i = 0; i < checks.length; i++) {
		checks[i].checked=false;
		for (var j = 0; j < valList.length; j++) {
			if (checks[i].value == valList[j]) {
				checks[i].checked = true;
				break;
			}
		}
	}
}

// Escape the '+' character as well as those escaped by Javascript escape.
// This matches up unescaping that is done by CSP.
function cspEscape(str)
{
	return escape(str).replace('+','%2B');
}

var cspHexChars = "0123456789ABCDEF";

function cspEncodeChar(ch) {
	return '%' + cspHexChars.charAt(ch >> 4) + cspHexChars.charAt(ch & 0x0F);
}

function cspEncodeUTF8(s) {
	if (s==null || s.length==0) {
		return "";
	}

	if (typeof encodeURIComponent == "function") {
		return encodeURIComponent(s);
	}

	var sbuf = '';
	var len;
	var i;
	var ch;

	s=s+""
	len = s.length;

	for (i = 0; i < len; i++) {
		ch = s.charCodeAt(i);
		if ( (65 <= ch && ch <= 90) || 		// 'A'..'Z'
		     (97 <= ch && ch <= 122) ||		// 'a'..'z'
		     (46 <= ch && ch <= 57) ) {		// '.', '/', '0'..'9'
			sbuf += s.charAt(i);
		} else if (ch == 32) {                   // space
			sbuf += '+';
		}

		else if (ch <= 0x007f) {                // other ASCII
			sbuf += cspEncodeChar(ch);
		} else if (ch <= 0x07FF) {                // non-ASCII <= 0x7FF
			sbuf += cspEncodeChar(0xc0 | (ch >> 6));
			sbuf += cspEncodeChar(0x80 | (ch & 0x3F));
		} else {                                  // 0x7FF < ch <= 0xFFFF
			sbuf += cspEncodeChar(0xe0 | (ch >> 12));
			sbuf += cspEncodeChar(0x80 | ((ch >> 6) & 0x3F));
			sbuf += cspEncodeChar(0x80 | (ch & 0x3F));
		}
	}
	return sbuf;
}
