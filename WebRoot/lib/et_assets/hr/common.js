(function($) {
	 $.fn.extend({
	   insertContent : function(myValue, t) {
	   var $t = $(this)[0];
	   if (document.selection) { // ie
	    this.focus();
	    var sel = document.selection.createRange();
	    sel.text = myValue;
	    this.focus();
	    sel.moveStart('character', -l);
	    var wee = sel.text.length;
	    if (arguments.length == 2) {
	    var l = $t.value.length;
	    sel.moveEnd("character", wee + t);
	    t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart( "character", wee - t - myValue.length);
	    sel.select();
	    }
	   } else if ($t.selectionStart
	    || $t.selectionStart == '0') {
	    var startPos = $t.selectionStart;
	    var endPos = $t.selectionEnd;
	    var scrollTop = $t.scrollTop;
	    $t.value = $t.value.substring(0, startPos)
	     + myValue
	     + $t.value.substring(endPos,$t.value.length);
	    this.focus();
	    $t.selectionStart = startPos + myValue.length;
	    $t.selectionEnd = startPos + myValue.length;
	    $t.scrollTop = scrollTop;
	    if (arguments.length == 2) {
	    $t.setSelectionRange(startPos - t,
	     $t.selectionEnd + t);
	    this.focus();
	    }
	   } else {
	    this.value += myValue;
	    this.focus();
	   }
	   }
	  })
})(jQuery);  


/**
 * 延时执行函数
 * 1.  function fn(param){}
 * 2.  var method = delayed(fn);
 * 3.  method(param);
 * @param {function} fn 延时执行的函数
 * @param {number} millisec 延时毫秒
 */
function delayed(fn, millisec) {
    var query;
    return function (param) {
        clearTimeout(query);
        query = setTimeout(function () {
            fn(param);
        }, millisec || 500);
    }
}

/**
 * 根据主键选中树形节点
 * @param {objcet} tree 树对象
 * @param {*} id 节点id
 */
function selectTreeNodeById(tree, id) {
    var node = tree.getNodesByParam('id', id)[0];
    tree.selectNode(node);
}

/**
 * 选中树形默认节点
 * @param {object} tree 树对象
 * @param {*} callback 回调函数 参数：节点对象
 */
function selectTreeDefault(tree) {
    var node = tree.getNodesByParam('isHidden', false)[0];
    tree.selectNode(node);
    return node;
}

/**
 * 检索树
 * @param {*} obj
 * value: 检索值
 * tree: 树对象
 * callback: 回调函数
 */
var queryTree = (function () {
    // 输入前值，与输入后值
    var preValue = '';
    var curValue = '';
    return function (obj) {
        var allNodes = obj.tree.getNodes(); //结构型所有节点;
        var allArrayNodes = obj.tree.transformToArray(allNodes); //数组型所有节点

        curValue = obj.value;
        // 判断如果前值与现值相等，不检索
        if (preValue === curValue) {
            return;
        }
        preValue = curValue;

        obj.tree.hideNodes(allArrayNodes); //隐藏所有节点
        allArrayNodes.map(function (v) { //过滤显示节点
            if (v.name.indexOf(obj.value) > -1) {
                
                if (v.isHidden === true) {
                    showNode(v, obj.tree);
                }
            }
        });

        obj.tree.refresh();
        // var selectNode = selectTreeDefault(obj.tree);
        if (typeof obj.callback === "function") {
            // obj.callback(selectNode);
            obj.callback();
        }

        function showNode(node, tree) {
            var pNode = tree.getNodeByTId(node.parentTId);
            node.isHidden = false;
            node.nocheck = false;
            if (pNode) {
                showNode(pNode, tree);
            }
        }
    }
})();

/**
 * 延迟检索
 */
var searchTree = delayed(queryTree, 400);

/**
 * 获取前后几年的 年度下拉框
 */
var getRecentYearForSelect = function (length) {
    var l = length || 5;

    var now = new Date();
    var nowYear = now.getFullYear();
    var source = [];

    source.push({ id: nowYear.toString(), label: nowYear.toString() });

    for (var i = 1; i <= l; i++) {
        var nextYear = (nowYear + i).toString();
        var prevYear = (nowYear - i).toString();
        source.push({ id: nextYear, label: nextYear });
        source.unshift({ id: prevYear, label: prevYear });
    }

    var editor = {
        type: 'select',
        keyField: 'year',
        source: source
    };
    return editor;
};

/**
 * 获取月份下拉框的 editor配置
 */
var getMonthForSelect = function () {
    var source = [];
    for (var i = 1; i <= 12; i++) {
        var cur = i < 10 ? '0' : '';
        var val = cur + i;
        source.push({ id: val, label:  val });
    }

    var editor = {
        type: 'select',
        keyField: 'month',
        source: source
    };
    return editor;
};


/**
 * 排班管理，班次 科室 日期的格式转为 params Array
 */
var schedulingCompileParams = function (data) {
	for (var i = 0; i < data.length; i++) {
		var item = data[i];
		var params = [];
		for (var j = 0; j < currentDateList.length; j++) {
			var date = currentDateList[j];
			var dept = item['dept--' + date]
			/*   var each_dept_name = item['each_dept_name--' + date] */
			var attend_calsscode = item['attend_calsscode--'+ date]
			/* var attend_calssname = item['attend_calssname--' + date] */
			// if (!attend_calsscode) {
			//     console.log('班次必填')
			// }
			params.push({
				// 科室如果没有编辑，那么取前面的科室数据
				dept : dept || item.dept_id, // 科室 id
				/*  each_dept_name: each_dept_name || item.dept_name, // 科室 名称 */
				attend_calsscode : attend_calsscode || attend_calsscode == 0 ? attend_calsscode : 0, // 班次 id
				/*   attend_calssname: attend_calssname || attend_calssname == 0 ? attend_calssname  : null, // 班次 名称 */
				attend_pbdate : date
				// 日期
			})
		}
		item.params = params
	}
	return data
}
/**
 * 排班管理，params转为 表格可用的数据格式
 * 默认 按数据的返回数据格式
 * 配置了开始日期 和 类型，按照开始日期开始累计循环
 */
var schedulingDecompileParams = function (data, startDate, type) {
	for (var i = 0; i < data.length; i++) {
	    var item = data[i];
	    var params = item.param || []
	    if (!startDate) {
	    	for (var j = 0; j < params.length; j++) {
		        var paramObj = params[j];
		        var dept = paramObj.dept
		        var each_dept_name = paramObj.each_dept_name
		        var attend_calsscode = paramObj.attend_calsscode
		        var attend_calssname = paramObj.attend_classsname
		        var attend_pbdate = paramObj.attend_pbdate

		        item['dept--' + attend_pbdate] = dept
		        item['each_dept_name--' + attend_pbdate] = each_dept_name
		        item['attend_calsscode--' + attend_pbdate] = attend_calsscode
		        item['attend_calssname--' + attend_pbdate] = attend_calssname
		    }
	    } else {
	    	var loopLength = 0
	    	if (type === 'weeks') {
	    		loopLength = 7
	    	} else if (type === 'months') {
	    		loopLength = moment(startDate, "YYYY-MM").daysInMonth()
	    	}
	    	for (var j = 0; j < loopLength; j++) {
		        var paramObj = params[j];
		        if (paramObj) {
		        	var dept = paramObj.dept
			        var each_dept_name = paramObj.each_dept_name
			        var attend_calsscode = paramObj.attend_calsscode
			        var attend_calssname = paramObj.attend_classsname
			        var attend_pbdate = moment(startDate).add(j, 'days').format('YYYY-MM-DD')

			        item['dept--' + attend_pbdate] = dept
			        item['each_dept_name--' + attend_pbdate] = each_dept_name
			        item['attend_calsscode--' + attend_pbdate] = attend_calsscode
			        item['attend_calssname--' + attend_pbdate] = attend_calssname
		        }
		    }
	    }
	}
	return data
}
