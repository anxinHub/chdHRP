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

    $(function() {
        loadHead(); //加载数据
        loadHotkeys();
    });
    //查询
    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'payment_item_id', value: payment_item_select.getValue().split(".")[0] },
            { name: 'dept_id', value: dept_name_select.getValue().split(".")[0] }
        ];

        grid.loadData(search, 'queryBudgExpFixAmount.do?isCheck=false');
    }

    function loadHead() {
        var columns = [
            { display: '支出项目', name: 'payment_item_name', width: "40%",
                render: function(ui) {
                	
                    return '<a href=javascript:openUpdate("'+ ui.rowData.budg_year + "|" + ui.rowData.dept_id + 
                    		"|" + ui.rowData.payment_item_id +'")>' + ui.rowData.payment_item_name + "</a>";
                }
            },
            { display: '科室名称', name: 'dept_name', width: "40%", },
            { display: '定额支出（元)', name: 'fix_amount', align: 'right', width:"18%",
                render: function(ui) {
                    var value = ui.cellData;

                    return formatNumber(value, 2, 1);
                }
            }
        ];

        var paramObj = {
            height: '100%',
            checkbox: true,
            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: openAdd }], icon: 'add' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
    				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
                ]
            }
        }

        grid = $("#maingrid").etGrid(paramObj);
    }

    function openAdd() {
        parent.$.etDialog.open({
            url: 'hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountAddPage.do?isCheck=false&budg_year=' + year_input.getValue(),
            title: '添加',
            isMax: true,
            frameName: window.name
        });
    }

    //
    function openUpdate(obj) {
    	var vo = obj.split("|");
        var parm =
            "budg_year=" + vo[0] + "&" +
            "dept_id=" + vo[1] + "&" +
            "payment_item_id=" + vo[2];

        parent.$.etDialog.open({
            url: 'hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountUpdatePage.do?isCheck=false&' + parm,
            title: '修改',
            isMax: true,
            frameName: window.name
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
                this.rowData.budg_year + "@" +
                this.rowData.dept_id + "@" +
                this.rowData.payment_item_id
            )
        });
        $.etDialog.confirm('确定删除?', function() {
            ajaxPostData({
                url: "deleteBudgExpFixAmount.do",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    query();
                }
            });
        });
    }

    function imp(){
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgExpFixAmountImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	

    //键盘事件
    function loadHotkeys() {

    }
</script>
<script>
    var year_input, payment_item_select, dept_name_select;
    $(function() {
        init();
    });

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
                    onChanged: query
                });
            }
        });

        payment_item_select = $("#payment_item_select").etSelect({
            url: "queryBudgPaymentItem.do?isCheck=false&is_read=1",
            defaultValue: "none",
            onChange: query
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: "queryBudgDeptSelect.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });
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
                <td class="label">支出项目：</td>
                <td class="ipt">
                    <select name="" id="payment_item_select" style="width:180px;"></select>
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>