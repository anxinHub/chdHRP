;(function ($) {
	$.fn.etDatepicker = function (options) {
		var $self = this;
		var default_options = {
			// classes: null, // 添加class
			// inline: false, // 一直显示
			// startDate: new Date(), // 初始化显示日期

			// view: "days", // days开始视图显示日期。months。years
			// minView: "days", // 最小的日期类型需要配合view使用。
			// dateFormat: "yyyy-mm-d" // @毫秒为单位。dd数字日，DD星期。mm数字月份。MM中文月份。yyyy年份，yy两位年份。yyyy1十年的第一年，yyyy2十年的最后一年

			// minDate: new Date(),
			// maxDate: new Date(),
			defaultDate: false, // 默认显示日期

			todayButton: true, // “今日”按钮
			clearButton: true, // “清除”按钮
			
			autoClose: true, // 选择完后自动关闭。默认false

			// multipleDates: false, // 为true是多选日期。为数字为日期选择个数限制
			
			// range: false, // 范围选择日期
			// multipleDatesSeparator: " 至 ", // 连个日期之间分割符
			
			toggleSelected: false, // false时可以选择同一天，true时点同一天取消选择

			// keyboardNav: true, 开启热键
			
			// position: "bottom left", // 定位位置
			// offset: 12, // 定位偏移

			// timepicker: false, // 选择小时，分钟
			// timeFormat: 'hh:ii aa' // 小时分钟格式化，默认24小时制。aa加上为12小时制
			// maxHours, minHours, maxMinutes, minMinutes,
			
			showNav: true,
			readonly: true,
			// onSelect: function (formattedDate, date, inst) {},
			// onShow: function (inst, animationCompleted) {},
			// onHide: function (inst, animationCompleted) {},
			// onChangeMonth: function (month, year) {},
			// onChangeYear: function (year) {},
			// onChangeDecade: function (decade) {},
			// onChangeView: function (view) {},
			// onRenderCell: function (date, cellType) {}
		};
		var opts = $.extend({}, default_options, options);

		//////////////////////////////////////////////////////////////////////
		//                             扩展方法                             //
		//////////////////////////////////////////////////////////////////////
		var protoMethods = {
			/**
			 * 设置日期
			 * @param {String | Array | Date obj | Boolean} date 日期。为true时设置为当天日期
			 */
			setValue: function (date) {
				var theDate = '';

				if (date) {
					if (typeof date === "string") {
						theDate = new Date(date);

					} else if (Array.isArray(date)) {
						theDate = [];

						date.forEach(function (item, index) {
							theDate.push(new Date(item));
						})
					} else if (date instanceof Date) {
						theDate = date;

					} else if (date === true) {
						theDate = new Date();
					}
				}

				this.selectDate(theDate);
			},
			/**
			 * 获取已经被选择的日期
			 * @param {String} format 自定义想要取出的日期格式
			 * @return {String | Arry}        [格式化后日期字符串，或其数组]
			 */
			getValue: function (format) {
				var separator = opts.multipleDatesSeparator;
				var value = this.el.value;

				if (!value) {
					return "";
				}
				if (format) {

					if (value.indexOf(separator) !== -1) {
						var valueArr = value.split(separator);

						valueArr = valueArr.map(function (item) {
							return protoMethods._formatDate(new Date(item), format);
						});
						return valueArr;

					} else {
						var theDate = new Date(value);
						return protoMethods._formatDate(theDate, format);
					}

				} else {

					if (value.indexOf(separator) !== -1) {
						return value.split(separator);
					} else {
						return value;
					}

				}
			},
			/**
			 * 格式化日期
			 * @param  {Date obj} date   [日期对象]
			 * @param  {String} format [格式化yyyy年，mm月，m月为个位月无0，dd日，first_dd一个月的第一天，last_dd一个月的最后一天]
			 * @return {String}        [格式化后日期字符串]
			 */
			_formatDate: function (date, format) {
				if (!format || typeof format !== "string") {
					var format = "yyyy-mm-dd";
				}
				var dateYear = date.getFullYear();
				var dateMonth = date.getMonth() + 1;
				var dateDay = date.getDate();

				dateMonth = dateMonth < 10 ? "0" + dateMonth : dateMonth;
				dateDay = dateDay < 10 ? "0" + dateDay : dateDay;

				// 注意顺序
				if (/yyyy/.test(format)) {
					format = format.replace(/yyyy/, dateYear);
				}
				// if (/first_mm/.test(format)) {
				// 	dateMonth = "01";
				// 	format = format.replace(/first_mm/, dateMonth);
				// }
				// if (/last_mm/.test(format)) {
				// 	dateMonth = "12";
				// 	format = format.replace(/last_mm/, dateMonth);
				// }
				if (/mm/.test(format)) {
					format = format.replace(/mm/, dateMonth);
				} else if (/m/.test(format)) {
					format = format.replace(/m/, date.getMonth() + 1);
				}

				if (/first_dd/.test(format)) {
					dateDay = "01";
					format = format.replace(/first_dd/, dateDay);
				}
				if (/last_dd/.test(format)) {
					var theLast_dd = new Date(dateYear, dateMonth, 0);
					dateDay = theLast_dd.getDate();

					format = format.replace(/last_dd/, dateDay);
				}
				
				if (/dd/.test(format)) {
					format = format.replace(/dd/, dateDay);
				} else if (/d/.test(format)) {
					format = format.replace(/d/, date.getDate());
				}

				return format;
			}
		}

		////////////////////////////////////////////////////////////////////
		//                         修改参数初始值                         //
		////////////////////////////////////////////////////////////////////
		if (opts.range && !opts.multipleDatesSeparator) {
			opts.multipleDatesSeparator = " 至 "
		}
		if (opts.multipleDates && !opts.range && !opts.multipleDatesSeparator) {
			opts.multipleDatesSeparator = ", "
		}
		if (opts.minDate && typeof opts.minDate === "string") {
			opts.minDate = protoMethods._formatDate(new Date(), opts.minDate);
			opts.minDate = new Date(opts.minDate);
		}
		if (opts.maxDate && typeof opts.maxDate === "string") {
			opts.maxDate = protoMethods._formatDate(new Date(), opts.maxDate);
			opts.maxDate = new Date(opts.maxDate);
		}
		if (opts.todayButton) {
			opts.todayButton = new Date();
		}
		if (opts.width) {
			$self.get(0).style.width = opts.width + 'px';
		}

		// 改变事件， 异步，来解决select时拿取不到日期对象的问题
		if (opts.onChanged) {
			opts.onSelect = function (formattedDate, date, inst) {
				var separator = opts.multipleDatesSeparator;
				var theDate = formattedDate;

				if (theDate.indexOf(separator) !== -1) {
					theDate = theDate.split(separator)
				}
				
				setTimeout(function () {
					opts.onChanged(theDate, date, inst);
				}, 10)
			}
		}

		// 生成
		var datepicker = $self.datepicker(opts).data('datepicker');
		// 将protoMethods里自定义的方法，拓展到datepicker原型中
		var $datepicker = $.extend(datepicker, protoMethods);

		//////////////////////////////////////////////////////////////////////////////
		//                            初始化后，参数判断                            //
		//////////////////////////////////////////////////////////////////////////////
		
		// 是否填写默认日期
		if (opts.defaultDate) {

			var defaultDate = opts.defaultDate;

			// 如果是true显示当天
			if (typeof defaultDate === "boolean") {

				$datepicker.selectDate(new Date());

			// 如果是指定日期显示指定日期
			} else if (typeof defaultDate === "string") {

				var stringDate = protoMethods._formatDate(new Date(), defaultDate);
				$datepicker.selectDate(new Date(stringDate));

			// 如果是数组，,multiple 或range范围选择
			} else if (Array.isArray(defaultDate) && (opts.multipleDates || opts.range)) {

				defaultDate.forEach(function (item, index) {
					if (typeof item === "string") {

						item = protoMethods._formatDate(new Date(), item);
						$datepicker.selectDate(new Date(item));

					} else if (item instanceof Date) {

						$datepicker.selectDate(item);

					}
				})
			}	
		}
		
		// 展示Nav
		if (!opts.showNav) {
			$datepicker.$nav.hide();
		}

		// 只读
		if (opts.readonly) {
			$self.attr("readonly", true);
		}
		$self.addClass("etDatePicker");

		return $datepicker;
	}
})(jQuery);