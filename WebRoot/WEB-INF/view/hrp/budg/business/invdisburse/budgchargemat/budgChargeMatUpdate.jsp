<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var formValidate;

    $(function() {
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#cost_budg"), type: 'number' }
            ]
        });
    });

    function saveBudgChargeMat() {
        if (!formValidate.test()) {
            return;
        }
        var formPara = {
            year: '${year}',
            month: '${month}',
            dept_id: '${dept_id}',
            mat_type_id: '${mat_type_id}',
            cost_budg: $("#cost_budg").val(),
            remark: $("#remark").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
            url: "updateBudgChargeMat.do",
            data: formPara,
            success: function(responseData) {
                parent.query();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        });
    }

</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" disabled value="${year}" />
                </td>
            </tr>
            <tr>
                <td class="label">月：</td>
                <td class="ipt">
                    <input id="month" type="text" disabled value="${month}" />
                </td>
            </tr>
            <tr>
                <td class="label">部门：</td>
                <td class="ipt">
                    <input id="dept_id" type="text" disabled value="${dept_name}" />
                </td>
            </tr>
            <tr>
                <td class="label">物资类别：</td>
                <td class="ipt">
                    <input id="mat_type_id" type="text" disabled value="${mat_type_name}" />
                </td>
            </tr>
            <tr>
                <td class="label">支出预算：</td>
                <td class="ipt">
                    <input id="cost_budg" type="text" value="${cost_budg}" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt">
                    <input id="remark" type="text" value="${remark}" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>