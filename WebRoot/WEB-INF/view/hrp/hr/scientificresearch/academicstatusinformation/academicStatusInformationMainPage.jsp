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
            var openAdd = function () {
                $.etDialog.open({
                    url: 'addAcademicStatusInformationPage.do?isCheck=false',
                    height: 300,
                    width: 700,
                    title: '添加',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.save();
                    }
                });
            };
            var remove = function () {
                ajaxPostData({
                    url: 'http://118.178.184.131:9090/delete',
                    success: function (res) {
                        grid.deleteSelectedRows();
                    }
                })
            };
            var query = function () {
                var param = [
                    { name: 'field_col_code', value: $('#field_col_code').val() },
                    { name: 'field_col_name', value: $('#field_col_name').val() }
                ];
                grid.loadData(param);
            };
            var openUpdate = function (openParam) {
                $.etDialog.open({
                    url: 'updateAcademicStatusInformationPage.do',
                    title: '修改',
                    width: 700,
                    height: 400,
                    btn: ['保存', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.save();
                    }
                });
            };
            var initGrid = function () {
                var columns = [
                    { display: '学术地位编码', name: 'field_col_code', width: 140 },
                    { display: '学术地位名称', name: 'field_col_name', width: 140 },
                    { display: '上级编码', name: 'super_col_name', width: 140 },
                    { display: '是否末级', name: 'is_last_text', width: 140 },
                ];
                var gridObj = {
                    checkbox: true,
                    height: '100%',
                    columns: columns,
                    dataModel: {
                        url: 'queryAcademicStatusInformation.do'
                    },
                   /*  rowDblClick: function (event, ui) {
                        openUpdate();
                    }, */

                    toolbar: {
                        items: [
                            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                            /* { type: "button", label: '添加', icon: 'add', listeners: [{ click: openAdd }] },
                            { type: "button", label: '删除', icon: 'delete', listeners: [{ click: remove }] } */
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(gridObj);
            };

            var save = function () {
                alert(333);
            }
            $(function () {
                initGrid();
            })
        </script>
    </head>

    <body>
        <!-- 
            个人学术地位信息主页            
            添加页面 URL addAcademicStatusInformationPage       
            修改页面跳转 URL    
            updateAcademicStatusInformationPage 
        -->
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label" style="width:85px">学术地位编码：</td>
                    <td class="ipt">
                        <input type="text" name="" id="field_col_code" style="width: 180px;" />
                    </td>
                    <td class="label" style="width:85px">学术地位名称：</td>
                    <td class="ipt">
                        <input type="text" name="" id="field_col_name" style="width: 180px;" />
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>