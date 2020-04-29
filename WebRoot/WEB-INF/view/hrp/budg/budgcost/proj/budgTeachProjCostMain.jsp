<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/static_resource.jsp">
		<jsp:param value="select,datepicker,grid,ligerUI" name="plugins"/>
	</jsp:include>
    <script type="text/javascript">
        var grid;
        var gridManager = null;
        var userUpdateStr;

        //打印 单元格格式化 用
        var renderFunc = {
            budg_amount: function (value) { //支出预算
                return formatNumber(value, 2, 1);
            }
        };
        $(function () {
            //加载数据
            loadHead(null);
            loadHotkeys();
            init();
        });
        
        var year_input, proj_name_select, source_select, subj_code_select, payment_item_select;

        function init() {
        	year_input = $("#year_input").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: function () {
                    setTimeout(function () {
                    	query();
                    }, 10);
                },
                defaultDate: true
            });

            proj_name_select = $("#proj_name_select").etSelect({
                url: "../../queryProjName.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });

            source_select = $("#source_select").etSelect({
                url: "../../queryBudgSource.do?isCheck=false&source_attr=4",
                defaultValue: "none",
                onChange: query
            });

            subj_code_select = $("#subj_code_select").etSelect({
                url: "../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1",
                defaultValue: "none",
                onChange: query
            });

            payment_item_select = $("#payment_item_select").etSelect({
                url: "../../queryBudgPaymentItem.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
        }

        //ajax获取数据
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
        }
        
        //查询
        function query() {
            var search = [
                { name: 'budg_year', value: year_input.getValue() },
                { name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
                { name: 'source_id', value: source_select.getValue() },
                { name: 'subj_code', value: subj_code_select.getValue() },
                { name: 'payment_item_id', value: payment_item_select.getValue() }
            ];
            grid.loadData(search, '');
        }

        function loadHead() {
            grid = $("#maingrid").etGrid({
                columns: [
                    { display: '年度', name: 'budg_year', align: 'center', width: 60 },
                    { display: '项目编码', name: 'proj_code', align: 'left', width: 120 },
                    { display: '项目名称', name: 'proj_name', align: 'left', width: 300 },
                    { display: '支出项目名称', name: 'payment_item_name', align: 'left', width: 300 },
                    { display: '资金来源', name: 'source_name', align: 'left', width: 100 },
                    { display: '预算科目', name: 'subj_name', align: 'left', width: 180 },
                    {
                        display: '支出预算', name: 'budg_amount', align: 'right', minWidth: 120,
                        render:function(ui) {
                        	if(ui.rowData.budg_amount){
                        		return formatNumber(ui.rowData.budg_amount,2,1);
                        	}
                        }
                    }
                ],
                dataModel: {
                    method: 'POST',
                    location: 'remote',
                    url: 'queryBudgTeachProjCost.do',
                    recIndx: 'budg_year' //必填 且该列不能为空  
                },
                usePager: true,
                width: '100%',
                inWindowHeight: true,
                height: '100%',
                checkbox: true,
                toolbar: {
                    items: [
                        { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                    ]
                },
            });
        }

        //打印回调方法
        function lodopPrint() {
            var head = "";
            grid.options.lodop.head = head;
            grid.options.lodop.fn = renderFunc;
            grid.options.lodop.title = "教学项目支出预算";
        }
        //键盘事件
        function loadHotkeys() {
            hotkeys('Q', query);
        }
    </script>
     
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>

    <div id="toptoolbar"></div>
   
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label">项目名称：</td>
                <td class="ipt">
                    <select name="" id="proj_name_select" style="width:180px;"></select>
                </td>
                <td class="label">资金来源：</td>
                <td class="ipt">
                    <select name="" id="source_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">支出科目：</td>
                <td class="ipt">
                    <select name="" id="subj_code_select" style="width:180px;"></select>
                </td>
                <td class="label">支出项目：</td>
                <td class="ipt">
                    <select name="" id="payment_item_select" style="width:180px;"></select>
                </td>
                <td class=""></td>
                <td class=""></td>
            </tr>
        </table>
    </div>

    <div id="maingrid"></div>

</body>
</html>