<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog,etValidate" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var budg_year, apply_dept, apply_emp, payment_item_id;
    var grid;
    var formValidate;

    $(function() {
        loadDict();
        loadHead();
        loadHotkeys();

        formValidate = $.etValidate({
            config: {},
            items: [
                { el: $("#budg_year"), required: true },
                { el: $("#apply_dept"), required: true },
                { el: $("#apply_emp"), required: true },
                { el: $("#payment_item_id"), required: true }
            ]
        });
    });

    function save () {
        /* if (!formValidate.test()) {
            return;
        }
        var a1=grid.getTotalSummary('amount');
        var a2=$("#budg_amount").val();
        if(a1-a2!=0){
        	$.etDialog.error("明细金额与总金额不一致，请修改！");
        	return;
        }
        var data = grid.getAllData();
 */
 var data = grid.getAllData();
        //if (validateGrid(data)) {
          if(true){
            var formPara = {
                apply_id: $("#apply_id").val() || 0,
                budg_year: budg_year.getValue(),
                apply_dept: apply_dept.getValue().split(".")[0],
                dept_no: apply_dept.getValue().split(".")[1],
                apply_emp: apply_emp.getValue().split(".")[0],
                emp_no: apply_emp.getValue().split(".")[1],
                payment_item_id: payment_item_id.getValue().split(".")[0],
                payment_item_no: payment_item_id.getValue().split(".")[1],
                budg_amount: $("#budg_amount").val(),
                remark:$('#remark').val(),
                ParamVo: JSON.stringify(data)
            };

            ajaxPostData({
                url: "addBudgExpenseApply.do",
                data: formPara,
                success: function(responseData) {
                    $("#apply_id").val(responseData.apply_id);
                    $("#budg_amount").val(responseData.budg_amount);

                    $.etDialog.confirm(
                        '添加成功。是否继续添加单据?',
                        function (index) {
                            $("#apply_id").val("");
                           // budg_year.clear();
                            apply_dept.clearItem();
                            apply_emp.clearItem();
                            payment_item_id.clearItem();

                            // 删除所有行
                            for (var i = data.length - 1; i >= 0; i--) {
                                grid.deleteRow(i);
                            }
                            grid.addRow();

                            $.etDialog.close(index);
                        },
                        function (index) {
                            var parentFrameName = parent.$.etDialog.parentFrameName;
                            var parentWindow = parent.window[parentFrameName];
                            parentWindow.query();

                            iframe_close();
                        }
                    );
                }
            });
        }
    }

    function loadHead() {
        var columns = [
            { display: '申报月份', name: 'month_name', width: '100',
                editor: {
                    type: 'select',
                    keyField: 'month',
                    source: [
                        { "id" : "01", "label" : "1月" },
                        { "id" : "02", "label" : "2月" },
                        { "id" : "03", "label" : "3月" },
                        { "id" : "04", "label" : "4月" },
                        { "id" : "05", "label" : "5月" },
                        { "id" : "06", "label" : "6月" },
                        { "id" : "07", "label" : "7月" },
                        { "id" : "08", "label" : "8月" },
                        { "id" : "09", "label" : "9月" },
                        { "id" : "10", "label" : "10月" },
                        { "id" : "11", "label" : "11月" },
                        { "id" : "12", "label" : "12月" }
                   ],
                }
            },
            { display: '申请事由', name: 'reson', width: '300' },
            { display: '金额', name: 'amount', width: '150', dataType: 'number', align: 'right',
                editor: {
                    change: function (rowData, cellData) {
                        var amount_sum = grid.getTotalSummary('amount');

                        $("#budg_amount").val(amount_sum);
                    }
                },
                render: function(ui) {
                    return formatNumber(ui.rowData.amount, 2, 1);
                }
            }
        ];

        var paramObj = {
            height: '100%',
            checkbox: true,
            editable: true,
            summary: {
                totalColumns: ['amount'],
                keyWordCol:'month'
            },
            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                    { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '添加', listeners: [{ click: function () { grid.addRow() } }], icon: 'add' },
                    { type: 'button', label: '关闭', listeners: [{ click: iframe_close }], icon: 'close' }
                ]
            }
        };

        grid = $("#maingrid").etGrid(paramObj);

        grid.addRow();
    }

    function validateGrid(data) {
        
        if (data.length === 0) {
            $.etDialog.warn("请添加明细数据！");
            return false;
        }

        //明细
        var msg = "";
        var checkRepeatArr = [];

        $.each(data, function(i, v) {
            var rowm = "";
            if (v) {
                if (!v.month) {
                    rowm += "[申报月份]、";
                }
                if (!v.reson) {
                    rowm += "[申请事由]、";
                }
                if (!v.amount || parseFloat(v.amount) < 0) {
                    rowm += "[金额]、";
                }
                if (rowm) {
                    rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空或不能为负数" + "</br>";
                }
                msg += rowm;

                checkRepeatArr.push(v.month);
            }
        });
        
        if (msg) {
            $.etDialog.warn(msg);
            return false;
        }

        var isRepeat = false;
        checkRepeatArr.forEach(function (item, index) {
            for (var i = index + 1; i < checkRepeatArr.length; i++) {
                if (item === checkRepeatArr[i]) {
                    isRepeat = true;
                }
            }
        })
        if (isRepeat) {
            $.etDialog.warn('申报月份重复');
            return false;
        }
        
        return true;
    }

    function remove() {
        grid.deleteSelectedRows();
        var amount_sum = grid.getTotalSummary('amount');
        $("#budg_amount").val(amount_sum);
    }

    function iframe_close () {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('A', save);
        hotkeys('D', remove);
    }

    function loadDict() {
        // 预算年度
        ajaxPostData({
            url: '../../../queryBudgYear.do?isCheck=false',
            success: function (data) {
                budg_year = $("#budg_year").etDatepicker({
                	defaultDate: data[0].text,
                	view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text
                })
            }
        });
    	
    	/* ajaxPostData({
            url: '../../../../sys/queryEmpDict.do?isCheck=false',
            data:{user_id:${user_id}},
            success: function (data) {
            	console.log(data)
            }
        }) */
        
        //申报科室
        apply_dept = $("#apply_dept").etSelect({
            url: '../../../queryDept.do?isCheck=false',
            defaultValue: 'none'
        });

        //支出项目
        payment_item_id = $("#payment_item_id").etSelect({
            url: 'queryBudgPaymentItem.do?isCheck=false&is_read=1',
            defaultValue: 'none'
        });

        //申报人
        apply_emp = $("#apply_emp").etSelect({
            url: 'queryEmpDict.do?isCheck=false',
            defaultValue: 'none'
        });

        //预算金额
        $("#budg_amount").val('0');
        
        apply_emp.setValue("${emp_id}.${emp_no}");
        
        apply_dept.setValue("${dept_id}");
    }
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">费用申报单号：</td>
                <td class="ipt">
                    <input id="apply_id" type="text" disabled />
                </td>

                <td class="label no-empty">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text"/>
                </td>

                <td class="label no-empty">申报科室：</td>
                <td class="ipt">
                    <select id="apply_dept" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">申报人员：</td>
                <td class="ipt">
                    <select id="apply_emp" style="width: 180px;"></select>
                </td>

                <td class="label no-empty">支出项目：</td>
                <td class="ipt">
                    <select id="payment_item_id" style="width: 180px;"></select>
                </td>

                <td class="label no-empty">预算金额：</td>
                <td class="ipt">
                    <input id="budg_amount" type="text" />
                </td>
            </tr>
            <tr>
            <td class="label no-empty">备注：</td>
            <td class="label no-empty" colspan=5 algin=left><textarea cols=160 rows=100 id="remark">${budgExpenseApply.remark}</textarea></td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>