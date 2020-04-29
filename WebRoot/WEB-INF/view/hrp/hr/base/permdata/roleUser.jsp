<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var query = function () {
            params = [
                { name: 'user_code', value: $('#user_code').val() },
                { name: 'role_id', value: '${role_id}' }
            ];
            grid.loadData(params);
        };
        var initGrid = function () {
            var columns = [
				{ display: '用户ID', name: 'user_id', width: 100 },
				{ display: '用户编码', name: 'user_code', width: 100 },
				{ display: '用户名称', name: 'user_name', width: 100 },
				{ display: '状态', name: 'is_stop', width: 100,
					render : function(ui) {
						var cellData = ui.cellData;

						return cellData.is_stop == 0 ? '启用' : '停用';
					}
				}
            ];
            var paramObj = {
                height: '100%',
                checkbox: true,

                dataModel: {
                    url: '../../sys/hosuser/queryUserByRole.do?role_id=${role_id}'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initGrid();
        })
    </script>
</head>

<body>
	<div class="mian">
		<table class="table-layout">
			<tr>
				<td class="label">用户编码：</td>
				<td class="ipt">
					<input id="user_code" type="text" />
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>