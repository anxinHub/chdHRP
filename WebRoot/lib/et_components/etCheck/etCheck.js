;(function ($) {
    $.fn.etCheck = function (options) {
        var $self = this;
        var default_options = {
            // handle: '',
            checkboxClass: 'icheckbox_minimal',
            radioClass: 'iradio_minimal',
            // checkedClass: 'checked',
            // checkedCheckboxClass: '',
            // checkedRadioClass: '',
            // uncheckedClass: '',
            // uncheckedCheckboxClass: '',
            // uncheckedRadioClass: '',
            // disabledClass: 'disabled',
            // disabledCheckboxClass: '',
            // disabledRadioClass: '',
            // enabledClass: '',
            // enabledCheckboxClass: '',
            // enabledRadioClass: '',
            // indeterminateClass: 'indeterminate',
            // indeterminateCheckboxClass: '',
            // indeterminateRadioClass: '',
            // determinateClass: '',
            // determinateCheckboxClass: '',
            // determinateRadioClass: '',
            // hoverClass: 'hover',
            // focusClass: 'focus',
            // activeClass: 'active',
            labelHover: true,
            // labelHoverClass: 'hover',
            increaseArea: '20%', // 增加减少可点击区域百分比
            // cursor: false, // 设置光标
            // inheritClass: false, // 继承原始class的名称
            // inheritID: false,   // 'iCheck-'前缀
            // aria: true, // 开启ARIA的支持
            // insert: ''  // 自定义html插入checkbox中
            
            // checked: false,
            // disabled: false,
        };
        var opts = $.extend({}, default_options, options);
        
        var checkObj = {
            status: '',
            checked: false,
            disabled: false,
            options: opts
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        //                             状态改变的回调函数--内置事件                             //
        //////////////////////////////////////////////////////////////////////////////////////////

        // 被创建时
        $self.on('ifCreated', function () {
            if (opts.created) {
                opts.created();
            }

            // 判断下一个是不是对应的label，设置水平对齐样式
            // var $label = $self.parent().next();
            // if ($label.get(0).tagName.toLowerCase() === 'label' && $label.prop('for') === $self.prop('id')) {
            //     $label.css('vertical-align', 'middle');
            // }
            
            // 设置状态
            if (opts.checked) {
                $self.prop('checked', true);
                checkObj.checked = true;
                checkObj.status = 'checked';
            }
            if (opts.disabled) {
                $self.prop('disabled', true);
                checkObj.disabled = true;
                checkObj.status = 'disabled';
            }
        })
        // 被销毁时
        $self.on('ifDestroyed', function () {
            if (opts.destroyed) {
                opts.destroyed();
            }
        })
        // 选择时
        $self.on('ifChecked', function () {
            if (opts.ifChecked) {
                opts.ifChecked();
            }
        })
        // 取消选择时
        $self.on('ifUnchecked', function () {
            if (opts.ifUnchecked) {
                opts.ifUnchecked();
            }
        })
        // 被禁用时
        $self.on('ifDisabled', function () {
            if (opts.ifDisabled) {
                opts.ifDisabled();
            }
        })
        // 被启用时
        $self.on('ifEnabled', function () {
            if (opts.ifEnabled) {
                opts.ifEnabled();
            }
        })

        // 点击时。
        $self.on('ifClicked', function () {
            if (opts.ifClicked) {
                opts.ifClicked();
            }
        })
        // 状态改变时
        $self.on('ifChanged', function () {
            if ($self.get(0).disabled) {
                checkObj.status = 'disabled';
                checkObj.disabled = true;
            } else {
                if ($self.get(0).checked) {
                    checkObj.status = 'checked';
                } else {
                    checkObj.status = 'unchecked';
                }
                checkObj.disabled = false;
            }
            if (opts.ifChanged) {
                opts.ifChanged(checkObj.status, checkObj.checked, checkObj.disabled);
            }
        })
        // toggle。因为click 》toggle 》change。在click时状态还未附上。所以状态判断放这里
        $self.on('ifToggled', function () {
            if ($self.get(0).checked) {
                checkObj.status = 'checked';
                checkObj.checked = true;
            } else {
                checkObj.status = 'unchecked';
                checkObj.checked = false;
            }
            if (opts.ifToggled) {
                opts.ifToggled(checkObj.checked);
            }
        })
        // 状态不确定
        // $self.on('ifIndeterminate', function () {
        //     if (opts.indeterminate) {
        //         opts.indeterminate();
        //     }
        // })
        // 不确定状态移除
        // $self.on('ifDeterminate', function () {
        //     if (opts.determinate) {
        //         opts.determinate();
        //     }
        // })

        ////////////////////////////////////////////////////////////////////////////////////////
        //                                      内置方法                                      //
        ////////////////////////////////////////////////////////////////////////////////////////

        var methods = {
            setCheck: function (fn) {
                $self.iCheck('check', fn);
                return this;
            },
            setUncheck: function (fn) {
                $self.iCheck('uncheck', fn);
                 return this;
            },
            setToggle: function (fn) {
                $self.iCheck('toggle', fn);
                 return this;
            },
            setDisable: function (fn) {
                $self.iCheck('disable', fn);
                 return this;
            },
            setEnable: function (fn) {
                $self.iCheck('enable', fn);
                 return this;
            },
            // indeterminate: function (fn) {
            //     $self.iCheck('indeterminate', fn);
            // },
            // determinate: function (fn) {
            //     $self.iCheck('determinate', fn);
            // },
            setUpdate: function (fn) {
                $self.iCheck('update', fn);
                 return this;
            },
            // destroy: function (fn) {
            //     $self.iCheck('destroy', fn);
            // }
        }

        var $check = $self.iCheck(opts);

        checkObj.$el = $check;

        $.extend(checkObj, methods);

        return checkObj;
    }
})(jQuery);