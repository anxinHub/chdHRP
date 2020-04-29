STR_UINTIP = 1;
STR_QLOGIN_VERSION_ERR = 2;
STR_NO_UIN = 3;
STR_NO_PWD = 4;
STR_NO_VCODE = 5;
STR_INV_UIN = 6;
STR_INV_VCODE = 7;
STR_UIN = 8;
STR_PWD = 9;
STR_VCODE = 10;
STR_VCODE_TIP = 11;
STR_CHANGE_VCODE = 12;
STR_REMEMBER_PWD = 13;
STR_1_DAY = 14;
STR_1_WEEK = 15;
STR_1_MONTH = 16;
STR_HALF_YEAR = 17;
STR_1_YEAR = 18;
STR_FORGET_PWD = 19;
STR_LOGIN = 20;
STR_RESET = 21;
STR_SWITCH_QLOGIN = 22;
STR_LOGIN_TITLE = 23;
STR_QLOGIN_INTRO = 24;
STR_QLOGINING = 25;
STR_QLOGIN_HELP = 26;
STR_SWITCH_NORMAL = 27;
STR_QLOGIN = 28;
STR_QLOGIN_BUSY = 29;
STR_QLOGIN_OFFLINE = 30;
STR_QLOGIN_OTHER_ERR = 31;
STR_BACK = 32;
STR_RETRY = 33;
STR_NEW_REG = 34;
STR_QLOGIN_SELECT = 35;
STR_QLOGIN_VER = 36;
STR_QLOGIN_SELECT_TIP = 37;
STR_QLOGIN_NO_UIN = 38;
STR_QLOGIN_SELECT_OFFLINE = 39;
my_version = unescape("version1.0.0");
function ptui_trim(A) {
	return A.replace(/(^\s*)|(\s*$)/g, "")
}
function ptui_str(A) {
	A -= 1;
	if (A >= 0 && A < g_strArray.length) {
		return g_strArray[A]
	}
	return ""
}
function ptui_mapStr(B) {
	for (i = 0; i < B.length; i++) {
		var A = document.getElementById(B[i][1]);
		if (A != null) {
			if ("A" == A.nodeName || "U" == A.nodeName
					|| "OPTION" == A.nodeName || "LABEL" == A.nodeName
					|| "P" == A.nodeName) {
				if (A.innerHTML == "") {
					A.innerHTML = ptui_str(B[i][0])
				}
			} else {
				if ("INPUT" == A.nodeName) {
					if (A.value == "") {
						A.value = ptui_str(B[i][0])
					}
				} else {
					if ("IMG" == A.nodeName) {
						A.alt = ptui_str(B[i][0])
					}
				}
			}
		}
	}
}
function ptui_onUserFocus(C, A) {
	var B = document.getElementById(C);
	if (ptui_str(STR_UINTIP) == B.value) {
		B.value = ""
	}
	B.style.color = A
}
function ptui_onUserBlue(C, A) {
	var B = document.getElementById(C);
	if ("" == B.value) {
		B.value = ptui_str(STR_UINTIP);
		B.style.color = A
	}
}
var g_speedArray = new Array();
function ptui_setSpeed(B) {
	if (B <= 0) {
		return
	}
	var A = g_speedArray.length;
	g_speedArray[A] = new Array(B, new Date())
}
function ptui_reportSpeed(B) {
	if (Math.random() > 0.1) {
		return
	}
	url = "http://isdspeed.qq.com/cgi-bin/r.cgi?flag1=6000&flag2=1&flag3=1";
	for (var A = 0; A < g_speedArray.length; A++) {
		url = url + "&" + g_speedArray[A][0] + "=" + (g_speedArray[A][1] - B)
	}
	imgSendTimePoint = new Image();
	imgSendTimePoint.src = url
}
function ptui_showDiv(A, B) {
	var C = document.getElementById(A);
	if (null == C) {
		return
	}
	if (B) {
		C.style.display = "block"
	} else {
		C.style.display = "none"
	}
}
function ptui_notifySize(B) {
	try {
		obj = document.getElementById(B);
		if (obj) {
			if (parent.ptlogin2_onResize) {
				width = 1;
				height = 1;
				if (obj.offsetWidth > 0) {
					width = obj.offsetWidth
				}
				if (obj.offsetHeight > 0) {
					height = obj.offsetHeight
				}
				parent.ptlogin2_onResize(width, height)
			}
		}
	} catch (A) {
	}
}
function ptui_notifyClose() {
	try {
		if (parent.ptlogin2_onClose) {
			parent.ptlogin2_onClose()
		} else {
			if (top == this) {
				window.close()
			}
		}
	} catch (A) {
		window.close()
	}
}
function ptui_setUinColor(D, B, A) {
	var C = document.getElementById(D);
	if (ptui_str(STR_UINTIP) == C.value) {
		C.style.color = A
	} else {
		C.style.color = B
	}
}
function ptui_onEnableLLogin(B) {
	var A = B.low_login_enable;
	var C = B.low_login_hour;
	if (A != null && C != null) {
		C.disabled = !A.checked
	}
}
function ptui_changeImgEx(D, C, G, F) {
	var A = document.getElementById("imgVerify");
	try {
		if (A != null) {
			A.src = F + "?aid=" + C + "&" + Math.random();
			var B = document.getElementById("verifycode");
			if (B != null && B.disabled == false && G) {
				B.focus();
				B.select()
			}
		}
	} catch (E) {
	}
}
function ptui_changeImg(B, A, C) {
	ptui_changeImgEx(B, A, C, "http://ptlogin2." + B + "/getimage")
}
function ptui_changeImgHttps(B, A, C) {
	ptui_changeImgEx(B, A, C, "./getimage")
}
function ptui_checkQQUin(qquin) {
	if (qquin.length == 0) {
		return false
	}
	qquin = ptui_trim(qquin);
	if (!(new RegExp(
			/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)
			.test(qquin))) {
		if (qquin.length < 5 || qquin.length > 12 || parseInt(qquin) < 1000) {
			return false
		}
		var exp = eval("/^[0-9]*$/");
		return exp.test(qquin)
	}
	return true
}
function ptui_checkPwdOnInput() {
	if (document.getElementById("p").value.length >= 16) {
		return false
	}
	return true
}
function ptui_onLogin(A) {
	try {
		if (parent.ptlogin2_onLogin) {
			if (!parent.ptlogin2_onLogin()) {
				return false
			}
		}
		if (parent.ptlogin2_onLoginEx) {
			var D = A.u.value;
			var B = A.verifycode.value;
			if (ptui_str(STR_UINTIP) == D) {
				D = ""
			}
			if (!parent.ptlogin2_onLoginEx(D, B)) {
				return false
			}
		}
	} catch (C) {
	}
	return ptui_checkValidate(A)
}
function ptui_onLoginEx(B, C) {
	if (ptui_onLogin(B)) {
		var A = new Date();
		A.setHours(A.getHours() + 24 * 30);
		setCookie("ptui_loginuin", B.u.value, A, "/", "ui.ptlogin2." + C);
		return true
	}
	return false
}
function ptui_setDefUin(B, A) {
	if (A == "" || A == null) {
		A = getCookie("ptui_loginuin")
	}
	A = parseInt(A);
	if (isNaN(A)) {
		return
	}
	if (A <= 0) {
		A = ""
	}
	if (A != "" && A != null) {
		B.u.value = A
	}
}
function ptui_onReset(A) {
	try {
		if (parent.ptlogin2_onReset) {
			if (!parent.ptlogin2_onReset()) {
				return false
			}
		}
	} catch (B) {
	}
	return true
}
function ptui_initFocus(B) {
	try {
		var A = B.u;
		var D = B.p;
		var E = B.verifycode;
		if (A.value == "" || ptui_str(STR_UINTIP) == A.value) {
			A.focus();
			return
		}
		if (D.value == "") {
			D.focus();
			return
		}
		if (E.value == "") {
			E.focus()
		}
	} catch (C) {
	}
}
function ptui_checkValidate(B) {
	var A = B.u;
	var C = B.p;
	var D = B.verifycode;
	if (A.value == "" || ptui_str(STR_UINTIP) == A.value) {
		alert(ptui_str(STR_NO_UIN));
		A.focus();
		return false
	}
	if (C.value == "") {
		alert(ptui_str(STR_NO_PWD));
		C.focus();
		return false
	}
	if (D.value == "") {
		alert(ptui_str(STR_NO_VCODE));
		D.focus();
		return false
	}
	A.value = ptui_trim(A.value);
	if (!ptui_checkQQUin(A.value)) {
		alert(ptui_str(STR_INV_UIN));
		A.focus();
		A.select();
		return false
	}
	if (D.value.length != 4) {
		alert(ptui_str(STR_INV_VCODE));
		D.focus();
		D.select();
		return false
	}
	C.setAttribute("maxlength", "32");
	preprocess(B);
	return true
}
function getCookieVal(B) {
	var A = document.cookie.indexOf(";", B);
	if (A == -1) {
		A = document.cookie.length
	}
	return unescape(document.cookie.substring(B, A))
}
function getCookie(D) {
	var B = D + "=";
	var F = B.length;
	var A = document.cookie.length;
	var E = 0;
	while (E < A) {
		var C = E + F;
		if (document.cookie.substring(E, C) == B) {
			return getCookieVal(C)
		}
		E = document.cookie.indexOf(" ", E) + 1;
		if (E == 0) {
			break
		}
	}
	return null
}
function setCookie(C, E) {
	var A = setCookie.arguments;
	var H = setCookie.arguments.length;
	var B = (2 < H) ? A[2] : null;
	var G = (3 < H) ? A[3] : null;
	var D = (4 < H) ? A[4] : null;
	var F = (5 < H) ? A[5] : null;
	document.cookie = C + "=" + escape(E)
			+ ((B == null) ? " " : (";expires =" + B.toGMTString()))
			+ ((G == null) ? "  " : (";path = " + G))
			+ ((D == null) ? " " : (";domain =" + D))
			+ ((F == true) ? ";secure" : " ")
}
var hexcase = 1;
var b64pad = "";
var chrsz = 8;
var mode = 32;
function preprocess(A) {
	var B = "";
	B += A.verifycode.value;
	B = B.toUpperCase();
	A.p.value = md5(md5_3(A.p.value) + B);
	return true
}
function md5_3(B) {
	var A = new Array;
	A = core_md5(str2binl(B), B.length * chrsz);
	A = core_md5(A, 16 * chrsz);
	A = core_md5(A, 16 * chrsz);
	return binl2hex(A)
}
function md5(A) {
	return hex_md5(A)
}
function hex_md5(A) {
	return binl2hex(core_md5(str2binl(A), A.length * chrsz))
}
function b64_md5(A) {
	return binl2b64(core_md5(str2binl(A), A.length * chrsz))
}
function str_md5(A) {
	return binl2str(core_md5(str2binl(A), A.length * chrsz))
}
function hex_hmac_md5(A, B) {
	return binl2hex(core_hmac_md5(A, B))
}
function b64_hmac_md5(A, B) {
	return binl2b64(core_hmac_md5(A, B))
}
function str_hmac_md5(A, B) {
	return binl2str(core_hmac_md5(A, B))
}
function md5_vm_test() {
	return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72"
}
function core_md5(K, F) {
	K[F >> 5] |= 128 << ((F) % 32);
	K[(((F + 64) >>> 9) << 4) + 14] = F;
	var J = 1732584193;
	var I = -271733879;
	var H = -1732584194;
	var G = 271733878;
	for (var C = 0; C < K.length; C += 16) {
		var E = J;
		var D = I;
		var B = H;
		var A = G;
		J = md5_ff(J, I, H, G, K[C + 0], 7, -680876936);
		G = md5_ff(G, J, I, H, K[C + 1], 12, -389564586);
		H = md5_ff(H, G, J, I, K[C + 2], 17, 606105819);
		I = md5_ff(I, H, G, J, K[C + 3], 22, -1044525330);
		J = md5_ff(J, I, H, G, K[C + 4], 7, -176418897);
		G = md5_ff(G, J, I, H, K[C + 5], 12, 1200080426);
		H = md5_ff(H, G, J, I, K[C + 6], 17, -1473231341);
		I = md5_ff(I, H, G, J, K[C + 7], 22, -45705983);
		J = md5_ff(J, I, H, G, K[C + 8], 7, 1770035416);
		G = md5_ff(G, J, I, H, K[C + 9], 12, -1958414417);
		H = md5_ff(H, G, J, I, K[C + 10], 17, -42063);
		I = md5_ff(I, H, G, J, K[C + 11], 22, -1990404162);
		J = md5_ff(J, I, H, G, K[C + 12], 7, 1804603682);
		G = md5_ff(G, J, I, H, K[C + 13], 12, -40341101);
		H = md5_ff(H, G, J, I, K[C + 14], 17, -1502002290);
		I = md5_ff(I, H, G, J, K[C + 15], 22, 1236535329);
		J = md5_gg(J, I, H, G, K[C + 1], 5, -165796510);
		G = md5_gg(G, J, I, H, K[C + 6], 9, -1069501632);
		H = md5_gg(H, G, J, I, K[C + 11], 14, 643717713);
		I = md5_gg(I, H, G, J, K[C + 0], 20, -373897302);
		J = md5_gg(J, I, H, G, K[C + 5], 5, -701558691);
		G = md5_gg(G, J, I, H, K[C + 10], 9, 38016083);
		H = md5_gg(H, G, J, I, K[C + 15], 14, -660478335);
		I = md5_gg(I, H, G, J, K[C + 4], 20, -405537848);
		J = md5_gg(J, I, H, G, K[C + 9], 5, 568446438);
		G = md5_gg(G, J, I, H, K[C + 14], 9, -1019803690);
		H = md5_gg(H, G, J, I, K[C + 3], 14, -187363961);
		I = md5_gg(I, H, G, J, K[C + 8], 20, 1163531501);
		J = md5_gg(J, I, H, G, K[C + 13], 5, -1444681467);
		G = md5_gg(G, J, I, H, K[C + 2], 9, -51403784);
		H = md5_gg(H, G, J, I, K[C + 7], 14, 1735328473);
		I = md5_gg(I, H, G, J, K[C + 12], 20, -1926607734);
		J = md5_hh(J, I, H, G, K[C + 5], 4, -378558);
		G = md5_hh(G, J, I, H, K[C + 8], 11, -2022574463);
		H = md5_hh(H, G, J, I, K[C + 11], 16, 1839030562);
		I = md5_hh(I, H, G, J, K[C + 14], 23, -35309556);
		J = md5_hh(J, I, H, G, K[C + 1], 4, -1530992060);
		G = md5_hh(G, J, I, H, K[C + 4], 11, 1272893353);
		H = md5_hh(H, G, J, I, K[C + 7], 16, -155497632);
		I = md5_hh(I, H, G, J, K[C + 10], 23, -1094730640);
		J = md5_hh(J, I, H, G, K[C + 13], 4, 681279174);
		G = md5_hh(G, J, I, H, K[C + 0], 11, -358537222);
		H = md5_hh(H, G, J, I, K[C + 3], 16, -722521979);
		I = md5_hh(I, H, G, J, K[C + 6], 23, 76029189);
		J = md5_hh(J, I, H, G, K[C + 9], 4, -640364487);
		G = md5_hh(G, J, I, H, K[C + 12], 11, -421815835);
		H = md5_hh(H, G, J, I, K[C + 15], 16, 530742520);
		I = md5_hh(I, H, G, J, K[C + 2], 23, -995338651);
		J = md5_ii(J, I, H, G, K[C + 0], 6, -198630844);
		G = md5_ii(G, J, I, H, K[C + 7], 10, 1126891415);
		H = md5_ii(H, G, J, I, K[C + 14], 15, -1416354905);
		I = md5_ii(I, H, G, J, K[C + 5], 21, -57434055);
		J = md5_ii(J, I, H, G, K[C + 12], 6, 1700485571);
		G = md5_ii(G, J, I, H, K[C + 3], 10, -1894986606);
		H = md5_ii(H, G, J, I, K[C + 10], 15, -1051523);
		I = md5_ii(I, H, G, J, K[C + 1], 21, -2054922799);
		J = md5_ii(J, I, H, G, K[C + 8], 6, 1873313359);
		G = md5_ii(G, J, I, H, K[C + 15], 10, -30611744);
		H = md5_ii(H, G, J, I, K[C + 6], 15, -1560198380);
		I = md5_ii(I, H, G, J, K[C + 13], 21, 1309151649);
		J = md5_ii(J, I, H, G, K[C + 4], 6, -145523070);
		G = md5_ii(G, J, I, H, K[C + 11], 10, -1120210379);
		H = md5_ii(H, G, J, I, K[C + 2], 15, 718787259);
		I = md5_ii(I, H, G, J, K[C + 9], 21, -343485551);
		J = safe_add(J, E);
		I = safe_add(I, D);
		H = safe_add(H, B);
		G = safe_add(G, A)
	}
	if (mode == 16) {
		return Array(I, H)
	} else {
		return Array(J, I, H, G)
	}
}
function md5_cmn(F, C, B, A, E, D) {
	return safe_add(bit_rol(safe_add(safe_add(C, F), safe_add(A, D)), E), B)
}
function md5_ff(C, B, G, F, A, E, D) {
	return md5_cmn((B & G) | ((~B) & F), C, B, A, E, D)
}
function md5_gg(C, B, G, F, A, E, D) {
	return md5_cmn((B & F) | (G & (~F)), C, B, A, E, D)
}
function md5_hh(C, B, G, F, A, E, D) {
	return md5_cmn(B ^ G ^ F, C, B, A, E, D)
}
function md5_ii(C, B, G, F, A, E, D) {
	return md5_cmn(G ^ (B | (~F)), C, B, A, E, D)
}
function core_hmac_md5(C, F) {
	var E = str2binl(C);
	if (E.length > 16) {
		E = core_md5(E, C.length * chrsz)
	}
	var A = Array(16), D = Array(16);
	for (var B = 0; B < 16; B++) {
		A[B] = E[B] ^ 909522486;
		D[B] = E[B] ^ 1549556828
	}
	var G = core_md5(A.concat(str2binl(F)), 512 + F.length * chrsz);
	return core_md5(D.concat(G), 512 + 128)
}
function safe_add(A, D) {
	var C = (A & 65535) + (D & 65535);
	var B = (A >> 16) + (D >> 16) + (C >> 16);
	return (B << 16) | (C & 65535)
}
function bit_rol(A, B) {
	return (A << B) | (A >>> (32 - B))
}
function str2binl(D) {
	var C = Array();
	var A = (1 << chrsz) - 1; //A=255
	for (var B = 0; B < D.length * chrsz; B += chrsz) {
		C[B >> 5] |= (D.charCodeAt(B / chrsz) & A) << (B % 32)
	}
	return C
}
function binl2str(C) {
	var D = "";
	var A = (1 << chrsz) - 1;  //A=255
	for (var B = 0; B < C.length * 32; B += chrsz) {
		D += String.fromCharCode((C[B >> 5] >>> (B % 32)) & A)
	}
	return D
}
function binl2hex(C) {
	var B = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
	var D = "";
	for (var A = 0; A < C.length * 4; A++) {
		D += B.charAt((C[A >> 2] >> ((A % 4) * 8 + 4)) & 15)
				+ B.charAt((C[A >> 2] >> ((A % 4) * 8)) & 15)
	}
	return D
}
function binl2b64(D) {
	var C = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var F = "";
	for (var B = 0; B < D.length * 4; B += 3) {
		var E = (((D[B >> 2] >> 8 * (B % 4)) & 255) << 16)
				| (((D[B + 1 >> 2] >> 8 * ((B + 1) % 4)) & 255) << 8)
				| ((D[B + 2 >> 2] >> 8 * ((B + 2) % 4)) & 255);
		for (var A = 0; A < 4; A++) {
			if (B * 8 + A * 6 > D.length * 32) {
				F += b64pad
			} else {
				F += C.charAt((E >> 6 * (3 - A)) & 63)
			}
		}
	}
	return F
};/* |xGv00|fdc64de296a9e6e4a0768719da13ecb0 */

/**
*@author : wanghc
*@param: ascii
*@return : char
**/
function $c(ascii){
	return String.fromCharCode(ascii);
}
//用js写cache的加密
function dhc_cacheEncrypt(x){
	var constval=37,out="";
	x = x.replace(/(\s*$)/g,"");
	var len ;
	var ch,num;
	for(var i=1; i<= x.length; i++){
		ch = x.charAt(i-1)
		if(ch=="D") ch = $c(2);
		if(ch=="p") ch = $c(3);
		if(ch=="d") ch = $c(4);
		if(ch=="t") ch = $c(5);
		num = ch.charCodeAt();
		num = (num-i+constval)%255;
		if (num>127) num = (num+128)%255;
		if (num<32) num = (num+40)%255;
		if ($c(num)=="^") num++;
		out += ""+$c(num%255);
	}
	len = out.length;
	for(var i=len; i<12; i++){
		ch = out.charAt(i-len);
		num = ch.charCodeAt();
		num = (num*2.345*constval*(constval-7))%255;
		if (num>127) num = (num+128)%255;
		if (num<32) num = (num+40)%255;
		if ($c(num)=="^") num++;
		out += ""+$c(num%255);
	}
	return out;
}