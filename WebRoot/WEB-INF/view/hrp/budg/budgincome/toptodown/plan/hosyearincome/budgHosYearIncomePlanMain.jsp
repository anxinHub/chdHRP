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
		<jsp:param value="select,datepicker,etValidate,dialog,grid,ligerUI" name="plugins" />
	</jsp:include>
	<script>
		var grid,year_input, subj_name_select, subj_level_select;
		$(function () {
			loadDict();
			loadGrid();
		});

		function loadDict(){
				year_input = $("#year_input").etDatepicker({
					defaultDate: true,
					view: "years",
					minView: "years",
					dateFormat: "yyyy",
					/* minDate: data[data.length - 5].text,
					maxDate: data[data.length + 5].text, */
					todayButton: false,
					onSelect: function (value) {
						setTimeout(function () {
							reloadSubjName(value);
						}, 10);
						setTimeout(function () {
							query();
						}, 10);
					}
				});
				subj_name_select = $("#subj_name_select").etSelect({
					url:"../../../../queryBudgSubj.do?isCheck=false&subj_type=04&type_code=01&budg_year="+year_input.getValue(),
					defaultValue: "none",
					onChange: query
				});
				
			function reloadSubjName(value){
				subj_name_select.reload({
					url:"../../../../queryBudgSubj.do?isCheck=false",
					para:{
						budg_year:value,
						subj_type:"04",
						type_code:"01"
					}
				});
			}
			
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../../queryBudgSubjLevel.do?isCheck=false",
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

		function query(){
			var search = [
				{ name: 'budg_year', value: year_input.getValue() },
				{ name: 'subj_code', value:subj_name_select.getValue().split(",")[0] },
				{ name: 'subj_level', value: subj_level_select.val() }
			];
			
			//加载查询条件
			grid.loadData(search,'queryBudgHosYearIncomePlan.do?isCheck=false');
		}

		function loadGrid() {
			var columns = [
					{
						display: '科目编码', name: 'subj_code', align: 'left', width: 100, 
						render: function (ui) {
							var rowdata = ui.rowData;

							return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" +
								rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.budg_year + "|"
								+ rowdata.subj_code + "')>"
								+ rowdata.subj_code + "</a>";
						}
					},
					{
						display: '科目全称', name: 'subj_name_all', align: 'left', width: 300, 
					},
					{
						display: '科目级次', name: 'subj_level', align: 'center', width: 60, 
					},
					{
						display: '是否独立核算', name: 'is_single_count', align: 'center', hidden: false, isSort: false, width: 80, 
						render: function (ui) {
							var rowdata = ui.rowData;
							if (rowdata.is_single_count == '1') {
								return "<input id=is_single_count" + rowdata.subj_code + "  type ='checkbox' checked='checked' style='margin-top:8px;'>";
							} else {
								return "<input id=is_single_count" + rowdata.subj_code + "  type ='checkbox' style='margin-top:8px;'>";
							}
						}
					},
					{
						display: '编制方法', name: 'method', align: 'left',
						columns: [
							{
								display: '编制方法', name: 'edit_method_name', align: 'left', width: 80,
							},
							{
								display: '取值方法', name: 'get_way_name', align: 'left', width: 120,
							},
							{
								display: '计算公式', name: 'formula_name', align: 'left', width: 100,

								render: function (ui) {
									var rowdata = ui.rowData;
									if (rowdata.formula_id) {
										return "<a href=javascript:openFormula('" + rowdata.group_id + "|" +
											rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.formula_id + "')>"
											+ rowdata.formula_name + "</a>";
									}
								}
							},
							{
								display: '取值函数', name: 'fun_name', align: 'left', width: 100,
								render: function (ui) {
									var rowdata = ui.rowData;
									if (rowdata.fun_id) {
										return "<a href=javascript:openFun('" + rowdata.group_id + "|" +
											rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.fun_id + "')>"
											+ rowdata.fun_name + "</a>";
									}
								},
							}]
					},

					{
						display: '分解或汇总', name: 'resolvesum', align: 'left',
						columns: [
							{
								display: '分解或汇总', name: 'resolve_or_sum_name', align: 'left', width: 100,
							},
							{
								display: '分解方法', name: 'resolve_way_name', align: 'left', width: 150,
							}
						]
					},
				];
			var toolbar = {
					items: [
						{ type: 'button', label: '查询（<u>Q</u>）', id: 'search', listeners: [{ click: query }], icon: 'search' },
						{ type: 'button', label: '生成（<u>G</u>）', id: 'generate', listeners: [{ click: generate }], icon: 'generate' },						
						{ type: 'button', label: '添加（<u>A</u>）', id: 'add', listeners: [{ click: add_open }], icon: 'add' },
						{ type: 'button', label: '删除（<u>D</u>）',  id: 'delete',listeners: [{ click: remove }], icon: 'delete' },
						//{ type: 'button', label: '打印（<u>P</u>）',  id: 'print',listeners: [{ click: printDate }], icon: 'print' },						
						/* { type: 'button', label: '下载导入模板（<u>B</u>）', id: 'downTemplate', listeners: [{ click: downTemplate }], icon: 'import' },
						{ type: 'button', label: '导入（<u>I</u>）', id: 'import',listeners: [{ click: imp }], icon: 'import' },												 */
					]
			}
			var gridParam = {
				columns: columns,
				usePager: true,
				checkbox: true,
				width: '100%', 
				height: '100%',
				freezeCols:4,
				rowDblClick: rowDblClick,
				dataModel:{
					recIndx: "dept_code",
				},
				toolbar:toolbar
			}
			 grid = $("#maingrid").etGrid(gridParam);

			 function rowDblClick (evt,ui){
				var rowdata = ui.rowData;
				openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.budg_year + "|" +
						rowdata.subj_code
					);
			 }
		}

		//打印数据
		function printDate() {

			if (grid.getAllData().length == 0) {
				$.etDialog.error("无打印数据！");
				return;
			}

			var parms = [];

			parms.push({ name: 'budg_year', value: year_input.getValue() });
			parms.push({ name: 'subj_code', value: subj_name_select.getValue() });
			parms.push({ name: 'subj_level', value: subj_level_select.getValue() });
			var selPara = {};
			$.each(parms, function (i, obj) {
				selPara[obj.name] = obj.value;
			});
			var printPara = {
				headCount: 2,
				title: "医院年度医疗收入预算编制方案",
				type: 3,
				columns: grid.getColumns()
			};

			ajaxPostData({
                url: "queryBudgHosYearIncomePlan.do?isCheck=false",
                data: selPara,
                success: function (res) {
                    if (res.state == "true") {
                        printGridView(res, printPara);
                    }
                }
            })
		}
	
		function generate(){
			var formPara = {
				budg_year: year_input.getValue()
			}

			$.etDialog.confirm('生成功能产生的新数据将会将覆盖原有数据,确定生成?',function (){
				ajaxPostData({
					url: "generateBudgHosYearIncomePlan.do?isCheck=false",
					data: formPara,
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			});
		}
		
		function add_open() {

			parent.$.ligerDialog.open({
				url: 'hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanAddPage.do?isCheck=false&', data: {},
				height: 400, width: 700, title: '医院年度医疗收入预算编制方案添加', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgHosYearIncomePlanAdd(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});

		}
		/* function add_open() {

			parent.$.etDialog.open({
				url: 'hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanAddPage.do?isCheck=false&',
				width: 700,
				height: 450,
				btn: ['确定', '取消'],
				title: '医院年度医疗收入预算编制方案添加',
				frameName: window.name,
				btn1: function (index, el) {
					var iframeWindow = parent.window[el.find('iframe').get(0).name];

					iframeWindow.saveBudgHosYearIncomePlanAdd();
				}
			})
		}
 */
		function remove() {

			var data = grid.selectGetChecked();
			if (data.length == 0) {
				$.etDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function () {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.budg_year + "@" +
						this.rowData.subj_code
					)
				});

				$.etDialog.confirm('确定删除?',function (){
					ajaxPostData({
						url: "deleteBudgHosYearIncomePlan.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res){
							if (res.state == "true") {
								query();
							}
						}
					})
				});
			}
		}
		function imp() {

			$.etDialog.open({
				url: 'budgHosYearIncomePlanImportPage.do?isCheck=false',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				isMax: true,
				title: '导入'
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
				"budg_year=" + vo[3] + "&" +
				"subj_code=" + vo[4]


			parent.$.ligerDialog.open({
				url: 'hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanUpdatePage.do?isCheck=false&' + parm, data: {},
				height: 400, width: 700, title: '医院年度医疗收入预算编制方案修改', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgHosYearIncomePlan(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});
		}
		
		/* function openUpdate(obj) {

			var vo = obj.split("|");
			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"budg_year=" + vo[3] + "&" +
				"subj_code=" + vo[4]

			parent.$.etDialog.open({
				url: 'hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanUpdatePage.do?isCheck=false&' + parm,
				width: 600,
				height: 350,
				btn: ['确定', '取消'],
				title: '医院年度医疗收入预算编制方案修改',
				frameName: window.name,
				btn1: function (index, el) {
					var iframeWindow = parent.window[el.find('iframe').get(0).name];

					iframeWindow.saveBudgHosYearIncomePlan();
				}

			})
		} */
		//计算公式页面跳转
		function openFormula(obj) {

			var vo = obj.split("|");
			var parm = "formula_id=" + vo[3]

			parent.$.etDialog.open({
				url: 'hrp/budg/common/budgformula/budgFormulaUpdatePage.do?isCheck=false&' + parm,
				isMax: true,
				height: 500,
				width: 600,
				btn: ['确定', '取消'],
				title: '计算公式',
				frameName: window.name
			})
		}
		// 取值函数页面跳转
		function openFun(obj) {

			var vo = obj.split("|");
			var parm =
				"fun_code=" + vo[3] + "&" +
				"index_type_code=" + '04' //指标类型 01基本指标 02费用标准 03预算指标  04收入科目

			parent.$.etDialog.open({
				url: 'hrp/budg/common/budgfun/budgFunUpdatePage.do?isCheck=false&' + parm,
				isMax: true,
				height: 500,
				width: 600,
				title: '取值函数',
				frameName: window.name
			})
			
		}
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add);
			hotkeys('D', remove);
			hotkeys('B', downTemplate);
			hotkeys('P', printDate);
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