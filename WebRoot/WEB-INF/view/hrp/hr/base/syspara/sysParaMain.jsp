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
        var grid;
        var query = function () {
            params = [
                { name: 'para_code', value: $("#para_code").val() },
                { name: 'para_name', value: $("#para_name").val() }
            ];
            grid.loadData(params);
        };
        var save = function (data) {
        	ajaxPostData({
                url: 'updateSysPara.do',
                data: {
                	group_id:data.group_id,
    				hos_id:data.hos_id,
    				copy_code:data.copy_code,
    				para_code:data.para_code,
    				para_value:data.para_value
                },
                delayCallback:true,
                success: function (responseData) {
                	if(responseData.state=="true"){
    	                query();
    	            }
                }
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '参数编码', name: 'para_code', width: '10%' },
                { display: '参数名称', name: 'para_name', width: '20%' },
                { display: '参数值', name: 'para_value', width: '13%', editable: true},
                { display: '参数说明', name: 'note', width: '37%'},
                { display: '操作', name: 'edit', width: '15%', align:'center',
                	render: function (ui) {
                        var btnHtml =
                            '<button class="savebtn" row-index="' + ui.rowIndx + '">保存</button>'

                        return btnHtml;
                    }
                }

            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
                checkbox: true,

                dataModel: {
                    url: 'querySysPara.do'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        /* { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
			
            $("#mainGrid").on('click', '.savebtn', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                save(currentRowData);
            })
        };

        $(function () {
            initGrid();
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">参数编码：</td>
            <td class="ipt">
                <input id="para_code" type="text" />
            </td>

            <td class="label" style="width: 100px;">参数名称：</td>
            <td class="ipt">
                <input id="para_name" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
