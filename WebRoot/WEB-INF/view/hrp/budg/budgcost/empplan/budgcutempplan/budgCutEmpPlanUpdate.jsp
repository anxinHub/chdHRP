<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<style>
    .table-layout .detail {
        padding-left: 20px;
    }

    .table-layout .detail span {
        margin-right: 20px;
    }

    .table-layout .detail span.make_date,
    .table-layout .detail span.check_date {
        margin-right: 0;
    }
</style>
<script>
    var grid, gridManager;
    var state = '${state}';
    var formData = {};

    $(function() {
        init();
        loadGrid();
    });

    function init() {
        initMakeDate();
    }

    //加载审核日期
    function initMakeDate() {
        var make_date = '${make_date}';
        make_date = make_date.split(" ")[0];
        $(".make_date").text(make_date);

        var check_date = '${check_date}';
        check_date = check_date.split(" ")[0];
        $(".check_date").text(check_date);
    }

    //加载表格
    function loadGrid() {
        var paramObj = {
            height: '100%',
            checkbox: true,
            editable: true,
            numberCell: {
                show: true
            }
        };
        paramObj.columns = [
            { display: '科室名称', name: 'dept_name', width: '200px',
                editor: {
                    type: 'select',
                    keyField: 'dept_id',
                    url: 'queryBudgHosDept.do?isCheck=false'
                }
            },
            { display: '职工名称', name: 'emp_name', width: '200px',
                editor: {
                    type: 'select',
                    url: 'queryBudgEmpName.do?isCheck=false&dept_id=',
                    valueField: 'empId',
                    textField: 'empText',
                    create: function (rowData, cellData, setting) {
                        if (rowData.dept_id) {
                            var pram_dept_id = rowData.dept_id.split(',')[0];
                            setting.url = 'queryBudgEmpName.do?isCheck=false&dept_id=' + pram_dept_id;
                        }
                    },
                    change: function (rowData) {
                        // 异步是因为，现在change事件之后，行数据实际还未更新
                        setTimeout(function () {
                            getNum();
                        }, 100)
                    }
                },
                render: function (ui) {
                    if (ui.rowData.empId) {
                        ui.rowData.emp_id = ui.rowData.empId;
                    }
                    return ui.cellData;
                }
            },
            { display: '减员原因', name: 'reason', width: '200px', dataType: 'string' },
            { display: '减员后职工类别', name: 'type_name', width: '200px', textField: 'text',
                editor: {
                    type: 'select',
                    keyField: 'emp_type_code',
                    url: '../../queryBudgEmpType.do?isCheck=false'
                }
            },
            { display: '计划减员日期', name: 'out_month', width: '200px', dataType: 'string' }
        ]
        paramObj.dataModel = {
            url: 'queryBudgCutEmpDetail.do?isCheck=false&plan_code=' + '${plan_code}' + '&plan_year=' + '${plan_year}'
        }
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '保存（<u>S</u>）', listeners: [{ click: saveNew }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: removeRows }], icon: 'delete' },
                { type: 'button', label: '关闭（<u>R</u>）', listeners: [{ click: close }], icon: 'close' },
                { type: 'button', label: '添加行', listeners: [{ click: addRow }], icon: 'add' }
            ]
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    //提交数据
    function saveNew() {
        var data = grid.getAllData();
        if (data.length == 0) {
            $.etDialog.error('没有需要保存的数据!');
            return;
        }
        if (!validateGrid(data)) {
            return false;
        }

        formData = {
            plan_code: '${plan_code}',
            plan_year: '${plan_year}',
            plan_type: '${plan_type}',
            explain: $("#explain_input").val(),
            num: $("#number_input").val(),
            planData: JSON.stringify(data)
        }

        ajaxPostData({
            url: "updateBudgCutEmpPlan.do?isCheck=false",
            data: formData,
            success: function(responseData) {
                if (responseData.state == "true") {
                    var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.queryNew();
                }
            }
        });
    }

    //关闭窗口
    function close() {
        var index = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(index);
    }

    //获取数据条数
    function getNum() {
        var allData = grid.getAllData();
        var cutNum = 0;
        allData.forEach(function (item) {
            if (item.emp_id) {
                cutNum++;
            }
        });

        $('#number_input').val(cutNum);
    }

    //添加一行
    function addRow() {
        grid.addRow();
    }

    //校验数据
    function validateGrid(data) {
        var msg = "";
        var rowm = "";
        //判断grid 中的数据是否重复或者为空
        var targetMap = new HashMap();
        $.each(data, function(i, v) {
            rowm = "";
            if (!v.dept_id) {
                rowm += "[科室名称]、";
            }
            if (!v.emp_id) {
                rowm += "[职工名称]、";
            }
            if (!v.reason) {
                rowm += "[减员原因]、";
            }
            if (!v.out_month) {
                rowm += "[计划减员日期]、";
            }
            if (rowm) {
                rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
            }
            msg += rowm;
            var key = v.emp_id /*  + v.payment_item_id  */ ;
            var value = "第" + (i + 1) + "行";
            if (!targetMap.get(key)) {
                targetMap.put(key, value);
            } else {
                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
            }
        });
        if (msg != "") {
            $.etDialog.warn(msg);
            return false;
        } else {
            return true;
        }
    }

    //删除行
    function removeRows() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }
        grid.deleteRows(data);
        getNum();
    }
</script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="toptoolbar"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">计划单号：</td>
                <td class="ipt">
                    <input type="text" disabled value="${plan_code}" />
                </td>
                <td class="label">计划年度：</td>
                <td class="ipt">
                    <input type="text" disabled value="${plan_year}" />
                </td>
                <td class="label">计划类型：</td>
                <td class="ipt">
                    <input type="text" disabled value="${plan_type_name}" />
                </td>
            </tr>
            <tr>
                <td class="label">减员人数：</td>
                <td class="ipt">
                    <input type="text" id="number_input" disabled value="${num}" />
                </td>
                <td class="label">申报说明：</td>
                <td class="ipt" colspan="3">
                    <input type="text" style="width:480px;" id="explain_input" value="${explain}" />
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <div class="detail">
                        <span>申报人：${maker_name}</span>
                        <span>申报日期：<span class="make_date"></span></span>
                        <span>审核人：${checker_name}</span>
                        <span>审核日期：<span class="check_date"></span></span>
                        <span>状态：${state_name}</span>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>