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
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
		});
		//查询
		function query() {
			var date = date_input.getValue();
			var start_date = "" ;
			var end_date = "";
			if(typeof date === "string"){
				start_date = date;
			}else{
				start_date = date[0];
				end_date = date[1];
			}
			grid.options.parms = [
				{ name: 'get_date', value:start_date},
				{ name: 'end_get_date', value: end_date},
				{ name: 'proj_name', value: $("#proj_name_input").val() },
				{ name: 'source_id', value: source_id_select.getValue() }
			];
			grid.options.newPage = 1;

			//加载查询条件
			grid.loadData(grid.where);
		}
		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [
					{
						display: '编号', name: 'record_id', align: 'left', editor: { type: 'text', }
					},
					{
						display: '摘要', name: 'remark', align: 'left', width: 450,
						editor: { type: 'text', }
					},
					{
						display: '到账日期', name: 'get_date', align: 'left', type: 'date', format: 'yyyy-MM-dd', minWidth: 100,
						editor: { type: 'date', showSelectBox: false },
					},
					{
						display: '收入科目', name: 'income_subj', align: 'left', textField: 'subj_name', valueField: 'subj_id',
						editor: {
							type: 'select',
							valueField: 'id',
							textField: 'text',
							url: '../../../acc/querySubjToProj.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '项目名称', name: 'proj_id', align: 'left', textField: 'proj_name', valueField: 'proj_id',
						editor: {
							type: 'select',
							valueField: 'id',
							textField: 'text',
							url: '../../queryProjName.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '资金来源', name: 'source_id', align: 'left', textField: 'source_name', valueField: 'source_id',
						editor: {
							type: 'select',
							valueField: 'id',
							textField: 'text',
							url: '../../queryBudgSourceNotExistsZCZJ.do?isCheck=false',
							keySupport: true,
							autocomplete: true,
						}
					},
					{
						display: '到账金额', name: 'get_amount', align: 'left',
						editor: { type: 'text', }
					}
				],
				dataAction: 'server',
				enabledEdit: true,
				usePager: true,
				url: 'queryBudgOutsideFundGet.do',
				width: '100%',
				height: '100%',
				checkbox: true,
				rownumbers: true,
				selectRowButtonOnly: true,//heightDiff: -10,
				onChangeRow: onChangeRowSeve,
				toolbar: {
					items: [
						{ text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true },
						{ text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
						{ line: true },
						{ text: '导入（<u>I</u>）', id: 'import', click: imp, icon: 'up' },
						{ line: true },
						{ text: '确认', id: 'confirm', click: confirm, icon: 'audit' }
					]
				},
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function confirm() {
			ajaxJsonObjectByUrl("confirmAddOrUpdateBudgOutsideFundGet.do?isCheck=false", '', function (responseData) {
			});
		}

		function onChangeRowSeve(e) {
			if (!e.record.record_id) {
				setTimeout(function () {
					$.ligerDialog.error("编号不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.remark) {
				setTimeout(function () {
					$.ligerDialog.error("摘要不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.get_date) {
				setTimeout(function () {
					$.ligerDialog.error("到账日期不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.income_subj) {
				setTimeout(function () {
					$.ligerDialog.error("收入科目不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.proj_id) {
				setTimeout(function () {
					$.ligerDialog.error("项目名称不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.source_id) {
				setTimeout(function () {
					$.ligerDialog.error("资金来源不能为空！！！")
				}, 10)
				return;
			}
			if (!e.record.get_amount) {
				setTimeout(function () {
					$.ligerDialog.error("到账金额不能为空！！！")
				}, 10)
				return;
			}
			var dataAll = grid.getData();
			for (var j = 0; j < dataAll.length; j++) {
				if (dataAll[j].record_id !== undefined) {
					if (e.record.__index != j) {
						if (e.record.record_id == dataAll[j].record_id) {
							setTimeout(function () {
								$.ligerDialog.error("第" + (e.record.__index + 1) + "行数据填写的工资项编码为【" + e.record.record_id + "】，该编码已存在，请修改数据后在保存");
							}, 10)
							return false;
						}
					}
				}
			}
			var date = new Date(e.record.get_date);
			dates = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			var paramData = {};
			paramData = e.record;
			paramData.proj_no = e.record.proj_id.split(",")[1];
			paramData.proj_id = e.record.proj_id.split(",")[0];
			paramData.get_date = dates;
			ajaxJsonObjectByUrl("addOrUpdateBudgOutsideFundGet.do?isCheck=false", paramData, function (responseData) {
				query();
				is_addRow();
			});

		}


		function is_addRow() {
			setTimeout(function () { //当数据为空时 默认新增一行
				grid.addRow();
			}, 100);
		}

		function add_open() {

			$.ligerDialog.open({
				url: 'budgOutsideFundGetAddPage.do?isCheck=false&', data: {}, height: 300, width: 450, title: '外拨经费到账', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgOutsideFundGet(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});

		}

		function remove() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function () {
					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.record_id
					)
				});
				$.ligerDialog.confirm('确定删除?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteBudgOutsideFundGet.do", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
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
				content: 'budgOutsideFundGetImportPage.do?isCheck=false'
			});
			layer.full(index);
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
				"record_id=" + vo[3]


			$.ligerDialog.open({
				url: 'budgOutsideFundGetUpdatePage.do?isCheck=false&', data: {}, height: 300, width: 450, title: '外拨经费到账', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgOutsideFundGet(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});

		}
		function loadDict() {
			//字典下拉框
			autocomplete("#source_id", "../../queryBudgSourceNotExistsZCZJ.do?isCheck=false", "id", "text", true, true);
		}
		//键盘事件
		function loadHotkeys() {

			hotkeys('Q', query);

			hotkeys('A', add);
			hotkeys('D', remove);

			hotkeys('B', downTemplate);
			hotkeys('I', imp);


		}
		function Format(now, mask) {
			var d = now;
			var zeroize = function (value, length) {
				if (!length) length = 2;
				value = String(value);
				for (var i = 0, zeros = ''; i < (length - value.length); i++) {
					zeros += '0';
				}
				return zeros + value;
			};

			return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
				switch ($0) {
					case 'd': return d.getDate();
					case 'dd': return zeroize(d.getDate());
					case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
					case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
					case 'M': return d.getMonth() + 1;
					case 'MM': return zeroize(d.getMonth() + 1);
					case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
					case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
					case 'yy': return String(d.getFullYear()).substr(2);
					case 'yyyy': return d.getFullYear();
					case 'h': return d.getHours() % 12 || 12;
					case 'hh': return zeroize(d.getHours() % 12 || 12);
					case 'H': return d.getHours();
					case 'HH': return zeroize(d.getHours());
					case 'm': return d.getMinutes();
					case 'mm': return zeroize(d.getMinutes());
					case 's': return d.getSeconds();
					case 'ss': return zeroize(d.getSeconds());
					case 'l': return zeroize(d.getMilliseconds(), 3);
					case 'L': var m = d.getMilliseconds();
						if (m > 99) m = Math.round(m / 10);
						return zeroize(m);
					case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
					case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
					case 'Z': return d.toUTCString().match(/[A-Z]+$/);
					// Return quoted strings with the surrounding quotes removed
					default: return $0.substr(1, $0.length - 2);
				}
			});
		};
	</script>
	<script>
		var date_input,source_id_select;
		$(function(){
			init();
		});

		function init(){
			date_input = $("#date_input").etDatepicker({
				range:true,
				onChanged:query
			});

			source_id_select = $("#source_id_select").etSelect({
				url:"../../queryBudgSourceNotExistsZCZJ.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">到账日期：</td>
				<td class="ipt">
					<input type="text" id="date_input">
				</td>
				<td class="label">项目名称：</td>
				<td class="ipt">
					<input type="text"  id="proj_name_input">
				</td>
				<td class="label">资金来源：</td>
				<td class="ipt">
					<select name="" id="source_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>
</html>