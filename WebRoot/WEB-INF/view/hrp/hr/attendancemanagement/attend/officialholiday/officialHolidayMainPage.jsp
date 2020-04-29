
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,grid,select,datepicker" name="plugins" />
    </jsp:include>
    
<!-- 日历   --> 
<link rel="stylesheet" href="<%=path%>/lib/calendar/calendar.css">
<script src="<%=path%>/lib/calendar/calendar.js" type="text/javascript"></script>
<script src="<%=path%>/lib/calendar/index.js"></script>

    <script>
        var hos_name,
            year,
            holiday_name,
            grid;
        $(function () {
            initDict();
        })

        function initDict() {
        /*     hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            });  */
        }

    </script>
</head>

<body style="overflow-y:auto">
    <div class="main">
     <!--    <table class="table-layout" style="float:left">
            <tr>
                <td class="label">单位信息：</td>
                <td class="ipt">
                    <select id="hos_name"  style="width:180px" disabled></select>
                </td>
            </tr>
        </table> -->
        <div id="selectYear" style="float:left">
	   		<div id="YL" class="btn"><</div>
	       	<select class="SY"></select>
	       	<div id="YR" class="btn">></div>
	       	<div class="text">年</div>
	   	</div>
	   	 <div id="saveData" class="button-group" style="text-align: left;margin-top: -5px">
	   	 	<button class='saveChange'>保存</button>
	   	 </div>
    </div>
    
    
    <div id="calendar" style="width:100%;min-width: 1100px"></div>

</body>

</html>