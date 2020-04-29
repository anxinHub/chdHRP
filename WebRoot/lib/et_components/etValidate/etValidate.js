;(function ($) {

    $.etValidate = function (options) {
        var default_option = {
            config: {
                emptyTip: "必填项,不可为空!",
                typeTip: "类型错误",
                formatTip: "格式错误",

                tipTime: 3000
            },
            items: [
                // {
                //     el: $('#el'),
                //     required: true,
                //     type: 'string',
                //     test: /a/ 可以写正则
                // }
            ]
        }

        var settings = $.extend(true, {}, default_option, options);

        var globalConfig = settings.config;
        var vldItems = settings.items;

        var vldMethods = {

            // 设置验证
            // _setValidate: function (item) {
            //     var $vldEl = item.el;

            //     $vldEl.addClass('etValidate');
            // },

            // 添加验证提示框的节点
            _appendTipElment: function ($el, tip) {
                if ($el.attr('showValidate')) {
                    return;
                }

                var $tipEl = $(
                    '<div class="etValidate_tip_wrap">' +
                        '<span class="etValidate_tip_msg">' + tip + '</span>' +
                    '</div>'
                );

                // 添加正在显示提示框的属性
                $el.attr('showValidate', true);

                var elTop = $el.offset().top + $el.height() + 10;
                var elLeft = $el.offset().left;

                // 针对etSelect插件的判断
                if ($el.hasClass('selectized')) {
                    var $selectEl = $el.next();
                    elTop = $selectEl.offset().top + $selectEl.height() + 5;
                    elLeft = $selectEl.offset().left;
                }

                $tipEl
                    .appendTo($('body'))
                    .css({
                        'top': elTop,
                        'left': elLeft
                    });

                if (globalConfig.tipTime) {
                    setTimeout(function () {
                        vldMethods._removeTipElement($el, $tipEl);
                    }, globalConfig.tipTime)
                }

                return $tipEl;
            },

            // 移除验证提示框，取消验证状态
            _removeTipElement: function (el, $tipEl) {
                el.removeAttr('showValidate');

                if ($tipEl) {
                    $tipEl.remove();
                }
            },

            // 遍历开启验证
            _testValidete: function () {
                var isPass = true;

                vldItems.forEach(function (item, index) {
                    var $vldEl = item.el;
                    var elValue = $vldEl.val();

                    // 验证是否有值
                    if (item.required) {
                        if (!elValue) {
                            var emptyTip = item.emptyTip ? item.emptyTip : globalConfig.emptyTip;

                            var $tipEl = vldMethods._appendTipElment($vldEl, emptyTip);

                            item.$tipEl = $tipEl ? $tipEl : item.$tipEl;
                            isPass = false;
                        } else {
                            vldMethods._removeTipElement(item.el, item.$tipEl);
                        }
                    }

                    // 验证类型
                    if (item.type && elValue) {
                        var isRightType = true;

                        switch(item.type) {
                            case 'number':
                                if (typeof Number(elValue) !== 'number') {
                                    isRightType = false;
                                }
                            case 'int':
                                if (typeof Number(elValue) === 'number') {
                                    if (!/^(-|\+)?\d+$/.test(elValue)) {
                                        isRightType = false;
                                    }
                                } else {
                                    isRightType = false;
                                }
                                break;
                            case 'float':
                                if (typeof Number(elValue) === 'number') {
                                    if (!/^(-|\+)?\d+\.\d*$/.test(elValue)) {
                                        isRightType = false;
                                    }
                                } else {
                                    isRightType = false;
                                }
                                break;
                        }

                        if (!isRightType) {
                            var typeTip = item.typeTip ? item.typeTip : globalConfig.typeTip;

                            var $tipEl = vldMethods._appendTipElment($vldEl, typeTip);

                            item.$tipEl = $tipEl ? $tipEl : item.$tipEl;

                            isPass = false;
                        } else {
                            vldMethods._removeTipElement(item.el, item.$tipEl);
                        }
                    }

                    // 验证是否通过正则匹配
                    if (item.test && elValue) {
                        if (!item.test.test(elValue)) {
                            var formatTip = item.formatTip ? item.formatTip : globalConfig.formatTip;

                            var $tipEl = vldMethods._appendTipElment($vldEl, formatTip);

                            item.$tipEl = $tipEl ? $tipEl : item.$tipEl;
                            isPass = false;
                        } else {
                            vldMethods._removeTipElement(item.el, item.$tipEl);
                        }
                    }
                })

                // 如果验证未通过，返回false
                if (isPass) {
                    return true;
                } else {
                    return false;
                }
            },

            
            // 关闭一个表单的验证
            _setCloseValidate: function ($closeItem) {
                vldItems.forEach(function (item, index) {
                    var itemNode = item.el[0];
                    
                    // 重置为false，关闭验证
                    if (itemNode === $closeItem[0]) {
                        if (vldItems[index].required) {
                            vldItems[index].required = false;
                        }
                        if (vldItems[index].test) {
                            vldItems[index].prevTest = vldItems[index].test;
                            vldItems[index].test = false;
                        }

                        vldMethods._removeTipElement(vldItems[index].el, vldItems[index].$tipEl);
                    }
                })
            },

            // 开启一个表单的验证
            _setOpenValidate: function ($closeItem) {
                vldItems.forEach(function (item, index) {
                    var itemNode = item.el[0];
                    
                    // 启用为true，开启验证
                    if (itemNode === $closeItem[0]) {
                        if (!vldItems[index].required && typeof vldItems[index].required === 'boolean') {
                            vldItems[index].required = true;
                        }
                        if (vldItems[index].prevTest) {
                            vldItems[index].test = vldItems[index].prevTest;
                            delete vldItems[index].prevTest;
                        }
                    }
                })
            },

            // 增加一个表单的验证
            
            // 移除一个表单的验证
        }

        // 遍历验证项目，添加验证
        // vldItems.forEach(function (item, index) {
        //     vldMethods._setValidate(item);
        // });

        return {
            config: globalConfig,
            items: vldItems,

            test: vldMethods._testValidete, // 验证，返回Boolean，是否验证成功
            closeValidate: vldMethods._setCloseValidate, // 关闭验证
            openValidate: vldMethods._setOpenValidate // 开启验证
        }
    }
})(jQuery)
