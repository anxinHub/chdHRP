<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var year_input, month_input, dept_name_select, material_type_select;
    var grid;
    $(function() {
        loadHead();
        init();
        loadHotkeys();
    });
    
    function query() {
        var search = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'mat_type_id', value: material_type_select.getValue().split(",")[0] }
        ];
        grid.loadData(search, 'queryBudgNoMedMat.do');
    }

    function loadHead () {
        var paramObj = {
            height: '100%',
            checkbox: true,
            rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                openUpdate([
                    rowData.year,
                    rowData.month,
                    rowData.dept_id,
                    rowData.mat_type_id
                ]);
            }
        };
        paramObj.columns = [
            { display: '预算年度', name: 'year', width: 80 },
            { display: '月份', name: 'month', width: 80 },
            { display: '科室名称', name: 'dept_name', width: 120 },
            { display: '物资分类', name: 'mat_type_name', width: 120 },
            { display: '上年同期支出', name: 'last_cost', align: 'right', width: 120,
                render: function(ui) {
                    ui.rowData.last_cost = ui.rowData.last_cost || 0;

                    return formatNumber(ui.rowData.last_cost, 2, 1);
                }
            },
            { display: '增长比例', name: 'grow_rate', align: 'right', width: 120,
                render: function(ui) {
                    ui.rowData.grow_rate = ui.rowData.grow_rate || 0;

                    return ui.rowData.grow_rate + '%';
                }
            },
            { display: '计算值', name: 'count_value', align: 'right', width: 120,
                render: function(ui) {
                    ui.rowData.count_value = ui.rowData.count_value || 0;

                    return formatNumber(ui.rowData.count_value, 2, 1);
                }
            },
            { display: '支出预算', name: 'cost_budg', align: 'right', width: 120,
                render: function(ui) {
                    ui.rowData.cost_budg = ui.rowData.cost_budg || 0;

                    return formatNumber(ui.rowData.cost_budg, 2, 1);
                }
            },
            { display: '说明', name: 'remark', width: 180 }
        ];
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '生成（<u>G</u>）', listeners: [{ click: generate }], icon: 'generate' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
                { type: 'button', label: '增长比例设置（<u>S</u>）', listeners: [{ click: growRateSet }], icon: 'collect' }
            ]
        };
        grid = $("#maingrid").etGrid(paramObj);
    }

    function generate() {
        $.etDialog.confirm('确定生成本年预算', function() {
            var formPara = {
                year: year_input.getValue()
            };
            ajaxPostData({
                url: "generateBudgNoMedMat.do?isCheck=false",
                data: formPara,
                success: function(responseData) {
                    query();
                }
            });
        });
    }

    function growRateSet() {
        var data = grid.selectGet();

        if (data.length === 0) {
            $.etDialog.error('请选择行');
        } else {
            $.etDialog.open({
                url: 'budgNoMedMatGrowRatePage.do?isCheck=false',
                data: { ParamVo: JSON.stringify(data) },
                height: 200,
                width: 400,
                title: '科室非医用材料预算编制',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.collectBudgNoMedMat()
                }
            })
        }
    }

    function openUpdate (obj) {
        var parm =
            "year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "mat_type_id=" + obj[3];

        $.etDialog.open({
            url: 'budgNoMedMatUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 450,
            width: 500,
            title: '科室非医用材料预算编制',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgNoMedMat()
            }
        });
    }

    function add_open () {
        $.etDialog.open({
            url: 'budgNoMedMatAddPage.do?isCheck=false&budg_year=' + $("#year").val(),
            height: 450,
            width: 500,
            title: '科室非医用材料预算编制',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgNoMedMat()
            }
        });
    }

    function remove () {
        var data = grid.selectGet();
        if (data.length === 0) {
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
                    this.rowData.mat_type_id
                )
            });
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgNoMedMat.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        query();
                    }
                });
            });
        }
    }
    function imp () {
        $.etDialog.open({
            url: 'budgNoMedMatImportPage.do?isCheck=false',
            height: 893,
            width: 500,
            title: '导入',
            maxmin: true,
            isMax: true
        });
    }
    function downTemplate() {
        location.href = "downTemplate.do?isCheck=false";
    }
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('G', generate);
        hotkeys('A', add);
        hotkeys('D', remove);
        //hotkeys('I', imp);
        hotkeys('S', growRateSet);
    }

    function init() {
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
                    onChanged:  function (value){
                    	reloadBudgMatType(value);
                    	query();
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
            onChanged: query
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });

		material_type_select = $("#material_type_select").etSelect({
            
            defaultValue: "none",
            onChange: query
        })
        
		function reloadBudgMatType(year){
        	material_type_select.reload({
				url: "../../../queryBudgMatTypeSubj.do?isCheck=false",
				para:{
					budg_year:year
				}
			})
		}
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
                    <select name="" id="dept_name_select" style="width:180px"></select>
                </td>
            </tr>
            <tr>
                <td class="label">物资类别：</td>
                <td class="ipt">
                    <select name="" id="material_type_select" style="width:180px"></select>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>