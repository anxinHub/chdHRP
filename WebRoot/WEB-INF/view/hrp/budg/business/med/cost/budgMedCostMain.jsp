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
    var grid;

    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    var year_input, month_input, subj_level_select, dept_name_select;

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
                    onChanged: function(value) {
                        reloadSubjName(value);
                        query();
                    }
                });
            }
        });

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            showNav: false,
            dateFormat: "mm",
            onChanged: query
        });

        subj_name_select = $("#subj_name_select").etSelect({
            defaultValue: "none",
            onChange: query
        });

        function reloadSubjName(year) {
            subj_name_select.reload({
                url: "../../../queryBudgSubj.do?isCheck=false",
                para: {
                    subj_type: "05",
                    budg_year: year
                }
            });
        }

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'subj_code', value: subj_name_select.getValue() }
        ];
        grid.loadData(search, 'queryDeptMedCost.do');
    }

    function loadHead() {
    	var columns = [
			{ display: '预算年度', name: 'budg_year', width: 80 },
            { display: '科室编码', name: 'dept_code', width: 100 },
            { display: '科室名称', name: 'dept_name', width: 180 },
            { display: '科目编码', name: 'subj_code', width: 150 },
            { display: '科目名称', name: 'subj_name', width: 120 },
            { display: '本年合计', name: 'this_year_total', align: 'right', width: 120,
                render: function(ui) {
                    var rowData = ui.rowData;
                    var result = 0;

                    for (var i = 1; i < 13; i++) {
                        var the_month_data = "month_data" + i;

                        result += parseFloat(rowData[the_month_data]);
                    }
                    return formatNumber(result, 2, 1);
                }
            }
        ];

        // 循环添加12个位月份的列信息
        for (var i = 1; i < 13; i++) {
        	columns.push({
        		display: i + '月',
        		name: 'month_data' + i,
        		align: 'right',
        		width: 110,
        		render: function (ui) {
        			var value = ui.cellData;

        			return formatNumber(value, 2, 1);
        		}
        	})
        }
        
        var paramObj = {
        	height: '100%',
            checkbox: true,
           /*  rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                var paramArray = [
                    rowData.budg_year,
                    rowData.month,
                    rowData.dept_id,
                    rowData.subj_code
                ];

                openUpdate(paramArray);
            },  */
			pageModel: {
				type: 'remote',
			},
            columns: columns,
            toolbar: {
                items: [
					{ type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '生成（<u>A</u>）', listeners: [{ click: generate }], icon: 'generate' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
    				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function generate () {
        var formPara = {
            budg_year: year_input.getValue()
        }

        $.etDialog.confirm('生成功能产生的新数据将会将覆盖原有数据,确定生成?', function() {
            ajaxPostData({
            	url: "generateBudgMedCost.do?isCheck=false",
            	data: formPara,
            	success: function(responseData) {
                    query();
                }
            });
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgMedCostAddPage.do?isCheck=false&',
            height: 450,
            width: 450,
            title: '医疗支出预算',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMedCost()
            }
        });
    }

    function remove() {
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
                this.rowData.dept_id + "@" +
                this.rowData.subj_code
            )
        });
        $.etDialog.confirm('确定删除?', function(yes) {
            ajaxPostData({
            	url: "deleteBudgMedCost.do",
            	data: { ParamVo: ParamVo.toString() },
            	success: function(responseData) {
                    query();
                }
            });
        });
    }

    function openUpdate (obj) {
        var parm =
            "budg_year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "subj_code=" + obj[3];

        $.etDialog.open({
            url: 'budgMedCostUpdatePage.do?isCheck=false&'+parm,
            height: 450,
            width: 450,
            title: '医疗支出预算',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.savebudgMedCost()
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
			content : 'budgMedCostImportPage.do?isCheck=false'
		});
		layer.full(index);
   	}	
   
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', generate);
        hotkeys('D', remove);
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
                    <select name="" id="dept_name_select" style="width:240px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">科目名称：</td>
                <td class="ipt" colspan='3' >
                    <select name="" id="subj_name_select" style="width:260px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>