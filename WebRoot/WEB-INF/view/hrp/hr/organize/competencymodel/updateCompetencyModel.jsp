<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="validate,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var is_stop;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#target_code"), required: true },
                    { el: $("#target_name"), required: true },
                    { el: $("#target_num"), required: true },
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
            })
        };

        var save = function () {
            if (!formValidate.test()) {
                return;
            }
            ajaxPostData({
                url: 'http://118.178.184.131:9090/add',
                data: {
                    target_code: $("#target_code").val(),
                    target_name: $("#target_name").val(),
                    target_num: $("#target_num").val(),
                    note: $("#note").val(),
                    is_stop: is_stop.getValue()
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
                    parent.tree.reAsyncChildNodes(null, 'refresh');
                },
                delayCallback: true
            })
        };

        $(function () {
            initValidate();
            initForm();
        })

        
    </script>
</head>

<body>
    <table class="table-layout" style="width: 100%;">
        <tr>
            <td class="label no-empty">指标编码：</td>
            <td class="ipt">
                <input id="target_code" type="text" value="${target_code}" />
            </td>
        </tr>
        <tr>
            <td class="label no-empty">指标名称：</td>
            <td class="ipt">
                <input id="target_name" type="text" value="${target_name}" />
            </td>
        </tr>
        <tr>
            <td class="label no-empty">指标值：</td>
            <td class="ipt">
                <input id="target_num" type="text" value="${target_num}" />
            </td>
        </tr>
        <tr>
            <td class="label">备注：</td>
            <td class="ipt">
                <input id="note" type="text" value="${note}" />
            </td>
        </tr>
        <tr>
            <td class="label no-empty">是否停用：</td>
            <td class="ipt">
                <select id="is_stop" style="width:180px;"></select>
            </td>
        </tr>
    </table>
</body>

</html>