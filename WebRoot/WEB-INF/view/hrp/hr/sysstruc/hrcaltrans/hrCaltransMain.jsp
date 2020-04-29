<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>hrCaltransMain</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,dialog,grid" name="plugins" />
    </jsp:include>
    <script>
        var main_tab_code,
            main_col_code,
            tran_freq,
            tran_state,
            note,
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

        function loadDict() {
            $("#main_tab_code").on('change', search)
            $("#main_col_code").on('change', search)
       /*      tran_freq = $("#tran_freq").etSelect({
                options: [
                    { id: 'T', text: '一次' },
                    { id: 'D', text: '每天' },
                    { id: 'W', text: '每周' },
                    { id: 'M', text: '每月' },
                    { id: 'S', text: '每季' },
                    { id: 'Y', text: '每年' },
                ],
                defaultValue: "none",
                onChange: search
            }); */
            tran_state = $("#tran_state").etSelect({
                options: [
                    { id: '0', text: '新建' },
                    { id: '1', text: '启动' },
                    { id: '2', text: '中止' },
                ],
                defaultValue: "none",
                onChange: search
            });
        }

        function loadGrid() {
            var gridObj = {
                editable: true,
                checkbox: true,
                height: '100%',
                showBottom:false,
                addRowByKey: true //  快捷键控制添加行
            };
            gridObj.columns = [{
                display: "事务编号",
                align: "left",
                width: 120,
                name: "tran_id",
                editable: false,
                render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                        '</a>'
                }
            },
            {
                display: '数据表编码',
                align: 'left',
                name: 'main_tab_code',
                width: 100
            },
            {
                display: "数据列编码",
                align: "left",
                width: 120,
                name: "main_col_code"
            },
            {
                display: "模块",
                align: "left",
                width: 120,
                name: "mod_code"
            },
            {
                display: "类型",
                align: "left",
                width: 120,
                name: "is_system",
                render:function(r){
                	debugger;
                   if(r.rowData.is_system==0)
                        return "普通";
                   else
                   		return "系统";
                }
            },
            {
                display: "级别",
                align: "left",
                width: 120,
                name: "func_type",
                render:function(r){
                    if(r.rowData.func_type==0)
                         return "数据库";
                    else
                    	 return "应用";
                 }
            },
            {
                display: "执行规则",
                align: "left",
                width: 120,
                name: "exec_time"
            },
            {
                display: "事务方法",
                align: "left",
                width: 350,
                name: "exec_func"
            },
            {
                display: "状态",
                align: "left",
                width: 120,
                name: "state_name"
            },
            {
                display: "备注",
                align: "left",
                width: 120,
                name: "note"
            }
            ];
            gridObj.dataModel = { // 数据加载的有关属性
                location: "remote",
                url: 'queryHrCaltrans.do?isChect=false',
                recIndx: 'a'
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
                /* }, {
                    type: "button",
                    label: '保存',
                    icon: 'save',
                    listeners: [{
                        click: save
                    }] */
                }, {
                    type: "button",
                    label: '启用',
                    icon: 'gear',
                    listeners: [{
                        click: startFunc
                    }]
                }, {
                    type: "button",
                    label: '中止',
                    icon: 'close',
                    listeners: [{
                        click: stopFunc
                    }]
                }
                ]
            };
            grid = $("#maingrid").etGrid(gridObj);
        }

        function add() {
        	
        	parent.$.etDialog.open({
                url: 'hrp/hr/sysstruc/hrcaltrans/hrCaltransAddPage.do?isCheck=false',
                isMax: false,
                height:$(window).height()-10,
                width: $(window).width()-20,
                title: '计算事务数据添加页',
                btn: ['确定', '取消'],
                frameNameObj: { 'add': window.name }, 
                btn1: function (index, el) {
                	/* var addFrameName = parent.$.etDialog.getFrameName('add');
                	var addWindow = parent.window[addFrameName]; */
                   var iframeWindow = parent.window[el.find('iframe')[0]['name']];
                	iframeWindow.saveData();
                }
            });
        }

        function update(data, index, value) {
        	/* if(data.tran_state!=0){
        		$.etDialog.error('请选择新建状态的计算事物');
        	}else{ */
        		parent.$.etDialog.open({
                    url: 'hrp/hr/sysstruc/hrcaltrans/hrCaltransUpdatePage.do?isCheck=false&tran_id=' + value,
                isMax: false,
                height:$(window).height()-10,
                width: $(window).width()-20,
                title: '计算事务数据修改页',
                btn: ["确定", "取消"],
                btn1: function (index, el) {
                	  var iframeWindow = parent.window[el.find('iframe')[0]['name']];
                  	iframeWindow.saveData();
                },
             /*    btn2: function (index) {
                    $.etDialog.close(index); // 关闭弹窗
                    return false;
                } */
            });
        	//}
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
                    console.log(JSON.stringify(ParamVo))
                    ajaxPostData({
                        url: "deleteHrCaltrans.do",
                        data: {
                            ParamVo: JSON.stringify(ParamVo)
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

        /* function save() {

        }
 */
        //启动
        function startFunc() {
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var ParamVo = [];
                var tranId="";
                $(data).each(function () {
                	if(this.rowData.tran_state==1){
                		tranId+=this.rowData.tran_id+",";
                	}
                	
                    var rowdata = this.rowData;
                    ParamVo.push(rowdata);
                });
                if(tranId!=""){
                	$.etDialog.error('请选择新建状态的事务！');
                	return;
                }else{
                	ajaxPostData({
                        url: "startFuncHrCaltrans.do?isCheck=false",
                        data: {
                            ParamVo: JSON.stringify(ParamVo)
                        },
                        success: function (res) {
                            if (res.state == "true") {
                                search();
                            }
                        }
                    })
                }
                
            }
        }
        //终止
        function stopFunc() {
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var ParamVo = [];
                var tranId="";
                $(data).each(function () {
                	if(this.rowData.tran_state!=1){
                		tranId+=this.rowData.tran_id+",";
                	}
                    var rowdata = this.rowData;
                    ParamVo.push(rowdata);
                });
                
                if(tranId!=""){
                	$.etDialog.error('请选择启用状态的事务！');
                	return;
                }else{
                	ajaxPostData({
                        url: "stopFuncHrCaltrans.do?isCheck=false",
                        data: {
                            ParamVo: JSON.stringify(ParamVo)
                        },
                        success: function (res) {
                            if (res.state == "true") {
                                search();
                            }
                        }
                    })
                }
                
            }
        }

        function search() {

            var param = [];
            param.push({
                name: 'main_tab_code',
                value: $('#main_tab_code').val()
            });
            param.push({
                name: 'main_col_code',
                value: $('#main_col_code').val()
            });
     /*        param.push({
                name: 'tran_freq',
                value: tran_freq.getValue()
            }); */
            param.push({
                name: 'tran_state',
                value: tran_state.getValue()
            });
            grid.loadData(param, 'queryHrCaltrans.do?isCheck=false');
        }
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">数据表编码：</td>
                <td class="ipt">
                    <input name="main_tab_code" id="main_tab_code" style="width:180px;" type="text"></input>
                </td>
                <td class="label">数据列编码：</td>
                <td class="ipt">
                    <input name="main_col_code" id="main_col_code" style="width:180px;" type="text"></input>
                </td>
                <td class="label">事务状态：</td>
                <td class="ipt">
                    <select name="tran_state" id="tran_state" style="width:180px;"></select>
                </td>

            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>