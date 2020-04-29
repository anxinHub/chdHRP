var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            var widgetName = "colorpicker";

            var ColorPicker = (function (_super) {
                __extends(ColorPicker, _super);
                function ColorPicker() {
                    _super.apply(this, arguments);
                }
                ColorPicker.prototype._create = function () {
                    var o = this.options;
                    this.colorItems = new Array();

                    var outDiv = $("<div class='colorpicker-outDiv'></div>");

                    var themeColorDiv = this._createThemeColorFrame();
                    var standardColorDiv = this._createStandardColorFrame();

                    if (!o.showThemeColorTitle) {
                        var themeTitleDiv = themeColorDiv.find('tr').get(0);
                        $(themeTitleDiv).css("display", "none");
                    }
                    if (!o.showStandardColorTitle) {
                        var standardColorTitleDiv = standardColorDiv.find('tr').get(0);
                        $(standardColorTitleDiv).css("display", "none");
                        var sp = $("<span class='colorpicker-horizontalseparator'></span>");
                        $(sp).css("margin", "5px");
                        themeColorDiv.append(sp);
                        $(themeColorDiv).find('.colorpicker-colorcell').css("margin", "1px");
                    }
                    if (!o.showOutline) {
                        outDiv.css("border", "0px");
                    }

                    outDiv.append(themeColorDiv, standardColorDiv);

                    if (o.showNoFill) {
                        var noColorDiv = this._createNoColorFrame();
                        outDiv.append(noColorDiv);
                    }
                    if (o.showMoreColors) {
                        var sp = $("<span class='colorpicker-horizontalseparator'></span>");

                        var moreColorDiv = this._createMoreColorFrame();
                        outDiv.append(sp, moreColorDiv);
                    }

                    outDiv.appendTo(this.element);

                    //forbidden select text.
                    outDiv.bind("selectstart", function () {
                        return false;
                    });
                    this._addEvents();

                    if (this.dialog === null || this.dialog === undefined) {
                        this.dialog = new ColorDailog();
                        this.dialog._create();
                        var self = this;
                        $(this.dialog).on('dialogClose', function (evt, isCloseByOK) {
                            if (isCloseByOK) {
                                var val;
                                val = { color: self.dialog.value };
                                self._clearSelection();
                                self._setSelection(self.dialog.value);
                                self.options.value = self.dialog.value;
                                self.options.selectedItem = { name: "", baseColor: "", tint: 0, color: self.dialog.value };
                                self._raiseValueChanged(evt, val);
                            }
                        });
                    }
                };

                ColorPicker.prototype._createThemeColorFrame = function () {
                    var computedcolors = this._initThemeColors(this.options.themeColors);

                    var div2 = $('<div></div>');
                    var table2 = $("<table class='colorpicker-colortable' cellspacing= 0 cellpadding= 0></table>");
                    var tbody2 = $("<tbody></tbody>");
                    var tr2_1 = $('<tr></tr>');
                    var td2_1 = $('<td></td>');
                    td2_1.attr("colspan", "10");
                    td2_1.append($("<div class='colorpicker-title colorpicker-div-themecolor-title'></div>").text(this.options.themeColorsTitle));
                    tr2_1.append(td2_1);
                    tbody2.append(tr2_1);
                    for (var row = 0; row < 6; row++) {
                        var tr = $('<tr></tr>');
                        this.colorItems[row] = new Array();
                        if (row === 0) {
                            tr.addClass('colorpicker-themecolors-firstline');
                        }
                        for (var col = 0; col < 10; col++) {
                            var td = $('<td></td>');
                            var div = $('<div></div>');
                            div.addClass("colorpicker-colorcell");
                            if (row === 0) {
                                div.css("background", this.options.themeColors[col].baseColor);
                                div.attr('row', '0');
                                div.attr('col', col.toString());
                                this.colorItems[row][col] = { name: this.options.themeColors[col].name, baseColor: this.options.themeColors[col].baseColor, tint: 0, color: this.options.themeColors[col].baseColor };
                            } else {
                                div.css("background", computedcolors[col * 5 + row - 1].color);
                                div.attr('row', row.toString());
                                div.attr('col', col.toString());
                                this.colorItems[row][col] = { name: this.options.themeColors[col].name + " " + computedcolors[col * 5 + row - 1].tint.toString(), baseColor: this.options.themeColors[col].baseColor, tint: computedcolors[col * 5 + row - 1].tint, color: computedcolors[col * 5 + row - 1].color };
                            }
                            div.append($("<div class='colorpicker-div-inner-colorcell'></div>"));
                            td.append(div);
                            tr.append(td);
                        }
                        tbody2.append(tr);
                    }
                    table2.append(tbody2);
                    div2.append(table2);
                    return div2;
                };

                ColorPicker.prototype._createStandardColorFrame = function () {
                    this.colorItems[6] = new Array();

                    var div3 = $('<div></div>');
                    var table3 = $("<table class='colorpicker-colortable' cellspacing= 0 cellpadding= 0></table>");
                    var tbody3 = $('<tbody></tbody>');
                    var tr3_1 = $('<tr></tr>');
                    var td3_1 = $('<td></td>');
                    td3_1.attr("colspan", "10");
                    td3_1.append($("<div class='colorpicker-title colorpicker-div-standardcolor-title'></div>").text(this.options.standardColorsTitle));
                    tr3_1.append(td3_1);
                    tbody3.append(tr3_1);
                    var tr3_2 = $('<tr></tr>');
                    for (var col = 0; col < 10; col++) {
                        var td3_2 = $('<td></td>');
                        var div3_2 = $('<div></div>');
                        div3_2.css("background", this.options.standardColors[col].baseColor);
                        div3_2.attr('row', '6');
                        div3_2.attr('col', col.toString());
                        this.colorItems[6][col] = { name: this.options.standardColors[col].name, baseColor: this.options.standardColors[col].baseColor, tint: 0, color: this.options.standardColors[col].baseColor };
                        div3_2.addClass("colorpicker-colorcell");
                        div3_2.append($("<div class='colorpicker-div-inner-colorcell'></div>"));
                        td3_2.append(div3_2);
                        tr3_2.append(td3_2);
                    }
                    tbody3.append(tr3_2);
                    table3.append(tbody3);
                    div3.append(table3);
                    return div3;
                };

                ColorPicker.prototype._createNoColorFrame = function () {
                    var divNoColor = $("<div class='colorpicker-div-nocolor'></div>");
                    divNoColor.append($("<table class='colorpicker-table-nocolor'><tbody><tr><td class='colorpicker-td-nocolor-image'><div class='colorpicker-div-out-nofill' ><div class='colorpicker-div-nocolor-image'></div></div></td><td><span class='colorpicker-verticalseparator'></span><label class='colorpicker-Labeltext colorpicker-label-nofill-text'>" + this.options.noFillText + "</label></td></tr></tbody></table>"));
                    return divNoColor;
                };

                ColorPicker.prototype._createMoreColorFrame = function () {
                    var div4 = $("<div class='colorpicker-div-morecolors' ></div>");
                    div4.append($("<table class='colorpicker-table-morecolors'><tbody><tr><td class='colorpicker-td-morecolors-image'><div class='colorpicker-div-morecolor-icon'></div></td><td><span class='colorpicker-verticalseparator'></span><label class='colorpicker-Labeltext colorpicker-label-morecolors-text'>" + this.options.moreColorsText + "</label></td></tr></tbody></table>"));
                    return div4;
                };

                ColorPicker.prototype._addEvents = function () {
                    var _this = this;
                    this.element.find('.colorpicker-colorcell').on('mouseover.colorpicker', function (evt) {
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        if (ele.className.indexOf("colorpicker-div-inner-colorcell") !== -1) {
                            ele = ele.parentElement;
                        }
                        if (_this.options.value == "" || ele.style.background != _this.options.value) {
                            $(ele).addClass("hover");
                            var innerele = ele.childNodes[0];
                            $(innerele).addClass("hover");
                        }
                    });
                    this.element.find('.colorpicker-colorcell').on('mousedown.colorpicker', function (evt) {
                        var col, row;
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        if (ele.className.indexOf("colorpicker-div-inner-colorcell") !== -1) {
                            ele = ele.parentElement;
                        }
                        col = $(ele).attr('col');
                        row = $(ele).attr('row');
                        _this._raiseChoosedColor(evt, _this.colorItems[row][col]);
                        if ($(ele).hasClass('selected')) {
                            return;
                        }

                        $(ele).removeClass('hover').addClass("selected");
                        var innerele = ele.childNodes[0];

                        $(innerele).removeClass('hover').addClass("selected");

                        //Restore before Color Cell back to orignal border.
                        if (_this.options.value !== "") {
                            if (_this.options.value === undefined) {
                                _this.element.find('.colorpicker-div-out-nofill').removeClass('selected');
                            } else {
                                var allCells = _this.element.find('.colorpicker-colorcell');
                                for (var i = 0; i < allCells.length; i++) {
                                    if (allCells[i].style.background === _this.options.value) {
                                        $(allCells[i]).removeClass('selected');
                                        var ic = allCells[i].childNodes[0];
                                        $(ic).removeClass('selected');
                                    }
                                }
                            }
                        }
                        _this.options.value = _this._convertColor(ele.style.background);
                        _this.colorItems[row][col].color = _this._convertColor(_this.colorItems[row][col].color);
                        _this.options.selectedItem = _this.colorItems[row][col];
                        _this._raiseValueChanged(evt, _this.colorItems[row][col]);
                    });
                    this.element.find('.colorpicker-colorcell').on('mouseout.colorpicker', function (evt) {
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        if (ele.className.indexOf("colorpicker-div-inner-colorcell") !== -1) {
                            ele = ele.parentElement;
                        }
                        if (_this.options.value == "" || ele.style.background != _this.options.value) {
                            $(ele).removeClass('hover');

                            //ele.style.border = "1px solid rgb(195, 195, 195)";
                            var innerele = ele.childNodes[0];

                            //innerele.style.borderColor = "rgba(0,0,0,0)";
                            $(innerele).removeClass('hover');
                        }
                    });

                    this.element.find('.colorpicker-div-nocolor,.colorpicker-div-morecolors').on('mouseover.colorpicker', function (evt) {
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        var divNoColor = _this.element.find('.colorpicker-div-nocolor');
                        var divMoreColor = _this.element.find('.colorpicker-div-morecolors');
                        if (divNoColor.length > 0 && divNoColor[0].contains(ele)) {
                            divNoColor.addClass('hovered');
                        } else if (divMoreColor.length > 0 && divMoreColor[0].contains(ele)) {
                            divMoreColor.addClass('hovered');
                        }
                    });
                    this.element.find('.colorpicker-div-nocolor').on('mousedown.colorpicker', function (evt) {
                        //this.element.find('.colorpicker-div-out-nofill').css("border", "");
                        //this.element.find('.colorpicker-div-out-nofill').addClass('selected');
                        _this.element.find('.colorpicker-div-out-nofill').addClass('selected');

                        //Restore before Color Cell back to orignal border.
                        if (_this.options.value !== "") {
                            var allCells = _this.element.find('.colorpicker-colorcell');
                            for (var i = 0; i < allCells.length; i++) {
                                if (allCells[i].style.background === _this.options.value) {
                                    $(allCells[i]).removeClass('selected');

                                    //allCells[i].style.border = "1px solid rgb(195, 195, 195)";
                                    var ic = allCells[i].childNodes[0];

                                    //ic.style.borderColor = "rgba(0,0,0,0)";
                                    $(ic).removeClass('selected');
                                    break;
                                }
                            }
                        }
                        _this.options.value = undefined;
                        _this.options.selectedItem = undefined;
                        _this._raiseValueChanged(evt, undefined);
                        _this._raiseChoosedColor(evt, undefined);
                    });
                    this.element.find('.colorpicker-div-nocolor,.colorpicker-div-morecolors').on('mouseout.colorpicker', function (evt) {
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        var divNoColor = _this.element.find('.colorpicker-div-nocolor');
                        var divMoreColor = _this.element.find('.colorpicker-div-morecolors');

                        //$(ele).removeClass('hovered');
                        if (divNoColor.length > 0 && divNoColor[0].contains(ele)) {
                            divNoColor.removeClass('hovered');
                        } else if (divMoreColor.length > 0 && divMoreColor[0].contains(ele)) {
                            divMoreColor.removeClass('hovered');
                        }
                    });

                    this.element.find('.colorpicker-div-morecolors').on('click.colorpicker', function (evt) {
                        _this._raiseOpenColorDialog(evt);
                        _this._showColorDialog();
                    });
                };

                ColorPicker.prototype._removeEvent = function () {
                    this.element.find('.colorpicker-colorcell,.colorpicker-div-nocolor,.colorpicker-div-morecolors').off('.colorpicker');
                };

                ColorPicker.prototype._showColorDialog = function () {
                    this.dialog.value = this.options.value;
                    if (this.dialog.value === "" || this.dialog.value === undefined) {
                        //this.dialog._updateColor(undefined, undefined, undefined, true, "rgb(0,0,0)");
                        this.dialog._updateColor(true, "rgb(0,0,0)");
                    } else {
                        //this.dialog._updateColor(undefined, undefined, undefined, true, this.dialog.value);
                        this.dialog._updateColor(true, this.dialog.value);
                    }
                    this.dialog.open();
                };

                ColorPicker.prototype._setOption = function (key, value) {
                    if (key === 'noFillText') {
                        this.options.noFillText = value;
                        if (this.options.showNoFill) {
                            this.element.find('.colorpicker-label-nofill-text').text(value);
                        }
                    } else if (key === 'moreColorsText') {
                        this.options.moreColorsText = value;
                        if (this.options.showMoreColors) {
                            this.element.find('.colorpicker-label-morecolors-text').text(value);
                        }
                    } else if (key === 'showNoFill') {
                        this.options.showNoFill = value;
                        this._redraw();
                    } else if (key === 'showMoreColors') {
                        this.options.showMoreColors = value;
                        this._redraw();
                    } else if (key === 'themeColorsTitle') {
                        this.options.themeColorsTitle = value;
                        this.element.find('.colorpicker-div-themecolor-title').text(value);
                    } else if (key === 'standardColorsTitle') {
                        this.options.standardColorsTitle = value;
                        this.element.find('.colorpicker-div-standardcolor-title').text(value);
                    } else if (key === 'themeColors') {
                        this.options.themeColors = value;
                        this._redraw();
                    } else if (key === 'standardColors') {
                        this.options.standardColors = value;
                        this._redraw();
                    } else if (key === 'value') {
                        value = this._convertColor(value);
                        this._setSelection(value);
                        this.options.value = value;
                        this.options.selectedItem = { name: "", baseColor: "", tint: 0, color: value };
                    } else if (key === 'selectedItem') {
                        if (value === undefined || value === null) {
                            this._clearSelection();
                            this.options.selectedItem = value;
                            this.options.value = value;
                        } else {
                            value.color = this._convertColor(value.color);
                            this._setSelectionbyItem(value);
                            this.options.selectedItem = value;
                            this.options.value = this.options.selectedItem.color;
                        }
                    } else if (key === 'showThemeColorTitle') {
                        this.options.showThemeColorTitle = value;
                        this._redraw();
                    } else if (key === 'showStandardColorTitle') {
                        this.options.showStandardColorTitle = value;
                        this._redraw();
                    } else if (key === 'showOutline') {
                        this.options.showOutline = value;
                        this._redraw();
                    }
                };

                ColorPicker.prototype._redraw = function () {
                    this._removeEvent();
                    this.element.empty();
                    this._create();
                };
                ColorPicker.prototype._convertColor = function (value) {
                    return $("<span></span>").css('background-color', value).css('background-color');
                };
                ColorPicker.prototype._setSelection = function (value) {
                    //Restore before Color Cell back to orignal border.
                    if (this.options.value !== "") {
                        if (this.options.value === undefined) {
                            this.element.find('.colorpicker-div-out-nofill').removeClass('selected');
                        } else {
                            var allCells = this.element.find('.colorpicker-colorcell');
                            for (var i = 0; i < allCells.length; i++) {
                                if (allCells[i].style.background === this.options.value) {
                                    $(allCells[i]).removeClass('selected');
                                    var ic = allCells[i].childNodes[0];
                                    $(ic).removeClass('selected');
                                    break;
                                }
                            }
                        }
                    }

                    if (value !== "") {
                        if (value === undefined) {
                            this.element.find('.colorpicker-div-out-nofill').addClass('selected');
                        } else {
                            var allCells = this.element.find('.colorpicker-colorcell');
                            for (var i = 0; i < allCells.length; i++) {
                                if (allCells[i].style.background === value) {
                                    $(allCells[i]).addClass('selected');
                                    var innerele = (allCells[i].childNodes[0]);
                                    $(innerele).addClass('selected');
                                    break;
                                }
                            }
                        }
                    }
                };
                ColorPicker.prototype._clearSelection = function () {
                    //Restore before Color Cell back to orignal border.
                    if (this.options.selectedItem !== null) {
                        if (this.options.selectedItem === undefined) {
                            this.element.find('.colorpicker-div-out-nofill').removeClass('selected');
                        } else {
                            var allCells = this.element.find('.colorpicker-colorcell');
                            if (this.options.selectedItem.name !== null && this.options.selectedItem.name !== undefined) {
                                var row = -1;
                                var col = -1;
                                for (var j = 0; j < this.colorItems.length; j++) {
                                    for (var k = 0; k < this.colorItems[j].length; k++) {
                                        if (this.colorItems[j][k].name === this.options.selectedItem.name) {
                                            row = j;
                                            col = k;
                                            break;
                                        }
                                    }
                                    if (row !== -1 && col !== -1) {
                                        break;
                                    }
                                }

                                for (var i = 0; i < allCells.length; i++) {
                                    if (($(allCells[i]).attr("row") === row.toString()) && ($(allCells[i]).attr("col") === col.toString())) {
                                        $(allCells[i]).removeClass('selected');
                                        var ic = allCells[i].childNodes[0];
                                        $(ic).removeClass('selected');
                                        break;
                                    }
                                }
                            } else {
                                for (var i = 0; i < allCells.length; i++) {
                                    if (allCells[i].style.background === this.options.selectedItem.color) {
                                        $(allCells[i]).removeClass('selected');
                                        var ic = allCells[i].childNodes[0];
                                        $(ic).removeClass('selected');
                                        break;
                                    }
                                }
                            }
                        }
                    }
                };
                ColorPicker.prototype._setSelectionbyItem = function (item) {
                    this._clearSelection();
                    if (item !== "") {
                        if (item === undefined) {
                            this.element.find('.colorpicker-div-out-nofill').addClass('selected');
                        } else {
                            var allCells = this.element.find('.colorpicker-colorcell');
                            if (item.name !== null && item.name !== undefined) {
                                var row = -1;
                                var col = -1;
                                for (var j = 0; j < this.colorItems.length; j++) {
                                    for (var k = 0; k < this.colorItems[j].length; k++) {
                                        if (this.colorItems[j][k].name === item.name) {
                                            row = j;
                                            col = k;
                                            break;
                                        }
                                    }
                                    if (row !== -1 && col !== -1) {
                                        break;
                                    }
                                }

                                for (var i = 0; i < allCells.length; i++) {
                                    if (($(allCells[i]).attr("row") === row.toString()) && ($(allCells[i]).attr("col") === col.toString())) {
                                        $(allCells[i]).addClass('selected');
                                        var innerele = (allCells[i].childNodes[0]);
                                        $(innerele).addClass('selected');
                                        break;
                                    }
                                }
                            } else {
                                for (var i = 0; i < allCells.length; i++) {
                                    if (allCells[i].style.background === item.color) {
                                        $(allCells[i]).addClass('selected');
                                        var innerele = (allCells[i].childNodes[0]);
                                        $(innerele).addClass('selected');
                                        break;
                                    }
                                }
                            }
                        }
                    }
                };
                ColorPicker.prototype._raiseValueChanged = function (evt, v) {
                    this._trigger('valueChanged', evt, v);
                };
                ColorPicker.prototype._raiseOpenColorDialog = function (evt) {
                    this._trigger('openColorDialog', evt);
                };
                ColorPicker.prototype._raiseChoosedColor = function (evt, v) {
                    this._trigger('choosedColor', evt, v);
                };
                ColorPicker.prototype._initThemeColors = function (tc) {
                    var firstLinecolors = [];
                    for (var i = 0; i < tc.length; i++) {
                        firstLinecolors.push(tc[i].baseColor);
                    }
                    var columnCount = 5;
                    var themeColors = new Array();
                    var WhiteBlackChanges = [-5, -15, -25, -35, -50];
                    var LightDarkChanges = [-10, -25, -50, -75, -90];
                    var LightDarkDegree = [80, 60, 40, -25, -50];
                    for (var i = 0; i < 10; i++) {
                        var cb = ColorHelper._getColorBrightness(firstLinecolors[i]);
                        for (var j = 0; j < 5; j++) {
                            if (cb === 1) {
                                //white
                                themeColors.push({ color: this._updateTint(firstLinecolors[i], WhiteBlackChanges[j]), tint: WhiteBlackChanges[j] });
                            } else if (cb >= 0.8) {
                                //LightDark, IsDark
                                themeColors.push({ color: this._updateTint(firstLinecolors[i], LightDarkChanges[j]), tint: LightDarkChanges[j] });
                            } else if (cb === 0) {
                                //black
                                themeColors.push({ color: this._updateTint(firstLinecolors[i], Math.abs(WhiteBlackChanges[columnCount - j - 1])), tint: Math.abs(WhiteBlackChanges[columnCount - j - 1]) });
                            } else if (cb <= 0.2) {
                                //LightDark, IsLight
                                themeColors.push({ color: this._updateTint(firstLinecolors[i], Math.abs(LightDarkChanges[columnCount - j - 1])), tint: Math.abs(LightDarkChanges[columnCount - j - 1]) });
                            } else {
                                themeColors.push({ color: this._updateTint(firstLinecolors[i], LightDarkDegree[j]), tint: LightDarkDegree[j] });
                            }
                        }
                    }
                    return themeColors;
                };

                ColorPicker.prototype._updateTint = function (color, tint) {
                    tint = tint / 100.0;
                    if (tint === 0) {
                        return color;
                    }
                    var hls = ColorHelper._hLSColor(color, false);
                    var lumDiff = parseInt((tint > 0 ? ((240 - hls.luminosity) * tint) : (hls.luminosity * tint)).toString(), 10);
                    var newColor = ColorHelper._colorFromHLS(hls.hue, hls.luminosity + lumDiff, hls.saturation);
                    return "rgb(" + newColor.r.toString() + ", " + newColor.g.toString() + ", " + newColor.b.toString() + ")";
                };

                ColorPicker.prototype.destroy = function () {
                    this._removeEvent();
                    this.element.empty();
                    if (this.dialog) {
                        this.dialog.destroy();
                    }
                };
                ColorPicker.DefaultOptions = {
                    themeColors: [
                        {
                            name: 'text 1',
                            baseColor: '#FFFFFF'
                        },
                        {
                            name: 'background 1',
                            baseColor: '#000000'
                        },
                        {
                            name: 'text 2',
                            baseColor: '#E7E6E6'
                        },
                        {
                            name: 'background 2',
                            baseColor: '#44546A'
                        },
                        {
                            name: 'accent 1',
                            baseColor: '#5B9BD5'
                        },
                        {
                            name: 'accent 2',
                            baseColor: '#ED7D31'
                        },
                        {
                            name: 'accent 3',
                            baseColor: '#8064A2'
                        },
                        {
                            name: 'accent 4',
                            baseColor: '#4BACC6'
                        },
                        {
                            name: 'accent 5',
                            baseColor: '#F79646'
                        },
                        {
                            name: 'accent 6',
                            baseColor: '#0000FF'
                        }
                    ],
                    themeColorsTitle: designer.res.colorPicker.themeColorsTitle,
                    standardColors: [
                        {
                            baseColor: '#C00000'
                        },
                        {
                            baseColor: '#FF0000'
                        },
                        {
                            baseColor: '#FFC000'
                        },
                        {
                            baseColor: '#FFFF00'
                        },
                        {
                            baseColor: '#92D050'
                        },
                        {
                            baseColor: '#00B050'
                        },
                        {
                            baseColor: '#00B0F0'
                        },
                        {
                            baseColor: '#0070C0'
                        },
                        {
                            baseColor: '#002060'
                        },
                        {
                            baseColor: '#7030A0'
                        }
                    ],
                    standardColorsTitle: designer.res.colorPicker.standardColorsTitle,
                    showNoFill: true,
                    noFillText: designer.res.colorPicker.noFillText,
                    showMoreColors: true,
                    moreColorsText: designer.res.colorPicker.moreColorsText,
                    value: "",
                    showThemeColorTitle: true,
                    showStandardColorTitle: true,
                    showOutline: true
                };
                return ColorPicker;
            })(gcui.gcuiWidget);
            designer.ColorPicker = ColorPicker;
            ColorPicker.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, ColorPicker.DefaultOptions);
            $.gcui.registerWidget(widgetName, ColorPicker.prototype);

            var ColorDailog = (function () {
                function ColorDailog() {
                    this.value = "";
                }
                ColorDailog.prototype.open = function () {
                    this.innerDialog.dialog('open');
                    if (this.value === "") {
                        this.innerDialog.find('.colordialog-div-currentcolor').css('background', "rgb(0,0,0)");
                        this.innerDialog.find('.colordialog-div-newcolor').css('background', "rgb(0,0,0)");
                    } else if (this.value === undefined) {
                        this.innerDialog.find('.colordialog-div-currentcolor').css('background', "rgb(255,255,255)");
                        this.innerDialog.find('.colordialog-div-newcolor').css('background', "rgb(255,255,255)");
                    } else {
                        this.innerDialog.find('.colordialog-div-currentcolor').css('background', this.value);
                        this.innerDialog.find('.colordialog-div-newcolor').css('background', this.value);
                    }
                };
                ColorDailog.prototype._create = function () {
                    var self = this;
                    this.hls = {
                        luminosity: 128,
                        saturation: 0,
                        hue: 0
                    };
                    this.isDragingLum = false;
                    this.isDragingHueSat = false;
                    this.innerDialog = $("<div title='Color' class='colordialog-div-container'></div>");
                    this.innerDialog.dialog({
                        title: designer.res.colorPicker.colorDialogTitle,
                        autoOpen: false,
                        resizable: false,
                        width: 280,
                        modal: true,
                        buttons: [
                            {
                                text: designer.res.ok,
                                click: function (evt) {
                                    $(this).dialog("close");
                                    self.value = "rgb(" + self.innerDialog.find('.colordialog-input-red').val() + "," + self.innerDialog.find('.colordialog-input-green').val() + "," + self.innerDialog.find('.colordialog-input-blue').val() + ")";
                                    self._raiseClose(evt, true);
                                }
                            },
                            {
                                text: designer.res.cancel,
                                click: function (evt) {
                                    $(this).dialog("close");
                                    self._raiseClose(evt, false);
                                }
                            }
                        ],
                        beforeClose: function (event, ui) {
                            if (event.currentTarget) {
                                self._raiseClose(event, false);
                            }
                        }
                    }).append($("<table><tbody><tr><td><div class='colordialog-div-pickercolor-image'><span class='colordialog-span-pickercolor-mousepointer'></span></div></td><td><div class='colordialog-div-dragcolor-container'><div class='colordialog-div-dragcolor'></div><span class='colordialog-span-dragcolor-arrow'></span></div></td></tr><tr><td><table><tbody><tr><td class='red-input-label'>Red:</td><td><input class='colordialog-rgb colordialog-input-red'></input></td></tr><tr><td class='green-input-label'>Green:</td><td><input class='colordialog-rgb colordialog-input-green'></input></td></tr><tr><td class='blue-input-label'>Blue:</td><td><input class='colordialog-rgb colordialog-input-blue'></input></td></tr></tbody></table></td><td><table class='colordialog-table-newcurrentcolor'><tbody><tr><td class='new-label'>New</td></tr><tr><td><div class='colordialog-div-newcolor'></div></td></tr><tr><td><div class='colordialog-div-currentcolor'></div></td></tr><tr><td class='current-label'>Current</td></tr></tbody></table></td></tr></tbody></table>"));

                    this.innerDialog.find(".colordialog-table-newcurrentcolor").attr('cellSpacing', '0px').attr('cellPadding', '0px');
                    this.innerDialog.find(".red-input-label").text(designer.res.colorPicker.red);
                    this.innerDialog.find(".green-input-label").text(designer.res.colorPicker.green);
                    this.innerDialog.find(".blue-input-label").text(designer.res.colorPicker.blue);
                    this.innerDialog.find(".new-label").text(designer.res.colorPicker.newLabel);
                    this.innerDialog.find(".current-label").text(designer.res.colorPicker.currentLabel);

                    //forbidden select text.
                    //.bind("selectstart", function () { return false; })
                    this.innerDialog.find(".colordialog-rgb").spinner({
                        max: 255,
                        min: 0,
                        change: function () {
                            self._updateColorDraw();
                        },
                        //stop: _updateColorDraw,
                        spin: function () {
                            self._updateColorDraw();
                        }
                    });

                    if (this.value !== "") {
                        this.innerDialog.find('.colordialog-div-currentcolor').css('background', this.value);
                    }
                    if (this.value === "") {
                        var dragColor = this.innerDialog.find('.colordialog-div-dragcolor');
                        dragColor.css("background-image", "linear-gradient(to bottom, rgb(255,  255, 255), rgb(0, 0, 0))");
                        dragColor.css("background-image", "-o-linear-gradient(to bottom, rgb(255,  255, 255), rgb(0, 0, 0))");
                        dragColor.css("background-image", "-moz-linear-gradient(to bottom, rgb(255,  255, 255), rgb(0, 0, 0))");
                        dragColor.css("background-image", "-webkit-linear-gradient(to bottom, rgb(255,  255, 255), rgb(0, 0, 0))");
                        dragColor.css("background-image", "-ms-linear-gradient(to bottom, rgb(255,  255, 255), rgb(0, 0, 0))");
                        dragColor.css("background-image", "-webkit-gradient(linear, left top, left bottom, color-stop(0, rgb(255,  255, 255)),  color-stop(1, rgb(0, 0, 0)))");
                        this.innerDialog.find('.colordialog-input-red').spinner().spinner('value', 0);
                        this.innerDialog.find('.colordialog-input-green').spinner().spinner('value', 0);
                        this.innerDialog.find('.colordialog-input-blue').spinner().spinner('value', 0);
                    } else {
                        this._updateColor(true, this.value);
                    }

                    this._addEvent();
                };

                ColorDailog.prototype._updateColorDraw = function () {
                    var self = this;
                    if (self.isDragingLum) {
                        return;
                    }
                    if (self.innerDialog.find('.colordialog-input-red').val() == "") {
                        self.innerDialog.find('.colordialog-input-red').spinner().spinner('value', 0);
                    }
                    if (self.innerDialog.find('.colordialog-input-green').val() == "") {
                        self.innerDialog.find('.colordialog-input-green').spinner().spinner('value', 0);
                    }
                    if (self.innerDialog.find('.colordialog-input-blue').val() == "") {
                        self.innerDialog.find('.colordialog-input-blue').spinner().spinner('value', 0);
                    }
                    var r = parseInt(self.innerDialog.find('.colordialog-input-red').val());
                    var g = parseInt(self.innerDialog.find('.colordialog-input-green').val());
                    var b = parseInt(self.innerDialog.find('.colordialog-input-blue').val());
                    if (r.toString() === "NaN") {
                        r = 0;
                    }
                    if (g.toString() === "NaN") {
                        g = 0;
                    }
                    if (b.toString() === "NaN") {
                        b = 0;
                    }
                    var rgb = "rgb(" + r.toString() + "," + g.toString() + "," + b.toString() + ")";

                    self._updateColor(true, rgb);

                    var color;
                    color = {
                        a: 0xff,
                        r: r,
                        g: g,
                        b: b
                    };

                    var hlsC = ColorHelper._hLSColor(color, true);
                    self.hls.hue = Math.floor(hlsC.hue / 240 * 255);
                    self.hls.luminosity = Math.floor(hlsC.luminosity / 240 * 255);
                    self.hls.saturation = Math.floor(hlsC.saturation / 240 * 255);
                    if (!self.isDragingLum && !self.isDragingHueSat) {
                        self.innerDialog.find('.colordialog-span-dragcolor-arrow').css('display', 'block').css('top', 160 - self.hls.luminosity / 255 * 160 + 4);
                    }
                    self.innerDialog.find('.colordialog-span-pickercolor-mousepointer').css('display', 'block').css('left', self.hls.hue / 255 * 190 - 5).css('top', 160 - self.hls.saturation / 255 * 160 - 5);
                };

                ColorDailog.prototype._addEvent = function () {
                    var _this = this;
                    this.innerDialog.find('.colordialog-div-pickercolor-image').mousedown(function (evt) {
                        _this.isDragingHueSat = true;
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        var x, y;
                        if (ele.className.indexOf("colordialog-span-pickercolor-mousepointer") !== -1) {
                            var ele2 = ele;
                            x = evt.offsetX + parseInt(ele2.style.left);
                            y = evt.offsetY + parseInt(ele2.style.top);
                        } else {
                            x = evt.offsetX;
                            y = evt.offsetY;
                        }
                        var width = 190;
                        var height = 160;
                        _this.hls.saturation = (height - y) / height * 255;
                        _this.hls.hue = x / width * 255;
                        _this._updateColor(false, undefined);
                        _this.innerDialog.find('.colordialog-span-pickercolor-mousepointer').css('display', 'block').css('left', x - 5).css('top', y - 5);
                    });
                    $(document).mouseup(function (evt) {
                        _this.isDragingHueSat = false;
                        _this.isDragingLum = false;
                        //$(document).off('mousemove');
                    });
                    this.innerDialog.find('.colordialog-div-pickercolor-image').mousemove(function (evt) {
                        if (_this.isDragingHueSat) {
                            var srcElement = evt.target || evt.srcElement;
                            var ele = srcElement;
                            var x, y;
                            if (ele.className.indexOf('colordialog-span-pickercolor-mousepointer') !== -1) {
                                var ele2 = ele;
                                x = evt.offsetX + parseInt(ele2.style.left);
                                y = evt.offsetY + parseInt(ele2.style.top);
                            } else {
                                x = evt.offsetX;
                                y = evt.offsetY;
                            }
                            var width = 190;
                            var height = 160;
                            _this.hls.saturation = (height - y) / height * 255;
                            _this.hls.hue = x / width * 255;
                            _this._updateColor(false, undefined);
                            _this.innerDialog.find('.colordialog-span-pickercolor-mousepointer').css('display', 'block').css('left', x - 5).css('top', y - 5);
                        }
                    });

                    var startY, startClientY;
                    this.innerDialog.find('.colordialog-div-dragcolor-container').mousedown(function (evt) {
                        _this.isDragingLum = true;
                        var srcElement = evt.target || evt.srcElement;
                        var ele = srcElement;
                        var y;
                        if (ele.className.indexOf('colordialog-span-dragcolor-arrow') !== -1) {
                            var ele2 = ele;
                            y = evt.offsetY + parseInt(ele2.style.top) - 8;
                        } else {
                            y = evt.offsetY;
                        }
                        if (y > 160) {
                            y = 160;
                        }
                        if (y < 0) {
                            y = 0;
                        }
                        _this.innerDialog.find('.colordialog-span-dragcolor-arrow').css('display', 'block').css('top', y + 4);
                        var height = 160;
                        _this.hls.luminosity = (160 - y) / height * 255;
                        _this._updateColor(false, undefined);

                        startY = y;
                        startClientY = evt.clientY;

                        $(document).on('mousemove', mousemove);
                    });

                    var mousemove = function (evt) {
                        if (_this.isDragingLum) {
                            var y = evt.clientY - startClientY + startY;

                            if (y > 160) {
                                y = 160;
                            }
                            if (y < 0) {
                                y = 0;
                            }
                            _this.innerDialog.find('.colordialog-span-dragcolor-arrow').css('display', 'block').css('top', y + 4);
                            var height = 160;
                            _this.hls.luminosity = (160 - y) / height * 255;
                            _this._updateColor(false, undefined);
                        }
                    };
                };
                ColorDailog.prototype._raiseClose = function (evt, v) {
                    $(this).trigger('dialogClose', v);
                };
                ColorDailog.prototype._updateColor = function (needUpdateDragColor, rgbcolor) {
                    var color;
                    var colorOfDragMiddle;
                    if (rgbcolor) {
                        var s = rgbcolor.split(',');
                        var s1 = s[0].split('(');
                        var s3 = s[2].split(')');
                        var r = s1[1];
                        var g = s[1];
                        var b = s3[0];
                        color = {
                            a: 0xff,
                            r: parseInt(r),
                            g: parseInt(g),
                            b: parseInt(b)
                        };
                        if (needUpdateDragColor) {
                            var hls = ColorHelper._hLSColor(color, true);
                            colorOfDragMiddle = ColorHelper._colorFromHLS(Math.floor(hls.hue / 255 * 240), Math.floor(128 / 255 * 240), Math.floor(hls.saturation / 255 * 240));
                        }
                    } else {
                        if (this.hls.luminosity === 0) {
                            color = {
                                a: 0xff,
                                r: 0,
                                g: 0,
                                b: 0
                            };
                        } else if (this.hls.luminosity === 255) {
                            color = {
                                a: 0xff,
                                r: 255,
                                g: 255,
                                b: 255
                            };
                        } else {
                            color = ColorHelper._colorFromHLS(Math.floor(this.hls.hue / 255 * 240), Math.floor(this.hls.luminosity / 255 * 240), Math.floor(this.hls.saturation / 255 * 240));
                        }
                        if (needUpdateDragColor || this.hls.luminosity === 0 || this.hls.luminosity === 255) {
                            colorOfDragMiddle = ColorHelper._colorFromHLS(Math.floor(this.hls.hue / 255 * 240), Math.floor(128 / 255 * 240), Math.floor(this.hls.saturation / 255 * 240));
                        }
                    }
                    color.r = parseInt(color.r.toString(), 10);
                    color.g = parseInt(color.g.toString(), 10);
                    color.b = parseInt(color.b.toString(), 10);
                    this.innerDialog.find('.colordialog-input-red').spinner().spinner('value', color.r.toString());
                    this.innerDialog.find('.colordialog-input-green').spinner().spinner('value', color.g.toString());
                    this.innerDialog.find('.colordialog-input-blue').spinner().spinner('value', color.b.toString());
                    var rgbvalue = "rgb(" + color.r.toString() + ", " + color.g.toString() + ", " + color.b.toString() + ")";
                    this.innerDialog.find('.colordialog-div-newcolor').css('background', rgbvalue);

                    if (needUpdateDragColor || this.hls.luminosity === 0 || this.hls.luminosity === 255) {
                        colorOfDragMiddle.r = parseInt(colorOfDragMiddle.r.toString(), 10);
                        colorOfDragMiddle.g = parseInt(colorOfDragMiddle.g.toString(), 10);
                        colorOfDragMiddle.b = parseInt(colorOfDragMiddle.b.toString(), 10);
                        var rgbOfDragMiddle = "rgb(" + colorOfDragMiddle.r.toString() + ", " + colorOfDragMiddle.g.toString() + ", " + colorOfDragMiddle.b.toString() + ")";
                        var dragColor = this.innerDialog.find('.colordialog-div-dragcolor');
                        dragColor.css("background-image", "linear-gradient(to bottom, rgb(255,  255, 255)," + rgbOfDragMiddle + ", rgb(0, 0, 0))");
                        dragColor.css("background-image", "-o-linear-gradient(to bottom, rgb(255,  255, 255)," + rgbOfDragMiddle + ", rgb(0, 0, 0))");
                        dragColor.css("background-image", "-moz-linear-gradient(to bottom, rgb(255,  255, 255)," + rgbOfDragMiddle + ", rgb(0, 0, 0))");
                        dragColor.css("background-image", "-webkit-linear-gradient(to bottom, rgb(255,  255, 255)," + rgbOfDragMiddle + ", rgb(0, 0, 0))");
                        dragColor.css("background-image", "-ms-linear-gradient(to bottom, rgb(255,  255, 255)," + rgbOfDragMiddle + ", rgb(0, 0, 0))");
                        dragColor.css("background-image", "-webkit-gradient(linear, left top, left bottom, color-stop(0, rgb(255,  255, 255)), color-stop(0.5, " + rgbOfDragMiddle + "), color-stop(1, rgb(0, 0, 0)))");
                    }
                };
                ColorDailog.prototype.destroy = function () {
                    if (this.innerDialog) {
                        this.innerDialog.dialog("destroy");
                        this.innerDialog.remove();
                    }
                };
                return ColorDailog;
            })();
            designer.ColorDailog = ColorDailog;

            var ColorHelper = (function () {
                function ColorHelper() {
                }
                ColorHelper._getColorBrightness = function (color) {
                    var hls = this._hLSColor(color, false);
                    return hls.luminosity / 240;
                };

                ColorHelper._colorFromHLS = function (hue, luminosity, saturation) {
                    var r, g, b;
                    if (saturation === 0) {
                        r = g = b = parseInt(((luminosity * 0xff) / 240).toString(), 10);
                    } else {
                        var n1, n2;
                        if (luminosity <= 120) {
                            n2 = ((luminosity * (240 + saturation)) + 120) / 240;
                        } else {
                            n2 = (luminosity + saturation) - (((luminosity * saturation) + 120) / 240);
                        }
                        n1 = (2 * luminosity) - n2;

                        r = parseInt((((this._hueToRGB(n1, n2, hue + 80) * 0xff) + 120) / 240).toString(), 10);
                        g = parseInt((((this._hueToRGB(n1, n2, hue) * 0xff) + 120) / 240).toString(), 10);
                        b = parseInt((((this._hueToRGB(n1, n2, hue - 80) * 0xff) + 120) / 240).toString(), 10);
                    }
                    var argb = {
                        a: 0xff,
                        r: r,
                        g: g,
                        b: b
                    };
                    return argb;
                };

                ColorHelper._hLSColor = function (rgbColor, isColorObject) {
                    var luminosity, saturation, hue;
                    var r, g, b;
                    if (isColorObject) {
                        r = rgbColor.r;
                        g = rgbColor.g;
                        b = rgbColor.b;
                    } else {
                        r = parseInt(rgbColor.substring(1, 3), 16);
                        g = parseInt(rgbColor.substring(3, 5), 16);
                        b = parseInt(rgbColor.substring(5, 7), 16);
                    }

                    var maxUnit = Math.max(Math.max(r, g), b);
                    var minUnit = Math.min(Math.min(r, g), b);
                    var sum = maxUnit + minUnit;
                    luminosity = parseInt((((sum * 240) + 0xff) / 510).toString(), 10);
                    var diff = maxUnit - minUnit;
                    if (diff === 0) {
                        saturation = 0;
                        hue = 160;
                    } else {
                        if (luminosity <= 120) {
                            saturation = parseInt((((diff * 240) + (sum / 2)) / sum).toString(), 10);
                            saturation = parseInt((((diff * 240) + (sum / 2)) / sum).toString(), 10);
                        } else {
                            saturation = parseInt((((diff * 240) + ((510 - sum) / 2)) / (510 - sum)).toString(), 10);
                        }
                        var partR = (((maxUnit - r) * 40) + (diff / 2)) / diff;
                        var partG = (((maxUnit - g) * 40) + (diff / 2)) / diff;
                        var partB = (((maxUnit - b) * 40) + (diff / 2)) / diff;
                        if (r === maxUnit) {
                            hue = parseInt((partB - partG).toString(), 10);
                        } else if (g === maxUnit) {
                            hue = parseInt(((80 + partR) - partB).toString(), 10);
                        } else {
                            hue = parseInt(((160 + partG) - partR).toString(), 10);
                        }
                        if (hue < 0) {
                            hue += 240;
                        }
                        if (hue > 240) {
                            hue -= 240;
                        }
                    }
                    var result = {
                        luminosity: luminosity,
                        saturation: saturation,
                        hue: hue
                    };
                    return result;
                };

                ColorHelper._hueToRGB = function (n1, n2, hue) {
                    if (hue < 0) {
                        hue += 240;
                    }
                    if (hue > 240) {
                        hue -= 240;
                    }
                    if (hue < 40) {
                        return (n1 + ((((n2 - n1) * hue) + 20) / 40));
                    }
                    if (hue < 120) {
                        return n2;
                    }
                    if (hue < 160) {
                        return (n1 + ((((n2 - n1) * (160 - hue)) + 20) / 40));
                    }
                    return n1;
                };
                return ColorHelper;
            })();
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
