<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,tab,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridDept;
    var etTab;
    var check_type = '${check_type}';
    var bc_state = '${bc_state}';

    $(function() {
        loadDict();
        loadHeadHos();
		loadHeadDept();

        etTab = $("#item_tab").etTab({
        	onCreated: function () {
				query('hos');
        	},
            onChanged: function(avtiveState) {
                if (avtiveState.index === 0) {
                    query('hos');
                } else {
                    query('dept');
                }
            }
        });

        //根据状态 置灰 不同按钮  
        if (bc_state == '01') {
            // $("#save").ligerButton({ click: save, width: 80, disabled: false });
            // $("#send").ligerButton({ click: send, width: 80, disabled: false });
            $("#recall").attr("disabled", true);
            $("#audit").attr("disabled", true);
            $("#unAudit").attr("disabled", true);
            $("#issuedBudg").attr("disabled", true);
            $("#unIssuedBudg").attr("disabled", true);
        } else if (bc_state == '02') {
            $("#save").attr("disabled", true);
            $("#send").attr("disabled", true);
            // $("#recall").ligerButton({ click: recall, width: 80, disabled: false });
            // $("#audit").ligerButton({ click: audit, width: 80, disabled: false });
            $("#unAudit").attr("disabled", true);
            $("#issuedBudg").attr("disabled", true);
            $("#unIssuedBudg").attr("disabled", true);
        } else if (bc_state == '03') {
            $("#save").attr("disabled", true);
            $("#send").attr("disabled", true);
            $("#recall").attr("disabled", true);
            $("#audit").attr("disabled", true);
            // $("#unAudit").ligerButton({ click: unaudit, width: 80, disabled: false });
            // $("#issuedBudg").ligerButton({ click: issuedBudg, width: 80, disabled: false });
            $("#unIssuedBudg").attr("disabled", true);
        } else if (bc_state == '04') {
            $("#save").attr("disabled", true);
            $("#send").attr("disabled", true);
            $("#recall").attr("disabled", true);
            $("#audit").attr("disabled", true);
            $("#unAudit").attr("disabled", true);
            $("#issuedBudg").attr("disabled", true);
            // $("#unIssuedBudg").ligerButton({ click: unIssuedBudg, width: 80, disabled: false });
        }
    });

    function query(type) {
        var parms = [
            { name: 'year', value: "${budg_year}" },
            { name: 'check_type', value: "${check_type}" },
            { name: 'check_code', value: "${check_code}" }
        ];

        if (type === 'hos') {
            var url;
            if (check_type == '02') {
                if (bc_state == '04') {
                    url = "queryBudgMedCostHosMonthAdjustCopy.do?isCheck=false";
                } else {
                    url = "queryBudgMedCostHosMonthAdjust.do?isCheck=false";
                }
            } else {
                if (bc_state == '04') {
                    url = "queryBudgMedCostHosMonthCopy.do?isCheck=false";
                } else {
                    url = "queryBudgMedCostHosMonth.do?isCheck=false";
                }
            }

            grid.loadData(parms, url);

        } else if (type === 'dept') {

            var url;
            if (check_type == '02') {
                if (bc_state == '04') {
                    url = "queryBudgMedCostCheckDeptMonthAdjustCopy.do?isCheck=false";
                } else {
                    url = "queryBudgMedCostCheckDeptMonthAdjust.do?isCheck=false";
                }
            } else {
                if (bc_state == '04') {
                    url = "queryBudgMedCostDeptMonthCopy.do?isCheck=false";
                } else {
                    url = "queryBudgMedCostDeptMonth.do?isCheck=false";
                }
            }
            gridDept.loadData(parms, url);
        }
    }

    function loadHeadHos() {
        var columns = [
            { display: '科目编码', name: 'subj_code', width: 100 },
            { display: '科目名称', name: 'subj_name', width: 100 },
            {
                display: '本年合计',
                name: 'year_sum',
                align: 'right',
                width: 120,
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
            hoverShowTitle: showAdjustPreData,

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
            {
                display: '本年合计',
                name: 'year_sum',
                align: 'right',
                width: 120,
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
            hoverShowTitle: showAdjustPreData,

            columns: columns
        };
        gridDept = $("#maingridDept").etGrid(paramObj);
    }

    // 调整前数据浮现
    function showAdjustPreData(ui) {
        if (check_type == '02') {
            var data = ui.rowData;
            return {
                year_sum: "调整前:" + formatNumber(data.year_sumPre, 2, 1),
                month1: "调整前:" + formatNumber(data.month1Pre, 2, 1),
                month2: "调整前:" + formatNumber(data.month2Pre, 2, 1),
                month3: "调整前:" + formatNumber(data.month3Pre, 2, 1),
                month4: "调整前:" + formatNumber(data.month4Pre, 2, 1),
                month5: "调整前:" + formatNumber(data.month5Pre, 2, 1),
                month6: "调整前:" + formatNumber(data.month6Pre, 2, 1),
                month7: "调整前:" + formatNumber(data.month7Pre, 2, 1),
                month8: "调整前:" + formatNumber(data.month8Pre, 2, 1),
                month9: "调整前:" + formatNumber(data.month9Pre, 2, 1),
                month10: "调整前:" + formatNumber(data.month10Pre, 2, 1),
                month11: "调整前:" + formatNumber(data.month11Pre, 2, 1),
                month12: "调整前:" + formatNumber(data.month12Pre, 2, 1)
            }
        } else {
            return false;
        }
    }

    function save() {
        var formPara = {
            budg_year: "${budg_year}",
            check_code: "${check_code}",
            check_type: "${check_type}",
            adjust_code: "${adjust_code}",
            remark: $("#remark").val(),
            bc_state: "${bc_state}"
        };

        ajaxPostData({
            url: "updateBudgMedCostCheck.do",
            data: formPara,
            success: function(responseData) {
                parent_query();
                //frameElement.dialog.close();
            }
        });
    }

    //发送
    function send() {
        var ParamVo = [];
        if (bc_state == '01') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '02'
            )
        } else {
            $.etDialog.error("发送失败！该单据单据不是新建状态不允许发送！");
            return false;
        }

        $.etDialog.confirm('确定发送吗?', function() {
            ajaxPostData({
                url: "sendOrRecall.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //召回
    function recall() {
        var ParamVo = [];
        if (bc_state == '02') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '01'
            )

        } else {
            $.etDialog.error("召回失败！该单据单据不是已发送状态不允许召回！");
            return false;
        }
        $.etDialog.confirm('确定召回吗?', function(yes) {
            ajaxPostData({
                url: "sendOrRecall.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //审核
    function audit() {
        var ParamVo = [];
        if (bc_state == '02') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '03'
            )
        } else {
            $.etDialog.error("审核失败！该单据单据不是已发送状态不允许审核！");
            return false;
        }

        $.etDialog.confirm('确定审核?', function() {
            ajaxPostData({
                url: "auditOrUnAudit.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //销审
    function unaudit() {
        var ParamVo = [];
        if (bc_state == '03') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '02'
            )
        } else {
            $.etDialog.error("销审失败！该单据单据不是已审核状态不允许销审！");

            return false;
        }
        $.etDialog.confirm('确定销审?', function(yes) {
            ajaxPostData({
                url: "auditOrUnAudit.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //预算下达
    function issuedBudg() {
        var ParamVo = [];
        if (bc_state == '03') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '04'
            )
        } else {
            $.etDialog.error("预算下达失败！该单据单据不是已审核状态不允许预算下达！");
            return false;
        }
        $.etDialog.confirm('确定预算下达吗?', function(yes) {
            ajaxPostData({
                url: "cancelIssueOrIssue.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //取消下达
    function unIssuedBudg() {
        var ParamVo = [];
        if (bc_state == '04') {
            ParamVo.push(
                '${budg_year}' + "@" +
                '${check_code}' + "@" +
                '03'
            )
        } else {
            $.etDialog.error("取消下达失败！该单据单据不是已下达状态不允许取消下达！");
            return false;
        }
        $.etDialog.confirm('确定取消下达吗?', function(yes) {
            ajaxPostData({
                url: "cancelIssueOrIssue.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    parent_query();
                    location.reload();
                }
            });
        });
    }
    //打印
    function PrintDate() {}

    // 关闭当前页面
    function close_page() {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }
    function parent_query () {
    	var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.window[parentFrameName];
        parentWindow.query();
    }

    function loadDict() {
		var check_type = $("#check_type").etSelect({
			url: "../../queryBudgCheckType.do?isCheck=false",
            defaultValue: '${check_type}'
        });

        var bc_state = $("#bc_state").etSelect({
        	url: "../../queryBudgBcState.do?isCheck=false",
            defaultValue: '${bc_state}'
        });

        $("#save").on("click", save);
        $("#send").on("click", send);
        $("#recall").on("click", recall);
        $("#audit").on("click", audit);
        $("#unAudit").on("click", unaudit);
        $("#issuedBudg").on("click", issuedBudg);
        $("#unIssuedBudg").on("click", unIssuedBudg);
        $("#printDate").on("click", printDate);
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
                    <input id="budg_year" type="text" disabled value="${budg_year}" />
                </td>
                <td class="label">审批单号：</td>
                <td class="ipt">
                    <input id="check_code" type="text" disabled value="${check_code}" />
                </td>
                <td class="label">审批类型：</td>
                <td class="ipt">
                	<select id="check_type" style="width: 180px;" disabled></select>
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                	<select id="bc_state" style="width: 180px;" disabled></select>
                </td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="remark" type="text" value="${remark}" />
                </td>
            </tr>
        </table>
    </div>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">制单人：</td>
                <td class="ipt">${maker_name}</td>
                <td class="label">制单日期：</td>
                <td class="ipt">${make_data}</td>
                <td class="label">审核人：</td>
                <td class="ipt">${check_name}</td>
                <td class="label">审核日期：</td>
                <td class="ipt">${check_data}</td>
                <td class="label">下达日期：</td>
                <td class="ipt">${issue_data}</td>
            </tr>
        </table>
    </div>
    <div class="button-group">
        <button id="save">保存</button>
        <button id="send">发送</button>
        <button id="recall">召回</button>
        <button id="audit">审核</button>
        <button id="unAudit">销审</button>
        <button id="issuedBudg">预算下达</button>
        <button id="unIssuedBudg">取消下达</button>
        <button id="printDate">打印</button>
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