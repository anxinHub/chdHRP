<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var year_input, month_input, dept_name_select, wage_item_select;
    var grid;
    $(function() {
        //加载数据
        loadHead();
        init();
        // loadHotkeys();
    });

    function queryNew() {
        parms = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'wage_item_code', value: wage_item_select.getValue() }
        ]
        //加载查询条件
        grid.loadData(parms);
    }
    
    function loadHead() {
        var paramObj = {
            height: '100%',
            checkbox: true,
            numberCell: {
                show: true
            }
        };
        paramObj.columns = [
            { display: '预算年度', name: 'budg_year', width: 80 },
            { display: '月份', name: 'month', width: 80 },
            { display: '科室名称', name: 'dept_name', width: 120 },
            { display: '工资项目', name: 'wage_item_name', width: 120 },
            { display: '计算值(元)', name: 'count_value', width: 120, 
                render: function(ui) {
                    var value = ui.cellData;

                    return value ? formatNumber(value, 2, 1) : formatNumber(0, 2, 1);
                }
            },
            { display: '调整比例', name: 'adj_rate', width: 120,
                render: function(ui) {
                    var value = ui.cellData;

                    return value ? value + "%" : 0 + "%";
                }
            },
            { display: '支出预算(元)', name: 'cost_budg', width: 120,
                render: function(ui) {
                    var value = ui.cellData;

                    return value ? formatNumber(value, 2, 1) : formatNumber(0, 2, 1);
                }
            },
            { display: '说明', name: 'remark', width: 200 }
        ]
        paramObj.dataModel = {
            url: 'queryBudgWage.do?isCheck=false',
        }
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '查询', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '生成', listeners: [{ click: generate }], icon: 'add' },
                { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '批量调整', listeners: [{ click: add_open }], icon: 'update' }
            ]
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function add_open(){
        $.etDialog.open({
            url : 'budgWageAddPage.do?isCheck=false&',
            height: 300,
            width: 500,
            title:'人员工资预算调整',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgWage()
            }
        }); 
    }

    function generate() {

        var budg_year = year_input.getValue();

        $.etDialog.confirm('生成将会清空当前年度现有数据后,重新加载数据,确定生成?', function() {
            if (budg_year) {
                ajaxPostData({
                    url: "generate.do?isCheck=false&budg_year=" + budg_year,
                    data: {},
                    success: function(responseData) {
                        queryNew();
                    }
                });
            } else {
                $.etDialog.error("预算年度不能为空");
            }
        });
    }

    function remove () {

        var data = grid.selectGet();

        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }

        var ParamVo = [];

        $(data).each(function() {
            ParamVo.push(
                this.rowData.group_id + "@" +
                this.rowData.hos_id + "@" +
                this.rowData.copy_code + "@" +
                this.rowData.budg_year + "@" +
                this.rowData.month + "@" +
                this.rowData.dept_id + "@" +
                this.rowData.wage_item_code
            )
        });
        $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: "deleteBudgWage.do",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    if (responseData.state == "true") {
                        queryNew();
                    }
                }
            });
        });
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add);
        hotkeys('D', remove);
    }

    function init() {
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });


        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            todayButton: false,
            onChanged: queryNew
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../queryBudgDeptDictAll.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        wage_item_select = $("#wage_item_select").etSelect({
            url: "../../queryBudgWageItem.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" style="width:150px;" />
                </td>
                <td class="label">月份：</td>
                <td class="ipt">
                    <input type="text" id="month_input" style="width:150px;" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:150px;"></select>
                </td>
                <td class="label">工资项目：</td>
                <td class="ipt">
                    <select name="" id="wage_item_select" style="width:150px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>