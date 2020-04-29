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
    var year;
    //打印 单元格格式化 用
    var renderFunc = {

        last_cost: function(value) { //上年支出
            return formatNumber(value, 2, 1);
        },
        grow_rate: function(value) { //增长比例
            return formatNumber(value, 2, 1) + "%";
        },
        grow_value: function(value) { //增长额 
            return formatNumber(value, 2, 1);
        },
        budg_value: function(value) { //支出预算
            return formatNumber(value, 2, 1);
        }
    };
    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });
    
    var year_input, month_input, subj_code_select;

    function init() {
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function (value) {
                setTimeout(function () {
                	reloadSubjName(value);
                	query();
                }, 10);
            },
            defaultDate: true
        });
    	

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            todayButton: false,
            showNav: false,
            onChanged: query
        });
		
        subj_code_select = $("#subj_code_select").etSelect({
			defaultValue: "none",
			onChange: query,
		});
                
		//再次加载科目名称
		function reloadSubjName(value) {
			var url = "../../../queryBudgSubj.do?isCheck=false?isCheck=false";
			subj_code_select.reload({
				url: url,
				type: "POST",
				para: {
					subj_type: '05',
					is_last:'1',
					budg_year: value
				}
			});
		}
		
		reloadSubjName(year_input.getValue());
    }
    
    //查询
    function query() {
        if (!year_input.getValue()) {
            $.etDialog.error('年度不能为空');
            return false;
        }

        var parms = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'subj_code', value: subj_code_select.getValue() }
        ];

        //加载查询条件
        grid.loadData(parms, 'queryBudgElseCost.do?isCheck=false');
    }

    function loadHead() {
        grid = $("#maingrid").etGrid({
            columns: [{
                    display: '年度',
                    name: 'budg_year',
                    align: 'center',
                    minWidth: '5%',
                    editable: setEdit,
                    dataType: 'string',
                    editor: {
                        valueField: 'id',
                        textField: 'text',
                        type: 'select', //编辑框为下拉框时
                        //source:[],   //  静态数据接口  也可以是回调函数返回值
                        url: '../../../queryBudgYearTen.do?isCheck=false', //  动态数据接口
                        change: function(rowdata, celldata) {
                            grid.updateRow(celldata.rowIndx, { subj_name: "" })
                        }
                    },
                },
                {
                    display: '月份',
                    name: 'month',
                    align: 'center',
                    width: '5%',
                    editable: setEdit,
                    dataType: 'string',
                    editor: {
                        valueField: 'monthID',
                        textField: 'label',
                        type: 'select', //编辑框为下拉框时
                        source: [
                            { id: "01", label: "1月" },
                            { id: "02", label: "2月" },
                            { id: "03", label: "3月" },
                            { id: "04", label: "4月" },
                            { id: "05", label: "5月" },
                            { id: "06", label: "6月" },
                            { id: "07", label: "7月" },
                            { id: "08", label: "8月" },
                            { id: "09", label: "9月" },
                            { id: "10", label: "10月" },
                            { id: "11", label: "11月" },
                            { id: "12", label: "12月" }
                        ], //  静态数据接口  也可以是回调函数返回值
                        change: setLastCost, //根据填写数据查询上年支出
                    }
                },
                {
                    display: '支出科目',
                    name: 'subj_name',
                    align: 'left',
                    width: '27%',
                    dataType: 'string',
                    editable: setEdit,
                    editor: {
                        valueField: 'code',
                        textField: 'name',
                        type: 'select', //编辑框为下拉框时
                        url: 'queryBudgCostSubj.do?isCheck=false&budg_year=' + year, //  动态数据接口
                        change: setLastCost, //根据填写数据查询上年支出
                        //与年度联动查询
                        create: function(rowdata, celldata, setting) {
                            if (rowdata.id) {
                                setting.url = 'queryBudgCostSubj.do?isCheck=false&budg_year=' + rowdata.id;
                            } else {
                                $.etDialog.error('请先填写年度');
                                return false;
                            }
                        },
                    }
                },
                {
                    display: '上年支出',
                    name: 'last_cost',
                    align: 'right',
                    dataType: 'float',
                    width: "10%",
                    editable: function(ui) {
                        if (ui.rowData && !ui.rowData.grow_value) {
                            return true;
                        } else if (!ui.rowData) {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    editor: {
                        type: 'textbox', // 文本框
                        change: countBudgValue, //编辑后 计算 支出预算
                    },
                    render:function(ui) {
                        if (ui.rowData.last_cost) {
                            return formatNumber(ui.rowData.last_cost, 2, 1);
                        }
                    }
                },
                {
                    display: '增长比例',
                    name: 'grow_rate',
                    align: 'right',
                    dataType: 'float',
                    width: "10%",
                    editable: function(ui) {
                        if (ui.rowData && !ui.rowData.grow_value) {
                            return true;
                        } else if (!ui.rowData) {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    editor: {
                        type: 'textbox', // 文本框
                        change: countBudgValue, //编辑后 计算 支出预算
                    },
                    render:function(ui) {
                        if (ui.rowData.grow_rate) {
                            return formatNumber(ui.rowData.grow_rate, 2, 1) + "%";
                        }
                    }
                },
                {
                    display: '增长额',
                    name: 'grow_value',
                    align: 'right',
                    dataType: 'float',
                    width: "10%",
                    editable: function(ui) {
                        if (ui.rowData && !ui.rowData.grow_rate) {
                            return true;
                        } else if (!ui.rowData) {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    editor: {
                        type: 'textbox', // 文本框
                        change: countBudgValue, //编辑后 计算 支出预算
                    },

                    render:function(ui) {
                        if (ui.rowData.grow_value) {
                            return formatNumber(ui.rowData.grow_value, 2, 1);
                        }
                    }
                },
                {
                    display: '支出预算',
                    name: 'budg_value',
                    align: 'right',
                    dataType: 'float',
                    width: "10%",
                    editable: false,
                    render:function(ui) {
                        if (ui.rowData.budg_value) {
                            return formatNumber(ui.rowData.budg_value, 2, 1);
                        }
                    }
                },
                {
                    display: '说明',
                    name: 'remark',
                    align: 'left',
                    dataType: 'string',
                    width: "20%",
                    editor: {
                        type: 'textbox', // 文本框
                    }
                }
            ],
            dataModel: {
                method: 'POST',
                location: 'remote',
                url: '',
                recIndx: 'budg_year' //必填 且该列不能为空  
            },
            usePager: true,
            width: '100%',
            height: '97%',
            checkbox: true,
            editable: true,
            addRowByKey: true,
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                    { type: "button", label: '生成', icon: 'plus', listeners: [{ click: addBudgElseCost }] },
                    { type: "button", label: '添加行', icon: 'plus', listeners: [{ click: add_Row }] },
                    { type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
                    { type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
                    //{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
                    { type: "button", label: '批量设置', icon: 'wrench', listeners: [{ click: setBatchBudgElseCost }] },
                ]
            },
        });
    }

    //打印回调方法
    function lodopPrint() {
        var head = "";
        grid.options.lodop.head = head;
        grid.options.lodop.fn = renderFunc;
        grid.options.lodop.title = "其他支出预算";
    }

    // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui) {
        if (ui.rowData && ui.rowData.group_id) {
            return false;
        } else {
            return true;
        }
    }
    // 填写 年度 、月份 、支出科目后 查询 其上年支出
    function setLastCost(rowdata, celldata) {

        if (rowdata.id && rowdata.monthID && rowdata.code) {

            var param = {
                budg_year: rowdata.id,
                month: rowdata.monthID,
                subj_code: rowdata.code
            };

            ajaxPostData({
            	url: "setLastCost.do?isCheck=false",
            	data: param,
            	success: function(responseData) {
                    grid.updateRow(celldata.rowIndx, { last_cost: Number(responseData.last_cost) })
                }
            });
        }
    }

    //编辑 上年支出、增长比例、增长额后 计算并修改支出预算 
    function countBudgValue(rowdata, celldata) {
        if (rowdata.grow_rate < 0 || rowdata.grow_rate > 100) {
            $.etDialog.error('增长比例输入不合法,只能输入大于0小于等于100的数值!');
            return false;
        }

        if (rowdata.last_cost || rowdata.last_cost == 0) {

            if (rowdata.grow_rate) {

                var budg_value = Number(rowdata.last_cost) * (1 + Number(rowdata.grow_rate/100.00 ))

                if (budg_value == 0) {

                    grid.updateRow(celldata.rowIndx, { budg_value: 0.00 })

                } else {

                    grid.updateRow(celldata.rowIndx, { budg_value: budg_value })
                }

            } else if (rowdata.grow_value) {

                var budg_value = Number(rowdata.last_cost) + Number(rowdata.grow_value)

                if (budg_value == 0) {

                    grid.updateRow(celldata.rowIndx, { budg_value: 0.00 })

                } else {

                    grid.updateRow(celldata.rowIndx, { budg_value: budg_value })
                }
            }

        } else {
            $.etDialog.error('请先填写年度、月份、支出科目数据!');
        }
    }

    // 生成 
    function addBudgElseCost() {
        if (year_input.getValue()) {
            ajaxPostData({
            	url: "addBudgElseCost.do?isCheck=false&budg_year=" + year_input.getValue(),
            	success: function(responseData) {
                    query();
                }
            });
        } else {
            $.etDialog.error('年度不能为空!');
        }
    }

    //添加行 
    function add_Row() {
        grid.addRow();
    }

    //保存 
    function save() {
        var data = grid.getChanges();
        var ParamVo = [];

        if (data.addList.length > 0 || data.updateList.length > 0) {

            if (data.addList.length > 0) {

                var addData = data.addList;
               
                if (!validateGrid(addData)) {
                    return false;
                }
                $(addData).each(function() {

                    ParamVo.push(
                        this.id + "@" +
                        this.monthID + "@" +
                        this.code + "@" +
                        this.last_cost + "@" +
                        (this.grow_rate ? this.grow_rate : "") + "@" +
                        (this.grow_value ? this.grow_value : "") + "@" +
                        this.budg_value + "@" +
                        (this.remark ? this.remark : "") + "@" +
                        this._rowIndx + "@" + //行号 错误时 标识行数据用 
                        '1' //添加数据标识
                    )
                });
            }
            if (data.updateList.length > 0) {

                var updateData = data.updateList;
                $(updateData).each(function() {
                    ParamVo.push(
                        this.budg_year + "@" +
                        this.month + "@" +
                        this.subj_code + "@" +
                        this.last_cost + "@" +
                        (this.grow_rate ? this.grow_rate : "") + "@" +
                        (this.grow_value ? this.grow_value : "") + "@" +
                        this.budg_value + "@" +
                        (this.remark ? this.remark : "") + "@" +
                        this._rowIndx + "@" + //行号 错误时 标识行数据用 
                        '2' //修改数据标识
                    )
                });
            }
            ajaxPostData({
            	url: "saveBudgElseCost.do?isCheck=false",
            	data: { ParamVo: ParamVo.toString() },
            	success: function(responseData) {
                    query();
                }
            });
        } else {
            $.etDialog.warn('没有需要保存的数据!');
        }
    }

    //删除
    function remove() {

        var data = grid.selectGetChecked();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = []; //后台删除数据
            var deletePageRow = []; // 页面删除数据
            $(data).each(function() {
                if (this.rowData.group_id) {
                    ParamVo.push(
                        this.rowData.group_id + "@" +
                        this.rowData.hos_id + "@" +
                        this.rowData.copy_code + "@" +
                        this.rowData.budg_year + "@" +
                        this.rowData.month + "@" +
                        this.rowData.subj_code
                    )
                } else {
                    deletePageRow.push(this);
                }
            });
            if (ParamVo.length > 0) {
                $.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                    	url: "deleteBudgElseCost.do?isCheck=false",
                    	data: { ParamVo: ParamVo.toString() },
                    	success: function(responseData) {
                            query();
                        }
                    });
                });
            } else if (deletePageRow.length > 0) {
                grid.deleteRows(deletePageRow);
                $.etDialog.success("删除成功!");
            }
        }
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
            content: 'budgElseCostImportPage.do?isCheck=false'
        });
        layer.full(index);
    }

    function downTemplate() {
        location.href = "downTemplate.do?isCheck=false";
    }
    // 修改页面跳转  （暂时不用） 
    function openUpdate(obj) {
        var vo = obj.split("|");
        var parm =
            "group_id=" + vo[0] + "&" +
            "hos_id=" + vo[1] + "&" +
            "copy_code=" + vo[2] + "&" +
            "budg_year=" + vo[3] + "&" +
            "month=" + vo[4] + "&" +
            "subj_code=" + vo[5]

        $.etDialog.open({
            url: 'budgElseCostUpdatePage.do?isCheck=false&' + parm,
            title: '其他支出预算执行修改',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgElseCost()
            }
        });
    }

    //批量设置 
    function setBatchBudgElseCost() {
        var data = grid.selectGetChecked();

        if (data.length == 0) {

            $.etDialog.warn('请选择要设置的行数据!');

            return false;
        }

        $.etDialog.open({
            url: 'budgElseCostUpdateBatchPage.do?isCheck=false',
            height: 300,
            width: 450,
            title: '其他支出预算批量设置',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgElseCost()
            }
        });
    }

    // 添加 校验数据 
    function validateGrid(data) {
        var msg = "";
        var rowm = "";
        //判断grid 中的数据是否重复或者为空
        var targetMap = new HashMap();
        $.each(data, function(i, v) {
            rowm = "";
            if (v.budg_year || v.month || v.subj_name || v.amount) {

                if (!v.budg_year) {
                    rowm += "[年度]、";
                }
                if (!v.month) {
                    rowm += "[月份]、";
                }
                if (!v.subj_name) {
                    rowm += "[支出科目]、";
                }
                if (!v.last_cost && Number(v.last_cost) != 0) {
                    rowm += "[上年支出]、";
                }
                if (!v.budg_value && Number(v.budg_value) != 0) {
                    rowm += "[支出预算]、";
                }
            }

            if (rowm != "") {
                rowm = "第" + (v._rowIndx + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
            }
            msg += rowm;
            var key = v.budg_year + v.month + v.subj_name
            var value = "第" + (v._rowIndx + 1) + "行";
            
            if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
                targetMap.put(key, value);
            } else {
                msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
            }
        });
        if (msg != "") {
            $.etDialog.warn(msg);
            return false;
        } else {
            return true;
        }
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', add_Row);
        hotkeys('S', save);
        hotkeys('D', remove);
        hotkeys('I', imp);
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
                <td class="label">預算科目：</td>
                <td class="ipt">
                    <select name="" id="subj_code_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>