<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="validate,select,dialog" name="plugins" />
</jsp:include>
<script>
        var indicator_code, indicator_name, is_stop;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    {
                        el: $("#indicator_code"),
                        required: true
                    },
                    {
                        el: $("#indicator_name"),
                        required: true
                    },
                    {
                        el: $("#is_stop"),
                        required: true
                    }
                ]
            });
        };

        var initForm = function () {
            is_stop = $("#is_stop").etSelect({
                options: [{
                        id: 0,
                        text: '否'
                    },
                    {
                        id: 1,
                        text: '是'
                    }
                ],
                defaultValue: '${is_stop}'
            })
        };

        var save = function () {
            if (!formValidate.test()) {
                return;
            }
            ajaxPostData({
                url: 'updateCompetencyTarget.do',
                data: {
                    indicator_code: $("#indicator_code").val(),
                    indicator_name: $("#indicator_name").val(),
                    note: $("#note").val(),
                    is_stop: is_stop.getValue()
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
	<div class="mian">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label no-empty">指标编码：</td>
				<td class="ipt"><input id="indicator_code"
					value="${indicator_code}" type="text" disabled="disabled"></td>
			</tr>
			<tr>
				<td class="label no-empty">指标名称：</td>
				<td class="ipt"><input id="indicator_name"
					value="${indicator_name}" type="text"></td>
			</tr>

			<tr>
				<td class="label no-empty">是否停用：</td>
				<td class="ipt"><select id="is_stop" style="width: 180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">说明：</td>
				<td class="ipt"><input id="note" value="${note}" type="text">
				</td>
			</tr>
		</table>
	</div>
</body>

</html>