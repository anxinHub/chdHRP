<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid,etValidate" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var budg_year, payment_item_id, fix_amount;
    var formValidate;
    var grid;

    $(function() {
        loadDict();
        loadHead();
        loadHotkeys();

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#payment_item_id"), required: true },
                { el: $("#fix_amount"), required: true, type: 'number' },
            ]
        });
    });

    function query () {
        var parms = [
            { name: 'budg_year', value: budg_year.getValue() },
            { name: 'payment_item_id', value: payment_item_id.getValue().split(".")[0] },
            { name: 'payment_item_no', value: payment_item_id.getValue().split(".")[1] }
        ];

        grid.loadData(parms, 'queryBudgDept.do?isCheck=false');
    }

    function loadHead() {
        var columns = [
            { display: '科室编码', name: 'dept_code', width: 160 },
            { display: '科室名称', name: 'dept_name', width: 180 },
            { display: '部门分类', name: 'type_name', width: 160 },
            { display: '部门类型', name: 'kind_name', width: 160 },
            { display: '部门性质', name: 'natur_name', width: 160 }
        ];

        var paramObj = {
            height: '100%',
            checkbox: true,


            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '保存（<u>A</u>）', listeners: [{ click: save }], icon: 'add' }
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function save() {
        if (!formValidate.test()) {
            return;
        }

        var data = grid.selectGet();

        if (data.length === 0) {
            $.etDialog.error('请选择行数据后再保存！');
            return false;
        }

        var ParamVo = [];
        var budg_year_val = budg_year.getValue();
        var payment_item_id_val = payment_item_id.getValue().split(".")[0];
        var payment_item_no_val = payment_item_id.getValue().split(".")[1];
        var fix_amount = $("#fix_amount").val();

        $(data).each(function() {
            ParamVo.push(this.rowData.dept_id + "@" + this.rowData.dept_no)
        });

        $.etDialog.confirm('确定保存所选的数据?', function(yes) {
            ajaxPostData({
                url: "addBudgExpFixAmount.do?isCheck=false",
                data: {
                    paramVo: ParamVo.toString(),
                    budg_year: budg_year_val,
                    payment_item_id: payment_item_id_val,
                    payment_item_no: payment_item_no_val,
                    fix_amount: fix_amount
                },
                success: function(responseData) {
                    query();

                    var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query();
                }
            });
        });
    }

    function loadDict () {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function (data) {
                budg_year = $("#budg_year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    defaultDate: data[0].text,
                    onChanged: query
                })
            }
        })
        payment_item_id = $("#payment_item_id").etSelect({
            url: "queryBudgPaymentItem.do?isCheck=false&is_read=1",
            defaultValue: "none",
            onChanege: query
        })
    }

    function loadHotkeys() {}
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label no-empty">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" />
                </td>
            
                <td class="label no-empty">支出项目：</td>
                <td class="ipt">
                    <select id="payment_item_id" style="width: 180px;"></select>
                </td>

                <td class="label no-empty">定额支出(元)：</td>
                <td class="ipt">
                    <input id="fix_amount" type="text" />
                </td>
            </tr>
        </table>
    </div>

    <div id="maingrid"></div>
</body>

</html>