!(function ($) {
    $.fn.etSelect = function (options) {
        var methods, select,
            $self = this,
            default_options = {
                options: [],
                items: [],
                valueField: 'id',
                labelField: 'text',
                searchField: ['text'],
                url: "",
                type: "POST",
                defaultValue: "",
                maxOptions: 50,
                et_extend: { //扩展属性
                    remoteLoad: extend_remoteLoad,
                },
                showClear: true,
                checkboxMode: false, // 是否开始复选框模式
                //optgroups: [],
                //plugins: [],
                //delimiter: ',',
                //splitOn: null, // regexp or string for splitting up values from a paste command
                //persist: true,
                //diacritics: true,
                // create: false,
                // createOnBlur: false,
                // createFilter: null,
                // highlight: true,
                // openOnFocus: true,
                //maxItems: null,
                //hideSelected: null,
                // addPrecedence: false,
                // selectOnTab: false,
                // preload: false,
                // allowEmptyOption: false,
                // closeAfterSelect: false,
                // scrollDuration: 60,
                // loadThrottle: 300,
                // loadingClass: 'loading',
                // dataAttr: 'data-data',
                //optgroupField: 'optgroup',
                //optgroupLabelField: 'label',
                //optgroupValueField: 'value',
                //lockOptgroupOrder: false,
                // sortField: '$order',
                // searchConjunction: 'and',
                // mode: null,
                // wrapperClass: 'selectize-control',
                // inputClass: 'selectize-input',
                // dropdownClass: 'selectize-dropdown',
                // dropdownContentClass: 'selectize-dropdown-content',
                // dropdownParent: null,
                // copyClassesToDropdown: true,
            };
        options = options || {};
        var opts = $.extend(true, {}, default_options, options);
        opts.onInitialize = function () {
            var _this = this;
            var optionList = _this.options;
            var value = _this.items[0];
            var settings = _this.settings;
            var $control = _this.$control;

            //选项不为空
            if (JSON.stringify(optionList) !== "{}") {
                //选中默认值
                if (optionList[settings.defaultValue]) {
                    _this.setValue(settings.defaultValue, true);
                }

                //构造完成
                if (typeof settings.onInit === "function") {
                    settings.onInit(value);
                }
            }

            // 构建清除按钮
            if (settings.showClear) {
                var $clearButton = $(
                    '<div class="select-clear-button">' +
                    '<svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">' +
                    '<path fill="currentColor" d="M18.984 6.422l-5.578 5.578 5.578 5.578-1.406 1.406-5.578-5.578-5.578 5.578-1.406-1.406 5.578-5.578-5.578-5.578 1.406-1.406 5.578 5.578 5.578-5.578z"></path>' +
                    '</svg>' +
                    '</div>'
                );

                if ($self.attr("disabled")) {//禁用属性
                    $clearButton.hide();
                }

                $control.after($clearButton);
                $clearButton.on("click", function () {
                    _this.clear();
                })
            }
        }

        // 开启复选框模式
        if (opts.checkboxMode) {
            opts.maxItems = 'null';
            opts.hideSelected = false;
            opts.showClear = false;
            opts.plugins = {
                'checkbox_mode': {}
            }
        }

        select = $self.selectize(opts);


        methods = {
            reload: _loadOptions,
            clearItem: _clearItem,
            clearOptions: _clearOptions,
            addOptions: _insertOptions,
            setValue: _setValue,
            getValue: _getValue,
            getText: _getText,
            open: _open,
            disable: _disable,
            enable: _enable
        }
        var $select = $.extend({}, select, methods);

        return $select;


        //加载数据 非首次加载
        function _loadOptions(data) {
            _addOptions(data);
        }

        //清空选项
        function _clearItem() {
            $select[0].selectize.clear();
        }

        //清空下拉内容
        function _clearOptions() {
            $select[0].selectize.clearOptions();
        }

        //打开
        function _open() {
            $select[0].selectize.open();
        }

        //禁用
        function _disable() {
            $select.next().find(".select-clear-button").hide();
            $select[0].selectize.disable();
        }

        //启用
        function _enable() {
            $select.next().find(".select-clear-button").show();
            $select[0].selectize.enable();
        }

        //设置选中值
        function _setValue(value, silent) {
            $select[0].selectize.setValue(value, silent);
        }

        //增加数据
        function _insertOptions(option) {
            $select[0].selectize.addOption(option);
        }

        //添加option
        function _addOptions(data, isFirst) {
            var that = this,
                url = data.url,
                type = data.type || "POST",
                successFn = data.success,
                para = data.para;
            //如果参数为空或者未传参 则只做清空不做后台请求
            if (_isEmptyPara(para)) {
                //清空选项
                $select[0].selectize.clearOptions();
                return;
            }
            if (url) {
                $.ajax({
                    url: url,
                    type: type,
                    data: para,
                    dataType: "JSON",
                    success: function (res) {
                        var defaultValue = "";
                        //清空选项
                        $select[0].selectize.clearOptions();
                        //填充选项
                        if (res.length > 0) {
                            $select[0].selectize.addOption(res);
                        }
                        //默认项
                        if (opts.defaultValue && _inOptions(opts.defaultValue, res, opts.valueField)) {
                            defaultValue = opts.defaultValue;
                        } else if (opts.defaultValue != "none") {
                            var data = res[0];
                            if (data) {
                                defaultValue = data.id;
                            }
                        }
                        $select[0].selectize.setValue(defaultValue);
                    },
                    error: function (res) {
                        console.error('请求失败', res)
                    },
                    complete: function (res) {
                        if (typeof successFn === "function") {
                            successFn(res);
                        }
                    }
                });
            }
        }

        //判断默认项是否在下拉列表中
        function _inOptions(value, options, field) {
            for (var i = 0, len = options.length; i < len; i++) {
                if (value == options[i][field]) {
                    return true;
                }
            }
            return false;
        }

        function _isEmptyPara(object) {
            for (var key in object) {
                if (object.hasOwnProperty(key)) {
                    var element = object[key];
                    if (element !== "") {
                        return false;
                    }
                }
            }
            return true;
        }

        function _getValue(separator) {
            var items = $select[0].selectize.items;
            var result = "";
            //分隔数组的字符串
            if (separator) {
                return items.length > 1 ? items.join(separator) : (items[0] || "");
            }
            if (items.length === 1) {
                result = items[0];
            } else if (items.length === 0) {
                result = "";
            } else {
                result = items;
            }
            return result;
        }

        function _getText(separator) {
            var select = $select[0].selectize;
            var result = separator ? select.items.join(separator) : select.items;
            var resultText = '';
            if (result.length < 2) {
                resultText = result[0] ? select.options[result[0]].text : '';
            } else {
                for (var index = 0; index < result.length; index++) {
                    var v = result[index];
                    resultText.push(select.options[v].text);
                }
            }
            return resultText;
        }


        //扩展插件 远程加载数据
        function extend_remoteLoad(param, callback) {
            var url = param.url,
                type = param.type || "POST",
                para = param.para;
            if (url) {
                $.ajax({
                    url: url,
                    type: type,
                    data: para,
                    dataType: "JSON",
                    success: function (res) {
                        if (typeof callback === "function") {
                            callback(res);
                        }
                    },
                    error: function () {
                        console.error("请求失败", url);
                    }
                });
            }
        }

    }
})(jQuery);
