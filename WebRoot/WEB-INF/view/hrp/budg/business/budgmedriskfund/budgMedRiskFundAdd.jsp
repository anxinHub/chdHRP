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
	var budg_year, dept_id;
	var formValidate;

    $(function() {
        loadDict();

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#cost_budg"), type: 'number' }
            ]
        });
    });

    function save() {
        var formPara = {
            budg_year: budg_year.getValue().split('-')[0],
            month: budg_year.getValue().split('-')[1],
            dept_id: dept_id.getValue(),
            cost_budg: $("#cost_budg").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
        	url: "addOrUpdateBudgMedRiskFund.do?isCheck=false",
			data: formPara,
            success: function(responseData) {
                budg_year.clear();
                dept_id.clearItem();
                $("#cost_budg").val('');

                parent.query();
            }
        });
    }

    function saveBudgMedRiskFund() {
        if (formValidate.test()) {
            save();
        }
    }

    function loadDict () {
    	budg_year = $("#budg_year").etDatepicker({
            defaultDate: true,
            dateFormat: 'yyyy-mm'
        });

        dept_id = $("#dept_id").etSelect({
            url: "queryHosDeptDict.do?isCheck=false",
            defaultValue: "none"
        })
    }
</script>
</head>

<body>
	<div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">统计年月：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">科室名称：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">支出预算：</td>
                <td class="ipt">
                    <input id="cost_budg" type="text" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>