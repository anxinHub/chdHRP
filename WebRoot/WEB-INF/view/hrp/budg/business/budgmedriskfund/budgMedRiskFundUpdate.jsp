<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value=",dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    function saveBudgMedRiskFund() {
        var formPara = {
            budg_year: '${budg_year}',
            month: '${month}',
            dept_id: '${dept_id}',
            cost_budg: $("#cost_budg").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
        	url: "addOrUpdateBudgMedRiskFund.do?isCheck=false",
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
                <td class="label no-empty">统计年月：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" disabled value="${budg_year}-${month}" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">科室名称：</td>
                <td class="ipt">
                    <input id="dept_id" type="text" disabled value="${dept_id}" />
                </td>
            </tr>
            <tr>
                <td class="label no-empty">支出预算：</td>
                <td class="ipt">
                    <input id="cost_budg" type="text" value="${cost_budg}" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>