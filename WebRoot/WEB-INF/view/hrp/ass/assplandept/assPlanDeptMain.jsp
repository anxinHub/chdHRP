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
				<jsp:param value="select,datepicker,ligerUI,pageOffice" name="plugins" />
			</jsp:include>
			<script type="text/javascript">
				var grid;
				var gridManager = null;
				var userUpdateStr;
				$(function () {
					loadHotkeys();
					loadHead();
				});
				//查询
				function query() {
					var year_date = getDate(year_input.getValue());  
					var create_date = getDate(date1_input.getValue());   
					var check_date = getDate(date2_input.getValue());

					grid.options.parms = [
						{ name: 'plan_no', value: $("#plan_no_input").val() },
						{ name: 'plan_year1', value: year_date.start_date },
						{ name: 'plan_year2', value: year_date.end_date },
						{ name: 'dept_id', value: dept_id_select.getValue().split("@")[0] },
						{ name: 'check_emp', value: check_emp_select.getValue() },
						{ name: 'check_date1', value: check_date.start_date },
						{ name: 'check_date2', value: check_date.end_date },
						{ name: 'create_date1', value: create_date.start_date },
						{ name: 'create_date2', value: create_date.end_date },
						{ name: 'is_add', value: is_add_select.getValue() },
						{ name: 'state', value: state_select.getValue() },
						{ name: 'buy_code', value: buy_code_select.getValue() }
					];
					grid.options.newPage = 1;
					
					grid.loadData(grid.where);
				}

				function loadHead() {
					grid = $("#maingrid").ligerGrid(
						{
							columns: [
								{
									display: '计划号',
									name: 'plan_no',
									align: 'left',
									width: '110',
									frozen: true,
									render: function (rowdata, rowindex,
										value) {
										if (rowdata.note == "合计") {
											return '';
										}
										if (rowdata.is_add == 1) {
											return "<a href=javascript:openUpdate('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.plan_id
												+ "|"
												+ rowdata.state
												+ "')>"
												+ rowdata.plan_no
												+ "&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
										} else {
											return "<a href=javascript:openUpdate('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.plan_id
												+ "|"
												+ rowdata.state
												+ "')>"
												+ rowdata.plan_no
												+ "</a>";
										}
									}
								},
								{
									display: '摘要',
									name: 'note',
									align: 'left',
									width: '160',
									frozen: true,
									render: function (rowdata, rowindex,
										value) {
										var note = "";
										if (rowdata.note == null) {
											note = "追溯购置申请";
										} else {
											note = rowdata.note;
										}


										if (rowdata.is_import == 1) {
											return "<a href=javascript:openViewApply('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.plan_id
												+ "|"
												+ rowdata.state
												+ "')>"
												+ note
												+ "</a>";
										} else {
											return rowdata.note;
										}
									}

								},
								{
									display: '购置年度',
									name: 'plan_year',
									align: 'left',
									width: '100',
									frozen: true
								},
								{
									display: '编制科室名称',
									name: 'dept_no',
									align: 'left',
									width: '150',
									render: function (rowdata, rowindex,
										value) {
										return rowdata.dept_name;
									}
								},
								{
									display: '计划金额',
									name: 'plan_money',
									align: 'right',
									width: '110',
									render: function (rowdata, rowindex,
										value) {
										return formatNumber(
											rowdata.plan_money == null ? 0
												: rowdata.plan_money,
											'${ass_05005}',
											1);
									}

								},
								{
									display: '制单人',
									name: 'create_emp',
									align: 'left',
									width: '100',
									render: function (rowdata, rowindex,
										value) {
										return rowdata.create_emp_name;
									}
								},
								{
									display: '制单日期',
									name: 'create_date',
									align: 'left',
									width: '100'
								},
								{
									display: '审核人',
									name: 'check_emp',
									align: 'left',
									width: '100',
									render: function (rowdata, rowindex,
										value) {
										return rowdata.check_emp_name;
									}
								},
								{
									display: '审核日期',
									name: 'check_date',
									align: 'left',
									width: '100'
								},
								{
									display: '是否追加计划',
									name: 'is_add',
									align: 'left',
									width: '90',
									render: function (rowdata, rowindex,
										value) {
										if (rowdata.is_add == 0) {
											return "否";
										} else if (rowdata.is_add == 1) {
											return "是";
										}
									}
								},
								{
									display: '状态',
									name: 'state',
									align: 'left',
									width: '100',
									render: function (rowdata, rowindex,
										value) {
										if (rowdata.state == 0) {
											return "新建";
										} else if (rowdata.state == 1) {
											return "审核";
										}
									}
								},
								{
									display: '采购方式',
									name: 'buy_code',
									align: 'left',
									width: '100',
									render: function (rowdata, rowindex,
										value) {
										if (rowdata.buy_code == 0) {
											return "自主采购";
										} else if (rowdata.buy_code == 1) {
											return "集中采购";
										}

									}
								}],
							dataAction: 'server',
							dataType: 'server',
							usePager: true,
							url: 'queryAssPlanDept.do',
							width: '100%',
							height: '100%',
							checkbox: true,
							rownumbers: true,
							delayLoad: true,
							selectRowButtonOnly: true,
							checkBoxDisplay: isCheckDisplay,
							toolbar: {
								items: [{
									text: '查询（<u>E</u>）',
									id: 'search',
									click: query,
									icon: 'search'
								}, {
									line: true
								}, {
									text: '添加（<u>A</u>）',
									id: 'add',
									click: add_open,
									icon: 'add'
								}, {
									line: true
								}, {
									text: '删除（<u>D</u>）',
									id: 'delete',
									click: remove,
									icon: 'delete'
								}, {
									line: true
								}, {
									text: '审核（<u>S</u>）',
									id: 'toExamine',
									click: toExamine,
									icon: 'ok'
								}, {
									line: true
								}, {
									text: '销审（<u>X</u>）',
									id: 'notToExamine',
									click: notToExamine,
									icon: 'right'
								}, {
									line: true
								}, {
									text: '引入购置申请（<u>U</u>）',
									id: 'importApply',
									click: importApplys,
									icon: 'refresh'
								}, {
									line: true
								}, {
									text: '批量打印（<u>P</u>）',
									id: 'print',
									click: printDate,
									icon: 'print'
								}, {
									line: true
								}, {
									text: '模板设置',
									id: 'printSet',
									click: printSet,
									icon: 'print'
								}
								]
							},
							onDblClickRow: function (rowdata, rowindex, value) {//双击行事件
								if (rowdata.note == '合计') {
									return;
								} else {
									openUpdate(rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.plan_id + "|" + rowdata.state);
								}
							}
						});

					gridManager = $("#maingrid").ligerGetGridManager();
				}

				function isCheckDisplay(rowdata) {
					if (rowdata.note == "合计")
						return false;
					return true;
				}

				function add_open() {

					parent.$.ligerDialog.open({
						title: '购置计划添加',
						height: $(window).height(),
						width: $(window).width(),
						url: 'hrp/ass/assplandept/assPlanDeptAddPage.do?isCheck=false&',
						modal: true,
						showToggle: false,
						showMax: true,
						showMin: false,
						isResize: true,
						parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
					});
				}

				function remove() {

					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {
						var ParamVo = [];
						$(data).each(
							function () {
								ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.plan_id)
							});
						$.ligerDialog.confirm('确定删除?', function (yes) {

							if (yes) {
								ajaxJsonObjectByUrl("deleteAssPlanDept.do", {
									ParamVo: ParamVo.toString()
								}, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					}
				}

				//引入购置申请单
				function importApplys() {
					parent.$.ligerDialog
						.open({
							title: '引入购置申请',
							height: $(window).height(),
							width: $(window).width(),
							url: 'hrp/ass/assplandept/assPlanDeptImportApplyPage.do?isCheck=false&',
							modal: true,
							showToggle: false,
							showMax: true,
							showMin: false,
							isResize: true,
							parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
						});
				}

				function openViewApply(obj) {
					var vo = obj.split("|");

					var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
						+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3];

					parent.$.ligerDialog.open({
						title: '购置申请追溯',
						height: $(window).height(),
						width: $(window).width(),
						url: 'hrp/ass/assplandept/assViewApplyPage.do?isCheck=false&'
						+ parm,
						modal: true,
						showToggle: false,
						showMax: true,
						showMin: false,
						isResize: true,
						parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
					});
				}

				function openUpdate(obj) {

					var vo = obj.split("|");
					if("null"==vo[3]){
						return false;
						
					}
					var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
						+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3] + "&"
						+ "state=" + vo[4];

					parent.$.ligerDialog.open({
						title: '购置计划修改',
						height: $(window).height(),
						width: $(window).width(),
						url: 'hrp/ass/assplandept/assPlanDeptUpdatePage.do?isCheck=false&'
						+ parm,
						modal: true,
						showToggle: false,
						showMax: true,
						showMin: false,
						isResize: true,
						parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
					});

				}
				
				//键盘事件
				function loadHotkeys() {

					hotkeys('Q', query);

					hotkeys('A', add_open);
					hotkeys('D', remove);

					hotkeys('S', toExamine);

					hotkeys('X', notToExamine);

					hotkeys('P', printDate);

					hotkeys('U', importApplys);

					hotkeys('I', approves);

				}

				function approves() {
					var ParamVo = [];
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {
						var row = gridManager.getSelectedRow();

						if (row.state != 1) {

							$.ligerDialog.error('该数据没有审核则不能进行审批');

							return false;
						} else {
							$(data).each(
								function () {
									ParamVo
										.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.plan_id);
								});
							$.ligerDialog.open({
								url: 'assPlanDeptApprovePage.do?isCheck=false&ParamVo='
								+ ParamVo.toString(),
								data: {},
								height: 300,
								width: 500,
								title: '审批意见',
								modal: true,
								showToggle: false,
								showMax: false,
								showMin: false,
								isResize: true,
								buttons: [{
									text: '确定',
									onclick: function (item, dialog) {
										dialog.frame.savePlanDeptApprove();
									},
									cls: 'l-dialog-btn-highlight'
								}, {
									text: '取销',
									onclick: function (item, dialog) {
										dialog.close();
									}
								}]
							});
						}
					}
				}

				function toExamine() {
					var ParamVo = [];
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {
						$(data).each(
							function () {
								ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.plan_id);

							});
						$.ligerDialog.confirm('确定审核?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("updateToExaminePlanDept.do", {
									ParamVo: ParamVo.toString()
								}, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					}
				}
				function notToExamine() {
					var ParamVo = [];
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {
						$(data).each(
							function () {
								ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.plan_id);
							});
						$.ligerDialog.confirm('确定销核?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("updateNotToExaminePlanDept.do", {
									ParamVo: ParamVo.toString()
								}, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					}
				}

				//打印回调方法
				function lodopPrint() {
					var head = "<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
					head = head + "<tr><td>制单日期：" + $("#create_date_start").val() + " 至  " + $("#create_date_end").val() + "</td></tr>";
					head = head + "</table>";
					grid.options.lodop.head = head;
					grid.options.lodop.fn = renderFunc;
					grid.options.lodop.title = "置购计划";
				}

				function printSet() {

					var useId = 0;//统一打印
					if ('${ass_05009}' == 1) {
						//按用户打印
						useId = '${user_id }';
					}
					//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
					officeFormTemplate({template_code:"05009",use_id:useId});

				}
				//打印
				function printDate() {
					var useId = 0;//统一打印
					if ('${ass_05009}' == 1) {
						//按用户打印
						useId = '${user_id }';
					}
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {

						var plan_id = "";
						var plan_nos = "";
						$(data).each(function () {
							plan_id += "'"+this.plan_id + "',"

						});
					var para = {


						template_code: '05009',
						class_name: "com.chd.hrp.ass.serviceImpl.plan.AssPlanDeptServiceImpl",
						method_name: "queryAssPlanDeptDY",

						paraId: plan_id.substring(0, plan_id.length - 1),
						isPrintCount: false,//更新打印次数
						isPreview: true,//预览窗口，传绝对路径
						use_id: useId,
						p_num: 1
					};
					ajaxJsonObjectByUrl("queryPlanDeptState.do?isCheck=false",{paraId :plan_id.substring(0,plan_id.length-1),state:1},function(responseData){
						if (responseData.state == "true") {
							officeFormPrint(para);
						}
					});
				}
				}
			</script>
			<script>
				var year_input, dept_id_select, date1_input, buy_code_select, check_emp_select, date2_input, state_select, is_add_select;
				$(function () {
					init();
				});

				function init() {
					year_input = $("#year_input").etDatepicker({
						minView: "years",
						view: "years",
						dateFormat: "yyyy",
						range: true,
						onChanged:query
					});

					date1_input = $("#date1_input").etDatepicker({
						range: true,
						onChanged:query
					});

					date2_input = $("#date2_input").etDatepicker({
						range: true,
						onChanged:query
					});

					dept_id_select = $("#dept_id_select").etSelect({
						url: "../queryDeptDict.do?isCheck=false",
						defaultValue: "none",
						onChange: query
					});

					buy_code_select = $("#buy_code_select").etSelect({
						options: [{ id: 0, text: '自主采购' }, { id: 1, text: '集中采购' }],
						defaultValue: "none",
						onChange: query
					});

					check_emp_select = $("#check_emp_select").etSelect({
						url: "../../../hrp/sys/queryUserDict.do?isCheck=false",
						defaultValue: "none",
						onChange: query
					});

					state_select = $("#state_select").etSelect({
						options: [{ id: 0, text: '新建' }, { id: 1, text: '审核' }],
						defaultValue: "none",
						onChange: query
					});

					is_add_select = $("#is_add_select").etSelect({
						options: [{ id: 0, text: '否' }, { id: 1, text: '是' }],
						defaultValue: "none",
						onChange: query
					});
				}

				function getDate(date) {
					var start_date = "";
					var end_date = "";
					if (typeof date === "string") {
						start_date = date;
					} else {
						start_date = date[0];
						end_date = date[1];
					}
					return { start_date, end_date };
				}
			</script>
		</head>

		<body>
			<div id="pageloading" class="l-loading" style="display: none"></div>

			<div id="toptoolbar"></div>
			
			<div class="main">
				<table class="table-layout" width="80%">
					<tr>
						<td class="label">购置年度：</td>
						<td class="ipt">
							<input type="text" id="year_input">
						</td>
						<td class="label">计划号：</td>
						<td class="ipt">
							<input type="text" id="plan_no_input">
						</td>
						<td class="label">编制科室：</td>
						<td class="ipt">
							<select name="" id="dept_id_select" style="width:180px;"></select>
						</td>
					</tr>
					<tr>
						<td class="label">制单日期：</td>
						<td class="ipt">
							<input type="text" id="date1_input">
						</td>
						<td class="label">采购方式：</td>
						<td class="ipt">
							<select name="" id="buy_code_select" style="width:180px;"></select>
						</td>
						<td class="label">审核人：</td>
						<td class="ipt">
							<select name="" id="check_emp_select" style="width:180px;"></select>
						</td>
					</tr>
					<tr>
						<td class="label">审核日期：</td>
						<td class="ipt">
							<input type="text" id="date2_input">
						</td>
						<td class="label">状态：</td>
						<td class="ipt">
							<select name="" id="state_select" style="width:180px;"></select>
						</td>
						<td class="label">追加计划：</td>
						<td class="ipt">
							<select name="" id="is_add_select" style="width:180px;"></select>
						</td>
					</tr>
				</table>
			</div>

			<div id="maingrid"></div>
		</body>

		</html>