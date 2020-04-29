;(function ($) {
    $.fn.etTab = function (options) {
        var $self = this;
        var default_options = {
            onCreated: function() {},
            onChanged: function() {}
        };

        var opts = $.extend({}, default_options, options);

        // 状态机
        var etState = {
            // 活动状态
            activeState: {
                index: 0,
                $tab: null,
                $panel: null
            }
        };

        /**
         * 构建
         */
        function _init () {
            var panelArr = $self.children('div[title]')
            var $tabHtml = $('<ul class="ettab-nav"></ul>');

            panelArr.each(function (index, item) {
                var title = $(item).attr('title');

                $(item)
                    .addClass('ettab-panel')
                    .attr('ettabindex', index)
                    .removeAttr('title');
                var $tab = $('<li class="ettab-tab" ettabindex="' + index + '">' + title + '</li>');
                $tabHtml.append($tab);

                if (index === 0) {
                    $tab.addClass('ettab-active');
                    $(item).addClass('ettab-active');

                    etState.activeState.index = 0;
                    etState.activeState.$tab = $tab;
                    etState.activeState.$panel = $(item);
                }
            })

            $self
                .addClass('ettab-wrap')
                .prepend($tabHtml);
            
            // 钩子 - 构建完事件
            opts.onCreated(etState.activeState);

            // 给tab节点绑定事件
            $self.on('click', '.ettab-tab', function () {
                _changeTab($(this))
            })
        };

        /**
         * tab切换事件
         * @param  {jq obj} $tab [tab的jq对象]
         */
        function _changeTab ($tab) {
            if ($tab.hasClass('ettab-active')) {
                return;
            }
            var tabIndex = Number($tab.attr('ettabindex'));

            etState.activeState.$tab.removeClass('ettab-active');
            etState.activeState.$panel.removeClass('ettab-active');

            var $panel = $self.find('.ettab-panel[ettabindex=' + tabIndex + ']');
            $tab.addClass('ettab-active');
            $panel.addClass('ettab-active');
            
            etState.activeState.index = tabIndex;
            etState.activeState.$tab = $tab;
            etState.activeState.$panel = $panel;

            // 钩子 - 改变事件
            opts.onChanged(etState.activeState);
        }

        _init();

        return {
            activeState: etState.activeState
        };
    }
})(jQuery)