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
    var year, month, dept_id, med_type_id;
    var formValidate;

    $(function() {
        init();

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#year"), required: true },
                { el: $("#month"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#med_type_id"), required: true },
                { el: $("#cost_budg"), type: 'number' },
            ]
        });
    });

    function init () {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                year = $("#year").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text
                });
            }
        });

        month = $("#month").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false
        });

        dept_id = $("#dept_id").etSelect({
            url: 'queryHosDeptDict.do?isCheck=false',
            defaultValue: "none"
        });

        med_type_id = $("#med_type_id").etSelect({
            url: 'queryBudgMedTypeSubj.do?isCheck=false&budg_year=' + parent.year_input.getValue(),
            defaultValue: "none"
        })

    }

    function save() {
        var formPara = {
            budg_year: year.getValue(),
            month: month.getValue(),
            dept_id: dept_id.getValue(),
            med_type_id: med_type_id.getValue().split(",")[0],
            cost_budg: $("#cost_budg").val(),
            remark: $("#remark").val(),
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
            url: "addOrUpdateBudgDrug.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                year.clear();
                month.clear();
                dept_id.clearItem();
                med_type_id.clearItem();
                $("#cost_budg").val("");
                $("#remark").val("");

                parent.query();
            }
        });
    }

    function saveBudgDrug() {
        if (formValidate.test()) {
            save();
        }
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">
                    年度：
                </td>
                <td class="ipt">
                    <input type="text" id="year" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">
                    月份：
                </td>
                <td class="ipt">
                    <input type="text" id="month" />
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
                    药品分类：
                </td>
                <td class="ipt">
                    <select id="med_type_id" style="width:180px;"></select>
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