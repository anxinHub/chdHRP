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
	<script>
		var grid,
		year_input, 
		month,
		subj_name_select,
		/* subj_level_select, */
		dept_name_select,
		grid,
		//打印 单元格格式化 用
		renderFunc = {
			amount: function (value) { //余额  
				return formatNumber(value, 2, 1);
			}
		};
		$(function () {
			init();
			loadGrid();
		});
	
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
				 var date=new Date();
                 var sy=date.getFullYear();
				year_input = $("#year_input").etDatepicker({
					defaultDate: sy+'',
					view: "years",
					minView: "years",
					dateFormat: "yyyy",
					//minDate: data[data.length - 1].text,
					//maxDate: data[0].text,
					todayButton: false,
					onSelect: function (value) {
						reloadSubjName(value);
						setTimeout(function () {
							query();
						}, 10);
					}
				});
				reloadSubjName(sy);
			});

		 

			// 月份
			month = $("#month").etDatepicker({
				view: "months",
				maxView: "months",
				minView: "months",
				dateFormat: "mm",
				showNav: false,
				todayButton: false,
				onSelect: function (value) {
					queryNew();
				}
			})
			
			subj_name_select = $("#subj_name_select").etSelect({
				defaultValue: "none",
				onChange: queryNew ,
			});
			//再次加载科目名称
			function reloadSubjName(value) {
				var url = "../../queryBudgSubj.do?isCheck=false?isCheck=false";
				subj_name_select.reload({
					url: url,
					type: "POST",
					para: {
						subj_type: '04',
						is_last:'1',
						budg_year: value
					}
				});
			}


			/* subj_level_select = $("#subj_level_select").etSelect({
				url: "../../queryBudgSubjLevel.do?isCheck=false",
				defaultValue: "none",
				onChange: queryNew
			}); */

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../queryDept.do?isCheck=false",
				defaultValue: "none",
				onChange: queryNew
			});
		}

		function loadGrid() {
			var columns = [
					{display: '年度', name: 'year', align: 'left', width: "10%"},
					{display: '月份', name: 'month', align: 'left', width: "5%"},
					{display: '科室编码', name: 'dept_code', align: 'left',width:"10%"},
					{display: '科室名称', name: 'dept_name', align: 'left',width:"15%"},
					{display: '科目编码', name: 'subj_code', align: 'left',width:"10%"},
					{display: '科目名称', name: 'subj_name', align: 'left',width:"15%"},
					{display: '金额（元）', name: 'amount', align: 'right',minWidth:"15%",
						render: function (ui) {
							var rowdata = ui.rowData;
							if(rowdata.year !='合计'){
								return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" +
									rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.year +
									"|" + rowdata.month + "|" + rowdata.dept_id + "|" +
									rowdata.subj_code + "')>" + formatNumber(rowdata.amount, 2, 1) + "</a>";
							}else{
								return formatNumber(rowdata.amount, 2, 1) ;
							}
							
						}
					},
					{display: '说明', name: 'remark', align: 'left',minWidth:"18%"}
			];
			var toolbar = {
					items: [
						{ type: 'button', label: '查询（<u>Q</u>）', id: 'search', listeners: [{ click: queryNew }], icon: 'search' },
						{ type: 'button', label: '添加（<u>A</u>）', id: 'add', listeners: [{ click: add_open }], icon: 'add' },
						{ type: 'button', label: '删除（<u>D</u>）',  id: 'delete',listeners: [{ click: remove }], icon: 'delete' },
						{ type: "button", label: '财务取数',icon:'search',listeners: [{ click: getDatafromAcc}] },
						{ type: 'button', label: '下载导入模板（<u>B</u>）', id: 'downTemplate', listeners: [{ click: downTemplate }], icon: 'import' },
						{ type: 'button', label: '导入（<u>I</u>）', id: 'import',listeners: [{ click: imp }], icon: 'import' },												
					]
			}
			var gridParam = {
				columns: columns,
				usePager: true,
				checkbox: true,
				width: '100%', 
				height: '100%',
				inWindowHeight: true,
				dataModel:{
					method: 'POST',
     	         	location: 'remote',
     	         	url: '',
					recIndx: "year",
				},
				pageModel: {
					type: 'remote',
				}, 
				toolbar:toolbar,
				summaryRowIndx: [0] ,
			}
			 grid = $("#maingrid").etGrid(gridParam);

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
		}

		function queryNew() {
			var year = year_input.getValue();
			var search = [
				{ name: 'year', value: year },
				{ name: 'month', value: month.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				//{ name: 'subj_level', value: subj_level_select.getValue() }
			];
			if (!year) {
				$.etDialog.error('预算年度不能为空');
				return false;
			}
	
			//加载查询条件
			grid.loadData(search, "queryBudgPreExecute.do?isCheck=false");
		}

		function downTemplate() {
			location.href = "downTemplate.do?isCheck=false";
		}

		function openUpdate(obj) {
			var vo = obj.split("|");
			var parm =
				"year=" + vo[3] + "&" +
				"month=" + vo[4] + "&" +
				"dept_id=" + vo[5] + "&" +
				"subj_code=" + vo[6]

				$.etDialog.open({
					url: 'budgPreExecuteUpdatePage.do?isCheck=false&' + parm,
					height: 320,
					width: 600,
					title: '医疗收入执行数据',
					btn: ["确定", "取消"],
					btn1: function (index, el) {
						var frameWindow = window[el.find('iframe')[0].name];
						frameWindow.saveBudgPreExecute();
					},
					btn2:function (index) {
						$.etDialog.close(index); // 关闭弹窗
						return false;
					}
				});

		}

		function imp() {
			$.etDialog.open({
				url: 'budgPreExecuteImportPage.do?isCheck=false',
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				isMax: true
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
						/* rowdata.group_id + "@" +
						rowdata.hos_id + "@" +
						rowdata.copy_code + "@" + */
						rowdata.year + "@" +
						rowdata.dept_id + "@" +
						rowdata.month + "@" +
						rowdata.subj_code
					);
				});

				$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgPreExecute.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res) {
							if (res.state == "true") {
								queryNew();
							}
						}
					})
				});
			}
		}

		function add_open() {
			var dialog = $.etDialog.open({
				type: "iframe",
				url: 'budgPreExecuteAddPage.do?isCheck=false&year='+year_input.getValue(),
				width: 600,
				height: 350,
				title: '历史执行数据添加',
				btnAlign: 'right',
				btn: ['确定', '取消'],
				btn1: function (index, el) {
					var childFrame = window[el.find('iframe')[0].name]; // 获取window对象
					childFrame.saveBudgPreExecute();
				},
				btn2: function (index, el) {
					$.etDialog.close(index); // 关闭弹窗
					return false;
				}
			})
		}

		 //财务取数
	    function getDatafromAcc(){
	    	$.etDialog.open({
                title: '财务取数',
                height: 388,
                width: 600,
                btn: ["确定", "取消"],
                btn1: function (index, el) {
                    var frameWindow = window[el.find('iframe')[0].name];
                    frameWindow.getDatafromAcc(); //子页函数
                },
                btn2: function (index) {
                    $.etDialog.close(index); // 关闭弹窗
                    return false;
                },
                url: "getDatafromAccPage.do?isCheck=false"
            });
	 	   	/* var budg_year = year_input.getValue();
	 	   	var acc_month = month.getValue();
	 	   	if(!budg_year || !acc_month){
	 	   		$.etDialog.error("预算年度和月份必填,不可为空");
	 	   		return
	 	   	}
	 	   	ajaxPostData({
			    url: "getDatafromAcc.do?isCheck=false&budg_year="+budg_year+"&acc_month="+acc_month,
			    data: '',
			    success: function (responseData) {
			    	queryNew();
			    },
			}) */
	    }
		
		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "历史执行数据采集";
		}
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', queryNew);
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
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">月份：</td>
				<td class="ipt">
					<input name="month" id="month" style="width:180px;" />
				</td>
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px"></select>
				</td>
			<!-- </tr>
			<tr>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px"></select>
				</td> -->
			
				<td class="label">科室名称：</td>
				<td>
					<select name="" id="dept_name_select" style="width:180px"></select>
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>