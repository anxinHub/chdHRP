<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="checkbox,dialog" name="plugins" />
	</jsp:include>
    <script>
		var is_read, is_write,mapVo;
        var save = function () {
        //	var saveParam = [${mapVo}];
			var param = {
				is_read: is_read.checked ? 1 : 0,
				is_write: is_write.checked ? 1 : 0,
			}
			//var mainGridData = mainGrid.getAllData();
		
			ajaxPostData({
				url: 'batchAddPerm.do?isCheck=false',
				data: {paramVo: JSON.stringify(saveParam)},
				success: function () {

				}
			})
		};
		var initFrom = function () {
            is_read = $("#is_read").etCheck();
            is_write = $("#is_write").etCheck();
        };
		$(function () {
            initFrom();
        })
    </script>
</head>

<body>
	<div class="mian">
		<table class="table-layout" style="width:100%;">
			<tr>
				<td class="label"></td>
				<td class="ipt">
					<input type="checkbox" name="is_read" id="is_read">
					<label for="is_read">全部读取权限</label>
				</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td class="ipt">
					<input type="checkbox" name="is_write" id="is_write">
					<label for="is_write">全部写入权限</label>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>