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
    var year_input, month_input, dept_name_select, mat_type_select;
    var grid;

    $(function() {
        loadHead();
        init();
        loadHotkeys();
    });

    function init() {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                year_input = $("#year_input").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged: function (value){
                    	reloadBudgMatType(value);
                    	queryNew();
                    }
                });
            }
        });

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            todayButton: false,
            onChanged: queryNew
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: queryNew
        });

        mat_type_select = $("#mat_type_select").etSelect({
            
            defaultValue: "none",
            onChange: queryNew
        })
        
		function reloadBudgMatType(year){
			mat_type_select.reload({
				url: "../../../queryBudgMatTypeSubj.do?isCheck=false",
				para:{
					budg_year:year
				}
			})
		}
    }

    function queryNew() {
        var parms = [
            { name: 'year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'mat_type_id', value: mat_type_select.getValue().split(",")[0] }
        ];

        grid.loadData(parms);
    }

    function loadHead() {
        var paramObj = {
            height: '100%',
            checkbox: true,
            rowDblClick: function(event, ui) {
                var rowData = ui.rowData;

                var openParam = [rowData.year, rowData.month, rowData.dept_id, rowData.mat_type_id.split(",")[0]];

                openUpdate(openParam);
            },
            summaryRowIndx: [0] 
        }
        paramObj.columns = [
            { display: '年度', name: 'year', width: "10%" },
            { display: '月', name: 'month', width: "10%"},
            { display: '部门', name: 'dept_name', width:"30%"},
            { display: '物资类别', name: 'mat_type_name', width: "30%" },
            { display: '金额', name: 'amount',  align: 'right',minWidth: "17%",
            	render:function(ui){
            		return formatNumber(ui.rowData.amount,2,1)
            	}
            }
        ];
        paramObj.toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: queryNew }], icon: 'search' },
                { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                { type: 'button', label: '数据采集（<u>C</u>）', listeners: [{ click: collectData }], icon: 'calculator' },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
            ]
        }
        paramObj.dataModel = {
            url: 'queryBudgChargeMatCost.do'
        }
        
        grid = $("#maingrid").etGrid(paramObj);
    }

    function openUpdate(openParam) {
        var parm =
            "year=" + openParam[0] + "&" +
            "month=" + openParam[1] + "&" +
            "dept_id=" + openParam[2] + "&" +
            "mat_type_id=" + openParam[3];

        $.etDialog.open({
            url: 'budgChargeMatCostUpdatePage.do?isCheck=false&' + parm,
            height: 450,
            width: 450,
            title: '科室收费材料支出',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgChargeMatCost()
            }
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgChargeMatCostAddPage.do?isCheck=false&',
            height: 450,
            width: 450,
            title: '科室收费材料支出',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgChargeMatCost()
            }
        });
    }

    function remove() {
        var data = grid.selectGet();

        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];

            data.forEach(function(item) {
                ParamVo.push(
                    item.rowData.group_id + "@" +
                    item.rowData.hos_id + "@" +
                    item.rowData.copy_code + "@" +
                    item.rowData.year + "@" +
                    item.rowData.month + "@" +
                    item.rowData.dept_id + "@" +
                    item.rowData.mat_type_id.split(",")[0]
                )
            })

            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgChargeMatCost.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        queryNew();
                    }
                });
            });
        }
    }
	function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgChargeMatCostImportPage.do?isCheck=false'
		});
		layer.full(index);
   	}	
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
	
    // 数据 采集
    function collectData(){
    	var year = year_input.getValue() ;
    	
    	if(!year){
    		$.etDialog.error('年度不能为空!');
    	}else{
    		
    		ajaxPostData({
                url: "collectData.do?isCheck=false",
                data: { year: year },
                success: function(responseData) {
                    queryNew();
                }
            });
    	}
    }
    
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', queryNew);
        hotkeys('A', add_open);
        hotkeys('C', collectData);
        hotkeys('D', remove);
    }
</script>
</head>

<body>
    <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="toptoolbar"></div>
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
                <td class="label">部门：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">物资类别：</td>
                <td class="ipt">
                    <select name="" id="mat_type_select" style="width:180px;"></select>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>