<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var grid, last_select;
            var query = function () {
                var param = [
                    { name: 'field_col_code', value: $('#field_col_code').val() },
                    { name: 'field_col_name', value: $('#field_col_name').val() },
                    { name: 'is_last', value: last_select.getValue() },
                ];
                grid.loadData(param);
            };
            
            var initGrid = function () {
                var columns = [
                    { display: '学术荣誉编码', name: 'field_col_code', width: 140 },
                    { display: '学术荣誉名称', name: 'field_col_name', width: 140 },
                    { display: '上级编码', name: 'super_col_name', width: 140 },
                    { display: '是否末级', name: 'is_last_text', width: 140 },
                ];
                var gridObj = {
                    checkbox: false,
                    height: '100%',
                    columns: columns,
                    dataModel: {
                        url: 'queryPersonalAcademicHonors.do'
                    },
                    toolbar: {
                        items: [
                            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(gridObj);
            };

            var initSelect = function(){
                last_select = $("#last_select").etSelect({
                    options:[{id:0,text:'否'},{id:1,text:'是'} ],defaultValue: "none"
                })
            }

            $(function () {
                initGrid();
                initSelect();
            })
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label" style="width:85px">学术荣誉编码：</td>
                    <td class="ipt">
                        <input type="text" name="" id="field_col_code" style="width: 180px;" />
                    </td>
                    <td class="label" style="width:85px">学术荣誉名称：</td>
                    <td class="ipt">
                        <input type="text" name="" id="field_col_name" style="width: 180px;" />
                    </td>
                    <td class="label" style="width:85px">是否末级：</td>
                    <td class="ipt">
                        <select name="" id="last_select" style="width:180px;"></select>
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>