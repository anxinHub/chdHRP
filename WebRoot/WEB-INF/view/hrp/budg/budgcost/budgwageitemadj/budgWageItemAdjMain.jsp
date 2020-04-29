<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/static_resource.jsp">
        <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
    </jsp:include>
    <script>
    var date_input, emp_type_select, wage_item_select;
    var grid, gridManager;

    $(function() {
        init();
        loadGrid();
    });

    function init() {
        ajaxPostData({
            url: "../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                date_input = $("#date_input").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged: queryNew
                });
            }
        });

        emp_type_select = $("#emp_type_select").etSelect({
            url: "../../queryBudgEmpType.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        wage_item_select = $("#wage_item_select").etSelect({
            url: "../../queryBudgWageItem.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });
    }

    function loadGrid() {
        var paramsObj = {
            height: '100%',
            checkbox: true,
            editable: true
        };
        paramsObj.columns = [
            { display: '预算年度', name: 'budg_year', width: 100, editable: false },
            { display: '工资项目编码', name: 'wage_item_name', width: 180, editable: false },
            { display: '职工类别编码', name: 'type_name', width: 200, editable: false },
            {
                display: '调整金额(E)',
                name: 'adj_amount',
                width: 120,
                dataType: 'float',
                editable: function (ui) {
                    return ui.rowData.adj_rate ? false : true;
                },
                render: function(ui) {
                    var value = ui.cellData;

                    return value ? formatNumber(value, 2, 1) : '';
                }
            },
            {
                display: '调整比例(E)',
                name: 'adj_rate',
                width: 120,
                dataType: 'float',
                editable: function (ui) {
                    return ui.rowData.adj_amount ? false : true;
                },
                render: function(ui) {
                    var value = ui.cellData;

                    return value ? value + "%" : '';
                }
            },
            { display: '计划调整月份(E)', name: 'adj_month', width: 120, dataType: 'string' },
            { display: '说明(E)', name: 'remark', width: 290, dataType: 'string' }
        ];
        // paramsObj.dataModel = {
        //     url: 'queryBudgWageItemAdj.do'
        // }
        paramsObj.toolbar = {
            items: [
                { type: 'button', label: '查询', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                { type: 'button', label: '批量设置', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
            ]
        }
        // onToFirst: updateDateExist, //翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
        // onToPrev: updateDateExist, //翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
        // onToNext: updateDateExist, //翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
        // onToLast: updateDateExist, //翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）

        grid = $("#maingrid").etGrid(paramsObj);
    }

    function queryNew() {
        parms = [
            { name: 'budg_year', value: date_input.getValue() },
            { name: 'emp_type_code', value: emp_type_select.getValue() },
            { name: 'wage_item_code', value: wage_item_select.getValue() }
        ];
        grid.loadData(parms, "queryBudgWageItemAdj.do");
    }

    //翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
    function updateDateExist() {
        var data = grid.changedCells;
        if (!isObjEmpty(data)) {
            $.etDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面确定后再操作)</span>】');
            return false;
        }

        function isObjEmpty(obj) { //新注册的方法判断对象是否为空对象
            for (var i in obj) {
                return false;
            }
            return true;
        }
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgWageItemAdjAddPage.do?isCheck=false&',
            height: 400,
            width: 700,
            title: '工资变动',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgWageItemAdj();
                queryNew();
            }
        });
    }

    //保存
    function save() {
        var data = grid.getAllData();
        if (data.length == 0) {
            $.etDialog.error('请添加行数据');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.budg_year + "@" +
                    this.wage_item_code + "@" +
                    this.emp_type_code + "@" +
                    (this.adj_amount ? this.adj_amount : "") + "@" +
                    (this.adj_rate ? this.adj_rate : "") + "@" +
                    this.adj_month + "@" +
                    (this.remark ? this.remark : "")
                )
            });

            if (!ParamVo) {
                $.etDialog.error('没有需要保存的数据,请确认添加数据后操作!');
            }
            ajaxPostData({
                url: "updateBudgWageItemAdj.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    if (responseData.state == "true") {
                        queryNew();
                    }
                }
            });
        }
    }

    //删除
    function remove() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.emp_type_code + "@" +
                    this.rowData.wage_item_code
                )
            });
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgWageItemAdj.do",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        if (responseData.state == "true") {
                            queryNew();
                        }
                    }
                });
            });
        }
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add);
        hotkeys('D', remove);
    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="toptoolbar"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input type="text" id="date_input" />
                </td>
                <td class="label">职工类别：</td>
                <td class="ipt">
                    <select name="" id="emp_type_select" style="width:180px;"></select>
                </td>
                <td class="label">工资项目：</td>
                <td class="ipt">
                    <select name="" id="wage_item_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>