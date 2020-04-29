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
                    { el: $("#kind_code"), required: true },
                    { el: $("#kind_name"), required: true },
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
                url: 'updateStationKind.do?isCheck=false',
                data: {
                    kind_code: $("#kind_code").val(),
                    kind_name: $("#kind_name").val(),
                    is_stop: is_stop.getValue(),
                    note: $("#note").val(),
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
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
            <td class="label no-empty">岗位分类编码：</td>
            <td class="ipt">
                <input id="kind_code" type="text" value="${kind_code}" disabled="disabled"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">岗位分类名称：</td>
            <td class="ipt">
                <input id="kind_name" type="text" value="${kind_name}"/>
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