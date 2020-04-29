<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="dialog" name="plugins" />
</jsp:include>
<script>
    var results;

    function saveData (value) {
        var data = results.val();
       //console.log(value[0],data);
        ajaxPostData({
            url: "updateEndRepUser.do?isCheck=false",
            data: {
            	rep_code : value[0],
            	rep_note : data
            	},
            delayCallback: true,
            success: function (res) {
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        })
    }
    $(function(){
        results = $('#results');
    })
</script>
</head>
<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label" style="vertical-align: baseline;">维修结果：</td>
                <td class="ipt" style="width:240px;">
                    <textarea name="results" style="height:120px;width: 240px" id="results" cols="30" rows="15"></textarea>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>