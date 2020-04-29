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
        <jsp:param value="dialog" name="plugins"/>
    </jsp:include>
    <script>
        var formData={
            year: '${yearMonth}'.substr(0, 4),
            month: '${yearMonth}'.substr(4, 2),
            dept_id: '${dept_id}',
            emp_id: '${emp_id}',
            emp_type_code: '${emp_type_code}',
            wage_item_code: '${wage_item_code}'
        };
        $(function(){
            init();
        });

        function init(){
            formatMoney();
        }

        //格式化金额
        function formatMoney() {
            $("#amount_money").blur(function () {
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

            $("#amount_money").val(fm($("#amount_money").val()));
        }

        //提交数据
        function saveBudgEmpWageTotal() {
            var validate = validateForm();
            if (validate) {
                formData.amount =  $("#amount_money").val();
                ajaxPostData({
                    url: "updateBudgEmpWageTotal.do",
                    data: formData,
                    success: function (responseData) {
                        parent.queryNew();

                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                    },
                    delayCallback: true
                })
            }
        }

        function validateForm(){
            var result_empty = true;
            var result_money= true;
            var amount_money = $("#amount_money").val();
            if(amount_money == ""|| amount_money == undefined || amount_money == null){
                result_empty = false;
            }else {
                var req = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                if (!req.test(amount_money))  //验证金额格式
                    result_money = false;
            }

            if (!result_empty) {
                $.etDialog.error("必填字段不能为空");
            } else if (!result_money) {
                $.etDialog.error("金额格式不正确");
            }
            return result_empty && result_money;
        }
    </script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">统计年月：</td>
                <td class="ipt">
                    <input type="text" value="${yearMonth}" disabled>
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <input type="text" value="${dept_name}" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">职工名称：</td>
                <td class="ipt">
                    <input type="text" value="${emp_name}" disabled>
                </td>
                <td class="label">职称类别：</td>
                <td class="ipt">
                    <input type="text" value="${type_name}" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">工资项目：</td>
                <td class="ipt">
                    <input type="text" value="${wage_item_name}" disabled>
                </td>
                <td class="label no-empty">金额：</td>
                <td class="ipt">
                    <input type="text" id="amount_money" value="${amount}">
                </td>
            </tr>
        </table>
    </div>
</body>
</html>