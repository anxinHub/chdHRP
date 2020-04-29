<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,grid,select,dialog" name="plugins" />
</jsp:include>
<script>
        var grid,
            target_name;

        var query = function () {
        	
        	 params = [
                 { name: 'indicator_name', value: $('#indicator_name').val() },
                { name: 'indicator_code', value: $('#indicator_code').val() }
            ];
            grid.loadData(params);
        	
        	
        };
        var add = function () {
            $.etDialog.open({
                url: 'addCompetencyTargetPage.do?isCheck=false',
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
        	var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	var ParamVo = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    ParamVo.push(rowdata);
                });
            $.etDialog.confirm('确定删除?', function () {
	        ajaxPostData({
            url: 'deleteCompetencyTarget.do',
            data: { paramVo: JSON.stringify(ParamVo) },
            success: function () {
                query();
            }
        })
                });}
        
        };
        var print = function () {};
        var openUpdate = function (rowData) {
            $.etDialog.open({
                url: 'updateCompetencyTargetPage.do?isCheck=false&indicator_code=' + rowData.indicator_code,
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
                { display: '指标编码', name: 'indicator_code', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '指标名称', name: 'indicator_name', width: 140 },
                { display: '说明', name: 'note', width: 180 },
                { display: '是否停用', name: 'is_stop', width: 120,
                	 render: function (ui) {
                         var cellData = ui.cellData;

                         return cellData === 1 ? '是' : '否'
                     } }
            ];
            var paramObj = {
                height: '100%',
                checkbox: true,
                rowDblClick: function (event, ui) {
                    openUpdate(ui.rowData);
                },
                dataModel: {
                    url: 'queryCompetencyTarget.do'
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

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                openUpdate(currentRowData);
            })
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
				<td class="label">指标编码：</td>
				<td class="ipt"><input id="indicator_code" type="text" /></td>

				<td class="label">指标名称：</td>
				<td class="ipt"><input id="indicator_name" type="text" /></td>
			</tr>
		</table>
	</div>

	<div id="mainGrid"></div>
</body>

</html>