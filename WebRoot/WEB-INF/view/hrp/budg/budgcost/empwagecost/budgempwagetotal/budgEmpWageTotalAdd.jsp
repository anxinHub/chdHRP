<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/static_resource.jsp">
        <jsp:param value="select,datepicker,dialog" name="plugins" />
    </jsp:include>
    <script>
    var total_date, dept_name_select, emp_name_select, emp_type_select, wage_item_select, amount_money;
    var formData = {};
    $(function() {
        init();
    });

    function init() {
        total_date = $("#total_date").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy-mm",
        });
        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../queryBudgHosDept.do?isCheck=false",
            defaultValue: "none"
        });

        emp_name_select = $("#emp_name_select").etSelect({
            url: "../../queryBudgEmpName.do?isCheck=false",
            defaultValue: "none"
        });

        emp_type_select = $("#emp_type_select").etSelect({
            url: "../../queryBudgEmpType.do?isCheck=false",
            defaultValue: "none"
        });

        wage_item_select = $("#wage_item_select").etSelect({
            url: "../../queryBudgWageItem.do?isCheck=false",
            defaultValue: "none"
        });

        formatMoney();
    }

    //表单验证
    function validateForm() {
        var result_empty = true; //验证字段为空
        var result_money = true; //验证金额格式
        formData = {
            year: total_date.getValue("yyyy"),
            month: total_date.getValue("mm"),
            dept_id: dept_name_select.val(),
            emp_id: emp_name_select.val(),
            emp_type_code: emp_type_select.val(),
            wage_item_code: wage_item_select.val(),
            amount: $("#amount_money").val()
        };

        for (var key in formData) {
            if (formData.hasOwnProperty(key)) {
                var data = formData[key];
                if (data == "" || data == undefined || data == null) {
                    result_empty = false;
                } else {
                    if (key === "amount") {
                        var req = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                        if (!req.test(data)) //验证金额格式
                            result_money = false;
                    }
                }
            }
        }
        if (!result_empty) {
            $.etDialog.error("必填字段不能为空");
        } else if (!result_money) {
            $.etDialog.error("金额格式不正确");
        }
        return result_empty && result_money;
    }

    //提交数据
    function saveBudgEmpWageTotal() {
        var validate = validateForm();
        if (validate) {
            ajaxPostData({
                url: 'addBudgEmpWageTotal.do',
                data: formData,
                success: function(responseData) {
                    resetForm();
                    parent.queryNew();
                    // 关闭当前弹框
                    // var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    // parent.$.etDialog.close(curIndex);
                }
            })
        }
    }
    //重置表单
    function resetForm() {
        total_date.clear();
        dept_name_select.clearItem();
        emp_name_select.clearItem();
        emp_type_select.clearItem();
        wage_item_select.clearItem();
        $("#amount_money").val('');
    }

    //格式化金额
    function formatMoney() {
        $("#amount_money").blur(function() {
            var value = isNaN(this.value) || this.value == "" ? this.value : fm(this.value);
            this.value = value;
        });

        function fm(s, n) {
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(),
                r = s.split(".")[1];
            t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }
            var result = t.split("").reverse().join("") + "." + r;
            return result.replace(/,/g, "");
        }
    }
    </script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">统计年月：</td>
                <td>
                    <input type="text" id="total_date" name="total_date" />
                </td>
                <td class="label no-empty">科室名称：</td>
                <td class="ipt">
                    <select name="dept_name_select" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">职工名称：</td>
                <td class="ipt">
                    <select name="emp_name_select" id="emp_name_select" style="width:180px;"></select>
                </td>
                <td class="label no-empty">职工类别：</td>
                <td class="ipt">
                    <select name="" id="emp_type_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">工资项目：</td>
                <td class="ipt">
                    <select name="" id="wage_item_select" style="width:180px;"></select>
                </td>
                <td class="label no-empty">金额：</td>
                <td class="ipt">
                    <input type="text" style="text-align:right" id="amount_money" name="amount_money" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>