<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="select,validate,dialog" name="plugins" />
	</jsp:include>
    <script>
        var sup_code, is_stop, is_last;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#professional_code"), required: true },
                    { el: $("#professional_name"), required: true },
                    { el: $("#is_stop"), required: true },
                ]
            });
        };
        var initForm = function () {
            is_stop = $("#is_stop").etSelect({
                options: [
                    { id: 0, text: '否' },
                    { id: 1, text: '是' }
                ],
                defaultValue: '${is_stop}'
            });
        };


        var save = function () {
            if (!formValidate.test()) {
                return;
            }
            ajaxPostData({
                url: 'updateProfessionalInfo.do',
                data: {
                    professional_code: $("#professional_code").val(),
                    professional_name: $("#professional_name").val(),
                    is_stop: is_stop.getValue(),
                    note: $("#note").val(),
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
            <td class="label no-empty">专业编码：</td>
            <td class="ipt">
                <input id="professional_code" type="text" value="${professional_code}" disabled="disabled"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">专业名称：</td>
            <td class="ipt">
                <input id="professional_name" type="text" value="${professional_name}"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">是否停用：</td>
            <td class="ipt">
                <select id="is_stop" style="width: 180px;"></select>
            </td>
        </tr>
         <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                    <textarea id="note" rows="20" cols="25" >${note}</textarea>
                </td>
            </tr>
    </table>
</body>

</html>