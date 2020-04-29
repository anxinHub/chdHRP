<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-dialog.css">
<style>
    html, body {
        height: 100%;
    }
    body {
        margin: 0;
        padding: 0;
        font-size: 14px;
        background: #f0f0f0;
        -webkit-user-select: none;
           -moz-user-select: none;
            -ms-user-select: none;
                user-select: none;
    }
    ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }
    .module-tool .choose-list,
    .module-tool .button-list,
    .module-settings ul {
        width: 100%;
        padding: 10px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        font-size: 0;
    }
    .module-tool {
        background: #fff;
        -webkit-box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
                box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        z-index: 9;
    }
    .choose-list div {
        display: inline-block;
        font-size: 14px;
        padding: 5px 8px;
        margin-right: 8px;
        border: 1px solid #bed5f3;
        border-radius: 3px;
        background: #3d84a8;
        color: #fff;
    }
    .choose-list div.choose-item {
        background: #3d84a8 url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 5px;
        background-size: 20px;
        padding: 5px 8px 5px 22px;
    }
    .choose-list div.choose-item.draged {
        background: #999;
        cursor: default;
        padding: 5px 8px;
    }
    .choose-item {
        display: inline-block;
        font-size: 14px;
        padding: 5px 8px 5px 22px;
        margin-right: 8px;
        border: 1px solid #bed5f3;
        border-radius: 3px;
        background: #3d84a8 url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 5px;
        background-size: 20px;
        color: #fff;
        cursor: move;
    }
    /*.choose-list div {
        display: inline-block;
        font-size: 14px;
        padding: 5px 8px;
        margin-right: 8px;
        border: 1px solid #bed5f3;
        border-radius: 3px;
        background: #fff;
        margin-bottom: 5px;
        color: #333;
    }
    .choose-list div.choose-item {
        background: #fff url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 5px;
        -webkit-background-size: 18px;
        background-size: 18px;
        padding: 5px 8px 5px 22px;
    }
    .choose-list .choose-item.draged {
        background: #eee;
        cursor: default;
        padding: 5px 8px;
    }
    .choose-item {
        display: inline-block;
        font-size: 14px;
        padding: 5px 8px 5px 22px;
        margin-right: 8px;
        border: 1px solid #bed5f3;
        border-radius: 3px;
        background: #fff url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 5px;
        -webkit-background-size: 18px;
        background-size: 18px;
        color: #333;
        cursor: move;
    }*/

    .choose-item.draging {
        position: absolute;
        opacity: 0.5;
        z-index: 99;
    }
    /*按钮列表*/
    .button-list {
        border-top: 1px solid #bed5f3;
        border-bottom: 1px solid #bed5f3;
    }
    .button-list:after {
        content: "";
        display: block;
        clear: both;
    }
    .button-list li {
        margin-right: 14px;
        float: left;
    }
    .button-list li button {
        font-size: 14px;
        padding: 3px 8px;
        cursor: pointer;
        border: 1px solid #bed5f3;
        background: #eaf2fe;
        outline: none;
        color: #48466d;
        border-radius: 3px;
        -webkit-transition: all 0.3s;
        transition: all 0.3s;
    }
    .button-list li button.disabled {
        background: #ddd;
    }
    .button-list li button.disabled:hover {
        -webkit-box-shadow: none;
                box-shadow: none;
    }
    .button-list li button:hover {
        -webkit-box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.2);
                box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.2);
        -webkit-transition: all 0.3s;
        transition: all 0.3s;
    }
    .button-list button.button-set-button {
        display: none;
    }
    .button-list .drag-module {
        font-size: 14px;
        cursor: move;
        outline: none;
        color: #48466d;
        display: none;
    }
    .button-list .drag-module .drag-module-way {
        border: 1px solid #aaa;
        background: #999;
        padding: 3px 8px;
        border-radius: 3px;
        width: 50px;
        display: inline-block;
        text-align: center;
    }
    .button-list .drag-module .drag-text {
        border: 1px solid #aaa;
        background: #aaa url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 3px;
        background-size: 20px 20px;
        color: #fff;
        padding: 3px 8px 3px 22px;
        margin-left: 3px;
        border-radius: 5px;
    }


    /*设置模块*/
    .module-settings {
        padding-top: 100px;
        min-width: 700px;
        overflow: auto;
    }
    .module-settings .module-list:after {
        content: "";
        display: block;
        clear: both;
    }
    .module-item-wrap {
        display: inline-block;
        float: left;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        padding: 6px 10px;
        width: 50%;
        min-width: 300px;
    }
    .module-item-wrap.full-row {
        width: 100%;
    }
    
    .module-item {
        border-radius: 3px;
        -webkit-box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.3);
                box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.3);
    }
    .module-head {
        font-size: 14px;
        height: 26px;
        line-height: 26px;
        background: #3d84a8;
        padding: 0 15px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        color: #fff;
    }
    /*.module-item {
        border-radius: 5px;
        border: 1px solid #ccc;
    }
    .module-head {
        font-size: 14px;
        height: 26px;
        line-height: 26px;
        background: #fff;
        padding: 0 15px;
        box-sizing: border-box;
        color: #333;
        border-bottom: 1px solid #ccc;
    }*/

    .module-content {
        font-size: 14px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        background: #fff;
        height: 100px;
        padding: 10px;
    }
    /*设置表单*/
    .set-form {
        margin: 0;
        line-height: 30px;
        padding-left: 20px;
    }
    .set-form input,
    .set-form select {
        width: 60px;
        border-radius: 2px;
        border: 1px solid #bed5f3;
    }
    .set-form .form-tip {
        margin-left: 20px;
        color: #ff6102;
    }
    .set-form .set-notice {
        color: #2e6aaf;
        line-height: 50px;
        cursor: pointer;
    }

    /*设置模块布局*/
    .module-layout {
        font-size: 0;
        text-align: center;
    }
    .module-layout li {
        z-index: 1;
        position: relative;
        margin: 20px 0;
    }
    .module-layout li:after {
        content: "";
        display: block;
        clear: both;
    }
    .module-layout li.draging {
        position: absolute;
        opacity: 0.5;
        width: 50%;
        z-index: 99;
        cursor: move;
    }
    .module-layout .layout-chunk-wrap {
        height: 80px;
        padding: 10px;
        display: inline-block;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
    }
    .module-layout .layout-6 {
        width: 50%;
        float: left;
    }
    .module-layout .layout-12 {
        width: 100%;
    }
    .module-layout .layout-chunk {
        border: 1px dashed #999;
        border-radius: 5px;
        height: 100%;
        background: #f9f9f9;
    }
    .module-layout .layout-chunk .choose-item {
        margin-top: 15px;
    }
    .module-layout .layout-buttons {
        position: absolute;
        top: 0;
        right: 10px;
        font-size: 12px;
        opacity: 0.5;
    }
    .module-layout .layout-buttons:hover {
        opacity: 1;
    }
    .module-layout .layout-buttons span {
        margin-right: 10px;
        padding: 2px 5px 2px 22px;
        border: 1px solid #999;
        border-radius: 3px;
        color: #fff;
        width: 16px;
        height: 16px;
    }
    .module-layout .layout-buttons span.layout-drag-button {
        background: #999 url(<%=path %>/lib/images/portal-drag.png) no-repeat 0 2px;
        background-size: 16px;
        cursor: move;
    }
    .module-layout .layout-buttons span.layout-remove-button {
        background: #999 url(<%=path %>/lib/images/portal-delete.png) no-repeat 0 2px;
        background-size: 16px;
        cursor: pointer;
    }
</style>
</head>

<body>
    <div class="module-tool">
        <div class="choose-list"></div>
        <ul class="button-list">
            <li><button class="button-save">保存</button></li>
            <li><button class="button-set">布局设置</button></li>
            <li>
                <div class="drag-module drag-full">
                    <span class="drag-module-way">12</span>
                    <span class="drag-text">拖动</span>
                </div>
            </li>
            <li>
                <div class="drag-module drag-falf">
                    <span class="drag-module-way">6 * 6</span>
                    <span class="drag-text">拖动</span>
                </div>
            </li>
            <li><button class="button-set-button set-confirm">确认</button></li>
            <li><button class="button-set-button set-cancle">取消</button></li>
        </ul>
    </div>

    <div class="module-settings">
        <ul class="module-list"></ul>
    </div>
</body>

<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"></script>
<script>
    (function () {
        // var moduleElArray = [];
        // var chooseElArray = [];
        var initData = [], currentData = [];
        var $moduleSettings = $(".module-settings");
        var $moduleList = $(".module-list");
        var $moduleLayout;

        renderModules();

        // 关闭按钮点击事件
        // $moduleSettings.on("click", ".module-remove", function () {
        //     var $moduleItem = $(this).parent().parent().parent();
        //     removeModule($moduleItem);
        // })

        /**
         * 保存按钮
         */
        $(".button-save").on("click", function () {
            // if (currentData) {
            //     currentData.forEach(function (item, index) {
            //         item.sort_code = index + 1;

            //         if (typeof item.para_json === "object") {
            //             item.para_json = JSON.stringify(item.para_json);
            //         }
            //     })
            // } else {
            //     currentData = $.map(initData, function (item, index) {
            //         return {
            //             title_code: item.title_code,
            //             title_name: item.title_name,
            //             show_rows: item.show_rows,
            //             is_colspan: item.is_colspan,
            //             sort_code: index + 1,
            //         }
            //     })
            // }

            currentData.forEach(function (item, index) {
                item.sort_code = index + 1;
                item.is_more = String(item.is_more);
            })
            $.ajax({
                type: "POST",
                data: {
                    ParamVo: JSON.stringify(currentData)
                },
                dataType: "json",
                url: "../../../hrp/portal/saveSysPortalTitleSet.do?isCheck=false",
                success: function (result) {
                    if (result.state) {
                        $.ligerDialog.success(result.msg);
                    } else {
                        $.ligerDialog.error(result.error);
                    }
                }
            })
        })

        /**
         * 布局设置按钮
         */
        $(".button-set").on("click", function () {
            var _this = $(this);
            var $buttonSetButton = $(".button-set-button");

            _this.addClass("disabled").attr("disabled", true);
            $(".button-save").addClass("disabled").attr("disabled", true);
            
            $(".drag-module").show();
            $buttonSetButton.show();

            $(".choose-list").children().each(function (index, item) {
                $(item).addClass("choose-item").addClass("choose-item-" + index + "")
            })

            $moduleList.remove();
            renderLayout();
            
            $moduleSettings.css("padding-top", $(".module-tool").height())
        })

        $(".button-set-button").on("click", function () {
            /**
             * 布局确认事件
             */
            if ($(this).hasClass("set-confirm")) {
                var chooseItemArr = $moduleSettings.find(".choose-item");
                var newLayoutData = [];

                chooseItemArr.each(function (index, item) {
                    var newLayoutItem = {};
                    var layoutWrap = $(item).parent().parent();

                    newLayoutItem.title_name = $(item).text();
                    newLayoutItem.title_code = $(item).attr("data-code");
                    
                    if (layoutWrap.hasClass("layout-12")) {
                        newLayoutItem.is_colspan = 2;
                    } else if (layoutWrap.hasClass("layout-6")) {
                        newLayoutItem.is_colspan = 1;
                    }
                    newLayoutData.push(newLayoutItem);
                })

                newLayoutData.forEach(function (item, index) {
                    // 判断需不需要在原始数据中查找
                    var findInInit = true;

                    for (var k = 0; k < currentData.length; k++) {
                        if (item.title_code === currentData[k].title_code) {
                            findInInit = false;
                            item.is_more = currentData[k].is_more;
                            item.mod_code = currentData[k].mod_code;
                            item.show_rows = currentData[k].show_rows;
                            item.title_type = currentData[k].title_type;
                        }
                    }
                    if (findInInit) {
                        for (var j = 0; j < initData.length; j++) {
                            if (item.title_code === initData[j].title_code) {
                                item.is_more = initData[j].is_more;
                                item.mod_code = initData[j].mod_code;
                                item.title_type = initData[j].title_type;
                                item.show_rows = 5; // 默认给值为5，initData没有show_rows
                            }
                        }
                    }
                })

                $moduleList.children().remove();

                currentData = newLayoutData;

                for (var i = 0; i < newLayoutData.length; i++) {
                    $moduleList.append(createModuleHtml(newLayoutData[i]));
                }

            /**
             * 布局取消事件
             */
            } else if ($(this).hasClass("set-cancle")) {

            }

            $moduleList.appendTo($moduleSettings);
            $moduleLayout.remove();
            $(".choose-list").children().removeClass();
            $(".button-set").removeClass("disabled").attr("disabled", false);
            $(".button-save").removeClass("disabled").attr("disabled", false);
            $(".button-set-button").hide();
            $(".drag-module").hide();
            $moduleSettings.css("padding-top", $(".module-tool").height())
        })

        /**
         * 改变展示行数时，更改currentData
         */
        $moduleSettings.on("change", ".showRow", function () {
            var $this = $(this);
            var index = $this.parent().parent().parent().parent().index();

            currentData[index].show_rows = Number($this.val());
        })
        
        /**
         * layout布局删除事件
         */
        $(".module-settings").on("click", ".layout-remove-button", function () {
            var $thelayout = $(this).parent().parent();

            findTagRemove($thelayout);

            $thelayout.remove();
        })

       

        $(".module-settings").on("click", ".set-notice", function () {
            parent.$.ligerDialog.open({
                url: "hrp/portal/sysNoticePage.do?isCheck=false",
                title: "设置",
                parentframename: window.name,
                isResize: false,
                showMin: false,
                showMax: true,
                slide: false
            })
        })

        /**
         * 鼠标拖拽事件
         */
        var isDragLayout = false;
        var isDragTag = false;
        var isDragMove = false;
        var $layoutHtml;
        var $chooseTag; // 拖动后tag
        var $theTag; // 拖动前tag
        /**
         * 绑定选择布局模型的拖拽事件
         */
        $(".drag-module").on("mousedown", function () {
            isDragLayout = true;
            $this = $(this);

            if ($this.hasClass("drag-full")) {
                $layoutHtml = $(creatLayoutHtml("full"));
            } else if ($this.hasClass("drag-falf")) {
                $layoutHtml = $(creatLayoutHtml("half"));
            }
        })
        /**
         * 移动后的layout，再拖拽
         */
        $(".module-settings").on("mousedown", ".layout-drag-button", function () {
            isDragLayout = true;
            $layoutHtml = $(this).parent().parent();
        })
        /**
         * 绑定选择列表拖拽事件
         */
        $(".choose-list").on("mousedown", ".choose-item", function () {
            var $this = $(this);

            if (!$this.hasClass("draged")) {
                isDragTag = true;
                $chooseTag = $this.clone(true);

                $theTag = $this;
            }
        })

        /**
         * 移动后的tag，再次拖拽
         */
        $(".module-settings").on("mousedown", ".choose-item", function () {
            isDragTag = true;
            $chooseTag = $(this)

            var classArray = $chooseTag.attr("class").split(" ");
            var chooseClass = [];
            classArray.forEach(function (item) {
                chooseClass.push("." + item);
            })
            chooseClass = chooseClass.join("");

            $theTag = $(chooseClass).eq(0);
        })
        $(document)
            .on("mousemove", function (e) {
                var event = e || window.event;
                // 移动layout事件
                if (isDragLayout) {
                    isDragMove = true;

                    $layoutHtml
                        .appendTo($moduleLayout)
                        .addClass("draging")
                        .css({
                            "top": event.pageY - 20,
                            "left": event.pageX - $layoutHtml.width() / 1.2
                        });

                    // 如果移出窗口结束
                    if (event.pageX <= 0 || event.pageY <= 0) {
                        $layoutHtml.remove();
                        findTagRemove($layoutHtml);

                        isDragLayout = false;
                        isDragMove = false;
                        return;
                    }

                // 移动tag事件
                } else if (isDragTag) {
                    isDragMove = true;

                    $chooseTag
                        .appendTo($("body"))
                        .addClass("draging")
                        .css({
                            "top": event.pageY - 10,
                            "left": event.pageX - $chooseTag.width() / 2
                        })

                    // 如果移出窗口结束
                    if (event.pageX <= 0 || event.pageY <= 0) {
                        $chooseTag.remove();
                        isDragTag = false;
                        isDragMove = false;
                        $theTag.removeClass("draged");
                        return;
                    }
                }
            })
            .on("mouseup", function (e) {
                var event = e || window.event;
                var x = e.pageX;
                var y = e.pageY;

                if (isDragLayout && isDragMove) {
                    isDragLayout = false;
                    isDragMove = false;

                    if (y > $moduleLayout.offset().top) {
                        $layoutHtml
                            .removeClass("draging")
                            .css({
                                "left": "0",
                                "top": "0"
                            });

                        y -= $(".module-tool").height();
                        var diff = y % 100;
                        var index = Math.floor(y / 100);
                        var currentListNum = $moduleLayout.children("li").length - 1; // 当前li的个数，除去drag的
                        
                        if (index < currentListNum) {
                            if (diff < 30) {
                                $layoutHtml.remove().insertBefore($moduleLayout.children("li").eq(index))
                            } else {
                                findTagRemove($layoutHtml);
                                $layoutHtml.remove();
                            }
                        }

                    } else {
                        findTagRemove($layoutHtml);
                        $layoutHtml.remove();
                    }

                } else if (isDragTag && isDragMove) {
                    isDragTag = false;
                    isDragMove = false;

                    if (y > $moduleLayout.offset().top) {
                        
                        $chooseTag
                            .removeClass("draging")
                            .css({
                                "top": "0",
                                "left": "0"
                            })

                        y -= $(".module-tool").height();
                        var diff = y % 100;
                        var index = Math.floor(y / 100);
                        var currentListNum = $moduleLayout.children("li").length;
                        var $appendLayout = $($moduleLayout.children("li")[index]);

                        // 判断有没有layout，偏差在内容框中
                        if (currentListNum > 0 && $appendLayout && diff > 20) {
                            
                            // 判断是否在full的内容框中   (终点)
                            if ($appendLayout.children().eq(0).hasClass("layout-12") && $appendLayout.find(".layout-chunk").children().length === 0) {
                                $chooseTag.remove().appendTo($appendLayout.find(".layout-chunk"));
                                $theTag.addClass("draged");

                            // 判断是否在half的内容框中
                            } else if ($appendLayout.children().eq(0).hasClass("layout-6")) {
                                var $left = $appendLayout.children().eq(0);
                                var $right = $appendLayout.children().eq(1);

                                // 判断是否在half的左边   (终点)
                                if ($left.offset().left < x && x < $left.offset().left + $left.width() && $left.find(".layout-chunk").children().length === 0) {
                                    $chooseTag.remove().appendTo($left.find(".layout-chunk"));
                                    $theTag.addClass("draged");

                                // 判断是否在half的右边   (终点)
                                } else if ($right.offset().left < x && x < $right.offset().left + $right.width() && $right.find(".layout-chunk").children().length === 0) {
                                    $chooseTag.remove().appendTo($right.find(".layout-chunk"));
                                    $theTag.addClass("draged");

                                } else {
                                    $chooseTag.remove();
                                    $theTag.removeClass("draged");
                                }
                            } else {
                                $chooseTag.remove();
                                $theTag.removeClass("draged");
                            }
                        } else {
                            $chooseTag.remove();
                            $theTag.removeClass("draged");
                        }
                    } else {
                        $chooseTag.remove();
                        $theTag.removeClass("draged");
                    }
                }

                if (isDragLayout || isDragTag) {
                    isDragLayout = false;
                    isDragTag = false;
                }
            })

        /**
         * 动态改变module-settings的padding-top
         */
        $(window).on("resize", function () {
            $moduleSettings.css("padding-top", $(".module-tool").height())
        })
        $moduleSettings.css("padding-top", $(".module-tool").height())
        /**
         * 生成module html的函数
         * @param  {obj} param 要填入的参数
         * @return {obj}       module的节点
         */
        function createModuleHtml (param) {
            var mp = param

            var $moduleHtml = 
                $('<li class="module-item-wrap">' +
                    '<div class="module-item">' +
                        '<div class="module-head" data-code="' + mp.title_code + '">' +
                            mp.title_name +
                        '</div>' +
                        '<div class="module-content">' +
                            '<form class="set-form">' +
                                // '<label>展示类型：</label>' +
                                // '<select class="chooseType" name="chooseType">' +
                                //     // '<option value=""></option>' +
                                //     '<option value="grid">表格</option>' +
                                //     '<option value="link">链接</option>' +
                                //     '<option value="notice">通知</option>' +
                                //     '<option value="chart">图表</option>' +
                                //     '<option value="bulletin">公告</option>' +
                                // '</select>' +
                                // '<br>' +
                                '<label>数据条数：</label>' +
                                '<input class="showRow" type="number" value="' + mp.show_rows + '" min="0">' +
                                '<br>' +
                                // '<label>展示列数：</label>' +
                                // '<select class="showCol" name="showCol">' +
                                //     '<option value="1">1</option>' +
                                //     '<option value="2">2</option>' +
                                // '</select>' +
                                // '<br>' +
                            '</form>' +
                        '</div>' +
                    '</div>' +
                '</li>');

            if (mp.is_colspan === 2) {
                $moduleHtml.addClass("full-row")
            }
            if (mp.title_type === "notice") {
                $moduleHtml.find(".set-form").html("<a class='set-notice'>设置</a>")
            }
            return $moduleHtml;
        }

        /**
         * 页面初始渲染数据
         */
        function renderModules () {
            $.ajax({
                type: "POST",
                data: {},
                dataType: "json",
                url: '../../../hrp/portal/queryMedPortalSetInfo.do?isCheck=false',
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {
                        currentData.push({
                            mod_code: result[i].mod_code,
                            title_code: result[i].title_code,
                            title_name: result[i].title_name,
                            title_type: result[i].title_type,
                            show_rows: result[i].show_rows,
                            is_colspan: result[i].is_colspan,
                            is_more: result[i].more_url ? 1 : 0
                        })

                        var $moduleEl = createModuleHtml(result[i]);

                        $moduleList.append($moduleEl);
                    }
                }
            })
            $.ajax({
                type: "POST",
                data: {},
                dataType: "json",
                url: '../../../hrp/portal/queryMedPortalTitle.do?isCheck=false',
                success: function (result) {
                    for (var j = 0; j < result.length; j++) {
                        if (result[j].title_code === "00002" || result[j].title_code === "00003" || result[j].title_code === "00004") {
                            continue;
                        }

                        initData.push({
                            mod_code: result[j].mod_code,
                            title_code: result[j].title_code,
                            title_name: result[j].title_name,
                            title_type: result[j].title_type,
                            is_more: result[j].more_url ? 1 : 0
                        })

                        var $chooseEl = $("<div data-code=" + result[j].title_code + ">" + result[j].title_name + "</div>");

                        $(".choose-list").append($chooseEl);

                        if (result[j].title_code === "00001") {
                            $(".choose-list").append("<br>")
                        }
                    }

                    // 动态样式
                    $moduleSettings.css("padding-top", $(".module-tool").outerHeight());
                }
            })
        }

        /**
         * 生成布局html字符串
         * @param  {string} chunk [布局类型，可选half，full]
         * @return {string}       [布局html string]
         */
        function creatLayoutHtml (chunk) {
            var layoutHtml = "<li>";

            if (chunk === "half") {
                layoutHtml +=
                    '<div class="layout-chunk-wrap layout-6">' +
                        '<div class="layout-chunk"></div>' +
                    '</div>' +
                    '<div class="layout-chunk-wrap layout-6">' +
                        '<div class="layout-chunk"></div>' +
                    '</div>';
            } else if (chunk === "full") {
                layoutHtml +=
                    '<div class="layout-chunk-wrap layout-12">' +
                        '<div class="layout-chunk"></div>' +
                    '</div>'; 
            }

            layoutHtml += 
                '<div class="layout-buttons">' +
                    '<span class="layout-drag-button">拖动</span>' +
                    '<span class="layout-remove-button">删除</span>' +
                '</div>' +
                '</li>';

            return layoutHtml;
        }


        /**
         * 从module渲染到layout
         */
        function renderLayout () {
            $moduleLayout = $('<ul class="module-layout"></ul>');

            for (var i = 0; i < currentData.length; i++) {
                var $layoutHtml, $chooseEl;
                var is_colspan = currentData[i].is_colspan;
                var title_code = currentData[i].title_code;

                $(".choose-list").children("div").each(function (index, item) {
                    if (currentData[i].title_code === $(item).attr("data-code")) {
                        $chooseEl = $(item).addClass("draged").clone(true);
                    }
                })

                if (is_colspan === 1) {
                    $layoutHtml = $(creatLayoutHtml("half"));
                    $layoutHtml.find(".layout-chunk").eq(0).append($chooseEl);

                    // 判断下一个是不是为1。是的话拔下一个也添加进去
                    if (currentData[i+1] && currentData[i+1].is_colspan === 1) {
                        $(".choose-list").children("div").each(function (index, item) {
                            if (currentData[i+1].title_code === $(item).attr("data-code")) {
                                $chooseEl = $(item).addClass("draged").clone(true);
                            }
                        })
                        $layoutHtml.find(".layout-chunk").eq(1).append($chooseEl);
                        i++;
                    }
                } else if (is_colspan === 2) {
                    $layoutHtml = $(creatLayoutHtml("full"));
                    $layoutHtml.find(".layout-chunk").append($chooseEl);
                }

                $moduleLayout.append($layoutHtml);
            }

            $moduleSettings.append($moduleLayout);
        }

         /**
         * 寻找chooseitem，如果有把对应的选择tag的draged移除
         * @param  {jq obj} layout [jquery 布局节点]
         * @return {[type]}        [description]
         */
        function findTagRemove (layout) {
            layout.find(".choose-item").each(function (index, item) {
                var classArray = $(item).attr("class").split(" ");
                var chooseClass = [];

                classArray.forEach(function (item) {
                    chooseClass.push("." + item);
                })
                chooseClass = chooseClass.join("");

                $(chooseClass).eq(0).removeClass("draged")
            })
        }
    })()
</script>
</html>