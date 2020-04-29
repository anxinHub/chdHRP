<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
</jsp:include>
<style>
    body {
        padding-top: 20px;
    }
</style>
<script>
    var budg_year, wage_item_code, emp_type_code;
    var etValidate;

    $(function() {
        loadDict();

        etValidate = $.etValidate({
            config: {

            },
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#wage_item_code"), required: true },
                { el: $("#emp_type_code"), required: true },
                { el: $("#adj_month"), required: true },
                { el: $("#adj_rate"), required: true },
                { el: $("#adj_amount"), required: true }
            ]
        })

        $("#adj_rate").change(function() {
            if ($("#adj_rate").val()) {
                etValidate.closeValidate($("#adj_amount"));
                $("#adj_amount").prop("disabled", "true");
            } else {
                etValidate.openValidate($("#adj_amount"));
                $("#adj_amount").removeProp("disabled");
            }
        })

        $("#adj_amount").change(function() {
            if ($("#adj_amount").val()) {
                etValidate.closeValidate($("#adj_rate"));
                $("#adj_rate").prop("disabled", "true");
            } else {
                etValidate.openValidate($("#adj_rate"));
                $("#adj_rate").removeProp("disabled");
            }
        })
    });

    function save() {
        var formPara = {
            budg_year: budg_year.getValue(),
            wage_item_code: wage_item_code.getValue(),
            emp_type_code: emp_type_code.getValue(';'),

            adj_month: $("#adj_month").val(),
            adj_amount: $("#adj_amount").val(),
            adj_rate: $("#adj_rate").val(),
            remark: $("#remark").val()
        };
        ajaxPostData({
            url: "addBudgWageItemAdj.do",
            data: formPara,
            success: function(responseData) {
                if (responseData.state == "true") {
                    parent.queryNew();
                    
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                }
            }
        });
    }

    function saveBudgWageItemAdj() {
        if (etValidate.test()) {
            save();
        }
    }

    function loadDict() {
        ajaxPostData({
            url: '../../queryBudgYear.do?isCheck=false',
            success: function (data) {
                budg_year = $("#budg_year").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                });
            }
        })

        wage_item_code = $("#wage_item_code").etSelect({
            url: "../../queryBudgWageItem.do?isCheck=false",
            defaultValue: "none"
        });
        emp_type_code = $("#emp_type_code").etSelect({
            url: "../../queryBudgEmpType.do?isCheck=false",
            defaultValue: "none",
            checkboxMode: true
        });
    }
</script>
</head>

<body>
    <table class="table-layout" style="width: 100%;">
        <tr>
            <td class="label no-empty">年度：</td>
            <td class="ipt">
                <input id="budg_year" name="budg_year" type="text"/>
            </td>
            <td class="label no-empty">工资项目：</td>
            <td class="ipt">
                <select id="wage_item_code" style="width: 180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">职工类别：</td>
            <td class="ipt">
                <select id="emp_type_code" style="width: 180px;"></select>
            </td>
            <td class="label no-empty">计划调整月份：</td>
            <td class="ipt">
                <input id="adj_month" name="adj_month" type="text"/>月
            </td>
        </tr>
        <tr>
            <td class="label no-empty">调整比例：</td>
            <td class="ipt">
                <input id="adj_rate" name="adj_rate" type="text"/>%
            </td>
            <td class="label no-empty">调整额度：</td>
            <td class="ipt">
                <input id="adj_amount" name="adj_amount" type="text"/>元
            </td>
        </tr>
        <tr>
            <td class="label">说明：</td>
            <td class="ipt">
                <input name="remark" type="text" id="remark" />
            </td>
        </tr>
    </table>
</body>

</html>