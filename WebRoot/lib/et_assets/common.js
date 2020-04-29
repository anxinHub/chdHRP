/**
 * ajax post 请求 json格式； 自带loading；必须引用$.etDialog - add - wsj.2017.08.11
 * success回调函数，增加接受数据是字符串和对象的判断，。增加类false值判断，进入error事件 - update - wsj .2017.9.29
 * @param  {[Object]} paramObj，看param
 */
var ajaxPostData = function (paramObj) {
    var param = {
        url: '', // 请求路径
        async: true, //默认异步
        data: {}, // 请求参数
        success: function () { }, // 成功回调
        error: function () { }, // 失败回调
        delayCallback: false // 是否在success等弹框点击确定后再执行回调函数
    }
    $.extend(param, paramObj);

    var loadIndex = $.etDialog.load();

    $.ajax({
        type: 'POST',
        url: param.url,
        async: param.async, 
        data: param.data,
        dataType: 'json',
        success: function (res) {
            $.etDialog.close(loadIndex);

            if (!res) {
                param.error(res);
                return;
            }

            if (typeof res === 'string') {
                param.success(res);
                return;
            }

            if (typeof res === 'object') {
                // 成功
                if (res.msg) {

                    if (param.delayCallback) {
                        $.etDialog.success(res.msg, function (index) {
                            $.etDialog.close(index);
                            param.success(res);
                        });
                    } else {
                        $.etDialog.success(res.msg);
                        param.success(res);
                    }

                    // 错误
                } else if (res.error) {

                    if (param.delayCallback) {
                        $.etDialog.error(res.error, function (index) {
                            $.etDialog.close(index);
                            param.error(res);
                        });
                    } else {
                        $.etDialog.error(res.error);
                        param.error(res);
                    }

                    // 警告
                } else if (res.warn) {

                    if (param.delayCallback) {
                        $.etDialog.warn(res.warn, function (index) {
                            $.etDialog.close(index);
                            param.error(res);
                        });
                    } else {
                        $.etDialog.warn(res.warn);
                        param.error(res);
                    }

                    // 正常获取数据
                } else {
                    param.success(res);
                }
                return;
            }

        },
        error: function (XMLHttpRequest, errorMsg, reason) {
            $.etDialog.close(loadIndex);

            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");

            if (sessionstatus === "MSG_TIME_OUT") {

                alert('会话超时，请重新登录.');
                // 跳转登录页面
                // window.top.location.href="login.html";

            } else if (sessionstatus === "NOT_PERMID") {

                $.etDialog.error('没有该操作权限.');

            } else if (sessionstatus === "TOKEN_MAPPING") {

                $.etDialog.error("重复提交数据.");

            } else if (sessionstatus === "REQUEST_MAPPING") {

                $.etDialog.error("没有找到对应的请求.");

            } else if (errorMsg === "parsererror") {

                $.etDialog.error("返回类型不是json.");

            } else {

                if (XMLHttpRequest.status && XMLHttpRequest.status === "404") {

                    $.etDialog.error('没有找到对应的请求404.');

                } else {

                    $.etDialog.error('操作失败.');

                }

            }
        }
    })
};

/**
 * 表单提交
 * @param {*} paramObj 
 */
var ajaxPostFormData = function (paramObj) {
    // $.ajax({
    //     url: paramObj.url,
    //     type: "POST",
    //     data: paramObj.data,
    //     dataType: "json",
    //     async: false,
    //     cache: false,
    //     contentType: false,
    //     processData: false,
    //     success: function (data) {
    //         if (typeof paramObj.success === 'function') {
    //             paramObj.success(data);
    //         }
    //     },
    //     error: function (data) {
    //         console.log(data.status + " : " + data.statusText + " : " + data.responseText);
    //     }
    // });
    var param = {
        url: '', // 请求路径
        data: {}, // 请求参数
        success: function () { }, // 成功回调
        error: function () { }, // 失败回调
        delayCallback: false // 是否在success等弹框点击确定后再执行回调函数
    }
    $.extend(param, paramObj);

    var loadIndex = $.etDialog.load();

    $.ajax({
        type: 'POST',
        url: param.url,
        data: param.data,
        dataType: 'json',
        sync: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res) {
            $.etDialog.close(loadIndex);

            if (!res) {
                param.error(res);
                return;
            }

            if (typeof res === 'string') {
                param.success(res);
                return;
            }

            if (typeof res === 'object') {
                // 成功
                if (res.msg) {

                    if (param.delayCallback) {
                        $.etDialog.success(res.msg, function (index) {
                            $.etDialog.close(index);
                            param.success(res);
                        });
                    } else {
                        $.etDialog.success(res.msg);
                        param.success(res);
                    }

                    // 错误
                } else if (res.error) {

                    if (param.delayCallback) {
                        $.etDialog.error(res.error, function (index) {
                            $.etDialog.close(index);
                            param.error(res);
                        });
                    } else {
                        $.etDialog.error(res.error);
                        param.error(res);
                    }

                    // 警告
                } else if (res.warn) {

                    if (param.delayCallback) {
                        $.etDialog.warn(res.warn, function (index) {
                            $.etDialog.close(index);
                            param.error(res);
                        });
                    } else {
                        $.etDialog.warn(res.warn);
                        param.error(res);
                    }

                    // 正常获取数据
                } else {
                    param.success(res);
                }
                return;
            }

        },
        error: function (XMLHttpRequest, errorMsg, reason) {
            $.etDialog.close(loadIndex);

            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");

            if (sessionstatus === "MSG_TIME_OUT") {

                alert('会话超时，请重新登录.');
                // 跳转登录页面
                // window.top.location.href="login.html";

            } else if (sessionstatus === "NOT_PERMID") {

                $.etDialog.error('没有该操作权限.');

            } else if (sessionstatus === "TOKEN_MAPPING") {

                $.etDialog.error("重复提交数据.");

            } else if (sessionstatus === "REQUEST_MAPPING") {

                $.etDialog.error("没有找到对应的请求.");

            } else if (errorMsg === "parsererror") {

                $.etDialog.error("返回类型不是json.");

            } else {

                if (XMLHttpRequest.status && XMLHttpRequest.status === "404") {

                    $.etDialog.error('没有找到对应的请求404.');

                } else {

                    $.etDialog.error('操作失败.');

                }

            }
        }
    })
};

var exportGrid = function (grid) {
    var data = buildGridDataForExport(grid);
    $.ajax({
        url: 'http://118.178.184.131:9090/export',
        type: 'post',
        data: data,
        dataType: 'text',
        success: function (res) {
            var url = 'data:application/vnd.ms-excel;base64,' + res;
            $(document.body).append("<iframe height='0' width='0' frameborder='0'  src=" + url + "></iframe>");
        }
    });


    function buildGridDataForExport(grid) {
        var columns = [];
        var rows = [];
        var dataIndex = [];
        $(grid.getColumns()).each(function (i, v) {
            if (v.title) {
                columns.push({ caption: v.title, type: 'string' });
                dataIndex.push(v.dataIndx);
            }
        });
        $(grid.getAllData()).each(function (i, v) {
            var row = [];
            $(dataIndex).each(function () {
                var val = v[this] || '';
                row.push(val.toString());
            });
            rows.push(row);
        });
        return { columns: JSON.stringify(columns), rows: JSON.stringify(rows) };
    }
};

/**
 * 导入
 */
var importSpreadView = function (url, para) {
    parent.$.etDialog.open({
        url: '/CHD-HRP/print/importForHr.jsp',
        frameName: window.name,
        title: '导入',
        isMax: true
    });
    window.dataForImport = {
        url: url,
        para: para
    };
};

// 绑定快捷键
$(function () {
    $("[hotkey]").each(function (i, v) {
        var _hotkey = $(v).attr('hotkey').charCodeAt();
        var _ctrl = $(v).attr('ctrl')
        _ctrl = _ctrl ? _ctrl === 'true' ? true : false : false;
        var _alt = $(v).attr('alt');
        _alt = _alt ? _alt === "true" ? true : false : false;
        var that = this;
        // 绑定事件
        (function () {
            var clear;
            $(document).keydown(function (e) {
                var code = e.which;
                var ctrlKey = e.ctrlKey;
                var altKey = e.altKey;
                if (code === _hotkey && ctrlKey === _ctrl && altKey === _alt) {
                    clearTimeout(clear);
                    clear = setTimeout(function () {
                        $(that).click();
                    }, 100);
                }
            });
        })();
    });
});
