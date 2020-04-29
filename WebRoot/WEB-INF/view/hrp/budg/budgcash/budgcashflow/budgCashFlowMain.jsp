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
	<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year;
    $(function (){
    	//加载数据
    	loadHead(null);	
    	init();
    });
    
    var year_input,month_input,cash_dire,cash_type_id,cash_item_id;
    
	function init(){
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


		month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false,
			onSelect:query
		});

		cash_dire = $("#cash_dire").etSelect({
			url:'../../queryCashDire.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});

		cash_type_id = $("#cash_type_id").etSelect({
			url:'../../queryCashType.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});
		
		cash_item_id = $("#cash_item_id").etSelect({
			url:'../../queryCashItem.do?isCheck=false',
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
	};
    
    //查询
    function  query(){
    	var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() },
			{ name: 'cash_dire', value: cash_dire.getValue() },
			{ name: 'cash_type_id', value: cash_type_id.getValue() },
			{ name: 'cash_item_id', value: cash_item_id.getValue() }
		];
    	//加载查询条件
		grid.loadData(parms, '');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
              { display: '预算年度', name: 'budg_year', align: 'left',width:'10%'
				},
              { display: '月份', name: 'month', align: 'left',width:'5%'
				},
              { display: '现金流量类别', name: 'cash_type_name', align: 'left',width:'25%'
				},
              { display: '现金流量项目', name: 'cash_item_name', align: 'left',width:'38%'
				},
              { display: '方向', name: 'direName', align: 'left',width:'5%'
				},
              { display: '金额', name: 'amount', align: 'right',minWidth:150,
					render:function(ui){
						var value = ui.cellData;
						if(value){
							return formatNumber(value,2,1);
						}else{
							return formatNumber(0,2,1);
						}
					}
				}
           ],
           dataModel:{
	           method:'POST',
	           location:'remote',
	           url:'queryBudgCashFlow.do?isCheck=false',
	           recIndx: 'budg_year' //必填 且该列不能为空  
           },
           usePager:false,width: '100%', height: '100%',checkbox: true,
           toolbar: {
	           items: [
		           { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				   { type: "button", label: '计算',icon:'contact',listeners: [{ click: count}] },
	           ]
           },
       });
    }
    
    //计算   1 后台查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总 之后  插入或更新入现金流量表   budg_cash_flow
    //2 对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
    function count(){
    	var param = {
    		budg_year : year_input.getValue()
    	}
    	ajaxPostData({
            url: "collectBudgCashFlow.do?isCheck=false",
            data: param,
            success: function(responseData) {
                query();
            }
        });
    }
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
				<td class="label">方向：</td>
				<td class="ipt">
					<select name="cash_dire" id="cash_dire" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label" style="width:120px;" >现金流量类别：</td>
				<td class="ipt">
					<select name="cash_type_id" id="cash_type_id" style="width:180px;"></select>
				</td>
				<td class="label" style="width:120px;" >现金流量项目： </td>
				<td class="ipt">
					<select name="cash_item_id" id="cash_item_id" style="width:180px;"></select>
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
