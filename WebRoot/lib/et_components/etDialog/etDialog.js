;(function (window, $) {

	$.etDialog = {
		config: layer.config,
		ready: layer.ready,

		parentFrameName: null,
		parentFrameNameObj: {},
		getFrameName: function (key) {
			if (key) {
				return this.parentFrameNameObj[key];
			}

			return this.parentFrameName;
		},

		open: function (opts) {
			if (opts.url) {
				opts.content = opts.url;
				opts.type = 2;

				if (opts.btnAlign) {
					if (opts.btnAlign === 'left') {
						opts.btnAlign = 'l';
					} else if (opts.btnAlign === 'center') {
						opts.btnAlign = 'c';
					} else if (opts.btnAlign === 'right') {
						opts.btnAlign = 'r';
					}
				}

				if (opts.width) {
					if (typeof opts.width === 'number') {
						opts.width += 'px';
					} else if (typeof opts.width === 'string' && opts.width !== 'auto' && opts.width.indexOf('px') === -1) {
						opts.width += 'px';
					}
				}
				if (opts.height) {
					if (typeof opts.height === 'number') {
						opts.height += 'px';
					} else if (typeof opts.height === 'string' && opts.height !== 'auto' && opts.height.indexOf('px') === -1) {
						opts.height += 'px';
					}
				}

				if (opts.success) {
					var _success = opts.success;
				}
				opts.success = function (layero, index) {
					layero.find('iframe').focus();
					if (typeof _success === 'function') {
						_success(layero, index);
					}
				}

				var def_opt = {
					title: "iFrame层",
					resize: false,
					area: [opts.width, opts.height]
				}

				var options = $.extend({}, def_opt, opts);
				var index = layer.open(options);
				
				if (opts.isMax) {
					layer.full(index);
				}

				// 获取设置的windows name
				if (opts.frameName) {
					this.parentFrameName = opts.frameName;
				}

				// 获取设置的windows name的对象
				if (opts.frameNameObj) {
					var key = Object.keys(opts.frameNameObj)[0];
					this.parentFrameNameObj[key] = opts.frameNameObj[key];
					this.parentFrameName = opts.frameNameObj[key];
				}

				return index;
			}
		},

		alert: function (content) {
			var options = {
				title: "提示"
			};
			var yes;
			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				yes = arguments[2];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				yes = arguments[1];
			}

			if (options.success) {
				var _success = options.success;
			}
			options.success = function (layero, index) {
				layero.find('.layui-layer-btn0').attr('tabindex', 1).focus();
				if (typeof _success === 'function') {
					_success(layero, index);
				}
			}

			return layer.alert(content, options, yes);
		},
		success: function (content) {
			var options = {
				icon: 1,
				title: "成功"
			}
			var yes;
			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				yes = arguments[2];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				yes = arguments[1];
			}

			if (options.success) {
				var _success = options.success;
			}
			options.success = function (layero, index) {
				layero.find('.layui-layer-btn0').attr('tabindex', 1).focus();
				if (typeof _success === 'function') {
					_success(layero, index);
				}
			}

			return layer.alert(content, options, yes);
		},
		error: function (content) {
			var options = {
				icon: 2,
				title: "错误"
			};
			var yes;
			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				yes = arguments[2];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				yes = arguments[1];
			}

			if (options.success) {
				var _success = options.success;
			}
			options.success = function (layero, index) {
				layero.find('.layui-layer-btn0').attr('tabindex', 1).focus();
				if (typeof _success === 'function') {
					_success(layero, index);
				}
			}

			return layer.alert(content, options, yes);
		},
		warn: function (content) {
			var options = {
				icon: 0,
				title: "警告"
			};
			var yes;
			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				yes = arguments[2];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				yes = arguments[1];
			}

			if (options.success) {
				var _success = options.success;
			}
			options.success = function (layero, index) {
				layero.find('.layui-layer-btn0').attr('tabindex', 1).focus();
				if (typeof _success === 'function') {
					_success(layero, index);
				}
			}

			return layer.alert(content, options, yes);
		},

		confirm: function (content) {
			var options = {
				icon: 3,
				title: "提示"
			};
			var yes, cancel;

			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				yes = arguments[2];
				cancel = arguments[3];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				yes = arguments[1];
				cancel = arguments[2];
			}

			if (options.success) {
				var _success = options.success;
			}
			options.success = function (layero, index) {
				layero.find('.layui-layer-btn0').attr('tabindex', 1).focus();
				if (typeof _success === 'function') {
					_success(layero, index);
				}
			}

			return layer.confirm(content, options, yes, cancel);
		},
		msg: function (content) {
			var options = {};
			var end;

			if (arguments[1] && typeof arguments[1] === 'object') {
				$.extend(options, arguments[1]);
				end = arguments[2];
			} else if (arguments[1] && typeof arguments[1] === 'function') {
				end = arguments[1];
			}
			return layer.msg(content, options, end);
		},
		load: function (options) {
			return layer.load(2, options);
		},
		// tips: function (content) {},

		/**
		 * 辅助方法
		 */
		close: layer.close,
		closeAll: layer.closeAll,
		changeTitle: layer.title,
		setTop: layer.setTop,
		max: layer.full,
		min: layer.min,
		restore: layer.restore,
		// 当是iframe层时提供的方法
		getChildFrame: layer.getChildFrame,
		getFrameIndex: layer.getFrameIndex,
		iframeAuto: layer.iframeAuto,
		iframeSrc: layer.iframeSrc
	};

	$.etDialog.config({
		shade: [0.1, '#000']
	})
})(window, jQuery)
	
