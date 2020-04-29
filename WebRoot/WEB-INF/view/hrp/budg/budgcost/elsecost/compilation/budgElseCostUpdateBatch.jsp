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
    var dataFormat;
    var formValidate;
    $(function() {
		formValidate = $.etValidate({
            items: [
                { el: $("#grow_rate"), required: true },
                { el: $("#grow_value"), required: true }
            ]
        });

        $("#grow_rate").change(function() {
            if ($("#grow_rate").val()) {
                $("#grow_value").attr('disabled', true);
                formValidate.closeValidate($("#grow_value"));
            } else {
                $("#grow_value").removeAttr('disabled');
                formValidate.openValidate($("#grow_value"));
            }
        })

        $("#grow_value").change(function() {
            if ($("#grow_value").val()) {
                $("#grow_rate").attr('disabled', true);
                formValidate.closeValidate($("#grow_rate"));
            } else {
                $("#grow_rate").removeAttr('disabled');
                formValidate.openValidate($("#grow_rate"));
            }
        })

    });

    function save() {
        var data = parent.grid.selectGetChecked();

        var grow_rate = $("#grow_rate").val() ? $("#grow_rate").val() : "-1";
        var grow_value = $("#grow_value").val() ? $("#grow_value").val() : "-1";
        var remark = $("#remark").val() ? $("#remark").val() : "-1";

        var ParamVo = [];
        $(data).each(function() {
            ParamVo.push(
                this.rowData.budg_year + "@" +
                this.rowData.month + "@" +
                this.rowData.subj_code + "@" +
                this.rowData.last_cost + "@" +
                grow_rate + "@" +
                grow_value + "@" +
                remark
            )
        });
        ajaxPostData({
        	url: "updateBatchBudgElseCost.do?isCheck=false",
        	data: { ParamVo: ParamVo.toString() },
        	success: function(responseData) {
                parent.query();

                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex);
            }
        });
    }

    function saveBudgElseCost() {
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
                    <input id="grow_rate" type="text" />%
                </td>
            </tr>
            <tr>
                <td class="label">增长额：</td>
                <td class="ipt">
                	<input id="grow_value" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt">
                	<textarea id="remark" style="width:180px"></textarea>
                </td>
            </tr>
        </table>
    </div>
</body>

</html>