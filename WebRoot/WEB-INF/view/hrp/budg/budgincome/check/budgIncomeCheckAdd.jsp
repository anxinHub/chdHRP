<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid,etValidate,tab" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var budg_year, check_type, bc_state;
    var grid;
    var gridDept;
    var formValidate;
    var etTab;

    $(function() {
        loadDict();
        loadHeadHos();
        loadHeadDept();

        etTab = $("#item_tab").etTab({
            onChanged: function (avtiveState) {
                if (avtiveState.index === 0) {
                    query('hos');
                } else {
                    query('dept');
                }
            }
        });

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#check_type"), required: true },
            ]
        });
    });

    //查询
    function query (type) {
        if (!budg_year.getValue()) {
            $.etDialog.error('预算年度不能为空');
            return false;
        }

        if (!check_type.getValue()) {
            $.etDialog.error('审批类型不能为空');
            return false;
        }
        var parms = [
            { name: 'year', value: budg_year.getValue() },
            { name: 'check_type', value: check_type.getValue() }
        ];
        if (type === 'hos') {
            grid.loadData(parms, "queryBudgMedIncomeHosMonth.do?isCheck=false");
        } else if (type === 'dept') {
            gridDept.loadData(parms, "queryBudgMedIncomeDeptMonth.do?isCheck=false");
        }
    }

    function loadHeadHos() {
        var columns = [
            { display: '科目编码', name: 'subj_code', width: 100 },
            { display: '科目名称', name: 'subj_name', width: 100 },
            { display: '本年合计', name: 'year_sum', align: 'right', width: 120,
                render:function(ui) {
                    if (ui.rowData.year_sum) {
                        return formatNumber(ui.rowData.year_sum, 2, 1);
                    }
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
            height: '100%',
            checkbox: true,
            inWindowHeight: true,
            freezeCols: 2,
            columns: columns
        };

        grid = $("#maingridHos").etGrid(paramObj);
    }

    function loadHeadDept() {
        var columns = [
            { display: '指标编码', name: 'subj_code', width: 100 },
            { display: '指标名称', name: 'subj_name', width: 100 },
            { display: '科室名称', name: 'dept_code', width: 100 },
            { display: '科室名称', name: 'dept_name', width: 100 },
            { display: '本年合计', name: 'year_sum', align: 'right', width: 120,
                render:function(ui) {
                    if (ui.cellData) {
                        return formatNumber(ui.cellData, 2, 1);
                    }
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
            height: '100%',
            checkbox: true,
            inWindowHeight: true,
            freezeCols: 4,

            columns: columns
        };
        gridDept = $("#maingridDept").etGrid(paramObj);
    }



    function save() {
        if (!formValidate.test()) {
            return;
        }

        var formPara = {
            budg_year: budg_year.getValue(),
            check_code: $("#check_code").val(),
            check_type: check_type.getValue(),
            adjust_code: $("#adjust_code").val(),
            remark: $("#remark").val(),
            bc_state: bc_state.getValue()
        };

        ajaxPostData({
            url: "addBudgIncomeCheck.do",
            data: formPara,
            success: function(responseData) {
                var parentFrameName = parent.$.etDialog.parentFrameName;
                var parentWindow = parent.window[parentFrameName];
                parentWindow.query();

                close_page();
            },
            delayCallback: true
        });
    }

    //关闭当前页面
    function close_page() {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }

    function loadDict() {
        bc_state = $("#bc_state").etSelect({
            url: "../../queryBudgBcState.do?isCheck=false",
            defaultValue: "01"
        });
        
        
        /**
         * 这里声明了一个wait等待方法，并放到when中执行
         * 里面Deferred是 jq的延迟对象 。resolve表示成功。并可执行done
         */
        var wait_check_type = function(dtd) {
            var dtd = $.Deferred();

            check_type = $("#check_type").etSelect({
                url: "../../queryBudgCheckType.do?isCheck=false",
                onInit: function () {
                    dtd.resolve();
                },
                onChange: function () {
                    if (etTab.activeState.index === 0) {
                        query('hos');
                    } else {
                        query('dept');
                    }
                }
            });
	    　　　　return dtd.promise();
	    　　};

        $.when(wait_check_type())
        .done(function() {
            ajaxPostData({
                url: "../../queryBudgYear.do?isCheck=false",
                success: function (data) {
                    budg_year = $("#budg_year").etDatepicker({
                        view: "years",
                        minView: "years",
                        dateFormat: "yyyy",
                       /*  minDate: data[data.length - 1].text,
                        maxDate: data[0].text, */
                        defaultDate: data[0].text,
                        onChanged: function () {
                            if (etTab.activeState.index === 0) {
                                query('hos');
                            } else {
                                query('dept');
                            }
                        }
                    })
                }
            })
        })

        $("#save").on("click", save);
        $("#close").on("click", close_page);
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" />
                </td>

                <td class="label">审批单号：</td>
                <td class="ipt">
                    <input id="check_code" type="text" disabled value="系统生成" />
                </td>

                <td class="label">审批类型：</td>
                <td class="ipt">
                    <select id="check_type" style="width: 180px;"></select>
                </td>

                <td class="label">调整单号：</td>
                <td class="ipt">
                    <input id="adjust_code" type="text" disabled />
                </td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="remark" type="text" />
                </td>

                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="bc_state" style="width: 180px;" disabled></select>
                </td>
            </tr>
        </table>
    </div>
    <div class="button-group">
        <button id="save">保存</button>
        <button id="close">关闭</button>
    </div>

    <div id="item_tab">
        <div id="hosItem" title="医院">
            <div id="maingridHos"></div>
        </div>
        <div id="deptItem" title="科室">
            <div id="maingridDept"></div>
        </div>
    </div>
</body>

</html>