<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="validate,dialog,select" name="plugins" />
	</jsp:include>
    <script>       
        var formValidate;
        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#seq_code"), required: true },
                    { el: $("#seq_name"), required: true }
                ]
            });
        };        

        var save = function () {
            if (!formValidate.test()) {
                return;
            }

            ajaxPostData({
                url: 'updateSeqManage.do',
                data: {
                	seq_code: $("#seq_code").val(),
                	seq_name: $("#seq_name").val(),
                	min_value: $("#min_value").val(),
                    max_value:$("#max_value").val(),
                    buffer_value:$("#buffer_value").val(),
                },
                delayCallback:true,
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
                }
            })
        };

        $(function () {
            initValidate();
        })   
    </script>
</head>

<body>
    <div class="mian">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label no-empty">序列编码：</td>
                <td class="ipt">
                    <input id="seq_code" type="text" value="${seq_code}" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">序列名称：</td>
                <td class="ipt">
                    <input id="seq_name" type="text" value="${seq_name}" />
                </td>
            </tr>
            <tr>
                <td class="label">最小值：</td>
                <td class="ipt">
                    <input id="min_value" type="text" style="width: 180px;" />
                </td>
            </tr>
               <tr>
                <td class="label">最大值：</td>
                <td class="ipt">
                <input id="max_value"  type="text" style="width: 180px;"/>
                </td>
            </tr>
               <tr>
                <td class="label">缓冲大小：</td>
                <td class="ipt">
                <input id="buffer_value"  type="text" style="width: 180px;"/>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>