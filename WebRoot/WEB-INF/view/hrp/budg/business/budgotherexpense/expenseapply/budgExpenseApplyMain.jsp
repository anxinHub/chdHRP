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

    var year_input, dateRange_input, apply_dept_select, duty_dept_select, payment_item_select, state_select;

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
                    onChanged: function(value) {
                        reloadDutyDept(value);
                        query();
                    }
                });
            }
        });

        dateRange_input = $("#dateRange_input").etDatepicker({
            range: true,
            onChanged: query
        });

        apply_dept_select = $("#apply_dept_select").etSelect({
            url: "../../../queryDept.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });

        duty_dept_select = $("#duty_dept_select").etSelect({
        	url: "queryBudgCostDutyDept.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });

        function reloadDutyDept(year) {
            duty_dept_select.reload({
            	
                url: "queryBudgCostDutyDept.do?isCheck=false",
                para: {
                    budg_year: year
                }
            });
        }

        payment_item_select = $("#payment_item_select").etSelect({
            url: "queryBudgPaymentItem.do?isCheck=false&is_read=1",
            defaultValue: "none",
            onChange: query
        });

        state_select = $("#state_select").etSelect({
            url: "../../../queryBudgSysDict.do?isCheck=false&f_code=STATE",
            defaultValue: "none",
            onChange: query
        });

    }

    function query() {
        var apply_date = dateRange_input.getValue();
        var start_date, end_date;
        if (typeof apply_date === "object") {
            start_date = apply_date[0];
            end_date = apply_date[1];
        } else {
            start_date = apply_date;
            end_date = "";
        }
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'apply_date_b', value: start_date },
            { name: 'apply_date_e', value: end_date },
            { name: 'apply_dept', value: apply_dept_select.getValue().split(".")[0] },
            { name: 'duty_dept_id', value: duty_dept_select.getValue() },
            { name: 'payment_item_id', value: payment_item_select.getValue().split(".")[0] },
            { name: 'state', value: state_select.getValue() }
        ];

        grid.loadData(search, 'queryBudgExpenseApply.do');
    }

    //加载表格
    function loadHead() {
        var columns = [
            { display: '费用申报单号', name: 'apply_id', width: 120 },
            { display: '申报科室', name: 'apply_dept_name', width: 120 },
            { display: '支出项目', name: 'payment_item_name', width: 120 },
            { display: '预算金额', name: 'budg_amount', align: 'right', width: 120,
                render: function(ui) {
                    return formatNumber(ui.rowData.budg_amount, 2, 1);
                }
            },
            { display: '申报人员', name: 'apply_emp_name', width: 120 },
            { display: '申报日期', name: 'make_date', width: 120 },
            { display: '审核人', name: 'checker_emp_name', width: 120 },
            { display: '审核日期', name: 'check_date', width: 120 },
            { display: '确认人', name: 'affi_emp_name', width: 120 },
            { display: '确认日期', name: 'affi_date', width: 120 },
            { display: '备注', name: 'remark', width: 120 },
            { display: '状态', name: 'state_name', width: 120 }
        ];

        var toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '审核（<u>S</u>）', listeners: [{ click: audit }], icon: 'audit' },
                { type: 'button', label: '消审（<u>X</u>）', listeners: [{ click: reAudit }], icon: 'right' },
                { type: 'button', label: '确认（<u>C</u>）', listeners: [{ click: affi }], icon: 'audit' },
                { type: 'button', label: '取消确认（<u>C</u>）', listeners: [{ click: reAffi }], icon: 'audit' },
                { type: "button", label: '下载导入模板',icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}] },
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }
            ]
        };

        var paramObj = {
            height: '100%',
            checkbox: true,
            rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                var paramArray = [ rowData.budg_year, rowData.apply_id ];

                openUpdate(paramArray)
            },

            columns: columns,
            toolbar: toolbar,
            summaryRowIndx: [0] 
        }

        grid = $("#maingrid").etGrid(paramObj);

    }
	
    function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
	}
    
    function affi(){
    	 var ParamVo = [];
         var data = grid.selectGet();
         var applyId = "";
         var budg_year;
         
         if (data.length == 0) {
             $.etDialog.error('请选择行');
             return;
         }
         
         $(data).each(function (){
         	applyId += "'" + this.rowData.apply_id + "',";
             ParamVo.push(
            		this.rowData.budg_year   +"@"+ 
 				this.rowData.apply_id   +"@"+ 
 			    '03' 
             );
         })
 		
         //校验 选中数据状态
         ajaxPostData({
             url: "queryBudgExpenseApplyState.do?isCheck=false",
             data: {
             	budg_year : year_input.getValue(),
             	apply_id : applyId.substring(0,applyId.length-1),
 				state : '02'
             },
             success: function(responseData) {
                 if (responseData.state == "false") {
                 	$.etDialog.error("确认失败！所选单据【"+responseData.apply_id+"】不是审核状态不允许确认！");
                     return;
                 } else {
                     $.etDialog.confirm('是否确认?', function() {
                         ajaxPostData({
                             url: "affiOrUnAffi.do?isCheck=false",
                             data: { ParamVo : ParamVo.toString() },
                             success: function(responseData) {
                                 query();
                             }
                         });
                     });
                 }
             }
         })
    }
    
  //销审
    function reAffi() {
        var ParamVo = [];
        var data = grid.selectGet();
        var applyId = "";
        var budg_year;
        
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }

        $(data).each(function () {
        	applyId += "'" + this.rowData.apply_id + "',";
            ParamVo.push(
	          	this.rowData.budg_year   +"@"+ 
				this.rowData.apply_id   +"@"+ 
			    '02' 
            );
        })
		
         //校验 选中数据状态
        ajaxPostData({
            url: "queryBudgExpenseApplyState.do?isCheck=false",
            data: {
            	budg_year : year_input.getValue(),
            	apply_id :applyId.substring(0,applyId.length-1),
				state :'03'
            },
            success: function(responseData) {
                if (responseData.state == "false") {
                	$.etDialog.error("取消确认失败！所选单据【"+responseData.apply_id+"】不是确认状态不允许取消审核！");
                    return;
                } else {
                    $.etDialog.confirm('是否取消确认?', function() {
                        ajaxPostData({
                            url: "affiOrUnAffi.do?isCheck=false",
                            data: { ParamVo : ParamVo.toString() },
                            success: function(responseData) {
                                query();
                            }
                        });
                    });
                }
            }
        })
    }
    //审核
    function audit() {
        var ParamVo = [];
        var data = grid.selectGet();
        var applyId = "";
        var budg_year;
        
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }
        
        $(data).each(function (){
        	applyId += "'" + this.rowData.apply_id + "',";
            ParamVo.push(
           		this.rowData.budg_year   +"@"+ 
				this.rowData.apply_id   +"@"+ 
			    '02' 
            );
        })
		
        //校验 选中数据状态
        ajaxPostData({
            url: "queryBudgExpenseApplyState.do?isCheck=false",
            data: {
            	budg_year : year_input.getValue(),
            	apply_id : applyId.substring(0,applyId.length-1),
				state : '01'
            },
            success: function(responseData) {
                if (responseData.state == "false") {
                	$.etDialog.error("审核失败！所选科目【"+responseData.apply_id+"】不是新建状态不允许审核！");
                    return;
                } else {
                    $.etDialog.confirm('确定审核?', function() {
                        ajaxPostData({
                            url: "auditOrUnAudit.do",
                            data: { ParamVo : ParamVo.toString() },
                            success: function(responseData) {
                                query();
                            }
                        });
                    });
                }
            }
        })
    }

    //销审
    function reAudit () {
        var ParamVo = [];
        var data = grid.selectGet();
        var applyId = "";
        var budg_year;
        
        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }

        $(data).each(function () {
        	applyId += "'" + this.rowData.apply_id + "',";
            ParamVo.push(
	          	this.rowData.budg_year   +"@"+ 
				this.rowData.apply_id   +"@"+ 
			    '01' 
            );
        })
		
         //校验 选中数据状态
        ajaxPostData({
            url: "queryBudgExpenseApplyState.do?isCheck=false",
            data: {
            	budg_year : year_input.getValue(),
            	apply_id :applyId.substring(0,applyId.length-1),
				state :'02'
            },
            success: function(responseData) {
                if (responseData.state == "false") {
                	$.etDialog.error("销审失败！所选科目【"+responseData.apply_id+"】不是审核状态不允许审核！");
                    return;
                } else {
                    $.etDialog.confirm('确定销审?', function() {
                        ajaxPostData({
                            url: "auditOrUnAudit.do",
                            data: { ParamVo : ParamVo.toString() },
                            success: function(responseData) {
                                query();
                            }
                        });
                    });
                }
            }
        })
    }

    //导入
    function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgExpenseApplyImportPage.do?isCheck=false'
		});
		layer.full(index);
	}

    //添加
    function add_open () {
        parent.$.etDialog.open({
            url: 'hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyAddPage.do?isCheck=false&budg_year=' + $("#budg_year").val(),
            title: '费用申报录入',
            isMax: true,
            frameName: window.name
        });
    }

    //修改
    function openUpdate(obj) {
    
 	    var parm = "budg_year=" + obj[0] + "&" + "apply_id=" + obj[1];

        parent.$.etDialog.open({
           url: 'hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyUpdatePage.do?isCheck=false&' + parm.toString(),
           title: '费用申报修改',
           isMax: true,
           frameName: window.name
        });
    }

    //删除
    function remove() {
        var data = grid.selectGet();
        var applyId = "";
        var budg_year;
        
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function () {
            	applyId += "'" + this.rowData.apply_id + "',";
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.apply_id
                )
            });
            //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgExpenseApplyState.do?isCheck=false",
                data: {
                	budg_year : year_input.getValue(),
                	apply_id :applyId.substring(0,applyId.length-1),
    				state :'01'
                },
                success: function(responseData) {
                	console.log(responseData)
                    if (responseData.state == "false") {
                    	$.etDialog.error("删除失败！所选科目【"+responseData.apply_id+"】不是新建状态不允许删除！");
                        return;
                    } else {
                        $.etDialog.confirm('确定删除?', function() {
                            ajaxPostData({
                                url: "deleteBudgExpenseApply.do?isCheck=false",
                                data: { ParamVo : ParamVo.toString() },
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
        hotkeys('I', imp);
        hotkeys('S', audit);
        hotkeys('X', reAudit);
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input">
                </td>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input type="text" id="dateRange_input">
                </td>
                <td class="label">申报科室：</td>
                <td class="ipt">
                    <select name="" id="apply_dept_select" style="width:180px"></select>
                </td>
            </tr>
            <tr>
                <td class="label">归口科室：</td>
                <td class="ipt">
                    <select name="" id="duty_dept_select" style="width:180px"></select>
                </td>
                <td class="label">支出项目：</td>
                <td class="ipt">
                    <select name="" id="payment_item_select" style="width:180px"></select>
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select name="" id="state_select" style="width:180px"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>