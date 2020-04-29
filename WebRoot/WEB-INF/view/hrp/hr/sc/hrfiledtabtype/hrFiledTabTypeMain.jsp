<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrFiledtabTypeMain</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var type_filed_code,
                grid;
            $(function () {
                loadDict();
                loadGrid();

                $('#maingrid').on('click', '.td-a', function () { // 给a标签设置事件代理
                    var index = $(this).attr('data-item') * 1;
                    var data = grid.getRowData(index);
                    var value = $(this).text();
                    update(data, index, value);
                })
                search();
            })

            function loadDict() {
               /*  type_filed_code = $("#type_filed_code").etSelect({
                    url: '',
                    defaultValue: "none"
                }); */
            }

            function loadGrid() {
                var gridObj = {
                    editable: false,
                    checkbox: true,
                    height: '100%',
                    showBottom:false,
                    addRowByKey: true //  快捷键控制添加行
                };
                gridObj.columns = [{
                        display: "类别代码",
                        width: 120,
                        name: "type_filed_code",
                        editable: false,
                        render: function (ui) { // 修改页打开
                            return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                                '</a>'
                        }
                    },
                    {
                        display: '类别名称',
                        name: 'type_filed_name',
                        width: 120
                    },
                    {
                        display: "备注",
                        width: 120,
                        name: "note"
                    }
                ];
                gridObj.dataModel = { // 数据加载的有关属性
                    recIndx: 'type_filed_code'
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
                        /* }, {
                            type: "button",
                            label: '保存',
                            icon: 'save',
                            listeners: [{
                                click: save
                            }] */
                        }/* ,{
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importDate
                            }]
                        } */
                    ]
                };
                grid = $("#maingrid").etGrid(gridObj);
            }

            function update(data, index, value) {
            	console.log(value)
                var parm = 'type_filed_code='+value;
                $.etDialog.open({
                    url: 'hrFiledTabTypeUpdatePage.do?isCheck=false&' + parm,
                    height: 320,
                    width: 650,
                    title: '代码表数据修改页',
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
                          ParamVo.push({   // 所需的主键字段 push到里面去
                        	  type_filed_code:rowdata.type_filed_code,
                        	  group_id :rowdata.group_id,
                        	  hos_id :rowdata.hos_id/* ,
                        	  copy_code :rowdata.copy_code, */
                         }); 
                    });
					console.log(ParamVo)
                    $.etDialog.confirm('确定删除?', function () {
                        ajaxPostData({
                            url: "deleteHrFiledTabType.do",
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
                    url: 'hrFiledTabTypeAddPage.do?isCheck=false',
                    height: 300,
                    width: 700,
                    title: '代码表数据添加页',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.saveData();
                    }
                });
            }

            /*function save() {

            } */

            function search() {
                var param = [];
                 param.push({
                	name :'type_filed_code',
                	value:$('#type_filed_code').val()
                	});
                grid.loadData(param,'queryHrFiledTabType.do');
            }
            //导入数据
            function importDate(){
    		var para = {
    			"column" : [ {
    				"name" : "type_filed_code",
    				"display" : "类别代码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "type_filed_name",
    				"display" : "类别名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		  importSpreadView("/hrp/hr/sysstruc/hrfiledtabtype/importDateHDTT.do?isCheck=false", para, search);  
    	}
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">类别代码：</td>
                    <td class="ipt">
                        <input name="type_filed_code" id="type_filed_code" style="width:180px;" type="text"/>
                    </td>
                </tr>
            </table>
        </div>
        <div id="maingrid">
        </div>
    </body>

    </html>