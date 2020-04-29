<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;

    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    var naturs_code_select, ass_name_select, dept_name_select, use_state_select, is_throw_select, is_depr_select;

    function init () {
        naturs_code_select = $("#naturs_code_select").etSelect({
            url: '../../../queryAssNaturesInassets.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
        });

        ass_name_select = $("#ass_name_select").etSelect({
            url: '../../../queryAssDictInassets.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: '../../../queryBudgDeptDictAll.do?isCheck=false&is_last=1',
            defaultValue: 'none',
            onChange: query
        });

        use_state_select = $("#use_state_select").etSelect({
            url: '../../../../ass/card/queryAssCardUseStateDict.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
        });

        is_throw_select = $("#is_throw_select").etSelect({
            url: '../../../../mat/queryMatYearOrNo.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
        });

        is_depr_select = $("#is_depr_select").etSelect({
            url: '../../../../mat/queryMatYearOrNo.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'ass_card_no', value: $("#card_no_input").val() },
            { name: 'ass_id', value: ass_name_select.getValue() },
            { name: 'naturs_code', value: naturs_code_select.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'use_state', value: use_state_select.getValue() },
            { name: 'is_throw', value: is_throw_select.getValue() },
            { name: 'is_depr', value: is_depr_select.getValue() }
        ];

        grid.loadData(search,'queryBudgAssCardIntangible.do?isCheck=false');
    }

    function loadHead () {
    	var columns = [
			{ display: '卡片编号', name: 'ass_card_no', width: 120 },
            { display: '资产名称', name: 'ass_name', width: 80 },
            { display: '管理科室', name: 'dept_name', width: 80 },
            { display: '资产原值', name: 'price', width: 100,align: 'right',
            	render:function(ui){
            		return formatNumber(ui.rowData.price,2,1)
            	}
            },
            { display: '累计折旧', name: 'depre_money', width: 100,align: 'right',
            	render:function(ui){
	        		return formatNumber(ui.rowData.depre_money,2,1)
	        	}
            },
            { display: '资产净值', name: 'cur_money', width: 100,align: 'right',
            	render:function(ui){
	        		return formatNumber(ui.rowData.cur_money,2,1)
	        	}	
            },
            { display: '预留残值', name: 'fore_money', width: 100,align: 'right',
            	render:function(ui){
	        		return formatNumber(ui.rowData.fore_money,2,1)
	        	}
            },
            { display: '上次折旧年', name: 'last_depr_year', width: 80 },
            { display: '上次折旧月', name: 'last_depr_month', width: 80 },
            { display: '累计折旧月份', name: 'add_depre_month', width: 80 },
            { display: '使用状态', name: 'state_name', width: 80 },
            { display: '在用状态', name: 'is_dept_text', width: 80 },
            { display: '仓库', name: 'store_name', width: 140 },
            { display: '是否投放', name: 'is_throw_text', width: 80 },
            { display: '是否折旧', name: 'is_depr_text', width: 80 },
            { display: '折旧方法', name: 'ass_depre_name', width: 200 },
            { display: '计提年数', name: 'acc_depre_amount', width: 80 },
            { display: '备注', name: 'note', width: 140 }
        ];

        var toolbar = {
            items: [
                { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '下载资产现状模板（<u>B</u>）', listeners: [{ click: downTemplate1 }], icon: 'down' },
                { type: 'button', label: '导入资产现状（<u>I</u>）', listeners: [{ click: imp1 }], icon: 'up' },
                { type: 'button', label: '下载资金来源模板（<u>B</u>）', listeners: [{ click: downTemplate2 }], icon: 'down' },
                { type: 'button', label: '导入资金来源（<u>I</u>）', listeners: [{ click: imp2 }], icon: 'up' }
            ]
        }

        var paramObj = {
        	height: '100%',
            checkbox: true,
            rowDblClick: function (event, ui) {
                var rowData = ui.rowData;

                openUpdate(rowData.ass_card_no,rowData.naturs_code);
            },

            columns: columns,
            toolbar: toolbar,
            dataModel: {
            	url: 'queryBudgAssCardIntangible.do'
            }
        }
        grid = $("#maingrid").etGrid(paramObj);
    }

    function add_open() {
        parent.$.etDialog.open({
            url: 'hrp/budg/business/intangible/budgasscard/budgAssCardAddPage.do?isCheck=false&',
            height: 300,
            width: 450,
            title: '添加',
            isMax: true,
            frameName: window.name,
            btn: ['取消']
        });
    }

    function remove() {

        var data = grid.selectGet();

        if (data.length === 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.ass_card_no
                )
            });
            $.etDialog.confirm('确定删除?', function(yes) {
                ajaxPostData({
                	url: "deleteBudgAssCard.do?isCheck=false",
                	data: { ParamVo: ParamVo.toString() },
                	success: function(responseData) {
                        query();
                    }
                });
            });
        }
    }

    function imp1 () {
        $.etDialog.open({
            url: 'budgAssIntangibleCardImportPage.do?isCheck=false',
            title: '导入',
            width: '893px',
            height: '500px',
            maxmin: true,
            isMax: true
        })
    }

    function downTemplate1() {
        location.href = "downTemplate1.do?isCheck=false";
    }
	
    function imp2 () {
        $.etDialog.open({
            url: 'budgAssIntangibleCardSourceImportPage.do?isCheck=false',
            title: '导入',
            width: '893px',
            height: '500px',
            maxmin: true,
            isMax: true
        })
    }

    function downTemplate2() {
        location.href = "downTemplate2.do?isCheck=false";
    }

    function openUpdate (ass_card_no,naturs_code) {
        var parm = "ass_card_no=" + ass_card_no +"&naturs_code="+naturs_code;

        parent.$.etDialog.open({
            url: 'hrp/budg/business/intangible/budgasscard/budgAssCardUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 300,
            width: 450,
            title: '更新',
            isMax: true,
            frameName: window.name,
            btn: ['关闭']
        });
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('A', add);
        hotkeys('D', remove);
        hotkeys('B', downTemplate1);
    }

</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">资产性质：</td>
                <td class="ipt">
                    <select name="" id="naturs_code_select" style="width:150px;"></select>
                </td>
                <td class="label">卡片编号：</td>
                <td class="ipt">
                    <input type="text" id="card_no_input" style="width:150px;" />
                </td>
                <td class="label">资产名称：</td>
                <td class="ipt">
                    <select name="" id="ass_name_select" style="width:150px;"></select>
                </td>
                <td class="label">管理部门：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:150px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">使用状态：</td>
                <td class="ipt">
                    <select name="" id="use_state_select" style="width:150px;"></select>
                </td>
                <td class="label">是否投放：</td>
                <td class="ipt">
                    <select name="" id="is_throw_select" style="width:150px;"></select>
                </td>
                <td class="label">是否折旧：</td>
                <td class="ipt">
                    <select name="" id="is_depr_select" style="width:150px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>