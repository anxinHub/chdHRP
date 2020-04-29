<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrCastransMain</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var main_tab_code,
                affi_tab_code,
                up_auto,
                cas_nature,
                cas_level,
                grid;
            $(function () {
                loadDict();
                loadGrid();

                $('#maingrid').on('click', '.td-a', function () {
                    var index = $(this).attr('data-item') * 1;
                    var data = grid.getRowData(index);
                    var value = $(this).text();
                    update(data, index, value);
                })
            })
    function isNumber(){         
        	var val = $("#cas_level").val();
        	if(val!=""){
        		var ival = parseInt(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN
                if(!isNaN(ival)){
                	if(ival<=0){
                		  $.etDialog.error('请输入大于0的数字');
                		return false;
                	}
                } else{
              	  $.etDialog.error('非法的数字类型');
                  return false;
              }	
        	}
        	
        	return true;
        }

            function loadDict() {
                main_tab_code = $("#main_tab_code").etSelect({
                    url: '../../queryHrTabStruc.do?isCheck=false',
                    defaultValue: "none"
                });
                affi_tab_code = $("#affi_tab_code").etSelect({
                    url: '../../queryHrTabStruc.do?isCheck=false',
                    defaultValue: "none"
                });
                up_auto = $("#up_auto").etSelect({
                	options: [{id:0,text:'手动'},{id:1,text:'自动'}],
                    defaultValue: "none"
                });
                cas_nature = $("#cas_nature").etSelect({
                	options: [{id:'mf',text:'主到附'},{id:'fm',text:'附到主'},{id:'rt',text:'双向实时'}],
                    defaultValue: "none"
                });
                

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
                        display: "主数据表名",
                        align: "left",
                        width: 120,
                        name: "main_tab_name",
                        editable: false,
                        render: function (ui) { // 修改页打开
                            return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                                '</a>'
                        }
                    },
                    {
                        display: '主数据列名',
                        align: 'left',
                        name: 'main_col_name',
                        width: 120
                    },
                    {
                        display: "附数据表名",
                        align: "left",
                        width: 120,
                        name: "affi_tab_name"
                    },
                    {
                        display: "附数据列名",
                        align: "left",
                        width: 120,
                        name: "affi_col_name"
                    },
                    {
                        display: "级联性质",
                        align: "left",
                        width: 120,
                        name: "cas_nature",
                        render: function (ui) {
                        	switch(ui.cellData){
                        	case 'mf':
                        	  return '主到附';
                        	  break;
                        	case 'fm':
                        	  return '附到主';
                        	  break;
                        	case 'rt':
                        	  return '双向实时';
                        	  break;
                        	default:
                        	  return '';
                        	}
                        }
                    },
                    {
                        display: "自动更新",
                        align: "center",
                        width: 120,
                        name: "up_auto",
                        render: function (ui) {
                        	switch(ui.cellData){
                        	case 0:
                        	  return '手动';
                        	  break;
                        	case 1:
                        	  return '自动';
                        	  break;
                        	default:
                        	  return '';
                        	}
                        }
                    },
                    {
                        display: "事务别级",
                        align: "left",
                        width: 120,
                        name: "cas_level"
                    },
                    {
                        display: "自定义级联",
                        align: "left",
                        width: 120,
                        name: "is_sql",
                        render: function (ui) {
                        	switch(ui.cellData){
                        	case 0:
                        	  return '否';
                        	  break;
                        	case 1:
                        	  return '是';
                        	  break;
                        	default:
                        	  return '';
                        	}
                        }
                    },
                    {
                        display: "级联条件",
                        align: "left",
                        width: 220,
                        name: "cas_sql"
                    }
                ];
                gridObj.dataModel = { // 数据加载的有关属性
                    location: "remote",
                    url: 'queryHrCastrans.do',
                    recIndx: 'class_code'
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
                            id: 'add',
                            listeners: [{
                                click: add
                            }]
                        },
                        {
                            type: "button",
                            label: '删除',
                            icon: 'delete',
                            listeners: [{
                                click: deleteData
                            }]
                         }/* , {
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importData
                            }] 
                        }/* , {
                            type: "button",
                            label: '导出',
                            icon: 'export',
                            listeners: [{
                                click: exportData
                            }]
                        } */
                    ]
                };
                grid = $("#maingrid").etGrid(gridObj);
            }

            function add() {
                $.etDialog.open({
                    url: 'hrCastransAddPage.do?isCheck=false',
                    height: 500,
                    width: 750,
                    title: '级联事务数据添加页',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.saveData();
                    }
                });
            }

            function update(data, index, value) {
                var parm = 'main_tab_code='+data.main_tab_code+'&main_col_code='+data.main_col_code;
                $.etDialog.open({
                    url: 'hrCastransUpdatePage.do?isCheck=false&' + parm,
                    height: 500,
                    width: 700,
                    title: '级联事务数据修改页',
                    btn: ["确定", "取消"],
                    btn1: function (index, el) {
                        var frameWindow = window[el.find('iframe')[0].name];
                        frameWindow.saveData();
                    },
                    btn2: function (index) {
                        $.etDialog.close(index); // 关闭弹窗
                        return false;
                    }
                });

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
                            url: "deleteHrCastrans.do",
                            data: {paramVo:JSON.stringify(ParamVo)},
                            success: function (res) {
                                if (res.state == "true") {
                                    search();
                                }
                            }
                        })
                    });
                }
            }

            /* function importData() {

            } */

            /* function exportData() {
            	exportGrid(gridObj);
            } */

            function search() {
            	if(!isNumber())
          			return;

            	var param = [
                	{name:'main_tab_code',value:main_tab_code.getValue()},
                	{name:'affi_tab_code',value:affi_tab_code.getValue()},
                	{name:'up_auto',value:up_auto.getValue()},
                	{name:'cas_nature',value:cas_nature.getValue()},
                	{name:'cas_level',value:$('#cas_level').val()}
                ];
                grid.loadData(param);
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">主数据表名：</td>
                    <td class="ipt">
                        <select name="main_tab_code" id="main_tab_code" style="width:180px;"></select>
                    </td>
                    <td class="label">附数据表名：</td>
                    <td class="ipt">
                        <select name="affi_tab_code" id="affi_tab_code" style="width:180px;"></select>
                    </td>
                    <td class="label">自动更新：</td>
                    <td class="ipt">
                        <select name="up_auto" id="up_auto" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">级联性质：</td>
                    <td class="ipt">
                        <select name="cas_nature" id="cas_nature" style="width:180px;"></select>
                    </td>
                    <td class="label">事务别级：</td>
                    <td class="ipt">
                        <input name="cas_level" type="text" id="cas_level" style="width:180px;">
                    </td>
                </tr>
            </table>
        </div>
        <div id="maingrid"></div>
    </body>

    </html>