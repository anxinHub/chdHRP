<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var year_input, month_input, dept_name_select, med_type_select;
    var grid;

    $(function() {
        loadHead();
        init();
        loadHotkeys();
    });

    function loadHead () {
        var columns = [
            { display: '年度', name: 'year', align: 'center',width: "10%" },
            { display: '月', name: 'month',align: 'center', width: "10%" },
            { display: '科室名称', name: 'dept_name',align: 'left', width: "30%" },
            { display: '药品分类编码', name: 'med_type_name',align: 'left', width: "30%"},
            {
                display: '金额',
                name: 'amount',
                align: 'right',
                minWidth:"17%",
                render: function(ui) {
                    ui.rowData.amount = ui.rowData.amount || 0;

                    return formatNumber(ui.rowData.amount, 2, 1)
                }
            }
        ];
        var toolbar = {
            items: [
                { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
            ]
        };

        var paramObj = {
            columns: columns,
            toolbar: toolbar,

            height: '100%',
            checkbox: true,
            rowDblClick: function (event, ui) {
                var rowData = ui.rowData;

                openUpdate([
                    rowData.year,
                    rowData.month,
                    rowData.dept_id,
                    rowData.med_type_id
                ]);
            }
        };
        grid = $("#maingrid").etGrid(paramObj);
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgDrugCostAddPage.do?isCheck=false&',
            height: 450,
            width: 450,
            title: '科室药品支出',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.savebudgDrugCost()
            }
        });
    }

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
                    this.rowData.med_type_id
                )
            });
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgDrugCost.do",
                    data: { ParamVo: ParamVo.toString() },
                    success: function() {
                        queryNew();
                    }
                });
            });
        }
    }

    function openUpdate(obj) {
        var parm =
            "year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "med_type_id=" + obj[3];

        $.etDialog.open({
            url: 'budgDrugCostUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 450,
            width: 450,
            title: '科室药品支出',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.savebudgDrugCost()
            }
        });
    }
    function imp(){
   		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgDrugCostImportPage.do?isCheck=false'
		});
		layer.full(index);
   	}	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add);
        hotkeys('D', remove);
    }

    function init () {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                year_input = $("#year_input").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged: queryNew
                });
            }
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
            url: '../../../queryBudgDeptDict.do?isCheck=false',
            defaultValue: "none",
            onChange: queryNew
        });

        med_type_select = $("#med_type_select").etSelect({
            url: '../../../queryBudgDrugType.do?isCheck=false',
            defaultValue: "none",
            onChange: queryNew
        });
    }

    function queryNew () {
        var search = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'med_type_id', value: med_type_select.getValue().split(',')[0] }
        ];
        grid.loadData(search, "queryBudgDrugCost.do");
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label">月份：</td>
                <td class="ipt">
                    <input type="text" id="month_input" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">药品分类：</td>
                <td class="ipt">
                    <select name="" id="med_type_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>