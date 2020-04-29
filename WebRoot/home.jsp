<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    //out.print(path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
    html, body {
        height: 100%;
    }
    body {
        margin: 0;
        font-size: 14px;
        overflow:hidden;
        background: #f9f9f9;
    }
    .l-loading { 
        background: url(<%=path%>/lib/images/loading.gif) no-repeat;
        -webkit-background-size: cover;
        background-size: cover;
        position: absolute;
        width: 20px;
        height: 20px;
        z-index: 99999;
        top: 50%;
        left: 50%;
        margin-top: -10px;
        margin-left: -10px;
    }
    /*模块列表*/
    #module-list {
        margin: 0;
        padding: 0;
        width: 100%;
        min-width: 700px;
        height: 100%;
        list-style: none;
        font-size: 0;
        overflow: auto;
    }
    /*列表项外层*/
    .module-item-wrap {
        display: inline-block;
        float: left;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        padding: 6px 10px;
        min-height: 200px;
        width: 50%;
        min-width: 300px;
    }
    .all-nothing {
        width: 920px;
        height: 500px;
        /*background: url(<%=path%>/lib/images/nothing-bg.png) no-repeat;*/
        -webkit-background-size: cover;
        background-size: cover;
        font-size: 20px;
        text-align: center;
        line-height: 50px;
        position: relative;

        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -460px;
        margin-top: -250px;
    }
    /*.all-nothing p {
        text-align: center;
        font-size: 30px;
        height: 40px;
        color: #fff;
        position: absolute;
        top: 35%;
        left: 0;
        right: 0;
    }*/
    .module-item-wrap.full-row {
        width: 100%;
    }
    .module-item {
        border-radius: 3px;
        border: 1px solid #bbb;
        position: relative;
        background: #fff;
    } 
    .module-head {
        font-size: 14px;
        height: 26px;
        line-height: 26px;
        background: url(lib/ligerUI/skins/Aqua/images/grid/header-bg.gif) repeat; 
        padding: 0 15px;  
        -webkit-box-sizing: border-box; 
                box-sizing: border-box; 
        color: #333;
        font-weight: bold;
        border-bottom: 1px solid #bbb;
    }
    .module-more {
        float: right;
        cursor: pointer;
        color: #333;
        text-decoration: none;
    }
    /*.module-item {
        border-radius: 3px;
        border: 1px solid #bbb;
        box-shadow: 1px 1px 2px 2px rgba(0, 0, 0, 0.3);
    }
    .module-head {
        font-size: 14px;
        height: 26px;
        line-height: 26px;
        background: #3d84a8;
        padding: 0 15px;
        box-sizing: border-box;
        color: #fff;
    }
    .module-more {
        float: right;
        cursor: pointer;
        color: #fff;
    }*/
    .module-more:hover {
        text-decoration: underline;
        color: #fb9a5f;
    }
    .module-content {
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        min-height: 174px;
        padding: 10px;
        box-sizing: border-box;
        overflow: auto;
    }
    /*表格形式*/
    /*.module-grid {
        font-size: 14px;
        border-top: 1px solid #a3c0e8;
        border-left: 1px solid #a3c0e8;
    }
    .module-grid tr {
        height: 22px;
        line-height: 22px;
        width: auto;
    }
    .module-grid tr:hover {
        background: #e0ecff;
    }
    .module-grid th {
        font-weight: bold;
        text-align: center;
        background: url("lib/ligerUI/skins/Aqua/images/grid/header-bg.gif") repeat-x;
        padding: 0 5px;
        border-bottom: 1px solid #a3c0e8;
        border-right: 1px solid #a3c0e8;
        white-space: nowrap;
    }
    .module-grid td {
        text-align: center;
        padding: 0 5px;
        border-bottom: 1px solid #a3c0e8;
        border-right: 1px solid #a3c0e8;
        white-space: nowrap;
    }*/
    .module-grid {
        font-size: 14px;
        width: 100%;
        position: relative; 
    }
    .module-grid:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: 1px dashed #bbb;
        position: absolute;
        left: 0;
    }
    .module-grid tr {
        display: table-row;
        border-bottom: 1px dashed #bbb;
        height: 30px;
        line-height: 30px;
        text-align: left;
        position: relative;
        box-sizing: border-box;
    }
    .module-grid tr:hover {
        background: #f9f9f9;
        cursor: pointer;
    }
    .module-grid tr:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: 1px dashed #bbb;
        position: absolute;
        left: 0;
    }
    .module-grid tr:first-child:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: none;
    }
    .module-grid tr td {
        padding: 0;
        height: 30px;
        box-sizing: border-box;
    }
    .module-grid tr td:first-child,
    .module-grid tr th:first-child {
        padding-left: 10px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
    }
    /*图表形式*/
    .module-chart {
        font-size: 14px;
        width: 100%;
        height: 100%;
    }
    /*无表头表格超链接*/
    .module-link {
        list-style: none;
        font-size: 14px;
    }
    .module-link:after {
        content: "";
        display: block;
        clear: both;
    }
    .module-link li {
        height: 20px;
        line-height: 20px;
        width: 100%;
        float: left;
    }
    .module-link .col {
        width: 50%;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
    }
    .module-link .col:nth-child(even) {
        border-left: 3px solid #aaa;
    }
    .module-link li a {
        display: block;
        width: 100%;
        color: #333;
        padding: 0 10px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
    .module-link a:hover {
        color: #ff6102;
    }
    /*通知形式*/
    .module-notice {
        list-style: none;
        font-size: 14px;
    }
    /*公布*/
    .module-public li {
        height: 20px;
        line-height: 20px;
    }
    .module-public li a {
        display: inline-block;
        width: 75%;
        padding-left: 5px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        color: #333;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;    
    }
    .module-public li a:hover {
        color: #ff6102;
    }
    .module-public li a.newNotice {
        position: relative;
        padding-left: 26px;
    }
    .module-public li a.newNotice:before {
        content: "New";
        color: red;
        font-size: 12px;
        position: absolute;
        top: -5px;
        left: 0;
        font-style: italic;
                transform: rotate(-7deg);
        -webkit-transform: rotate(-7deg);
    }
    .module-public li span {
        display: inline-block;
        width: 25%;
        max-width: 75px;
        float: right;
        text-align: center;
    }
    /*公报形式*/

    /*无数据*/
    .data-none {
        font-size: 14px;
        line-height: 100px;
        text-align: center;
    }
</style>
</head>

<body>
    <div class="l-loading init-loading"></div>
    <ul id="module-list"></ul>
</body>

<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/hrp/script/dateformat.js"></script>
<script src="<%=path%>/lib/echarts/echarts.js"></script>
<script src='<%=path%>/lib/ckeditor/ckeditor.js'></script>
<script>

    (function () {
    	
        $.ajax({
            type: "POST",
            url: "./hrp/portal/querySysShowPortalInfo.do?isCheck=false",
            data: {},
            dataType: "json",
            success: function (result) {
                var compareHeightArr = []; // 比较高度的数组
                var isAllowCompare = false;

                var $moduleList = $("#module-list");
                if (result.length === 0) {
                    $(".init-loading").hide(); 
                    $moduleList.html("<div class='all-nothing'></div>");
                    return;
                }
                for (var i = 0; i < result.length; i++) {
                    var more_url = result[i].more_url;
                    var is_more = result[i].is_more;
                    var title_name = result[i].title_name;
                    var is_colspan = result[i].is_colspan;

                    var moduleHtml = "";
                    moduleHtml +=
                        '<li class="module-item-wrap">' +
                            '<div class="module-item">' +
                                '<div class="module-head">' +
                                    '<span>' + title_name + '</span>';
                                if (is_more) {
                                    moduleHtml += '<a class="module-more" href="' + more_url + '">more</a>';
                                }
                    moduleHtml +=
                                '</div>' +
                                '<div class="module-content"></div>' +
                                '<div class="l-loading module-loading"></div>' +
                            '</div>' +
                        '</li>';

                    var $moduleHtml = $(moduleHtml);

                    $moduleList.append($moduleHtml);

                    if (is_colspan === 2) {
                        $moduleHtml.addClass("full-row")
                    }

                    $(".init-loading").hide();              

                    // 请求模块的数据
                    (function (data){
                        var resultLength = result.length;
                        var in_$moduleHtml = $moduleHtml;
                        var $moduleContent = in_$moduleHtml.find(".module-content");
                        var view_code = data.view_code;
                        var view_where = data.view_where;
                        var view_column = data.view_column;
                        var show_rows = data.show_rows;
                        var title_code = data.title_code;
                        var type = data.title_type;

                        $.ajax({
                            type: "POST",
                            url: "./hrp/portal/querySysShowPortalData.do?isCheck=false",
                            data: {
                                view_code: view_code,
                                view_column: view_column,
                                view_where: view_where,
                                show_rows: show_rows,
                                title_code: title_code
                            },
                            dataType: "json",
                            success: function (content) {
                                // 如果数据为空
                                var contentData = content[title_code];

                                if (contentData.length === 0) {

                                    // 如果是通知的数据为空，整个栏目不要
                                    if (type === "notice") {
                                        in_$moduleHtml.remove();

                                        if (resultLength === 1) {
                                            // $moduleList.html("<div class='all-nothing'></div>");
                                        }
                                    }
                                    // 移除更多按钮
                                    in_$moduleHtml.find(".module-more").remove();
                                    $moduleContent.next(".module-loading").remove();
                                    $moduleContent.html("<div class='data-none'>暂无数据</div>");

                                } else {
                                    // 判断类型
                                    
                                    // 表格类型
                                    if (type === "grid") {
                                        var moduleGrid = "";
                                        var colData = data.column_name.split(",");
                                        var columnArr = [];

                                        data.view_column.split(",").forEach(function(item, index) {
                                            columnArr.push(item.split(" ")[1]);
                                        })

                                        moduleGrid +=
                                            '<table class="module-grid">';
                                                '<tr>';

                                                for (var l = 0; l < colData.length; l++) {
                                                    moduleGrid += '<th>' + colData[l] + '</th>';
                                                }
                                            moduleGrid += '</tr>';
                                                 
                                                for (var j = 0; j < show_rows; j++) {
                                                    moduleGrid += '<tr>';

                                                    if (contentData[j]) {
                                                        for (var k = 0; k < columnArr.length; k++) {
                                                            var content = contentData[j][columnArr[k]];
                                                            content = content ? content : "";
                                                            moduleGrid += '<td>' + content + '</td>';
                                                        }
                                                    } else {
                                                        for (var k = 0; k < columnArr.length; k++) {
                                                            moduleGrid += '<td></td>';
                                                        }
                                                    }
                                                    moduleGrid += '</tr>';
                                                }
                                            moduleGrid += '</table>';

                                        $moduleContent.html(moduleGrid);

                                    // 通知类型
                                    } else if (type === "notice") {
                                        
                                        var noticeContent = contentData[0].CONTENT;

                                        var noticeHtml = '<div class="module-notice">' + noticeContent + '</div>';

                                        $moduleContent.html(noticeHtml);

                                        // $(document).find("#notice-frame").contents().find("body").html(noticeContent)

                                        // if (CKEDITOR.env.ie && CKEDITOR.env.version < 9) {
                                        //     CKEDITOR.tools.enableHtml5Elements(document);
                                        // }

                                        // CKEDITOR.config.height = 'auto';
                                        // CKEDITOR.config.width = 'auto';
                                        // CKEDITOR.disableAutoInline = true;

                                        // var initSample = (function () {
                                        //     var wysiwygareaAvailable = isWysiwygareaAvailable(),
                                        //         isBBCodeBuiltIn = !!CKEDITOR.plugins.get('bbcode');

                                        //     return function () {
                                        //         var editorElement = CKEDITOR.document.getById('editor');

                                        //         if (isBBCodeBuiltIn) {
                                        //             editorElement.setHtml(
                                        //                 '填写通知内容'
                                        //             );
                                        //         }

                                        //         if (wysiwygareaAvailable) {
                                        //             CKEDITOR.replace('editor');
                                        //         } else {
                                        //             editorElement.setAttribute('contenteditable', 'true');
                                        //             CKEDITOR.inline('editor');
                                        //         }
                                        //     };

                                        //     function isWysiwygareaAvailable () {
                                        //         if (CKEDITOR.revision == ('%RE' + 'V%')) {
                                        //             return true;
                                        //         }

                                        //         return !!CKEDITOR.plugins.get('wysiwygarea');
                                        //     }
                                        // })();
                                        // initSample();

                                    // 链接类型
                                    } else if (type === "link") {
                                        // var linkData = [
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" },
                                        //     { text: "链接链接", link: "" }
                                        // ];

                                        // var linkHtml = '<ul class="module-link">';

                                        //     for (var j = 0; j < linkData.length; j++) {
                                        //         linkHtml +=
                                        //             '<li class="col">' +
                                        //                 '<a href="' + linkData[j].link + '" title="' + linkData[j].text + '">' + linkData[j].text + '</a>' +
                                        //             '</li>';
                                        //     }
                                        // linkHtml += '</ul>';

                                        // $moduleContent.html(linkHtml);

                                    // 图表类型 
                                    } else if (type === "chart") {
                                        // var chartData = {
                                        //     name: "访问来源",
                                        //     data: [
                                        //         {value:335, name:'直接访问'},
                                        //         {value:310, name:'邮件营销'},
                                        //         {value:234, name:'联盟广告'},
                                        //         {value:135, name:'视频广告'},
                                        //         {value:1548, name:'搜索引擎'}
                                        //     ]
                                        // };

                                        // var $chartHtml = $('<div class="module-chart"></div>');

                                        // $moduleContent.html($chartHtml);

                                        // var textChart = echarts.init($chartHtml.get(0));
                                        // var chartOption = {
                                        //     series: [
                                        //         {
                                        //             name: chartData.name,
                                        //             type: 'pie',
                                        //             radius: '55%',
                                        //             center: ['50%', '50%'],
                                        //             data: chartData.data
                                        //         }
                                        //     ]
                                        // }
                                        // textChart.setOption(chartOption);
                                    
                                    // 公告类型
                                    } else if (type === "public") {
                                        var noticeHtml = '<ul class="module-public">';

                                        for (var j = 0; j < contentData.length; j++) {
                                            noticeHtml += 
                                                '<li>' +
                                                    '<a href="' + contentData[j].link + '" title="' + contentData[j].CONTENT + '"';

                                                    if (contentData[j].isNew) {
                                                        noticeHtml += ' class="newNotice"';
                                                    }

                                            noticeHtml += 
                                                    '>' + contentData[j].CONTENT + '</a>' +
                                                    '<span>' + contentData[j].CREAT_TIME + '</span>' +
                                                '</li>';
                                        }
                                    }
                                }
                                $moduleContent.css({
                                    "minHeight": show_rows * 30 + 70
                                });
                                $moduleContent.next(".module-loading").remove();
                            }
                        })  
                    })(result[i]);
                }
            }
        })
        

        /**
         * 给a标签绑定事件，点击打开dialog全屏
         */
        $(document).on("click", "a", function (event) {
            var event = event || window.event;
            event.preventDefault();

            var _this = $(this);
            var url = _this.attr("href");

            if (_this.hasClass("module-more")) {
                var title = _this.prev().text();
            } else {
                var title = _this.text();
            }

            if (url) {
                //parent.openDialog(url, title, 0, 0, true, true);
                parent.$.ligerDialog.open({
                    url: url,
                    title: title,
                    isResize: false,
                    showMax: false,
                    showMin: false,
                    slide: false,
                    modal:true,
                    width :$(window).width()+5,
                    height : $(window).height()
                })
            }
        })
    })()
</script>
</html>
