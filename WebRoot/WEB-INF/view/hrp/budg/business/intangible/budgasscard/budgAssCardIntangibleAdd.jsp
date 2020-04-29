<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,etValidate,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var ass_id, use_state, is_dept, dept_id, store_id, is_depr, depr_method, is_throw;
    var in_date, last_depr_year, last_depr_month;
    var formValidate;

    var isUseAffiStore = '${p04044 }' == 1 ? true : false;
    $(function() {
        loadDict();
        loadHead();

        // 表单验证
        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#ass_card_no"), required: true },
                { el: $("#ass_id"), required: true },
                { el: $("#use_state"), required: true },
                { el: $("#dept_id"), required: true },
                { el: $("#last_depr_year"), required: true },
                // { el: $("#last_depr_month"), required: true },
                { el: $("#in_date"), required: true },
                { el: $("#is_depr"), required: true },
                { el: $("#depr_method"), required: true },
                { el: $("#add_depre_month"), required: true }
            ]
        });
    });

    function save () {
        var gridAllData = grid.getAllData();
        if (gridAllData.length === 0) {
            $.etDialog.error('请添加表格数据！');
        }

        var formPara = {
            // 编号
            ass_card_no: $("#ass_card_no").val(),
            // 资产id
            ass_id: ass_id.getValue(),
            // 管理部门id
            dept_id: dept_id.getValue(),
            // 使用状态
            use_state: use_state.getValue(),
            // 在用/在库
            is_dept: is_dept.getValue(),
            // 仓库id
            store_id: store_id.getValue().split(",")[0],
            // 是否投放
            is_throw: is_throw.getValue(),
            // 入库日期
            in_date: in_date.getValue(),
            // 备注
            note: $("#note").val(),
            // 是否折旧
            is_depr: is_depr.getValue(),
            // 折旧方法
            depr_method: depr_method.getValue(),
            // 计提年数
            acc_depre_amount: $("#acc_depre_amount").val(),
            // 资产原值
            price: $("#price").val(),
            // 累计折旧
            depre_money: $("#depre_money").val(),
            // 资产净值
            cur_money: $("#cur_money").val(),
            // 预留残值
            fore_money: $("#fore_money").val(),
            // 累计折旧月
            add_depre_month: $("#add_depre_month").val(),
            // 上次折旧年
            last_depr_year: last_depr_year.getValue(),
            // 上次折旧月
            last_depr_month: last_depr_month.getValue(),
            // 表格中数据
            detailData: JSON.stringify(grid.getAllData())
        };

        ajaxPostData({
            url: "addBudgInAssCard.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                $("#ass_card_no").val('');
                $("#note").val('');
                $("#acc_depre_amount").val('');
                $("#price").val('');
                $("#depre_money").val('');
                $("#cur_money").val('');
                $("#fore_money").val('');
                $("#add_depre_month").val('');

                ass_id.clearItem();
                dept_id.clearItem();
                use_state.clearItem();
                is_dept.clearItem();
                store_id.clearItem();
                is_throw.clearItem();
                is_depr.clearItem();
                depr_method.clearItem();

                in_date.clear();
                last_depr_year.clear();
                last_depr_month.clear();

                // 获取父级window对象
                var parentFrameName = parent.$.etDialog.parentFrameName;
                var parentWindow = parent.window[parentFrameName];
                parentWindow.query();
            }
        });
    }

    function loadHead() {
        var columns = [
            { display: '资金来源', name: 'source_name', width: 180,
                editor: {
                    type: 'select',
                    valueField: 'source_code',
                    textField: 'source_name',
                    url: '../../../../acc/accleder/querySourceDict.do?isCheck=false'
                },
                render: function (ui) {
                    if (ui.rowData.source_code) {
                        ui.rowData.source_id = ui.rowData.source_code;
                    }
                }
            },
            { display: '原值', name: 'price', width: 140, dataType: 'float' },
            { display: '累计折旧', name: 'depre_money', width: 140, dataType: 'float' },
            { display: '净值', name: 'cur_money', width: 140, dataType: 'float' },
            { display: '残值', name: 'fore_money', width: 140, dataType: 'float' }
        ];

        var paramObj = {
            height: '100%',
            checkbox: true,
            editable: true,
            // addRowByKey: true,
            editorEnd: function (event, ui) {
                switch (ui.dataIndx) {
                    case 'price':
                        var price_sum = grid.getTotalSummary('price');

                        $('#price').val(price_sum);
                        break;
                    case 'depre_money':
                        var depre_money_sum = grid.getTotalSummary('depre_money');
                        
                        // 累计折旧，动态验证上次折旧
                        if (depre_money_sum > 0) {
                            $('.last_depr_year_label').addClass('no-empty');

                            formValidate.openValidate($("#last_depr_year"));
                        } else {
                            $('.last_depr_year_label').removeClass('no-empty');
                            
                            formValidate.closeValidate($("#last_depr_year"));
                        }

                        $('#depre_money').val(depre_money_sum);
                        break;
                    case 'cur_money':
                        var cur_money_sum = grid.getTotalSummary('cur_money');

                        $('#cur_money').val(cur_money_sum);
                        break;
                    case 'fore_money':
                        var fore_money_sum = grid.getTotalSummary('fore_money');

                        $('#fore_money').val(fore_money_sum);
                        break;
                }
            },

            columns: columns,
            summary: {
                keyWordCol: 'source_name',
                totalColumns: ['price', 'depre_money', 'cur_money', 'fore_money']
            },
            toolbar: {
                items: [
                    { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '保存', listeners: [{ click: saveAssCardAdd }], icon: 'save' },
                    { type: 'button', label: '添加行', listeners: [{ click: addRow }], icon: 'add' },
                ]
            }
        };

        grid = $("#maingrid").etGrid(paramObj);

        grid.addRow();
    }

    function remove() {
        grid.deleteSelectedRow();
    }

    function addRow () {
        grid.addRow();
    }

    function saveAssCardAdd() {
        if (formValidate.test()) {
            save();
        }
    }

    // function saveBudgAssCard() {
    //     if ($("form").valid()) {
    //         save();
    //     }
    // }

    function loadDict() {

        // 资产名称
        ass_id = $("#ass_id").etSelect({
            url: "../../../queryAssDictInassets.do?isCheck=false",
            defaultValue: "none"
        });

        // 使用状态
        use_state = $("#use_state").etSelect({
            url: "../../../../ass/card/queryAssCardUseStateDict.do?isCheck=false",
            defaultValue: "none"
        });

        // 在用 &在库
        is_dept = $("#is_dept").etSelect({
            options: [
                { id: "1", text: "在用" },
                { id: "0", text: "在库" }
            ],
            defaultValue: "none"
        });

        // 管理部门
        dept_id = $("#dept_id").etSelect({
            url: "../../../queryBudgDeptDictAll.do?isCheck=false&is_last=1",
            defaultValue: "none"
        });

        // 仓库
        store_id = $("#store_id").etSelect({
            url: "../../../queryHosStoreDict.do?isCheck=false",
            defaultValue: "none"
        });

        // 是否折旧
        is_depr = $("#is_depr").etSelect({
            url: '../../../../mat/queryMatYearOrNo.do?isCheck=false',
            defaultValue: "none",
            onChange: function (value) {
                var $depr_method_label = $(".depr_method_label");
                var $add_depre_month_label = $(".add_depre_month_label");

                if (value == 1) {
                    $depr_method_label.addClass('no-empty');
                    $add_depre_month_label.addClass('no-empty');

                    formValidate.openValidate($("#depr_method"));
                    formValidate.openValidate($("#add_depre_month"));
                } else {
                    $depr_method_label.removeClass('no-empty');
                    $add_depre_month_label.removeClass('no-empty');

                    formValidate.closeValidate($("#depr_method"));
                    formValidate.closeValidate($("#add_depre_month"));
                }
            }
        });

        // 折旧方法
        depr_method = $("#depr_method").etSelect({
            url: '../../../../ass/card/queryAssDepreMethodDict.do?isCheck=false',
            defaultValue: "none"
        });

        // 是否投放
        is_throw = $("#is_throw").etSelect({
            url: '../../../../mat/queryMatYearOrNo.do?isCheck=false',
            defaultValue: "none"
        });

        // 入库日期
        in_date = $("#in_date").etDatepicker({
            dateFormat: "yyyy-mm-dd",
            todayButton: false,
            defaultDate: 'yyyy-mm-first_dd'
        });

        // 上次折旧年月
        last_depr_year = $("#last_depr_year").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy"
        });
        last_depr_month = $("#last_depr_month").etDatepicker({
            view: "months",
            minView: "months",
            showNav: false,
            dateFormat: "mm",
            position: "bottom right"
        });
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">卡片编号：</td>
                <td class="ipt">
                    <input id="ass_card_no" type="text" />
                </td>

                <td class="label no-empty">资产名称：</td>
                <td class="ipt">
                    <select id="ass_id" style="width: 180px;"></select>
                </td>

                <td class="label no-empty">使用状态：</td>
                <td class="ipt">
                    <select id="use_state" style="width: 180px;"></select>
                </td>

                <td class="label">在用 &在库：</td>
                <td class="ipt">
                    <select id="is_dept" style="width: 180px;"></select>
                </td>
            </tr>

            <tr>
                <td class="label no-empty">管理部门：</td>
                <td class="ipt">
                    <select id="dept_id" style="width: 180px;"></select>
                </td>

                <td class="label">仓库：</td>
                <td class="ipt">
                    <select id="store_id" style="width: 180px;"></select>
                </td>

                <td class="label no-empty">入库日期：</td>
                <td class="ipt">
                    <input id="in_date" type="text" />
                </td>

                <td class="label">备注：</td>
                <td class="ipt">
                    <input id="note" type="text"/>
                </td>
            </tr>

            <tr>
                <td class="label no-empty">是否折旧：</td>
                <td class="ipt">
                    <select id="is_depr" style="width: 180px;"></select>
                </td>

                <td class="label depr_method_label no-empty">折旧方法：</td>
                <td class="ipt">
                    <select id="depr_method" style="width: 180px;"></select>
                </td>

                <td class="label">计提年数：</td>
                <td class="ipt">
                    <input id="acc_depre_amount" type="text" />
                </td>

                <td class="label last_depr_year_label no-empty">上次折旧：</td>
                <td class="ipt">
                    <input id="last_depr_year" type="text" style="width: 80px"/>年
                    <input id="last_depr_month" type="text" style="width: 80px"/>月
                </td>
            </tr>

            <tr>
                <td class="label">资产原值：</td>
                <td class="ipt">
                    <input id="price" type="text" disabled/>
                </td>

                <td class="label">累计折旧：</td>
                <td class="ipt">
                    <input id="depre_money" type="text" disabled/>
                </td>

                <td class="label">资产净值：</td>
                <td class="ipt">
                    <input id="cur_money" type="text" disabled/>
                </td>

                <td class="label">预留残值：</td>
                <td class="ipt">
                    <input id="fore_money" type="text" disabled/>
                </td>
            </tr>

            <tr>
                <td class="label add_depre_month_label no-empty">累计折旧月份：</td>
                <td class="ipt">
                    <input id="add_depre_month" type="text"/>
                </td>

                <td class="label">是否投放：</td>
                <td class="ipt">
                    <select id="is_throw" style="width: 180px;"></select>
                </td>
            </tr>
        </table>
    </div>
            
    <div id="maingrid"></div>
</body>

</html>