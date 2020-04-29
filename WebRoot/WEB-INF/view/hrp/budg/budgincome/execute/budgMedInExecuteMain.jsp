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
		<jsp:param value="select,datepicker,dialog,grid" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		var budg_year;

		//打印 单元格格式化 用
		var renderFunc = {
			amount: function (value) { //余额  
				return formatNumber(value, 2, 1);
			}
		};
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
		
		var year_input,month_input,subj_name_select,/* subj_level_select, */dept_name_select;
		
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
		function init() {
			getData("../../queryBudgYear.do?isCheck=false", function (data) {
				year_input = $("#year_input").etDatepicker({
					defaultDate: data[0].text,
					view: "years",
					minView: "years",
					dateFormat: "yyyy",
				/* 	minDate: data[data.length - 1].text,
					maxDate: data[0].text, */
					todayButton: false,
					onSelect: function (value) {
						reloadSubjName(value);
						setTimeout(function () {
							query();
						}, 10);
					}
				});
				reloadSubjName(data[0].text);
			});

			month_input = $("#month_input").etDatepicker({
				view:'months',
				minView:'months',
				dateFormat:"mm",
				todayButton:false,
				showNav:false,
				onSelect:query
			});

			subj_name_select = $("#subj_name_select").etSelect({
				defaultValue: "none",
				onChange: query
			});
			function reloadSubjName(year){
				subj_name_select.reload({
					url:"../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:'04',
						budg_year:year ,
						is_last : 1
					}
				});
			}

			/* subj_level_select = $("#subj_level_select").etSelect({
				url:'../../queryBudgSubjLevel.do?isCheck=false',
				defaultValue: "none",
				onChange: query
			});
 */
			dept_name_select = $("#dept_name_select").etSelect({
				url:'../../queryDept.do?isCheck=false',
				defaultValue: "none",
				onChange: query
			});
		}

		function query(){
			var year = year_input.getValue();
			var search=[
				{ name: 'year', value: year },
				{ name: 'month', value: month_input.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				//{ name: 'subj_level', value: subj_level_select.getValue() }
			];
			if (!year) {
				$.etDialog.error('预算年度不能为空');
				return false;
			}
			//加载查询条件
			grid.loadData(search, "queryBudgMedInExecute.do?isCheck=false");
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
		
		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '年度', name: 'year', align: 'left', width: '10%'},
					{display: '月份', name: 'month', align: 'left', width: '5%'},
					{display: '科室编码', name: 'dept_code', align: 'left', width: '10%'},
					{display: '科室名称', name: 'dept_name', align: 'left', width: '15%'},
					{display: '科目编码', name: 'subj_code', align: 'left', width: '10%'},
					{display: '科目名称', name: 'subj_name', align: 'left', width: '15%'},
					{display: '金额（元）', name: 'amount', align: 'right', width: '15%',
						render: function (ui) {
							var rowdata = ui.rowData;
							if(rowdata.year != '合计'){
								return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" +
								rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.year +
								"|" + rowdata.month + "|" + rowdata.dept_id + "|" +
								rowdata.subj_code + "')>" + formatNumber(rowdata.amount, 2, 1) + "</a>";
							}else{
								return  formatNumber(rowdata.amount, 2, 1);
							}
							
						}
					},
					{display: '说明', name: 'remark', align: 'left', minWidth: '18%'}
				],
				dataModel:{
	               	method:'POST',
	               	location:'remote',
	               	url:'',
	               	recIndx: 'year' //必填 且该列不能为空  
                },
   				pageModel:{
					type:'remote',
				},
                summaryRowIndx: [0] ,
                usePager:true,width: '100%', height: '100%',checkbox: true,
                toolbar: {
	                items: [
	                	{ type: 'button', label: '查询（<u>Q</u>）', id: 'search', listeners: [{ click: query }], icon: 'search' },
						{ type: 'button', label: '添加（<u>A</u>）', id: 'add', listeners: [{ click: add_open }], icon: 'add' },
						{ type: 'button', label: '删除（<u>D</u>）',  id: 'delete',listeners: [{ click: remove }], icon: 'delete' },
						{ type: 'button', label: '下载导入模板（<u>B</u>）', id: 'downTemplate', listeners: [{ click: downTemplate }], icon: 'import' },
						{ type: 'button', label: '导入（<u>I</u>）', id: 'import',listeners: [{ click: imp }], icon: 'import' },
						{ type: 'button', label: 'his数据采集', id: 'import',listeners: [{ click: hisImp }], icon: 'import' },
	            	]
				}
			});
		}

		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "医疗收入预算执行";
		}

		 //添加
	    function add_open() {
	        $.etDialog.open({
	            url: 'budgMedInExecuteAddPage.do?isCheck=false',
	            height: 300,
	            width: 700,
	            title: '医疗收入执行数据',
	            btn: ['确定', '取消'],
	            btn1: function(index, el) {
	                var iframeWindow = window[el.find('iframe').get(0).name];
	                iframeWindow.saveBudgMedInExecute();
	            }				
	        });
	    }
		 
		function remove() {
			var data = grid.selectGet();
			if (data.length == 0) {
				$.etDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function () {
					var rowdata = this.rowData;
					ParamVo.push(
						rowdata.year + "@" +
						rowdata.dept_id + "@" +
						rowdata.month + "@" +
						rowdata.subj_code
					);
				});

				$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgMedInExecute.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res) {
							if (res.state == "true") {
								query();
							}
						}
					})
				});
			}
		}
		
		function imp() {
			var index = layer.open({
				type: 2,
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				area: ['893px', '500px'],
				content: 'budgMedInExecuteImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		//his收入数据采集
		function hisImp() {
			$.etDialog.open({
	            url: 'hisIncomeDataCollectPage.do?isCheck=false',
	            height: 300,
	            width: 400,
	            title: '收入数据采集',
	            btn: ['确定', '取消'],
	            btn1: function(index, el) {
	                var iframeWindow = window[el.find('iframe').get(0).name];
	                iframeWindow.saveHisExecuteData();
	            }				
	        });
		}
		
		function downTemplate() {
			location.href = "downTemplate.do?isCheck=false";
		}
		
		function openUpdate(obj) {
			var vo = obj.split("|");
			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"year=" + vo[3] + "&" +
				"month=" + vo[4] + "&" +
				"dept_id=" + vo[5] + "&" +
				"subj_code=" + vo[6];
			$.etDialog.open({
				 url: 'budgMedInExecuteUpdatePage.do?isCheck=false&' + parm,
	            height: 300,
	            width: 700,
	            title: '医疗收入执行数据',
	            btn: ['确定', '取消'],
	            btn1: function(index, el) {
	                var iframeWindow = window[el.find('iframe').get(0).name];
	                iframeWindow.saveBudgMedInExecute();
	            }
			});
		}
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add_open);
			hotkeys('D', remove);
			hotkeys('B', downTemplate);
			hotkeys('I', imp);
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	
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
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px;"></select>
				</td>
			<!-- </tr>
			<tr>
				<td class="label">科室级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px;"></select>
				</td> -->
				<td class="label">科室名称： </td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
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