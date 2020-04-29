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
    var budg_year, month, dept_id, payment_item_id;
    var formValidate;

    $(function() {
        init();

        formValidate = $.etValidate({
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#payment_item_id"), required: true },
                { el: $("#cost_budg"), type: 'number', typeTip: '支出预算格式必须为数字类型!' }
            ]
        });
    });

    function init() {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                budg_year = $("#budg_year").etDatepicker({
                    view: "months",
                    minView: "months",
                    dateFormat: "yyyy-mm"
                });
            }
        });

        dept_id = $("#dept_id").etSelect({
            url: '../../../queryBudgDept.do?isCheck=false',
            defaultValue: "none"
        });

        payment_item_id = $("#payment_item_id").etSelect({
            url: '../../../queryBudgPaymentItemDict.do?isCheck=false&is_read=1',
            defaultValue: "none"
        })

    }

    function savebudgExpenseElse () {
        if (!formValidate.test()) {
            return;
        }

        var formPara = {
            budg_year: budg_year.getValue("yyyy"),
            month: budg_year.getValue("mm"),
            dept_id: dept_id.getValue().split(",")[0],
            dept_no: dept_id.getValue().split(",")[1],
            payment_item_id: payment_item_id.getValue().split(",")[0],
            payment_item_no: payment_item_id.getValue().split(",")[1],
            cost_budg: $("#cost_budg").val(),
            remark: $("#remark").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
            url: "addBudgExpenseElse.do",
            data: formPara,
            success: function(responseData) {
                budg_year.clear();
                dept_id.clearItem();
                payment_item_id.clearItem();
                $("#cost_budg").val("");
                $("#remark").val("");

                parent.queryNew();
            }
        });
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">
                    预算年度：
                </td>
                <td class="ipt">
                    <input type="text" id="budg_year" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">
                    科室名称：
                </td>
                <td class="ipt">
                    <select id="dept_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">
                    支出项目：
                </td>
                <td class="ipt">
                    <select id="payment_item_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">
                    支出预算：
                </td>
                <td class="ipt">
                    <input type="text" id="cost_budg" />
                </td>
            </tr>
            <tr>
                <td class="label">
                    说明：
                </td>
                <td class="ipt">
                    <input type="text" id="remark" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>