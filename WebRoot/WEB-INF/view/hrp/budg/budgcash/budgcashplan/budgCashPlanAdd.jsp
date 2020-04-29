<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<style>
    #remark {
        width: 400px;
        height: 30px;
        outline: none;
        resize: none;
        border: 1px solid #aecaf0;
    }
</style>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var addData; // 记录添加数据用变量
    var budg_year; // = liger.get("budg_year").getValue();
    var plan_code; //= $("#plan_code").val();
    var cash_item_name; //现金流量项目名称
    var event; // = $("#event").val();
    var remark; //= $("#remark").val();
	
    var year_input;
	$(function(){
		init();
	});


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
    }
    
    $(function() {
        //加载数据
        loadHead(null);
    });

    function loadHead() {
        grid = $("#maingrid").etGrid({
            columns: [{
                    display: '现金流量类别',
                    name: 'cash_type_name',
                    align: 'left',
                    width: 150,
                    editor: {
                        type: 'select',
                        keyField: 'cash_type_id',
                        url: '../../queryCashType.do?isCheck=false&is_stop=0'
                    }
                },
                {
                    display: '现金流量项目',
                    name: 'cash_item_name',
                    align: 'left',
                    width: 200,
                    editor: {
                        type: 'select',
                        keyField: 'cash_item_id',
                        url: 'queryCashItem.do?isCheck=false&cash_type_id=',
                        create: function(rowData, cellData, setting) {
                            if (rowData.cash_type_id) {
                                var cash_type_id = rowData.cash_type_id;
                                setting.url = 'queryCashItem.do?isCheck=false&cash_type_id=' + cash_type_id;
                                // alert(11111)
                            } else {
                                $.etDialog.warn('请先填写现金流量类别');
                                return false;
                            }
                        },
                        change: function(rowData) {
                            // 异步是因为，现在change事件之后，行数据实际还未更新
                            setTimeout(function() {
                                cash_item_name = rowData.cash_item_name.split(" ")[1];
                            }, 100)
                        }
                    }
                },
                {
                    display: '合计(元)',
                    name: 'count',
                    align: 'right',
                    width: 120,
                    editable:false,
                    render: function(ui) {
                        var sum = Number(ui.rowData.m01) + Number(ui.rowData.m02) + Number(ui.rowData.m03) + Number(ui.rowData.m04) +
                            Number(ui.rowData.m05) + Number(ui.rowData.m06) + Number(ui.rowData.m07) + Number(ui.rowData.m08) +
                            Number(ui.rowData.m09) + Number(ui.rowData.m10) + Number(ui.rowData.m11) + Number(ui.rowData.m12);
                        return formatNumber(sum, 2, 1);
                        
                    }
                },
                {
                    display: '01月(E)',
                    name: 'm01',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '02月(E)',
                    name: 'm02',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '03月(E)',
                    name: 'm03',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '04月(E)',
                    name: 'm04',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '05月(E)',
                    name: 'm05',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '06月(E)',
                    name: 'm06',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '07月(E)',
                    name: 'm07',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '08月(E)',
                    name: 'm08',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '09月(E)',
                    name: 'm09',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '10月(E)',
                    name: 'm10',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '11月(E)',
                    name: 'm11',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                },
                {
                    display: '12月(E)',
                    name: 'm12',
                    align: 'right',
                    width: 120,
                    editor: { type: 'float' },
                    render: function(ui) {
                        var value = ui.cellData;
                        return formatNumber(value, 2, 1);
                    }
                }
            ],
            dataModel: {
                method: 'POST',
                location: 'remote',
                url: '',
                recIndx: 'year'
            },
            usePager: true,
            width: '100%',
            height: '100%',
            checkbox: true,
            editable: true,
            addRowByKey: true,
            editorEnd: function (event, ui) {
                // 判断当月份列编辑结束后刷新合计单元格
                if (/^m\d/.test(ui.dataIndx)) {
                    grid.refreshCell(ui.rowIndx, 'count');
                }
            },
            toolbar: {
                items: [
                    { type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
                    { type: "button", label: '删除', icon: 'minus', listeners: [{ click: del }] },
                    { type: 'button', label: '添加行', listeners: [{ click: addRow }], icon: 'add' },
                    { type: "button", label: '关闭', icon: 'close', listeners: [{ click: close }] },
                ]
            },
        });
    }
    //添加一行
    function addRow() {
        grid.addRow();
    }

    function save() {
        event = $("#event").val();
        if (!event) {
            $.etDialog.warn('资金流动事项必填,不得为空,请填写后操作!');
            return false;
        }
        var data = grid.getAllData();
        
        if (data.length == 0) {
            $.etDialog.warn('请添加行数据');
        } else {
            if (!validateGrid(data)) {
                return false;
            }
            budg_year = year_input.getValue();
            plan_code = $("#plan_code").val();
            remark = $("#remark").val();
            var ParamVo =[];
 	        $(data).each(function (){	
 	        	ParamVo.push( budg_year +"@"+ "01" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m01? this.m01:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "02" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m02? this.m02:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "03" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m03? this.m03:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "04" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m04? this.m04:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "05" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m05? this.m05:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "06" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m06? this.m06:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "07" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m07? this.m07:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "08" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m08? this.m08:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "09" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m09? this.m09:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "10" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m10? this.m10:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "11" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m11? this.m11:"-1") +"@"+ cash_item_name);
 	        	ParamVo.push( budg_year +"@"+ "12" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m12? this.m12:"-1") +"@"+ cash_item_name);
 	        });
 	        ajaxPostData({
 	            url: 'addBudgCashPlan.do?isCheck=false',
 	            data: {ParamVo : ParamVo.toString()},
 	            success: function (res) {
 	            	parentQuery();
 	            }
 	        })
        }
    }
	
    //父页面查询
    function parentQuery(){
    	var parentFrameName = parent.$.etDialog.parentFrameName;
    	var parentWindow = parent.window[parentFrameName];
    	parentWindow.query();
    }
    
    function del() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.warn('请选择要删除的行!');
            return;
        } else {
            grid.deleteRows(data);
        }
    }

    function validateGrid(data) {
        var msg = "";
        var rowm = "";
        //判断grid 中的数据是否重复或者为空
        var targetMap = new HashMap();
        $.each(data, function(i, v) {
            rowm = "";
            if (v.cash_item_id == "" || v.cash_item_id == null || v.cash_item_id == 'undefined') {
                rowm += "[现金流量项目]、";
            }

            if (rowm != "") {
                rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
            }
            msg += rowm;
            var key = v.cash_type_id + v.cash_item_id
            var value = "第" + (i + 1) + "行";
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
    //关闭
    function close() {
    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
    	parent.$.etDialog.close(curIndex);
    }

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="toptoolbar"></div>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">资金计划单号：</td>
                <td class="ipt">
                    <input type="text" id="plan_code" disabled value="系统自动生成" />
                </td>
                <td class="label no-empty">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="budg_year" />
                </td>
                <td class="label no-empty">资金流动事项：</td>
                <td class="ipt">
                    <input type="text" id="event" style="width:240px;" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt" colspan="3">
                    <input type="text" id="remark" style="width:480px;" />
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>