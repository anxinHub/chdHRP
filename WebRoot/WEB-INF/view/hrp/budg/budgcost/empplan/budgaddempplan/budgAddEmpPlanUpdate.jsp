<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<style>
    body {
        overflow: hidden;
    }
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
    var grid;
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
            },
            // 合计行
            summary: {
                keyWordCol: 'dept_id',
                totalColumns: ['in_num', 'add_num']
            },
            editorEnd: function (event, ui) {
                if (ui.dataIndx === 'add_num') {
                    var data = grid.getAllData();
                    var addNumSum = 0;

                    data.forEach(function (item, index) {
                        addNumSum += item.add_num ? item.add_num : 0;
                    })
                    $("#number_input").val(addNumSum);
                }
            }
        };
        paramObj.columns = [
            { display: '需求科室', name: 'dept_name', width: '200px',
                editor: {
                    type: 'select',
                    keyField: 'dept_id',
                    url: '../../queryBudgDept.do?isCheck=false'
                }
            },
            { display: '职工类别', name: 'type_name', width: '200px',
                editor: {
                    type: 'select',
                    keyField: 'emp_type_code',
                    url: '../../queryBudgEmpType.do?isCheck=false'
                }
            },
            { display: '在岗人数', name: 'in_num', width: '150px', dataType: 'integer' },
            { display: '增员人数', name: 'add_num', width: '150px', dataType: 'integer' },
            { display: '增员原因', name: 'reason', width: '270px', dataType: 'string' },
            { display: '计划到岗日期', name: 'in_month', width: '150px', dataType: 'string' }
        ];
        paramObj.dataModel = {
            url: 'queryBudgAddEmpDetail.do?isCheck=false&plan_code=' + '${plan_code}' + '&plan_year=' + '${plan_year}'
        }
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '保存（<u>S</u>）', listeners: [{ click: save }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: removeRows }], icon: 'delete' },
                { type: 'button', label: '关闭（<u>R</u>）', listeners: [{ click: close }], icon: 'close' },
                { type: 'button', label: '添加行', listeners: [{ click: addRow }], icon: 'add' }
            ]
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function save() { //延时保存 由于grid延时数据同步
        saveNew();
    }

    //保存
    function saveNew() {
        var data = grid.getAllData();

        if (state === 1) {
            $.etDialog.error('当前数据状态为已审核,不可更改!');
            return;
        }
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
            url: "updateBudgAddEmpPlan.do?isCheck=false",
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

    //添加行
    function addRow() {
        grid.addRow();
    }
    //校验数据
    function validateGrid(data) {
        var msg = "";
        //判断grid 中的数据是否重复或者为空
        // var targetMap = new HashMap();
        var checkRepeatArr = [];

        /**
         * 校验需求科室和职工类别，不嫩刚出现重复数据
         */
        $.each(data, function(i, v) {
            var rowm = "";
            var checkRepeatObj = {};

            if (!v.dept_id) {
                rowm += "[需求科室]、";
            } else {
                checkRepeatObj.dept_id = v.dept_id;
            }
            if (!v.emp_type_code) {
                rowm += "[职工类别]、";
            } else {
                checkRepeatObj.emp_type_code = v.emp_type_code;
            }
            if (!v.add_num) {
                rowm += "[增员人数]、";
            }
            if (!v.in_month) {
                rowm += "[计划到岗日期]、";
            }
            if (rowm) {
                rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "<br>";
            }
            msg += rowm;
            checkRepeatArr.push(checkRepeatObj);
        });

        if (msg) {
            $.etDialog.warn(msg);
            return;
        }
        var isRepeat = false;
        checkRepeatArr.forEach(function (item, index) {
            for (var i = index + 1; i < checkRepeatArr.length; i++) {
                if (JSON.stringify(item) === JSON.stringify(checkRepeatArr[i])) {
                    isRepeat = true;
                }
            }
        })
        if (isRepeat) {
            $.etDialog.warn('需求科室和职工类别重复');
            return;
        }
        return true;
    }

    //删除行
    function removeRows() {
        var data = grid.selectGet();
        if (data.length === 0) {
            $.etDialog.error('请选择行');
        } else {
            grid.deleteRows(data);
        }
    }

    function close() {
        var index = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(index);
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
                <td class="label">增员人数：</td>
                <td class="ipt">
                    <input type="text" id="number_input" value="${num}" disabled />
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