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

    function savebudgDrugCost () {
        if (!formValidate.test()) {
            return;
        }

        var formPara = {
            year: '${year}',
            month: '${month}',
            dept_id: '${dept_id}',
            med_type_id: '${med_type_id}',
            amount: $("#amount").val()
        };

        formPara.amount = formPara.amount || 0;

        ajaxPostData({
            url: "updateBudgDrugCost.do",
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
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">
                  	  年度：
                </td>
                <td class="ipt">
                    <input type="text" id="year" value="${year}" disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td class="label">
                   	 月份：
                </td>
                <td class="ipt">
                    <input type="text" id="month" value="${month}" disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td class="label">
                 	   部门：
                </td>
                <td class="ipt">
                    <input type="text" id="dept_id" value="${dept_name}" disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td class="label">
                  	  药品分类：
                </td>
                <td class="ipt">
                    <input type="text" id="med_type_id" value="${med_type_name}" disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">
                  	  金额：
                </td>
                <td class="ipt">
                    <input type="text" id="amount" value="${amount}" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>