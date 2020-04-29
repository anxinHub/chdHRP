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
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
	 var initSubGrid = function () {
         var columns = [
         	 { display: '标的物类型', name: 'subject_type_name',width: '10%'},
              { display: '标的物名称', name: 'subject_name',width: '12%'},
              { display: '变动前数量', name: 'amount_b', width: '9%',align: "right"},
              { display: '变动前单价', name: 'price_b', width: '9%',align: "right"},
              { display: '变动后数量', name: 'amount_a', width: '9%',align: "right"},
              { display: '变动后单价', name: 'price_a', width: '9%',align: "right"},
              { display: '单价变动', name: 'price_c', width: '9%',align: "right"},
              { display: '金额变动', name: 'money_c', width: '9%',align: "right"},
              { display: '关联卡片', name: '',width: '9%',align: "right"},              
              { display: '原值变动单', name: '',width: '9%', align: 'center',},
         ];
         var paramObj = {
        	 editable: false,
             height: '97%',
             width:'100%',
             usePager: true,
             dataModel: {
	             url: 'queryPactMoneyChangeFKHTDet.do?isCheck=false&pact_code=${pact_code}&change_code=${change_code}'
             },
             columns: columns,
         };
         
       	subGrid = $("#subGrid").etGrid(paramObj);
     };
	
	$(function(){
		initSubGrid();
	})
	

</script>
</head>

<body style="overflow: scroll; ">
	<div id="subGrid"></div>
</body>

</html>

