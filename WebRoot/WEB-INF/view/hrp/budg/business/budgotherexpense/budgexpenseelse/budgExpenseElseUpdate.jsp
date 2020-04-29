<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="dialog,etValidate" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var formValidate;

    $(function () {
        formValidate = $.etValidate({
            items: [
                { el: $("#cost_budg"), type: 'number' }
            ]
        })
    })

    function savebudgExpenseElse () {
        if (!formValidate.test()) {
            return;
        }
        
        var formPara={
           budg_year:"${budg_year}",
           month:"${month}",
           dept_id:"${dept_id}",
           payment_item_id:"${payment_item_id}",
           cost_budg:$("#cost_budg").val(),
           remark:$("#remark").val()
        };

        formPara.cost_budg = formPara.cost_budg || 0;

        ajaxPostData({
            url: "updateBudgExpenseElse.do",
            data: formPara,
            success: function(responseData) {
                parent.queryNew();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            },
            delayCallback: true
        });
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">
                预算年度：
                </td>
                <td class="ipt">
                    <input type="text" id="budg_year" value = "${budg_year}-${month}" disabled/>
                </td>
            </tr>
            <tr>
                <td class="label">
                科室名称：
                </td>
                <td class="ipt">
                    <input type ="text" id="dept_id" style="width:180px;" value = "${dept_name}" disabled="disabled"></input>
                </td>
            </tr>
            <tr>
                <td class="label">
                支出项目：
                </td>
                <td class="ipt">
                    <input type ="text" id="payment_item_id" style="width:180px;" value = "${payment_item_name}" disabled="disabled" ></input>
                </td>
            </tr>
            <tr>
                <td class="label">
                支出预算：
                </td>
                <td class="ipt">
                <input type="text" id="cost_budg" value = "${cost_budg}"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                说明：
                </td>
                <td class="ipt">
                <input type="text" id="remark" value = "${remark}"/>
                </td>
            </tr>
        </table>
    </div>
  </body>

</html>