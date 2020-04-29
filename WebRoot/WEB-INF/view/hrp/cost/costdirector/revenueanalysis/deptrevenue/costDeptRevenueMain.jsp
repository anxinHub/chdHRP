<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室收入分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
      var map = new HashMap();
      map.put('tabitem1' ,["tabitem1","costDeptRevenueConstituteMainPage.do?isCheck=false"]);
      map.put('tabitem2' ,["tabitem2","costDeptRevenueTrendMainPage.do?isCheck=false"]);
      map.put('tabitem3' ,["tabitem3","costDeptRevenueCompareMainPage.do?isCheck=false"]);
      map.put('tabitem4' ,["tabitem4","costDeptRevenueOpeningOrderMainPage.do?isCheck=false"]);
      map.put('tabitem5' ,["tabitem5","costDeptRevenueImplementMainPage.do?isCheck=false"]);
      $(function(){
    	  loadTab();
    	  loadSrc("tabitem1");
      });
      function loadTab(){
    	  $("#tab").ligerTab({
    		    contextmenu:false,
    		    onAfterSelectTabItem: function (tabid)
    		    {
    		    	loadSrc(tabid);
                } 
              });
        }

      function loadSrc(tabid){
    	if($("#"+map.get(tabid)[0]+"").attr("src") == null)
    	   $("#"+map.get(tabid)[0]+"").attr("src",map.get(tabid)[1]);
    }

      
</script>
</head>
<body style="padding: 0px;overflow:hidden;">
    <div id="tab" style="width: 100%;overflow:hidden; border:1px solid #A3C0E8; " >
	  <div  title="构成分析" style="height:580px;"><iframe id="tabitem1" frameborder="0" width='100%' height="500px"></iframe></div>
      <div  title="趋势分析" style="height:580px;"><iframe id="tabitem2" frameborder="0" width='100%' height="500px"></iframe></div>
      <div  title="比较分析" style="height:580px;"><iframe id="tabitem3" frameborder="0" width='100%' height="500px"></iframe></div>
      <div  title="开单收入分析" style="height:580px;"><iframe id="tabitem4" frameborder="0" width='100%' height="500px"></iframe></div>
      <div  title="执行收入分析" style="height:580px;"><iframe id="tabitem5" frameborder="0" width='100%' height="500px"></iframe></div>
     </div>
</body>
</html>