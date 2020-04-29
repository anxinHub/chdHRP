<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,tree,hr,validate,datepicker,dialog,grid,form" name="plugins" />
    </jsp:include>
    <script>
        var method_grid, tree, func_type_select, main_tab_code, main_col_code, mod_code,func_type;
        var active_form;
         
        var normal_form_validate, active_form_validate;
		
        $(function () {
        	mod_code = $("#mod_code").etSelect({
                url: '../../queryMods.do?isCheck=false',
                defaultValue: 'none',
                onChange: function (value) {
                    reloadMainColSelect(value);
                }
            });
        	 tran_freq = $("#func_type").etSelect({
                 options: [
                     { id: '0', text: '数据库' },
                     { id: '1', text: '应用' },
                 ],
                 defaultValue: '1'
             });
            init();
        });
        function init() {
            initGrid();
            initSelect();
            initTree();
            initValidate();
        }

        function initSelect() {
            func_type_select = $("#func_type_select").etSelect({
                url: '../../queryHpmFunType.do?isCheck=false',
                defaultValue: 'none',
                onChange: function (value) {
                    tree.setting.async.otherParam = {
                        func_type_select: value
                    };
                    tree.reAsyncChildNodes(null, 'refresh');
                    query();
                }
            });
            main_tab_code = $("#main_tab_code").etSelect({
                url: '../../queryHrTabStruc.do?isCheck=false',
                defaultValue: 'none',
                onChange: function (value) {
                    reloadMainColSelect(value)
                }
            });

            main_col_code = $("#main_col_code").etSelect({
                url: '',
            });

            function reloadMainColSelect(value) {
                main_col_code.reload({
                    url: "../../queryHrColStruc.do?isCheck=false",
                    para: { tab_code: value }
                });
            }
         
            // 生成静态选择数据
            var simpleNumberOptions = function (times, isAdd) {
                var options = [];
                var startNum = 0;

                if (isAdd) {
                    startNum = 1;
                    times += 1;
                }
                if (times) {
                    for (var i = startNum; i < times; i++) {
                        var pre = i < 10 ? '0' : '';
                        var value = pre + i;

                        options.push({
                            id: value,
                            text: value
                        })
                    }
                }
                return options;
            };
            // 执行时机
          
        }

        function initValidate() {
            normal_form_validate = $.etValidate({
                items: [
                    { el: $("#main_tab_code"), required: true },
                    { el: $("#main_col_code"), required: true },
                    { el: $("#mod_code"), required: true },
                    { el: $("#func_type"), required: true },
                    { el: $("#exe_time"), required: true },
                ]
            });
        }
        
        function getRunTimeInput(rate) {
            var ipts = [];

            switch (rate) {
                case 'T':
                    ipts = [ 'exec_select_times' ];
                    break;
                case 'D':
                    ipts = [];
                    break;
                case 'W':
                    ipts = [ 'exec_select_week' ];
                    break;
                case 'M':
                    ipts = [ 'exec_select_day' ];
                    break;
                case 'S':
                    ipts = [ 'exec_select_season', 'exec_select_day' ];
                    break;
                case 'Y':
                    ipts = [ 'exec_select_month', 'exec_select_day' ];
                    break;
            }
            return ipts;
        }

       

        function initTree() {
            var url = 'queryCaltransFunTypeTree.do?isCheck=false';
            tree = $("#tree").etTree({
                async: {
                    enable: true,
                    url: url
                },
                callback: {
                    onClick: function (event, name, node) {
                        query(node.id)
                    }
                }
            });
        }

        function initGrid() {
            // 基础表格参数
            var method_toolbar = {
                items: [
                    { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                    { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] }
                ]
            };
            var method_columns = [
                { display: '函数代码', name: 'fun_code', width: 120 },
                { display: '函数名称', name: 'fun_name', width: 120 },
                { display: '函数格式', name: 'fun_method_eng', width: 280 }
            ];
            var method_obj = {
                height: '200',
                width: '600',
                editable: true,
                inWindowHeight: true,
                checkbox: true,
                usePager: false,
                //toolbar: method_toolbar,
                columns: method_columns,
                selectionModel: {
                    type: 'row',
                    mode: 'single',
                },
                rowSelect: function (event, ui) {
                    var fun_code = ui.rowData.fun_code;

                    ajaxPostData({
                        url: 'queryPrmFunByCode.do?isCheck=false',
                        data: { fun_code: fun_code },
                        success: function (res) {
                            res.fieldItems.forEach(function (item) {
                                item.place = 1
                            })
                            $("#active_form").html('');
                            active_form = $("#active_form").etForm(res);

                            active_form.initWidget();
                            active_form_validate = active_form.initValidate();
                        }
                    })

                },
                rowUnSelect: function () {
                    $("#active_form").html('');
                }
            };

            method_grid = $("#method_grid").etGrid(method_obj);
        }

        function query(value) {
            var params = [
                { name: 'fun_code', value: value }
            ];
            method_grid.loadData(params, 'queryHrFun.do?isCheck=false');
        }

        function add() {
            method_grid.addRow({})
        }
		function setCron(){
			$.etDialog.open({
                url: 'hrCaltransSetCron.do?isCheck=false',
                isMax: false,
                height:$(window).height()-100,
                width: $(window).width()-200,
                title: '计算事务数据添加页',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                	debugger;
                	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                   
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    var s=iframeWindow.getValue();
                    $("#exe_time").val(s);
                    $.etDialog.close(index);
                }
            });
		}
        function del() {
            method_grid.deleteSelectedRows();
        }

        function saveData() {
        	debugger;
            if (!normal_form_validate.test()) {
                return;
            }
            var gridData = method_grid.selectGet();
            if (gridData.length === 0) {
                $.etDialog.error('请选择函数格式');
                return;
            }
            if (!active_form_validate.test()) {
                return;
            }
            var data = {
            		main_tab_code: main_tab_code.getValue(), // 数据表编码
            		main_col_code: main_col_code.getValue(), // 数据列编码
                	note: $('#note').val(),
                	func_type: $('#func_type').val(),
                	mod_code: $('#mod_code').val(),
                	is_system:1
            };
            // 函数格式
            data.exec_func = gridData[0].rowData.fun_method_eng;
            // 动态表单
            active_form_data = active_form.getFormData()
            data.exec_func_val = [];

            for (var key of active_form_data.keys()) {
                data.exec_func_val.push({[key]:active_form_data.get(key)})
            }
            data.exec_func_val = JSON.stringify(data.exec_func_val)
          
			data.exec_time=$("#exe_time").val();
            
            debugger;
            ajaxPostData({
                url: 'addHrCaltrans.do?isCheck=false',
                data: data,
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.search();
                },
                delayCallback: true
            })
        }
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label" style="width:100px;">
                    数据表编码：
                </td>
                <td class="ipt">
                    <select id="main_tab_code" style="width:180px;"></select>
                </td>
                <td class="label" style="width:100px;">
                    数据列编码：
                </td>
                <td class="ipt">
                    <input id="main_col_code" style="width:180px;"></input>
                </td>
                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="note" type="text">
                </td>
            </tr>
            <tr>
                <td class="label">
                    业务模块：
                </td>
                <td class="ipt">
                    <select id="mod_code" style="width:180px;"></select>
                </td>
                <td class="label">
                   事务类型：
                </td>
                <td class="ipt">
                    <select id="func_type" style="width:180px;"></select>
                </td>
                <td class="label">执行规则：</td>
                <td  class="ipt"> <input type="text" name="exe_time" id="exe_time" readonly value="${exec_time}" }></td>
           <td><input type=button id="sel" value="选择" onclick="setCron();"></td>
            </tr>
        </table>
    </div>
    <hr style="margin: 10px 20px;" />
    <div class="flex-wrap" style="height:600px;padding:5px;">
        <div class="flex-item-1" style="padding-left:17px;">
            <div>
                函数类型：
                <select id="func_type_select" style="width:180px"></select>
            </div>
            <div style="padding-top:10px;">
                <div style="height:280px;" id="tree">tree</div>
            </div>
        </div>
        <div class="flex-item-2">
            <div id="method_grid"></div>
            <div id="active_form" class="clearfix"></div>
        </div>
    </div>
</body>

</html>