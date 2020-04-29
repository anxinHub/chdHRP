/*!
 * Globalize
 *
 * http://github.com/jquery/globalize
 *
 * Copyright Software Freedom Conservancy, Inc.
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 */
(function (z, y) { var a, x, t, s, w, c, j, r, k, v, p, e, b, q, f, h, m, n, u, l, g, i, o, d; a = function (b) { return new a.prototype.init(b) }; if (typeof require !== "undefined" && typeof exports !== "undefined" && typeof module !== "undefined") module.exports = a; else z.Globalize = a; a.cultures = {}; a.prototype = { constructor: a, init: function (b) { this.cultures = a.cultures; this.cultureSelector = b; return this } }; a.prototype.init.prototype = a.prototype; a.cultures["default"] = { name: "en", englishName: "English", nativeName: "English", isRTL: false, language: "en", numberFormat: { pattern: ["-n"], decimals: 2, ",": ",", ".": ".", groupSizes: [3], "+": "+", "-": "-", NaN: "NaN", negativeInfinity: "-Infinity", positiveInfinity: "Infinity", percent: { pattern: ["-n %", "n %"], decimals: 2, groupSizes: [3], ",": ",", ".": ".", symbol: "%" }, currency: { pattern: ["($n)", "$n"], decimals: 2, groupSizes: [3], ",": ",", ".": ".", symbol: "$" } }, calendars: { standard: { name: "Gregorian_USEnglish", "/": "/", ":": ":", firstDay: 0, days: { names: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"], namesAbbr: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"], namesShort: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"] }, months: { names: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", ""], namesAbbr: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", ""] }, AM: ["AM", "am", "AM"], PM: ["PM", "pm", "PM"], eras: [{ name: "A.D.", start: null, offset: 0 }], twoDigitYearMax: 2029, patterns: { d: "M/d/yyyy", D: "dddd, MMMM dd, yyyy", t: "h:mm tt", T: "h:mm:ss tt", f: "dddd, MMMM dd, yyyy h:mm tt", F: "dddd, MMMM dd, yyyy h:mm:ss tt", M: "MMMM dd", Y: "yyyy MMMM", S: "yyyy'-'MM'-'dd'T'HH':'mm':'ss" } } }, messages: {} }; a.cultures["default"].calendar = a.cultures["default"].calendars.standard; a.cultures.en = a.cultures["default"]; a.cultureSelector = "en"; x = /^0x[a-f0-9]+$/i; t = /^[+-]?infinity$/i; s = /^[+-]?\d*\.?\d*(e[+-]?\d+)?$/; w = /^\s+|\s+$/g; c = function (a, c) { if (a.indexOf) return a.indexOf(c); for (var b = 0, d = a.length; b < d; b++) if (a[b] === c) return b; return -1 }; j = function (b, a) { return b.substr(b.length - a.length) === a }; r = function (i) { var g, d, c, b, f, h, a = arguments[0] || {}, e = 1, j = arguments.length, i = false; if (typeof a === "boolean") { i = a; a = arguments[1] || {}; e = 2 } if (typeof a !== "object" && !v(a)) a = {}; for (; e < j; e++) if ((g = arguments[e]) != null) for (d in g) { c = a[d]; b = g[d]; if (a === b) continue; if (i && b && (p(b) || (f = k(b)))) { if (f) { f = false; h = c && k(c) ? c : [] } else h = c && p(c) ? c : {}; a[d] = r(i, h, b) } else if (b !== y) a[d] = b } return a }; k = Array.isArray || function (a) { return Object.prototype.toString.call(a) === "[object Array]" }; v = function (a) { return Object.prototype.toString.call(a) === "[object Function]" }; p = function (a) { return Object.prototype.toString.call(a) === "[object Object]" }; e = function (b, a) { return b.indexOf(a) === 0 }; b = function (a) { return (a + "").replace(w, "") }; q = function (a) { return isNaN(a) ? NaN : a | 0 }; f = function (a, c, d) { for (var b = a.length; b < c; b += 1) a = d ? "0" + a : a + "0"; return a }; h = function (e, b) { for (var d = 0, a = false, c = 0, g = e.length; c < g; c++) { var f = e.charAt(c); switch (f) { case "'": if (a) b.push("'"); else d++; a = false; break; case "\\": a && b.push("\\"); a = !a; break; default: b.push(f); a = false } } return d }; m = function (e, a) { a = a || "F"; var b, d = e.patterns, c = a.length; if (c === 1) { b = d[a]; if (!b) throw "Invalid date format string '" + a + "'."; a = b } else if (c === 2 && a.charAt(0) === "%") a = a.charAt(1); return a }; n = function (b, f, r) { var c = r.calendar, s = c.convert; if (!f || !f.length || f === "i") { var a; if (r && r.name.length) if (s) a = n(b, c.patterns.F, r); else { var z = new Date(b.getTime()), H = g(b, c.eras); z.setFullYear(i(b, c, H)); a = z.toLocaleString() } else a = b.toString(); return a } var A = c.eras, y = f === "s"; f = m(c, f); a = []; var j, G = ["0", "00", "000"], p, w, B = /([^d]|^)(d|dd)([^d]|$)/g, x = 0, v = l(), o; function e(d, a) { var b, c = d + ""; if (a > 1 && c.length < a) { b = G[a - 2] + c; return b.substr(b.length - a, a) } else b = c; return b } function D() { if (p || w) return p; p = B.test(f); w = true; return p } function u(a, b) { if (o) return o[b]; switch (b) { case 0: return a.getFullYear(); case 1: return a.getMonth(); case 2: return a.getDate() } } if (!y && s) o = s.fromGregorian(b); for (; ;) { var E = v.lastIndex, q = v.exec(f), C = f.slice(E, q ? q.index : f.length); x += h(C, a); if (!q) break; if (x % 2) { a.push(q[0]); continue } var t = q[0], d = t.length; switch (t) { case "ddd": case "dddd": var F = d === 3 ? c.days.namesAbbr : c.days.names; a.push(F[b.getDay()]); break; case "d": case "dd": p = true; a.push(e(u(b, 2), d)); break; case "MMM": case "MMMM": var k = u(b, 1); a.push(c.monthsGenitive && D() ? c.monthsGenitive[d === 3 ? "namesAbbr" : "names"][k] : c.months[d === 3 ? "namesAbbr" : "names"][k]); break; case "M": case "MM": a.push(e(u(b, 1) + 1, d)); break; case "y": case "yy": case "yyyy": k = o ? o[0] : i(b, c, g(b, A), y); if (d < 4) k = k % 100; a.push(e(k, d)); break; case "h": case "hh": j = b.getHours() % 12; if (j === 0) j = 12; a.push(e(j, d)); break; case "H": case "HH": a.push(e(b.getHours(), d)); break; case "m": case "mm": a.push(e(b.getMinutes(), d)); break; case "s": case "ss": a.push(e(b.getSeconds(), d)); break; case "t": case "tt": k = b.getHours() < 12 ? c.AM ? c.AM[0] : " " : c.PM ? c.PM[0] : " "; a.push(d === 1 ? k.charAt(0) : k); break; case "f": case "ff": case "fff": a.push(e(b.getMilliseconds(), 3).substr(0, d)); break; case "z": case "zz": j = b.getTimezoneOffset() / 60; a.push((j <= 0 ? "+" : "-") + e(Math.floor(Math.abs(j)), d)); break; case "zzz": j = b.getTimezoneOffset() / 60; a.push((j <= 0 ? "+" : "-") + e(Math.floor(Math.abs(j)), 2) + ":" + e(Math.abs(b.getTimezoneOffset() % 60), 2)); break; case "g": case "gg": c.eras && a.push(c.eras[g(b, A)].name); break; case "/": a.push(c["/"]); break; default: throw "Invalid date format pattern '" + t + "'."; } } return a.join("") }; (function () { var a; a = function (j, h, l) { var m = l.groupSizes, i = m[0], k = 1, p = Math.pow(10, h), n = Math.round(j * p) / p; if (!isFinite(n)) n = j; j = n; var b = j + "", a = "", e = b.split(/e/i), c = e.length > 1 ? parseInt(e[1], 10) : 0; b = e[0]; e = b.split("."); b = e[0]; a = e.length > 1 ? e[1] : ""; var q; if (c > 0) { a = f(a, c, false); b += a.slice(0, c); a = a.substr(c) } else if (c < 0) { c = -c; b = f(b, c + 1); a = b.slice(-c, b.length) + a; b = b.slice(0, -c) } if (h > 0) a = l["."] + (a.length > h ? a.slice(0, h) : f(a, h)); else a = ""; var d = b.length - 1, o = l[","], g = ""; while (d >= 0) { if (i === 0 || i > d) return b.slice(0, d + 1) + (g.length ? o + g + a : a); g = b.slice(d - i + 1, d + 1) + (g.length ? o + g : ""); d -= i; if (k < m.length) { i = m[k]; k++ } } return b.slice(0, d + 1) + o + g + a }; u = function (d, e, j) { if (!isFinite(d)) return d === Infinity ? j.numberFormat.positiveInfinity : d === -Infinity ? j.numberFormat.negativeInfinity : j.numberFormat.NaN; if (!e || e === "i") return j.name.length ? d.toLocaleString() : d.toString(); e = e || "D"; var i = j.numberFormat, b = Math.abs(d), g = -1, k; if (e.length > 1) g = parseInt(e.slice(1), 10); var m = e.charAt(0).toUpperCase(), c; switch (m) { case "D": k = "n"; b = q(b); if (g !== -1) b = f("" + b, g, true); if (d < 0) b = "-" + b; break; case "N": c = i; case "C": c = c || i.currency; case "P": c = c || i.percent; k = d < 0 ? c.pattern[0] : c.pattern[1] || "n"; if (g === -1) g = c.decimals; b = a(b * (m === "P" ? 100 : 1), g, c); break; default: throw "Bad number format specifier: " + m; } for (var n = /n|\$|-|%/g, h = ""; ;) { var o = n.lastIndex, l = n.exec(k); h += k.slice(o, l ? l.index : k.length); if (!l) break; switch (l[0]) { case "n": h += b; break; case "$": h += i.currency.symbol; break; case "-": if (/[1-9]/.test(b)) h += i["-"]; break; case "%": h += i.percent.symbol } } return h } })(); l = function () { return /\/|dddd|ddd|dd|d|MMMM|MMM|MM|M|yyyy|yy|y|hh|h|HH|H|mm|m|ss|s|tt|t|fff|ff|f|zzz|zz|z|gg|g/g }; g = function (e, c) { if (!c) return 0; for (var b, d = e.getTime(), a = 0, f = c.length; a < f; a++) { b = c[a].start; if (b === null || d >= b) return a } return 0 }; i = function (d, b, e, c) { var a = d.getFullYear(); if (!c && b.eras) a -= b.eras[e].offset; return a }; (function () { var p, n, k, j, a, f, d; p = function (d, b) { if (b < 100) { var e = new Date, f = g(e), c = i(e, d, f), a = d.twoDigitYearMax; a = typeof a === "string" ? (new Date).getFullYear() % 100 + parseInt(a, 10) : a; b += c - c % 100; if (b > a) b -= 100 } return b }; n = function (h, b, i) { var e, g = h.days, a = h._upperDays; if (!a) h._upperDays = a = [d(g.names), d(g.namesAbbr), d(g.namesShort)]; b = f(b); if (i) { e = c(a[1], b); if (e === -1) e = c(a[2], b) } else e = c(a[0], b); return e }; k = function (a, e, k) { var j = a.months, i = a.monthsGenitive || a.months, b = a._upperMonths, g = a._upperMonthsGen; if (!b) { a._upperMonths = b = [d(j.names), d(j.namesAbbr)]; a._upperMonthsGen = g = [d(i.names), d(i.namesAbbr)] } e = f(e); var h = c(k ? b[1] : b[0], e); if (h < 0) h = c(k ? g[1] : g[0], e); return h }; j = function (d, g) { var e = d._parseRegExp; if (!e) d._parseRegExp = e = {}; else { var o = e[g]; if (o) return o } var f = m(d, g).replace(/([\^\$\.\*\+\?\|\[\]\(\)\{\}])/g, "\\\\$1"), b = ["^"], p = [], i = 0, n = 0, k = l(), c; while ((c = k.exec(f)) !== null) { var s = f.slice(i, c.index); i = k.lastIndex; n += h(s, b); if (n % 2) { b.push(c[0]); continue } var j = c[0], t = j.length, a; switch (j) { case "dddd": case "ddd": case "MMMM": case "MMM": case "gg": case "g": a = "(\\D+)"; break; case "tt": case "t": a = "(\\D*)"; break; case "yyyy": case "fff": case "ff": case "f": a = "(\\d{" + t + "})"; break; case "dd": case "d": case "MM": case "M": case "yy": case "y": case "HH": case "H": case "hh": case "h": case "mm": case "m": case "ss": case "s": a = "(\\d\\d?)"; break; case "zzz": a = "([+-]?\\d\\d?:\\d{2})"; break; case "zz": case "z": a = "([+-]?\\d\\d?)"; break; case "/": a = "(\\" + d["/"] + ")"; break; default: throw "Invalid date format pattern '" + j + "'."; } a && b.push(a); p.push(c[0]) } h(f.slice(i), b); b.push("$"); var r = b.join("").replace(/\s+/g, "\\s+"), q = { regExp: r, groups: p }; return e[g] = q }; a = function (a, c, b) { return a < c || a > b }; f = function (a) { return a.split("\u00a0").join(" ").toUpperCase() }; d = function (c) { for (var b = [], a = 0, d = c.length; a < d; a++) b[a] = f(c[a]); return b }; o = function (A, M, L) { A = b(A); var c = L.calendar, H = j(c, M), K = new RegExp(H.regExp).exec(A); if (K === null) return null; for (var J = H.groups, C = null, m = null, i = null, l = null, u = null, h = 0, o, D = 0, E = 0, B = 0, q = null, z = false, w = 0, N = J.length; w < N; w++) { var d = K[w + 1]; if (d) { var I = J[w], r = I.length, g = parseInt(d, 10); switch (I) { case "dd": case "d": l = g; if (a(l, 1, 31)) return null; break; case "MMM": case "MMMM": i = k(c, d, r === 3); if (a(i, 0, 11)) return null; break; case "M": case "MM": i = g - 1; if (a(i, 0, 11)) return null; break; case "y": case "yy": case "yyyy": m = r < 4 ? p(c, g) : g; if (a(m, 0, 9999)) return null; break; case "h": case "hh": h = g; if (h === 12) h = 0; if (a(h, 0, 11)) return null; break; case "H": case "HH": h = g; if (a(h, 0, 23)) return null; break; case "m": case "mm": D = g; if (a(D, 0, 59)) return null; break; case "s": case "ss": E = g; if (a(E, 0, 59)) return null; break; case "tt": case "t": z = c.PM && (d === c.PM[0] || d === c.PM[1] || d === c.PM[2]); if (!z && (!c.AM || d !== c.AM[0] && d !== c.AM[1] && d !== c.AM[2])) return null; break; case "f": case "ff": case "fff": B = g * Math.pow(10, 3 - r); if (a(B, 0, 999)) return null; break; case "ddd": case "dddd": u = n(c, d, r === 3); if (a(u, 0, 6)) return null; break; case "zzz": var y = d.split(/:/); if (y.length !== 2) return null; o = parseInt(y[0], 10); if (a(o, -12, 13)) return null; var x = parseInt(y[1], 10); if (a(x, 0, 59)) return null; q = o * 60 + (e(d, "-") ? -x : x); break; case "z": case "zz": o = g; if (a(o, -12, 13)) return null; q = o * 60; break; case "g": case "gg": var t = d; if (!t || !c.eras) return null; t = b(t.toLowerCase()); for (var v = 0, O = c.eras.length; v < O; v++) if (t === c.eras[v].name.toLowerCase()) { C = v; break } if (C === null) return null } } } var f = new Date, G, s = c.convert; G = s ? s.fromGregorian(f)[0] : f.getFullYear(); if (m === null) m = G; else if (c.eras) m += c.eras[C || 0].offset; if (i === null) i = 0; if (l === null) l = 1; if (s) { f = s.toGregorian(m, i, l); if (f === null) return null } else { f.setFullYear(m, i, l); if (f.getDate() !== l) return null; if (u !== null && f.getDay() !== u) return null } if (z && h < 12) h += 12; f.setHours(h, D, E, B); if (q !== null) { var F = f.getMinutes() - (q + f.getTimezoneOffset()); f.setHours(f.getHours() + parseInt(F / 60, 10), F % 60) } return f } })(); d = function (a, f, g) { var b = f["-"], c = f["+"], d; switch (g) { case "n -": b = " " + b; c = " " + c; case "n-": if (j(a, b)) d = ["-", a.substr(0, a.length - b.length)]; else if (j(a, c)) d = ["+", a.substr(0, a.length - c.length)]; break; case "- n": b += " "; c += " "; case "-n": if (e(a, b)) d = ["-", a.substr(b.length)]; else if (e(a, c)) d = ["+", a.substr(c.length)]; break; case "(n)": if (e(a, "(") && j(a, ")")) d = ["-", a.substr(1, a.length - 2)] } return d || ["", a] }; a.prototype.findClosestCulture = function (b) { return a.findClosestCulture.call(this, b) }; a.prototype.format = function (d, c, b) { return a.format.call(this, d, c, b) }; a.prototype.localize = function (c, b) { return a.localize.call(this, c, b) }; a.prototype.parseInt = function (d, c, b) { return a.parseInt.call(this, d, c, b) }; a.prototype.parseFloat = function (d, c, b) { return a.parseFloat.call(this, d, c, b) }; a.prototype.culture = function (b) { return a.culture.call(this, b) }; a.addCultureInfo = function (a, c, e) { var b = {}, d = false; if (typeof a !== "string") { e = a; a = this.culture().name; b = this.cultures[a] } else if (typeof c !== "string") { e = c; d = this.cultures[a] == null; b = this.cultures[a] || this.cultures["default"] } else { d = true; b = this.cultures[c] } this.cultures[a] = r(true, {}, b, e); if (d) this.cultures[a].calendar = this.cultures[a].calendars.standard }; a.findClosestCulture = function (a) { var e; if (!a) return this.cultures[this.cultureSelector] || this.cultures["default"]; if (typeof a === "string") a = a.split(","); if (k(a)) { for (var d, h = this.cultures, n = a, i = n.length, g = [], c = 0; c < i; c++) { a = b(n[c]); var f, j = a.split(";"); d = b(j[0]); if (j.length === 1) f = 1; else { a = b(j[1]); if (a.indexOf("q=") === 0) { a = a.substr(2); f = parseFloat(a); f = isNaN(f) ? 0 : f } else f = 1 } g.push({ lang: d, pri: f }) } g.sort(function (a, b) { return a.pri < b.pri ? 1 : -1 }); for (c = 0; c < i; c++) { d = g[c].lang; e = h[d]; if (e) return e } for (c = 0; c < i; c++) { d = g[c].lang; do { var m = d.lastIndexOf("-"); if (m === -1) break; d = d.substr(0, m); e = h[d]; if (e) return e } while (1) } for (c = 0; c < i; c++) { d = g[c].lang; for (var o in h) { var l = h[o]; if (l.language == d) return l } } } else if (typeof a === "object") return a; return e || null }; a.format = function (a, b, c) { culture = this.findClosestCulture(c); if (a instanceof Date) a = n(a, b, culture); else if (typeof a === "number") a = u(a, b, culture); return a }; a.localize = function (a, b) { return this.findClosestCulture(b).messages[a] || this.cultures["default"].messages[a] }; a.parseDate = function (g, a, b) { b = this.findClosestCulture(b); var c, h, d; if (a) { if (typeof a === "string") a = [a]; if (a.length) for (var e = 0, i = a.length; e < i; e++) { var f = a[e]; if (f) { c = o(g, f, b); if (c) break } } } else { d = b.calendar.patterns; for (h in d) { c = o(g, d[h], b); if (c) break } } return c || null }; a.parseInt = function (d, c, b) { return q(a.parseFloat(d, c, b)) }; a.parseFloat = function (a, n, u) { if (typeof n !== "number") { u = n; n = 10 } var k = this.findClosestCulture(u), o = NaN, c = k.numberFormat; if (a.indexOf(k.numberFormat.currency.symbol) > -1) { a = a.replace(k.numberFormat.currency.symbol, ""); a = a.replace(k.numberFormat.currency["."], k.numberFormat["."]) } a = b(a); if (t.test(a)) o = parseFloat(a); else if (!n && x.test(a)) o = parseInt(a, 16); else { var e = d(a, c, c.pattern[0]), g = e[0], h = e[1]; if (g === "" && c.pattern[0] !== "(n)") { e = d(a, c, "(n)"); g = e[0]; h = e[1] } if (g === "" && c.pattern[0] !== "-n") { e = d(a, c, "-n"); g = e[0]; h = e[1] } g = g || "+"; var l, i, j = h.indexOf("e"); if (j < 0) j = h.indexOf("E"); if (j < 0) { i = h; l = null } else { i = h.substr(0, j); l = h.substr(j + 1) } var f, m, y = c["."], q = i.indexOf(y); if (q < 0) { f = i; m = null } else { f = i.substr(0, q); m = i.substr(q + y.length) } var r = c[","]; f = f.split(r).join(""); var v = r.replace(/\u00A0/g, " "); if (r !== v) f = f.split(v).join(""); var p = g + f; if (m !== null) p += "." + m; if (l !== null) { var w = d(l, c, "-n"); p += "e" + (w[0] || "+") + w[1] } if (s.test(p)) o = parseFloat(p) } return o }; a.culture = function (a) { if (typeof a !== "undefined") this.cultureSelector = a; return this.findClosestCulture(a) || this.culture["default"] } })(this);

/*
 *
  *
  * Copyright(c) GrapeCity, Inc.  All rights reserved.
  *
 *
 */
var __extends = this.__extends || function (d, b) {
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var gcui;
(function (gcui) {
    var $ = jQuery;
    var GCUI = {
        autoMobilize : true,
        uiCSS : {
            widget: "ui-widget",
            overlay: "ui-widget-overlay",
            content: "ui-widget-content",
            header: "ui-widget-header",
            stateDisabled: "ui-state-disabled",
            stateFocus: "ui-state-focus",
            stateActive: "ui-state-active",
            stateDefault: "ui-state-default",
            stateHightlight: "ui-state-highlight",
            stateHover: "ui-state-hover",
            stateChecked: "ui-state-checked",
            stateError: "ui-state-error",
            getState: function (name) {
                name = name.charAt(0).toUpperCase() + name.substr(1);
                return $.gcui.uiCSS["state" + name];
            },
            icon: "ui-icon",
            iconCheck: "ui-icon-check",
            iconRadioOn: "ui-icon-radio-on",
            iconRadioOff: "ui-icon-radio-off",
            iconClose: "ui-icon-close",
            iconArrow4Diag: "ui-icon-arrow-4-diag",
            iconNewWin: "ui-icon-newwin",
            iconVGripSolid: "ui-icon-grip-solid-vertical",
            iconHGripSolid: "ui-icon-grip-solid-horizontal",
            iconPlay: "ui-icon-play",
            iconPause: "ui-icon-pause",
            iconStop: "ui-icon-stop",
            iconArrowUp: "ui-icon-triangle-1-n",
            iconArrowRight: "ui-icon-triangle-1-e",
            iconArrowDown: "ui-icon-triangle-1-s",
            iconArrowLeft: "ui-icon-triangle-1-w",
            iconArrowRightDown: "ui-icon-triangle-1-se",
            iconCaratUp: "ui-icon-carat-1-n",
            iconCaratRight: "ui-icon-carat-1-e",
            iconCaratDown: "ui-icon-carat-1-s",
            iconCaratLeft: "ui-icon-carat-1-w",
            iconSeekFirst: "ui-icon-seek-first",
            iconSeekEnd: "ui-icon-seek-end",
            iconSeekNext: "ui-icon-seek-next",
            iconSeekPrev: "ui-icon-seek-prev",
            inputSpinnerLeft: "ui-input-spinner-left",
            inputSpinnerRight: "ui-input-spinner-right",
            inputTriggerLeft: "ui-input-trigger-left",
            inputTriggerRight: "ui-input-trigger-right",
            inputSpinnerTriggerLeft: "ui-input-spinner-trigger-left",
            inputSpinnerTriggerRight: "ui-input-spinner-trigger-right",
            cornerAll: "ui-corner-all",
            cornerLeft: "ui-corner-left",
            cornerRight: "ui-corner-right",
            cornerBottom: "ui-corner-bottom",
            cornerBL: "ui-corner-bl",
            cornerBR: "ui-corner-br",
            cornerTop: "ui-corner-top",
            cornerTL: "ui-corner-tl",
            cornerTR: "ui-corner-tr",
            helperClearFix: "ui-helper-clearfix",
            helperReset: "ui-helper-reset",
            helperHidden: "ui-helper-hidden",
            priorityPrimary: "ui-priority-primary",
            prioritySecondary: "ui-priority-secondary",
            button: "ui-button",
            buttonText: "ui-button-text",
            buttonTextOnly: "ui-button-text-only",
            tabs: "ui-tabs",
            tabsTop: "ui-tabs-top",
            tabsBottom: "ui-tabs-bottom",
            tabsLeft: "ui-tabs-left",
            tabsRight: "ui-tabs-right",
            tabsLoading: "ui-tabs-loading",
            tabsActive: "ui-tabs-active",
            tabsPanel: "ui-tabs-panel",
            tabsNav: "ui-tabs-nav",
            tabsHide: "ui-tabs-hide",
            tabsCollapsible: "ui-tabs-collapsible",
            activeMenuitem: "ui-active-menuitem"
        },
        mobileCSS : {
            content: "ui-content",
            header: "ui-header",
            overlay: "ui-overlay",
            stateDisabled: "ui-disabled",
            stateFocus: "ui-focus",
            stateActive: "ui-btn-active",
            stateDefault: "ui-btn-up-a",
            iconArrowUp: "ui-icon-arrow-u",
            iconArrowRight: "ui-icon-arrow-r",
            iconArrowDown: "ui-icon-arrow-d",
            iconArrowLeft: "ui-icon-arrow-l",
            iconArrowRightDown: "ui-icon-arrow-d",
            iconSeekFirst: "ui-icon-arrow-l",
            iconSeekEnd: "ui-icon-arrow-r",
            iconSeekNext: "ui-icon-arrow-r",
            iconSeekPrev: "ui-icon-arrow-l",
            iconClose: "ui-icon-delete",
            iconStop: "ui-icon-grid",
            iconCheck: "ui-icon-checkbox-on"
        },
        mobileThemePrefix : [
            "ui-bar", 
            "ui-body", 
            "ui-overlay", 
            "ui-btn-up", 
            "ui-btn-hover", 
            "ui-btn-down"
        ],
        autoInit : function(widgetName, pageKeepNative) {
            if($.mobile && $.mobile.page && pageKeepNative) {
                var keepNative = $.mobile.page.prototype.options.keepNative;
                if(keepNative && keepNative.length && keepNative.indexOf(pageKeepNative) === -1) {
                    keepNative = [
                        keepNative, 
                        pageKeepNative
                    ].join(", ");
                } else {
                    keepNative = pageKeepNative;
                }
                $.mobile.page.prototype.options.keepNative = keepNative;
            }
            if($.mobile && $.gcui[widgetName] && $.gcui[widgetName].prototype && $.gcui[widgetName].prototype.enhanceWithin) {
                $(document).bind("pagecreate create", function (e) {
                    $.gcui[widgetName].prototype.enhanceWithin(e.target);
                });
            }
        },
        registerWidget : function(name, baseType, def, customizeInit) {
            var fullName = "gcui." + name, init;
            if(typeof def === 'function') {
                init = def;
                def = null;
            }
            if(def === null || def === undefined) {
                def = $.extend(true, {}, baseType);
                baseType = $.gcui.widget;
            }
            def.options = def.options || {};
            def.options.initSelector = def.options.initSelector || ":jqmData(role='" + name + "')";
            if($.mobile && def.options && def.options.mobileCSS) {
                def.options.uiCSS = def.options.uiCSS || {};
                $.extend(def.options.uiCSS, def.options.mobileCSS);
            }
            $.widget(fullName, baseType, def);
            if(init) {
                init.call();
            } else if(this.autoInit) {
                this.autoInit(name, def.options.initSelector);
            }
        },
        addThemeToMobileCSS: function (theme, classes) {
            $.each(classes, function (key, cl) {
                if(typeof cl === "string") {
                    $.each(jQueryGCUI.mobileThemePrefix, function (idx, css) {
                        var regExp = new RegExp("\\b" + css);
                        if(regExp.test(cl)) {
                            classes[key] = cl + " " + css + "-" + theme;
                        }
                    });
                } else {
                    jQueryGCUI.addThemeToMobileCSS(theme, cl);
                }
            });
        }
    };    
    $.gcui = GCUI;
    var GCUIWidget =  (function () {
        function GCUIWidget() { }

        GCUIWidget.prototype.options = {
            uiCSS: $.gcui.uiCSS
        };

        GCUIWidget.prototype.widget = function () {
            return this.element;
        };
        
        ["destory", "_setOption", "_create", "_init"].forEach(function (name) {
            GCUIWidget.prototype[name] = function () {
                $.Widget.prototype[name].apply(this, arguments);
            }
        });

        return GCUIWidget;
    })();
    gcui.GCUIWidget = GCUIWidget;    
    var GCUIMobileWidget = (function (_super) {
        __extends(GCUIMobileWidget, _super);
        function GCUIMobileWidget() {
            _super.apply(this, arguments);
        }
        return GCUIMobileWidget;
    })(GCUIWidget);
    gcui.GCUIMobileWidget = GCUIMobileWidget;
    $(window.document).trigger("gcuiinit");
    var gcuiWidget = (function (_super) {
        __extends(gcuiWidget, _super);
        function gcuiWidget() {
            _super.apply(this, arguments);

            this._widgetCreated = false;
        }
        gcuiWidget.prototype._baseWidget = function () {
            return this._isMobile ? $.mobile.widget : $.Widget;
        };
        gcuiWidget.prototype._createWidget = function (options, element) {
            this._widgetCreated = true;
            if(this._syncEventPrefix) {
                this.widgetEventPrefix = this.widgetName;
            }
            if(window.applyTouchUtilEvents) {
                $ = window.applyTouchUtilEvents($);
            }
            this._baseWidget().prototype._createWidget.apply(this, arguments);
        };
        gcuiWidget.prototype._create = function () {
            this._baseWidget().prototype._create.apply(this, arguments);
        };
        gcuiWidget.prototype._init = function () {
            this._baseWidget().prototype._init.apply(this, arguments);
        };
        gcuiWidget.prototype.destroy = function () {
            this._baseWidget().prototype.destroy.apply(this, arguments);
        };
        gcuiWidget.prototype._setOption = function (name, value) {
            this._baseWidget().prototype._setOption.apply(this, arguments);
            if(name === "disabled" && value && this._isMobile) {
                this.element.removeClass("ui-state-disabled").addClass(this.options.uiCSS.stateDisabled);
            }
        };
        return gcuiWidget;
    })(GCUIMobileWidget);
    gcui.gcuiWidget = gcuiWidget;
    gcuiWidget.prototype._syncEventPrefix = true;
    gcuiWidget.prototype._isMobile = false;
    if($.mobile != null && $.gcui.autoMobilize === true) {
        $.extend(true, gcuiWidget.prototype.options.uiCSS, $.gcui.mobileCSS);
        gcuiWidget.prototype._isMobile = true;
        gcuiWidget.prototype.options = $.extend(true, {}, gcuiWidget.prototype.options, gcuiWidget.prototype._baseWidget().prototype.options);
        gcuiWidget.prototype.enhanceWithin = function (target, useKeepNative) {
            if(!this._widgetCreated) {
                $.mobile.widget.prototype.enhanceWithin.apply(this, arguments);
            }
        };
        gcuiWidget.prototype._getCreateOptions = function () {
            var ele = this.element, baseOptions,
                optionsParser = function (value) {
                    if (typeof value === 'undefined') {
                        return {};
                    } else if (value === null) {
                        return {};
                    }
                    var reg = /(?:(?:\{[\n\r\t\s]*(.+?)\s*\:[\n\r\t\s]*)|(?:,[\n\r\t\s]*(.+?)\s*\:[\n\r\t\s]*))('(.*?[^\\])')?/gi, arrReg = /\[.*?(?=[\]\[])|[\]\[].*?(?=[\]])/gi, str = value.replace(reg, function (i, str1, str2, str3) {
                        var result, reg1 = /[\n\r\t\s]*['"]?([^\{,\s]+?)['"]?\s*:[\n\r\t\s]*/i, reg2 = /\:[\n\r\t\s]*(?:'(.*)')?/i;
                        result = i.replace(reg1, "\"$1\":");
                        if (str3) {
                            return result.replace(reg2, ":\"$1\"");
                        }
                        return result;
                    }).replace(arrReg, function (i) {
                        var reg1 = /'(.*?[^\\])'/g;
                        return i.replace(reg1, "\"$1\"");
                    });
                    return $.parseJSON(str);
                },
                options = optionsParser(ele.attr("data-" + $.mobile.nsNormalize("options"))),
                css;

            baseOptions = $.mobile.widget.prototype._getCreateOptions.apply(this, arguments);
            css = $.extend(true, {}, this.options.uiCSS);
            this.theme = this.options.theme !== undefined ? this.options.theme : this.element.jqmData("theme");
            if (this.theme) {
                $.gcui.addThemeToMobileCSS(this.theme, css);
            }
            return $.extend(baseOptions, {
                uiCSS: css
            }, options);
        };
        $.widget("gcui.widget", $.mobile.widget, gcuiWidget.prototype);
        $(document).on("pageshow", function (event, ui) {
            if(event.target == null) {
                return;
            }
            var page = $(event.target);
            if(page.gcuiTriggerVisibility) {
                page.gcuiTriggerVisibility();
            }
        });
    } else {
        gcuiWidget.prototype.options = $.extend(true, {}, gcuiWidget.prototype.options, gcuiWidget.prototype._baseWidget().prototype.options);
        $.widget("gcui.widget", gcuiWidget.prototype);
    }
})(gcui || (gcui = {}));

(function (gcui) {
    $.fn.extend({
        getTextSelection: function () {
            var start, end, t = this[0];
            var val = this.val();
            if (arguments.length === 0) {
                var range, stored_range, s, e;
                var browser = GC.Spread.Sheets.util.browser;
                if (browser.msie && parseInt(browser.version) < 9) {
                    try {
                        var selection = document.selection;
                        if (t.tagName.toLowerCase() != "textarea") {
                            range = selection.createRange().duplicate();
                            range.moveEnd("character", val.length);
                            s = (range.text === "" ? val.length : val.lastIndexOf(range.text));
                            range = selection.createRange().duplicate();
                            range.moveStart("character", -val.length);
                            e = range.text.length;
                        } else {
                            range = selection.createRange();
                            stored_range = range.duplicate();
                            stored_range.moveToElementText(t);
                            stored_range.setEndPoint('EndToEnd', range);
                            s = stored_range.text.length - range.text.length;
                            e = s + range.text.length;
                        }
                    } catch (e) {
                    }
                } else {
                    s = t.selectionStart;
                    e = t.selectionEnd;
                }
                var te = val.substring(s, e);
                return {
                    start: s,
                    end: e,
                    text: te,
                    replace: function (st) {
                        return val.substring(0, s) + st + val.substring(e, val.length);
                    }
                };
            } else if (arguments.length === 1) {
                if (typeof arguments[0] === "object" && typeof arguments[0].start === "number" && typeof arguments[0].end === "number") {
                    start = arguments[0].start;
                    end = arguments[0].end;
                } else if (typeof arguments[0] === "string") {
                    if ((start = val.indexOf(arguments[0])) > -1) {
                        end = start + arguments[0].length;
                    }
                } else if (Object.prototype.toString.call(arguments[0]) === "[object RegExp]") {
                    var re = arguments[0].exec(val);
                    if (re != null) {
                        start = re.index;
                        end = start + re[0].length;
                    }
                }
            } else if (arguments.length === 2) {
                if (typeof arguments[0] === "number" && typeof arguments[1] === "number") {
                    start = arguments[0];
                    end = arguments[1];
                }
            }
            if (typeof start === "undefined") {
                start = 0;
                end = val.length;
            }
            if (browser.msie) {
                var selRange = t.createTextRange();
                selRange.collapse(true);
                selRange.moveStart('character', start);
                selRange.moveEnd('character', end - start);
                selRange.select();
            } else {
                t.selectionStart = start;
                t.selectionEnd = end;
            }
        },
        getContent: function (url) {
            return this.each(function () {
                this.innerHTML = '<iframe frameborder="0" style="width: 100%; height: 100%;" src="' + url + '">"';
            });
        },
        addVisibilityObserver: function (h, namespace) {
            return this.each(function () {
                var _this = this;
                $(this).addClass("gcui-observer-visibility");
                $(this).bind("gcuimovisibilitychanged" + (namespace ? ("." + namespace) : ""), function (e) {
                    h.apply(_this, arguments);
                    e.stopPropagation();
                });
            });
        },
        removeVisibilityObserver: function (h) {
            return this.each(function () {
                $(this).removeClass("gcui-observer-visibility");
                if (!h) {
                    $(this).unbind("gcuimovisibilitychanged");
                } else if (jQuery.isFunction(h)) {
                    $(this).unbind("gcuimovisibilitychanged", h);
                } else {
                    $(this).unbind("gcuimovisibilitychanged." + h);
                }
            });
        },
        triggerVisibility: function () {
            return this.each(function () {
                var $el = $(this);
                if ($el.hasClass("gcui-observer-visibility")) {
                    $el.trigger("gcuimovisibilitychanged");
                }
                $el.find(".gcui-observer-visibility").trigger("gcuimovisibilitychanged");
            });
        },
        leftBorderWidth: function () {
            var blw = parseFloat($(this).css("borderLeftWidth"));
            var pl = parseFloat($(this).css("padding-left"));
            var ml = 0;
            if ($(this).css("margin-left") !== "auto") {
                ml = parseFloat($(this).css("margin-left"));
            }
            return naNTest(blw) + naNTest(pl) + naNTest(ml);
        },
        rightBorderWidth: function () {
            var brw = parseFloat($(this).css("borderRightWidth"));
            var pr = parseFloat($(this).css("padding-right"));
            var mr = 0;
            if ($(this).css("margin-right") !== "auto") {
                mr = parseFloat($(this).css("margin-right"));
            }
            return naNTest(brw) + naNTest(pr) + naNTest(mr);
        },
        topBorderWidth: function () {
            var blw = parseFloat($(this).css("borderTopWidth"));
            var pl = parseFloat($(this).css("padding-top"));
            var ml = 0;
            if ($(this).css("margin-top") !== "auto") {
                ml = parseFloat($(this).css("margin-top"));
            }
            return naNTest(blw) + naNTest(pl) + naNTest(ml);
        },
        bottomBorderWidth: function () {
            var brw = parseFloat($(this).css("borderBottomWidth"));
            var pr = parseFloat($(this).css("padding-bottom"));
            var mr = 0;
            if ($(this).css("margin-bottom") !== "auto") {
                mr = parseFloat($(this).css("margin-bottom"));
            }
            return naNTest(brw) + naNTest(pr) + naNTest(mr);
        },
        borderSize: function () {
            var bw = $(this).leftBorderWidth() + $(this).rightBorderWidth();
            var bh = $(this).topBorderWidth() + $(this).bottomBorderWidth();
            var b = {
                width: bw,
                height: bh
            };
            return b;
        },
        setOutWidth: function (width) {
            var bw = $(this).leftBorderWidth() + $(this).rightBorderWidth();
            $(this).width(width - bw);
            return this;
        },
        setOutHeight: function (height) {
            var bh = $(this).topBorderWidth() + $(this).bottomBorderWidth();
            $(this).height(height - bh);
            return this;
        },
        getWidget: function () {
            var widgetName = this.data("widgetName");
            if (widgetName && widgetName !== "") {
                return this.data(widgetName);
            }
            return null;
        },
        gcuishow: function (animation, customAnimations, customAnimationOptions, showing, shown) {
            var animated = animation.animated || false, duration = animation.duration || 400, easing = animation.easing, option = animation.option || {
            };
            if (showing && $.isFunction(showing)) {
                showing.call(this);
            }
            if (animated) {
                if (customAnimations && customAnimations[animated]) {
                    customAnimations[animated](animation, $.extend(customAnimationOptions, {
                        complete: shown
                    }));
                    return;
                }
                if ($.effects) {
                    if ($.effects[animated] || ($.effects.effect && $.effects.effect[animated])) {
                        this.show(animated, $.extend(option, {
                            easing: easing
                        }), duration, shown);
                        return;
                    }
                }
            }
            this.show();
            if (shown && $.isFunction(shown)) {
                shown.call(this);
            }
        },
        gcuihide: function (animation, customAnimations, customAnimationOptions, hiding, hidden) {
            var animated = animation.animated || false, duration = animation.duration || 400, easing = animation.easing, option = animation.option || {
            };
            if (hiding && $.isFunction(hiding)) {
                hiding.call(this);
            }
            if (animated) {
                if (customAnimations && customAnimations[animated]) {
                    customAnimations[animated](animation, $.extend(customAnimationOptions, {
                        complete: hidden
                    }));
                    return;
                }
                if ($.effects) {
                    if ($.effects[animated] || ($.effects.effect && $.effects.effect[animated])) {
                        this.stop().hide(animated, $.extend(option, {
                            easing: easing
                        }), duration, hidden);
                        return;
                    }
                }
            }
            this.hide();
            if (hidden && $.isFunction(hidden)) {
                hidden.call(this);
            }
        }
    });
    function naNTest(num) {
        return isNaN(num) ? 0 : num;
    }
    $.save = function (element, set) {
        if ($.effects) {
            return $.effects.save(element, set);
        }
        for (var i = 0; i < set.length; i++) {
            if (set[i] !== null) {
                element.data("ec.storage." + set[i], element[0].style[set[i]]);
            }
        }
    };
    $.restore = function (element, set) {
        if ($.effects) {
            return $.effects.restore(element, set);
        }
        for (var i = 0; i < set.length; i++) {
            if (set[i] !== null) {
                element.css(set[i], element.data("ec.storage." + set[i]));
            }
        }
    };
    $.createWrapper = function (element) {
        if ($.effects) {
            return $.effects.createWrapper(element);
        }
        if (element.parent().is('.ui-effects-wrapper')) {
            return element.parent();
        }
        var props = {
            width: element.outerWidth(true),
            height: element.outerHeight(true),
            'float': element.css('float')
        }, wrapper = $('<div></div>').addClass('ui-effects-wrapper').css({
            fontSize: '100%',
            background: 'transparent',
            border: 'none',
            margin: 0,
            padding: 0
        }), active = document.activeElement;
        element.wrap(wrapper);
        if (element[0] === active || $.contains(element[0], active)) {
            $(active).focus();
        }
        wrapper = element.parent();
        if (element.css('position') == 'static') {
            wrapper.css({
                position: 'relative'
            });
            element.css({
                position: 'relative'
            });
        } else {
            $.extend(props, {
                position: element.css('position'),
                zIndex: element.css('z-index')
            });
            $.each([
                'top',
                'left',
                'bottom',
                'right'
            ], function (i, pos) {
                props[pos] = element.css(pos);
                if (isNaN(parseInt(props[pos], 10))) {
                    props[pos] = 'auto';
                }
            });
            element.css({
                position: 'relative',
                top: 0,
                left: 0,
                right: 'auto',
                bottom: 'auto'
            });
        }
        return wrapper.css(props).show();
    };
    $.removeWrapper = function (element) {
        if ($.effects) {
            return $.effects.removeWrapper(element);
        }
        var parent, active = document.activeElement;
        if (element.parent().is('.ui-effects-wrapper')) {
            parent = element.parent().replaceWith(element);
            if (element[0] === active || $.contains(element[0], active)) {
                $(active).focus();
            }
            return parent;
        }
        return element;
    };
    $.setMode = function (el, mode) {
        if ($.effects) {
            return $.effects.setMode(el, mode);
        }
        if (mode === "toggle") {
            mode = el.is(":hidden") ? "show" : "hide";
        }
        return mode;
    };
    var gcuiCharValidator = function () {
    };
    $.extend(gcuiCharValidator.prototype, {
        _UTFPunctuationsString: ' ! \" # % & \' ( ) * , - . / : ; ? @ [ \\ ] { } \u00a1 \u00ab \u00ad \u00b7 \u00bb \u00bf \u037e \u0387 \u055a \u055b \u055c \u055d \u055e \u055f \u0589 \u058a \u05be \u05c0 \u05c3 \u05f3 \u05f4 \u060c \u061b \u061f \u066a \u066b \u066c \u066d \u06d4 \u0700 \u0701 \u0702 \u0703 \u0704 \u0705 \u0706 \u0707 \u0708 \u0709 \u070a \u070b \u070c \u070d \u0964 \u0965 \u0970 \u0df4 \u0e4f \u0e5a \u0e5b \u0f04 \u0f05 \u0f06 \u0f07 \u0f08 \u0f09 \u0f0a \u0f0b \u0f0c \u0f0d \u0f0e \u0f0f \u0f10 \u0f11 \u0f12 \u0f3a \u0f3b \u0f3c \u0f3d \u0f85 \u104a \u104b \u104c \u104d \u104e \u104f \u10fb \u1361 \u1362 \u1363 \u1364 \u1365 \u1366 \u1367 \u1368 \u166d \u166e \u169b \u169c \u16eb \u16ec \u16ed \u17d4 \u17d5 \u17d6 \u17d7 \u17d8 \u17d9 \u17da \u17dc \u1800 \u1801 \u1802 \u1803 \u1804 \u1805 \u1806 \u1807 \u1808 \u1809 \u180a \u2010 \u2011 \u2012 \u2013 \u2014 \u2015 \u2016 \u2017 \u2018 \u2019 \u201a \u201b \u201c \u201d \u201e \u201f \u2020 \u2021 \u2022 \u2023 \u2024 \u2025 \u2026 \u2027 \u2030 \u2031 \u2032 \u2033 \u2034 \u2035 \u2036 \u2037 \u2038 \u2039 \u203a \u203b \u203c \u203d \u203e \u2041 \u2042 \u2043 \u2045 \u2046 \u2048 \u2049 \u204a \u204b \u204c \u204d \u207d \u207e \u208d \u208e \u2329 \u232a \u3001 \u3002 \u3003 \u3008 \u3009 \u300a \u300b \u300c \u300d \u300e \u300f \u3010 \u3011 \u3014 \u3015 \u3016 \u3017 \u3018 \u3019 \u301a \u301b \u301c \u301d \u301e \u301f \u3030 \ufd3e \ufd3f \ufe30 \ufe31 \ufe32 \ufe35 \ufe36 \ufe37 \ufe38 \ufe39 \ufe3a \ufe3b \ufe3c \ufe3d \ufe3e \ufe3f \ufe40 \ufe41 \ufe42 \ufe43 \ufe44 \ufe49 \ufe4a \ufe4b \ufe4c \ufe50 \ufe51 \ufe52 \ufe54 \ufe55 \ufe56 \ufe57 \ufe58 \ufe59 \ufe5a \ufe5b \ufe5c \ufe5d \ufe5e \ufe5f \ufe60 \ufe61 \ufe63 \ufe68 \ufe6a \ufe6b \uff01 \uff02 \uff03 \uff05 \uff06 \uff07 \uff08 \uff09 \uff0a \uff0c \uff0d \uff0e \uff0f \uff1a \uff1b \uff1f \uff20 \uff3b \uff3c \uff3d \uff5b \uff5d \uff61 \uff62 \uff63 \uff64\';this.UTFWhitespacesString_=\'\t \u000b \u000c \u001f   \u00a0 \u1680 \u2000 \u2001 \u2002 \u2003 \u2004 \u2005 \u2006 \u2007 \u2008 \u2009 \u200a \u200b \u2028 \u202f \u3000',
        isDigit: function (c) {
            var code = c.charCodeAt(0);
            return (code >= 48 && code < 58);
        },
        isLetter: function (c) {
            return !!((c + '').match(new RegExp('[A-Za-z\u00aa\u00b5\u00ba\u00c0-\u00d6\u00d8-\u00f6\u00f8-\u021f\u0222-\u0233\u0250-\u02ad\u02b0-\u02b8\u02bb-\u02c1\u02d0\u02d1\u02e0-\u02e4\u02ee\u037a\u0386\u0388-\u038a\u038c\u038e-\u03a1\u03a3-\u03ce\u03d0-\u03d7\u03da-\u03f3\u0400-\u0481\u048c-\u04c4\u04c7\u04c8\u04cb\u04cc\u04d0-\u04f5\u04f8\u04f9\u0531-\u0556\u0559\u0561-\u0587\u05d0-\u05ea\u05f0-\u05f2\u0621-\u063a\u0640-\u064a\u0671-\u06d3\u06d5\u06e5\u06e6\u06fa-\u06fc\u0710\u0712-\u072c\u0780-\u07a5\u0905-\u0939\u093d\u0950\u0958-\u0961\u0985-\u098c\u098f\u0990\u0993-\u09a8\u09aa-\u09b0\u09b2\u09b6-\u09b9\u09dc\u09dd\u09df-\u09e1\u09f0\u09f1\u0a05-\u0a0a\u0a0f\u0a10\u0a13-\u0a28\u0a2a-\u0a30\u0a32\u0a33\u0a35\u0a36\u0a38\u0a39\u0a59-\u0a5c\u0a5e\u0a72-\u0a74\u0a85-\u0a8b\u0a8d\u0a8f-\u0a91\u0a93-\u0aa8\u0aaa-\u0ab0\u0ab2\u0ab3\u0ab5-\u0ab9\u0abd\u0ad0\u0ae0\u0b05-\u0b0c\u0b0f\u0b10\u0b13-\u0b28\u0b2a-\u0b30\u0b32\u0b33\u0b36-\u0b39\u0b3d\u0b5c\u0b5d\u0b5f-\u0b61\u0b85-\u0b8a\u0b8e-\u0b90\u0b92-\u0b95\u0b99\u0b9a\u0b9c\u0b9e\u0b9f\u0ba3\u0ba4\u0ba8-\u0baa\u0bae-\u0bb5\u0bb7-\u0bb9\u0c05-\u0c0c\u0c0e-\u0c10\u0c12-\u0c28\u0c2a-\u0c33\u0c35-\u0c39\u0c60\u0c61\u0c85-\u0c8c\u0c8e-\u0c90\u0c92-\u0ca8\u0caa-\u0cb3\u0cb5-\u0cb9\u0cde\u0ce0\u0ce1\u0d05-\u0d0c\u0d0e-\u0d10\u0d12-\u0d28\u0d2a-\u0d39\u0d60\u0d61\u0d85-\u0d96\u0d9a-\u0db1\u0db3-\u0dbb\u0dbd\u0dc0-\u0dc6\u0e01-\u0e30\u0e32\u0e33\u0e40-\u0e46\u0e81\u0e82\u0e84\u0e87\u0e88\u0e8a\u0e8d\u0e94-\u0e97\u0e99-\u0e9f\u0ea1-\u0ea3\u0ea5\u0ea7\u0eaa\u0eab\u0ead-\u0eb0\u0eb2\u0eb3\u0ebd\u0ec0-\u0ec4\u0ec6\u0edc\u0edd\u0f00\u0f40-\u0f47\u0f49-\u0f6a\u0f88-\u0f8b\u1000-\u1021\u1023-\u1027\u1029\u102a\u1050-\u1055\u10a0-\u10c5\u10d0-\u10f6\u1100-\u1159\u115f-\u11a2\u11a8-\u11f9\u1200-\u1206\u1208-\u1246\u1248\u124a-\u124d\u1250-\u1256\u1258\u125a-\u125d\u1260-\u1286\u1288\u128a-\u128d\u1290-\u12ae\u12b0\u12b2-\u12b5\u12b8-\u12be\u12c0\u12c2-\u12c5\u12c8-\u12ce\u12d0-\u12d6\u12d8-\u12ee\u12f0-\u130e\u1310\u1312-\u1315\u1318-\u131e\u1320-\u1346\u1348-\u135a\u13a0-\u13f4\u1401-\u166c\u166f-\u1676\u1681-\u169a\u16a0-\u16ea\u1780-\u17b3\u1820-\u1877\u1880-\u18a8\u1e00-\u1e9b\u1ea0-\u1ef9\u1f00-\u1f15\u1f18-\u1f1d\u1f20-\u1f45\u1f48-\u1f4d\u1f50-\u1f57\u1f59\u1f5b\u1f5d\u1f5f-\u1f7d\u1f80-\u1fb4\u1fb6-\u1fbc\u1fbe\u1fc2-\u1fc4\u1fc6-\u1fcc\u1fd0-\u1fd3\u1fd6-\u1fdb\u1fe0-\u1fec\u1ff2-\u1ff4\u1ff6-\u1ffc\u207f\u2102\u2107\u210a-\u2113\u2115\u2119-\u211d\u2124\u2126\u2128\u212a-\u212d\u212f-\u2131\u2133-\u2139\u3005\u3006\u3031-\u3035\u3041-\u3094\u309d\u309e\u30a1-\u30fa\u30fc-\u30fe\u3105-\u312c\u3131-\u318e\u31a0-\u31b7\u3400-\u4db5\u4e00-\u9fa5\ua000-\ua48c\uac00-\ud7a3\uf900-\ufa2d\ufb00-\ufb06\ufb13-\ufb17\ufb1d\ufb1f-\ufb28\ufb2a-\ufb36\ufb38-\ufb3c\ufb3e\ufb40\ufb41\ufb43\ufb44\ufb46-\ufbb1\ufbd3-\ufd3d\ufd50-\ufd8f\ufd92-\ufdc7\ufdf0-\ufdfb\ufe70-\ufe72\ufe74\ufe76-\ufefc\uff21-\uff3a\uff41-\uff5a\uff66-\uffbe\uffc2-\uffc7\uffca-\uffcf\uffd2-\uffd7\uffda-\uffdc]')));
        },
        isLetterOrDigit: function (c) {
            return this.isLetter(c) || this.isDigit(c);
        },
        isDoubleByteNumber: function (c) {
            var code = c.charCodeAt(0);
            return code >= 65296 && code < 65306;
        },
        isSymbol: function (c) {
            var re = new RegExp('[$+<->^`|~\u00a2-\u00a9\u00ac\u00ae-\u00b1\u00b4\u00b6\u00b8\u00d7\u00f7\u02b9\u02ba\u02c2-\u02cf\u02d2-\u02df\u02e5-\u02ed\u0374\u0375\u0384\u0385\u0482\u06e9\u06fd\u06fe\u09f2\u09f3\u09fa\u0b70\u0e3f\u0f01-\u0f03\u0f13-\u0f17\u0f1a-\u0f1f\u0f34\u0f36\u0f38\u0fbe-\u0fc5\u0fc7-\u0fcc\u0fcf\u17db\u1fbd\u1fbf-\u1fc1\u1fcd-\u1fcf\u1fdd-\u1fdf\u1fed-\u1fef\u1ffd\u1ffe\u2044\u207a-\u207c\u208a-\u208c\u20a0-\u20af\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211e-\u2123\u2125\u2127\u2129\u212e\u2132\u213a\u2190-\u21f3\u2200-\u22f1\u2300-\u2328\u232b-\u237b\u237d-\u239a\u2400-\u2426\u2440-\u244a\u249c-\u24e9\u2500-\u2595\u25a0-\u25f7\u2600-\u2613\u2619-\u2671\u2701-\u2704\u2706-\u2709\u270c-\u2727\u2729-\u274b\u274d\u274f-\u2752\u2756\u2758-\u275e\u2761-\u2767\u2794\u2798-\u27af\u27b1-\u27be\u2800-\u28ff\u2e80-\u2e99\u2e9b-\u2ef3\u2f00-\u2fd5\u2ff0-\u2ffb\u3004\u3012\u3013\u3020\u3036\u3037\u303e\u303f\u309b\u309c\u3190\u3191\u3196-\u319f\u3200-\u321c\u322a-\u3243\u3260-\u327b\u327f\u328a-\u32b0\u32c0-\u32cb\u32d0-\u32fe\u3300-\u3376\u337b-\u33dd\u33e0-\u33fe\ua490-\ua4a1\ua4a4-\ua4b3\ua4b5-\ua4c0\ua4c2-\ua4c4\ua4c6\ufb29\ufe62\ufe64-\ufe66\ufe69\uff04\uff0b\uff1c-\uff1e\uff3e\uff40\uff5c\uff5e\uffe0-\uffe6\uffe8-\uffee\ufffc\ufffd]');
            return re.test(c + '');
        },
        isPunctuation: function (c) {
            return this._UTFPunctuationsString.indexOf(c) >= 0;
        },
        isPrintableChar: function (c) {
            if ((!this.isLetterOrDigit(c) && !this.isPunctuation(c)) && !this.isSymbol(c) && !this.isDoubleByteNumber(c)) {
                return (c === ' ');
            }
            return true;
        },
        isAscii: function (c) {
            return (c >= '!') && (c <= '~');
        },
        isAsciiLetter: function (c) {
            return ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'));
        },
        isUpper: function (c) {
            return c.toUpperCase() === c;
        },
        isLower: function (c) {
            return c.toLowerCase() === c;
        },
        isAlphanumeric: function (c) {
            return !this.isLetter(c) ? this.isDigit(c) : true;
        },
        isAciiAlphanumeric: function (c) {
            if (((c < '0') || (c > '9')) && ((c < 'A') || (c > 'Z'))) {
                if (c >= 'a') {
                    return (c <= 'z');
                }
                return false;
            }
            return true;
        },
        setChar: function (input, ch, pos) {
            if (pos >= input.length || pos < 0) {
                return input;
            }
            return '' || input.substr(0, pos) + ch + input.substr(pos + 1);
        }
    });
    if (!$.fn.zIndex) {
        $.fn.zIndex = function (zIndex) {
            if (zIndex !== undefined) {
                return this.css("zIndex", zIndex);
            }
            if (this.length) {
                var elem = $(this[0]), position, value;
                while (elem.length && elem[0] !== document) {
                    position = elem.css("position");
                    if (position === "absolute" || position === "relative" || position === "fixed") {
                        value = parseInt(elem.css("zIndex"), 10);
                        if (!isNaN(value) && value !== 0) {
                            return value;
                        }
                    }
                    elem = elem.parent();
                }
            }
            return 0;
        };
    }
    var c__escapeArr1 = [
        '\n',
        '\r',
        '"',
        '@',
        '+',
        '\'',
        '<',
        '>',
        '%',
        '{',
        '}'
    ], c__escapeArr2 = [
        "!ESC!NN!",
        "!ESC!RR!",
        "!ESC!01!",
        "!ESC!02!",
        "!ESC!03!",
        "!ESC!04!",
        "!ESC!05!",
        "!ESC!06!",
        "!ESC!07!",
        "!ESC!08!",
        "!ESC!09!"
    ], c__escapeArr3 = [
        "(\n)",
        "(\r)",
        "(\")",
        "(@)",
        "(\\+)",
        "(')",
        "(\\<)",
        "(\\>)",
        "(%)",
        "(\\{)",
        "(\\})"
    ];
    if (!$.gcuiutil) {
        $.extend({
            gcuiutil: {
                charValidator: new gcuiCharValidator(),
                encodeString: function (s) {
                    for (var i = 0; i < c__escapeArr1.length; i++) {
                        var r = new RegExp(c__escapeArr3[i], "g");
                        s = s.replace(r, c__escapeArr2[i]);
                    }
                    return s;
                },
                decodeString: function (s) {
                    if (s === "") {
                        return;
                    }
                    for (var i = 0; i < c__escapeArr2.length; i++) {
                        var r = new RegExp(c__escapeArr2[i], "g");
                        s = s.replace(r, c__escapeArr1[i]);
                    }
                    return s;
                }
            }
        });
    }
    ;
    if (!jQuery.uaMatch) {
        jQuery.uaMatch = function (ua) {
            ua = ua.toLowerCase();
            var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];
            return {
                browser: match[1] || "",
                version: match[2] || "0"
            };
        };
    }
    if (!jQuery.browser) {
        var matched = jQuery.uaMatch(navigator.userAgent), browser = {
            version: "0",
            msie: false,
            opera: false,
            safari: false,
            mozilla: false,
            webkit: false,
            chrome: false
        };
        if (matched.browser) {
            browser[matched.browser] = true;
            browser.version = matched.version;
        }
        if (browser.chrome) {
            browser.webkit = true;
        } else if (browser.webkit) {
            browser.safari = true;
        }
        jQuery.browser = browser;
    }
    if ($.ui && $.ui.position && $.ui.position.flipfit) {
        $.ui.position.flip.top = function (position, data) {
            var within = data.within, withinOffset = within.offset.top + within.scrollTop, outerHeight = within.height, offsetTop = within.isWindow ? within.scrollTop : within.offset.top, collisionPosTop = position.top - data.collisionPosition.marginTop, overTop = collisionPosTop - offsetTop, overBottom = collisionPosTop + data.collisionHeight - outerHeight - offsetTop, top = data.my[1] === "top", myOffset = top ? -data.elemHeight : data.my[1] === "bottom" ? data.elemHeight : 0, atOffset = data.at[1] === "top" ? data.targetHeight : data.at[1] === "bottom" ? -data.targetHeight : 0, offset = -2 * data.offset[1], newOverTop, newOverBottom;
            if (overTop < 0) {
                newOverBottom = position.top + myOffset + atOffset + offset + data.collisionHeight - outerHeight - withinOffset;
                if ((position.top + myOffset + atOffset + offset) > overTop && (newOverBottom < 0 || newOverBottom < Math.abs(overTop))) {
                    position.top += myOffset + atOffset + offset;
                }
            } else if (overBottom > 0) {
                newOverTop = position.top - data.collisionPosition.marginTop + myOffset + atOffset + offset - offsetTop;
                if ((position.top + myOffset + atOffset + offset + data.collisionHeight - outerHeight - offsetTop) < overBottom && (newOverTop > 0 || Math.abs(newOverTop) < overBottom)) {
                    position.top += myOffset + atOffset + offset;
                }
            }
        };
    }
    var $position = $.fn.position;
    $.fn.position = function (options) {
        if (options && $.isPlainObject(options) && options.offset) {
            var my = (options.my || "").split(" "), rhorizontal = /left|center|right/, rvertical = /top|center|bottom/, offset = (options.offset || "").split(" ");
            if (my.length === 1) {
                rhorizontal.test(my[0]) ? my.concat([
                    "center"
                ]) : rvertical.test(my[0]) ? [
                    "center"
                ].concat(my) : [
                    "center",
                    "center"
                ];
            }
            if (offset.length === 1) {
                offset.concat(offset[0]);
            }
            $.each(my, function (i, m) {
                if (/\+|-/.test(offset[i])) {
                    my[i] = my[i] + offset[i];
                } else {
                    my[i] = my[i] + "+" + offset[i];
                }
            });
            options.my = my.join(" ");
        }
        return $position.apply(this, arguments);
    };
    function getKeyCodeEnum() {
        if ($.ui && $.ui.keyCode) {
            return $.ui.keyCode;
        }
        if ($.mobile && $.mobile.keyCode) {
            return $.mobile.keyCode;
        }
        throw "keyCode object is not found";
    }
    gcui.getKeyCodeEnum = getKeyCodeEnum;
})(gcui || (gcui = {}));
function __gcuiReadOptionEvents(eventsArr, widgetInstance) {
    for (var k = 0; k < eventsArr.length; k++) {
        if (widgetInstance.options[eventsArr[k]] !== null) {
            widgetInstance.element.bind(eventsArr[k], widgetInstance.options[eventsArr[k]]);
        }
    }
    for (var k in widgetInstance.options) {
        if (k.indexOf(" ") !== -1) {
            var arr = k.split(" ");
            for (var j = 0; j < arr.length; j++) {
                if (arr[j].length > 0) {
                    widgetInstance.element.bind(arr[j], widgetInstance.options[k]);
                }
            }
        }
    }
}
;
function gcuiASPNetParseOptionsReviewer(o, k) {
    var a, v = o[k], d;
    if (v) {
        switch (typeof v) {
            case "string":
                a = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?):(\d{3})Z$/.exec(v);
                if (a) {
                    d = new Date(+a[1], +a[2] - 1, +a[3], +a[4], +a[5], +a[6], +a[7]);
                    d.setFullYear(+a[1]);
                    o[k] = d;
                }
                break;
            case "object":
                if (v.needQuotes !== undefined && v.valueString !== undefined) {
                    if (!v.needQuotes) {
                        o[k] = eval(v.valueString);
                    } else {
                        o[k] = v.valueString;
                    }
                } else {
                    for (k in v) {
                        gcuiASPNetParseOptionsReviewer(v, k);
                    }
                }
                break;
        }
    }
}
function gcuiASPNetParseOptions(o) {
    var k;
    if (!o) {
        return o;
    }
    for (k in o) {
        gcuiASPNetParseOptionsReviewer(o, k);
    }
    return o;
}

(function (gcui) {
    gcui.expando = ".gcui";
    var GCUIError = (function () {
        function GCUIError(message) {
            this.message = message;
            this.stack = "gcui" + (new Error()).stack;
            this.name = "GCUIError";
        }
        return GCUIError;
    })();
    gcui.GCUIError = GCUIError;
    var err = GCUIError;
    err.prototype = new Error();
    err.prototype["constructor"] = err;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var Expando = (function () {
            function Expando(object) {
                this.object = object;
            }
            Expando.getFrom = function getFrom(obj, create) {
                if (typeof create === "undefined") { create = true; }
                var propertyName = gcui.expando, ext;
                if (Object(obj) !== obj) {
                    return null;
                }
                ext = obj[propertyName];
                if (ext && ext.object !== obj) {
                    ext = null;
                }
                if (create && !(ext instanceof Expando && Object.prototype.hasOwnProperty.call(obj, propertyName))) {
                    ext = new Expando(obj);
                    try {
                        Object.defineProperty(obj, propertyName, {
                            value: ext,
                            configurable: false,
                            enumerable: false,
                            writable: false
                        });
                    } catch (e) {
                        obj[propertyName] = ext;
                    }
                }
                return ext;
            };
            return Expando;
        })();
        data.Expando = Expando;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        (function (util) {
            function funcClass(ctor) {
                return function () {
                    var result = function () {
                        return ctor.prototype._call.apply(result, arguments);
                    };
                    $.extend(result, ctor.prototype);
                    ctor.apply(result, arguments);
                    return result;
                };
            }
            util.funcClass = funcClass;
        })(data.util || (data.util = {}));
        var util = data.util;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var SubscriberEntry = (function () {
            function SubscriberEntry(handler, context) {
                this.handler = handler;
                this.context = context;
            }
            SubscriberEntry.prototype.trigger = function (args) {
                return this.handler.apply(this.context, args);
            };
            return SubscriberEntry;
        })();
        data.SubscriberEntry = SubscriberEntry;
        var Subscribable = (function () {
            function Subscribable(defaultContext) {
                this.defaultContext = defaultContext;
                this._entries = [];
            }
            Subscribable.prototype.subscribe = function (handler, context) {
                if (typeof context === "undefined") { context = this.defaultContext; }
                var _this = this;
                var entry = new SubscriberEntry(handler, context);
                this._entries.push(entry);
                return {
                    dispose: function () {
                        return data.util.remove(_this._entries, entry);
                    }
                };
            };
            Subscribable.prototype.trigger = function () {
                var args = [];
                for (var _i = 0; _i < (arguments.length - 0) ; _i++) {
                    args[_i] = arguments[_i + 0];
                }
                data.util.each(this._entries, function (_, e) {
                    return e.trigger(args);
                });
            };
            Subscribable.makeSubscribable = function makeSubscribable(obj) {
                var subscribable = new Subscribable(obj);
                obj.subscribe = $.proxy(subscribable.subscribe, subscribable);
                return subscribable;
            };
            return Subscribable;
        })();
        data.Subscribable = Subscribable;
        function isSubscriptable(subscribable) {
            return $.isFunction(subscribable.subscribe);
        }
        data.isSubscriptable = isSubscriptable;
        var BaseObservable = (function () {
            function BaseObservable() { }
            BaseObservable.prototype.subscribe = function (handler, context) {
                this._subscribable = this._subscribable || new Subscribable(this);
                return this._subscribable.subscribe(handler, context);
            };
            BaseObservable.prototype._trigger = function () {
                var args = [];
                for (var _i = 0; _i < (arguments.length - 0) ; _i++) {
                    args[_i] = arguments[_i + 0];
                }
                if (this._subscribable) {
                    this._subscribable.trigger.apply(this._subscribable, arguments);
                }
            };
            return BaseObservable;
        })();
        data.BaseObservable = BaseObservable;
        var _ReadOnlyObservable = (function (_super) {
            __extends(_ReadOnlyObservable, _super);
            function _ReadOnlyObservable(mutable) {
                _super.call(this);
                this.mutable = mutable;
            }
            _ReadOnlyObservable.prototype._call = function () {
                return this.mutable.value;
            };
            return _ReadOnlyObservable;
        })(BaseObservable);
        data._ReadOnlyObservable = _ReadOnlyObservable;
        var ReadOnlyObservable = data.util.funcClass(_ReadOnlyObservable);
        var _MutableObservable = (function (_super) {
            __extends(_MutableObservable, _super);
            function _MutableObservable(value, checkNewValue) {
                if (typeof checkNewValue === "undefined") { checkNewValue = false; }
                _super.call(this);
                this.value = value;
                this.checkNewValue = checkNewValue;
            }
            _MutableObservable.prototype._call = function (newValue) {
                if (arguments.length > 0 && (!this.checkNewValue || newValue !== this.value)) {
                    this.value = newValue;
                    this._trigger(newValue);
                    if (this._readOnly) {
                        this._readOnly._trigger(newValue);
                    }
                }
                return this.value;
            };
            _MutableObservable.prototype.read = function () {
                this._readOnly = this._readOnly || new ReadOnlyObservable(this);
                return this._readOnly;
            };
            return _MutableObservable;
        })(BaseObservable);
        data._MutableObservable = _MutableObservable;
        var MutableObservable = data.util.funcClass(_MutableObservable);
        function observable(value) {
            if (typeof value === "undefined") { value = null; }
            return new MutableObservable(value);
        }
        data.observable = observable;
        function observableWithNewValueCheck(value) {
            if (typeof value === "undefined") { value = null; }
            return new MutableObservable(value, true);
        }
        data.observableWithNewValueCheck = observableWithNewValueCheck;
        function isObservable(observable) {
            return $.isFunction(observable) && isSubscriptable(observable);
        }
        data.isObservable = isObservable;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        (function (util) {
            var $ = jQuery;
            function clone(obj, deep) {
                if (typeof deep === "undefined") { deep = false; }
                if ($.isArray(obj)) {
                    obj = obj.slice(0);
                } else if ($.isPlainObject(obj)) {
                    obj = $.extend(!!deep, {
                    }, obj);
                }
                return obj;
            }
            util.clone = clone;
            function isString(str) {
                return typeof str === "string" || str instanceof String;
            }
            util.isString = isString;
            function isNumeric(value) {
                return typeof value === "number";
            }
            util.isNumeric = isNumeric;
            function isInternalProperty(p) {
                return p === gcui.expando || p === $.expando;
            }
            util.isInternalProperty = isInternalProperty;
            function each(obj, fn) {
                var _this = this;
                $.each(obj, function (key, value) {
                    if (!isInternalProperty(key)) {
                        return fn.call(_this, key, value);
                    }
                });
            }
            util.each = each;
            function map(obj, fn) {
                var result = $.map(obj, fn);
                delete result[gcui.expando];
                return result;
            }
            util.map = map;
            function toStr(obj) {
                var text;
                if (obj && $.isFunction(obj.toString) && obj.toString !== Object.prototype.toString) {
                    text = obj.toString();
                } else {
                    text = JSON.stringify(obj);
                }
                if (text != null && text.length > 2 && text[0] === '"' && text[text.length - 1] === '"') {
                    text = text.substr(1, text.length - 2);
                }
                return text;
            }
            util.toStr = toStr;
            function format(format) {
                var args = [];
                for (var _i = 0; _i < (arguments.length - 1) ; _i++) {
                    args[_i] = arguments[_i + 1];
                }
                return format.replace(/{(\d+)}/g, function (m, index) {
                    return toStr(args[parseInt(index, 10)]);
                });
            }
            util.format = format;
            function every(obj, predicate) {
                var res = true;
                each(obj, function (key, value) {
                    res = value;
                    if (predicate) {
                        res = predicate.call(res, res, key);
                    }
                    if (!res) {
                        return false;
                    }
                });
                return res;
            }
            util.every = every;
            function some(obj, predicate) {
                var res = false;
                each(obj, function (key, value) {
                    res = value;
                    if (predicate) {
                        res = predicate.call(res, res, key);
                    }
                    if (res) {
                        return false;
                    }
                });
                return res;
            }
            util.some = some;
            function compare(a, b) {
                var i, len, cmp;
                if (a == null) {
                    return b == null ? 0 : -1;
                } else if (b == null) {
                    return 1;
                }
                if ($.isArray(a) && $.isArray(b)) {
                    len = Math.min(a.length, b.length);
                    for (i = 0; i < len; i++) {
                        cmp = compare(a[i], b[i]);
                        if (cmp !== 0) {
                            return cmp;
                        }
                    }
                    return a.length - b.length;
                } else if (isString(a) && isString(b)) {
                    a = a.toLowerCase();
                    b = b.toLowerCase();
                    return a < b ? -1 : a > b ? 1 : 0;
                }
                cmp = a - b;
                return isNaN(cmp) ? 0 : cmp;
            }
            util.compare = compare;
            function contains(array, elem) {
                return $.inArray(elem, array) >= 0;
            }
            util.contains = contains;
            function remove(array, elem) {
                var removed = 0, i;
                for (i = 0; i < array.length;) {
                    if (array[i] !== elem) {
                        i++;
                    } else {
                        array.splice(i, 1);
                        removed++;
                    }
                }
                return removed;
            }
            util.remove = remove;
            function pageCount(totalCount, pageSize) {
                if (totalCount == -1) {
                    return -1;
                } else if (totalCount == 0) {
                    return 0;
                } else if (!pageSize) {
                    return 1;
                } else {
                    return Math.ceil(totalCount / pageSize);
                }
            }
            util.pageCount = pageCount;
            ;
            function executeDelayed(fn, context) {
                var args = [];
                for (var _i = 0; _i < (arguments.length - 2) ; _i++) {
                    args[_i] = arguments[_i + 2];
                }
                function callback() {
                    return fn.apply(context, args);
                }
                if (typeof setTimeout === typeof undefined) {
                    return callback();
                } else {
                    setTimeout(callback, 10);
                }
            }
            util.executeDelayed = executeDelayed;
            function logError(message) {
                if (typeof console == "undefined") {
                    return;
                }
                if (console.error) {
                    console.error(message);
                } else if (console.log) {
                    console.log(message);
                }
            }
            util.logError = logError;
            function getProperty(obj, property) {
                var start = 0, value = obj, key;
                while (true) {
                    var point = property.indexOf('.', start);
                    if (point >= 0) {
                        key = property.substring(start, point);
                        start = point + 1;
                    } else if (start > 0) {
                        key = property.substring(start);
                    } else {
                        key = property;
                    }
                    value = value[key];
                    if (data.isObservable(value)) {
                        value = value();
                    }
                    if (point < 0) {
                        break;
                    }
                }
                return value;
            }
            util.getProperty = getProperty;
            function setProperty(obj, property, newValue) {
                var start = 0, key;
                while (true) {
                    var point = property.indexOf('.', start);
                    if (point >= 0) {
                        key = property.substring(start, point);
                        start = point + 1;
                    } else if (start > 0) {
                        key = property.substring(start);
                    } else {
                        key = property;
                    }
                    var value = obj[key];
                    if (point >= 0) {
                        if (data.isObservable(value)) {
                            value = value();
                        }
                        obj = value;
                    } else {
                        if (data.isObservable(value)) {
                            value(newValue);
                        } else {
                            obj[key] = newValue;
                        }
                        break;
                    }
                }
            }
            util.setProperty = setProperty;
            function isClassInstance(instance) {
                return typeof instance === "object" && !$.isArray(instance) && !$.isPlainObject(instance) && instance.constructor !== (Object.prototype).constructor;
            }
            util.isClassInstance = isClassInstance;
            function convertDateProperties(entities) {
                data.util.each(entities, function (_, entity) {
                    if (!entity || typeof entity !== "object") {
                        return;
                    }
                    data.util.each(entity, function (key, value) {
                        var match;
                        if (!data.util.isString(value)) {
                            return;
                        }
                        match = /\/Date\((-?\d+)\)\//.exec(value);
                        if (!match) {
                            return;
                        }
                        entity[key] = new Date(parseInt(match[1], 10));
                    });
                });
            }
            util.convertDateProperties = convertDateProperties;
            var HashMapEntry = (function () {
                function HashMapEntry(key) {
                    this.key = key;
                }
                return HashMapEntry;
            })();
            util.HashMapEntry = HashMapEntry;
            var HashMap = (function () {
                function HashMap() {
                    this.hash = {
                    };
                }
                HashMap.prototype.getEntry = function (key, create) {
                    if (typeof create === "undefined") { create = false; }
                    if (key === null) {
                        if (!this.nullEntry && create) {
                            this.nullEntry = new HashMapEntry(key);
                        }
                        return this.nullEntry;
                    }
                    var strKey = String(key);
                    var list = this.hash[strKey];
                    var entry;
                    if (list == null) {
                        if (!create) {
                            return null;
                        }
                        list = [];
                        this.hash[strKey] = list;
                    }
                    for (var i = 0; i < list.length; i++) {
                        if (list[i].key === key) {
                            return list[i];
                        }
                    }
                    if (create) {
                        entry = new HashMapEntry(key);
                        list.push(entry);
                    }
                    return null;
                };
                HashMap.prototype.containsKey = function (key) {
                    return !!this.getEntry(key);
                };
                HashMap.prototype.get = function (key, defaultValue) {
                    if (typeof defaultValue === "undefined") { defaultValue = null; }
                    var entry = this.getEntry(key);
                    return entry ? entry.value : defaultValue;
                };
                HashMap.prototype.put = function (key, value) {
                    this.getEntry(key, true).value = value;
                };
                return HashMap;
            })();
            util.HashMap = HashMap;
        })(data.util || (data.util = {}));
        var util = data.util;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        data.errors = {
        };
        data.errors._register = function (messages) {
            data.util.each(messages, function (name, msg) {
                function create() {
                    var fmtArgs = [];
                    for (var _i = 0; _i < (arguments.length - 0) ; _i++) {
                        fmtArgs[_i] = arguments[_i + 0];
                    }
                    var lastChar;
                    if ($.isFunction(msg)) {
                        msg = msg.apply(this, arguments);
                    } else if (arguments.length > 0) {
                        fmtArgs.unshift(msg);
                        msg = data.util.format.apply(this, fmtArgs);
                    }
                    msg = $.trim(msg);
                    lastChar = msg[msg.length - 1];
                    if (lastChar !== '.' && lastChar !== '!' && lastChar !== '?') {
                        msg += '.';
                    }
                    return new gcui.GCUIError(msg);
                }
                data.errors[name] = function () {
                    throw create.apply(this, arguments);
                };
                data.errors[name].create = create;
            });
        };
        data.errors._register({
            indexOutOfBounds: "Index is outside the bounds of the array.",
            notImplemented: "The operation is not implemented",
            unsupprtedOperation: "Unsupported operation",
            unsupportedFilterOperator: "Unsupported filter operator: {0}",
            unsupportedDataSource: "Unsupported data source",
            argument: function (paramName) {
                var message = "Unexpected argument value.";
                if (paramName) {
                    message += "\nParameter name: " + paramName;
                }
                return message;
            },
            argumentNull: "Argument '{0}' is null/undefined",
            noParser: "There is no parser for type '{0}'",
            noUrl: "Url is not specified",
            cantConvert: "Value can't be converted to type '{0}': '{1}'",
            noGlobalize: "Globalize is not defined. Make sure you include globalize.js",
            itemNotInView: "Item {0} is not in the data view",
            unsupportedFilterFormat: "The filter format is not supported",
            multiPropertyKeysNotSupported: "Entities with multiple properties in the primary key are not supported. Entity type: {0}",
            keyPropertyNotFound: "Key property not found in {0} entity type"
        });
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        (function (filtering) {
            var $ = jQuery;
            filtering.opMap = {
                "==": "equals",
                doesnotcontain: "notcontain",
                "!=": "notequal",
                ">": "greater",
                "<": "less",
                ">=": "greaterorequal",
                "<=": "lessorequal",
                isnotempty: "notisempty",
                isnotnull: "notisnull"
            };
            function findOperator(name, throwIfNotFound) {
                if (typeof throwIfNotFound === "undefined") { throwIfNotFound = false; }
                name = name.toLowerCase();
                var op = filtering.ops[name];
                if (!op) {
                    var mappedName = filtering.opMap[name];
                    if (mappedName) {
                        op = filtering.ops[mappedName];
                    }
                }
                if (!op && throwIfNotFound) {
                    data.errors.unsupportedOperator(name);
                }
                return op;
            }
            function normalizeCondition(cond) {
                var filter;
                if (!$.isPlainObject(cond)) {
                    return {
                        operator: "==",
                        op: filtering.ops.equals,
                        value: cond
                    };
                }
                var op = cond.operator || filtering.ops.equals;
                if (data.util.isString(cond.operator)) {
                    if (cond.operator.toLowerCase() === "nofilter") {
                        return null;
                    }
                    op = findOperator(cond.operator, true);
                } else if (!$.isFunction(op.apply)) {
                    data.errors.unsupportedOperator(op);
                }
                return {
                    operator: cond.operator,
                    op: op,
                    value: cond.value
                };
            }
            filtering.normalizeCondition = normalizeCondition;
            function compile(filter) {
                var result = {
                    original: filter,
                    func: null,
                    normalized: null
                };
                if ($.isFunction(filter)) {
                    result.func = filter;
                } else if ($.isArray(filter)) {
                    data.errors.unsupportedFilterFormat(filter);
                } else if (filter) {
                    result.normalized = {
                    };
                    var hasConditions = false;
                    data.util.each(filter, function (prop, cond) {
                        if ($.isArray(cond)) {
                            data.errors.unsupportedFilterFormat();
                        }
                        cond = normalizeCondition(cond);
                        if (cond) {
                            result.normalized[prop] = cond;
                            hasConditions = true;
                        }
                    });
                    if (!hasConditions) {
                        result.normalized = null;
                    } else {
                        result.func = function (x) {
                            return data.util.every(result.normalized, function (cond, prop) {
                                var propValue = data.util.getProperty(x, prop);
                                return cond.op.apply(propValue, cond.value);
                            });
                        };
                    }
                }
                if (!result.normalized && !result.func) {
                    result.func = function (x) {
                        return true;
                    };
                }
                return result;
            }
            filtering.compile = compile;
            filtering.ops = (function () {
                var ops = {},
                    types = {
                    str: ["string"],
                    prim: ["string", "number", "datetime", "currency", "boolean"]
                };
                function op(name, displayName, arity, types, apply) {
                    return ops[name.toLowerCase()] = {
                        name: name,
                        displayName: displayName,
                        arity: arity,
                        applicableTo: types,
                        apply: apply
                    };
                }
                function preprocessOperand(value) {
                    if (value instanceof Date) {
                        value = value.getTime();
                    }
                    if (data.util.isString(value)) {
                        value = value.toLowerCase();
                    }
                    return value;
                }
                function bin(name, displayName, types, apply) {
                    op(name, displayName, 2, types, function (left, right) {
                        return apply(preprocessOperand(left), preprocessOperand(right));
                    });
                }
                function unary(name, displayName, types, apply) {
                    op(name, displayName, 1, types, apply);
                }
                function binprim(name, displayName, apply) {
                    bin(name, displayName, types.prim, apply);
                }
                function binstr(name, displayName, apply) {
                    bin(name, displayName, types.str, apply);
                }
                binprim("Equals", "Equals", function (l, r) {
                    return l == r;
                });
                binprim("NotEqual", "Not equal", function (l, r) {
                    return l != r;
                });
                binprim("Greater", "Greater than", function (l, r) {
                    return l > r;
                });
                binprim("Less", "Less than", function (l, r) {
                    return l < r;
                });
                binprim("GreaterOrEqual", "Greater or equal", function (l, r) {
                    return l >= r;
                });
                binprim("LessOrEqual", "Less or equal", function (l, r) {
                    return l <= r;
                });
                binstr("Contains", "Contains", function (left, right) {
                    return left == right || left && left.indexOf && left.indexOf(right) >= 0;
                });
                binstr("NotContain", "Does not contain", function (left, right) {
                    return left != right && (!left || !left.indexOf || left.indexOf(right) < 0);
                });
                binstr("BeginsWith", "Begins with", function (left, right) {
                    return left == right || left && left.indexOf && left.indexOf(right) == 0;
                });
                binstr("EndsWith", "Ends with", function (left, right) {
                    var idx;
                    if (!data.util.isString(left) || !data.util.isString(right)) {
                        return false;
                    }
                    idx = left.lastIndexOf(right);
                    return idx >= 0 && left.length - idx === right.length;
                });
                unary("IsEmpty", "Is empty", types.str, function (x) {
                    return !x && x !== 0 && x !== false;
                });
                unary("NotIsEmpty", "Is not empty", types.str, function (x) {
                    return !!x || x === 0 || x === false;
                });
                unary("IsNull", "Is null", types.prim, function (x) {
                    return x == null;
                });
                unary("NotIsNull", "Is not null", types.prim, function (x) {
                    return x != null;
                });
                return ops;
            })();
        })(data.filtering || (data.filtering = {}));
        var filtering = data.filtering;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        (function (sorting) {
            function normalize(sort) {
                var result = [];
                sort = data.util.isString(sort) ? sort.split(/,\s*/) :
                    !$.isArray(sort) ? [sort] : sort.slice(0);
                sort = $.isArray(sort) ? sort.slice(0) : [sort];
                data.util.each(sort, function (_, prop) {
                    var asc = true, i;
                    if (prop == null) {
                        return;
                    }
                    if (!data.util.isString(prop)) {
                        if (prop.property != null) {
                            result.push(prop);
                        }
                        return;
                    }
                    if (prop[0] === "-") {
                        asc = false;
                        prop = prop.substr(1);
                    } else {
                        var match = /\s(asc|desc)\s*$/.exec(prop);
                        if (match) {
                            prop = prop.substr(0, match.index);
                            asc = !(match[1] === "desc");
                        }
                    }
                    result.push({
                        property: prop,
                        asc: asc
                    });
                });
                return result;
            }
            function compile(sort, compareTo) {
                if (typeof compareTo === "undefined") { compareTo = data.util.compare; }
                var normalized = normalize(sort);
                var result = {
                    original: sort,
                    propertyCompareTo: compareTo,
                    compare: null,
                    normalized: normalized
                };
                if (normalized != null) {
                    result.compare = function (a, b) {
                        var i = 0, cmp, descr;
                        for (i = 0; i < normalized.length; i++) {
                            descr = normalized[i];
                            cmp = compareTo(data.util.getProperty(a, descr.property), data.util.getProperty(b, descr.property));
                            if (cmp !== 0) {
                                if (!descr.asc) {
                                    cmp = -cmp;
                                }
                                return cmp;
                            }
                        }
                        return 0;
                    };
                }
                return result;
            }
            sorting.compile = compile;
        })(data.sorting || (data.sorting = {}));
        var sorting = data.sorting;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var dataViewFactories = [];
        function registerDataViewFactory(factory) {
            if (!$.isFunction(factory)) {
                data.errors.argument("factory");
            }
            dataViewFactories.push(factory);
            return {
                dispose: function () {
                    data.util.remove(dataViewFactories, factory);
                }
            };
        }
        data.registerDataViewFactory = registerDataViewFactory;
        registerDataViewFactory(function (view) {
            return isDataView(view) && view;
        });
        registerDataViewFactory(function (array) {
            return $.isArray(array) && new data.ArrayDataView(array);
        });
        function asDataView(src) {
            if (isDataView(src)) {
                return src;
            }
            var view = data.util.some(dataViewFactories, function (p) {
                return p(src);
            });
            return view || data.errors.unsupportedDataSource();
        }
        data.asDataView = asDataView;
        function isDataView(view) {
            return view && $.isFunction(view.count) && $.isFunction(view.item) && $.isFunction(view.getProperty);
        }
        data.isDataView = isDataView;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var CurrencyManager = (function () {
            function CurrencyManager(array) {
                this.array = array;
                this.currentItem = data.observableWithNewValueCheck(null);
                this.currentPosition = data.observableWithNewValueCheck(-1);
                var syncing = false;
                function synced(fn) {
                    return function () {
                        if (syncing) {
                            return;
                        }
                        syncing = true;
                        try {
                            fn.apply(this, arguments);
                        } finally {
                            syncing = false;
                        }
                    };
                }
                this.currentItem.subscribe(synced(function (value) {
                    this.currentPosition(value == null ? -1 : $.inArray(value, this.array));
                }), this);
                this.currentPosition.subscribe(synced(function (value) {
                    if (!data.util.isNumeric(value)) {
                        data.errors.argument("value");
                    }
                    if (value < -1 || value >= this.array.length) {
                        data.errors.indexOutOfBounds();
                    }
                    this.currentItem(value < 0 ? null : this.array[value]);
                }), this);
            }
            CurrencyManager.prototype.update = function () {
                var item = this.currentItem(), pos = this.currentPosition(), newIndex = $.inArray(item, this.array);
                if (newIndex < 0 && item == null && this._recentlyRemovedItem != null) {
                    newIndex = $.inArray(this._recentlyRemovedItem, this.array);
                }
                if (newIndex >= 0) {
                    if (item) {
                        this._recentlyRemovedItem = item;
                    }
                    this.currentPosition(newIndex);
                } else if (pos >= 0 && pos < this.array.length) {
                    this.currentItem(this.array[pos]);
                } else if (pos == this.array.length && this.array.length > 0) {
                    pos = this.array.length - 1;
                    this.currentPosition(pos);
                    this.currentItem(this.array[pos]);
                } else {
                    this.currentPosition(-1);
                    this.currentItem(null);
                }
            };
            CurrencyManager.prototype.updateDelayed = function () {
                data.util.executeDelayed(this.update, this);
            };
            return CurrencyManager;
        })();
        data.CurrencyManager = CurrencyManager;
    })(gcui.data || (gcui.data = {}));
    var data = gcui.data;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var Shape = (function () {
            function Shape(onChanged) {
                this.onChanged = onChanged;
                this.filter = data.observable();
                this.sort = data.observable();
                this.pageIndex = data.observable(0);
                this.pageSize = data.observable(0);
                this._skip = 0;
                this._take = -1;
                this.filter.subscribe(function (newValue) {
                    this.onFilterChanged(newValue);
                    onChanged();
                }, this);
                this.sort.subscribe(function () {
                    this._compiledSort = data.sorting.compile(this.sort());
                    onChanged();
                }, this);
                function updatePaging() {
                    if (this.pageSize() > 0 && this.pageIndex() >= 0) {
                        this._skip = this.pageSize() * this.pageIndex();
                        this._take = this.pageSize();
                    } else {
                        this._skip = 0;
                        this._take = -1;
                    }
                    onChanged(true);
                }
                this.pageIndex.subscribe(updatePaging, this);
                this.pageSize.subscribe(updatePaging, this);
            }
            Shape.prototype.onFilterChanged = function (newValue) {
                this._compiledFilter = data.filtering.compile(newValue);
            };
            Shape.prototype.setFilter = function (filter) {
                this.filter(filter);
            };
            Shape.prototype.update = function (shape) {
                if ("filter" in shape) {
                    this.setFilter(shape.filter);
                }
                if ("sort" in shape) {
                    this.sort(shape.sort);
                }
                if ("pageSize" in shape) {
                    this.pageSize(shape.pageSize);
                }
                if ("pageIndex" in shape) {
                    this.pageIndex(shape.pageIndex);
                }
            };
            Shape.prototype.apply = function (array, applyPaging) {
                if (typeof applyPaging === "undefined") { applyPaging = true; }
                var _this = this;
                var result = [];
                data.util.each(array, function (i, item) {
                    if (_this._compiledFilter && !_this._compiledFilter.func(item)) {
                        return;
                    }
                    result.push(item);
                });
                if (this._compiledSort) {
                    result.sort(this._compiledSort.compare);
                }
                var totalCount = result.length;
                if (applyPaging && this._take > 0) {
                    if (this._skip > 0) {
                        result.splice(0, Math.min(this._skip, result.length));
                    }
                    if (this._take < result.length) {
                        result.length = this._take;
                    }
                }
                return {
                    results: result,
                    totalCount: totalCount
                };
            };
            Shape.prototype.toObj = function () {
                return {
                    filter: this._compiledFilter && this._compiledFilter.normalized,
                    sort: this._compiledSort && this._compiledSort.normalized,
                    pageSize: this.pageSize(),
                    pageIndex: this.pageIndex()
                };
            };
            return Shape;
        })();
        data.Shape = Shape;
        var ArrayDataViewBase = (function () {
            function ArrayDataViewBase(shape) {
                this.isRemote = false;
                this.localPaging = true;
                this.local = [];
                this._updatingShape = false;
                this._pageCount = data.observable(1);
                this._totalItemCount = data.observable(0);
                this._isLoaded = data.observable(false);
                this._isLoading = data.observable(false);
                this._currentEditItem = data.observable();
                this._isCurrentEditItemNew = false;
                this._currentEditItemSnapshot = null;
                this.isLoading = this._isLoading.read();
                this.isLoaded = this._isLoaded.read();
                this._changed = new data.Subscribable(this);
                this.currentEditItem = this._currentEditItem.read();
                this._initCurrency();
                this._initShape(shape);
            }
            ArrayDataViewBase.prototype.dispose = function () {
            };
            ArrayDataViewBase.prototype.count = function () {
                return this.local.length;
            };
            ArrayDataViewBase.prototype.item = function (index) {
                if (index < 0 || index >= this.local.length) {
                    data.errors.indexOutOfBounds();
                }
                return this.local[index];
            };
            ArrayDataViewBase.prototype.indexOf = function (item) {
                return $.inArray(item, this.local);
            };
            ArrayDataViewBase.prototype.getSource = function () {
                return this.sourceArray;
            };
            ArrayDataViewBase.prototype.toObservableArray = function () {
                if (!this._koArray) {
                    this._koArray = ko.observableArray(this.local);
                }
                return this._koArray;
            };
            ArrayDataViewBase._getProps = function _getProps(item) {
                var cols = [];
                data.util.each(item, function (key, value) {
                    key = String(key);
                    if (key.match(/^_/)) {
                        return;
                    }
                    if ($.isFunction(value) && !value.subscribe) {
                        return;
                    }
                    cols.push({
                        name: key
                    });
                });
                return cols;
            };
            ArrayDataViewBase.prototype.getProperties = function () {
                return this.count() ? ArrayDataViewBase._getProps(this.item(0)) : [];
            };
            ArrayDataViewBase.prototype._readProperty = function (item, property) {
                return data.util.getProperty(item, property);
            };
            ArrayDataViewBase.prototype.getProperty = function (itemOrIndex, property) {
                var item = this._getItem(itemOrIndex);
                return this._readProperty(item, property);
            };
            ArrayDataViewBase.prototype._writeProperty = function (item, property, newValue) {
                data.util.setProperty(item, property, newValue);
            };
            ArrayDataViewBase.prototype.setProperty = function (itemOrIndex, property, newValue) {
                var item = this._getItem(itemOrIndex);
                if (item === this.currentEditItem() && this._currentEditItemSnapshot && !(property in this._currentEditItemSnapshot)) {
                    this._currentEditItemSnapshot[property] = this.getProperty(itemOrIndex, property);
                }
                this._writeProperty(item, property, newValue);
                return this;
            };
            ArrayDataViewBase.prototype.subscribe = function (handler, context) {
                return this._changed.subscribe(handler, context);
            };
            ArrayDataViewBase.prototype.trigger = function () {
                this._currencyManager.update();
                this._changed.trigger(this.local);
                if (this._koArray) {
                    this._koArray.notifySubscribers(this.local);
                }
            };
            ArrayDataViewBase.prototype.canFilter = function () {
                return true;
            };
            ArrayDataViewBase.prototype.canSort = function () {
                return true;
            };
            ArrayDataViewBase.prototype._updateShape = function (shape) {
                this._updatingShape = true;
                try {
                    this._shape.update(shape);
                } finally {
                    this._updatingShape = false;
                }
            };
            ArrayDataViewBase.prototype.prevPage = function () {
                if (this.pageIndex() < 1) {
                    return false;
                }
                this.pageIndex(this.pageIndex() - 1);
                return true;
            };
            ArrayDataViewBase.prototype.nextPage = function () {
                if (this.pageCount() > 0 && this.pageIndex() + 1 >= this.pageCount()) {
                    return false;
                }
                this.pageIndex(this.pageIndex() + 1);
                return true;
            };
            ArrayDataViewBase.prototype._createShape = function (onChanged) {
                return new Shape(onChanged);
            };
            ArrayDataViewBase.prototype._initShape = function (shape) {
                var _this = this;
                var onChanged = function (onlyPaging) {
                    if (typeof onlyPaging === "undefined") { onlyPaging = false; }
                    if (!_this._updatingShape) {
                        _this.refresh(null, onlyPaging && _this.localPaging);
                    }
                };
                this._shape = this._createShape(onChanged);
                if (shape) {
                    this._updateShape(shape);
                }
                this.filter = this._shape.filter;
                this.sort = this._shape.sort;
                this.pageIndex = this._shape.pageIndex;
                this.pageSize = this._shape.pageSize;
                this.pageCount = this._pageCount.read();
                this.totalItemCount = this._totalItemCount.read();
            };
            ArrayDataViewBase.prototype._localRefresh = function (doPaging) {
                if (typeof doPaging === "undefined") { doPaging = this.localPaging; }
                var _this = this;
                var result = this._shape.apply(this.sourceArray, doPaging);
                this.local.length = 0;
                $.each(result.results, function (_, x) {
                    return _this.local.push(x);
                });
                if (doPaging) {
                    this._totalItemCount(result.totalCount);
                }
                this._pageCount(data.util.pageCount(this.totalItemCount(), this.pageSize()));
                this.trigger();
                return $.Deferred().resolve().promise();
            };
            ArrayDataViewBase.prototype._remoteRefresh = function () {
                return this._localRefresh();
            };
            ArrayDataViewBase.prototype.refresh = function (shape, local) {
                if (typeof local === "undefined") { local = false; }
                var _this = this;
                this.cancelRefresh();
                if (shape) {
                    this._updateShape(shape);
                }
                this._isLoading(true);
                var promise = local ? this._localRefresh() : this._remoteRefresh();
                return promise.then(function () {
                    _this._isLoaded(true);
                    _this._isLoading(false);
                });
            };
            ArrayDataViewBase.prototype.cancelRefresh = function () {
            };
            ArrayDataViewBase.prototype._initCurrency = function () {
                this._currencyManager = new data.CurrencyManager(this.local);
                this.currentItem = this._currencyManager.currentItem;
                this.currentPosition = this._currencyManager.currentPosition;
            };
            ArrayDataViewBase.prototype.isCurrentEditItemNew = function () {
                return this._isCurrentEditItemNew;
            };
            ArrayDataViewBase.prototype._beginEdit = function (item, isNew) {
                this.commitEdit();
                this._currentEditItemSnapshot = {
                };
                this._isCurrentEditItemNew = isNew;
                this._currentEditItem(item);
            };
            ArrayDataViewBase.prototype.canAdd = function () {
                return true;
            };
            ArrayDataViewBase.prototype.add = function (item) {
                if (!item) {
                    data.errors.argument("item");
                }
                this.commitEdit();
                this.local.push(item);
                this._beginEdit(item, true);
                this.trigger();
            };
            ArrayDataViewBase.prototype.canAddNew = function () {
                return false;
            };
            ArrayDataViewBase.prototype.addNew = function () {
                return data.errors.unsupportedOperation();
            };
            ArrayDataViewBase.prototype.editItem = function (item) {
                if (typeof item === "undefined") { item = this.currentItem(); }
                this.commitEdit();
                item = this._getItem(item);
                if (item) {
                    this._beginEdit(item, false);
                }
            };
            ArrayDataViewBase.prototype.canRemove = function () {
                return true;
            };
            ArrayDataViewBase.prototype._remove = function (entry) {
                this.local.splice(entry.index, 1);
                data.util.remove(this.sourceArray, entry.item);
                this.trigger();
            };
            ArrayDataViewBase.prototype.remove = function (item) {
                if (typeof item === "undefined") { item = this.currentItem(); }
                this.commitEdit();
                var entry = this._resolve(item);
                if (!entry) {
                    return false;
                }
                this._remove(entry);
                return true;
            };
            ArrayDataViewBase.prototype.canCancelEdit = function () {
                return true;
            };
            ArrayDataViewBase.prototype.cancelEdit = function () {
                var _this = this;
                var key;
                if (!this.currentEditItem()) {
                    return;
                }
                var item = this.currentEditItem();
                this._currentEditItem(null);
                if (this._isCurrentEditItemNew) {
                    data.util.remove(this.local, this.item);
                } else if (this._currentEditItemSnapshot) {
                    $.each(this._currentEditItemSnapshot, function (k, v) {
                        return _this._writeProperty(item, k, v);
                    });
                }
                this.trigger();
            };
            ArrayDataViewBase.prototype.canCommitEdit = function () {
                return true;
            };
            ArrayDataViewBase.prototype.commitEdit = function () {
                if (!this.currentEditItem()) {
                    return;
                }
                var item = this.currentEditItem;
                this._currentEditItem(null);
                if (this._isCurrentEditItemNew) {
                    this.sourceArray.push(item);
                }
                var filter = this._shape._compiledFilter && this._shape._compiledFilter.func;
                if (filter && !filter(item)) {
                    data.util.remove(this.local, item);
                }
                this.trigger();
            };
            ArrayDataViewBase.prototype._getItem = function (itemOrIndex) {
                var index;
                if (data.util.isNumeric(itemOrIndex)) {
                    return this.item(itemOrIndex);
                } else {
                    return itemOrIndex;
                }
            };
            ArrayDataViewBase.prototype._resolve = function (itemOrIndex, raiseIfNotContained) {
                if (typeof raiseIfNotContained === "undefined") { raiseIfNotContained = false; }
                var index;
                if (data.util.isNumeric(itemOrIndex)) {
                    return {
                        index: itemOrIndex,
                        item: this.item(itemOrIndex)
                    };
                } else {
                    index = this.indexOf(itemOrIndex);
                    if (index < 0) {
                        if (raiseIfNotContained) {
                            data.errors.itemNotInView(itemOrIndex);
                        }
                        return null;
                    }
                    return {
                        index: index,
                        item: itemOrIndex
                    };
                }
            };
            return ArrayDataViewBase;
        })();
        data.ArrayDataViewBase = ArrayDataViewBase;
        var ArrayDataView = (function (_super) {
            __extends(ArrayDataView, _super);
            function ArrayDataView(source, shape) {
                _super.call(this, shape);
                this.sourceArray = source;
                this.refresh();
            }
            return ArrayDataView;
        })(ArrayDataViewBase);
        data.ArrayDataView = ArrayDataView;
    })(gcui.data || (gcui.data = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var RemoteDataView = (function (_super) {
            __extends(RemoteDataView, _super);
            function RemoteDataView(options) {
                _super.call(this);
                this.isRemote = true;
                this.sourceArray = [];
                if (options) {
                    this._construct(options);
                }
            }
            RemoteDataView.prototype._construct = function (options) {
                this.options = options = $.extend({
                    localPaging: false
                }, options);
                this.localPaging = options.localPaging;
                this._updateShape(options);
            };
            return RemoteDataView;
        })(data.ArrayDataViewBase);
        data.RemoteDataView = RemoteDataView;
    })(gcui.data || (gcui.data = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery;
        var datasourceReader = (function () {
            function datasourceReader(originalReader) {
                this._originalReader = originalReader;
            }
            datasourceReader.prototype.read = function (dataSource) {
                dataSource.items = null;
                if (this._originalReader && $.isFunction(this._originalReader.read)) {
                    this._originalReader.read(dataSource);
                }
                if (!$.isArray(dataSource.items)) {
                    if ($.isArray(dataSource.data)) {
                        dataSource.items = dataSource.data;
                    } else if (dataSource.data && $.isArray(dataSource.data.rows)) {
                        dataSource.items = dataSource.data.rows;
                    } else {
                        dataSource.items = [];
                    }
                }
                if (dataSource.dynamic) {
                    if (!dataSource.data || isNaN(dataSource.data.totalRows)) {
                        throw "totalRows value is missing";
                    }
                }
            };
            return datasourceReader;
        })();
        var datasourceView = (function (_super) {
            __extends(datasourceView, _super);
            function datasourceView(dataSource) {
                var _this = this;
                _super.call(this);
                this.dataSource = dataSource;
                this.isRemote = true;
                this.localPaging = true;
                this._origLoaded = dataSource.loaded;
                this._origReader = dataSource.reader;
                dataSource.loaded = function (e, data) {
                    _this._loaded();
                    if ($.isFunction(_this._origLoaded)) {
                        _this._origLoaded.apply(_this, arguments);
                    }
                };
                dataSource.reader = new datasourceReader(dataSource.reader);
                var hasItems = dataSource.items && dataSource.items.length > 0;
                if ($.isArray(dataSource.data) && !hasItems) {
                    dataSource.read();
                }
                if (dataSource.items) {
                    this._loaded();
                }
            }
            datasourceView.prototype.dispose = function () {
                this.dataSource.loaded = this._origLoaded;
                this.dataSource.reader = this._origReader;
                _super.prototype.dispose.call(this);
            };
            datasourceView.prototype.getProperties = function () {
                return this.sourceArray && this.sourceArray.length ? data.ArrayDataViewBase._getProps(this.sourceArray[0]) : [];
            };
            datasourceView.prototype._loaded = function () {
                this.sourceArray = this.dataSource.items;
                if (this.dataSource.data && data.util.isNumeric(this.dataSource.data.totalRows)) {
                    this._totalItemCount(this.dataSource.data.totalRows);
                }
                var def = _super.prototype._localRefresh.call(this, !this.dataSource.dynamic);
                if (this._currentDeferred) {
                    def.then(this._currentDeferred.resolve);
                }
            };
            datasourceView.prototype._remoteRefresh = function () {
                if (this._currentDeferred && this._currentDeferred.state() === "pending") {
                    this._currentDeferred.fail();
                }
                this._currentDeferred = $.Deferred();
                var userData = {
                }, forceReload = false;
                if (this.dataSource.dynamic) {
                    forceReload = true;
                    userData.data = this._prepareRequest();
                    if (this.dataSource.proxy) {
                        if (!this._origDataOption) {
                            this._origDataOption = $.extend({
                            }, this.dataSource.proxy.options.data);
                        }
                        this.dataSource.proxy.options.data = $.extend({
                        }, this._origDataOption, userData.data);
                    }
                }
                this.dataSource.load(userData, forceReload);
                return this._currentDeferred;
            };
            datasourceView.prototype._prepareRequest = function () {
                return {
                    filtering: this._prepareFilterRequest(),
                    paging: this._preparePageRequest(),
                    sorting: this._prepareSortRequest()
                };
            };
            datasourceView.prototype._prepareFilterRequest = function () {
                var result = [];
                if (this._shape._compiledFilter && this._shape._compiledFilter.normalized) {
                    $.each(this._shape._compiledFilter.normalized, function (prop, cond) {
                        result.push({
                            dataKey: prop,
                            filterOperator: cond.op.Name,
                            filterValue: cond.value
                        });
                    });
                }
                return result;
            };
            datasourceView.prototype._preparePageRequest = function () {
                return {
                    pageIndex: this._shape.pageIndex(),
                    pageSize: this._shape.pageSize()
                };
            };
            datasourceView.prototype._prepareSortRequest = function () {
                if (!this._shape._compiledSort || !this._shape._compiledSort.normalized || this._shape._compiledSort.normalized.length == 0) {
                    return [];
                }
                return $.map(this._shape._compiledSort.normalized, function (sd) {
                    return {
                        dataKey: sd.property,
                        sortDirection: sd.asc ? "ascending" : "descending"
                    };
                });
            };
            return datasourceView;
        })(data.ArrayDataViewBase);
        data.registerDataViewFactory(function (dataSource) {
            if (typeof gcuidatasource !== "function" || !(dataSource instanceof gcuidatasource)) {
                return;
            }
            return new datasourceView(dataSource);
        });
    })(gcui.data || (gcui.data = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (pager) {
        "use strict";
        var $ = jQuery;
        var gcuipager = (function (_super) {
            __extends(gcuipager, _super);
            function gcuipager() {
                _super.apply(this, arguments);

            }
            gcuipager.prototype._create = function () {
                if (window.applyTouchUtilEvents) {
                    $ = window.applyTouchUtilEvents($);
                }
                this.element.addClass(this.options.uiCSS.widget + " gcui-pager " + this.options.uiCSS.helperClearFix);
                if (this.options.disabled) {
                    this.disable();
                }
                this._refresh();
            };
            gcuipager.prototype._destroy = function () {
                this.element.removeClass(this.options.uiCSS.widget + " gcui-pager " + this.options.uiCSS.helperClearFix);
                this.$ul.remove();
            };
            gcuipager.prototype._setOption = function (key, value) {
                this._super(key, value);
                this._refresh();
            };
            gcuipager.prototype._refresh = function () {
                this._validate();
                if (this.$ul) {
                    this.$ul.remove();
                }
                this.element.append(this.$ul = $("<ul class=\"ui-list " + this.options.uiCSS.cornerAll + " " + this.options.uiCSS.content + " " + this.options.uiCSS.helperClearFix + "\" role=\"tablist\"></ul>"));
                switch ((this.options.mode || "").toLowerCase()) {
                    case "nextprevious":
                        this._createNextPrev(false);
                        break;
                    case "nextpreviousfirstlast":
                        this._createNextPrev(true);
                        break;
                    case "numeric":
                        this._createNumeric(false);
                        break;
                    case "numericfirstlast":
                        this._createNumeric(true);
                        break;
                }
            };
            gcuipager.prototype._validate = function () {
                var o = this.options;
                if (isNaN(o.pageCount) || o.pageCount < 1) {
                    o.pageCount = 1;
                }
                if (isNaN(o.pageIndex) || o.pageIndex < 0) {
                    o.pageIndex = 0;
                } else {
                    if (o.pageIndex >= o.pageCount) {
                        o.pageIndex = o.pageCount - 1;
                    }
                }
                if (isNaN(o.pageButtonCount) || o.pageButtonCount < 1) {
                    o.pageButtonCount = 1;
                }
            };
            gcuipager.prototype._createNextPrev = function (addFirstLast) {
                var o = this.options;
                if (addFirstLast && o.pageIndex) {
                    this.$ul.append(this._createPagerItem(false, o.firstPageText, 1, o.firstPageClass));
                }
                if (o.pageIndex) {
                    this.$ul.append(this._createPagerItem(false, o.previousPageText, o.pageIndex, o.previousPageClass));
                }
                if (o.pageIndex + 1 < o.pageCount) {
                    this.$ul.append(this._createPagerItem(false, o.nextPageText, o.pageIndex + 2, o.nextPageClass));
                }
                if (addFirstLast && (o.pageIndex + 1 < o.pageCount)) {
                    this.$ul.append(this._createPagerItem(false, o.lastPageText, o.pageCount, o.lastPageClass));
                }
            };
            gcuipager.prototype._createNumeric = function (addFirstLast) {
                var o = this.options, currentPage = o.pageIndex + 1, startPageNumber = 1, endPageNumber = Math.min(o.pageCount, o.pageButtonCount), i;
                if (currentPage > endPageNumber) {
                    startPageNumber = (Math.floor(o.pageIndex / o.pageButtonCount)) * o.pageButtonCount + 1;
                    endPageNumber = startPageNumber + o.pageButtonCount - 1;
                    endPageNumber = Math.min(endPageNumber, o.pageCount);
                    if (endPageNumber - startPageNumber + 1 < o.pageButtonCount) {
                        startPageNumber = Math.max(1, endPageNumber - o.pageButtonCount + 1);
                    }
                }
                if (startPageNumber !== 1) {
                    if (addFirstLast) {
                        this.$ul.append(this._createPagerItem(false, o.firstPageText, 1, o.firstPageClass));
                    }
                    this.$ul.append(this._createPagerItem(false, "...", startPageNumber - 1, ""));
                }
                for (i = startPageNumber; i <= endPageNumber; i++) {
                    this.$ul.append(this._createPagerItem(i === currentPage, i.toString(), i, ""));
                }
                if (o.pageCount > endPageNumber) {
                    this.$ul.append(this._createPagerItem(false, "...", endPageNumber + 1, ""));
                    if (addFirstLast) {
                        this.$ul.append(this._createPagerItem(false, o.lastPageText, o.pageCount, o.lastPageClass));
                    }
                }
            };
            gcuipager.prototype._createPagerItem = function (active, title, pageIndex, btnClass) {
                var btnContent, css = this.options.uiCSS, self = this, $li = $("<li />").addClass("gcui-pager-button " + css.cornerAll).attr({
                    "role": "tab",
                    "aria-label": title,
                    "title": title
                });
                if (active) {
                    $li.addClass(css.stateActive).attr("aria-selected", "true");
                } else {
                    $li.addClass(css.stateDefault).hover(function () {
                        if (!self.options.disabled) {
                            $(this).addClass(css.stateHover);
                        }
                    }, function () {
                        if (!self.options.disabled) {
                            $(this).removeClass(css.stateHover);
                        }
                    }).bind("click." + this.widgetName, {
                        newPageIndex: pageIndex - 1
                    }, $.proxy(this._onClick, this));
                }
                if (active) {
                    btnContent = $("<span />");
                } else {
                    btnContent = btnClass ? $("<span />").addClass(css.icon + " " + btnClass) : $("<a/>").attr("href", "#");
                }
                btnContent.text(title);
                $li.append(btnContent);
                return $li;
            };
            gcuipager.prototype._onClick = function (arg) {
                if (this.options.disabled) {
                    return false;
                }
                var pagingArgs = {
                    newPageIndex: arg.data.newPageIndex,
                    handled: false
                };
                if (this._trigger("pageIndexChanging", null, pagingArgs) !== false) {
                    if (this.options.pageIndex !== pagingArgs.newPageIndex) {
                        this.options.pageIndex = pagingArgs.newPageIndex;
                        if (!pagingArgs.handled) {
                            this._refresh();
                        }
                        var pagedArgs = {
                            newPageIndex: pagingArgs.newPageIndex
                        };
                        this._trigger("pageIndexChanged", null, pagedArgs);
                    }
                }
                return false;
            };
            return gcuipager;
        })(gcui.gcuiWidget);
        pager.gcuipager = gcuipager;
        gcuipager.prototype.widgetEventPrefix = "gcuipager";
        var pager_options = (function () {
            function pager_options() {
                this.mobileCSS = {
                    header: "ui-header ui-bar-a",
                    content: "ui-body-c",
                    stateDefault: "ui-btn-up-c",
                    stateHover: "ui-btn-down-b",
                    stateActive: "ui-btn-down-c"
                };
                this.firstPageClass = $.gcui.widget.prototype.options.uiCSS.iconSeekFirst;
                this.firstPageText = "First";
                this.lastPageClass = $.gcui.widget.prototype.options.uiCSS.iconSeekEnd;
                this.lastPageText = "Last";
                this.mode = "numeric";
                this.nextPageClass = $.gcui.widget.prototype.options.uiCSS.iconSeekNext;
                this.nextPageText = "Next";
                this.pageButtonCount = 10;
                this.previousPageClass = $.gcui.widget.prototype.options.uiCSS.iconSeekPrev;
                this.previousPageText = "Previous";
                this.pageCount = 1;
                this.pageIndex = 0;
                this.pageIndexChanging = null;
                this.pageIndexChanged = null;
            }
            return pager_options;
        })();
        gcuipager.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, new pager_options());
        $.gcui.registerWidget("gcuipager", gcuipager.prototype);
    })(gcui.pager || (gcui.pager = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (tabs) {
        "use strict";
        var $ = jQuery;
        var tabId = 0, listId = 0, effects = $.effects ? $.effects : $, effectsSave = effects.save, effectsRestore = effects.restore, effectsCreateWrapper = effects.createWrapper, effectsRemoveWrapper = effects.removeWrapper, getNextTabId = function () {
            return ++tabId;
        }, getNextListId = function () {
            return ++listId;
        };
        var gcuitabs = (function (_super) {
            __extends(gcuitabs, _super);
            function gcuitabs() {
                _super.apply(this, arguments);
            }
            gcuitabs.prototype._setOption = function (key, value) {
                _super.prototype._setOption.call(this, key, value);
                switch(key) {
                    case 'selected':
                        if(this.options.collapsible && value === this.options.selected) {
                            return;
                        }
                        this.select(value);
                        break;
                    case 'alignment':
                        this._innerDestroy(true);
                        this._tabify(true);
                        break;
                    default:
                        this._tabify(false);
                        break;
                }
            };
            gcuitabs.prototype._create = function () {
                var self = this;
                var o = this.options;
                if(this.element.is(":hidden") && this.element.addVisibilityObserver) {
                    this.element.addVisibilityObserver(function () {
                        if(self.element.removeVisibilityObserver) {
                            self.element.removeVisibilityObserver();
                        }
                        var dataObj = self.element.data("gcuitabs"),
                            uiDataObj = self.element.data("gcuiUITabs");
                        self._innerDestroy(true);
                        self.element.data("gcuitabs", dataObj);
                        self.element.data("gcuiUITabs", uiDataObj);
                        self._tabify(true);
                    }, "gcuitabs");
                }
                this._tabify(true);
                if(o.disabledstate || o.disabled) {
                    this.disable();
                }
                _super.prototype._create.call(this);
            };
            gcuitabs.prototype.destroy = function () {
                this._innerDestroy();
                _super.prototype.destroy.call(this);
            };
            gcuitabs.prototype._tabify = function (init) {
                this.list = this.element.children('ol,ul').eq(0);
                this.lis = $('li:has(a)', this.list);
                this.anchors = this.lis.map(function () {
                    return $('a', this)[0];
                });
                this.panels = $([]);
                var self = this, o = self.options, fragmentId = /^#.+/, tabsAlign, panelCorner, content, i, li, addState, removeState, showTab, hideTab;
                $.each(self.anchors, function (i, a) {
                    var href = $(a).attr('href') || "", hrefBase = href.split('#')[0], baseEl, id, $panel;
                    if(hrefBase && (hrefBase === location.toString().split('#')[0] || (baseEl = $('base')[0]) && hrefBase === baseEl.href)) {
                        href = a.hash;
                        a.href = href;
                    }
                    if(fragmentId.test(href)) {
                        self.panels = self.panels.add(self._sanitizeSelector(href), self.element);
                    } else if(href !== '#') {
                        $.data(a, 'href.tabs', href);
                        $.data(a, 'load.tabs', href.replace(/#.*$/, ''));
                        id = self._tabId(a);
                        a.href = '#' + id;
                        $panel = $('#' + id);
                        if(!$panel.length) {
                            $panel = $(o.panelTemplate || self._defaults.panelTemplate).attr('id', id).addClass(o.uiCSS.tabsPanel).addClass(o.uiCSS.content).addClass(o.uiCSS.cornerBottom).insertAfter(self.panels[i - 1] || self.list);
                            $panel.data('destroy.tabs', true);
                        }
                        self.panels = self.panels.add($panel);
                    } else {
                        o.disabledIndexes.push(i);
                    }
                });
                tabsAlign = this._getAlignment(true);
                panelCorner = this._getAlignment(false);
                if(init) {
                    this.list.attr("role", "tablist");
                    this.lis.attr("role", "tab");
                    this.panels.attr("role", "tabpanel");
                    this.element.addClass(o.uiCSS.tabs).addClass(o.uiCSS.gcuitabs).addClass(o.uiCSS.widget).addClass(o.uiCSS.content).addClass(o.uiCSS.cornerAll).addClass(o.uiCSS.helperClearFix).addClass(o.uiCSS.tabsPanel).addClass(o.uiCSS.tabsPanel).addClass(o.uiCSS["tabs" + tabsAlign]);
                    this.list.addClass(o.uiCSS.tabsNav).addClass(o.uiCSS.helperReset).addClass(o.uiCSS.helperClearFix).addClass(o.uiCSS.header).addClass(o.uiCSS.cornerAll);
                    this.lis.addClass(o.uiCSS.stateDefault).addClass(o.uiCSS["corner" + tabsAlign]);
                    this.panels.addClass(o.uiCSS.tabsPanel).addClass(o.uiCSS.content).addClass(o.uiCSS["corner" + panelCorner]);
                    switch(tabsAlign) {
                        case 'Bottom':
                            this.list.appendTo(this.element);
                            break;
                        case 'Left':
                            content = $('<div/>').addClass(o.uiCSS.tabsContent).appendTo(this.element);
                            this.panels.appendTo(content);
                            break;
                        case 'Right':
                            content = $('<div/>').addClass(o.uiCSS.tabsContent).insertBefore(this.list);
                            this.panels.appendTo(content);
                            break;
                        case 'Top':
                            this.list.prependTo(this.element);
                            break;
                    }
                    if(o.sortable && this.list.sortable) {
                        this.list.sortable({
                            axis: (tabsAlign === 'Top' || tabsAlign === 'Bottom') ? "x" : "y"
                        });
                    }
                    if(o.selected === undefined) {
                        if(location.hash) {
                            $.each(this.anchors, function (i, a) {
                                if(a.hash === location.hash) {
                                    o.selected = i;
                                    return false;
                                }
                            });
                        }
                        if(typeof o.selected !== 'number' && o.cookie) {
                            o.selected = parseInt(self._cookie(undefined, undefined), 10);
                        }
                        if(typeof o.selected !== 'number' && this.lis.filter('.' + o.uiCSS.tabsActive).length) {
                            o.selected = this.lis.index(this.lis.filter('.' + o.uiCSS.tabsActive));
                        }
                        o.selected = o.selected || (this.lis.length ? 0 : -1);
                    } else if(o.selected === null) {
                        o.selected = -1;
                    }
                    o.selected = ((o.selected >= 0 && this.anchors[o.selected]) || o.selected < 0) ? o.selected : 0;
                    o.disabledIndexes = $.unique(o.disabledIndexes.concat($.map(this.lis.filter('.' + o.uiCSS.stateDisabled), function (n, i) {
                        return self.lis.index(n);
                    }))).sort();
                    if($.inArray(o.selected, o.disabledIndexes) !== -1) {
                        o.disabledIndexes.splice($.inArray(o.selected, o.disabledIndexes), 1);
                    }
                    this.panels.addClass(o.uiCSS.tabsHide).attr('aria-hidden', true);
                    this.lis.removeClass(o.uiCSS.tabsActive).removeClass(o.uiCSS.stateActive).attr('aria-selected', false);
                    if(o.selected >= 0 && this.anchors.length) {
                        this.panels.eq(o.selected).removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false);
                        this.lis.eq(o.selected).addClass(o.uiCSS.tabsActive).addClass(o.uiCSS.stateActive).attr('aria-selected', true);
                        self.element.queue("tabs", function () {
                            if(self.element.triggerVisibility) {
                                $(self.panels[o.selected]).triggerVisibility();
                            }
                            self._trigger('show', null, self._ui(self.anchors[o.selected], self.panels[o.selected]));
                        });
                        this.load(o.selected);
                    }
                    $(window).bind('unload', function () {
                        if(self.lis) {
                            self.lis.add(self.anchors).unbind('.tabs');
                        }
                        self.lis = self.anchors = self.panels = null;
                    });
                } else {
                    o.selected = this.lis.index(this.lis.filter('.' + o.uiCSS.tabsActive));
                }
                this.element[o.collapsible ? 'addClass' : 'removeClass'](o.uiCSS.tabsCollapsible);
                if(o.cookie) {
                    this._cookie(o.selected, o.cookie);
                }
                for(i = 0; i < this.lis.length; i++) {
                    li = this.lis[i];
                    $(li)[$.inArray(i, o.disabledIndexes) !== -1 && !$(li).hasClass(o.uiCSS.tabsActive) ? 'addClass' : 'removeClass'](o.uiCSS.stateDisabled);
                    if($(li).hasClass(o.uiCSS.stateDisabled)) {
                        $(li).attr('aria-disabled', true);
                    }
                }
                if(o.cache === false) {
                    this.anchors.removeData('cache.tabs');
                }
                this.lis.add(this.anchors).unbind('.tabs');
                if(!o.disabledState && !o.disabled && o.event !== 'mouseover') {
                    addState = function (state, el) {
                        if(el.is(':not(.' + o.uiCSS.stateDisabled + ')')) {
                            el.addClass(state);
                        }
                    };
                    removeState = function (state, el) {
                        el.removeClass(state);
                    };
                    this.lis.bind('mouseover.tabs', function () {
                        addState(o.uiCSS.stateHover, $(this));
                    });
                    this.lis.bind('mouseout.tabs', function () {
                        removeState(o.uiCSS.stateHover, $(this));
                    });
                    this.anchors.bind('focus.tabs', function () {
                        self.lis.filter("." + o.uiCSS.stateFocus).removeClass(o.uiCSS.stateFocus);
                        addState(o.uiCSS.stateFocus, $(this).closest('li'));
                    });
                    this.anchors.bind('blur.tabs', function () {
                        removeState(o.uiCSS.stateFocus, $(this).closest('li'));
                    });
                }
                if(o.showOption === undefined || o.showOption === null) {
                    o.showOption = {};
                }
                this._normalizeBlindOption(o.showOption);
                if(o.hideOption === undefined || o.hideOption === null) {
                    o.hideOption = {};
                }
                this._normalizeBlindOption(o.hideOption);
                showTab = ((o.showOption.blind || o.showOption.fade) && o.showOption.duration > 0) ? function (clicked, $show) {
                    $(clicked).closest('li').addClass(o.uiCSS.tabsActive).addClass(o.uiCSS.stateActive).attr('aria-selected', true);
                    self._showContent();
                    $show.removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false);
                    if(tabsAlign === 'Top' || tabsAlign === 'Bottom') {
                        var props = {
                            duration: o.showOption.duration,
                            height: 'toggle',
                            opacity: 'toggle'
                        };
                        if(!o.showOption.blind) {
                            delete props.height;
                        }
                        if(!o.showOption.fade) {
                            delete props.opacity;
                        }
                        $show.hide().removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false).animate(props, o.showOption.duration || 'normal', function () {
                            self._resetStyle($show);
                            if($show.triggerVisibility) {
                                $show.triggerVisibility();
                            }
                            self._trigger('show', null, self._ui(clicked, $show[0]));
                        });
                    } else {
                        self._showContent();
                        self._blindPanel($show, 'show');
                    }
                } : function (clicked, $show) {
                    $(clicked).closest('li').addClass(o.uiCSS.tabsActive).addClass(o.uiCSS.stateActive).attr('aria-selected', true);
                    self._showContent();
                    $show.removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false);
                    if($show.triggerVisibility) {
                        $show.triggerVisibility();
                    }
                    self._trigger('show', null, self._ui(clicked, $show[0]));
                };
                hideTab = ((o.hideOption.blind || o.hideOption.fade) && o.hideOption.duration > 0) ? function (clicked, $hide) {
                    if(tabsAlign === 'Top' || tabsAlign === 'Bottom') {
                        var props = {
                            duration: o.hideOption.duration,
                            height: 'toggle',
                            opacity: 'toggle'
                        };
                        if(!o.hideOption.blind) {
                            delete props.height;
                        }
                        if(!o.hideOption.fade) {
                            delete props.opacity;
                        }
                        $hide.animate(props, o.hideOption.duration || 'normal', function () {
                            self.lis.removeClass(o.uiCSS.tabsActive).removeClass(o.uiCSS.stateActive).attr('aria-selected', false);
                            $hide.addClass(o.uiCSS.tabsHide).attr('aria-hidden', true);
                            self._resetStyle($hide);
                            self.element.dequeue("tabs");
                        });
                    } else {
                        self._saveLayout();
                        self._blindPanel($hide, 'hide');
                    }
                } : function (clicked, $hide, $show) {
                    self.lis.removeClass(o.uiCSS.tabsActive).removeClass(o.uiCSS.stateActive).attr('aria-selected', false);
                    self._hideContent();
                    $hide.addClass(o.uiCSS.tabsHide).attr('aria-hidden', true);
                    self.element.dequeue("tabs");
                };
                if(!o.disabledState && !o.disabled) {
                    this.anchors.bind(o.event + '.tabs', function () {
                        var el = this, $li = $(this).closest('li'), $hide = self.panels.filter(':not(.' + o.uiCSS.tabsHide + ')'), $show = $(self._sanitizeSelector(this.hash), self.element);
                        if(($li.hasClass(o.uiCSS.tabsActive) && !o.collapsible) || $li.hasClass(o.uiCSS.stateDisabled) || $li.hasClass(o.uiCSS.tabsLoading) || self._trigger('select', null, self._ui(this, $show[0])) === false) {
                            this.blur();
                            return false;
                        }
                        o.selected = self.anchors.index(this);
                        self.abort();
                        if(o.collapsible) {
                            if($li.hasClass(o.uiCSS.tabsActive)) {
                                o.selected = -1;
                                if(o.cookie) {
                                    self._cookie(o.selected, o.cookie);
                                }
                                self.element.queue("tabs", function () {
                                    hideTab(el, $hide);
                                }).dequeue("tabs");
                                this.blur();
                                return false;
                            } else if(!$hide.length) {
                                if(o.cookie) {
                                    self._cookie(o.selected, o.cookie);
                                }
                                self.element.queue("tabs", function () {
                                    showTab(el, $show);
                                });
                                self.load(self.anchors.index(this));
                                this.blur();
                                return false;
                            }
                        }
                        if(o.cookie) {
                            self._cookie(o.selected, o.cookie);
                        }
                        if($show.length) {
                            if($hide.length) {
                                self.element.queue("tabs", function () {
                                    hideTab(el, $hide);
                                });
                            }
                            self.element.queue("tabs", function () {
                                showTab(el, $show);
                            });
                            self.load(self.anchors.index(this));
                        } else {
                            throw 'jQuery UI Tabs: Mismatching fragment identifier.';
                        }
                        if (GC.Spread.Sheets.util.browser.msie) {
                            this.blur();
                        }
                    });
                }
                this._initScroller();
                this.anchors.bind('click.tabs', function () {
                    return false;
                });
            };
            gcuitabs.prototype._blindPanel = function (panel, mode) {
                var self = this, o = self.options, content = panel.parent('.' + o.uiCSS.tabsContent),
                    props = ['position', 'top', 'left', 'width'],
                    blindOption = mode === 'show' ? o.showOption : o.hideOption, wrapper, a, listWidth;
                if(!content.length) {
                    return;
                }
                self.list.width(self.list.width());
                if(effectsSave) {
                    effectsSave(panel, props);
                }
                panel.show();
                if(mode === 'show') {
                    panel.removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false);
                    panel.width(self.element.data('panel.width'));
                } else {
                    panel.width(panel.width());
                }
                if(effectsCreateWrapper) {
                    wrapper = effectsCreateWrapper(panel).css({
                        overflow: 'hidden'
                    });
                } else {
                    wrapper = $('<div></div>');
                }
                if(mode === 'show') {
                    wrapper.css($.extend({ width: 0 }, blindOption.fade ? { opacity: 0 } : {}));
                }
                a = $.extend({ width: mode === 'show' ? self.element.data('panel.outerWidth') : 0 },
                            blindOption.fade ? { opacity: mode === 'show' ? 1 : 0 } : {});
                listWidth = self.list.outerWidth(true);
                wrapper.animate(a, {
                    duration: blindOption.duration,
                    step: function () {
                        var ww = wrapper.outerWidth(true);
                        self.element.width(listWidth + ww);
                        content.width(Math.max(0, self.element.innerWidth() - listWidth - 6));
                    },
                    complete: function () {
                        if(mode === 'hide') {
                            self.lis.removeClass(o.uiCSS.tabsActive).removeClass(o.uiCSS.stateActive).attr('aria-selected', false);
                            panel.addClass(o.uiCSS.tabsHide).attr('aria-hidden', true);
                        } else {
                            panel.css('width', '');
                        }
                        if(effectsRemoveWrapper) {
                            effectsRemoveWrapper(panel);
                        }
                        if(mode === 'show') {
                            self._restoreLayout();
                        }
                        self._resetStyle(panel);
                        panel.dequeue();
                        self.element.dequeue("tabs");
                    }
                });
            };
            gcuitabs.prototype._hideContent = function () {
                var css = this.options.uiCSS, content = this.element.find('.' + css.tabsContent);
                if(content.length) {
                    this._saveLayout();
                    content.addClass(css.tabsHide).attr('aria-hidden', true);
                    this.element.width(this.list.outerWidth(true));
                }
            };
            gcuitabs.prototype._showContent = function () {
                var css = this.options.uiCSS, content = this.element.find('.' + css.tabsContent);
                if(content.length) {
                    this._restoreLayout();
                    content.removeClass(css.tabsHide).attr('aria-hidden', false);
                }
            };
            gcuitabs.prototype._saveLayout = function () {
                var css = this.options.uiCSS,
                    props = ['width', 'height', 'overflow'],
                    $hide = this.panels.filter(':not(.' + css.tabsHide + ')');
                if(effectsSave) {
                    effectsSave(this.element, props);
                    effectsSave(this.list, props);
                    effectsSave(this.element.find('.' + css.tabsContent), props);
                }
                this.list.width(this.list.width());
                this.element.data('panel.width', $hide.width());
                this.element.data('panel.outerWidth', $hide.outerWidth(true));
            };
            gcuitabs.prototype._restoreLayout = function () {
                var css = this.options.uiCSS,
                    props = ['width', 'height', 'overflow'];
                if(effectsRestore) {
                    effectsRestore(this.element, props);
                    effectsRestore(this.list, props);
                    effectsRestore(this.element.find('.' + css.tabsContent), props);
                }
            };
            gcuitabs.prototype._resetStyle = function ($el) {
                $el.css({ display: '' });
                if(!$.support.opacity) {
                    $el[0].style.removeAttribute('filter');
                }
            };
            gcuitabs.prototype._normalizeBlindOption = function (o) {
                if(o.blind === undefined) {
                    o.blind = false;
                }
                if(o.fade === undefined) {
                    o.fade = false;
                }
                if(o.duration === undefined) {
                    o.duration = 200;
                }
                if(typeof o.duration === 'string') {
                    try  {
                        o.duration = parseInt(o.duration, 10);
                    } catch (e) {
                        o.duration = 200;
                    }
                }
            };
            gcuitabs.prototype._initScroller = function () {
                var _this = this;
                var horz = $.inArray(this._getAlignment(true), ['Top', 'Bottom']) !== -1, width = 0;
                if(!horz) {
                    return;
                }
                $.each(this.lis, function (idx, li) {
                    var item = $(li), link, itemWidth = 0, iefix = 0;
                    if (GC.Spread.Sheets.util.browser.msie && !!_this.options.scrollable) {
                        $.each(item.children(), function (i, ele) {
                            var jqEle = $(ele);
                            if(jqEle.is("a")) {
                                jqEle.width(jqEle.width());
                            }
                            itemWidth += jqEle.outerWidth(true);
                        });
                        iefix = itemWidth - item.width();
                        item.width(itemWidth);
                    }
                    width += _this._getLiWidth($(li)) + iefix;
                });
                if(!!this.options.scrollable && this.element.innerWidth() < width) {
                    if(this.scrollWrap === undefined) {
                        this.list.wrap("<div class='scrollWrap'></div>");
                        this.scrollWrap = this.list.parent();
                        if(effectsSave) {
                            effectsSave(this.list, ['width', 'height', 'overflow']);
                        }
                    }
                    this.list.width(Math.ceil(width) + 2);
                    this.scrollWrap.height(this.list.outerHeight(true));
                    this.scrollWrap.superpanel({
                        allowResize: false,
                        hScroller: {
                            scrollMode: 'edge'
                        },
                        vScroller: {
                            scrollBarVisibility: 'hidden'
                        }
                    });
                } else {
                    this._removeScroller();
                }
            };
            gcuitabs.prototype._ui = function (tab, panel) {
                return {
                    tab: tab,
                    panel: panel,
                    index: this.anchors.index(tab)
                };
            };
            gcuitabs.prototype._tabId = function (a) {
                var $a = $(a), tabId, hrefParams;
                if($a.data && $a.data("tabid")) {
                    return $a.data("tabid");
                }
                if(a.href && a.href.length) {
                    hrefParams = this._getURLParameters(a.href);
                    if(hrefParams.tabId) {
                        tabId = hrefParams.tabId;
                        $a.data("tabid", tabId);
                        return tabId;
                    }
                }
                tabId = a.title && a.title.replace(/\s/g, '_').replace(/[^A-Za-z0-9\-_:\.]/g, '') || this.options.idPrefix + getNextTabId();
                $a.data("tabid", tabId);
                return tabId;
            };
            gcuitabs.prototype._getURLParameters = function (url) {
                var params = {
                }, parametersString, parameters;
                if(url.indexOf('?') > -1) {
                    parametersString = url.split('?')[1];
                    parameters = parametersString.split('&');
                    $.each(parameters, function (i, param) {
                        var p = param.split('=');
                        if(p.length > 1) {
                            params[p[0]] = p[1];
                        }
                    });
                }
                return params;
            };
            gcuitabs.prototype._getAlignment = function (tabs) {
                var align = this.options.alignment || 'top';
                if(tabs) {
                    return align.charAt(0).toUpperCase() + align.substr(1);
                }
                switch(align) {
                    case 'top':
                        align = 'Bottom';
                        break;
                    case 'bottom':
                        align = 'Top';
                        break;
                    case 'left':
                        align = 'Right';
                        break;
                    case 'right':
                        align = 'Left';
                        break;
                }
                return align;
            };
            gcuitabs.prototype._sanitizeSelector = function (hash) {
                return hash.replace(/:/g, '\\:');
            };
            gcuitabs.prototype._innerDestroy = function (keepAddedItems) {
                var o = this.options, content = $('.' + o.uiCSS.tabsContent);
                this.abort();
                this._removeScroller();
                this.element.unbind('.tabs').removeClass(o.uiCSS.gcuitabs).removeClass(o.uiCSS.tabsTop).removeClass(o.uiCSS.tabsBottom).removeClass(o.uiCSS.tabsLeft).removeClass(o.uiCSS.tabsRight).removeClass(o.uiCSS.tabs).removeClass(o.uiCSS.widget).removeClass(o.uiCSS.content).removeClass(o.uiCSS.cornerAll).removeClass(o.uiCSS.tabsCollapsible).removeClass(o.uiCSS.helperClearFix).removeData('tabs').removeAttr('role');
                this.list.removeClass(o.uiCSS.tabsNav).removeClass(o.uiCSS.helperReset).removeClass(o.uiCSS.helperClearFix).removeClass(o.uiCSS.header).removeClass(o.uiCSS.cornerAll).removeAttr('role');
                $.each(this.anchors, function (idx, a) {
                    var $a = $(a), href = $a.data('href.tabs');
                    if(href) {
                        a.href = href;
                    }
                    $a.unbind('.tabs');
                    $.each(['href', 'load', 'cache'], function (i, prefix) {
                        $a.removeData(prefix + '.tabs');
                    });
                });
                this.lis.unbind('.tabs').add(this.panels).each(function (idx, li) {
                    var $li = $(li);
                    if($li.data('destroy.tabs') && !keepAddedItems) {
                        $li.remove();
                    } else {
                        $li.removeClass(o.uiCSS.stateDefault).removeClass(o.uiCSS.cornerTop).removeClass(o.uiCSS.cornerBottom).removeClass(o.uiCSS.cornerLeft).removeClass(o.uiCSS.cornerRight).removeClass(o.uiCSS.tabsActive).removeClass(o.uiCSS.stateActive).removeClass(o.uiCSS.stateHover).removeClass(o.uiCSS.stateFocus).removeClass(o.uiCSS.stateDisabled).removeClass(o.uiCSS.tabsPanel).removeClass(o.uiCSS.content).removeClass(o.uiCSS.tabsHide).css({
                            position: '',
                            left: '',
                            top: ''
                        }).removeAttr('role').removeAttr('aria-hidden').removeAttr('aria-selected').removeAttr('aria-disabled');
                    }
                });
                if(content.length) {
                    content.replaceWith(content.contents());
                }
                if(o.cookie) {
                    this._cookie(null, o.cookie);
                }
                return this;
            };
            gcuitabs.prototype._cleanup = function () {
                var css = this.options.uiCSS;
                this.lis.filter('.' + css.tabsLoading).removeClass(css.tabsLoading).find('span:data(label.tabs)').each(function () {
                    var el = $(this);
                    el.html(el.data('label.tabs')).removeData('label.tabs');
                });
            };
            gcuitabs.prototype._removeScroller = function () {
                if(!this.scrollWrap) {
                    return;
                }
                this.scrollWrap.superpanel('destroy').replaceWith(this.scrollWrap.contents());
                this.scrollWrap = undefined;
                if(effectsRestore) {
                    effectsRestore(this.list, ['width', 'height', 'overflow']);
                }
            };
            gcuitabs.prototype._cookie = function (index, cookie) {
                var c = this.cookie || (this.cookie = this.options.cookie.name || 'ui-tabs-' + getNextListId());
                return $.cookie.apply(null, [
                    c
                ].concat($.makeArray(arguments)));
            };
            gcuitabs.prototype._getLiWidth = function (li) {
                return this._parsePxToNumber(li.css('margin-left')) + this._parsePxToNumber(li.css('border-left-width')) + this._parsePxToNumber(li.css('padding-left')) + this._parsePxToNumber(li.css('width')) + this._parsePxToNumber(li.css('margin-right')) + this._parsePxToNumber(li.css('border-right-width')) + this._parsePxToNumber(li.css('padding-right'));
            };
            gcuitabs.prototype._parsePxToNumber = function (px) {
                var pxIndex = px.indexOf("px");
                if(!pxIndex || pxIndex === -1) {
                    return 0;
                } else {
                    return parseFloat(px.substr(0, pxIndex));
                }
            };
            gcuitabs.prototype.abort = function () {
                this.element.queue([]);
                this.panels.stop(false, true);
                this.element.queue("tabs", this.element.queue("tabs").splice(-2, 2));
                if(this.xhr) {
                    this.xhr.abort();
                    delete this.xhr;
                }
                this._cleanup();
                return this;
            };
            gcuitabs.prototype.select = function (index) {
                if(typeof index === 'string') {
                    index = this.anchors.index(this.anchors.filter('[href$=' + index + ']'));
                } else if(index === null) {
                    index = -1;
                }
                if(index === -1 && this.options.collapsible) {
                    index = this.options.selected;
                }
                this.anchors.eq(index).trigger(this.options.event + '.tabs');
                return this;
            };
            gcuitabs.prototype.load = function (index) {
                var self = this, o = self.options, a = self.anchors.eq(index)[0], url = $.data(a, 'load.tabs'), span = $('span', a);
                self.abort();
                if(false === self._trigger('beforeShow', null, self._ui(self.anchors[index], self.panels[index]))) {
                    self.element.dequeue("tabs");
                    return;
                }
                if(!url || self.element.queue("tabs").length !== 0 && $.data(a, 'cache.tabs')) {
                    self.element.dequeue("tabs");
                    return;
                }
                self.lis.eq(index).addClass(o.uiCSS.tabsLoading);
                if(o.spinner || self._defaults.spinner) {
                    span.data('label.tabs', span.html()).html(o.spinner || self._defaults.spinner);
                }
                self.xhr = $.ajax($.extend({
                }, o.ajaxOptions, {
                    url: url,
                    success: function (r, s) {
                        $(self._sanitizeSelector(a.hash), self.element).html(r);
                        self._cleanup();
                        if(o.cache) {
                            $.data(a, 'cache.tabs', true);
                        }
                        self._trigger('load', null, self._ui(self.anchors[index], self.panels[index]));
                        try  {
                            o.ajaxOptions.success(r, s);
                        } catch (e1) {
                        }
                    },
                    error: function (xhr, s, e) {
                        self._cleanup();
                        self._trigger('load', null, self._ui(self.anchors[index], self.panels[index]));
                        try  {
                            o.ajaxOptions.error(xhr, s, index, a);
                        } catch (e2) {
                        }
                    }
                }));
                self.element.dequeue("tabs");
                return self;
            };
            gcuitabs.prototype.add = function (url, label, index) {
                if(index === undefined) {
                    index = this.anchors.length;
                }
                var self = this, o = self.options, $li = $((o.tabTemplate || self._defaults.tabTemplate).replace(/#\{href\}/g, url).replace(/#\{label\}/g, label)), id = !url.indexOf('#') ? url.replace('#', '') : self._tabId($('a', $li)[0]), tabsAlign = self._getAlignment(true), panelCorner = self._getAlignment(false), $panel = $('#' + id), $content;
                $li.addClass(o.uiCSS.stateDefault).addClass(o.uiCSS["corner" + tabsAlign]).data('destroy.tabs', true).attr('role', 'tab').attr('aria-selected', false);
                if(!$panel.length) {
                    $panel = $(o.panelTemplate || self._defaults.panelTemplate).attr('id', id).data('destroy.tabs', true).attr('role', 'tabpanel');
                }
                $panel.addClass(o.uiCSS.tabsPanel).addClass(o.uiCSS.content).addClass(o.uiCSS["corner" + panelCorner] + ' ' + o.uiCSS.tabsHide).attr('aria-hidden', true);
                if(index >= self.lis.length) {
                    $li.appendTo(self.list);
                    if(self.panels.length > 0) {
                        $panel.insertAfter(self.panels[self.panels.length - 1]);
                    } else {
                        $content = self.element.find('.' + o.uiCSS.tabsContent);
                        if($content.length === 0) {
                            $content = self.element;
                        }
                        $panel.appendTo($content);
                    }
                } else {
                    $li.insertBefore(self.lis[index]);
                    $panel.insertBefore(self.panels[index]);
                }
                o.disabledIndexes = $.map(o.disabledIndexes, function (n, i) {
                    return n >= index ? ++n : n;
                });
                self._removeScroller();
                self._tabify(false);
                if(self.anchors.length === 1) {
                    o.selected = 0;
                    $li.addClass(o.uiCSS.tabsActive).addClass(o.uiCSS.stateActive).attr('aria-selected', true);
                    $panel.removeClass(o.uiCSS.tabsHide).attr('aria-hidden', false);
                    self.element.queue("tabs", function () {
                        if(self.element.triggerVisibility) {
                            $(self.panels[0]).triggerVisibility();
                        }
                        self._trigger('show', null, self._ui(self.anchors[0], self.panels[0]));
                    });
                    self.load(0);
                }
                self._trigger('add', null, self._ui(self.anchors[index], self.panels[index]));
                return self;
            };
            gcuitabs.prototype.remove = function (index) {
                var o = this.options, $li = this.lis.eq(index).remove(), $panel = this.panels.eq(index).remove();
                if($li.hasClass(o.uiCSS.tabsActive) && this.anchors.length > 1) {
                    this.select(index + (index + 1 < this.anchors.length ? 1 : -1));
                }
                o.disabledIndexes = $.map($.grep(o.disabledIndexes, function (n, i) {
                    return n !== index;
                }, false), function (n, i) {
                    return n >= index ? --n : n;
                });
                this._removeScroller();
                this._tabify(false);
                this._trigger('remove', null, this._ui($li.find('a')[0], $panel[0]));
                return this;
            };
            gcuitabs.prototype.enableTab = function (index) {
                var o = this.options;
                if($.inArray(index, o.disabledIndexes) === -1) {
                    return;
                }
                this.lis.eq(index).removeClass(o.uiCSS.stateDisabled).removeAttr('aria-disabled');
                o.disabledIndexes = $.grep(o.disabledIndexes, function (n, i) {
                    return n !== index;
                }, false);
                this._trigger('enable', null, this._ui(this.anchors[index], this.panels[index]));
                return this;
            };
            gcuitabs.prototype.disableTab = function (index) {
                var o = this.options;
                if(index !== o.selected) {
                    this.lis.eq(index).addClass(o.uiCSS.stateDisabled).attr('aria-disabled', true);
                    o.disabledIndexes.push(index);
                    o.disabledIndexes.sort();
                    this._trigger('disable', null, this._ui(this.anchors[index], this.panels[index]));
                }
                return this;
            };
            gcuitabs.prototype.url = function (index, url) {
                this.anchors.eq(index).removeData('cache.tabs').data('load.tabs', url);
                return this;
            };
            gcuitabs.prototype.length = function () {
                return this.anchors.length;
            };
            return gcuitabs;
        })(gcui.gcuiWidget);
        tabs.gcuitabs = gcuitabs;        
        gcuitabs.prototype._defaults = {
            panelTemplate: '<div></div>',
            spinner: '<em>Loading&#8230;</em>',
            tabTemplate: '<li><a href="#{href}"><span>#{label}</span></a></li>'
        };
        var gcuitabs_options = (function () {
            function gcuitabs_options() {
                this.uiCSS = {
                    gcuitabs: "gcui-gcuitabs",
                    tabsContent: "gcui-gcuitabs-content",
                    tabsHide: "gcui-gcuitabs-hide"
                };
                this.mobileCSS = {
                    header: "ui-header ui-bar-c",
                    content: "ui-content ui-body ui-body-c",
                    stateDefault: "ui-btn-up-a",
                    stateActive: "ui-btn-down-b"
                };
                this.alignment = 'top';
                this.sortable = false;
                this.scrollable = false;
                this.ajaxOptions = null;
                this.cache = false;
                this.cookie = null;
                this.collapsible = false;
                this.hideOption = null;
                this.showOption = null;
                this.disabledIndexes = [];
                this.event = 'click';
                this.idPrefix = 'ui-tabs-';
                this.panelTemplate = '';
                this.spinner = '';
                this.tabTemplate = '';
                this.add = null;
                this.remove = null;
                this.select = null;
                this.beforeShow = null;
                this.show = null;
                this.load = null;
                this.disable = null;
                this.enable = null;
            }
            return gcuitabs_options;
        })();        
        ;
        gcuitabs.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, new gcuitabs_options());
        $.gcui.registerWidget("gcuitabs", gcuitabs.prototype);
        ;
    })(gcui.tabs || (gcui.tabs = {}));
    var tabs = gcui.tabs;
})(gcui || (gcui = {}));

$.fn.extend({
    getBounds: function () {
        return $.extend({}, $(this).offset(), {
            width: $(this).outerWidth(true),
            height: $(this).outerHeight(true)
        });
    },
    setBounds: function (bounds) {
        $(this).css({
            'left': bounds.left,
            'top': bounds.top
        }).width(bounds.width).height(bounds.height);
        return this;
    },
    getMaxZIndex: function () {
        var max = (($(this).css('z-index') == 'auto') ? 0 : $(this).css('z-index')) * 1;
        $(this).siblings().each(function (i, e) {
            max = Math.max(max, (($(e).css('z-index') == 'auto') ? 0 : $(e).css('z-index')) * 1);
        });
        return Math.max(max, $(this).zIndex());
    }
});

(function (gcui) {
    (function (popup) {
        var gcuipopup = (function (_super) {
            __extends(gcuipopup, _super);
            function gcuipopup() {
                _super.apply(this, arguments);

            }
            gcuipopup.prototype._init = function () {
                this.elementZIndex = this.element.zIndex();
                if(!!this.options.ensureOutermost) {
                    var root = $('form');
                    if(root.length === 0) {
                        root = $(document.body);
                    }
                    this.element.appendTo(root);
                }
                this.element.data('visible.gcuipopup', false);
                this.element.css('position', "absolute");
                this.element.position({
                    of: $(document.body)
                });
                this.element.hide();
            };
            gcuipopup.prototype._setOption = function (key, value) {
                _super.prototype._setOption.call(this, key, value);
                if(key === 'autoHide') {
                    var visible = this.isVisible();
                    this.hide();
                    if(visible) {
                        this.show();
                    }
                }
            };
            gcuipopup.prototype.destroy = function () {
                _super.prototype.destroy.call(this);
                if(this.isVisible()) {
                    this.hide();
                }
                var browser = GC.Spread.Sheets.util.browser;
                if (browser.msie && (parseInt(browser.version) < 7)) {
                    var jFrame = this.element.data('backframe.gcuipopup');
                    if(!jFrame) {
                        jFrame.remove();
                    }
                }
                var self = this;
                this.element.unbind('.gcuipopup');
                $.each(["visible", "backframe", "animating", "width"], function (i, prefix) {
                    self.element.removeData(prefix + ".gcuipopup");
                });
            };
            gcuipopup.prototype.isVisible = function () {
                return (!!this.element.data('visible.gcuipopup') && this.element.is(':visible'));
            };
            gcuipopup.prototype.isAnimating = function () {
                return !!this.element.data("animating.gcuipopup");
            };
            gcuipopup.prototype.show = function (position) {
                var self = this;
                this._setPosition(position);
                if(this.isVisible()) {
                    return;
                }
                if(this._trigger('showing') === false) {
                    return;
                }
                if(this.options.autoHide) {
                    window.setTimeout(function () {
                        $(document).bind('mouseup.gcuipopup', $.proxy(self._onDocMouseUp, self));
                    }, 0);
                }
                var effect = this.options.showEffect || "show";
                var duration = this.options.showDuration || 300;
                var ops = this.options.showOptions || {
                };
                this.element.data("animating.gcuipopup", true);
                if($.effects && $.effects.effect[effect]) {
                    this.element.show(effect, ops, duration, $.proxy(this._showCompleted, this));
                } else {
                    this.element[effect]((effect === 'show' ? null : duration), $.proxy(this._showCompleted, this));
                }
                if(!effect || !duration || effect === 'show' || duration <= 0) {
                    this._showCompleted();
                }
            };
            gcuipopup.prototype._showCompleted = function () {
                this.element.removeData("animating.gcuipopup");
                this.element.data('visible.gcuipopup', true);
                this._trigger('shown');
            };
            gcuipopup.prototype.showAt = function (x, y) {
                this.show({
                    my: 'left+' + x + ' top+' + y,
                    at: 'left top',
                    of: document.body
                });
            };
            gcuipopup.prototype.hide = function () {
                if(!this.isVisible()) {
                    return;
                }
                if(this._trigger('hiding') === false) {
                    return;
                }
                $(document).unbind('mouseup.gcuipopup');
                var effect = this.options.hideEffect || "hide";
                var duration = this.options.hideDuration || 300;
                var ops = this.options.hideOptions || {
                };
                this.element.data("animating.gcuipopup", true);
                if($.effects && $.effects.effect[effect]) {
                    this.element.hide(effect, ops, duration, $.proxy(this._hideCompleted, this));
                } else {
                    this.element[effect]((effect === 'hide' ? null : duration), $.proxy(this._hideCompleted, this));
                }
                if(!effect || !duration || effect === 'hide' || duration <= 0) {
                    this._hideCompleted();
                }
            };
            gcuipopup.prototype._hideCompleted = function () {
                if(this.element.data('width.gcuipopup') !== undefined) {
                    this.element.width(this.element.data('width.gcuipopup'));
                    this.element.removeData('width.gcuipopup');
                }
                this.element.unbind('move.gcuipopup');
                this.element.removeData("animating.gcuipopup");
                var browser = GC.Spread.Sheets.util.browser;
                if (browser.msie && (parseInt(browser.version) < 7)) {
                    var jFrame = this.element.data('backframe.gcuipopup');
                    if(jFrame) {
                        jFrame.hide();
                    }
                }
                this._trigger('hidden');
            };
            gcuipopup.prototype._onDocMouseUp = function (e) {
                var srcElement = e.target ? e.target : e.srcElement;
                if(this.isVisible() && !!this.options.autoHide) {
                    if(srcElement != this.element.get(0) && $(srcElement).parents().index(this.element) < 0) {
                        this.hide();
                    }
                }
            };
            gcuipopup.prototype._onMove = function (e) {
                var jFrame = this.element.data('backframe.gcuipopup');
                if(jFrame) {
                    this.element.before(jFrame);
                    jFrame.css({
                        'top': this.element.css('top'),
                        'left': this.element.css('left')
                    });
                }
            };
            gcuipopup.prototype._addBackgroundIFrame = function () {
                 var browser = GC.Spread.Sheets.util.browser;
                if (browser.msie && (parseInt(browser.version) < 7)) {
                    var jFrame = this.element.data('backframe.gcuipopup');
                    if(!jFrame) {
                        jFrame = jQuery('<iframe/>').css({
                            'position': 'absolute',
                            'display': 'none',
                            'filter': 'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'
                        }).attr({
                            'src': 'javascript:\'<html></html>\';',
                            'scrolling': 'no',
                            'frameborder': '0',
                            'tabIndex ': -1
                        });
                        this.element.before(jFrame);
                        this.element.data('backframe.gcuipopup', jFrame);
                        this.element.bind('move.gcuipopup', $.proxy(this._onMove, this));
                    }
                    jFrame.setBounds(this.element.getBounds());
                    jFrame.css({
                        'display': 'block',
                        'left': this.element.css('left'),
                        'top': this.element.css('top'),
                        'z-index': this.element.css('z-index') - 1
                    });
                }
            };
            gcuipopup.prototype._setZIndex = function (index) {
                this.element.css('z-index', index);
                var jFrame = this.element.data('backframe.gcuipopup');
                if(jFrame) {
                    jFrame.css('z-index', (this.element.css('z-index')) - 1);
                }
            };
            gcuipopup.prototype._setPosition = function (position) {
                var visible = this.element.is(':visible'), of;
                this.element.show();
                this.element.position($.extend({
                }, this.options.position, position ? position : {
                }));
                if(!visible) {
                    this.element.hide();
                }
                if(position) {
                    of = position.of;
                }
                if(!of) {
                    of = this.options.position.of;
                }
                this._addBackgroundIFrame();
                var zIndex = Math.max(1000, this.elementZIndex);
                if(of) {
                    zIndex = Math.max(zIndex, $(of).getMaxZIndex());
                }
                this._setZIndex(zIndex + 10);
                this._trigger('posChanged');
            };
            return gcuipopup;
        })(gcui.GCUIWidget);
        popup.gcuipopup = gcuipopup;        
        var gcuipopup_options = (function () {
            function gcuipopup_options() {
                this.ensureOutermost = false;
                this.showEffect = 'show';
                this.showOptions = {
                };
                this.showDuration = 300;
                this.hideEffect = 'hide';
                this.hideOptions = {
                };
                this.hideDuration = 100;
                this.autoHide = false;
                this.position = {
                    at: 'left bottom',
                    my: 'left top'
                };
                this.showing = null;
                this.shown = null;
                this.hiding = null;
                this.hidden = null;
                this.posChanged = null;
            }
            return gcuipopup_options;
        })();        
        ;
        gcuipopup.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, new gcuipopup_options());
        $.gcui.registerWidget("gcuipopup", gcuipopup.prototype);
    })(gcui.popup || (gcui.popup = {}));
    var popup = gcui.popup;
})(gcui || (gcui = {}));

jQuery.extend(jQuery.support, {
    isTouchEnabled: function () {
        if(!("ontouchstart" in window) && !("ontouchend" in document) && !(typeof (window.navigator.msMaxTouchPoints) !== "undefined" && window.navigator.msMaxTouchPoints > 0)) {
            return false;
        } else {
            return true;
        }
    }
});
function applyTouchUtilEvents($) {
    return $;
}

(function (gcui) {
    (function (touch) {
        "use strict";
        var $ = jQuery;
        var dataPropertyName = "virtualMouseBindings", touchTargetPropertyName = "virtualTouchID", virtualEventNames = "gcuimouseover gcuimousedown gcuimousemove gcuimouseup gcuiclick gcuitap gcuidoubletap gcuitaphold gcuimouseout gcuimousecancel".split(" "), msPointerEnabled = window.navigator.msPointerEnabled, realEventNames = {
            "gcuimouseover": (!msPointerEnabled ? "mouseover" : "MSPointerOver"),
            "gcuimousedown": (!msPointerEnabled ? "mousedown" : "MSPointerDown"),
            "gcuijmousemove": (!msPointerEnabled ? "mousemove" : "MSPointerMove"),
            "gcuimouseup": (!msPointerEnabled ? "mouseup" : "MSPointerUp"),
            "gcuimouseout": (!msPointerEnabled ? "mouseout" : "MSPointerOut"),
            "gcuimousecancel": (!msPointerEnabled ? "mousecancel" : "mousecancel"),
            "gcuiclick": (!msPointerEnabled ? "click" : "MSGestureTap"),
            "gcuidoubletap": (!msPointerEnabled ? "dblclick" : "MSGestureDoubleTap"),
            "gcuitaphold": (!msPointerEnabled ? "gesturehold" : "MSGestureHold")
        }, touchEventProps = "clientX clientY pageX pageY screenX screenY".split(" "), nativeEventProps = "clientX clientY pageX pageY screenX screenY".split(" "), mouseHookProps = $.event.mouseHooks ? $.event.mouseHooks.props : [], mouseEventProps = $.event.props.concat(mouseHookProps), activeDocHandlers = {
        }, resetTimerID = 0, startX = 0, startY = 0, didScroll = false, clickBlockList = [], blockMouseTriggers = false, blockTouchTriggers = false, eventCaptureSupported = "addEventListener" in document, $document = $(document), nextTouchID = 1, lastTouchID = 0, touchStartEventM = (!msPointerEnabled) ? "touchstart" : "MSPointerDown", touchEndEventM = (!msPointerEnabled) ? "touchend" : "MSPointerUp", touchMoveEventM = (!msPointerEnabled) ? "touchmove" : "MSPointerMove", touchScrollEventM = (!msPointerEnabled) ? "scroll" : "scroll";
        $.gcuimouse = {
            moveDistanceThreshold: 10,
            clickDistanceThreshold: 10,
            resetTimerDuration: 1500
        };
        function getNativeEvent(event) {
            while(event && typeof event.originalEvent !== "undefined") {
                event = event.originalEvent;
            }
            return event;
        }
        function createVirtualEvent(event, eventType) {
            var t = event.type, oe, props, ne, prop, ct, touch, i, j, len;
            event = $.Event(event);
            event.nativeType = event.type;
            event.type = eventType;
            oe = event.originalEvent;
            props = $.event.props;
            if(t.search(/^(mouse|click)/) > -1) {
                props = mouseEventProps;
            }
            if(oe) {
                for(i = props.length , prop; i; ) {
                    prop = props[--i];
                    event[prop] = oe[prop];
                }
            }
            if(t.search(/mouse(down|up)|click/) > -1 && !event.which) {
                event.which = 1;
            }
            ne = getNativeEvent(oe);
            for(j = 0 , len = nativeEventProps.length; j < len; j++) {
                prop = nativeEventProps[j];
                if(event[prop] === undefined) {
                    event[prop] = ne[prop];
                }
            }
            if(t.search(/^touch/) !== -1) {
                t = ne.touches;
                ct = ne.changedTouches;
                touch = (t && t.length) ? t[0] : ((ct && ct.length) ? ct[0] : undefined);
                if(touch) {
                    for(j = 0 , len = touchEventProps.length; j < len; j++) {
                        prop = touchEventProps[j];
                        event[prop] = touch[prop];
                    }
                }
            }
            return event;
        }
        function getVirtualBindingFlags(element) {
            var flags = {
            }, b, k;
            while(element) {
                b = $.data(element, dataPropertyName);
                for(k in b) {
                    if(b[k]) {
                        flags[k] = flags.hasVirtualBinding = true;
                    }
                }
                element = element.parentNode;
            }
            return flags;
        }
        function getClosestElementWithVirtualBinding(element, eventType) {
            var b;
            while(element) {
                b = $.data(element, dataPropertyName);
                if(b && (!eventType || b[eventType])) {
                    return element;
                }
                element = element.parentNode;
            }
            return null;
        }
        function enableTouchBindings() {
            blockTouchTriggers = false;
        }
        function disableTouchBindings() {
            blockTouchTriggers = true;
        }
        function enableMouseBindings() {
            lastTouchID = 0;
            clickBlockList.length = 0;
            blockMouseTriggers = false;
            disableTouchBindings();
        }
        function disableMouseBindings() {
            enableTouchBindings();
        }
        function startResetTimer() {
            clearResetTimer();
            resetTimerID = setTimeout(function () {
                resetTimerID = 0;
                enableMouseBindings();
            }, $.gcuimouse.resetTimerDuration);
        }
        function clearResetTimer() {
            if(resetTimerID) {
                clearTimeout(resetTimerID);
                resetTimerID = 0;
            }
        }
        function triggerVirtualEvent(eventType, event, flags) {
            var ve;
            if((flags && flags[eventType]) || (!flags && getClosestElementWithVirtualBinding(event.target, eventType))) {
                ve = createVirtualEvent(event, eventType);
                $(event.target).trigger(ve);
            }
            return ve;
        }
        function mouseEventCallback(event) {
            var touchID = $.data(event.target, touchTargetPropertyName), vEventName, k;
            if(!blockMouseTriggers && (!lastTouchID || lastTouchID !== touchID)) {
                vEventName = "gcui" + event.type;
                for(k in realEventNames) {
                    if(realEventNames[k] === event.type) {
                        vEventName = k;
                    }
                }
                var ve = triggerVirtualEvent(vEventName, event);
                if(ve) {
                    if(ve.isDefaultPrevented()) {
                        event.preventDefault();
                    }
                    if(ve.isPropagationStopped()) {
                        event.stopPropagation();
                    }
                    if(ve.isImmediatePropagationStopped()) {
                        event.stopImmediatePropagation();
                    }
                }
            }
        }
        function handleTouchStart(event) {
            var touches = getNativeEvent(event).touches, target, flags, ne;
            if(touches && touches.length === 1 || msPointerEnabled) {
                target = event.target;
                flags = getVirtualBindingFlags(target);
                if(flags.hasVirtualBinding) {
                    lastTouchID = nextTouchID++;
                    $.data(target, touchTargetPropertyName, lastTouchID);
                    clearResetTimer();
                    disableMouseBindings();
                    didScroll = false;
                    ne = getNativeEvent(event);
                    var t = ne.touches ? ne.touches[0] : ne;
                    startX = t.pageX;
                    startY = t.pageY;
                    triggerVirtualEvent("gcuimouseover", event, flags);
                    triggerVirtualEvent("gcuimousedown", event, flags);
                }
            }
        }
        function handleScroll(event) {
            if(blockTouchTriggers) {
                return;
            }
            if(!didScroll) {
                triggerVirtualEvent("gcuimousecancel", event, getVirtualBindingFlags(event.target));
            }
            didScroll = true;
            startResetTimer();
        }
        function handleTouchMove(event) {
            if(blockTouchTriggers) {
                return;
            }
            var ne = getNativeEvent(event), t = ne.touches ? ne.touches[0] : ne, didCancel = didScroll, moveThreshold = $.gcuimouse.moveDistanceThreshold;
            didScroll = didScroll || (Math.abs(t.pageX - startX) > moveThreshold || Math.abs(t.pageY - startY) > moveThreshold);
            var flags = getVirtualBindingFlags(event.target);
            if(didScroll && !didCancel) {
                triggerVirtualEvent("gcuimousecancel", event, flags);
            }
            triggerVirtualEvent("gcuimousemove", event, flags);
            startResetTimer();
        }
        function handleTouchEnd(event) {
            if(blockTouchTriggers) {
                return;
            }
            disableTouchBindings();
            var flags = getVirtualBindingFlags(event.target), ne, t;
            triggerVirtualEvent("gcuimouseup", event, flags);
            if(!didScroll) {
                var ve = triggerVirtualEvent("gcuiclick", event, flags);
                if(ve && ve.isDefaultPrevented()) {
                    ne = getNativeEvent(event);
                    t = ne.changedTouches ? ne.changedTouches[0] : ne;
                    clickBlockList.push({
                        touchID: lastTouchID,
                        x: t.clientX,
                        y: t.clientY
                    });
                    blockMouseTriggers = true;
                }
            }
            triggerVirtualEvent("gcuimouseout", event, flags);
            didScroll = false;
            startResetTimer();
        }
        function hasVirtualBindings(ele) {
            var bindings = $.data(ele, dataPropertyName), k;
            if(bindings) {
                for(k in bindings) {
                    if(bindings[k]) {
                        return true;
                    }
                }
            }
            return false;
        }
        function dummyMouseHandler() {
        }
        function getSpecialEventObject(eventType) {
            var realType = realEventNames[eventType];
            return {
                setup: function (data, namespace) {
                    if(!hasVirtualBindings(this)) {
                        $.data(this, dataPropertyName, {
                        });
                    }
                    var bindings = $.data(this, dataPropertyName);
                    bindings[eventType] = true;
                    $(this).bind(realType, dummyMouseHandler);
                    var touchStartBound = false;
                    if(eventCaptureSupported) {
                        activeDocHandlers[touchStartEventM] = (activeDocHandlers[touchStartEventM] || 0) + 1;
                        if(activeDocHandlers[touchStartEventM] === 1) {
                            touchStartBound = true;
                            $document.bind(touchStartEventM, handleTouchStart).bind(touchEndEventM, handleTouchEnd).bind(touchMoveEventM, handleTouchMove).bind(touchScrollEventM, handleScroll);
                        }
                    }
                    if(!touchStartBound || touchStartEventM !== realType) {
                        activeDocHandlers[eventType] = (activeDocHandlers[eventType] || 0) + 1;
                        if(activeDocHandlers[eventType] === 1) {
                            $document.bind(realType, mouseEventCallback);
                        }
                    }
                },
                teardown: function (data, namespace) {
                    --activeDocHandlers[eventType];
                    if(!activeDocHandlers[eventType]) {
                        $document.unbind(realType, mouseEventCallback);
                    }
                    if(eventCaptureSupported) {
                        --activeDocHandlers[touchStartEventM];
                        if(!activeDocHandlers[touchStartEventM]) {
                            $document.unbind(touchStartEventM, handleTouchStart).unbind(touchMoveEventM, handleTouchMove).unbind(touchEndEventM, handleTouchEnd).unbind(touchScrollEventM, handleScroll);
                        }
                    }
                    var $this = $(this), bindings = $.data(this, dataPropertyName);
                    if(bindings) {
                        bindings[eventType] = false;
                    }
                    $this.unbind(realType, dummyMouseHandler);
                    if(!hasVirtualBindings(this)) {
                        $this.removeData(dataPropertyName);
                    }
                }
            };
        }
        for(var i = 0; i < virtualEventNames.length; i++) {
            $.event.special[virtualEventNames[i]] = getSpecialEventObject(virtualEventNames[i]);
        }
        if(eventCaptureSupported) {
            document.addEventListener("click", function (e) {
                var cnt = clickBlockList.length, target = e.target, x, y, ele, i, o, touchID;
                if(cnt) {
                    x = e.clientX;
                    y = e.clientY;
                    var threshold = $.gcuimouse.clickDistanceThreshold;
                    ele = target;
                    while(ele) {
                        for(i = 0; i < cnt; i++) {
                            o = clickBlockList[i];
                            touchID = 0;
                            if((ele === target && Math.abs(o.x - x) < threshold && Math.abs(o.y - y) < threshold) || $.data(ele, touchTargetPropertyName) === o.touchID) {
                                e.preventDefault();
                                e.stopPropagation();
                                return;
                            }
                        }
                        ele = ele.parentNode;
                    }
                }
            }, true);
        }
        $.each(("touchstart touchmove touchend orientationchange throttledresize " + "gcuitap gcuitaphold gcuiswipe gcuiswipeleft gcuiswiperight gcuiscrollstart gcuiscrollstop").split(" "), function (i, name) {
            $.fn[name] = function (fn) {
                return fn ? this.bind(name, fn) : this.trigger(name);
            };
            if($.attrFn) {
                $.attrFn[name] = true;
            }
        });
        var supportTouch = ("ontouchend" in document), scrollEvent = "touchmove scroll", touchStartEvent = supportTouch ? "touchstart" : "mousedown", touchStopEvent = supportTouch ? "touchend" : "mouseup", touchMoveEvent = supportTouch ? "touchmove" : "mousemove", gesturestartEvent = supportTouch ? "gesturestart" : "gesturestart", gesturechangeEvent = supportTouch ? "gesturechange" : "gesturechange", gestureendEvent = supportTouch ? "gestureend" : "gestureend";
        if(window.navigator.msPointerEnabled) {
            touchStartEvent = "MSPointerDown";
            touchStopEvent = "MSPointerUp";
            touchMoveEvent = "MSPointerMove";
            gesturestartEvent = "MSGestureStart";
            gesturechangeEvent = "MSGestureChange";
            gestureendEvent = "MSGestureEnd";
        }
        function triggerCustomEvent(obj, eventType, event) {
            var originalType = event.type;
            event.nativeType = event.type;
            event.type = eventType;
            try  {
                $([obj]).trigger(event);
            }finally {
                event.type = originalType;
            }
        }
        $.event.special.gcuiscrollstart = {
            enabled: true,
            setup: function () {
                var thisObject = this, $this = $(thisObject), scrolling, timer;
                function trigger(event, state) {
                    scrolling = state;
                    triggerCustomEvent(thisObject, scrolling ? "gcuiscrollstart" : "gciscrollstop", event);
                }
                $this.bind(scrollEvent, function (event) {
                    if (!$.event.special.gcuiscrollstart.enabled) {
                        return;
                    }
                    if(!scrolling) {
                        trigger(event, true);
                    }
                    clearTimeout(timer);
                    timer = setTimeout(function () {
                        trigger(event, false);
                    }, 50);
                });
            }
        };
        $.event.special.gcuitap = {
            setup: function () {
                var thisObject = this, $this = $(thisObject);
                $this.bind("gcuimousedown", function (event) {
                    if(event.which && event.which !== 1) {
                        return false;
                    }
                    var origTarget = event.target, origEvent = event.originalEvent, timer;
                    function clearTapTimer() {
                        clearTimeout(timer);
                    }
                    function clearTapHandlers() {
                        clearTapTimer();
                        $this.unbind("gcuiclick", clickHandler).unbind("gcuimouseup", clearTapTimer);
                        $(document).unbind("gcuimousecancel", clearTapHandlers);
                    }
                    function clickHandler(event) {
                        clearTapHandlers();
                        if(origTarget == event.target) {
                            triggerCustomEvent(thisObject, "gcuitap", event);
                        }
                    }
                    $this.bind("gcuimouseup", clearTapTimer).bind("gcuiclick", clickHandler);
                    $(document).bind("gcuimousecancel", clearTapHandlers);
                    timer = setTimeout(function () {
                        triggerCustomEvent(thisObject, "gcuitaphold", $.Event("gcuitaphold", {
                            target: origTarget
                        }));
                    }, 750);
                });
            }
        };
        $.event.special.gcuiswipe = {
            scrollSupressionThreshold: 10,
            durationThreshold: 1000,
            horizontalDistanceThreshold: 30,
            verticalDistanceThreshold: 75,
            setup: function () {
                var thisObject = this, $this = $(thisObject);
                $this.bind(touchStartEvent, function (event) {
                    var data = event.originalEvent.touches ? event.originalEvent.touches[0] : event, start = {
                        time: (new Date()).getTime(),
                        coords: [
                            data.pageX, 
                            data.pageY
                        ],
                        origin: $(event.target)
                    }, stop;
                    function moveHandler(event) {
                        if(!start) {
                            return;
                        }
                        var data = event.originalEvent.touches ? event.originalEvent.touches[0] : event;
                        stop = {
                            time: (new Date()).getTime(),
                            coords: [
                                data.pageX, 
                                data.pageY
                            ]
                        };
                        if(Math.abs(start.coords[0] - stop.coords[0]) > $.event.special.gcuiswipe.scrollSupressionThreshold) {
                            event.preventDefault();
                        }
                    }
                    $this.bind(touchMoveEvent, moveHandler).one(touchStopEvent, function (event) {
                        $this.unbind(touchMoveEvent, moveHandler);
                        if(start && stop) {
                            if(stop.time - start.time < $.event.special.gcuiswipe.durationThreshold && Math.abs(start.coords[0] - stop.coords[0]) > $.event.special.gcuiswipe.horizontalDistanceThreshold && Math.abs(start.coords[1] - stop.coords[1]) < $.event.special.gcuiswipe.verticalDistanceThreshold) {
                                start.origin.trigger("gcuiswipe").trigger(start.coords[0] > stop.coords[0] ? "gcuiswipeleft" : "gcuiswiperight");
                            }
                        }
                        start = stop = undefined;
                    });
                });
            }
        };
        var gestureEventProps = "altKey ctrlKey metaKey rotation scale shiftKey target velocityX velocityY translationX translationY".split(" ");
        function preInitGestureEvent(elem, eventName) {
            if(window.navigator.msPointerEnabled && !elem.__MSGesturePreInit) {
                elem.__MSGesturePreInit = true;
                var msGesture = new MSGesture();
                msGesture.target = elem;
                elem.addEventListener("MSPointerDown", function (e) {
                    msGesture.addPointer(e.pointerId);
                }, false);
            }
        }
        function triggerGestureEvent(eventType, event) {
            var ve = createGestureEvent(event, eventType);
            $(event.target).trigger(ve);
            return ve;
        }
        function createGestureEvent(event, eventType) {
            var t = event.type, oe, props, ne, prop, ct, touch, i, j, len;
            oe = event.originalEvent;
            event = $.Event(event);
            event.nativeType = event.type;
            event.type = eventType;
            props = $.event.props;
            if(oe) {
                for(i = props.length , prop; i; ) {
                    prop = props[--i];
                    event[prop] = event[prop] || oe[prop];
                }
            }
            for(j = 0 , len = gestureEventProps.length; j < len; j++) {
                prop = gestureEventProps[j];
                event[prop] = event[prop] || oe[prop];
            }
            if(window.navigator.msPointerEnabled && event.rotation) {
                event.rotation = event.rotation * 360 / Math.PI;
            }
            return event;
        }
        $.event.special.gcuigesturestart = {
            setup: function () {
                var thisObject = this, $this = $(thisObject);
                preInitGestureEvent(thisObject, "gcuigesturestart");
                $this.bind(gesturestartEvent, function (event) {
                    triggerGestureEvent("gcuigesturestart", event);
                });
            }
        };
        $.event.special.gcuigesturechange = {
            setup: function () {
                var thisObject = this, $this = $(thisObject);
                preInitGestureEvent(thisObject, "gcuigesturechange");
                $this.bind(gesturechangeEvent, function (event) {
                    triggerGestureEvent("gcuigesturechange", event);
                });
            }
        };
        $.event.special.gcuigestureend = {
            setup: function () {
                var thisObject = this, $this = $(thisObject);
                preInitGestureEvent(thisObject, "gcuigestureend");
                $this.bind(gestureendEvent, function (event) {
                    triggerGestureEvent("gcuigestureend", event);
                });
            }
        };
        (function ($, window) {
            var win = $(window), special_event, get_orientation, last_orientation, initial_orientation_is_landscape, initial_orientation_is_default,
                portrait_map = {
                    "0": true,
                    "180": true
                };
            if("orientation" in window && "onorientationchange" in window) {
                var ww = window.innerWidth || $(window).width(), wh = window.innerHeight || $(window).height(), landscape_threshold = 50;
                initial_orientation_is_landscape = ww > wh && (ww - wh) > landscape_threshold;
                initial_orientation_is_default = portrait_map[window.orientation];
                if((initial_orientation_is_landscape && initial_orientation_is_default) || (!initial_orientation_is_landscape && !initial_orientation_is_default)) {
                    portrait_map = {
                        "-90": true,
                        "90": true
                    };
                }
            }
            $.event.special.orientationchange = special_event = {
                setup: function () {
                    if(("orientation" in window && "onorientationchange" in window) && $.mobile.orientationChangeEnabled) {
                        return false;
                    }
                    last_orientation = get_orientation();
                    win.bind("throttledresize", handler);
                },
                teardown: function () {
                    if(("orientation" in window && "onorientationchange" in window) && $.mobile.orientationChangeEnabled) {
                        return false;
                    }
                    win.unbind("throttledresize", handler);
                },
                add: function (handleObj) {
                    var old_handler = handleObj.handler;
                    handleObj.handler = function (event) {
                        event.orientation = get_orientation();
                        return old_handler.apply(this, arguments);
                    };
                }
            };
            function handler() {
                var orientation = get_orientation();
                if(orientation !== last_orientation) {
                    last_orientation = orientation;
                    win.trigger("orientationchange");
                }
            }
            $.event.special.orientationchange.orientation = get_orientation = function () {
                var isPortrait = true, elem = document.documentElement;
                if("orientation" in window && "onorientationchange" in window) {
                    isPortrait = portrait_map[window.orientation];
                } else {
                    isPortrait = elem && elem.clientWidth / elem.clientHeight < 1.1;
                }
                return isPortrait ? "portrait" : "landscape";
            };
        })(jQuery, window);
        (function () {
            $.event.special.throttledresize = {
                setup: function () {
                    $(this).bind("resize", handler);
                },
                teardown: function () {
                    $(this).unbind("resize", handler);
                }
            };
            var throttle = 250, handler = function () {
                curr = (new Date()).getTime();
                diff = curr - lastCall;
                if(diff >= throttle) {
                    lastCall = curr;
                    $(this).trigger("throttledresize");
                } else {
                    if(heldCall) {
                        clearTimeout(heldCall);
                    }
                    heldCall = setTimeout(handler, throttle - diff);
                }
            }, lastCall = 0, heldCall, curr, diff;
        })();
        $.each({
            gcuiscrollstop: "gcuiscrollstart",
            gcuitaphold: "gcuitap",
            gcuiswipeleft: "gcuiswipe",
            gcuiswiperight: "gcuiswipe"
        }, function (event, sourceEvent) {
            $.event.special[event] = {
                setup: function () {
                    $(this).bind(sourceEvent, $.noop);
                }
            };
        });
    })(gcui.touch || (gcui.touch = {}));
})(gcui || (gcui = {}));


$(document).ready(function () {
	var userAgent = window.navigator.userAgent,
		isAndroidVerBellow2_2 = userAgent.match(new RegExp("android ((1\\.)|(2\\.([^345]+)))", "i")),
		areTransformsSupported = ((window.WebKitCSSMatrix) ? !isAndroidVerBellow2_2 : false), 
		isWebKitEnabled = !!(typeof userAgent == "string" && userAgent.match(/applewebkit/)),
		isTouchDevice = "ontouchend" in document,
		div = document.createElement('div'),
		cssTransitionsSupported;
	div.setAttribute('style', 'transition:top 1s ease;-webkit-transition:top 1s ease;-moz-transition:top 1s ease;-o-transition:top 1s ease;');
	document.body.appendChild(div);
	cssTransitionsSupported = !!(div.style.transition || div.style.webkitTransition || div.style.MozTransition || div.style.OTransitionDuration);
	div.parentNode.removeChild(div);
	div = null;

	// prevent transition flickering for iTouch devices:
	function with3DAcceleration(element, action, args) {
	    var property = "-webkit-transform",
            enabler = "translate3d(0,0,0)",
	        origValue = element.css(property);

	    var isNone = !(origValue && origValue.match(/^\s*none\s*$/i));
	    element.css(property, isNone ? enabler : origValue + " " + enabler);
	    try {
	        return action.apply(this, args);
	    } finally {
	        element.css(property, origValue);
	    }
	}


	var $animate = jQuery.fn.animate;
	jQuery.fn.animate = function (prop, speed, easing, callback) {
	    if (!isWebKitEnabled || !webKitTransition.apply(this, arguments)) {
	        return with3DAcceleration.call(this, this, function () {
	            return $animate.apply(this, arguments);
	        }, arguments);
		}
	};

	function webKitTransition(prop, speed, easing, callback) {
		var result = false, propVal, newVal,
			speedOpt = $.speed(speed, easing, callback),
			self = this;
		if (!prop) {
			return false;
		}

		for (var k in prop) {
			propVal = prop[k];
			if (k === "height") {
				if (propVal === "hide") {
					this.css("display", "none");
					result = true;
					speedOpt.complete();
					//result = true;
					//fadeOut(this[0], speedOpt.duration, "linear", speedOpt.complete);
				} else if (propVal === "show") {
					this.css("display", "");
					result = true;
					speedOpt.complete();
					//result = true;
					//fadeIn(this[0], speedOpt.duration, "linear", speedOpt.complete);
				}
			} else if (k === "width") {
				if (propVal === "hide") {
					this.css("display", "none");
					result = true;
					speedOpt.complete();
					//result = true;
					//fadeOut(this[0], speedOpt.duration, "linear", speedOpt.complete);
				} else if (propVal === "show") {
					this.css("display", "");
					result = true;
					speedOpt.complete();
					//result = true;
					//fadeIn(this[0], speedOpt.duration, "linear", speedOpt.complete);
				}
			} else if (k === "left") {
				newVal = this.css("left").replace("px", "") * 1;

				if (typeof propVal === "string") {
					if (propVal.indexOf("-=") === 0) {
						newVal = newVal -
                                propVal.replace("-=", "") * 1;
					}
					else if (propVal.indexOf("+=") === 0) {
						newVal = newVal +
                                propVal.replace("+=", "") * 1;
					}
				}

				result = true;
				/*
				var completeTranslateTransition = $.proxy(function () {
				speedOpt.complete();
				}, this);
				*/
				// prevent flickering for iTouch devices:
				this.css("-webkit-perspective", 1000);
				this.css("-webkit-backface-visibility", "hidden");
				//
				//-webkit-perspective: 1000;
				//: ;  
				scrollToX(this[0], newVal, speedOpt.duration, "linear", speedOpt.complete);
				//speedOpt.complete();
			}
		}

		return result;
	}

	/// <summary>
	/// Hide the element by fading it to transparent.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element
	///	</param>
	/// <param name="duration" type="Number">
	///	Transition duration in milliseconds.
	///	</param>
	/// <param name="timing" type="String">
	///	String, easing type. 
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
	/// <param name="endHandler" type="Function">
	///	Transition end handler.
	///	</param>
	function fadeOut(element, duration, timing, endHandler) {
	    if (cssTransitionsSupported) {
	        if (!timing) {
	            timing = "ease";
	        }
	        setupTransition(element, "opacity", duration, timing);
	        setOpacity(element, 0);

	        element.addEventListener("webkitTransitionEnd", $.proxy(function () {
	            clearTransition(element);
	            element.style.display = "none";
	            setOpacity(element, 1);
	            if (endHandler) {
	                endHandler();
	            }
	        }, this), false);
	    }
	    else {
	        //jQuery(element).animate({left: + x + "px"}), duration, 'linear', endHandler);
	    }
	}

	/// <summary>
	/// Display the element by fading it to opaque.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element
	///	</param>
	/// <param name="duration" type="Number">
	///	Transition duration in milliseconds.
	///	</param>
	/// <param name="timing" type="String">
	///	String, easing type. 
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
	/// <param name="endHandler" type="Function">
	///	Transition end handler.
	///	</param>
	function fadeIn(element, duration, timing, endHandler) {
	    if (cssTransitionsSupported) {
	        if (!timing) {
	            timing = "ease";
	        }
	        setupTransition(element, "opacity", duration, timing);
	        setOpacity(element, 1);

	        element.addEventListener("webkitTransitionEnd", $.proxy(function () {
	            clearTransition(element);
	            if (element.style.display === "none") {
	                element.style.display = "";
	            }
	            if (endHandler) {
	                endHandler();
	            }
	        }, this), false);
	    }
	    else {
	        //jQuery(element).animate({left: + x + "px"}), duration, 'linear', endHandler);
	    }
	}

	/// <summary>
	/// Scroll element horizontally using webkit transitions.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element
	///	</param>
	/// <param name="x" type="Number">
	///	The new translate value.
	///	</param>
	/// <param name="duration" type="Number">
	///	Transition duration in milliseconds.
	///	</param>
	/// <param name="timing" type="String">
	///	String, easing type. 
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
	/// <param name="endHandler" type="Function">
	///	Transition end handler.
	///	</param>
	function scrollToX(element, x, duration, timing, endHandler) {
	    if (cssTransitionsSupported) {
	        setupTransitionTransform(element, duration, timing);
	        setTranslateX(element, x);
	        element.___animationEndHandler = endHandler;
	        element.___internalEndHandler = $.proxy(_onScrollTransitionEnd, {});
	        element.addEventListener("webkitTransitionEnd", element.___internalEndHandler, false);
	    }
	    else {
	        //jQuery(element).animate({left: + x + "px"}), duration, 'linear', endHandler);
	    }
	}

	/// <summary>
	/// Set opacity value.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element or style object.
	///	</param>
	/// <param name="value" type="Number">
	///	The new opacity value.
	///	</param>
    function setOpacity(element, value) {
	    var st;
	    if (cssTransitionsSupported) {
	        st = element.style || element;
	        st.opacity = value;
	    }
	    else {

	    }
	}


	/// <summary>
	/// Set translate X value. This method will use left style for non-webkit browsers.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element or style object.
	///	</param>
	/// <param name="value" type="Number">
	///	The new translate value.
	///	</param>
    function setTranslateX(element, value) {
	    var st;
	    if (cssTransitionsSupported) {
	        element.___translateX = value;
	        st = element.style || element;
	        //st.webkitTransform = "translate3d(" + value + "px," + getTranslateY(element) + "px, 0)";
	        st.webkitTransform = "translateX(" + value + "px)";
	    }
	    else {
	        element.style.left = value + "px";
	    }
	}

	/// <summary>
	/// Set translate Y value. This method will use top style for non-webkit browsers.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM Element or style object.
	///	</param>
	/// <param name="value" type="Number">
	///	The new translate value.
	///	</param>
    function setTranslateY(element, value) {
	    var st;
	    if (cssTransitionsSupported) {
	        element.___translateY = value;
	        st = element.style || element;
	        //st.webkitTransform = "translate3d(" + getTranslateX(element) + "px," + value + "px, 0)";
	        st.webkitTransform = "translateY(" + value + "px)";
	    }
	    else {
	        element.style.top = value + "px";
	    }
	}

	/// <summary>
	/// Gets translate X value. This method will return left style for non-webkit browsers.
	/// </summary>
    function getTranslateX(element) {
	    var style, transform, r, match, k;
	    if (!element) {
	        return 0;
	    }
	    if (cssTransitionsSupported) {
	        style = element.style || element;
	        transform = style.webkitTransform;
	        if (typeof transform === "string") {
	            r = new RegExp("translate(3d|X|)\\(-*(\\d+)");
	            match = r.exec(transform);
	            if (match) {
	                k = parseFloat(match[1]);
	                if (isFinite(k)) {
	                    return k;
	                }
	            }
	        }
	    }
	    else {
	        var k = parseInt($(element).css("left"));
	        if (isFinite(k)) {
	            return k;
	        }
	    }
	    return 0;
	}

	/// <summary>
	/// Gets translate Y value. This method will return top style for non-webkit browsers.
	/// </summary>
    function getTranslateY(element) {
	    var style, transform, r, match, k;
	    if (!element) {
	        return 0;
	    }
	    if (cssTransitionsSupported) {
	        style = element.style || element;
	        transform = style.webkitTransform;
	        if (typeof transform === "string") {
	            r = new RegExp("translate(3d|X|)\\(.*,\\s*-*(\\d+)");
	            match = r.exec(transform);
	            if (match) {
	                k = parseFloat(match[1]);
	                if (isFinite(k)) {
	                    return k;
	                }
	            }
	        }
	    }
	    else {
	        var k = parseInt(jQuery(element).css("top"));
	        if (isFinite(k)) {
	            return k;
	        }
	    }
	    return 0;
	}

	/// <summary>
	/// Call this method in order to setup transition for transform animation.
	/// </summary>
	/// <param name="duration" type="Number">
	///	Transition duration in milliseconds.
	///	</param>
	/// <param name="timing" type="String">
	///	String, easing type. 
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
    function setupTransitionTransform(element, duration, timing) {
	    if (!timing) {
	        timing = "ease";
	    }
	    setupTransition(element, "-webkit-transform", duration, timing);
	}

	/// <summary>
	/// Setup transition animation for element given by parameter element.
	/// </summary>
	/// <param name="element" type="Object">
	///	DOM element for transition.
	///	</param>
	/// <param name="properties" type="String">
	///	String, specifies the name of the CSS property to which the transition is applied. 
	///	You can list multiple properties. Property names should be bare, unquoted names.
	///	</param>
	/// <param name="duration" type="Number">
	///	Transition duration in milliseconds.
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
	/// <param name="timing" type="String">
	///	String, easing type. 
	///	Available values are: ease, linear, ease-in, ease-out, ease-in-out.
	///	</param>
    function setupTransition(element, properties, duration, timing) {
	    if (cssTransitionsSupported) {
	        var style = (element).style;
	        style.webkitTransitionProperty = properties;
	        style.webkitTransitionDuration = duration + 'ms';
	        style.webkitTransitionTimingFunction = timing;
	    }
	}

	/// <summary>
	/// Clear transition animation for element given by parameter element.
	/// </summary>
    function clearTransition(element) {
	    if (cssTransitionsSupported) {
	        var st = element.style;
	        st.webkitTransitionProperty = 'none';
	        var internalEndHandler = element.___internalEndHandler;
	        if (internalEndHandler) {
	            delete element.___internalEndHandler;
	            element.removeEventListener('webkitTransitionEnd', internalEndHandler, false);
	        }
	    }
	}

    function _onScrollTransitionEnd(e) {
	    try {
	        var targetElement, endHandler;
	        switch (e.type) {
	            case "webkitTransitionEnd":
	                targetElement = e.target;
	                if (targetElement) {
	                    clearTransition(targetElement);
	                    if (targetElement.___animationEndHandler) {
	                        endHandler = targetElement.___animationEndHandler;
	                        targetElement.___animationEndHandler = null;
	                        endHandler(e);
	                    }
	                }
	                break;
	        }
	    }
	    catch (ex) {
	        if (window.console && window.console.log) {
	            window.console.log("[ew219290] error: " +
					(ex.message ? ex.message : ex) +
						", event: " + e);
	        }
	    }
	    return true;
	}

});

(function (gcui) {
    (function (superpanel) {
        "use strict";
        var $ = jQuery;
        var superpanel_options = (function () {
            function superpanel_options() {
                this.mobileCSS = {
                    header: "ui-header ui-bar-a",
                    content: "ui-body-c",
                    stateDefault: "ui-btn-up-c",
                    stateHover: "ui-btn-down-c",
                    stateActive: "ui-btn-down-c"
                };
                this.initSelector = ":jqmData(role='superpanel')";
                this.allowResize = false;
                this.autoRefresh = false;
                this.animationOptions = {
                    queue: false,
                    disabled: false,
                    duration: 250,
                    easing: undefined
                };
                this.hScrollerActivating = null;
                this.hScroller = {
                    scrollBarPosition: "bottom",
                    scrollBarVisibility: "auto",
                    scrollMode: "scrollBar",
                    scrollValue: null,
                    scrollMax: 100,
                    scrollMin: 0,
                    scrollLargeChange: null,
                    scrollSmallChange: null,
                    scrollMinDragLength: 6,
                    increaseButtonPosition: null,
                    decreaseButtonPosition: null,
                    hoverEdgeSpan: 20,
                    firstStepChangeFix: 0
                };
                this.keyboardSupport = false;
                this.keyDownInterval = 100;
                this.mouseWheelSupport = true;
                this.bubbleScrollingEvent = true;
                this.resizableOptions = {
                    handles: "all",
                    helper: "ui-widget-content gcui-superpanel-helper"
                };
                this.resized = null;
                this.dragStop = null;
                this.painted = null;
                this.scrolling = null;
                this.scroll = null;
                this.scrolled = null;
                this.showRounder = true;
                this.vScrollerActivating = null;
                this.vScroller = {
                    scrollBarPosition: "right",
                    scrollBarVisibility: "auto",
                    scrollMode: "scrollBar",
                    scrollValue: null,
                    scrollMax: 100,
                    scrollMin: 0,
                    scrollLargeChange: null,
                    scrollSmallChange: null,
                    scrollMinDragLength: 6,
                    increaseButtonPosition: null,
                    decreaseButtonPosition: null,
                    hoverEdgeSpan: 20,
                    firstStepChangeFix: 0
                };
                this.customScrolling = false;
                this.listenContentScroll = false;
            }
            return superpanel_options;
        })();
        var superpanel = (function (_super) {
            __extends(superpanel, _super);
            function superpanel() {
                _super.apply(this, arguments);

            }
            return superpanel;
        })(gcui.gcuiWidget);
        superpanel.superpanel = superpanel;
        var superpanel_widget;
        if (!$.support.isTouchEnabled || !$.support.isTouchEnabled()) {
            var scrollerHandle = "gcui-superpanel-handle", hbarContainerCSS = "gcui-superpanel-hbarcontainer", vbarContainerCSS = "gcui-superpanel-vbarcontainer", activeCss = "", innerElementHtml = "<div class='gcui-superpanel-statecontainer'>" + "<div class='gcui-superpanel-contentwrapper'>" + "<div class='gcui-superpanel-templateouterwrapper'></div>" + "</div>" + "</div>", hbarHtml = "<div class='gcui-superpanel-hbarcontainer {header}'>" + "<div class='gcui-superpanel-handle {stateDefault} {cornerAll}'>" + "<span class='{icon} {iconVGripSolid}'></span></div>" + "<div class='gcui-superpanel-hbar-buttonleft {stateDefault} {cornerBL}'>" + "<span class='{icon} {iconArrowLeft}'></span></div>" + "<div class='gcui-superpanel-hbar-buttonright {stateDefault} {cornerBR}'>" + "<span class='{icon} {iconArrowRight}'></span></div>" + "</div>", vbarHtml = "<div class='gcui-superpanel-vbarcontainer {header}'>" + "<div class='gcui-superpanel-handle {stateDefault} {cornerAll}'>" + "<span class='{icon} {iconHGripSolid}'></span></div>" + "<div class='gcui-superpanel-vbar-buttontop {stateDefault} {cornerTR}'>" + "<span class='{icon} {iconArrowUp}'></span></div>" + "<div class='gcui-superpanel-vbar-buttonbottom {stateDefault} {cornerBR}'>" + "<span class='{icon} {iconArrowDown}'></span></div>" + "</div>", hButtons = "<div class='{stateDefault} gcui-superpanel-button " + "gcui-superpanel-buttonleft'><span class='{icon} {iconCaratLeft}'>" + "</span></div><div class='{stateDefault}" + " gcui-superpanel-button gcui-superpanel-buttonright'>" + "<span class='{icon} {iconCaratRight}'></span></div>", vButtons = "<div class='{stateDefault} gcui-superpanel-button" + " gcui-superpanel-buttontop'><span class='{icon} {iconCaratUp}'>" + "</span></div><div class='{stateDefault} gcui-superpanel-button" + " gcui-superpanel-buttonbottom'><span class='{icon} {iconCaratDown}'>" + " </span></div>";
            superpanel.prototype = $.extend(true, {}, $.Widget.prototype, {
                widgetEventPrefix: "gcuisuperpanel",
                _setOption: function (key, value) {
                    var self = this, o = self.options, f = self._fields(), hd = f.hbarDrag, vd = f.vbarDrag, r = f.resizer;
                    if (key === "animationOptions") {
                        value = $.extend(o.animationOptions, value);
                    } else if (key === "hScroller") {
                        if (value.scrollLargeChange !== undefined && value.scrollLargeChange !== null) {
                            self._autoHLarge = false;
                        }
                        value = $.extend(o.hScroller, value);
                        self.refresh();
                    } else if (key === "vScroller") {
                        if (value.scrollLargeChange !== undefined && value.scrollLargeChange !== null) {
                            self._autoVLarge = false;
                        }
                        value = $.extend(o.vScroller, value);
                        self.refresh();
                    } else if (key === "resizableOptions") {
                        value = $.extend(self.resizableOptions, value);
                    }
                    $.Widget.prototype._setOption.apply(self, arguments);
                    if ($.isPlainObject(value)) {
                        self.options[key] = value;
                    }
                    switch (key) {
                        case "allowResize":
                            self._initResizer();
                            break;
                        case "disabled":
                            if (value) {
                                if (hd !== undefined) {
                                    hd.draggable("disable");
                                }
                                if (vd !== undefined) {
                                    vd.draggable("disable");
                                }
                                if (r !== undefined) {
                                    r.resizable("disable");
                                }
                            } else {
                                if (hd !== undefined) {
                                    hd.draggable("enable");
                                }
                                if (vd !== undefined) {
                                    vd.draggable("enable");
                                }
                                if (r !== undefined) {
                                    r.resizable("enable");
                                }
                            }
                            break;
                        case "mouseWheelSupport":
                        case "keyboardSupport":
                            self._bindElementEvents(self, f, self.element, o);
                            break;
                    }
                    return self;
                },
                _create: function () {
                    var self = this, o = self.options;
                    o.vScroller.dir = "v";
                    o.hScroller.dir = "h";
                    self._initMarkup();
                    self.paintPanel();
                    self._initResizer();
                    if (self.options.disabled) {
                        self.disable();
                    }
                    self._detectAutoRefresh();
                    if (o.listenContentScroll) {
                        self._listenContentScroll();
                    }
                    if (self.element.is(":hidden") && self.element.addVisibilityObserver) {
                        self.element.addVisibilityObserver(function () {
                            if (self.element.removeVisibilityObserver) {
                                self.element.removeVisibilityObserver();
                            }
                            self.refresh();
                        }, "gcuisuperpanel");
                    }
                },
                _initMarkup: function () {
                    var css = this.options.uiCSS, reg = /\{(\w+?)\}/ig, callback = function (i, g1) {
                        return css[g1];
                    };
                    activeCss = css.stateActive;
                    hbarHtml = hbarHtml.replace(reg, callback);
                    vbarHtml = vbarHtml.replace(reg, callback);
                    hButtons = hButtons.replace(reg, callback);
                    vButtons = vButtons.replace(reg, callback);
                },
                _detectAutoRefresh: function () {
                    var self = this, panels = $.gcui.superpanel.panels;
                    if (panels === undefined) {
                        panels = [];
                        $.gcui.superpanel.panels = panels;
                    }
                    panels.push(self);
                    if (self.options.autoRefresh) {
                        if (!$.gcui.superpanel.setAutoRefreshInterval) {
                            $.gcui.superpanel.setAutoRefreshInterval = self._setAutoRefreshInterval;
                            $.gcui.superpanel.setAutoRefreshInterval();
                        }
                    }
                },
                _setAutoRefreshInterval: function () {
                    var interval = $.gcui.superpanel.autoRereshInterval, panels = $.gcui.superpanel.panels, intervalID;
                    intervalID = window.setInterval(function () {
                        window.clearInterval(intervalID);
                        var count = panels.length, toContinue = false, i, panel, mainElement, autoRefresh, ele, mark;
                        for (i = 0; i < count; i++) {
                            panel = panels[i];
                            mainElement = panel.element[0];
                            autoRefresh = panel.options.autoRefresh;
                            if (autoRefresh) {
                                toContinue = true;
                            }
                            ele = panel.getContentElement();
                            mark = panel._paintedMark;
                            if (autoRefresh && ele.is(":visible") && (mark === undefined || mark.width !== ele[0].offsetWidth || mark.height !== ele[0].offsetHeight || mark.mainWidth !== mainElement.offsetWidth || mark.mainHeight !== mainElement.offsetHeight)) {
                                panel.paintPanel();
                            }
                        }
                        if (toContinue) {
                            window.setTimeout($.gcui.superpanel.setAutoRefreshInterval, 0);
                        }
                    }, interval === undefined ? 500 : interval);
                },
                destroy: function () {
                    var self = this, f = self._fields(), ele = self.element, buttons, templateWrapper, css = self.options.uiCSS;
                    $.gcui.superpanel.panels = $.grep($.gcui.superpanel.panels, function (value) {
                        return value !== self;
                    }, false);
                    if (!f.initialized) {
                        return;
                    }
                    if (self._radiusKey) {
                        self.element.css(self._radiusKey, "");
                    }
                    if (f.intervalID !== undefined) {
                        window.clearInterval(f.intervalID);
                        f.intervalID = undefined;
                    }
                    if (f.resizer !== undefined) {
                        f.resizer.resizable("destroy");
                    }
                    if (f.hbarContainer !== undefined) {
                        f.hbarDrag.remove();
                        f.hbarContainer.unbind("." + self.widgetName);
                    }
                    if (f.vbarContainer !== undefined) {
                        f.vbarDrag.remove();
                        f.vbarContainer.unbind("." + self.widgetName);
                    }
                    ele.unbind("." + self.widgetName);
                    f.contentWrapper.unbind("." + self.widgetName);
                    buttons = f.stateContainer.find(">.gcui-superpanel-button");
                    buttons.unbind("." + self.widgetName);
                    templateWrapper = f.templateWrapper;
                    templateWrapper.contents().each(function (index, e) {
                        ele.append(e);
                    });
                    f.stateContainer.remove();
                    if (f.tabindex) {
                        ele.removeAttr("tabindex");
                    }
                    ele.removeClass([
                        "gcui-superpanel",
                        css.widget,
                        css.content,
                        css.cornerAll
                    ].join(' '));
                    $.Widget.prototype.destroy.apply(self, arguments);
                },
                _fields: function () {
                    var self = this, ele = self.element, key = self.widgetName + "-fields", d = self._fieldsStore;
                    if (d === undefined) {
                        d = {};
                        ele.data(key, d);
                        self._fieldsStore = d;
                    }
                    return d;
                },
                _hasMode: function (scroller, mode) {
                    var modes = scroller.scrollMode.split(",");
                    modes = $.map(modes, function (n) {
                        return $.trim(n).toLowerCase();
                    });
                    return $.inArray(mode.toLowerCase(), modes) > -1;
                },
                _bindElementEvents: function (self, f, ele, o) {
                    var hEdge = self._hasMode(o.hScroller, "edge"), vEdge = self._hasMode(o.vScroller, "edge"), wn = self.widgetName;
                    if (hEdge || vEdge) {
                        if (self._mousemoveBind === undefined) {
                            self._mousemoveBind = true;
                            ele.bind("mousemove." + wn, self, self._contentMouseMove);
                            ele.bind("mouseleave." + wn, null, function () {
                                self._clearInterval();
                            });
                        }
                    } else {
                        ele.unbind("mousemove", self._contentMouseMove);
                        self._mousemoveBind = undefined;
                    }
                    if (o.mouseWheelSupport) {
                        if (self._mouseWheelBind === undefined) {
                            self._mouseWheelBind = true;
                            ele.bind("mousewheel." + wn, self, self._panelMouseWheel);
                        }
                    } else {
                        ele.unbind("mousewheel", self._panelMouseWheel);
                        self._mouseWheelBind = undefined;
                    }
                    if (o.keyboardSupport) {
                        if (self._keyboardBind === undefined) {
                            self._keyboardBind = true;
                            ele.bind("keydown." + wn, self, self._panelKeyDown);
                        }
                    } else {
                        ele.unbind("keydown", self._panelKeyDown);
                        self._keyboardBind = undefined;
                    }
                },
                _dragStop: function (e, self, dir) {
                    var data = {
                        dragHandle: dir
                    };
                    self._trigger("dragStop", e, data);
                },
                _contentMouseMove: function (e) {
                    if (e.data.options.disabled) {
                        return;
                    }
                    var self = e.data, o = self.options, hScroller = o.hScroller, vScroller = o.vScroller, contentWrapper = $(e.currentTarget), f = self._fields(), hMode = self._hasMode(hScroller, "edge"), vMode = self._hasMode(vScroller, "edge"),
                        mousePagePosition = {
                            X: e.pageX,
                            Y: e.pageY
                        },
                        off = contentWrapper.offset(), left = off.left, top = off.top,
                        hEdge = hScroller.hoverEdgeSpan, vEdge = vScroller.hoverEdgeSpan,
                        innerHeight = contentWrapper.innerHeight(), innerWidth = contentWrapper.innerWidth(),
                        dir = "";

                    left = mousePagePosition.X - left;
                    top = mousePagePosition.Y - top;
                    self._clearInterval();
                    if (hMode) {
                        if (left < hEdge) {
                            dir = "left";
                        }
                        if (left > (innerWidth - hEdge)) {
                            dir = "right";
                        }
                    }
                    if (vMode) {
                        if (top < vEdge) {
                            dir = "top";
                        }
                        if (top > (innerHeight - vEdge)) {
                            dir = "bottom";
                        }
                    }
                    self._setScrollingInterval(f, dir, self, false);
                },
                _setScrollingInterval: function (f, dir, self, large) {
                    var o = self.options;
                    if (dir.length > 0) {
                        f.internalFuncID = window.setInterval(function () {
                            self._doScrolling(dir, self, large);
                        }, o.keyDownInterval);
                    }
                },
                _scrollButtonMouseOver: function (e) {
                    var self = e.data, o = self.options, button = $(e.currentTarget);
                    if (o.disabled) {
                        return;
                    }
                    if (!button.hasClass(o.uiCSS.stateDisabled)) {
                        button.bind("mouseout." + self.widgetName, self, self._buttonMouseOut).bind("mousedown." + self.widgetName, self, self._buttonMouseDown).bind("mouseup." + self.widgetName, self, self._buttonMouseUp).addClass(o.uiCSS.stateHover);
                        self._buttonScroll(button, self, "buttonshover");
                    }
                },
                _buttonScroll: function (button, self, mode) {
                    var dir = "", o = self.options, f = self._fields(), hMode = self._hasMode(o.hScroller, mode), vMode = self._hasMode(o.vScroller, mode);
                    if (button.hasClass("gcui-superpanel-buttonleft") && hMode) {
                        dir = "left";
                    } else if (button.hasClass("gcui-superpanel-buttonright") && hMode) {
                        dir = "right";
                    } else if (button.hasClass("gcui-superpanel-buttontop") && vMode) {
                        dir = "top";
                    } else if (button.hasClass("gcui-superpanel-buttonbottom") && vMode) {
                        dir = "bottom";
                    }
                    if (dir.length > 0) {
                        self._clearInterval();
                        self._doScrolling(dir, self, true);
                        self._setScrollingInterval(f, dir, self, true);
                    }
                },
                _listenContentScroll: function () {
                    var self = this, o = self.options, f = self._fields(), hbarContainer = f.hbarContainer, hbarDrag = f.hbarDrag, vbarContainer = f.vbarContainer, vbarDrag = f.vbarDrag, templateWrapper = f.templateWrapper, contentWrapper = f.contentWrapper, w = contentWrapper.width(), h = contentWrapper.height(), offset = templateWrapper && templateWrapper.offset(), ox = offset && offset.left, oy = offset && offset.top, contentWidth = f.contentWidth, contentHeight = f.contentHeight;
                    contentWrapper.bind("scroll", function (event) {
                        var pos = templateWrapper.position(), x = pos.left, y = pos.top;
                        contentWrapper.scrollTop(0).scrollLeft(0);
                        templateWrapper.css({
                            left: x,
                            top: y
                        });
                        if (x <= 0 && x > w - contentWidth) {
                            self._updateScrollValue(self.scrollPxToValue(-x, "h"), o.hScroller);
                            self._scrollDrag("h", hbarContainer, hbarDrag, true);
                        }
                        if (y <= 0 && y > h - contentHeight) {
                            self._updateScrollValue(self.scrollPxToValue(-y, "v"), o.vScroller);
                            self._scrollDrag("v", vbarContainer, vbarDrag, true);
                        }
                    });
                },
                _buttonMouseDown: function (e) {
                    var self = e.data, button = $(e.currentTarget), css = self.options.uiCSS;
                    if (self.options.disabled) {
                        return;
                    }
                    if (!button.hasClass(css.stateDisabled)) {
                        button.addClass(css.stateActive);
                        self._buttonScroll(button, self, "buttons");
                    }
                },
                _buttonMouseUp: function (e) {
                    var self = e.data, button = $(e.currentTarget);
                    button.removeClass(self.options.css.stateActive);
                    self._clearInterval();
                },
                _buttonMouseOut: function (e) {
                    var self = e.data, css = self.options.uiCSS, button = $(e.currentTarget);
                    button.unbind("mouseout", self._buttonMouseOut).unbind("mousedown", self._buttonMouseDown).unbind("mouseup", self._buttonMouseUp).removeClass(css.stateHover).removeClass(css.stateActive);
                    self._clearInterval();
                },
                _updateScrollValue: function (val, scroller) {
                    var ev = $.Event("scrollValueChanged");
                    scroller.scrollValue = val;
                    this._trigger("scrollValueChanged", ev, scroller);
                },
                _panelKeyDown: function (e) {
                    var self = e.data, o = self.options, shift = e.shiftKey, keycode = e.keyCode, kCode = gcui.getKeyCodeEnum();
                    if (!o.keyboardSupport || o.disabled) {
                        return;
                    }
                    if (keycode === kCode.LEFT) {
                        self._doScrolling("left", self, shift);
                    } else if (keycode === kCode.RIGHT) {
                        self._doScrolling("right", self, shift);
                    } else if (keycode === kCode.UP) {
                        self._doScrolling("top", self, shift);
                    } else if (keycode === kCode.DOWN) {
                        self._doScrolling("bottom", self, shift);
                    }
                    e.stopPropagation();
                    e.preventDefault();
                },
                _draggingInternal: function (data, self, scroller, originalElement) {
                    var dir = scroller.dir, h = dir === "h", key = h ? "left" : "top", left = data.position[key] - self._getScrollContainerPadding(key), track = self._getTrackLen(dir) - originalElement[h ? "outerWidth" : "outerHeight"](true), proportion = left / track, topValue = (scroller.scrollMax - scroller.scrollLargeChange + 1), v = proportion * topValue, arg, scrollValue, val;
                    if (v < scroller.scrollMin) {
                        v = scroller.scrollMin;
                    }
                    if (v > topValue) {
                        v = topValue;
                    }
                    arg = {
                        oldValue: scroller.scrollValue,
                        newValue: v,
                        dir: dir
                    };
                    if (!self._scrolling(true, self, arg)) {
                        return;
                    }
                    if (self.customScroll) {
                        val = Math.abs(self.customScroll);
                        scrollValue = self.scrollPxToValue(val, scroller.dir);
                    }
                    self._updateScrollValue(scrollValue || v, scroller);
                    self.customScroll = undefined;
                    self._setDragAndContentPosition(true, false, dir, "dragging");
                },
                _dragging: function (e, data, self) {
                    var o = self.options, originalElement = $(e.target), p = originalElement.parent();
                    if (p.hasClass(hbarContainerCSS)) {
                        self._draggingInternal(data, self, o.hScroller, originalElement);
                    } else {
                        self._draggingInternal(data, self, o.vScroller, originalElement);
                    }
                },
                _panelMouseWheel: function (e, delta) {
                    var self = e.data, o = self.options, originalElement, dir = "", onHbar, hScroller = o.hScroller, vScroller = o.vScroller, scrollEnd;
                    if (!o.mouseWheelSupport || o.disabled) {
                        return;
                    }
                    originalElement = $(e.srcElement || e.originalEvent.target);
                    onHbar = originalElement.closest("." + hbarContainerCSS, self.element).size() > 0;
                    if (delta > 0) {
                        dir = onHbar ? "left" : "top";
                    } else {
                        dir = onHbar ? "right" : "bottom";
                    }
                    if (dir.length > 0) {
                        self._doScrolling(dir, self);
                    }
                    scrollEnd = false;
                    if (dir === "left") {
                        scrollEnd = !self.hNeedScrollBar || Math.abs(hScroller.scrollValue - hScroller.scrollMin) < 0.001;
                    }
                    if (dir === "right") {
                        scrollEnd = !self.hNeedScrollBar || Math.abs(hScroller.scrollValue - (hScroller.scrollMax - self._getHScrollBarLargeChange() + 1)) < 0.001;
                    }
                    if (dir === "top") {
                        scrollEnd = !self.vNeedScrollBar || Math.abs(vScroller.scrollValue - vScroller.scrollMin) < 0.001;
                    }
                    if (dir === "bottom") {
                        scrollEnd = !self.vNeedScrollBar || Math.abs(vScroller.scrollValue - (vScroller.scrollMax - self._getVScrollBarLargeChange() + 1)) < 0.001;
                    }
                    if (!scrollEnd || !o.bubbleScrollingEvent || dir === "left" || dir === "right") {
                        e.stopPropagation();
                        e.preventDefault();
                    }
                },
                _documentMouseUp: function (e) {
                    var self = e.data.self, ele = e.data.ele;
                    ele.removeClass(self.options.uiCSS.stateActive);
                    self._clearInterval();
                    $(document).unbind("mouseup", self._documentMouseUp);
                },
                _scrollerMouseOver: function (e) {
                    var self = e.data, o = self.options, originalElement, ele = null, addhover = false;
                    if (o.disabled) {
                        return;
                    }
                    originalElement = $(e.srcElement || e.originalEvent.target || e.originalEvent.srcElement);
                    if (originalElement.hasClass(o.uiCSS.stateDefault)) {
                        ele = originalElement;
                        addhover = true;
                    } else if (originalElement.parent().hasClass(o.uiCSS.stateDefault)) {
                        ele = originalElement.parent();
                        addhover = true;
                    } else if (originalElement.hasClass(vbarContainerCSS) || originalElement.hasClass(hbarContainerCSS)) {
                        ele = originalElement;
                    }
                    if (ele) {
                        if (addhover) {
                            ele.addClass(o.uiCSS.stateHover);
                        }
                        ele.bind("mouseout." + self.widgetName, self, self._elementMouseOut);
                        ele.bind("mousedown." + self.widgetName, self, self._elementMouseDown);
                        ele.bind("mouseup." + self.widgetName, self, self._elementMouseUp);
                    }
                },
                _elementMouseUp: function (e) {
                    var ele = $(e.currentTarget);
                    ele.removeClass(activeCss);
                },
                _elementMouseDown: function (e) {
                    var ele = $(e.currentTarget), self = e.data, hbarDrag, vbarDrag, scrollDirection = "", large = false, active = false, pos, pos2, f;
                    if (self.options.disabled || e.which !== 1) {
                        return;
                    }
                    if (ele.hasClass("gcui-superpanel-vbar-buttontop")) {
                        scrollDirection = "top";
                        active = true;
                    } else if (ele.hasClass("gcui-superpanel-vbar-buttonbottom")) {
                        scrollDirection = "bottom";
                        active = true;
                    } else if (ele.hasClass("gcui-superpanel-hbar-buttonleft")) {
                        scrollDirection = "left";
                        active = true;
                    } else if (ele.hasClass("gcui-superpanel-hbar-buttonright")) {
                        scrollDirection = "right";
                        active = true;
                    } else if (ele.hasClass(scrollerHandle)) {
                        ele.addClass(activeCss);
                        return;
                    } else if (ele.hasClass(hbarContainerCSS)) {
                        hbarDrag = ele.find("." + scrollerHandle);
                        pos = hbarDrag.offset();
                        if (e.pageX < pos.left) {
                            scrollDirection = "left";
                        } else {
                            scrollDirection = "right";
                        }
                        large = true;
                    } else if (ele.hasClass(vbarContainerCSS)) {
                        vbarDrag = ele.find("." + scrollerHandle);
                        pos2 = vbarDrag.offset();
                        if (e.pageY < pos2.top) {
                            scrollDirection = "top";
                        } else {
                            scrollDirection = "bottom";
                        }
                        large = true;
                    }
                    self._clearInterval();
                    self._doScrolling(scrollDirection, self, large);
                    f = self._fields();
                    self._setScrollingInterval(f, scrollDirection, self, large);
                    if (active) {
                        ele.addClass(activeCss);
                    }
                    $(document).bind("mouseup." + self.widgetName, {
                        self: self,
                        ele: ele
                    }, self._documentMouseUp);
                },
                doScrolling: function (dir, large) {
                    this._doScrolling(dir, this, large);
                },
                _setScrollerValue: function (dir, scroller, smallChange, largeChange, isAdd, isLarge, self) {
                    var vMin = scroller.scrollMin, change = isLarge ? largeChange : smallChange, value = scroller.scrollValue, t = 0, vTopValue, firstStepChangeFix, data, scrollValue, val;
                    if (!value) {
                        value = vMin;
                    }
                    if (isAdd) {
                        vTopValue = scroller.scrollMax - largeChange + 1;
                        if (Math.abs(value - vTopValue) < 0.001) {
                            self._clearInterval();
                            return false;
                        }
                        firstStepChangeFix = scroller.firstStepChangeFix;
                        t = value + change;
                        if (!isLarge && Math.abs(value - vMin) < 0.0001 && !isNaN(firstStepChangeFix)) {
                            t += firstStepChangeFix;
                        }
                        if (t > vTopValue) {
                            t = vTopValue;
                        }
                    } else {
                        if (Math.abs(value - vMin) < 0.001) {
                            self._clearInterval();
                            return false;
                        }
                        t = value - change;
                        if (t < 0) {
                            t = vMin;
                        }
                    }
                    data = {
                        oldValue: scroller.scrollValue,
                        newValue: t,
                        direction: dir,
                        dir: scroller.dir
                    };
                    if (!self._scrolling(true, self, data)) {
                        return false;
                    }
                    if (self.customScroll) {
                        val = Math.abs(self.customScroll);
                        scrollValue = self.scrollPxToValue(val, scroller.dir);
                    }
                    self._updateScrollValue(scrollValue || t, scroller);
                    self.customScroll = undefined;
                    return true;
                },
                _doScrolling: function (dir, self, large) {
                    var o = self.options, vScroller = o.vScroller, hScroller = o.hScroller, vSmall = self._getVScrollBarSmallChange(), vLarge = self._getVScrollBarLargeChange(), hLarge = self._getHScrollBarLargeChange(), hSmall = self._getHScrollBarSmallChange();
                    if (dir === "top" || dir === "bottom") {
                        if (!self._setScrollerValue(dir, vScroller, vSmall, vLarge, dir === "bottom", large, self)) {
                            return;
                        }
                        dir = "v";
                    } else if (dir === "left" || dir === "right") {
                        if (!self._setScrollerValue(dir, hScroller, hSmall, hLarge, dir === "right", large, self)) {
                            return;
                        }
                        dir = "h";
                    }
                    self._setDragAndContentPosition(true, true, dir);
                },
                _disableButtonIfNeeded: function (self) {
                    var f = self._fields(), o = self.options, buttonLeft = f.buttonLeft, buttonRight = f.buttonRight, buttonTop = f.buttonTop, buttonBottom = f.buttonBottom, hLargeChange, hMax, hValue, hScrollMin, vLargeChange, vMax, vValue, vScrollMin;
                    if (f.intervalID > 0) {
                        window.clearInterval(f.intervalID);
                    }
                    if (buttonLeft !== undefined) {
                        hLargeChange = self._getHScrollBarLargeChange();
                        hMax = o.hScroller.scrollMax - hLargeChange + 1;
                        hValue = o.hScroller.scrollValue;
                        hScrollMin = o.hScroller.scrollMin;
                        if (hValue === undefined) {
                            hValue = hScrollMin;
                        }
                        if (Math.abs(hValue - hScrollMin) < 0.001 || !f.hScrolling) {
                            buttonLeft.addClass(o.uiCSS.stateDisabled);
                        } else {
                            buttonLeft.removeClass(o.uiCSS.stateDisabled);
                        }
                        if (Math.abs(hValue - hMax) < 0.001 || !f.hScrolling) {
                            buttonRight.addClass(o.uiCSS.stateDisabled);
                        } else {
                            buttonRight.removeClass(o.uiCSS.stateDisabled);
                        }
                    }
                    if (buttonTop !== undefined) {
                        vLargeChange = self._getVScrollBarLargeChange();
                        vMax = o.vScroller.scrollMax - vLargeChange + 1;
                        vValue = o.vScroller.scrollValue;
                        vScrollMin = o.vScroller.scrollMin;
                        if (vValue === undefined) {
                            vValue = vScrollMin;
                        }
                        if (Math.abs(vValue - vScrollMin) < 0.001 || !f.vScrolling) {
                            buttonTop.addClass(o.uiCSS.stateDisabled);
                        } else {
                            buttonTop.removeClass(o.uiCSS.stateDisabled);
                        }
                        if (Math.abs(vValue - vMax) < 0.001 || !f.vScrolling) {
                            buttonBottom.addClass(o.uiCSS.stateDisabled);
                        } else {
                            buttonBottom.removeClass(o.uiCSS.stateDisabled);
                        }
                    }
                },
                _clearInterval: function () {
                    var f = this._fields(), intervalID = f.internalFuncID;
                    if (intervalID > 0) {
                        window.clearInterval(intervalID);
                        f.internalFuncID = -1;
                    }
                },
                _elementMouseOut: function (event) {
                    var ele = $(event.currentTarget), self = event.data;
                    ele.unbind("mouseout", self._elementMouseOut);
                    ele.unbind("mousedown", self._elementMouseDown);
                    ele.unbind("mouseup", self._elementMouseUp);
                    ele.removeClass(self.options.uiCSS.stateHover);
                },
                _getScrollOffset: function (child1) {
                    var child = $(child1), f = this._fields(), cWrapper = f.contentWrapper, tempWrapper = f.templateWrapper, childOffset, templateOffset, cWrapperOffset, tDistance, bDistance, lDistance, rDistance, result = {
                        left: null,
                        top: null
                    };
                    if (child.length === 0) {
                        return result;
                    }
                    childOffset = child.offset();
                    templateOffset = tempWrapper.offset();
                    childOffset.leftWidth = childOffset.left + child.outerWidth();
                    childOffset.topHeight = childOffset.top + child.outerHeight();
                    cWrapperOffset = cWrapper.offset();
                    cWrapperOffset.leftWidth = cWrapperOffset.left + cWrapper.outerWidth();
                    cWrapperOffset.topHeight = cWrapperOffset.top + cWrapper.outerHeight();
                    lDistance = childOffset.left - templateOffset.left;
                    if (childOffset.left < cWrapperOffset.left) {
                        result.left = lDistance;
                    } else if (childOffset.leftWidth > cWrapperOffset.leftWidth) {
                        rDistance = childOffset.leftWidth - templateOffset.left - cWrapper.innerWidth();
                        if (lDistance < rDistance) {
                            result.left = lDistance;
                        } else {
                            result.left = rDistance;
                        }
                    }
                    tDistance = childOffset.top - templateOffset.top;
                    if (childOffset.top < cWrapperOffset.top) {
                        result.top = tDistance;
                    } else if (childOffset.topHeight > cWrapperOffset.topHeight) {
                        bDistance = childOffset.topHeight - templateOffset.top - cWrapper.innerHeight();
                        if (tDistance < bDistance) {
                            result.top = tDistance;
                        } else {
                            result.top = bDistance;
                        }
                    }
                    return result;
                },
                _scrollDrag: function (dir, hbarContainer, hbarDrag, fireScrollEvent) {
                    var self = this, o = self.options, v = dir === "v", scroller = v ? o.vScroller : o.hScroller, hMin = scroller.scrollMin, hMax = scroller.scrollMax, hValue = scroller.scrollValue === undefined ? hMin : (scroller.scrollValue - hMin), hLargeChange = self._getLargeChange(dir), max = hMax - hMin - hLargeChange + 1, dragleft = -1, track, drag, padding;
                    if (hValue > max) {
                        hValue = max;
                    }
                    if (hbarContainer !== undefined) {
                        track = self._getTrackLen(dir);
                        drag = hbarDrag[v ? "outerHeight" : "outerWidth"]();
                        padding = self._getScrollContainerPadding(v ? "top" : "left");
                        dragleft = (hValue / max) * (track - drag) + padding;
                    }
                    if (dragleft >= 0) {
                        hbarDrag.css(v ? "top" : "left", dragleft + "px");
                    }
                    self._scrollEnd(fireScrollEvent, self, dir);
                },
                needToScroll: function (child) {
                    var offset = this._getScrollOffset(child);
                    return offset.top !== null || offset.left !== null;
                },
                scrollChildIntoView: function (child) {
                    var offset = this._getScrollOffset(child), left = offset.left, top = offset.top;
                    if (left !== null) {
                        this.hScrollTo(left);
                    }
                    if (top !== null) {
                        this.vScrollTo(top);
                    }
                },
                hScrollTo: function (x, isScrollValue) {
                    var o = this.options;
                    this._updateScrollValue(!!isScrollValue ? x : this.scrollPxToValue(x, "h"), o.hScroller);
                    this._setDragAndContentPosition(true, true, "h", "nonestop");
                },
                vScrollTo: function (y, isScrollValue) {
                    var o = this.options;
                    this._updateScrollValue(!!isScrollValue ? y : this.scrollPxToValue(y, "v"), o.vScroller);
                    this._setDragAndContentPosition(true, true, "v", "nonestop");
                },
                scrollPxToValue: function (px, dir) {
                    var o = this.options, m = (dir === "h" ? "outerWidth" : "outerHeight"), m1 = (dir === "h" ? "contentWidth" : "contentHeight"), scroller = (dir === "h" ? "hScroller" : "vScroller"), f = this._fields(), cWrapper = f.contentWrapper, size = f[m1], contentHeight = cWrapper[m](), vMin = o[scroller].scrollMin, vMax = o[scroller].scrollMax, vRange = vMax - vMin, vLargeChange = (dir === "h" ? this._getHScrollBarLargeChange() : this._getVScrollBarLargeChange()), maxv = vRange - vLargeChange + 1, ret = maxv * (px / (size - contentHeight));
                    if (ret < vMin) {
                        ret = vMin;
                    }
                    if (ret > maxv) {
                        ret = maxv;
                    }
                    return ret;
                },
                scrollTo: function (x, y, isScrollValue) {
                    this.hScrollTo(x, isScrollValue);
                    this.vScrollTo(y, isScrollValue);
                },
                refresh: function () {
                    this.paintPanel();
                },
                paintPanel: function (unfocus) {
                    var self = this, ele = self.element, focused, o, f, templateWrapper;
                    if (ele.is(":visible")) {
                        focused = typeof document.activeElement != 'unknown' ? document.activeElement : undefined;
                        o = self.options;
                        f = self._fields();
                        if (!f.initialized) {
                            self._initialize(f, ele, self);
                        }
                        self._resetLargeChange(self, f, o);
                        self._bindElementEvents(self, f, ele, o);
                        templateWrapper = f.templateWrapper;
                        templateWrapper.css({
                            "float": "left",
                            left: "0px",
                            top: "0px",
                            width: "auto",
                            height: "auto"
                        });
                        templateWrapper.hide();
                        templateWrapper.show();
                        f.contentWidth = templateWrapper.width();
                        f.contentHeight = templateWrapper.height();
                        templateWrapper.css("float", "");
                        self._setRounder(self, ele);
                        self._setInnerElementsSize(f, ele);
                        if (self._testScroll(self, f, o) === false) {
                            return false;
                        }
                        self._initScrollBars(self, f, o);
                        self._initScrollButtons(self, f, o);
                        self._trigger("painted");
                        self._paintedMark = {
                            date: new Date(),
                            mainWidth: ele[0].offsetWidth,
                            mainHeight: ele[0].offsetHeight,
                            width: f.contentWidth,
                            height: f.contentWidth
                        };
                        if (focused !== undefined && !unfocus) {
                            $(focused).focus();
                        }
                        return true;
                    }
                    return false;
                },
                _resetLargeChange: function (self, f, o) {
                    var handle;
                    if (self._autoVLarge) {
                        o.vScroller.scrollLargeChange = null;
                    }
                    if (self._autoHLarge) {
                        o.hScroller.scrollLargeChange = null;
                    }
                    f.vTrackLen = undefined;
                    f.hTrackLen = undefined;
                    if (f.vbarContainer) {
                        handle = f.vbarContainer.children("." + scrollerHandle + ":eq(0)");
                        handle.detach();
                        f.vbarContainer.remove();
                        f.vbarContainer = undefined;
                    }
                    if (f.hbarContainer) {
                        handle = f.hbarContainer.children("." + scrollerHandle + ":eq(0)");
                        handle.detach();
                        f.hbarContainer.remove();
                        f.hbarContainer = undefined;
                    }
                },
                _initialize: function (f, ele, self) {
                    var css = self.options.uiCSS;
                    f.initialized = true;
                    ele.addClass([
                        "gcui-superpanel",
                        css.widget,
                        css.content
                    ].join(' '));
                    f.oldHeight = ele.css("height");
                    var old = ele.css("overflow");
                    ele.css("overflow", "");
                    ele.height(ele.height());
                    ele.css("overflow", old);
                    self._createAdditionalDom(self, f, ele);
                },
                getContentElement: function () {
                    return this._fields().templateWrapper;
                },
                _setButtonPosition: function (self, o, scroller, dir, target, f, state) {
                    var h = dir === "h", mouseoverkey = "mouseover." + self.widgetName, decKey = h ? "buttonLeft" : "buttonTop", incKey = h ? "buttonRight" : "buttonBottom", decButton = f[decKey], incButton = f[incKey], html, buttons, defaultPosition;
                    if (self._hasMode(scroller, "buttons") || self._hasMode(scroller, "buttonshover")) {
                        html = h ? hButtons : vButtons;
                        if (decButton === undefined) {
                            buttons = $(html).appendTo(state);
                            buttons.bind(mouseoverkey, self, self._scrollButtonMouseOver);
                            f[decKey] = decButton = state.children(h ? ".gcui-superpanel-buttonleft" : ".gcui-superpanel-buttontop");
                            f[incKey] = incButton = state.children(h ? ".gcui-superpanel-buttonright" : ".gcui-superpanel-buttonbottom");
                        }
                        defaultPosition = {
                            my: h ? "left" : "top",
                            of: target,
                            at: h ? "left" : "top",
                            collision: "none"
                        };
                        $.extend(defaultPosition, scroller.decreaseButtonPosition);
                        decButton.position(defaultPosition);
                        defaultPosition = {
                            my: h ? "right" : "bottom",
                            of: target,
                            at: h ? "right" : "bottom",
                            collision: "none"
                        };
                        $.extend(defaultPosition, scroller.increaseButtonPosition);
                        incButton.position(defaultPosition);
                    } else if (decButton !== undefined) {
                        decButton.remove();
                        incButton.remove();
                        f[decKey] = f[incKey] = undefined;
                    }
                },
                _initScrollButtons: function (self, f, o) {
                    var a = f.contentWrapper, state = f.stateContainer;
                    self._setButtonPosition(self, o, o.hScroller, "h", a, f, state);
                    self._setButtonPosition(self, o, o.vScroller, "v", a, f, state);
                },
                _getVScrollBarSmallChange: function () {
                    var o = this.options, va;
                    if (!o.vScroller.scrollSmallChange) {
                        va = this._getVScrollBarLargeChange();
                        o.vScroller.scrollSmallChange = va / 2;
                    }
                    return o.vScroller.scrollSmallChange;
                },
                _getVScrollBarLargeChange: function () {
                    return this._getLargeChange("v");
                },
                _getLargeChange: function (dir) {
                    var self = this, o = self.options, f = self._fields(), v = dir === "v", scroller = v ? o.vScroller : o.hScroller, clientKey = v ? "innerHeight" : "innerWidth", offsetKey = v ? "contentHeight" : "contentWidth", autoKey = v ? "_autoVLarge" : "_autoHLarge", hMax, hMin, hRange, content, contentWidth, wrapperWidth, percent, large;
                    if (scroller.scrollLargeChange) {
                        return scroller.scrollLargeChange;
                    }
                    hMax = scroller.scrollMax;
                    hMin = scroller.scrollMin;
                    hRange = hMax - hMin;
                    content = f.contentWrapper;
                    contentWidth = content[clientKey]();
                    wrapperWidth = f[offsetKey];
                    percent = contentWidth / (wrapperWidth - contentWidth);
                    large = ((hRange + 1) * percent) / (1 + percent);
                    if (isNaN(large)) {
                        large = 0;
                    }
                    scroller.scrollLargeChange = large;
                    self[autoKey] = true;
                    return scroller.scrollLargeChange;
                },
                _getHScrollBarSmallChange: function () {
                    var o = this.options, va;
                    if (!o.hScroller.scrollSmallChange) {
                        va = this._getHScrollBarLargeChange();
                        o.hScroller.scrollSmallChange = va / 2;
                    }
                    return o.hScroller.scrollSmallChange;
                },
                _getHScrollBarLargeChange: function () {
                    return this._getLargeChange("h");
                },
                _initScrollBars: function (self, f, o) {
                    var hScroller = o.hScroller, hMax = hScroller.scrollMax, hMin = hScroller.scrollMin, hRange = hMax - hMin, vScroller = o.vScroller, vMax = vScroller.scrollMax, vMin = vScroller.scrollMin, vRange = vMax - vMin, hbarDrag = f.hbarDrag, vbarDrag = f.vbarDrag, hLargeChange, track, dragLen, difference, icon, vLargeChange, track1, dragLen1, difference1, icon1;
                    if (self.hNeedScrollBar && hbarDrag.is(":visible")) {
                        hLargeChange = self._getHScrollBarLargeChange();
                        track = self._getTrackLen("h");
                        dragLen = self._getDragLength(hRange, hLargeChange, track, o.hScroller.scrollMinDragLength);
                        hbarDrag.width(dragLen);
                        difference = hbarDrag.outerWidth(true) - hbarDrag.width();
                        hbarDrag.width(dragLen - difference);
                        icon = hbarDrag.children("span");
                        icon.css("margin-left", (hbarDrag.width() - icon[0].offsetWidth) / 2);
                        if (track <= hbarDrag.outerWidth(true)) {
                            hbarDrag.hide();
                        } else {
                            hbarDrag.show();
                        }
                        if (self._isDragging == true) {
                            $(document).trigger("mouseup");
                            self._isDragging = false;
                        }
                    }
                    if (self.vNeedScrollBar && vbarDrag.is(":visible")) {
                        vLargeChange = self._getVScrollBarLargeChange();
                        track1 = self._getTrackLen("v");
                        dragLen1 = self._getDragLength(vRange, vLargeChange, track1, o.vScroller.scrollMinDragLength);
                        vbarDrag.height(dragLen1);
                        difference1 = vbarDrag.outerHeight(true) - vbarDrag.height();
                        vbarDrag.height(dragLen1 - difference1);
                        icon1 = vbarDrag.children("span");
                        icon1.css("margin-top", (vbarDrag.height() - icon1[0].offsetHeight) / 2);
                        if (track1 <= vbarDrag.outerHeight(true)) {
                            vbarDrag.hide();
                        } else {
                            vbarDrag.show();
                        }
                        if (self._isDragging == true) {
                            $(document).trigger("mouseup");
                            self._isDragging = false;
                        }
                    }
                    self._setDragAndContentPosition(false, false, "both");
                },
                _getTrackLen: function (dir) {
                    var self = this, f = self._fields(), key = dir + "TrackLen", hbarContainer = f.hbarContainer, vbarContainer = f.vbarContainer, track = 0, padding = 0;
                    if (f[key] !== undefined) {
                        return f[key];
                    }
                    if (dir === "h") {
                        padding = self._getScrollContainerPadding("h");
                        track = hbarContainer.innerWidth();
                    }
                    if (dir === "v") {
                        padding = self._getScrollContainerPadding("v");
                        track = vbarContainer.innerHeight();
                    }
                    f[key] = (track - padding);
                    return f[key];
                },
                _getScrollContainerPadding: function (paddingType) {
                    var self = this, f = self._fields(), padding = 0, container, key;
                    if (paddingType === "h") {
                        padding = self._getScrollContainerPadding("left") + self._getScrollContainerPadding("right");
                    } else if (paddingType === "v") {
                        padding = self._getScrollContainerPadding("top") + self._getScrollContainerPadding("bottom");
                    } else {
                        if (paddingType === "left" || paddingType === "right") {
                            container = f.hbarContainer;
                        } else {
                            container = f.vbarContainer;
                        }
                        key = paddingType + "Padding";
                        if (f[key] !== undefined) {
                            padding = f[key];
                            return padding;
                        }
                        if (container && container.css) {
                            padding = parseFloat(container.css("padding-" + paddingType));
                        }
                        f[key] = padding;
                    }
                    return padding;
                },
                _triggerScroll: function (contentLeft, dir, contentAnimationOptions) {
                    var data = {
                        position: contentLeft,
                        dir: dir,
                        animationOptions: contentAnimationOptions
                    };
                    this._trigger("scroll", null, data);
                },
                _contentDragAnimate: function (dir, animated, hbarContainer, hbarDrag, stop, fireScrollEvent, dragging) {
                    var self = this, o = self.options, v = dir === "v", scroller = v ? o.vScroller : o.hScroller, tempKey = v ? "outerHeight" : "outerWidth", wrapKey = v ? "innerHeight" : "innerWidth", contentKey = v ? "contentHeight" : "contentWidth", paddingKey = v ? "top" : "left", hMin = scroller.scrollMin, hMax = scroller.scrollMax, hRange = hMax - hMin, hValue = scroller.scrollValue === undefined ? hMin : (scroller.scrollValue - hMin), hLargeChange = self._getLargeChange(dir), max = hRange - hLargeChange + 1, f = self._fields(), cWrapper = f.contentWrapper, tempWrapper = f.templateWrapper, contentLeft, dragleft, track, drag, r, padding, dragAnimationOptions, properties, contentAnimationOptions, userComplete, properties1, key;
                    if (hValue > max) {
                        hValue = max;
                    }
                    contentLeft = (f[contentKey] - cWrapper[wrapKey]()) * (hValue / max);
                    if (Math.abs(contentLeft) < 0.001) {
                        contentLeft = 0;
                    }
                    contentLeft = Math.round(contentLeft);
                    dragleft = -1;
                    if (hbarContainer !== undefined) {
                        if (animated && hbarDrag.is(":animated") && stop !== "nonestop") {
                            hbarDrag.stop(true, false);
                        }
                        track = self._getTrackLen(dir);
                        drag = hbarDrag[tempKey](true);
                        r = track - drag;
                        padding = self._getScrollContainerPadding(paddingKey);
                        dragleft = (hValue / max) * r + padding;
                    }
                    if (animated && o.animationOptions && !o.animationOptions.disabled) {
                        if (dragleft >= 0 && dragging !== "dragging") {
                            dragAnimationOptions = $.extend({
                            }, o.animationOptions);
                            dragAnimationOptions.complete = undefined;
                            if (v) {
                                properties = {
                                    top: dragleft
                                };
                            } else {
                                properties = {
                                    left: dragleft
                                };
                            }
                            hbarDrag.animate(properties, dragAnimationOptions);
                        }
                        contentAnimationOptions = $.extend({
                        }, o.animationOptions);
                        userComplete = o.animationOptions.complete;
                        contentAnimationOptions.complete = function () {
                            self._scrollEnd(fireScrollEvent, self, dir);
                            if ($.isFunction(userComplete)) {
                                userComplete(arguments);
                            }
                        };
                        if (animated && tempWrapper.is(":animated") && stop !== "nonestop") {
                            tempWrapper.stop(true, false);
                        }
                        if (v) {
                            properties1 = {
                                top: -contentLeft
                            };
                        } else {
                            properties1 = {
                                left: -contentLeft
                            };
                        }
                        if (!o.customScrolling) {
                            tempWrapper.animate(properties1, contentAnimationOptions);
                        } else {
                            self._scrollEnd(fireScrollEvent, self, dir, hValue);
                        }
                        self._triggerScroll(contentLeft, dir, contentAnimationOptions);
                    } else if (scroller.scrollBarVisibility !== "hidden") {
                        key = v ? "top" : "left";
                        if (dragleft >= 0 && dragging !== "dragging") {
                            hbarDrag[0].style[key] = dragleft + "px";
                        }
                        if (!o.customScrolling) {
                            tempWrapper[0].style[key] = -contentLeft + "px";
                        }
                        self._triggerScroll(contentLeft, dir);
                        self._scrollEnd(fireScrollEvent, self, dir, hValue);
                    }
                },
                _setDragAndContentPosition: function (fireScrollEvent, animated, dir, stop, dragging) {
                    var self = this, f = self._fields(), hbarContainer = f.hbarContainer, hbarDrag = f.hbarDrag, vbarContainer = f.vbarContainer, vbarDrag = f.vbarDrag;
                    if ((dir === "both" || dir === "h") && f.hScrolling) {
                        self._contentDragAnimate("h", animated, hbarContainer, hbarDrag, stop, fireScrollEvent, dragging);
                    }
                    if ((dir === "both" || dir === "v") && f.vScrolling) {
                        self._contentDragAnimate("v", animated, vbarContainer, vbarDrag, stop, fireScrollEvent, dragging);
                    }
                    if (f.intervalID > 0) {
                        window.clearInterval(f.intervalID);
                    }
                    f.intervalID = window.setInterval(function () {
                        self._disableButtonIfNeeded(self);
                    }, 500);
                },
                _scrolling: function (fireEvent, self, d) {
                    var r = true;
                    if (fireEvent) {
                        d.beforePosition = self.getContentElement().position();
                        self._beforePosition = d.beforePosition;
                        r = self._trigger("scrolling", null, d);
                        self.customScroll = d.customScroll;
                    }
                    return r;
                },
                _scrollEnd: function (fireEvent, self, dir, newValue) {
                    if (fireEvent) {
                        window.setTimeout(function () {
                            var content = self.getContentElement(), after, d;
                            if (!content.is(":visible")) {
                                return;
                            }
                            after = self.getContentElement().position();
                            d = {
                                dir: dir,
                                beforePosition: self._beforePosition,
                                afterPosition: after
                            };
                            if (!isNaN(newValue)) {
                                d.newValue = newValue;
                            }
                            self._trigger("scrolled", null, d);
                        }, 0);
                    }
                },
                _getDragLength: function (range, largeChange, track, min) {
                    var divide = range / largeChange, dragLength = track / divide, minidrag = min;
                    if (dragLength < minidrag) {
                        dragLength = minidrag;
                    } else if ((dragLength + 1) >= track) {
                        dragLength = track - 1;
                    }
                    return Math.round(dragLength);
                },
                _needScrollbar: function (scroller, needscroll) {
                    var scrollbarMode = this._hasMode(scroller, "scrollbar"), barVisibility = scroller.scrollBarVisibility, needScrollBar = scrollbarMode && (barVisibility === "visible" || (barVisibility === "auto" && needscroll));
                    return needScrollBar;
                },
                _bindBarEvent: function (barContainer, barDrag, dir) {
                    var self = this;
                    barContainer.bind("mouseover." + self.widgetName, self, self._scrollerMouseOver);
                    if (!$.fn.draggable) {
                        return;
                    }
                    barDrag.draggable({
                        axis: dir === "h" ? "x" : "y",
                        start: function (e, data) {
                            if (self.options.disabled) {
                                return false;
                            }
                            self._isDragging = true;
                        },
                        drag: function (e, data) {
                            self._dragging(e, data, self);
                        },
                        containment: "parent",
                        stop: function (e) {
                            self._dragStop(e, self, dir);
                            $(e.target).removeClass(activeCss);
                            self._isDragging = false;
                        }
                    });
                },
                _createBarIfNeeded: function (hNeedScrollBar, scrollerWrapper, dir, html, content) {
                    if (hNeedScrollBar) {
                        var self = this, data, f = self._fields(), strBarContainer = dir + "barContainer", strBarDrag = dir + "barDrag", hbar = dir === "h", contentLength = content[hbar ? "innerHeight" : "innerWidth"](), c = f[strBarContainer] = $(html), targetBarLen, d;
                        scrollerWrapper.append(c);
                        targetBarLen = c[0][hbar ? "offsetHeight" : "offsetWidth"];
                        contentLength = contentLength - targetBarLen;
                        data = {
                            direction: hbar ? "horizontal" : "vertical",
                            targetBarLen: targetBarLen,
                            contentLength: contentLength
                        };
                        if (self._trigger(hbar ? "hScrollerActivating" : "vScrollerActivating", null, data) === false) {
                            return false;
                        }
                        d = f[strBarDrag] = c.find("." + scrollerHandle);
                        self._bindBarEvent(c, d, dir);
                        content[hbar ? "height" : "width"](contentLength);
                    }
                },
                _setScrollbarPosition: function (wrapper, self, content, targetBarContainer, referBarContainer, targetNeedScrollBar, referNeedScrollBar, targetScrollBarPosition, referScrollBarPosition, dir, scrollingNeed) {
                    var hbar = dir === "h", targetBarLen, targetPadding, targetBarPosition, barPosition1, contentPosition1, barPosition2, contentPosition2, contentLength2, referBarWidth, css = self.options.uiCSS;
                    if (targetNeedScrollBar) {
                        targetBarLen = targetBarContainer[0][hbar ? "offsetHeight" : "offsetWidth"];
                        targetPadding = self._getScrollContainerPadding(dir);
                        targetBarPosition = hbar ? "top" : "left";
                        barPosition1 = hbar ? {
                            top: "0px",
                            bottom: "auto",
                            left: "auto",
                            right: "auto"
                        } : {
                            left: "0px",
                            right: "auto",
                            top: "auto",
                            bottom: "auto"
                        };
                        contentPosition1 = hbar ? {
                            top: targetBarLen + "px",
                            left: null
                        } : {
                            left: targetBarLen + "px"
                        };
                        barPosition2 = hbar ? {
                            top: "auto",
                            right: "auto",
                            left: "auto",
                            bottom: "0px"
                        } : {
                            left: "auto",
                            right: "0px",
                            top: "auto",
                            bottom: "auto"
                        };
                        contentPosition2 = hbar ? {
                            top: "",
                            left: null
                        } : {
                            left: ""
                        };
                        contentLength2 = content[hbar ? "innerWidth" : "innerHeight"]();
                        if (targetScrollBarPosition === targetBarPosition) {
                            targetBarContainer.css(barPosition1);
                            content.css(contentPosition1);
                            if (hbar) {
                                targetBarContainer.children(".gcui-superpanel-hbar-buttonleft").removeClass(css.cornerBL).addClass(css.cornerTL);
                                targetBarContainer.children(".gcui-superpanel-hbar-buttonright").removeClass(css.cornerBR).addClass(css.cornerTR);
                                targetBarContainer.removeClass(css.cornerBottom).addClass(css.cornerTop);
                            } else {
                                targetBarContainer.children(".gcui-superpanel-vbar-buttontop").removeClass(css.cornerTR).addClass(css.cornerTL);
                                targetBarContainer.children(".gcui-superpanel-vbar-buttonbottom").removeClass(css.cornerBR).addClass(css.cornerBL);
                                targetBarContainer.removeClass(css.cornerRight).addClass(css.cornerLeft);
                            }
                        } else {
                            targetBarContainer.css(barPosition2);
                            content.css(contentPosition2);
                            if (hbar) {
                                targetBarContainer.children(".gcui-superpanel-hbar-buttonleft").removeClass(css.cornerTL).addClass(css.cornerBL);
                                targetBarContainer.children(".gcui-superpanel-hbar-buttonright").removeClass(css.cornerBL).addClass(css.cornerBR);
                                targetBarContainer.removeClass(css.cornerTop).addClass(css.cornerBottom);
                            } else {
                                targetBarContainer.children(".gcui-superpanel-vbar-buttontop").removeClass(css.cornerTL).addClass(css.cornerTR);
                                targetBarContainer.children(".gcui-superpanel-vbar-buttonbottom").removeClass(css.cornerBL).addClass(css.cornerBR);
                                targetBarContainer.removeClass(css.cornerLeft).addClass(css.cornerRight);
                            }
                        }
                        referBarWidth = 0;
                        if (referNeedScrollBar) {
                            referBarWidth = referBarContainer[0][hbar ? "offsetWidth" : "offsetHeight"];
                            if (referScrollBarPosition === "left") {
                                targetBarContainer.css("right", "0px");
                            } else if (referScrollBarPosition === "right") {
                                targetBarContainer.css("left", "0px");
                            } else if (referScrollBarPosition === "top") {
                                targetBarContainer.css("bottom", "0px");
                            } else if (referScrollBarPosition === "bottom") {
                                targetBarContainer.css("top", "0px");
                            }
                        }
                        if (!hbar && referNeedScrollBar) {
                            referBarWidth = 0;
                        }
                        targetBarContainer[hbar ? "width" : "height"](contentLength2 - targetPadding);
                        self._enableDisableScrollBar(dir, targetBarContainer, !scrollingNeed);
                    } else {
                        wrapper.css(hbar ? "left" : "top", "");
                    }
                },
                _testScroll: function (self, f, o) {
                    var wrapper = f.templateWrapper, content = f.contentWrapper, scrollerWrapper = f.stateContainer, contentWidth = content.innerWidth(), contentHeight = content.innerHeight(), wrapperWidth = f.contentWidth, wrapperHeight = f.contentHeight, hNeedScrollBar, vNeedScrollBar, hbarContainer, vbarContainer, hbarPosition, vbarPosition;
                    f.hScrolling = wrapperWidth > contentWidth;
                    f.vScrolling = wrapperHeight > contentHeight;
                    hNeedScrollBar = self.hNeedScrollBar = self._needScrollbar(o.hScroller, f.hScrolling);
                    if (self._createBarIfNeeded(hNeedScrollBar, scrollerWrapper, "h", hbarHtml, content) === false) {
                        return false;
                    }
                    if (hNeedScrollBar && !f.vScrolling) {
                        wrapper.css("float", "left");
                        f.contentHeight = wrapper.height();
                        f.vScrolling = f.contentHeight > (contentHeight - f.hbarContainer[0].offsetHeight);
                        wrapper.css("float", "");
                    }
                    vNeedScrollBar = self.vNeedScrollBar = self._needScrollbar(o.vScroller, f.vScrolling);
                    if (self._createBarIfNeeded(vNeedScrollBar, scrollerWrapper, "v", vbarHtml, content) === false) {
                        return false;
                    }
                    if (vNeedScrollBar && !f.hScrolling) {
                        wrapper.css("float", "left");
                        f.contentWidth = wrapper.width();
                        f.hScrolling = f.contentWidth > (contentWidth - f.vbarContainer[0].offsetWidth);
                        wrapper.css("float", "");
                        if (f.hScrolling && !hNeedScrollBar) {
                            hNeedScrollBar = self.hNeedScrollBar = self._needScrollbar(o.hScroller, f.hScrolling);
                            if (self._createBarIfNeeded(hNeedScrollBar, scrollerWrapper, "h", hbarHtml, content) === false) {
                                return false;
                            }
                        }
                    }
                    hbarContainer = f.hbarContainer;
                    vbarContainer = f.vbarContainer;
                    hbarPosition = o.hScroller.scrollBarPosition;
                    vbarPosition = o.vScroller.scrollBarPosition;
                    self._setScrollbarPosition(wrapper, self, content, hbarContainer, vbarContainer, hNeedScrollBar, vNeedScrollBar, hbarPosition, vbarPosition, "h", f.hScrolling);
                    self._setScrollbarPosition(wrapper, self, content, vbarContainer, hbarContainer, vNeedScrollBar, hNeedScrollBar, vbarPosition, hbarPosition, "v", f.vScrolling);
                },
                _enableDisableScrollBar: function (bar, barContainer, disable) {
                    var o = this.options;
                    if (bar === "v") {
                        barContainer[disable ? "addClass" : "removeClass"]("gcui-superpanel-vbarcontainer-disabled");
                        barContainer.find("." + o.uiCSS.stateDefault)[disable ? "addClass" : "removeClass"](o.uiCSS.stateDisabled);
                    } else if (bar === "h") {
                        barContainer[disable ? "addClass" : "removeClass"]("gcui-superpanel-hbarcontainer-disabled");
                        barContainer.find("." + o.uiCSS.stateDefault)[disable ? "addClass" : "removeClass"](o.uiCSS.stateDisabled);
                    }
                    barContainer.children("." + scrollerHandle)[disable ? "hide" : "show"]();
                },
                _initResizer: function () {
                    var self = this, o = self.options, f = self._fields(), resizer = f.resizer, resizableOptions, oldstop;
                    if (!$.fn.resizable) {
                        return;
                    }
                    if (!resizer && o.allowResize) {
                        resizableOptions = o.resizableOptions;
                        oldstop = resizableOptions.stop;
                        resizableOptions.stop = function (e) {
                            self._resizeStop(e, self);
                            if ($.isFunction(oldstop)) {
                                oldstop(e);
                            }
                        };
                        f.resizer = resizer = self.element.resizable(resizableOptions);
                    }
                    if (!o.allowResize && f.resizer) {
                        resizer.resizable("destroy");
                        f.resizer = null;
                    }
                },
                _resizeStop: function (e, self) {
                    if (!this.options.autoRefresh) {
                        self.paintPanel(true);
                    }
                    self._trigger("resized");
                },
                _createAdditionalDom: function (self, f, ele) {
                    if (!ele.attr("tabindex")) {
                        ele.attr("tabindex", "-1");
                        f.tabindex = true;
                    }
                    var stateContainer = f.stateContainer = $(innerElementHtml), templateW;
                    f.contentWrapper = stateContainer.children();
                    templateW = f.templateWrapper = f.contentWrapper.children();
                    ele.contents().each(function (index, el) {
                        var jel = $(el);
                        if (jel.hasClass("gcui-superpanel-header")) {
                            f.header = jel;
                            return;
                        }
                        if (jel.hasClass("gcui-superpanel-footer")) {
                            f.footer = jel;
                            return;
                        }
                        templateW[0].appendChild(el);
                    });
                    if (f.header !== undefined) {
                        ele.prepend(f.header);
                    }
                    ele[0].appendChild(stateContainer[0]);
                    if (f.footer !== undefined) {
                        f.footer.insertAfter(stateContainer);
                    }
                },
                _setRounder: function (self, ele) {
                    if (this.options.showRounder) {
                        ele.addClass(this.options.uiCSS.cornerAll);
                        if (self._rounderAdded) {
                            return;
                        }
                        var browser = GC.Spread.Sheets.util.browser;
                        if (browser.msie) {
                            return;
                        }
                        var key1 = "", key = "", value, border;
                        if (browser.webkit) {
                            key = "WebkitBorderTopLeftRadius";
                            key1 = "WebkitBorderRadius";
                        } else if (browser.mozilla) {
                            key = "MozBorderRadiusBottomleft";
                            key1 = "MozBorderRadius";
                        } else {
                            key = "border-top-left-radius";
                            key1 = "border-radius";
                        }
                        value = ele.css(key);
                        border = parseInt(value, 10);
                        ele.css(key1, border + 1);
                        self._rounderAdded = true;
                        self._radiusKey = key1;
                    } else {
                        ele.removeClass(this.options.uiCSS.cornerAll);
                    }
                },
                _setInnerElementsSize: function (f, ele) {
                    var state = f.stateContainer, content = f.contentWrapper, height = 0, style, clientHeight, clientWidth, style2;
                    if (f.header !== undefined) {
                        height += f.header.outerHeight();
                    }
                    if (f.footer !== undefined) {
                        height += f.footer.outerHeight();
                    }
                    style = state[0].style;
                    clientHeight = ele.innerHeight() - height;
                    clientWidth = ele.innerWidth();
                    style.display = "none";
                    style.height = clientHeight + "px";
                    style.width = clientWidth + "px";
                    style2 = content[0].style;
                    style2.height = clientHeight + "px";
                    style2.width = clientWidth + "px";
                    style.display = "";
                }
            });
            superpanel.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, new superpanel_options());
            $.gcui.registerWidget("superpanel", superpanel.prototype);
        } else {
            var scrollerHandle = "gcui-superpanel-handle", innerElementHtml = "<div class='gcui-superpanel-statecontainer'>" + "<div class='gcui-superpanel-contentwrapper-touch'>" + "</div></div>";
            superpanel.prototype = $.extend(true, {
            }, $.Widget.prototype, {
                widgetEventPrefix: "gcuisuperpanel",
                _setOption: function (key, value) {
                    var self = this, o = self.options, f = self._fields();
                    if (key === "animationOptions" || key === "resizableOptions") {
                        value = $.extend(o[key], value);
                    } else if (key === "hScroller" || key === "vScroller") {
                        value = $.extend(o[key], value);
                        self.refresh();
                    }
                    $.Widget.prototype._setOption.apply(self, arguments);
                    switch (key) {
                        case "allowResize":
                            self._initResizer();
                            break;
                        case "disabled":
                            self._handleDisabledOption(value, self.element);
                            break;
                        case "mouseWheelSupport":
                        case "keyboardSupport":
                            self._bindElementEvents(self, f, self.element, o);
                            break;
                    }
                },
                _create: function () {
                    var self = this, o = self.options;
                    o.vScroller.dir = "v";
                    o.hScroller.dir = "h";
                    self._createDom();
                    if (self.options.disabled) {
                        self.disable();
                    }
                    if (self.element.is(":hidden") && self.element.addVisibilityObserver) {
                        self.element.addVisibilityObserver(function () {
                            self.refresh();
                            if (self.element.removeVisibilityObserver) {
                                self.element.removeVisibilityObserver();
                            }
                        }, "gcuisuperpanel");
                    }
                },
                _handleDisabledOption: function (disabled, ele) {
                    var self = this;
                    if (disabled) {
                        if (!self.disabledDiv) {
                            self.disabledDiv = self._createDisabledDiv(ele);
                        }
                        self.disabledDiv.appendTo("body");
                    } else {
                        if (self.disabledDiv) {
                            self.disabledDiv.remove();
                            self.disabledDiv = null;
                        }
                    }
                },
                _createDisabledDiv: function (outerEle) {
                    var self = this, ele = outerEle ? outerEle : self.element, eleOffset = ele.offset(), disabledWidth = ele.outerWidth(), disabledHeight = ele.outerHeight();
                    return $("<div></div>").addClass("ui-disabled").css({
                        "z-index": "99999",
                        position: "absolute",
                        width: disabledWidth,
                        height: disabledHeight,
                        left: eleOffset.left,
                        top: eleOffset.top
                    });
                },
                _createDom: function () {
                    var self = this, el = self.element;
                    self.paintPanel();
                    self._initResizer();
                    self._bindElementEvents();
                },
                _applyOverflow: function (stateContainer) {
                    var css = {
                    }, o = this.options, hs = o.hScroller.scrollBarVisibility, vs = o.vScroller.scrollBarVisibility;
                    css["overflow-x"] = hs;
                    css["overflow-y"] = vs;
                    if ($.support.isTouchEnabled && $.support.isTouchEnabled()) {
                        if (hs === "visible" && vs === "visible") {
                            css["overflow-x"] = "auto";
                            css["overflow-y"] = "auto";
                        }
                    }
                    stateContainer.css(css);
                },
                _createAdditionalDom: function (self, f, ele) {
                    if (!ele.attr("tabindex")) {
                        ele.attr("tabindex", "-1");
                        f.tabindex = true;
                    }
                    var stateContainer = f.stateContainer = $(innerElementHtml), templateW;
                    self._applyOverflow(f.stateContainer);
                    f.contentWrapper = stateContainer.children();
                    ele.contents().each(function (index, el) {
                        var jel = $(el);
                        if (jel.hasClass("gcui-superpanel-header")) {
                            f.header = jel;
                            return;
                        }
                        if (jel.hasClass("gcui-superpanel-footer")) {
                            f.footer = jel;
                            return;
                        }
                        f.contentWrapper[0].appendChild(el);
                    });
                    if (f.header !== undefined) {
                        ele.prepend(f.header);
                    }
                    ele[0].appendChild(stateContainer[0]);
                    if (f.footer !== undefined) {
                        f.footer.insertAfter(stateContainer);
                    }
                    self._resetDom();
                    f.contentWrapper.css("float", "left");
                    f.contentWidth = f.contentWrapper.width();
                    f.contentHeight = f.contentWrapper.height();
                    f.contentWrapper.css("float", "");
                },
                _bindElementEvents: function () {
                    var self = this, ele = self.element, o = self.options, wn = self.widgetName, scrollEle = ele.find(".gcui-superpanel-statecontainer"), scrollTop = scrollEle.scrollTop();
                    scrollEle.bind("scroll", function (event, data) {
                        var curScrollTop = $(this).scrollTop();
                        if (curScrollTop === scrollTop) {
                            self._triggerScroll($(this).scrollLeft(), "h");
                            self._updateScrollValue($(this).scrollLeft(), "h");
                        } else {
                            self._triggerScroll(curScrollTop, "v");
                            self._updateScrollValue(curScrollTop, "v");
                            scrollTop = curScrollTop;
                        }
                    });
                    if (o.keyboardSupport) {
                        if (self._keyboardBind === undefined) {
                            self._keyboardBind = true;
                            ele.bind("keydown." + wn, self, self._panelKeyDown);
                        }
                    } else {
                        ele.unbind("keydown." + wn, self._panelKeyDown);
                        self._keyboardBind = undefined;
                    }
                    if (!o.mouseWheelSupport) {
                        ele.bind("mousewheel", function (event) {
                            event.stopPropagation();
                            return false;
                        });
                    }
                },
                _setScrollingInterval: function (f, dir, self, large) {
                    var o = self.options;
                    if (dir.length > 0) {
                        f.internalFuncID = window.setInterval(function () {
                            self._doScrolling(dir, self, large);
                        }, o.keyDownInterval);
                    }
                },
                _triggerScroll: function (contentLeft, dir) {
                    var data = {
                        position: contentLeft,
                        dir: dir
                    };
                    this._trigger("scroll", null, data);
                },
                _panelKeyDown: function (e) {
                    var self = e.data, o = self.options, shift, keycode, kCode = gcui.getKeyCodeEnum();
                    if (!o.keyboardSupport || o.disabled) {
                        return;
                    }
                    shift = e.shiftKey;
                    keycode = e.keyCode;
                    if (keycode === kCode.LEFT) {
                        self._doScrolling("left", self, shift);
                    } else if (keycode === kCode.RIGHT) {
                        self._doScrolling("right", self, shift);
                    } else if (keycode === kCode.UP) {
                        self._doScrolling("top", self, shift);
                    } else if (keycode === kCode.DOWN) {
                        self._doScrolling("bottom", self, shift);
                    }
                    e.stopPropagation();
                    e.preventDefault();
                },
                _doScrolling: function (dir, self, large) {
                    var value, orient, func, f = self._fields(), ele = f.stateContainer[0], animateOpt = {
                    }, scrollVal;
                    if (dir === "top" || dir === "bottom") {
                        orient = "v";
                        func = "scrollTop";
                    } else if (dir === "left" || dir === "right") {
                        orient = "h";
                        func = "scrollLeft";
                    }
                    if (large) {
                        value = self._getLargeChange(orient);
                    } else {
                        value = self._getSmallChange(orient);
                    }
                    scrollVal = ele[func];
                    if (dir === "top" || dir === "left") {
                        animateOpt[func] = scrollVal - value;
                    } else {
                        animateOpt[func] = scrollVal + value;
                    }
                    self._animateTo(animateOpt);
                },
                _getLargeChange: function (div) {
                    var self = this, f = self._fields(), largeChange, length = div == "h" ? f.clientWidth : f.clientHeight;
                    if (length) {
                        largeChange = length;
                    } else {
                        largeChange = f.stateContainer[div == "h" ? "width" : "height"]();
                    }
                    return largeChange;
                },
                _getSmallChange: function () {
                    return this._getLargeChange() / 2;
                },
                _setRounder: function (self, ele) {
                    var cornerCSS = this.options.uiCSS.cornerAll;
                    if (this.options.showRounder) {
                        ele.addClass(cornerCSS);
                        if (self._rounderAdded) {
                            return;
                        }
                        var browser = GC.Spread.Sheets.util.browser;
                        if (browser.msie) {
                            return;
                        }
                        var key1 = "", key = "", value, border;
                        if (browser.webkit) {
                            key = "WebkitBorderTopLeftRadius";
                            key1 = "WebkitBorderRadius";
                        } else if (browser.mozilla) {
                            key = "MozBorderRadiusBottomleft";
                            key1 = "MozBorderRadius";
                        } else {
                            key = "border-top-left-radius";
                            key1 = "border-radius";
                        }
                        value = ele.css(key);
                        border = parseInt(value, 10);
                        ele.css(key1, border + 1);
                        self._rounderAdded = true;
                        self._radiusKey = key1;
                    } else {
                        ele.removeClass(cornerCSS);
                    }
                },
                _initResizer: function () {
                    var self = this, o = self.options, f = self._fields(), resizer = f.resizer, resizableOptions, oldstop;
                    if (!$.fn.resizable) {
                        return;
                    }
                    if (!resizer && o.allowResize) {
                        resizableOptions = o.resizableOptions;
                        oldstop = resizableOptions.stop;
                        resizableOptions.stop = function (e) {
                            self._resetDom();
                            self._trigger("resized");
                            if ($.isFunction(oldstop)) {
                                oldstop(e);
                            }
                        };
                        f.resizer = resizer = self.element.resizable(resizableOptions);
                    }
                    if (!o.allowResize && f.resizer) {
                        resizer.resizable("destroy");
                        f.resizer = null;
                    }
                },
                _resetDom: function () {
                    var self = this, o = self.options, ele = self.element, f = self._fields(), width = ele.width(), height = ele.height();
                    if (f.header !== undefined) {
                        height -= f.header.outerHeight();
                    }
                    if (f.footer !== undefined) {
                        height -= f.footer.outerHeight();
                    }
                    if (f.stateContainer.length) {
                        f.stateContainer.css({
                            width: width,
                            height: height
                        });
                        f.clientWidth = f.stateContainer[0].clientWidth;
                        f.clientHeight = f.stateContainer[0].clientHeight;
                    }
                },
                _fields: function () {
                    var self = this, ele = self.element, key = self.widgetName + "-fields", d = self._fieldsStore;
                    if (d === undefined) {
                        d = {
                        };
                        ele.data(key, d);
                        self._fieldsStore = d;
                    }
                    return d;
                },
                _getScrollOffset: function (child1) {
                    var child = $(child1), f, cWrapper, childOffset, templateOffset, cWrapperOffset, tDistance, bDistance, lDistance, rDistance, result = {
                        left: null,
                        top: null
                    };
                    if (child.length === 0) {
                        return result;
                    }
                    f = this._fields();
                    cWrapper = f.contentWrapper;
                    childOffset = child.offset();
                    childOffset.leftWidth = childOffset.left + child.outerWidth();
                    childOffset.topHeight = childOffset.top + child.outerHeight();
                    cWrapperOffset = cWrapper.offset();
                    cWrapperOffset.leftWidth = cWrapperOffset.left + f.clientWidth;
                    cWrapperOffset.topHeight = cWrapperOffset.top + f.clientHeight;
                    lDistance = childOffset.left - cWrapperOffset.left;
                    if (childOffset.left < cWrapperOffset.left) {
                        result.left = lDistance;
                    } else if (childOffset.leftWidth > cWrapperOffset.leftWidth) {
                        rDistance = childOffset.leftWidth - cWrapperOffset.left - f.clientWidth;
                        if (lDistance < rDistance) {
                            result.left = lDistance;
                        } else {
                            result.left = rDistance;
                        }
                    }
                    tDistance = childOffset.top - cWrapperOffset.top;
                    if (childOffset.top < cWrapperOffset.top) {
                        result.top = tDistance;
                    } else if (childOffset.topHeight > cWrapperOffset.topHeight) {
                        bDistance = childOffset.topHeight - cWrapperOffset.top - f.clientHeight;
                        if (tDistance < bDistance) {
                            result.top = tDistance;
                        } else {
                            result.top = bDistance;
                        }
                    }
                    return result;
                },
                _initialize: function (f, ele, self) {
                    var css = self.options.uiCSS;
                    f.initialized = true;
                    ele.addClass([
                        "gcui-superpanel",
                        css.widget,
                        css.content
                    ].join(' '));
                    self._setRounder(self, ele);
                    self._createAdditionalDom(self, f, ele);
                    self._trigger("painted");
                    self._initScrollPosition();
                },
                _initScrollPosition: function () {
                    var o = this.options, hScroller = o.hScroller, vScroller = o.vScroller, hScrollValue = hScroller.scrollValue, vSCrollValue = vScroller.scrollValue, f = this._fields(), hScrollPx = hScrollValue ? this._scrollValueToPx(hScrollValue, "h") : 0, vScrollPx = vSCrollValue ? this._scrollValueToPx(vSCrollValue, "v") : 0;
                    if (hScrollValue) {
                        f.stateContainer.prop("scrollLeft", hScrollPx);
                    }
                    if (vSCrollValue) {
                        f.stateContainer.prop("scrollTop", vScrollPx);
                    }
                },
                _updateScrollValue: function (px, dir) {
                    var value = this.scrollPxToValue(px, dir), o = this.options, scroller = (dir === "h" ? "hScroller" : "vScroller"), ev = $.Event("scrollValueChanged");
                    o[scroller].scrollValue = value;
                    this._trigger("scrollValueChanged", ev, {
                        dir: dir,
                        value: value
                    });
                },
                scrollPxToValue: function (px, dir) {
                    var o = this.options, m = (dir === "h" ? "width" : "height"), m1 = (dir === "h" ? "contentWidth" : "contentHeight"), scroller = (dir === "h" ? "hScroller" : "vScroller"), f = this._fields(), cWrapper = f.stateContainer, size = f[m1], contentHeight = cWrapper[m](), vMin = o[scroller].scrollMin, vMax = o[scroller].scrollMax, vRange = vMax - vMin, ret = vRange * px / (size - contentHeight) + vMin;
                    return ret;
                },
                _scrollValueToPx: function (value, dir) {
                    var o = this.options, m = (dir === "h" ? "width" : "height"), m1 = (dir === "h" ? "contentWidth" : "contentHeight"), scroller = (dir === "h" ? "hScroller" : "vScroller"), f = this._fields(), cWrapper = f.stateContainer, size = f[m1], contentHeight = cWrapper[m](), vMin = o[scroller].scrollMin, vMax = o[scroller].scrollMax, vRange = vMax - vMin, ret = (value - vMin) * (size - contentHeight) / vRange;
                    return ret;
                },
                _animateTo: function (to) {
                    var self = this, ele = self.element, o = self.options, ao = o.animationOptions, f = self._fields();
                    f.stateContainer.animate(to, ao);
                },
                destroy: function () {
                    var self = this, ele = self.element, o = self.options, f = self._fields(), cWrapper, css = o.uiCSS;
                    if (self.disabledDiv) {
                        self.disabledDiv.remove();
                        self.disabledDiv = null;
                    }
                    if (f.resizer !== undefined) {
                        f.resizer.resizable("destroy");
                    }
                    ele.unbind("." + self.widgetName);
                    ele.removeClass([
                        "gcui-superpanel",
                        css.widget,
                        css.content,
                        css.cornerAll
                    ].join(' '));
                    cWrapper = f.contentWrapper;
                    cWrapper.contents().each(function (index, e) {
                        ele.append(e);
                    });
                    f.stateContainer.remove();
                    $.Widget.prototype.destroy.apply(self, arguments);
                },
                doScrolling: function (dir, large) {
                    this._doScrolling(dir, this, large);
                },
                paintPanel: function (unfocus) {
                    var self = this, ele = self.element, f = self._fields();
                    if (!f.initialized) {
                        this._initialize(f, ele, self);
                    }
                },
                needToScroll: function (child1) {
                    var offset = this._getScrollOffset(child1);
                    return offset.top !== null || offset.left !== null;
                },
                scrollChildIntoView: function (child1) {
                    var offset = this._getScrollOffset(child1), left = offset.left, top = offset.top;
                    this.scrollTo(left, top);
                },
                getContentElement: function () {
                    return this._fields().contentWrapper;
                },
                hScrollTo: function (x, isScrollValue) {
                    var val = !!isScrollValue ? this._scrollValueToPx(x) : x;
                    this._updateScrollValue(val, "h");
                    this._animateTo({
                        "scrollLeft": val
                    });
                },
                vScrollTo: function (y, isScrollValue) {
                    var val = !!isScrollValue ? this._scrollValueToPx(y) : y;
                    this._updateScrollValue(val, "v");
                    this._animateTo({
                        "scrollTop": val
                    });
                },
                refresh: function () {
                    var self = this, f = self._fields();
                    self._applyOverflow(f.stateContainer);
                    self._resetDom();
                },
                scrollTo: function (x, y, isScrollValue) {
                    var valX = !!isScrollValue ? this._scrollValueToPx(x) : x, valY = !!isScrollValue ? this._scrollValueToPx(y) : y;
                    this._updateScrollValue(valX, "h");
                    this._updateScrollValue(valY, "v");
                    this._animateTo({
                        "scrollTop": valY,
                        "scrollLeft": valX
                    });
                }
            });
            superpanel.prototype.options = $.extend(true, {
            }, gcui.gcuiWidget.prototype.options, {
                mobileCSS: {
                    header: "ui-header ui-bar-a",
                    content: "ui-body-c",
                    stateDefault: "ui-btn-up-c",
                    stateHover: "ui-btn-down-c",
                    stateActive: "ui-btn-down-c"
                },
                initSelector: ":jqmData(role='superpanel')",
                allowResize: false,
                animationOptions: {
                    queue: false,
                    disabled: false,
                    duration: 250,
                    easing: undefined
                },
                hScroller: {
                    scrollBarVisibility: "auto",
                    scrollValue: null,
                    scrollMax: 100,
                    firstStepChangeFix: 0,
                    scrollMin: 0,
                    hoverEdgeSpan: 20
                },
                keyboardSupport: false,
                keyDownInterval: 100,
                mouseWheelSupport: true,
                resizableOptions: {
                    handles: "all",
                    helper: "ui-widget-content gcui-superpanel-helper"
                },
                resized: null,
                painted: null,
                scroll: null,
                showRounder: true,
                vScroller: {
                    scrollBarVisibility: "auto",
                    scrollValue: null,
                    scrollMax: 100,
                    scrollMin: 0,
                    firstStepChangeFix: 0,
                    hoverEdgeSpan: 20
                }
            });
            $.gcui.registerWidget("superpanel", superpanel.prototype);
        }
    })(gcui.superpanel || (gcui.superpanel = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        function extendWidgetOptions(baseOptions, newOptions) {
            var result = $.extend(true, {}, baseOptions, newOptions);
            delete result.constructor;
            return result;
        }
        grid.extendWidgetOptions = extendWidgetOptions;
        var gcuigrid = (function (_super) {
            __extends(gcuigrid, _super);
            function gcuigrid() {
                _super.apply(this, arguments);

                this._dataOffset = 0;
                this._scrollingState = {
                    x: null,
                    y: null,
                    index: 0
                };
                this._windowResizeTimer = 0;
            }
            gcuigrid.prototype._createWidget = function (options, element) {
                var data = options && gcui.data.util.isClassInstance(options.data) && !((typeof gcuidatasource === "function") && (options.data instanceof gcuidatasource)) && options.data;
                if(data) {
                    delete options.data;
                }
                _super.prototype._createWidget.apply(this, arguments);
                if(data) {
                    options.data = data;
                    this.option("data", data);
                }
            };
            gcuigrid.prototype._create = function () {
                var self = this;
                if(!this.element.is("table")) {
                    throw "invalid markup";
                }
                this._windowResizeTimer = 0;
                this._dataOffset = 0;
                this._scrollingState = {
                    x: null,
                    y: null,
                    index: 0
                };
                this._initialized = false;
                this._destroyed = false;
                this._rendered = false;
                this._eventUID = undefined;
                ;
                this._dataViewWrapper = undefined;
                this._originalHtml = undefined;
                this._originalAttr = undefined;
                this._originalCssText = undefined;
                this._autoHeight = undefined;
                this._autoWidth = undefined;
                this._renderCounter = 0;
                this.$superPanelHeader = undefined;
                this.$topPagerDiv = undefined;
                this.$bottomPagerDiv = undefined;
                this.$groupArea = undefined;
                this.__uid = undefined;
                this._dataView = undefined;
                this.outerDiv = undefined;
                this.sketchTable = undefined;
                this.cellFormatter = undefined;
                this.rowStyleFormatter = undefined;
                this.cellStyleFormatter = undefined;
                this._eventUID = gcui.grid.getUID();
                this._spinnerIsActivated = false;
                this.deficientFilters = {
                };
                var hasData = ("data" in this.options), data = hasData && this.options.data;
                if(hasData) {
                    delete this.options.data;
                }
                this.options = $.extend(true, {}, this.options);
                if(hasData) {
                    this.options.data = data;
                }
                if(window.applyTouchUtilEvents) {
                    $ = window.applyTouchUtilEvents($);
                }
                if($.isFunction(window["gcuiASPNetParseOptions"])) {
                    window["gcuiASPNetParseOptions"](this.options);
                }
                this._initialized = false;
                this._destroyed = false;
                this._field("closestCulture", Globalize.findClosestCulture(this.options.culture) || Globalize.findClosestCulture("default"));
                this._dataViewWrapper = new gcui.grid.dataViewWrapper(this);
                this._originalHtml = this.element.html();
                this._originalAttr = {
                };
                this._originalCssText = this.element[0].style.cssText;
                this.element.addClass("gcui-gcuigrid-root");
                this.element.wrap("<div class=\"" + this.options.uiCSS.widget + " gcui-gcuigrid " + this.options.uiCSS.content + " " + this.options.uiCSS.cornerAll + "\"></div>");
                this.outerDiv = this.element.parent();
                var styleHeight = this.element[0].style.height, styleWidth = this.element[0].style.width;
                if(styleHeight) {
                    this.outerDiv.css("height", styleHeight);
                }
                this._autoHeight = (styleHeight == "" || styleHeight == "auto");
                if(styleWidth) {
                    this.outerDiv.css("width", this.element[0].style.width);
                }
                this._autoWidth = (styleWidth == "" || styleWidth == "auto");
                this.element.css({ "height": "", "width": ""});
                if(this.options.disabled) {
                    this.disable();
                }
                this.cellFormatter = new gcui.grid.cellFormatterHelper();
                this.rowStyleFormatter = new gcui.grid.rowStyleFormatterHelper(this);
                this.cellStyleFormatter = new gcui.grid.cellStyleFormatterHelper(this);
                this._field("viewRenderBounds", { start: 0, end: 0 });
                if(this._allowVirtualScrolling()) {
                    this._field("viewRenderBounds").start = this._scrollingState.index;
                    if(this._serverSideVirtualScrolling()) {
                        this._dataOffset = this._scrollingState.index;
                    }
                }
                if(this.element.addVisibilityObserver) {
                    this.element.addVisibilityObserver(function () {
                        if(self._initialized && !self._destroyed) {
                            self.setSize();
                        }
                    }, "gcuigrid");
                }
                this._renderCounter = 0;
            };
            gcuigrid.prototype._destroy = function () {
                var tmp, self = this;
                try  {
                    this._view().dispose();
                    this._detachEvents(true);
                    if(tmp = this._field("resizer")) {
                        tmp.dispose();
                    }
                    if(tmp = this._field("frozener")) {
                        tmp.dispose();
                    }
                    if(tmp = this._field("selectionui")) {
                        tmp.dispose();
                    }
                    if(tmp = this._field("dragndrop")) {
                        tmp.dispose();
                    }
                    this._dataViewWrapper.dispose();
                    gcui.grid.remove$dataByPrefix(this.element, this._data$prefix);
                    if(this.element.removeVisibilityObserver) {
                        this.element.removeVisibilityObserver();
                    }
                    this.element.insertBefore(this.outerDiv);
                    this.outerDiv.remove();
                    this.element.html(this._originalHtml);
                    $.each(this._originalAttr, function (key, value) {
                        if(value === undefined) {
                            self.element.removeAttr(key);
                        } else {
                            self.element.attr(key, value);
                        }
                    });
                    this.element.removeClass("gcui-gcuigrid-root");
                    this.element[0].style.cssText = this._originalCssText;
                }finally {
                    this._destroyed = true;
                }
            };
            gcuigrid.prototype._init = function () {
                this.$superPanelHeader = null;
                this.$topPagerDiv = null;
                this.$bottomPagerDiv = null;
                this.$groupArea = null;
                this._field("closestCulture", Globalize.findClosestCulture(this.options.culture) || Globalize.findClosestCulture("default"));
                if(!this.options.data) {
                    if(!this._field("thead")) {
                        this._field("thead", gcui.grid.readTableSection(this.element, 1));
                    }
                    if(!this._field("tfoot")) {
                        this._field("tfoot", gcui.grid.readTableSection(this.element, 3));
                    }
                }
                this._initialized = this._initialized || false;
                this.ensureControl(true);
            };
            gcuigrid.prototype._setOption = function (key, value) {
                var presetFunc = this["_preset_" + key], oldValue = this.options[key], optionChanged, postsetFunc;
                if(presetFunc !== undefined) {
                    value = presetFunc.apply(this, [
                        value, 
                        oldValue
                    ]);
                }
                optionChanged = (value !== oldValue);
                _super.prototype._setOption.apply(this, [
                    key, 
                    value
                ]);
                if(optionChanged) {
                    postsetFunc = this["_postset_" + key];
                    if(postsetFunc !== undefined) {
                        postsetFunc.apply(this, [
                            value, 
                            oldValue
                        ]);
                    }
                }
            };
            gcuigrid.prototype.columns = function () {
                return this._field("columns") || [];
            };
            gcuigrid.prototype.currentCell = function (a, b, changeSelection) {
                var currentCell, view = this._view(), rows = this._rows();
                if(arguments.length === 0) {
                    currentCell = this._field("currentCell");
                    if(!currentCell) {
                        this._field("currentCell", currentCell = gcui.grid.cellInfo.outsideValue);
                    }
                    return currentCell;
                } else {
                    currentCell = (typeof (a) !== "number") ? (a)._clone() : new gcui.grid.cellInfo(a, b);
                    if(!currentCell.isEqual(gcui.grid.cellInfo.outsideValue)) {
                        if(!currentCell._isValid()) {
                            throw "invalid arguments";
                        }
                        currentCell._clip(this._getDataCellsRange());
                        if(currentCell.rowIndex() >= 0) {
                            if(!(view._getRowInfo(rows.item(currentCell.rowIndex())).type & gcui.grid.rowType.data)) {
                                return;
                            }
                        }
                    }
                    currentCell._setGridView(this);
                    this._changeCurrentCell(null, currentCell, {
                        changeSelection: changeSelection || false,
                        setFocus: false
                    });
                    return this._field("currentCell");
                }
            };
            gcuigrid.prototype.data = function () {
                return this._dataViewWrapper.dataView().getSource();
            };
            gcuigrid.prototype.dataView = function () {
                return this._dataViewWrapper.dataView();
            };
            gcuigrid.prototype.doRefresh = function (userData) {
                if(!$.isPlainObject(userData)) {
                    userData = {};
                }
                var leaves, self = this, uid = gcui.grid.EXPANDO, virtualRefresh = userData && userData.virtualScrollData;
                if(!this._initialized) {
                    try  {
                        this._prepareColumnOptions(this.options.columns, this.options.columnsAutogenerationMode, this._dataViewWrapper.getFieldsInfo(), true, true);
                    } catch (e) {
                        throw e;
                    }finally {
                        this._ownerise(true);
                        this._initialized = true;
                    }
                } else {
                }
                if(!virtualRefresh) {
                    this._rebuildLeaves();
                    var dataSlice = this._dataViewWrapper.data();
                    var dataView = this._dataViewWrapper.dataView();
                    $.each(this._field("leaves"), function () {
                        this._totalsValue = (dataSlice.totals) ? dataSlice.totals[this.dataKey] : undefined;
                    });
                    this.sketchTable = [];
                    if(dataSlice.data && dataSlice.data.length) {
                        leaves = this._field("leaves");
                        $.each(dataSlice.data, function (i, item) {
                            self.sketchTable.push(self._buildSketchRow(item, leaves));
                        });
                    } else {
                        if(dataSlice.emptyData) {
                            leaves = this._field("visibleLeaves");
                            $.each(dataSlice.emptyData, function (i, item) {
                                self.sketchTable.push(self._buildSketchRowEmptyDataItem(item, leaves, i === dataSlice.emptyData.length - 1));
                            });
                        }
                    }
                }
                this._onRendering(userData);
                if(!virtualRefresh) {
                    this._refresh(userData);
                } else {
                    this._refreshVirtual(userData);
                }
                this._onRendered(userData);
                if(userData && $.isFunction(userData.afterRefresh)) {
                    userData.afterRefresh.apply(this, [
                        userData
                    ]);
                }
            };
            gcuigrid.prototype.beginEdit = function () {
                return this._beginEditInternal(null);
            };
            gcuigrid.prototype.endEdit = function () {
                return this._endEditInternal(null);
            };
            gcuigrid.prototype.ensureControl = function (loadData, userData) {
                this._loading();
                if(!$.isPlainObject(userData)) {
                    userData = {
                        data: null,
                        afterRefresh: null,
                        beforeRefresh: null
                    };
                }
                userData._gridMarker = true;
                if(this._initialized) {
                    this._convertWidgetsToOptions();
                } else {
                    this._prepareColumnOptions(this.options.columns, this.options.columnsAutogenerationMode, this._dataViewWrapper.isDataLoaded() && this._dataViewWrapper.getFieldsInfo(), this._dataViewWrapper.isDataLoaded(), false);
                    if(!this._dataViewWrapper.isOwnDataView()) {
                        (new gcui.grid.settingsManager(this)).MapDVToWG();
                    }
                }
                this._ownerise(true);
                if(this._initialized) {
                    if(userData && $.isFunction(userData.beforeRefresh)) {
                        userData.beforeRefresh.apply(this);
                    }
                }
                this._field("allowVirtualScrolling", null);
                if(loadData === true) {
                    this._dataViewWrapper.load(userData);
                } else {
                    this.doRefresh(userData);
                    this._loaded();
                }
            };
            gcuigrid.prototype.getCellInfo = function (domCell) {
                var cellInfo = null;
                if(domCell && (domCell = this._findUntilOuterDiv(domCell, { td: true, th: true }))) {
                    cellInfo = this._view().getAbsoluteCellInfo(domCell);
                }
                return cellInfo;
            };
            gcuigrid.prototype.getFilterOperatorsByDataType = function (dataType) {
                return (new gcui.grid.filterOperatorsCache(this)).getByDataType(dataType || "string");
            };
            gcuigrid.prototype.pageCount = function () {
                if(this._customPagingEnabled()) {
                    return Math.ceil(this.options.totalRows / this.options.pageSize) || 1;
                }
                return this.options.allowPaging ? (this._dataViewWrapper.dataView()).pageCount() : 1;
            };
            gcuigrid.prototype._serverShaping = function () {
                return false;
            };
            gcuigrid.prototype._pageIndexForDataView = function () {
                return this.options.pageIndex;
            };
            gcuigrid.prototype.setSize = function (width, height) {
                var view = this._view(), scrollValue = {
                    type: "",
                    hScrollValue: null,
                    vScrollValue: null
                }, outerDiv = this.outerDiv, frozener = this._field("frozener"), visibleLeaves = this._field("visibleLeaves"), leavesWithFilter = [];
                if(view && view.getScrollValue) {
                    scrollValue = view.getScrollValue();
                }
                if(width || (width === 0)) {
                    this._autoWidth = false;
                    outerDiv.width(width);
                }
                if(height || (height === 0)) {
                    this._autoHeight = false;
                    outerDiv.height(height);
                }
                $.each(visibleLeaves, function (index, leaf) {
                    var th = view.getHeaderCell(index), cols = view.getJoinedCols(index);
                    $(th).css("width", "");
                    $.each(cols, function (index, col) {
                        $(col).css("width", "");
                    });
                });
                this._view().updateSplits(scrollValue);
                if(frozener) {
                    frozener.refresh();
                }
            };
            gcuigrid.prototype.selection = function () {
                var selection = this._field("selection");
                if(!selection) {
                    this._field("selection", selection = new gcui.grid.selection(this));
                }
                return selection;
            };
            gcuigrid.prototype._onDataViewCurrentPositionChanged = function (e, args) {
                var cellInfo = this._currentCellFromDataView(this.currentCell().cellIndex()), selection = this.selection();
                cellInfo = this.currentCell(cellInfo, null, true);
            };
            gcuigrid.prototype._resetDataProperties = function () {
                this.options.pageIndex = 0;
                var bounds = this._field("viewRenderBounds");
                bounds.start = bounds.end = 0;
            };
            gcuigrid.prototype._onDataViewLoading = function () {
                this._activateSpinner();
                this._trigger("dataLoading");
            };
            gcuigrid.prototype._onDataViewReset = function (userData) {
                (new gcui.grid.settingsManager(this)).MapDVToWG();
                this._trigger("dataLoaded");
                this.doRefresh(userData);
                this._loaded();
            };
            gcuigrid.prototype._onDataViewLoaded = function () {
            };
            gcuigrid.prototype._loading = function () {
                this._activateSpinner();
                this._trigger("loading");
            };
            gcuigrid.prototype._loaded = function () {
                this._deactivateSpinner();
                this._trigger("loaded");
            };
            gcuigrid.prototype._buildSketchRow = function (wrappedDataItem, leaves) {
                var i, len, leaf, cellAttr, value, tmp, sketchRow = [], expando = gcui.data.Expando.getFrom(wrappedDataItem.values, false), rowAttributes = expando ? expando[gcui.grid.EXPANDO] : null;
                for(i = 0 , len = leaves.length; i < len; i++) {
                    leaf = leaves[i];
                    if(gcui.grid.validDataKey(leaf.dataKey)) {
                        cellAttr = (rowAttributes && rowAttributes.cellsAttributes) ? rowAttributes.cellsAttributes[leaf.dataKey] : {};
                        value = this._dataViewWrapper.getValue(wrappedDataItem.values, leaf.dataKey);
                        sketchRow.push({
                            value: this.parse(leaf, value),
                            __attr: cellAttr || {},
                            __style: {}
                        });
                    }
                }
                (sketchRow).originalRowIndex = wrappedDataItem.originalRowIndex;
                (sketchRow).rowType = gcui.grid.rowType.data;
                if(wrappedDataItem.originalRowIndex % 2 !== 0) {
                    (sketchRow).rowType |= gcui.grid.rowType.dataAlt;
                }
                (sketchRow).__style = {};
                (sketchRow).__attr = (rowAttributes && rowAttributes.rowAttributes) ? rowAttributes.rowAttributes : {};
                return sketchRow;
            };
            gcuigrid.prototype._buildSketchRowEmptyDataItem = function (dataItem, leaves, isLastRow) {
                var i, len, sketchRow = [], leavesLen = leaves.length;
                for(i = 0 , len = dataItem.length; i < len; i++) {
                    sketchRow.push({
                        html: dataItem[i],
                        __attr: {
                            colSpan: (leavesLen > 0 && isLastRow) ? leavesLen : 1
                        },
                        __style: {}
                    });
                }
                (sketchRow).rowType = gcui.grid.rowType.emptyDataRow;
                (sketchRow).__style = {};
                (sketchRow).__attr = {};
                return sketchRow;
            };
            gcuigrid.prototype._prepareColumnOptions = function (columns, generationMode, fieldsInfo, dataLoaded, finalStage) {
                gcui.grid.traverse(columns, function (column) {
                    column.isBand = ($.isArray(column.columns) || (column.clientType === "c1band"));
                    column._originalDataKey = column.dataKey;
                    column._originalHeaderText = column.headerText;
                });
                new gcui.grid.bandProcessor().getVisibleHeight(columns, true);
                var leaves = [], headerRow = this._originalHeaderRowData(), footerRow = this._originalFooterRowData(), autogenerationMode = (generationMode || "").toLowerCase(), self = this;
                if(dataLoaded) {
                    gcui.grid.columnsGenerator.generate(autogenerationMode, fieldsInfo, columns);
                }
                gcui.grid.setTraverseIndex(columns);
                gcui.grid.traverse(columns, function (column) {
                    column.isBand = ($.isArray(column.columns) || (column.clientType === "c1band"));
                    gcui.grid.shallowMerge(column, grid.c1basefield.prototype.options);
                    if(!column.isBand) {
                        gcui.grid.shallowMerge(column, grid.c1field.prototype.options);
                        column.groupInfo = column.groupInfo || {};
                        gcui.grid.shallowMerge(column.groupInfo, grid.c1field.prototype.options.groupInfo);
                        if(!column.clientType) {
                            column.clientType = "c1field";
                        }
                    } else {
                        column.clientType = "c1band";
                    }
                    if(column.isLeaf && !column.isBand) {
                        leaves.push(column);
                    }
                });
                this._field("leaves", leaves);
                if(dataLoaded) {
                    $.each(leaves, $.proxy(function (i, leaf) {
                        var thIndex = (typeof (leaf.dataKey) === "number") ? leaf.dataKey : i;
                        if(autogenerationMode === "merge" || leaf.dynamic === true) {
                            if(leaf.headerText === undefined) {
                                if(self._dataViewWrapper && self._dataViewWrapper.isBoundedToDOM() && headerRow && (thIndex < headerRow.length)) {
                                    leaf.headerText = $.trim(headerRow[thIndex]);
                                } else {
                                    if(gcui.grid.validDataKey(leaf.dataKey)) {
                                        leaf.headerText = "" + leaf.dataKey;
                                    }
                                }
                            }
                        }
                        if(self._dataViewWrapper && self._dataViewWrapper.isBoundedToDOM() && footerRow && (thIndex < footerRow.length)) {
                            leaf._footerTextDOM = $.trim(footerRow[thIndex]);
                        }
                    }, this));
                }
            };
            gcuigrid.prototype._rebuildLeaves = function () {
                var tmpColumns = [], leaves = [], tmp;
                if(this._showRowHeader()) {
                    tmp = gcui.grid.createDynamicField({
                        clientType: "c1basefield",
                        dataIndex: -1,
                        travIdx: -1,
                        parentVis: true,
                        allowMoving: false,
                        allowSizing: false,
                        allowSort: false,
                        isRowHeader: true
                    });
                    tmp.owner = this;
                    tmpColumns.push(tmp);
                }
                $.each(this.options.columns, function (index, item) {
                    tmpColumns.push(item);
                });
                this._columnsHeadersTable(new gcui.grid.bandProcessor().generateSpanTable(tmpColumns, leaves));
                this._field("leaves", leaves);
                this._onLeavesCreated();
            };
            gcuigrid.prototype._onLeavesCreated = function () {
                var leaves = this._field("leaves"), fieldsInfo = this._dataViewWrapper.getFieldsInfo(), meta, dataIndex = 0, visLeavesIdx = 0, self = this;
                this._field("visibleLeaves", $.grep(leaves, function (leaf, index) {
                    leaf.leavesIdx = index;
                    if(gcui.grid.validDataKey(leaf.dataKey)) {
                        leaf.dataIndex = dataIndex++;
                    } else {
                        leaf.dataIndex = -1;
                    }
                    if(!leaf.isBand) {
                        meta = fieldsInfo[leaf.dataKey];
                        if(meta) {
                            leaf._underlyingDataType = meta.type;
                            leaf._underlyingDataFormatString = meta.format;
                        }
                        if($.isFunction(leaf.dataParser)) {
                            leaf.dataParser = new (leaf.dataParser)();
                        }
                    }
                    if(leaf.parentVis) {
                        leaf.visLeavesIdx = visLeavesIdx++;
                        return true;
                    }
                    return false;
                }));
            };
            gcuigrid.prototype._allowVirtualScrolling = function () {
                var val = this._field("allowVirtualScrolling");
                if(!val) {
                    val = this._field("allowVirtualScrolling", !this.options.allowPaging && this.options.allowVirtualScrolling && (this.options.staticRowIndex < 0) && (this.options.scrollMode !== "none") && !this._hasMerging());
                }
                return val;
            };
            gcuigrid.prototype._dragndrop = function (force) {
                if (typeof force === "undefined") { force = false; }
                var dnd = this._field("dragndrop");
                if(!dnd && force) {
                    this._field("dragndrop", dnd = new gcui.grid.uiDragndrop(this));
                }
                return dnd;
            };
            gcuigrid.prototype._headerRows = function () {
                return this._view().headerRows();
            };
            gcuigrid.prototype._filterRow = function () {
                return this._view().filterRow();
            };
            gcuigrid.prototype._rows = function () {
                return this._view().bodyRows();
            };
            gcuigrid.prototype._localizeFilterOperators = function (locArray) {
                var self = this, helper = new gcui.grid.filterOperatorsCache(this);
                $.each(locArray, function (i, o) {
                    if(o.name) {
                        var fop = helper.getByName(o.name);
                        if(fop) {
                            fop.displayName = o.displayName;
                        }
                    }
                });
            };
            gcuigrid.prototype._selectionui = function (force) {
                var selectionui = this._field("selectionui");
                if(!selectionui && force) {
                    this._field("selectionui", selectionui = new gcui.grid.uiSelection(this));
                }
                return selectionui;
            };
            gcuigrid.prototype._postset_allowColMoving = function (value, oldValue) {
                var self = this;
                $.each(this.columns(), function (idx, field) {
                    if(value) {
                        self._dragndrop(true).attach(field);
                    } else {
                        self._dragndrop(true).detach(field);
                    }
                });
                var groupedWidgets = this._field("groupedWidgets");
                if(groupedWidgets) {
                    $.each(groupedWidgets, function (idx, field) {
                        if(value) {
                            self._dragndrop(true).attach(field);
                        } else {
                            self._dragndrop(true).detach(field);
                        }
                    });
                }
            };
            gcuigrid.prototype._postset_allowSorting = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_columns = function (value, oldValue) {
                this._initialized = false;
                this.ensureControl(true);
            };
            gcuigrid.prototype._postset_allowPaging = function (value, oldValue) {
                this.ensureControl(true);
            };
            gcuigrid.prototype._postset_culture = function (value, oldValue) {
                throw "read-only";
            };
            gcuigrid.prototype._postset_customFilterOperators = function (value, oldValue) {
                var dataView = this._dataViewWrapper.dataView();
            };
            gcuigrid.prototype._postset_data = function (value, oldValue) {
                this._ownerise(false);
                gcui.grid.traverse(this.options.columns, function (column, columns) {
                    if(column.dynamic) {
                        var idx = $.inArray(column, columns);
                        if(idx >= 0) {
                            columns.splice(idx, 1);
                        }
                    } else {
                        column.dataKey = column._originalDataKey;
                        column.headerText = column._originalHeaderText;
                    }
                });
                this._initialized = false;
                if(this._dataViewWrapper) {
                    this._dataViewWrapper.dispose();
                }
                this._dataViewWrapper = new gcui.grid.dataViewWrapper(this);
                this.ensureControl(true);
            };
            gcuigrid.prototype._postset_disabled = function (value, oldValue) {
                var self = this, view = this._view();
                gcui.grid.iterateChildrenWidgets(this.outerDiv, function (index, widget) {
                    if(widget !== self) {
                        widget.option("disabled", value);
                    }
                });
                if(view) {
                    view.ensureDisabledState();
                }
            };
            gcuigrid.prototype._postset_groupIndent = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_groupAreaCaption = function (value, oldValue) {
                var groupedColumns = this._groupedColumns();
                if(this.$groupArea && (!groupedColumns || !groupedColumns.length)) {
                    this.$groupArea.html(value || "&nbsp;");
                }
            };
            gcuigrid.prototype._postset_highlightCurrentCell = function (value, oldValue) {
                var currentCell = this.currentCell();
                if(currentCell && currentCell._isValid()) {
                    this._highlightCellPosition(currentCell, value);
                }
            };
            gcuigrid.prototype._preset_pageIndex = function (value, oldValue) {
                if(isNaN(value)) {
                    throw "out of range";
                }
                var pageCount = this.pageCount(), fn = function (val) {
                    if(val > pageCount - 1) {
                        val = pageCount - 1;
                    }
                    if(val < 0) {
                        val = 0;
                    }
                    return val;
                }, args;
                value = fn(value);
                if(this.options.allowPaging && value !== oldValue) {
                    args = {
                        newPageIndex: value
                    };
                    if(!this._onPageIndexChanging(args)) {
                        value = oldValue;
                    } else {
                        value = fn(args.newPageIndex);
                    }
                }
                return value;
            };
            gcuigrid.prototype._postset_pageIndex = function (value, oldValue) {
                if(this.options.allowPaging) {
                    var args = {
                        newPageIndex: value
                    };
                    if(this._customPagingEnabled()) {
                        this._convertWidgetsToOptions();
                        this._onPageIndexChanged(args);
                    } else {
                        this.ensureControl(true, {
                            afterRefresh: function () {
                                this._onPageIndexChanged(args);
                            }
                        });
                    }
                }
            };
            gcuigrid.prototype._preset_pageSize = function (value, oldValue) {
                if(isNaN(value)) {
                    throw "out of range";
                }
                if(value <= 0) {
                    value = 1;
                }
                return value;
            };
            gcuigrid.prototype._postset_pageSize = function (value, oldValue) {
                this._resetDataProperties();
                if(this.options.allowPaging && !this._customPagingEnabled()) {
                    this.ensureControl(true);
                }
            };
            gcuigrid.prototype._postset_pagerSettings = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_scrollMode = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_selectionMode = function (value, oldValue) {
                var selection = this.selection(), currentCell = this.currentCell(), hasSelection = this.selection().selectedCells().length();
                selection.beginUpdate();
                selection.clear();
                if(currentCell && currentCell._isValid() && hasSelection) {
                    selection._selectRange(new gcui.grid.cellInfoRange(currentCell, currentCell), false, false, gcui.grid.cellRangeExtendMode.none, null);
                }
                selection.endUpdate();
                this._view().toggleDOMSelection(value === "none");
            };
            gcuigrid.prototype._postset_showFilter = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_showGroupArea = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_showRowHeader = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._postset_staticRowIndex = function () {
                if(this.options.scrollMode !== "none") {
                    this.ensureControl(false);
                }
            };
            gcuigrid.prototype._postset_staticColumnIndex = function () {
                if(this.options.scrollMode !== "none") {
                    this.ensureControl(false);
                }
            };
            gcuigrid.prototype._postset_allowVirtualScrolling = function (value, oldValue) {
                this.ensureControl(false);
            };
            gcuigrid.prototype._preset_allowVirtualScrolling = function (value, oldValue) {
                if(isNaN(value) || value < 0) {
                    throw "out of range";
                }
                return value;
            };
            gcuigrid.prototype._activateSpinner = function () {
                if(!this._spinnerIsActivated) {
                    var css = this.options.uiCSS, loadingText = this.outerDiv.append("<div class=\"gcui-gcuigrid-overlay " + css.overlay + "\"></div>" + "<span class=\"gcui-gcuigrid-loadingtext " + css.content + " " + css.cornerAll + "\">" + "<span class=\"" + css.icon + " ui-icon-clock\"></span>" + this.options.loadingText + "</span>").find("> .gcui-gcuigrid-loadingtext");
                    loadingText.position({
                        my: "center",
                        at: "center center",
                        of: this.outerDiv,
                        collision: "none"
                    });
                    this._spinnerIsActivated = true;
                }
            };
            gcuigrid.prototype._customPagingEnabled = function () {
                return this.options.allowPaging && this.options.totalRows >= 0;
            };
            gcuigrid.prototype._deactivateSpinner = function () {
                if(this._spinnerIsActivated) {
                    try  {
                        this.outerDiv.find("> .gcui-gcuigrid-overlay, > .gcui-gcuigrid-loadingtext").remove();
                    }finally {
                        this._spinnerIsActivated = false;
                    }
                }
            };
            gcuigrid.prototype._columnWidgetsFactory = function ($node, columnOpt) {
                var columnWidget, clientType = columnOpt.clientType;
                if(!clientType && columnOpt.isBand) {
                    clientType = "c1band";
                }
                columnOpt = $.extend({
                }, columnOpt, {
                    disabled: this.options.disabled
                });
                try  {
                    $.data($node[0], "gcuigridowner", this);
                    switch(clientType) {
                        case "c1basefield":
                            columnWidget = $node.c1basefield(columnOpt);
                            break;
                        case "c1band":
                            columnWidget = $node.c1band(columnOpt);
                            break;
                        default:
                            columnWidget = $node.c1field(columnOpt);
                    }
                }finally {
                    $.removeData($node[0], "gcuigridowner");
                }
                return columnWidget;
            };
            gcuigrid.prototype._convertWidgetsToOptions = function () {
                if(this._initialized && this._mergeWidgetsWithOptions) {
                    this._ownerise(false);
                    this._widgetsToOptions();
                    this._ownerise(true);
                }
            };
            gcuigrid.prototype._field = function (name, value) {
                return gcui.grid.dataPrefix(this.element, this._data$prefix, name, value);
            };
            gcuigrid.prototype._removeField = function (name) {
                var internalDataName = this._data$prefix + name;
                this.element.removeData(internalDataName);
            };
            gcuigrid.prototype._prepareTotalsRequest = function (isLocal) {
                var leaves = this._field("leaves"), result, test;
                if(!leaves || !this.options.showFooter) {
                    return [];
                }
                result = $.map((leaves), function (element, index) {
                    if(!element.isBand && gcui.grid.validDataKey(element.dataKey) && element.aggregate && element.aggregate !== "none") {
                        if(isLocal) {
                            return [
                                {
                                    column: element,
                                    aggregate: element.aggregate
                                }
                            ];
                        } else {
                            return [
                                {
                                    dataKey: element.dataKey,
                                    aggregate: element.aggregate
                                }
                            ];
                        }
                    }
                    return null;
                });
                return result;
            };
            gcuigrid.prototype._widgetsToOptions = function () {
                var colOptionsList = gcui.grid.flatten(this.options.columns);
                $.each(this.columns(), function (index, colWidget) {
                    delete colWidget.options.columns;
                    var congruentColOption = colOptionsList[colWidget.options.travIdx];
                    $.extend(true, congruentColOption, colWidget.options);
                    congruentColOption.filterValue = colWidget.options.filterValue;
                    congruentColOption.filterOperator = colWidget.options.filterOperator;
                });
            };
            gcuigrid.prototype._recreateColumnWidgets = function () {
                $.each(this.columns(), function (index, item) {
                    item.destroy();
                });
                var columns = [], headerRows = this._headerRows(), visibleColumns, i, len, column, headerRowObj, th, columnWidget;
                if(headerRows && headerRows.length()) {
                    visibleColumns = [];
                    gcui.grid.traverse(this.options.columns, function (column) {
                        if(column.parentVis) {
                            visibleColumns.push(column);
                        }
                    });
                    for(i = 0 , len = visibleColumns.length; i < len; i++) {
                        column = visibleColumns[i];
                        headerRowObj = headerRows.item(column.thY);
                        th = gcui.grid.rowAccessor.getCell(headerRowObj, column.thX);
                        columnWidget = this._columnWidgetsFactory($(th), column);
                        columns.push(columnWidget.data(gcui.grid.widgetName(columnWidget)));
                    }
                }
                this._field("columns", columns);
            };
            gcuigrid.prototype._groupedColumns = function (force) {
                var result;
                force = !(result = this._field("groupedColumns")) || force;
                if(force) {
                    result = [];
                    var leaves = this._field("leaves") || [], rebuildIndexes = false, isGrouped = function (column) {
                        return column.groupInfo && column.groupInfo.position && (column.groupInfo.position !== "none");
                    };
                    $.each(leaves, function (i, column) {
                        if(isGrouped(column)) {
                            rebuildIndexes = rebuildIndexes || (column.groupedIndex === undefined);
                            if(!rebuildIndexes) {
                                result.push(column);
                            }
                        } else {
                            delete column.groupedIndex;
                        }
                    });
                    if(rebuildIndexes) {
                        $.each(leaves, function (i, column) {
                            if(isGrouped(column)) {
                                column.groupedIndex = result.length;
                                result.push(column);
                            }
                        });
                    } else {
                        result.sort(function (a, b) {
                            return a.groupedIndex - b.groupedIndex;
                        });
                        $.each(result, function (i, column) {
                            column.groupedIndex = i;
                        });
                    }
                    this._field("groupedColumns", result);
                }
                return result || [];
            };
            gcuigrid.prototype._ownerise = function (flag) {
                if(flag) {
                    var self = this;
                    gcui.grid.traverse(this.options.columns, function (column) {
                        column.owner = self;
                        var tmp, i, len;
                        if((tmp = column.groupInfo)) {
                            tmp.owner = column;
                            if(tmp.expandInfo) {
                                for(i = 0 , len = tmp.expandInfo.length; i < len; i++) {
                                    tmp.expandInfo[i].owner = tmp;
                                }
                            }
                        }
                    });
                } else {
                    gcui.grid.traverse(this.options.columns, function (column) {
                        delete column.owner;
                        var tmp, i, len;
                        if((tmp = column.groupInfo)) {
                            delete tmp.owner;
                            if(tmp.expandInfo) {
                                for(i = 0 , len = tmp.expandInfo.length; i < len; i++) {
                                    delete tmp.expandInfo[i].owner;
                                }
                            }
                        }
                    });
                }
            };
            gcuigrid.prototype._ensureRenderBounds = function (bounds) {
                var total = this._totalRowsCount();
                if(bounds.start < 0) {
                    bounds.start = 0;
                }
                bounds.start = Math.min(bounds.start, total - 1);
                if(bounds.end < 0) {
                    bounds.end = 0;
                }
                bounds.end = Math.min(bounds.end, total - 1);
                return bounds;
            };
            gcuigrid.prototype._refresh = function (userData) {
                new gcui.grid.grouper().group(this, this.sketchTable, this._field("leaves"));
                new gcui.grid.merger().merge(this.sketchTable, this._field("visibleLeaves"));
                var bounds = this._field("viewRenderBounds");
                this._ensureRenderBounds(bounds);
                if(this.options.scrollMode !== "none") {
                    this._field("view", new gcui.grid.fixedView(this, this._field("viewRenderBounds")));
                } else {
                    this._field("view", new gcui.grid.flatView(this, this._field("viewRenderBounds")));
                }
                this._render();
                this._ownerise(false);
                this._recreateColumnWidgets();
                this._ownerise(true);
                if(this.options.allowPaging) {
                    if(this.$topPagerDiv) {
                        this.$topPagerDiv.gcuipager(this._pagerSettings2PagerWidgetSettings()).css("zIndex", 5);
                    }
                    if(this.$bottomPagerDiv) {
                        this.$bottomPagerDiv.gcuipager(this._pagerSettings2PagerWidgetSettings()).css("zIndex", 5);
                    }
                }
            };
            gcuigrid.prototype._refreshVirtual = function (userData) {
                var scrollData = userData.virtualScrollData, diffData = {
                    top: 0,
                    bottom: 0
                };
                if(scrollData.data) {
                    diffData = this._processVirtualData(scrollData);
                }
                this._updateRowInfos(scrollData, diffData);
                this._renderVirtualIntoView(scrollData);
            };
            gcuigrid.prototype._updateRowInfos = function (scrollData, diffData) {
                var bounds = this._field("viewRenderBounds"), view = this._view(), newBounds = scrollData.newBounds, rows = this._view().bodyRows(), relMatch, i, diff, rowInfo;
                switch(scrollData.mode) {
                    case "reset":
                        break;
                    case "overlapBottom":
                        relMatch = {
                            start: newBounds.start - bounds.start,
                            end: bounds.end - bounds.start
                        };
                        diff = newBounds.start - bounds.start;
                        for(i = relMatch.start; i <= relMatch.end; i++) {
                            rowInfo = view._getRowInfo(rows.item(i), false);
                            rowInfo.sectionRowIndex -= diff;
                            rowInfo.dataItemIndex += diffData.top;
                            view._setRowInfo(rowInfo.$rows, rowInfo);
                        }
                        break;
                    case "overlapTop":
                        relMatch = {
                            start: bounds.start - bounds.start,
                            end: newBounds.end - bounds.start
                        };
                        diff = bounds.start - newBounds.start;
                        for(i = relMatch.start; i <= relMatch.end; i++) {
                            rowInfo = view._getRowInfo(rows.item(i), false);
                            rowInfo.sectionRowIndex += diff;
                            rowInfo.dataItemIndex += diffData.top;
                            view._setRowInfo(rowInfo.$rows, rowInfo);
                        }
                        break;
                }
            };
            gcuigrid.prototype._renderVirtualIntoView = function (scrollData) {
                var bounds = this._field("viewRenderBounds"), view = this._view(), fnDataItemIndex = function (sketchRow) {
                    return (sketchRow.rowType & gcui.grid.rowType.data) ? sketchRow.originalRowIndex : -1;
                }, match, i, sketchRow, sectionRowIndex;
                switch(scrollData.mode) {
                    case "reset":
                        view._clearBody();
                        for(i = scrollData.newBounds.start; i <= scrollData.newBounds.end; i++) {
                            sketchRow = this.sketchTable[i - this._dataOffset];
                            view._insertBodyRow(sketchRow, -1, fnDataItemIndex(sketchRow), i);
                        }
                        view._rebuildOffsets();
                        break;
                    case "overlapBottom":
                        match = {
                            start: scrollData.newBounds.start,
                            end: bounds.end
                        };
                        for(i = 0; i < match.start - bounds.start; i++) {
                            view._removeBodyRow(0, false);
                        }
                        for(i = match.end + 1; i <= scrollData.newBounds.end; i++) {
                            sketchRow = this.sketchTable[i - this._dataOffset];
                            view._insertBodyRow(sketchRow, -1, fnDataItemIndex(sketchRow), i);
                        }
                        break;
                    case "overlapTop":
                        match = {
                            start: bounds.start,
                            end: scrollData.newBounds.end
                        };
                        for(i = 0; i < bounds.end - scrollData.newBounds.end; i++) {
                            view._removeBodyRow(match.end - match.start + 1, false);
                        }
                        sectionRowIndex = 0;
                        for(i = scrollData.newBounds.start; i < bounds.start; i++) {
                            sketchRow = this.sketchTable[i - this._dataOffset];
                            view._insertBodyRow(sketchRow, sectionRowIndex++, fnDataItemIndex(sketchRow), i);
                        }
                        break;
                    default:
                        break;
                }
            };
            gcuigrid.prototype._processVirtualData = function (scrollData) {
                var dvw = this._dataViewWrapper, source = dvw.dataView().getSource(), dataItem, leaves = this._field("leaves"), i, alignedViewBounds, cachedBounds, exceeded = 0, dataDiff = {
                    top: 0,
                    bottom: 0
                }, rowAttributes, margin = this._serverSideVirtualScrollingMargin();
                switch(scrollData.mode) {
                    case "reset":
                        this.sketchTable.splice(0, this.sketchTable.length);
                        dvw._unsafeSplice(0, source.length);
                        this._dataOffset = scrollData.request.index;
                        for(i = 0; i < scrollData.data.length; i++) {
                            dvw._unsafePush(dataItem = scrollData.data[i]);
                            this.sketchTable.push(this._buildSketchRow(dvw._wrapDataItem(dataItem, i), leaves));
                        }
                        break;
                    case "overlapBottom":
                        for(i = 0; i < scrollData.data.length; i++) {
                            dvw._unsafePush(dataItem = scrollData.data[i]);
                            this.sketchTable.push(this._buildSketchRow(dvw._wrapDataItem(dataItem, source.length - 1), leaves));
                        }
                        dataDiff.bottom = scrollData.data.length;
                        break;
                    case "overlapTop":
                        for(i = scrollData.data.length - 1; i >= 0; i--) {
                            dvw._unsafeSplice(0, 0, dataItem = scrollData.data[i]);
                            this.sketchTable.splice(0, 0, this._buildSketchRow(dvw._wrapDataItem(dataItem, i), leaves));
                        }
                        this._dataOffset = scrollData.request.index;
                        dataDiff.top = scrollData.data.length;
                        break;
                }
                alignedViewBounds = this._ensureRenderBounds({
                    start: scrollData.newBounds.start,
                    end: scrollData.newBounds.end
                });
                cachedBounds = {
                    start: this._dataOffset,
                    end: this._dataOffset + source.length - 1
                };
                exceeded = (cachedBounds.end - alignedViewBounds.end) - margin;
                if(exceeded > 0) {
                    dataDiff.bottom -= exceeded;
                    dvw._unsafeSplice(source.length - exceeded, exceeded);
                    this.sketchTable.splice(this.sketchTable.length - exceeded, exceeded);
                }
                exceeded = (alignedViewBounds.start - cachedBounds.start) - margin;
                if(exceeded > 0) {
                    dataDiff.top -= exceeded;
                    dvw._unsafeSplice(0, exceeded);
                    this.sketchTable.splice(0, exceeded);
                    this._dataOffset += exceeded;
                }
                for(i = 0; i < this.sketchTable.length; i++) {
                    this.sketchTable[i].originalRowIndex = i;
                }
                dvw._refreshSilent();
                return dataDiff;
            };
            gcuigrid.prototype._needToCreatePagerItem = function () {
                return this.options.allowPaging === true;
            };
            gcuigrid.prototype._isMobileEnv = function () {
                return this._isMobile;
            };
            gcuigrid.prototype._render = function () {
                var view = this._view(), o = this.options, css = this.options.uiCSS, content;
                view.render();
                content = this.outerDiv;
                if(o.scrollMode !== "none") {
                    content = this.outerDiv.find("div.gcui-gcuigrid-scroller:first");
                }
                this.$superPanelHeader = null;
                if(this.$topPagerDiv) {
                    if (this.$topPagerDiv.data("gcui-gcuipager")) {
                        this.$topPagerDiv.gcuipager("destroy");
                    }
                    this.$topPagerDiv.remove();
                }
                this.$topPagerDiv = null;
                if(this._needToCreatePagerItem() && ((o.pagerSettings.position === "top") || (o.pagerSettings.position === "topAndBottom"))) {
                    if(!this.$topPagerDiv) {
                        content.prepend(this.$superPanelHeader = $("<div class=\"gcui-superpanel-header\"></div>"));
                        this.$superPanelHeader.prepend(this.$topPagerDiv = $("<div class=\"gcui-gcuigrid-header " + css.header + " " + css.cornerTop + "\"></div>"));
                    }
                }
                if(o.showGroupArea) {
                    this._processGroupArea(content);
                } else {
                    this.$groupArea = null;
                }
                if(this.$bottomPagerDiv) {
                    if (this.$bottomPagerDiv.data("gcui-gcuipager")) {
                        this.$bottomPagerDiv.gcuipager("destroy");
                    }
                    this.$bottomPagerDiv.remove();
                }
                this.$bottomPagerDiv = null;
                if(this._needToCreatePagerItem() && ((o.pagerSettings.position === "bottom") || (o.pagerSettings.position === "topAndBottom"))) {
                    if(!this.$bottomPagerDiv) {
                        content.append(this.$bottomPagerDiv = $("<div class=\"gcui-gcuigrid-footer gcui-superpanel-footer " + css.stateDefault + " " + css.cornerBottom + "\"></div>"));
                    }
                }
            };
            gcuigrid.prototype._processGroupArea = function (content) {
                var self = this, groupCollection = this._groupedColumns(), groupWidgetCollection = [];
                this.$groupArea = $("<div class=\"" + this.options.uiCSS.content + " " + this.options.uiCSS.helperClearFix + "\"></div>");
                if(groupCollection.length > 0) {
                    $.each(groupCollection, function (index, item) {
                        var groupElement = $("<a href=\"#\"></a>").appendTo(self.$groupArea);
                        try  {
                            $.data(groupElement[0], "gcuigridowner", self);
                            groupElement.c1groupedfield($.extend({
                            }, {
                                allowMoving: item.allowMoving,
                                allowSort: item.allowSort,
                                dataIndex: item.dataIndex,
                                headerText: item.headerText,
                                isBand: item.isBand,
                                isLeaf: item.isLeaf,
                                linearIdx: item.linearIdx,
                                parentIdx: item.parentIdx,
                                sortDirection: item.sortDirection,
                                travIdx: item.travIdx,
                                groupedIndex: item.groupedIndex
                            }, {
                                disabled: self.options.disabled
                            }));
                        }finally {
                            $.removeData(groupElement[0], "gcuigridowner");
                        }
                        groupWidgetCollection.push(groupElement.data("gcui-c1groupedfield"));
                    });
                } else {
                    this.$groupArea.addClass("gcui-gcuigrid-group-area").css("padding", 0).html(this.options.groupAreaCaption || "&nbsp;");
                }
                this._field("groupedWidgets", groupWidgetCollection);
                if(!this.$superPanelHeader) {
                    content.prepend(this.$superPanelHeader = $("<div class=\"gcui-superpanel-header\"></div>"));
                }
                this.$superPanelHeader.prepend(this.$groupArea);
                this._dragndrop(true).attachGroupArea(this.$groupArea);
            };
            gcuigrid.prototype._pagerSettings2PagerWidgetSettings = function () {
                return $.extend({
                }, this.options.pagerSettings, {
                    disabled: this.options.disabled,
                    pageCount: this.pageCount(),
                    pageIndex: this.options.pageIndex,
                    pageIndexChanging: $.proxy(this._onPagerWidgetPageIndexChanging, this),
                    pageIndexChanged: $.proxy(this._onPagerWidgetPageIndexChanged, this)
                });
            };
            gcuigrid.prototype._showRowHeader = function () {
                return (this.options.showRowHeader === true) && (this.options.staticColumnsAlignment !== "right");
            };
            gcuigrid.prototype._attachEvents = function () {
                var view = this._view(), $fe = view.focusableElement(), self = this;
                $fe.bind("keydown." + this.widgetName, $.proxy(this._onKeyDown, this));
                $fe.bind("keypress." + this.widgetName, $.proxy(this._onKeyPress, this));
                $.each(view.subTables(), function (index, element) {
                    var domTable = element.element();
                    if(domTable) {
                        if(domTable.tHead) {
                            $(domTable.tHead).bind("click." + self.widgetName, $.proxy(self._onClick, self));
                        }
                        if(domTable.tBodies.length) {
                            $(domTable.tBodies[0]).bind("click." + self.widgetName, $.proxy(self._onClick, self)).bind("dblclick." + self.widgetName, $.proxy(self._onDblClick, self)).bind("mousemove." + self.widgetName, $.proxy(self._onMouseMove, self)).bind("mouseout." + self.widgetName, $.proxy(self._onMouseOut, self));
                        }
                    }
                });
                $(window).bind("resize." + this.widgetName + "." + this._eventUID, $.proxy(this._onWindowResize, this));
            };
            gcuigrid.prototype._detachEvents = function (destroy) {
                var view = this._view(), self = this, $fe;
                this._windowResizeTimer = 0;
                $(window).unbind("resize." + this.widgetName + "." + this._eventUID);
                if(view) {
                    $fe = view.focusableElement();
                    $fe.unbind("keydown." + this.widgetName);
                    $fe.unbind("keypress." + this.widgetName);
                    $.each(view.subTables(), function () {
                        var domTable = this.element();
                        if(domTable) {
                            if(domTable.tHead) {
                                $(domTable.tHead).unbind("." + self.widgetName);
                            }
                            if(domTable.tBodies.length) {
                                $(domTable.tBodies[0]).unbind("." + self.widgetName);
                            }
                        }
                    });
                }
            };
            gcuigrid.prototype._handleSort = function (column, multiSort) {
                var columns = this.options.columns, travIdx = column.travIdx, newSortDirection, args;
                if(column && this.options.allowSorting) {
                    newSortDirection = ((column.sortDirection === "none") ? "ascending" : ((column.sortDirection === "ascending") ? "descending" : "ascending"));
                    args = {
                        column: column,
                        sortDirection: newSortDirection,
                        sortCommand: column.dataKey + " " + (newSortDirection === "ascending" ? "asc" : "desc")
                    };
                    if(this._onColumnSorting(args)) {
                        column.sortDirection = args.sortDirection;
                        if(multiSort) {
                            column.sortOrder = this._customSortOrder++;
                        } else {
                            this._customSortOrder = 1000;
                            $.each(this.columns(), function (index, item) {
                                item.options.sortOrder = 0;
                                if(item.options.travIdx !== travIdx && !(item.options.groupInfo && item.options.groupInfo.position !== "none")) {
                                    item.options.sortDirection = "none";
                                }
                            });
                            gcui.grid.traverse(columns, function (item) {
                                item.sortOrder = 0;
                                if(item.travIdx !== travIdx && !(item.groupInfo && item.groupInfo.position !== "none")) {
                                    item.sortDirection = "none";
                                }
                            });
                        }
                        args = {
                            column: column,
                            sortDirection: column.sortDirection,
                            sortCommand: column.dataKey + " " + (column.sortDirection === "ascending" ? "asc" : "desc")
                        };
                        if(this._customPagingEnabled()) {
                            this._convertWidgetsToOptions();
                            this._onColumnSorted(args);
                        } else {
                            this.ensureControl(true, {
                                afterRefresh: function () {
                                    this._onColumnSorted(args);
                                }
                            });
                        }
                    }
                }
            };
            gcuigrid.prototype._handleDragnDrop = function (dragTravIdx, dropTravIdx, at, dragInGroup, dropInGroup) {
                var drag = gcui.grid.getColumnByTravIdx(this.options.columns, dragTravIdx), drop = gcui.grid.getColumnByTravIdx(this.options.columns, dropTravIdx), dragSource = dragInGroup ? "groupArea" : "columns", dropSource = dropInGroup ? "groupArea" : "columns";
                if(dropInGroup) {
                    if(this._onColumnGrouping({
                        drag: drag.found,
                        drop: drop ? drop.found : null,
                        dragSource: dragSource,
                        dropSource: dropSource,
                        at: at
                    })) {
                        this.ensureControl(true, {
                            beforeRefresh: function () {
                                if(!drop) {
                                    drag.found.groupedIndex = 0;
                                } else {
                                    switch(at) {
                                        case "left":
                                            drag.found.groupedIndex = drop.found.groupedIndex - 0.5;
                                            break;
                                        case "right":
                                            drag.found.groupedIndex = drop.found.groupedIndex + 0.5;
                                            break;
                                    }
                                }
                                if(!dragInGroup) {
                                    $.extend(true, drag.found, {
                                        groupInfo: {
                                            position: "header"
                                        }
                                    });
                                }
                            },
                            afterRefresh: function () {
                                this._onColumnGrouped({
                                    drag: drag.found,
                                    drop: drop ? drop.found : null,
                                    dragSource: dragSource,
                                    dropSource: dropSource,
                                    at: at
                                });
                            }
                        });
                    }
                } else {
                    if(this._onColumnDropping({
                        drag: drag.found,
                        drop: drop.found,
                        at: at
                    })) {
                        this.ensureControl(false, {
                            beforeRefresh: function () {
                                drag.at.splice(drag.found.linearIdx, 1);
                                switch(at) {
                                    case "left":
                                        if(drag.at === drop.at && drag.found.linearIdx < drop.found.linearIdx) {
                                            drop.at.splice(drop.found.linearIdx - 1, 0, drag.found);
                                        } else {
                                            drop.at.splice(drop.found.linearIdx, 0, drag.found);
                                        }
                                        break;
                                    case "right":
                                        if(drag.at === drop.at && drag.found.linearIdx < drop.found.linearIdx) {
                                            drop.at.splice(drop.found.linearIdx, 0, drag.found);
                                        } else {
                                            drop.at.splice(drop.found.linearIdx + 1, 0, drag.found);
                                        }
                                        break;
                                    case "center":
                                        drop.found.columns.push(drag.found);
                                        break;
                                }
                                gcui.grid.setTraverseIndex(this.options.columns);
                            },
                            afterRefresh: function () {
                                this._onColumnDropped({
                                    drag: drag.found,
                                    drop: drop.found,
                                    at: at
                                });
                            }
                        });
                    }
                }
            };
            gcuigrid.prototype._handleFilter = function (column, rawOperator, rawValue) {
                var operator = (new gcui.grid.filterOperatorsCache(this)).getByName(rawOperator), value, ok, args;
                if(operator) {
                    if(operator.arity > 1) {
                        value = this.parse(column.options, rawValue);
                        ok = (value !== null && (gcui.grid.getDataType(column.options) === "string" || !isNaN(value)));
                    } else {
                        ok = true;
                    }
                    if(ok) {
                        args = {
                            column: column.options,
                            operator: operator.name,
                            value: value
                        };
                        if(this._onColumnFiltering(args)) {
                            column.options.filterValue = args.value;
                            column.options.filterOperator = args.operator;
                            this._resetDataProperties();
                            if(this._customPagingEnabled()) {
                                this._convertWidgetsToOptions();
                                this._onColumnFiltered({
                                    column: column.options
                                });
                            } else {
                                this.ensureControl(true, {
                                    afterRefresh: function () {
                                        this._onColumnFiltered({
                                            column: column.options
                                        });
                                    }
                                });
                            }
                        }
                    }
                }
            };
            gcuigrid.prototype._handleUngroup = function (columnTravIdx) {
                var column = gcui.grid.getColumnByTravIdx(this.options.columns, columnTravIdx), result;
                if(column && column.found) {
                    result = column.found;
                    if(this._onColumnUngrouping({
                        column: result
                    })) {
                        this.ensureControl(true, {
                            beforeRefresh: function () {
                                delete result.groupedIndex;
                                $.extend(true, result, {
                                    groupInfo: {
                                        position: "none"
                                    }
                                });
                            },
                            afterRefresh: function () {
                                this._onColumnUngrouped({
                                    column: result
                                });
                            }
                        });
                    }
                }
            };
            gcuigrid.prototype._onVirtualScrolling = function (newBounds, request, mode, scrollIndex, completeCallback, data) {
                this.ensureControl(this._serverSideVirtualScrolling(), {
                    virtualScrollData: {
                        newBounds: newBounds,
                        request: request,
                        mode: mode,
                        data: data
                    },
                    afterRefresh: function (userData) {
                        var bounds = this._field("viewRenderBounds");
                        $.extend(bounds, userData.virtualScrollData.newBounds);
                        this._view()._adjustRowsHeights();
                        completeCallback(scrollIndex);
                    }
                });
            };
            gcuigrid.prototype._handleVirtualScrolling = function (scrollIndex, completeCallback) {
                var bounds = this._field("viewRenderBounds"), newBounds = this._ensureRenderBounds({
                    start: scrollIndex,
                    end: scrollIndex + this.options.pageSize - 1
                }), cachedDataBounds = this._ensureRenderBounds({
                    start: this._dataOffset,
                    end: this._dataOffset + this._dataViewWrapper.dataView().count() - 1
                }), request = null, mode;
                if(newBounds.start > bounds.end || newBounds.end < bounds.start) {
                    mode = "reset";
                } else {
                    if(newBounds.start > bounds.start) {
                        mode = "overlapBottom";
                    } else {
                        if(newBounds.start < bounds.start) {
                            mode = "overlapTop";
                        } else {
                        }
                    }
                }
                if(this._serverSideVirtualScrolling()) {
                    switch(mode) {
                        case "reset":
                            request = {
                                index: scrollIndex,
                                maxCount: this.options.pageSize
                            };
                            break;
                        case "overlapBottom":
                            if(newBounds.end > cachedDataBounds.end) {
                                request = {
                                    index: cachedDataBounds.end + 1,
                                    maxCount: this.options.pageSize
                                };
                            }
                            break;
                        case "overlapTop":
                            if(newBounds.start < cachedDataBounds.start) {
                                request = {
                                    index: Math.max(0, cachedDataBounds.start - this.options.pageSize),
                                    maxCount: 0
                                };
                                request.maxCount = cachedDataBounds.start - request.index;
                            }
                            break;
                    }
                }
                if(mode !== "none") {
                    this._onVirtualScrolling(newBounds, request, mode, scrollIndex, completeCallback);
                }
            };
            gcuigrid.prototype._serverSideVirtualScrolling = function () {
                return false;
            };
            gcuigrid.prototype._serverSideVirtualScrollingMargin = function () {
                return this.options.pageSize * 2;
            };
            gcuigrid.prototype._onColumnDropping = function (args) {
                return this._trigger("columnDropping", null, args);
            };
            gcuigrid.prototype._onColumnDropped = function (args) {
                this._trigger("columnDropped", null, args);
            };
            gcuigrid.prototype._onColumnGrouping = function (args) {
                return this._trigger("columnGrouping", null, args);
            };
            gcuigrid.prototype._onColumnGrouped = function (args) {
                this._trigger("columnGrouped", null, args);
            };
            gcuigrid.prototype._onColumnUngrouping = function (args) {
                return this._trigger("columnUngrouping", null, args);
            };
            gcuigrid.prototype._onColumnUngrouped = function (args) {
                this._trigger("columnUngrouped", null, args);
            };
            gcuigrid.prototype._onColumnFiltering = function (args) {
                return this._trigger("filtering", null, args);
            };
            gcuigrid.prototype._onColumnFiltered = function (args) {
                this._trigger("filtered", null, args);
            };
            gcuigrid.prototype._onFilterOperatorsListShowing = function (args) {
                this._trigger("filterOperatorsListShowing", null, args);
            };
            gcuigrid.prototype._onColumnSorting = function (args) {
                return this._trigger("sorting", null, args);
            };
            gcuigrid.prototype._onColumnSorted = function (args) {
                this._trigger("sorted", null, args);
            };
            gcuigrid.prototype._onCurrentCellChanged = function (e, info) {
                var o = this.options, currentCell = this.currentCell();
                var dataIndex = this._gridDataRowIndexToDataView(currentCell.rowIndex());
                this._dataViewWrapper.currentPosition(dataIndex);
                if(o.scrollMode !== "none" && currentCell && !currentCell.isEqual(gcui.grid.cellInfo.outsideValue)) {
                    (this._view()).scrollTo(currentCell);
                }
                this._trigger("currentCellChanged");
                var eventType = (e && (e.type || "").toLowerCase()) || "";
                if(info.changeSelection) {
                    var selection = this.selection(), selectionMode = this.options.selectionMode.toLowerCase(), extendMode = info.selectionExtendMode || gcui.grid.cellRangeExtendMode.none;
                    selection.beginUpdate();
                    if(eventType) {
                        if(currentCell._isValid()) {
                            switch(eventType) {
                                case "click":
                                    if(!e.shiftKey || (!selection._multipleRangesAllowed() && selectionMode !== "singlerange")) {
                                        selection._startNewTransaction(currentCell);
                                    }
                                    if(e.shiftKey && e.ctrlKey) {
                                        selection._clearRange(new gcui.grid.cellInfoRange(currentCell, currentCell), extendMode);
                                    } else {
                                        selection._selectRange(new gcui.grid.cellInfoRange(selection._anchorCell(), currentCell), e.ctrlKey, e.shiftKey, extendMode, null);
                                    }
                                    break;
                                case "keydown":
                                    if(!e.shiftKey || (!selection._multipleRangesAllowed() && selectionMode !== "singlerange")) {
                                        selection._startNewTransaction(currentCell);
                                    }
                                    selection._selectRange(new gcui.grid.cellInfoRange(selection._anchorCell(), currentCell), false, e.shiftKey, gcui.grid.cellRangeExtendMode.none, null);
                                    break;
                            }
                        }
                    } else {
                        selection.clear();
                        if(currentCell._isValid()) {
                            selection._startNewTransaction(currentCell);
                            selection._selectRange(new gcui.grid.cellInfoRange(currentCell, currentCell), false, false, gcui.grid.cellRangeExtendMode.none, null);
                        }
                    }
                    selection.endUpdate();
                }
                if(eventType === "click" && this._editBySingleClick()) {
                    this._beginEditInternal(e);
                }
            };
            gcuigrid.prototype._onPageIndexChanging = function (args) {
                return this._trigger("pageIndexChanging", null, args);
            };
            gcuigrid.prototype._onPageIndexChanged = function (args) {
                this._trigger("pageIndexChanged", null, args);
            };
            gcuigrid.prototype._onPagerWidgetPageIndexChanging = function (sender, args) {
                args.handled = true;
            };
            gcuigrid.prototype._onPagerWidgetPageIndexChanged = function (sender, args) {
                this._setOption("pageIndex", args.newPageIndex);
            };
            gcuigrid.prototype._onRendering = function (userData) {
                var view = this._view();
                this._rendered = false;
                if(userData.virtualScrollData) {
                    this.selection().clear();
                    if(this.options.highlightCurrentCell) {
                        this._highlightCellPosition(this.currentCell(), false);
                    }
                } else {
                    if(view) {
                        view.dispose();
                    }
                    this._detachEvents(false);
                    this.element.detach();
                    this.element.empty();
                    this.outerDiv.empty();
                    this.outerDiv.append(this.element);
                    if(this._field("selectionui")) {
                        this._field("selectionui").dispose();
                        this._field("selectionui", null);
                    }
                    if(this._field("resizer")) {
                        this._field("resizer").dispose();
                        this._field("resizer", null);
                    }
                    if(this._field("frozener")) {
                        this._field("frozener").dispose();
                        this._field("frozener", null);
                    }
                }
                var cc = (this._field("currentCell") || gcui.grid.cellInfo.outsideValue)._clone();
                cc._setGridView(null);
                this._field("currentCellPrev", cc);
                this._field("currentCell", null);
                this._trigger("rendering");
            };
            gcuigrid.prototype._onRendered = function (userData) {
                var view = this._view(), currentCell, resizer, hasSelection = this.selection().selectedCells().length() > 0;
                this._rendered = true;
                this._field("selection", null);
                this._setAttr(view.focusableElement(), "tabIndex", 0);
                currentCell = this._field("currentCellPrev");
                currentCell = this._currentCellFromDataView(currentCell.cellIndex());
                currentCell._isEdit(false);
                if(!currentCell._isValid() && this.options.showSelectionOnRender) {
                    currentCell = this._getFirstDataRowCell(0);
                }
                this.currentCell(currentCell, null, true);
                if(!userData.virtualScrollData) {
                    this._attachEvents();
                    this._selectionui(true);
                    resizer = new gcui.grid.uiResizer(this);
                    $.each(this.columns(), function (index, colWidget) {
                        var o = colWidget.options;
                        if(o.visible && o.parentVis && o.isLeaf) {
                            resizer.addElement(colWidget);
                        }
                    });
                    this._field("resizer", resizer);
                    view.updateSplits(this._scrollingState);
                    if(this.options.scrollMode !== "none") {
                        this._field("frozener", new gcui.grid.uiFrozener(this));
                    }
                }
                this._renderCounter++;
                this._trigger("rendered");
            };
            gcuigrid.prototype._onClick = function (e) {
                if(!this._canInteract() || !e.target) {
                    return;
                }
                var view = this._view(), clickedCell = this._findUntilOuterDiv(e.target, {
                    td: true,
                    th: true
                }), $row, clickedCellInfo, extendMode = gcui.grid.cellRangeExtendMode.none, currentCell, selection;
                if(clickedCell) {
                    if($(e.target).hasClass("gcui-gcuigrid-grouptogglebtn")) {
                        this._onGroupBtnClick(e);
                        e.stopPropagation();
                        return false;
                    }
                    $row = $(clickedCell).closest("tr");
                    if(!$row.length) {
                        return;
                    }
                    clickedCellInfo = view.getAbsoluteCellInfo(clickedCell);
                    if($row.hasClass("gcui-gcuigrid-datarow") || $row.hasClass("gcui-gcuigrid-headerrow")) {
                        if(clickedCellInfo.cellIndex() < 0 || clickedCellInfo.rowIndex() < 0) {
                            if(clickedCellInfo.rowIndex() >= 0) {
                                clickedCellInfo = new gcui.grid.cellInfo(0, clickedCellInfo.rowIndex());
                                extendMode = gcui.grid.cellRangeExtendMode.row;
                            } else {
                                clickedCellInfo = this._getFirstDataRowCell(clickedCellInfo.cellIndex());
                                extendMode = gcui.grid.cellRangeExtendMode.column;
                            }
                        }
                        this._changeCurrentCell(e, clickedCellInfo, {
                            changeSelection: true,
                            setFocus: !$(e.target).is(":focus"),
                            selectionExtendMode: extendMode
                        });
                    }
                    var cellClickedArgs = {
                        cell: clickedCellInfo
                    };
                    this._trigger("cellClicked", null, cellClickedArgs);
                }
            };
            gcuigrid.prototype._onDblClick = function (e) {
                if(!this._editBySingleClick()) {
                    this._beginEditInternal(e);
                }
            };
            gcuigrid.prototype._onGroupBtnClick = function (e) {
                if(!this._canInteract()) {
                    return;
                }
                var $row = $(e.target).closest("tr"), gh = gcui.grid.groupHelper, groupInfo = gh.getGroupInfo($row[0]), column, group;
                if(groupInfo) {
                    column = gh.getColumnByGroupLevel(this._field("leaves"), groupInfo.level);
                    if(column) {
                        group = column.groupInfo.expandInfo[groupInfo.index];
                        if(group.isExpanded) {
                            group.collapse();
                        } else {
                            group.expand(e.shiftKey);
                        }
                        this.setSize();
                    }
                }
            };
            gcuigrid.prototype._onKeyDown = function (e) {
                if(!this._canInteract()) {
                    return true;
                }
                var tag = (e.target).tagName.toLowerCase(), canChangePos = false, nextPos, currentCell = this.currentCell(), selection, keyCodeEnum = gcui.getKeyCodeEnum();
                if((tag === "input" || tag === "option" || tag === "select" || tag === "textarea") && ($(e.target).closest("tr.gcui-gcuigrid-datarow").length === 0)) {
                    return true;
                }
                if(this.options.allowEditing) {
                    if((e.which === keyCodeEnum.ESCAPE || e.which === 113) && (currentCell._isValid() && currentCell._isEdit())) {
                        this._endEditInternal(e);
                        return false;
                    } else {
                        if(e.which === 113) {
                            this._beginEditInternal(e);
                            return false;
                        }
                    }
                }
                if(!this.options.allowKeyboardNavigation || (e.which === keyCodeEnum.TAB)) {
                    return true;
                }
                var currentCellAbs, dataRange, vScrollingOffset = 0;
                if(this._allowVirtualScrolling) {
                    vScrollingOffset = this._scrollingState.index;
                    currentCellAbs = new gcui.grid.cellInfo(currentCell.cellIndex(), currentCell.rowIndex() + vScrollingOffset);
                    dataRange = this._getDataCellsRange(true);
                } else {
                    currentCellAbs = currentCell;
                    dataRange = this._getDataCellsRange(false);
                }
                var moveDown = false, moveUp = false;
                switch(e.which) {
                    case keyCodeEnum.DOWN:
                    case keyCodeEnum.PAGE_DOWN:
                        moveDown = true;
                    case keyCodeEnum.UP:
                    case keyCodeEnum.PAGE_UP:
                        moveUp = true;
                    case keyCodeEnum.LEFT:
                    case keyCodeEnum.RIGHT:
                    case keyCodeEnum.HOME:
                    case keyCodeEnum.END:
                    case keyCodeEnum.TAB:
                        nextPos = this._getNextCurrencyPos(dataRange, currentCellAbs, e.keyCode, e.shiftKey);
                        canChangePos = this._canMoveToAnotherCell(e.target, e.which);
                        break;
                }
                if(canChangePos) {
                    var renderedRange = this._getDataCellsRange(false), self = this, nextAbsPos;
                    if(this._allowVirtualScrolling() && (moveDown && (nextPos.rowIndex > renderedRange.bottomRight().rowIndex() + vScrollingOffset)) || (moveUp && (nextPos.rowIndex - vScrollingOffset < renderedRange.topLeft().rowIndex()))) {
                        var scrollToIndex = nextPos.rowIndex;
                        if(moveDown) {
                            scrollToIndex -= renderedRange._height();
                        }
                        (this._view()).vsUI.scrollTo(scrollToIndex, function () {
                            self._changeCurrentCell(e, new gcui.grid.cellInfo(nextPos.cellIndex, nextPos.rowIndex - self._scrollingState.index), {
                                changeSelection: true,
                                setFocus: true
                            });
                            $(self._view().focusableElement()).focus();
                        });
                    } else {
                        this._changeCurrentCell(e, new gcui.grid.cellInfo(nextPos.cellIndex, nextPos.rowIndex - vScrollingOffset), {
                            changeSelection: true,
                            setFocus: true
                        });
                    }
                    return false;
                }
                return true;
            };
            gcuigrid.prototype._onKeyPress = function (e) {
                if(this._canInteract() && this.options.allowEditing) {
                    var charCode = e.which, currentCell = this.currentCell(), tag, table, domSubTables;
                    if(charCode && currentCell._isValid() && !currentCell._isEdit()) {
                        tag = (e.target).tagName.toLowerCase();
                        if(tag !== "input" && tag !== "option" && tag !== "select" && tag !== "textarea") {
                            table = $(e.target).closest(".gcui-gcuigrid-table");
                            if(table.length) {
                                domSubTables = $.map(this._view().subTables(), function (item, index) {
                                    return item.element();
                                });
                                if($.inArray(table[0], domSubTables) >= 0) {
                                    if ($.gcuiutil.charValidator.isPrintableChar(String.fromCharCode(charCode))) {
                                        this._beginEditInternal(e);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            };
            gcuigrid.prototype._onMouseMove = function (e) {
                var view = this._view(), frozener = this._field("frozener"), hoveredCell, $hoveredRow, hoveredCellInfo, rowIndex, rowObj, rowInfo, $rs = gcui.grid.renderState;
                if(!this.options.highlightOnHover || !this._canInteract() || (frozener && frozener.inProgress())) {
                    return;
                }
                hoveredCell = this._findUntilOuterDiv(e.target, {
                    td: true,
                    th: true
                });
                if(hoveredCell) {
                    $hoveredRow = $(hoveredCell).closest("tr");
                    if(!$hoveredRow.length || !($hoveredRow.hasClass("gcui-gcuigrid-datarow") || $hoveredRow.hasClass("gcui-gcuigrid-headerrow"))) {
                        return;
                    }
                    hoveredCellInfo = view.getAbsoluteCellInfo(hoveredCell);
                    rowIndex = this._field("hoveredRow");
                    if(hoveredCellInfo.rowIndex() !== rowIndex) {
                        if(rowIndex !== undefined) {
                            rowObj = this._rows().item(rowIndex);
                            if(rowObj) {
                                rowInfo = view._getRowInfo(rowObj);
                                view._changeRowRenderState(rowInfo, $rs.hovered, false);
                                this.rowStyleFormatter.format(rowInfo);
                            }
                        }
                        rowIndex = hoveredCellInfo.rowIndex();
                        this._field("hoveredRow", rowIndex);
                        if(rowIndex >= 0) {
                            rowObj = this._rows().item(rowIndex);
                            if(rowObj) {
                                rowInfo = view._getRowInfo(rowObj);
                                view._changeRowRenderState(rowInfo, $rs.hovered, true);
                                this.rowStyleFormatter.format(rowInfo);
                            }
                        }
                    }
                }
            };
            gcuigrid.prototype._onMouseOut = function (e) {
                if(!this._canInteract()) {
                    return;
                }
                if($(e.relatedTarget).closest(".gcui-gcuigrid-data").length === 0) {
                    var hovRowIndex = this._field("hoveredRow"), rowObj, rowInfo, view = this._view();
                    if(hovRowIndex >= 0) {
                        rowObj = this._rows().item(hovRowIndex);
                        if(rowObj) {
                            rowInfo = view._getRowInfo(rowObj);
                            view._changeRowRenderState(rowInfo, gcui.grid.renderState.hovered, false);
                            this.rowStyleFormatter.format(rowInfo);
                        }
                    }
                }
            };
            gcuigrid.prototype._onWindowResize = function (e) {
                var self = this;
                if(this._windowResizeTimer > 0) {
                    window.clearTimeout(this._windowResizeTimer);
                    this._windowResizeTimer = 0;
                }
                if(this._windowResizeTimer !== -1) {
                    this._windowResizeTimer = window.setTimeout(function () {
                        self._windowResizeTimer = -1;
                        try  {
                            if(!self._destroyed && self._initialized && self.element.parent().length) {
                                self.setSize();
                            }
                        }finally {
                            self._windowResizeTimer = 0;
                        }
                    }, 50);
                }
            };
            gcuigrid.prototype._fieldResized = function (fieldWidget, oldWidth, newWidth) {
                if(oldWidth < 0) {
                    oldWidth = 0;
                }
                if(newWidth <= 0) {
                    newWidth = 1;
                }
                var resizingArgs = {
                    column: fieldWidget.options,
                    oldWidth: oldWidth,
                    newWidth: newWidth
                };
                if(this._trigger("columnResizing", null, resizingArgs) !== false) {
                    if(isNaN(resizingArgs.newWidth) || resizingArgs.newWidth < 0) {
                        resizingArgs.newWidth = 1;
                    }
                    fieldWidget.option("width", resizingArgs.newWidth);
                    var resizedArgs = {
                        column: fieldWidget.options
                    };
                    this._trigger("columnResized", null, resizedArgs);
                }
            };
            gcuigrid.prototype._changeCurrentCell = function (e, cellInfo, info) {
                var currentCell = this.currentCell(), dataRange = this._getDataCellsRange(), currentCellChangingArgs, cellEditCompleted, highlight = this.options.highlightCurrentCell, domCell;
                if((dataRange._isValid() && dataRange._containsCellInfo(cellInfo)) || (cellInfo.isEqual(gcui.grid.cellInfo.outsideValue))) {
                    if(currentCell.cellIndex() !== cellInfo.cellIndex() || currentCell.rowIndex() !== cellInfo.rowIndex()) {
                        currentCellChangingArgs = {
                            cellIndex: cellInfo.cellIndex(),
                            rowIndex: cellInfo.rowIndex(),
                            oldCellIndex: currentCell.cellIndex(),
                            oldRowIndex: currentCell.rowIndex()
                        };
                        if(this._trigger("currentCellChanging", null, currentCellChangingArgs)) {
                            cellEditCompleted = false;
                            if(!this.options.allowEditing || !currentCell._isEdit() || (cellEditCompleted = this._endEditInternal(null))) {
                                if(dataRange._containsCellInfo(currentCell)) {
                                    if(highlight) {
                                        this._highlightCellPosition(currentCell, false);
                                    }
                                    if(domCell = currentCell.tableCell()) {
                                        $(domCell).removeAttr("tabIndex");
                                    }
                                }
                                currentCell = cellInfo._clone();
                                currentCell._setGridView(this);
                                if(highlight) {
                                    this._highlightCellPosition(currentCell, true);
                                }
                                if(domCell = currentCell.tableCell()) {
                                    $(domCell).attr("tabIndex", 0);
                                    if(info.setFocus && !currentCell._isEdit()) {
                                    }
                                }
                                this._field("currentCell", currentCell);
                                this._onCurrentCellChanged(e, info);
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if(highlight) {
                            this._highlightCellPosition(currentCell, true);
                        }
                        if(domCell = currentCell.tableCell()) {
                            $(domCell).attr("tabIndex", 0);
                        }
                        if(e && !currentCell._isEdit() && this._editBySingleClick()) {
                            this._beginEditInternal(e);
                        }
                    }
                } else {
                }
                return true;
            };
            gcuigrid.prototype._highlightCellPosition = function (cellInfo, add) {
                if(cellInfo && cellInfo._isValid()) {
                    var x = cellInfo.cellIndexAbs(), y = cellInfo.rowIndexAbs(), $rs = gcui.grid.renderState, view = this._view(), rowInfo, obj, state;
                    obj = view.getHeaderCell(x);
                    if(obj) {
                        rowInfo = view._getRowInfo(this._headerRows().item(cellInfo.column().thY));
                        obj = $(obj);
                        state = view._changeCellRenderState(obj, $rs.current, add);
                        this.cellStyleFormatter.format(obj, x, cellInfo.column(), rowInfo, state);
                    }
                    obj = view.getJoinedRows(y, 0);
                    if(obj) {
                        rowInfo = view._getRowInfo(obj);
                        view._changeRowRenderState(rowInfo, $rs.current, add);
                        this.rowStyleFormatter.format(rowInfo);
                    }
                    obj = view.getCell(x, y);
                    if(obj) {
                        obj = $(obj);
                        state = view._changeCellRenderState(obj, $rs.current, add);
                        this.cellStyleFormatter.format(obj, x, cellInfo.column(), rowInfo, state);
                    }
                }
            };
            gcuigrid.prototype._beginEditInternal = function (e) {
                if(this._canInteract() && this.options.allowEditing) {
                    var column = this.currentCell().column(), res;
                    if(column && !column.readOnly) {
                        res = new gcui.grid.cellEditorHelper().currentCellEditStart(this, e);
                        if(res) {
                        }
                        return res;
                    }
                }
                return false;
            };
            gcuigrid.prototype._endEditInternal = function (e) {
                if(this._canInteract() && this.options.allowEditing) {
                    var res = new gcui.grid.cellEditorHelper().currentCellEditEnd(this, e);
                    if(res && !this._allowVirtualScrolling()) {
                        this._view().ensureHeight(this.currentCell().rowIndex());
                    }
                    return res;
                }
                return false;
            };
            gcuigrid.prototype._onViewInsertEmptyRow = function (rowType, sectionRowIndex, dataRowIndex, dataItemIndex, virtualDataItemIndex) {
                return null;
            };
            gcuigrid.prototype._onViewCreateEmptyCell = function (rowInfo, dataCellIndex, column) {
                return null;
            };
            gcuigrid.prototype._onViewCellRendered = function (rowInfo, $cell, cellIndex, column) {
            };
            gcuigrid.prototype._onViewRowRendered = function (rowInfo) {
            };
            gcuigrid.prototype._getDataParser = function (column) {
                return column.dataParser || gcui.data.defaultParsers[column.dataType] || gcui.data.defaultParsers.string;
            };
            gcuigrid.prototype.parse = function (column, value) {
                var fromType = gcui.grid.getDataType(column), toType = gcui.grid.getDataType(column);
                if($.isFunction(value)) {
                    value = value();
                }
                value = gcui.data.convert(value, fromType, toType, {
                    culture: this._field("closestCulture"),
                    format: column.dataFormatString || column._underlyingDataFormatString,
                    nullString: this.options.nullString,
                    parser: column.dataParser
                });
                return value;
            };
            gcuigrid.prototype.toStr = function (column, value) {
                var dataView = this._dataViewWrapper.dataView(), fromType = gcui.grid.getDataType(column), toType = "string";
                value = gcui.data.convert(value, fromType, toType, {
                    culture: this._field("closestCulture"),
                    format: column.dataFormatString || column._underlyingDataFormatString,
                    nullString: this.options.nullString,
                    parser: column.dataParser
                });
                return value;
            };
            gcuigrid.prototype.parseFailed = function (column, value, dataItem, cell) {
                return value;
            };
            gcuigrid.prototype._funcOptions = function () {
                return [
                    "cellStyleFormatter", 
                    "rowStyleFormatter", 
                    "afterCellEdit", 
                    "afterCellUpdate", 
                    "beforeCellEdit", 
                    "beforeCellUpdate", 
                    "cellClicked", 
                    "columnDragging", 
                    "columnDragged", 
                    "columnDropping", 
                    "columnDropped", 
                    "columnResizing", 
                    "columnResized", 
                    "columnGrouping", 
                    "columnGrouped", 
                    "columnUngrouping", 
                    "columnUngrouped", 
                    "currentCellChanging", 
                    "currentCellChanged", 
                    "filtering", 
                    "filtered", 
                    "filterOperatorsListShowing", 
                    "groupAggregate", 
                    "groupText", 
                    "invalidCellValue", 
                    "pageIndexChanging", 
                    "pageIndexChanged", 
                    "selectionChanged", 
                    "sorting", 
                    "sorted", 
                    "dataLoading", 
                    "dataLoaded", 
                    "loading", 
                    "loaded", 
                    "rendering", 
                    "rendered"
                ];
            };
            gcuigrid.prototype._canInteract = function () {
                return !this.options.disabled;
            };
            gcuigrid.prototype._canMoveToAnotherCell = function (domElement, keyCode) {
                var tag = domElement.tagName.toLowerCase(), len, selectionRange, kc, res;
                switch(tag) {
                    case "input":
                        if($(domElement).hasClass("gridinput")) {
                            var input = domElement;
                            if(input.type === "text") {
                                len = input.value.length;
                                selectionRange = new gcui.grid.domSelection(input).getSelection();
                                kc = gcui.getKeyCodeEnum();
                                res = ((keyCode === kc.UP || keyCode === kc.DOWN || keyCode === kc.PAGE_DOWN || keyCode === kc.PAGE_UP) || (selectionRange.length === 0 && ((selectionRange.start === 0 && (keyCode === kc.LEFT || keyCode === kc.HOME)) || (selectionRange.end >= len && (keyCode === kc.RIGHT || keyCode === kc.END)))));
                                return res;
                            }
                            return true;
                        }
                        return false;
                    case "textarea":
                    case "select":
                        return false;
                }
                return true;
            };
            gcuigrid.prototype._editBySingleClick = function () {
                var value = (this.options.editingInitOption || "").toLowerCase();
                switch(value) {
                    case "click":
                    case "doubleclick":
                        break;
                    case "auto":
                    default:
                        value = this._isMobileEnv() ? "click" : "doubleclick";
                        break;
                }
                return value === "click";
            };
            gcuigrid.prototype._getDataToAbsOffset = function () {
                var x = 0, y = 0, headerRows = this._headerRows();
                if(this._showRowHeader()) {
                    x++;
                }
                if(headerRows) {
                    y += headerRows.length();
                }
                if(this._filterRow()) {
                    y++;
                }
                return {
                    x: x,
                    y: y
                };
            };
            gcuigrid.prototype._gridDataRowIndexToDataView = function (value) {
                var res = -1, $rt = gcui.grid.rowType, rowInfo;
                if(value < 0) {
                    return res;
                }
                rowInfo = this._view()._getRowInfo(this._rows().item(value));
                return (rowInfo.type & gcui.grid.rowType.data) ? rowInfo.dataItemIndex : -1;
            };
            gcuigrid.prototype._currentCellFromDataView = function (cellIndex) {
                var dataViewRowIndex = this._dataViewWrapper.currentPosition(), cellInfo = new gcui.grid.cellInfo(cellIndex, this._dataViewDataRowIndexToGrid(dataViewRowIndex), null);
                if(cellInfo.rowIndex() < 0) {
                    cellInfo.cellIndex(-1);
                } else {
                    if(cellInfo.cellIndex() < 0) {
                        cellInfo.cellIndex(0);
                    }
                }
                return cellInfo;
            };
            gcuigrid.prototype._dataViewDataRowIndexToGrid = function (value) {
                var $rt = gcui.grid.rowType, view = this._view(), rows = this._rows(), i, len, rowInfo;
                if(value < 0) {
                    return -1;
                }
                for(i = 0 , len = rows.length(); i < len; i++) {
                    rowInfo = view._getRowInfo(rows.item(i));
                    if((rowInfo.type & $rt.data) && (rowInfo.dataItemIndex === value)) {
                        return rowInfo.sectionRowIndex;
                    }
                }
                return -1;
            };
            gcuigrid.prototype._getDataCellsRange = function (forceTotal) {
                var minCol = 0, minRow = 0, maxCol = this._field("visibleLeaves").length - 1, maxRow = forceTotal || !this._rendered ? this._totalRowsCount() - 1 : this._rows().length() - 1;
                if(this._showRowHeader()) {
                    maxCol--;
                }
                if(maxCol < 0 || maxRow < 0) {
                    minCol = minRow = maxCol = maxRow = -1;
                }
                return new gcui.grid.cellInfoRange(new gcui.grid.cellInfo(minCol, minRow, null), new gcui.grid.cellInfo(maxCol, maxRow, null));
            };
            gcuigrid.prototype._getDataItem = function (dataItemIndex) {
                return this.dataView().item(dataItemIndex);
            };
            gcuigrid.prototype._getFirstDataRowCell = function (absCellIndex) {
                var rowIndex, len, rowInfo, view = this._view(), rows = this._rows(), $rt = gcui.grid.rowType;
                for(rowIndex = 0 , len = rows.length(); rowIndex < len; rowIndex++) {
                    rowInfo = view._getRowInfo(rows.item(rowIndex));
                    if(rowInfo.type & $rt.data) {
                        return new gcui.grid.cellInfo(absCellIndex, rowIndex);
                    }
                }
                return gcui.grid.cellInfo.outsideValue;
            };
            gcuigrid.prototype._getNextCurrencyPos = function (dataRange, cellInfo, keyCode, shiftKeyPressed) {
                var cellIndex = cellInfo.cellIndex(), rowIndex = cellInfo.rowIndex(), tmp, keyCodeEnum = gcui.getKeyCodeEnum();
                switch(keyCode) {
                    case keyCodeEnum.PAGE_UP:
                        if(this._reverseKey && rowIndex === dataRange.topLeft().rowIndex()) {
                            rowIndex = dataRange.bottomRight().rowIndex();
                        } else {
                            rowIndex -= this._pageSizeKey;
                            if(rowIndex < (tmp = dataRange.topLeft().rowIndex())) {
                                rowIndex = tmp;
                            }
                        }
                        break;
                    case keyCodeEnum.PAGE_DOWN:
                        if(this._reverseKey && rowIndex === dataRange.bottomRight().rowIndex()) {
                            rowIndex = dataRange.topLeft().rowIndex();
                        } else {
                            rowIndex += this._pageSizeKey;
                            if(rowIndex > (tmp = dataRange.bottomRight().rowIndex())) {
                                rowIndex = tmp;
                            }
                        }
                        break;
                    case keyCodeEnum.END:
                        cellIndex = (this._reverseKey && cellIndex === dataRange.bottomRight().cellIndex()) ? dataRange.topLeft().cellIndex() : dataRange.bottomRight().cellIndex();
                        break;
                    case keyCodeEnum.HOME:
                        cellIndex = (this._reverseKey && cellIndex === dataRange.topLeft().cellIndex()) ? dataRange.bottomRight().cellIndex() : dataRange.topLeft().cellIndex();
                        break;
                    case keyCodeEnum.LEFT:
                        if(cellIndex > dataRange.topLeft().cellIndex()) {
                            cellIndex--;
                        } else if(this._reverseKey) {
                            cellIndex = dataRange.bottomRight().cellIndex();
                        }
                        break;
                    case keyCodeEnum.UP:
                        if(rowIndex > dataRange.topLeft().rowIndex()) {
                            rowIndex--;
                        } else if(this._reverseKey) {
                            rowIndex = dataRange.bottomRight().rowIndex();
                        }
                        break;
                    case keyCodeEnum.RIGHT:
                        if(cellIndex < dataRange.bottomRight().cellIndex()) {
                            cellIndex++;
                        } else if(this._reverseKey) {
                            cellIndex = dataRange.topLeft().cellIndex();
                        }
                        break;
                    case keyCodeEnum.ENTER:
                    case keyCodeEnum.DOWN:
                        if(rowIndex < dataRange.bottomRight().rowIndex()) {
                            rowIndex++;
                        } else {
                            if(this._reverseKey) {
                                rowIndex = dataRange.topLeft().rowIndex();
                            }
                        }
                        break;
                    case keyCodeEnum.TAB:
                        if(false) {
                            if(shiftKeyPressed) {
                                cellIndex--;
                                if(cellIndex < dataRange.topLeft().cellIndex()) {
                                    cellIndex = dataRange.bottomRight().cellIndex();
                                    rowIndex--;
                                    if(rowIndex < dataRange.topLeft().rowIndex()) {
                                        rowIndex = dataRange.bottomRight().rowIndex();
                                    }
                                }
                            } else {
                                cellIndex++;
                                if(cellIndex > dataRange.bottomRight().cellIndex()) {
                                    cellIndex = dataRange.topLeft().cellIndex();
                                    rowIndex++;
                                    if(rowIndex > dataRange.bottomRight().rowIndex()) {
                                        rowIndex = dataRange.topLeft().rowIndex();
                                    }
                                }
                            }
                        }
                        break;
                }
                return {
                    cellIndex: cellIndex,
                    rowIndex: rowIndex
                };
            };
            gcuigrid.prototype._findUntilOuterDiv = function (start, tagsToFind) {
                var current = start, stopper, nodeName, item = null;
                for(; current; current = current.parentNode) {
                    nodeName = current.nodeName.toLowerCase();
                    if(nodeName) {
                        if(current === this.outerDiv[0]) {
                            stopper = current;
                            break;
                        }
                        if(tagsToFind[nodeName]) {
                            item = current;
                        }
                    }
                }
                return stopper ? item : null;
            };
            gcuigrid.prototype._getStaticIndex = function (bRow) {
                var result, dataRange = this._getDataCellsRange();
                if(this._hasSpannedCells()) {
                    return -1;
                }
                if(bRow) {
                    result = Math.min(this.options.staticRowIndex, dataRange.bottomRight().rowIndex());
                } else {
                    result = Math.min(this.options.staticColumnIndex, dataRange.bottomRight().cellIndex());
                }
                if(result < -1) {
                    result = -1;
                }
                return result;
            };
            gcuigrid.prototype._getStaticOffsetIndex = function (isColumn) {
                var index = 0;
                if(isColumn) {
                    if(this._showRowHeader()) {
                        index++;
                    }
                } else {
                    index = this._columnsHeadersTable().length;
                    if(this.options.showFilter) {
                        index++;
                    }
                }
                return index;
            };
            gcuigrid.prototype._getRealStaticColumnIndex = function () {
                var leaves, len, offsetStaticIndex = this._getStaticOffsetIndex(true), staticColumnIndex = this._getStaticIndex(false), resultIndex, tmp;
                resultIndex = staticColumnIndex + offsetStaticIndex;
                if(staticColumnIndex >= 0) {
                    leaves = this._field("visibleLeaves");
                    len = leaves.length;
                    tmp = resultIndex;
                    for(; resultIndex < len; resultIndex++) {
                        if(leaves[resultIndex].parentIdx === -1) {
                            if(resultIndex > tmp) {
                                resultIndex--;
                            }
                            break;
                        }
                    }
                    if(resultIndex >= len) {
                        resultIndex = len - 1;
                    }
                }
                return resultIndex;
            };
            gcuigrid.prototype._getRealStaticRowIndex = function () {
                var offsetStaticIndex = this._getStaticOffsetIndex(false);
                return this._getStaticIndex(true) + offsetStaticIndex;
            };
            gcuigrid.prototype._hasMerging = function () {
                var leaves = this._field("leaves"), i, len, leaf, result = false;
                if(leaves) {
                    for(i = 0 , len = leaves.length; (i < len) && !result; i++) {
                        leaf = leaves[i];
                        result = result || (leaf.parentVis && (leaf.rowMerge !== "none"));
                    }
                }
                return result;
            };
            gcuigrid.prototype._hasGrouping = function () {
                var leaves = this._field("leaves"), i, len, leaf, result = false;
                for(i = 0 , len = leaves.length; (i < len) && !result; i++) {
                    leaf = leaves[i];
                    result = leaf.groupInfo && (leaf.groupInfo.position !== "none");
                }
                return result;
            };
            gcuigrid.prototype._hasSpannedCells = function () {
                return this._hasGrouping() || this._hasMerging();
            };
            gcuigrid.prototype._columnsHeadersTable = function (value) {
                if(arguments.length) {
                    this._field("columnsHeadersTable", value);
                }
                return this._field("columnsHeadersTable");
            };
            gcuigrid.prototype._view = function () {
                return this._field("view");
            };
            gcuigrid.prototype._originalFooterRowData = function () {
                var footer = this._field("tfoot");
                return (footer && footer.length) ? footer[0] : null;
            };
            gcuigrid.prototype._originalHeaderRowData = function () {
                var header = this._field("thead");
                return (header && header.length) ? header[0] : null;
            };
            gcuigrid.prototype._setAttr = function ($element, key, value) {
                var self = this;
                if($element === this.element) {
                    if(arguments.length === 2) {
                        $.each(key, function (k, v) {
                            if(!(k in self._originalAttr)) {
                                self._originalAttr[k] = $element.attr(k);
                            }
                        });
                        return $element.attr(key);
                    } else {
                        if(!(key in this._originalAttr)) {
                            this._originalAttr[key] = $element.attr(key);
                        }
                        return $element.attr(key, value);
                    }
                } else {
                    return (arguments.length === 3) ? $element.attr(key, value) : $element.attr(key);
                }
                return this;
            };
            gcuigrid.prototype._totalRowsCount = function () {
                return this.sketchTable.length;
            };
            gcuigrid.prototype._trackScrollingPosition = function (x, y) {
                this._scrollingState.x = x;
                this._scrollingState.y = y;
            };
            gcuigrid.prototype._trackScrollingIndex = function (index) {
                this._scrollingState.index = index;
            };
            gcuigrid.prototype._uid = function () {
                if(this.__uid === undefined) {
                    this.__uid = gcui.grid.getUID();
                }
                return "gcuigrid" + this.__uid;
            };
            return gcuigrid;
        })(gcui.gcuiWidget);
        grid.gcuigrid = gcuigrid;        
        gcuigrid.prototype.widgetEventPrefix = "gcuigrid";
        gcuigrid.prototype._data$prefix = "gcuigrid";
        gcuigrid.prototype._customSortOrder = 1000;
        gcuigrid.prototype._reverseKey = false;
        gcuigrid.prototype._pageSizeKey = 10;
        gcuigrid.prototype._mergeWidgetsWithOptions = true;
        var gcuigrid_options = (function () {
            function gcuigrid_options() {
                this.mobileCSS = {
                    header: "ui-header ui-bar-a",
                    content: "ui-body-c",
                    stateHover: "ui-btn-down-c",
                    stateActive: "ui-btn-down-c"
                };
                this.allowColMoving = false;
                this.allowColSizing = false;
                this.allowEditing = false;
                this.allowKeyboardNavigation = true;
                this.allowPaging = false;
                this.allowSorting = false;
                this.allowVirtualScrolling = false;
                this.cellStyleFormatter = undefined;
                this.columns = [];
                this.columnsAutogenerationMode = "merge";
                this.culture = "";
                this.customFilterOperators = [];
                this.data = null;
                this.editingInitOption = "auto";
                this.ensureColumnsPxWidth = false;
                this.filterOperatorsSortMode = "alphabeticalCustomFirst";
                this.groupAreaCaption = "Drag a column here to group by that column.";
                this.groupIndent = 10;
                this.highlightCurrentCell = false;
                this.highlightOnHover = true;
                this.loadingText = "Loading...";
                this.nullString = "";
                this.pageIndex = 0;
                this.pageSize = 10;
                this.pagerSettings = {
                    mode: "numeric",
                    pageButtonCount: 10,
                    position: "bottom"
                };
                this.readAttributesFromData = false;
                this.rowStyleFormatter = undefined;
                this.scrollMode = "none";
                this.selectionMode = "singleRow";
                this.showFilter = false;
                this.showFooter = false;
                this.showGroupArea = false;
                this.showSelectionOnRender = true;
                this.showRowHeader = false;
                this.staticColumnIndex = -1;
                this.staticColumnsAlignment = "left";
                this.staticRowIndex = -1;
                this.totalRows = -1;
                this.afterCellEdit = null;
                this.afterCellUpdate = null;
                this.beforeCellEdit = null;
                this.beforeCellUpdate = null;
                this.cellClicked = null;
                this.columnDragging = null;
                this.columnDragged = null;
                this.columnDropping = null;
                this.columnDropped = null;
                this.columnGrouping = null;
                this.columnGrouped = null;
                this.columnResizing = null;
                this.columnResized = null;
                this.columnUngrouping = null;
                this.columnUngrouped = null;
                this.currentCellChanging = null;
                this.currentCellChanged = null;
                this.filterOperatorsListShowing = null;
                this.filtering = null;
                this.filtered = null;
                this.groupAggregate = null;
                this.groupText = null;
                this.invalidCellValue = null;
                this.pageIndexChanging = null;
                this.pageIndexChanged = null;
                this.selectionChanged = null;
                this.sorting = null;
                this.sorted = null;
                this.dataLoading = null;
                this.dataLoaded = null;
                this.loading = null;
                this.loaded = null;
                this.rendering = null;
                this.rendered = null;
            }
            return gcuigrid_options;
        })();        
        gcuigrid.prototype.options = gcui.grid.extendWidgetOptions(gcui.gcuiWidget.prototype.options, new gcuigrid_options());
        $.gcui.registerWidget("gcuigrid", gcuigrid.prototype);
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var c1basefield = (function (_super) {
            __extends(c1basefield, _super);
            function c1basefield() {
                _super.apply(this, arguments);

            }
            c1basefield.prototype._create = function () {
                var gcuigrid = $.data(this.element[0], "gcuigridowner");
                this._field("owner", gcuigrid);
                gcui.grid.widgetName(this.element[0], this.widgetFullName);
                this._destroyed = false;
                this.element.addClass(gcuigrid.options.uiCSS.widget + " gcui-c1basefield " + gcuigrid.options.uiCSS.stateDefault);
                if(this.options.disabled) {
                    this.disable();
                }
                if(gcuigrid.options.allowColMoving) {
                    gcuigrid._dragndrop(true).attach(this);
                }
            };
            c1basefield.prototype._init = function () {
                this.element.wrapInner("<div class='gcui-gcuigrid-innercell'></div>");
                this._refreshHeaderCell();
            };
            c1basefield.prototype.destroy = function () {
                try  {
                    _super.prototype.destroy.apply(this, arguments);
                }finally {
                    this._destroyed = true;
                }
            };
            c1basefield.prototype._destroy = function () {
                if(this._isDestroyed()) {
                    return;
                }
                var gcuigrid = this._owner();
                if(gcuigrid && gcuigrid._dragndrop(false)) {
                    gcuigrid._dragndrop(false).detach(this);
                }
                gcui.grid.remove$dataByPrefix(this.element, this._data$prefix);
                this.element.removeClass(gcuigrid.options.uiCSS.widget + " gcui-c1basefield " + gcuigrid.options.uiCSS.stateDefault).html(this.element.find(".gcui-gcuigrid-headertext").html());
            };
            c1basefield.prototype._field = function (name, value) {
                return gcui.grid.dataPrefix(this.element, this._data$prefix, name, value);
            };
            c1basefield.prototype._removeField = function (name) {
                var internalDataName = this._data$prefix + name;
                this.element.removeData(internalDataName);
            };
            c1basefield.prototype._setOption = function (key, value) {
                var presetFunc = this["_preset_" + key], oldValue = this.options[key], optionChanged, postsetFunc;
                if(presetFunc !== undefined) {
                    value = presetFunc.apply(this, [
                        value, 
                        oldValue
                    ]);
                }
                optionChanged = (value !== oldValue);
                _super.prototype._setOption.apply(this, [key, value]);
                if(optionChanged) {
                    if(this.options.groupedIndex !== undefined) {
                        var groupedWidget = this._owner()._field("groupedWidgets")[this.options.groupedIndex];
                        if(groupedWidget && (key in groupedWidget.options)) {
                            groupedWidget._setOption(key, value);
                        }
                    }
                    postsetFunc = this["_postset_" + key];
                    if(postsetFunc !== undefined) {
                        postsetFunc.apply(this, [value, oldValue]);
                    }
                }
            };
            c1basefield.prototype._postset_allowMoving = function (value, oldValue) {
            };
            c1basefield.prototype._preset_clientType = function (value, oldValue) {
                throw "read-only";
            };
            c1basefield.prototype._postset_headerText = function (value, oldValue) {
                this._refreshHeaderCell();
            };
            c1basefield.prototype._postset_visible = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1basefield.prototype._postset_width = function (value, oldValue) {
                var gcuigrid = this._owner();
                if(gcuigrid) {
                    this.options.ensurePxWidth = true;
                    var found = gcui.grid.getColumnByTravIdx(gcuigrid.options.columns, this.options.travIdx);
                    if(found) {
                        found.found.width = value;
                        found.found.ensurePxWidth = true;
                    }
                    gcuigrid.setSize();
                }
            };
            c1basefield.prototype._owner = function () {
                return this._field("owner");
            };
            c1basefield.prototype._canSize = function () {
                return this.options.allowSizing && this._owner().options.allowColSizing;
            };
            c1basefield.prototype._canDrag = function () {
                return this.options.allowMoving === true;
            };
            c1basefield.prototype._canDropTo = function (field) {
                if (gcui.grid.isChildOf(this._owner().options.columns, field, this)) {
                    return false;
                }
                return true;
            };
            c1basefield.prototype._createHeaderContent = function ($container) {
                return $container.html(this.options.headerText || "&nbsp;");
            };
            c1basefield.prototype._decorateHeaderContent = function ($container) {
                return $container.wrapInner("<span class=\"gcui-gcuigrid-headertext\" />");
            };
            c1basefield.prototype._refreshHeaderCell = function () {
                var $container = this.element.children(".gcui-gcuigrid-innercell").empty();
                this._createHeaderContent($container);
                this._decorateHeaderContent($container);
            };
            c1basefield.prototype._isDestroyed = function () {
                return this._destroyed;
            };
            return c1basefield;
        })(gcui.gcuiWidget);
        grid.c1basefield = c1basefield;        
        c1basefield.prototype._data$prefix = "c1basefield";
        var c1basefield_options = (function () {
            function c1basefield_options() {
                this.allowMoving = true;
                this.allowSizing = true;
                this.cellFormatter = undefined;
                this.dataKey = undefined;
                this.ensurePxWidth = undefined;
                this.footerText = undefined;
                this.headerText = undefined;
                this.textAlignment = undefined;
                this.visible = true;
                this.width = undefined;
            }
            return c1basefield_options;
        })();
        grid.c1basefield_options = c1basefield_options;        
        ;
        c1basefield.prototype.options = gcui.grid.extendWidgetOptions(gcui.gcuiWidget.prototype.options, new c1basefield_options());
        $.gcui.registerWidget("c1basefield", c1basefield.prototype);
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var c1field = (function (_super) {
            __extends(c1field, _super);
            function c1field() {
                _super.apply(this, arguments);

            }
            c1field.prototype._create = function () {
                _super.prototype._create.apply(this, arguments);
                var gcuigrid = this._owner();
                this.element.addClass(gcuigrid.options.uiCSS.widget + " gcui-c1field");
            };
            c1field.prototype._destroy = function () {
                if(this._isDestroyed()) {
                    return;
                }
                var gcuigrid = this._owner();
                this.element.find("*").unbind("." + this.widgetName);
                if(this.$filterEditor) {
                    this.$filterEditor.closest("td").find("*").unbind("." + this.widgetName);
                    switch(this._getInputEditorType(this.options)) {
                        case "date":
                            if(this.$filterEditor.data("gcui-inputdate")) {
                                this.$filterEditor.gcuiinputdate("destroy");
                            }
                            break;
                        case "mask":
                            if(this.$filterEditor.data("gcui-inputmask")) {
                                this.$filterEditor.gcuiinputmask("destroy");
                            }
                            break;
                        case "numberCurrency":
                        case "numberNumber":
                        case "numberPercent":
                            if(this.$filterEditor.data("gcui-inputnumber")) {
                                this.$filterEditor.gcuiinputnumber("destroy");
                            }
                            break;
                    }
                    this.$filterEditor = null;
                }
                this.element.removeClass(gcuigrid.options.uiCSS.widget + " gcui-c1field");
                this._removeDropDownFilterList();
                _super.prototype._destroy.apply(this, arguments);
            };
            c1field.prototype._init = function () {
                _super.prototype._init.apply(this, arguments);
                this.$filterEditor = null;
                var gcuigrid = this._owner();
                this.filterRow = gcuigrid._filterRow();
                if(gcuigrid.options.showFilter && this.options.showFilter && (this.options.dataIndex >= 0)) {
                    this._prepareFilterCell();
                }
            };
            c1field.prototype._postset_aggregate = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1field.prototype._postset_allowSort = function (value, oldValue) {
                this._refreshHeaderCell();
            };
            c1field.prototype._postset_dataType = function (value, oldValue) {
                throw "read-only";
            };
            c1field.prototype._postset_dataParser = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1field.prototype._postset_dataFormatString = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1field.prototype._postset_filterOperator = function (value, oldValue) {
                this._owner().ensureControl(true);
            };
            c1field.prototype._postset_filterValue = function (value, oldValue) {
                this._owner().ensureControl(true);
            };
            c1field.prototype._postset_groupInfo = function (value, oldValue) {
                this._owner().ensureControl(true);
            };
            c1field.prototype._postset_rowMerge = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1field.prototype._postset_showFilter = function (value, oldValue) {
                this._owner().ensureControl(false);
            };
            c1field.prototype._postset_sortDirection = function (value, oldValue) {
                this.options.sortOrder = 0;
                this._owner().ensureControl(true);
            };
            c1field.prototype._postset_width = function (value, oldValue) {
                _super.prototype._postset_width.apply(this, arguments);
            };
            c1field.prototype._canDropTo = function (field) {
                if(_super.prototype._canDropTo.apply(this, arguments)) {
                    if (this.options.groupedIndex !== undefined && (field instanceof $.gcui.c1groupedfield)) {
                        return false;
                    }
                    return true;
                }
                return false;
            };
            c1field.prototype._canSort = function () {
                var grid = this._owner();
                return (grid && grid.options.allowSorting && this.options.allowSort && (this.options.dataIndex >= 0));
            };
            c1field.prototype._decorateHeaderContent = function ($container) {
                if(this._canSort()) {
                    var gcuigrid = this._owner(), $anchor = $container.wrapInner("<a class=\"gcui-gcuigrid-headertext\" href=\"#\" role=\"button\" />").children("a");
                    $anchor.bind("click." + this.widgetName, this, $.proxy(this._onHrefClick, this));
                    switch(this.options.sortDirection) {
                        case "ascending":
                            $container.append($("<span class=\"gcui-gcuigrid-sort-icon " + gcuigrid.options.uiCSS.icon + " " + gcuigrid.options.uiCSS.iconArrowUp + "\">ascending</span>"));
                            break;
                        case "descending":
                            $container.append($("<span class=\"gcui-gcuigrid-sort-icon " + gcuigrid.options.uiCSS.icon + " " + gcuigrid.options.uiCSS.iconArrowDown + "\">descending</span>"));
                            break;
                    }
                } else {
                    _super.prototype._decorateHeaderContent.apply(this, arguments);
                }
            };
            c1field.prototype._prepareFilterCell = function () {
                var filterCellIndex = this.options.visLeavesIdx, gcuigrid = this._owner(), filterCell = null, dataValue, editorOptions, self = this, editorType, inputType = gcui.grid.HTML5InputSupport.getDefaultInputType(gcuigrid._isMobileEnv(), this.options);
                if(filterCellIndex >= 0) {
                    gcuigrid = this._owner();
                    if(this.filterRow) {
                        filterCell = $(gcui.grid.rowAccessor.getCell(this.filterRow, filterCellIndex));
                    } else {
                        throw "exception";
                    }
                    this.$filterEditor = filterCell.find("input");
                    filterCell.bind((($.support).selectstart ? "selectstart" : "mousedown"), function (event) {
                        event.stopPropagation();
                    });
                    dataValue = gcuigrid.parse(this.options, gcui.grid.filterHelper.getSingleValue(this.options.filterValue));
                    if(dataValue === null || dataValue === "undefined") {
                        switch(gcui.grid.getDataType(this.options)) {
                            case "boolean":
                                dataValue = false;
                                break;
                            case "number":
                            case "currency":
                            case "datetime":
                                dataValue = 0;
                                break;
                            default:
                                dataValue = "";
                        }
                    }
                    editorOptions = {
                        culture: gcuigrid.options.culture,
                        disabled: gcuigrid.options.disabled,
                        decimalPlaces: (function (pattern) {
                            var test = /^(n|p|c){1}(\d*)$/.exec(pattern);
                            if(test && test[2]) {
                                return parseInt(test[2], 10);
                            }
                            test = /^(d){1}(\d*)$/.exec(pattern);
                            if(test) {
                                return 0;
                            }
                            return 2;
                        })(this.options.dataFormatString)
                    };
                    switch(editorType = this._getInputEditorType(this.options)) {
                        case "date":
                            if(inputType === "text") {
                                this.$filterEditor.gcuiinputdate($.extend(editorOptions, {
                                    date: dataValue,
                                    dateFormat: this.options.dataFormatString || undefined,
                                    showTrigger: true
                                }));
                            } else {
                                this._createHtmlEditor(this.$filterEditor, inputType, gcui.grid.HTML5InputSupport.toStr(dataValue || new Date(), inputType));
                            }
                            break;
                        case "mask":
                            this.$filterEditor.gcuiinputmask({
                                text: dataValue + ""
                            });
                            break;
                        case "numberCurrency":
                            this.$filterEditor.gcuiinputnumber($.extend(editorOptions, {
                                type: "currency",
                                value: dataValue
                            }));
                            break;
                        case "numberNumber":
                            if(inputType === "text") {
                                this.$filterEditor.gcuiinputnumber($.extend(editorOptions, {
                                    value: dataValue
                                }));
                            } else {
                                this._createHtmlEditor(this.$filterEditor, inputType, gcui.grid.HTML5InputSupport.toStr(dataValue, inputType));
                            }
                            break;
                        case "numberPercent":
                            this.$filterEditor.gcuiinputnumber($.extend(editorOptions, {
                                type: "percent",
                                value: dataValue * 100
                            }));
                            break;
                        default:
                            throw gcui.grid.stringFormat("Unsupported editor type: \"{0}\"", editorType);
                    }
                    filterCell.find(".gcui-gcuigrid-filter-trigger").attr({
                        "role": "button",
                        "aria-haspopup": "true"
                    }).bind("mouseenter." + this.widgetName, function (e) {
                        if(!self.options.disabled) {
                            $(this).addClass(gcuigrid.options.uiCSS.stateHover);
                        }
                    }).bind("mouseleave." + this.widgetName, function (e) {
                        if(!self.options.disabled) {
                            $(this).removeClass(gcuigrid.options.uiCSS.stateHover + " " + gcuigrid.options.uiCSS.stateActive);
                        }
                    }).bind("mouseup." + this.widgetName, this, function (e) {
                        if(!self.options.disabled) {
                            $(this).removeClass(gcuigrid.options.uiCSS.stateActive);
                        }
                    }).bind("mousedown." + this.widgetName, {
                        column: this
                    }, this._onFilterBtnClick).bind("click." + this.widgetName, function (e) {
                        e.preventDefault();
                    });
                }
            };
            c1field.prototype._createHtmlEditor = function (input, inputType, value) {
                return input.wrap("<span class=\"gcuigrid-input-wrapper\"></span").attr("type", inputType).val(value);
            };
            c1field.prototype._onFilterBtnClick = function (e) {
                var column = e.data.column, maxItemsCount = 8, gcuigrid = column._owner(), filterOpLowerCase, applicableFilters, args, items, key, operator, width, eventUID, zIndex, inputType = gcui.grid.HTML5InputSupport.getDefaultInputType(gcuigrid._isMobileEnv(), column.options);
                if(column.options.disabled) {
                    return false;
                }
                if(column.$dropDownFilterList) {
                    column._removeDropDownFilterList();
                    return false;
                }
                e.target.focus();
                filterOpLowerCase = gcui.grid.filterHelper.getSingleOperatorName(column.options.filterOperator).toLowerCase();
                applicableFilters = gcuigrid.getFilterOperatorsByDataType(gcui.grid.getDataType(column.options));
                args = {
                    operators: $.extend(true, [], applicableFilters),
                    column: column.options
                };
                gcuigrid._onFilterOperatorsListShowing(args);
                items = [];
                if(args.operators) {
                    $.each(args.operators, function (key, operator) {
                        items.push({
                            label: operator.displayName || operator.name,
                            value: operator.name,
                            selected: operator.name.toLowerCase() === filterOpLowerCase
                        });
                    });
                }
                column.$dropDownFilterList = $("<div class=\"gcui-gcuigrid-filterlist\"></div").appendTo(document.body).gcuilist({
                    autoSize: true,
                    maxItemsCount: maxItemsCount,
                    selected: function (data, arg) {
                        var filterValue, editorType;
                        switch(editorType = column._getInputEditorType(column.options)) {
                            case "date":
                                if(inputType === "text") {
                                    filterValue = column.$filterEditor.gcuiinputdate("option", "date") || new Date();
                                } else {
                                    filterValue = gcui.grid.HTML5InputSupport.parse(column.$filterEditor.val(), inputType) || new Date();
                                }
                                break;
                            case "mask":
                                filterValue = column.$filterEditor.gcuiinputmask("option", "text");
                                break;
                            case "numberNumber":
                                if(inputType !== "text") {
                                    filterValue = gcui.grid.HTML5InputSupport.parse(column.$filterEditor.val(), inputType) || 0;
                                    break;
                                }
                            case "numberCurrency":
                            case "numberPercent":
                                filterValue = column.$filterEditor.gcuiinputnumber("option", "value");
                                if(editorType === "numberPercent") {
                                    filterValue /= 100;
                                }
                                break;
                        }
                        column._removeDropDownFilterList();
                        gcuigrid._handleFilter(column, arg.item.value, filterValue);
                    }
                });
                if($.ui && $.fn.zIndex) {
                    zIndex = gcuigrid.outerDiv.zIndex();
                    if(zIndex) {
                        zIndex++;
                    }
                }
                column.$dropDownFilterList.css("z-index", Math.max(zIndex || 0, 9999));
                column.$dropDownFilterList.gcuilist("setItems", items).gcuilist("renderList");
                width = column.$dropDownFilterList.width() | 150;
                column.$dropDownFilterList.width(items.length > maxItemsCount ? width + 20 : width).gcuilist("refreshSuperPanel").position({
                    of: $(this),
                    my: "left top",
                    at: "left bottom"
                });
                (column.$dropDownFilterList).$button = $(this);
                eventUID = (column.$dropDownFilterList).eventUID = gcui.grid.getUID();
                $(document).bind("mousedown." + column.widgetName + "." + eventUID, {
                    column: column
                }, column._onDocMouseDown);
            };
            c1field.prototype._onDocMouseDown = function (e) {
                var $target = $(e.target), $filterList = $target.parents(".gcui-gcuigrid-filterlist:first"), $filterButton = $target.is(".gcui-gcuigrid-filter-trigger") ? $target : $target.parents(".gcui-gcuigrid-filter-trigger:first");
                if(($filterButton.length && ($filterButton[0] === e.data.column.$dropDownFilterList.$button[0])) || ($filterList.length && ($filterList[0] === e.data.column.$dropDownFilterList[0]))) {
                } else {
                    e.data.column._removeDropDownFilterList();
                }
            };
            c1field.prototype._onHrefClick = function (args) {
                if(args.data.options.disabled) {
                    return false;
                }
                if(args.data.options.allowSort) {
                    var gcuigrid = args.data._owner();
                    gcuigrid._handleSort(args.data.options, args.ctrlKey);
                }
                return false;
            };
            c1field.prototype._removeDropDownFilterList = function () {
                if(this.$dropDownFilterList) {
                    var eventUID = (this.$dropDownFilterList).eventUID;
                    this.$dropDownFilterList.remove();
                    this.$dropDownFilterList = null;
                    $(document).unbind("mousedown." + this.widgetName + "." + eventUID, this._onDocMouseDown);
                }
            };
            c1field.prototype._getInputEditorType = function (column) {
                switch(gcui.grid.getDataType(column)) {
                    case "number":
                        return (column.dataFormatString && column.dataFormatString.indexOf("p") === 0) ? "numberPercent" : "numberNumber";
                    case "currency":
                        return "numberCurrency";
                    case "datetime":
                        return "date";
                    default:
                        return "mask";
                }
            };
            return c1field;
        })(grid.c1basefield);
        grid.c1field = c1field;        
        var c1field_options = (function (_super) {
            __extends(c1field_options, _super);
            function c1field_options() {
                _super.apply(this, arguments);

                this.aggregate = "none";
                this.allowSort = true;
                this.dataType = undefined;
                this.inputType = undefined;
                this.dataParser = undefined;
                this.dataFormatString = undefined;
                this.filterOperator = "nofilter";
                this.filterValue = undefined;
                this.groupInfo = {
                    expandInfo: [],
                    level: undefined,
                    groupSingleRow: true,
                    collapsedImageClass: $.gcui.uiCSS.iconArrowRight,
                    expandedImageClass: $.gcui.uiCSS.iconArrowRightDown,
                    position: "none",
                    outlineMode: "startExpanded",
                    headerText: undefined,
                    footerText: undefined
                };
                this.readOnly = false;
                this.rowMerge = "none";
                this.showFilter = true;
                this.sortDirection = "none";
                this.valueRequired = false;
            }
            return c1field_options;
        })(grid.c1basefield_options);
        grid.c1field_options = c1field_options;        
        
        c1field.prototype.options = gcui.grid.extendWidgetOptions(grid.c1basefield.prototype.options, new c1field_options());
        $.gcui.registerWidget("c1field", $.gcui.c1basefield, c1field.prototype);
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var c1band = (function (_super) {
            __extends(c1band, _super);
            function c1band() {
                _super.apply(this, arguments);

            }
            c1band.prototype._create = function () {
                _super.prototype._create.apply(this, arguments);
                var gcuigrid = this._owner();
                this.element.addClass(gcuigrid.options.uiCSS.widget + " gcui-c1band");
            };
            c1band.prototype._canDropTo = function (field) {
                if(_super.prototype._canDropTo.apply(this, arguments)) {
                    return !(field instanceof $.gcui.c1groupedfield);
                }
                return false;
            };
            return c1band;
        })(grid.c1basefield);
        grid.c1band = c1band;        
        var c1band_options = (function (_super) {
            __extends(c1band_options, _super);
            function c1band_options() {
                _super.apply(this, arguments);

                this.columns = [];
            }
            return c1band_options;
        })(grid.c1field_options);        
        ;
        c1band.prototype.options = gcui.grid.extendWidgetOptions(grid.c1basefield.prototype.options, new c1band_options());
        $.gcui.registerWidget("c1band", $.gcui.c1basefield, c1band.prototype);
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var c1groupedfield = (function (_super) {
            __extends(c1groupedfield, _super);
            function c1groupedfield() {
                _super.apply(this, arguments);

            }
            c1groupedfield.prototype._create = function () {
                var gcuigrid = $.data(this.element[0], "gcuigridowner");
                this._field("owner", gcuigrid);
                gcui.grid.widgetName(this.element[0], this.widgetFullName);
                this._destroyed = false;
                this.element.addClass("gcui-gcuigrid-group-button " + gcuigrid.options.uiCSS.stateDefault + " " + gcuigrid.options.uiCSS.cornerAll);
                if(this.options.disabled) {
                    this.disable();
                }
                if(gcuigrid.options.allowColMoving) {
                    gcuigrid._dragndrop(true).attach(this);
                }
            };
            c1groupedfield.prototype._init = function () {
                this._refreshHeaderCell();
            };
            c1groupedfield.prototype.destroy = function () {
                try  {
                    _super.prototype.destroy.apply(this, arguments);
                }finally {
                    this._destroyed = true;
                }
            };
            c1groupedfield.prototype._destroy = function () {
                if(this._isDestroyed()) {
                    return;
                }
                this.element.find("*").unbind("." + this.widgetName);
                var gcuigrid = this._owner();
                if(gcuigrid && gcuigrid._dragndrop(false)) {
                    gcuigrid._dragndrop(false).detach(this);
                }
                gcui.grid.remove$dataByPrefix(this.element, this._data$prefix);
            };
            c1groupedfield.prototype._field = function (name, value) {
                return gcui.grid.dataPrefix(this.element, this._data$prefix, name, value);
            };
            c1groupedfield.prototype._removeField = function (name) {
                var internalDataName = this._data$prefix + name;
                this.element.removeData(internalDataName);
            };
            c1groupedfield.prototype._setOption = function (key, value) {
                var presetFunc = this["_preset_" + key], oldValue = this.options[key], optionChanged, postsetFunc;
                if(presetFunc !== undefined) {
                    value = presetFunc.apply(this, [
                        value, 
                        oldValue
                    ]);
                }
                optionChanged = (value !== oldValue);
                _super.prototype._setOption.apply(this, [
                    key, 
                    value
                ]);
                if(optionChanged) {
                    postsetFunc = this["_postset_" + key];
                    if(postsetFunc !== undefined) {
                        postsetFunc.apply(this, [
                            value, 
                            oldValue
                        ]);
                    }
                }
            };
            c1groupedfield.prototype._postset_headerText = function (value, oldValue, isInvokedOutside) {
                this._refreshHeaderCell();
            };
            c1groupedfield.prototype._postset_allowSort = function (value, oldValue, isInvokedOutside) {
                this._refreshHeaderCell();
            };
            c1groupedfield.prototype._owner = function () {
                return this._field("owner");
            };
            c1groupedfield.prototype._canSize = function () {
                return this.options.allowSizing && this._owner().options.allowColSizing;
            };
            c1groupedfield.prototype._canDrag = function () {
                return this.options.allowMoving === true;
            };
            c1groupedfield.prototype._canDropTo = function (field) {
                if (!(field instanceof $.gcui.c1groupedfield)) {
                    return false;
                }
                if (gcui.grid.isChildOf(this._owner().options.columns, field, this)) {
                    return false;
                }
                return true;
            };
            c1groupedfield.prototype._canSort = function () {
                var grid = this._owner();
                return (grid && grid.options.allowSorting && this.options.allowSort && (this.options.dataIndex >= 0));
            };
            c1groupedfield.prototype._refreshHeaderCell = function () {
                var css = this._owner().options.uiCSS, $closeButton = $("<span class=\"gcui-gcuigrid-group-button-close " + css.stateDefault + " " + css.cornerAll + "\"><span class=\"" + css.icon + " " + css.iconClose + "\"></span></span>").bind("click." + this.widgetName, this, this._onCloseClick);
                this.element.html(this.options.headerText || "").prepend($closeButton).bind("click." + this.widgetName, this, $.proxy(this._onHrefClick, this));
                if(this._canSort()) {
                    switch(this.options.sortDirection) {
                        case "ascending":
                            this.element.append($("<span class=\"gcui-gcuigrid-group-button-sort " + css.icon + " " + css.iconArrowUp + "\"></span>"));
                            break;
                        case "descending":
                            this.element.append($("<span class=\"gcui-gcuigrid-group-button-sort " + css.icon + " " + css.iconArrowDown + "\"></span>"));
                            break;
                    }
                }
            };
            c1groupedfield.prototype._onCloseClick = function (args) {
                var options = args.data.options;
                if(!options.disabled) {
                    (args.data._owner())._handleUngroup(args.data.options.travIdx);
                }
                return false;
            };
            c1groupedfield.prototype._onHrefClick = function (args) {
                var gcuigrid = args.data._owner(), options = args.data.options, column;
                if(!(options).disabled && options.allowSort) {
                    column = gcui.grid.search(gcuigrid.columns(), function (test) {
                        return test.options.travIdx === options.travIdx;
                    });
                    column = (!column.found) ? gcui.grid.getColumnByTravIdx(gcuigrid.options.columns, options.travIdx).found : column.found.options;
                    if(column) {
                        gcuigrid._handleSort(column, args.ctrlKey);
                    }
                }
                return false;
            };
            c1groupedfield.prototype._isDestroyed = function () {
                return this._destroyed;
            };
            return c1groupedfield;
        })(gcui.gcuiWidget);
        grid.c1groupedfield = c1groupedfield;        
        c1groupedfield.prototype._data$prefix = "c1groupedfield";
        c1groupedfield.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options,
            {
                uiCSS: {
                    header: "ui-header ui-bar-a",
                    content: "ui-body-c",
                    stateDefault: "ui-btn-up-c",
                    stateHover: "ui-btn-down-c",
                    stateActive: "ui-btn-down-c"
                },
                allowMoving: true,
                allowSort: true,
                headerText: undefined,
                sortDirection: "none"
            });
        $.gcui.registerWidget("c1groupedfield", c1groupedfield.prototype);
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var bandProcessor = (function () {
            function bandProcessor() { }
            bandProcessor.prototype.generateSpanTable = function (root, leaves) {
                this._height = this._width = this._inc = this._shift = 0;
                this._table = [];
                this._traverseList = [];
                this._savedXPos = [];
                var spanTable = this._generateSpanTable(root, leaves, true);
                return spanTable;
            };
            bandProcessor.prototype._generateSpanTable = function (root, leaves, parentVisibility) {
                var i, j, self = this;
                this._height = this.getVisibleHeight(root, parentVisibility);
                leaves = leaves || [];
                gcui.grid.traverse(root, function (column) {
                    if(column.isLeaf) {
                        leaves.push(column);
                    }
                    self._traverseList.push(column);
                });
                this._width = leaves.length;
                for(i = 0; i < this._height; i++) {
                    this._table[i] = [];
                    for(j = 0; j < this._width; j++) {
                        this._table[i][j] = {
                            column: null,
                            colSpan: 0,
                            rowSpan: 0
                        };
                    }
                }
                this._setTableValues(root, 0, 0);
                return this._table;
            };
            bandProcessor.prototype.getVisibleHeight = function (root, parentVisibility) {
                var i, len, colVis, tmp, result = 0;
                if($.isArray(root)) {
                    for(i = 0 , len = root.length; i < len; i++) {
                        tmp = this.getVisibleHeight(root[i], parentVisibility);
                        result = Math.max(result, tmp);
                    }
                } else {
                    colVis = (root.visible === undefined) ? true : root.visible;
                    root.parentVis = colVis && parentVisibility;
                    if(root.isBand) {
                        for(i = 0 , len = root.columns.length; i < len; i++) {
                            tmp = this.getVisibleHeight(root.columns[i], root.parentVis);
                            result = Math.max(result, tmp);
                        }
                        if(!root.parentVis) {
                            return result;
                        }
                        root.isLeaf = (result === 0);
                        result++;
                    } else {
                        root.isLeaf = true;
                        if(root.parentVis) {
                            result = 1;
                        }
                    }
                }
                return result;
            };
            bandProcessor.prototype._getVisibleParent = function (column) {
                while(column) {
                    column = this._traverseList[column.parentIdx];
                    if(column && (column.parentVis || column.parentVis === undefined)) {
                        return column;
                    }
                }
                return null;
            };
            bandProcessor.prototype._setTableValues = function (root, y, x) {
                var i, len, tx, posX, parentIsLeaf, visibleParent;
                if($.isArray(root)) {
                    for(i = 0 , len = root.length; i < len; i++) {
                        this._setTableValues(root[i], y, x);
                    }
                } else {
                    if(root.travIdx === undefined) {
                        throw "undefined travIdx";
                    }
                    tx = x + this._shift;
                    if(root.parentVis) {
                        posX = tx + this._inc;
                        this._table[y][posX].column = root;
                        this._savedXPos[root.travIdx] = posX;
                    }
                    if(root.isBand) {
                        for(i = 0 , len = root.columns.length; i < len; i++) {
                            this._setTableValues(root.columns[i], y + 1, x);
                        }
                    }
                    if(root.parentVis) {
                        if(this._shift - tx === 0) {
                            this._table[y][this._savedXPos[root.travIdx]].rowSpan = this._height - y;
                            this._shift++;
                        } else {
                            this._table[y][this._savedXPos[root.travIdx]].colSpan = this._shift - tx;
                        }
                    } else {
                        if(!root.isBand && this._height > 0) {
                            visibleParent = this._getVisibleParent(root);
                            parentIsLeaf = (visibleParent) ? visibleParent.isLeaf : false;
                            if(parentIsLeaf) {
                                this._inc++;
                            }
                            if(y >= this._height) {
                                y = this._height - 1;
                            }
                            posX = x + this._shift + this._inc;
                            this._table[y][posX].column = root;
                            if(!parentIsLeaf) {
                                if(visibleParent && (this._savedXPos[visibleParent.travIdx] === posX)) {
                                    this._shiftTableElements(posX, y);
                                }
                                this._inc++;
                            }
                        }
                    }
                }
            };
            bandProcessor.prototype._shiftTableElements = function (x, untilY) {
                var i;
                for(i = 0; i < untilY; i++) {
                    this._table[i][x + 1] = this._table[i][x];
                    this._table[i][x] = {
                        column: null,
                        colSpan: 0,
                        rowSpan: 0
                    };
                    if(this._table[i][x + 1].column) {
                        this._savedXPos[this._table[i][x + 1].column.travIdx]++;
                    }
                }
            };
            return bandProcessor;
        })();
        grid.bandProcessor = bandProcessor;        
        function getAllLeaves(columns) {
            var leaves = [];
            _getAllLeaves(columns, leaves);
            return leaves;
        }
        grid.getAllLeaves = getAllLeaves;
        function _getAllLeaves(columns, leaves) {
            var i, len, column, subColumns;
            if(columns) {
                for(i = 0 , len = columns.length; i < len; i++) {
                    column = columns[i];
                    if(column.options) {
                        column = column.options;
                    }
                    subColumns = column.columns;
                    if(subColumns && subColumns.length) {
                        _getAllLeaves(subColumns, leaves);
                    } else {
                        leaves.push(column);
                    }
                }
            }
        }
        function getColumnByTravIdx(columns, travIdx) {
            var i, len, column, result = null;
            if(columns && travIdx >= 0) {
                for(i = 0 , len = columns.length; i < len && !result; i++) {
                    column = columns[i];
                    if(column.options) {
                        column = column.options;
                    }
                    if(column.travIdx === travIdx) {
                        return {
                            found: column,
                            at: columns
                        };
                    }
                    if(column.columns) {
                        result = this.getColumnByTravIdx(column.columns, travIdx);
                    }
                }
            }
            return result;
        }
        grid.getColumnByTravIdx = getColumnByTravIdx;
        function isChildOf(columns, child, parent) {
            if(child.options) {
                child = child.options;
            }
            if(parent.options) {
                parent = parent.options;
            }
            if(parent.isBand && child.parentIdx >= 0) {
                if(child.parentIdx === parent.travIdx) {
                    return true;
                }
                if(child.parentIdx > parent.travIdx) {
                    var traverse = this.flatten(columns);
                    while(true) {
                        child = traverse[child.parentIdx];
                        if(child.travIdx === parent.travIdx) {
                            return true;
                        }
                        if(child.parentIdx === -1) {
                            break;
                        }
                    }
                }
            }
            return false;
        }
        grid.isChildOf = isChildOf;
        function getLeaves(columns) {
            var leaves = [];
            _getLeaves(columns, leaves);
            return leaves;
        }
        grid.getLeaves = getLeaves;
        function _getLeaves(columns, leaves) {
            var i, len, column;
            if(columns) {
                for(i = 0 , len = columns.length; i < len; i++) {
                    column = columns[i];
                    if(column.isLeaf) {
                        leaves.push(column);
                    }
                    if(column.columns) {
                        _getLeaves(column.columns, leaves);
                    }
                }
            }
        }
        function setTraverseIndex(columns) {
            return _setTraverseIndex(columns, 0, -1);
        }
        grid.setTraverseIndex = setTraverseIndex;
        function _setTraverseIndex(columns, idx, parentIdx) {
            var i, len, column;
            if(columns) {
                for(i = 0 , len = columns.length; i < len; i++) {
                    column = columns[i];
                    if(column.options) {
                        column = column.options;
                    }
                    column.linearIdx = i;
                    column.travIdx = idx++;
                    column.parentIdx = parentIdx;
                    if(column.columns) {
                        idx = _setTraverseIndex(column.columns, idx, idx - 1);
                    }
                }
            }
            return idx;
        }
        function flatten(columns) {
            var result = [];
            gcui.grid.traverse(columns, function (column) {
                result.push(column);
            });
            return result;
        }
        grid.flatten = flatten;
            function traverse(columns, callback) {
            var i, len, column;
            if(columns && ($.isFunction(callback))) {
                for(i = 0; i < columns.length; i++) {
                    column = columns[i];
                    if(column.options) {
                        column = column.options;
                    }
                    len = columns.length;
                    callback(column, columns);
                    if(columns.length !== len) {
                        i--;
                        continue;
                    }
                    if(column.columns) {
                        gcui.grid.traverse(column.columns, callback);
                    }
                }
            }
        }
        grid.traverse = traverse;
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var settingsManager = (function () {
            function settingsManager(gcuigrid) {
                this._dvFilteringSettings = undefined;
                this._dvPagingSettings = undefined;
                this._dvSortingSettings = undefined;
                this._wgFilteringSettings = undefined;
                this._wgPagingSettings = undefined;
                this._wgSortingSettings = undefined;
                if(!gcuigrid) {
                    throw "exception";
                }
                this._grid = gcuigrid;
                this._dataView = gcuigrid._dataViewWrapper.dataView();
                this._filterCache = new gcui.grid.filterOperatorsCache(gcuigrid);
            }
            settingsManager.prototype.compareSettings = function () {
                var result = true, a, b, i, len;
                result = ((a = this.DVPagingSettings()) === (b = this.WGPagingSettings()));
                if(!result && a && b) {
                    result = (a.pageSize === b.pageSize && a.pageIndex === b.pageIndex);
                }
                if(result) {
                    result = ((a = this.DVSortingSettings()) === (b = this.WGSortingSettings()));
                    if(!result && a && b && ((len = a.length) === b.length)) {
                        result = true;
                        for(i = 0; i < len && result; i++) {
                            result = ((a[i].dataKey === b[i].dataKey) && (a[i].sortDirection === b[i].sortDirection));
                        }
                    }
                }
                if(result) {
                    a = this.DVFilteringSettings();
                    b = this.WGFilteringSettings();
                    result = gcui.grid.compareObj(a, b);
                }
                return result;
            };
            settingsManager.prototype.DVFilteringSettings = function () {
                function traverse(filter, normalizedItem) {
                    var condition;
                    $.each(filter, function (i, item) {
                        if(typeof (item) === "string") {
                            condition = item;
                        } else {
                            if($.isArray(item)) {
                                traverse(item, normalizedItem);
                            } else {
                                normalizedItem.filterValue.push(item.value);
                                normalizedItem.filterOperator.push({
                                    name: item.originalOperator || item.operator,
                                    condition: condition
                                });
                            }
                        }
                    });
                }
                if(this._dvFilteringSettings === undefined) {
                    var foo = [], filter = this._dataView.filter();
                    if(filter) {
                        $.each(filter, function (dataKey, item) {
                            var normalizedItem = {
                                dataKey: dataKey
                            };
                            if($.isArray(item)) {
                                normalizedItem.filterValue = [];
                                normalizedItem.filterOperator = [];
                                traverse(item, normalizedItem);
                            } else if($.isPlainObject(item)) {
                                normalizedItem.filterValue = item.value;
                                normalizedItem.filterOperator = item.originalOperator || item.operator || "Equals";
                            } else {
                                normalizedItem.filterValue = item;
                                normalizedItem.filterOperator = "Equals";
                            }
                            foo.push(normalizedItem);
                        });
                    }
                    this._dvFilteringSettings = (foo.length) ? foo : null;
                }
                return this._dvFilteringSettings;
            };
            settingsManager.prototype.DVPagingSettings = function () {
                if(this._dvPagingSettings === undefined) {
                    var pageableDataView = gcui.grid.asPagedDataView(this._dataView);
                    if(pageableDataView) {
                        var pageSize = pageableDataView.pageSize();
                        this._dvPagingSettings = (pageSize > 0) ? {
                            pageSize: pageableDataView.pageSize(),
                            pageIndex: pageableDataView.pageIndex()
                        } : null;
                    }
                }
                return this._dvPagingSettings;
            };
            settingsManager.prototype.DVSortingSettings = function () {
                if(this._dvSortingSettings === undefined) {
                    var foo = [];
                    if(true) {
                        var sortDescription = gcui.data.sorting.compile(this._dataView.sort()).normalized;
                        if(sortDescription) {
                            $.each(sortDescription, function (key, prop) {
                                if(prop !== null) {
                                    foo.push({
                                        dataKey: (typeof (prop) === "string") ? prop : prop.property,
                                        sortDirection: prop.asc || prop.asc === undefined ? "ascending" : "descending"
                                    });
                                }
                            });
                        }
                    }
                    this._dvSortingSettings = (foo.length) ? foo : null;
                }
                return this._dvSortingSettings;
            };
            settingsManager.prototype.WGFilteringSettings = function () {
                if(this._wgFilteringSettings === undefined) {
                    var foo = [], leaves = this._grid._field("leaves"), self = this;
                    if(leaves) {
                        $.each(leaves, function (key, leaf) {
                            if(gcui.grid.validDataKey(leaf.dataKey) && leaf.filterOperator) {
                                var fvToVerify = (gcui.grid.deepExtend({}, { foo: leaf.filterValue })).foo,
                                    fopToVerify = (gcui.grid.deepExtend({}, { foo: leaf.filterOperator })).foo,
                                    verifiedFop = gcui.grid.filterHelper.verify(fopToVerify, fvToVerify, leaf.dataType, self._filterCache);
                                if(verifiedFop) {
                                    foo.push({
                                        owner: leaf,
                                        dataKey: leaf.dataKey,
                                        filterValue: verifiedFop.filterValue,
                                        filterOperator: verifiedFop.filterOperator
                                    });
                                }
                            }
                        });
                    }
                    this._wgFilteringSettings = (foo.length) ? foo : null;
                }
                return this._wgFilteringSettings;
            };
            settingsManager.prototype.WGPagingSettings = function () {
                if(this._wgPagingSettings === undefined) {
                    this._wgPagingSettings = this._grid.options.allowPaging ? {
                        pageSize: this._grid.options.pageSize,
                        pageIndex: this._grid.options.pageIndex
                    } : null;
                }
                return this._wgPagingSettings;
            };
            settingsManager.prototype.WGSortingSettings = function () {
                if(this._wgSortingSettings === undefined) {
                    var sortDictionary = {}, sortArray = [],
                        groupedColumns = this._grid._groupedColumns(true),
                        leaves = this._grid._field("leaves") || [],
                        canSort = function (column) {
                        return (!column.isBand && column.allowSort && gcui.grid.validDataKey(column.dataKey));
                        },
                        sortOrder = 0;

                    if(this._grid.options.allowSorting) {
                        $.each(groupedColumns, function (i, leaf) {
                            if(canSort(leaf)) {
                                if(leaf.sortDirection === "none") {
                                    leaf.sortDirection = "ascending";
                                }
                                sortDictionary[leaf.dataKey] = {
                                    dataKey: leaf.dataKey,
                                    sortDirection: leaf.sortDirection,
                                    sortOrder: sortOrder++
                                };
                            }
                        });
                        sortOrder++;
                        $.each(leaves, function (i, leaf) {
                            if(canSort(leaf)) {
                                if(leaf.sortDirection === "ascending" || leaf.sortDirection === "descending") {
                                    if(!sortDictionary[leaf.dataKey]) {
                                        sortDictionary[leaf.dataKey] = {
                                            dataKey: leaf.dataKey,
                                            sortDirection: leaf.sortDirection,
                                            sortOrder: (leaf.sortOrder || 0) + sortOrder
                                        };
                                    }
                                }
                            }
                        });
                        $.each(sortDictionary, function (key, value) {
                            sortArray.push(value);
                        });
                        sortArray.sort(function (a, b) {
                            return a.sortOrder - b.sortOrder;
                        });
                        $.each(sortArray, function (i, item) {
                            delete item.sortOrder;
                        });
                    }
                    this._wgSortingSettings = (sortArray.length) ? sortArray : null;
                }
                return this._wgSortingSettings;
            };
            settingsManager.prototype.MapWGToDV = function () {
                var self = this;
                var result = {}, foo, newDVFilterOption;
                if(this._mapPagingParams() && (foo = this.WGPagingSettings())) {
                    result.pageIndex = foo.pageIndex;
                    result.pageSize = foo.pageSize;
                } else {
                    result.pageSize = -1;
                }
                if(this._mapSortingParams()) {
                    result.sort = [];
                    if(foo = this.WGSortingSettings()) {
                        result.sort = [];
                        $.each(foo, function (key, o) {
                            result.sort.push({
                                property: o.dataKey,
                                asc: o.sortDirection === "ascending"
                            });
                        });
                    }
                }
                this._grid.deficientFilters = {
                };
                if(this._mapFilteringParams()) {
                    $.each(this._grid._field("leaves"), function (key, leaf) {
                        if(gcui.grid.validDataKey(leaf.dataKey) && ((leaf.filterOperator === undefined) ^ (leaf.filterValue === undefined))) {
                            self._grid.deficientFilters[leaf.dataKey] = {
                                filterOperator: leaf.filterOperator,
                                filterValue: leaf.filterValue
                            };
                        }
                    });
                    result.filter = {
                    };
                    if(foo = this.WGFilteringSettings()) {
                        result.filter = this._convertFilterToDV(foo);
                    }
                    if($.isEmptyObject(result.filter)) {
                        result.filter = null;
                    }
                }
                return result;
            };
            settingsManager.prototype.MapDVToWG = function () {
                var foo, leavesByDataKey = {
                }, mapSortingParams = this._mapSortingParams(), mapPagingParams = this._mapPagingParams(), mapFilteringParams = this._mapFilteringParams(), pagedDataView = gcui.grid.asPagedDataView(this._dataView), self = this;
                $.each(this._grid._field("leaves"), function (key, leaf) {
                    if(mapSortingParams) {
                        leaf.sortOrder = 0;
                        leaf.sortDirection = "none";
                    }
                    if(mapFilteringParams) {
                        leaf.filterOperator = "nofilter";
                        leaf.filterValue = undefined;
                    }
                    leavesByDataKey[leaf.dataKey] = leaf;
                });
                if(mapPagingParams && pagedDataView) {
                    this._grid.options.pageSize = pagedDataView.pageSize();
                    this._grid.options.pageIndex = pagedDataView.pageIndex();
                }
                if(mapSortingParams && (foo = this.DVSortingSettings())) {
                    $.each(foo, function (idx, o) {
                        var leaf;
                        if((leaf = leavesByDataKey[o.dataKey])) {
                            leaf.sortDirection = o.sortDirection;
                            leaf.sortOrder = idx;
                        }
                    });
                }
                if(mapFilteringParams) {
                    if(foo = this.DVFilteringSettings()) {
                        $.each(foo, function (key, o) {
                            var leaf;
                            if((leaf = leavesByDataKey[o.dataKey])) {
                                leaf.filterValue = o.filterValue;
                                leaf.filterOperator = o.filterOperator;
                                if($.isPlainObject(leaf.filterOperator)) {
                                    leaf.filterOperator = leaf.filterOperator.name;
                                }
                                delete self._grid.deficientFilters[leaf.dataKey];
                            }
                        });
                    }
                    $.each(this._grid.deficientFilters, function (dataKey, defFilter) {
                        leavesByDataKey[dataKey].filterOperator = defFilter.filterOperator;
                        leavesByDataKey[dataKey].filterValue = defFilter.filterValue;
                    });
                }
            };
            settingsManager.prototype._mapPagingParams = function () {
                return this._grid.options.allowPaging && !this._grid._customPagingEnabled() && !this._grid._serverShaping();
            };
            settingsManager.prototype._mapSortingParams = function () {
                return this._grid.options.allowSorting && !this._grid._serverShaping();
            };
            settingsManager.prototype._mapFilteringParams = function () {
                return !this._grid._serverShaping();
            };
            settingsManager.prototype.makeProxyFilter = function (owner, prop, name, value) {
                name = name.toLowerCase();
                var self = this, isDateColumn = (owner.dataType === "datetime"), result = {
                    originalOperator: undefined,
                    property: prop,
                    operator: name,
                    value: value
                };
                var internalOp = this._filterCache.getByNameInt(name);
                var builtinOp = gcui.data.filtering.ops[name];
                if((name !== "nofilter") && (internalOp.isCustom || isDateColumn)) {
                    result.originalOperator = result.operator;
                    if(internalOp.isCustom) {
                        result.operator = $.extend(true, {
                        }, internalOp.op);
                        result.operator.apply = result.operator.operator;
                    } else {
                        result.operator = $.extend(true, {
                        }, builtinOp);
                        var filterRequirements = owner.inputType ? gcui.grid.TimeUnitConverter.convertInputType(owner.inputType) : gcui.grid.TimeUnitConverter.convertFormatString(owner.dataFormatString || "d");
                        result.operator.apply = function (a, b) {
                            if(!(a instanceof Date)) {
                                a = self._grid.parse(owner, a);
                            }
                            if(!(b instanceof Date)) {
                                b = self._grid.parse(owner, b);
                            }
                            gcui.grid.TimeUnitConverter.cutDate(a, filterRequirements);
                            gcui.grid.TimeUnitConverter.cutDate(b, filterRequirements);
                            return builtinOp.apply(a, b);
                        };
                    }
                }
                return result;
            };
            settingsManager.prototype._convertFilterToDV = function (normalizedFilter) {
                var result = {}, manager = this;
                $.each(normalizedFilter, function (i, group) {
                    var prop = group.dataKey, currConds = [], currConn = "and", conn, operators, values, conds;
                    if(!$.isPlainObject(group)) {
                        return;
                    }
                    operators = group.filterOperator;
                    values = group.filterValue;
                    if(operators == null) {
                        return;
                    }
                    if(!$.isArray(operators)) {
                        operators = [
                            operators
                        ];
                    }
                    if(!$.isArray(values)) {
                        values = [
                            values
                        ];
                    }
                    if(operators.length != values.length) {
                        throw "The number of filter operators must match the number of filter values";
                    }
                    if(operators.length == 0) {
                        return;
                    }
                    $.each(operators, function (i, operator) {
                        var value;
                        if(typeof operator === "string") {
                            operator = {
                                name: operator
                            };
                        }
                        if(!$.isPlainObject(operator) || !operator.name) {
                            throw "Invalid filter operator";
                        }
                        value = values[i];
                        if(!$.isArray(value)) {
                            value = [
                                value
                            ];
                        }
                        conds = $.map(value, function (operand) {
                            return manager.makeProxyFilter(group.owner, prop, operator.name, operand);
                        });
                        function adjustConds() {
                            if(conds.length > 1) {
                                conds.splice(0, 0, "or");
                            } else {
                                conds = conds[0];
                            }
                        }
                        function adjustCurrConds() {
                            if(currConn == null) {
                                currConn = conn;
                                currConds.splice(0, 0, conn);
                            }
                        }
                        currConn = null;
                        conn = operator.condition || "or";
                        if(currConds.length <= 1 || currConn == conn) {
                            if(conds.length == 1 || currConds.length <= 1 || currConn == "or") {
                                currConds = currConds.concat(conds);
                                adjustCurrConds();
                            } else {
                                adjustConds();
                                currConds.push(conds);
                                adjustCurrConds();
                            }
                        } else {
                            adjustConds();
                            currConds = [
                                currConds, 
                                conds
                            ];
                            adjustCurrConds();
                        }
                    });
                    $.each(currConds, function (j, cond) {
                        if($.isArray(cond) && cond.length == 2) {
                            currConds[j] = cond[1];
                        }
                    });
                    if(currConds.length == 2 && typeof (currConds[0] === "string")) {
                        currConds.shift();
                    }
                    if(currConds.length == 1) {
                        currConds = currConds[0];
                    }
                    result[prop] = currConds;
                });
                return result;
            };
            return settingsManager;
        })();
        grid.settingsManager = settingsManager;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var dataViewWrapper = (function () {
            function dataViewWrapper(gcuigrid) {
                this._domSource = null;
                this._ignoreAllEvents = false;
                this._ignoreChangeEvent = false;
                this._ignoreCurrentChangedEvent = false;
                this._sharedDataItems = null;
                this._userData = null;
                this._totals = null;
                this._changeTimer = 0;
                this._toDispose = [];
                this._isOwnDataView = false;
                this._isGCDataSource = false;
                this._isDynamicDataSource = false;
                this._grid = gcuigrid;
                this._createDataViewWrapper();
            }
            dataViewWrapper.prototype.data = function () {
                var dataView = this._getDataViewInst(), pagedDataView = gcui.grid.asPagedDataView(dataView);
                return {
                    data: this._getSharedDataItems(),
                    totalRows: pagedDataView != null ? pagedDataView.totalItemCount() : (dataView.getSource() || []).length,
                    totals: this._getTotals(),
                    emptyData: this.isBoundedToDOM() ? this._domSource.emptyData : null
                };
            };
            dataViewWrapper.prototype.dataView = function () {
                return this._getDataViewInst();
            };
            dataViewWrapper.prototype.dispose = function () {
                var dataView = this._getDataViewInst();
                this._propChangeListener.dispose();
                $.each(this._toDispose, function (_, disposable) {
                    disposable.dispose();
                });
                if(dataView && this._isOwnDataView) {
                    dataView.dispose();
                }
                this._grid._dataView = null;
            };
            dataViewWrapper.prototype.ignoreChangeEvent = function (value) {
                if(arguments.length) {
                    this._ignoreChangeEvent = (value === true);
                } else {
                    return this._ignoreChangeEvent;
                }
            };
            dataViewWrapper.prototype.ignoreCurrentChangedEvent = function (value) {
                if(arguments.length) {
                    this._ignoreCurrentChangedEvent = (value === true);
                } else {
                    return this._ignoreCurrentChangedEvent;
                }
            };
            dataViewWrapper.prototype.isDataLoaded = function () {
                var dataView = this._getDataViewInst();
                return dataView.isLoaded();
            };
            dataViewWrapper.prototype.isOwnDataView = function () {
                return this._isOwnDataView;
            };
            dataViewWrapper.prototype.getFieldsInfo = function () {
                var dataView = this._getDataViewInst();
                return this._propDescriptorsToFieldsInfo(dataView.getProperties());
            };
            dataViewWrapper.prototype._propDescriptorsToFieldsInfo = function (propDescriptors) {
                var result = {
                };
                if(propDescriptors) {
                    $.each(propDescriptors, function (_, prop) {
                        if(prop.name === "$$hash") {
                            return;
                        }
                        result[prop.name] = {
                            name: prop.name,
                            type: prop.type || "string"
                        };
                    });
                }
                return result;
            };
            dataViewWrapper.prototype.isBoundedToDOM = function () {
                return this._domSource !== null;
            };
            dataViewWrapper.prototype.load = function (userData) {
                this._userData = userData;
                var dataView = this._getDataViewInst();
                if(!dataView) {
                    this._createDataViewWrapper();
                    dataView = this._getDataViewInst();
                }
                this._onDataViewLoading();
                var sm = new gcui.grid.settingsManager(this._grid);
                if(this._needToLoad(sm)) {
                    var loadParams = sm.MapWGToDV(), local = false;
                    if(this._isGCDataSource && !this._isDynamicDataSource && dataView.isLoaded()) {
                        local = true;
                    }
                    this.ignoreCurrentChangedEvent(true);
                    dataView.refresh(loadParams, local);
                } else {
                    if(this.isDataLoaded()) {
                        this._onDataViewLoaded();
                        this._onDataViewReset();
                    }
                }
            };
            dataViewWrapper.prototype.currentPosition = function (rowIndex) {
                var dataView = this._getDataViewInst();
                if(!arguments.length) {
                    return dataView.currentPosition();
                }
                this.ignoreCurrentChangedEvent(true);
                try  {
                    dataView.currentPosition(rowIndex);
                }finally {
                    this.ignoreCurrentChangedEvent(false);
                }
            };
            dataViewWrapper.prototype.getValue = function (indexOrItem, key) {
                var dataView = this._getDataViewInst();
                if(typeof (key) === "number") {
                    key = key + "";
                }
                return dataView.getProperty(indexOrItem, key);
            };
            dataViewWrapper.prototype.setValue = function (indexOrItem, key, value) {
                var dataView = this._getDataViewInst();
                this.ignoreChangeEvent(true);
                try  {
                    if(typeof (key) === "number") {
                        key = key + "";
                    }
                    dataView.setProperty(indexOrItem, key, value);
                }finally {
                    this.ignoreChangeEvent(false);
                }
            };
            dataViewWrapper.prototype.makeDirty = function () {
                this._propChangeListener.removeAll();
                this._totals = null;
                this._sharedDataItems = null;
            };
            dataViewWrapper.prototype._createDataViewWrapper = function () {
                var dataItemToGetProperties, data = this._grid.options.data, dataView = this._getDataViewInst(), isGCDataSource = false;
                if(dataView) {
                    return;
                }
                if(!data) {
                    this._domSource = this._processDOM(this._grid.element, this._grid.options.readAttributesFromData);
                    data = this._domSource.items;
                }
                isGCDataSource = (typeof (gcuidatasource) !== "undefined" && (data instanceof gcuidatasource));
                this._isOwnDataView = ($.isArray(data) || isGCDataSource);
                this._isGCDataSource = !!(this._isOwnDataView && isGCDataSource);
                this._isDynamicDataSource = !!(this._isOwnDataView && isGCDataSource && data.dynamic);
                if(this._isOwnDataView) {
                    if(!this._domSource && this._grid.options.readAttributesFromData) {
                        this._moveAttributesToExpando(data);
                    }
                    var tBody = this.isBoundedToDOM() && gcui.grid.getTableSection(this._grid.element, gcui.grid.rowScope.body);
                    dataView = gcui.grid.GridDataView.create(gcui.data.asDataView(this._parseOwnData(data, tBody)));
                } else {
                    dataView = data;
                }
                this._grid._dataView = dataView;
                this._toDispose.push(dataView.isLoading.subscribe($.proxy(this._onDataViewLoadingInternal, this)));
                this._toDispose.push(dataView.isLoaded.subscribe($.proxy(this._onDataViewLoadedInternal, this)));
                this._toDispose.push(dataView.subscribe($.proxy(this._onDataViewChangeInternal, this)));
                this._toDispose.push(dataView.currentPosition.subscribe($.proxy(this._onDataViewCurrentChangedInternal, this)));
                this._propChangeListener = new gcui.grid.propChangeListener($.proxy(this._onPropertyChanged, this));
            };
            dataViewWrapper.prototype._parseOwnData = function (data, tbody) {
                var self = this;
                if(data && data.length) {
                    var columns = $.extend(true, [], this._grid.options.columns), props = gcui.data.ArrayDataViewBase._getProps(data[0]) || [], fieldsInfo = this._propDescriptorsToFieldsInfo(props), dataLeaves = [];
                    this._grid._prepareColumnOptions(columns, "merge", fieldsInfo, true, true);
                    gcui.grid.traverse(columns, function (column) {
                        if(gcui.grid.validDataKey(column.dataKey)) {
                            dataLeaves.push(column);
                        }
                    });
                    if(columns.length) {
                        $.each(data, function (i, dataItem) {
                            gcui.grid.dataViewWrapper._parseDataItem(self._grid, dataItem, (tbody && tbody.rows[i]), dataLeaves);
                        });
                    }
                }
                return data;
            };
            dataViewWrapper._parseDataItem = function _parseDataItem(parseHandler, dataItem, domRow, leaves) {
                $.each(leaves, function (i, leaf) {
                    if((gcui.grid.getDataType(leaf) !== "string") && gcui.grid.validDataKey(leaf.dataKey)) {
                        var value = dataItem[leaf.dataKey], newValue = parseHandler.parse(leaf, value);
                        if(isNaN(newValue)) {
                            var domCell = null;
                            if(domRow) {
                                domCell = domRow.cells[leaf.dataKey];
                            }
                            newValue = parseHandler.parseFailed(leaf, value, dataItem, domCell);
                        }
                        dataItem[leaf.dataKey] = newValue;
                    }
                });
                return dataItem;
            };
            dataViewWrapper.prototype._getDataViewInst = function () {
                return this._grid._dataView;
            };
            dataViewWrapper.prototype._needToLoad = function (settingsManager) {
                var dataView = this._getDataViewInst();
                if(this._isDynamicDataSource || (this._isGCDataSource && !dataView.isLoaded())) {
                    return true;
                }
                if(this._isOwnDataView && !this._isGCDataSource && this.isDataLoaded()) {
                    return true;
                }
                if(this.isDataLoaded() || dataView.isLoading()) {
                    return !settingsManager.compareSettings();
                }
                return true;
            };
            dataViewWrapper.prototype._validateSettings = function (settingsManager) {
                if(!this._isOwnDataView && this._grid.options.allowPaging && ((settingsManager.DVPagingSettings() || {
                }).pageSize !== (settingsManager.WGPagingSettings() || {
                }).pageSize)) {
                    throw "The pageSize option of the external dataView can't be changed.";
                }
            };
            dataViewWrapper.prototype._onDataViewLoadingInternal = function (isLoading) {
                if(this._ignoreAllEvents) {
                    return;
                }
                if(isLoading) {
                    if(!this._userData) {
                        this._onDataViewLoading();
                    }
                }
            };
            dataViewWrapper.prototype._onDataViewLoadedInternal = function (isLoaded) {
                if(this._ignoreAllEvents) {
                    return;
                }
                if(isLoaded) {
                    this._onDataViewLoaded();
                }
            };
            dataViewWrapper.prototype._onDataViewChangeInternal = function (args) {
                var self = this;
                if(this._ignoreAllEvents || this._ignoreChangeEvent) {
                    return;
                }
                if(args.changes) {
                    if(args.length && args[0].entityState() === "detached") {
                        return;
                    }
                    $.each(args.changes, function (_, change) {
                        switch(change.changeType) {
                            case "remove":
                                self._propChangeListener.remove(change.index);
                                break;
                            case "add":
                                self._propChangeListener.insert(change.index, change.element);
                                break;
                        }
                    });
                }
                this._onDataViewChange.apply(this, arguments);
            };
            dataViewWrapper.prototype._onDataViewCurrentChangedInternal = function () {
                if(this._ignoreAllEvents || this._ignoreCurrentChangedEvent) {
                    return;
                }
                this._onDataViewCurrentChanged.apply(this, arguments);
            };
            dataViewWrapper.prototype._onDataViewReset = function () {
                try  {
                    this.ignoreCurrentChangedEvent(false);
                    this.makeDirty();
                    this._grid._onDataViewReset(this._userData);
                }finally {
                    this._userData = null;
                }
            };
            dataViewWrapper.prototype._onPropertyChanged = function (newValue) {
                var self = this;
                if(this._changeTimer > 0) {
                    window.clearTimeout(this._changeTimer);
                    this._changeTimer = 0;
                }
                if(this._changeTimer != -1) {
                    this._changeTimer = window.setTimeout(function () {
                        self._changeTimer = -1;
                        if(!self._grid._destroyed) {
                            self._onDataViewChange();
                        }
                        self._changeTimer = 0;
                    }, 100);
                }
            };
            dataViewWrapper.prototype._onDataViewChange = function (args) {
                this._onDataViewReset();
            };
            dataViewWrapper.prototype._onDataViewCurrentChanged = function (e, args) {
                this._grid._onDataViewCurrentPositionChanged(e, args);
            };
            dataViewWrapper.prototype._onDataViewLoading = function () {
                this._grid._onDataViewLoading();
            };
            dataViewWrapper.prototype._onDataViewLoaded = function () {
                this._grid._onDataViewLoaded();
            };
            dataViewWrapper.prototype._getSharedDataItems = function () {
                if(!this._sharedDataItems) {
                    var dataView = this._getDataViewInst(), len = dataView.count();
                    this._sharedDataItems = [];
                    var firstWiredSuccessfully = true;
                    for(var i = 0; i < len; i++) {
                        var dataItem = dataView.item(i);
                        if(firstWiredSuccessfully) {
                            if(!this._propChangeListener.insert(i, dataItem) && i === 0) {
                                firstWiredSuccessfully = false;
                            }
                        }
                        this._sharedDataItems.push(this._wrapDataItem(dataItem, i));
                    }
                }
                if(!this._sharedDataItems) {
                    this._sharedDataItems = [];
                }
                return this._sharedDataItems;
            };
            dataViewWrapper.prototype._getTotals = function () {
                if(!this._totals) {
                    var dataView = this._getDataViewInst();
                    this._totals = this._prepareTotals(dataView, this._grid._prepareTotalsRequest(true));
                }
                if(!this._totals) {
                    this._totals = {
                    };
                }
                return this._totals;
            };
            dataViewWrapper.prototype._prepareTotals = function (dataView, request) {
                if(!request || request.length === 0) {
                    return {
                    };
                }
                var i, len, j, len2, tallies = [], result = {
                };
                for(i = 0 , len = request.length; i < len; i++) {
                    tallies.push(new gcui.grid.tally());
                }
                for(i = 0 , len = dataView.count(); i < len; i++) {
                    for(j = 0 , len2 = tallies.length; j < len2; j++) {
                        tallies[j].add(this._grid.parse(request[j].column, this.getValue(i, request[j].column.dataKey)));
                    }
                }
                for(i = 0 , len = tallies.length; i < len; i++) {
                    result[request[i].column.dataKey] = tallies[i].getValueString(request[i].column);
                }
                return result;
            };
            dataViewWrapper.prototype._processDOM = function ($obj, readAttributes) {
                var result = {
                    items: [],
                    emptyData: [],
                    header: gcui.grid.readTableSection($obj, gcui.grid.rowScope.head)
                };
                if(gcui.grid.getTableSectionLength($obj, 2) === 1 && $(gcui.grid.getTableSectionRow($obj, 2, 0)).hasClass("gcui-gcuigrid-emptydatarow")) {
                    result.emptyData = gcui.grid.readTableSection($obj, gcui.grid.rowScope.body);
                } else {
                    result.items = gcui.grid.readTableSection($obj, gcui.grid.rowScope.body, readAttributes);
                }
                return result;
            };
            dataViewWrapper.prototype._moveAttributesToExpando = function (rawData) {
                $.each(rawData, function (i, item) {
                    var expando = (gcui).data.Expando.getFrom(item, true), rowMeta;
                    rowMeta = expando[gcui.grid.EXPANDO] = {
                        cellsAttributes: {
                        },
                        rowAttributes: {
                        }
                    };
                    if(item.rowAttributes) {
                        rowMeta.rowAttributes = item.rowAttributes;
                        delete item.rowAttributes;
                    }
                    $.each(item, function (dataKey, dataValue) {
                        if($.isArray(dataValue)) {
                            rowMeta.cellsAttributes[dataKey] = dataValue[1];
                            item[dataKey] = dataValue[0];
                        }
                    });
                });
            };
            dataViewWrapper.prototype._wrapDataItem = function (dataItem, dataItemIndex) {
                return {
                    values: dataItem,
                    originalRowIndex: dataItemIndex
                };
            };
            dataViewWrapper.prototype._refreshSilent = function () {
                var dataView = this._getDataViewInst();
                if(dataView) {
                    try  {
                        this._ignoreAllEvents = true;
                        dataView.refresh();
                    }finally {
                        this._ignoreAllEvents = false;
                    }
                }
            };
            dataViewWrapper.prototype._unsafeReplace = function (index, newItem) {
                var dataView = this._getDataViewInst();
                if(!(dataView instanceof gcui.grid.GridDataView)) {
                    "operation is not supported";
                }
                dataView._unsafeReplace(index, newItem);
            };
            dataViewWrapper.prototype._unsafeSplice = function (index, count, item) {
                var dataView = this._getDataViewInst();
                if(!(dataView instanceof gcui.grid.GridDataView)) {
                    "operation is not supported";
                }
                if(arguments.length === 2) {
                    dataView._unsafeSplice(index, count);
                } else {
                    dataView._unsafeSplice(index, count, item);
                }
            };
            dataViewWrapper.prototype._unsafePush = function (item) {
                var dataView = this._getDataViewInst();
                if(!(dataView instanceof gcui.grid.GridDataView)) {
                    "operation is not supported";
                }
                dataView._unsafePush(item);
            };
            return dataViewWrapper;
        })();
        grid.dataViewWrapper = dataViewWrapper;        
        function asPagedDataView(dataView) {
            return dataView && ("pageCount" in dataView) ? dataView : null;
        }
        grid.asPagedDataView = asPagedDataView;
        function asEditableDataView(dataView) {
            return dataView && ("commitEdit" in dataView) ? dataView : null;
        }
        grid.asEditableDataView = asEditableDataView;
        var propChangeListener = (function () {
            function propChangeListener(callback) {
                this._subscriptions = [];
                this._callback = callback;
            }
            propChangeListener.prototype.insert = function (index, dataViewItem) {
                var itemSubscrArray = null, self = this;
                $.each(dataViewItem, function (key, value) {
                    if(self._isValidPropName(key) && value && $.isFunction(value.subscribe)) {
                        itemSubscrArray = itemSubscrArray || [];
                        itemSubscrArray.push(value.subscribe(self._callback));
                    }
                });
                if(!itemSubscrArray) {
                    return false;
                }
                if(this._subscriptions.length < index) {
                    this._subscriptions.length = index;
                }
                this._subscriptions.splice(index, 0, itemSubscrArray);
                return true;
            };
            propChangeListener.prototype.remove = function (index) {
                var subscrArray = this._subscriptions[index];
                if(subscrArray) {
                    $.each(subscrArray, function (key, propSubscr) {
                        propSubscr.dispose();
                        subscrArray[key] = null;
                    });
                }
                this._subscriptions[index] = null;
                this._subscriptions.splice(index, 1);
            };
            propChangeListener.prototype.removeAll = function () {
                var len, subscr;
                while(len = this._subscriptions.length) {
                    this.remove(len - 1);
                }
                this._subscriptions = [];
            };
            propChangeListener.prototype.dispose = function () {
                this.removeAll();
            };
            propChangeListener.prototype._isValidPropName = function (name) {
                if(name && (typeof (name) === "string")) {
                    return name.match(/^entityState|jQuery/) === null;
                }
                return true;
            };
            return propChangeListener;
        })();
        grid.propChangeListener = propChangeListener;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var groupRange = (function () {
            function groupRange(expanded, range, sum, position, hasHeaderOrFooter) {
                this.cr = new gcui.grid.cellRange(-1, -1);
                this.isExpanded = false;
                this._value = -1;
                this._sum = -1;
                this._position = "none";
                this._hasHeaderOrFooter = true;
                if(expanded !== undefined) {
                    this.isExpanded = expanded;
                }
                if(range !== undefined) {
                    this.cr = range;
                }
                if(sum !== undefined) {
                    this._sum = sum;
                }
                if(position !== undefined) {
                    this._position = position;
                }
                if(hasHeaderOrFooter !== undefined) {
                    this._hasHeaderOrFooter = hasHeaderOrFooter;
                }
            }
            groupRange.prototype.isSubRange = function (groupRange) {
                return ((this.cr.r1 >= groupRange.cr.r1) && (this.cr.r2 <= groupRange.cr.r2));
            };
            groupRange.prototype.toString = function () {
                return this.cr.r1 + "-" + this.cr.r2;
            };
            groupRange.prototype.getHeaderImageClass = function (expanded) {
                var groupInfo = this.owner;
                if(groupInfo) {
                    return expanded ? groupInfo.expandedImageClass || grid.c1field.prototype.options.groupInfo.expandedImageClass : groupInfo.collapsedImageClass || grid.c1field.prototype.options.groupInfo.collapsedImageClass;
                }
                return null;
            };
            groupRange.prototype.collapse = function () {
                var groupInfo, column, grid, leaves, groupedColumnsCnt;
                if((groupInfo = this.owner) && (column = groupInfo.owner) && (grid = column.owner)) {
                    leaves = grid._field("leaves");
                    if(gcui.grid.groupHelper.isParentExpanded(leaves, this.cr, groupInfo.level)) {
                        if((groupInfo.position !== "footer") && (groupInfo.outlineMode !== "none")) {
                            groupedColumnsCnt = gcui.grid.groupHelper.getGroupedColumnsCount(leaves);
                            this._collapse(grid._rows(), leaves, this, groupedColumnsCnt, grid._field("viewRenderBounds").start);
                        }
                    }
                }
            };
            groupRange.prototype.expand = function (expandChildren) {
                var groupInfo, column, grid, leaves, groupedColumnsCnt;
                if((groupInfo = this.owner) && (column = groupInfo.owner) && (grid = column.owner)) {
                    leaves = grid._field("leaves");
                    if(gcui.grid.groupHelper.isParentExpanded(leaves, this.cr, groupInfo.level)) {
                        groupedColumnsCnt = gcui.grid.groupHelper.getGroupedColumnsCount(leaves);
                        this._expand(grid._rows(), leaves, this, groupedColumnsCnt, expandChildren, true, grid._field("viewRenderBounds").start);
                    }
                }
            };
            groupRange.prototype._collapse = function (rowAccessor, leaves, groupRange, groupedColumnsCnt, virtualOffset) {
                var groupInfo = groupRange.owner, dataStart = groupRange.cr.r1, dataEnd = groupRange.cr.r2, i, len, childRanges, childRange, j;
                switch(groupInfo.position) {
                    case "header":
                    case "headerAndFooter":
                        this._toggleRowVisibility(rowAccessor.item(groupRange.cr.r1 - virtualOffset), undefined, false);
                        dataStart++;
                        break;
                }
                for(i = dataStart; i <= dataEnd; i++) {
                    this._toggleRowVisibility(rowAccessor.item(i - virtualOffset), false);
                }
                groupRange.isExpanded = false;
                this._updateHeaderIcon(rowAccessor, groupRange, virtualOffset);
                for(i = groupInfo.level + 1; i <= groupedColumnsCnt; i++) {
                    childRanges = gcui.grid.groupHelper.getChildGroupRanges(leaves, groupRange.cr, i - 1);
                    for(j = 0 , len = childRanges.length; j < len; j++) {
                        childRange = childRanges[j];
                        childRange.isExpanded = false;
                        switch(childRange.owner.position) {
                            case "header":
                            case "headerAndFooter":
                                this._toggleRowVisibility(rowAccessor.item(childRange.cr.r1 - virtualOffset), undefined, false);
                                break;
                        }
                        this._updateHeaderIcon(rowAccessor, childRange, virtualOffset);
                    }
                }
            };
            groupRange.prototype._expand = function (rowAccessor, leaves, groupRange, groupedColumnsCnt, expandChildren, isRoot, virtualOffset) {
                var groupInfo = groupRange.owner, dataStart = groupRange.cr.r1, dataEnd = groupRange.cr.r2, i, len, childRanges, childRange, childIsRoot;
                switch(groupInfo.position) {
                    case "header":
                        this._toggleRowVisibility(rowAccessor.item(dataStart - virtualOffset), true, isRoot || expandChildren);
                        dataStart++;
                        break;
                    case "footer":
                        this._toggleRowVisibility(rowAccessor.item(dataEnd - virtualOffset), true);
                        dataEnd--;
                        break;
                    case "headerAndFooter":
                        this._toggleRowVisibility(rowAccessor.item(dataStart - virtualOffset), true, isRoot || expandChildren);
                        if(isRoot) {
                            this._toggleRowVisibility(rowAccessor.item(dataEnd - virtualOffset), true);
                        }
                        dataStart++;
                        dataEnd--;
                        break;
                }
                if(isRoot) {
                    groupRange.isExpanded = true;
                    this._updateHeaderIcon(rowAccessor, groupRange, virtualOffset);
                } else {
                    return;
                }
                if(groupRange.owner.level === groupedColumnsCnt) {
                    for(i = dataStart; i <= dataEnd; i++) {
                        this._toggleRowVisibility(rowAccessor.item(i - virtualOffset), true);
                    }
                } else {
                    childRanges = gcui.grid.groupHelper.getChildGroupRanges(leaves, groupRange.cr, groupRange.owner.level);
                    if(childRanges.length && (dataStart !== childRanges[0].cr.r1)) {
                        for(i = dataStart; i < childRanges[0].cr.r1; i++) {
                            this._toggleRowVisibility(rowAccessor.item(i - virtualOffset), true);
                        }
                    }
                    if(expandChildren) {
                        for(i = 0 , len = childRanges.length; i < len; i++) {
                            childRange = childRanges[i];
                            this._expand(rowAccessor, leaves, childRange, groupedColumnsCnt, expandChildren, true, virtualOffset);
                        }
                    } else {
                        for(i = 0 , len = childRanges.length; i < len; i++) {
                            childRange = childRanges[i];
                            childIsRoot = (childRange.owner.position === "footer" || childRange.owner.outlineMode === "none") ? true : false;
                            this._expand(rowAccessor, leaves, childRange, groupedColumnsCnt, false, childIsRoot, virtualOffset);
                        }
                    }
                }
            };
            groupRange.prototype._toggleRowVisibility = function (rowObj, visible, expanded) {
                if(rowObj) {
                    if(rowObj[0]) {
                        if(visible !== undefined) {
                            rowObj[0].style.display = visible ? "" : "none";
                            rowObj[0]["aria-hidden"] = visible ? "false" : "true";
                        }
                        if(expanded !== undefined) {
                            rowObj[0]["aria-expanded"] = expanded ? "true" : "false";
                        }
                    }
                    if(rowObj[1]) {
                        if(visible !== undefined) {
                            rowObj[1].style.display = visible ? "" : "none";
                            rowObj[1]["aria-hidden"] = visible ? "false" : "true";
                        }
                        if(expanded !== undefined) {
                            rowObj[1]["aria-expanded"] = expanded ? "true" : "false";
                        }
                    }
                }
            };
            groupRange.prototype._updateHeaderIcon = function (rowAccessor, groupRange, virtualOffset) {
                if(groupRange.owner.position !== "footer") {
                    var rowObj = rowAccessor.item(groupRange.cr.r1 - virtualOffset);
                    if(rowObj) {
                        $.each(rowObj, function (i, row) {
                            if(row) {
                                var imageDiv = $(row).find("div.gcui-gcuigrid-grouptogglebtn:first-child");
                                if(imageDiv && imageDiv.length) {
                                    imageDiv.toggleClass(groupRange.getHeaderImageClass(!groupRange.isExpanded), false);
                                    imageDiv.toggleClass(groupRange.getHeaderImageClass(groupRange.isExpanded), true);
                                }
                            }
                        });
                    }
                }
            };
            return groupRange;
        })();
        grid.groupRange = groupRange;        
        var grouper = (function () {
            function grouper() {
                this._groupRowIdx = 0;
            }
            grouper.prototype.group = function (grid, data, leaves) {
                var self = this;
                this._grid = grid;
                this._data = data;
                this._leaves = leaves;
                this._groupRowIdx = 0;
                $.each(leaves, function (i, column) {
                    if(column.groupInfo) {
                        delete column.groupInfo.level;
                        delete column.groupInfo.expandInfo;
                    }
                });
                var groupedColumns = this._grid._groupedColumns(true), level = 1;
                $.each(groupedColumns, function (i, column) {
                    self._groupRowIdx = 0;
                    if(column.groupInfo && (column.groupInfo.position && (column.groupInfo.position !== "none")) && (column.dataIndex >= 0)) {
                        column.groupInfo.level = level;
                        column.groupInfo.expandInfo = [];
                        self._processRowGroup(column, level++);
                    }
                });
                delete this._grid;
                delete this._data;
                delete this._leaves;
            };
            grouper.prototype._processRowGroup = function (leaf, level) {
                var row, cellRange, isExpanded, startCollapsed, indentRow, groupRange, isParentCollapsed, header, footer, i, firstVisibleLeafIdx = 0, hasHeaderOrFooter = true;
                $.each(this._leaves, function (i, leaf) {
                    if(leaf.parentVis) {
                        firstVisibleLeafIdx = i;
                        return false;
                    }
                });
                for(row = 0; row < this._data.length; row++) {
                    if(!(this._data[row].rowType & gcui.grid.rowType.data)) {
                        continue;
                    }
                    cellRange = this._getGroupCellRange(row, leaf, level);
                    isExpanded = true;
                    startCollapsed = (leaf.groupInfo.outlineMode === "startCollapsed");
                    if(startCollapsed || gcui.grid.groupHelper.isParentCollapsed(this._leaves, cellRange, level)) {
                        if((leaf.groupInfo.groupSingleRow === false) && (cellRange.r1 === cellRange.r2)) {
                            continue;
                        }
                        isExpanded = false;
                    }
                    if(level && this._grid.options.groupIndent) {
                        for(indentRow = cellRange.r1; indentRow <= cellRange.r2; indentRow++) {
                            this._addIndent(this._data[indentRow][firstVisibleLeafIdx], level);
                        }
                    }
                    hasHeaderOrFooter = !(leaf.groupInfo.groupSingleRow === false && (cellRange.r1 === cellRange.r2));
                    switch(leaf.groupInfo.position) {
                        case "header":
                            groupRange = this._addGroupRange(leaf.groupInfo, cellRange, isExpanded, hasHeaderOrFooter);
                            for(i = cellRange.r1; i <= cellRange.r2; i++) {
                                this._data[i].__attr["aria-level"] = level + 1;
                                if(!isExpanded) {
                                    this._data[i].__style.display = "none";
                                    this._data[i].__attr["aria-hidden"] = true;
                                }
                            }
                            if(!hasHeaderOrFooter) {
                                break;
                            }
                            this._updateByGroupRange(groupRange, level);
                            isParentCollapsed = gcui.grid.groupHelper.isParentCollapsed(this._leaves, groupRange.cr, level);
                            header = this._buildGroupRow(groupRange, cellRange, true, isParentCollapsed);
                            this._data.splice(cellRange.r1, 0, header);
                            header.__attr["arial-level"] = level;
                            header.__attr["aria-expanded"] = isExpanded;
                            if(isParentCollapsed) {
                                header.__style.display = "none";
                                header.__attr["aria-hidden"] = true;
                            }
                            row = cellRange.r2 + 1;
                            break;
                        case "footer":
                            groupRange = this._addGroupRange(leaf.groupInfo, cellRange, true, hasHeaderOrFooter);
                            if(!hasHeaderOrFooter) {
                                break;
                            }
                            this._updateByGroupRange(groupRange, level);
                            footer = this._buildGroupRow(groupRange, cellRange, false, false);
                            footer.__attr["aria-level"] = level;
                            this._data.splice(cellRange.r2 + 1, 0, footer);
                            row = cellRange.r2 + 1;
                            isParentCollapsed = gcui.grid.groupHelper.isParentCollapsed(this._leaves, groupRange.cr, level);
                            if(isParentCollapsed) {
                                footer.__style.display = "none";
                                footer.__attr["aria-hidden"] = true;
                            }
                            break;
                        case "headerAndFooter":
                            groupRange = this._addGroupRange(leaf.groupInfo, cellRange, isExpanded, hasHeaderOrFooter);
                            for(i = cellRange.r1; i <= cellRange.r2; i++) {
                                this._data[i].__attr["aria-level"] = level + 1;
                                if(!isExpanded) {
                                    this._data[i].__style.display = "none";
                                    this._data[i].__attr["aria-hidden"] = true;
                                }
                            }
                            if(!hasHeaderOrFooter) {
                                break;
                            }
                            this._updateByGroupRange(groupRange, level);
                            isParentCollapsed = gcui.grid.groupHelper.isParentCollapsed(this._leaves, groupRange.cr, level);
                            header = this._buildGroupRow(groupRange, cellRange, true, isParentCollapsed);
                            footer = this._buildGroupRow(groupRange, cellRange, false, false);
                            this._data.splice(cellRange.r2 + 1, 0, footer);
                            footer.__attr["aria-level"] = level;
                            if(isParentCollapsed || !isExpanded) {
                                footer.__style.display = "none";
                                footer.__attr["aria-hidden"] = true;
                            }
                            this._data.splice(cellRange.r1, 0, header);
                            header.__attr["aria-level"] = level;
                            header.__attr["aria-expanded"] = isExpanded;
                            if(isParentCollapsed) {
                                header.__style.display = "none";
                                header.__attr["aria-hidden"] = true;
                            }
                            row = cellRange.r2 + 2;
                            break;
                        default:
                            throw gcui.grid.stringFormat("Unknown Position value: \"{0}\"", leaf.groupInfo.position);
                    }
                    this._groupRowIdx++;
                }
            };
            grouper.prototype._buildGroupRow = function (groupRange, cellRange, isHeader, isParentCollapsed) {
                var groupInfo = groupRange.owner, leaf = groupInfo.owner, gridView = leaf.owner, row = [], groupByText = "", aggregate = "", tmp, cell, caption, args, span, col, bFirst, agg;
                (row).__style = {};
                (row).__attr = {};
                (row).__attr.id = ((isHeader) ? "GH" : "GF") + this._groupRowIdx + "-" + groupInfo.level;
                (row).rowType = (isHeader) ? gcui.grid.rowType.groupHeader : gcui.grid.rowType.groupFooter;
                if((leaf.dataIndex >= 0) && ((tmp = this._data[cellRange.r1][leaf.dataIndex].value) !== null)) {
                    groupByText = gridView.toStr(leaf, tmp);
                }
                if(this._grid._showRowHeader()) {
                    row.push({
                        html: "&nbsp;"
                    });
                }
                cell = {
                    html: "",
                    __attr: {},
                    __style: {}
                };
                if(isHeader && groupInfo.outlineMode !== "none") {
                    if(groupRange.isExpanded) {
                        cell.html = "<div class=\"" + gridView.options.uiCSS.icon + " " + groupRange.getHeaderImageClass(true) + " gcui-gcuigrid-grouptogglebtn\">&nbsp;</div>";
                    } else {
                        cell.html = "<div class=\"" + gridView.options.uiCSS.icon + " " + groupRange.getHeaderImageClass(false) + " gcui-gcuigrid-grouptogglebtn\">&nbsp;</div>";
                    }
                }
                row.push(cell);
                if(leaf.aggregate && (leaf.aggregate !== "none")) {
                    aggregate = this._getAggregate(cellRange, leaf, leaf, isHeader, groupByText);
                }
                caption = (isHeader) ? groupInfo.headerText : groupInfo.footerText;
                if(caption === "custom") {
                    args = {
                        data: this._data,
                        column: leaf,
                        groupByColumn: groupInfo.owner,
                        groupText: groupByText,
                        text: "",
                        groupingStart: cellRange.r1,
                        groupingEnd: cellRange.r2,
                        isGroupHeader: isHeader,
                        aggregate: aggregate
                    };
                    if(this._grid._trigger("groupText", null, args)) {
                        caption = args.text;
                    }
                } else {
                    if((caption === undefined) || (caption === null)) {
                        if(isHeader) {
                            caption = "{1}: {0}";
                        }
                        if(aggregate || (aggregate === 0)) {
                            caption = caption ? caption + " {2}" : "{2}";
                        }
                    }
                    caption = gcui.grid.stringFormat(caption, groupByText, leaf && leaf.headerText ? leaf.headerText : "", aggregate.toString());
                }
                if(!caption) {
                    caption = "&nbsp;";
                }
                cell.html += "<span>" + caption + "</span>";
                this._addIndent(cell, groupInfo.level - 1);
                span = 1;
                col = (this._grid._showRowHeader()) ? 1 : 0;
                bFirst = true;
                for(; col < this._leaves.length; col++) {
                    tmp = this._leaves[col];
                    if(tmp.parentVis) {
                        if(bFirst) {
                            bFirst = false;
                            continue;
                        }
                        if((tmp.dynamic !== true) && tmp.aggregate && (tmp.aggregate !== "none")) {
                            break;
                        }
                        span++;
                    }
                }
                for(; col < this._leaves.length; col++) {
                    tmp = this._leaves[col];
                    if(tmp.parentVis) {
                        agg = this._getAggregate(cellRange, tmp, groupInfo.owner, isHeader, groupByText);
                        if(!agg && (agg !== 0)) {
                            agg = "&nbsp;";
                        }
                        row.push({
                            html: agg.toString(),
                            __attr: {
                                groupInfo: {
                                    leafIndex: tmp.leavesIdx,
                                    purpose: gcui.grid.groupRowCellPurpose.aggregateCell
                                }
                            }
                        });
                    }
                }
                cell.__attr.colSpan = span;
                cell.__attr.groupInfo = {
                    leafIndex: leaf.leavesIdx,
                    purpose: gcui.grid.groupRowCellPurpose.groupCell
                };
                return row;
            };
            grouper.prototype._getAggregate = function (cellRange, column, groupByColumn, isGroupHeader, groupByText) {
                var aggregate = "", args, tally, row;
                if(!column.aggregate || (column.aggregate === "none")) {
                    return aggregate;
                }
                if(column.aggregate === "custom") {
                    args = {
                        data: this._data,
                        column: column,
                        groupByColumn: groupByColumn,
                        groupText: groupByText,
                        text: "",
                        groupingStart: cellRange.r1,
                        groupingEnd: cellRange.r2,
                        isGroupHeader: isGroupHeader
                    };
                    if(this._grid._trigger("groupAggregate", null, args)) {
                        aggregate = args.text;
                    }
                } else {
                    tally = new gcui.grid.tally();
                    for(row = cellRange.r1; row <= cellRange.r2; row++) {
                        tally.add(this._data[row][column.dataIndex].value);
                    }
                    aggregate = tally.getValueString(column);
                }
                return aggregate;
            };
            grouper.prototype._getGroupCellRange = function (row, leaf, level) {
                var idx = leaf.leavesIdx, range = new gcui.grid.cellRange(row, idx), parentRange = gcui.grid.groupHelper.getParentGroupRange(this._leaves, range, level), value, nextValue, count;
                if(this._data[row].rowType & gcui.grid.rowType.data) {
                    value = this._data[row][leaf.dataIndex].value;
                    if(value instanceof Date) {
                        value = value.getTime();
                    }
                    for(range.r2 = row , count = this._data.length - 1; range.r2 < count; range.r2++) {
                        if(!(this._data[range.r2 + 1].rowType & gcui.grid.rowType.data) || (parentRange && (range.r2 + 1 > parentRange.cr.r2))) {
                            break;
                        }
                        nextValue = this._data[range.r2 + 1][leaf.dataIndex].value;
                        if(nextValue instanceof Date) {
                            nextValue = nextValue.getTime();
                        }
                        if(value !== nextValue) {
                            break;
                        }
                    }
                }
                return range;
            };
            grouper.prototype._addGroupRange = function (groupInfo, cellRange, isExpanded, hasHeaderOrFooter) {
                var result = null, idx = gcui.grid.groupHelper.getChildGroupIndex(cellRange, groupInfo.expandInfo), range, expandState, r1, r2;
                if(idx >= 0 && idx < groupInfo.expandInfo.length) {
                    result = groupInfo.expandInfo[idx];
                } else {
                    range = new gcui.grid.cellRange(cellRange.r1, cellRange.r1, cellRange.r2, cellRange.r2);
                    expandState = (groupInfo.position === "footer" || !hasHeaderOrFooter) ? true : isExpanded && (groupInfo.outlineMode !== "startCollapsed");
                    result = new gcui.grid.groupRange(expandState, range, -1, groupInfo.position, hasHeaderOrFooter);
                    result.owner = groupInfo;
                    groupInfo.expandInfo.push(result);
                }
                if(result && hasHeaderOrFooter) {
                    r1 = cellRange.r1;
                    r2 = cellRange.r2;
                    if(groupInfo.position === "headerAndFooter") {
                        r2 += 2;
                    }
                    if(groupInfo.position !== "headerAndFooter") {
                        r2++;
                    }
                    result.cr.r2 = r2;
                }
                return result;
            };
            grouper.prototype._updateByGroupRange = function (groupRange, level) {
                var i, len, groupInfo, len2, j, cur, delta;
                for(i = 0 , len = this._leaves.length; i < len; i++) {
                    groupInfo = this._leaves[i].groupInfo;
                    if(groupInfo && (groupInfo.level < level)) {
                        len2 = (groupInfo.expandInfo) ? groupInfo.expandInfo.length : 0;
                        for(j = 0; j < len2; j++) {
                            cur = groupInfo.expandInfo[j];
                            delta = (groupRange.position === "headerAndFooter") ? 2 : 1;
                            if(cur.cr.r1 >= groupRange.cr.r1 && !((cur.cr.r1 === groupRange.cr.r1) && (cur.position === "footer"))) {
                                cur.cr.r1 += delta;
                            }
                            if(cur.cr.r2 >= groupRange.cr.r1) {
                                cur.cr.r2 += delta;
                            }
                        }
                    }
                }
            };
            grouper.prototype._addIndent = function (cellObj, level) {
                var indent;
                if(level > 0 && (indent = this._grid.options.groupIndent)) {
                    cellObj.__style.paddingLeft = (indent * level) + "px";
                }
            };
            return grouper;
        })();
        grid.grouper = grouper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var groupHelper = (function () {
            function groupHelper() { }
            groupHelper._getGroupInfoRegExp = new RegExp(".*G([HF]){1}(\\d+)-(\\d+)$");
            groupHelper.getGroupInfo = function getGroupInfo(row) {
                if(row) {
                    var info = gcui.grid.groupHelper._getGroupInfoRegExp.exec(row.id), level, index, isHeader;
                    if(info) {
                        level = parseInt(info[3], 10);
                        index = parseInt(info[2], 10);
                        isHeader = (info[1] === "H");
                        return {
                            level: level,
                            index: index,
                            isHeader: isHeader,
                            toString: function () {
                                return (isHeader ? "GH" : "GF") + index + "-" + level;
                            }
                        };
                    }
                }
                return null;
            };
            groupHelper.getColumnByGroupLevel = function getColumnByGroupLevel(leaves, level) {
                var i, len, leaf;
                for(i = 0 , len = leaves.length; i < len; i++) {
                    leaf = leaves[i];
                    if(leaf.groupInfo && (leaf.groupInfo.level === level)) {
                        return leaf;
                    }
                }
                return null;
            };
            groupHelper.getGroupedColumnsCount = function getGroupedColumnsCount(leaves) {
                var result = 0, i, len, groupInfo;
                for(i = 0 , len = leaves.length; i < len; i++) {
                    groupInfo = leaves[i].groupInfo;
                    if(groupInfo && (groupInfo.position === "header" || groupInfo.position === "headerAndFooter" || groupInfo.position === "footer")) {
                        result++;
                    }
                }
                return result;
            };
            groupHelper.getChildGroupIndex = function getChildGroupIndex(cellRange, childExpandInfo) {
                var left = 0, right = childExpandInfo.length - 1, median, cmp;
                while(left <= right) {
                    median = ((right - left) >> 1) + left;
                    cmp = childExpandInfo[median].cr.r1 - cellRange.r1;
                    if(cmp === 0) {
                        return median;
                    }
                    if(cmp < 0) {
                        left = median + 1;
                    } else {
                        right = median - 1;
                    }
                }
                return left;
            };
            groupHelper.getParentGroupIndex = function getParentGroupIndex(cellRange, parentExpandInfo) {
                var idx = gcui.grid.groupHelper.getChildGroupIndex(cellRange, parentExpandInfo);
                if(idx > 0) {
                    idx--;
                }
                return (idx < parentExpandInfo.length) ? idx : -1;
            };
            groupHelper.getChildGroupRanges = function getChildGroupRanges(leaves, cellRange, level) {
                var result = [], childRanges, childRange, i, len, firstChildIdx, childGroupedColumn = gcui.grid.groupHelper.getColumnByGroupLevel(leaves, level + 1);
                if(childGroupedColumn) {
                    childRanges = childGroupedColumn.groupInfo.expandInfo;
                    firstChildIdx = gcui.grid.groupHelper.getChildGroupIndex(cellRange, childRanges);
                    for(i = firstChildIdx , len = childRanges.length; i < len; i++) {
                        childRange = childRanges[i];
                        if(childRange.cr.r2 <= cellRange.r2) {
                            result.push(childRange);
                        } else {
                            break;
                        }
                    }
                }
                return result;
            };
            groupHelper.getParentGroupRange = function getParentGroupRange(leaves, cellRange, level) {
                var i, groupInfo, idx;
                if(level === undefined) {
                    level = 0xFFFF;
                }
                if(level - 2 >= 0) {
                    for(i = leaves.length - 1; i >= 0; i--) {
                        groupInfo = leaves[i].groupInfo;
                        if(!groupInfo || !groupInfo.expandInfo || (groupInfo.level < 0) || (groupInfo.level !== level - 1)) {
                            continue;
                        }
                        idx = gcui.grid.groupHelper.getParentGroupIndex(cellRange, groupInfo.expandInfo);
                        if(idx >= 0) {
                            return groupInfo.expandInfo[idx];
                        }
                    }
                }
                return null;
            };
            groupHelper.isParentCollapsed = function isParentCollapsed(leaves, cellRange, level) {
                var i, parentGroupRange;
                if(level === 1) {
                    return false;
                }
                for(i = level; i > 1; i--) {
                    parentGroupRange = gcui.grid.groupHelper.getParentGroupRange(leaves, cellRange, i);
                    if(parentGroupRange && !parentGroupRange.isExpanded) {
                        return true;
                    }
                    cellRange = parentGroupRange.cr;
                }
                return false;
            };
            groupHelper.isParentExpanded = function isParentExpanded(leaves, cellRange, level) {
                var i, parentGroupRange;
                if(level === 1) {
                    return true;
                }
                for(i = level; i > 1; i--) {
                    parentGroupRange = gcui.grid.groupHelper.getParentGroupRange(leaves, cellRange, i);
                    if(parentGroupRange && parentGroupRange.isExpanded) {
                        return true;
                    }
                    cellRange = parentGroupRange.cr;
                }
                return false;
            };
            return groupHelper;
        })();
        grid.groupHelper = groupHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var cellRange = (function () {
            function cellRange(row1, col1, row2, col2) {
                switch(arguments.length) {
                    case 2:
                        this.r1 = this.r2 = row1;
                        this.c1 = this.c2 = col1;
                        break;
                    case 4:
                        this.r1 = row1;
                        this.r2 = row2;
                        this.c1 = col1;
                        this.c2 = col2;
                        break;
                    default:
                        this.r1 = 0;
                        this.r2 = 0;
                        this.c1 = 0;
                        this.c2 = 0;
                }
            }
            cellRange.prototype.isSingleCell = function () {
                return ((this.r1 === this.r2) && (this.c1 === this.c2));
            };
            return cellRange;
        })();
        grid.cellRange = cellRange;        
        var merger = (function () {
            function merger() {
            }
            merger.prototype.merge = function (data, visibleLeaves) {
                this._leaves = visibleLeaves;
                this._data = data;
                var i, len, leaf;
                for(i = 0 , len = this._leaves.length; i < len; i++) {
                    leaf = this._leaves[i];
                    if((leaf.dataIndex >= 0) && !leaf.isBand && (leaf.rowMerge === "free" || leaf.rowMerge === "restricted")) {
                        this._mergeColumn(leaf);
                    }
                }
                delete this._data;
                delete this._leaves;
            };
            merger.prototype._mergeColumn = function (column) {
                var dataIdx = column.dataIndex, i, len, range, span, spannedRow;
                for(i = 0 , len = this._data.length; i < len; i++) {
                    if(!(this._data[i].rowType & gcui.grid.rowType.data)) {
                        continue;
                    }
                    range = this._getCellRange(i, column);
                    if(range.r1 !== range.r2) {
                        span = range.r2 - range.r1 + 1;
                        this._data[range.r1][dataIdx].__attr.rowSpan = span;
                        for(spannedRow = range.r1 + 1; spannedRow <= range.r2; spannedRow++) {
                            this._data[spannedRow][dataIdx].visible = false;
                        }
                    }
                    i = range.r2;
                }
            };
            merger.prototype._getCellRange = function (rowIdx, column) {
                var columnIdx = column.dataIndex, range = new gcui.grid.cellRange(rowIdx, columnIdx), str = (this._data[rowIdx][columnIdx].value || "").toString(), dataLen = this._data.length, dataItem, leafIdx, prevLeaf, range2;
                for(range.r2 = rowIdx; range.r2 < dataLen - 1; range.r2++) {
                    dataItem = this._data[range.r2 + 1];
                    if(!(dataItem.rowType & gcui.grid.rowType.data) || ((dataItem[columnIdx].value || "").toString() !== str)) {
                        break;
                    }
                }
                leafIdx = column.leavesIdx;
                if(leafIdx > 0 && column.rowMerge === "restricted") {
                    prevLeaf = this._leaves[leafIdx - 1];
                    if(prevLeaf.dataIndex >= 0) {
                        range2 = this._getCellRange(rowIdx, prevLeaf);
                        range.r1 = Math.max(range.r1, range2.r1);
                        range.r2 = Math.min(range.r2, range2.r2);
                    }
                }
                return range;
            };
            return merger;
        })();
        grid.merger = merger;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        grid.EXPANDO = "__gcuigrid";
        (function (rowType) {
            rowType._map = [];
            rowType.header = 1;
            rowType.data = 2;
            rowType.dataAlt = 4;
            rowType.filter = 8;
            rowType.groupHeader = 16;
            rowType.groupFooter = 32;
            rowType.footer = 64;
            rowType.emptyDataRow = 128;
        })(grid.rowType || (grid.rowType = {}));
        var rowType = grid.rowType;
        (function (renderState) {
            renderState._map = [];
            renderState.none = 0;
            renderState.rendering = 1;
            renderState.current = 2;
            renderState.hovered = 4;
            renderState.selected = 8;
        })(grid.renderState || (grid.renderState = {}));
        var renderState = grid.renderState;
        (function (rowScope) {
            rowScope._map = [];
            rowScope.table = 0;
            rowScope.head = 1;
            rowScope.body = 2;
            rowScope.foot = 3;
        })(grid.rowScope || (grid.rowScope = {}));
        var rowScope = grid.rowScope;
        (function (cellRangeExtendMode) {
            cellRangeExtendMode._map = [];
            cellRangeExtendMode.none = 0;
            cellRangeExtendMode.column = 1;
            cellRangeExtendMode.row = 2;
        })(grid.cellRangeExtendMode || (grid.cellRangeExtendMode = {}));
        var cellRangeExtendMode = grid.cellRangeExtendMode;
        (function (objectMode) {
            objectMode._map = [];
            objectMode.createIfNull = 0;
            objectMode.createAlways = 1;
            objectMode.dispose = 2;
        })(grid.objectMode || (grid.objectMode = {}));
        var objectMode = grid.objectMode;
        (function (groupRowCellPurpose) {
            groupRowCellPurpose._map = [];
            groupRowCellPurpose.groupCell = 0;
            groupRowCellPurpose.aggregateCell = 1;
        })(grid.groupRowCellPurpose || (grid.groupRowCellPurpose = {}));
        var groupRowCellPurpose = grid.groupRowCellPurpose;
        function compareObj(a, b) {
            var i, len, flag;
            if($.isArray(a) && $.isArray(b)) {
                if(a.length === b.length) {
                    flag = true;
                    for(i = 0 , len = a.length; i < len && flag; i++) {
                        flag = gcui.grid.compareObj(a[i], b[i]);
                    }
                    return flag;
                }
            } else {
                if($.isPlainObject(a) && $.isPlainObject(b)) {
                    for(i in a) {
                        if(a.hasOwnProperty(i)) {
                            if(!gcui.grid.compareObj(a[i], b[i])) {
                                return false;
                            }
                        }
                    }
                    for(i in b) {
                        if(b.hasOwnProperty(i)) {
                            if(!gcui.grid.compareObj(a[i], b[i])) {
                                return false;
                            }
                        }
                    }
                    return true;
                } else {
                    if(a instanceof Date) {
                        a = a.getTime();
                    }
                    if(b instanceof Date) {
                        b = b.getTime();
                    }
                }
            }
            return a === b;
        }
        grid.compareObj = compareObj;
        function stringFormat(pattern) {
            var params = [];
            for (var _i = 0; _i < (arguments.length - 1); _i++) {
                params[_i] = arguments[_i + 1];
            }
            var i, len;
            if(!pattern) {
                return "";
            }
            for(i = 0 , len = params.length; i < len; i++) {
                pattern = pattern.replace(new RegExp("\\{" + i + "\\}", "gm"), params[i]);
            }
            return pattern;
        }
        grid.stringFormat = stringFormat;
        function validDataKey(dataKey) {
            return (dataKey && !(dataKey < 0)) || (dataKey === 0);
        }
        grid.validDataKey = validDataKey;
        function validDate(date) {
            if(date && (date instanceof Date)) {
                return !isNaN(date.getTime());
            }
            return false;
        }
        grid.validDate = validDate;
        function getDataType(column) {
            return column.dataType || column._underlyingDataType || "string";
        }
        grid.getDataType = getDataType;
        function iterateChildrenWidgets(item, callback) {
            if(item && callback) {
                item.find(".ui-widget").each(function (index, dom) {
                    $.each($(dom).data(), function (dataKey, dataValue) {
                        if(dataValue.widgetName) {
                            callback(index, dataValue);
                        }
                    });
                    return true;
                });
            }
        }
        grid.iterateChildrenWidgets = iterateChildrenWidgets;
        function remove$dataByPrefix(element, prefix) {
            var data$keys = [];
            $.each(element.data(), function (key) {
                if(key.indexOf(prefix) === 0) {
                    data$keys.push(key);
                }
            });
            $.each(data$keys, function (idx, key) {
                element.removeData(key);
            });
        }
        grid.remove$dataByPrefix = remove$dataByPrefix;
        var domSelection = (function () {
            function domSelection(dom) {
                this._dom = dom;
            }
            domSelection.prototype.getSelection = function () {
                var start = 0, end = 0, textRange;
                if(this._dom.selectionStart !== undefined) {
                    start = this._dom.selectionStart;
                    end = this._dom.selectionEnd;
                } else {
                    if(document.selection) {
                        textRange = document.selection.createRange().duplicate();
                        end = textRange.text.length;
                        start = Math.abs(textRange.moveStart("character", -this._dom.value.length));
                        end += start;
                    }
                }
                return {
                    start: start,
                    end: end,
                    length: end - start
                };
            };
            domSelection.prototype.setSelection = function (range) {
                if(this._dom.selectionStart !== undefined) {
                    this._dom.setSelectionRange(range.start, range.end);
                } else {
                    var textRange = this._dom.createTextRange();
                    textRange.collapse(true);
                    textRange.moveStart("character", range.start);
                    textRange.moveEnd("character", range.end);
                    textRange.select();
                }
            };
            domSelection.prototype.toggleSelection = function (enable) {
                var $dom = $(this._dom), useSelectStart = "onselectstart" in this._dom;
                if(enable) {
                    if(useSelectStart) {
                        $dom.unbind(".gcuigrid-disableSelection");
                    } else {
                        $dom.css({
                            "MozUserSelect": "",
                            "WebkitUserSelect": ""
                        });
                    }
                } else {
                    if(useSelectStart) {
                        $dom.bind("selectstart.gcuigrid-disableSelection", function (e) {
                            e.preventDefault();
                        });
                    } else {
                        $dom.css({
                            "MozUserSelect": "-moz-none",
                            "WebkitUserSelect": "none"
                        });
                    }
                }
            };
            return domSelection;
        })();
        grid.domSelection = domSelection;        
        function createDynamicField(options) {
            return $.extend(true, {}, grid.c1basefield.prototype.options, grid.c1field.prototype.options, {
                dynamic: true,
                isLeaf: true,
                isBand: false,
                parentIdx: -1
            }, options);
        }
        grid.createDynamicField = createDynamicField;
        function bounds(element, client) {
            if(element) {
                var $dom = element.nodeType ? $(element) : element, offset = $dom.offset();
                if(offset) {
                    if(client) {
                        return {
                            top: offset.top,
                            left: offset.left,
                            width: $dom[0].clientWidth || 0,
                            height: $dom[0].clientHeight || 0
                        };
                    }
                    return {
                        top: offset.top,
                        left: offset.left,
                        width: $dom.outerWidth(),
                        height: $dom.outerHeight()
                    };
                }
            }
            return null;
        }
        grid.bounds = bounds;
        function _getDOMText(dom, maxDepth, ignoreTextNodes) {
            if(maxDepth === undefined) {
                maxDepth = 3;
            }
            if(dom && maxDepth !== 0) {
                if(!ignoreTextNodes && dom.nodeType === 3) {
                    return dom.nodeValue;
                }
                if(dom.nodeType === 1) {
                    switch((dom).type) {
                        case "button":
                        case "text":
                        case "textarea":
                        case "select-one":
                            return (dom).value;
                        case "checkbox":
                            return (dom).checked.toString();
                    }
                    var result = "", i = 0, child;
                    while(child = dom.childNodes[i++]) {
                        result += gcui.grid._getDOMText(child, maxDepth - 1);
                    }
                    return result;
                }
            }
            return "";
        }
        grid._getDOMText = _getDOMText;
        function dataPrefix(obj, prefix, name, value) {
            var treatAsArray = (obj.jquery || $.isArray(obj)), i, len, tmp, internalName = prefix + name;
            if(arguments.length === 3) {
                if(treatAsArray) {
                    return $.data(obj[0], internalName);
                }
                return $.data(obj, internalName);
            } else {
                if(treatAsArray) {
                    for(i = 0 , len = obj.length; i < len; i++) {
                        tmp = $.data(obj[i], internalName, value);
                    }
                    return tmp;
                }
                return $.data(obj, internalName, value);
            }
        }
        grid.dataPrefix = dataPrefix;
        function shallowMerge(target, src) {
            if(src && target) {
                var name, value, typeOf;
                for(name in src) {
                    if(src.hasOwnProperty(name)) {
                        value = src[name];
                        typeOf = typeof (value);
                        if((typeOf === "string" || typeOf === "boolean" || typeOf === "number") && (target[name] === undefined)) {
                            target[name] = value;
                        }
                    }
                }
            }
        }
        grid.shallowMerge = shallowMerge;
        function isCustomObject(value) {
            return (value && (typeof (value) === "object") && !(value instanceof Date));
        }
        grid.isCustomObject = isCustomObject;
        function search(value, test) {
            var key, foo, isFunc = $.isFunction(test);
            for(key in value) {
                if(value.hasOwnProperty(key)) {
                    foo = isFunc ? test(value[key]) : (value[key] === test);
                    if(foo === true) {
                        return {
                            at: key,
                            found: value[key]
                        };
                    }
                }
            }
            return {
                at: null,
                found: null
            };
        }
        grid.search = search;
        function getAttributes(dom, prevent) {
            if(dom) {
                var i, len, cnt = 0, result = {}, attrValue, attrName;
                for(i = 0 , len = dom.attributes.length; i < len; i++) {
                    attrName = dom.attributes[i].name;
                    if(attrName && (!prevent || !prevent(attrName))) {
                        attrValue = dom.getAttribute(attrName);
                        if(attrName === "style") {
                            attrValue = (typeof (attrValue) === "object") ? attrValue.cssText : attrValue;
                        }
                        if(!attrValue && attrName === "class") {
                            attrValue = dom.getAttribute("className");
                        }
                        if(attrValue && (typeof (attrValue) !== "function")) {
                            result[attrName] = attrValue;
                            cnt++;
                        }
                    }
                }
                if(cnt) {
                    return result;
                }
            }
            return null;
        }
        grid.getAttributes = getAttributes;
        function deepExtend(source, target) {
            var key, src, dst, isArray, clone;
            if(source) {
                if(typeof (target) !== "object" && !$.isFunction(target)) {
                    target = {};
                }
                for(key in source) {
                    src = source[key];
                    dst = target[dst];
                    if(src === target) {
                        continue;
                    }
                    if(src && ($.isPlainObject(src) || (isArray = $.isArray(src)))) {
                        if(isArray) {
                            isArray = false;
                            clone = dst && $.isArray(dst) ? dst : [];
                        } else {
                            clone = dst && $.isPlainObject(dst) ? dst : {};
                        }
                        target[key] = gcui.grid.deepExtend(src, clone);
                    } else {
                        target[key] = src;
                    }
                }
            }
            return target;
        }
        grid.deepExtend = deepExtend;
                        function widgetName(element, name) {
            if(element && element.jquery) {
                element = element[0];
            }
            if(element) {
                return (arguments.length === 1) ? $.data(element, "gcuigridwidgetName") : $.data(element, "gcuigridwidgetName", name);
            }
            return undefined;
        }
        grid.widgetName = widgetName;
        var HTML5InputSupport = (function () {
            function HTML5InputSupport() { }
            HTML5InputSupport._requiresExtendedSupport = {
                "date": "",
                "datetime": "",
                "datetime-local": "",
                "month": "",
                "time": ""
            };
            HTML5InputSupport.isExtendSupportRequired = function isExtendSupportRequired(inputType) {
                inputType = (inputType || "").toLowerCase();
                return (inputType in gcui.grid.HTML5InputSupport._requiresExtendedSupport);
            };
            HTML5InputSupport.getDefaultInputType = function getDefaultInputType(mobileEnvironment, column) {
                var inputType = column.inputType;
                if(!inputType) {
                    inputType = "text";
                    if(mobileEnvironment) {
                        switch(gcui.grid.getDataType(column)) {
                            case "number":
                            case "currency":
                                inputType = "number";
                                break;
                            case "datetime":
                                inputType = "datetime";
                                break;
                        }
                    }
                }
                return inputType;
            };
            HTML5InputSupport.toStr = function toStr(value, inputType) {
                var result = value;
                inputType = (inputType || "").toLowerCase();
                if(gcui.grid.HTML5InputSupport.isExtendSupportRequired(inputType)) {
                    switch(inputType) {
                        case "datetime":
                            result = (value) ? result = Globalize.format(value, "yyyy-MM-ddTHH:mm:ssZ") : "";
                            break;
                        case "datetime-local":
                            result = (value) ? result = Globalize.format(value, "yyyy-MM-ddTHH:mm:ss") : "";
                            break;
                        case "date":
                            result = (value) ? result = Globalize.format(value, "yyyy-MM-dd") : "";
                            break;
                        case "month":
                            result = (value) ? result = Globalize.format(value, "yyyy-MM") : "";
                            break;
                        case "time":
                            result = (value) ? result = Globalize.format(value, "HH:mm:ss") : "";
                            break;
                    }
                } else {
                    result = value + "";
                }
                return result;
            };
            HTML5InputSupport.parse = function parse(value, inputType) {
                var result, fallback = function (date) {
                    date = new Date(date);
                    if(!gcui.grid.validDate(date)) {
                        date = null;
                    }
                    return date;
                };
                inputType = (inputType || "").toLowerCase();
                if(gcui.grid.HTML5InputSupport.isExtendSupportRequired(inputType)) {
                    switch(inputType) {
                        case "datetime":
                            result = Globalize.parseDate(value, "yyyy-MM-ddTHH:mm:ssZ") || Globalize.parseDate(value, "yyyy-MM-ddTHH:mmZ") || fallback(value);
                            break;
                        case "datetime-local":
                            result = Globalize.parseDate(value, "yyyy-MM-ddTHH:mm:ss") || Globalize.parseDate(value, "yyyy-MM-ddTHH:mm") || fallback(value);
                            break;
                        case "date":
                            result = Globalize.parseDate(value, "yyyy-MM-dd") || fallback(value);
                            break;
                        case "month":
                            result = Globalize.parseDate(value, "yyyy-MM");
                            break;
                        case "time":
                            result = Globalize.parseDate(value, "HH:mm:ss") || Globalize.parseDate(value, "HH:mm");
                            ;
                            break;
                        case "number":
                            result = parseFloat(value);
                    }
                } else {
                    result = value;
                }
                return result;
            };
            HTML5InputSupport.extend = function extend(value, extendWith, inputType) {
                if(!value) {
                    value = extendWith;
                } else {
                    inputType = (inputType || "").toLowerCase();
                    switch(inputType) {
                        case "date":
                            value.setFullYear(extendWith.getFullYear(), extendWith.getMonth(), extendWith.getDate());
                            break;
                        case "month":
                            value.setFullYear(extendWith.getFullYear(), extendWith.getMonth());
                            break;
                        case "time":
                            value.setHours(extendWith.getHours());
                            value.setMinutes(extendWith.getMinutes());
                            value.setSeconds(extendWith.getSeconds());
                            break;
                        default:
                            value = extendWith;
                    }
                }
                return value;
            };
            return HTML5InputSupport;
        })();
        grid.HTML5InputSupport = HTML5InputSupport;        
        function isOverAxis(x, reference, size) {
            return (x > reference) && (x < (reference + size));
        }
        grid.isOverAxis = isOverAxis;
        function isOver(y, x, top, left, height, width) {
            return gcui.grid.isOverAxis(y, top, height) && gcui.grid.isOverAxis(x, left, width);
        }
        grid.isOver = isOver;
        var __uid = 0;
        function getUID() {
            return "uid" + __uid++;
        }
        grid.getUID = getUID;
        $.extend($.gcui.gcuigrid, {
            rowType: gcui.grid.rowType,
            renderState: gcui.grid.renderState,
            bounds: gcui.grid.bounds
        });
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        (function (TimeUnit) {
            TimeUnit._map = [];
            TimeUnit.Millisecond = 1;
            TimeUnit.Second = 2;
            TimeUnit.Minute = 4;
            TimeUnit.Hour = 8;
            TimeUnit.Day = 16;
            TimeUnit.Month = 32;
            TimeUnit.Year = 64;
            TimeUnit.DATE = TimeUnit.Year | TimeUnit.Month | TimeUnit.Day;
            TimeUnit.TIME = TimeUnit.Hour | TimeUnit.Minute | TimeUnit.Second | TimeUnit.Millisecond;
            TimeUnit.ALL = TimeUnit.DATE | TimeUnit.TIME;
        })(grid.TimeUnit || (grid.TimeUnit = {}));
        var TimeUnit = grid.TimeUnit;
        ;
        var TimeUnitConverter = (function () {
            function TimeUnitConverter() { }
            TimeUnitConverter.convertInputType = function convertInputType(inputType) {
                var formatString = gcui.grid.TimeUnitConverter.convertInputTypeToFormatString(inputType), result = gcui.grid.TimeUnitConverter.convertFormatString(formatString);
                return result;
            };
            TimeUnitConverter.convertInputTypeToFormatString = function convertInputTypeToFormatString(inputType) {
                switch((inputType || "").toLowerCase()) {
                    case "datetime":
                    case "datetime-local":
                        return "f";
                    case "date":
                        return "d";
                    case "month":
                        return "Y";
                    case "time":
                        return "t";
                }
                return "";
            };
            TimeUnitConverter.convertFormatString = function convertFormatString(dateFormatString) {
                var result = 0;
                if(dateFormatString) {
                    if(dateFormatString.length === 1) {
                        switch(dateFormatString[0]) {
                            case "t":
                                return TimeUnit.Hour | TimeUnit.Minute;
                            case "T":
                                return TimeUnit.TIME;
                            case "d":
                            case "D":
                                return TimeUnit.DATE;
                            case "Y":
                                return TimeUnit.Month | TimeUnit.Year;
                            case "M":
                                return TimeUnit.Month | TimeUnit.Day;
                            case "f" , "F" , "S":
                                return TimeUnit.ALL;
                        }
                    }
                    var quoteFirst, quoteLast;
                    if(((quoteFirst = dateFormatString.indexOf("'")) >= 0) && ((quoteLast = dateFormatString.lastIndexOf("'")) >= 0) && (quoteFirst !== quoteLast)) {
                        dateFormatString = dateFormatString.substr(0, quoteFirst) + dateFormatString.substring(quoteLast + 1, dateFormatString.length - 1);
                    }
                    for(var i = 0, len = dateFormatString.length; i < len; i++) {
                        switch(dateFormatString[i]) {
                            case "d":
                                result |= TimeUnit.Day;
                                break;
                            case "M":
                                result |= TimeUnit.Month;
                                break;
                            case "y":
                                result |= TimeUnit.Year;
                                break;
                            case "m":
                                result |= TimeUnit.Minute;
                                break;
                            case "h":
                            case "H":
                                result |= TimeUnit.Hour;
                                break;
                            case "s":
                                result |= TimeUnit.Second;
                                break;
                            case "f":
                                result |= TimeUnit.Millisecond;
                                break;
                        }
                    }
                }
                return result || TimeUnit.ALL;
            };
            TimeUnitConverter.cutDate = function cutDate(date, timeUnit) {
                if(date) {
                    timeUnit = ~timeUnit;
                    if(timeUnit & TimeUnit.Millisecond) {
                        date.setMilliseconds(0);
                    }
                    if(timeUnit & TimeUnit.Second) {
                        date.setSeconds(0);
                    }
                    if(timeUnit & TimeUnit.Minute) {
                        date.setMinutes(0);
                    }
                    if(timeUnit & TimeUnit.Hour) {
                        date.setHours(0);
                    }
                    if(timeUnit & TimeUnit.Day) {
                        date.setDate(1);
                    }
                    if(timeUnit & TimeUnit.Month) {
                        date.setMonth(0);
                    }
                    if(timeUnit & TimeUnit.Year) {
                        date.setFullYear(0);
                    }
                }
                return date;
            };
            return TimeUnitConverter;
        })();
        grid.TimeUnitConverter = TimeUnitConverter;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var builtInFilterOperators = (function () {
            function builtInFilterOperators() { }
            builtInFilterOperators.NoFilterOp = {
                applicableTo: null,
                name: "NoFilter",
                arity: 1,
                operator: function () {
                    return true;
                }
            };
            return builtInFilterOperators;
        })();        
        var filterOperatorsCache = (function () {
            function filterOperatorsCache(gcuigrid) {
                this._cache = {
                };
                var self = this;
                this._grid = gcuigrid;
                this._addOperator(null, builtInFilterOperators.NoFilterOp);
                $.each(gcui.data.filtering.ops, function (name, op) {
                    self._addOperator(name, op);
                });
                $.each(gcuigrid.options.customFilterOperators, function (key, fop) {
                    self._addOperator(null, fop, true);
                });
            }
            filterOperatorsCache.prototype.getByName = function (name) {
                var fop = this.getByNameInt(name);
                return (fop) ? fop.op : null;
            };
            filterOperatorsCache.prototype.getByNameInt = function (name) {
                return this._cache[(name || "").toLowerCase()];
            };
            filterOperatorsCache.prototype.getByDataType = function (dataType) {
                var intResult = [], result;
                $.each(this._cache, function (key, val) {
                    var fop = val.op;
                    if(!fop.applicableTo || $.inArray(dataType, fop.applicableTo) >= 0) {
                        intResult.push(val);
                    }
                });
                switch(this._grid.options.filterOperatorsSortMode.toLowerCase()) {
                    case "alphabetical":
                        intResult.sort(this._sortAlpha);
                        break;
                    case "alphabeticalcustomfirst":
                        intResult.sort(this._sortAlphaCustomFirst);
                        break;
                    case "alphabeticalembeddedFirst":
                        intResult.sort(this._sortAlphaEmbeddedFirst);
                        break;
                    case "none":
                        break;
                    default:
                        break;
                }
                result = $.map(intResult, function (val, key) {
                    return val.op;
                });
                return result;
            };
            filterOperatorsCache.prototype._addOperator = function (name, fop, isCustom) {
                if (typeof isCustom === "undefined") { isCustom = false; }
                if(name && !fop.name) {
                    fop.name = name;
                }
                name = (name || fop.name).toLowerCase();
                if(!this._cache[name]) {
                    this._cache[name] = {
                        op: fop,
                        isCustom: (isCustom === true)
                    };
                }
            };
            filterOperatorsCache.prototype._sortAlpha = function (a, b) {
                var n1 = a.op.name.toLowerCase(), n2 = b.op.name.toLowerCase();
                if(n1 !== n2) {
                    if(n1 === "nofilter") {
                        return -1;
                    }
                    if(n2 === "nofilter") {
                        return 1;
                    }
                }
                if(n1 === n2) {
                    return 0;
                }
                return (n1 < n2) ? -1 : 1;
            };
            filterOperatorsCache.prototype._sortAlphaEmbeddedFirst = function (a, b) {
                var n1 = a.op.name.toLowerCase(), n2 = b.op.name.toLowerCase();
                if(n1 !== n2) {
                    if(n1 === "nofilter") {
                        return -1;
                    }
                    if(n2 === "nofilter") {
                        return 1;
                    }
                }
                if(a.isCustom !== b.isCustom) {
                    if(a.isCustom) {
                        return 1;
                    }
                    if(b.isCustom) {
                        return -1;
                    }
                }
                if(n1 === n2) {
                    return 0;
                }
                return (n1 < n2) ? -1 : 1;
            };
            filterOperatorsCache.prototype._sortAlphaCustomFirst = function (a, b) {
                var n1 = a.op.name.toLowerCase(), n2 = b.op.name.toLowerCase();
                if(n1 !== n2) {
                    if(n1 === "nofilter") {
                        return -1;
                    }
                    if(n2 === "nofilter") {
                        return 1;
                    }
                }
                if(a.isCustom !== b.isCustom) {
                    if(a.isCustom) {
                        return -1;
                    }
                    if(b.isCustom) {
                        return 1;
                    }
                }
                if(n1 === n2) {
                    return 0;
                }
                return (n1 < n2) ? -1 : 1;
            };
            return filterOperatorsCache;
        })();
        grid.filterOperatorsCache = filterOperatorsCache;        
        var filterHelper = (function () {
            function filterHelper() { }
            filterHelper.marker = "_gcuigrid";
            filterHelper.getSingleValue = function getSingleValue(filterValue) {
                if($.isArray(filterValue)) {
                    filterValue = filterValue[0];
                    if($.isArray(filterValue)) {
                        filterValue = filterValue[0];
                    }
                }
                return filterValue;
            };
            filterHelper.getSingleOperatorName = function getSingleOperatorName(filterOperator) {
                if($.isArray(filterOperator)) {
                    filterOperator = filterOperator[0];
                }
                return filterOperator.name || filterOperator || "";
            };
            filterHelper.verify = function verify(filterOperator, filterValue, dataType, cache) {
                if(filterOperator) {
                    if($.isArray(filterOperator)) {
                        var i, len, fop = [], fval = [];
                        if(!$.isArray(filterValue)) {
                            filterValue = [
                                filterValue
                            ];
                        }
                        for(i = 0 , len = filterOperator.length; i < len; i++) {
                            if(gcui.grid.filterHelper._verifySingleOp(filterOperator[i], filterValue[i], dataType, cache)) {
                                fop.push({
                                    name: filterOperator[i].name || filterOperator[i],
                                    condition: filterOperator[i].condition || "or"
                                });
                                fval.push(filterValue ? filterValue[i] : undefined);
                            }
                        }
                        if(fop.length) {
                            return {
                                filterOperator: fop,
                                filterValue: fval
                            };
                        }
                    } else {
                        if(gcui.grid.filterHelper._verifySingleOp(filterOperator, filterValue, dataType, cache)) {
                            return {
                                filterOperator: filterOperator,
                                filterValue: filterValue
                            };
                        }
                    }
                }
                return null;
            };
            filterHelper._verifySingleOp = function _verifySingleOp(filterOperator, filterValue, dataType, cache) {
                if(filterOperator && (filterOperator = (filterOperator.name || filterOperator))) {
                    var fop;
                    filterOperator = (filterOperator || "").toLowerCase();
                    if((filterOperator !== "nofilter" || filterValue !== undefined) && (fop = cache.getByName(filterOperator))) {
                        if(fop.applicableTo === null || $.inArray(dataType || "string", fop.applicableTo) >= 0) {
                            if(fop.arity === 1 || (fop.arity > 1 && gcui.grid.filterHelper.getSingleValue(filterValue) !== undefined)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            };
            return filterHelper;
        })();
        grid.filterHelper = filterHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        function getTableSection(table, scope) {
            if(table && !table.nodeType) {
                table = table[0];
            }
            if(table) {
                switch(scope) {
                    case gcui.grid.rowScope.head:
                        return table.tHead;
                    case gcui.grid.rowScope.body:
                        if(table.tBodies) {
                            return table.tBodies[0] || null;
                        }
                        break;
                    case gcui.grid.rowScope.foot:
                        return table.tFoot;
                    default:
                        return table;
                }
            }
            return null;
        }
        grid.getTableSection = getTableSection;
        function getTableSectionLength(table, scope) {
            var section;
            if(table && !table.nodeType) {
                table = table[0];
            }
            return (table && (section = this.getTableSection(table, scope))) ? section.rows.length : 0;
        }
        grid.getTableSectionLength = getTableSectionLength;
                        function getTableSectionRow(table, scope, rowIndex) {
            var section;
            if(table && !table.nodeType) {
                table = table[0];
            }
            return (table && (section = this.getTableSection(table, scope))) ? (section.rows[rowIndex] || null) : null;
        }
        grid.getTableSectionRow = getTableSectionRow;
        function readTableSection(table, scope, readAttributes) {
            var ri, rowLen, ci, celLen, domRow, row, expando, rowAttributes, result = [],
                prevent = function (attrName) {
                attrName = attrName.toLowerCase();
                return attrName === "rowspan" || attrName === "colspan";
                },
                section;

            if(table && !table.nodeType) {
                table = table[0];
            }
            if(table && (section = this.getTableSection(table, scope))) {
                for(ri = 0 , rowLen = section.rows.length; ri < rowLen; ri++) {
                    domRow = section.rows[ri];
                    row = [];
                    if(readAttributes) {
                        expando = (gcui).data.Expando.getFrom(row, true);
                        rowAttributes = expando[gcui.grid.EXPANDO] = {
                            cellsAttributes: {},
                            rowAttributes: gcui.grid.getAttributes(domRow) || {}
                        };
                    }
                    for(ci = 0 , celLen = domRow.cells.length; ci < celLen; ci++) {
                        row[ci] = $.trim((domRow.cells[ci]).innerHTML);
                        if(readAttributes) {
                            rowAttributes.cellsAttributes[ci] = gcui.grid.getAttributes(domRow.cells[ci], prevent) || {};
                        }
                    }
                    result[ri] = row;
                }
            }
            return result;
        }
        grid.readTableSection = readTableSection;
        var htmlTableAccessor = (function () {
            function htmlTableAccessor(domTable, skipOffsets, ensureTBody, ensureColgroup) {
                this._width = 0;
                this._table = domTable;
                this._offsets = [];
                if(ensureColgroup) {
                    this.ensureColGroup();
                }
                if(ensureTBody) {
                    this.ensureTBody();
                }
                if(!skipOffsets) {
                    this._buildOffsets();
                }
            }
            htmlTableAccessor.prototype.element = function () {
                return this._table;
            };
            htmlTableAccessor.prototype.width = function () {
                return this._width;
            };
            htmlTableAccessor.prototype.getCellIdx = function (colIdx, rowIdx) {
                return (colIdx < this._width) ? this._offsets[rowIdx][colIdx].cellIdx : -1;
            };
            htmlTableAccessor.prototype.getColumnIdx = function (cellIdx, rowIdx) {
                if(typeof (cellIdx) !== "number") {
                    var domCell = cellIdx;
                    cellIdx = domCell.cellIndex;
                    rowIdx = domCell.parentNode.rowIndex;
                }
                return (cellIdx < this._width) ? this._offsets[rowIdx][cellIdx].colIdx : -1;
            };
            htmlTableAccessor.prototype.clearSection = function (scope) {
                var start, end, section = gcui.grid.getTableSection(this._table, scope);
                switch(scope) {
                    case gcui.grid.rowScope.body:
                        start = this.getSectionLength(gcui.grid.rowScope.table);
                        end = start + this.getSectionLength(scope) - 1;
                        break;
                    case gcui.grid.rowScope.foot:
                        start = this.getSectionLength(gcui.grid.rowScope.table) + this.getSectionLength(gcui.grid.rowScope.head);
                        end = start + this.getSectionLength(scope) - 1;
                        break;
                    default:
                        start = 0;
                        end = this.getSectionLength(scope) - 1;
                }
                while(section.rows.length) {
                    section.deleteRow(0);
                }
                this._offsets.splice(start, end - start + 1);
            };
            htmlTableAccessor.prototype.getSectionLength = function (scope) {
                return gcui.grid.getTableSectionLength(this._table, scope);
            };
            htmlTableAccessor.prototype.getSectionRow = function (rowIndex, scope) {
                return gcui.grid.getTableSectionRow(this._table, scope, rowIndex);
            };
            htmlTableAccessor.prototype.forEachColumnCellNatural = function (columnIdx, callback, param) {
                var i, rowLen, row, result;
                for(i = 0 , rowLen = this._table.rows.length; i < rowLen; i++) {
                    row = this._table.rows[i];
                    if(columnIdx < row.cells.length) {
                        result = callback(row.cells[columnIdx], i, param);
                        if(result !== true) {
                            return result;
                        }
                    }
                }
                return true;
            };
            htmlTableAccessor.prototype.forEachColumnCell = function (columnIdx, callback, param) {
                var i, rowLen, row, offsetCellIdx, result;
                for(i = 0 , rowLen = this._offsets.length; i < rowLen; i++) {
                    row = this._table.rows[i];
                    offsetCellIdx = this.getCellIdx(columnIdx, i);
                    if(offsetCellIdx >= 0) {
                        result = callback(row.cells[offsetCellIdx], i, param);
                        if(result !== true) {
                            return result;
                        }
                    }
                }
                return true;
            };
            htmlTableAccessor.prototype.forEachRowCell = function (rowIndex, callback, param) {
                var row = this._table.rows[rowIndex], i, celLen, result;
                for(i = 0 , celLen = row.cells.length; i < celLen; i++) {
                    result = callback(row.cells[i], i, param);
                    if(result !== true) {
                        return result;
                    }
                }
                return true;
            };
            htmlTableAccessor.prototype.colGroupTag = function () {
                var cgs = this._table.getElementsByTagName("colgroup");
                return ((cgs && cgs[0])) || null;
            };
            htmlTableAccessor.prototype.colTags = function () {
                var colGroup = this.colGroupTag();
                return (colGroup && colGroup.getElementsByTagName("col")) || [];
            };
            htmlTableAccessor.prototype.ensureTBody = function () {
                return ((this._table.tBodies && this._table.tBodies[0]) || this._table.appendChild(document.createElement("tbody")));
            };
            htmlTableAccessor.prototype.ensureTHead = function () {
                return (this._table.tHead && this._table.tHead[0]) || this._table.createTHead();
            };
            htmlTableAccessor.prototype.ensureTFoot = function () {
                return (this._table.tFoot && this._table.tFoot[0]) || this._table.createTFoot();
            };
            htmlTableAccessor.prototype.ensureColGroup = function () {
                var colGroup = this._table.getElementsByTagName("colgroup");
                return ((colGroup && colGroup[0]) || this._table.appendChild(document.createElement("colgroup")));
            };
            htmlTableAccessor.prototype.appendCol = function (domCol) {
                var colGroup = this.ensureColGroup();
                return ((domCol && colGroup.appendChild(domCol)) || colGroup.appendChild(document.createElement("col")));
            };
            htmlTableAccessor.prototype.removeOffset = function (idx) {
                if(idx >= 0 && idx < this._offsets.length) {
                    if(idx < 0 || (!idx && idx !== 0)) {
                        idx = this._offsets.length - 1;
                    }
                    this._offsets.splice(idx, 1);
                }
            };
            htmlTableAccessor.prototype.insertOffset = function (idx) {
                var row, i;
                if(this._width > 0) {
                    row = [];
                    for(i = 0; i < this._width; i++) {
                        row.push({
                            cellIdx: i,
                            colIdx: i
                        });
                    }
                    if(idx < 0 || (!idx && idx !== 0)) {
                        idx = this._offsets.length;
                    }
                    this._offsets.splice(idx, 0, row);
                }
            };
            htmlTableAccessor.prototype.rebuildOffsets = function () {
                this._offsets = [];
                this._width = 0;
                this._buildOffsets();
            };
            htmlTableAccessor.prototype._buildOffsets = function () {
                var rowSpan = [], rowOffsets, i, rowLen, row, j, jOffset, celLen, cell, cs, rowSpanLen;
                for(i = 0 , rowLen = this._table.rows.length; i < rowLen; i++) {
                    rowOffsets = [];
                    this._offsets[i] = rowOffsets;
                    row = this._table.rows[i];
                    for(j = 0 , jOffset = 0 , celLen = row.cells.length; j < celLen; j++ , jOffset++) {
                        cell = row.cells[j];
                        for(; rowSpan[jOffset] > 1; jOffset++) {
                            rowSpan[jOffset]--;
                            rowOffsets[jOffset] = {
                                cellIdx: -1,
                                colIdx: -1
                            };
                        }
                        if(!(rowSpan[jOffset] > 1)) {
                            rowSpan[jOffset] = cell.rowSpan;
                        }
                        rowOffsets[jOffset] = {
                            cellIdx: j,
                            colIdx: -1
                        };
                        rowOffsets[j].colIdx = jOffset;
                        cs = cell.colSpan;
                        for(; cs > 1; cs--) {
                            rowOffsets[++jOffset] = {
                                cellIdx: -1,
                                colIdx: -1
                            };
                        }
                    }
                    rowSpanLen = rowSpan.length;
                    for(; jOffset < rowSpanLen; jOffset++) {
                        rowSpan[jOffset]--;
                        rowOffsets[jOffset] = {
                            cellIdx: -1,
                            colIdx: -1
                        };
                    }
                    this._width = Math.max(this._width, rowSpanLen);
                }
            };
            return htmlTableAccessor;
        })();
        grid.htmlTableAccessor = htmlTableAccessor;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var cellInfo = (function () {
            function cellInfo(cellIndex, rowIndex, gcuigrid, absolute) {
                if (typeof gcuigrid === "undefined") { gcuigrid = null; }
                if (typeof absolute === "undefined") { absolute = false; }
                this.__isEdit = false;
                this._grid = gcuigrid;
                if(absolute) {
                    this._cia = cellIndex;
                    this._ria = rowIndex;
                } else {
                    this._ci = cellIndex;
                    this._ri = rowIndex;
                }
            }
            cellInfo.outsideValue = new cellInfo(-1, -1, null, true);
            cellInfo.prototype.cellIndexAbs = function (value) {
                if(arguments.length === 0) {
                    this._ensureCia();
                    return this._cia;
                }
                this._ci = undefined;
                this._cia = value;
            };
            cellInfo.prototype.rowIndexAbs = function (value) {
                if(arguments.length === 0) {
                    this._ensureRia();
                    return this._ria;
                }
                this._ri = undefined;
                this._ria = value;
            };
            cellInfo.prototype.cellIndex = function (value) {
                if(arguments.length === 0) {
                    this._ensureCi();
                    return this._ci;
                }
                this._cia = undefined;
                this._ci = value;
            };
            cellInfo.prototype.column = function () {
                if(this._grid && this._isValid()) {
                    return this._grid._field("visibleLeaves")[this.cellIndexAbs()];
                }
                return null;
            };
            cellInfo.prototype.container = function () {
                var tableCell = this.tableCell(), $innerDiv;
                if(tableCell) {
                    $innerDiv = $(tableCell).children("div.gcui-gcuigrid-innercell");
                    if($innerDiv) {
                        return $innerDiv;
                    }
                }
                return null;
            };
            cellInfo.prototype.isEqual = function (value) {
                return (value && (value.rowIndex() === this.rowIndex()) && (value.cellIndex() === this.cellIndex()));
            };
            cellInfo.prototype.row = function () {
                var rowObj = null, result = null;
                if(this._grid) {
                    rowObj = this._grid._view().rows().item(this.rowIndexAbs());
                    if(rowObj && rowObj.length) {
                        result = this._grid._view()._getRowInfo(rowObj);
                    }
                }
                return result;
            };
            cellInfo.prototype.rowIndex = function (value) {
                if(arguments.length === 0) {
                    this._ensureRi();
                    return this._ri;
                }
                this._ria = undefined;
                this._ri = value;
            };
            cellInfo.prototype.tableCell = function () {
                if(this._grid && this._isValid()) {
                    return this._grid._view().getCell(this.cellIndexAbs(), this.rowIndexAbs());
                }
                return null;
            };
            cellInfo.prototype.value = function (value) {
                var column, rowInfo, colVal;
                if(this._grid && this._isValid()) {
                    rowInfo = this._grid._view()._getRowInfo(this._grid._rows().item(this.rowIndex()));
                    if(rowInfo.type & gcui.grid.rowType.data) {
                        column = this.column();
                        if(arguments.length === 0) {
                            colVal = this._grid._dataViewWrapper.getValue(rowInfo.data, column.dataKey);
                            return this._grid.parse(column, colVal);
                        } else {
                            value = this._grid.parse(column, value);
                            if((value === null && column.valueRequired) || ((gcui.grid.getDataType(column) !== "string") && isNaN(value))) {
                                throw "invalid value";
                            }
                            this._grid._dataViewWrapper.setValue(rowInfo.dataItemIndex, column.dataKey, value);
                        }
                    }
                }
            };
            cellInfo.prototype.toString = function () {
                return this.cellIndex() + ":" + this.rowIndex();
            };
            cellInfo.prototype._clip = function (range, absolute) {
                if (typeof absolute === "undefined") { absolute = false; }
                var flag = false, val;
                if(absolute) {
                    if(this.cellIndexAbs() < (val = range.topLeft().cellIndexAbs())) {
                        flag = true;
                        this._cia = val;
                        this._ci = undefined;
                    }
                    if(this.cellIndexAbs() > (val = range.bottomRight().cellIndexAbs())) {
                        flag = true;
                        this._cia = val;
                        this._ci = undefined;
                    }
                    if(this.rowIndexAbs() < (val = range.topLeft().rowIndexAbs())) {
                        flag = true;
                        this._ria = val;
                        this._ri = undefined;
                    }
                    if(this.rowIndexAbs() > (val = range.bottomRight().rowIndexAbs())) {
                        flag = true;
                        this._ria = val;
                        this._ri = undefined;
                    }
                } else {
                    if(this.cellIndex() < (val = range.topLeft().cellIndex())) {
                        flag = true;
                        this._ci = val;
                        this._cia = undefined;
                    }
                    if(this.cellIndex() > (val = range.bottomRight().cellIndex())) {
                        flag = true;
                        this._ci = val;
                        this._cia = undefined;
                    }
                    if(this.rowIndex() < (val = range.topLeft().rowIndex())) {
                        flag = true;
                        this._ri = val;
                        this._ria = undefined;
                    }
                    if(this.rowIndex() > (val = range.bottomRight().rowIndex())) {
                        flag = true;
                        this._ri = val;
                        this._ria = undefined;
                    }
                }
                return flag;
            };
            cellInfo.prototype._clone = function () {
                return new gcui.grid.cellInfo(this.cellIndex(), this.rowIndex(), this._grid);
            };
            cellInfo.prototype._isValid = function () {
                return this.cellIndex() >= 0 && this.rowIndex() >= 0;
            };
            cellInfo.prototype._isEdit = function (value) {
                if(!arguments.length) {
                    return this.__isEdit;
                }
                this.__isEdit = value;
            };
            cellInfo.prototype._setGridView = function (value) {
                this._grid = value;
            };
            cellInfo.prototype._ensureCia = function () {
                if(this._cia === undefined) {
                    if(this._ci === null) {
                        throw "relative index value is undefined";
                    }
                    if(this._ci >= 0) {
                        if(!this._grid) {
                            throw "gcuigrid is null";
                        }
                        this._cia = this._ci + this._grid._getDataToAbsOffset().x;
                    } else {
                        this._cia = this._ci;
                    }
                    if(this._cia < 0) {
                        this._cia = -1;
                    }
                }
            };
            cellInfo.prototype._ensureRia = function () {
                if(this._ria === undefined) {
                    if(this._ri === undefined) {
                        throw "relative index value is undefined";
                    }
                    if(this._ri >= 0) {
                        if(!this._grid) {
                            throw "gcuigrid is null";
                        }
                        this._ria = this._ri + this._grid._getDataToAbsOffset().y;
                    } else {
                        this._ria = this._ri;
                    }
                    if(this._ria < 0) {
                        this._ria = -1;
                    }
                }
            };
            cellInfo.prototype._ensureCi = function () {
                if(this._ci === undefined) {
                    if(this._cia === undefined) {
                        throw "absolute index value is undefined";
                    }
                    if(this._cia >= 0) {
                        if(!this._grid) {
                            throw "gcuigrid is null";
                        }
                        this._ci = this._cia - this._grid._getDataToAbsOffset().x;
                    } else {
                        this._ci = this._cia;
                    }
                    if(this._ci < 0) {
                        this._ci = -1;
                    }
                }
            };
            cellInfo.prototype._ensureRi = function () {
                if(this._ri === undefined) {
                    if(this._ria === undefined) {
                        throw "relative index value is undefined";
                    }
                    if(this._ria >= 0) {
                        if(!this._grid) {
                            throw "gcuigrid is null";
                        }
                        this._ri = this._ria - this._grid._getDataToAbsOffset().y;
                    } else {
                        this._ri = this._ria;
                    }
                    if(this._ri < 0) {
                        this._ri = -1;
                    }
                }
            };
            return cellInfo;
        })();
        grid.cellInfo = cellInfo;        
        var cellInfoRange = (function () {
            function cellInfoRange(topLeft, bottomRight) {
                if(!topLeft || !bottomRight) {
                    throw "invalid arguments";
                }
                this._topLeft = topLeft._clone();
                this._bottomRight = bottomRight._clone();
            }
            cellInfoRange.prototype.bottomRight = function () {
                return this._bottomRight;
            };
            cellInfoRange.prototype.isEqual = function (range) {
                return (range && this._topLeft.isEqual(range.topLeft()) && this._bottomRight.isEqual(range.bottomRight()));
            };
            cellInfoRange.prototype.topLeft = function () {
                return this._topLeft;
            };
            cellInfoRange.prototype.toString = function () {
                return this._topLeft.toString() + " - " + this._bottomRight.toString();
            };
            cellInfoRange.prototype._isIntersect = function (range) {
                var rangeH, thisH, rangeW, thisW;
                if(range) {
                    rangeH = range.bottomRight().rowIndex() - range.topLeft().rowIndex() + 1;
                    thisH = this._bottomRight.rowIndex() - this._topLeft.rowIndex() + 1;
                    if((range.topLeft().rowIndex() + rangeH) - this._topLeft.rowIndex() < rangeH + thisH) {
                        rangeW = range.bottomRight().cellIndex() - range.topLeft().cellIndex() + 1;
                        thisW = this._bottomRight.cellIndex() - this._topLeft.cellIndex() + 1;
                        return ((range.topLeft().cellIndex() + rangeW) - this._topLeft.cellIndex() < rangeW + thisW);
                    }
                }
                return false;
            };
            cellInfoRange.prototype._isValid = function () {
                return this._topLeft._isValid() && this._bottomRight._isValid();
            };
            cellInfoRange.prototype._clip = function (clipBy, absolute) {
                if (typeof absolute === "undefined") { absolute = false; }
                var a = this._topLeft._clip(clipBy, absolute);
                var b = this._bottomRight._clip(clipBy, absolute);
                return a || b;
            };
            cellInfoRange.prototype._clone = function () {
                return new cellInfoRange(this._topLeft._clone(), this._bottomRight._clone());
            };
            cellInfoRange.prototype._containsCellInfo = function (info) {
                return (info && info.cellIndex() >= this._topLeft.cellIndex() && info.cellIndex() <= this._bottomRight.cellIndex() && info.rowIndex() >= this._topLeft.rowIndex() && info.rowIndex() <= this._bottomRight.rowIndex());
            };
            cellInfoRange.prototype._containsCellRange = function (range) {
                return (range && this._containsCellInfo(range.topLeft()) && this._containsCellInfo(range.bottomRight()));
            };
            cellInfoRange.prototype._extend = function (mode, borders) {
                if(mode === gcui.grid.cellRangeExtendMode.column) {
                    this._topLeft.rowIndex(borders.topLeft().rowIndex());
                    this._bottomRight.rowIndex(borders.bottomRight().rowIndex());
                } else {
                    if(mode === gcui.grid.cellRangeExtendMode.row) {
                        this._topLeft.cellIndex(borders.topLeft().cellIndex());
                        this._bottomRight.cellIndex(borders.bottomRight().cellIndex());
                    }
                }
                return this;
            };
            cellInfoRange.prototype._normalize = function () {
                var x0 = this._topLeft.cellIndex(), y0 = this._topLeft.rowIndex(), x1 = this._bottomRight.cellIndex(), y1 = this._bottomRight.rowIndex();
                this._topLeft.cellIndex(Math.min(x0, x1));
                this._topLeft.rowIndex(Math.min(y0, y1));
                this._bottomRight.cellIndex(Math.max(x0, x1));
                this._bottomRight.rowIndex(Math.max(y0, y1));
            };
            cellInfoRange.prototype._height = function () {
                return this._bottomRight.rowIndex() - this._topLeft.rowIndex();
            };
            cellInfoRange.prototype._width = function () {
                return this._bottomRight.cellIndex() - this._topLeft.cellIndex();
            };
            return cellInfoRange;
        })();
        grid.cellInfoRange = cellInfoRange;        
        $.extend($.gcui.gcuigrid, {
            cellInfo: gcui.grid.cellInfo,
            cellInfoRange: gcui.grid.cellInfoRange
        });
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var baseView = (function () {
            function baseView(gcuigrid, renderBounds) {
                this._rowHeaderSize = 22;
                this._sizesAdjCache = {
                    th: 0,
                    col: 0,
                    subTable: 0
                };
                if(!gcuigrid) {
                    throw "'gcuigrid' must be specified";
                }
                this._grid = gcuigrid;
                this._bounds = renderBounds;
                this._grid.element.addClass("gcui-gcuigrid-table");
            }
            baseView.prototype.dispose = function () {
                this.toggleDOMSelection(true);
                this._grid.element.removeClass("gcui-gcuigrid-table");
            };
            baseView.prototype.ensureDisabledState = function () {
                var disabledClass = "gcui-gcuigrid" + "-disabled " + this._grid.options.uiCSS.stateDisabled, disabled = this._grid.options.disabled, self = this;
                $.each(this.subTables(), function (key, table) {
                    if(table) {
                        var $table = $(table.element());
                        if(disabled) {
                            $table.addClass(disabledClass);
                            self._grid._setAttr($table, "aria-disabled", true);
                        } else {
                            $table.removeClass(disabledClass);
                            self._grid._setAttr($table, "aria-disabled", false);
                        }
                    }
                });
            };
            baseView.prototype.ensureWidth = function (index, value, oldValue) {
                this._setColumnWidth(index, value);
            };
            baseView.prototype.ensureHeight = function (rowIndex) {
            };
            baseView.prototype.getScrollValue = function () {
                return null;
            };
            baseView.prototype.getVisibleAreaBounds = function () {
                throw "not implemented";
            };
            baseView.prototype.getFixedAreaVisibleBounds = function () {
                throw "not implemented";
            };
            baseView.prototype.render = function () {
                this._ensureRenderBounds();
                this._preRender();
                var display = this._grid.outerDiv.css("display");
                this._grid.outerDiv.css("display", "none");
                this._renderContent();
                this._grid.outerDiv.css("display", display);
                this._postRender();
            };
            baseView.prototype.toggleDOMSelection = function (enable) {
                $.each(this.subTables(), function (index, htmlTableAccessor) {
                    (new gcui.grid.domSelection(htmlTableAccessor.element())).toggleSelection(enable);
                });
                (new gcui.grid.domSelection(this._grid.outerDiv)).toggleSelection(enable);
            };
            baseView.prototype.updateSplits = function (scrollValue) {
                throw "not implemented";
            };
            baseView.prototype.bodyRows = function () {
                if(!this._bodyRowsAccessor) {
                    this._bodyRowsAccessor = new gcui.grid.rowAccessor(this, gcui.grid.rowScope.body, 0, 0);
                }
                return this._bodyRowsAccessor;
            };
            baseView.prototype.filterRow = function () {
                if(this._grid.options.showFilter) {
                    var accessor = new gcui.grid.rowAccessor(this, gcui.grid.rowScope.head, 0, 0);
                    return accessor.item(accessor.length() - 1);
                }
                return null;
            };
            baseView.prototype.headerRows = function () {
                var bottomOffset;
                if(!this._headerRowsAccessor) {
                    bottomOffset = this._grid.options.showFilter ? 1 : 0;
                    this._headerRowsAccessor = new gcui.grid.rowAccessor(this, gcui.grid.rowScope.head, 0, bottomOffset);
                }
                return this._headerRowsAccessor;
            };
            baseView.prototype.rows = function () {
                if(!this._rowsAccessor) {
                    this._rowsAccessor = new gcui.grid.rowAccessor(this, gcui.grid.rowScope.table, 0, 0);
                }
                return this._rowsAccessor;
            };
            baseView.prototype.focusableElement = function () {
                throw "not implemented";
            };
            baseView.prototype.forEachColumnCell = function (columnIndex, callback, param) {
                throw "not implemented";
            };
            baseView.prototype.forEachRowCell = function (rowIndex, callback, param) {
                throw "not implemented";
            };
            baseView.prototype.getAbsoluteCellInfo = function (domCell) {
                throw "not implemented";
            };
            baseView.prototype.getAbsoluteRowIndex = function (domRow) {
                throw "not implemented";
            };
            baseView.prototype.getCell = function (absColIdx, absRowIdx) {
                throw "not implemented";
            };
            baseView.prototype.getColumnIndex = function (domCell) {
                throw "not implemented";
            };
            baseView.prototype.getHeaderCell = function (absColIdx) {
                throw "not implemented";
            };
            baseView.prototype.getJoinedCols = function (columnIndex) {
                throw "not implemented";
            };
            baseView.prototype.getJoinedRows = function (rowIndex, rowScope) {
                throw "not implemented";
            };
            baseView.prototype.getJoinedTables = function (byColumn, index) {
                throw "not implemented";
            };
            baseView.prototype.subTables = function () {
                throw "not implemented";
            };
            baseView.prototype._getMappedScrollMode = function () {
                var scrollMode = this._grid.options.scrollMode, vScrollBarVisibility = "auto", hScrollBarVisibility = "auto";
                switch(scrollMode) {
                    case "horizontal":
                        vScrollBarVisibility = "hidden";
                        hScrollBarVisibility = "visible";
                        break;
                    case "vertical":
                        vScrollBarVisibility = "visible";
                        hScrollBarVisibility = "hidden";
                        break;
                    case "both":
                        vScrollBarVisibility = "visible";
                        hScrollBarVisibility = "visible";
                        break;
                }
                return {
                    vScrollBarVisibility: vScrollBarVisibility,
                    hScrollBarVisibility: hScrollBarVisibility
                };
            };
            baseView.prototype._postRender = function () {
                this.ensureDisabledState();
                this._sizesAdjCache.col = 0;
                this._sizesAdjCache.th = 0;
                this._sizesAdjCache.subTable = 0;
                var leaves = this._grid._field("visibleLeaves");
                if(leaves.length > 0) {
                    var th = this.getHeaderCell(0), cols = this.getJoinedCols(0);
                    if(th) {
                        this._sizesAdjCache.th = $(th).leftBorderWidth() + $(th).rightBorderWidth();
                    }
                    if(cols && cols.length) {
                        this._sizesAdjCache.col = $(cols[0]).leftBorderWidth() + $(cols[0]).rightBorderWidth();
                    }
                }
                var subTable = this.subTables()[0];
                this._sizesAdjCache.subTable = $(subTable.element()).leftBorderWidth() + $(subTable.element()).rightBorderWidth();
            };
            baseView.prototype._preRender = function () {
                throw "not implemented";
            };
            baseView.prototype._ensureRenderBounds = function () {
                var dataRange = this._grid._getDataCellsRange();
                this._bounds.start = 0;
                this._bounds.end = dataRange.bottomRight().rowIndex();
            };
            baseView.prototype._renderContent = function () {
                this._renderCOLS();
                this._renderHeader();
                if(this._grid.options.showFilter) {
                    this._renderFilter();
                }
                this._renderBody();
                if(this._grid.options.showFooter) {
                    this._renderFooter();
                }
            };
            baseView.prototype._renderCOLS = function () {
                var visibleLeaves = this._grid._field("visibleLeaves"), leaf, domCol, i, len;
                for(i = 0 , len = visibleLeaves.length; i < len; i++) {
                    leaf = visibleLeaves[i];
                    domCol = this._createCol(leaf, i);
                    this._appendCol(domCol, leaf, i);
                }
            };
            baseView.prototype._renderHeader = function () {
                var $rt = gcui.grid.rowType, cht = this._grid._columnsHeadersTable(), i, height, rowInfo;
                if(cht && (height = cht.length)) {
                    for(i = 0; i < height; i++) {
                        rowInfo = this._insertEmptyRow($rt.header, i, -1, -1, -1);
                        this._renderRow(rowInfo, null, cht[i]);
                    }
                }
            };
            baseView.prototype._renderFilter = function () {
                var rowInfo = this._insertEmptyRow(gcui.grid.rowType.filter, -1, -1, -1, -1);
                this._renderRow(rowInfo, this._grid._field("visibleLeaves"), null);
            };
            baseView.prototype._renderBody = function () {
                var $rt = gcui.grid.rowType, visibleLeaves = this._grid._field("visibleLeaves"), sketch = this._grid.sketchTable, dataRowIndex = -1, virtualDataItemIndexBase = 0, cnt = 0, i, rowInfo, sketchRow, isDataRow, dataOffset = this._grid._dataOffset;
                if(this._bounds.start >= 0) {
                    for(i = this._bounds.start; i <= this._bounds.end; i++) {
                        sketchRow = sketch[i - dataOffset];
                        isDataRow = (sketchRow.rowType & $rt.data) !== 0;
                        rowInfo = this._insertEmptyRow(sketchRow.rowType, cnt++, isDataRow ? ++dataRowIndex : -1, isDataRow ? sketchRow.originalRowIndex : -1, isDataRow ? virtualDataItemIndexBase + sketchRow.originalRowIndex : -1);
                        this._renderRow(rowInfo, visibleLeaves, sketchRow);
                    }
                }
            };
            baseView.prototype._renderFooter = function () {
                var rowInfo = this._insertEmptyRow(gcui.grid.rowType.footer, -1, -1, -1, -1);
                this._renderRow(rowInfo, this._grid._field("visibleLeaves"), null);
            };
            baseView.prototype._insertEmptyRow = function (rowType, sectionRowIndex, dataRowIndex, dataItemIndex, virtualDataItemIndex) {
                var domRow = this._grid._onViewInsertEmptyRow.apply(this._grid, arguments), domRowArr = this._insertRow(rowType, sectionRowIndex, domRow);
                return this._createRowInfo(domRowArr, rowType, gcui.grid.renderState.rendering, sectionRowIndex, dataRowIndex, dataItemIndex, virtualDataItemIndex);
            };
            baseView.prototype._createEmptyCell = function (rowInfo, dataCellIndex, column) {
                var rt = gcui.grid.rowType, domCell = this._grid._onViewCreateEmptyCell.apply(this._grid, arguments);
                return this._createCell(rowInfo.type, domCell);
            };
            baseView.prototype._insertRow = function (rowType, sectionRowIndex, domRow) {
                throw "not implemented";
            };
            baseView.prototype._createCell = function (rowType, domCell) {
                var rt = gcui.grid.rowType, innerContainer;
                if(!domCell) {
                    if(rowType === rt.header) {
                        domCell = document.createElement("th");
                    } else {
                        domCell = document.createElement("td");
                    }
                }
                if(rowType !== rt.filter) {
                    innerContainer = document.createElement("div");
                    innerContainer.className = "gcui-gcuigrid-innercell";
                    if(domCell.firstChild) {
                        while(domCell.firstChild) {
                            innerContainer.appendChild(domCell.firstChild);
                        }
                    }
                    domCell.appendChild(innerContainer);
                }
                return $(domCell);
            };
            baseView.prototype._appendCell = function (rowInfo, cellIndex, $cell) {
                throw "not implemented";
            };
            baseView.prototype._createCol = function (column, visibleIdx) {
                throw "not implemented";
            };
            baseView.prototype._appendCol = function (domCol, column, visibleIdx) {
                throw "not implemented";
            };
            baseView.prototype._renderRow = function (rowInfo, visibleLeaves, item) {
                var $rt = gcui.grid.rowType, rowAttr, rowStyle;
                switch(rowInfo.type) {
                    case $rt.filter:
                        this._renderFilterRow(rowInfo, visibleLeaves);
                        break;
                    case $rt.footer:
                        this._renderFooterRow(rowInfo, visibleLeaves);
                        break;
                    case $rt.header:
                        this._renderHeaderRow(rowInfo, item);
                        break;
                    case $rt.data:
                    case $rt.data | $rt.dataAlt:
                        this._renderDataRow(rowInfo, visibleLeaves, item);
                        rowAttr = item.__attr;
                        rowStyle = item.__style;
                        break;
                    case $rt.emptyDataRow:
                    case $rt.groupHeader:
                    case $rt.groupFooter:
                        this._renderSpannedRow(rowInfo, visibleLeaves, item);
                        rowAttr = item.__attr;
                        rowStyle = item.__style;
                        break;
                    default:
                        throw "unknown rowType";
                }
                this._rowRendered(rowInfo, rowAttr, rowStyle);
            };
            baseView.prototype._renderCell = function (rowInfo, cellIndex, value, useHtml, leaf, attr, style) {
                var $cell = this._createEmptyCell(rowInfo, leaf.dataIndex, leaf);
                var $container = (rowInfo.type === gcui.grid.rowType.filter) ? $cell : $($cell[0].firstChild);
                this._appendCell(rowInfo, cellIndex, $cell);
                if(useHtml) {
                    $container.html(value);
                } else {
                    this._grid.cellFormatter.format($container, leaf, value, rowInfo);
                }
                this._cellRendered(rowInfo, $cell, cellIndex, leaf, attr, style);
            };
            baseView.prototype._renderDataRow = function (rowInfo, visibleLeaves, sketchRow) {
                var i, len, leaf, dataIndex, cellValue, cellAttr, cellStyle, useHtml = false;
                for(i = 0 , len = visibleLeaves.length; i < len; i++) {
                    leaf = visibleLeaves[i];
                    dataIndex = leaf.dataIndex;
                    cellValue = null;
                    if(dataIndex >= 0 && (!sketchRow[dataIndex] || (sketchRow[dataIndex].visible === false))) {
                        continue;
                    }
                    cellValue = (dataIndex >= 0) ? this._grid.toStr(leaf, sketchRow[dataIndex].value) : null;
                    cellAttr = (dataIndex >= 0) ? sketchRow[dataIndex].__attr : null;
                    cellStyle = (dataIndex >= 0) ? sketchRow[dataIndex].__style : null;
                    this._renderCell(rowInfo, i, cellValue, useHtml, leaf, cellAttr, cellStyle);
                }
            };
            baseView.prototype._renderFilterRow = function (rowInfo, visibleLeaves) {
                var i, len, leaf;
                for(i = 0 , len = visibleLeaves.length; i < len; i++) {
                    leaf = visibleLeaves[i];
                    this._renderCell(rowInfo, i, gcui.grid.filterHelper.getSingleValue(leaf.filterValue), false, leaf);
                }
            };
            baseView.prototype._renderFooterRow = function (rowInfo, visibleLeaves) {
                var i, len;
                for(i = 0 , len = visibleLeaves.length; i < len; i++) {
                    this._renderCell(rowInfo, i, "", false, visibleLeaves[i]);
                }
            };
            baseView.prototype._renderHeaderRow = function (rowInfo, item) {
                var i, len, thX = 0, headerInfo;
                for(i = 0 , len = item.length; i < len; i++) {
                    headerInfo = item[i];
                    if(headerInfo.column && headerInfo.column.parentVis) {
                        headerInfo.column.thX = thX++;
                        headerInfo.column.thY = rowInfo.sectionRowIndex;
                        this._renderCell(rowInfo, i, headerInfo.column.headerText, false, headerInfo.column, {
                            colSpan: headerInfo.colSpan,
                            rowSpan: headerInfo.rowSpan
                        });
                    }
                }
            };
            baseView.prototype._renderSpannedRow = function (rowInfo, visibleLeaves, sketchRow) {
                var i, leaf, len = Math.min(visibleLeaves.length, sketchRow.length);
                for(i = 0; i < len; i++) {
                    this._renderCell(rowInfo, i, sketchRow[i].html, true, visibleLeaves[i], sketchRow[i].__attr, sketchRow[i].__style);
                }
            };
            baseView.prototype._cellRendered = function (rowInfo, $cell, cellIndex, leaf, attr, style) {
                this._grid.cellStyleFormatter.format($cell, cellIndex, leaf, rowInfo, gcui.grid.renderState.rendering, attr, style);
                this._changeCellRenderState($cell, gcui.grid.renderState.rendering, false);
                this._grid._onViewCellRendered(rowInfo, $cell, cellIndex, leaf);
            };
            baseView.prototype._rowRendered = function (rowInfo, rowAttr, rowStyle) {
                this._grid.rowStyleFormatter.format(rowInfo, rowAttr, rowStyle);
                rowInfo.state &= ~gcui.grid.renderState.rendering;
                this._setRowInfo(rowInfo.$rows, rowInfo);
                this._grid._onViewRowRendered(rowInfo);
            };
            baseView.prototype._isBodyRow = function (rowInfo) {
                var $rt = gcui.grid.rowType, type = rowInfo.type;
                return ((type & $rt.data) || (type === $rt.groupHeader) || (type === $rt.groupFooter) || (type === $rt.emptyDataRow));
            };
            baseView.prototype._changeRowRenderState = function (rowInfo, state, combine) {
                if(combine) {
                    rowInfo.state |= state;
                } else {
                    rowInfo.state &= ~state;
                }
                this._setRowInfo(rowInfo.$rows, rowInfo);
            };
            baseView.prototype._changeCellRenderState = function ($obj, state, combine) {
                var $dp = gcui.grid.dataPrefix, prefix = this._grid._data$prefix, prevState = $dp($obj, prefix, "renderState");
                if(combine) {
                    state = prevState | state;
                    $dp($obj, prefix, "renderState", state);
                } else {
                    state = prevState & ~state;
                    $dp($obj, prefix, "renderState", state);
                }
                return state;
            };
            baseView.prototype._adjustWidthArray = function (maxWidthArray, minWidthArray, expectedWidth, ensureColumnsPxWidth) {
                var maxWidth = this._sumWidthArray(maxWidthArray), minWidth = this._sumWidthArray(minWidthArray), widthArray = [], adjustWidth, expandCount = 0, expandWidth, remainingWidth, bFirst = true;
                if(maxWidth <= expectedWidth) {
                    $.extend(true, widthArray, maxWidthArray);
                    if(maxWidth === expectedWidth || ensureColumnsPxWidth) {
                        return widthArray;
                    } else {
                        adjustWidth = expectedWidth - maxWidth;
                    }
                } else {
                    $.extend(true, widthArray, minWidthArray);
                    if(minWidth >= expectedWidth) {
                        return widthArray;
                    } else {
                        adjustWidth = expectedWidth - minWidth;
                    }
                }
                $.each(widthArray, function (index, colWidth) {
                    if(!colWidth.real) {
                        expandCount++;
                    }
                });
                if(expandCount !== 0) {
                    expandWidth = Math.floor(adjustWidth / expandCount);
                    remainingWidth = adjustWidth - expandWidth * expandCount;
                    $.each(widthArray, function (index, colWidth) {
                        if(!colWidth.real) {
                            colWidth.width += expandWidth;
                            if(bFirst) {
                                colWidth.width += remainingWidth;
                                bFirst = false;
                            }
                        }
                    });
                }
                return widthArray;
            };
            baseView.prototype._getColumnWidth = function (index) {
                var result, leaf, joinedTables, relIdx, i, table, rows, cell, row, j, len;
                leaf = this._grid._field("visibleLeaves")[index];
                if(leaf._realWidth !== undefined) {
                    result = {
                        width: leaf._realWidth,
                        real: true
                    };
                } else if(leaf.isRowHeader) {
                    result = {
                        width: this._rowHeaderSize,
                        real: true
                    };
                } else {
                    var maxW = 0;
                    joinedTables = this.getJoinedTables(true, index);
                    relIdx = joinedTables[2];
                    for(i = 0; i < 2; i++) {
                        table = joinedTables[i];
                        if(table !== null) {
                            rows = table.element().rows;
                            if(len = rows.length) {
                                for(j = len - 1 , row = null; j >= 0; j--) {
                                    if(rows[j].cells.length === table.width()) {
                                        row = rows[j];
                                        break;
                                    }
                                }
                                if(row) {
                                    cell = row.cells[relIdx];
                                    maxW = Math.max(maxW, $(cell).outerWidth());
                                }
                            }
                        }
                    }
                    result = {
                        width: maxW,
                        real: false
                    };
                }
                return result;
            };
            baseView.prototype._setColumnWidth = function (index, px) {
                var th = this.getHeaderCell(index), cols = this.getJoinedCols(index), value;
                if(px) {
                    var self = this;
                    if(th) {
                        value = px - this._sizesAdjCache.th;
                        if(value < 0) {
                            value = 0;
                        }
                        th.style.width = value + "px";
                    }
                    $.each(cols, function (i, col) {
                        if(col) {
                            value = px - self._sizesAdjCache.col;
                            if(value < 0) {
                                value = 0;
                            }
                            col.style.width = value + "px";
                        }
                    });
                }
            };
            baseView.prototype._setTableWidth = function (subTables, expectedWidth, expandColumnWidth, expandIndex) {
                var after, diff, self = this;
                $.each(subTables, function (index, table) {
                    table[0].style.tableLayout = "fixed";
                    table[0].style.width = (expectedWidth - self._sizesAdjCache.subTable) + "px";
                });
                after = subTables[0].outerWidth();
                diff = after - expectedWidth;
                if(diff !== 0) {
                    this._setColumnWidth(expandIndex, expandColumnWidth - diff);
                }
            };
            baseView.prototype._sumWidthArray = function (widthArray, startIndex, endIndex) {
                var result = 0;
                $.each(widthArray, function (index, colWidth) {
                    if(startIndex !== undefined && endIndex !== undefined && (index < startIndex || index > endIndex)) {
                        return true;
                    }
                    result += colWidth.width;
                });
                return result;
            };
            baseView.prototype._clearBody = function () {
                $.each(this.subTables(), function (key, table) {
                    table.clearSection(2);
                });
            };
            baseView.prototype._rebuildOffsets = function () {
                $.each(this.subTables(), function (key, table) {
                    table.rebuildOffsets();
                });
            };
            baseView.prototype._removeBodyRow = function (sectionRowIndex, affectMetadata) {
                var $rt = gcui.grid.rowType, rows = this._grid._rows(), i, len, rowInfo, ex, cmp, absRowIdx, joinedTables;
                if((sectionRowIndex >= 0) && (sectionRowIndex < (len = rows.length()))) {
                    if(affectMetadata) {
                        for(i = 0; i < len; i++) {
                            rowInfo = this._getRowInfo(rows.item(i));
                            cmp = rowInfo.sectionRowIndex - sectionRowIndex;
                            if(rowInfo.sectionRowIndex > sectionRowIndex) {
                                rowInfo.sectionRowIndex--;
                                if(rowInfo.type & $rt.data) {
                                    if(rowInfo.type & $rt.dataAlt) {
                                        rowInfo.type &= ~$rt.dataAlt;
                                    } else {
                                        rowInfo.type |= $rt.dataAlt;
                                    }
                                    rowInfo.dataItemIndex--;
                                    rowInfo.dataRowIndex--;
                                    rowInfo.virtualDataItemIndex--;
                                }
                                this._setRowInfo(rowInfo.$rows, rowInfo);
                            }
                        }
                    }
                    rowInfo = this._getRowInfo(rows.item(sectionRowIndex), false);
                    absRowIdx = this.getAbsoluteRowIndex(rowInfo.$rows[0]);
                    rowInfo.$rows.remove();
                    joinedTables = this.getJoinedTables(false, absRowIdx);
                    if(joinedTables[0]) {
                        joinedTables[0].removeOffset(joinedTables[2]);
                    }
                    if(joinedTables[1]) {
                        joinedTables[1].removeOffset(joinedTables[2]);
                    }
                }
            };
            baseView.prototype._insertBodyRow = function (sketchRow, sectionRowIndex, dataItemIndex, virtualDataItemIndex) {
                var visibleLeaves = this._grid._field("visibleLeaves"), $rt = gcui.grid.rowType, view = this._grid._view(), rows = this._grid._rows(), len = rows.length(), isDataRow = ((sketchRow.rowType & $rt.data) !== 0), rowInfo, absRowIdx, joinedTables;
                if(sectionRowIndex < 0 || sectionRowIndex >= len || (!sectionRowIndex && sectionRowIndex !== 0)) {
                    sectionRowIndex = len;
                }
                rowInfo = this._insertEmptyRow(sketchRow.rowType, sectionRowIndex, -1, dataItemIndex, virtualDataItemIndex);
                this._renderRow(rowInfo, visibleLeaves, sketchRow);
                absRowIdx = this.getAbsoluteRowIndex(rowInfo.$rows[0]);
                joinedTables = this.getJoinedTables(false, absRowIdx);
                if(joinedTables[0]) {
                    joinedTables[0].insertOffset(absRowIdx);
                }
                if(joinedTables[1]) {
                    joinedTables[1].insertOffset(absRowIdx);
                }
                return rowInfo;
            };
            baseView.prototype._findRowInfo = function (callback) {
                var rowsAccessor = this.bodyRows(), i = 0, len = rowsAccessor.length(), rowInfo;
                if($.isFunction(callback)) {
                    for(i = 0; i < len; i++) {
                        rowInfo = this._getRowInfo(rowsAccessor.item(i));
                        if(callback(rowInfo) === true) {
                            return rowInfo;
                        }
                    }
                }
                return null;
            };
            baseView.prototype._setRowInfo = function (obj, rowInfo) {
                var hasRows = "$rows" in rowInfo, hasData = "data" in rowInfo, tmpRows, tmpData;
                if(hasRows) {
                    tmpRows = rowInfo.$rows;
                    delete rowInfo.$rows;
                }
                if(hasData) {
                    tmpData = rowInfo.data;
                    delete rowInfo.data;
                }
                gcui.grid.dataPrefix(obj, this._grid._data$prefix, "rowInfo", rowInfo);
                if(hasRows) {
                    rowInfo.$rows = tmpRows;
                }
                if(hasData) {
                    rowInfo.data = tmpData;
                }
            };
            baseView.prototype._getRowInfo = function (rowObj, retrieveDataItem) {
                if (typeof retrieveDataItem === "undefined") { retrieveDataItem = true; }
                var gcuigrid = this._grid, $rows = rowObj[1] ? $(rowObj) : $(rowObj[0]), rowInfo = gcui.grid.dataPrefix($rows, gcuigrid._data$prefix, "rowInfo"), tmp;
                rowInfo.$rows = $rows;
                if(retrieveDataItem && (rowInfo.dataItemIndex >= 0) && (rowInfo.type & gcui.grid.rowType.data)) {
                    try  {
                        rowInfo.data = gcuigrid._getDataItem(rowInfo.dataItemIndex);
                    } catch (ex) {
                        rowInfo.data = null;
                    }
                }
                return rowInfo;
            };
            baseView.prototype._createRowInfo = function (row, type, state, sectionRowIndex, dataRowIndex, dataItemIndex, virtualDataItemIndex) {
                var tmp, rowInfo = {
                    type: type,
                    state: state,
                    sectionRowIndex: sectionRowIndex,
                    dataRowIndex: dataRowIndex,
                    dataItemIndex: dataItemIndex,
                    virtualDataItemIndex: virtualDataItemIndex,
                    $rows: $(row)
                };
                if((dataItemIndex >= 0) && (type & gcui.grid.rowType.data)) {
                    rowInfo.data = this._grid._getDataItem(dataItemIndex);
                }
                return rowInfo;
            };
            return baseView;
        })();
        grid.baseView = baseView;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var flatView = (function (_super) {
            __extends(flatView, _super);
            function flatView(gcuigrid, renderBounds) {
                _super.call(this, gcuigrid, renderBounds);
                this._dataTable = null;
                this._contentArea = null;
            }
            flatView.prototype.dispose = function () {
                this._grid.outerDiv.unbind("scroll", this._onScroll);
                _super.prototype.dispose.call(this);
            };
            flatView.prototype.ensureWidth = function (index, value, oldValue) {
                var $table = $(this._dataTable.element()), tableWidth = $table.width() + value - oldValue;
                _super.prototype.ensureWidth.call(this, index, value, oldValue);
                this._setTableWidth([ $table ], tableWidth, value, index);
            };
            flatView.prototype.getVisibleAreaBounds = function () {
                var dataTableBounds = gcui.grid.bounds(this._dataTable.element()), splitSEBounds;
                if(this._grid.options.scrollMode === "none") {
                    return dataTableBounds;
                } else {
                    splitSEBounds = gcui.grid.bounds(this._grid.outerDiv.find(".gcui-gcuigrid-split-area-se:first")[0]);
                    return {
                        top: dataTableBounds.top,
                        left: dataTableBounds.left,
                        width: Math.min(splitSEBounds.width, dataTableBounds.width),
                        height: Math.min(splitSEBounds.height, dataTableBounds.height)
                    };
                }
            };
            flatView.prototype.updateSplits = function (scrollValue) {
                var self = this, gcuigrid = this._grid, o = gcuigrid.options, gridElement = gcuigrid.element, maxWidthArray = [], minWidthArray = [], resultWidthArray = [], visibleLeaves = gcuigrid._field("visibleLeaves"), outerDiv = gcuigrid.outerDiv, headerWidth, expandIndex;
                gridElement.css({
                    "table-layout": "",
                    "width": ""
                });
                $.each(visibleLeaves, function (index, leaf) {
                    var isPercentage, w = leaf.width;
                    if(w || (w === 0)) {
                        isPercentage = ((typeof (w) === "string") && (w.length > 1) && (w[w.length - 1] === "%"));
                        if(isPercentage) {
                            w = outerDiv.width() * parseFloat(w) / 100;
                        } else {
                            w = parseFloat(w);
                        }
                        if(leaf.ensurePxWidth || (leaf.ensurePxWidth === undefined && o.ensureColumnsPxWidth)) {
                            leaf._realWidth = w;
                        }
                        self._setColumnWidth(index, w);
                    }
                });
                $.each(visibleLeaves, function (index, leaf) {
                    maxWidthArray.push(self._getColumnWidth(index));
                });
                gridElement.css("width", "1px");
                $.each(visibleLeaves, function (index, leaf) {
                    minWidthArray.push(self._getColumnWidth(index));
                });
                headerWidth = outerDiv.width();
                resultWidthArray = this._adjustWidthArray(maxWidthArray, minWidthArray, headerWidth, o.ensureColumnsPxWidth);
                $.each(resultWidthArray, function (index, colWidth) {
                    var leaf = visibleLeaves[index];
                    if(leaf._realWidth !== undefined) {
                        delete leaf._realWidth;
                        return;
                    }
                    self._setColumnWidth(index, colWidth.width);
                });
                expandIndex = resultWidthArray.length - 1;
                if(expandIndex !== -1) {
                    this._setTableWidth([
                        gridElement
                    ], this._sumWidthArray(resultWidthArray, 0, expandIndex), resultWidthArray[expandIndex].width, expandIndex);
                }
            };
            flatView.prototype.focusableElement = function () {
                return $(this._dataTable.element());
            };
            flatView.prototype.forEachColumnCell = function (columnIndex, callback, param) {
                return this._dataTable.forEachColumnCell(columnIndex, callback, param);
            };
            flatView.prototype.forEachRowCell = function (rowIndex, callback, param) {
                return this._dataTable.forEachRowCell(rowIndex, callback, param);
            };
            flatView.prototype.getAbsoluteCellInfo = function (domCell) {
                return new gcui.grid.cellInfo(this.getColumnIndex(domCell), (domCell.parentNode).rowIndex, this._grid, true);
            };
            flatView.prototype.getAbsoluteRowIndex = function (domRow) {
                return domRow.rowIndex;
            };
            flatView.prototype.getCell = function (absColIdx, absRowIdx) {
                var cellIdx = this._dataTable.getCellIdx(absColIdx, absRowIdx), rowObj;
                if(cellIdx >= 0) {
                    rowObj = this.getJoinedRows(absRowIdx, 0);
                    if(rowObj[0]) {
                        return rowObj[0].cells[cellIdx];
                    }
                }
                return null;
            };
            flatView.prototype.getColumnIndex = function (domCell) {
                return this._dataTable.getColumnIdx(domCell);
            };
            flatView.prototype.getHeaderCell = function (absColIdx) {
                var leaf = this._grid._field("visibleLeaves")[absColIdx], headerRow;
                if(leaf && (headerRow = this._grid._headerRows())) {
                    return gcui.grid.rowAccessor.getCell(headerRow.item(leaf.thY), leaf.thX);
                }
                return null;
            };
            flatView.prototype.getJoinedCols = function (columnIndex) {
                var $colGroup = $(this._dataTable.element()).find("> colgroup");
                if($colGroup.length) {
                    if(columnIndex < $colGroup[0].childNodes.length) {
                        return [
                            $colGroup[0].childNodes[columnIndex], 
                            null
                        ];
                    }
                }
                return [
                    null, 
                    null
                ];
            };
            flatView.prototype.getJoinedRows = function (rowIndex, rowScope) {
                return [
                    this._dataTable.getSectionRow(rowIndex, rowScope), 
                    null
                ];
            };
            flatView.prototype.getJoinedTables = function (byColumn, index) {
                return [
                    this._dataTable, 
                    null, 
                    index
                ];
            };
            flatView.prototype.subTables = function () {
                return [
                    this._dataTable
                ];
            };
            flatView.prototype._preRender = function () {
                this._dataTable = new gcui.grid.htmlTableAccessor(this._grid.element[0], true, true, true);
            };
            flatView.prototype._postRender = function () {
                this._grid.element.find("> tbody").addClass(this._grid.options.uiCSS.content + " gcui-gcuigrid-data");
                this._dataTable = new gcui.grid.htmlTableAccessor(this._grid.element[0]);
                this._grid._setAttr(this._grid.element, {
                    role: "grid",
                    cellpadding: "0",
                    border: "0",
                    cellspacing: "0"
                });
                this._grid.element.css("border-collapse", "separate");
                this._grid.outerDiv.bind("scroll", {
                    gcuigrid: this._grid
                }, $.proxy(this._onScroll, this));
                _super.prototype._postRender.call(this);
            };
            flatView.prototype._insertRow = function (rowType, sectionRowIndex, domRow) {
                var $rt = gcui.grid.rowType, tableSection;
                switch(rowType) {
                    case $rt.header:
                    case $rt.filter:
                        tableSection = this._dataTable.ensureTHead();
                        break;
                    case $rt.footer:
                        tableSection = this._dataTable.ensureTFoot();
                        break;
                    default:
                        tableSection = this._dataTable.ensureTBody();
                }
                if(domRow) {
                    return [
                        tableSection.appendChild(domRow)
                    ];
                } else {
                    if(sectionRowIndex > tableSection.rows.length) {
                        sectionRowIndex = -1;
                    }
                    return [
                        tableSection.insertRow(sectionRowIndex)
                    ];
                }
            };
            flatView.prototype._rowRendered = function (rowInfo, rowAttr, rowStyle) {
                var domRow = rowInfo.$rows[0];
                if(!domRow.cells.length && this._isBodyRow(rowInfo)) {
                    domRow.parentNode.removeChild(domRow);
                } else {
                    _super.prototype._rowRendered.call(this, rowInfo, rowAttr, rowStyle);
                }
            };
            flatView.prototype._appendCell = function (rowInfo, cellIndex, $cell) {
                rowInfo.$rows[0].appendChild($cell[0]);
            };
            flatView.prototype._createCol = function (column, visibleIdx) {
                return [
                    document.createElement("col")
                ];
            };
            flatView.prototype._appendCol = function (domCol, column, visibleIdx) {
                this._dataTable.appendCol(domCol[0]);
            };
            flatView.prototype._onScroll = function (e) {
                if(e.data.gcuigrid.$superPanelHeader) {
                    e.data.gcuigrid.$superPanelHeader.css("left", e.target.scrollLeft);
                }
                if(e.data.gcuigrid.$bottomPagerDiv) {
                    e.data.gcuigrid.$bottomPagerDiv.css("left", e.target.scrollLeft);
                }
            };
            return flatView;
        })(grid.baseView);
        grid.flatView = flatView;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var fixedView = (function (_super) {
            __extends(fixedView, _super);
            function fixedView(gcuigrid, renderBounds) {
                _super.call(this, gcuigrid, renderBounds);
                this._verScrollBarSize = 18;
                this._viewTables = {};
                this._splitAreas = {};
                this.element = gcuigrid.element;
                this._allowVirtualScrolling = gcuigrid._allowVirtualScrolling() , this._staticDataRowIndex = gcuigrid._getStaticIndex(true);
                this._staticRowIndex = gcuigrid._getRealStaticRowIndex();
                this._staticColumnIndex = gcuigrid._getRealStaticColumnIndex();
                this._staticAllColumnIndex = (this._staticColumnIndex === -1) ? -1 : gcuigrid._field("visibleLeaves")[this._staticColumnIndex].leavesIdx;
                this._mouseWheelHandler = $.proxy(this._onMouseWheel, this);
            }
            fixedView.prototype.dispose = function () {
                _super.prototype.dispose.call(this);
                this._grid.outerDiv.unbind("mousewheel", this._mouseWheelHandler);
            };
            fixedView.prototype.ensureWidth = function (index, value, oldValue) {
                var gcuigrid = this._grid, o = gcuigrid.options, staticColumnIndex = this._staticColumnIndex, bWest = index <= staticColumnIndex, $tableNW = $(this._viewTables.nw.element()), $tableNE = $(this._viewTables.ne.element()), $tableSW = $(this._viewTables.sw.element()), $tableSE = $(this._viewTables.se.element()),
                    tableArray = bWest ? [$tableNW, $tableSW] : [$tableNE, $tableSE],
                    tableWidth = (bWest ? $tableNW.width() : $tableNE.width()) + value - oldValue,
                    frozener = gcuigrid._field("frozener"), scrollValue = this.getScrollValue();

                this._destroySuperPanel();
                _super.prototype.ensureWidth.call(this, index, value, oldValue);
                this._setTableWidth(tableArray, tableWidth, value, index);
                try  {
                    if(staticColumnIndex >= 0) {
                        o.splitDistanceX = $tableNW[0].offsetWidth;
                    } else {
                        o.splitDistanceX = 0;
                    }
                } catch (ex) {
                }
                this._updateSplitAreaBounds(0);
                this._adjustRowsHeights();
                try  {
                    if(this._staticRowIndex >= 0) {
                        o.splitDistanceY = Math.max($tableNW[0].offsetHeight, $tableNE[0].offsetHeight);
                    } else {
                        o.splitDistanceY = 0;
                    }
                } catch (ex) {
                }
                this._updateSplitAreaBounds(1);
                this.refreshPanel(scrollValue);
                frozener.refresh();
            };
            fixedView.prototype.ensureHeight = function (rowIndex) {
                var rowObjsArray, gcuigrid = this._grid, o = gcuigrid.options, $tableNW = $(this._viewTables.nw.element()), $tableNE = $(this._viewTables.ne.element()), $tableSW = $(this._viewTables.sw.element()), $tableSE = $(this._viewTables.se.element()), frozener = gcuigrid._field("frozener"), scrollValue = this.getScrollValue(), maxHeight;
                this._destroySuperPanel();
                if(arguments.length > 0) {
                    rowObjsArray = this.getJoinedRows(rowIndex, 2);
                    this._setRowHeight(rowObjsArray, this._getRowHeight(rowObjsArray));
                }
                $tableSE.css("height", "");
                $tableSW.css("height", "");
                maxHeight = Math.max($tableSE.height(), $tableSW.height());
                $tableSE.height(maxHeight);
                $tableSW.height(maxHeight);
                try  {
                    if(this._staticRowIndex >= 0) {
                        o.splitDistanceY = Math.max($tableNW[0].offsetHeight, $tableNE[0].offsetHeight);
                    } else {
                        o.splitDistanceY = 0;
                    }
                } catch (ex) {
                }
                this._updateSplitAreaBounds(1);
                this.refreshPanel(scrollValue);
                frozener.refresh();
            };
            fixedView.prototype.getScrollValue = function () {
                var superPanelObj = this._getSuperPanel();
                return superPanelObj ? {
                    x: superPanelObj.options.hScroller.scrollValue,
                    y: superPanelObj.options.vScroller.scrollValue
                } : null;
            };
            fixedView.prototype.getVisibleAreaBounds = function () {
                var bounds = gcui.grid.bounds(this._grid.outerDiv.find(".gcui-superpanel-contentwrapper:first"));
                if(!bounds) {
                    bounds = gcui.grid.bounds(this._grid.outerDiv);
                }
                return bounds;
            };
            fixedView.prototype.getFixedAreaVisibleBounds = function () {
                var bounds = this.getVisibleAreaBounds(), neBounds = gcui.grid.bounds(this._splitAreas.ne), nwBounds = gcui.grid.bounds(this._splitAreas.nw), horBounds = null, verBounds = null;
                if(neBounds.height || nwBounds.height) {
                    horBounds = {
                        left: bounds.left,
                        top: bounds.top,
                        width: bounds.width,
                        height: Math.min(neBounds.height || nwBounds.height, bounds.height)
                    };
                }
                if(nwBounds.width) {
                    verBounds = {
                        left: nwBounds.left,
                        top: bounds.top,
                        width: Math.min(nwBounds.width, bounds.width),
                        height: bounds.height
                    };
                }
                return [
                    horBounds, 
                    verBounds
                ];
            };
            fixedView.prototype.refreshPanel = function (scrollValue) {
                var self = this, gcuigrid = this._grid, options = gcuigrid.options, gridWidth = this._getGridWidth(options.scrollMode), panelModes = this._getMappedScrollMode(), needVBar = this._testNeedVBar(gcuigrid.outerDiv, gcuigrid.element, $(this._viewTables.ne.element()), options.scrollMode, gcuigrid._autoHeight), excludeVBarWidth = needVBar && !isTouchEnabled, hScrollValue = scrollValue ? scrollValue.x : null, vScrollValue = scrollValue ? scrollValue.y : null, isTouchEnabled = ($.support.isTouchEnabled && $.support.isTouchEnabled());
                this._scroller.width(gridWidth);
                this._splitAreas.ne.width(gridWidth - options.splitDistanceX - (excludeVBarWidth ? this._verScrollBarSize : 0));
                if(options.staticColumnsAlignment === "right") {
                    this._splitAreas.nw.css({
                        "left": "",
                        "right": excludeVBarWidth ? this._verScrollBarSize : 0
                    });
                    this._splitAreas.sw.css({
                        "left": "",
                        "right": excludeVBarWidth ? this._verScrollBarSize : 0
                    });
                }
                if(!this._scroller.data("gcui-superpanel")) {
                    if(this._allowVirtualScrolling) {
                        this.vsUI = new gcui.grid.uiVirtualScroller(gcuigrid, this._splitAreas.se, options.splitDistanceY);
                    }
                    this._scroller.superpanel({
                        disabled: gcuigrid.options.disabled,
                        scroll: $.proxy(this._onScroll, this),
                        bubbleScrollingEvent: true,
                        customScrolling: this._allowVirtualScrolling,
                        vScroller: {
                            scrollBarVisibility: panelModes.vScrollBarVisibility,
                            "scrollValue": scrollValue ? scrollValue.y : null
                        },
                        hScroller: {
                            scrollBarVisibility: panelModes.hScrollBarVisibility,
                            "scrollValue": scrollValue ? scrollValue.x : null
                        },
                        hScrollerActivating: function (e, data) {
                            var diff;
                            if(gcuigrid._autoHeight) {
                                diff = gcuigrid.element.height() + options.splitDistanceY - data.contentLength;
                                if(diff > 0) {
                                    self._scroller.height(self._scroller.height() + diff);
                                    self._scroller.superpanel("paintPanel");
                                    return false;
                                }
                            }
                            self._splitAreas.sw.height(data.contentLength - options.splitDistanceY);
                        }
                    });
                    if(this._allowVirtualScrolling) {
                        this.vsUI.attach(this._scroller);
                    }
                } else {
                    this._scroller.superpanel("paintPanel");
                }
                this._scroller.find(".gcui-superpanel-hbarcontainer, .gcui-superpanel-vbarcontainer").css("zIndex", 5);
            };
            fixedView.prototype.scrollTo = function (currentCell) {
                if(!currentCell.tableCell()) {
                    return;
                }
                var gcuigrid = this._grid, o = gcuigrid.options, superPanelObj = this._getSuperPanel(), tableCell = currentCell.tableCell(), $tableCell = tableCell.nodeType ? $(tableCell) : tableCell, resultLeft = null, resultTop = null;
                if(superPanelObj && $tableCell.is(":visible")) {
                    var contentElement = (superPanelObj).getContentElement(), wrapperElement = contentElement.parent(), visibleLeft = parseInt((contentElement.css("left") + "").replace("px", ""), 10) * -1, visibleTop = parseInt((contentElement.css("top") + "").replace("px", ""), 10) * -1, visibleWidth = wrapperElement.outerWidth() - o.splitDistanceX, visibleHeight = wrapperElement.outerHeight() - o.splitDistanceY, elementPosition = $tableCell.position(), elementLeft = elementPosition.left, elementTop = elementPosition.top, elementWidth = $tableCell.outerWidth(), elementHeight = $tableCell.outerHeight(), staticRowIndex = gcuigrid._getStaticIndex(true), staticColumnIndex = gcuigrid._getStaticIndex(false), currentRowIndex = currentCell.rowIndex(), currentCellIndex = currentCell.cellIndex();
                    if(currentRowIndex <= staticRowIndex) {
                        if(currentCellIndex <= staticColumnIndex) {
                            resultLeft = 0;
                            resultTop = 0;
                        } else {
                            elementLeft += visibleLeft;
                            if(elementLeft + elementWidth > visibleLeft + visibleWidth) {
                                visibleLeft = resultLeft = elementLeft + elementWidth - visibleWidth;
                            }
                            if(elementLeft < visibleLeft) {
                                resultLeft = elementLeft;
                            }
                            resultTop = 0;
                        }
                    } else {
                        if(currentCellIndex <= staticColumnIndex) {
                            elementTop += this._splitAreas.sw.scrollTop();
                            if(elementTop + elementHeight > visibleTop + visibleHeight) {
                                visibleTop = resultTop = elementTop + elementHeight - visibleHeight;
                            }
                            if(elementTop < visibleTop) {
                                resultTop = elementTop;
                            }
                            resultLeft = 0;
                        } else {
                            elementLeft -= o.splitDistanceX;
                            if(elementTop + elementHeight > visibleTop + visibleHeight) {
                                visibleTop = resultTop = elementTop + elementHeight - visibleHeight;
                            }
                            if(elementLeft + elementWidth > visibleLeft + visibleWidth) {
                                visibleLeft = resultLeft = elementLeft + elementWidth - visibleWidth;
                            }
                            if(elementTop < visibleTop) {
                                resultTop = elementTop;
                            }
                            if(elementLeft < visibleLeft) {
                                resultLeft = elementLeft;
                            }
                        }
                    }
                    if(resultLeft !== null) {
                        (superPanelObj).hScrollTo(resultLeft);
                    }
                    if(resultTop !== null) {
                        if(this._allowVirtualScrolling) {
                        } else {
                            (superPanelObj).vScrollTo(resultTop);
                        }
                    }
                }
            };
            fixedView.prototype.updateSplits = function (scrollValue) {
                var gcuigrid = this._grid, o = gcuigrid.options, headerWidth, self = this, resultWidthArray = [], minWidthArray = [], maxWidthArray = [], staticColumnIndex = this._staticColumnIndex, expandIndex, mode = o.scrollMode, visibleLeaves = gcuigrid._field("visibleLeaves"), $tableSE = $(this._viewTables.se.element()), $tableNE = $(this._viewTables.ne.element()), $tableSW = $(this._viewTables.sw.element()), $tableNW = $(this._viewTables.nw.element()), outerDiv = gcuigrid.outerDiv, tmp, i, hasDataRows;
                this._destroySuperPanel();
                outerDiv.unbind("mousewheel", this._mouseWheelHandler);
                if((tmp = self._viewTables.se.element().tBodies) && (tmp = tmp[0])) {
                    hasDataRows = false;
                    for(i = 0; i < tmp.rows.length; i++) {
                        if(!$(tmp.rows[i]).hasClass("gcui-gcuigrid-groupheaderrow")) {
                            hasDataRows = true;
                            break;
                        }
                    }
                    if(!hasDataRows) {
                        gcuigrid.element.css("width", "100%");
                    }
                }
                $.each([ $tableSE, $tableNE, $tableSW, $tableNW ], function (index, table) {
                    table.css({
                        "table-layout": "",
                        "width": ""
                    });
                });
                $.each(visibleLeaves, function (index, leaf) {
                    var isPercentage, w = leaf.width;
                    if(w || (w === 0)) {
                        isPercentage = ((typeof (w) === "string") && (w.length > 1) && (w[w.length - 1] === "%"));
                        if(isPercentage) {
                            w = outerDiv.width() * parseFloat(w) / 100;
                        } else {
                            w = parseFloat(w);
                        }
                        if(leaf.ensurePxWidth || (leaf.ensurePxWidth === undefined && o.ensureColumnsPxWidth)) {
                            leaf._realWidth = w;
                        }
                        self._setColumnWidth(index, w);
                    }
                });
                $.each(visibleLeaves, function (index, leaf) {
                    maxWidthArray.push(self._getColumnWidth(index));
                });
                $.each([ $tableNW, $tableNE, $tableSW, $tableSE ], function (index, table) {
                    table.css({
                        "width": "1px"
                    });
                });
                $.each(visibleLeaves, function (index, leaf) {
                    minWidthArray.push(self._getColumnWidth(index));
                });
                headerWidth = outerDiv.width();
                resultWidthArray = this._adjustWidthArray(maxWidthArray, minWidthArray, headerWidth, o.ensureColumnsPxWidth);
                $.each(resultWidthArray, function (index, colWidth) {
                    var leaf = visibleLeaves[index];
                    if(leaf._realWidth !== undefined) {
                        delete leaf._realWidth;
                        return;
                    }
                    self._setColumnWidth(index, colWidth.width);
                });
                if(staticColumnIndex >= 0) {
                    expandIndex = staticColumnIndex;
                    this._setTableWidth([
                        $tableNW, 
                        $tableSW
                    ], this._sumWidthArray(resultWidthArray, 0, expandIndex), resultWidthArray[expandIndex].width, expandIndex);
                }
                try  {
                    if(staticColumnIndex >= 0) {
                        o.splitDistanceX = $tableNW[0].offsetWidth;
                    } else {
                        o.splitDistanceX = 0;
                    }
                } catch (ex) {
                }
                this._updateSplitAreaBounds(0);
                if(!o.ensureColumnsPxWidth) {
                    $tableNE.parent().width(headerWidth - o.splitDistanceX);
                }
                expandIndex = resultWidthArray.length - 1;
                if(expandIndex !== -1) {
                    this._setTableWidth([
                        $tableNE, 
                        $tableSE
                    ], this._sumWidthArray(resultWidthArray, staticColumnIndex + 1, expandIndex), resultWidthArray[expandIndex].width, expandIndex);
                }
                this._adjustRowsHeights();
                try  {
                    if(this._staticRowIndex >= 0) {
                        o.splitDistanceY = Math.max($tableNW[0].offsetHeight, $tableNE[0].offsetHeight);
                    } else {
                        o.splitDistanceY = 0;
                    }
                } catch (ex) {
                }
                this._updateSplitAreaBounds(1);
                if(!o.ensureColumnsPxWidth) {
                    if(this._testNeedVBar(gcuigrid.outerDiv, $tableSE, $tableNE, mode, gcuigrid._autoHeight)) {
                        headerWidth -= this._verScrollBarSize;
                        resultWidthArray = this._adjustWidthArray(maxWidthArray, minWidthArray, headerWidth, o.ensureColumnsPxWidth);
                        $.each(resultWidthArray, function (index, colWidth) {
                            if(!colWidth.real) {
                                self._setColumnWidth(index, colWidth.width);
                            }
                        });
                        if(staticColumnIndex >= 0) {
                            expandIndex = staticColumnIndex;
                            this._setTableWidth([
                                $tableNW, 
                                $tableSW
                            ], this._sumWidthArray(resultWidthArray, 0, expandIndex), resultWidthArray[expandIndex].width, expandIndex);
                        }
                        try  {
                            if(staticColumnIndex >= 0) {
                                o.splitDistanceX = $tableNW[0].offsetWidth;
                            } else {
                                o.splitDistanceX = 0;
                            }
                        } catch (ex) {
                        }
                        this._updateSplitAreaBounds(0);
                        $tableNE.parent().width(headerWidth - o.splitDistanceX);
                        expandIndex = resultWidthArray.length - 1;
                        if(expandIndex !== -1) {
                            this._setTableWidth([
                                $tableNE, 
                                $tableSE
                            ], this._sumWidthArray(resultWidthArray, staticColumnIndex + 1, expandIndex), resultWidthArray[expandIndex].width, expandIndex);
                        }
                        this._adjustRowsHeights();
                        try  {
                            if(this._staticRowIndex >= 0) {
                                o.splitDistanceY = Math.max($tableNW[0].offsetHeight, $tableNE[0].offsetHeight);
                            } else {
                                o.splitDistanceY = 0;
                            }
                        } catch (ex) {
                        }
                        this._updateSplitAreaBounds(1);
                    }
                }
                this.refreshPanel(scrollValue);
                outerDiv.bind("mousewheel", $.proxy(this._mouseWheelHandler, this));
            };
            fixedView.prototype._clearBody = function () {
                _super.prototype._clearBody.call(this);
            };
            fixedView.prototype.bodyRows = function () {
                var accessor = _super.prototype.bodyRows.call(this);
                return accessor;
            };
            fixedView.prototype.focusableElement = function () {
                return this._grid.outerDiv;
            };
            fixedView.prototype.forEachColumnCell = function (columnIndex, callback, param) {
                var joinedTables = this.getJoinedTables(true, columnIndex), relIdx, callbackRes;
                if(joinedTables[0] !== null) {
                    relIdx = joinedTables[2];
                    callbackRes = joinedTables[0].forEachColumnCell(relIdx, callback, param);
                    if(callbackRes !== true) {
                        return callbackRes;
                    }
                    if(joinedTables[1] !== null) {
                        callbackRes = joinedTables[1].forEachColumnCell(relIdx, callback, param);
                        if(callbackRes !== true) {
                            return callbackRes;
                        }
                    }
                }
                return true;
            };
            fixedView.prototype.forEachRowCell = function (rowIndex, callback, param) {
                var joinedTables = this.getJoinedTables(false, rowIndex), table0 = joinedTables[0], table1 = joinedTables[1], relIdx, callbackResult;
                if(table0 !== null) {
                    relIdx = joinedTables[2];
                    if(relIdx < table0.element().rows.length) {
                        callbackResult = table0.forEachRowCell(relIdx, callback, param);
                        if(callbackResult !== true) {
                            return callbackResult;
                        }
                    }
                    if((table1 !== null) && (relIdx < table1.element().rows.length)) {
                        callbackResult = table1.forEachRowCell(relIdx, callback, param);
                        if(callbackResult !== true) {
                            return callbackResult;
                        }
                    }
                }
                HTMLTableCellElement;
                return true;
            };
            fixedView.prototype.getAbsoluteCellInfo = function (domCell) {
                return new gcui.grid.cellInfo(this.getColumnIndex(domCell), this.getAbsoluteRowIndex(domCell.parentNode), this._grid, true);
            };
            fixedView.prototype.getAbsoluteRowIndex = function (domRow) {
                var index = domRow.rowIndex, table = domRow.parentNode;
                while(table.tagName.toLowerCase() !== "table") {
                    table = table.parentNode;
                }
                return (table === this._viewTables.nw.element() || table === this._viewTables.ne.element()) ? index : index + this._staticRowIndex + 1;
            };
            fixedView.prototype.getCell = function (absColIdx, absRowIdx) {
                var joinedTablesRow = this.getJoinedTables(false, absRowIdx), joinedTablesCol, relRowIdx, relColIdx, table, cellIdx;
                if(joinedTablesRow[0] !== null) {
                    joinedTablesCol = this.getJoinedTables(true, absColIdx);
                    if(joinedTablesCol[0] !== null) {
                        relRowIdx = joinedTablesRow[2];
                        relColIdx = joinedTablesCol[2];
                        table = null;
                        if(joinedTablesRow[1] !== null) {
                            table = (absColIdx === relColIdx) ? joinedTablesRow[0] : joinedTablesRow[1];
                        } else {
                            table = joinedTablesRow[0];
                        }
                        cellIdx = table.getCellIdx(relColIdx, relRowIdx);
                        if(cellIdx >= 0) {
                            return table.element().rows[relRowIdx].cells[cellIdx];
                        }
                    }
                }
                return null;
            };
            fixedView.prototype.getColumnIndex = function (domCell) {
                var owner = null, htmlTable = null, flag = false, colIdx;
                for(owner = domCell.parentNode; owner.tagName.toLowerCase() !== "table"; owner = owner.parentNode) {
                }
                if(owner !== null) {
                    if(owner === this._viewTables.nw.element()) {
                        htmlTable = this._viewTables.nw;
                    } else {
                        if(owner === this._viewTables.ne.element()) {
                            htmlTable = this._viewTables.ne;
                            flag = true;
                        } else {
                            if(owner === this._viewTables.sw.element()) {
                                htmlTable = this._viewTables.sw;
                            } else {
                                if(owner === this._viewTables.se.element()) {
                                    htmlTable = this._viewTables.se;
                                    flag = true;
                                }
                            }
                        }
                    }
                    if(htmlTable !== null) {
                        colIdx = htmlTable.getColumnIdx(domCell);
                        if(flag) {
                            colIdx += this._staticColumnIndex + 1;
                        }
                        return colIdx;
                    }
                }
                return -1;
            };
            fixedView.prototype.getHeaderCell = function (absColIdx) {
                var leaf = this._grid._field("visibleLeaves")[absColIdx], headerRow;
                if(leaf && (headerRow = this._grid._headerRows())) {
                    return gcui.grid.rowAccessor.getCell(headerRow.item(leaf.thY), leaf.thX);
                }
                return null;
            };
            fixedView.prototype.getJoinedCols = function (columnIndex) {
                var result = [], joinedTables = this.getJoinedTables(true, columnIndex), relIndex = joinedTables[2];
                joinedTables.splice(joinedTables.length - 1, 1);
                $.each(joinedTables, function (index, table) {
                    result.push(table ? $(table.element()).find("col")[relIndex] : null);
                });
                return result;
            };
            fixedView.prototype.getJoinedRows = function (rowIndex, rowScope) {
                var row0 = null, row1 = null, table0 = null, table1 = null, fixedRowIdx = this._staticRowIndex, fixedColIdx = this._staticColumnIndex, lastColIdx = this._grid._field("visibleLeaves").length - 1, lastRowIdx = this._rowsCountRaw() - 1, allRowsFixed = (fixedRowIdx === lastRowIdx), allsRowUnfixed = (fixedRowIdx < 0), rowsFixedSlice = !allRowsFixed && !allsRowUnfixed, sectionLength = 0;
                if(allRowsFixed || rowsFixedSlice) {
                    if(fixedColIdx >= 0 && fixedColIdx < lastColIdx) {
                        table0 = this._viewTables.nw;
                        table1 = this._viewTables.ne;
                    } else {
                        table0 = (fixedColIdx < 0) ? this._viewTables.ne : this._viewTables.nw;
                    }
                    sectionLength = table0.getSectionLength(rowScope);
                    if(rowIndex < sectionLength) {
                        row0 = table0.getSectionRow(rowIndex, rowScope);
                        if(table1 !== null) {
                            row1 = table1.getSectionRow(rowIndex, rowScope);
                        }
                    }
                }
                if(allsRowUnfixed || (rowsFixedSlice && (row0 === null))) {
                    if(!allsRowUnfixed) {
                        rowIndex -= sectionLength;
                    }
                    if(fixedColIdx >= 0 && fixedColIdx < lastColIdx) {
                        table0 = this._viewTables.sw;
                        table1 = this._viewTables.se;
                    } else {
                        table0 = (fixedColIdx < 0) ? this._viewTables.se : this._viewTables.sw;
                    }
                    row0 = table0.getSectionRow(rowIndex, rowScope);
                    if(table1 !== null) {
                        row1 = table1.getSectionRow(rowIndex, rowScope);
                    }
                }
                return (row0 === null && row1 === null) ? null : [
                    row0, 
                    row1
                ];
            };
            fixedView.prototype.getJoinedTables = function (byColumn, index) {
                var t0 = null, t1 = null, idx = index, gcuigrid = this._grid, fixedRowIdx = this._staticRowIndex, fixedColIdx = this._staticColumnIndex;
                if(byColumn) {
                    if(index <= fixedColIdx) {
                        t0 = this._viewTables.nw;
                        t1 = this._viewTables.sw;
                    } else {
                        t0 = this._viewTables.ne;
                        t1 = this._viewTables.se;
                        idx = idx - (fixedColIdx + 1);
                    }
                    if(fixedRowIdx < 0) {
                        t0 = null;
                    }
                    if(fixedRowIdx === this._rowsCountRaw() - 1) {
                        t1 = null;
                    }
                } else {
                    if(index <= fixedRowIdx) {
                        t0 = this._viewTables.nw;
                        t1 = this._viewTables.ne;
                    } else {
                        t0 = this._viewTables.sw;
                        t1 = this._viewTables.se;
                        idx = idx - (fixedRowIdx + 1);
                    }
                    if(fixedColIdx < 0) {
                        t0 = null;
                    }
                    if(fixedColIdx === gcuigrid._field("leaves").length - 1) {
                        t1 = null;
                    }
                }
                if(t0 === null) {
                    t0 = t1;
                    t1 = null;
                }
                return [
                    t0, 
                    t1, 
                    idx
                ];
            };
            fixedView.prototype.subTables = function () {
                return [
                    this._viewTables.nw, 
                    this._viewTables.ne, 
                    this._viewTables.sw, 
                    this._viewTables.se
                ];
            };
            fixedView.prototype._getGridWidth = function (mode) {
                var gcuigrid = this._grid, tableWidth = gcuigrid.element.outerWidth(true) + gcuigrid.options.splitDistanceX, outWidth = gcuigrid.outerDiv.innerWidth();
                if(this._testNeedVBar(gcuigrid.outerDiv, gcuigrid.element, $(this._viewTables.ne.element()), mode, gcuigrid._autoHeight)) {
                    tableWidth += this._verScrollBarSize;
                }
                if(tableWidth > outWidth) {
                    tableWidth = outWidth;
                }
                return tableWidth;
            };
            fixedView.prototype._getSuperPanel = function () {
                return this._scroller ? this._scroller.data("gcui-superpanel") : null;
            };
            fixedView.prototype._ensureRenderBounds = function () {
                if(this._grid._allowVirtualScrolling()) {
                    this._grid._ensureRenderBounds(this._bounds);
                    if(this._grid._serverSideVirtualScrolling()) {
                    }
                    this._bounds.end = this._bounds.start + this._grid.options.pageSize - 1;
                    this._grid._ensureRenderBounds(this._bounds);
                } else {
                    _super.prototype._ensureRenderBounds.call(this);
                }
            };
            fixedView.prototype._renderContent = function () {
                _super.prototype._renderContent.call(this);
            };
            fixedView.prototype._preRender = function () {
                var docFragment = document.createDocumentFragment(), HTA = gcui.grid.htmlTableAccessor;
                this._grid.outerDiv.wrapInner("<div class=\"gcui-gcuigrid-fixedview\"><div class=\"gcui-gcuigrid-scroller\"><div class=\"gcui-gcuigrid-split-area-se gcui-gcuigrid-content-area\"></div></div></div>");
                this._scroller = this._grid.outerDiv.find(".gcui-gcuigrid-scroller");
                this._scroller.css("padding", 0);
                this._scroller.after(this._splitAreas.nw = $("<div class=\"gcui-gcuigrid-split-area gcui-gcuigrid-split-area-nw\" style=\"overflow:hidden;position:absolute;z-index:4;top:0px;left:0px;\"></div>"));
                this._scroller.after(this._splitAreas.ne = $("<div class=\"gcui-gcuigrid-split-area gcui-gcuigrid-split-area-ne\" style=\"overflow:hidden;position:absolute;z-index:4;top:0px;left:0px;\"></div>"));
                this._scroller.after(this._splitAreas.sw = $("<div class=\"gcui-gcuigrid-split-area gcui-gcuigrid-split-area-sw\" style=\"overflow:hidden;position:absolute;z-index:4;top:0px;left:0px;\"></div>"));
                this._splitAreas.se = this._scroller.find(".gcui-gcuigrid-split-area-se:first");
                this._viewTables = {
                    nw: new HTA(docFragment.appendChild(document.createElement("table")), true, true, true),
                    ne: new HTA(docFragment.appendChild(document.createElement("table")), true, true, true),
                    sw: new HTA(docFragment.appendChild(document.createElement("table")), true, true, true),
                    se: new HTA(docFragment.appendChild(this._grid.element[0]), true, true, true)
                };
            };
            fixedView.prototype._postRender = function () {
                var t00, t01, t10, t11, HTA = gcui.grid.htmlTableAccessor, self = this;
                this._viewTables = {
                    nw: new HTA(t00 = this._viewTables.nw.element()),
                    ne: new HTA(t01 = this._viewTables.ne.element()),
                    sw: new HTA(t10 = this._viewTables.sw.element()),
                    se: new HTA(t11 = this._viewTables.se.element())
                };
                this._splitAreas.nw.empty().append(t00);
                this._splitAreas.ne.empty().append(t01);
                this._splitAreas.sw.empty().append(t10);
                this._splitAreas.se.empty().append(t11);
                $.each(this._viewTables, function (idx, hta) {
                    var $element = $(hta.element());
                    self._grid._setAttr($element, {
                        role: "grid",
                        border: "0",
                        cellpadding: "0",
                        cellspacing: "0"
                    });
                    $element.addClass("gcui-gcuigrid-table").css("border-collapse", "separate").find("> tbody").addClass(self._grid.options.uiCSS.content + " gcui-gcuigrid-data");
                });
                _super.prototype._postRender.call(this);
            };
            fixedView.prototype._rowsCountRaw = function () {
                var t00 = this._viewTables.nw.element(), t01 = this._viewTables.ne.element(), t10 = this._viewTables.sw.element(), t11 = this._viewTables.se.element(), res;
                res = Math.max(t00.rows.length, t01.rows.length) + Math.max(t10.rows.length, t11.rows.length);
                return res;
            };
            fixedView.prototype._createCol = function (column, visibleIdx) {
                return [
                    document.createElement("col"), 
                    document.createElement("col")
                ];
            };
            fixedView.prototype._appendCol = function (domCol, column, visibleIdx) {
                if(visibleIdx <= this._staticColumnIndex) {
                    this._viewTables.nw.appendCol(domCol[0]);
                    this._viewTables.sw.appendCol(domCol[1]);
                } else {
                    this._viewTables.ne.appendCol(domCol[0]);
                    this._viewTables.se.appendCol(domCol[1]);
                }
            };
            fixedView.prototype._insertRow = function (rowType, sectionRowIndex, domRow) {
                var $rt = gcui.grid.rowType, leftSection, rightSection, vt = this._viewTables;
                switch(rowType) {
                    case $rt.header:
                    case $rt.filter:
                        leftSection = vt.nw.ensureTHead();
                        rightSection = vt.ne.ensureTHead();
                        break;
                    case $rt.footer:
                        leftSection = vt.sw.ensureTFoot();
                        rightSection = vt.se.ensureTFoot();
                        break;
                    default:
                        if(sectionRowIndex <= this._staticDataRowIndex) {
                            leftSection = vt.nw.ensureTBody();
                            rightSection = vt.ne.ensureTBody();
                        } else {
                            sectionRowIndex -= this._staticDataRowIndex + 1;
                            leftSection = vt.sw.ensureTBody();
                            rightSection = vt.se.ensureTBody();
                        }
                }
                if(domRow) {
                    return [
                        leftSection.appendChild(domRow), 
                        rightSection.appendChild(domRow.cloneNode(false))
                    ];
                } else {
                    return [
                        leftSection.insertRow(sectionRowIndex > leftSection.rows.length ? -1 : sectionRowIndex), 
                        rightSection.insertRow(sectionRowIndex > rightSection.rows.length ? -1 : sectionRowIndex)
                    ];
                }
            };
            fixedView.prototype._rowRendered = function (rowInfo, rowAttr, rowStyle) {
                var leftRow = rowInfo.$rows[0], rightRow = rowInfo.$rows[1];
                if(!leftRow.cells.length && this._isBodyRow(rowInfo)) {
                    leftRow.parentNode.removeChild(leftRow);
                    leftRow = null;
                }
                if(!rightRow.cells.length && this._isBodyRow(rowInfo)) {
                    rightRow.parentNode.removeChild(rightRow);
                    rightRow = null;
                }
                if(leftRow || rightRow) {
                    if(!leftRow || !rightRow) {
                        rowInfo.$rows = leftRow ? $(leftRow) : $(rightRow);
                    }
                    _super.prototype._rowRendered.call(this, rowInfo, rowAttr, rowStyle);
                }
            };
            fixedView.prototype._appendCell = function (rowInfo, cellIndex, $cell) {
                var staticColIndex = (rowInfo.type !== gcui.grid.rowType.header) ? this._staticColumnIndex : this._staticAllColumnIndex;
                if(cellIndex <= staticColIndex) {
                    rowInfo.$rows[0].appendChild($cell[0]);
                } else {
                    rowInfo.$rows[1].appendChild($cell[0]);
                }
            };
            fixedView.prototype._getRowHeight = function (rowObj, ignoreSpannedCells) {
                if(rowObj[0] && rowObj[1]) {
                    var lRow = rowObj[0], rRow = rowObj[1], $lRow = $(lRow), $rRow = $(rRow), lRowH, rRowH, customHeight, getRowHeightUsingUnspannedCells = function ($row) {
                        var i, domRow = $row[0], domCell;
                        for(i = 0; i < domRow.cells.length; i++) {
                            domCell = domRow.cells[i];
                            if(!domCell.rowSpan || domCell.rowSpan === 1) {
                                return $(domCell).outerHeight();
                            }
                        }
                        ;
                        return $row.height();
                    };
                    if(customHeight = $.data(lRow, "customHeight")) {
                        lRowH = rRowH = parseInt(customHeight);
                    } else {
                        $lRow.css("height", "");
                        $rRow.css("height", "");
                        if(ignoreSpannedCells) {
                            lRowH = getRowHeightUsingUnspannedCells($lRow);
                            rRowH = getRowHeightUsingUnspannedCells($rRow);
                        } else {
                            lRowH = $lRow.height();
                            rRowH = $rRow.height();
                        }
                    }
                    return Math.max(lRowH, rRowH);
                }
                return null;
            };
            fixedView.prototype._setRowHeight = function (rowObj, maxHeight) {
                if(rowObj[0] && rowObj[1]) {
                    var $rowObj = [ $(rowObj[0]), $(rowObj[1]) ], dif;
                    if(maxHeight === null) {
                        return;
                    }
                    maxHeight += 1;
                    $.each($rowObj, function (index, $el) {
                        $el.height(maxHeight);
                        dif = maxHeight - $el.height();
                        if(dif) {
                            $el.height(maxHeight + dif);
                        }
                    });
                }
            };
            fixedView.prototype._adjustRowHeight = function () {
                var gcuigrid = this._grid, fixedColIdx = this._staticColumnIndex, lastColIdx = gcuigrid._field("visibleLeaves").length - 1, fixedRowIdx, lastRowIdx, tables, tableNE, tableNEParent, tableNW, tableNWParent, tableSE, tableSEParent, tableSW, tableSWParent, rowCount, i, j, leftRows, rightRows, heightArray = [];
                if(fixedColIdx > -1 && fixedColIdx < lastColIdx) {
                    fixedRowIdx = this._staticRowIndex;
                    lastRowIdx = this._rowsCountRaw() - 1;
                    tables = this._viewTables;
                    if(fixedRowIdx > -1 && fixedRowIdx <= lastRowIdx) {
                        tableNE = tables.ne.element();
                        tableNEParent = tableNE.parentNode;
                        tableNW = tables.nw.element();
                        tableNWParent = tableNW.parentNode;
                        leftRows = tableNW.rows;
                        rightRows = tableNE.rows;
                        rowCount = leftRows.length;
                        for(i = 0; i < rowCount; i++) {
                            heightArray.push(this._getRowHeight([
                                leftRows[i], 
                                rightRows[i]
                            ], true));
                        }
                    }
                    if(fixedRowIdx >= -1 && fixedRowIdx < lastRowIdx) {
                        tableSE = tables.se.element();
                        tableSEParent = tableSE.parentNode;
                        tableSW = tables.sw.element();
                        tableSWParent = tableSW.parentNode;
                        leftRows = tableSW.rows;
                        rightRows = tableSE.rows;
                        rowCount = leftRows.length;
                        for(i = 0; i < rowCount; i++) {
                            heightArray.push(this._getRowHeight([
                                leftRows[i], 
                                rightRows[i]
                            ]));
                        }
                    }
                    if(fixedRowIdx > -1 && fixedRowIdx <= lastRowIdx) {
                        tableNWParent.removeChild(tableNW);
                        tableNEParent.removeChild(tableNE);
                    }
                    if(fixedRowIdx >= -1 && fixedRowIdx < lastRowIdx) {
                        tableSWParent.removeChild(tableSW);
                        tableSEParent.removeChild(tableSE);
                    }
                    if(fixedRowIdx > -1 && fixedRowIdx <= lastRowIdx) {
                        leftRows = tableNW.rows;
                        rightRows = tableNE.rows;
                        rowCount = leftRows.length;
                        for(i = 0 , j = 0; i < rowCount; i++) {
                            this._setRowHeight([
                                leftRows[i], 
                                rightRows[i]
                            ], heightArray[j++]);
                        }
                    }
                    if(fixedRowIdx >= -1 && fixedRowIdx < lastRowIdx) {
                        leftRows = tableSW.rows;
                        rightRows = tableSE.rows;
                        rowCount = leftRows.length;
                        for(i = 0; i < rowCount; i++) {
                            this._setRowHeight([
                                leftRows[i], 
                                rightRows[i]
                            ], heightArray[j++]);
                        }
                    }
                    if(fixedRowIdx > -1 && fixedRowIdx <= lastRowIdx) {
                        tableNWParent.appendChild(tableNW);
                        tableNEParent.appendChild(tableNE);
                    }
                    if(fixedRowIdx >= -1 && fixedRowIdx < lastRowIdx) {
                        tableSWParent.appendChild(tableSW);
                        tableSEParent.appendChild(tableSE);
                    }
                }
            };
            fixedView.prototype._adjustRowsHeights = function () {
                var $tableSW = $(this._viewTables.sw.element()), $tableSE = $(this._viewTables.se.element()), height;
                $tableSE.css("height", "");
                $tableSW.css("height", "");
                this._adjustRowHeight();
                height = Math.max($tableSE.height(), $tableSW.height());
                $tableSW.height(height);
                $tableSE.height(height);
            };
            fixedView.prototype._destroySuperPanel = function () {
                if(this._scroller.data("gcui-superpanel")) {
                    if(this.vsUI) {
                        this.vsUI.dispose();
                    }
                    this._scroller.superpanel("destroy");
                }
            };
            fixedView.prototype._onScroll = function (event, data) {
                var spInstance = this._getSuperPanel();
                if(this._allowVirtualScrolling) {
                    if(data.dir === "h") {
                        this._setFixedAreaPosition((spInstance).getContentElement(), data.dir, data.position, data.animationOptions, false);
                        this._setFixedAreaPosition(this._splitAreas.ne, data.dir, data.position, data.animationOptions, true);
                    }
                } else {
                    this._setFixedAreaPosition(data.dir === "h" ? this._splitAreas.ne : this._splitAreas.sw, data.dir, data.position, data.animationOptions, true);
                }
                this._grid._trackScrollingPosition(spInstance.options.hScroller.scrollValue, spInstance.options.vScroller.scrollValue);
            };
            fixedView.prototype._onMouseWheel = function (e, delta) {
                var bounds, dir = (delta > 0) ? "top" : "bottom", isOverFixedArea = false, vPos;
                if(this._grid._canInteract()) {
                    bounds = this.getFixedAreaVisibleBounds();
                    $.each(bounds, function (i, o) {
                        if(o && gcui.grid.isOver(e.pageY, e.pageX, o.top, o.left, o.height, o.width)) {
                            isOverFixedArea = true;
                            return false;
                        }
                    });
                    if(isOverFixedArea && this._scroller.data("gcui-superpanel")) {
                        vPos = this._scroller.superpanel("option", "vScroller").scrollValue;
                        this._scroller.superpanel("doScrolling", dir);
                        if(vPos !== this._scroller.superpanel("option", "vScroller").scrollValue) {
                            e.stopPropagation();
                            e.preventDefault();
                        }
                    }
                }
            };
            fixedView.prototype._setFixedAreaPosition = function (element, direction, position, animation, useScrollProp) {
                var prop = {}, key;
                if(direction === "h") {
                    key = useScrollProp ? "scrollLeft" : "left";
                } else {
                    key = useScrollProp ? "scrollTop" : "top";
                }
                if(!useScrollProp) {
                    position = -position;
                }
                if(animation) {
                    prop[key] = position;
                    element.animate(prop, animation);
                } else {
                    if(useScrollProp) {
                        element[0][key] = position;
                    } else {
                        element.css(key, position);
                    }
                }
            };
            fixedView.prototype._testNeedVBar = function (outerDiv, gridElement, tableNE, mode, autoHeight) {
                var excludeVBarWidth, gcuigrid = this._grid, gridWidth = tableNE.width() + gcuigrid.options.splitDistanceX, gridHeight = gridElement.height() + gcuigrid.options.splitDistanceY, outerWidth = outerDiv.width(), outerHeight = outerDiv.height(), contentHeight, topHeight = 0, bottomHeight = 0;
                if(this._allowVirtualScrolling && gcuigrid._totalRowsCount() > 1) {
                    return true;
                }
                if(gcuigrid.$superPanelHeader !== null) {
                    topHeight = gcuigrid.$superPanelHeader.outerHeight(true);
                }
                if(gcuigrid.$bottomPagerDiv !== null) {
                    bottomHeight = gcuigrid.$bottomPagerDiv.outerHeight(true);
                }
                contentHeight = outerHeight - topHeight - bottomHeight;
                if(mode === "both" || mode === "vertical") {
                    excludeVBarWidth = true;
                } else {
                    excludeVBarWidth = (mode === "auto") && ((gridHeight > contentHeight) || (!autoHeight && gridWidth > outerWidth && gridHeight > contentHeight - this._verScrollBarSize));
                }
                return excludeVBarWidth;
            };
            fixedView.prototype._updateSplitAreaBounds = function (bSet) {
                var gcuigrid = this._grid, o = gcuigrid.options, controlHeight, contentHeight, topHeight = 0, bottomHeight = 0;
                if(bSet === 0 || bSet === 2) {
                    this._splitAreas.nw.width(o.splitDistanceX);
                    this._splitAreas.sw.width(o.splitDistanceX);
                    if(gcuigrid.options.staticColumnsAlignment === "right") {
                        this._splitAreas.se.css("marginRight", o.splitDistanceX);
                        this._splitAreas.ne.css("marginRight", o.splitDistanceX);
                    } else {
                        this._splitAreas.se.css("marginLeft", o.splitDistanceX);
                        this._splitAreas.ne.css("marginLeft", o.splitDistanceX);
                    }
                }
                if(bSet === 1 || bSet === 2) {
                    this._scroller.css("height", "");
                    this._splitAreas.se.css("marginTop", 0);
                    controlHeight = gcuigrid.outerDiv.height();
                    if(!gcuigrid._autoHeight) {
                        this._scroller.height(controlHeight);
                    } else {
                        this._scroller.height(controlHeight + o.splitDistanceY);
                    }
                    this._splitAreas.nw.height(o.splitDistanceY);
                    this._splitAreas.ne.height(o.splitDistanceY);
                    if(gcuigrid.$superPanelHeader !== null) {
                        topHeight = gcuigrid.$superPanelHeader.outerHeight(true);
                    }
                    if(gcuigrid.$bottomPagerDiv !== null) {
                        bottomHeight = gcuigrid.$bottomPagerDiv.outerHeight(true);
                    }
                    contentHeight = controlHeight - topHeight - bottomHeight;
                    if(gcuigrid.$superPanelHeader !== null) {
                        this._splitAreas.nw.css("top", topHeight + "px");
                        this._splitAreas.ne.css("top", topHeight + "px");
                    }
                    if(!gcuigrid._autoHeight) {
                        this._splitAreas.sw.height(contentHeight - o.splitDistanceY);
                    } else {
                        this._splitAreas.sw.height(contentHeight);
                    }
                    this._splitAreas.sw.css("top", o.splitDistanceY + topHeight);
                    this._splitAreas.se.css("marginTop", o.splitDistanceY);
                }
            };
            return fixedView;
        })(grid.baseView);
        grid.fixedView = fixedView;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var selection = (function () {
            function selection(gcuigrid) {
                this._updates = 0;
                this._selectedColumns = null;
                this._selectedRows = null;
                this._extend_rules = {
                    "singlecell": [
                        0, 
                        0, 
                        0
                    ],
                    "singlecolumn": [
                        1, 
                        1, 
                        1
                    ],
                    "singlerow": [
                        2, 
                        2, 
                        2
                    ],
                    "singlerange": [
                        0, 
                        1, 
                        2
                    ],
                    "multicolumn": [
                        1, 
                        1, 
                        1
                    ],
                    "multirow": [
                        2, 
                        2, 
                        2
                    ],
                    "multirange": [
                        0, 
                        1, 
                        2
                    ]
                };
                if(!gcuigrid) {
                    throw "invalid arguments";
                }
                this._grid = gcuigrid;
                this._addedCells = new cellInfoOrderedCollection(gcuigrid);
                this._removedCells = new cellInfoOrderedCollection(gcuigrid);
                this._selectedCells = new cellInfoOrderedCollection(gcuigrid);
                this._addedDuringCurTransactionCells = new cellInfoOrderedCollection(gcuigrid);
            }
            selection.prototype.selectedCells = function () {
                return this._selectedCells;
            };
            selection.prototype.addColumns = function (start, end) {
                if(!end && end !== 0) {
                    end = start;
                }
                this.addRange(start, 0, end, 0xFFFFFF);
            };
            selection.prototype.addRange = function (cellRange, y0, x1, y1) {
                if(!cellRange && (arguments.length === 1)) {
                    throw "invalid argument";
                }
                var range = (arguments.length === 4) ? new gcui.grid.cellInfoRange(new gcui.grid.cellInfo(cellRange, y0), new gcui.grid.cellInfo(x1, y1)) : cellRange._clone();
                range._normalize();
                if(!range._isValid()) {
                    throw "invalid argument";
                }
                this.beginUpdate();
                this._startNewTransaction(this._grid._field("currentCell"));
                this._selectRange(range, false, true, gcui.grid.cellRangeExtendMode.none, null);
                this.endUpdate();
            };
            selection.prototype.addRows = function (start, end) {
                if(!end && end !== 0) {
                    end = start;
                }
                this.addRange(0, start, 0xFFFFFF, end);
            };
            selection.prototype.removeRange = function (cellRange, y0, x1, y1) {
                if(!cellRange && (arguments.length === 1)) {
                    throw "invalid argument";
                }
                var range = (arguments.length === 4) ? new gcui.grid.cellInfoRange(new gcui.grid.cellInfo(cellRange, y0), new gcui.grid.cellInfo(x1, y1)) : cellRange._clone();
                range._normalize();
                if(!range._isValid()) {
                    throw "invalid argument";
                }
                this.beginUpdate();
                this._startNewTransaction(this._grid._field("currentCell"));
                this._clearRange(range, gcui.grid.cellRangeExtendMode.none);
                this.endUpdate();
            };
            selection.prototype.removeColumns = function (start, end) {
                if(!end && end !== 0) {
                    end = start;
                }
                this.removeRange(start, 0, end, 0xFFFFFF);
            };
            selection.prototype.removeRows = function (start, end) {
                if(!end && end !== 0) {
                    end = start;
                }
                this.removeRange(0, start, 0xFFFFFF, end);
            };
            selection.prototype.clear = function () {
                this.beginUpdate();
                this._removedCells._clear();
                this._removedCells._addFrom(this._selectedCells);
                this.endUpdate();
            };
            selection.prototype.selectAll = function () {
                this.beginUpdate();
                this._selectRange(this._grid._getDataCellsRange(), false, false, gcui.grid.cellRangeExtendMode.none, null);
                this.endUpdate();
            };
            selection.prototype.beginUpdate = function () {
                this._updates++;
            };
            selection.prototype.endUpdate = function () {
                if(this._updates > 0) {
                    this._updates--;
                    if(this._updates === 0) {
                        this.doSelection();
                        if(this._addedCells.length() || this._removedCells.length()) {
                            if(this._selectedColumns !== null) {
                                this._selectedColumns.UnderlyingDataChanged();
                            }
                            if(this._selectedRows !== null) {
                                this._selectedRows.UnderlyingDataChanged();
                            }
                            this._grid._trigger("selectionChanged", null, {
                                addedCells: this._addedCells,
                                removedCells: this._removedCells
                            });
                        }
                        this._addedCells = new gcui.grid.cellInfoOrderedCollection(this._grid);
                        this._removedCells._clear();
                    }
                }
            };
            selection.prototype._multipleRangesAllowed = function () {
                var mode = this._grid.options.selectionMode;
                return (mode && ((mode = mode.toLowerCase()) === "multicolumn" || mode === "multirow" || mode === "multirange"));
            };
            selection.prototype._anchorCell = function () {
                return this.__anchorCell;
            };
            selection.prototype._startNewTransaction = function (dataCellInfo) {
                if(dataCellInfo) {
                    this.__anchorCell = dataCellInfo._clone();
                    this._addedDuringCurTransactionCells = new gcui.grid.cellInfoOrderedCollection(this._grid);
                }
            };
            selection.prototype._clearRange = function (range, extendMode) {
                var selectionMode = this._grid.options.selectionMode.toLowerCase(), rangeToClear, rowsLen, cellsLen, flag, row, cell, i, len, cellInfo, dataRange = this._grid._getDataCellsRange();
                if(range._isValid() && (selectionMode !== "none") && (this._selectedCells.length() > 0)) {
                    rangeToClear = range._clone();
                    rangeToClear._normalize();
                    rangeToClear._clip(dataRange);
                    if(!range._isValid()) {
                        return;
                    }
                    rangeToClear._extend(this._extend_rules[selectionMode][extendMode], dataRange);
                    this.beginUpdate();
                    for(i = 0 , len = this._selectedCells.length(); i < len; i++) {
                        cellInfo = this._selectedCells.item(i);
                        if(rangeToClear._containsCellInfo(cellInfo)) {
                            this._removedCells._add(cellInfo);
                        }
                    }
                    this.endUpdate();
                }
            };
            selection.prototype._selectRange = function (range, ctrlKey, shiftKey, extendMode, endPoint) {
                var selectionMode = this._grid.options.selectionMode.toLowerCase(), rangeToSelect, dataRange = this._grid._getDataCellsRange();
                if((selectionMode !== "none") && range._isValid()) {
                    rangeToSelect = range._clone();
                    rangeToSelect._normalize();
                    rangeToSelect._clip(dataRange);
                    if(!rangeToSelect._isValid()) {
                        return;
                    }
                    this.beginUpdate();
                    if(!this._multipleRangesAllowed()) {
                        this.clear();
                    } else {
                        if(ctrlKey || shiftKey) {
                            if(shiftKey) {
                                this._removedCells._clear();
                                this._removedCells._addFrom(this._addedDuringCurTransactionCells);
                            }
                        } else {
                            this.clear();
                        }
                    }
                    switch(selectionMode) {
                        case "singlecell":
                        case "singlecolumn":
                        case "singlerow":
                            rangeToSelect = (endPoint === null) ? new gcui.grid.cellInfoRange(rangeToSelect.topLeft(), rangeToSelect.topLeft()) : new gcui.grid.cellInfoRange(endPoint, endPoint);
                            break;
                    }
                    rangeToSelect._extend(this._extend_rules[selectionMode][extendMode], dataRange);
                    this.doRange(rangeToSelect, true);
                    this.endUpdate();
                }
            };
            selection.prototype.doSelection = function () {
                var offsets = this._grid._getDataToAbsOffset(), cellOffset = offsets.x, rowOffset = offsets.y, view = this._grid._view(), i, len, info, cell, $cell, index, $rs = gcui.grid.renderState, rowInfo, state, prevRowIndex = -1;
                for(i = 0 , len = this._removedCells.length(); i < len; i++) {
                    info = this._removedCells.item(i);
                    if(this._addedCells.indexOf(info) < 0) {
                        cell = view.getCell(info.cellIndex() + cellOffset, info.rowIndex() + rowOffset);
                        if(cell) {
                            if(prevRowIndex !== info.rowIndex()) {
                                rowInfo = info.row();
                                prevRowIndex = info.rowIndex();
                            }
                            $cell = $(cell);
                            state = view._changeCellRenderState($cell, $rs.selected, false);
                            this._grid.cellStyleFormatter.format($cell, info.cellIndex(), info.column(), rowInfo, state);
                        }
                        this._selectedCells._remove(info);
                        this._addedDuringCurTransactionCells._remove(info);
                    } else {
                        this._removedCells._removeAt(i);
                        i--;
                        len--;
                    }
                }
                prevRowIndex = -1;
                for(i = 0 , len = this._addedCells.length(); i < len; i++) {
                    info = this._addedCells.item(i);
                    index = this._selectedCells.indexOf(info);
                    if(index < 0) {
                        cell = view.getCell(info.cellIndex() + cellOffset, info.rowIndex() + rowOffset);
                        if(cell) {
                            if(prevRowIndex !== info.rowIndex()) {
                                rowInfo = info.row();
                                prevRowIndex = info.rowIndex();
                            }
                            $cell = $(cell);
                            state = view._changeCellRenderState($cell, $rs.selected, true);
                            this._grid.cellStyleFormatter.format($cell, info.cellIndex(), info.column(), rowInfo, state);
                        }
                        this._selectedCells._insertUnsafe(info, ~index);
                        this._addedDuringCurTransactionCells._add(info);
                    } else {
                        this._addedCells._removeAt(i);
                        i--;
                        len--;
                    }
                }
            };
            selection.prototype.doRange = function (range, add) {
                var x0 = range.topLeft().cellIndex(), y0 = range.topLeft().rowIndex(), x1 = range.bottomRight().cellIndex(), y1 = range.bottomRight().rowIndex(), cnt, row, col, cell, view = this._grid._view(), rowInfo, rows;
                if(add) {
                    cnt = this._addedCells.length();
                    rows = this._grid._rows();
                    for(row = y0; row <= y1; row++) {
                        rowInfo = view._getRowInfo(rows.item(row));
                        if(rowInfo.type & gcui.grid.rowType.data) {
                            for(col = x0; col <= x1; col++) {
                                cell = new gcui.grid.cellInfo(col, row);
                                if(cnt === 0) {
                                    this._addedCells._appendUnsafe(cell);
                                } else {
                                    this._addedCells._add(cell);
                                }
                            }
                        }
                    }
                } else {
                    cnt = this._removedCells.length();
                    for(row = y0; row <= y1; row++) {
                        for(col = x0; col <= x1; col++) {
                            cell = new gcui.grid.cellInfo(col, row);
                            if(cnt === 0) {
                                this._removedCells._appendUnsafe(cell);
                            } else {
                                this._removedCells._add(cell);
                            }
                        }
                    }
                }
            };
            return selection;
        })();
        grid.selection = selection;        
        var cellInfoOrderedCollection = (function () {
            function cellInfoOrderedCollection(gcuigrid) {
                this._clear = function () {
                    this._list.length = 0;
                };
                if(!gcuigrid) {
                    throw "invalid arguments";
                }
                this._grid = gcuigrid;
                this._list = [];
            }
            cellInfoOrderedCollection.prototype.item = function (index) {
                return this._list[index];
            };
            cellInfoOrderedCollection.prototype.length = function () {
                return this._list.length;
            };
            cellInfoOrderedCollection.prototype.indexOf = function (cellIndex, rowIndex) {
                if(arguments.length === 1) {
                    rowIndex = cellIndex.rowIndex();
                    cellIndex = cellIndex.cellIndex();
                }
                var lo = 0, hi = this._list.length - 1, med, current, cmp;
                while(lo <= hi) {
                    med = lo + ((hi - lo) >> 1);
                    current = this._list[med];
                    cmp = current.rowIndex() - rowIndex;
                    if(cmp === 0) {
                        cmp = current.cellIndex() - cellIndex;
                    }
                    if(cmp < 0) {
                        lo = med + 1;
                    } else {
                        if(cmp > 0) {
                            hi = med - 1;
                        } else {
                            return med;
                        }
                    }
                }
                return ~lo;
            };
            cellInfoOrderedCollection.prototype.toString = function () {
                var val = "", i, len;
                for(i = 0 , len = this._list.length; i < len; i++) {
                    val += this._list[i].toString() + "\n";
                }
                return val;
            };
            cellInfoOrderedCollection.prototype._add = function (value) {
                var idx = this.indexOf(value);
                if(idx < 0) {
                    this._list.splice(~idx, 0, value);
                    value._setGridView(this._grid);
                    return true;
                }
                return false;
            };
            cellInfoOrderedCollection.prototype._addFrom = function (addFrom) {
                if(addFrom) {
                    var fromLen = addFrom.length(), thisLen = this._list.length, i;
                    if(thisLen === 0) {
                        this._list.length = fromLen;
                        for(i = 0; i < fromLen; i++) {
                            this._list[i] = addFrom.item(i);
                            this._list[i]._setGridView(this._grid);
                        }
                    } else {
                        for(i = 0; i < fromLen; i++) {
                            this._add(addFrom.item(i));
                        }
                    }
                }
            };
            cellInfoOrderedCollection.prototype._appendUnsafe = function (value) {
                this._list[this._list.length] = value;
                value._setGridView(this._grid);
            };
            cellInfoOrderedCollection.prototype._insertUnsafe = function (value, index) {
                this._list.splice(index, 0, value);
            };
            cellInfoOrderedCollection.prototype._remove = function (value) {
                var idx = this.indexOf(value);
                if(idx >= 0) {
                    this._list.splice(idx, 1);
                    return true;
                }
                return false;
            };
            cellInfoOrderedCollection.prototype._removeAt = function (index) {
                this._list.splice(index, 1);
            };
            cellInfoOrderedCollection.prototype._getColumnsIndicies = function () {
                var columns = [], len = this._list.length, tmpColumns, i, len2;
                if(len) {
                    tmpColumns = [];
                    for(i = 0; i < len; i++) {
                        tmpColumns[this._list[i].cellIndex()] = 1;
                    }
                    len = tmpColumns.length;
                    len2 = 0;
                    for(i = 0; i < len; i++) {
                        if(tmpColumns[i]) {
                            columns[len2++] = i;
                        }
                    }
                }
                return columns;
            };
            cellInfoOrderedCollection.prototype._getSelectedRowsIndicies = function () {
                var rows = [], len = this._list.length, tmpRows, i, len2;
                if(len) {
                    tmpRows = [];
                    for(i = 0; i < len; i++) {
                        tmpRows[this._list[i].rowIndex()] = 1;
                    }
                    len = tmpRows.length;
                    len2 = 0;
                    for(i = 0; i < len; i++) {
                        if(tmpRows[i]) {
                            rows[len2++] = i;
                        }
                    }
                }
                return rows;
            };
            cellInfoOrderedCollection.prototype._rectangulate = function () {
                var len = this._list.length, x0 = 0xFFFFFFFF, y0 = 0xFFFFFFFF, x1 = 0, y1 = 0, i, cellInfo;
                if(len) {
                    for(i = 0; i < len; i++) {
                        cellInfo = this._list[i];
                        x0 = Math.min(x0, cellInfo.cellIndex());
                        y0 = Math.min(y0, cellInfo.rowIndex());
                        x1 = Math.max(x1, cellInfo.cellIndex());
                        y1 = Math.max(y1, cellInfo.rowIndex());
                    }
                    return new gcui.grid.cellInfoRange(new gcui.grid.cellInfo(x0, y0), new gcui.grid.cellInfo(x1, y1));
                }
                return null;
            };
            return cellInfoOrderedCollection;
        })();
        grid.cellInfoOrderedCollection = cellInfoOrderedCollection;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var uiSelection = (function () {
            function uiSelection(gcuigrid) {
                this._gap_to_start = 10;
                this._inProgress = false;
                this._additionalEventsAttached = false;
                this._grid = gcuigrid;
                this._evntFormat = "{0}." + this._grid.widgetName + ".selectionui";
                this._addedCells = new gcui.grid.cellInfoOrderedCollection(this._grid);
                this._view = this._grid._view();
                this._rootElement = this._view.focusableElement();
                this._visLeavesLen = this._grid._field("visibleLeaves").length;
                this._rootElement.bind(this._eventKey("mousedown"), $.proxy(this._onGridMouseDown, this));
            }
            uiSelection.prototype.dispose = function () {
                this._rootElement.unbind(this._eventKey("mousedown"), this._onGridMouseDown);
                this._detachAdditionalEvents();
            };
            uiSelection.prototype._onGridMouseDown = function (args) {
                if(!this._grid._canInteract() || this._grid.options.selectionMode.toLowerCase() === "none") {
                    return;
                }
                var visibleBounds = this._view.getVisibleAreaBounds(), mouse = {
                    x: args.pageX,
                    y: args.pageY
                }, tag = ((args.target && (args.target).tagName !== undefined) ? (args.target).tagName.toLowerCase() : undefined), $target = $(args.target);
                if((!tag || $target.is("td.gcuigridtd, th.gcuigridtd, div.gcui-gcuigrid-innercell")) && (mouse.x > visibleBounds.left && mouse.x < visibleBounds.left + visibleBounds.width) && (mouse.y > visibleBounds.top && mouse.y < visibleBounds.top + visibleBounds.height)) {
                    this._attachAdditionalEvents();
                    this._startPos = mouse;
                    this._startCellInfo = this._coordToDataCellInfo(this._startPos);
                }
            };
            uiSelection.prototype._onDocumentMouseMove = function (args) {
                if(!this._startCellInfo || !this._startCellInfo._isValid()) {
                    return;
                }
                var mouse = {
                    x: args.pageX,
                    y: args.pageY
                }, tmp, info, range, dataOffset, desiredCells, rowsLen, cellsLen, row, cell, i, len, $cell, rowInfo, prevRowIndex, state, view = this._grid._view(), rows = this._grid._rows(), $rs = gcui.grid.renderState;
                if(!this._inProgress) {
                    this._inProgress = (Math.abs(this._startPos.x - mouse.x) > this._gap_to_start) || (Math.abs(this._startPos.y - mouse.y) > this._gap_to_start);
                }
                if(this._inProgress) {
                    tmp = this._coordToDataCellInfo(mouse);
                    if(!tmp._isValid()) {
                        return;
                    }
                    this._endCellInfo = tmp;
                    range = new gcui.grid.cellInfoRange(this._startCellInfo, this._endCellInfo);
                    range._normalize();
                    range._clip(this._grid._getDataCellsRange());
                    if(range._isValid() && !range.isEqual(this._prevMouseMoveRange)) {
                        dataOffset = this._grid._getDataToAbsOffset();
                        this._prevMouseMoveRange = range;
                        desiredCells = new gcui.grid.cellInfoOrderedCollection(this._grid);
                        rowsLen = range.bottomRight().rowIndex();
                        cellsLen = range.bottomRight().cellIndex();
                        for(row = range.topLeft().rowIndex(); row <= rowsLen; row++) {
                            rowInfo = view._getRowInfo(rows.item(row));
                            if(rowInfo.type & gcui.grid.rowType.data) {
                                for(cell = range.topLeft().cellIndex(); cell <= cellsLen; cell++) {
                                    desiredCells._appendUnsafe(new gcui.grid.cellInfo(cell, row));
                                }
                            }
                        }
                        prevRowIndex = -1;
                        for(i = 0 , len = this._addedCells.length(); i < len; i++) {
                            info = this._addedCells.item(i);
                            if(desiredCells.indexOf(info) < 0) {
                                if(this._grid.selection().selectedCells().indexOf(info) < 0) {
                                    cell = this._view.getCell(info.cellIndex() + dataOffset.x, info.rowIndex() + dataOffset.y);
                                    if(cell) {
                                        if(prevRowIndex !== info.rowIndex()) {
                                            rowInfo = info.row();
                                            prevRowIndex = info.rowIndex();
                                        }
                                        $cell = $(cell);
                                        state = view._changeCellRenderState($cell, $rs.selected, false);
                                        this._grid.cellStyleFormatter.format($cell, info.cellIndex(), info.column(), rowInfo, state);
                                    }
                                }
                                this._addedCells._removeAt(i);
                                i--;
                                len--;
                            }
                        }
                        prevRowIndex = -1;
                        for(i = 0 , len = desiredCells.length(); i < len; i++) {
                            info = desiredCells.item(i);
                            if(this._addedCells.indexOf(info) < 0 && this._grid.selection().selectedCells().indexOf(info) < 0) {
                                if(this._addedCells._add(info)) {
                                    cell = this._view.getCell(info.cellIndex() + dataOffset.x, info.rowIndex() + dataOffset.y);
                                    if(cell) {
                                        if(prevRowIndex !== info.rowIndex()) {
                                            rowInfo = info.row();
                                            prevRowIndex = info.rowIndex();
                                        }
                                        $cell = $(cell);
                                        state = view._changeCellRenderState($cell, $rs.selected, true);
                                        this._grid.cellStyleFormatter.format($cell, info.cellIndex(), info.column(), rowInfo, state);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            uiSelection.prototype._onDocumentMouseUp = function (args) {
                this._detachAdditionalEvents();
                if(this._inProgress) {
                    this._inProgress = false;
                    if(this._prevMouseMoveRange && this._prevMouseMoveRange._isValid()) {
                        this._grid._changeCurrentCell(args, this._endCellInfo, {
                            changeSelection: false,
                            setFocus: false
                        });
                        if(!args.shiftKey || (!this._grid.selection()._multipleRangesAllowed() && this._grid.options.selectionMode.toLowerCase() !== "singleRange")) {
                            this._grid.selection()._startNewTransaction(this._startCellInfo);
                        }
                        this._grid.selection().beginUpdate();
                        this._grid.selection()._selectRange(this._prevMouseMoveRange, args.shiftKey, args.ctrlKey, gcui.grid.cellRangeExtendMode.none, this._endCellInfo);
                        this._grid.selection().endUpdate();
                        var view = this._grid._view(), dataOffset = this._grid._getDataToAbsOffset(), i, len, info, cell, $cell, prevRowIndex = -1, rowInfo, state, $rs = gcui.grid.renderState;
                        for(i = 0 , len = this._addedCells.length(); i < len; i++) {
                            info = this._addedCells.item(i);
                            if(this._grid.selection().selectedCells().indexOf(info) < 0) {
                                cell = view.getCell(info.cellIndex() + dataOffset.x, info.rowIndex() + dataOffset.y);
                                if(cell !== null) {
                                    if(prevRowIndex !== info.rowIndex()) {
                                        rowInfo = info.row();
                                        prevRowIndex = info.rowIndex();
                                    }
                                    $cell = $(cell);
                                    state = view._changeCellRenderState($cell, $rs.selected, false);
                                    this._grid.cellStyleFormatter.format($cell, info.cellIndex(), info.column(), rowInfo, state);
                                }
                            }
                        }
                        this._addedCells._clear();
                        this._startCellInfo = this._endCellInfo = this._prevMouseMoveRange = null;
                        return false;
                    }
                }
            };
            uiSelection.prototype._attachAdditionalEvents = function () {
                if(!this._additionalEventsAttached) {
                    try  {
                        this._view.toggleDOMSelection(false);
                        $(document).bind(this._eventKey("mousemove"), $.proxy(this._onDocumentMouseMove, this)).bind(this._eventKey("mouseup"), $.proxy(this._onDocumentMouseUp, this));
                    }finally {
                        this._additionalEventsAttached = true;
                    }
                }
            };
            uiSelection.prototype._detachAdditionalEvents = function () {
                if(this._additionalEventsAttached) {
                    try  {
                        this._view.toggleDOMSelection(true);
                        $(document).unbind(this._eventKey("mousemove"), this._onDocumentMouseMove).unbind(this._eventKey("mouseup"), this._onDocumentMouseUp);
                    }finally {
                        this._additionalEventsAttached = false;
                    }
                }
            };
            uiSelection.prototype._eventKey = function (eventType) {
                return gcui.grid.stringFormat(this._evntFormat, eventType);
            };
            uiSelection.prototype._coordToDataCellInfo = function (pnt) {
                var left = 0, right = this._visLeavesLen - 1, median = 0, cellIdx = -1, bounds, gridRowsAccessor = new gcui.grid.rowAccessor(this._view, 2, 0, 0), rowIdx, rowObj, dataOffset, result;
                while(left <= right) {
                    median = ((right - left) >> 1) + left;
                    bounds = gcui.grid.bounds(this._view.getHeaderCell(median));
                    if(!bounds) {
                        rowObj = gridRowsAccessor.item(0);
                        bounds = gcui.grid.bounds(gcui.grid.rowAccessor.getCell(rowObj, median));
                    }
                    if(!bounds) {
                        break;
                    }
                    if(pnt.x < bounds.left) {
                        right = median - 1;
                    } else if(pnt.x > bounds.left + bounds.width) {
                        left = median + 1;
                    } else {
                        cellIdx = median;
                        break;
                    }
                }
                if(cellIdx === -1) {
                    return gcui.grid.cellInfo.outsideValue;
                }
                gridRowsAccessor = new gcui.grid.rowAccessor(this._view, 0, 0, 0);
                rowIdx = -1;
                left = 0;
                right = gridRowsAccessor.length() - 1;
                median = 0;
                while(left <= right) {
                    median = ((right - left) >> 1) + left;
                    rowObj = gridRowsAccessor.item(median);
                    bounds = gcui.grid.bounds(gcui.grid.rowAccessor.getCell(rowObj, 0));
                    if(pnt.y < bounds.top) {
                        right = median - 1;
                    } else if(pnt.y > bounds.top + bounds.height) {
                        left = median + 1;
                    } else {
                        rowIdx = median;
                        break;
                    }
                }
                if(rowIdx === -1) {
                    return gcui.grid.cellInfo.outsideValue;
                }
                dataOffset = this._grid._getDataToAbsOffset();
                result = new gcui.grid.cellInfo(cellIdx - dataOffset.x, rowIdx - dataOffset.y);
                result._clip(this._grid._getDataCellsRange());
                return result;
            };
            return uiSelection;
        })();
        grid.uiSelection = uiSelection;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var rowAccessor = (function () {
            function rowAccessor(view, scope, offsetTop, offsetBottom) {
                this._view = view;
                this._scope = scope;
                this._offsetBottom = offsetBottom;
                this._offsetTop = offsetTop;
            }
            rowAccessor.prototype.item = function (index) {
                var len = this.length();
                return (index < len) ? this._view.getJoinedRows(index + this._offsetTop, this._scope) : null;
            };
            rowAccessor.prototype.length = function () {
                var joinedTables = this._view.getJoinedTables(true, 0), len = 0, htmlAccessor;
                if(htmlAccessor = joinedTables[0]) {
                    len = htmlAccessor.getSectionLength(this._scope);
                }
                if(htmlAccessor = joinedTables[1]) {
                    len += htmlAccessor.getSectionLength(this._scope);
                }
                len -= this._offsetTop + this._offsetBottom;
                if(len < 0) {
                    len = 0;
                }
                return len;
            };
            rowAccessor.iterateCells = function iterateCells(rowObj, callback, param) {
                if(rowObj && callback) {
                    var globCellIdx = 0, i, len, domRow, j, cellLen, result;
                    for(i = 0 , len = rowObj.length; i < len; i++) {
                        domRow = rowObj[i];
                        if(domRow) {
                            for(j = 0 , cellLen = domRow.cells.length; j < cellLen; j++) {
                                result = callback(domRow.cells[j], globCellIdx++, param);
                                if(result !== true) {
                                    return;
                                }
                            }
                        }
                    }
                }
            };
            rowAccessor.getCell = function getCell(rowObj, globCellIndex) {
                var domRow, cellLen;
                if(rowObj && (domRow = rowObj[0])) {
                    cellLen = domRow.cells.length;
                    if(globCellIndex < cellLen) {
                        return domRow.cells[globCellIndex];
                    }
                    globCellIndex -= cellLen;
                    if(domRow = rowObj[1]) {
                        cellLen = domRow.cells.length;
                        if(globCellIndex < cellLen) {
                            return domRow.cells[globCellIndex];
                        }
                    }
                }
                return null;
            };
            rowAccessor.cellsCount = function cellsCount(rowObj) {
                var res = 0, domRow;
                if(rowObj && (domRow = rowObj[0])) {
                    res = domRow.cells.length;
                    if(domRow = rowObj[1]) {
                        res += domRow.cells.length;
                    }
                }
                return res;
            };
            return rowAccessor;
        })();
        grid.rowAccessor = rowAccessor;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var cellEditorHelper = (function () {
            function cellEditorHelper() {
                this._timeout = 25;
            }
            cellEditorHelper.prototype.currentCellEditStart = function (grid, e) {
                var result = false, currentCell = grid.currentCell(), view = grid._view(), rowInfo, args, $innerDiv, rowType;
                if(currentCell._isValid() && !currentCell._isEdit() && (currentCell.column().dataIndex >= 0)) {
                    rowInfo = currentCell.row();
                    if(rowInfo) {
                        rowType = rowInfo.type;
                        if(rowType & gcui.grid.rowType.data) {
                            args = {
                                cell: currentCell,
                                event: e,
                                handled: false
                            };
                            if(result = grid._trigger("beforeCellEdit", null, args)) {
                                if(!args.handled) {
                                    result = this._defaultBeforeCellEdit(grid, args);
                                }
                            }
                            if(result) {
                                currentCell._isEdit(true);
                                if(grid._showRowHeader()) {
                                    $innerDiv = $((rowInfo.$rows[0]).cells[0]).children("div.gcui-gcuigrid-innercell");
                                    if($innerDiv.length) {
                                        $innerDiv.empty();
                                        $innerDiv.append($("<div>&nbsp;</div>").addClass(grid.options.uiCSS.icon + " ui-icon-pencil"));
                                    }
                                }
                            }
                        }
                    }
                }
                return result;
            };
            cellEditorHelper.prototype.currentCellEditEnd = function (grid, e) {
                var currentCell = grid.currentCell(), column = currentCell.column(), result = false, view = grid._view(), rowInfo, rowType, escPressed, a, b, domCell, keyCodeEnum = gcui.getKeyCodeEnum(), inputType;
                if(!currentCell._isValid() || !currentCell._isEdit()) {
                    return;
                }
                rowInfo = currentCell.row();
                if(rowInfo) {
                    rowType = rowInfo.type;
                    if(!(rowType & gcui.grid.rowType.data)) {
                        return result;
                    }
                    escPressed = (e && e.which === keyCodeEnum.ESCAPE);
                    if(!e || (!escPressed)) {
                        var bcuArgs = {
                            cell: currentCell,
                            value: undefined
                        };
                        if(result = grid._trigger("beforeCellUpdate", null, bcuArgs)) {
                            if(bcuArgs.value === undefined) {
                                bcuArgs.value = this._getCellValue(grid, currentCell);
                            }
                            a = bcuArgs.value;
                            b = currentCell.value();
                            try  {
                                inputType = this._getHTMLInputElementType(currentCell);
                                if(gcui.grid.HTML5InputSupport.isExtendSupportRequired(inputType)) {
                                    bcuArgs.value = gcui.grid.HTML5InputSupport.parse(bcuArgs.value, inputType);
                                    bcuArgs.value = gcui.grid.HTML5InputSupport.extend(b, bcuArgs.value, inputType);
                                } else {
                                    bcuArgs.value = grid.parse(currentCell.column(), bcuArgs.value);
                                }
                                a = bcuArgs.value;
                            } catch (ex) {
                                bcuArgs.value = a;
                            }
                            if(gcui.grid.getDataType(column) === "datetime") {
                                if(a instanceof Date) {
                                    a = a.getTime();
                                }
                                if(b instanceof Date) {
                                    b = b.getTime();
                                }
                            }
                            if(a !== b) {
                                try  {
                                    currentCell.value(bcuArgs.value);
                                } catch (ex) {
                                    var icvArgs = {
                                        cell: currentCell,
                                        value: bcuArgs.value
                                    };
                                    result = false;
                                    grid._trigger("invalidCellValue", null, icvArgs);
                                }
                                if(result) {
                                    var acuArgs = {
                                        cell: currentCell
                                    };
                                    grid._trigger("afterCellUpdate", null, acuArgs);
                                }
                            }
                        }
                    } else {
                        result = true;
                    }
                    if(result) {
                        var aceArgs = {
                            cell: currentCell,
                            event: e,
                            handled: false
                        };
                        grid._trigger("afterCellEdit", null, aceArgs);
                        if(!aceArgs.handled) {
                            result = this._defaultAfterCellEdit(grid, aceArgs);
                        }
                        if(result) {
                            currentCell._isEdit(false);
                        }
                        if(grid._showRowHeader()) {
                            $((rowInfo.$rows[0]).cells[0]).children("div.gcui-gcuigrid-innercell").html("&nbsp;");
                        }
                        window.setTimeout(function () {
                            if(!grid._destroyed) {
                                currentCell = grid.currentCell();
                                if(domCell = currentCell.tableCell()) {
                                    $(domCell).attr("tabIndex", 0).focus();
                                } else {
                                    $(grid._view().focusableElement()).focus();
                                }
                            }
                        }, this._timeout);
                    }
                }
                return result;
            };
            cellEditorHelper.prototype._defaultBeforeCellEdit = function (grid, args) {
                var column = args.cell.column(), result = false, value, strValue, $container, $input, len, kbEvent, keyCodeEnum = gcui.getKeyCodeEnum(), inputType = gcui.grid.HTML5InputSupport.getDefaultInputType(grid._isMobileEnv(), column);
                if(column.dataIndex >= 0) {
                    value = args.cell.value();
                    result = true;
                    try  {
                        $container = args.cell.container();
                        if(gcui.grid.getDataType(column) === "boolean") {
                            var $span = $container.children("span");
                            if($span.length && $span.prop("disabled")) {
                                $.data($span[0], "disabledSpan", true);
                                $span.prop("disabled", false);
                            }
                            $input = $container.find(":checkbox");
                            $input.prop("disabled", false);
                            $input.focus();
                            if(args.event && args.event.type === "keypress") {
                                $input.one("keyup", function (e) {
                                    if(e.which === keyCodeEnum.SPACE) {
                                        e.preventDefault();
                                        ($input[0]).checked = !value;
                                    }
                                });
                            }
                        } else {
                            $input = $("<input />").attr("type", inputType).addClass("gridinput gcui-input " + grid.options.uiCSS.stateFocus).bind("keydown", grid, $.proxy(this._checkBoxOrInputKeyDown, this));
                            $input.bind((($.support).selectstart ? "selectstart" : "mousedown"), function (event) {
                                event.stopPropagation();
                            });
                            if(args.event && args.event.type === "keypress" && args.event.which) {
                                $input.val(String.fromCharCode(args.event.which));
                            } else {
                                switch(gcui.grid.getDataType(column)) {
                                    case "currency":
                                    case "number":
                                        if(value !== null) {
                                            $input.val(value);
                                            break;
                                        }
                                    case "datetime":
                                        if(gcui.grid.HTML5InputSupport.isExtendSupportRequired(inputType)) {
                                            $input.val(gcui.grid.HTML5InputSupport.toStr(value, inputType));
                                            break;
                                        }
                                    default:
                                        $input.val(grid.toStr(column, value));
                                        break;
                                }
                            }
                            $container.empty().append($input);
                            len = $input.val().length;
                            if(inputType === "text") {
                                new gcui.grid.domSelection($input[0]).setSelection({
                                    start: len,
                                    end: len
                                });
                            }
                            $input.focus();
                            setTimeout(function () {
                                $input.focus();
                            }, this._timeout * 2);
                        }
                    } catch (ex) {
                        alert(ex.message);
                        result = false;
                    }
                }
                return result;
            };
            cellEditorHelper.prototype._defaultAfterCellEdit = function (grid, args) {
                var leafOpt = args.cell.column(), result = false, $container, cellValue, $input, rowInfo, view;
                if(leafOpt.dataIndex >= 0) {
                    result = true;
                    view = grid._view();
                    try  {
                        $container = args.cell.container();
                        cellValue = grid.toStr(leafOpt, args.cell.value());
                        rowInfo = view._getRowInfo(grid._rows().item(args.cell.rowIndex()));
                        if(gcui.grid.getDataType(leafOpt) === "boolean") {
                            var $span = $container.children("span"), disable = $span.length && $.data($span[0], "disabledSpan");
                            $input = $container.find(":checkbox");
                            if(cellValue === "true") {
                                $input.attr("checked", "checked");
                            } else {
                                $input.removeAttr("checked");
                            }
                            if(disable) {
                                $span.prop("disabled", true);
                                $input.prop("disabled", true);
                            }
                        } else {
                            grid.cellFormatter.format($container, leafOpt, cellValue, rowInfo);
                        }
                    } catch (ex) {
                        alert("defaultAfterCellEdit: " + ex.message);
                        result = false;
                    }
                }
                return result;
            };
            cellEditorHelper.prototype._checkBoxOrInputKeyDown = function (args) {
                var keyCodeEnum = gcui.getKeyCodeEnum();
                if(args.which === keyCodeEnum.ENTER) {
                    var grid = args.data;
                    if(grid) {
                        grid._endEditInternal(args);
                        return false;
                    }
                }
            };
            cellEditorHelper.prototype._getCellValue = function (grid, currentCell) {
                var $input = currentCell.container().find(":input:first"), result = null;
                if($input.length) {
                    result = ($input.attr("type") === "checkbox") ? ($input[0]).checked : $input.val();
                }
                return result;
            };
            cellEditorHelper.prototype._getHTMLInputElementType = function (currentCell) {
                return currentCell.container().find(":input:first").attr("type");
            };
            return cellEditorHelper;
        })();
        grid.cellEditorHelper = cellEditorHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var cellFormatterHelper = (function () {
            function cellFormatterHelper() { }
            cellFormatterHelper._div = document.createElement("div");
            cellFormatterHelper.prototype.format = function ($container, column, formattedValue, rowInfo) {
                if(rowInfo.type & gcui.grid.rowType.footer) {
                    if(column.aggregate && (column.aggregate !== "none")) {
                        formattedValue = gcui.grid.stringFormat(column.footerText || "{0}", column._totalsValue || "");
                    } else {
                        formattedValue = column.footerText || column._footerTextDOM || "";
                    }
                }
                var useDefault = true, defaultFormatter = null, args = {
                    $container: $container,
                    column: column,
                    formattedValue: formattedValue,
                    row: rowInfo,
                    afterDefaultCallback: null
                }, temp;
                if($.isFunction(column.cellFormatter)) {
                    useDefault = !column.cellFormatter(args);
                }
                if(useDefault) {
                    switch(gcui.grid.getDataType(column)) {
                        case "boolean":
                            defaultFormatter = this._boolFormatter;
                            break;
                        default:
                            defaultFormatter = this._textFormatter;
                    }
                    if(defaultFormatter) {
                        defaultFormatter.call(this, args);
                        if($.isFunction(temp = args.afterDefaultCallback)) {
                            temp(args);
                        }
                    }
                }
            };
            cellFormatterHelper.prototype._textFormatter = function (args) {
                var domContainer;
                switch(args.row.type) {
                    case gcui.grid.rowType.filter:
                        this._defFormatFilterCell(args);
                        break;
                    default:
                        domContainer = args.$container[0];
                        if(domContainer.firstChild) {
                            while(domContainer.firstChild) {
                                domContainer.removeChild(domContainer.firstChild);
                            }
                        }
                        gcui.grid.cellFormatterHelper._div.innerHTML = args.formattedValue || "&nbsp;";
                        while(gcui.grid.cellFormatterHelper._div.firstChild) {
                            domContainer.appendChild(gcui.grid.cellFormatterHelper._div.firstChild);
                        }
                }
            };
            cellFormatterHelper.prototype._boolFormatter = function (args) {
                var grid, allowEditing, disableStr = "disabled='disabled'", targetElement, currentCell, $rt = gcui.grid.rowType, keyCodeEnum = gcui.getKeyCodeEnum();
                switch(args.row.type) {
                    case $rt.data:
                    case $rt.data | $rt.dataAlt:
                        grid = args.column.owner;
                        allowEditing = grid.options.allowEditing && (args.column.readOnly !== true);
                        if(allowEditing) {
                            disableStr = "";
                        }
                        if(grid.parse(args.column, grid._dataViewWrapper.getValue(args.row.data, args.column.dataKey)) === true) {
                            args.$container.html("<input class='gridinput' type='checkbox' checked='checked' " + disableStr + " />");
                        } else {
                            args.$container.html("<input class='gridinput' type='checkbox' " + disableStr + " />");
                        }
                        if(allowEditing) {
                            args.$container.children("input").bind("mousedown", function (e) {
                                targetElement = args.$container.parent()[0];
                                currentCell = grid.currentCell();
                                if(currentCell.tableCell() !== targetElement) {
                                    grid._onClick({
                                        target: targetElement
                                    });
                                }
                                if(!currentCell._isEdit()) {
                                    grid.beginEdit();
                                }
                            }).bind("keydown", function (e) {
                                if(e.which === keyCodeEnum.ENTER) {
                                    grid._endEditInternal(e);
                                    return false;
                                }
                            });
                        }
                        break;
                    default:
                        this._textFormatter(args);
                }
            };
            cellFormatterHelper.prototype._defFormatFilterCell = function (args) {
                var grid = args.column.owner, css = grid.options.uiCSS;
                args.$container.addClass(css.widget + " " + css.stateDefault);
                if((args.column.dataIndex >= 0) && !args.column.isBand && args.column.showFilter) {
                    args.$container.html("<table cellPadding=\"0\" cellSpacing=\"0\" class=\"gcui-gcuigrid-filter " + css.cornerAll + "\"><tr><td style=\"width:100%\"><input type=\"text\" class=\"gcui-gcuigrid-filter-input\" style=\"width:100%\" /></td><td class=\"gcui-gcuigrid-filter-trigger " + css.cornerRight + " " + css.stateDefault + "\"><span class=\"" + css.icon + " " + css.iconArrowDown + "\"></span></td></tr></table>");
                } else {
                    args.$container.html("&nbsp;");
                }
            };
            return cellFormatterHelper;
        })();
        grid.cellFormatterHelper = cellFormatterHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var uiResizer = (function () {
            function uiResizer(gcuigrid) {
                this.MIN_WIDTH = 5;
                this._elements = [];
                this._gap = 10;
                this._step = 1;
                this._inProgress = false;
                this._hoveredField = null;
                this._startLocation = null;
                this._lastLocation = null;
                this._proxy = null;
                this._grid = gcuigrid;
                this._evntFormat = "{0}." + this._grid.widgetName + ".resizer";
            }
            uiResizer.prototype.addElement = function (c1basefield) {
                if(c1basefield && c1basefield.element) {
                    c1basefield.element.bind(this._eventKey("mousemove"), $.proxy(this._onMouseMove, this)).bind(this._eventKey("mousedown"), $.proxy(this._onMouseDown, this)).bind(this._eventKey("mouseout"), $.proxy(this._onMouseOut, this));
                    this._elements.push(c1basefield);
                }
            };
            uiResizer.prototype.dispose = function () {
                var self = this;
                $.each(this._elements, function (index, c1basefield) {
                    c1basefield.element.unbind(self._eventKey("mousemove"), self._onMouseMove).unbind(self._eventKey("mousedown"), self._onMouseDown).unbind(self._eventKey("mouseout"), self._onMouseOut);
                });
                this._detachDocEvents();
            };
            uiResizer.prototype.inProgress = function () {
                return this._inProgress;
            };
            uiResizer.prototype._onMouseMove = function (e) {
                if(!this._inProgress) {
                    var hoveredField = this._getFieldByPos({
                        x: e.pageX,
                        y: e.pageY
                    });
                    if(hoveredField && hoveredField._canSize() && this._grid._canInteract()) {
                        hoveredField.element.css("cursor", "e-resize");
                        this._hoveredField = hoveredField;
                        e.stopPropagation();
                    } else {
                        this._onMouseOut(e);
                    }
                }
            };
            uiResizer.prototype._onMouseOut = function (e) {
                if(!this._inProgress) {
                    if(this._hoveredField) {
                        this._hoveredField.element.css("cursor", "");
                        this._hoveredField = null;
                    }
                }
            };
            uiResizer.prototype._onMouseDown = function (e) {
                this._hoveredField = this._getFieldByPos({
                    x: e.pageX,
                    y: e.pageY
                });
                if(this._hoveredField && (this._hoveredField)._canSize() && this._grid._canInteract()) {
                    try  {
                        this._hoveredField.element.css("cursor", "");
                        this._docCursor = document.body.style.cursor;
                        document.body.style.cursor = "e-resize";
                        this._startLocation = this._lastLocation = gcui.grid.bounds(this._hoveredField.element);
                        this._proxy = $("<div class=\"gcui-gcuigrid-resizehandle " + this._grid.options.uiCSS.stateHightlight + "\">&nbsp;</div>");
                        var visibleAreaBounds = this._grid._view().getVisibleAreaBounds();
                        this._proxy.css({
                            "left": e.pageX,
                            "top": this._startLocation.top,
                            "height": visibleAreaBounds.height + visibleAreaBounds.top - this._startLocation.top
                        });
                        $(document.body).append(this._proxy);
                    }finally {
                        this._attachDocEvents();
                        this._inProgress = true;
                        e.stopPropagation();
                    }
                }
            };
            uiResizer.prototype._onDocumentMouseMove = function (e) {
                var deltaX = this._step * Math.round((e.pageX - this._lastLocation.left) / this._step);
                this._lastLocation = {
                    left: this._lastLocation.left + deltaX,
                    top: e.pageY,
                    width: undefined,
                    height: undefined
                };
                this._proxy.css("left", this._lastLocation.left);
            };
            uiResizer.prototype._onDocumentMouseUp = function (e) {
                try  {
                    document.body.style.cursor = this._docCursor;
                    this._proxy.remove();
                    if(this._startLocation !== this._lastLocation) {
                        this._grid._fieldResized(this._hoveredField, this._startLocation.width, Math.max(this._lastLocation.left - this._startLocation.left, this.MIN_WIDTH));
                    }
                }finally {
                    this._hoveredField = null;
                    this._proxy = null;
                    this._detachDocEvents();
                    this._inProgress = false;
                }
            };
            uiResizer.prototype._onSelectStart = function (e) {
                e.preventDefault();
            };
            uiResizer.prototype._attachDocEvents = function () {
                if(!this._inProgress) {
                    $(document).bind(this._eventKey("mousemove"), $.proxy(this._onDocumentMouseMove, this)).bind(this._eventKey("mouseup"), $.proxy(this._onDocumentMouseUp, this));
                    if($.fn.disableSelection) {
                        $(document.body).disableSelection();
                    }
                    if("onselectstart" in document) {
                        $(document.body).bind("selectstart", this._onSelectStart);
                    }
                }
            };
            uiResizer.prototype._detachDocEvents = function () {
                if(this._inProgress) {
                    $(document).unbind(this._eventKey("mousemove"), this._onDocumentMouseMove).unbind(this._eventKey("mouseup"), this._onDocumentMouseUp);
                    if($.fn.enableSelection) {
                        $(document.body).enableSelection();
                    }
                    if("onselectstart" in document) {
                        $(document.body).unbind("selectstart", this._onSelectStart);
                    }
                }
            };
            uiResizer.prototype._getFieldByPos = function (mouse) {
                var i, len, c1basefield, bounds, res;
                for(i = 0 , len = this._elements.length; i < len; i++) {
                    c1basefield = this._elements[i];
                    bounds = gcui.grid.bounds(c1basefield.element);
                    res = gcui.grid.isOver(mouse.y, mouse.x, bounds.top, bounds.left + bounds.width - this._gap, bounds.height, this._gap);
                    if(res) {
                        return c1basefield;
                    }
                }
                return null;
            };
            uiResizer.prototype._eventKey = function (eventType) {
                return gcui.grid.stringFormat(this._evntFormat, eventType);
            };
            return uiResizer;
        })();
        grid.uiResizer = uiResizer;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var uiDragndrop = (function () {
            function uiDragndrop(gcuigrid) {
                this._scope_guid = "scope_" + gcui.grid.getUID();
                this._dragEnd = false;
                this._grid = gcuigrid;
                this._css = this._grid.options.uiCSS;
                this._wrapHtml = "<div class=\"" + this._css.widget + " gcui-gcuigrid " + this._css.content + " " + this._css.cornerAll + "\">" + "<table class=\"gcui-gcuigrid-root gcui-gcuigrid-table\">" + "<tr class=\"gcui-gcuigrid-headerrow\">" + "</tr>" + "</table>" + "</div>";
            }
            uiDragndrop.prototype.attachGroupArea = function (element) {
                var draggedField, self = this;
                if(!($.ui).droppable || !($.ui).draggable) {
                    return;
                }
                element.droppable({
                    scope: this._scope_guid,
                    tolerance: "pointer",
                    greedy: true,
                    accept: function (draggable) {
                        if(self._grid.options.allowColMoving) {
                            draggedField = self._getFieldInstance(draggable);
                            if (draggedField) {
                                if ((draggedField instanceof $.gcui.c1groupedfield) && (draggedField.options.groupedIndex === self._grid._groupedColumns().length - 1)) {
                                    return false;
                                }
                                return !draggedField.options.isBand && (draggedField.options.groupedIndex === undefined || (draggedField instanceof $.gcui.c1groupedfield));
                            }
                        }
                        return false;
                    },
                    drop: function (e, ui) {
                        if (!self._isInElement(e, ui.draggable) && (draggedField = self._getFieldInstance(ui.draggable))) {
                            self._dragEnd = true;
                        }
                    },
                    over: function (e, ui) {
                        var cnt = self._grid._field("groupedWidgets").length;
                        self._dropTargetRedirected = (cnt > 0);
                        self._droppableField = (cnt > 0) ? self._grid._field("groupedWidgets")[cnt - 1] : element;
                        element.data("thisDroppableField", self._droppableField);
                    },
                    out: function (e, ui) {
                        if(self._droppableField === element.data("thisDroppableField")) {
                            self._droppableField = null;
                        }
                    }
                });
            };
            uiDragndrop.prototype.attach = function (field) {
                var element, draggedField, self = this;
                if(!($.ui).droppable || !($.ui).draggable) {
                    return;
                }
                if(!field || !(element = field.element)) {
                    return;
                }
                element.draggable({
                    helper: function (e) {
                        if(field instanceof $.gcui.c1groupedfield) {
                            return element.clone().addClass("gcui-gcuigrid-dnd-helper");
                        } else {
                            return element.clone().wrap(self._wrapHtml).width(element.width()).height(element.height()).closest(".gcui-gcuigrid").addClass("gcui-gcuigrid-dnd-helper");
                        }
                    },
                    appendTo: "body",
                    scope: self._scope_guid,
                    drag: function (e, ui) {
                        self._hideArrows();
                        if(self._droppableField && !self._isInElement(e, element)) {
                            var $arrowsTarget = self._droppableField.element;
                            if(!$arrowsTarget) {
                                $arrowsTarget = (self._droppableField);
                            }
                            self._showArrows($arrowsTarget, self._getPosition(field, self._droppableField, e, ui));
                        }
                    },
                    start: function (e, ui) {
                        if(self._grid._canInteract() && self._grid.options.allowColMoving && !self._grid._field("resizer").inProgress()) {
                            var column = field.options, travIdx = field.options.travIdx, dragInGroup = (field instanceof $.gcui.c1groupedfield), dragSource = dragInGroup ? "groupArea" : "columns";
                            if(dragInGroup) {
                                column = gcui.grid.search(self._grid.columns(), function (test) {
                                    return test.options.travIdx === travIdx;
                                });
                                column = (!column.found) ? gcui.grid.getColumnByTravIdx(self._grid.options.columns, travIdx).found : column.found.options;
                            }
                            if(field._canDrag() && self._grid._trigger("columnDragging", null, {
                                drag: column,
                                dragSource: dragSource
                            })) {
                                self._grid._trigger("columnDragged", null, {
                                    drag: column,
                                    dragSource: dragSource
                                });
                                return true;
                            }
                        }
                        return false;
                    },
                    stop: function (e, ui) {
                        self._hideArrows();
                        try  {
                            if(self._dragEnd) {
                                if(!self._droppableField.element) {
                                    self._grid._handleDragnDrop(field.options.travIdx, -1, "left", field instanceof $.gcui.c1groupedfield, true);
                                } else {
                                    self._grid._handleDragnDrop(field.options.travIdx, self._droppableField.options.travIdx, self._getPosition(field, self._droppableField, e, ui), field instanceof $.gcui.c1groupedfield, self._droppableField instanceof $.gcui.c1groupedfield);
                                }
                            }
                        }finally {
                            self._droppableField = null;
                            self._dragEnd = false;
                        }
                    }
                }).droppable({
                    hoverClass: self._css.stateHover,
                    scope: self._scope_guid,
                    tolerance: "pointer",
                    greedy: true,
                    accept: function (draggable) {
                        if(self._grid.options.allowColMoving) {
                            if(element[0] !== draggable[0]) {
                                draggedField = self._getFieldInstance(draggable);
                                if(draggedField) {
                                    return draggedField._canDropTo(field);
                                }
                            }
                        }
                        return false;
                    },
                    drop: function (e, ui) {
                        if(draggedField = self._getFieldInstance(ui.draggable)) {
                            self._dragEnd = true;
                        }
                    },
                    over: function (e, ui) {
                        self._dropTargetRedirected = false;
                        self._droppableField = field;
                        element.data("thisDroppableField", self._droppableField);
                    },
                    out: function (e, ui) {
                        if(self._droppableField === field.element.data("thisDroppableField")) {
                            self._droppableField = null;
                        }
                    }
                });
            };
            uiDragndrop.prototype.detach = function (field) {
                var element;
                if(field && (element = field.element)) {
                    if(element.data("ui-draggable")) {
                        element.draggable("destroy");
                    }
                    if(element.data("ui-droppable")) {
                        element.droppable("destroy");
                    }
                }
            };
            uiDragndrop.prototype.dispose = function () {
                if(this._$topArrow) {
                    this._$topArrow.remove();
                    this._$topArrow = null;
                }
                if(this._$bottomArrow) {
                    this._$bottomArrow.remove();
                    this._$bottomArrow = null;
                }
            };
            uiDragndrop.prototype._getFieldInstance = function (draggable) {
                var widgetName = gcui.grid.widgetName(draggable);
                if(!widgetName) {
                    throw "widgetName is undedined";
                }
                return draggable.data(widgetName);
            };
            uiDragndrop.prototype._showArrows = function (element, position) {
                this._topArrow().show().position({
                    my: "center",
                    at: position + " top",
                    of: element,
                    collision: "none"
                });
                this._bottomArrow().show().position({
                    my: "center",
                    at: position + " bottom",
                    of: element,
                    collision: "none"
                });
            };
            uiDragndrop.prototype._hideArrows = function () {
                this._topArrow().hide();
                this._bottomArrow().hide();
            };
            uiDragndrop.prototype._topArrow = function () {
                if(!this._$topArrow) {
                    this._$topArrow = $("<div />").addClass("gcui-gcuigrid-dnd-arrow-top").append($("<span />").addClass(this._css.icon + " ui-icon-arrowthick-1-s")).hide().appendTo(document.body);
                }
                return this._$topArrow;
            };
            uiDragndrop.prototype._bottomArrow = function () {
                if(!this._$bottomArrow) {
                    this._$bottomArrow = $("<div />").addClass("gcui-gcuigrid-dnd-arrow-bottom").append($("<span />").addClass(this._css.icon + " ui-icon-arrowthick-1-n")).hide().appendTo(document.body);
                }
                return this._$bottomArrow;
            };
            uiDragndrop.prototype._isInElement = function (e, element) {
                var bounds = gcui.grid.bounds(element, false);
                return ((e.pageX > bounds.left && e.pageX < bounds.left + bounds.width) && (e.pageY > bounds.top && e.pageY < bounds.top + bounds.height));
            };
            uiDragndrop.prototype._getPosition = function (drag, drop, e, dragui) {
                if(!drop.element) {
                    return "left";
                }
                if(this._dropTargetRedirected) {
                    return "right";
                }
                var bounds = gcui.grid.bounds(drop.element, false), sixth = bounds.width / 6, centerX = bounds.left + (bounds.width / 2), result = "right", distance;
                if(e.pageX < centerX) {
                    result = "left";
                }
                if(drop instanceof $.gcui.c1groupedfield) {
                    if(drag instanceof $.gcui.c1groupedfield) {
                        distance = drop.options.groupedIndex - drag.options.groupedIndex;
                        if(Math.abs(distance) === 1) {
                            result = (distance < 0) ? "left" : "right";
                        }
                    }
                    return result;
                }
                distance = drop.options.linearIdx - drag.options.linearIdx;
                if(drop.options.isBand && (drag.options.parentIdx !== drop.options.travIdx) && (Math.abs(e.pageX - centerX) < sixth)) {
                    return "center";
                }
                if(drag.options.parentIdx === drop.options.parentIdx && Math.abs(distance) === 1) {
                    result = (distance < 0) ? "left" : "right";
                }
                return result;
            };
            return uiDragndrop;
        })();
        grid.uiDragndrop = uiDragndrop;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var cellStyleFormatterHelper = (function () {
            function cellStyleFormatterHelper(gcuigrid) {
                if(!gcuigrid) {
                    throw "invalid arguments";
                }
                this._grid = gcuigrid;
            }
            cellStyleFormatterHelper.prototype.format = function ($cell, cellIndex, column, rowInfo, state, cellAttr, cellStyle) {
                var $rs = gcui.grid.renderState, $rt = gcui.grid.rowType, rowType = rowInfo.type, args, groupRowCellInfo = null;
                if(cellIndex === 0 && this._grid._showRowHeader()) {
                    column = null;
                }
                if(rowType === $rt.groupHeader || rowType === $rt.groupFooter) {
                    column = null;
                    if(cellAttr && (groupRowCellInfo = cellAttr.groupInfo)) {
                        column = this._grid._field("leaves")[groupRowCellInfo.leafIndex];
                        delete cellAttr.groupInfo;
                    }
                }
                args = {
                    $cell: $cell,
                    state: state,
                    row: rowInfo,
                    column: column,
                    _cellIndex: cellIndex,
                    _purpose: groupRowCellInfo ? groupRowCellInfo.purpose : undefined
                };
                if(state === $rs.rendering) {
                    this._renderingStateFormatter(args, cellAttr, cellStyle);
                } else {
                    this._currentStateFormatter(args, state & $rs.current);
                    this._selectedStateFormatter(args, state & $rs.selected);
                }
                if($.isFunction(this._grid.options.cellStyleFormatter)) {
                    this._grid.options.cellStyleFormatter(args);
                }
            };
            cellStyleFormatterHelper.prototype._renderingStateFormatter = function (args, cellAttr, cellStyles) {
                var $rt = gcui.grid.rowType, key, value, leaf = args.column, rowType = args.row.type;
                switch(rowType) {
                    case $rt.header:
                        args.$cell.addClass("gcuigridth");
                        break;
                    default:
                        args.$cell.addClass("gcuigridtd");
                }
                if((rowType & $rt.data) && leaf && leaf.textAlignment) {
                    switch(leaf.textAlignment.toLowerCase()) {
                        case "left":
                            args.$cell.addClass("gcui-align-left");
                            break;
                        case "right":
                            args.$cell.addClass("gcui-align-right");
                            break;
                        case "center":
                            args.$cell.addClass("gcui-align-center");
                            break;
                    }
                }
                if(cellAttr) {
                    for(key in cellAttr) {
                        if(cellAttr.hasOwnProperty(key)) {
                            value = cellAttr[key];
                            if((key === "colSpan" || key === "rowSpan") && !(value > 1)) {
                                continue;
                            }
                            if(key === "class") {
                                args.$cell.addClass(value);
                            } else {
                                args.$cell.attr(key, value);
                            }
                        }
                    }
                }
                if(cellStyles) {
                    for(key in cellStyles) {
                        if(cellStyles.hasOwnProperty(key)) {
                            if(key === "paddingLeft") {
                                args.$cell.children(".gcui-gcuigrid-innercell").css(key, cellStyles[key]);
                                continue;
                            }
                            args.$cell.css(key, cellStyles[key]);
                        }
                    }
                }
                if(args._cellIndex === 0 && this._grid._showRowHeader()) {
                    args.$cell.attr({
                        "role": "rowheader",
                        "scope": "row"
                    }).addClass(this._grid.options.uiCSS.stateDefault + " " + this._grid.options.uiCSS.content + " gcui-gcuigrid-rowheader");
                } else {
                    switch(rowType) {
                        case ($rt.header):
                            args.$cell.attr({
                                "role": "columnheader",
                                "scope": "col"
                            });
                            break;
                        case ($rt.footer):
                            args.$cell.attr({
                                "role": "columnfooter",
                                "scope": "col"
                            });
                            break;
                        default:
                            args.$cell.attr("role", "gridcell");
                    }
                }
                if(rowType & $rt.data) {
                    if(args._cellIndex >= 0 && leaf) {
                        args.$cell.attr("headers", (window).escape(leaf.headerText));
                        if(leaf.readOnly) {
                            args.$cell.attr("aria-readonly", true);
                        }
                        if(leaf.dataIndex >= 0) {
                            args.$cell.addClass("gcui-data-type-" + gcui.grid.getDataType(leaf));
                        }
                    }
                }
                if(rowType === $rt.groupHeader || rowType === $rt.groupFooter) {
                    if(leaf && args._purpose === gcui.grid.groupRowCellPurpose.aggregateCell) {
                        args.$cell.addClass("gcui-data-type-" + gcui.grid.getDataType(leaf));
                    }
                }
            };
            cellStyleFormatterHelper.prototype._currentStateFormatter = function (args, add) {
                var $rt = gcui.grid.rowType;
                if(add) {
                    args.$cell.addClass(this._grid.options.uiCSS.stateActive);
                    if(args.row.type === $rt.header) {
                        args.$cell.addClass("gcui-gcuigrid-current-headercell");
                    } else {
                        args.$cell.addClass("gcui-gcuigrid-current-cell");
                    }
                } else {
                    args.$cell.removeClass(this._grid.options.uiCSS.stateActive);
                    if(args.row.type === $rt.header) {
                        args.$cell.removeClass("gcui-gcuigrid-current-headercell");
                    } else {
                        args.$cell.removeClass("gcui-gcuigrid-current-cell");
                    }
                }
            };
            cellStyleFormatterHelper.prototype._hoveredStateFormatter = function (args, add) {
                if(add) {
                } else {
                }
            };
            cellStyleFormatterHelper.prototype._selectedStateFormatter = function (args, add) {
                if(add) {
                    args.$cell.addClass(this._grid.options.uiCSS.stateHightlight).attr("aria-selected", "true");
                } else {
                    args.$cell.removeClass(this._grid.options.uiCSS.stateHightlight).removeAttr("aria-selected");
                }
            };
            return cellStyleFormatterHelper;
        })();
        grid.cellStyleFormatterHelper = cellStyleFormatterHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var rowStyleFormatterHelper = (function () {
            function rowStyleFormatterHelper(gcuigrid) {
                if(!gcuigrid) {
                    throw "invalid arguments";
                }
                this._grid = gcuigrid;
            }
            rowStyleFormatterHelper.prototype.format = function (rowInfo, rowAttr, rowStyle) {
                var $rs = gcui.grid.renderState, $rt = gcui.grid.rowType, state = rowInfo.state, args = rowInfo;
                if(state === $rs.rendering) {
                    this._renderingStateFormatter(args, rowAttr, rowStyle);
                } else {
                    this._currentStateFormatter(args, (state & $rs.current) !== 0);
                    this._hoveredStateFormatter(args, (state & $rs.hovered) !== 0);
                    this._selectedStateFormatter(args, (state & $rs.selected) !== 0);
                }
                if($.isFunction(this._grid.options.rowStyleFormatter)) {
                    this._grid.options.rowStyleFormatter(args);
                }
            };
            rowStyleFormatterHelper.prototype._renderingStateFormatter = function (args, rowAttr, rowStyle) {
                var className = "gcui-gcuigrid-row " + this._grid.options.uiCSS.content, contentClass = "gcui-gcuigrid-row " + this._grid.options.uiCSS.content, $rt = gcui.grid.rowType, key;
                args.$rows.attr("role", "row");
                if(rowAttr) {
                    for(key in rowAttr) {
                        if(rowAttr.hasOwnProperty(key)) {
                            if(key === "class") {
                                args.$rows.addClass(rowAttr[key]);
                            } else {
                                args.$rows.attr(key, rowAttr[key]);
                                if(args.$rows[0].style.height) {
                                    args.$rows.each(function () {
                                        $.data(this, "customHeight", this.style.height);
                                        return true;
                                    });
                                }
                            }
                        }
                    }
                }
                if(rowStyle) {
                    for(key in rowStyle) {
                        if(rowStyle.hasOwnProperty(key)) {
                            args.$rows.css(key, rowStyle[key]);
                        }
                    }
                }
                switch(args.type & ~$rt.dataAlt) {
                    case ($rt.header):
                        className = "gcui-gcuigrid-headerrow";
                        break;
                    case ($rt.data):
                        className = contentClass + " gcui-gcuigrid-datarow";
                        if(args.type & $rt.dataAlt) {
                            className += " gcui-gcuigrid-alternatingrow";
                        }
                        break;
                    case ($rt.emptyDataRow):
                        className = contentClass + " gcui-gcuigrid-emptydatarow";
                        break;
                    case ($rt.filter):
                        className = "gcui-gcuigrid-filterrow";
                        break;
                    case ($rt.groupHeader):
                        className = contentClass + " gcui-gcuigrid-groupheaderrow";
                        break;
                    case ($rt.groupFooter):
                        className = contentClass + " gcui-gcuigrid-groupfooterrow";
                        break;
                    case ($rt.footer):
                        className = "gcui-gcuigrid-footerrow " + this._grid.options.uiCSS.stateHightlight;
                        break;
                    default:
                        throw gcui.grid.stringFormat("unknown rowType: {0}", args.type);
                }
                args.$rows.addClass(className);
            };
            rowStyleFormatterHelper.prototype._currentStateFormatter = function (args, flag) {
                if(this._grid._showRowHeader()) {
                    if(flag) {
                        $((args.$rows[0]).cells[0]).addClass(this._grid.options.uiCSS.stateActive + " gcui-gcuigrid-current-rowheadercell");
                    } else {
                        $((args.$rows[0]).cells[0]).removeClass(this._grid.options.uiCSS.stateActive + " gcui-gcuigrid-current-rowheadercell");
                    }
                }
            };
            rowStyleFormatterHelper.prototype._hoveredStateFormatter = function (args, flag) {
                if(flag) {
                    args.$rows.addClass(this._grid.options.uiCSS.stateHover);
                } else {
                    args.$rows.removeClass(this._grid.options.uiCSS.stateHover);
                }
            };
            rowStyleFormatterHelper.prototype._selectedStateFormatter = function (args, flag) {
                if(flag) {
                } else {
                }
            };
            return rowStyleFormatterHelper;
        })();
        grid.rowStyleFormatterHelper = rowStyleFormatterHelper;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var tally = (function () {
            function tally() {
                this._sum = 0;
                this._sum2 = 0;
                this._cntNumbers = 0;
                this._cntStrings = 0;
                this._cntDates = 0;
                this._max = 0;
                this._min = 0;
                this._minDate = 0;
                this._maxDate = 0;
            }
            tally.prototype.add = function (value) {
                if(value === null || value === "") {
                    return;
                }
                var foo, typeOf = (value instanceof Date) ? "datetime" : typeof (value);
                foo = value.toString();
                if(this._cntStrings++ === 0) {
                    this._minString = this._maxString = foo;
                }
                if(foo < this._minString) {
                    this._minString = foo;
                }
                if(foo > this._maxString) {
                    this._maxString = foo;
                }
                if(typeOf === "number") {
                    if(this._cntNumbers++ === 0) {
                        this._min = this._max = value;
                    }
                    this._sum += value;
                    this._sum2 += value * value;
                    if(value < this._min) {
                        this._min = value;
                    }
                    if(value > this._max) {
                        this._max = value;
                    }
                } else {
                    if(typeOf === "datetime") {
                        foo = value.getTime();
                        if(this._cntDates++ === 0) {
                            this._minDate = this._maxDate = foo;
                        }
                        if(foo < this._minDate) {
                            this._minDate = foo;
                        }
                        if(foo > this._maxDate) {
                            this._maxDate = foo;
                        }
                    }
                }
            };
            tally.prototype.getValueString = function (column) {
                var gcuigrid = column.owner;
                if(this._cntNumbers && (column.dataType === "number" || column.dataType === "currency")) {
                    var value = this._getValue(column.aggregate);
                    return gcuigrid.toStr(column, value);
                }
                if(this._cntDates && (column.dataType === "datetime")) {
                    switch(column.aggregate) {
                        case "max":
                            return gcuigrid.toStr(column, new Date(this._maxDate));
                        case "min":
                            return gcuigrid.toStr(column, new Date(this._minDate));
                        case "count":
                            return this._cntStrings + "";
                    }
                }
                if(this._cntStrings) {
                    switch(column.aggregate) {
                        case "max":
                            return this._maxString;
                        case "min":
                            return this._minString;
                        case "count":
                            return this._cntStrings + "";
                    }
                }
                return "";
            };
            tally.prototype._getValue = function (aggregate) {
                switch(aggregate) {
                    case "average":
                        return (this._cntNumbers === 0) ? 0 : this._sum / this._cntNumbers;
                    case "count":
                        return this._cntStrings;
                    case "max":
                        return this._max;
                    case "min":
                        return this._min;
                    case "sum":
                        return this._sum;
                    case "std":
                        if(this._cntNumbers <= 1) {
                            return 0;
                        }
                        return Math.sqrt(this._getValue("var"));
                    case "stdPop":
                        if(this._cntNumbers <= 1) {
                            return 0;
                        }
                        return Math.sqrt(this._getValue("varPop"));
                    case "var":
                        if(this._cntNumbers <= 1) {
                            return 0;
                        }
                        return this._getValue("varPop") * this._cntNumbers / (this._cntNumbers - 1);
                    case "vapPop":
                        if(this._cntNumbers <= 1) {
                            return 0;
                        }
                        var tmp = this._sum / this._cntNumbers;
                        return this._sum2 / this._cntNumbers - tmp * tmp;
                }
                return 0;
            };
            return tally;
        })();
        grid.tally = tally;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var uiFrozener = (function () {
            function uiFrozener(gcuigrid) {
                this._docEventsUID = "gcuigridfrozener" + gcui.grid.getUID();
                this._docEventsAttached = false;
                this._newStaticIndex = -1;
                this._staticColumnIndex = -1;
                this._staticRowIndex = -1;
                this._staticOffsetH = 0;
                this._staticOffsetV = 0;
                this._inProgress = false;
                this._grid = gcuigrid;
                this.refresh();
            }
            uiFrozener.prototype.inProgress = function () {
                return this._inProgress;
            };
            uiFrozener.prototype.refresh = function () {
                this.dispose();
                this._$outerDiv = this._grid.outerDiv.find(".gcui-gcuigrid-fixedview");
                this._superPanel = (this._grid._view())._getSuperPanel();
                this._staticOffsetH = this._grid._getStaticOffsetIndex(false);
                this._staticOffsetV = this._grid._getStaticOffsetIndex(true);
                this._staticColumnIndex = this._grid._getStaticIndex(false);
                this._staticRowIndex = this._grid._getStaticIndex(true);
                this._visibleBounds = this._grid._view().getVisibleAreaBounds();
                var allFixedAreaBounds = gcui.grid.bounds(this._$outerDiv.find(".gcui-gcuigrid-split-area-nw")), containerBounds = gcui.grid.bounds(this._$outerDiv);
                if(this._grid.options.staticColumnsAlignment !== "right" || this._staticColumnIndex >= 0) {
                    this._createVBar(this._visibleBounds, allFixedAreaBounds, containerBounds);
                }
                if(!this._grid._serverSideVirtualScrolling()) {
                    this._createHBar(this._visibleBounds, allFixedAreaBounds, containerBounds);
                }
            };
            uiFrozener.prototype.dispose = function () {
                if(this._$hBar) {
                    this._$hBar.remove();
                    this._$hBar = null;
                }
                if(this._$vBar) {
                    this._$vBar.remove();
                    this._$vBar = null;
                }
                if(this._$proxy) {
                    this._$proxy.remove();
                    this._$proxy = null;
                }
                this._$outerDiv = null;
                this._superPanel = null;
                this._detachDocEvents();
            };
            uiFrozener.prototype._createVBar = function (visibleBounds, allFixedAreaBounds, containerBounds) {
                var lAlign = (this._grid.options.staticColumnsAlignment !== "right"), leftPos = lAlign ? allFixedAreaBounds.width + allFixedAreaBounds.left : allFixedAreaBounds.left - 2, self = this;
                if(leftPos <= visibleBounds.left + visibleBounds.width) {
                    this._$vBar = $("<div><div></div></div>").addClass("gcui-grid-freezing-handle-v").addClass(lAlign ? "" : "not-allowed").css({
                        left: leftPos - containerBounds.left,
                        top: allFixedAreaBounds.top - containerBounds.top,
                        height: visibleBounds.height + visibleBounds.top - allFixedAreaBounds.top
                    }).bind("mousedown", function (e) {
                        e.data = true;
                        self._onBarMouseDown.apply(self, arguments);
                    }).appendTo(this._$outerDiv);
                    this._$vBar.find("div").addClass(this._grid.options.uiCSS.header).css({
                        width: 0,
                        height: "100%"
                    });
                }
            };
            uiFrozener.prototype._createHBar = function (visibleBounds, allFixedAreaBounds, containerBounds) {
                var topPos = allFixedAreaBounds.top + allFixedAreaBounds.height, lAlign = (this._grid.options.staticColumnsAlignment !== "right"), self = this;
                if(topPos <= visibleBounds.top + visibleBounds.height) {
                    this._$hBar = $("<div><div></div></div>").addClass("gcui-grid-freezing-handle-h").css({
                        left: lAlign ? allFixedAreaBounds.left - containerBounds.left : 0,
                        top: topPos - containerBounds.top,
                        width: lAlign ? visibleBounds.width + visibleBounds.left - allFixedAreaBounds.left : visibleBounds.width
                    }).bind("mousedown", function (e) {
                        e.data = false;
                        self._onBarMouseDown.apply(self, arguments);
                    }).appendTo(this._$outerDiv);
                    this._$hBar.find("div").addClass(this._grid.options.uiCSS.header).css({
                        width: "100%",
                        height: 0
                    });
                }
            };
            uiFrozener.prototype._onBarMouseDown = function (e) {
                if(this._grid.options.disabled || (this._grid.options.staticColumnsAlignment === "right" && e.data)) {
                    return false;
                }
                this._visibleBounds = this._grid._view().getVisibleAreaBounds();
                this._newStaticIndex = e.data ? this._staticColumnIndex : this._staticRowIndex;
                this._$proxy = $("<div class=\"gcui-gcuigrid-resizehandle " + this._grid.options.uiCSS.header + "\"></div>").appendTo(document.body);
                this._attachDocEvents(e.data);
                this._inProgress = true;
                e.stopPropagation();
            };
            uiFrozener.prototype._onDocumentMouseMove = function (e) {
                if(e.data && this._superPanel.options.hScroller.scrollValue) {
                    (this._superPanel).hScrollTo(0);
                } else if(!e.data && this._superPanel.options.vScroller.scrollValue) {
                    (this._superPanel).vScrollTo(0);
                }
                this._showPosition(e);
            };
            uiFrozener.prototype._onDocumentMouseUp = function (e) {
                try  {
                    if(this._$proxy) {
                        this._$proxy.remove();
                    }
                    this._detachDocEvents();
                    if(e.data) {
                        if(this._newStaticIndex !== this._staticColumnIndex) {
                            this._grid.option("staticColumnIndex", this._newStaticIndex);
                        }
                    } else {
                        if(this._newStaticIndex !== this._staticRowIndex) {
                            this._grid.option("staticRowIndex", this._newStaticIndex);
                        }
                    }
                }finally {
                    this._$proxy = null;
                    this._inProgress = false;
                }
            };
            uiFrozener.prototype._attachDocEvents = function (verticalBarTouched) {
                if(!this._docEventsAttached) {
                    try  {
                        if($.fn.disableSelection) {
                            $(document.body).disableSelection();
                        }
                        this._grid._view().toggleDOMSelection(false);
                        $(document).bind(this._docEventKey("mousemove"), verticalBarTouched, $.proxy(this._onDocumentMouseMove, this)).bind(this._docEventKey("mouseup"), verticalBarTouched, $.proxy(this._onDocumentMouseUp, this));
                    }finally {
                        this._docEventsAttached = true;
                    }
                }
            };
            uiFrozener.prototype._detachDocEvents = function () {
                if(this._docEventsAttached) {
                    try  {
                        if($.fn.enableSelection) {
                            $(document.body).enableSelection();
                        }
                        this._grid._view().toggleDOMSelection(true);
                        $(document).unbind("." + this._docEventsUID);
                    }finally {
                        this._docEventsAttached = false;
                    }
                }
            };
            uiFrozener.prototype._docEventKey = function (eventName) {
                return gcui.grid.stringFormat("{0}.{1}", eventName, this._docEventsUID);
            };
            uiFrozener.prototype._showPosition = function (e) {
                var element, elementBounds, centerXOrY, currentIdx, prevIdx, leftOrTop, position, barBounds, lAlign = (this._grid.options.staticColumnsAlignment !== "right");
                if(e.data) {
                    barBounds = gcui.grid.bounds(this._$vBar);
                    if(Math.abs(e.pageX - (barBounds.left + barBounds.width / 2)) < barBounds.width) {
                        this._$proxy.hide();
                        return;
                    }
                    if((element = this._getFieldByPos({ x: e.pageX, y: e.pageY }))) {
                        elementBounds = gcui.grid.bounds(element.element);
                        centerXOrY = elementBounds.left + elementBounds.width / 2;
                        currentIdx = element.options.visLeavesIdx - this._staticOffsetV;
                        prevIdx = Math.max(currentIdx - 1, -1);
                        leftOrTop = e.pageX < centerXOrY ? (prevIdx !== this._staticColumnIndex) : (currentIdx === this._staticColumnIndex);
                        position = leftOrTop ? elementBounds.left : elementBounds.left + elementBounds.width;
                        if(!gcui.grid.isOverAxis(position, this._visibleBounds.left - 1, this._visibleBounds.width + 2)) {
                            return;
                        }
                        this._newStaticIndex = leftOrTop ? prevIdx : currentIdx;
                        this._$proxy.show().css({
                            left: position,
                            top: elementBounds.top,
                            width: 3,
                            height: this._visibleBounds.height + this._visibleBounds.top - elementBounds.top
                        });
                    }
                } else {
                    barBounds = gcui.grid.bounds(this._$hBar);
                    if(Math.abs(e.pageY - (barBounds.top + barBounds.height / 2)) < barBounds.height) {
                        this._$proxy.hide();
                        return;
                    }
                    if((element = this._getRowByPos({ x: e.pageX, y: e.pageY }))) {
                        elementBounds = gcui.grid.bounds(element);
                        centerXOrY = elementBounds.top + elementBounds.height / 2;
                        currentIdx = this._grid._view().getAbsoluteRowIndex(element) - this._staticOffsetH;
                        prevIdx = Math.max(currentIdx - 1, -1);
                        leftOrTop = e.pageY < centerXOrY ? (prevIdx !== this._staticRowIndex) : (currentIdx === this._staticRowIndex);
                        position = leftOrTop ? elementBounds.top : elementBounds.top + elementBounds.height;
                        if(!gcui.grid.isOverAxis(position, this._visibleBounds.top - 1, this._visibleBounds.height + 2)) {
                            return;
                        }
                        this._newStaticIndex = leftOrTop ? prevIdx : currentIdx;
                        this._$proxy.show().css({
                            left: lAlign ? elementBounds.left : this._visibleBounds.left,
                            top: position,
                            width: lAlign ? this._visibleBounds.width + this._visibleBounds.left - elementBounds.left : this._visibleBounds.width,
                            height: 3
                        });
                    }
                }
            };
            uiFrozener.prototype._getFieldByPos = function (pos) {
                var columns = this._grid.columns(), i, len, colWidget, o, bounds;
                for(i = 0 , len = columns.length; i < len; i++) {
                    colWidget = columns[i];
                    o = colWidget.options;
                    if(o.isLeaf) {
                        bounds = gcui.grid.bounds(colWidget.element);
                        if(gcui.grid.isOverAxis(pos.x, bounds.left, bounds.width)) {
                            return colWidget;
                        }
                    }
                }
                return null;
            };
            uiFrozener.prototype._getRowByPos = function (pos) {
                var rows = this._grid._rows(), i, len, row, bounds;
                for(i = 0 , len = rows.length(); i < len; i++) {
                    row = rows.item(i)[0];
                    bounds = gcui.grid.bounds($(row));
                    if(gcui.grid.isOverAxis(pos.y, bounds.top, bounds.height)) {
                        return row;
                    }
                }
                return null;
            };
            return uiFrozener;
        })();
        grid.uiFrozener = uiFrozener;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var columnsGenerator = (function () {
            function columnsGenerator() { }
            columnsGenerator.generate = function generate(mode, fieldsInfo, columns) {
                switch(mode) {
                    case "append":
                        columnsGenerator._processAppendMode(fieldsInfo, columns);
                        break;
                    case "merge":
                        columnsGenerator._processMergeMode(fieldsInfo, columns);
                        break;
                    case "none":
                        break;
                    default:
                        throw gcui.grid.stringFormat("Unsupported value: \"{0}\"", mode);
                }
            };
            columnsGenerator._processAppendMode = function _processAppendMode(fieldsInfo, columns) {
                var autoColumns = {};
                gcui.grid.traverse(columns, function (column) {
                    if(column.dynamic && gcui.grid.validDataKey(column.dataKey)) {
                        autoColumns[column.dataKey] = true;
                    }
                });
                $.each(fieldsInfo, function (key, fieldInfo) {
                    if(("name" in fieldInfo) && !autoColumns[fieldInfo.name]) {
                        var leaf = columnsGenerator._createAutoField(fieldInfo);
                        columns.push(leaf);
                    }
                });
            };
            columnsGenerator._processMergeMode = function _processMergeMode(fieldsInfo, columns) {
                var columnsHasNoDataKey = [], i;
                gcui.grid.traverse(columns, function (column) {
                    if(column.isLeaf && !column.isBand) {
                        var dataKey = column.dataKey;
                        if(gcui.grid.validDataKey(dataKey)) {
                            if(fieldsInfo[dataKey] !== undefined) {
                                delete fieldsInfo[dataKey];
                            }
                        } else {
                            if(dataKey !== null) {
                                columnsHasNoDataKey.push(column);
                            }
                        }
                    }
                });
                if(columnsHasNoDataKey.length) {
                    i = 0;
                    $.each(fieldsInfo, function (key, info) {
                        var leaf = columnsHasNoDataKey[i++];
                        if(leaf) {
                            leaf.dataKey = info.name;
                            delete fieldsInfo[key];
                        }
                    });
                }
                $.each(fieldsInfo, function (key, info) {
                    var leaf = columnsGenerator._createAutoField(info);
                    columns.push(leaf);
                });
            };
            columnsGenerator._createAutoField = function _createAutoField(fieldInfo) {
                return gcui.grid.createDynamicField({
                    dataKey: fieldInfo.name
                });
            };
            return columnsGenerator;
        })();
        grid.columnsGenerator = columnsGenerator;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui|| (gcui = {}));

(function (gcui) {
    (function (grid) {
        "use strict";
        var $ = jQuery;
        var uiVirtualScroller = (function () {
            function uiVirtualScroller(gcuigrid, $content, fixedAreaHeight) {
                this._timer = 0;
                this._timeout = 50;
                this._ignoreScrollEvents = false;
                this._grid = gcuigrid;
                this._fixedAreaHeight = fixedAreaHeight;
                this._view = this._grid._view();
                this._N = this._grid._totalRowsCount();
                var rowH = 20, height = this._grid.outerDiv.height() + this._N * rowH;
                $content.height(height - fixedAreaHeight - (this._N > 0 ? rowH : 0));
                this._view._splitAreas.sw.height(height);
            }
            uiVirtualScroller.prototype.scrollTo = function (rowIndex, callback) {
                this._completeListener = callback;
                (this._panelInst).vScrollTo(rowIndex * (this._panelInst).options.vScroller.scrollSmallChange, true);
            };
            uiVirtualScroller.prototype.attach = function ($scroller) {
                this._$scroller = $scroller;
                this._panelInst = $scroller.data("gcui-superpanel");
                var tmp, $view = $scroller.find(".gcui-superpanel-contentwrapper:first"), contentHeight = (this._panelInst).getContentElement().height(), totalHeight = this._fixedAreaHeight + contentHeight, viewHeight = $view.innerHeight(), smallChange = (101 / (this._N - 1)) * ((totalHeight - viewHeight) / totalHeight);
                tmp = this._panelInst.options.vScroller;
                tmp.scrollSmallChange = smallChange;
                this._panelInst.option("vScroller", tmp);
                $scroller.bind("superpanelscrolled.gcuigrid", $.proxy(this._onSuperpanelScrolled, this));
                $scroller.bind("superpanelscrolling.gcuigrid", $.proxy(this._onSuperpanelScrolling, this));
                $scroller.bind("superpanelscrolled.gcuigrid", $.proxy(this._onSuperpanelPostScrolled, this));
            };
            uiVirtualScroller.prototype.dispose = function () {
                this._$scroller.unbind(".gcuigrid");
                this._clearTimer();
            };
            uiVirtualScroller.prototype._clearTimer = function () {
                window.clearTimeout(this._timer);
                this._timer = 0;
            };
            uiVirtualScroller.prototype._onSuperpanelScrolling = function (e, args) {
                if(this._ignoreScrollEvents || (args.dir !== "v")) {
                    return;
                }
                if(this._timer === -1) {
                    return false;
                }
            };
            uiVirtualScroller.prototype._onSuperpanelScrolled = function (e, args) {
                var self = this;
                if(this._ignoreScrollEvents || (args.dir !== "v")) {
                    return;
                }
                if(this._timer > 0) {
                    this._clearTimer();
                }
                if(this._timer !== -1) {
                    self._timer = -1;
                    var scrollToIndex = Math.round(args.newValue / self._panelInst.options.vScroller.scrollSmallChange), oldScrollIndex = self._view._bounds.start;
                    if(scrollToIndex < 0) {
                        scrollToIndex = 0;
                    }
                    if(scrollToIndex >= self._N) {
                        scrollToIndex = self._N - 1;
                    }
                    if(scrollToIndex !== oldScrollIndex) {
                        self._grid._handleVirtualScrolling(scrollToIndex, $.proxy(self._scrollingCompleted, self));
                    } else {
                        self._log();
                        self._clearTimer();
                    }
                }
            };
            uiVirtualScroller.prototype._scrollingCompleted = function (scrollIndex) {
                this._grid._trackScrollingIndex(scrollIndex);
                this._log();
                if(this._completeListener) {
                    this._completeListener();
                    this._completeListener = null;
                }
                this._clearTimer();
            };
            uiVirtualScroller.prototype._onSuperpanelPostScrolled = function () {
                if($.isFunction(this._postScrolled)) {
                    this._postScrolled.apply(this, arguments);
                }
            };
            uiVirtualScroller.prototype._log = function () {
            };
            return uiVirtualScroller;
        })();
        grid.uiVirtualScroller = uiVirtualScroller;        
    })(gcui.grid || (gcui.grid = {}));
    var grid = gcui.grid;
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        var $ = jQuery, glob = Globalize;
        function convert(val, fromType, toType, options) {
            var origValue = val;
            options = $.extend({
                nullString: "",
                format: ""
            }, options);
            function getParser(type) {
                options.parser = options.parser || data.defaultParsers[type];
                if (!options.parser && val != null) {
                    data.errors.noParser(type);
                }
                return options.parser;
            }
            fromType = fromType || val != null && typeof val;
            toType = toType || fromType;
            if (!toType) {
                return val;
            }
            if (toType == "string") {
                getParser(fromType);
                if (!options.parser) {
                    return val;
                }
                return options.parser.toStr(val, options.culture, options.format, options.nullString, true);
            }
            getParser(toType);
            if (!options.parser) {
                return val;
            }
            val = options.parser.parse(val, options.culture, options.format, options.nullString, true);
            if (isNaN(val) && val != null && data.util.isNumeric(val)) {
                if (options.ignoreError) {
                    return origValue;
                }
                data.errors.cantConvert(toType, origValue);
            }
            return val;
        }
        data.convert = convert;
        data.defaultParsers = {
            string: {
                parse: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    switch (value) {
                        case null:
                            return null;
                        case nullString:
                            if (convertEmptyStringToNull) {
                                return null;
                            }
                        case undefined:
                        case "&nbsp":
                            return "";
                        default:
                            return "" + value;
                    }
                },
                toStr: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    if (value === null && convertEmptyStringToNull) {
                        return nullString;
                    }
                    return "" + value;
                }
            },
            number: {
                parse: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    var type = typeof (value);
                    if (type === "number") {
                        return isNaN(value) ? NaN : value;
                    }
                    if ((!value && value !== 0) || (value === "&nbsp;") || (value === nullString && convertEmptyStringToNull)) {
                        return null;
                    }
                    return glob.parseFloat(value, 10, culture.name);
                },
                toStr: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    if (value === null && convertEmptyStringToNull) {
                        return nullString;
                    }
                    return glob.format(value, format || "n", culture.name);
                }
            },
            currency: {
                parse: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    var type = typeof (value);
                    if (type === "number") {
                        return isNaN(value) ? NaN : value;
                    }
                    if ((!value && value !== 0) || (value === "&nbsp;") || (value === nullString && convertEmptyStringToNull)) {
                        return null;
                    }
                    if (type === "string") {
                        value = value.replace(culture.numberFormat.currency.symbol, "");
                    }
                    return glob.parseFloat(value, 10, culture.name);
                },
                toStr: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    if (value === null && convertEmptyStringToNull) {
                        return nullString;
                    }
                    return glob.format(value, format || "c", culture.name);
                }
            },
            datetime: {
                parse: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    var match;
                    if (value instanceof Date) {
                        return value;
                    }
                    if (!value || (value === "&nbsp;") || (value === nullString && convertEmptyStringToNull)) {
                        return null;
                    }
                    match = /^\/Date\((\d+)\)\/$/.exec(value);
                    if (match) {
                        return new Date(parseInt(match[1], 10));
                    }
                    var date = glob.parseDate(value, format, culture.name);
                    if (date == null || isNaN(date)) {
                        date = Date.parse(value);
                        date = isNaN(date) ? NaN : new Date(date);
                    }
                    return date;
                },
                toStr: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    if (value === null && convertEmptyStringToNull) {
                        return nullString;
                    }
                    return glob.format(value, format || "d", culture.name);
                }
            },
            boolean: {
                parse: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    var valType = typeof (value);
                    if (valType === "number") {
                        return value != 0;
                    }
                    if (valType === "boolean") {
                        return value;
                    }
                    if (valType === "string") {
                        value = $.trim(value);
                    }
                    if (!value || (value === "&nbsp;") || (value === nullString && convertEmptyStringToNull)) {
                        return null;
                    }
                    switch (value.toLowerCase()) {
                        case "true":
                            return true;
                        case "false":
                            return false;
                    }
                    return NaN;
                },
                toStr: function (value, culture, format, nullString, convertEmptyStringToNull) {
                    if (value === null && convertEmptyStringToNull) {
                        return nullString;
                    }
                    return (value) ? "true" : "false";
                }
            }
        };
        function checkGlob(func) {
            return function () {
                if (!glob) {
                    data.util.logError(data.errors.noGlobalize.create().message);
                }
                return func.apply(this, arguments);
            };
        }
        $.each(data.defaultParsers, function (_, parser) {
            parser.parse = parser.parse && checkGlob(parser.parse);
            parser.toStr = parser.toStr && checkGlob(parser.toStr);
        });
    })(gcui.data || (gcui.data = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (data) {
        (function (filtering) {
            (function (extended) {
                extended.Connective = {
                    AND: "and",
                    OR: "or"
                };
                function normalizeFilter(filter) {
                    function norm(filter) {
                        var result = [];
                        if (filter.length === 0) {
                            return result;
                        }
                        var connective = extended.Connective.AND;
                        data.util.each(filter, function (i, cond) {
                            if (i == 0 && data.util.isString(cond)) {
                                var lowerConnective = cond.toLowerCase();
                                if (lowerConnective == extended.Connective.AND || lowerConnective == extended.Connective.OR) {
                                    connective = lowerConnective;
                                    return;
                                }
                            }
                            if ($.isArray(cond)) {
                                cond = norm(cond);
                                if (!cond) {
                                    return;
                                }
                                if (cond[0] === connective || cond.length == 2) {
                                    cond.shift();
                                    result = result.concat(cond);
                                    return;
                                }
                            } else {
                                var predicate = filtering.normalizeCondition(cond);
                                if (!predicate) {
                                    return;
                                }
                                predicate.property = cond.property;
                                cond = predicate;
                            }
                            result.push(cond);
                        });
                        if (result.length == 0) {
                            return null;
                        } else {
                            result.unshift(connective);
                            return result;
                        }
                    }
                    return norm(filter);
                }
                function compilAsExtended(extendedFilter) {
                    if (!$.isArray(extendedFilter)) {
                        return null;
                    }
                    var result = {
                        original: extendedFilter,
                        normalized: normalizeFilter(extendedFilter),
                        func: null
                    };
                    if (result.normalized == null) {
                        result.func = function (x) {
                            return true;
                        };
                    } else {
                        result.func = function (x) {
                            function check(filter) {
                                var isAnd = filter[0] === extended.Connective.AND, checker = isAnd ? data.util.every : data.util.some;
                                return checker(filter, function (cond, i) {
                                    if (i === 0) {
                                        return isAnd;
                                    }
                                    if ($.isArray(cond)) {
                                        return check(cond);
                                    } else {
                                        var value = data.util.getProperty(x, cond.property);
                                        return cond.op.apply(value, cond.value);
                                    }
                                });
                            }
                            return check(result.normalized);
                        };
                    }
                    return result;
                }
                function compile(filter) {
                    return compilAsExtended(filter) || data.filtering.compile(filter);
                }
                extended.compile = compile;
            })(filtering.extended || (filtering.extended = {}));
            var extended = filtering.extended;
        })(data.filtering || (data.filtering = {}));
    })(gcui.data || (gcui.data = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (grid) {
        var $ = jQuery;
        var gcuiData = gcui.data;
        var filterExt = gcuiData.filtering.extended;
        var GridDataView = (function () {
            function GridDataView(real) {
                this.real = real;
                this._updatingFilter = false;
                this._overrideFilter();
                this._makeMemberProxies();
            }
            GridDataView.prototype._overrideFilter = function () {
                var _this = this;
                this.filter = gcuiData.observable(this.real.filter());
                this.filter.subscribe(function (newValue) {
                    if (_this.real.filter() !== newValue && !_this._updatingFilter) {
                        _this.refresh({
                            filter: newValue
                        });
                    }
                });
                this.real.filter.subscribe(function (newValue) {
                    if (newValue !== _this._lastSetFilter) {
                        _this.filter(newValue);
                    }
                });
            };
            GridDataView.prototype.refresh = function (shape, local) {
                if (typeof local === "undefined") { local = false; }
                shape = $.extend({}, shape);
                var origFilter = shape.filter;
                shape.filter = this._coerceFilter(shape.filter);
                this._lastSetFilter = shape.filter;
                this._updatingFilter = true;
                try {
                    if ($.isFunction(shape.filter) && !$.isFunction(origFilter)) {
                        this.filter(origFilter);
                    } else {
                        this.filter(shape.filter);
                    }
                } finally {
                    this._updatingFilter = false;
                }
                return this.real.refresh(shape, local);
            };
            GridDataView.prototype._makeMemberProxies = function () {
                var _this = this;
                gcuiData.util.each(this.real, function (key, value) {
                    if (!$.isFunction(value) || _this[key] || key.charAt(0) === "_") {
                        return;
                    }
                    _this[key] = $.isFunction(value.subscribe) ? value : function () {
                        return value.apply(_this.real, arguments);
                    };
                });
            };
            GridDataView.create = function create(dataView) {
                return new GridDataView(dataView);
            };
            GridDataView.prototype._convertComplexPropertyFilterToExtendedFilterFormat = function (filter) {
                var result = [];
                $.each(filter, function (prop, condList) {
                    if (!$.isArray(condList)) {
                        condList = [
                            condList
                        ];
                    } else {
                        var connective = gcuiData.util.isString(condList[0]) && condList[0].toLowerCase();
                        if (connective === filterExt.Connective.AND || connective === filterExt.Connective.OR) {
                            result.push(condList);
                            return;
                        }
                    }
                    var normCondList = [];
                    $.each(condList, function (_, cond) {
                        var normCond = gcuiData.filtering.normalizeCondition(cond);
                        if (normCond) {
                            normCond.property = prop;
                            normCondList.push(normCond);
                        }
                    });
                    if (normCondList.length > 0) {
                        result.push(normCondList);
                    }
                });
                return result.length > 0 ? result : null;
            };
            GridDataView.prototype._coerceFilter = function (filter) {
                if ($.isArray(filter)) {
                    return filterExt.compile(filter).func;
                } else if (!$.isPlainObject(filter)) {
                    return filter;
                }
                filter = $.extend(true, {
                }, filter);
                var simpleFilter = {
                };
                $.each(filter, function (prop, cond) {
                    if (!$.isArray(cond)) {
                        cond = [
                            cond
                        ];
                    }
                    if (simpleFilter) {
                        var possibleConnective = gcuiData.util.isString(cond[0]) && cond[0].toLowerCase();
                        if (cond.length == 1 || cond.length == 2 && (possibleConnective === "and" || possibleConnective === "or")) {
                            simpleFilter[prop] = cond[cond.length - 1];
                            return;
                        }
                    }
                    filter[prop] = cond;
                    simpleFilter = null;
                });
                if (simpleFilter) {
                    return simpleFilter;
                }
                var extendedFilter = this._convertComplexPropertyFilterToExtendedFilterFormat(filter);
                return filterExt.compile(extendedFilter).func;
            };
            GridDataView.prototype._unsafeReplace = function (index, newItem) {
                (this.real).sourceArray[index] = newItem;
                (this.real).local[index] = newItem;
            };
            GridDataView.prototype._unsafeSplice = function (index, count, item) {
                if (arguments.length === 2) {
                    (this.real).sourceArray.splice(index, count);
                    (this.real).local.splice(index, count);
                } else {
                    (this.real).sourceArray.splice(index, count, item);
                    (this.real).local.splice(index, count, item);
                }
            };
            GridDataView.prototype._unsafePush = function (item) {
                (this.real).sourceArray.push(item);
                (this.real).local.push(item);
            };
            return GridDataView;
        })();
        grid.GridDataView = GridDataView;
    })(gcui.grid || (gcui.grid = {}));
})(gcui || (gcui = {}));

(function (gcui) {
    (function (ribbon) {
        "use strict";
        var $ = jQuery, widgetName = "gcuiribbon";
        var css_ribbon = "gcui-ribbon", css_ribbon_disabled = css_ribbon + "-disabled", css_ribbon_bigbutton = css_ribbon + "-bigbutton", css_ribbon_panel = css_ribbon + "-panel", css_ribbon_groups = css_ribbon + "-groups", css_ribbon_group = css_ribbon + "-group", css_ribbon_groupcontent = css_ribbon_group + "-content", css_ribbon_grouplabel = css_ribbon_group + "-label", css_ribbon_dropdown = css_ribbon + "-dropdown", css_ribbon_dropdowngroup = css_ribbon + "-dropdowngroup", css_ribbon_abbrev = css_ribbon + "-abbrev", css_ribbon_abbrevgroup = css_ribbon_abbrev + "group", css_ribbon_text = css_ribbon + "-text", css_ribbon_icon = css_ribbon + "-icon", css_ribbon_abbrevicon = css_ribbon + "-abbrevicon";
        var gcuiribbon = (function (_super) {
            __extends(gcuiribbon, _super);
            function gcuiribbon() {
                _super.apply(this, arguments);
            }
            gcuiribbon.prototype._create = function () {
                var self = this;
                if(window.applyTouchUtilEvents) {
                    $ = window.applyTouchUtilEvents($);
                }
                self._ribbonify();
                $(window).bind("resize." + this.widgetName, function () {
                    self.updateRibbonSize();
                });
                if(self.element.is(":hidden") && self.element.addVisibilityObserver) {
                    self.element.addVisibilityObserver(function () {
                        self.updateRibbonSize();
                        if(self.options.disabled) {
                            self._setDisabled(true);
                        }
                        if(self.element.removeVisibilityObserver) {
                            self.element.removeVisibilityObserver();
                        }
                    }, "gcuiribbon");
                }
                if(self.options.disabled) {
                    self._setDisabled(true);
                }
            };
            gcuiribbon.prototype._setOption = function (key, value) {
                $.gcui.widget.prototype._setOption.apply(this, arguments);
                if(key === "disabled") {
                    this._setDisabled(value);
                }
                $(".gcui-ribbon-startmenulink", this.element).unbind("click.tabs");
            };
            gcuiribbon.prototype._setDisabled = function (value) {
                var self = this, element = self.element, eleOffset = element.offset(), offsetTop = eleOffset.top, offsetLeft = eleOffset.left, offsetParent = $("body"), css = this.options.uiCSS, disabledModal = self.disabledModal;
                if (element.data("gcuiUITabs")) {
                    element.gcuitabs("option", "disabled", value);
                }
                if(element.closest(".gcui-editor").length !== 0) {
                    offsetTop = 0;
                    offsetLeft = 0;
                    offsetParent = element.parent();
                }
                if(value) {
                    if(!disabledModal) {
                        disabledModal = $("<div></div>").addClass(css.stateDisabled + " " + css_ribbon_disabled).css({
                            top: offsetTop,
                            left: offsetLeft,
                            "background-color": "lightgray",
                            "position": "absolute"
                        }).appendTo(offsetParent).bind("click mousedown mouseup mouseover mouseout " + "focus keydown keypress", function (e) {
                            e.stopPropagation();
                            e.preventDefault();
                            return false;
                        });
                        self.disabledModal = disabledModal;
                    }
                    self.disabledModal.width(element.width()).height(element.outerHeight()).show();
                } else {
                    if(self.disabledModal) {
                        self.disabledModal.hide();
                    }
                }
            };
            gcuiribbon.prototype._initCustomButtons = function () {
                var self = this, customtype;
                $.each($("[customtype]", self.element), function (index, ele) {
                    customtype = $(ele).attr("customtype");
                    if(customtype && self.options.custombuttons[customtype]) {
                        self.options.custombuttons[customtype].create($(ele));
                    }
                });
            };
            gcuiribbon.prototype._ribbonify = function () {
                var self = this, css = this.options.uiCSS;
                self.buttons = {};
                self.listUis = {};
                self.accessbarBtns = {};
                self._initCustomButtons();
                self._createAccessToolBar();
                self._getButtonSets();
                self._createButtons();
                self._createGroup();
                self._createSplit();
                self._createDropdwon();
                self._createInputDropdown();
                self._createGallery();
                self._hideShowedList();
                self._createTab();
                $("." + css.helperReset + ":not(." + css_ribbon_groups + ", ." + css.tabsNav + ", .gcui-menu-list)", self.element).hover(function () {
                    $(this).addClass(css.stateHover);
                }, function () {
                    $(this).removeClass(css.stateHover);
                });
                $("." + css.button, self.element).bind("mouseenter", function () {
                    $("." + css.buttonText, this).addClass(css.stateHover);
                }).bind("mouseleave", function () {
                    $("." + css.buttonText, this).removeClass(css.stateHover);
                });
                $("button." + css.button, self.element).bind("mousedown", function () {
                    self._addActiveClassToButtonText(this);
                }).bind("mouseup", function () {
                    self._addActiveClassToButtonText(this);
                });
                $("label." + css.button, self.element).bind("click", function () {
                    if($("span", this) && $("span", this).length === 1) {
                        self._addActiveClassToLabelButtonText(this);
                    }
                });
            };
            gcuiribbon.prototype._addActiveClassToButtonText = function (button) {
                var css = this.options.uiCSS;
                if(button) {
                    if($(button).hasClass(css.stateActive)) {
                        $("." + css.buttonText, button).addClass(css.stateActive);
                    } else {
                        $("." + css.buttonText, button).removeClass(css.stateActive);
                    }
                }
            };
            gcuiribbon.prototype._addActiveClassToLabelButtonText = function (button) {
                var self = this, oriEle, oriEleID, eleGroup;
                if(button) {
                    oriEleID = $(button).attr("for");
                    oriEle = $("#" + oriEleID, self.element);
                    if(oriEle.is(":checkbox")) {
                        self._addActiveClassToButtonText(button);
                    } else if((oriEle.is(":radio"))) {
                        eleGroup = self.element.find("[name='" + oriEle.attr("name") + "']");
                        $.each(eleGroup, function () {
                            self._updateGroupElementTextState(this);
                        });
                    }
                }
            };
            gcuiribbon.prototype._updateGroupElementTextState = function (button) {
                var radioLabelEle, css = this.options.uiCSS;
                if(!button) {
                    return;
                }
                radioLabelEle = $("[for='" + $(button).attr("id") + "']", $(button).parent());
                if(radioLabelEle) {
                    if(radioLabelEle.hasClass(css.stateActive)) {
                        radioLabelEle.children("." + css.buttonText).addClass(css.stateActive);
                    } else {
                        radioLabelEle.children("." + css.buttonText).removeClass(css.stateActive);
                    }
                }
            };
            gcuiribbon.prototype._getButtonSets = function () {
                var self = this, span;
                self.groups = [];
                self.splits = [];
                self.dropdowns = [];
                self.inputDropdowns = [];
                $("span>button, span>:checkbox, span>:radio, div>button, div>:checkbox," + " div>:radio, div>:text", self.element).each(function (i, btn) {
                    if($(btn).parents("[customtype]") && $(btn).parents("[customtype]").length !== 0) {
                        return;
                    }
                    span = $(btn).parent();
                    if($(">ul", span).length === 0 && !span.hasClass("gcui-ribbon-accesstoolbar")) {
                        self.groups.push(span);
                    } else {
                        if($(">button", span).length === 2) {
                            self.splits.push(span);
                        } else if($(">button", span).length === 1) {
                            self.dropdowns.push(span);
                        } else if($(">input[type='text']", span).length === 1) {
                            self.inputDropdowns.push(span);
                        }
                    }
                    return this;
                });
                self.groups = self._unique(self.groups);
                self.splits = self._unique(self.splits);
            };
            gcuiribbon.prototype._unique = function (group) {
                var array = $.makeArray($.map(group, function (n) {
                    return n.get(0);
                }));
                return $.map($.unique(array), function (n) {
                    return $(n);
                });
            };
            gcuiribbon.prototype._createButtons = function () {
                var self = this, element = self.element;
                $(":checkbox", element).each(function () {
                    if($(this).parents("[customtype]") && $(this).parents("[customtype]").length !== 0) {
                        return;
                    }
                    self._createButton($(this), "checkbox");
                    $(this).bind("change", function () {
                        self._addActiveClassToLabelButtonText($(this).button("widget")[0]);
                    });
                    return this;
                });
                $(":radio", element).each(function () {
                    if($(this).parents("[customtype]") && $(this).parents("[customtype]").length !== 0) {
                        return;
                    }
                    self._createButton($(this), "radio");
                    $(this).bind("change", function () {
                        self._addActiveClassToLabelButtonText($(this).button("widget")[0]);
                    });
                    return this;
                });
                $("button", element).each(function () {
                    if($(this).parents("[customtype]") && $(this).parents("[customtype]").length !== 0) {
                        return;
                    }
                    self._createButton($(this), "button");
                    return this;
                });
            };
            gcuiribbon.prototype._createButton = function (button, type) {
                var self = this, css = this.options.uiCSS, options = self._buildButtonOption(button, type), name = button.data("commandName"), labelEle;
                button.button(options);
                if(!options.text) {
                    if(type === "button") {
                        button.children("." + css.buttonText).text(name);
                    } else {
                        labelEle = $("[for='" + button.attr("id") + "']", button.parent());
                        if(labelEle) {
                            labelEle.children("." + css.buttonText).text(name);
                        }
                    }
                }
                if(button.parent().is(".gcui-ribbon-accesstoolbar") && button.attr("canlocateonaccesstoolbar") === "true") {
                    button.button("widget").hide();
                }
                self._triggerEvent(button);
            };
            gcuiribbon.prototype._createGroup = function () {
                $.each(this.groups, function (i, group) {
                    group.buttonset();
                });
            };
            gcuiribbon.prototype._createSplit = function () {
                var self = this, css = this.options.uiCSS;
                $.each(self.splits, function (i, split) {
                    var list = split.children("ul"), content = split.children("button:eq(0)"), splitName = content.data("commandName"), triggerEle = split.children("button:eq(1)"), splitObj;
                    list.children("li").addClass(css.cornerAll);
                    split.addClass(css_ribbon + "-" + splitName);
                    triggerEle.button({
                        icons: {
                            primary: css.iconArrowDown
                        },
                        text: false
                    }).data("list", list).unbind("click").bind("click", function (e) {
                        e.stopPropagation();
                        e.preventDefault();
                    }).bind("mouseup", function (e) {
                        list = $(this).data("list");
                        if(list.is(":visible")) {
                            self.showList.hide().css("z-index", "");
                            self.showList = null;
                        } else {
                            list.show().position({
                                my: 'left top',
                                at: 'left bottom',
                                of: split
                            }).css("z-index", 9999);
                            self.showList = list;
                        }
                        e.stopPropagation();
                        e.preventDefault();
                    });
                    triggerEle.children("." + css.buttonText).text("foo");
                    split.after(list);
                    list.css("position", "absolute").addClass(css.helperReset + " " + css.helperClearFix + " " + css.cornerAll + " " + css_ribbon_dropdown).hide();
                    split.buttonset();
                    splitObj = {
                        ui: content,
                        defaultValue: content.button("option", "label"),
                        buttons: [],
                        type: "split",
                        trigger: triggerEle,
                        list: list
                    };
                    $("button, :checkbox, :radio", list).bind("click", function (e) {
                        list.hide().css("z-index", "");
                        self.showList = null;
                        e.preventDefault();
                    });
                    list.find(">li>button,>li>:radio,>li>:checkbox").each(function (i, button) {
                        var commandName = $(button).data("commandName");
                        if(commandName !== "" && self.buttons[commandName]) {
                            self.buttons[commandName].parent = content;
                            self.buttons[commandName].type = "split";
                        }
                        splitObj.buttons.push($(button));
                    });
                    if(splitName !== "") {
                        self.listUis[splitName] = splitObj;
                    }
                });
            };
            gcuiribbon.prototype._createDropdwon = function () {
                var self = this, css = this.options.uiCSS, code, keyCode;
                self.dropdownLabels = {
                };
                $.each(self.dropdowns, function (i, dropdown) {
                    var list = dropdown.children("ul"), button = dropdown.children("button:eq(0)"), dropdownName = button.data("commandName"), dropdownObj;
                    list.children("li").addClass(css.cornerAll);
                    dropdown.addClass(css_ribbon + "-" + dropdownName);
                    button.button({
                        icons: {
                            secondary: css.iconArrowDown
                        }
                    }).unbind("click").bind("click", function (e) {
                        e.stopPropagation();
                        e.preventDefault();
                    }).bind("mouseup", function (e) {
                        self._showDropDownList(list, button);
                        e.stopPropagation();
                        e.preventDefault();
                    }).bind("keydown", function (e) {
                        if(!list.is(":visible")) {
                            return;
                        }
                        self._dropdownListKeyHandler(e, list, button);
                    });
                    dropdownObj = {
                        ui: button,
                        defaultValue: button.button("option", "label"),
                        buttons: [],
                        type: "dropdown",
                        list: list
                    };
                    $("button, :checkbox, :radio", list).bind("click", function (e) {
                        var name = $(this).data("commandName"), label, width = button.children("." + css.buttonText).width();
                        if(!self.dropdownLabels[name]) {
                            self.dropdownLabels[name] = self._getDropdownLabelSubstr($(this).button("option", "label"), button.children("." + css.buttonText), width);
                        }
                        label = self.dropdownLabels[name];
                        button.button("option", "label", label);
                        button.attr("title", $(this).button("option", "label"));
                        list.hide().css("z-index", "");
                        self.showList = null;
                        e.preventDefault();
                    });
                    list.css("position", "absolute").addClass(css.helperReset + " " + css.helperClearFix + " " + css.cornerAll + " " + css_ribbon_dropdown).hide();
                    dropdown.buttonset();
                    self._collectDropDownButtons(list, button, dropdownObj, dropdownName);
                });
            };
            gcuiribbon.prototype._createInputDropdown = function () {
                var self = this, css = this.options.uiCSS;
                self.dropdownLabels = {};
                $.each(self.inputDropdowns, function (i, dropdown) {
                    var list = dropdown.children("ul"), inputEle = dropdown.children("input[type='text']:eq(0)"), inputDropDownWrapper = self._createInputDropDownWrapper(inputEle), dropdownName = inputEle.prop("name"), dropdownObj;
                    dropdown.prepend(inputDropDownWrapper);
                    list.children("li").addClass(css.cornerAll);
                    dropdown.addClass(css_ribbon + "-" + dropdownName);
                    inputEle.removeProp("name").data("commandName", dropdownName).addClass(css.stateDefault + " " + css.content + " " + css.cornerLeft);
                    inputDropDownWrapper.find("a").bind("mouseup", function (e) {
                        self._showDropDownList(list, inputEle);
                        inputEle.focus();
                        e.stopPropagation();
                        e.preventDefault();
                    }).bind("keydown", function (e) {
                        self._dropdownListKeyHandler(e, list, inputDropDownWrapper);
                    });
                    self._bindInputEvent(inputEle, list);
                    dropdownObj = {
                        ui: inputEle,
                        defaultValue: inputEle.val(),
                        buttons: [],
                        type: "dropdown",
                        list: list
                    };
                    $("button, :checkbox, :radio", list).bind("click", function (e) {
                        var name = $(this).data("commandName"), label;
                        if(!self.dropdownLabels[name]) {
                            self.dropdownLabels[name] = $(this).button("option", "label");
                        }
                        label = self.dropdownLabels[name];
                        inputEle.val($(this).button("option", "label")).attr("title", $(this).button("option", "label"));
                        list.hide().css("z-index", "");
                        self.showList = null;
                        e.preventDefault();
                        e.stopPropagation();
                    });
                    list.css("position", "absolute").addClass(css.helperReset + " " + css.helperClearFix + " " + css.cornerAll + " " + css_ribbon_dropdown).hide();
                    self._collectDropDownButtons(list, inputEle, dropdownObj, dropdownName);
                });
            };
            gcuiribbon.prototype._collectDropDownButtons = function (list, parentEle, dropdownObj, dropdownName) {
                var self = this;
                list.find(">li>button,>li>:radio,>li>:checkbox").each(function (i, btn) {
                    var commandName = $(btn).data("commandName");
                    if(commandName !== "" && self.buttons[commandName]) {
                        self.buttons[commandName].parent = parentEle;
                        self.buttons[commandName].type = "dropdown";
                    }
                    dropdownObj.buttons.push($(btn));
                });
                if(dropdownName !== "") {
                    self.listUis[dropdownName] = dropdownObj;
                }
            };
            gcuiribbon.prototype._showDropDownList = function (list, buttonEle) {
                var self = this;
                if(list.is(":visible") && !list.find("li." + self.options.uiCSS.stateActive)) {
                    self.showList.hide().css("z-index", "");
                    self.showList = null;
                } else {
                    if(self.showList) {
                        self.showList.hide();
                        self.showList = null;
                    }
                    list.show().position({
                        my: 'left top',
                        at: 'left bottom',
                        of: buttonEle
                    }).css("z-index", 9999);
                    self.showList = list;
                    self.isShow = true;
                }
            };
            gcuiribbon.prototype._bindInputEvent = function (inputEle, list) {
                var self = this, code, keyCode;
                inputEle.bind("change", function (e) {
                    var obj = {
                        name: undefined,
                        commandName: undefined
                    };
                    obj.name = inputEle.prop("name");
                    obj.commandName = inputEle.val();
                    self._trigger("click", e, obj);
                }).bind("keydown", function (event) {
                    self._dropdownListKeyHandler(event, list, inputEle);
                });
            };
            gcuiribbon.prototype._dropdownListKeyHandler = function (event, list, container) {
                var self = this, code = event.keyCode, keyCode = self._getKeyCodeEnum();
                switch(code) {
                    case keyCode.UP:
                        self._activeDropDownListElement(false, list, container);
                        event.preventDefault();
                        break;
                    case keyCode.DOWN:
                        self._activeDropDownListElement(true, list, container);
                        event.preventDefault();
                        break;
                    case keyCode.ENTER:
                        list.find("label." + self.options.uiCSS.stateActive).parent("li").find("input").trigger("click");
                        event.preventDefault();
                        break;
                }
            };
            gcuiribbon.prototype._activeDropDownListElement = function (down, list, inputEle) {
                var self = this, len = list.find("li").length, activeIndex, activeItem;
                if(!list.is(":visible")) {
                    self._showDropDownList(list, inputEle);
                    return;
                }
                activeItem = list.find("label." + self.options.uiCSS.stateActive).parent("li");
                if(activeItem && activeItem.length > 0) {
                    activeIndex = list.find("li").index(activeItem);
                }
                self._changeListItemActiveState(activeItem, false);
                if(activeIndex === undefined) {
                    activeIndex = 0;
                } else {
                    if(down) {
                        activeIndex++;
                        if(activeIndex == len) {
                            activeIndex = undefined;
                        }
                    } else {
                        activeIndex--;
                        if(activeIndex == -1) {
                            activeIndex = len - 1;
                        }
                    }
                }
                self._changeListItemActiveState($(list.find("li")[activeIndex]), true);
            };
            gcuiribbon.prototype._changeListItemActiveState = function (listitem, activate) {
                var css = this.options.uiCSS;
                if(!listitem) {
                    return;
                }
                if(activate) {
                    $("label", listitem).addClass(css.stateActive);
                    $("span", listitem).addClass(css.stateActive);
                } else {
                    $("label", listitem).removeClass(css.stateActive);
                    $("span", listitem).removeClass(css.stateActive);
                }
            };
            gcuiribbon.prototype._createGallery = function () {
                var self = this, css = self.options.uiCSS;
                $(".gcui-ribbon-gallery", self.element).each(function (i, gallery) {
                    $(gallery).children("div").each(function (i, unitDiv) {
                        $(unitDiv).bind("mouseenter", function (e) {
                            if($(unitDiv).hasClass(css.stateDisabled)) {
                                return;
                            }
                            $(unitDiv).addClass(css.stateHover);
                        }).bind("mouseleave", function (e) {
                            if($(unitDiv).hasClass(css.stateDisabled)) {
                                return;
                            }
                            $(unitDiv).removeClass(css.stateHover);
                        }).bind("click", function (e) {
                            if($(unitDiv).hasClass(css.stateDisabled)) {
                                return;
                            }
                            $(unitDiv).parent("div.gcui-ribbon-gallery").children("div").removeClass(css.stateActive);
                            $(unitDiv).addClass(css.stateActive);
                            var obj = {
                                name: undefined,
                                commandName: undefined
                            };
                            obj.name = $(unitDiv).parent("div.gcui-ribbon-gallery").attr("name");
                            obj.commandName = $(unitDiv).attr("name");
                            self._trigger("click", e, obj);
                        });
                        self.buttons[$(unitDiv).attr("name")] = {
                            button: $(unitDiv),
                            type: "galleryBtn"
                        };
                    });
                });
            };
            gcuiribbon.prototype._getKeyCodeEnum = function () {
                if($.ui && $.ui.keyCode) {
                    return $.ui.keyCode;
                }
                if($.mobile && $.mobile.keyCode) {
                    return $.mobile.keyCode;
                }
                throw "keyCode object is not found";
            };
            gcuiribbon.prototype._createInputDropDownWrapper = function (inputEle) {
                var css = this.options.uiCSS;
                var inputDropDownWrapper = $("<span class='gcui-ribbon-inputdropdownwrapper'></span>").append(inputEle);
                $("<a></a>").appendTo(inputDropDownWrapper).button({
                    icons: {
                        primary: css.iconArrowDown
                    },
                    text: false
                }).removeClass(css.cornerAll).addClass("gcui-ribbon-inputdropdownbtn " + css.cornerRight);
                return inputDropDownWrapper;
            };
            gcuiribbon.prototype._createTab = function () {
                var self = this, css = this.options.uiCSS, element = self.element;
                element.addClass(css_ribbon);
                self.tabEle = element;
                if($(">ul", element).length > 0) {
                    element.gcuitabs({
                        select: function (e, args) {
                            self._hideAllMenus();
                        }
                    });
                    self._createRibbonGroups();
                    if($(".gcui-ribbon-startmenu") && $(".gcui-ribbon-startmenu").length > 0) {
                        element.gcuitabs("option", "selected", 1);
                        self._createStartMenu();
                    }
                    return true;
                } else {
                    element.addClass(css_ribbon + "-simple").addClass(css.stateDefault).addClass(css.helperClearFix);
                }
                return false;
            };
            gcuiribbon.prototype._createAccessToolBar = function () {
                var self = this, newId, accessBarDiv = $("<div class='gcui-ribbon-accesstoolbar'></div>"), menuBtn;
                if($("*[canlocateonaccesstoolbar='true']", self.element).length === 0 && $("*[locateonaccesstoolbar='true']", self.element).length === 0) {
                    return;
                }
                $("input[canlocateonaccesstoolbar='true'],input[locateonaccesstoolbar='true']", self.element).each(function () {
                    newId = $(this).attr("id") + "_accessbar";
                    var labelEle = $("label[for=" + $(this).attr("id") + "]");
                    self.accessbarBtns[labelEle.attr("title")] = {
                        button: $(this).clone().attr("id", newId),
                        type: $(this).attr("type"),
                        labelEle: labelEle.clone().attr("for", newId),
                        selected: $(this).attr("locateonaccesstoolbar") === "true" ? true : false
                    };
                });
                $("button[canlocateonaccesstoolbar='true'],button[locateonaccesstoolbar='true']", self.element).each(function () {
                    newId = $(this).attr("id") + "_accessbar";
                    self.accessbarBtns[$(this).attr("title")] = {
                        button: $(this).clone(),
                        type: 'button',
                        selected: $(this).attr("locateonaccesstoolbar") === "true" ? true : false
                    };
                });
                $.each(self.accessbarBtns, function (key, btnObj) {
                    accessBarDiv.append(btnObj.button);
                    if(btnObj.labelEle) {
                        accessBarDiv.append(btnObj.labelEle);
                    }
                });
                menuBtn = $('<button title="Customize Access Toolbar" class ="gcui-ribbon-accesstoolbarbtn ' + self.options.uiCSS.iconArrowDown + '"' + ' name="quicktoolbar"></button>');
                accessBarDiv.append(menuBtn);
                if(self.accessbarBtns) {
                    self.element.prepend(accessBarDiv);
                }
                self._createCustomQuickToolbarMenu(menuBtn, accessBarDiv);
            };
            gcuiribbon.prototype._createCustomQuickToolbarMenu = function (triggerEle, appendToEle) {
                var self = this, liEle, checkedCss, css = self.options.uiCSS, customQuickToolbarMenu = $('<ul class="gcui-ribbon-accesstoolbarmenu"></ul>');
                $.each(self.accessbarBtns, function (key, item) {
                    checkedCss = item.selected ? ' gcui-ribbon-accesstoolbarmenuitemchecked' : ' gcui-ribbon-accesstoolbarmenuitemunchecked';
                    liEle = $('<li><a class="' + css.cornerAll + '" tabindex="-1">' + '<span class="' + css.icon + checkedCss + '"></span><span' + ' class="gcui-menu-text">' + key + '</span></a></li>');
                    liEle.data("accessbarcommand", key);
                    customQuickToolbarMenu.append(liEle);
                });
                customQuickToolbarMenu.prepend("<li class='gcui-ribbon-accesstoolbarmenutitleitem'><a>Customize Access Toolbar</a></li>");
                appendToEle.append(customQuickToolbarMenu);
                customQuickToolbarMenu.gcuimenu({
                    trigger: triggerEle,
                    triggerEvent: "click",
                    orientation: "vertical",
                    select: function (e, item) {
                        var liEle = item.item, btnName = liEle.element.data("accessbarcommand"), iconSpan = $("span." + css.icon, liEle.element), accessBarBtn = self.accessbarBtns[btnName].button, btnWidget = accessBarBtn.button("widget");
                        if(iconSpan.hasClass("gcui-ribbon-accesstoolbarmenuitemchecked")) {
                            iconSpan.removeClass("gcui-ribbon-accesstoolbarmenuitemchecked").addClass("gcui-ribbon-accesstoolbarmenuitemunchecked");
                            btnWidget.hide().removeAttr("canlocateonaccesstoolbar").attr("locateonaccesstoolbar", "true");
                        } else {
                            iconSpan.addClass("gcui-ribbon-accesstoolbarmenuitemchecked").removeClass("gcui-ribbon-accesstoolbarmenuitemunchecked");
                            btnWidget.show().removeAttr("locateonaccesstoolbar").attr("canlocateonaccesstoolbar", "true");
                        }
                        customQuickToolbarMenu.gcuimenu("deactivate", null);
                    }
                });
                customQuickToolbarMenu.gcuimenu("setItemDisabled", $(".gcui-ribbon-accesstoolbarmenutitleitem"), true);
            };
            gcuiribbon.prototype.updateRibbonSize = function () {
                var self = this, groupDropDown, css = this.options.uiCSS, abbrevgroupContainer;
                self.tabEle.children("div").not(".gcui-menu").each(function (i, tabPage) {
                    var $tabPage = $(tabPage), isTabVisible = $tabPage.is(":visible"), groups = $tabPage.find(">ul>li:not(.gcui-menu-item)"), groupInfos = [], pageWidth = $tabPage.width();
                    if(!isTabVisible) {
                        $tabPage.removeClass(css.tabsHide);
                        pageWidth = $tabPage.width();
                    }
                    groups.each(function (j, li) {
                        var group = $(li), lblDiv = group.find(">div:last"), text = self._getGroupLabelText(lblDiv);
                        groupDropDown = group.children("." + css_ribbon_dropdowngroup);
                        abbrevgroupContainer = group.children("." + css_ribbon_abbrevgroup);
                        if(groupDropDown) {
                            group.addClass(css_ribbon + "-" + text.toLowerCase()).prepend(groupDropDown.children());
                            groupDropDown.remove();
                            if(abbrevgroupContainer) {
                                abbrevgroupContainer.remove();
                            }
                        }
                        groupInfos.push({
                            width: group.outerWidth(true),
                            contentWidth: group.width(),
                            height: group.outerHeight(true),
                            text: text
                        });
                        return this;
                    });
                    self._adjustRibbonGroupIfNeeded(groups, groupInfos, pageWidth);
                    if(!isTabVisible) {
                        $tabPage.addClass(css.tabsHide);
                    }
                    return this;
                });
                if(self.options.disabled) {
                    self._setDisabled(true);
                }
            };
            gcuiribbon.prototype._createRibbonGroups = function () {
                var self = this, css = this.options.uiCSS;
                self.tabEle.children("div").each(function (i, tabPage) {
                    var $tabPage = $(tabPage), isTabVisible = $tabPage.is(":visible"), groups = $tabPage.find(">ul:not(.gcui-ribbon-accesstoolbarmenu)>li"), groupInfos = [], pageWidth;
                    if($tabPage.data("destroy.tabs")) {
                        $tabPage.remove();
                        return;
                    }
                    if(!isTabVisible) {
                        $tabPage.removeClass(css.tabsHide);
                    }
                    pageWidth = $tabPage.width();
                    $tabPage.addClass(css_ribbon_panel);
                    if(!$tabPage.hasClass(css.tabsPanel)) {
                        $tabPage.addClass("gcui-ribbon-accesstoolbar");
                        self.tabEle.prepend($tabPage);
                    }
                    $tabPage.find(">ul").addClass(css.helperReset + " " + css.helperClearFix + " " + css.content + " " + css.cornerAll + " " + css_ribbon_groups);
                    groups.each(function (j, li) {
                        var group = $(li), lblDiv = group.find(">div:last"), groupSpanText = lblDiv.find("span"),
                            groupcss = css.stateDefault + " " + css_ribbon_group, text = self._getGroupLabelText(lblDiv);
                        if(lblDiv) {
                            groupcss += " " + css_ribbon + "-" + text.toLowerCase();
                        }
                        group.addClass(groupcss);
                        lblDiv.addClass(css_ribbon_grouplabel).bind("click", function () {
                            return false;
                        });
                        group.wrapInner("<div class = '" + css_ribbon_groupcontent + "'></div>");
                        group.children().bind("mouseover", function () {
                            $(this).addClass(css.stateHover);
                        }).bind("mouseout", function () {
                            $(this).removeClass(css.stateHover);
                        });
                        lblDiv.appendTo(group);
                        groupInfos.push({
                            width: group.outerWidth(true),
                            text: text
                        });
                        return this;
                    });
                    self._originalGroupInfo = groupInfos;
                    self._adjustRibbonGroupIfNeeded(groups, groupInfos, pageWidth);
                    if(!isTabVisible) {
                        $tabPage.addClass(css.tabsHide);
                    }
                    return this;
                });
            };
            gcuiribbon.prototype._createStartMenu = function () {
                var self = this, startMenuLink = $(".gcui-ribbon-startmenulink", self.element), css = self.options.uiCSS;
                startMenuLink.unbind("click");
                startMenuLink.parent("li").addClass("gcui-ribbon-startmenulinkcontainer").hover(function () {
                    $(this).removeClass("gcui-ribbon-startmenulinkcontainer");
                }, function () {
                    $(this).addClass("gcui-ribbon-startmenulinkcontainer");
                });
                $(".gcui-ribbon-startmenuindicator", self.element).addClass(css.icon + " " + css.iconArrowDown);
                $(".gcui-ribbon-startmenu", self.element).gcuimenu({
                    trigger: $(".gcui-ribbon-startmenulink", self.element).parent(),
                    triggerEvent: "click",
                    orientation: "vertical",
                    select: function (e, item) {
                        var obj = {
                            name: undefined,
                            commandName: undefined
                        };
                        obj.commandName = item.item._link.attr("name");
                        self._trigger("click", e, obj);
                    },
                    hidden: function (e, item) {
                        if(item.activeItem) {
                            item.activeItem._link.removeClass(css.stateFocus);
                        }
                    }
                });
            };
            gcuiribbon.prototype._getGroupLabelText = function (lblDiv) {
                var groupSpanText, text;
                if(lblDiv) {
                    groupSpanText = lblDiv.find("span");
                    if(lblDiv.attr("displayname")) {
                        text = lblDiv.attr("displayname");
                    } else {
                        if(groupSpanText && groupSpanText.length > 0 && !lblDiv.hasClass(css_ribbon_abbrevgroup)) {
                            text = $(groupSpanText[0]).text();
                        } else {
                            text = $.trim(lblDiv.text());
                        }
                    }
                }
                return text;
            };
            gcuiribbon.prototype._adjustRibbonGroupIfNeeded = function (groups, groupInfos, pageWidth) {
                var self = this, i = groups.length - 1, j = 0, iWidth = 0, jWidth = 0, groupDropDown, abbrevgroupContainer, gi;
                for(; i >= 0; i--) {
                    jWidth = 0;
                    for(j = 0; j < i; j++) {
                        jWidth += groupInfos[j].width;
                    }
                    if(jWidth + iWidth + groupInfos[i].width <= pageWidth) {
                        groupDropDown = $(groups[i]).children("." + css_ribbon_dropdowngroup);
                        abbrevgroupContainer = $(groups[i]).children("." + css_ribbon_abbrevgroup);
                        if(groupDropDown) {
                            $(groups[i]).addClass(css_ribbon + "-" + groupInfos[i].text.toLowerCase()).prepend(groupDropDown.children());
                            groupDropDown.remove();
                            if(abbrevgroupContainer) {
                                abbrevgroupContainer.remove();
                            }
                        }
                    } else {
                        gi = groupInfos[i];
                        iWidth += self._createDropDownRibbonGroup(gi, groups[i], pageWidth);
                    }
                }
            };
            gcuiribbon.prototype._createDropDownRibbonGroup = function (gi, group, pageWidth) {
                var self = this,
                    text = gi.text,
                    grpClass = css_ribbon + "-" + text.toLowerCase(),
                    css = this.options.uiCSS,
                    $group = $(group).removeClass(grpClass),
                    displayText = self._getGroupLabelText($group.find(">div:last")) || text, $abbrevgrp;
                $group.wrapInner("<div class='" + css_ribbon_dropdowngroup + " " + css_ribbon_group + "'></div>").children().hide().addClass(grpClass).bind("mouseup." + self.widget, function (e) {
                    if(self.showDrpDwnGroup !== null) {
                        self.showDrpDwnGroup.hide().css("z-index", "");
                        self.showDrpDwnGroup = null;
                    }
                });
                $abbrevgrp = $("<div class='" + css_ribbon_abbrevgroup + "'>" + "<span class='" + css_ribbon_abbrev + displayText.toLowerCase() + " " + css_ribbon_icon + " " + css_ribbon_abbrevicon + "'></span>" + "<span class='" + css_ribbon_text + "'>" + displayText + "</span>" + "<span class='" + css.icon + " " + css.iconArrowDown + " " + css_ribbon_icon + "'></span></div>").appendTo($group).unbind(self.widget).bind("mouseover." + self.widget, function (e) {
                    $(this).addClass(css.stateHover);
                }).bind("mouseout." + self.widget, function (e) {
                    $(this).removeClass(css.stateHover);
                }).bind("click." + self.widget, function (e) {
                    var $drpGroup = $(this).siblings("." + css_ribbon_dropdowngroup);
                    if($drpGroup.is(":visible")) {
                        $drpGroup.hide().css("z-index", "");
                        self.showDrpDwnGroup = null;
                    } else {
                        if(self.showDrpDwnGroup) {
                            self.showDrpDwnGroup.hide().css("z-index", "");
                        }

                        var width = gi.contentWidth + 1, height = gi.height;

                        $drpGroup.css({ "width": width, "height": height });    // set with actual size

                        $drpGroup.show().position({
                            my: "left top",
                            at: "left bottom",
                            of: this
                        }).css("z-index", "10000");

                        self.showDrpDwnGroup = $drpGroup;
                        e.stopPropagation();
                    }
                });
                return $group.outerWidth(true);
            };
            gcuiribbon.prototype._hideShowedList = function () {
                var self = this;
                $(document).bind("mouseup", function (e) {
                    var target = e.target;
                    if(self.showList) {
                        if($(target).is(document) || !$.contains(self.showList.get(0), target)) {
                            self.showList.hide().css("z-index", "");
                            self.showList = null;
                        }
                    }
                    if(self.showDrpDwnGroup) {
                        if($(target).is(document) || !$.contains(self.showDrpDwnGroup.get(0), target)) {
                            self.showDrpDwnGroup.hide().css("z-index", "");
                            self.showDrpDwnGroup = null;
                        }
                    }
                });
            };
            gcuiribbon.prototype._buildButtonOption = function (node, type) {
                var text = true, self = this, nodeClass = node.attr("class"), spans, iconClass, iconEle, imagePosition, label, labelEle, name;
                if(nodeClass && nodeClass !== "" && nodeClass !== css_ribbon_bigbutton) {
                    iconClass = nodeClass.split(" ")[0];
                    node.removeClass(iconClass);
                    label = $.trim(node.text());
                    if(label === "") {
                        text = false;
                    }
                } else {
                    if(type === "checkbox" || type === "radio") {
                        if($.trim(node.attr("id")) === "") {
                            return;
                        }
                        labelEle = $("[for='" + node.attr("id") + "']", node.parent());
                        if(!labelEle.is("label")) {
                            return;
                        }
                        nodeClass = labelEle.attr("class");
                        if(nodeClass && nodeClass !== "" && nodeClass !== css_ribbon_bigbutton) {
                            iconClass = nodeClass.split(" ")[0];
                            labelEle.removeClass(iconClass);
                            label = $.trim(labelEle.text());
                            if(label === "") {
                                text = false;
                            }
                        } else {
                            spans = labelEle.children("span,div");
                        }
                    } else if(type === "button") {
                        spans = node.children("span,div");
                    }
                    if(spans) {
                        if(spans.length === 1) {
                            if(spans.eq(0).attr("class") !== "") {
                                iconEle = spans.eq(0);
                                iconClass = iconEle.attr("class");
                                text = false;
                            }
                        } else if(spans.length === 2) {
                            if(spans.eq(0).attr("class")) {
                                iconEle = spans.eq(0);
                                iconClass = iconEle.attr("class");
                                if(iconEle.is("span")) {
                                    imagePosition = "left";
                                } else if(iconEle.is("div")) {
                                    imagePosition = "top";
                                }
                                label = spans.eq(1).text();
                            }
                        } else {
                            if(type === "button" && $.trim(node.text()) !== "") {
                                label = $.trim(node.text());
                            }
                        }
                    }
                }
                if(type === "button") {
                    node.empty();
                    name = $.trim(node.attr("name"));
                    if(name !== "") {
                        node.removeAttr("name");
                    }
                } else {
                    name = $.trim(labelEle.attr("name"));
                    if(name !== "") {
                        labelEle.removeAttr("name");
                    }
                }
                if(name !== "") {
                    node.data("commandName", name);
                    if(!self.buttons[name]) {
                        self.buttons[name] = {
                            button: undefined,
                            accessToolbarButton: undefined
                        };
                    }
                    if(node.parent().is(".gcui-ribbon-accesstoolbar")) {
                        self.buttons[name].accessToolbarButton = node;
                    } else {
                        self.buttons[name].button = node;
                    }
                }
                return {
                    label: label,
                    icons: {
                        primary: iconClass
                    },
                    position: imagePosition,
                    text: text
                };
            };
            gcuiribbon.prototype._triggerEvent = function (button) {
                var self = this, sameBtn, btnWidget, css = self.options.uiCSS;
                button.bind("click", function (e) {
                    if(self.options.disabled) {
                        e.preventDefault();
                        e.stopPropagation();
                        return;
                    }
                    self._hideAllMenus();
                    var checked = true, commandName = button.data("commandName"), buttonObj = self.buttons[commandName], obj = {
                        name: undefined,
                        commandName: undefined
                    };
                    if(buttonObj && buttonObj.parent) {
                        obj.name = buttonObj.parent.data("commandName");
                    }
                    obj.commandName = commandName;
                    self._trigger("click", e, obj);
                    if(button.parent().is(".gcui-ribbon-accesstoolbar")) {
                        sameBtn = self.buttons[commandName].button;
                    } else {
                        sameBtn = self.buttons[commandName].accessToolbarButton;
                    }
                    if(sameBtn) {
                        btnWidget = sameBtn.button("widget");
                        if(!sameBtn.is("button")) {
                            if(btnWidget && btnWidget.hasClass(css.stateActive)) {
                                checked = false;
                            }
                            self._setBtnWidgetCheckedStyle(sameBtn, checked);
                        }
                    }
                    if(button.is("button")) {
                        e.preventDefault();
                        e.stopPropagation();
                    }
                    $("." + css.buttonText, this).removeClass(css.stateHover);
                });
            };
            gcuiribbon.prototype._getDropdownLabelSubstr = function (text, label, width) {
                var self = this, length = text.length, start = 0, end = length, mid = Math.floor((start + end) / 2), newLabel = $("<span></span>"), str = text.substr(0, mid), midWidth;
                newLabel.appendTo(label.parent());
                midWidth = self._calculateWidth(str, newLabel);
                while(midWidth !== width && end > start) {
                    str = text.substr(0, mid + 1);
                    midWidth = self._calculateWidth(str, newLabel);
                    if(midWidth > width) {
                        end = mid - 1;
                    } else if(midWidth < width) {
                        start = mid + 1;
                    }
                    mid = Math.floor((start + end) / 2);
                }
                newLabel.remove();
                return str;
            };
            gcuiribbon.prototype._calculateWidth = function (str, span) {
                span.text(str);
                return span.width();
            };
            gcuiribbon.prototype.hideDropdown = function (name) {
                var self = this, dropdown = self.listUis[name];
                if(dropdown && dropdown.list) {
                    dropdown.list.hide();
                }
            };
            gcuiribbon.prototype._hideAllMenus = function () {
                var self = this;
                $(".gcui-menu", self.tabEle).hide();
            };
            gcuiribbon.prototype.setTabPageVisible = function (id, visible) {
                var self = this, tabpage = $("a[href=#" + id + "]", self.element).parent("li"), tab = tabpage.parent("ul"), selectedIndex;
                if(tabpage && tabpage.length > 0) {
                    if(visible) {
                        tabpage.show();
                        selectedIndex = $('li', tab).index(tabpage);
                    } else {
                        tabpage.hide();
                        selectedIndex = $('li', tab).index($('li:visible:not(".gcui-ribbon-startmenulinkcontainer")', tab));
                    }
                }
                self.element.gcuitabs("option", "selected", selectedIndex);
            };
            gcuiribbon.prototype.destroy = function () {
                if(this.disabledModal) {
                    this.disabledModal.remove();
                }
                $(window).unbind("resize." + this.widgetName);
                $.Widget.prototype.destroy.call(this);
            };
            gcuiribbon.prototype.getDropdownList = function (name) {
                var self = this, dropdown = self.listUis[name], retrunObj = {};
                if(dropdown) {
                    $.each(dropdown.buttons, function (i, button) {
                        retrunObj[button.data("commandName")] = button.button("option", "label");
                    });
                }
                return retrunObj;
            };
            gcuiribbon.prototype.setButtonDisabled = function (commandName, disabled) {
                var buttonObj = this.buttons[commandName], css = this.options.uiCSS, isButtonEle = false, button, accessBarBtn, splitUi;
                if(buttonObj && buttonObj.disabled) {
                    buttonObj.disabled(disabled);
                    return;
                }
                if(buttonObj && buttonObj.button && buttonObj.type === "galleryBtn") {
                    buttonObj.button.addClass(css.stateDisabled);
                }
                if(buttonObj && buttonObj.button) {
                    button = buttonObj.button;
                    accessBarBtn = buttonObj.accessToolbarButton;
                    isButtonEle = button.is("button");
                    if(buttonObj.type === "galleryBtn") {
                        if(disabled) {
                            button.addClass(css.stateDisabled);
                        } else {
                            button.removeClass(css.stateDisabled);
                        }
                        return;
                    }
                    button.button("option", "disabled", disabled);
                    if(accessBarBtn) {
                        accessBarBtn.button("option", "disabled", disabled);
                    }
                    if(isButtonEle) {
                        if (commandName !== "save") {
                            /* DO NOT change the text
                            button.children("." + css.buttonText).text(commandName);
                            */
                            if(accessBarBtn) {
                                button.children("." + css.buttonText).text(commandName);
                            }
                        }
                    } else {
                        $("[for='" + button.attr("id") + "']", button.parent()).children("." + css.buttonText).text(commandName);
                    }
                    splitUi = this.listUis[commandName];
                    if(splitUi && splitUi.type === "split") {
                        splitUi.trigger.button("option", "disabled", disabled);
                        splitUi.trigger.children("." + css.buttonText).text(commandName);
                    }
                }
            };
            gcuiribbon.prototype.setButtonsDisabled = function (commands) {
                var self = this;
                $.each(commands, function (key, value) {
                    self.setButtonDisabled(key, value);
                });
            };
            gcuiribbon.prototype.setButtonsChecked = function (commands) {
                var self = this;
                $.each(commands, function (key, value) {
                    if($.isPlainObject(value)) {
                        self.setButtonChecked(key, value.checked, value.name);
                    } else {
                        self.setButtonChecked(key, value, null);
                    }
                });
            };
            gcuiribbon.prototype.registerButtonInformation = function (cmdName, eleObj) {
                if(cmdName && eleObj) {
                    return this.buttons[cmdName] = eleObj;
                }
            };
            gcuiribbon.prototype.ribbonClick = function (e, obj) {
                this._trigger("click", e, obj);
            };
            gcuiribbon.prototype._setBtnWidgetCheckedStyle = function (buttonEle, checked) {
                var self = this, buttonEle, css = self.options.uiCSS;
                if(buttonEle.is("button")) {
                    if(checked) {
                        buttonEle.addClass(css.stateActive);
                    } else {
                        buttonEle.removeClass(css.stateActive);
                    }
                } else if(!buttonEle.is("input[type='text']")) {
                    buttonEle.prop("checked", checked);
                    buttonEle.button("refresh");
                }
                self._addActiveClassToButtonText(buttonEle.button("widget"));
            };
            gcuiribbon.prototype.setButtonChecked = function (commandName, checked, name) {
                var self = this, radios, buttonObj = self.buttons[commandName], css = this.options.uiCSS, buttonEle, accessBarBtnEle, listUi, label;
                if(buttonObj && buttonObj.checked) {
                    buttonObj.checked(checked);
                    return;
                }
                if(buttonObj && buttonObj.button) {
                    buttonEle = buttonObj.button;
                    accessBarBtnEle = buttonObj.accessToolbarButton;
                    if(buttonEle.is("button")) {
                        if(checked) {
                            buttonEle.addClass(css.stateActive);
                            if(accessBarBtnEle) {
                                accessBarBtnEle.addClass(css.stateActive);
                            }
                        } else {
                            buttonEle.removeClass(css.stateActive);
                            if(accessBarBtnEle) {
                                accessBarBtnEle.removeClass(css.stateActive);
                            }
                        }
                    } else if(buttonObj.type === "galleryBtn") {
                        var galleryEle = buttonEle.parent("div");
                        $("div", galleryEle).removeClass(css.stateActive);
                        if(checked) {
                            buttonEle.addClass(css.stateActive);
                        } else {
                            buttonEle.removeClass(css.stateActive);
                        }
                        return;
                    } else if(!buttonEle.is("input[type='text']")) {
                        buttonEle.prop("checked", checked);
                        buttonEle.button("refresh");
                        if(accessBarBtnEle) {
                            accessBarBtnEle.prop("checked", checked);
                            accessBarBtnEle.button("refresh");
                        }
                    }
                    if(buttonObj.parent) {
                        if(buttonObj.type === "dropdown") {
                            if(checked) {
                                if(!self.dropdownLabels[commandName]) {
                                    self.dropdownLabels[commandName] = self._getDropdownLabelSubstr(buttonEle.button("option", "label"), buttonObj.parent.children("." + css.buttonText), buttonObj.parent.children("." + css.buttonText).width());
                                }
                                label = self.dropdownLabels[commandName];
                                if(buttonObj.parent.is("input[type='text']")) {
                                    buttonObj.parent.val(label);
                                    buttonObj.parent.attr("title", label);
                                } else {
                                    buttonObj.parent.button("option", "label", label);
                                    buttonObj.parent.attr("title", buttonEle.button("option", "label"));
                                }
                                radios = $(":radio", buttonObj.button.closest("ul"));
                                if(radios) {
                                    $.each(radios, function () {
                                        self._updateGroupElementTextState(this);
                                    });
                                }
                            }
                        }
                    } else {
                        self._addActiveClassToButtonText(buttonEle.button("widget"));
                        if(accessBarBtnEle) {
                            self._addActiveClassToButtonText(accessBarBtnEle.button("widget"));
                        }
                    }
                } else if(name) {
                    listUi = self.listUis[name];
                    if(listUi) {
                        if(listUi.ui.is("input[type='text']")) {
                            listUi.ui.val(listUi.defaultValue);
                        } else {
                            listUi.ui.button("option", "label", listUi.defaultValue);
                        }
                        if(listUi.buttons) {
                            $.each(listUi.buttons, function (i, btn) {
                                btn.prop("checked", false);
                                btn.button("refresh");
                                self._addActiveClassToButtonText(btn.button("widget"));
                            });
                        }
                    }
                }
            };
            return gcuiribbon;
        })(gcui.gcuiWidget);
        ribbon.gcuiribbon = gcuiribbon;
        var ribbon_options = (function () {
            function ribbon_options() {
                this.initSelector = ":jqmData(role='gcuiribbon')";
                this.uiCSS = {
                    tabsHide: "gcui-gcuitabs-hide",
                    dropDownGroupSpace: 20
                };
                this.click = null;
                this.custombuttons = null;
            }
            return ribbon_options;
        })();        
        gcuiribbon.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, new ribbon_options());
        $.gcui.registerWidget("gcuiribbon", gcuiribbon.prototype);
    })(gcui.ribbon || (gcui.ribbon = {}));
    //var ribbon = gcui.ribbon;
})(gcui|| (gcui = {}));
