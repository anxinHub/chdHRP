<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var dept_name, isStop;
        var tree, grid;

        var initFrom = function () {
            dept_name = $("#dept_name").etSelect({
                url: "http://118.178.184.131:9090/select",
                defaultValue: "none"
            });
            isStop = $("#isStop").etSelect({
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
            });
        };
        var query = function () {
            params = [
                { name: '', value: '' }
            ];
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addCompetencyModelPage.do?isCheck=false',
                width: 450,
                height: 450,
                title: '添加',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save()
                }
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
        var putin = function () {};
        var downloadModule = function () {};
        var openUpdate = function (rowData) {
            $.etDialog.open({
                url: 'updateCompetencyModelPage.do?isCheck=false',
                title: '修改',
                width: 450,
                height: 450,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '指标编码', name: 'a', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '指标名称', name: 'b', width: 120 },
                { display: '指标值', name: 'c', width: 120 },
                { display: '备注', name: 'd', width: 120 },
                { display: '是否停用', name: 'e', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,

                rowDblClick: function (event, ui) {
                    openUpdate(ui.rowData);
                },
                dataModel: {
                    url: 'http://118.178.184.131:9090/static_column/grid'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'remove' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'putin' },
                        { type: 'button', label: '下载导入末班', listeners: [{ click: downloadModule }], icon: 'download' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                openUpdate(currentRowData);
            })
        };

        $(function () {
            initFrom();
            initTree();
            initGrid();

            $(".text-input").on('keyup', function () {
                var $self = $(this);
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
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">岗位编码：</td>
                    <td class="ipt">
                        <input id="jobCode" type="text" />
                    </td>

                    <td class="label">岗位名称：</td>
                    <td class="ipt">
                        <input id="jobName" type="text" />
                    </td>

                    <td class="label">科室名称：</td>
                    <td class="ipt">
                        <select id="dept_name" style="width: 180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">是否停用：</td>
                    <td class="ipt">
                        <select id="isStop" style="width: 180px;"></select>
                    </td>

                    <td class="label"></td>
                    <td class="ipt">
                        <button>岗位结构图</button>
                    </td>
                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>