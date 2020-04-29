<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="dialog,grid,datepicker" name="plugins" />
        </jsp:include>
        <script>
            var grid;
            $(function () {
                initGrid();
            });
            function initGrid() {
                var columns = [
                    { display: '论文类别编码', name: 'field_col_code', width: 140 },
                    { display: '论文类别名称', name: 'field_col_name', width: 140 },
                    { display: '上级编码', name: 'super_col_name', width: 140 },
                    { display: '是否末级', name: 'is_last_text', width: 140 },
                ];
                var gridObj = {
                    checkbox: true,
                    height: '100%',
                    columns: columns,
                    dataModel: {
                        url: 'queryAcademicPapersCategory.do'
                    },
                    rowDblClick: function (event, ui) {
                        openUpdate();
                    },

                    toolbar: {
                        items: [
                            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                           /*  { type: "button", label: '添加', icon: 'add', listeners: [{ click: openAdd }] },
                            { type: "button", label: '删除', icon: 'delete', listeners: [{ click: remove }] } */
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(gridObj);
            }

            function query() {
                var param = [
                    { name: 'field_col_code', value: $('#field_col_code').val() },
                    { name: 'field_col_name', value: $('#field_col_name').val() }
                ];
                grid.loadData(param);
            }
         
      

        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label" style="width:125px">
                        论文类别编码：
                    </td>
                    <td class="ipt">
                        <input type="text" id="field_col_code">
                    </td>
                    <td class="label" style="width:125px">
                        论文类别名称：
                    </td>
                    <td class="ipt">
                        <input type="text" id="field_col_name">
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>