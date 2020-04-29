<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="grid,dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var save = function () {
            var allData = grid.getAllData();

            ajaxPostData({
                url: 'http://118.178.184.131:9090/add',
                data: { paramVo: JSON.stringify(allData) },
                success: function (result) {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
                    parent.tree.reAsyncChildNodes(null, 'refresh');
                },
                delayCallback: true
            })
        };
        var add = function () {
            grid.addRow();
        };
        var remove = function () {
            grid.deleteSelectedRow();
        };
        var initGrid = function () {
            var columns = [
                { display: '工资套', name: 'a', width: 120 },
                { display: '工资项编码', name: 'b', width: 120 },
                { display: '工资项名称', name: 'c', width: 120 },
                { display: '工资额', name: 'd', width: 120 },
                { display: '试用期工资', name: 'd', width: 120 },
                { display: '备注', name: 'd', width: 160 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,           
                checkbox: true,
                editable: true,
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' }
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
    <div id="mainGrid"></div>
</body>

</html>