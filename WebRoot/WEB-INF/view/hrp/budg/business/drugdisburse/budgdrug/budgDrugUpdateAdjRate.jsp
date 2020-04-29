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
            config: {},
            items: [
                { el: $("#adj_rate"), type: 'number' },
            ]
        });
    })
    

    function saveBudgChargeMat () {
        //if (!formValidate.test()) {
        //    return;
        //}

        var sourceData = parent.grid.selectGet();

        grow_rate = $("#grow_rate").val();
        adj_rate = $("#adj_rate").val() || 0;
        remark = $("#remark").val() || "-1";

        var ParamVo = [];
        $(sourceData).each(function() {
            ParamVo.push(
                this.rowData.budg_year + "@" +
                this.rowData.month + "@" +
                this.rowData.dept_id + "@" +
                this.rowData.mat_type_id + "@" +
                this.rowData.last_cost + "@" +
                this.rowData.grow_rate + "@" +
                this.rowData.adj_rate + "@" +
                this.rowData.count_value + "@" +
                grow_rate + "@" + adj_rate + "@" + remark
            )
        });
        ajaxPostData({
            url: "budgDrugUpdateAdjRate.do?isCheck=false",
            data: { ParamVo: ParamVo.toString() },
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
                <td class="label">收入预算增长比例：</td>
                <td class="ipt">
                    <input id="grow_rate" type="text" /> %
                </td>
            </tr>
            <tr>
                <td class="label">调整比例：</td>
                <td class="ipt">
                    <input id="adj_rate" type="text" />%
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt">
                    <input id="remark" type="text" />
                </td>
            </tr>
        </table>
    </div>
</body>

</html>