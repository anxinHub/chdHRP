<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
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
<script type="text/javascript">
    var date_year, date_month, dept_name_select, emp_type_select, emp_name_select;
    var grid;
    var columnArray = [
        { display: '年度', name: 'year', width: 80 },
        { display: '月份', name: 'month', width: 50 },
        { display: '科室编码', name: 'dept_code', width: 100 },
        { display: '科室名称', name: 'dept_name', width: 100 },
        { display: '职工类别', name: 'type_name', width: 100 },
        { display: '职工名称', name: 'emp_name', minWidth: 100 }
    ];
    $(function() {
        init();
        loadGrid();
        // loadHotkeys();
    });

    function init() {

        //加载年
        ajaxPostData({
            url: "../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                date_year = $("#year_ipt").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged: function(value) {
                        queryNew();
                    }
                });
            }
        });

        //加载月
        date_month = $("#month_ipt").etDatepicker({
            view: "months",
            maxView: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            todayButton: false,
            onSelect: queryNew
        })

        //科室名称
        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../queryBudgHosDept.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        //职工类别
        emp_type_select = $("#emp_type_select").etSelect({
            url: "../../queryBudgEmpType.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        //职工名称
        emp_name_select = $("#emp_name_select").etSelect({
            url: "../../queryBudgEmpName.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew,
            load: function(value, callback) {
                $.ajax({
                    url: "../../queryBudgEmpName.do?isCheck=false",
                    type: "POST",
                    dataType: "JSON",
                    data: { key: value },
                    success: function(res) {
                        callback(res);
                    }
                });
            }
        });
    }

    //查询
    function queryNew() {
        // 首先动态加载列头，然后query
        loadGridColumn(date_year.getValue());

        var parm = [
            { name: 'year', value: date_year.getValue() },
            { name: 'month', value: date_month.getValue() },
            { name: 'dept_id', value: dept_name_select.val() },
            { name: 'emp_id', value: emp_name_select.val() },
            { name: 'emp_type_code', value: emp_type_select.val() }
        ]
        grid.loadData(parm, 'queryBudgEmpWageTotal.do');
    }


    //加载表
    function loadGrid() {
        var gridParam = {
            height: "100%",
            numberCell: {
                show: true
            },
            checkbox: true,
            freezeCols: 6
        }
        gridParam.columns = columnArray;
        gridParam.dataModel = {
            location: "remote",
            recIndx: "dept_code",
            url: ''
        };
        gridParam.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                {type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
            ]
        }

        grid = $("#maingrid").etGrid(gridParam);
    }

    //加载列
    function loadGridColumn(value) {
        var colums = columnArray.slice(); //深度复制 不改变原数组

        $.ajax({
            type: "POST",
            url: "queryBudgWageItem.do?isCheck=false",
            data: { "year": value },
            dataType: "json",
            success: function(data) {
                if (data.Rows.length > 0) {
                    data.Rows.forEach(function (item, index) {
                        colums.push({
                            display: item.wage_item_name,
                            name: item.wage_item_code,
                            width: 120,
                            align: 'right',
                            render: function(ui) {
                                var rowData = ui.rowData;
                                var cellData = ui.cellData;
                                var data = [
                                    rowData.group_id,
                                    rowData.hos_id,
                                    rowData.copy_code,
                                    rowData.year,
                                    rowData.month,
                                    rowData.dept_id,
                                    rowData.emp_id,
                                    item.wage_item_code
                                ];

                                data = data.join("|");
                                
                                var result = 
                                    typeof cellData === "undefined" ? 
                                    "--" : 
                                    "<a href='javascript:;' onclick=openUpdate('" + data + "')>" + 
                                        formatNumber(cellData, 2, 1) + 
                                    "</a>";

                                return result;
                            }
                        });
                    })
                }
                // 重新配置列信息，并渲染，然后query
                grid.option('columns', colums);
                grid.refresh();
            }
        });
    }

    //添加
    function add_open() {
        $.etDialog.open({
            url: 'budgEmpWageTotalAddPage.do?isCheck=false&',
            height: 350,
            width: 600,
            title: '科室职工工资总表',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgEmpWageTotal();
            }
        });
    }

    //删除
    function remove() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.year + "@" +
                    this.rowData.month + "@" +
                    this.rowData.dept_id + "@" +
                    this.rowData.emp_id /* + "@" +
                    this.rowData.wage_item_code */
                )
            });

            $.etDialog.confirm('确定删除?', function(index, el) {
                ajaxPostData({
                    url: 'deleteBudgEmpWageTotal.do',
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        queryNew();
                    }
                });
            });
        }
    }

    //导入
    function imp() {
        $.etDialog.open({
            url: 'budgEmpWageTotalImportPage.do?isCheck=false',
            width: 893,
            height: 500,
            title: '导入',
            shadeClose: false,
            shade: false,
            isMax: true 
        });
    }

    function downTemplate() {
        location.href = "downTemplate.do?isCheck=false";
    }

    //修改
    function openUpdate(obj) {
        var vo = obj.split("|");
        var parm =
            "group_id=" + vo[0] + "&" +
            "hos_id=" + vo[1] + "&" +
            "copy_code=" + vo[2] + "&" +
            "year=" + vo[3] + "&" +
            "month=" + vo[4] + "&" +
            "dept_id=" + vo[5] + "&" +
            "emp_id=" + vo[6] + "&" +
            "wage_item_code=" + vo[7];

        $.etDialog.open({
            url: 'budgEmpWageTotalUpdatePage.do?isCheck=false&' + parm,
            height: 320,
            width: 600,
            title: '科室职工工资总表',
            btn: ["确定", "取消"],
            btn1: function(index, el) {
                var frameWindow = window[el.find('iframe')[0].name];
                frameWindow.saveBudgEmpWageTotal();
            }
        });

    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add_open);
        hotkeys('D', remove);
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout" cellpadding="0" border=0 cellspacing="0">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input type="text" id="year_ipt" readonly style="cursor:pointer" />
                </td>
                <td class="label">月份：</td>
                <td class="ipt">
                    <input type="text" id="month_ipt" readonly style="cursor:pointer" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">职工类别：</td>
                <td class="ipt">
                    <select name="" id="emp_type_select" style="width:180px;"></select>
                </td>
                <td class="label">职工名称：</td>
                <td class="ipt">
                    <select name="" id="emp_name_select" style="width:180px;"></select>
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>