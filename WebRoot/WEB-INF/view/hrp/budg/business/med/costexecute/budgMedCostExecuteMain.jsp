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
    
    var year_input, month_input, subj_name_select, subj_level_select, dept_name_select;

    function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
    function init() {
    	getData("../../../queryBudgYear.do?isCheck=false", function (data) {
    		year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjName(value);
					setTimeout(function () {
						query();
					}, 10);
				}
			});
    		reloadSubjName(data[0].text);
		});

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            showNav: false,
            dateFormat: "mm",
            todayButton: false,
            onChanged: query
        });

        subj_level_select = $("#subj_level_select").etSelect({
            options: subj_level.Rows,
            defaultValue: "none",
            onChange: query
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
            url: "../../../queryDept.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });
    }
    
    //查询
    function query() {
        var search = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'subj_code', value: subj_name_select.getValue() },
            { name: 'subj_level', value: subj_level_select.getValue() }
        ];

        grid.loadData(search, 'queryBudgMedCostExecute.do');
    }

    function loadHead() {
    	var columns = [
            { display: '年度', name: 'year', width: 80 },
            { display: '月份', name: 'month', width: 80 },
            { display: '科室编码', name: 'dept_code', width: 160 },
            { display: '科室名称', name: 'dept_name', width: 160 },
            { display: '科目编码', name: 'subj_code', width: 120 },
            { display: '科目名称', name: 'subj_name', width: 160 },
            { display: '金额(元)', name: 'amount', align: 'right', width: 120,
                render: function(ui) {
                	var value = ui.cellData;

                    return formatNumber(value, 2, 1);
                }
            },
            { display: '说明', name: 'remark', width: 160 }
        ];

        var paramObj = {
        	height: '100%',
            checkbox: true,

            columns: columns,
            pageModel:{
				type:'remote',
			},
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '下载导入模板（<u>B</u>）', listeners: [{ click: downTemplate }], icon: 'down' },
                    { type: 'button', label: '导入（<u>I</u>）', listeners: [{ click: imp }], icon: 'up' },
                    { type: 'button', label: '打印（<u>P</u>）', listeners: [{ click: printDate }], icon: 'print' },
                    { type: 'button', label: '数据采集（<u>C</u>）', listeners: [{ click: collectData }], icon: 'add' } 
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgMedCostExecuteAddPage.do?isCheck=false&',
            height: 450,
            width: 450,
            title: '医疗支出执行数据',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgMedCostExecute()
            }
        });
    }

    function remove() {
        var data = grid.selectGet();

        if (data.length === 0) {
            $.etDialog.error('请选择行');
            return;
        }
        var ParamVo = [];
        $(data).each(function() {
            ParamVo.push(
                this.rowData.group_id + "@" +
                this.rowData.hos_id + "@" +
                this.rowData.copy_code + "@" +
                this.rowData.year + "@" +
                this.rowData.dept_id + "@" +
                this.rowData.month + "@" +
                this.rowData.subj_code
            );
        });
        $.etDialog.confirm('确定删除?', function() {
            ajaxPostData({
            	url: "deleteBudgMedCostExecute.do",
            	data: { ParamVo: ParamVo.toString() },
            	success: function(responseData) {
                    query();
                }
            });
        });
    }

    function imp() {
    	$.etDialog.open({
            url: 'budgMedCostExecuteImportPage.do?isCheck=false',
            height: '500px',
            width: '893px',
            maxmin: true,
            isMax: true,
            title: '导入'
        });
    }

    function downTemplate() {
        location.href = "downTemplate.do?isCheck=false";
    }

    function openUpdate (obj) {
        var parm =
            "group_id=" + vo[0] + "&" +
            "hos_id=" + vo[1] + "&" +
            "copy_code=" + vo[2] + "&" +
            "year=" + vo[3] + "&" +
            "dept_id=" + vo[4] + "&" +
            "month=" + vo[5] + "&" +
            "subj_code=" + vo[6];
        $.etDialog.open({
            url: 'budgMedCostExecuteUpdatePage.do?isCheck=false&',
            data: {},
            height: 300,
            width: 450,
            title: '医疗支出执行数据',
            modal: true,
            showToggle: false,
            showMax: false,
            showMin: false,
            isResize: true,
            buttons: [
                { text: '确定', onclick: function(item, dialog) { dialog.frame.savebudgMedCostExecute(); }, cls: 'l-dialog-btn-highlight' },
                { text: '取消', onclick: function(item, dialog) { dialog.close(); } }
            ]
        });
    }
	
    //执行数据采集   (财务取数 )
    function collectData(){
    	if(!year_input.getValue()){
    		 $.etDialog.error('年度不能为空!');
             return;
    	}
    	if(!month_input.getValue()){
    		 $.etDialog.error('月份不能为空!');
             return;
    	}
    	
    	ajaxPostData({
        	url: "collectData.do?isCheck=false",
        	data: { year : year_input.getValue() ,
        			month : month_input.getValue()},
        	success: function(responseData) {
                query();
            }
        });
    }
    
    function printDate() {
        // if (grid.getAllData().length == 0) {
        //     $.etDialog.error("无打印数据！");
        //     return;
        // }
        // var search = [
        //     { name: 'year', value: year_input.getValue() },
        //     { name: 'month', value: month_input.getValue() },
        //     { name: 'dept_id', value: dept_name_select.getValue() },
        //     { name: 'subj_code', value: subj_name_select.getValue() },
        //     { name: 'subj_level', value: subj_level_select.getValue() }
        // ];
        // grid.options.parms = search;
        // grid.options.newPage = 1;

        // var selPara = {};
        // $.each(grid.options.parms, function(i, obj) {
        //     selPara[obj.name] = obj.value;
        // });
        // var printPara = {
        //     headCount: 2,
        //     title: "医疗支出预算执行信息",
        //     type: 3,
        //     columns: grid.getColumns(1)
        // };
        // ajaxPostData({
        // 	url: "queryBudgMedCostExecute.do?isCheck=false",
        // 	data: selPara,
        // 	success: function(responseData) {
        //         printGridView(responseData, printPara);
        //     }
        // });
    }
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', add);
        hotkeys('D', remove);
        hotkeys('B', downTemplate);
        hotkeys('P', printDate);
        hotkeys('I', imp);
        hotkeys('C', collectData);
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
                <td class="label">科目级次：</td>
                <td class="ipt">
                    <select name="" id="subj_level_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">科目名称：</td>
                <td class="ipt">
                    <select name="" id="subj_name_select" style="width:180px;"></select>
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
                <td class=""></td>
                <td class=""></td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>