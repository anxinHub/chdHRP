<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,tab" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var subj_code, subj_level, dept_id;
    var grid;
    var gridDept;
    var etTab;
    var renderFunc = {
        year_sum: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month1: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month2: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month3: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month4: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month5: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month6: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month7: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month8: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month9: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month10: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month11: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month12: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        }
    };

    $(function() {
        loadDict();
        
        loadHeadHos();
        loadHeadDept();

        query();

        etTab = $("#item_tab").etTab({
            onChanged: function(activeState) {
                if (activeState.index === 0) {
                    $(".input_1").hide();
                    query();
                } else if (activeState.index === 1) {
                    $(".input_1").show();
                    queryDept();
                }
            }
        });

        $(".input_1").hide(); //设置首次加载页面的时候不容许加载下拉框
    });
    //查询
    function query() {
        var parms = [
            { name: 'year', value: "${year}" },
            { name: 'subj_code', value: subj_code.getValue() },
            { name: 'subj_level', value: subj_level.getValue() },
            { name: 'check_code', value: "${check_code}" }
        ];
        grid.loadData(parms, "../medcostcheck/queryBudgMedCostHosMonthCopy.do?isCheck=false");
    }
    //查询
    function queryDept() {
        var parms = [
            { name: 'year', value: "${year}" },
            { name: 'subj_code', value: subj_code.getValue() },
            { name: 'subj_level', value: subj_level.getValue() },
            { name: 'dept_id', value: dept_id.getValue() },
            { name: 'check_code', value: "${check_code}" }
        ];
        gridDept.loadData(parms, "../medcostcheck/queryBudgMedCostDeptMonthCopy.do?isCheck=false");
    }

    function loadHeadHos() {
        var columns = [
            { display: '年度', name: 'year', width: 80 },
            { display: '科目编码', name: 'subj_code', width: 100 },
            { display: '科目名称', name: 'subj_name', width: 120 },
            { display: '本年合计', name: 'year_sum', align: 'right', width: 120,
                render:function(ui) {
                    return formatNumber(ui.rowData.year_sum, 2, 1) || '';
                }

            }
        ];

        for (var i = 1; i < 13; i++) {
            columns.push({
                display: i + '月',
                name: 'month' + i,
                align: 'right',
                width: 120,
                render:function(ui) {
                    if (ui.cellData) {
                        return formatNumber(ui.cellData, 2, 1);
                    }
                }
            })
        }

        var paramObj = {
            inWindowHeight: true,
            height: '100%',
            checkbox: true,
            freezeCols: 4,
            
            columns: columns,
            numberCell: {
                show: true,
                resizable: false,
                width: 30,
                minWidth: 30
            },
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                ]
            }
        };

        grid = $("#maingrid").etGrid(paramObj);
    }

    function loadHeadDept() {
        var columns = [
            { display: '年度', name: 'year', width: 80 },
            { display: '科室编码', name: 'dept_code', width: 100 },
            { display: '科室名称', name: 'dept_name', width: 120 },
            { display: '科目编码', name: 'subj_code', width: 100 },
            { display: '科目名称', name: 'subj_name', width: 120 },
            { display: '本年合计', name: 'year_sum', align: 'right', width: 120,
                render:function(ui) {
                    return formatNumber(ui.rowData.year_sum, 2, 1) || "";
                }

            }
        ];

        for (var i = 1; i < 13; i++) {
            columns.push({
                display: i + '月',
                name: 'month' + i,
                align: 'right',
                width: 120,
                render:function(ui) {
                    if (ui.cellData) {
                        return formatNumber(ui.cellData, 2, 1);
                    }
                }
            })
        }

        var paramObj = {
            inWindowHeight: true,
            height: '100%',
            checkbox: true,
            freezeCols: 6,

            columns: columns,
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: queryDept }] },
                ]
            }
        }

        gridDept = $("#maingrid1").etGrid(paramObj);
    }
    //打印回调方法
    function lodopPrint() {
        if (etTab.activeState.index === 0) {
            var head = "";
            grid.options.lodop.head = head;
            grid.options.lodop.fn = renderFunc;
            grid.options.lodop.title = "调整前预算查看(医院)";
        } else if (etTab.activeState.index === 1) {
            var head = "";
            gridDept.options.lodop.head = head;
            gridDept.options.lodop.fn = renderFunc;
            gridDept.options.lodop.title = "调整前预算查看(科室)";
        }

    }

    function loadDict() {
        subj_code = $("#subj_code").etSelect({
            url: "../../queryBudgSubj.do?isCheck=false&subj_type=" + '04' + "&budg_year=" + "${year}",
            defaultValue: "none",
            onChange: function () {
                if (etTab.activeState.index === 0) {
                    query();
                } else if (etTab.activeState.index === 1) {
                    queryDept();
                }
            }
        });

        subj_level = $("#subj_level").etSelect({
            url: "../../queryBudgSubjLevel.do?isCheck=false",
            defaultValue: "none",
            onChange: function () {
                if (etTab.activeState.index === 0) {
                    query();
                } else if (etTab.activeState.index === 1) {
                    queryDept();
                }
            }
        });

        dept_id = $("#dept_id").etSelect({
            url: "../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: function () {
                if (etTab.activeState.index === 0) {
                    query();
                } else if (etTab.activeState.index === 1) {
                    queryDept();
                }
            }
        });
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" disabled value="${year}" />
                </td>

                <td class="label">科目名称：</td>
                <td class="ipt">
                    <select id="subj_code" style="width: 180px;"></select>
                </td>

                <td class="label">科目级次：</td>
                <td class="ipt">
                    <select id="subj_level" style="width: 180px;"></select>
                </td>

                <td class="label input_1">科室名称：</td>
                <td class="ipt input_1">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>
            </tr>
        </table>
    </div>

    <div id="item_tab">
        <div id="hosItem" title="医院">
            <div id="maingrid"></div>
        </div>
        <div id="deptItem" title="科室">
            <div id="maingrid1"></div>
        </div>
    </div>
</body>

</html>