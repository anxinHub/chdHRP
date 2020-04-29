<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;

    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    var year_input, month_input, ass_natures_select, ass_type_select, dept_name_select, source_select;
    
    function init() {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                year_input = $("#year_input").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    maxDate: data[0].text,
                    minDate: data[data.length - 1].text,
                    todayButton: false,
                    onSelect: function(value) {
                        setTimeout(function() {
                            query();
                        }, 10);
                    }
                });
            }
        });

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            todayButton: false,
            onSelect: function(value) {
                query();
            }
        });

        ass_natures_select = $("#ass_natures_select").etSelect({
            url: '../../../queryAssNatures.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });

        ass_type_select = $("#ass_type_select").etSelect({
            url: '../../../queryBudgCostFassetType.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: '../../../queryBudgHosDept.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });

        source_select = $("#source_select").etSelect({
            url: '../../../queryBudgSource.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'asset_nature', value: ass_natures_select.getValue() },
            { name: 'ass_type_id', value: ass_type_select.getValue() },
            { name: 'source_id', value: source_select.getValue() }
        ];
        grid.loadData(search, 'queryBudgAsset.do');
    }

    //加载数据
    function loadHead() {
        var columns = [
            { display: '预算年度', name: 'budg_year', align: 'left', width: '10%' },
            { display: '月份', name: 'month', align: 'left', width: '5%' },
            { display: '科室名称', name: 'dept_name', align: 'left', width: '20%' },
            { display: '资金来源', name: 'source_name', align: 'left', width: '15%' },
            { display: '资产分类', name: 'ass_type_name', align: 'left', width: '15%' },
            { display: '现有资产折旧预算', name: 'now_budg', align: 'right', width: '15%',
                render: function(ui) {
                    var now_budg = formatNumber(ui.rowData.now_budg, 2, 1);

                    return "<a href='../assetnow/budgAssetNowMainPage.do?isCheck=false'>" + now_budg + "</a>";
                }
            },
            { display: '预购资产折旧预算', name: 'pur_budg', align: 'right', width: '15%',
                render: function(ui) {
                    var pur_budg = formatNumber(ui.rowData.pur_budg, 2, 1);

                    return "<a href='../assetpur/budgAssetPurMainPage.do?isCheck=false'>" + pur_budg + "</a>";
                }
            },
            { display: '合计', name: 'sum_budg', align: 'right', width: '10%',
                render: function(ui) {
                    var value = ui.cellData;

                    return formatNumber(value, 2, 1);
                }
            }
        ];

        var paramObj = {
            height: '100%',
            checkbox: true,

            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '计算（<u>C</u>）', listeners: [{ click: collect }], icon: 'collect' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' }
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    //计算操作
    function collect () {
        var budg_year = year_input.getValue();
        ajaxPostData({
            url: "collectBudgAsset.do",
            data: { budg_year: budg_year },
            success: function(responseData) {
                query();
            }
        });
    }

    //删除操作
    function remove () {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];

            $(data).each(function() {
                ParamVo.push(
                    this.rowData.asset_nature + "@" +
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.month + "@" +
                    this.rowData.dept_id + "@" +
                    this.rowData.ass_type_id + "@" +
                    this.rowData.source_id
                )
            });

            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgAsset.do",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        query();
                    }
                });
            });
        }
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('C', collect);
        hotkeys('D', remove);
    }
</script>
</head>

<body>
    <div class="mian">
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
                <td class="label">资产性质：</td>
                <td class="ipt">
                    <select name="" id="ass_natures_select" style="width:150px;"></select>
                </td>
                <td class="label">资产分类：</td>
                <td class="ipt">
                    <select name="" id="ass_type_select" style="width:150px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:150px;"></select>
                </td>
                <td class="label">资金来源：</td>
                <td class="ipt">
                    <select name="" id="source_select" style="width:150px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>