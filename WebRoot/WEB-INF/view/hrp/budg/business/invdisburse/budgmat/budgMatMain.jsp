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
        var search = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'mat_type_id', value: material_type_select.getValue().split(",")[0] }
        ];
        grid.loadData(search, 'queryBudgMat.do');
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
            { display: '预算年度', name: 'year', width: '10%' },
            { display: '月份', name: 'month', width: '5%' },
            { display: '科室名称', name: 'dept_name', width: '15%' },
            { display: '物资分类', name: 'mat_type_name', width: '20%' },
            { display: '支出预算', name: 'cost_budg', align: 'right', width: '17%',
                render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 2, 1);
                }
            },
            { display: '说明', name: 'remark', width:'30%' }
        ];
        paremObj.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '汇总（<u>C</u>）', listeners: [{ click: collect }], icon: 'collect' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
            ]
        }
        grid = $("#maingrid").etGrid(paremObj);
    }

    function collect () {
    	if(!year_input.getValue()){
    		 $.etDialog.error('年度不能为空');
    		 return ;
    	}
        var formPara = {
            year: year_input.getValue()
        };

        ajaxPostData({
            url: "collectBudgMat.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                query();
            }
        });
    }

    function openUpdate (obj) {
        var parm = 
            "year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "mat_type_id=" + obj[3];

        $.etDialog.open({
            url: 'budgMatUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 450,
            width: 500,
            title: '科室材料支出预算编制',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMat()
            }
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgMatAddPage.do?isCheck=false&budg_year=' + $("#year").val(),
            height: 450,
            width: 500,
            title: '科室材料支出预算编制',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMat()
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
                )
            });

            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgMat.do?isCheck=false",
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
            url: 'budgMatImportPage.do?isCheck=false',
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
        hotkeys('C', collect);
        hotkeys('A', add);
        hotkeys('D', remove);
        //hotkeys('I', imp);
    }
    var init_year;
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
                    onChange: function (value){
                    	reloadBudgMatType(value);
                    	query();
                    }
                });
                reloadBudgMatType(data[0].text);
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
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>