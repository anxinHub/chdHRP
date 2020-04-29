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
                { el: $("#grow_rate"), required: true },
            ]
        });
    });

    function save () {

        var sourceData = parent.grid.selectGet();

        var grow_rate = $("#grow_rate").val();
        var remark = $("#remark").val()?$("#remark").val() : "-1";

        var ParamVo = [];
        
        sourceData.forEach(function(item, index) {
            ParamVo.push(
                item.rowData.year + "@" +
                item.rowData.month + "@" +
                item.rowData.dept_id + "@" +
                item.rowData.mat_type_id + "@" +
                item.rowData.last_cost + "@" +
                grow_rate + "@" + remark
            )
        });
        ajaxPostData({
            url: "collectBudgNoMedMat.do?isCheck=false",
            data: { ParamVo: ParamVo.toString() },
            success: function(responseData) {
                parent.query();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        });
    }

    function collectBudgNoMedMat() {
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
                <td class="label">增长比例：</td>
                <td class="ipt">
                    <input id="grow_rate" type="text" /> %
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