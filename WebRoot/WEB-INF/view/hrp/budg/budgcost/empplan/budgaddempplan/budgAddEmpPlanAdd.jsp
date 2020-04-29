<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<style>
    body {
        overflow: hidden;
    }
</style>
<script>
    var date_input, plan_type_select;
    var grid, gridManager;
    var formData = {};

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
                    todayButton: false
                });
            }
        });

        plan_type_select = $("#plan_type_select").etSelect({
            url: "../../queryBudgPlanType.do?isCheck=false",
            defaultValue: "none"
        });
    }

    //表格加载
    function loadGrid() {
        var paramObj = {
            height: '100%',
            editable: true,
            checkbox: true,
            numberCell: {
                show: true
            },
            // 合计行
            summary: {
                keyWordCol: 'dept_id',
                totalColumns: ['in_num', 'add_num' ]
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
            { display: '需求科室', name: 'dept_text', width: '200px',
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
            { display: '增员原因', name: 'reason', width: '350px', dataType: 'string' },
            { display: '计划到岗日期', name: 'in_month', width: '150px', dataType: 'string' }
        ]
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '保存（<u>S</u>）', listeners: [ { click: saveBudgAddEmpPlan } ], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [ { click: removeRows } ], icon: 'delete' },
                { type: 'button', label: '关闭（<u>R</u>）', listeners: [ { click: close } ], icon: 'close' },
                { type: 'button', label: '添加行', listeners: [ { click: addRow } ], icon: 'add' }
            ]
        }
        grid = $("#maingrid").etGrid(paramObj);

        grid.addRow();
    }

    //验证提交数据
    function validateForm() {
        var requireEmpty = true;
        var data = grid.getAllData();

        if (data.length === 0) {
            $.etDialog.error('没有需要保存的数据!');
            return;
        }

        formData = {
            plan_code: $("#plan_code_input").val(),
            plan_year: date_input.getValue(),
            plan_type: plan_type_select.getValue(),
            explain: $("#explain_input").val(),
            num: $("#number_input").val(),
            planData: JSON.stringify(data)
        };

        if (formData.plan_year == "") {
            requireEmpty = false;
        } else if (formData.plan_type == "") {
            requireEmpty = false;
        }
        if (!requireEmpty) {
            $.etDialog.warn("必填项不能为空");
            return;
        }
        if (!validateGrid(data)) {
            return;
        }

        return true;
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

    //保存
    function saveBudgAddEmpPlan() {
        if (validateForm()) {
            saveNew();
        }
    }

    //保存
    function saveNew() {
        ajaxPostData({
            url: "addBudgAddEmpPlan.do?isCheck=false",
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

    function addRow () {
        grid.addRow();
    }

    //删除行
    function removeRows () {
        var data = grid.selectGet();
        if (data.length === 0) {
            $.etDialog.error('请选择行');
        } else {
            grid.deleteRows(data);
        }
    }

    function close () {
        var index = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(index);
    }
</script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">计划单号：</td>
                <td class="ipt">
                    <input type="text" id="plan_code_input" disabled value="系统生成" />
                </td>
                <td class="label no-empty">计划年度：</td>
                <td class="ipt">
                    <input type="text" id="date_input" />
                </td>
                <td class="label no-empty">计划类型：</td>
                <td class="ipt">
                    <select name="" id="plan_type_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">增员人数：</td>
                <td class="ipt">
                    <input type="text" id="number_input" disabled value="0" />
                </td>
                <td class="label">申报说明：</td>
                <td class="ipt" colspan="3">
                    <input type="text" id="explain_input" style="width:480px" />
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>