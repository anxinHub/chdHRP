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
    var grid;
    var budg_year, month, dept_id, payment_item_id;

    $(function() {
        init();
        loadHead();
        loadHotkeys();
    });

    function loadHead() {
    	var columns = [
			{ display: '预算年度', name: 'budg_year', width: "15%",align: 'center', },
            { display: '月份', name: 'month', width: "10%",align: 'center', },
            { display: '科室名称', name: 'dept_name', width: "20%" },
            { display: '支出项目', name: 'payment_item_name', width: "20%" },
            { display: '预算金额', name: 'cost_budg', align: 'right', width: "15%",align: 'right',
            	render:function(ui) {
                	return formatNumber(ui.rowData.cost_budg,2,1);
            	}
            },
            { display: '执行金额', name: 'exe_amount', align: 'right', width: "15%",align: 'right',
            	render:function(ui) {
                	return '<a href=javascript:openUpdate("'+ ui.rowData.budg_year + "|" + ui.rowData.month + "|" 
                			+ ui.rowData.dept_id + "|" + ui.rowData.payment_item_id + '")>' + formatNumber(ui.rowData.exe_amount,2,1) + '</a>';
            	}
            },
            { display: '预算余额', name: 'remain_amount', align: 'right', width: "15%",align: 'right',
            	render:function(ui) {
                	return formatNumber(ui.rowData.remain_amount,2,1);
            	}
            },
            { display: '说明', name: 'remark', width: "18%" }
        ];

        var paramObj = {
        	height: '100%',
            checkbox: true,
            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: queryNew }], icon: 'search' },
                    { type: 'button', label: '计算（<u>C</u>）', listeners: [{ click: countBudgOtherExpense }], icon: 'settle' },
                    /* { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
    				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }, */
                ]
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    //计算
    function countBudgOtherExpense() {
        var formPara = {
            budg_year: budg_year.getValue()
        }

        ajaxPostData({
        	url: "countBudgOtherExpense.do?isCheck=false",
        	data: formPara,
        	success: function(responseData) {
                queryNew();
            }
        });
    }

    function openUpdate(obj) {
    	var vo = obj.split("|")
        var parm =
            "budg_year=" + vo[0] + "&" +
            "month=" + vo[1] + "&" +
            "dept_id=" + vo[2] + "&" +
            "payment_item_id=" + vo[3]
		
        $.etDialog.open({
            url: 'budgExpenseUpdatePage.do?isCheck=false&' + parm,
            height: 450,
            width: 450,
            title: '科室其他费用预算编制修改',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.savebudgExpense()
            }
        });
    }
	
    
    function init() {
        ajaxPostData({
        	url: "../../queryBudgYear.do?isCheck=false",
        	success: function(data) {
	            budg_year = $("#budg_year").etDatepicker({
	                defaultDate: data[0].text,
	                view: "years",
	                minView: "years",
	                dateFormat: "yyyy",
	                minDate: data[data.length - 1].text,
	                maxDate: data[0].text,
	                onChanged: queryNew
	            });
	        }
	    });

        month = $("#month").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            onChanged: queryNew
        });

        dept_id = $("#dept_id").etSelect({
            url: '../../queryBudgDept.do?isCheck=false',
            defaultValue: "none",
            onChange: queryNew
        });


        payment_item_id = $("#payment_item_id").etSelect({
            url: '../../queryBudgPaymentItemDict.do?isCheck=false&is_read=1',
            defaultValue: "none",
            onChange: queryNew
        });
    }

    function queryNew() {
        var search = [
            { name: 'budg_year', value: budg_year.getValue() },
            { name: 'month', value: month.getValue() },
            { name: 'dept_id', value: dept_id.getValue().split(",")[0] },
            { name: 'payment_item_id', value: payment_item_id.getValue().split(",")[0] }
        ];

        grid.loadData(search, "queryBudgOtherExpenseCount.do");
    }
    //键盘事件
    function loadHotkeys() {
       
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">
                    预算年度：
                </td>
                <td class="ipt">
                    <input type="text" id="budg_year" />
                </td>
                <td class="label">
                    月份：
                </td>
                <td class="ipt">
                    <input type="text" id="month" />
                </td>
                <td class="label">
                    科室名称：
                </td>
                <td class="ipt">
                    <select id="dept_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">
                    支出项目：
                </td>
                <td class="ipt">
                    <select id="payment_item_id" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>