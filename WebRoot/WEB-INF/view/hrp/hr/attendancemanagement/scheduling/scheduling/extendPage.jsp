<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,grid,hr,datepicker,checkbox" name="plugins" />
</jsp:include>
<script>
	
	var attend_pbdate,check_defualt;
	$(function() {
		/* 日期 */
		var beginDate,endDate;
		var attend_pbrule='${attend_pbrule}';
		if(attend_pbrule=="0"){
			//按周排班
			beginDate=getDateAddDay('${begin_date}',-7);
			endDate=getDateAddDay('${begin_date}',-1);
		}else{
			//按月排班
			endDate=getDateAddDay('${begin_date}',-1);
			var date=new Date(endDate);
			var year = date.getFullYear();
			var month=parseInt(date.getMonth()+1);
			if(month<10){
				month="0"+month;
			}
			beginDate= year + "-" + month + "-01"; 
		}
		
		
		attend_pbdate = $("#attend_pbdate").etDatepicker({
			range : true,
			dateFormat : "yyyy-mm-dd",
			defaultDate : [ beginDate, endDate],
			onShow: function(picker) {
				 picker.update({
					//minDate : new Date(startDate),
					maxDate : new Date(endDate)
				}) 
			}
		});
		check_defualt = $('#check_defualt').etCheck({
			onChange: function (status) {
				state = status;
		  	}
		});
		
	})
	function getDate(){
			return attend_pbdate.getValue();
		}
	function getCheck(){
		return $("#check_defualt").prop("checked") ? 1 : 0;
	}
</script>
</head>

<body>
	<div class="container">
		<div class="center">
			<table class="table-layout">
				<tr>
					<td class="label">日期：</td>
					<td class="ipt"><input id="attend_pbdate" type="text"></td>
				</tr>
					<tr>
					<td class="label"><input id="check_defualt" type="checkbox" /></td>
				    <td><label for="check_defualt">是否包含数据</label></td>
				</tr>
			</table>
		</div>
	</div>
</body>

</html>