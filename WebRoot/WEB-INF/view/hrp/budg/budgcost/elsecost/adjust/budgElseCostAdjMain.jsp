<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="grid,select,datepicker,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    //打印 单元格格式化 用
    var renderFunc = {
        state: function(value) { //状态
            if (value == '01') {
                return "新建";
            }
            if (value == '02') {
                return "已审核";
            }
        }
    };
    
    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });
    
    var year_input, state_select;

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

        state_select = $("#state_select").etSelect({
            url: '../../../queryBudgState.do?isCheck=false',
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
            success: function(res) {
                if (typeof callback === "function") {
                    callback(res);
                }
            }
        });
    };
    
    function query() {
        if (!year_input.getValue()) {
            $.etDialog.error('请选择年度后查询!');
            return false;
        }
        var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'state', value: state_select.getValue() }
        ];

        grid.loadData(parms, 'queryBudgElseCostAdj.do?isCheck=false');
    }

    function loadHead() {
    	var columns = [
	    	{ display: '调整单号', name: 'adj_code', width: 120,
	            render:function(ui) {
	                return "<a href=javascript:openUpdate('" + ui.rowData.budg_year + "|" + ui.rowData.adj_code + "')>" + ui.rowData.adj_code + "</a>";
	            }
	        },
	        { display: '预算年度', name: 'budg_year', width: 80 },
	        { display: '调整文号', name: 'adj_file', width: 120,
	            render:function(ui) {
                    var adj_file = ui.cellData;

	                if (adj_file) {
	                    return "<a href=javascript:down('" + adj_file + "')>" + adj_file + "</a>";
	                } else {
	                    return "";
	                }
	            }
	        },
	        { display: '调整说明', name: 'adj_remark', width: 220 },
	        { display: '状态', name: 'state', width: 80,
	            render:function(ui) {
	                if (ui.rowData.state == '01') {
	                    return "新建";
	                }
	                if (ui.rowData.state == '02') {
	                    return "已审核";
	                }
	            }
	        },
	        { display: '调整前预算查看', name: 'last_check_code', width: 120,
	            render:function(ui) {
	                return "<a href=javascript:lookBeforAdjustInfo('" + ui.rowData.budg_year + "|" + ui.rowData.last_check_code + "|" + ui.rowData.bc_state + "')>" + ui.rowData.last_check_code + "</a>";
	            }
	        },
	        { display: '制单人', name: 'make_name', width: 80 },
	        { display: '制单日期', name: 'make_data', width: 90 },
	        { display: '审核人', name: 'check_name', width: 80 },
	        { display: '审核日期', name: 'check_date', width: 90 },
	    ];

	    var paramObj = {
			height: '100%',
            checkbox: true,
            columns: columns,
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                    { type: "button", label: '添加', icon: 'plus', listeners: [{ click: add_open }] },
                    { type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
                    { type: "button", label: '审核', icon: 'check', listeners: [{ click: audit }] },
                    { type: "button", label: '销审', icon: 'closethick', listeners: [{ click: unAudit }] },
                    //{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
                ]
            }
	    };

        grid = $("#maingrid").etGrid(paramObj);
    }

    //打印回调方法
    function lodopPrint() {
        var head = "";
        grid.options.lodop.head = head;
        grid.options.lodop.fn = renderFunc;
        grid.options.lodop.title = "其他支出预算调整";
    }

    function add_open() {
        budg_year = year_input.getValue();
        if (!budg_year) {
            $.etDialog.error('预算年度不能为空!');
            return;
        }
        ajaxPostData({
        	url: "queryCheckDataExists.do?isCheck=false&budg_year=" + budg_year,
        	success: function(responseData) {
        		ajaxPostData({
                	url: "queryBcState.do?isCheck=false&budg_year=" + budg_year,
                	success: function(responseData) {
                        $.etDialog.open({
                            url: 'budgElseCostAdjAddPage.do?isCheck=false&',
                            height: 300,
                            width: 450,
                            title: '其他支出预算调整申请',
                            isMax: true
                        });
                    }
                });
            }
        });
    }

    function remove() {
        var data = grid.selectGetChecked();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return
        }
        if (data.length > 1) {
            $.etDialog.error('每次只能删除一条且单号必需为最大单号的数据!');

        } else {
            var ParamVo = [];

            var adj_code = "";

            var budg_year = year_input.getValue();

            $(data).each(function() {

                adj_code += "'" + this.rowData.adj_code + "',";

                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.adj_code + "@" +
                    (this.rowData.adj_file ? this.rowData.adj_file : "-1")
                );
            });
            //校验 选中数据状态
            ajaxPostData({
            	url: "queryBudgElseCostAdjState.do?isCheck=false",
            	data: {
                    budg_year: budg_year,
                    adj_code: adj_code.substring(0, adj_code.length - 1),
                    state: '01'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("删除失败！" + responseData.adj_code + "单据不是新建状态不允许删除！");
                    } else {
                        $.etDialog.confirm('确定删除?', function() {
                            ajaxPostData({
                            	url: "deleteBudgElseCostAdj.do?isCheck=false",
                            	data: { ParamVo: ParamVo.toString() },
                            	success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
        }
    }

    function openUpdate(obj) {
        var vo = obj.split("|");
        var parm =
            "budg_year=" + vo[0] + "&" +
            "adj_code=" + vo[1];

        $.etDialog.open({
            url: 'budgElseCostAdjUpdatePage.do?isCheck=false&' + parm,
            height: 300,
            width: 450,
            title: '其他支出预算调整申请修改',
            isMax: true,
        });
    }

    //审核的功能
    function audit() {
        var data = grid.selectGetChecked();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return false;
        } else {
            var ParamVo = [];
            var adj_code = "";
            var budg_year = year_input.getValue();
            $(data).each(function() {
                adj_code += "'" + this.rowData.adj_code + "',";
                ParamVo.push(
                    this.rowData.budg_year + "@" +
                    this.rowData.adj_code + "@" +
                    '02'
                )
            });

            //校验 选中数据状态
            ajaxPostData({
            	url: "queryBudgElseCostAdjState.do?isCheck=false",
            	data: {
                    budg_year: budg_year,
                    adj_code: adj_code.substring(0, adj_code.length - 1),
                    state: '01'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("审核失败！" + responseData.check_code + "单据不是新建状态不允许审核！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定要审核选中数据吗?', function(yes) {
                            ajaxPostData({
                            	url: "auditBudgElseCostAdj.do?isCheck=false",
                            	data: { ParamVo: ParamVo.toString() },
                            	success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
        }
    }
    //消除审核的功能
    function unAudit() {
        var data = grid.selectGetChecked();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return false;
        } else {
            var ParamVo = [];
            var adj_code = "";
            var budg_year = year_input.getValue();
            $(data).each(function() {
                adj_code += "'" + this.rowData.adj_code + "',";
                ParamVo.push(
                    this.rowData.budg_year + "@" +
                    this.rowData.adj_code + "@" +
                    this.rowData.last_check_code + "@" +
                    '01'
                );
            });
            //校验 选中数据状态
            ajaxPostData({
            	url: "queryBudgElseCostAdjState.do?isCheck=false",
            	data: {
                    budg_year: budg_year,
                    adj_code: adj_code.substring(0, adj_code.length - 1),
                    state: '02'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("销审失败！" + responseData.check_code + "单据不是审核状态不允许销审！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定要销审选中数据吗?', function() {
                            ajaxPostData({
                            	url: "unAuditBudgElseCostAdj.do?isCheck=false",
                            	data: { ParamVo: ParamVo.toString() },
                            	success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
        }
    }
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', add);
        hotkeys('D', remove);
    }

    function down(adj_file) {
        location.href = "downElseIncomeAdjFile.do?isCheck=false&adj_file=" + adj_file;
    }

    //调整前预算查看
    function lookBeforAdjustInfo(obj) {
        var vo = obj.split("|");
        var parm = "budg_year=" + vo[0] + "&check_code =" + vo[1] + "&bc_state=" + vo[2];
        parent.$.etDialog.open({
            url: 'hrp/budg/budgcost/elsecost/adjust/adjLinkPage.do?isCheck=false&' + parm,
            title: '调整前预算查看',
            isMax: true,
            frameName: window.name, //用于parent弹出层调用本页面的方法或变量
        });
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
                <td class="label">状态： </td>
                <td class="ipt">
                    <select name="" id="state_select" style="width:180px;"></select>
                </td>
                <td class="label"></td>
                <td class="ipt">
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>