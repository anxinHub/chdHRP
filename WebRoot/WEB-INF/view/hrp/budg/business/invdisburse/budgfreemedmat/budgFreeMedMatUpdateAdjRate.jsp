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
    $(function() {
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#adj_rate"), required: true },
            ]
        });
    });

    function save() {

        var sourceData = parent.grid.selectGet();

        var adj_rate = $("#adj_rate").val();
        var remark = $("#remark").val() || "-1";

        if (sourceData.length === 0) {
            $.etDialog.error("没有需要批量设置的数据,如需操作,请选择数据后批量设置!");
            return;
        }
        var ParamVo = [];
        sourceData.forEach(function(item, index) {
            ParamVo.push(
                item.rowData.year + "@" +
                item.rowData.month + "@" +
                item.rowData.dept_id + "@" +
                item.rowData.mat_type_id + "@" +
                item.rowData.count_value + "@" +
                adj_rate + "@" + remark
            )
        });

        ajaxPostData({
            url: "budgFreeMedMatUpdateAdjRate.do?isCheck=false",
            data: { ParamVo: ParamVo.toString() },
            success: function(responseData) {
                parent.query();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        });
    }

    function saveBudgFreeMedMat() {
        if (formValidate.test()) {
            save();
        }
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">调整比例：</td>
                <td class="ipt">
                    <input id="adj_rate" type="text" /> %
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