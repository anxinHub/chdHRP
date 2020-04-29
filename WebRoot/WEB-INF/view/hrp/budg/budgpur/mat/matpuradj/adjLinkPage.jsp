<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var budg_year,mat_type_id;
    var gridManager1 = null;
    var userUpdateStr;
    var renderFunc = {
        year_sum: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month1: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month2: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month3: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month4: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month5: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month6: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month7: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month8: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month9: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month10: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month11: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        },
        month12: function(value) { //本年合计
            if (value) {
                return formatNumber(value, 2, 1);
            }
        }
    };
    $(function() {
        loadHead();
        loadDict();
    });
    //查询
    function query() {
        if (!'${budg_year}') {
            $.etDialog.error('预算年度不能为空');
            return false;
        }
        var parms = [
            { name: 'year', value: '${budg_year}' },
            { name: 'mat_type_id', value: mat_type_id.getValue() }
        ];
        //加载查询条件
        grid.loadData(parms, "../matpurcheck/queryBudgMatPurAdjustCopy.do?isCheck=false");
    }

    function loadHead() {
        grid = $("#maingrid").etGrid({
            columns: [
                { display: '物资编码', name: 'mat_type_id', align: 'left', width: 100 },
                { display: '物资名称', name: 'mat_type_name', align: 'left', width: 120 },
                {
                    display: '本年合计',
                    name: 'year_sum',
                    align: 'right',
                    width: 120,
                    frozen: true,
                    render:function(ui) {

                        if (ui.rowData.year_sum) {
                            return formatNumber(ui.rowData.year_sum, 2, 1);
                        }
                    }

                },
                {
                    display: '1月',
                    name: 'month1',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month1) {
                            return formatNumber(ui.rowData.month1, 2, 1);
                        }
                    }
                },
                {
                    display: '2月',
                    name: 'month2',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month2) {
                            return formatNumber(ui.rowData.month2, 2, 1);
                        }
                    }
                },
                {
                    display: '3月',
                    name: 'month3',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month3) {
                            return formatNumber(ui.rowData.month3, 2, 1);
                        }
                    }
                },
                {
                    display: '4月',
                    name: 'month4',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month4) {
                            return formatNumber(ui.rowData.month4, 2, 1);
                        }
                    }
                },
                {
                    display: '5月',
                    name: 'month5',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month5) {
                            return formatNumber(ui.rowData.month5, 2, 1);
                        }
                    }
                },
                {
                    display: '6月',
                    name: 'month6',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month6) {
                            return formatNumber(ui.rowData.month6, 2, 1);
                        }
                    }
                },
                {
                    display: '7月',
                    name: 'month7',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month7) {
                            return formatNumber(ui.rowData.month7, 2, 1);
                        }
                    }
                },
                {
                    display: '8月',
                    name: 'month8',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month8) {
                            return formatNumber(ui.rowData.month8, 2, 1);
                        }
                    }
                },
                {
                    display: '9月',
                    name: 'month9',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month9) {
                            return formatNumber(ui.rowData.month9, 2, 1);
                        }
                    }
                },
                {
                    display: '10月',
                    name: 'month10',
                    align: 'left',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month10) {
                            return formatNumber(ui.rowData.month10, 2, 1);
                        }
                    }
                },
                {
                    display: '11月',
                    name: 'month11',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month11) {
                            return formatNumber(ui.rowData.month11, 2, 1);
                        }
                    }

                },
                {
                    display: '12月',
                    name: 'month12',
                    align: 'right',
                    width: 120,
                    render:function(ui) {

                        if (ui.rowData.month12) {
                            return formatNumber(ui.rowData.month12, 2, 1);
                        }
                    }
                }
            ],
            dataModel: {
                method: 'POST',
                location: 'remote',
                url: '',
                recIndx: 'mat_type_name' //必填 且该列不能为空  
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
        grid.options.lodop.title = "调整前预算查看(医院)";
    }

    function loadDict() {
    	mat_type_id = $("#mat_type_id").etSelect({
             url: "../../../queryMatTypes.do?isCheck=false",
             defaultValue: "none",
             onInit: query,
             onChange: query
         });
    }
</script>
</head>
<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input id="budg_year" type="text" disabled value="${budg_year}" />
                </td>

                <td class="label">物资分类：</td>
                <td class="ipt">
                    <select id="mat_type_id" style="width: 180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>
</html>