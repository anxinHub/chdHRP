<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,tab,from,select" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var grid1, grid2, etTab, year, month, year_before, month_before, attend_code;
	
	var initGrid1 = function () {
		var columns = [ {
			display: '考勤年份', name: 'attend_year', width: 90, 
		}, {
			display: '考勤月份', name: 'attend_month', width: 90, 
		}, {
			display: '开始时间', name: 'begin_date', width: 120, 
		}, {
			display: '结束时间', name: 'end_date', width: 120, 
		}, {
			display: '是否结账', name: 'hr_flag', width: 90,
			render : function(ui){
				if(ui.rowData.hr_flag == 0){
					return "否";
				}else{
					return "是";
				}
			}
		}, { 
			display: '结账日期', name: 'hr_date', width: 120,
		}, {
			display: '操作人', name: 'hr_user', width: 120,
		} ];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: false,
			usePager: false,
			columns: columns,
			//flexHeight: true, 
		};
		
		grid1 = $("#mainGrid1").etGrid(paramObj);
	};
	
	var query1 = function () {
		params = [ {
			name: 'attdent_year', value: year
		} ];
		
		grid1.loadData(params, "../attend/queryHrPeriod.do?isCheck=false");
	}
	
	var initGrid2 = function () {
		var columns = [ {
			display: '年度', name: 'del_year', width: 90, 
		}, {
			display: '月份', name: 'del_month', width: 90,
		}, {
			display: '职工', name: 'emp_name', width: 120,
		}, {
			display: '考勤项目', name: 'attend_name', width: 120,
		}, {
			display: '额度(天)', name: 'attend_ed', width: 90,
		}, {
			display: '清除时间', name: 'del_date', width: 120,
		}, {
			display: '操作人', name: 'oper_name', width: 120,
		} ];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: false,
			usePager: false,
			columns: columns,
			//flexHeight: true, 
		};
		
		grid2 = $("#mainGrid2").etGrid(paramObj);
	};
	
	var query2 = function () {
		params = [ {
			name: 'year', value: year, 
			name: 'month', value: month
		} ];
		
		grid2.loadData(params, "queryAttendXjedDel.do?isCheck=false");
	}
	
	var changeText = function(res){
		if(res){
	    	year = res.year; 
	    	month = res.month; 
			year_before = res.year_before; 
			month_before = res.month_before; 
		}
		$("#year_month").text(year + "-" + month);
		$("#before_year_month").text(year_before + "-" + month_before);
	}

	var initForm = function () {
    	attend_code = $("#attend_code").etSelect({
            url: "../../queryAllAttendCode.do?isCheck=false",
            defaultValue: "none",
        });
	};
	
	//获取期间
	var getYearMonth = function(){
		ajaxPostData({
			url : 'queryAttendTermendYearMonth.do?isCheck=false',
			async: false, 
			data : {},
		    success: function (res) {
				changeText(res);
			},
		})
	}
	
	//清除余额
	var clearData = function(){
		
		$.etDialog.open({
			title: '清除余额',
			type: 1, 
			content: $("#itemDiv"), 
			width: 300, 
			height: 300,
			btn: ['确定', '取消'],
			btn1: function (index, el) {
				if(!attend_code.getValue()){
					$.etDialog.warn('请选择需要清除余额的考勤项目');
					return;
				}
				ajaxPostData({
					url : 'addAttendXjedDel.do',
					data : {
						year: year, 
						month: month, 
						attend_code: attend_code.getValue
					},
				    success: function (res) {
						$.etDialog.close(index); // 关闭弹窗
					},
				})
			}
		});
	}
	
	//结账
	var confirm = function(){
		if(!year || !month){
			$.etDialog.warn('下一期间不存在');
			return false;
		}
    	$.etDialog.confirm('是否结账?', function () { 
			ajaxPostData({
				url : 'confirmAttendTermend.do',
				data : {
					year : year,
					month : month
				},
			    success: function (res) {
					changeText(res);
					query1();
				},
			});
    	});
	}
	
	//反结账
	var unConfirm = function(){

		if(!year_before || !month_before){
			$.etDialog.warn('上一期间不存在');
			return false;
		}
    	$.etDialog.confirm('是否反结账?', function () {
			ajaxPostData({
				url : 'unConfirmAttendTermend.do',
				data : {
					year : year_before,
					month : month_before
				},
			    success: function (res) {
					changeText(res);
					query1();
				},
			});
    	});
	}
	
	var initTab = function(){
		etTab = $("#etTab").etTab({
			onChange: function(item){ 
				var index = item.index + 1;
				// 切换tab刷新grid(刷新视图和数据,也会重新加载后台数据)
				window['grid' + index].refreshDataAndView();
			}
		});
	}

	$(function () {
		getYearMonth();
		initForm();
		initTab();
		initGrid1();
		initGrid2();
		query1();
		query2();
		
		$("#clearData").click(function(){
			clearData();
		});
		
		$("#confirm").click(function(){
			confirm();
		});
		
		$("#unConfirm").click(function(){
			unConfirm();
		});
	})
</script>

<body>
	<div class="center">
		<table class="table-layout">
			<tr>
				<td class="label">结账期间：</td>
				<td style="width: 100px; text-align: left;">
					<span id="year_month"></span>
				</td>
				<td>
					<button id="clearData">清除余额</button>
					<button id="confirm">结账</button>
				</td>
			</tr>
			<tr>
				<td class="label">反结账期间：</td>
				<td style="width: 100px; text-align: left;">
					<span id="before_year_month"></span>
				</td>
				<td>
					<button id="unConfirm">反结账</button>
				</td>
			</tr>
		</table>
		<div>
			<div id="etTab">
				<div title="考勤周期" tabid='tab_1'>
					<div id="mainGrid1"></div>
				</div>
				<div title="历史清除余额" tabid='tab_2'>
					<div id="mainGrid2"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="itemDiv" style="display: none">
		<table class="table-layout">
			<tr>
				<td class="label">考勤项目：</td>
				<td class="ipt">
					<select id="attend_code" type="text" style="width:160px"/>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>