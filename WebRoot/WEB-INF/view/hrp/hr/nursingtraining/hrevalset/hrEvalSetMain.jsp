<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,datepicker,validate" name="plugins" />
        </jsp:include>
        <script>
            var grid, year,formValidate;
            
            var initValidate = function () {
                formValidate = $.etValidate({
                    items: [
                        { el: $("#year"), required: true },
                    ]
                });
            };
            
            var initGrid = function () {
                var columns = [
                    { display: '年度', name: 'year', width: 100 },
                    { display: '考核名称', name: 'eval_name', width: 100,
                    	editor: {
                            type: 'select',
                            keyField: 'eval_code',
                            url: '../../queryHrFiiedDataSelect.do?isCheck=false&store_type_code=DIC_EVAL',
                            change: function (rowData, cellData) {
                                grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                            }
                        }
                    },
                    { display: '考核合格分', name: 'eval_goal', width: 100 },
                    { display: '备注', name: 'note', width: 100 }

                ];
                var paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: true,
                    dataModel: {
                        url: 'queryHrEvalSet.do'
                    },
                    columns: columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(paramObj);
            };

            var initSelect = function () {
            	year = $("#year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    defaultDate: false
                });

            }

            var query = function () {
                var params = [
                    { name: 'year', value: $("#year").val() }
                ];
                grid.loadData(params);
            };

            var save = function () {
                var gridAllData = grid.getAllData();
                if (!gridAllData || gridAllData.length === 0) {
                    return;
                }

                ajaxPostData({
                    url: 'saveHrEvalSet.do',
                    data: {
                        paramVo: JSON.stringify(gridAllData)
                    },
                    success: function () {
                        query();
                    }
                })
            };

            var add = function () {
                grid.addRow();
            };

            var remove = function () {
                var selectData = grid.selectGet();
                if (selectData.length === 0) {
                    $.etDialog.error('请选择行');
                    return;
                }
				
                var param = [];
                selectData.forEach(function (item) {
                    param.push({
                        group_id: item.rowData.group_id,
                        hos_id: item.rowData.hos_id,
                        year: item.rowData.year,
                        eval_code: item.rowData.eval_code
                    });
                });
                
                $.etDialog.confirm(
                		'确定删除?',
                	    function () {
                			ajaxPostData({
                                url: 'deleteHrEvalSet.do',
                                data: { paramVo: JSON.stringify(param) },
                                success: function () {
                                    grid.deleteRows(selectData);
                                }
                            })
                	    }, function () {
                	        console.log('取消')
                	    }
                	)

                
            };

            $(function () {
                initSelect();
                initGrid();
                initValidate();
            });



        </script>
    </head>

    <body>
        <!-- 住院医师规培轮转成绩表（病区）主页 添加页面 URL hrZyyNtrainInareaAddPage 修改页面跳转 URL hrZyyNtrainInareaUpdatePage -->
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">年度：</td>
                    <td class="ipt">
                        <input id="year" type="text" />
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>