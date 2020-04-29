<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    //out.print(path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=path%>/lib/hrp/script/orgchart/jquery.orgchart.css">
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/hrp/script/orgchart/html2canvas.min.js"></script>
<script src="<%=path%>/lib/hrp/script/orgchart/jquery.orgchart.js"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js"></script>

<style type="text/css">

.orgchart {
  background: #fff; 
}
.orgchart td.left, .orgchart td.right, .orgchart td.top {
  border-color: #aaa;
}
.orgchart td>.down {
  background-color: #aaa;
} 
.orgchart .group .title {
  background-color: #006699;
}
.orgchart .group .content {
  border-color: #006699;
}
.orgchart .hos .title {
  background-color: #009933;
}
.orgchart .hos .content {
  border-color: #009933;
}

.orgchart .last .title {
  background-color: #996633;
}
.orgchart .last .content {
  border-color: #996633;
}

</style>
<script>
	var type;
	$(function (){
		
		//$("#chart-container").css("text-align","left");
		//$("#chart-container").css("height",$(window).height());
		  /* var datascource = {
	      'name': 'admin',
	      'title': '系统管理员',
	      'children': [
	         { 'name': 'dysuper', 'title': '东阳集团', 'className': 'group',
	          'children': [
	            { 'name': 'dyadmin1', 'title': '东阳医院1', 'className': 'hos' },
	            { 'name': 'dyadmin2', 'title': '东阳医院2', 'className': 'hos',
	              'children': [
	                { 'name': '001', 'title': '总账', 'className': 'last' },
	                { 'name': '002', 'title': '基建', 'className': 'last' },
	                { 'name': '003', 'title': '后勤', 'className': 'last' }
	              ]
	            }
	          ]
	        },
	        { 'name': 'tzsuper', 'title': '台州集团', 'className': 'group',
	          'children': [
	            { 'name': 'tzadmin1', 'title': '台州中心XXXXXXXX医院1', 'className': 'hos' },
	            { 'name': 'tzadmin2', 'title': '台州中心XXXXXXXX医院2', 'className': 'hos',
	              'children': [
	                { 'name': '001', 'title': '总账', 'className': 'last' },
	                { 'name': '002', 'title': '基建', 'className': 'last' },
	                { 'name': '003', 'title': '后勤', 'className': 'last' }
	              ]
	            }
	          ]
	        } 
	      ]
	    };  */
		
	    query();
	   
	});
	
	
	function query(){
		var index = layer.load(1);
		$("#chart-container").html("");
		ajaxJsonObjectBylayer("queryDeptOrg.do?isCheck=false", {}, function(result) {
			 $('#chart-container').orgchart({
			      'data' : result,
			      'nodeContent': 'title',
			      'exportButton': true,
			      'exportFilename': 'MyOrgChart',
			     // 'direction': 'l2r'
			      /* 'pan': true,
			       'zoom': true*/ 
			  });
	    },layer,index);
	}
</script>

</head>

<body>
	
   <div id="chart-container"></div>
</body>



</html>
