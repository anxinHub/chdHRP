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
	<jsp:param value="select,datepicker,grid,ligerUI" name="plugins"/>
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var gridDept;
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
        loadHeadHos();
        loadDict();
        $("#hosItem").show(); //显示医院
        $("#deptItem").hide(); //隐藏科室   
        $("#item_tab").ligerTab({
            onAfterSelectTabItem: function(tabid) {
                selectTab(tabid);
            }
        });

        $(".input_1").hide(); //设置首次加载页面的时候不容许加载下拉框

        $("#budg_year").change(function() {
            if ($("#hosItem").css('display') == 'block') {
                query();
            }

            if ($("#deptItem").css('display') == 'block') {

                queryDept();

            }
        })
        $("#subj_code").change(function() {
            if ($("#hosItem").css('display') == 'block') {
                query();
            }

            if ($("#deptItem").css('display') == 'block') {

                queryDept();

            }
        })

        $("#subj_level").change(function() {
            if ($("#hosItem").css('display') == 'block') {
                query();
            }

            if ($("#deptItem").css('display') == 'block') {

                queryDept();

            }
        })

        $("#dept_id").change(function() {
            if ($("#hosItem").css('display') == 'block') {
                query();
            }

            if ($("#deptItem").css('display') == 'block') {

                queryDept();

            }
        })
    });
    //查询
    function query() {
        if (!liger.get("budg_year").getValue()) {

            $.ligerDialog.error('预算年度不能为空');

            return false;
        }
        var parms = [];
        //根据表字段进行添加查询条件
        parms.push({ name: 'year', value: liger.get("budg_year").getValue() });
        parms.push({ name: 'subj_code', value: liger.get("subj_code").getValue() });
        parms.push({ name: 'subj_level', value: liger.get("subj_level").getValue() });
        parms.push({ name: 'check_code', value: "${check_code}" });
        //加载查询条件
        grid.loadData(parms, "../check/queryBudgElseIncomeHosMonthCopy.do?isCheck=false");
    }
    //查询
    function queryDept() {
        if (!liger.get("budg_year").getValue()) {

            $.ligerDialog.error('预算年度不能为空');

            return false;
        }
        var parms = [];
        //根据表字段进行添加查询条件
        parms.push({ name: 'year', value: liger.get("budg_year").getValue() });
        parms.push({ name: 'subj_code', value: liger.get("subj_code").getValue() });
        parms.push({ name: 'subj_level', value: liger.get("subj_level").getValue() });
        parms.push({ name: 'dept_id', value: liger.get("dept_id").getValue() });
        parms.push({ name: 'check_code', value: "${check_code}" });
        //加载查询条件
        gridDept.loadData(parms, "../check/queryBudgElseIncomeDeptMonthCopy.do?isCheck=false");

    }

    function loadHeadHos() {
        grid = $("#maingrid").etGrid({
            columns: [{ display: '年度', name: 'year', align: 'left', width: 80 },
                { display: '科目编码', name: 'subj_code', align: 'left', width: 100 },
                { display: '科目名称', name: 'subj_name', align: 'left', width: 120 },
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
                recIndx: 'subj_name' //必填 且该列不能为空  
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

    function loadHeadDept() {
        gridDept = $("#maingrid1").etGrid({
            columns: [{
                    display: '年度',
                    name: 'year',
                    align: 'left',
                    width: 80,
                },
                {
                    display: '科室编码',
                    name: 'dept_code',
                    align: 'left',
                    width: 100,
                },
                {
                    display: '科室名称',
                    name: 'dept_name',
                    align: 'left',
                    width: 120,
                },
                {
                    display: '科目编码',
                    name: 'subj_code',
                    align: 'left',
                    width: 100,
                },
                {
                    display: '科目名称',
                    name: 'subj_name',
                    align: 'left',
                    width: 120,
                },
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
                recIndx: 'subj_name' //必填 且该列不能为空  
            },
            usePager: true,
            width: '100%',
            inWindowHeight: true,
            height: '100%',
            checkbox: true,
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: queryDept }] },
                ]
            },
        });
    }
    //打印回调方法
    function lodopPrint() {
        if ($("#hosItem").css('display') == 'block') {
            var head = "";
            grid.options.lodop.head = head;
            grid.options.lodop.fn = renderFunc;
            grid.options.lodop.title = "调整前预算查看(医院)";
        }

        if ($("#deptItem").css('display') == 'block') {

            var head = "";
            gridDept.options.lodop.head = head;
            gridDept.options.lodop.fn = renderFunc;
            gridDept.options.lodop.title = "调整前预算查看(科室)";

        }

    }

    function selectTab(item) {
        switch (item) {
            case "tabitem1":
                $(".input_1").hide();
                if (!grid) {
                    loadHeadHos();
                }
                query();
                $("#hosItem").show();
                $("#deptItem").hide();
                return;

            case "tabitem2":
                $(".input_1").show();
                if (!gridDept) {
                    loadHeadDept();
                }
                queryDept();
                $("#hosItem").hide();
                $("#deptItem").show();
                return;

        }
    }

    function loadDict() {
        //字典下拉框
        //   加载年度下拉框
        autocomplete("#budg_year", "../../../queryBudgYear.do?isCheck=false", "id", "text", true, true, '', false, '${budg_year}');
        //加载指标名称下拉框          
        autocomplete("#subj_code", "../../../queryBudgSubj.do?isCheck=false&subj_type=" + '04' + "&budg_year=" + "${budg_year}", "id", "text", true, true, '', false, '', 200);

        autocomplete("#subj_level", "../../../queryBudgSubjLevel.do?isCheck=false", "id", "text", true, true, '', false, '', 80);

        //加载科室名称下拉框          
        autocomplete("#dept_id", "../../../queryBudgDeptDict.do?isCheck=false", "id", "text", true, true);

        $("#budg_year").ligerTextBox({ width: 180, disabled: true, cancelable: true });

        $("#subj_level").ligerTextBox({ width: 80 });

    }
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
            <tr>
                <td align="right" class="l-table-edit-td" style="padding-left:20px;">预算年度：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td" style="padding-left:20px;">科目名称：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td" style="padding-left:20px;">科目级次：</td>
                <td align="left" class="l-table-edit-td">
                    <input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
                <!-- 为了方便隐藏，使用jquery()class后面加上input_1属性,; -->
                <td align="right" class="l-table-edit-td input_1" style="padding-left:20px;">科室名称：</td>
                <td align="left" class="l-table-edit-td input_1" style="padding-left:20px;">
                    <input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </div>
    <div id="item_tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; ">
        <div id="hosItem" title="医院">
            <div id="maingrid"></div>
        </div>
        <div id="deptItem" title="科室">
            <div id="maingrid1"></div>
        </div>
    </div>
</body>

</html>