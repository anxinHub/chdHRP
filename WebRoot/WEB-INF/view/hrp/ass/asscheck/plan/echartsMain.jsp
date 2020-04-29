<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<link href="<%=path%>/lib/ligerUI/css/layui.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/echarts/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
	var chart1, chart2, echarts;
	var grid;
	var gridManager = null;
	var check_plan_no;
	$(function() {

		$('body').show();

		// 浏览器调整大小时echar自适应
// 		window.onresize = function () {
// 			chart1.resize();
// 			chart2.resize();
// 		}
		// es赋给全局变量echarts
		//echarts = es;
		check_plan_no = "'${check_plan_no_s}','${check_plan_no_g}','${check_plan_no_o}'";
		
		// 初始化echarts
		initChart();
		// 倉庫
		loadChart1Data();
		// 科室
		loadChart2Data();
		
	});
	//
	function initChart() {
			chart1 = echarts.init($('#chart1').get(0));
			chart2 = echarts.init($('#chart2').get(0));
			var option = {
				tooltip: {
					trigger: 'axis',
					axisPointer: { // 坐标轴指示器，坐标轴触发有效
						type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					},
					formatter: function (arr) {    //  可以自定义标签label
						var htm = '';
						var amount = arr[0].value * 1;
						htm += '<p>' + arr[0].axisValueLabel + '</p>';
						if (amount < 0) {
							htm +=  '未盘资产数量:' + amount;
						} /* else {
							htm +=  '已盘资产数量:' + amount;
						} */
						return htm;
					}
				},

				grid: {
					left: '3%',
					right: '9%',
					bottom: '3%',
					containLabel: true,
				},
				xAxis: [{
					type: 'value',
					name: '数量',
					axisLine: {
						lineStyle: {
							color: '#1e9fff' // X 轴坐标线颜色
						},
					},
					splitLine: { lineStyle: { type: 'dashed' } },  // 分割线颜色
				}],
				yAxis: [{
					type: 'category',
					axisTick: {
						show: true
					},
					axisLine: {
						lineStyle: {
							color: '#1e9fff' // Y 轴坐标线颜色
						}
					}
				}],
				series: [{
					name: '已盤数',
					type: 'bar',
					label: {
						normal: {
							show: true,
							position: 'inside'
						}
					},
					barMaxWidth: 50, // 条形图最大宽度设置
					itemStyle: {
						normal: {
							color: function (ui) { // 数据不同 渲染不同颜色
								if (ui.value < 0) {
									return '#c23531'
								} else {
									return '#6fab2c'
								}
							}
						}
					},
				}


				]
			}
			chart1.setOption(option);
			chart2.setOption(option);
		}

	function loadChart1Data() {
		// chart1 = echarts.init($('#chart1').get(0));
		var formPara = {
			check_plan_no : check_plan_no
		};
		ajaxJsonObjectByUrl("queryCheckPlanStore.do?isCheck=false", formPara, function(res) {
			var ydataList = res.Rows.map(function (item) {
				return item.store_name;
			});
			var valueList = res.Rows.map(function (item) {
				return item.amount;
			});

			chart1.setOption({
				yAxis: {
					data: ydataList // y轴上对应的数据 
				},
				series: [{
					data: valueList // 对应时间上的值
				}]
			});
		});
// 		ajaxJsonObjectByUrl({
// 			url: "queryCheckPlanStore.do?isCheck=false", formPara,
// 		}, function (res) {
// 			/* var　chartData=[];
// 			if (res.Rows.length>0) {
// 				var row=res.Rows;
// 				for(var i=0;i<row.length;i++){
// 					chartData.push([row[i].cert_type_name,row[i].days]);
// 				}
// 			} */
// 			alert(res);
// 			var ydataList = res.Rows.map(function (item) {
// 				return item.store_name;
// 			});
// 			var valueList = res.Rows.map(function (item) {
// 				return item.amount;
// 			});

// 			chart1.setOption({
// 				yAxis: {
// 					data: ydataList // y轴上对应的数据 
// 				},
// 				series: [{
// 					data: valueList // 对应时间上的值
// 				}]
// 			});
// 		});

	}

	function loadChart2Data() {
		var formPara = {
				check_plan_no : check_plan_no
			};
			ajaxJsonObjectByUrl("queryCheckPlanDept.do?isCheck=false", formPara, function(res) {
				
				var ydataList = res.Rows.map(function (item) {
					return item.dept_name;
				});
				var valueList = res.Rows.map(function (item) {
					return item.amount;
				});

				chart2.setOption({
					yAxis: {
						data: ydataList // y轴上对应的数据 
					},
					series: [{
						data: valueList // 对应时间上的值
					}]
				});
			});
		/*  ajaxPostLay({
			url: "queryCheckPlanDept.do?isCheck=false&check_plan_no="+check_plan_no
		}, function (res) {
			
			var ydataList = res.Rows.map(function (item) {
				return item.dept_name;
			});
			var valueList = res.Rows.map(function (item) {
				return item.amount;
			});

			chart2.setOption({
				yAxis: {
					data: ydataList // y轴上对应的数据 
				},
				series: [{
					data: valueList // 对应时间上的值
				}]
			});
		});  */
		/* var option = {
			tooltip: {
				 trigger: 'axis', 
				axisPointer: { // 坐标轴指示器，坐标轴触发有效
					type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				},
				formatter: function (arr) {    //  可以自定义标签label
					var htm = '';
					var days = arr[0].value * 1;
					htm += '<p>' + arr[0].axisValueLabel + '</p>';
					if (days < 0) {
						htm += arr[0].marker + '已过期天数:' + Math.abs(days);
					} else {
						htm += arr[0].marker + '即将过期天数:' + Math.abs(days);
					}
					return htm;
				}
			},

			grid: {
				left: '3%',
				right: '9%',
				bottom: '3%',
				containLabel: true,
			},
			xAxis: [{
				type: 'value',
				name: '天数',
				axisLine: {
					lineStyle: {
						color: '#1e9fff' // X 轴坐标线颜色
					}
				},
				splitLine: { lineStyle: { type: 'dashed' } }  // 分割线颜色
			}],
			yAxis: [{
				type: 'category',
				axisTick: {
					show: false
				},
				axisLine: {
					lineStyle: {
						color: '#1e9fff' // Y 轴坐标线颜色
					}
				}
			}],
			series: [{
				name: '即将到期天数',
				type: 'bar',
				label: {
					normal: {
						show: true,
						position: 'inside'
					}
				},
				barMaxWidth: 50, // 条形图最大宽度设置
				itemStyle: {
					normal: {
						color: function (ui) { // 数据不同 渲染不同颜色
							if (ui.value < 0) {
								return '#c23531'
							} else {
								return '#6fab2c'
							}
						}
					}
				}
			}


			]
		}
		chart2.setOption(option); */
	}	

</script>

</head>

<body style="padding: 10px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

		<div class="layui-row">
		<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
			<div class="panel panel-default  mr10 mb10">
				<div class="panel-header flex">
					<div class="title">
						仓库盘点统计
					</div>
					<div class="btns">
						<!-- button class="layui-btn layui-btn-xs layui-btn-primary">
							<i class="layui-icon">&#xe64c;</i>
						</button-->
						<!-- <button id="chart1_r" class="layui-btn layui-btn-xs layui-btn-primary">
							<i class="layui-icon">&#x1002;</i>
						</button> -->
					</div>
				</div>
				<div class="panel-body">
					<div id="chart1" style="height: 600px"></div>
				</div>
			</div>
		</div>
		<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
			<div class="panel panel-default  mr10 mb10">
				<div class="panel-header flex">
					<div class="title">
						科室盘点统计
					</div>
					<div class="btns">
						<!-- button class="layui-btn layui-btn-xs layui-btn-primary">
							<i class="layui-icon">&#xe64c;</i>
						</button-->
						<!-- <button id="chart2_r" class="layui-btn layui-btn-xs layui-btn-primary">
							<i class="layui-icon">&#x1002;</i>
						</button> -->
					</div>
				</div>
				<div class="panel-body">
					<div id="chart2" style="height: 600px"></div>
				</div>
			</div>
		</div>
	</div>
	

	<div id="maingrid"></div>

</body>
</html>
