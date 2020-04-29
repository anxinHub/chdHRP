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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        //loadDict();//加载下拉框
        setValue();
        $("#carry_start_button").click(function(){
			carry_start();
		});
	
		$("#carry_cancel_button").click(function(){
			carry_cancel();
		});
		
    });
   
    // 待结转月份 、已结转月份 赋值
	function setValue(){
		$.post("setValue.do?isCheck=false",null,function(responseData){
			var data = JSON.parse(responseData);
			$("#year_month_before").val(data.year_month_before);
			$("#year_month").val(data.year_month);
		});
	}
    
   //结转
	function carry_start(){  
	   
		var cur_year_month = $("#year_month").val();
		if(cur_year_month == ''){
			$.etDialog.warn('请先启用资金预算管理模块 ');
			return ; 
		}
	   
		$.etDialog.confirm('确定结转吗?', function (yes){
			var paras = {
				year: $("#year_month").val().substring(0,4),
			    month: $("#year_month").val().substring(4,6)
			};
			ajaxPostData({
				url: "chargeBudgWork.do?isCheck=false",
				data: paras,
				success: function (responseData) {
					setValue();
				}
			});
		});
   }
   
 //反结
  function carry_cancel(){ 
	  	var pre_year_month = $("#year_month_before").val();
		if(pre_year_month == ''){
			$.etDialog.warn('未有已结账月,不能反结  ');
			return ;
		}
		
		$.etDialog.confirm('您确定反结转吗?', function (yes){
			var paras = {
				year:$("#year_month_before").val().substring(0,4),
				month: $("#year_month_before").val().substring(4,6)
			};
			ajaxPostData({
				url: "reChargeBudgWork.do?isCheck=false",
				data: paras,
				success: function (responseData) {
					setValue();
				}
			});

		});
   }
   
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<div class="main" style="padding:50px;">
		<table class="table-layout">
			<tr>
				<td class="label">待结转月份：</td>
				<td class="ipt">
					<input type="text" id="year_month" disabled="disabled"/>
				</td>
				<td class="ipt">
					<button id="carry_start_button">结转</button>
				</td>
			</tr>
			<tr>
				<td class="label">已结转月份：</td>
				<td class="ipt">
					<input type="text" id="year_month_before" disabled="disabled"/>
				</td>
				<td class="ipt">
					<button id="carry_cancel_button">反结</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
