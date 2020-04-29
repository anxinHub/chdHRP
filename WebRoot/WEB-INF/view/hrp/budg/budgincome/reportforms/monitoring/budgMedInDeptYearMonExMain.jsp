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
		<jsp:param value="select,datepicker,grid,dialog,ligerUI,pageOffice" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
	
		var year_input, subj_name_select, subj_level_select,dept_name_select;

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
				url:"../../../queryBudgIncomeSubj.do?isCheck=false&subj_type=4&budg_year="+year_input.getValue(),
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

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../../queryBudgDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			})
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

		function query(){
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value:subj_name_select.getValue().split(",")[0] },
				{ name: 'subj_level', value: subj_level_select.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() }
			];
			//加载查询条件
			grid.loadData(search,'queryBudgMedInDeptYearMonEx.do?isCheck=false');
		}
		
		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '年度', name: 'year', align: 'left', width: 70},
					{display: '科室编码', name: 'dept_code', align: 'left', width: 100},
					{display: '科室名称', name: 'dept_name', align: 'left', width: 100},
					{display: '科目编码', name: 'subj_code', align: 'left', width: 100},
					{display: '科目名称', name: 'subj_name', align: 'left', width: 100},
					{display: '执行数', name: 'amount', align: 'right', width: 110
					},
					{display: '预算数', name: 'budg_value', align: 'right', width: 110
					},
					{display: '执行率', name: 'amoun', align: 'right', width: 110,
						render:function(ui) {
								return formatNumber(ui.rowData.amoun, 2, 1) + "%";
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
				width: '100%',
				height: '100%',
				checkBox: false,
				toolbar: {
	                items: [
	                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
	                    { type: "button", label: '打印', icon: 'search', listeners: [{ click: printDate }] },
	                ]
	            },
			});
		}

	
		var printDate = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={}; 
        	var printPara={
                title: "科室医疗收入预算执行监控统计数据表",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.budg.service.budgincome.reportforms.monitoring.BudgMedInDeptYearMonExService",
                method_name: "queryMedInDeptYearMonExPrintDate",
                bean_name: "budgMedInDeptYearMonExService",
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
			hotkeys('P', printDate);
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
			<tr>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="ipt"></td>
				<td class="label"></td>
				<td class="ipt"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>