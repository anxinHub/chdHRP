/**
 * jQuery ligerUI 1.3.3
 * 
 * http://s-chd.com
 * 
 * Author bell [ bell@s-chd.com ]
 * 
 */
(function($) {
	// 扩展一个 数字输入 的编辑器
	$.ligerDefaults.Grid.editors['numberbox'] = {
		create : function(container, editParm) {
			var g = this, p = this.options;
			var column = editParm.column;
			var precision = column.editor.precision;
			var input = $("<input type='text' style='text-align:right;' class='l-text' />");
			
			input.bind('keypress', function(e) {
				var keyCode = window.event ? e.keyCode : e.which;
				return keyCode >= 48 && keyCode <= 57 || keyCode == 46
						|| keyCode == 8;
			});
			
			input.bind('blur', function() {
				var value = input.val();
				input.val(parseFloat(value).toFixed(precision));
			});
			
			container.append(input);
			input.focus();
			return input;
		},
		getValue : function(input, editParm) {
			return parseFloat(input.val());
		},
		setValue : function(input, value, editParm) {
			var column = editParm.column;
			var precision = column.editor.precision;
			if (isNaN(value))
				value = "0.00";
			input.val(parseFloat(value).toFixed(precision));
		},
		resize : function(input, width, height, editParm) {
			input.width(width).height(height);
		}
	};

	// 扩展 numberbox 类型的格式化函数
	$.ligerDefaults.Grid.formatters['numberbox'] = function(value, column) {
		var precision = column.editor.precision;
		return value.toFixed(precision);
	};

})(jQuery);