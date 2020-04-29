<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 150});
		
		changeYearMonth("${acc_year_month}");

		$("#but_start").ligerButton({click: start, width:120});
		$("#but_cancel").ligerButton({click: cancel, width:120});
	});
	
	function start(){
		$.ligerDialog.confirm('确定结账?', function (yes){
			if(yes){
				//检查是否需要判断出纳和工资已结账  
				if("${a01024}" == 1 || "${a01025}" == 1 ){
					//判断出纳和工资已结账 
					var paras={
						acc_year : $("#year_month").text().split(".")[0],
						acc_month : $("#year_month").text().split(".")[1],
						check_wage : "${a01024}",
						check_cash : "${a01025}"
					};
					ajaxJsonObjectByUrl("queryAccClosingCheckStart.do",paras,function(responseData){
						if(responseData.state=="true"){ 
							var paras={
									acc_year : $("#year_month").text().split(".")[0],
									acc_month : $("#year_month").text().split(".")[1]
							};
							ajaxJsonObjectByUrl("confirmAccClosing.do",paras,function (responseData){
								if(responseData.state == "true"){ 
									changeYearMonth(responseData.year_month);
								}
							}); 
					 	}
					}); 
				}else{
					//不需要判断出纳和工资已结账 
					var paras={
							acc_year : $("#year_month").text().split(".")[0],
							acc_month : $("#year_month").text().split(".")[1]
					};
					ajaxJsonObjectByUrl("confirmAccClosing.do",paras,function (responseData){
						if(responseData.state == "true"){ 
							changeYearMonth(responseData.year_month);
						}
					}); 
				}
			}
		});
	}
	
	function cancel(){
		var acc_year=$("#year_month").text().split(".")[0];
		var acc_month=$("#year_month").text().split(".")[1];
		
		if(acc_year=='2019'&& acc_month=='01'){
			
			$.ligerDialog.error('2019年01月不能反结账！');
			
			return;
			
		}
		$.ligerDialog.confirm('确定反结账?', function (yes){
			if(yes){
				 var paras={
					acc_year : $("#year_month").text().split(".")[0],
					acc_month : $("#year_month").text().split(".")[1]
				};
				ajaxJsonObjectByUrl("reconfirmAccClosing.do",paras,function(responseData){
					if(responseData.state=="true"){ 
						changeYearMonth(responseData.year_month);
					}
				});
			}
		});
	}
	
	function changeYearMonth(year_month){
		var acc_year = year_month.substring(0, 4);
		var acc_month = year_month.substring(4, 6);
		var acc_year_before, acc_month_before;
		if(acc_month == '01'){
			acc_year_before = parseInt(acc_year) - 1;
			acc_month_before = '12';
		}else{
			acc_year_before = acc_year;
			acc_month_before = ('0' + (parseInt(acc_month) - 1).toString());
			acc_month_before = acc_month_before.substring(acc_month_before.length-2, acc_month_before.length);
		}
		
		$("#year_month").html(acc_year + '.' + acc_month);
		$("#year_month_before").html(acc_year_before + '.' + acc_month_before);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 20px">
			<tr>
				<td align="right" class="l-table-edit-td" width="180">
					当前期间：<span id="year_month"></span>
				</td>
				<td align="right" class="l-table-edit-td" >
					<input type="button" id="but_start" value="开始结账"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" width="180">
					上一期间：<span id="year_month_before"></span>
				</td>
				<td align="right" class="l-table-edit-td" >
					<input type="button" id="but_cancel" value="反结账"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
