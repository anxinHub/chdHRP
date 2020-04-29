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
      map.put('tabitem1' ,["tabitem1","costProjectTrendMainPage.do?isCheck=false"]);
      map.put('tabitem2' ,["tabitem2","costProjectCompareMainPage.do?isCheck=false"]);
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
      <div  title="成本项目趋势分析" style="height:580px;"><iframe id="tabitem1" frameborder="0" width='100%' height="500px"></iframe></div>
	  <div  title="成本项目比较" style="height:580px;"><iframe id="tabitem2" frameborder="0" width='100%' height="500px"></iframe></div>
     </div>
</body>
</html>