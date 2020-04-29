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
    var date_input, plan_type_select, state_select;
    var grid, gridManager;
    $(function() {
        init();
        loadGrid();
        // loadHotkeys();
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
                    onSelect: function() {
                        setTimeout(queryNew, 10);
                    }
                });
            }
        });

        plan_type_select = $("#plan_type_select").etSelect({
            url: "../../queryBudgPlanType.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        state_select = $("#state_select").etSelect({
            url: "../../queryBudgState.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });
    }

    //查询
    function queryNew() {
        var parms = [
            { name: 'plan_year', value: date_input.getValue() },
            { name: 'plan_type', value: plan_type_select.getValue() },
            { name: 'state', value: state_select.getValue() }
        ]
        grid.loadData(parms, "queryBudgCutEmpPlan.do");
    }

    function loadGrid() {
        var paramObj = {
            height: '100%',
            checkbox: true,
            numberCell: {
                show: true
            },
            rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                var openParam = [rowData.group_id, rowData.hos_id, rowData.copy_code, rowData.plan_code, rowData.plan_year];

                openParam = openParam.join('|');
                openUpdate(openParam);
            } 
        };
        paramObj.columns = [
            {
                display: '计划单号',
                name: 'plan_code',
                width: '150',
                render: function(ui) {
                    var rowData = ui.rowData;
                    var openParam = [rowData.group_id, rowData.hos_id, rowData.copy_code, rowData.plan_code, rowData.plan_year, rowData.plan_code];

                    openParam = openParam.join('|');
                    return "<a href=javascript:openUpdate('" + openParam + "')>" + rowData.plan_code + "</a>";
                }
            },
            { display: '计划年度', name: 'plan_year', width: '100' },
            { display: '计划类型', name: 'plan_type_name', width: '105' },
            { display: '减员人数', name: 'num', width: '100' },
            { display: '申报说明', name: 'explain', width: '180' },
            { display: '申报人', name: 'maker_name', width: '105' },
            { display: '申报日期', name: 'make_date', width: '105' },
            { display: '审核人', name: 'checker_name', width: '105' },
            { display: '审核日期', name: 'check_date', width: '105' },
            { display: '状态', name: 'state_name', maxWidth: '105' }
        ];
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '审核（<u>R</u>）', listeners: [{ click: review }], icon: 'bluebook' },
                { type: 'button', label: '销审（<u>R</u>）', listeners: [{ click: cancel }], icon: 'outbox' }
            ]
        };

        grid = $("#maingrid").etGrid(paramObj);
    }

    function add_open() {
        parent.$.etDialog.open({
            url: 'hrp/budg/budgcost/empplan/budgCutEmpPlanAddPage.do?isCheck=false',
            title: '减员计划添加明细',
            isMax: true,
            frameName: window.name
        });
    }

    function remove() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];

            data.forEach(function (item) {
                var paramItem = [
                    item.rowData.group_id,
                    item.rowData.hos_id,
                    item.rowData.copy_code,
                    item.rowData.plan_code,
                    item.rowData.plan_year,
                ]
                paramItem = paramItem.join('@');

                ParamVo.push(paramItem)
            })
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgCutEmpPlan.do",
                    data: { ParamVo: ParamVo.toString() },
                    success: function (responseData) {
                        queryNew();
                    }
                });
            });
        }
    }

    function openUpdate(obj) {
        var vo = obj.split("|");
        var parm =
            "group_id=" + vo[0] + "&" +
            "hos_id=" + vo[1] + "&" +
            "copy_code=" + vo[2] + "&" +
            "plan_code=" + vo[3] + "&" +
            "plan_year=" + vo[4];

        parent.$.etDialog.open({
            url: 'hrp/budg/budgcost/empplan/budgCutEmpPlanUpdatePage.do?isCheck=false&' + parm,
            title: '减员计划修改明细',
            isMax: true,
            frameName: window.name
        });
    }

    //审核
    function review() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }
        var ParamVo = [];

        data.forEach(function (item) {
            var paramItem = [
                item.rowData.group_id,
                item.rowData.hos_id,
                item.rowData.copy_code,
                item.rowData.plan_code,
                item.rowData.plan_year
            ];
            paramItem = paramItem.join('@');
            ParamVo.push(paramItem);
        })
        $.etDialog.confirm('确定审核?', function() {
            ajaxPostData({
                url: "reviewbudgCutEmpPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    queryNew();
                }
            });
        });
    }

    //销审
    function cancel() {
        var data = grid.selectGet();
        if (data.length === 0) {
            $.etDialog.error('请选择行');
            return false;
        }
        var ParamVo = [];

        data.forEach(function (item) {
            var paramItem = [
                item.rowData.group_id,
                item.rowData.hos_id,
                item.rowData.copy_code,
                item.rowData.plan_code,
                item.rowData.plan_year
            ];
            paramItem = paramItem.join('@');
            ParamVo.push(paramItem);
        })
        $.etDialog.confirm('确定销审?', function() {
            ajaxPostData({
                url: "cancelbudgCutEmpPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    queryNew();
                }
            });
        });
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add_open);
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
                <td class="label">计划年度：</td>
                <td class="ipt">
                    <input type="text" id="date_input" />
                </td>
                <td class="label">计划类型：</td>
                <td class="ipt">
                    <select name="" id="plan_type_select" style="width:180px;"></select>
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select name="" id="state_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>