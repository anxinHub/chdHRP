<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,select,tree,grid,dialog" name="plugins" />
	</jsp:include>
    <script>
        var dept_name, is_stop;
        var tree, grid;

        var initFrom = function () {
            dept_name = $("#dept_name").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            is_stop = $("#is_stop").etSelect({
                options: [
                    { id: 0, text: '否' },
                    { id: 1, text: '是' }
                ],
                defaultValue: "none"
            });
        };
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'http://118.178.184.131:9090/tree'
                },
                callback: {
                    onClick: function () {
                        query();
                    }
                }
            })
        };
        var query = function () {
            params = [
                { name: '', value: '' }
            ];
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addStationSalaryPage.do?isCheck=false',
                isMax: true,
                title: '添加'
            });
        };
        var remove = function () {
            var selectData = grid.selectGet();
            var param = [];
            selectData.forEach(function (item) {
                param.push({
                    kind_code: item.rowData.kind_code
                });
            })

            ajaxPostdata({
                url: '.do?isCheck=false',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                    tree.reAsyncChildNodes(null, 'refresh');
                }
            })
        };
        var print = function () {};
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updateStationSalaryPage.do?isCheck=false',
                title: '修改',
                isMax: true,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save();
                }
            })
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
                dataModel: {
                    url: 'http://118.178.184.131:9090/static_column/grid'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'remove' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initTree();
            initGrid();

            $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            })
        })
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="button-group">
                <button>移动</button>
                <button>合并</button>
            </div>
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">岗位编码：</td>
                    <td class="ipt">
                        <input id="station_code" type="text" />
                    </td>

                    <td class="label">岗位名称：</td>
                    <td class="ipt">
                        <input id="station_name" type="text" />
                    </td>

                    <td class="label">科室名称：</td>
                    <td class="ipt">
                        <select id="dept_name" style="width:180px;"></select>
                    </td>

                    <td class="label">是否停用：</td>
                    <td class="ipt">
                        <select id="is_stop" style="width:180px;"></select>
                    </td>
                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>