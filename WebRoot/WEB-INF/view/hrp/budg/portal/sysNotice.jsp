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
        .editor-content {
            min-width: 730px;
        }
        #cke_1_contents {
            height: 60% !important;
        }
    </style>
    <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
    <script src="<%=path%>/lib/ckeditor/ckeditor.js"></script>
    <script>
        if (CKEDITOR.env.ie && CKEDITOR.env.version < 9) {
            CKEDITOR.tools.enableHtml5Elements(document);
        }

        CKEDITOR.config.height = 450;
        CKEDITOR.config.width = 'auto';

        var initSample = (function () {
            var wysiwygareaAvailable = isWysiwygareaAvailable(),
                isBBCodeBuiltIn = !!CKEDITOR.plugins.get('bbcode');

            return function () {
                var editorElement = CKEDITOR.document.getById('editor');

                if (isBBCodeBuiltIn) {
                    editorElement.setHtml(
                        '填写通知内容'
                    );
                }

                if (wysiwygareaAvailable) {
                    CKEDITOR.replace('editor');
                } else {
                    editorElement.setAttribute('contenteditable', 'true');
                    CKEDITOR.inline('editor');
                }
            };

            function isWysiwygareaAvailable () {
                if (CKEDITOR.revision == ('%RE' + 'V%')) {
                    return true;
                }

                return !!CKEDITOR.plugins.get('wysiwygarea');
            }
        })();
    </script>
    <style>
        body {
            margin: 0;
            padding: 0;
        }
        .button-group {
            height: 30px;
            margin-top: 5px;
        }
        .button-group .button-item {
            font-size: 14px;
            float: right;
            padding: 0 10px;
            height: 28px;
            line-height: 28px;
            margin-right: 20px;
            border-radius: 3px;
            cursor: pointer;
            border: 1px solid #9dc8fb;
            background: #e0ebf8;
        }
    </style>
</head>
<body>
    <div class="editor-content">
        <div id="editor">
            ${content}
        </div>
    </div>
    <div class="button-group">
        <div class="button-item button-close">关闭</div>
        <div class="button-item button-unpublish">取消发布</div>
        <div class="button-item button-publish">发布</div>
        <div class="button-item button-save">保存</div>
    </div>

    
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"></script>
    <script src="<%=path%>/lib/hrp.js"></script>
    <script>
        initSample();
 
        /**
         * 发布
         */
        $(".button-publish").click(function () {
            var content = CKEDITOR.instances.editor.getData();

            if (content === "") {
                $.ligerDialog.error("内容不能为空");
            } else {
                setPublish("1", content);
            }
        })
        /**
         * 保存
         */
        $(".button-save").click(function () {
            var content = CKEDITOR.instances.editor.getData();

            if (content === "") {
                $.ligerDialog.error("内容不能为空");
            } else {
                $.ajax({
                    type: "POST",
                    url: "../../hrp/budg/portal/saveSysNotice.do?isCheck=false",
                    data: {
                        content: content
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.state === "true") {
                            $.ligerDialog.success(result.msg);
                        } else {
                            $.ligerDialog.error(result.error);
                        }
                    }
                })
            }
                
        })
        /**
         * 取消发布
         */
        $(".button-unpublish").click(function () {
            setPublish("0", "")
        })
        /**
         * 关闭
         */
        $(".button-close").click(function () {
            var dialog = frameElement.dialog;
            dialog.close();
        })

        function setPublish (state, content) {
            $.ajax({
                type: "POST",
                url: "../../hrp/portal/updateSysNoticeState.do?isCheck=false",
                data: {
                    state: state,
                    content: content
                },
                dataType: "json",
                success: function (result) {
                    if (result.state === "true") {
                        $.ligerDialog.success(result.msg)
                    } else {
                        $.ligerDialog.error(result.error)
                    }
                }
            })
        }
    </script>
</body>
</html>