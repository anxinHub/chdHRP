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

    var year_input, month_input, subj_level_select, subj_level;

    function init() {
        ajaxPostData({
        	url: "../../../queryBudgYearTen.do?isCheck=false",
        	success: function(data) {
	            year_input = $("#year_input").etDatepicker({
	                defaultDate: data[4].text,
	                view: "years",
	                minView: "years",
	                dateFormat: "yyyy",
	                minDate: data[0].text,
	                maxDate: data[data.length - 1].text,
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
            todayButton: false,
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

        subj_level = $("#subj_level").etSelect({
            url: "../../../queryBudgCostSubjLevel.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'subj_level', value: subj_level.getValue() },
            { name: 'subj_code', value: subj_name_select.getValue() }
        ];
        grid.loadData(search, 'queryAllMedCost.do');
    }

    function loadHead() {
    	var columns = [
			{ display: '预算年度', name: 'budg_year', width: 80 },
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

            columns: columns,
            pageModel: {
				type: 'remote',
			},
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' }
                ]
            }
        };

        grid = $("#maingrid").etGrid(paramObj);
    }

    function printDate() {
        // if (grid.getData().length == 0) {
        //     $.ligerDialog.error("无打印数据！");
        //     return;
        // }
        // var search = [
        //     { name: 'budg_year', value: year_input.getValue() },
        //     { name: 'month', value: month_input.getValue() },
        //     { name: 'subj_level', value: subj_level.getValue() },
        //     { name: 'subj_code', value: subj_name_select.getValue() }
        // ];
        // grid.options.parms = search;
        // grid.options.newPage = 1;

        // var selPara = {};
        // $.each(grid.options.parms, function(i, obj) {
        //     selPara[obj.name] = obj.value;
        // });
        // var printPara = {
        //     headCount: 2,
        //     title: "全院医疗支出预算信息",
        //     type: 3,
        //     columns: grid.getColumns(1)
        // };
        // ajaxJsonObjectByUrl("queryAllMedCost.do?isCheck=false", selPara, function(responseData) {
        //     printGridView(responseData, printPara);
        // });
    }
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('P', printDate);
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
                <td class="label">科目名称：</td>
                <td class="ipt">
                    <select id="subj_name_select" style="width:180px;"></select>
                </td>
            	<td class="label">科目级次：</td>
                <td class="ipt">
                    <select id="subj_level" style="width:80px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>