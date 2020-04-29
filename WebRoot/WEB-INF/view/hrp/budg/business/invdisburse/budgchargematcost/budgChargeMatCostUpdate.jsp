<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var formValidate;

    $(function() {
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#amount"), type: 'number' }
            ]
        });
    });

    function saveBudgChargeMatCost () {
        if (!formValidate.test()) {
            return;
        }
        
        var formPara = {
            year: '${year}',
            month: '${month}',
            dept_id: '${dept_id}',
            mat_type_id: '${mat_type_id}',
            amount: $("#amount").val()
        };

        formPara.amount = formPara.amount || 0;

        ajaxPostData({
            url: "addOrUpdateBudgChargeMatCost.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                parent.queryNew();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        });
    }

</script>
</head>

<body>
    <div class="mian" style="margin-top: 20px;">
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
                <td class="label">金额：</td>
                <td class="ipt">
                    <input id="amount" type="text" value="${amount}" />
                </td>
            </tr>

        </table>
    </div>
</body>

</html>