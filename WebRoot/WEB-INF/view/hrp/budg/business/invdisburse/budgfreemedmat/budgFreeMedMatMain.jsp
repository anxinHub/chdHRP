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
    var year_input, month_input, dept_name_select, material_type_select;
    var grid;
    $(function() {
        loadHead();
        init();
        loadHotkeys();
    });
    //查询
    function query() {
        var parms = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'mat_type_id', value: material_type_select.getValue().split(",")[0] }
        ];
        grid.loadData(parms);
    }

    function loadHead() {
        var paremObj = {
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
        paremObj.columns = [
            { display: '年度', name: 'year', width: 60 },
            { display: '月', name: 'month', width: 60 },
            { display: '部门', name: 'dept_name', width: 100 },
            { display: '物资类别', name: 'mat_type_name', width: 100 },
            {
                display: '业务量预算',
                columns: [
                    { display: '门诊', name: 'work_budg_out', width: 100 , align: 'right',
                    	render: function (ui) {
                            ui.rowData.work_budg_out = ui.rowData.work_budg_out || 0;

                            return formatNumber(ui.rowData.work_budg_out,2,1);
                        }},
                    { display: '住院', name: 'work_budg_in', width: 100,
                    	 align: 'right',
                     	render: function (ui) {
                             ui.rowData.work_budg_in = ui.rowData.work_budg_in || 0;

                             return formatNumber(ui.rowData.work_budg_in,2,1);
                         }
                     },
                    { display: '检查', name: 'work_budg_check', width: 100, align: 'right',
                    	render: function (ui) {
                            ui.rowData.work_budg_check = ui.rowData.work_budg_check || 0;

                            return formatNumber(ui.rowData.work_budg_check,2,1);
                        }
                     }
                ]
            },
            {
                display: '业务量支出配比',
                columns: [
                    { display: '门诊', name: 'work_cost_rate_out', width: 120 , align: 'center',
                    	render: function (ui) {
                            ui.rowData.work_cost_rate_out = ui.rowData.work_cost_rate_out || 0;

                            return formatNumber(ui.rowData.work_cost_rate_out,2,1) + '%';
                        }
                    },
                    { display: '住院', name: 'work_cost_rate_in', width: 120 , align: 'center',
                    	render: function (ui) {
                            ui.rowData.work_cost_rate_in = ui.rowData.work_cost_rate_in || 0;

                            return formatNumber(ui.rowData.work_cost_rate_in,2,1) + '%';
                        }
                    },
                    { display: '检查', name: 'work_cost_rate_check', width: 120, align: 'center',
                    	render: function (ui) {
                            ui.rowData.work_cost_rate_check = ui.rowData.work_cost_rate_check || 0;

                            return formatNumber(ui.rowData.work_cost_rate_check,2,1) + '%';
                        }
                    }
                ]
            },
            { display: '计算值', name: 'count_value', width: 100, align: 'right',
                render: function (ui) {
                    ui.rowData.count_value = ui.rowData.count_value || 0;

                    return formatNumber(ui.rowData.count_value,2,1);
                }
            },
            { display: '调整比例', name: 'adj_rate', width: 100,align: 'center',
                render: function (ui) {
                    ui.rowData.adj_rate = ui.rowData.adj_rate || 0;
                    
                    return formatNumber(ui.rowData.adj_rate,2,1) + '%';
                }
            },
            { display: '支出预算', name: 'cost_budg', width: 100,align: 'right',
                render: function (ui) {
                    ui.rowData.count_value = ui.rowData.cost_budg || 0;

                    return formatNumber(ui.rowData.cost_budg,2,1);
                }
            },
            { display: '说明', name: 'remark', width: 100 }
        ];
        paremObj.dataModel = {
            url: 'queryBudgFreeMedMat.do',
        };
        paremObj.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '生成（<u>G</u>）', listeners: [{ click: generate }], icon: 'generate' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '下载导入模板（<u>B</u>）', listeners: [{ click: downTemplate }], icon: 'down' },
                { type: 'button', label: '批量调整', listeners: [{ click: update }], icon: 'update' },
                { type: 'button', label: '导入（<u>I</u>）', listeners: [{ click: imp }], icon: 'up' }
            ]
        }
        grid = $("#maingrid").etGrid(paremObj);
    }

    //批量调整
    function update() {
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
                    this.rowData.mat_type_id + "@" +
                    this.rowData.count_value
                );
            });
            $.etDialog.open({
                url: 'budgBudgfreeMedMatUpdateAdjRatePage.do?isCheck=false&',
                data: ParamVo,
                height: 450,
                width: 450,
                title: '修改调整比例',
                btn: ['保存', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
					console.log(iframeWindow)
                    iframeWindow.saveBudgFreeMedMat()
                }
            });
        }
    }
    //生成
    function generate() {
    	if(!year_input.getValue()){
    		$.etDialog.error('年度不能为空');
    		return;
    	}
        var formPara = {
            year: year_input.getValue(),
        };

        ajaxPostData({
            url: "generatebudgFreeMedMat.do?isCheck=false",
            data: formPara, 
            success: function(responseData) {
                query();
            }
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgFreeMedMatAddPage.do?isCheck=false&',
            height: 450,
            width: 450,
            title: '添加',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgFreeMedMat()
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
                    this.rowData.mat_type_id
                );
            });
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgFreeMedMat.do",
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
            url: 'budgFreeMedMatImportPage.do?isCheck=false',
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

    function openUpdate(obj) {
        var parm =
            "year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "mat_type_id=" + obj[3];

        $.etDialog.open({
            url: 'budgFreeMedMatUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 450,
            width: 450,
            title: '更新',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgFreeMedMat()
            }
        });
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', add);
        hotkeys('D', remove);
        hotkeys('B', downTemplate);
        hotkeys('I', imp);
    }

    function init () {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function (data) {
                year_input = $("#year_input").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged : function (value){
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
            todayButton: false
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
                <td class="label">部门：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px"></select>
                </td>
            </tr>
            <tr>
                <td class="label">物资类别：</td>
                <td class="ipt">
                    <select name="" id="material_type_select" style="width:180px"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>