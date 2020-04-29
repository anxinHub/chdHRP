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
        var method_grid, tree, func_type_select, tran_freq,mod_code;
        var active_form;
        var exec_select_hour,
        exec_select_minutes,
        exec_select_seconds,
        exec_select_month,
        exec_select_day,
        exec_select_season,
        exec_select_week,
        exec_select_times;
        var normal_form_validate, active_form_validate;

        $(function () {
        	mod_code = $("#mod_code").etSelect({
                url: '../../queryMods.do?isCheck=false',
                defaultValue: '${mod_code}',
                onChange: function (value) {
                    reloadMainColSelect(value);
                }
            });
        	//$("#mod_code").ligerComboBox({disabled:true});	
        	//liger.get("mod_code").set("disabled", true);
        	if(${is_system}==0){
        		$("#fun_panel").show();
        		$("#hr").show();	
            	init();
            	debugger;
            	liger.get("mod_code").setData("01");
            	//$("#mod_code").val('{mod_code}');
        	}else{
        		
        	}init();
        });
        function init() {
            initGrid();
          /*   func_type_select = $("#func_type_select").etSelect({
                url: '../../queryHpmFunType.do?isCheck=false',
                defaultValue: 'none',
                onChange: function (value) {
                    tree.setting.async.otherParam = {
                        func_type_select: value
                    };
                    tree.reAsyncChildNodes(null, 'refresh');
                }
            }); */
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
                }
            });

            tran_freq = $("#tran_freq").etSelect({
                options: [
                    { id: 'T', text: '一次' },
                    { id: 'D', text: '每天' },
                    { id: 'W', text: '每周' },
                    { id: 'M', text: '每月' },
                    { id: 'S', text: '每季' },
                    { id: 'Y', text: '每年' },
                ],
                onInit: function (val) {
                    changeRunTime(val);
                },
                onChange: function (val) {
                    changeRunTime(val);
                },
                defaultValue: '${tran_freq}'
            });
            
            // 解析默认值
            var tran_freq_value = '${tran_freq}';
            var default_exec_time = {
                exec_select_times: '',
                exec_select_season: '',
                exec_select_month: '',
                exec_select_day: '',
                exec_select_week: '',
                exec_select_hour: '',
                exec_select_minutes: '',
                exec_select_seconds: '',
            };

            var iptArr = getRunTimeInput(tran_freq_value);
            
            var exec_time_split = exec_time.split('-');

            var exec_time_split_end = exec_time_split[exec_time_split.length - 1];
            var exec_time_split_end_split = exec_time_split_end.split(':');

            if (tran_freq_value === 'T') {
                default_exec_time.exec_select_times =
                    exec_time_split[0] + '-' + exec_time_split[1] + '-' + exec_time_split[2];
            } else {
                // 根据对应的顺序 赋值
                iptArr.forEach(function (ipt, index) {
                    default_exec_time[ipt] = exec_time_split[index];
                })
            }
            default_exec_time.exec_select_hour = exec_time_split_end_split[0];
            default_exec_time.exec_select_minutes = exec_time_split_end_split[1];
            default_exec_time.exec_select_seconds = exec_time_split_end_split[2];
            
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
            
            exec_select_times = $("#exec_select_times").etDatepicker({
                defaultDate: default_exec_time.exec_select_times
            });
            // 季
            exec_select_season = $("#exec_select_season").etSelect({
                options: [
                    { id: '1', text: '第一月' },
                    { id: '2', text: '第二月' },
                    { id: '3', text: '第三月' },
                ],
                showClear: false,
                defaultValue: default_exec_time.exec_select_season
            });
            // 月
            exec_select_month = $("#exec_select_month").etSelect({
                options: simpleNumberOptions(12, true),
                showClear: false,
                defaultValue: default_exec_time.exec_select_month
            });
            // 天
            var dayOptions = simpleNumberOptions(31, true);
            dayOptions.push({
                id: 'last_day',
                text: '最后一天'
            })
            exec_select_day = $("#exec_select_day").etSelect({
                options: dayOptions,
                showClear: false,
                defaultValue: default_exec_time.exec_select_day
            });
            // 周
            exec_select_week = $("#exec_select_week").etSelect({
                options: [
                    { id: '1', text: '周一' },
                    { id: '2', text: '周二' },
                    { id: '3', text: '周三' },
                    { id: '4', text: '周四' },
                    { id: '5', text: '周五' },
                    { id: '6', text: '周六' },
                    { id: '7', text: '周日' },
                ],
                showClear: false,
                defaultValue: default_exec_time.exec_select_week
            });
            // 小时
            exec_select_hour = $("#exec_select_hour").etSelect({
                options: simpleNumberOptions(24),
                showClear: false,
                defaultValue: default_exec_time.exec_select_hour
            });
            // 分钟
            exec_select_minutes = $("#exec_select_minutes").etSelect({
                options: simpleNumberOptions(60),
                showClear: false,
                maxOptions: 60,
                defaultValue: default_exec_time.exec_select_minutes
            });
            // 秒
            exec_select_seconds = $("#exec_select_seconds").etSelect({
                options: simpleNumberOptions(60),
                showClear: false,
                maxOptions: 60,
                defaultValue: default_exec_time.exec_select_seconds
            });
        }

        function initValidate() {
            normal_form_validate = $.etValidate({
                items: [
                    // { el: $("#main_tab_code"), required: true },
                    // { el: $("#main_col_code"), required: true },
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

        function changeRunTime(rate) {
            $(".exec_select_times").hide();
            $(".exec_select_week").hide();
            $(".exec_select_day").hide();
            $(".exec_select_season").hide();
            $(".exec_select_month").hide();

            var showIptArr = getRunTimeInput(rate);

            showIptArr.forEach(function (ipt) {
                $("." + ipt).show();
            })
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
                toolbar: method_toolbar,
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

        function del() {
            method_grid.deleteSelectedRows();
        }

        function saveData() {
        	debugger;
        	var is_system=${is_system};
        	if(is_system==0){
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
        	}
	            var data = {
	            		main_tab_code: '${main_tab_code}', // 数据表编码
	            		main_col_code: '${main_col_code}', // 数据列编码
	            		mod_code:$("#mod_code").val(),
	                    note: $('#note').val(),
	                    is_system: ${is_system},
	                    tran_id:'${tran_id}'
	            };
	         if(is_system==0){
	            // 函数格式
	            data.exec_func = gridData[0].rowData.fun_method_eng;
	            // 动态表单
	            active_form_data = active_form.getFormData()
	            data.exec_func_val = [];
	
	            for (var key of active_form_data.keys()) {
	                data.exec_func_val.push({[key]:active_form_data.get(key)})
	            }
	            data.exec_func_val = JSON.stringify(data.exec_func_val)
	         }
            // 事务频率
         //   var tran_freq_value = tran_freq.getValue();
         //   data.tran_freq = tran_freq_value;
            // 执行时间
         /*   var exec_select_value = '';
            var exec_select_validate = true;
            var runTimeIpts = getRunTimeInput(tran_freq_value);
            var runTimeIptsLen = runTimeIpts.length;

            for (var i = 0; i < runTimeIptsLen; i++) {
                var ipt = runTimeIpts[i];
                
                var v = window[ipt].getValue();
                if (!v) {
                    exec_select_validate = false;
                }
                exec_select_value += v + '-';
            }

            exec_select_value += exec_select_hour.getValue() + ':';
            exec_select_value += exec_select_minutes.getValue() + ':';
            exec_select_value += exec_select_seconds.getValue();
            */
            if (!exe_time) {
                $.etDialog.error('请填写执行时机！');
                return;
            }
           // data.exec_time = exec_select_value;
			data.exec_time=$("#exe_time").val();
            
            ajaxPostData({
                url: 'updateHrCaltrans.do?isCheck=false',
                data: data,
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.search();
                },
                delayCallback: true
            })
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
                    <input id="main_tab_code" type="text" disabled value="${main_tab_code}">
                </td>
                <td class="label" style="width:100px;">
                    数据列编码：
                </td>
                <td class="ipt">
                    <input id="main_col_code" type="text" disabled value="${main_col_code}">
                </td>
                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="note" type="text" value="${note}">
                </td>
            </tr>
            <tr>
            <td class="label">
                   业务模块：
                </td>
           <td  class="ipt"><select id="mod_code" style="width:180px;"></select></td>
                <td class="label">
                   执行规则：
                </td>
           <td  class="ipt"> <input type="text" name="exe_time" id="exe_time" readonly value="${exec_time}" }></td>
           <td><input type=button id="sel" value="选择" onclick="setCron();"></td>
                 
                
            </tr>
        </table>
    </div>
    <hr style="margin: 10px 20px;display:none" id="hr"/>
    <div class="flex-wrap" style="height:600px;padding:5px;display:none;" id="fun_panel">
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