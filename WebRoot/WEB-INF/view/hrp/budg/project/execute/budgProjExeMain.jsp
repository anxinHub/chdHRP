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
		<jsp:param value="select,datepicker,ligerUI" name="plugins" />
	</jsp:include>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;

		//打印 单元格格式化 用
		var renderFunc = {
			cost_amount: function (value) { //支出金额
				return formatNumber(value, 2, 1);
			}
		};
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			// tab 键  添加行
			$("body").keydown(function () {
				if (event.keyCode == "9") {//keyCode=9是Tab
					//添加行之前 校验之前的数据合法 并已保存
					var data = grid.getAdded();
					if (data.length > 0) {
						if (!validateGridAdd(data)) {
							return false;
						}
					}

					grid.addRowEdited({
						year: '',
						month: '',
						proj_name: '',
						source_name: '',
						payment_item_name: '',
						cost_amount: ''
					});
				}
			});

		});
		//查询
		function query() {
			var date = year_input.getValue();
			var start_date = "" ;
			var end_date = "";
			if(typeof date === "string"){
				start_date = date;
			}else{
				start_date = date[0];
				end_date = date[1];
			}

			grid.options.parms = [
				{ name: 'begin_date', value: start_date },
				{ name: 'end_date', value: end_date },
				{ name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
				{ name: 'source_id', value: source_id_select.getValue() },
				{ name: 'payment_item_id', value: payment_item_select.getValue().split(",")[0] }
			];
			grid.options.newPage = 1;
			
			//加载查询条件
			grid.loadData(grid.where);
		}

		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [
					{
						display: '年度', name: 'year', align: 'center', width: 60,
						valueField: 'id', textField: 'text',
						editor: {
							type: 'select',
							valueField: 'id',
							textField: 'text',
							url: '../../queryBudgYear.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						},
						render: function (rowdata, rowindex, value) {
							if (rowdata.group_id) {
								rowdata.notEidtColNames.push("year");
								rowdata.notEidtColNames.push("month");
								rowdata.notEidtColNames.push("proj_name");
								rowdata.notEidtColNames.push("source_name");
								rowdata.notEidtColNames.push("payment_item_name");
							}
							return value;
						}

					},
					{
						display: '月份', name: 'month', align: 'center', width: 60,
						valueField: 'monthID', textField: 'monthText',
						editor: {
							type: 'select',
							valueField: 'monthID',
							textField: 'monthText',
							data: [{ "monthID": "01", "monthText": "01" }, { "monthID": "02", "monthText": "02" },
							{ "monthID": "03", "monthText": "03" }, { "monthID": "04", "monthText": "04" },
							{ "monthID": "05", "monthText": "05" }, { "monthID": "06", "monthText": "06" },
							{ "monthID": "07", "monthText": "07" }, { "monthID": "08", "monthText": "08" },
							{ "monthID": "09", "monthText": "09" }, { "monthID": "10", "monthText": "10" },
							{ "monthID": "11", "monthText": "11" }, { "monthID": "12", "monthText": "12" }],
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '项目名称', name: 'proj_name', align: 'left',
						valueField: 'proj_id', textField: 'projName',
						editor: {
							type: 'select',
							valueField: 'proj_id',
							textField: 'projName',
							url: 'queryBudgProj.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '资金来源', name: 'source_name', align: 'left',
						valueField: 'source', textField: 'sourceName',
						editor: {
							type: 'select',
							valueField: 'source_id',
							textField: 'sourceName',
							url: 'queryBudgSource.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '支出项目', name: 'payment_item_name', align: 'left',
						valueField: 'payment_item_id', textField: 'item_name',
						editor: {
							type: 'select',
							valueField: 'payment_item_id',
							textField: 'item_name',
							url: 'queryBudgPaymentItem.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '支出金额', name: 'cost_amount', align: 'right', editor: { type: 'float' },
						render: function (rowdata, rowindex, value) {
							if (value) {

								return formatNumber(value, 2, 1);
							}

						}
					}
				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgProjExe.do',
				width: '100%', height: '100%', checkbox: true, rownumbers: true, enabledEdit: true, isAddRow: false,
				onChangeRow: savaData,
				onToFirst: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
				onToPrev: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
				onToNext: updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
				onToLast: updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: {
					items: [
						{ text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						/* { text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
						{ line:true }, */
						{ text: '保存（<u>A</u>）', id: 'save', click: save, icon: 'save' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true },
						{ text: '确认（<u>C</u>）', id: 'confirm', click: confirm, icon: 'audit' },
						{ line: true },
						{ text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
						{ line: true },
						{ text: '导入（<u>I</u>）', id: 'import', click: imp, icon: 'up' }
					]
				},
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}


		//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
		function updateDateExist() {
			var data = grid.changedCells;
			if (!isObjEmpty(data)) {

				$.ligerDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面确定后再操作)</span>】');

				return false;
			}
		}
		function isObjEmpty(obj) {      //判断对象是否为空对象
			for (var i in obj) {
				return false;
			}
			return true;
		}

		//行编辑结束后 保存数据
		function savaData(e) {

			if (!validateGrid(e.record)) {
				return false;
			}

			var formPara = {
				year: e.record.year,

				month: e.record.month,

				proj_id: e.record.proj_name,

				source_id: e.record.source_name,

				payment_item_id: e.record.payment_item_name,

				cost_amount: e.record.cost_amount


			};
			ajaxJsonObjectByUrl("addBudgProjExe.do", formPara, function (responseData) {

				if (responseData.state == "true") {
					//回写联合主键用于继续操作
					if (!e.record.group_id) {
						grid.updateRow(e.rowindex, {
							group_id: responseData.group_id,
							hos_id: responseData.hos_id,
							copy_code: responseData.copy_code
						});
						e.__status = '';//修改该行 __status属性
					}
				}
			});
		}

		//修改
		function save() {

			var changeData = grid.changedCells;

			if (isObjEmpty(changeData)) {

				$.ligerDialog.warn('数据没有变更,无需保存');

				return false;
			}

			var data = grid.getUpdated();
			if (data.length == 0) {

				$.ligerDialog.error('数据没有变更,无需保存');

			} else {
				var ParamVo = [];

				$(data).each(function () {
					ParamVo.push(
						this.year + "@" +
						this.month + "@" +
						this.proj_id + "@" +
						this.source_id + "@" +
						this.payment_item_id + "@" +
						this.cost_amount
					)
				});
				ajaxJsonObjectByUrl("updateBudgProjExe.do", { ParamVo: ParamVo.toString() }, function (responseData) {

					if (responseData.state == "true") {
						query();
					}
				});
			}

		}


		function remove() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {

				$.ligerDialog.error('请选择行');

			} else {
				var ParamVo = [];
				var deleteDate = [];
				$(data).each(function () {
					if (!this.group_id) {
						deleteDate.push(this);
					} else {

						ParamVo.push(
							this.year + "@" +
							this.month + "@" +
							this.proj_id + "@" +
							this.source_id + "@" +
							this.payment_item_id
						)
					}


				});

				$.ligerDialog.confirm('确定删除?', function (yes) {
					if (yes) {
						if (ParamVo.length > 0) {
							ajaxJsonObjectByUrl("deleteBudgProjExe.do", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						} else {

							query();

							$.ligerDialog.success('删除成功');
						}

					}
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
				content: 'budgProjExeImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {

			location.href = "downTemplate.do?isCheck=false";
		}


		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "项目预算执行";
		}

		//确认
		function confirm() {

			var year = $("#begin_date").val().substring(0, 4);

			ajaxJsonObjectByUrl("confirmBudgProjExe.do?isCheck=false&year=" + year, "", function (responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}


		// 换行保存  数据校验
		function validateGrid(e) {
			var rowm = "";
			//判断grid 中的数据是否合法
			if (e.year == "" || e.year == null || e.year == 'undefined') {
				rowm += "[年度]、";
			}
			if (e.month == "" || e.month == null || e.month == 'undefined') {
				rowm += "[年度]、";
			}
			if (e.proj_name == "" || e.proj_name == null || e.proj_name == 'undefined') {
				rowm += "[项目]、";
			}
			if (e.source_name == "" || e.source_name == null || e.source_name == 'undefined') {
				rowm += "[资金来源]、";
			}
			if (e.payment_item_name == "" || e.payment_item_name == null || e.payment_item_name == 'undefined') {
				rowm += "[支出项目]、";
			}
			if (e.cost_amount == "" || e.cost_amount == null || e.cost_amount == 'undefined') {
				rowm += "[支出金额]、";
			}
			if (rowm != "") {
				rowm = "该行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			if (rowm != "") {
				$.ligerDialog.warn(rowm);
				return false;
			} else {
				return true;
			}
		}

		// 新增行 时  校验是否有数据不合法, 防止数据未保存，继续添加。
		function validateGridAdd(data) {
			var rowm = "";
			//判断grid 中的数据是否重复或者为空
			var targetMap = new HashMap();
			$.each(data, function (i, v) {
				rowm = "";
				if (v.year == "" || v.year == null || v.year == 'undefined') {
					rowm += "[年度]、";
				}
				if (v.month == "" || v.month == null || v.month == 'undefined') {
					rowm += "[月份]、";
				}
				if (v.proj_name == "" || v.proj_name == null || v.proj_name == 'undefined') {
					rowm += "[项目]、";
				}
				if (v.source_name == "" || v.source_name == null || v.source_name == 'undefined') {
					rowm += "[资金来源]、";
				}
				if (v.payment_item_name == "" || v.payment_item_name == null || v.payment_item_name == 'undefined') {
					rowm += "[支出项目]、";
				}
				if (v.cost_amount == "" || v.cost_amount == null || v.cost_amount == 'undefined') {
					rowm += "[支出金额]、";
				}
				if (rowm != "") {
					rowm = "该行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
				}

			});
			if (rowm != "") {
				$.ligerDialog.warn(rowm);
				return false;
			} else {
				return true;
			}
		}

		
		//键盘事件
		function loadHotkeys() {

			hotkeys('Q', query);

			hotkeys('A', add);
			hotkeys('D', remove);

			hotkeys('B', downTemplate);

			hotkeys('I', imp);
		}
	</script>
	<script>
		var year_input,proj_name_select,source_id_select,payment_item_select;
		$(function(){
			init();
		});

		function init(){
			year_input = $("#year_input").etDatepicker({
				view:"months",
				minView:"months",
				dateFormat:"yyyy\nmm",
				onChanged:query,
				range:true
			});

			proj_name_select = $("#proj_name_select").etSelect({
				url:"../../queryProjName.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			source_id_select = $("#source_id_select").etSelect({
				url:"../../queryBudgSource.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			payment_item_select = $("#payment_item_select").etSelect({
				url:"../../queryBudgPaymentItemDict.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	

	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">日期：</td>
				<td class="ipt">
					<input type="text" id="year_input">
				</td>
				<td class="label">项目名称：</td>
				<td class="ipt">
					<select name="" id="proj_name_select" style="width:180px;"></select>
				</td>
				<td class="label">资金来源：</td>
				<td class="ipt">
					<select name="" id="source_id_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">支出项目：</td>
				<td class="ipt">
					<select name="" id="payment_item_select" style="width:180px;"></select>
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