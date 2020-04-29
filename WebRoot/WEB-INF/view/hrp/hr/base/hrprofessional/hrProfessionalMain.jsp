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
        var tree, grid;
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: 'queryHrProfessionalTree.do?isCheck=false'
                },
                callback: {
                    onClick: function () {
                        $("#professional_code").val('');
                        $("#professional_name").val('');
                        query('tree');
                    }
                }
            })
        };
        var query = function (queryFor) {
            if (queryFor === 'tree') {
                var selectedNode = tree.getSelectedNodes()[0];
                var professional_code = selectedNode ? selectedNode.id : '';

                params = [
                    {
                        name: 'professional_code',
                        value: professional_code
                    }
                ]
            } else {
                params = [
                    {
                        name: 'professional_code',
                        value: $("#professional_code").val()
                    },
                    {
                        name: 'professional_name',
                        value: $("#professional_name").val()
                    }
                ]
            }
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addProfessionalInfoPage.do?isCheck=false',
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
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(rowdata);
                });

                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: 'deleteProfessionalInfo.do',
                        data: {
                            paramVo: JSON.stringify(param)
                        },
                        success: function () {
                            grid.deleteRows(data);
                            tree.reAsyncChildNodes(null, 'refresh');
                        }
                    })
                });
            }
        };
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updateProfessionalInfoPage.do?isCheck=false&professional_code=' + openParam.professional_code,
                title: '修改',
                width: 450,
                height: 450,
                btn: ['保存', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [{
                    display: '专业编码',
                    name: 'professional_code',
                    width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                {
                    display: '专业名称',
                    name: 'professional_name',
                    width: 120
                },
                {
                    display: '是否停用',
                    name: 'is_stop_name',
                    width: 120,
                    render: function (ui) {
                        var cellData = ui.cellData;
                        if (cellData === "是") {
                            return  '<span style="color:red;"> 是</span>';
                        } else {
                            return '否';

                        }
                    }    
                },
                {
                    display: "备注",
                    width: 120,
                    name: "note"
                }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        professional_code: rowData.professional_code
                    };

                    openUpdate(openParam);
                },
                dataModel: {
                    url: 'queryHrProfessional.do?isCheck=false'
                },
                usePager: true,
                columns: columns,
                toolbar: {
                    items: [{
                            type: 'button',
                            label: '查询',
                            listeners: [{
                                click: query
                            }],
                            icon: 'search'
                        },
                        {
                            type: 'button',
                            label: '添加',
                            listeners: [{
                                click: add
                            }],
                            icon: 'add'
                        },
                        {
                            type: 'button',
                            label: '删除',
                            listeners: [{
                                click: remove
                            }],
                            icon: 'remove'
                        }, 
                        {
                            type: 'button',
                            label: '导入',
                            listeners: [{
                                click: importDate
                            }],
                            icon: 'import'
                        }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];

                var openParam = {
                    professional_code: currentRowData.professional_code
                };
                openUpdate(openParam);
            })
        };

        var initForm = function () {
     
        };

        $(function () {
            initTree();
            initForm();
            initGrid();

            // 给输入框绑定搜索树事件
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
        //导入
        function importDate(){
    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "professional_code",
    				"display" : "专业编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "professional_name",
    				"display" : "专业名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_stop",
    				"display" : "是否停用",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/base/importDateHPF.do?isCheck=false", para);
    	}
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">专业编码：</td>
                    <td class="ipt">
                        <input id="professional_code" type="text" />
                    </td>

                    <td class="label">专业名称：</td>
                    <td class="ipt">
                        <input id="professional_name" type="text" />
                    </td>

                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>