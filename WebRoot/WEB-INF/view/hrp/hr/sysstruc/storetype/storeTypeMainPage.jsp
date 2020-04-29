<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>storeTypeMain</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var class_code,
                grid;
            $(function () {
              
                loadGrid();

                $('#maingrid').on('click', '.td-a', function () { // 给a标签设置事件代理
                    var index = $(this).attr('data-item') * 1;
                    var data = grid.getRowData(index);
                    var value = $(this).text();
                    update(data, index, value);
                })

            })


            function loadGrid() {
                var gridObj = {
                    editable: true,
                    checkbox: true,
                    height: '100%',
                    addRowByKey: true //  快捷键控制添加行
                };
                gridObj.columns = [{
                        display: "类型代码",
                        width: 120,
                        name: "store_type_code",
                        editable: false,
                        render: function (ui) { // 修改页打开
                            return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                                '</a>'
                        }
                    },
                    {
                        display: '类型名称',
                        name: 'store_type_name',
                        width: 120
                    },
                    {
                        display: "备注",
                        width: 120,
                        name: "note"
                    }
                ];
                gridObj.dataModel = { // 数据加载的有关属性
                    url: 'queryHrStoreType.do',
                    recIndx: 'store_type_code'
                };
                gridObj.toolbar = {
                    items: [{
                            type: "button",
                            label: '查询',
                            icon: 'search',
                            id: 'search',
                            listeners: [{
                                click: search
                            }]
                        },
                        {
                            type: "button",
                            label: '添加',
                            icon: 'add',
                            listeners: [{
                                click: add
                            }]
                        }, {
                            type: "button",
                            label: '删除',
                            icon: 'delete',
                            listeners: [{
                                click: deleteData
                            }]
                        },{
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importDate
                            }]
                        }
                        /* , {
                            type: "button",
                            label: '保存',
                            icon: 'save',
                            listeners: [{
                                click: save
                            } ]
                        }*/
                    ]
                };
                grid = $("#maingrid").etGrid(gridObj);
            }

            function update(openParam) {
                var parm =  openParam.store_type_code;
                $.etDialog.open({
                    url: 'updateHrStoreTypePage.do?isCheck=false&store_type_code=' + parm,
                    height: 320,
                    width: 650,
                    title: '档案库分类构建修改页',
                    btn: ["确定", "取消"],
                    btn1: function (index, el) {
                        var frameWindow = window[el.find('iframe')[0].name];
                        frameWindow.saveData();
                    },
                    btn2: function (index) {
                        $.etDialog.close(index); // 关闭弹窗
                        return false;
                    }
                })
            }

            function deleteData() {
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
                            url: "deleteHrStoreType.do",
                            data: {
                                paramVo: JSON.stringify(ParamVo)
                            },
                            success: function (res) {
                                if (res.state == "true") {
                                	search();
                                }
                            }
                        })
                    });
                }

            }

            function add() {
                $.etDialog.open({
                    url: 'addHrStoreTypePage.do?isCheck=false',
                    height: 300,
                    width: 700,
                    title: '档案库分类构建添加页',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.saveData();
                    }
                });
            }

            function save() {

            }

            function search() {
            	   params = [
                       { name: 'store_type_code', value: $('#store_type_code').val() }
                   ];
                grid.loadData(params);
            }
            //导入数据
            function importDate(){
        		//$("form[name=fileForm]").submit();
        		var para = {
        			"column" : [ {
        				"name" : "store_type_code",
        				"display" : "类型代码",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "store_type_name",
        				"display" : "类型名称",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "note",
        				"display" : "备注",
        				"width" : "200"
        			} ]

        		};
        		importSpreadView("/hrp/hr/sysstruc/importDate.do?isCheck=false", para); 
        	}
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">档案库分类：</td>
                    <td class="ipt">
                        <input name="store_type_code" id="store_type_code" type="text"/>
                    </td>
                </tr>
            </table>
        </div>
        <div id="maingrid">
        </div>
    </body>

    </html>