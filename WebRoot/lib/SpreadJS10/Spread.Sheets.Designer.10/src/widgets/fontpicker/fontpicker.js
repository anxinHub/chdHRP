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
            var FontPicker = (function (_super) {
                __extends(FontPicker, _super);
                function FontPicker() {
                    _super.apply(this, arguments);
                }
                FontPicker.prototype.family = function (value) {
                    if (value === undefined) {
                        return this._family;
                    }

                    if (this._family !== value) {
                        this._family = value;
                        this._updateAppearance();
                    }
                    return this;
                };
                FontPicker.prototype.size = function (value) {
                    if (value === undefined) {
                        return this._size;
                    }

                    if (this._size !== value) {
                        this._size = value;
                        this._updateAppearance();
                    }
                    return this;
                };
                FontPicker.prototype.style = function (value) {
                    if (value === undefined) {
                        return this._style;
                    }

                    if (this._style !== value) {
                        this._style = value;
                        this._updateAppearance();
                    }
                    return this;
                };
                FontPicker.prototype.weight = function (value) {
                    if (value === undefined) {
                        return this._weight;
                    }

                    if (this._weight !== value) {
                        this._weight = value;
                        this._updateAppearance();
                    }
                    return this;
                };
                FontPicker.prototype.color = function (color) {
                    if (color === undefined) {
                        return this._color;
                    }

                    this._color = color;
                    this.options.color = color;

                    this._updateAppearance();
                    return this;
                };
                FontPicker.prototype.underline = function (value) {
                    if (value === undefined) {
                        return this._underline;
                    }
                    if (this._underline !== value) {
                        this._underline = value;
                        this._updateAppearance();
                    }
                    return this;
                };
                FontPicker.prototype.strikethrough = function (value) {
                    if (value === undefined) {
                        return this._strikethrough;
                    }
                    if (this._strikethrough !== value) {
                        this._strikethrough = value;
                        this._updateAppearance();
                    }
                    return this;
                };

                FontPicker.prototype.colorOption = function (name, value) {
                    if (value === undefined) {
                        return this._colorPicker.colorpicker('option', name);
                    } else {
                        this._colorPicker.colorpicker('option', name, value);
                        return this;
                    }
                };

                FontPicker.prototype._create = function () {
                    this._id = FontPicker._currentId++;
                    this.element.addClass('gcui-fontpicker');

                    this._createPicker('family', this.options.families, this.options.familyLabelText);
                    this._createPicker('style', this.options.styles, this.options.styleLabelText);
                    this._createPicker('size', this.options.size, this.options.sizeLabelText);
                    this._createPicker('weight', this.options.weights, this.options.weightLabelText);

                    var others = $('<div></div>').addClass('gcui-fontpicker-group-others').addClass('gcui-fontpicker-group');
                    if (this.options.isColorVisible) {
                        $('<label></label>').addClass('gcui-fontpicker-group-label').text(this.options.colorLabelText).appendTo(others);
                        this._createColorPicker(others);
                    }
                    this._createNormalFont(others);
                    others.append($('<div class="clear-float"></div>'));
                    this._createEffects(others);
                    this._createPreview(others);
                    this.element.append(others);
                };

                FontPicker.prototype._setOption = function (key, value) {
                    if (key === 'customFontStyle') {
                        this.options.customFontStyle = value;
                        for (var name in this.options.customFontStyle) {
                            if (this.options.customFontStyle[name] === 'disabled') {
                                switch (name) {
                                    case 'family':
                                        $('#gcui-fontpicker-input-family-' + this._id).attr('disabled', 'disabled');
                                        $('#gcui-fontpicker-select-family-' + this._id).attr('disabled', 'disabled');
                                        break;
                                    case 'size':
                                        $('#gcui-fontpicker-input-size-' + this._id).attr('disabled', 'disabled');
                                        $('#gcui-fontpicker-select-size-' + this._id).attr('disabled', 'disabled');
                                        break;
                                    case 'weight':
                                        $('#gcui-fontpicker-input-weight-' + this._id).attr('disabled', 'disabled');
                                        $('#gcui-fontpicker-select-weight-' + this._id).attr('disabled', 'disabled');
                                        break;
                                    case 'style':
                                        $('#gcui-fontpicker-input-style-' + this._id).attr('disabled', 'disabled');
                                        $('#gcui-fontpicker-select-style-' + this._id).attr('disabled', 'disabled');
                                        break;
                                }
                            }
                        }
                    }
                };

                FontPicker.prototype._createPicker = function (groupName, groupItems, groupLabel) {
                    var self = this;
                    var groupDiv = $('<div></div>').addClass('gcui-fontpicker-group').addClass('gcui-fontpicker-picker-group').addClass('gcui-fontpicker-group-' + groupName);
                    var groupInputId = 'gcui-fontpicker-input-' + groupName + '-' + this._id;
                    $('<lable></lable>').attr('for', groupInputId).addClass('gcui-fontpicker-group-label').text(groupLabel).appendTo(groupDiv);

                    var groupInput = $('<input>').attr({
                        'id': groupInputId,
                        'name': groupName,
                        'type': 'text'
                    }).addClass('ui-widget ui-widget-content').addClass('gcui-fontpicker-input-' + groupName);

                    groupDiv.append(groupInput);

                    var groupSelectId = 'gcui-fontpicker-select-' + groupName + '-' + this._id;
                    var groupSelect = $('<select></select>').attr({
                        'id': groupSelectId,
                        'name': groupName + '-list',
                        'size': 6
                    }).addClass('ui-widget ui-widget-content').addClass('gcui-fontpicker-select-' + groupName);
                    if (groupName === "weight") {
                        groupSelect.attr("size", 8);
                    }

                    if (Array.isArray(groupItems)) {
                        for (var i = 0; i < groupItems.length; i++) {
                            groupSelect.append($('<option></option>').val(groupItems[i]).text(groupItems[i]));
                        }
                    } else {
                        for (var name in groupItems) {
                            groupSelect.append($('<option></option>').val(name).text(groupItems[name]));
                        }
                    }

                    var fieldName = '_' + groupName;
                    groupSelect.on('change.gcui-fontpicker', function (e) {
                        var value = groupSelect.val();
                        groupInput.val(FontPicker._valueToText(groupItems, value));
                        if (self[fieldName] !== value) {
                            self[fieldName] = value;
                            self._onChanged(e, groupName, value);
                        }
                    });
                    groupInput.on('keyup.gcui-fontpicker', function (e) {
                        var value = FontPicker._textToValue(groupItems, groupInput.val());
                        if (value !== undefined) {
                            groupSelect.val(value);
                            if (self[fieldName] !== value) {
                                self[fieldName] = value;
                                self._onChanged(e, groupName, value);
                            }
                        }
                    });
                    groupInput.on('blur.gcui-fontpicker', function (e) {
                        var value = groupSelect.val() || "";
                        groupInput.val(FontPicker._valueToText(groupItems, value));
                        if (self[fieldName] !== value) {
                            self[fieldName] = value;
                            self._onChanged(e, groupName, value);
                        }
                    });

                    groupDiv.append(groupSelect);

                    this.element.append(groupDiv);
                };
                FontPicker.prototype._createColorPicker = function (parent) {
                    var self = this;
                    var container = $('<div></div>').addClass('gcui-fontpicker-color');

                    var content = $('<span></span>').appendTo(container);
                    var preview = $('<span></span>').addClass('gcui-fontpicker-color-preview').appendTo(content);
                    var popup = $('<div></div>').addClass('gcui-fontpicker-color-popup').appendTo(container);
                    this._colorPicker = $('<div></div>').addClass('gcui-fontpicker-color-picker').colorpicker({
                        valueChanged: function (e, value) {
                            if (value.color === undefined) {
                                self._color = undefined;
                                self._onChanged(e, 'color', undefined);
                            } else {
                                self._color = value;
                                self._onChanged(e, 'color', value);
                            }
                        },
                        choosedColor: function (e, value) {
                            container.comboframe('close');
                        },
                        openColorDialog: function (e, value) {
                            container.comboframe('close');
                        }
                    }).appendTo(popup);
                    container.comboframe().click(function () {
                        container.comboframe('open');
                    });

                    parent.append(container);
                };
                FontPicker.prototype._createNormalFont = function (parent) {
                    var self = this;

                    //Add hidden class because of bug 45769. We will remove Hidden class when NormalFont feature is ok.
                    //Now NormalFont doesn't take effect.
                    var container = $('<div></div>').addClass("hidden");
                    var inputId = 'gcui-fontpicker-normal-font-' + this._id;
                    $('<input>').attr('type', 'checkbox').attr('id', inputId).addClass('gcui-fontpicker-normal-font').click(function (e) {
                        if (this.checked) {
                            if (self._family) {
                                self._family = null;
                                self._onChanged(e, 'family', null);
                            }
                            if (self._style) {
                                self._style = null;
                                self._onChanged(e, 'style', null);
                            }
                            if (self._size) {
                                self._size = null;
                                self._onChanged(e, 'size', null);
                            }
                            if (self._weight) {
                                self._weight = null;
                                self._onChanged(e, 'weight', null);
                            }
                            if (self._color) {
                                self._color = null;
                                self._onChanged(e, 'color', null);
                            }
                            self._updateAppearance();
                        }
                    }).appendTo(container);
                    $('<label></label>').attr('for', inputId).text(this.options.normalFontLabelText).appendTo(container);

                    parent.append(container);
                };
                FontPicker.prototype._createPreview = function (parent) {
                    var container = $('<fieldset></fieldset>').addClass('ui-widget-content');
                    $('<legend></legend>').text(this.options.previewLabelText).appendTo(container);
                    $('<div></div>').addClass('gcui-fontpicker-preview-container').append($('<span></span>').text(this.options.previewText).addClass('gcui-fontpicker-preview')).appendTo(container);

                    parent.append(container);
                };
                FontPicker.prototype._createEffects = function (parent) {
                    var self = this;
                    var underlineCheckBoxID = "effects-underline";
                    var strikethroughCheckBoxID = "effects-strikethrough";
                    var outsideField = $('<fieldset></fieldset>').addClass('ui-widget-content');
                    $('<legend></legend>').text(designer.res.fontPicker.effects).appendTo(outsideField);
                    var innerContainer = $('<div></div>').addClass('gcui-fontpicker-effects-container').appendTo(outsideField);
                    $("<input>").attr({ "id": underlineCheckBoxID, "type": "checkbox" }).addClass("effects-underline").click(function (e) {
                        self._underline = self.element.find(".effects-underline").prop("checked");
                        self._onChanged(e, "underline", self._underline);
                    }).appendTo(innerContainer);
                    $("<label>").text(designer.res.fontPicker.underline).attr("for", underlineCheckBoxID).appendTo(innerContainer);
                    $("<input>").attr({ "id": strikethroughCheckBoxID, "type": "checkbox" }).addClass("effects-strikethrough").click(function (e) {
                        self._strikethrough = self.element.find(".effects-strikethrough").prop("checked");
                        self._onChanged(e, "strikethrough", self._strikethrough);
                    }).appendTo(innerContainer);
                    $("<label>").text(designer.res.fontPicker.strikethrough).attr("for", strikethroughCheckBoxID).appendTo(innerContainer);
                    parent.append(outsideField);
                };

                FontPicker.prototype._updateAppearance = function () {
                    this._updatePreview();
                    if (this.options.isColorVisible) {
                        this._colorPicker.colorpicker('option', 'selectedItem', this._color);
                    }
                    this._updateFamilyPicker(this._family);
                    this._updatePicker('style', this.options.styles, this._style);
                    this._updatePicker('size', this.options.size, this._size);
                    this._updatePicker('weight', this.options.weights, this._weight);
                    this._updateEffects('underline', this._underline);
                    this._updateEffects('strikethrough', this._strikethrough);
                    this._updateNormalFont();
                };
                FontPicker.prototype._updateFamilyPicker = function (family) {
                    if (family.match(/^["'].*["']$/)) {
                        family = family.substring(1, family.length - 1);
                    }

                    var text, value;
                    var regex = new RegExp('^(.*,\s*)?["\']?' + family + '["\']?\s*(,.*)?$');

                    for (var fontKey in this.options.families) {
                        if (regex.test(fontKey)) {
                            text = this.options.families[fontKey];
                            value = fontKey;
                            break;
                        }
                    }
                    if (text && value) {
                        this.element.find('.gcui-fontpicker-input-family').val(text);
                        this.element.find('.gcui-fontpicker-select-family').val(value);
                    } else {
                        this.element.find('.gcui-fontpicker-input-family').val("");
                        this.element.find('.gcui-fontpicker-select-family').get(0).selectedIndex = -1;
                    }
                };
                FontPicker.prototype._updateEffects = function (effectsName, value) {
                    this.element.find('.effects-' + effectsName).prop("checked", value);
                };
                FontPicker.prototype._updatePicker = function (groupName, groupItems, value) {
                    var text = FontPicker._valueToText(groupItems, value);
                    this.element.find('.gcui-fontpicker-input-' + groupName).val(text);
                    this.element.find('.gcui-fontpicker-select-' + groupName).val(value);
                };
                FontPicker.prototype._updateNormalFont = function () {
                    if (!this._family && !this._style && !this._size && !this._weight && !this._color) {
                        this.element.find('.gcui-fontpicker-normal-font').prop('checked', true);
                    } else {
                        this.element.find('.gcui-fontpicker-normal-font').prop('checked', false);
                    }
                };
                FontPicker.prototype._updatePreview = function () {
                    var preview = this.element.find('.gcui-fontpicker-preview');

                    if (this._color) {
                        this.element.find('.gcui-fontpicker-color-preview').css('background-color', this._color.color);
                        preview.css('color', this._color.color);
                    } else {
                        this.element.find('.gcui-fontpicker-color-preview').css('background-color', 'transparent');
                        preview.css('color', 'black');
                    }

                    var decoration = "";
                    if (this._underline) {
                        decoration += "underline ";
                    }
                    if (this._strikethrough) {
                        decoration += "line-through";
                    }
                    preview.css("text-decoration", decoration);

                    var font = '';
                    if (this._style) {
                        font += this._style + ' ';
                    }
                    if (this._weight) {
                        font += this._weight + ' ';
                    }
                    if (this._size) {
                        font += this._size + 'pt ';
                    } else {
                        font += this.options.defaultSize + 'pt ';
                    }
                    if (this._family) {
                        font += this._family + ',' + this.options.alternativeFonts;
                    } else {
                        font += this.options.alternativeFonts;
                    }

                    preview.css('font', font);
                };

                FontPicker._valueToText = function (groupItems, value) {
                    if (Array.isArray(groupItems)) {
                        return value;
                    }
                    return groupItems[value];
                };
                FontPicker._textToValue = function (groupItems, text) {
                    if (Array.isArray(groupItems)) {
                        return text;
                    }

                    // process with empty string when selection was cleared
                    if (text === "") {
                        return text;
                    }

                    text = text.toLocaleLowerCase();
                    var names = Object.getOwnPropertyNames(groupItems);

                    for (var i = 0; i < names.length; i++) {
                        var name = names[i];
                        var currentText = groupItems[name].toLocaleLowerCase();
                        if (currentText.indexOf(text) === 0) {
                            return name;
                        }
                    }
                    return undefined;
                };

                FontPicker.prototype._onChanged = function (e, groupName, value) {
                    this._updatePreview();
                    if (this._family || this._style || this._size || this._weight || this._color || this._underline || this._strikethrough) {
                        this.element.find('.gcui-fontpicker-normal-font').prop('checked', false);
                    }
                    this._trigger('changed', e, { name: groupName, value: value });
                };
                FontPicker.widgetName = "fontpicker";
                FontPicker.defaultOptions = {
                    familyLabelText: designer.res.fontPicker.familyLabelText,
                    styleLabelText: designer.res.fontPicker.styleLabelText,
                    sizeLabelText: designer.res.fontPicker.sizeLabelText,
                    weightLabelText: designer.res.fontPicker.weightLabelText,
                    colorLabelText: designer.res.fontPicker.colorLabelText,
                    normalFontLabelText: designer.res.fontPicker.normalFontLabelText,
                    previewLabelText: designer.res.fontPicker.previewLabelText,
                    previewText: designer.res.fontPicker.previewText,
                    families: designer.res.fontPicker.fontFamilies,
                    styles: designer.res.fontPicker.fontStyles,
                    size: [8, 9, 10, 11, 12, 14, 16, 18, 20, 24, 26, 28, 36, 48, 72],
                    weights: designer.res.fontPicker.fontWeights,
                    alternativeFonts: designer.res.fontPicker.alternativeFonts,
                    defaultSize: designer.res.fontPicker.defaultSize,
                    isColorVisible: true,
                    customFontStyle: { family: true, style: true, size: true, weight: true }
                };
                FontPicker._currentId = 0;
                return FontPicker;
            })(gcui.gcuiWidget);
            designer.FontPicker = FontPicker;
            FontPicker.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, FontPicker.defaultOptions);
            $.gcui.registerWidget(FontPicker.widgetName, FontPicker.prototype);
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
