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
	<jsp:param value="select,datepicker,ligerUI,dialog" name="plugins" />
</jsp:include>

<script type="text/javascript">
	
	//初始化
	$(function(){
		
		init_yearMonth();//初始化结转日期
	
		$("#carry_start_button").click(function(){
			carry_start();
		});
	
		$("#carry_cancel_button").click(function(){
			carry_cancel();
		});
	});
	
	//结转
	function carry_start() {
		
		var cur_year_month = $("#cur_year_month").val();
		if(cur_year_month == ''){
			$.etDialog.warn('请先启用业务预算管理模块 ');
			return ; 
		}
		
		$.etDialog.confirm('确定结转吗?', function () {
			var paras = {
				year: cur_year_month.substring(0, 4),
				month: cur_year_month.substring(4, 6),
				budg_year : cur_year_month.substring(0, 4),
				cur_year_month : cur_year_month
			};
			
			ajaxPostData({
				url: "chargeBudgWorkCarry.do",
				data: paras,
				success: function (responseData) {
					init_yearMonth();
				}
			});
		});
	}

	//反结
	function carry_cancel() {
		
		var pre_year_month = $("#pre_year_month").val();
		if(pre_year_month == ''){
			$.etDialog.warn('未有已结账月,不能反结  ');
			return ;
		}
		
		$.etDialog.confirm('确定反结吗?', function () {
			var paras = {
				pre_year_month : pre_year_month,
				year: pre_year_month.substring(0, 4),
				month: pre_year_month.substring(4, 6)
			};
			
			ajaxPostData({
				url: "reChargeBudgWorkCarry.do",
				data: paras,
				success: function (responseData) {
					init_yearMonth();
				}
			});
		});
	}
	
	//查询结转期间
	function init_yearMonth(){
		ajaxJsonObjectByUrl("queryYearMonth.do?isCheck=false",{}, function(responseData){
			if (responseData) {
				$("#cur_year_month").val(responseData.cur_year_month);
				$("#pre_year_month").val(responseData.pre_year_month);
			}
		});
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div class="main" style="padding:50px;">
		<table class="table-layout">
			<tr>
				<td class="label">待结转月份：</td>
				<td class="ipt">
					<input type="text" id="cur_year_month" disabled="disabled" />
				</td>
				<td class="ipt">
					<button id="carry_start_button">结转</button>
				</td>
			</tr>
			<tr>
				<td class="label">已结转月份：</td>
				<td class="ipt">
					<input type="text" id="pre_year_month" disabled="disabled"/>
				</td>
				<td class="ipt">
					<button id="carry_cancel_button">反结</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>