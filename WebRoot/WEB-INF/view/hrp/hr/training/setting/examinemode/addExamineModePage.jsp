<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="validate,dialog,select" name="plugins" />
	</jsp:include>
    <script>
        var is_stop;
        var formValidate;
        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#way_code"), required: true },
                    { el: $("#way_name"), required: true },
                    { el: $("#is_stop"), required: true }
                ]
            });
        };
        var initForm = function () {
            is_stop = $("#is_stop").etSelect({
                options: [
                    { id: 0, text: '否' },
                    { id: 1, text: '是' }
                ]
            });
        };

        var save = function () {
            if (!formValidate.test()) {
                return;
            }

            ajaxPostData({
                url: 'addExamineMode.do',
                data: {
                    way_code: $("#way_code").val(),
                    way_name: $("#way_name").val(),
                    is_stop: is_stop.getValue(),
                    note:$("#note").val()

                },
                delayCallback:true,
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                     parent.$.etDialog.close(curIndex);
                    parent.query();
                    $("#way_code").val('');
                    $("#way_name").val('');
                    is_stop.clearItem();
                }
            })
        };

        $(function () {
            initValidate();
            initForm();
        })
         //封装一个限制字数方法
    var checkStrLengths = function (str, maxLength) {
        var maxLength = maxLength;
        var result = 0;
        if (str && str.length > maxLength) {
            result = maxLength;
        } else {
            result = str.length;
        }
        return result;
    }

    //监听输入
    $(".wishContent").on('input propertychange', function () {

        //获取输入内容
        var userDesc = $(this).val();

        //判断字数
        var len;
        if (userDesc) {
            len = checkStrLengths(userDesc, 50);
        } else {
            len = 0
        }

        //显示字数
        $(".wordsNum").html(len + '/50');
    });
    </script>
</head>

<body>
    <div class="mian">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label no-empty">考核方式编码：</td>
                <td class="ipt">
                    <input id="way_code" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">考核方式名称：</td>
                <td class="ipt">
                    <input id="way_name" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">是否停用：</td>
                <td class="ipt">
                    <input id="is_stop" type="text" style="width: 180px;" />
                </td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                 <textarea id="note" rows="20" cols="25" class="wishContent" placeholder="请输入不超过50个汉字" maxlength="50"></textarea>
               
                </td>
            </tr>

        </table>
    </div>
</body>

</html>