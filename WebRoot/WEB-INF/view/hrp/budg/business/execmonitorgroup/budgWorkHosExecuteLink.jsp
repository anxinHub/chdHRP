<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
var grid;
var gridManager = null;
var userUpdateStr ;
var sum_year='${sum_year}';
	$(function (){
	  	loadHead(); 
	}); 
   
	function loadHead(){
		grid = $("#maingrid").etGrid({
        	 columns: [ 
				{ display: '预算值', name: 'budg_value', align: 'right',width: 600,
				 		render:function(ui){
				 			var value = ui.cellData;
				 			if(value){
				 				return formatNumber(value,2,1);
				 			}
				 		}
					 },
			    { display: '预算下达日期', name: 'issue_data',minWidth: 600							 			 
					 }
             ],
             dataModel: {
 	   			method: 'POST',
 	   			location: 'remote',
 	   			url: "queryBudgWorkCheckHosYear.do?isCheck=false&sum_year="+"${sum_year}"+"&year="+"${year}"+"&index_code="+"${index_code}",
 	   			recIndx: 'year'
   			 },
   			 usePager: true, width: '100%', height: '100%', checkbox: false
         });
    }
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="maingrid" ></div>
</body>
</html>