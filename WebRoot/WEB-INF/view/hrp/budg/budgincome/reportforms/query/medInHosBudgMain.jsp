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
		var userUpdateStr;
		var budg_year;
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
			loadSelect();
		});
		
		var year_input, subj_name_select, subj_level_select;

		function init(){
			year_input = $("#year_input").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: function () {
                    setTimeout(function () {
                    	query();
                    	reloadSubjName();
                    }, 10);
                },
                defaultDate: true
            });

			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
			function reloadSubjName(value){
				subj_name_select.reload({
					url:"../../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:'04',
						budg_year:year_input.getValue() 
					}
				});
			}
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../queryBudgSubjLevel.do?isCheck=false",
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
			})
		};
function loadSelect(){
			
			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../queryBudgIncomeSubj.do?isCheck=false&subj_type=4&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
		}
		function query(){
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value:subj_name_select.getValue().split(",")[0] },
				{ name: 'subj_level', value: subj_level_select.val() }
			];
			//加载查询条件
	    	grid.loadData(search,'queryMedInHosBudg.do?isCheck=false');
		}
		
		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{ display: '年度', name: 'year', align: 'left', width: 80 },
					{ display: '科目编码', name: 'subj_code', align: 'left', width: 120 },
					{ display: '科目名称', name: 'subj_name', align: 'left', width: 150 },
					{display: '项目', name: 'item', align: 'left', width: 100},
					{display: '本年合计', name: 'year_sum', align: 'right', width: 110,
						render:function(ui) {
							if (ui.rowData.item == '上月结转' || ui.rowData.item == '结转下月') {
								return "";
							}else {
								return formatNumber(ui.rowData.year_sum, 2, 1);
							}
						}
					},
					{ display: '01月(元/E)', name: 'month_1', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_1, 2, 1);
						}
					},
					{ display: '02月(元/E)', name: 'month_2', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_2, 2, 1);
						}
					},
					{ display: '03月(元/E)', name: 'month_3', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_3, 2, 1);
						}
					},
					{ display: '04月(元/E)', name: 'month_4', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_4, 2, 1);
						}
					},
					{ display: '05月(元/E)', name: 'month_5', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_5, 2, 1);
						}
					},
					{ display: '06月(元/E)', name: 'month_6', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_6, 2, 1);
						}
					},
					{ display: '07月(元/E)', name: 'month_7', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_7, 2, 1);
						}
					},
					{ display: '08月(元/E)', name: 'month_8', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_8, 2, 1);
						}
					},
					{ display: '09月(元/E)', name: 'month_9', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_9, 2, 1);
						}
					},
					{ display: '10月(元/E)', name: 'month_10', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_10, 2, 1);
						}
					},
					{ display: '11月(元/E)', name: 'month_11', align: 'right', width: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_11, 2, 1);
						}
					},
					{ display: '12月(元/E)', name: 'month_12', align: 'right', minWidth: 120, editor: { type: 'float' },
						render:function(ui) {
							return formatNumber(ui.rowData.month_12, 2, 1);
						}
					}
				],
				dataModel: {
					method: 'POST',
					location: 'remote',
					url: '',
					recInx: 'year'
				},
				pageModel:{
					type: 'remote',
				},
				summaryRowIndx :[0],
				usePager: true,
				height: '100%', checkbox: false, 
				toolbar: {
					items: [
						{ type:"button" ,label: '查询（Q）', listeners:[{click: query}] , icon: 'search' },
						{ type: "button", label: '打印', icon: 'search', listeners: [{ click: printDate }] },
					]
				}
			});
		}

		var printDate = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={}; 
        	var printPara={
                title: "医院月份医疗收入预算信息",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.budg.service.budgincome.reportforms.query.BudgMedInQueryService",
                method_name: "queryHosMedInQueryPrintDate",
                bean_name: "budgMedInQueryService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
         	
            officeGridPrint(printPara);
        	
        	
        }

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
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
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>
</html>