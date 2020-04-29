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
        <jsp:param value="select,datepicker,grid,ligerUI" name="plugins" />
    </jsp:include>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;

		//打印 单元格格式化 用
		var renderFunc = {
			budg_amount: function (value) { //收入预算
				return formatNumber(value, 2, 1);
			},
			in_amount: function (value) { //到账金额
				return formatNumber(value, 2, 1);
			}
		};
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
		 var year_input,project_name_select,source_select;

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

	            project_name_select = $("#project_name_select").etSelect({
					url:'../../queryProjName.do?isCheck=false',
					defaultValue: "none",
					onChange: query
				});

	            source_select = $("#source_select").etSelect({
					url:'../../queryBudgSource.do?isCheck=false&source_attr=3',
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

	        function query(){
	            var search = [
	                { name: 'budg_year', value: year_input.getValue() },
	                { name: 'proj_id', value: project_name_select.getValue().split(",")[0] },
	                { name: 'source_id', value: source_select.getValue() }
	            ]
	            //加载查询条件
	            grid.loadData(search,"queryBudgResearchProjIncome.do");
	        }

		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{ display: '年度', name: 'budg_year', align: 'center', width: 60 },
					{ display: '项目编码', name: 'proj_code', align: 'left', width: 120 },
					{ display: '项目名称', name: 'proj_name', align: 'left', width: 450 },
					{ display: '资金来源', name: 'source_name', align: 'left', width: 100 },
					{ display: '预算科目', name: 'subj_name', align: 'left', width: 180 },
					{ display: '收入预算', name: 'budg_amount', align: 'right', width: 120,
						render:function(ui) {
							var value = ui.cellData;
							if (value) {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{ display: '到账金额', name: 'in_amount', align: 'right', width: 120,
						render:function(ui) {
							var value = ui.cellData;
							if(value){
								return formatNumber(value, 2, 1);
							}
						}
					}
				],
				dataModel:{
					method: 'POST',
					location: 'remote',
					url: '',
					recInx: 'budg_year'
				},
				usePage: false,
				width: '100%',
				height: '100%',
				checkBox: true,
				toolbar: {
					items: [
						{type: 'button', label:'查询', icon: 'search', listeners: [{click: query }] }
					]
				}
			});
		}

		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "科研项目收入预算执行";
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
                <td class="label" >预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label">项目名称：</td>
                <td class="ipt">
                    <select name="" id="project_name_select" style="width:450px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">资金来源：</td>
                <td colspan="3">
                    <select name="" id="source_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>